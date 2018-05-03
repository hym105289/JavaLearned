[TOC]



# 一、Cookie 和 session

什么是 Cookie？

​	浏览器向 web 服务器发送请求时，服务器会将少量的数据以 set-cookie 消息头的方式发送给浏览器，浏览器将这些数据保存下来；当浏览器再次访问服务器时会将这些数据以Cookie 消息头的方式发送给服务器。

业务场景：登录时要记录登录账号，后续查询、增加、修改页面上显示账号信息。



cookie 和 session 专门解决此类问题：

- 它们内部的数据可以在多个请求之间共用；
- 它们内部的数据可以在多个 servlet 之间共用；
- 它们内部的数据可以在多个浏览器之间共用（服务器会给每个浏览器创建1组 cookie 和1个 session）。



## 1.1 cookie 和 session 的区别

- cookie 存储在浏览器上，服务器压力小，不安全；
- session 存储在服务器上，服务器压力大，安全。

## 1.2 使用建议 

重要的数据存入 session（安全），其他数据存入 cookie。



# 二、Cookie 使用方式

​	在登录时传入账号，服务器把账号存入 cookie，并把 cookie 给浏览器，浏览器在访问服务器器其他功能时，会自动传入 cookie。

![0 Cooki](/Users/chenjinhua/ziliao/JavaLearned/doc/44%20JSP/20%20Cookie.png)



## 2.1 Cookie 基本用法

- 创建 Cookie、发送 Cookie

```java
String user = req.getParameter("user");
// cookie 只能存1条数据,并且只能存字符串.
Cookie c1 = new Cookie("user",user);
// 将 cookie 绑定到 resp 上,当服务器发送响应时会自动发送 cookie.
resp.addCookie(c1);
```



- 获取所有 Cookie（获取一个 Cookie 对象的名称或值）

```java
Cookie cookie[] = req.getCookies();

if (cookie != null){
    resp.setContentType("text/html;charset=utf-8");
    PrintWriter w = resp.getWriter();
    for (Cookie c : cookie){
        w.println((c.getName() + " : " + c.getValue()));
    }
    w.close();
}
```



## 2.2 Cookie 生存时间

​	默认情况下，浏览器会将 Cookie 保存在内存中，只要浏览器不关闭，Cookie 就一直存在，浏览器关闭了，Cookie 就不存在了。

​	如果希望关闭浏览器后Cookie 仍存在，可以通过设置过期时间（网站上面的记住用户名和密码就是这样实现的）。

修改 Cookie 的生存时间：Void Cookie.setMaxAge(int seconds).

当 seconds<0：缺省值，浏览器会将 Cookie 保存到内存中；

当 seconds=0：删除 Cookie；

当 seconds>0：浏览器要保存 Cookie 的时间，此时 Cookie 保存在硬盘上。



## 2.3 设置 Cookie 的路径

​	浏览器访问服务器上某个地址时，会比较 Cookie 的路径是否与当前路径匹配，只有匹配的才会将 Cookie 发送给服务器。

​	Cookie 的默认路径是添加这个 Cookie 的 web 组件的路径，如在/appName/file/LoginServlet 下添加 Cookie， 该 Cookie 的路径为/appName/file。

​	设置 Cookie 的路径：

```java
Cookie c = new Cookie("name","zhangsan");
c.setPath("/appName");
resp.addCookie(c);
```



## 2.4 Cookie 含有中文（编码&解码）

Cookie 只能保存合法的 ASCII字符，如果需要保存中文，需要将中文转换成合法的 ASCII 码字符，即编码：

```java
Cookie c = new Cookie("city",URLEncoder..encode("上海","utf-8"));
```

编码后的 Cookie 为了能看到中文，需要还原后再显示：

```java
Cookie[] cookies = req.getCookies();
if(cookies != null){
    Cookie c = cookies[0];
    String value = c.getValue();
	value = URLDecoder.decode(value,"utf-8");
}
```



## 2.5 Cookie 在项目中的使用

​	EL 默认从4个隐含对象中取值：page\request\session\application，但是它也有能力从 Cookie 中取值，语法：cookie.name.value。

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<img src="images/logo.png" alt="logo" class="left"/>
<span>${cookie.user.value}</span>
<a href="#">[退出]</a>
```



## 2.6 include中的路径

​	重定向有路径、转发有路径、表单提交有路径、include 中有路径、图片、样式有路径等等等。

相对路径我们知道谁相对谁，对于 include 路径：

​	include 执行是在翻译时执行的，在翻译 find.jsp 时需要 logo.jsp，tomcat就去找 logo.jsp，所以相对路径就是这两个文件的相对路径。

![0.1 include中的路](/Users/chenjinhua/ziliao/JavaLearned/doc/44 JSP/20.1 include中的路径.png)



# 三、session

## 3.1 什么是 session

​	浏览器访问 web 服务器时，服务器会为每个浏览器在服务器端的内存中分配空间，单独创建一个 Session 对象，该对象有唯一的属性 id（sessionId）,服务器会将 sessionId 以 Cookie 的形式返回给浏览器；浏览器再次访问服务器时会带上 sessionId，服务器根据 sessionId 找到 session 对象。![1 sessio](/Users/chenjinhua/ziliao/JavaLearned/doc/44 JSP/21 session.png)



## 3.2 session 基本使用

### 3.2.1 如何获得session

不论第一次服务器端生成时获取还是第 n 次访问服务器端时获取都是如下的方法：

```java
HttpSession session = req.getSession()
```

### 3.2.2 如何存数据（session 绑定对象）

```java
session.setAttribute("user",loginName);
```

**注意1：session 中可以存任意类型的数据，cookie 里只能存 String 类型数据。**因为后者是运行在浏览器上，浏览器不认识 java 代码，而前者是运行在服务器端，服务器端可以处理 java 代码。

**注意2：session、cookie 是用来存储数据的，为啥不存储到数据库里？**因为后者是存储到数据库是永久保存，而前者是存储到内存里，这些数据不需要永久保存。



### 3.2.3 如何取数据（获取session 绑定的对象） 

```java
HttpSession session = req.getSession();
String user = (String) session.getAttribute("user");
```

### 3.2.4 如何删数据

```java
HttpSession session = req.getSession();
session.removeAttribute("user");
```

### 2.2.5 销毁 session

当用户点击退出登录时，需要销毁 session

```java
seesion.invalidate();
```

### 3.2.6 session 超时时间

默认是30分钟，超过此时间服务器会将该 session 对象删除，以节省服务器内存空间资源；

可在 web.xml 里设置超时时间。

## 3.3 cookie 和 session 理解

### 3.3.1 通俗的理解

- 它们都是暂存数据的对象，特征：
- 内部的数据可以在多个请求之间共用；
- 内部的数据可以在多个组件之间共用；
- 服务器会给每个浏览器创建一组 cookie，1个 session。



### 3.3.2 专业的理解

- Http 协议是一个无状态协议，即服务器默认不会记住浏览器；
- cookie 和 session 就是用来管理这种状态（状态：用来证明浏览器曾经访问过服务器的证据即数据），让服务器记住浏览器的。
- 即：cookie 和 session 它们都是用来进行状态管理的对象。



# 四、案例 - 验证码

​	之前代码设置 resp.setContentType("text/html")，所以**服务器返回给浏览器的是一个 html 文本文件，验证码是一张图片，需要再次向服务器发请求获取图片资源！**

![2 验证码功](/Users/chenjinhua/ziliao/JavaLearned/doc/44 JSP/22 验证码功能.png)



## 4.1 编写 ImageUtil 类

## 4.2 图片返回给浏览器

### 4.2.1 将图片返回给浏览器：

```java
/* 生成图片, 将图片发送给浏览器.*/ 
// 输出文件的类型可以在 tomcat 包里的 web.xml 里查找.
resp.setContentType("image/png");
// 将内容输出到浏览器需要一个流,之前输出的网页文本是字节流,现在图片需要字节流.
OutputStream os = resp.getOutputStream();
vCode.write(os); //  ImageUtil工具类提供的方法
os.close();
```

### 4.3.2 图片请求路径设置为访问 servlet：

```jsp
<td><img src="createCodeImg.do" alt="验证码" />点击更换</td>
```

### 4.3.3 点击更换验证码

不能刷新网页，因为刷新网页后用户名密码就丢失了，如下方式不可取：

```jsp
<td><img src="createCodeImg.do" alt="验证码"  onclick="location.href='createCodeImg.do'"/></td>
```

应该重置 img 标签的 src 属性的值

```jsp
<td><img src="createCodeImg.do" alt="验证码"  onclick="this.setAttribute('src','createCodeImg.do')"/></td>
```

更改为上述方法后发现点击验证码，验证码并没有更换。原因是：浏览器认为属性值并没有改动不会重新赋值，所以这里在请求后面加一个参数，值随机。

```jsp
<td><img src="createCodeImg.do" alt="验证码"  onclick="this.setAttribute('src','createCodeImg.do?x='+Math.random());"/></td>
```

### 4.3.4 校验验证码

失败时返回错误信息。

```java
/*
* 先校验验证码,因为如果验证码不对,就不需要读写数据库校验密码了.
* */
HttpSession session = req.getSession();
String code = (String) session.getAttribute("validateCode");
String pageCode = req.getParameter("pageCode");
if (! code.equalsIgnoreCase(pageCode)) {
    String error = "验证码错误,请重试";
    req.setAttribute("msg",error);
    req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req,resp);
    return;
}
```

