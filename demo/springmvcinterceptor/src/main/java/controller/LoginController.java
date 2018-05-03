package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jinhua.chen on 2018/5/1.
 */
@Controller
public class LoginController {

    @RequestMapping("/toLogin.do")
    public String toLogin(){
        return "login";
    }
}
