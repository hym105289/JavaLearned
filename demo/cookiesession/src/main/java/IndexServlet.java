import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by jinhua.chen on 2018/4/23.
 */
@WebServlet(name = "IndexServlet", urlPatterns = "/is")
public class IndexServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookie[] = req.getCookies();

        if (cookie != null){
            resp.setContentType("text/html;charset=utf-8");
            PrintWriter w = resp.getWriter();
            for (Cookie c : cookie){
                w.println((c.getName() + " : " + c.getValue()));
            }
            w.close();
        }
    }
}
