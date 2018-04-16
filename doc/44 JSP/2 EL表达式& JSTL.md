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

在 student.jsp 文件里可以用 ${stu.name} 获取Student 对象的name 属性值。

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



### 1.2.2 输出简单的运算结果；

### 1.2.3 获取请求参数。





