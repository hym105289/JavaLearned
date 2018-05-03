<%--
  Created by IntelliJ IDEA.
  User: jinhua.chen
  Date: 2018/4/30
  Time: 上午9:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <p>你的 BMI 指数是: ${result}</p>
    <c:if test="${result < 19.0}">
        <p>身体状况: 过轻</p>
    </c:if>



    <c:if test="${result > 24.0}">
        <p>身体状况: 过重</p>
    </c:if>

    <c:if test="${19.0 <= result && result<= 24.0}">
        <p>身体状况: 正常</p>
    </c:if>

</body>
</html>
