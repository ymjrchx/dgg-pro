<%--
  Created by IntelliJ IDEA.
  User: wu
  Date: 2018-02-22
  Time: 11:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>没有权限</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/error.css"/>
</head>
<body>
<div class="error-warp">
    <div class="error-404">
        <!--<div class="error-txt">guagua~服务器内部错误，崩溃啦！</div>-->
        <img src="/static/images/error/authority.png"/>
        <p>请联系系统管理员</p>
    </div>
</div>
</body>
</html>
