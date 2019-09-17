<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${siteName}</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/plug/easyui-1.3.5/themes/default/easyui.css?v=${version}"/>
    <!--引入公共样式文件-->
    <c:import url="header.jsp"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main-tabs.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/record_warp.css"/>
</head>
<body class="body">
<div id="dgg-head">
    <h1 class="head-logo"></h1>
    <div class="head-nav">
        <%--加载一级菜单列表--%>
        <div class="add-div-width">
            <c:forEach items="${menus}" var="vo" varStatus="vs">
                <c:if test="${vs.index == 1}">
                    <input id="indexMenu" type="hidden" value="${vo.menuId}">
                </c:if>
                <a href="javascript:void(0);" onclick="loadSecMenu('${vo.menuId}')">${vo.menuName}</a>

            </c:forEach>
        </div>
        <div class="clickCheckMore01">
            <a href="javascript:void(0);" class="CheckMore">&#187</a>
            <div class="hidden-div" id="hidden-div02">
            </div>
        </div>
    </div>
    <%--</div>--%>
    <div class="head-main">
        <div class="users">
            <div class="user-top">
                <div class="user-pic"><img src="${pageContext.request.contextPath}/static/images/pic.png"/></div>
                <div class="user-message">
                    <p class="user-name">您好，${user.realName}</p>
                </div>
            </div>
        </div>
    </div>
    <div class="user-state">
        <div class="state-now">
            <span><i class="state-online"></i>在线</span>
            <font class="fa fa-chevron-down"></font>
        </div>
        <ul class="state-list" style="display: none;">
            <li><i class="state-online"></i>在线</li>
            <li onclick="onLogout();"><i class="state-leave"></i>退出</li>
        </ul>
    </div>
</div>
<!--菜单 start-->
<div class="left-menu">
    <ul id="secMenu" class="left-menu-list">

    </ul>
</div>
<!--菜单 end-->
<div class="easyui-tabs kjdms-content" id="iframe-warp" data-options="tools:'#easy-opt'"></div>
<div id="mm" class="easyui-menu" style="width:120px;">
    <div id="mm-tabclose" data-options="name:1">关闭</div>
    <div id="mm-tabcloseall" data-options="name:2">全部关闭</div>
    <div id="mm-tabcloseother" data-options="name:3">除此之外全部关闭</div>
    <div class="menu-sep"></div>
    <div id="mm-tabcloseright" data-options="name:4,iconCls:'icon-undo'">当前页右侧全部关闭</div>
    <div id="mm-tabcloseleft" data-options="name:5">当前页左侧全部关闭</div>
    <div class="menu-sep"></div>
    <div data-options="name:6">刷新</div>
</div>
<div id="easy-opt">
    <a href="javascript:void(0)" onclick="otherTab(this)" class="layui-icon" data-tools="#tabs">&#xe65f;</a>
</div>
<%--存放下拉菜单--%>
<ul class="more-tab" id="tabs"></ul>
<c:import url="footer.jsp"/>
<script src="${pageContext.request.contextPath}/static/plug/easyui-1.3.5/jquery.easyui.min.js?v=${version}"
        type="text/javascript"
        charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/main-tabs.js?v=${version}"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/index/index.js?v=${version}"></script>
</body>
</html>
