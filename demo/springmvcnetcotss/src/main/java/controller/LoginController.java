package controller;

import entity.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import service.ApplicationException;
import service.LoginService;
import util.ImageUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by jinhua.chen on 2018/5/1.
 */

@Controller
public class LoginController {

    @Resource(name = "loginService")
    private LoginService loginService;

    @Resource(name = "imageUtil")
    private ImageUtil imageUtil;

    @RequestMapping("/checkCode.do")
    public void checkCode(HttpServletResponse resp){
        OutputStream os = null;

        // 通过工具类获得验证码图片
        resp.setContentType("image/png");
        BufferedImage image = imageUtil.getBuffImg();

        try {
            // 将图片发送给浏览器
            os = resp.getOutputStream();
            imageUtil.write(os);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /*
    *  这是一个异常处理方法.
    *  e 是处理器抛出的异常.
    * */
    @ExceptionHandler
    public String handlerEx(Exception e, HttpServletRequest req){
        if (e instanceof ApplicationException){
            // 向页面传值
            req.setAttribute("login_failed",e.getMessage()); // getMessage()获取抛出的异常内容.
            return "login";
        }else {
            return "error";
        }
    }

    @RequestMapping("/toLogin.do")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login.do")
    public String login(HttpServletRequest req){

        // 获取请求参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");

            Admin admin = loginService.checkLogin(username,password);
            HttpSession session = req.getSession();
            session.setAttribute("admin",admin);
            return "redirect:toIndex.do";
    }



    @RequestMapping("/toIndex.do")
    public String toIndex(){
        return "index";
    }
}
