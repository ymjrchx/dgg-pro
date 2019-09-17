<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>操作权限</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plug/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plug/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/privilege.css"/>
</head>
<body>
<div class="content">
    <div class="content-left">
        <div class="allot-search">
            <input type="text" id="permsNameSearch" placeholder="请输入权限名称" value="${operatePermissionName}"/>
        </div>
        <ul id="role_ul" class="role-list">
            <c:forEach var="operatePermission" items="${operatePermissions}">
                <li><a href="/role/opratepermission/index.html?operatePermissionId=${operatePermission.operatePermissionId}"
                       <c:if test="${operatePermission.operatePermissionId== operatePermissionId}">class="active"</c:if> >${operatePermission.operatePermissionName}(${operatePermission.code})</a></li>
            </c:forEach>
        </ul>
    </div>
    <div class="content-right">
        <div class="panel">
            <div class="panel-title">权限属性</div>
            <div class="panel-body ">
                <form class="layui-form">
                    <div class="layui-form-item">
                        <label class="layui-form-label required">权限名称：</label>
                        <div class="layui-input-block">
                            <input type="hidden" name="operatePermissionId" value="${operatePermission.operatePermissionId}">
                            <input type="text" id="operatePermissionName" name="operatePermissionName" placeholder="权限名称" class="layui-input" value="${operatePermission.operatePermissionName}">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label required">权限编号：</label>
                        <div class="layui-input-block">
                            <input type="text" id="code" placeholder="权限编号" name="code" class="layui-input" value="${operatePermission.code}">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">权限备注：</label>
                        <div class="layui-input-block">
                            <textarea id="remark" class="layui-textarea" name="remark" >${operatePermission.remark}</textarea>
                        </div>
                    </div>
                    <div class="form-btns">
                        <a class="layui-btn layui-btn-normal" lay-filter="addFilter" lay-submit><i
                                class="layui-icon" >&#xe654;</i>新增权限</a>
                        <a <c:if test="${empty operatePermission.operatePermissionId}">class="layui-btn layui-btn-disabled"</c:if>
                           <c:if test="${not empty operatePermission.operatePermissionId}">
                               class="layui-btn layui-btn-danger" lay-filter="delFilter" lay-submit
                           </c:if>
                           ><i class="layui-icon" >&#xe640;</i>删除权限</a>
                        <a <c:if test="${empty operatePermission.operatePermissionId}">class="layui-btn layui-btn-disabled"</c:if>
                                <c:if test="${not empty operatePermission.operatePermissionId}">
                                    class="layui-btn layui-btn-green" lay-filter="updateFilter" lay-submit
                                </c:if>
                           >保存修改</a>
                    </div>

                </form>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/permission/operate_permission.js"></script>
</body>
</html>