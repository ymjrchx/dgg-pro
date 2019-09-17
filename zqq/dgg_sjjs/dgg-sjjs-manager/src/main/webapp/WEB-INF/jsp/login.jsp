<%--
  用户登录页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
    <c:import url="header.jsp"/>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css"/>--%>
</head>
<body id="body">
<script language="JavaScript">
    document.onkeydown = function (event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if (e && e.keyCode == 13) {
            $('#loginBtn').click();
        }
    };
</script>
<div class="dgg-logo">
    <img src="${pageContext.request.contextPath}/static/images/login-logo.png">
</div>
<div class="login-box">
    <div class="login-bg"><img src="${pageContext.request.contextPath}/static/images/login1.jpg"></div>
</div>

<div class="login-body newLogin-body">
    <div class="login-top">

    </div>
    <form id="loginForm" name="loginForm">
        <div class="login-newForm">
            <%--<label id="userNameError" style="color: red"></label>--%>
            <label id="spanMsg"></label>
            <div class="form-box userName">
                <input type="text" id="userName" name="userName" class="in" placeholder="请输入账号" autofocus/>
            </div>
            <div class="form-box password">
                <input type="password" maxlength="100" class="in" name="password" id="password" placeholder="请输入密码"/>
            </div>
            <div class="login-bottom">
                <button id="loginBtn" type="button" class="layui-btn" onclick="pageIndex.onLogin()">登录</button>
            </div>
            <%--<div class="form-box userName">
                <input type="text" id="userName" name="userName" class="in" placeholder="请输入账号"/>
                <label id="userNameError" style="color: red"></label>
            </div>
            <div class="form-box password">
                <input type="password" maxlength="100" class="in" name="password" id="password" placeholder="请输入密码"/>
                <label id="passwordError" style="color: red"></label>
            </div>
            <div class="login-bottom">
                <button type="button" class="layui-btn" onclick="pageIndex.onLogin()">登录</button>
            </div>--%>
            <!--<p class="logo-p">请使用Google最新版本浏览器登录</p>-->
        </div>
    </form>
</div>
<div class="login-footer">
    <img src="${pageContext.request.contextPath}/static/images/icon5.png">
</div>
</body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-2.1.1.min.js"
        charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plug/layui/layui.all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dgg.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.js"></script>
<script src="${pageContext.request.contextPath}/static/js/index/login.js?v=${version}" type="text/javascript"
        charset="utf-8"></script>
