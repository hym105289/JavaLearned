package web;

import dao.AdminDao;
import dao.CostDao;
import entity.Admin;
import entity.Cost;
import util.ValidateCodeUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by jinhua.chen on 2018/4/22.
 */
@WebServlet(name = "MainServlet", urlPatterns = "*.do")
public class MainServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/findCost.do".equals(path)){
            findCost(req,resp);
        }else if ("/toAddCost.do".equals(path)){
            toAddCost(req,resp);
        }else if ("/addCost.do".equals(path)){
            addCost(req,resp);
        }else if ("/toUpdateCost.do".equals(path)){
            toUpdateCost(req,resp);
        }else if ("/updateCost.do".equals(path)){
            updateCost(req,resp);
        }else if ("/login.do".equals(path)){
            login(req,resp);
        }else if ("/index.do".equals(path)){
            index(req,resp);
        }else if ("/toLogin.do".equals(path)){
            toLogin(req,resp);
        }else if ("/createCodeImg.do".equals(path)){
            createCodeImg(req,resp);
        }else {
            throw new RuntimeException("该页面不存在");
        }
    }

    /*
    *  生成验证码以及图片
    * */
    protected void createCodeImg(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ValidateCodeUtil vCode = new ValidateCodeUtil();
        // 生成验证码, 并存入 session.
        String code = vCode.getCode();
        HttpSession session = req.getSession();
        session.setAttribute("validateCode",code);

        /*
        * 生成图片, 将图片发送给浏览器.
        * */
        // 输出文件的类型可以在 tomcat 包里的 web.xml 里查找.
        resp.setContentType("image/png");
        // 将内容输出到浏览器需要一个流,之前输出的网页文本是字节流,现在图片需要字节流.
        OutputStream os = resp.getOutputStream();
        vCode.write(os);
        os.close();
    }

    protected void toLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req,resp);
    }
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdminDao dao = new AdminDao();
        req.setCharacterEncoding("utf-8");
        String loginName = req.getParameter("loginName");
        String loginPasswd = req.getParameter("loginPasswd");
        Admin a = dao.findByLoginName(loginName);

        /*
        * 先校验验证码,因为如果验证码不对,就不需要读写数据库校验密码了.
        * */
        HttpSession session = req.getSession();
        String code = (String) session.getAttribute("validateCode");
        String pageCode = req.getParameter("pageCode");
        if (! code.equalsIgnoreCase(pageCode)) {
            String codeError = "验证码错误,请重试";
            req.setAttribute("codeError",codeError);
            req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req,resp);
            return;
        }

        if (a.getLoginPasswd().equals(loginPasswd)){
            Cookie c = new Cookie("user",loginName);
            resp.addCookie(c);
            resp.sendRedirect("index.do");
        }else {
            String error = "用户名或密码错误，请重试";
            req.setAttribute("msg",error);
            req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req,resp);
        }
    }
    protected void index(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/main/index.jsp").forward(req,resp);
    }

    protected void findCost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CostDao dao = new CostDao();
        List<Cost> list = dao.findAll();
        req.setAttribute("costList",list);
        req.getRequestDispatcher("/WEB-INF/cost/find_cost.jsp").forward(req,resp);
    }

    protected void toAddCost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/cost/add_cost.jsp").forward(req,resp);

    }
    protected void addCost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收传入的参数
        req.setCharacterEncoding("utf-8");
        String name = req.getParameter("name");
        String costType = req.getParameter("costType");
        String baseDuration = req.getParameter("baseDuration");
        String baseCost = req.getParameter("baseCost");
        String unitCost = req.getParameter("unitCost");
        String descr = req.getParameter("descr");

        Cost c = new Cost();
        c.setCostId(20);
        c.setName(name);
        if (baseDuration != null){
            c.setBaseDuration(Double.parseDouble(baseDuration));
        }
        c.setBaseCost(Double.parseDouble(baseCost));
        c.setUnitCost(Double.parseDouble(unitCost));
        c.setDescr(descr);
        c.setCostType(costType);

        CostDao dao = new CostDao();
        dao.save(c);

        resp.sendRedirect("findCost.do");

    }

    protected void toUpdateCost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CostDao dao = new CostDao();
        Cost c = dao.findById(Integer.parseInt(req.getParameter("id")));
        req.setAttribute("cost",c);
        req.getRequestDispatcher("/WEB-INF/cost/update_cost.jsp").forward(req,resp);
    }
    protected void updateCost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String costId = req.getParameter("costId");
        String name = req.getParameter("name");
        String baseDuration = req.getParameter("baseDuration");
        String baseCost = req.getParameter("baseCost");
        String unitCost = req.getParameter("unitCost");
        String costType = req.getParameter("costType");
        String descr = req.getParameter("descr");

        Cost c = new Cost();
//        c.setCostId(Integer.valueOf(costId));
        c.setCostId(1);
        c.setName(name);
        c.setBaseDuration(Double.valueOf(baseDuration));
        c.setBaseCost(Double.valueOf(baseCost));
        c.setUnitCost(Double.parseDouble(unitCost));
        c.setCostType(costType);
        c.setDescr(descr);

        CostDao dao = new CostDao();
        dao.update(c);
        resp.sendRedirect("findCost.do");
    }
}
