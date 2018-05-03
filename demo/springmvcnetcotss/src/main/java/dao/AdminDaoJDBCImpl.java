package dao;

import entity.Admin;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jinhua.chen on 2018/5/1.
 */

@Repository("adminDao")
public class AdminDaoJDBCImpl implements AdminDao {

    @Resource(name = "ds")
    private DataSource ds;

    @Override
    public Admin findByName(String name) {
        Connection conn = null;
        Admin admin = null;
        try {
            conn = ds.getConnection();
            String sql = "select * from account where login_name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                admin = new Admin();
                admin.setId(rs.getInt("account_id"));

                admin.setLoginName(rs.getString("login_name"));
                admin.setLoginPasswd(rs.getString("login_passwd"));
                admin.setStatus(rs.getString("status"));
                admin.setRealName(rs.getString("real_name"));
            }
            return admin;
        } catch (SQLException e) {
            // 1. 记录日志,保留现场
            e.printStackTrace();
            // 2. 系统异常,下层要抛出日志,让上层处理
            throw new RuntimeException(e);
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
