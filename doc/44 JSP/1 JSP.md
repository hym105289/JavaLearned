[TOC]

JSP：java server page，在标签内写 java。

# 一、 JSP 基本语法

​	Servlet 技术产生后，最麻烦的是使用 out.println() 语句输出页面，很难书写和维护。于是推出 JSP 技术，用来将 Servlet 中负责显示的语句抽取出来。

​	什么是 JSP？

sun 公司制定的一种服务器端动态页面技术的组件规范。JSP 是以".jsp"为后缀的文件，该文件主要是 HTML 和少量的 java 代码。JSP 会被容器转换成 Servlet 类然后执行。

​	如何编写 JSP？

1、写一个以".jsp"为后缀的文件；

2、在该文件中可以包含如下的内容：

- HTML（CSS、JS）
- Java代码
- 注释
- 指令
- 隐含对象



## 1.1 JSP 页面中的HTML代码

- 像编写HTML 页面一样编写；
- 作用：控制页面在浏览器上显示的效果；
- **转译成 Servlet时的规则：成为 Servlet 里 service 方法的 out.write 语句。**

## 1.2 JSP 页面中的 Java 代码

JSP 里的 java 代码，包含如下3种：

### 1.2.1 JSP 小脚本

- 语法规则：<% ... ... %>
- 合法内容：能够写在方法里的 java 代码片段都可以作为小脚本；
- **转译成 Servlet时的规则：原封不动成为 Servlet 里 service 方法的 一段代码。**

### 1.2.2 JSP 表达式

- 语法规则：<%= ... ... %>
- 合法内容：变量、表达式、有返回值的方法；
- **转译成 Servlet时的规则：在 service 方法中用的 out.write 语句输出该变量、表达式、方法的值。**

### 1.2.3 JSP 声明

- 语法规则：<%！ ... ... %>
- 合法内容：成员属性或者成员方法的声明；
- **转译成 Servlet时的规则：成为 JSP页面转译成的 Servlet 类中成员属性或成员方法。**

### 1.2.4 案例

​	在 JSP 页面中，以无序列表的形式输出10个随机数。

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>第1个 jsp</title>
</head>
<body>
    <ul>
        <%-- 1. JSP 小脚本--%>
        <%
            for (int i = 0; i < 10; i++) { %>
                <%-- 2. JSP 表达式--%>
                <li><%= Math.random()%> </li>
        <%
            }
        %>
    </ul>
</body>
</html>
```

计算某个小数的100倍.

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>第1个 jsp</title>
</head>
<body>
    <%-- 3. JSP 声明--%>
    <%!
        public double hundred(double num){
            return num * 100;
        }
    %>
    <ul>
        <%-- 1. JSP 小脚本--%>
        <%
            for (int i = 0; i < 10; i++) { %>
                <%-- 2. JSP 表达式--%>
                <li><%= hundred(Math.random()) %> </li>

        <%
            }
        %>
    </ul>
</body>
</html>
```

## 1.3 JSP 页面中的注释

```jsp
<！-- 注释内容--%>
    HTML 注释，如果注释里包含 java 代码，该 java 代码仍会执行。
<%-- 注释内容--%>
    JSP 特有的注释，如果注释里包含 java 代码，该 java 代码会被忽略，不会执行。
```

## 1.4 JSP 页面中的指令

- 语法规则：<%@ 指令名 属性=值 %>；
- 常用指令： page指令、include 指令、taglib 指令；
- 作用：控制 JSP 在转译成 Servlet 类时生成的内容。

### 1.4.1 Page 指令

作用：用于设置页面属性、导包；

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*,java.text.SimpleDateFormat"%>
```

pageEncoding 属性—— 声明此 JSP 文件的编码方式，容器读取该文件时的解码方式；

contentType 属性—— 声明服务器向浏览器输出内容的格式；

import 属性—— 导包。

### 1.4.2 include 指令

作用：在 JSP 转译成 Servlet 类时，能够将其他文件（可以是 JSP 也可以是静态 html 文件）包含进来。

```jsp
<%@include file="time.jsp"%>
```

### 1.4.3 指令练习

​	在 JSP 页面中，以“HH:mm:ss” 格式输出当前的系统时间。

time.jsp 文件

<%--由于该 JSP 文件是被引用的,不需要完整的 HTML 格式.--%>

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*,java.text.SimpleDateFormat"%>
<%
  Date d = new Date();
  SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
  String time = sdf.format(d);
%>

<p> <%= time%></p>
```

hello.jsp 文件

```jsp
<%@ page import="java.util.Random" %><%--
  Created by IntelliJ IDEA.
  User: jinhua.chen
  Date: 2018/4/15
  Time: 上午10:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>第1个 jsp</title>
</head>
<body>
    <%@include file="time.jsp"%>

   
</body>
</html>
```

# 二、 JSP 运行原理

## 2.1 JSP 运行的过程

![ JSP 运行原](/Users/chenjinhua/ziliao/JavaLearned/doc/44 JSP/1 JSP 运行原理.png)



​	**JSP 的本质就是 Servlet。**

## 2.2 tomcat 翻译 JSP 文件的过程

![ JSP 翻译过](/Users/chenjinhua/ziliao/JavaLearned/doc/44 JSP/2 JSP 翻译过程.png)



# 三、 JSP 隐含（内置）对象

什么是隐含对象？

​	容器自动创建，在 JSP 文件中可以直接使用的对象（也就是变量）。

作用：

​	JSP 预先创建的这些对象可以简化对 HTTP 请求、响应信息的访问。

## 3.1 隐含对象分别是

1. **request（HttpServletRequest）、**
2. response（HttpServletResponse）、
3. out（JSPWriter）、
4. config（ServletConfig）、
5. application（ServletContext）、
6. exception（Throwable）；
7. **session（HttpSession）**
8. page（Object，就是 this）
9. **pageContext（PageContext，是一个管理者，通过它可以获取其他8个隐含对象）**

## 3.2 如何使用隐含对象

在 JSP 文件中可以直接使用隐含对象

```jsp
<%String user = request.getParameter("user"); %>
```

## 3.3 查询员工案例

​	之前用 FindEmpServlet 实现的查询员工，现在用 JSP 实现（无需  FindEmpServlet 类）：

```jsp
<%--
  Created by IntelliJ IDEA.
  User: jinhua.chen
  Date: 2018/4/7
  Time: 下午5:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="dao.EmpDao,entity.Emp,java.util.*" %>
<html>
<head>
  <title>查询员工</title>
  <style>
    table {
      border: 1px solid red;
      border-collapse: collapse;

    }
    td {
      border: 1px solid red;
    }
  </style>
</head>
<body>


<a href="addEmp.html">新增</a>
<table>
  <tr>
    <td>姓名</td>
    <td>年龄</td>
  </tr>
  <%
    EmpDao dao = new EmpDao();
    List<Emp> emps = dao.findAll();

    if (emps.size()>0){
      for (Emp e :emps){
  %>

  <tr>
    <td><%= e.getName()%></td>
    <td><%= e.getAge()%></td>
  </tr>

  <%
      }
    }
  %>

</table>

</body>
</html>
```

# 四、MVC 模式

开发模式

模式一

![ Model](/Users/chenjinhua/ziliao/JavaLearned/doc/44 JSP/3 Model1.png)

模式二

![ MVC 模](/Users/chenjinhua/ziliao/JavaLearned/doc/44 JSP/4 MVC 模式.png)



MVC 模式：是一个经典的设计模式，是软件的分层思想：

M：Model 即业务层，用来处理业务；

V：View 即视图层，用来展现数据；

C：Controller 即控制层，是业务层和数据层的桥梁。

它可以降低软件中代码的耦合度，便于团队开发和维护。



# 五、转发



## 5.1 查询员工的案例

FindEmpServlet 类：

- 1. 将数据绑定到 Request 对象上；
- 2. 将请求转发给 find_emp.jsp。

```java
public class FindEmpServlet extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Emp> eList = new EmpDao().findAll();

        // 1. 将数据绑定到 Request 对象上
        req.setAttribute("empList",eList);

        // 2. 将请求转发给 find_emp.jsp
        req.getRequestDispatcher("find_emp.jsp").forward(req,resp);
    }
}
```

find_emp.jsp 文件：

```jsp
<%@ page import="java.util.List" %>
<%@ page import="entity.Emp" %><%--
  Created by IntelliJ IDEA.
  User: jinhua.chen
  Date: 2018/4/15
  Time: 下午2:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查询员工</title>
    <style>
        table {
            border: 1px solid red;
            border-collapse: collapse;
        }
        td {
            border: 1px solid red;
        }
    </style>
</head>
<body>
    <% List<Emp> eList = (List<Emp>) request.getAttribute("empList");%>

    <table>
        <tr>
            <td>姓名</td>
            <td>年龄</td>
        </tr>

        <% if (eList.size()>0){
            for (Emp e : eList){
        %>
        <tr>
            <td><%= e.getName()%></td>
            <td><%= e.getAge()%></td>
        </tr>

        <%
            }
        }%>
    </table>
</body>
</html>
```

 **注意：**在 MVC 模式下，我们访问路径一定是访问Servlet，不是 JSP。因为如果直接访问 JSP，JSP 没有 Servlet 里的数据。



## 5.2 转发和重定向

## 5.2.1 它们的相同点

- 都是解决2个 web 组件之间的跳转问题。
- web 组件： Servlet、JSP。

### 5.2.2 它们的区别

通俗的理解

![ 转发和重定向区](/Users/chenjinhua/ziliao/JavaLearned/doc/44 JSP/5 转发和重定向区别.png)



专业的理解

#### 5.2.2.1 转发的特点

![ 转发的特](/Users/chenjinhua/ziliao/JavaLearned/doc/44 JSP/6 转发的特点.png)

#### 5.2.2.2 重定向的特点

![ 重定向的特](/Users/chenjinhua/ziliao/JavaLearned/doc/44 JSP/7 重定向的特点.png)

### 5.2.3 使用建议

**通常查询时用转发，增加、修改、删除后重定向到查询。**

