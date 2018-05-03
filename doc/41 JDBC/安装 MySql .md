[TOC]



# 一、下载安装MySql

​	https://www.cnblogs.com/chenmo-xpw/p/6102933.html

​	http://bbs.bestsdk.com/detail/762.html

​	临时密码：root@localhost: k(id)T!Yj2S

卸载 MySql：https://www.cnblogs.com/TsengYuen/archive/2011/12/06/2278574.html

## 二、Sequal pro 客户端连接数据库服务端

用户名：root

密码：空（不输入）。

如果电脑重启后发现客户端连接不上服务端，则做以下操作：（以非授权模式启动, 这样可以不输入密码）：

　　①、先把MySQL服务关了

　　②、在终端输入

```
sudo mysqld_safe --skip-grant-tables
```

然后客户端就可以连接服务端了。

# 三、Java 连接 MySql

## 2.1 引入 jar

 1、MySql驱动：mysql-connector-java-5.1.27.jar

```xml
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>6.0.6</version>
</dependency>
```

2、dbcp 驱动

```xml
<!-- https://mvnrepository.com/artifact/org.apache.commons/commons-dbcp2 -->
<dependency>
  <groupId>org.apache.commons</groupId>
  <artifactId>commons-dbcp2</artifactId>
  <version>2.1.1</version>
</dependency>
```

## 2.2 DBUtil 类

```java
package util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by jinhua.chen on 2018/4/20.
 */
public class DBUtil {
    public static String driver;
    public static String url;
    public static String username;
    public static String pwd;
    public static BasicDataSource bds =null;
    public static String initSize;
    public static String maxSize;

    static {
        Properties p = new Properties();
        try {
            p.load(DBUtil.class.getClassLoader().getResourceAsStream("db.properties"));
            driver = p.getProperty("driver");
            url = p.getProperty("url");
            username = p.getProperty("username");
            pwd = p.getProperty("password");
            initSize = p.getProperty("initSize");
            maxSize = p.getProperty("maxSize");

            bds = new BasicDataSource();
            // 使用该参数注册驱动
            bds.setDriverClassName(driver);
            // 使用下面3个参数创建连接
            bds.setUrl(url);
            bds.setUsername(username);
            bds.setPassword(pwd);
            // 使用其他参数管理连接
            bds.setInitialSize(Integer.parseInt(initSize));
            bds.setMaxTotal(Integer.parseInt(maxSize));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return bds.getConnection();
    }

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

    public static void main(String[] args) throws SQLException {
        Connection conn = DBUtil.getConnection();
        System.out.println("conn:" + conn);
        DBUtil.close(conn);
    }
}
```

