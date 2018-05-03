<%--
  Created by IntelliJ IDEA.
  User: jinhua.chen
  Date: 2018/4/23
  Time: 下午4:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="index_navi">
    <ul id="menu">
        <%--导航栏 menu.jsp 被很多其他 JSP 所引用，即在很多网页被使用，所以无法事先确定请求的来源，适合写绝对路径。--%>
        <li><a href="index.do" class="index_on"></a></li>
        <li><a href="role/role_list.html" class="role_off"></a></li>
        <li><a href="admin/admin_list.html" class="admin_off"></a></li>
        <li><a href="findCost.do" class="fee_off"></a></li>
        <li><a href="account/account_list.html" class="account_off"></a></li>
        <li><a href="service/service_list.html" class="service_off"></a></li>
        <li><a href="bill/bill_list.html" class="bill_off"></a></li>
        <li><a href="report/report_list.html" class="report_off"></a></li>
        <li><a href="user/user_info.html" class="information_off"></a></li>
        <li><a href="user/user_modi_pwd.html" class="password_off"></a></li>
    </ul>
</div>
</body>
</html>
