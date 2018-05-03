package service;

import entity.Admin;

/**
 * Created by jinhua.chen on 2018/5/1.
 */
public interface LoginService {
    public Admin checkLogin(String name, String pwd);
}
