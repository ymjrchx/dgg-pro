<%--
  Created by IntelliJ IDEA.
  User: wu
  Date: 2018-02-22
  Time: 11:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>500</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/error.css"/>
</head>
<body>
<%--${code}${msg}--%>
<div class="error-warp">
    <div class="error-404">
        <div class="error-txt">${errorMsg}</div>
        <img src="/static/images/error/authority.png"/>
    </div>
</div>
</body>
</html>
