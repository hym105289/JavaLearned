package service;

import dao.AdminDao;
import entity.Admin;
import service.ApplicationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by jinhua.chen on 2018/5/1.
 */

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Resource(name = "adminDao")
    private AdminDao adminDao;


    @Override
    public Admin checkLogin(String name, String pwd) {
        Admin admin = adminDao.findByName(name);

        if (admin == null){
            // 账号不存在,抛一个应用异常(自定义异常).应用异常指的是因为用户不正确的操作而引起的异常(如账号\密码错误),需要上层明确提示用户正确操作.
            throw new ApplicationException("账号不存在");
        }
        if (! admin.getLoginPasswd().equals(pwd)){
            throw new ApplicationException("密码错误");
        }
        // 否则登录成功
        return admin;
    }
}
