package dao;

import entity.Cost;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinhua.chen on 2018/4/20.
 */
public class CostDao {
    public List<Cost> findAll(){
        List<Cost> costList = new ArrayList<Cost>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * FROM cost";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()){
                Cost cost = new Cost();
                cost.setCostId(rs.getInt("cost_id"));
                cost.setName(rs.getString("name"));
                cost.setBaseDuration(rs.getDouble("base_duration"));
                cost.setBaseCost(rs.getDouble("base_cost"));
                cost.setUnitCost(rs.getDouble("unit_cost"));
                cost.setStatus(rs.getString("status"));
                cost.setDescr(rs.getString("descr"));
                cost.setCostType(rs.getString("cost_type"));
                costList.add(cost);
            }
            return costList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询资费失败!",e);
        }finally {
            DBUtil.close(conn);
        }
    }

    public void save(Cost c){
            Connection conn = null;
            try {
                conn = DBUtil.getConnection();
                String sqlSave = "insert into cost values(?,?,?,?,?,1,?,?)";
                PreparedStatement ps = conn.prepareStatement(sqlSave);

                // setInt\setDouble 中不允许传入 null,但实际业务中需要此字段为 null,数据库也支持为 null,此时可以用setObject 方法.
                ps.setInt(1,c.getCostId());
                ps.setString(2,c.getName());
                ps.setObject(3,c.getBaseDuration());
                ps.setDouble(4,c.getBaseCost());
                ps.setObject(5,c.getUnitCost());
                ps.setString(6,c.getDescr());
                ps.setString(7,c.getCostType());
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("数据库连接失败",e);
            }finally {
                DBUtil.close(conn);
            }
    }

    public Cost findById(int id){
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            Cost c = new Cost();
            String sqlFind = "select * from cost where cost_id=?";
            PreparedStatement ps = conn.prepareStatement(sqlFind);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                c.setCostId(id);
                c.setName(rs.getString("name"));
                c.setBaseDuration(rs.getDouble("base_duration"));
                c.setBaseCost(rs.getDouble("base_cost"));
                c.setUnitCost(rs.getDouble("unit_cost"));
                c.setStatus(rs.getString("status"));
                c.setDescr(rs.getString("descr"));
                c.setCostType(rs.getString("cost_type"));
                return c;
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库连接失败",e);
        }
    }

    public Cost update(Cost c){
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sqlUpdate = "update cost SET name=?, base_duration=?, base_cost=?, unit_cost=?, status=1, descr=?, cost_type =? where cost_id =?";
            PreparedStatement ps = conn.prepareStatement(sqlUpdate);
            ps.setString(1,c.getName());
            ps.setDouble(2,c.getBaseDuration());
            ps.setDouble(3,c.getBaseCost());
            ps.setDouble(4,c.getUnitCost());
            ps.setString(5,c.getDescr());
            ps.setString(6,c.getCostType());
            ps.setInt(7,c.getCostId());
            int flag = ps.executeUpdate();
            return (flag>0)? c:null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库连接失败",e);
        }finally {
            DBUtil.close(conn);
        }
    }

//    public static void main(String[] args) {
//        CostDao costDao = new CostDao();
//        Cost c = new Cost();
//        c.setCostId(8);
//        c.setName("test");
//        c.setBaseDuration(12.2);
//        c.setBaseCost(12.2);
//        c.setUnitCost(12.2);
//        c.setCostType("1");
//        c.setDescr("test");
//
//        costDao.update(c);
//        System.out.println(c.getCostId() + "," + c.getName());
//    }
}
