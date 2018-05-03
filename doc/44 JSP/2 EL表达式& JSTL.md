[TOC]

为什么需要EL 表达式和 JSP 标签？

​	JSP 中嵌套了大量的 Java 代码，不利于代码的维护。为此sun 公司制定了 JSP 标签来代替 java 代码。

Apache组织开发的一套标签库被 sun 公司整合后称为标准标签库（JSP Standard Tag Library 即 JSTL），配合 EL 表达式，以减轻 JSP文件的复杂度，便于维护 JSP 文件。

![ EL JSTL作](/Users/chenjinhua/ziliao/JavaLearned/doc/44 JSP/8 EL JSTL作用.png)

# 一、EL 表达式

## 1.1 EL 表达式

EL 表达式是一套简单的计算规则，用于给 JSP标签的属性赋值，也可以直接用来输出。

## 1.2 EL 表达式的作用

EL 表达式的作用可以分为以下3类：

### 1.2.1 访问 Bean 属性

- 语法：${对象名.属性名} 

或者 ${对象名["属性名"]}

- 执行过程：tomcat 容器会依次从 pageContext->request->session->application中查找绑定名称为 “stu”的对象，找到后调用 getName 方法输出。

如：在 StudentServlet 类service 方法 将数据绑定到req并转发给 student.jsp ：

```java
req.setAtribute("stu",s);
req.getRequestDispacher("student.jsp").forward(req,resp);
```

在 student.jsp 文件里可以用 ${stu.name} 获取Student 对象的name 属性值：

```jsp
<p>${stu.name }</p>
```

以上代码等价于

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>第1个 jsp</title>
</head>
<body>
    <% 
    	Student stu = (Student) req.getAttribute("stu"); 
    %>
    	<%= stu.getName() %>
</body>
</html>
```

附录：Intellij 项目结构和 Eclipse 不一样，如下是 Intellij 的项目结构：

![0 Intellij + maven +Servlet项目结](/Users/chenjinhua/ziliao/JavaLearned/doc/44 JSP/10 Intellij + maven +Servlet项目结构.png)

注意 jsp 文件里 page 指令要加上属性 isIgnored = “false”！



- EL 表达式的取值范围

  默认从4个隐含对象内依次取值：page -> request -> session -> application。

### 1.2.2 输出简单的运算结果

​	算术运算、逻辑运算、关系运算、empty（判断字符串或集合是否为空，若为空则为 true）：

```jsp
<p> 兴趣爱好是否为空: ${empty stu.interests}</p>
```

### 1.2.3 获取请求参数

- 语法

  ${param.username} 等价于 req.getParam("username")；

  ${paramValues.interests} 等价于 req.getParamValues("interests")。

例子：浏览器里访问 url 设为：http://localhost:8080/testJstl/findStudent?username=%22jinhua%22&password=123。

```jsp
<p>接收到参数用户名: ${param.username},密码： ${param.password}</p>
```

# 二、JSTL

JSTL（Jsp Standard Tag Library）JSP 标准标签库。

## 2.1 如何使用 JSTL

1、将 JSTL 标签对应的 jar 文件拷贝到 WEB-INF 的lib 目录下：

需要引入 jstl.jar 和 taglibs.jar 包：

```xml
<dependency>
  <groupId>jstl</groupId>
  <artifactId>jstl</artifactId>
  <version>1.2</version>
</dependency>
<dependency>
  <groupId>taglibs</groupId>
  <artifactId>standard</artifactId>
  <version>1.1.2</version>
</dependency>
```



2、使用 taglib 指令导入要使用的 JSP 标签：

```jsp
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
```

其中 uri 是 JSP 标签的命名空间； prefix 是命名空间的前缀。



## 2.2 if 标签

语法：

```jsp
<p>性别:
    <c:if test="${stu.sex=='M'}">男</c:if>
    <c:if test="${stu.sex=='F'}">女</c:if>
</p>
```

test 属性值是：判断条件，双标签之内是：要输出的结果。所以上面代码含义时当 sex=m 时输出男。

## 2.3 choose 标签

语法：

```
<p>
    性别2:
    <c:choose>
    <c:when test="${stu.sex=='M'}">男生</c:when>
    <c:otherwise>女生</c:otherwise>
    </c:choose>
</p>
```

when 表示一个处理分支，当 test 属性为 true 时会执行，when 分支可出现1次或多次。

上面代码和下面代码结果一样。

```
<p>
    性别1:
    <c:if test="${stu.sex=='M'}">男/c:if
    <c:if test="${stu.sex=='F'}">女/c:if
</p>
```

## 2.4 forEach 标签

- 作用：用来遍历数组或集合
- item 属性是要遍历的集合，var 属性值是：每1次遍历得到的值；**用 EL 输出内容！**

```jsp
<p>
    <c:forEach items="${stu.interests}" var="i">
        ${i}
    </c:forEach>
</p>
```

## 2.5 JSTL  运行原理 & 本质 

![1 JSTL 本质&运行原](/Users/chenjinhua/ziliao/JavaLearned/doc/44 JSP/11 JSTL 本质&运行原理.png)

JSTL 本质：

​	JSTL 标签被 tomcat 翻译成 Servlet 当中的一句话即：new 标签类().doTag()。