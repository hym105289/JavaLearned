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
