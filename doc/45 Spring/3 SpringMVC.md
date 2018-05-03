[TOC]

# 一、什么是 SpringMVC

- 用来简化基于 MVC 架构的 web应用程序开发的框架；

- SpringMVC 是 Spring 中的一个模块。

  ​

![ SpringMV](/Users/chenjinhua/ziliao/JavaLearned/doc/45 Spring/3 SpringMVC.png)

# 二、SpringMVC五大核心组件

![ 五大组](/Users/chenjinhua/ziliao/JavaLearned/doc/45 Spring/4 五大组件.png)

​	请求发送给 DispatcherServlet 来处理，DispatcherServlet 会依据 HandlerMapping  的配置调用对应的 Controller 来处理。Controller 将处理结果封装成 ModelAndView，然后返回给 DispatcherServlet。DispatcherServlet 会依据 ViewResolver 的解析调用对应的视图对象（比如 jsp）来生成相应的页面。



# 三、SpringMVC 编程步骤

![ 五大组件运行原](/Users/chenjinhua/ziliao/JavaLearned/doc/45 Spring/5 五大组件运行原理.png)



**1、导包**

spring-webmvc （IOC 和 SpringMVC 都需要这个包）

**2、添加 spring 配置文件**

**3、在 web.xml 里配置DispatcherServlet**

```xml
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Archetype Created Web Application</display-name>
  
  <servlet>
    <servlet-name>springmvc</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!-- 初始化参数的目的是启动 Spring 容器-->
    <init-param>
      <!-- 参数contextConfigLocation 是用来指定 spring 配置文件的位置-->
      <param-name>contextConfigLocation</param-name>
      <!-- 值为一个配置文件-->
      <param-value>classpath:spring-mvc.xml</param-value>
    </init-param>
    <!--tomcat 启动后就会实例化DispatcherServlet -->
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>springmvc</servlet-name>
    <url-pattern>*.do</url-pattern>
  </servlet-mapping>
</web-app>
```

​	tomcat 启动后就会实例化DispatcherServlet，DispatcherServlet初始化方法执行后就会启动 Spring 容器。

**4、写 Controller 返回值ModelAndView**

```java
public class HelloController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        System.out.println("handleRequest()");
        // 返回视图名
        return new ModelAndView("hello");
    }
}
```

​	ModelAndView 常用构造器有2个：

- ModelAndView(String viewName)
- ModelAndView(String viewName,Map data) 

viewName 视图名即是 jsp 页面的名字；data 处理结果，数据存储到 request 的 attribute 中。

**5、在 spring.xml配置文件中添加HandleMapping，ViewResolver的配置**

```xml
<!-- 配置 HandlerMapping(接口) -->
<bean class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
        <property name="mappings">
            <props>
                <!-- 如果请求是 /hello.do，则交给 helloControl 对象处理-->
                <prop key="/hello.do">helloControl</prop>
            </props>
        </property>
</bean>

<!-- 定义 HelloController 对象 helloControl -->
<bean id="helloControl" class="HelloController"></bean>

<!-- 配置 ViewResolver(接口) -->
<bean id="jspViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"></property>
        <property name="suffix" value=".jsp"></property>
</bean>
```

​	上面ViewResolver的配置，若视图名是：hello，通过以上配置可以映射到 “/WEB-INF/jsp/hello.jsp”。



# 四、基于注解的 SpringMVC应用

## 4.1 编程步骤

1、导包：SpringMVC

2、添加 spring 配置文件

3、添加 DispatcherServlet

4、写 Controller

- 不用实现 Controller 接口；
- 方法名不做要求，返回值可以是 ModelAndView也可以是 String；
- 可以添加多个方法（处理多个请求）；
- 类前使用 @Controller；
- 可以在类或者方法前使用 @RequestMapping（相当于 HandlerMapping）。

```java
@Controller
public class HelloController {
    @RequestMapping("/hello.do")  
    public String hello(){
         return "hello";
    }
}
```

补充：

```java
@Controller
@RequestMapping("/demo") // 一般一个模块一个处理器时这样用
public class HelloController {
    @RequestMapping("/hello.do")  // 处理请求 http://ip:port/项目名/demo/hello.do
    public String hello(){
    }
    @RequestMapping("/login.do")  // 处理请求 http://ip:port/项目名/demo/login.do
    public String login(){
    }
}
```

5、写 jsp

6、在 spring 配置文件中，添加 ViewResolver配置、组件扫描配置、MVC 注解扫描配置（否则不能识别RequestMapping注解）

spring.xml 配置

```xml
<!-- 配置组件扫描 -->
<context:component-scan base-package="controller"></context:component-scan>

<!--配置 mvc 注解扫描-->
<mvc:annotation-driven></mvc:annotation-driven>

<!--配置视图解析器-->
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/"></property>
    <property name="suffix" value=".jsp"></property>
</bean>
```



# 五、读取请求参数值

## 5.1 方式一：通过 Request

``` java
@RequestMapping("/login.do")

// DispatcherServlet 在调用处理的方法之前，会利用 java 的反射机制分析方法的结构，比如这儿通过分析，会将 Request 对象传过来。
public String login(HttpServletRequest req){
    System.out.println("login()");
    String name = req.getParameter("username");
    String password = req.getParameter("pwd");
    System.out.println("username:" + name + ", password: " + password);
    return "index";
}
```

## 5.2 方式二：通过注解 @RrequestParam

```java
@RequestMapping("/login2.do")

// DispatcherServlet 在调用处理的方法之前，会利用 java 的反射机制分析方法的结构.这里会先调用 Request 对象的方法获取参数值，然后赋值给对应的形参。如果形参和请求中的参数名称不一致，需要加注解 @RequestParam。
public String login2(String username,@RequestParam("pwd") String pwd){
    System.out.println("username:" + username + ", password: " + pwd);
    return "index";
}
```

## 5.3 方式三：通过 JavaBean

步骤1：封装请求参数值的 java 类

- 属性名与请求参数名要一致，类型要匹配（会自动类型转换如请求参数 age 传来是 String会自动转为 int）；
- 提供 getter、setter 方法。

```java
package controller;

import org.springframework.stereotype.Component;

/**
 * Created by jinhua.chen on 2018/4/30.
 */
public class AdminParam {
    private String username;
    private String pwd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
```

步骤2：使用上面的 JavaBean

```java
@RequestMapping("/login3.do")
 
// DispatcherServlet 在调用处理的方法之前，会利用 java 的反射机制分析方法的结构.这里会将 AdminParam 对象创建好，会将请求参数值赋值给对应的属性。
public String login3(AdminParam admin){
    System.out.println("username:" + admin.getUsername() + ", password: " + admin.getPwd());
    return "index";
}
```



# 六、向页面传值

## 6.1 方式一：将数据绑定到 request

```java
@RequestMapping("/login4.do")
public String login4(AdminParam admin,HttpServletRequest req){
    String name = admin.getUsername();
    req.setAttribute("name",name);
    
    // 默认情况下，DispatcherServlet 会使用转发。所以这里不需要写任何代码，转发时会带上 Request。
    return "index";
}
```

index.jsp 获取传入的值：

```jsp
<p>账户名: ${name}</p>
```



## 6.2 方式二：返回 ModelAndView



```java
@RequestMapping("/login5.do")
public ModelAndView login5(AdminParam admin){
    String name = admin.getUsername();
    Map<String,Object> map = new HashMap<String, Object>();
    
    // 相当于执行了 req.setAttribute()
    map.put("name",name);
    ModelAndView mav = new ModelAndView("index",map);
    return mav;
}
```



index.jsp 获取传入的值同上。



## 6.3 方式三：将数据添加到 ModelMap

```java
@RequestMapping("/login6.do")
public String login6(AdminParam admin, ModelMap mm){
    String name = admin.getUsername();
    
    // 相当于执行了 req.setAttribute()
    mm.addAttribute("name",name);
    return "index";
}
```



index.jsp 获取传入的值同上。



## 6.4 方法四：将数据绑定到 session

```java
@RequestMapping("/login7.do")
public String login7(AdminParam admin,HttpSession session){
    String name = admin.getUsername();
    session.setAttribute("name",name);
    return "index";
}
```

index.jsp 获取传入的值同上。



## 6.5 总结

在满足使用条件下，优先使用生命周期短的，节省内存空间，即前3种方式。



# 七、重定向

## 7.1 方法的返回值是 String 时的重定向

语法：    return "redirect:toIndex.do";

```java
@RequestMapping("/login8.do")
public String login8(){
    // 重定向
    return "redirect:toIndex.do";
}
```



## 7.2 方法的返回值是 ModelAndView 时的重定向

语法：

```java
@RequestMapping("/login9.do")
public ModelAndView login9(){
	// 重定向
    RedirectView rv = new RedirectView("toIndex.do");
    ModelAndView mav = new ModelAndView(rv);
    return mav;
}
```