import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jinhua.chen on 2018/4/23.
 */
@WebServlet(name = "LoginServlet",urlPatterns = "/ls")
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String user = req.getParameter("user");

        // cookie 只能存1条数据,并且只能存字符串.
        Cookie c1 = new Cookie("user",user);
        // 将 cookie 绑定到 resp 上,当服务器发送响应时会自动发送 cookie.
        resp.addCookie(c1);
    }
}
