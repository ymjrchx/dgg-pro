<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>菜单权限</title>
    <c:import url="../header.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plug/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/privilege.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/station.css"/>
</head>
<body>
<div class="content">
    <div class="content-left">
        <ul id="leftTree" class="ztree"></ul>
    </div>
    <div class="fixed-content">
        <div class="menu-table table-left">
            <div class="allot-search">
                <input id="menuOperateName" type="text" placeholder="请输入权限名称"/>
                <input hidden="hidden" id="menuId" value="${menuId}" style="display:none;"/>
                <button onclick="tableSearch(0);" class="layui-btn layui-btn-normal">搜索</button>
            </div>
            <div class="layui-form menu-table-body">
                <h3 class="table-panel-tit">菜单绑定权限列表</h3>
                <table id="menu_operate_table" class="layui-table layui-table-mini">
                </table>
            </div>
        </div>
        <div class="menu-mid">
            <div class="menu-mid-main">
                <a class="addUser" onclick="move();"><i class="layui-icon">&#xe602;</i></a>
                <a class="removeUser" onclick="save();"><i class="layui-icon">&#xe603;</i></a>
                <%--<a href="javascript:void(0)" class="addAll" onclick="moveAll();"><i class="layui-icon">&#xe65b;</i></a>
                <a href="javascript:void(0)" class="removeAll" onclick="saveAll();"><i class="layui-icon">&#xe65a;</i></a>--%>
            </div>
        </div>
        <div class="menu-table table-right">
            <div class="allot-search">
                <input id="operateName" type="text" placeholder="请输入权限名称"/>
                <button onclick="tableSearch(1)" class="layui-btn layui-btn-normal">搜索</button>
            </div>
            <div class="layui-form menu-table-body">
                <h3 class="table-panel-tit">菜单未绑定权限列表</h3>
                <table id="operate_table" class="layui-table layui-table-mini"></table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-2.1.1.min.js"
        charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plug/layui/layui.all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dgg.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/extend/layer.extend.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/permission/menu_operate.js"></script>
</body>
</html>

