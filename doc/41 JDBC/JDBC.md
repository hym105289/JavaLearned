

[TOC]

![ web 框架技](/Users/chenjinhua/ziliao/JavaLearned/doc/41 JDBC/0 web 框架技术.png)



 

# 一、JDBC 工作原理



![ JDBC 接口及其实现 drive](/Users/chenjinhua/ziliao/JavaLearned/doc/41 JDBC/1 JDBC 接口及其实现 driver.png)

**1、sun 公司只提供了 JDBC 接口；**

**2、数据库厂商提供实现类；**

**3、由 DriverManager 实例化这个实现类.**



# 二、JDBC 工作过程

​	**使用 JDBC 的步骤：**

**加载JDBC驱动程序 → 建立数据库连接Connection → 创建执行SQL的语句Statement → 处理执行结果ResultSet → 关闭连接。**



## 2.1 注册驱动，建立连接 

```java
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by jinhua.chen on 2018/3/17.
 */
public class TestDay01 {

    @Test
    public void test1(){
        Connection conn = null;
        // 注册驱动
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("url","user","password");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```

上面的实现存在很多问题，用户 driver、url、user、password 都可能会变化，那每发生变化就需要改变源码。

## 2.2 连接管理

**在工程中，编写一个访问数据库的工具类。**

**方法：把数据库配置写在 Properties 属性文件里，工具类读取属性文件，逐行获取数据库参数。**

![ 连接数据库工具类 Propertie](/Users/chenjinhua/ziliao/JavaLearned/doc/41 JDBC/3 连接数据库工具类 Properties.png)



### 2.1.1 如何读取 properties 文件?

由于配置文件，最好只读1次，多次读会影响效率，所以用静态代码块保证只读取1次；

**由于注册驱动对象只需要1个，所以在 static 代码块里创建驱动对象 driver（只初始化1次）；**

读取后的参数，为了能够给其他方法使用，需要把参数存入到变量；

使用 java.util.Properties 类,它本质上是 map,它专门用来读取 properties 文件。

```java
@Test
public void test2(){
    Properties p = new Properties();
    try {
        // 使用 classLoader 类加载器从 classes 目录下读取 db.properties 文件.
        p.load(TestDay01.class.getClassLoader().getResourceAsStream("db.properties"));
        String driver = p.getProperty("driver");
        System.out.println(driver);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

### 2.1.2 工具类完整的实现代码

```java
package day01.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by jinhua.chen on 2018/3/17.
 */
public class DBTool {
    private static  String driver;
    private static  String url;
    private static  String user;
    private static  String pwd;

    static {
        Properties p = new Properties();
        try {
            // 加载配置文件
            p.load(DBTool.class.getClassLoader().getResourceAsStream("db.properties"));
            // 读取连接参数
            driver = p.getProperty("driver");
            url = p.getProperty("url");
            user = p.getProperty("user");
            pwd = p.getProperty("password");
            // 注册驱动
            Class.forName(driver);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("加载 db.properties 文件失败",e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("找不到这个驱动",e);
        }
    }

    public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(url,user,pwd);
    }

    public static void close(Connection conn){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭连接失败",e);
            }
        }
    }
}
```

现在写测试方法测试一下上面的用 DBTool 工具类建立连接：

```java
@Test
public void test3(){
    Connection conn = null;
    try {
        conn = DBTool.getConnection();
        System.out.println(conn);
    } catch (SQLException e) {
        e.printStackTrace();
    }finally {
        DBTool.close(conn);
    }
}
```



## 2.3 连接池（DataSource 数据源）

### 2.3.1 不用连接池会有什么问题

直接使用 DriverManager ：

- 创建连接时，每次调用它都会创建新连接对象，这些对象没有复用；
- 没有管理连接上限，当并发数大时会导致数据库崩溃。

### 2.3.2 连接池的作用

解决上述问题：

- 复用连接，提高效率
- 它能管理连接上限，避免数据库崩溃

### 2.3.3 有哪些连接池

- DBCP
- C3P0

### 2.3.4 连接池的工作原理

- 当创建连接池对象后，它会自动创建一批（数量可配）连接放于池内，
- 当有用户调用它时，它会给用户一个连接，并将连接标记为占用，
- 当用户使用完连接后，将连接归还给连接池，连接池会将连接池标记为空闲；
- 若连接池发现连接快不够用时，它会再创建一批（数量可配）连接，
- 若占用的连接达到数据库上限（数量可配），连接池会让新用户等待；
- 在连接需求高峰期过后，连接池会关闭一批连接（数量可配）

### 2.3.5 如何使用连接池

- sun规定了连接池的接口：DataSource；
- DBCP 实现了连接池的接口，实现类为 BasicDataSource；

使用连接池，我们需要重新实现 DBUtil，即用连接池创建连接的工具类，如下图

![ 连接池创建连接的工具类实](/Users/chenjinhua/ziliao/JavaLearned/doc/41 JDBC/4 连接池创建连接的工具类实现.png)

### 2.3.6 连接池创建连接的工具类

需要引入 jar 包: commons-jdbc

```java
package day01.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by jinhua.chen on 2018/3/18.
 */
public class DBUtil {
    private static String driver;
    private static String url;
    private static String user;
    private static String pwd;
    private static int initSize;
    private static int maxSize;
    private static BasicDataSource bds = null;

    static {
        Properties p = new Properties();
        try {
            p.load(DBUtil.class.getClassLoader().getResourceAsStream("db.properties"));
            driver = p.getProperty("driver");
            url = p.getProperty("url");
            user = p.getProperty("user");
            pwd = p.getProperty("pwd");
            initSize = Integer.parseInt(p.getProperty("initsize"));
            maxSize = Integer.parseInt(p.getProperty("maxsize"));

            bds = new BasicDataSource();
            bds.setDriverClassName(driver);
            bds.setUrl(url);
            bds.setUsername(user);
            bds.setPassword(pwd);
            bds.setInitialSize(initSize);
            bds.setMaxTotal(maxSize);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("加载 db.properties 文件失败",e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return  bds.getConnection();
    }

    /*
    归还连接
    由连接池创建的连接,连接的 close 方法被连接池重写了,变成了归还连接的逻辑,即连接池会将连接的状态设置为空闲,并清除连接包含的任何数据.
     */
    public static void close(Connection conn){
        if (conn !=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("归还连接失败!",e);
            }
        }
    }
}
```

### 2.3.7 测试方法

利用 junit，写测试方法，如下：

注意：sql 语句不写死,根据用户传入的参数动态拼。

```java
    /*
    测试 DBUtil 类的方法
     */
    @Test
    public void test1(){
        // 假设浏览器传入的查询条件是
        int empno = 1815;
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            Statement smt = conn.createStatement();

            // sql 语句不写死,根据用户传入的参数动态拼
            String sql = "SELECT empno,ename FROM EMP WHERE empno > " + empno ;
            ResultSet rs = smt.executeQuery(sql);
            while (rs.next()){
                System.out.println(rs.getInt("empno") + rs.getString("ename"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("连接池创建连接失败",e);
        }finally {
            DBUtil.close(conn);
        }
    }
```

## 2.4 创建Statement、PreparedStatement 语句对象

### 2.4.1 执行DML、DQL 语句

1、执行 DML 语句

```java
conn = DBTool.getConnection();
// 创建语句对象 Statement
Statement stat = conn.createStatement();
// SQL, Sql 语句结尾不要分号
String sql  = "insert into emp values(seq_emp.nextval,'唐僧','经理',0)";
// 执行 SQL
int rows = stat.executeUpdate(sql);
// 返回增加/修改/删除的行数
System.out.println(rows);
```

2、执行 DML 语句

```java
Connection conn = DBTool.getConnection();
Statement stm = conn.createStatement();
// 伪sql
String sql = "SELECT deptname FROM dept WHERE deptno = 1 ORDER BY empno";
ResultSet rs = stm.executeQuery(sql);
```

###  2.4.2 Statement 和 PreparedStatement

1、两者的联系（背）

- 都是用来执行 sql的；
- PreparedStatement 接口继承 Statement 接口

2、两者的区别（背）

- Statement 适合执行静态（无条件） sql；
- PreparedStatement 适合执行动态（有条件）sql。

3、 Statement 工作原理（理解）

![ Statement 不适合执行有条件 sq](/5 Statement 不适合执行有条件 sql.png)



4、PreparedStatement 工作原理（理解）

![ PrepareStatement 执行有条件的 sql](/6 PrepareStatement 执行有条件的 sql .png)



### 2.4.3 PS对象执行 DML、DQL 语句

1、PS对象执行 DML 语句

```java
/*
使用 PS 执行 DML 语句
 */
@Test
public void test2(){
    // 假设页面传入的参数.
    int empno = 2;
    String name = "张三";
    Connection conn = null;
    try {
        conn = DBUtil.getConnection();
        // 创建 ps 对象,并让它立刻发送 sql.
        String sql = "UPDATE emp SET ename=? WHERE empno=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        // 给?赋值.
        ps.setString(1,name);
        ps.setInt(2,empno);
        // 向数据库发送参数,并执行 sql.
        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }finally {
        DBUtil.close(conn);
    }
}
```

2、 PS对象执行 DQL 语句

```java
/*
使用 PS 执行 DQL 语句.
 */
@Test
public void test3(){
    // 假设页面传入查询条件是:
    double salary = 6000.0;
    Connection conn = null;
    try {
        conn = DBUtil.getConnection();
        // 工资大于6000的员工
        String sql = "SELECT empno,ename FROM emp WHERE salary>?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDouble(1,salary);
        ResultSet rs= ps.executeQuery();
        while (rs.next()){
            System.out.println(rs.getInt("empno") + ", " + rs.getString("ename"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }finally {
        DBUtil.close(conn);
    }
}
```

### 2.4.4 PS 防注入攻击

![ PreparedStatement 防注](/Users/chenjinhua/ziliao/JavaLearned/doc/41 JDBC/7 PreparedStatement 防注入.png)

验证 PS 是否能防注入的代码：

```java
/*
验证 PS 是否能防注入
 */
@Test
public void test4(){
    // 假设页面传入的登录条件是:
    String username = "张三峰";
    String pwd = "a' or 'b'='b";
    Connection conn = null;

    try {
        conn = DBUtil.getConnection();
        String sql = "SELECT userid FROM user WHERE username=? AND pwd=?";
        PreparedStatement ps= conn.prepareStatement(sql);
        ps.setString(1,username);
        ps.setString(2,pwd);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            System.out.println("登录成功");
        }else {
            System.out.println(" 登录失败");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }finally {
        DBUtil.close(conn);
    }
}
```

# 三、结果集

## 3.1 处理结果集

```java
// 返回的 ResultSet 是结果集
// 封装了查询到的结果(多行多列)
// 该对象采用迭代器模式设计而来.
ResultSet rs = stm.executeQuery(sql);
// 每次 next() 就可以获取下一行数据
while (rs.next()){
    // 获取该行每一列的值
    System.out.println(rs.getInt("empno") + "," + rs.getString("ename"));
}
```

## 3.2 关于结果集 ResultSet 的指针

![ ResultSet 结果](/Users/chenjinhua/ziliao/JavaLearned/doc/41 JDBC/8 ResultSet 结果集.png)

## 3.3 元数据 ResultSetMetaData

Meta：根本、本质；

MetaData：数据的根本，即数据的概述信息；

ResultSetMetaData：对结果集的概述信息。

```java
/*
测试 ResultSetMetaData 类
 */
@Test
public void test5(){
    Connection conn = null;
    try {
        conn = DBUtil.getConnection();
        String sql = "SELECT ename FROM emp";
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        ResultSetMetaData md =rs.getMetaData();
        // 输出结果集有几列
        System.out.println(md.getColumnCount());
        // 输出结果集第2列字段的类型
        System.out.println(md.getColumnTypeName(2));

    } catch (SQLException e) {
        e.printStackTrace();
    }finally {
        DBUtil.close(conn);
    }
}
```

# 四、 关闭连接

# 五、实战 —— 转账

>同一个银行间的转账功能，默认成功登录网银，转账步骤：
>
>1、查询付款账号，验证卡内余额是否够；
>
>2、查询收款账号，验证账号是否正确；
>
>3、转账，变更付款方余额；
>
>4、变更收款方余额。



```java
package day02;

import day01.util.DBUtil;
import org.junit.Test;

import java.sql.*;

/**
 * Created by jinhua.chen on 2018/3/18.
 */
public class TestDay02 {
    String payCardId = "carId";
    String receiveCardId = "ReceiveCardId";
    String pwd = "cardPwd";
    double payCardBalance = 0.0;
    double receiveCardBalance = 0.0;
    double transMoney = 5000;

    /*
    同一个银行间的转账功能，默认成功登录网银，转账步骤：
    1、查询付款账号，验证卡内余额是否够；
    2、查询收款账号，验证账号是否正确；
    3、转账，变更付款方余额；
    4、变更收款方余额。
     */
    @Test
    public void test6(){
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            if (checkPayCard(conn)){
                if (checkReceiveCard(conn)){
                    transfer(conn);
                }else {
                    System.out.println("转账卡号有误,转账失败");
                }
            }else {
                System.out.println("卡余额不足,转账失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn);
        }
    }

    private void transfer(Connection conn) {
        String sql1 = "UPDATE userBalance set balance=? WHERE cardId=?";
        String sql2 = "UPDATE userBalance set balance=? WHERE cardId=?";

        try {
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setString(1,String.valueOf(payCardBalance - transMoney));
            ps1.setString(2,payCardId);
            ps1.execute();
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setString(1,String.valueOf(receiveCardBalance + transMoney));
            ps2.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkReceiveCard(Connection conn) {
        String sql = "SELECT cardId,balance FROM userBalance WHERE cardId=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,receiveCardId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                receiveCardBalance = rs.getDouble("balance");
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkPayCard(Connection conn){
        try {
            String sql = "SELECT balance FROM userBalance WHERE cardId=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,payCardId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                payCardBalance = rs.getDouble("balance");
                if (payCardBalance > transMoney){
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
```

# 六、附录

## 6.1 引入 junit

​	直接在测试方法上加标签 @Test，然后点击错误提示，引入 junit 包就可以了。