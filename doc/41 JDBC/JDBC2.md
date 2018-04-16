[TOC]

# 一、JDBC 对事务的操作

## 1.1 默认管理事务

- JDBC默认会自动管理事务；
- 每次调用 executeUpdate() 时它会自动 commit。这只适合一个业务内只包含1次 DML。

## 1.2 手动管理事务

- 取消自动 conn.setAutoCommit(false)；
- 手动提交 conn.commit()；
- 手动回滚 conn.rollback()。

## 1.3 什么是事务

满足如下4个特征的数据库访问叫事务：

- 原子性：事务是一个完整的过程，要么都成功，要么都失败；
- 一致性：事务访问前后，数据要一致，即收支平衡；
- 隔离性：事务过程中访问的数据要隔离，不允许别人访问；
- 持久性：事务一旦达成，就永久有效。

上述4句话，是一个整体，构成事务的特征。

# 二、批量添加数据

![ 批量新增数](/Users/chenjinhua/ziliao/JavaLearned/doc/41 JDBC/9 批量新增数据.png)



![ 批量添加数据](/Users/chenjinhua/ziliao/JavaLearned/doc/41 JDBC/9 批量添加数据2.png)



```java
package day03;

import day01.util.DBUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

/**
 * Created by jinhua.chen on 2018/3/18.
 */
public class TestDay03 {
    /*
    测试: 批量添加 108 个员工.
    每次提交添加 50 个员工信息.
     */
    @Test
    public void test1(){
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            String sql = "INSERT INTO emp VALUES (emp_seq.nextval,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (int i = 0; i < 108; i++) {
                // 将数据暂存到 ps 对象里.
                ps.setString(1,"三班"+i);
                ps.setDate(3,new Date(System.currentTimeMillis()));
                ps.setDouble(2,50.0);
                ps.addBatch();
                
                // 每50个数据提交1次.
                if (i % 50 == 0){
                    ps.executeBatch();
                    ps.clearBatch();
                }
            }
            
            // 为了避免有零头,再提交1次.不需要 clear.因为 clear 是为下一批数据的插入.这里不需要.
            ps.executeBatch();
            conn.commit();
            
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtil.rollback(conn);
        }finally {
            DBUtil.close(conn);
        }

    }
}
```



补充：JDBC 中的日期类型

在 JDBC 中要使用 java.sql 下的日期类型：

java.sql.Date：年月日；

java.sql.Time：时分秒；

java.sql.Timestamp：年月日时分秒；

上述日期都是 java.util.Date 的子类。

# 三、返回自动主键

获取自动生成的 ID：

![0 返回自动主](/Users/chenjinhua/ziliao/JavaLearned/doc/41 JDBC/10 返回自动主键.png)

第一种方式：反查 ID

INSERT INTO users VALUES（seq.nextval,?,?）

SELECT id FROM users WHERE username=?

第二种方式：记录 ID

第三种方式：

## 3.1 让 ps 对象返回生成的 ID

 实战：向部门表里插入一条数据,然后向对应部门增加一个员工信息.

```java
package day03;
import day01.util.DBUtil;
import org.junit.Test;
import java.sql.*;
import java.util.Random;

/**
 * Created by jinhua.chen on 2018/3/18.
 */
public class TestDay03 {
    /*
    返回自动生成的 ID
    向部门表里插入一条数据,然后向对应部门增加一个员工信息.
     */
    @Test
    public void test2(){
        // 假设页面传过来的部门信息:
        String dname = "测试部";
        String locCity = "杭州";
        // 假设页面传过来的员工信息为:
        String ename = "八戒";
        String job = "取经";
        int mgr = 0;
        Date hireDate = new Date(System.currentTimeMillis());
        double salary = 5000.0;
        Connection conn = null;

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "INSERT INTO dept VALUES(dept_seq.nextval,?,?)";

            // 参数2是一个数组,用来告诉 ps 需要它返回哪些字段, 数组里写字段名.
            PreparedStatement ps = conn.prepareStatement(sql1,new String[]{"deptno"});
            ps.setString(1,dname);
            ps.setString(2,locCity);
            ps.executeUpdate();

            // 从 ps 中获取生成的主键.
            // 结果集包含1行1列,因为数组是1个所以是1列,又因只插入1个数据所以是1行.
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            // 这种场景下的结果集只能通过字段的序号获取值,不可以写字段名.
            int deptno = rs.getInt(1);

            String sql2 = "INSERT INTO emp VALUES(emp_seq.nextval,?,?,?,?,?,?)";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setString(1,ename);
            ps2.setString(2,job);
            ps2.setInt(3,mgr);
            ps2.setDate(4,hireDate);
            ps2.setDouble(5,salary);
            ps2.setInt(6,deptno);
            ps2.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtil.rollback(conn);
        }finally {
            DBUtil.close(conn);
        }

    }
}
```

# 四、JDBC 对分页的支持

补充：在不同的数据库中分页的 SQL 语句是不同的。在 oracle 中分页是借助行号ROWNUM 的。

- oracle 提供了一个伪列：ROWNUM，在查询结果集会自动加上这一列，该字段的值是结果集中每条记录的行号。
- ROWNUM 的值是动态生成的，伴随查询过程，只要可以查出一条记录，ROWNUM 就会为该条记录生成行号，从1开始每次递增1。
- 由于 ROWNUM 是在查询表的过程中编号的，所以在使用 ROWNUM 对结果集编行号的查询过程中，不要使用 ROWNUM 做大于1以上数字的判断，否则结果集没有任何数据（以为此时 ROWNUM 还没有值）。

如以下查询结果必为空：

```sql
SELECT ename FROM emps WHERE ROWNUM BETWEEN 6 AND 10
```

 应该先查询一遍，生成 ROWNUM 后再根据 ROWNUM 筛选：

```sql
SELECT ename FROM
				（SELECT ename FROM emps ORDER BY empno）e 	 # e是结果集的别名。
WHERE ROWNUM BETWEEN 6 AND 10
```

再如：

```sql
SELECT * FROM 
	(SELECT e.*,ROWNUM r FROM (SELECT * FROM emps ORDER BY empno) e)
WHERE r BETWEEN 6 AND 10
```

4.1 假分页（内存分页）

第一次查询时就获取所有数据，并将其存入内存（java 程序/对象即内存如 List ）；第 N 次查询时不再访问数据库，而是直接从内存取数据。

适合数据量小的项目。

4.2 真分页（物理分页）

- 每次查询都是获取一页的数据；
- 使用分页 SQL 进行查询；
- 特点：每次查询速度都一样，不消耗内存；
- 适合所有的项目。

![1 JDBC 对分页查询的支](/Users/chenjinhua/ziliao/JavaLearned/doc/41 JDBC/11 JDBC 对分页查询的支持.png)

## 4.3 分页查询员工

```
/*
分页查询员工.
 */
@Test
public void test3(){
    // 假设需求规定每一页显示10行.
    int size = 10;
    // 假设用户点了第3页
    int page = 3;

    Connection conn = null;
    try {
        conn = DBUtil.getConnection();
        String sql = "SELECT * FROM (SELECT e.*,ROWNUM r FROM (SELECT * FROM emps ORDER BY empno) e)WHERE r BETWEEN ? AND ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1,(page-1) * size + 1);
        ps.setInt(2,page * size);

        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            System.out.println(rs.getInt("empno"));
            System.out.println(rs.getString("ename"));
            System.out.println(rs.getString("job"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }finally {
        DBUtil.close(conn);
    }
}
```