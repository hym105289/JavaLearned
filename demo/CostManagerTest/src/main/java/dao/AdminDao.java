package dao;

import entity.Admin;
import util.DBUtil;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jinhua.chen on 2018/4/23.
 */
public class AdminDao implements Serializable{
    public Admin findByLoginName(String loginName){
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sqlFind = "select * from account where login_name = ?";
            PreparedStatement ps = conn.prepareStatement(sqlFind);
            ps.setString(1,loginName);
            ResultSet rs = ps.executeQuery();
            Admin admin = new Admin();
            if (rs.next()){
                admin.setId(rs.getInt("account_id"));
                admin.setRecommenderId(rs.getInt("recommender_id"));
                admin.setLoginName(rs.getString("login_name"));
                admin.setLoginPasswd(rs.getString("login_passwd"));
                admin.setStatus(rs.getString("status"));
                admin.setPauseDate(rs.getTimestamp("pause_date"));
                admin.setCloseDate(rs.getTimestamp("close_date"));
                admin.setRealName(rs.getString("real_name"));
                return admin;
            }else {return null;}
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库连接失败",e);
        }
    }

//    public static void main(String[] args) {
//        AdminDao dao = new AdminDao();
//        Admin a = dao.findByLoginName("taiji001");
//        System.out.println(a.getId() + ", " + a.getLoginPasswd());
//    }
}
