package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinhua.chen on 2018/4/30.
 */
@Controller
public class HelloController {


    @RequestMapping("/hello.do")
    public String hello(){
         return "hello";
    }

    @RequestMapping("/toLogin.do")
    public String toLogin(){
        return "login";
    }


    @RequestMapping("/login.do")
    public String login(HttpServletRequest req){
        String name = req.getParameter("username");
        String password = req.getParameter("pwd");
        System.out.println("username:" + name + ", password: " + password);
        return "index";
    }

    @RequestMapping("/login2.do")
    public String login2(String username,String pwd){
        System.out.println("username:" + username + ", password: " + pwd);
        return "index";
    }

    @RequestMapping("/login3.do")
    public String login3(AdminParam admin){
        System.out.println("username:" + admin.getUsername() + ", password: " + admin.getPwd());
        return "index";
    }

    @RequestMapping("/login4.do")
    public String login4(AdminParam admin,HttpServletRequest req){
        String name = admin.getUsername();
        req.setAttribute("name",name);
        return "index";
    }

    @RequestMapping("/login5.do")
    public ModelAndView login5(AdminParam admin){
        String name = admin.getUsername();
        Map<String,Object> map = new HashMap<String, Object>();
        // 相当于执行了 req.setAttribute()
        map.put("name",name);
        ModelAndView mav = new ModelAndView("index",map);
        return mav;
    }


    @RequestMapping("/login6.do")
    public String login6(AdminParam admin, ModelMap mm){
        String name = admin.getUsername();
        // 相当于执行了 req.setAttribute()
        mm.addAttribute("name",name);
        return "index";
    }

    @RequestMapping("/login7.do")
    public String login7(AdminParam admin,HttpSession session){
        String name = admin.getUsername();
        session.setAttribute("name",name);
        return "index";
    }

    @RequestMapping("/login8.do")
    public String login8(){
        return "redirect:toIndex.do";
    }
    @RequestMapping("/toIndex.do")
    public String toIndex(){
        return "index";
    }


    @RequestMapping("/login9.do")
    public ModelAndView login9(){

        RedirectView rv = new RedirectView("toIndex.do");
        ModelAndView mav = new ModelAndView(rv);
        return mav;
    }


}
