[TOC]

# 一、什么是 Servlet

### 1.1 服务器如何给浏览器返回一个页面

1、静态网页

不同的人看到的页面都一样为静态网页，如：开发手册、新闻。

服务器直接保存一份 HTML，并向浏览器发送此 HTML。

2、动态网页

不同的人看到的页面不一样为动态网页，如：淘宝、微博。

服务器保存一个对象，由它动态的拼成一个网页，发送给浏览器；在 java 中该对象是 Servlet。

3、Servlet 特征

- 它是满足sun规范的对象，也叫组件；
- 存储在服务器上；
- 可以动态的拼资源（HTML、IMG），术语：处理 Http 协议。

4、什么是 Servlet？

**是 sun 推出来的用于在服务器端处理 http 协议的组件。**

### 1.2 服务器

1、名词解释

Java 服务器、Web 服务器、Java Web 服务器、Servlet 容器。

2、本质

是一个可以运行 java 项目的**软件**。

3、举例

Tomcat、JBoss、	WebLogic、	WebSphere。

4、Tomcat 使用方式

4.1 单独使用（运维去做）

（1） 配置 java 环境变量；

（2） 下载安装 Tomcat；

（3）启动 Tomcat：终端进入 Tomact/bin 目录，加权限：chmod +x *sh，启动： ./startup.sh。

（4）访问 Tomcat：浏览器输入：http://localhost:8080，网页打开看见一只猫则启动成功。

（5）关闭 Tomcat：终端输入：./shutdown.sh。

4.2 通过 Intellij 管理（*）

# 二、Servlet 开发步骤

![ Servlet dem](/Users/chenjinhua/ziliao/JavaLearned/doc/43 Servlet /1 Servlet demo.png)



1、创建 web 项目

使用 maven 创建 web 项目 https://blog.csdn.net/sinat_34596644/article/details/52891274；

配置好服务器还需再对项目进行基本配置：https://www.cnblogs.com/wql025/p/5015057.html。

2、导入 jar 包（javaee，Tomcat 已包含）

3、开发 Servlet

> 创建一个类，继承 HttpServlet，间接实现了 Servlet 接口；
>
> 重写 service 方法，在此方法内拼接一个网页。

4、配置 web.xml

在 web.xml 通过2步配置好这个类。

5、部署项目

> 部署：拷贝的术语。

https://blog.csdn.net/maomengmeng/article/details/52043928/

6、访问Servlet

http://localhost:8080/项目名/类的网名。

7、程序执行过程

输入第6步的访问路径并回车后，即向服务端发生一个请求，浏览器专门有一个对象去处理请求：

- 根据请求路径里的项目名(如servlet1)，去Tomcat 下找 servlet1的文件夹；
- 然后那个对象会再根据请求路径里的网名去上面文件夹里找 web.xml 配置文件里找对应的网名，根据网名找到别名，继而找到类；
- 找到类后，Tomcat 帮我们实例化该类，并调用 service 方法。

## 2.1 HTTP 协议

1、什么是 http 协议？

- 就是 w3c 制定的一个规范；
- 规定了浏览器和服务器如何通信、通信的数据格式。

2、如何通信？

- 建立请求、发送请求、接收响应、关闭连接
- 一次请求一次连接，尽量降低服务器的压力。

3、通信的数据格式

- 请求数据结构：

  > 请求行：请求的基本信息
  >
  > 消息头：对实体内容的描述
  >
  > 实体内容：浏览器向服务器发送的业务数据

- 响应数据结构：

  > 状态行：服务器自动填写
  >
  > 消息头：大部分消息头数据由服务器自动填写，只有 content-type 由我们填写；
  >
  > 实体内容：服务器向浏览器发送的业务数据。

4、注意

（1）很多事情不需要我们处理：

- 通信步骤由浏览器和服务器自动实现
- 请求数据中的请求行、消息头由浏览器自动填写
- 响应数据中的请求行、消息头由服务器自动填写

（2）少量事情需要我们处理

- 请求数据中的实体内容由我们提供
- 响应数据中的实体内容由我们提供

即通过 request处理请求数据，response 处理响应数据。



# 三、Servlet 工作原理

## 3.1 Servlet 如何获取请求数据

获取请求参数的方法：

- 如果是1个：String req.getParameter("控件 name 值")，返回 String；

- 如果是多个：String[] req.getParameterValues("控件 name 值")，返回一个 String 数组。

  ​

![ 注册 dem](/Users/chenjinhua/ziliao/JavaLearned/doc/43 Servlet /2 注册 demo.png)



action 

- 完整路径：http://ip:port/项目名/网名；


- 绝对路径：/项目名/网名；
- 相对路径：

>当前静态 html 所在路径：servletdemo/reg.html
>
>目标路径（即RegServlet 类路径）：servletdemo/reg
>
>所以相对路径为：reg。（注意前面无路径符号！）

html 代码：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册</title>
</head>
<body>
    <form action="regs" method="get">
        <p>
            账号: <input type="text" name="username">
        </p>
        <p>
            密码: <input type="password" name="pwd">
        </p>
        <p>
            性别: <input type="radio" name="sex" value="M">男
            <input type="radio" name="sex" value="F">女
        </p>
        <p>
            兴趣爱好: <input type="checkbox" name="interests" value="food">美食
            <input type="checkbox" name="interests" value="game">竞技
            <input type="checkbox" name="interests" value="social">社交
        </p>
        <p>
            <input type="submit" value="注册">
        </p>
    </form>

</body>
</html>
```

RegServlet 类：

```java
package main.java.day02;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by jinhua.chen on 2018/4/14.
 */
public class RegServlet extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
        * 处理请求的一般流程:
        * 1. 接收参数
        * 2. 处理业务
        * 3. 发送响应
        * */
        String username = req.getParameter("username");
        String pwd = req.getParameter("pwd");
        String sex = req.getParameter("sex");
        String[] interestsList = req.getParameterValues("interests");

        System.out.println("姓名: " + username);
        System.out.println("性别: " + sex);
        if (interestsList.length >0){
            for (int i = 0; i < interestsList.length; i++) {
                System.out.print("兴趣爱好: " + interestsList[i] + ",");

            }
        }

        resp.setContentType("text/html");
        PrintWriter w = resp.getWriter();
        w.println("<p>" + "OK," + username + "</p>");
        w.close();
    }
}
```

web.xml 配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
    <servlet>
        <servlet-name>time</servlet-name>
        <servlet-class>main.java.day01.TimeServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>time</servlet-name>
        <url-pattern>/ts</url-pattern>
    </servlet-mapping>

    <servlet>
        <servlet-name>regs</servlet-name>
        <servlet-class>main.java.day02.RegServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>regs</servlet-name>
        <url-pattern>/regs</url-pattern>
    </servlet-mapping>
</web-app>
```

### 3.1.1 常用请求方式 GET 和 POST的区别

1. get 请求

   - 采用路径传参；
   - 参数在传递过程中可见，隐私性差；
   - 路径大小有限制，所有传递的参数大小受限
   - 所有的请求默认get 请求。

2. post 请求

   - 采用实体内容传参；
   - 参数在传递过程中不可见，隐私性好；
   - 实体内容不受大小限制，大小不受限；
   - 在表单上加上 “method=‘post’ ”。

3. 建议

   参数需要保密或者参数较多时用 post。

## 3.2 Servlet 如何运行（运行原理）

Servlet 运行原理：

​	浏览器和服务器这2者都是软件，软件内部是由对象构成的，他们之间的通信是由通信组件或者通信的对象来负责完成。通信的方式：连接、打包、发送、拆包，服务器拆包后将要把数据封装到 request、response对象里，然后给 Servlet...。

![ Servlet 运行原](/Users/chenjinhua/ziliao/JavaLearned/doc/43 Servlet /3 Servlet 运行原理.png)

## 3.3 乱码解决方案

### 3.3.1 处理请求参数中文

**乱码原因**

浏览器和服务器在网络上传输数据，所以浏览器打包后的数据以 byte 格式（String 转 byte，html 文件里指定了字符编码集 meta = 'utf-8'）传输给服务器，服务器拆包后将 byte 转为 String，而服务器默认编码格式为 ISO，编码和解码格式不一致，导致乱码。

**解决方案**

![ 解决乱码问](/Users/chenjinhua/ziliao/JavaLearned/doc/43 Servlet /4 解决乱码问题.png)

  方案一解决乱码

```java
// getBytes() 方法进行编码转换.
byte[] bs = username.getBytes("ISO-8859-1");
// String 构造方法将 byte 数组变成 String.
username = new String(bs,"utf-8");
```

  方案二解决乱码

  方案三解决乱码

```java
req.setCharacterEncoding("utf-8");
```

### 3.3.2 Servlet 输出中文

**乱码原因**

ISO 编码本身就不支持中文。

**解决方案**

按照 utf-8编码方式进行打包，同时告诉浏览器使用utf-8编码，往往这2者只要声明1处。

![ 响应内容里乱码解](/Users/chenjinhua/ziliao/JavaLearned/doc/43 Servlet /5 响应内容里乱码解决.png)

## 3.4 案例：查询员工

entity.Emp类

```java
package main.java.day02.entity;

import java.io.Serializable;

/**
 * Created by jinhua.chen on 2018/4/14.
 */
public class Emp implements Serializable{
    private String name;
    private int age;

    public Emp(){}
    public Emp(String name,int age){
        name = this.name;
        age = this.age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

dao.EmpDao类

```java
package main.java.day02.dao;

import main.java.day02.entity.Emp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinhua.chen on 2018/4/14.
 */
public class EmpDao implements Serializable{
    public List<Emp> findAll(){

        List<Emp> empList = new ArrayList<Emp>();

        Emp emp0 = new Emp();
        emp0.setName("张三");
        emp0.setAge(18);
        empList.add(emp0);

        Emp emp1 = new Emp();
        emp1.setName("李四");
        emp1.setAge(20);
        empList.add(emp1);

        return empList;
    }
}
```

web.FindEmpServlet

```java
package main.java.day02.web;

import main.java.day02.dao.EmpDao;
import main.java.day02.entity.Emp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by jinhua.chen on 2018/4/14.
 */
public class FindEmpServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 处理业务
        List<Emp> emps = new EmpDao().findAll();
        // 发送响应
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter w = resp.getWriter();
        w.println("<table border='1' cellspacing='0' width=30%>");
        w.println(" <tr>");
        w.println("     <td>姓名</td>");
        w.println("     <td>年龄</td>");
        w.println("</tr>");
        if (emps != null){
            for (Emp e : emps){
                w.println(" <tr>");
                w.println("     <td>" + e.getName() + " </td>");
                w.println("     <td>" + e.getAge() + " </td>");
                w.println(" </tr>");
            }
        }
        w.println("</table>");
        w.close();
    }
}
```

web.xml 

略

html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>查询员工</title>
</head>
<body>
    <form action="findEmp">
        <input type="submit" value="查询">
    </form>
</body>
</html>
```

## 3.5 重定向

重定向到查询页面，即建议浏览器自己去访问查询页面。

```java
/* 参数是路径,这里使用相对路径
	 当前路径:servlet/addEmp
	 目标路径:servlet/findEmp
*/
resp.sendRedirect("findEmp");
```

### 3.5.1 重定向经典使用场景

1、解决互联网上2个互不相关网站之间的跳转问题；

![ 重定向经典使用场](/Users/chenjinhua/ziliao/JavaLearned/doc/43 Servlet /6 重定向经典使用场景.png)

2、解决一个项目里2个独立的组件之间的跳转问题

![ 重定向使用场](/Users/chenjinhua/ziliao/JavaLearned/doc/43 Servlet /7 重定向使用场景.png)

### 3.5.2 重定向本质

​	服务器向浏览器发生一个302状态码和一个 Location 消息头（是一个地址，即重定向地址），浏览器收到后会立即向重定向地址发送请求。

## 3.6 访问路径

### 3.6.1 项目部署的过程

![ 项目部署过](/Users/chenjinhua/ziliao/JavaLearned/doc/43 Servlet /8 项目部署过程.png)



**所谓的访问路径都是指部署代码的访问路径。**

部署代码的路径规则：

- 静态资源（HTML/CSS/JS/IMG）：就是文件存放的位置；
- 动态资源（Servlet）：web.xml 中配置的路径。

### 3.6.2 如何获取访问路径

getContentPath() —— 项目名

getServletPath() ——Servlet 访问路径

getRequestURI() ——URI（绝对路径）

getRequestURL() —— URL（完整路径）

### 3.6.3 URI 和 URL 的区别

1、狭义的理解（Java Web 项目）

URI 是绝对路径，URL 是完整路径。

**2、广义的理解（任意的 web 项目）** 

- URI 是资源的名字，URL 是资源的真名；
- URI 包含 URL。

### 3.6.4 如何配置 Servlet 访问路径

1、精确配置

>/findEmp
>
>必须通过“/findEmp”才能访问此 Servlet；
>
>此 Servlet 只能处理“/findEmp”这一个请求。

2、通配符

> /*
>
> 通过任何路径都能访问此 Servlet；
>
> 此 Servlet 能处理一切请求。

3、后缀

>*.abc
>
>以“*.abc”为后缀的请求都能访问此 Servlet；
>
>此 Servlet 能处理多个请求。

### 3.6.5 一个 Servlet 如何处理多个请求

![ 一个 Servlet 处理多个请](/Users/chenjinhua/ziliao/JavaLearned/doc/43 Servlet /9 一个 Servlet 处理多个请求.png)


