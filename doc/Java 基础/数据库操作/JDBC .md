[TOC]

​	JDBC （Java Database Connective），Java 数据库连接技术，是由 Java 提供的一组与平台无关的数据库的操作标准（是一组接口的组成）。

​	常见的数据库操作形式： JDBC 网络连接，使用专门的数据库的网络连接指令进行指定主机的数据库操作，此种方式使用最多。

​	在国内使用最多的几种数据库：Oracle、MySQL、MongoDB。

#一、连接 Oracle 数据库

1、使用 JDBC 技术连接 Oracle 数据库；

2、观察 JDBC 中常用类与接口的使用结构。

​	Java 中所有数据库操作的类和接口都保存在了 java.sql 包里面，在这个包里面：

- 一个类： DriverManager 类；
- 四个接口：Connection、Statement、ResultSet、PreparedStatement。

## 1.1 JDBC 连接数据库

​	所有的 JDBC 连接数据库的操作流程都是固定的，按照如下的几步完成：

- 加载数据库的驱动程序（向容器加载）；
- 连接数据库（通过 DriverManager类，COnnection表示连接）
- 数据库的CRUD（Statement、PrepareStatement、ResultSet）
- 关闭连接

### 1.1 加载驱动程序

​	所有的 JDBC 都是由各个不同的数据库生产商提供的数据库驱动程序，这些驱动程序都是以 *.jar 文件给出来的。*

*驱动程序：../../../*x.jar。

Oracle 驱动程序类：oracle.jdbc.driver.oracleDriver。

加载类使用：Class.forName(String 驱动程序类名)。

```java
public class TestDemo {
    public static final String DBDRIVER = "oracle.jdbc.driver.oracleDriver";
    public static void main(String args[]) throws ClassNotFoundException {
      // 第一步：加载数据库驱动程序，此时不需要实例化，因为会由容器负责管理；
        Class.forName(DBDRIVER);
    }
}
```

## 1.2 连接数据库

​	要想连接数据库需要提供如下几个信息：

- 数据库的连接地址

jdbc:oracle:连接方式:@主机名称:端口名称:数据库的SID，

如连接本机的mldn 数据库为 jdbc:oracle:thin:@localhost:1521:mldn。

- 数据库的用户名：scott


- 数据库的密码：tiger。

要连接数据库必须依靠 DriverManager 类完成，此类定义有如下的方法：

- 连接数据库： public static Connection getConnection（String url, String user, String password）throws SQLException

在 JDBC 每一个数据库连接都要求使用一个 Connection 对象进行封装，所以只要有一个 Connection 对象就表示要连接一次数据库。

## 1.4 关闭数据库

Connection 接口提供有 close() 方法： public void close() throws SQLException。

```java
public class TestDemo {
    private static final String DBDRIVER = "oracle.jdbc.driver.oracleDriver";
    private static final String DBURL = "jdbc:oracle:thin:@localhost:1521:mldn";
    private static final String USER = "scott";
    private static final String PASSWORD = "tiger";
    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        // 第一步：加载数据库驱动程序，此时不需要实例化，因为会由容器负责管理；
        Class.forName(DBDRIVER);
        // 第二步: 连接数据库
        Connection conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
        System.out.println(conn);
        // 第四步: 关闭数据库
        conn.close();
    }
}
```

​	此时的程序已经可以连接 Oracle 数据库，可是很多时候会连接不上 Oracle 数据库的。可能出现的问题：

1、监听服务出现错误

- 监听的主机名称不是本机的计算机名称，也不要使用 IP 地址；
- 监听配置文件路径；

监听文件：listener.ora；监听的名称文件：tnsnames。

如果监听有问题，那么将出现以下

2、不能找到指定的 SID

​	数据库的名字就是 SID 的名字，但是很多时候该名称不会自动注册，也就是有数据库名称但没有 SID 名称。

##1.5 结构总结

![WX20171226-202210@2x](/Users/chenjinhua/git/javaLearning/Java 基础/数据库操作/WX20171226-202210@2x.png)

​	JDBC 操作中，在驱动数据库连接对象时，采用的是工厂设计模式，而 DriverManager 就是一个工厂类，客户端调用时会完全隐藏具体的实现子类。

## 1.6 总结

1、以后不管连接何种关系型数据库，都一定通过 DriverManager 进行数据库连接；

2、每一个 Connection 接口对象就表示一个数据库连接，程序的最后必须关闭数据库连接。