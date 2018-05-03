package dao;

import entity.Admin;

/**
 * Created by jinhua.chen on 2018/5/1.
 */
public interface AdminDao {
    public Admin findByName(String name);
}

