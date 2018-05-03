[TOC]

# 一、中文乱码处理

表单中含有中文参数值，如何读取？

解决方法：在 web.xml 最开始添加配置如下：

```xml
<filter>
  <filter-name>CharacterEncoding</filter-name>
  <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
  <init-param>
    <param-name>encoding</param-name>
    <param-value>UTF-8</param-value>
  </init-param>
  <init-param>
    <param-name>forceEncoding</param-name>
    <param-value>true</param-value>
  </init-param>
</filter>
<filter-mapping>
  <filter-name>CharacterEncoding</filter-name>
  <url-pattern>/*</url-pattern>
</filter-mapping>
```

 	这是 springmvc 提供的过滤器（CharacterEncodingFilter），只需要配置该过滤器即可。注意满足两个条件：1、表单的提交方式必须是 post；2、这里设置的编码与浏览器（jsp文件里设置的）一致。



# 二、拦截器

## 2.1 什么是拦截器

​	DispatcherServlet收到请求后，如果有拦截器，会先调用拦截器，然后再调用 Controller。



![ 拦截器&过滤](/Users/chenjinhua/ziliao/JavaLearned/doc/45 Spring/9 拦截器&过滤器.png)



## 2.2 拦截器和过滤器区别

​	过滤器属于 servlet 规范， 而拦截器属于 spring 框架。



## 2.3 如何写一个拦截器

步骤1：写一个类，实现 HandlerInterceptor 接口；

步骤2：在接口方法里，实现拦截处理逻辑；

步骤3：spring.xml 里配置拦截器。



## 2.4 拦截器里的方法

- preHandler 方法

DispatcherServlet 收到请求后，会先调用拦截器的 preHandler 方法，如果该方法的返回值是 true 则继续向后调用；如果返回值是 false，则中断请求。注：DispatcherServlet、拦截器 以及 Controller 三者共享同一个 request 和 response对象。

- postHandler 方法

Controller 的方法已经执行完毕，在 Controller 返回 ModelAndView 给前端控制器之前，执行 postHandler 方法，可以在该方法里修改处理结果（ ModelAndView）。

- afterCompletion 方法

最后执行的方法。Controller 里若有异常可以不处理，向上抛给拦截器处理。



![ 拦截](/Users/chenjinhua/ziliao/JavaLearned/doc/45 Spring/8 拦截器.png)





## 2.4 session 验证的拦截器

1、写 SomeInterceptor 类实现 HandlerInterceptor 接口：

```java
package interceptor;

/*
* 用于 session 验证的拦截器.
* */
public class SomeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
```



2、在 SomeInterceptor 类方法里处理拦截逻辑；

(1) 在 Controller 里将账户信息绑定到 session 里：

```java 
HttpSession session = req.getSession();
session.setAttribute("admin",admin);
```

（2）处理拦截逻辑：

```java
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
}
```



3、在 spring.xml 配置拦截器：

```xml
 <!-- 配置拦截器 -->
    <!--如果有多个拦截器满足拦截要求,则按照拦截器配置的顺序,按照顺序从前往后执行-->
    <mvc:interceptors>
        <mvc:interceptor>
            <!-- 拦截所有的 path 设为 "/**"-->
            <mvc:mapping path="/**"/>
            <mvc:exclude-mapping path="/toLogin.do"></mvc:exclude-mapping>
            <mvc:exclude-mapping path="/login.do"></mvc:exclude-mapping>
            <bean class="interceptor.SomeInterceptor"></bean>
        </mvc:interceptor>
    </mvc:interceptors>
```



# 三、Spring 框架处理异常

​	之前是在 Controller 里处理异常，我们可以将异常抛给 spring 框架，让框架帮我们处理。默认情况下，spring 框架会将异常直接抛给最终用户。

## 3.1 方式一：配置简单异常处理器

步骤1：在 spring.xml 配置文件里，配置简单异常处理器；

步骤2：添加相应的异常处理页面。

注意：简单异常处理器，不能对异常做复杂的处理。

![0 spring框架异常处理](/Users/chenjinhua/ziliao/JavaLearned/doc/45 Spring/10 spring框架异常处理1.png)



## 3.2 方式二：@ExceptionHandler

步骤1：在处理器中添加异常处理方法，该方法前面需要加 @ExceptionHandler 注解；

步骤2：在异常处理方法里，依据不同的异常做不同的处理。



使用 @ExceptionHandler 处理登录模块中产生的异常：

```java
/*
*  这是一个异常处理方法.
*  e 是处理器抛出的异常.
* */
@ExceptionHandler
public String handlerEx(Exception e, HttpServletRequest req){
    // 应用异常
    if (e instanceof ApplicationException){
        // 向页面传值
        req.setAttribute("login_failed",e.getMessage()); // getMessage()获取抛出的异常内容.
        return "login";
    }else {
        // 系统异常
        return "error";
    }
}
```



# 四、扩展——验证码

​	在 springmvc 框架中使用：

​    **Controller 方法的返回值既不是 ModelAndView 也不是 String，因为如果是上面二者，前端控制器默认会转发，而我们需要checkCode 方法返回一张图片，所以该方法的返回值只要不是上面二者就可以了， 这时前端控制器就不会转发，由开发者控制。这里返回值类型可以设为 void。**



1、引入验证码工具类：

2、jsp 页面设置图片的 src 属性值为："checkCode.do"；

- **图片资源是需要单独向服务器发请求获取的**

3、Controller 里添加方法 checkCode()；

​	**方法的返回值既不是 ModelAndView 也不是 String，因为如果是上面二者，前端控制器默认会转发，而我们需要checkCode 方法返回一张图片，所以该方法的返回值只要不是上面二者就可以了， 这时前端控制器就不会转发，由开发者控制。这里返回值类型可以设为 void。**

```java
@RequestMapping("/checkCode.do")
public void checkCode(HttpServletResponse resp){
    OutputStream os = null;

    // 通过工具类获得验证码图片
    BufferedImage image = ImageUtil.getBuffImg();
    try {
        // 将图片发送给浏览器
        os = resp.getOutputStream();
        ImageUtil.write(os);

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
```

4、修改拦截器配置

```xml
<mvc:exclude-mapping path="/checkCode.do"></mvc:exclude-mapping>
```