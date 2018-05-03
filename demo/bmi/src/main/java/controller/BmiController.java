package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jinhua.chen on 2018/4/30.
 */
@Controller
public class BmiController {

    @RequestMapping("toBmi.do")
    public String toBmi(){
        return "bmi";
    }

    @RequestMapping("bmi.do")
    public String bmi(BMI bmi, ModelMap mm){

        // 获取请求参数
        double weight = bmi.getWeight();
        double height = bmi.getHeight();

        // 处理业务
        double result = Math.round(weight / height / height);
        System.out.println(result);

        // 页面传值
        mm.addAttribute("result",result);

        return "result";
    }
}
