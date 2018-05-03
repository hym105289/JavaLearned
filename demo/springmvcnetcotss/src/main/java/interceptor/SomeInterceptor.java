package interceptor;

import entity.Admin;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by jinhua.chen on 2018/5/1.
 */

/*
* 用于 session 验证的拦截器.
* */
public class SomeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        Object obj = session.getAttribute("admin");
        if (obj == null){
            // session 里没有账号密码,说明未登录.需要转发到登录页,并且不处理后续的请求.
            response.sendRedirect("toLogin.do");
            return false;
        }
        // 说明已经登录,继续执行.
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
