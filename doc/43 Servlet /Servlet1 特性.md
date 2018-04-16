[TOC]

# 一、Servlet 生命周期

1.1 Servlet 的生命周期的四个阶段

![0 Tomcat 管理 Servlet 的方](/Users/chenjinhua/ziliao/JavaLearned/doc/43 Servlet /10 Tomcat 管理 Servlet 的方式.png)

- 默认情况下，第1次访问 Servlet 时，tomcat 会实例化它；

- 也可以改为 tomcat 启动时实例化 Servlet；

  ```xml
  在启动服务时第1个创建此 Servlet.

  <load-on-startup>1</load-on-startup>
  ```


- 第1、2、4只执行1次，所以某类型的 Servlet 只实例化1次，是单例。



# 二、 ServletConfig 和 ServletContext

## 2.1 它们的作用

都能够读取 web.xml 中为 Servlet 预置的参数；

## 2.2 它们的区别

- ServletConfig 和 Servlet 是1对1的关系；ServletContext 和 Servlet 是1对多的关系；
- 若数据只给某个 Servlet 使用，则选择 ServletConfig；若数据给很多 Servlet 使用，则选择 ServletContext。

## 2.3 ServletConfig 使用场景

场景：假设要开发一个网页游戏，若超过人数限制则需要排队。

​	开发登录功能 LoginServlet，人数上限应该是一个可配置的参数 maxOnline，该参数只给 LoginServlet 使用，所以使用 ServletConfig。

web.xml 配置

```xml
<servlet>
    <servlet-name>login</servlet-name>
    <servlet-class>day04.LoginServlet</servlet-class>
    <init-param>
        <param-name>maxOnline</param-name>
        <param-value>3000</param-value>
    </init-param>
</servlet>
<servlet-mapping>
    <servlet-name>login</servlet-name>
    <url-pattern>/login</url-pattern>
</servlet-mapping>
```

LoginServlet类

``` java
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
        Tomcat 初始化 LoginServlet 的过程:
        1. LoginServlet ls = new LoginServlet();
        2. ServletConfig cfg = new ServletConfig();
        3. ls.init(cfg);
        4. ls.service();
         */
        ServletConfig cfg = getServletConfig();
        String maxOnline = cfg.getInitParameter("maxOnline");
        System.out.println(maxOnline);
    }
}
```

## 2.4 ServletContext 使用场景

​	Tomcat 启动时会给每个项目创建一个 context 对象，并自动调用此对象加载对应项目的 web.xml 中的参数。

场景：大部分查询都具备分页功能，分页需要1个参数，该参数一般可配置，且被众多查询功能复用，使用 servletContext 读取。

web.xml 配置文件

```xml
<servlet>
    <servlet-name>findDept</servlet-name>
    <servlet-class>day04.FindDeptServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>findDept</servlet-name>
    <url-pattern>/findDept</url-pattern>
</servlet-mapping>
<servlet>
    <servlet-name>findEmp</servlet-name>
    <servlet-class>day04.FindEmpServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>findEmp</servlet-name>
    <url-pattern>/findEmp</url-pattern>
</servlet-mapping>

<context-param>
    <param-name>size</param-name>
    <param-value>20</param-value>
</context-param>
```

FindDeptServlet 类

```java
public class FindDeptServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        String size = ctx.getInitParameter("size");
        System.out.println(size);
    }
}
```

FindEmpServlet 类

``` java
public class FindEmpServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        String size = ctx.getInitParameter("size");
        System.out.println(size);
    }
}
```



## 2.5 ServletContext 存储变量

web.xml 里 InitServlet 的配置

``` xml
<servlet>
    <servlet-name>init</servlet-name>
    <servlet-class>day04.InitServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>
```

InitServlet 类

``` java
public class InitServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext ctx = getServletContext();
        ctx.setAttribute("count",0);
    }
}
```

FindDeptServlet 类

```java
public class FindDeptServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        String size = ctx.getInitParameter("size");
        System.out.println(size);

        /*
            统计流量
         */
        Integer count = (Integer)ctx.getAttribute("count");
        ctx.setAttribute("count",++count);
        System.out.println(count);
    }
}
```

FindEmpServlet 类

``` java
public class FindEmpServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        String size = ctx.getInitParameter("size");
        System.out.println(size);

         /*
            统计流量
         */
        Integer count = (Integer)ctx.getAttribute("count");
        ctx.setAttribute("count",++count);
        System.out.println(count);
    }
}
```



# 三、Servlet 线程安全问题

3.1 为什么会有线程安全问题

![1 Servlet 线程安](/Users/chenjinhua/ziliao/JavaLearned/doc/43 Servlet /11 Servlet 线程安全.png)

3.2 如何保证线程安全

synchronized 同步方法块。

