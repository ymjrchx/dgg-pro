<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>菜单管理</title>
    <c:import url="../header.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plug/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plug/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/privilege.css"/>
</head>
<body>
<div class="content">
    <div class="content-left">
        <ul id="leftTree" class="ztree"></ul>
    </div>
    <div class="content-right">
        <div class="panel">
            <div class="panel-title">菜单编辑</div>
            <div class="panel-body">
                <form id="menuForm" class="layui-form">
                    <input hidden="hidden" name="ancestorIds" id="ancestorIds" value="" style="display:none;"/>
                    <input hidden="hidden" name="menuId" id="menuId" value="${menuId}" style="display:none;"/>
                    <div class="layui-row layui-col-space10">
                        <div class="layui-col-xs6">
                            <div class="layui-form-item">
                                <label class="layui-form-label required">菜单名称：</label>
                                <div class="layui-input-block">
                                    <input type="text" id="menuName" name="menuName" placeholder="菜单名称" class="layui-input">
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-xs6">
                            <div class="layui-form-item">
                                <label class="layui-form-label">菜单地址：</label>
                                <div class="layui-input-block">
                                    <input type="text" id="menuUrl" name="menuUrl" placeholder="菜单地址" class="layui-input">
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-xs6">
                            <div class="layui-form-item">
                                <label class="layui-form-label">菜单图标：</label>
                                <div class="layui-input-block">
                                    <input type="text" id="icon" name="icon" placeholder="菜单图标" class="layui-input">
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-xs6">
                            <div class="layui-form-item">
                                <label class="layui-form-label required">菜单排序：</label>
                                <div class="layui-input-block">
                                    <input type="text" id="sortNum" name="sortNum" placeholder="菜单排序" class="layui-input" value="${sortNum}">
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-xs6">
                            <div class="layui-form-item">
                                <label class="layui-form-label required">菜单编号：</label>
                                <div class="layui-input-block">
                                    <input type="text" id="menuCode" name="menuCode" placeholder="菜单编号" class="layui-input">
                                    <label id="errorMenuCode" style="color: red"></label>
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-xs12">
                            <div class="layui-form-item">
                                <label class="layui-form-label">菜单备注：</label>
                                <div class="layui-input-block">
                                    <textarea id="remark" name="remark" class="layui-textarea"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="btn-box">
                        <button type="button" class="layui-btn layui-btn-normal" onclick="addMenu();">添加</button>
                        <button id="updateBtn" type="button" class="layui-btn layui-btn-disabled">修改</button>
                    </div>
                </form>
            </div>
        </div>
        <div class="content-form">
            <div class="layui-inline">
                <label>菜单名称：</label>
                <input id="menuNameSearch" type="text" placeholder="菜单名称" />
            </div>
            <div class="layui-inline">
                <label>菜单地址：</label>
                <input id="menuUrlSearch" type="text" placeholder="菜单地址" />
            </div>
            <div class="layui-inline">
                <button type="button" class="layui-btn layui-btn-normal" onclick="tableSearch();">搜索</button>
            </div>
        </div>
        <div class="panel">
            <div class="panel-title">菜单列表</div>
            <div class="panel-body">
                <button type="button" onclick="deleteBtn();" class="layui-btn layui-btn-mini layui-btn-danger">删除</button>
                <table class="layui-table " id="menuTable"></table>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/menu/menu.js"></script>
</body>
</html>

