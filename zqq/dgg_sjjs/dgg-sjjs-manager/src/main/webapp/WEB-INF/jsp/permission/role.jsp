<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>角色管理</title>
    <c:import url="../header.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plug/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plug/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css"/>--%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/privilege.css"/>
</head>
<body>
<div class="content">
    <div class="content-left role-left">
        <div class="allot-search">
            <input id="roleSearch" type="text" placeholder="请输入角色名称" value="${roleNameSearch}"/>
        </div>
        <ul id="role_ul" class="role-list">
            <c:forEach var="role" items="${roles}">
                <li><a href="/role/role_manager/index.html?roleId=${role.roleId}&roleName=${roleNameSearch}"
                       <c:if test="${role.roleId== roleId}">class="active"</c:if> >${role.roleName}</a></li>
            </c:forEach>
        </ul>
    </div>
    <div class="role-content">
        <div class="role-main role-main-left">
            <div class="role-main-space add-main-space02">
                <div class="panel">
                    <div class="panel-title">角色属性</div>
                    <div class="panel-body">
                        <form id="role_form" class="layui-form">
                            <input hidden="hidden" style="display:none;" id="roleId" name="roleId"
                                    <c:if test="${!empty roleForm }"> value="${roleForm.roleId}"</c:if> />
                            <div class="layui-form-item">
                                <label class="layui-form-label required">角色名称：</label>
                                <div class="layui-input-block">
                                    <input id="roleNameForm" name="roleName" type="text" placeholder="角色名称"
                                           class="layui-input"
                                            <c:if test="${!empty roleForm }"> value="${roleForm.roleName}"</c:if> />
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">角色备注：</label>
                                <div class="layui-input-block">
                                    <textarea id="roleRemakForm" name="remark" class="layui-textarea"><c:if
                                            test="${!empty roleForm }">${roleForm.remark}</c:if></textarea>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="panel pos-warp">
                    <div class="panel-title border-top">独立操作权限</div>
                    <div class="panel-body search-panel">
                        <div class="allot-search">
                            <input id="operatePermissionSearch" type="text"
                                   placeholder="请输入权限名称" <%--lay-filter="operatePermissionFileter"--%>/>
                        </div>
                        <div class="out-privilege-warp">
                            <div id="operatePermissionDiv" class="out-privilege layui-form add-out-privilege02">
                                <c:forEach var="operatePermission" items="${operatePermissions}">
                                    <p><input type="checkbox" id="operatePermission" name="operatePermission"
                                              lay-skin="primary"

                                    <c:if test="${operatePermission.roleOpm==1}"> checked="checked"</c:if>
                                              value="${operatePermission.operatePermissionId}"
                                              title="${operatePermission.operatePermissionName}(${operatePermission.code})">
                                            <%--<div class="layui-unselect layui-form-checkbox" lay-skin="primary"><span>监控测试(3)</span><i class="layui-icon"></i></div>--%>
                                    </p>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <div class="role-main role-main-right">
            <div class="role-main-space role-line role-line02">
                <div class="panel">
                    <div class="panel-title">菜单权限</div>
                    <div class="panel-body">
                        <div class="menu-privilege margin-10">
                            <div class="privil-ztree privil-ztree02">
                                <ul id="menuTree" class="ztree add-ztree"></ul>
                            </div>
                            <div class="privil-table layui-form privil-table02">
                                <table id="operate_table" class="layui-table layui-table-mini">
                                </table>
                            </div>
                            <div class="role-btn role-btn02">
                                <a href="javascript:void(0);"
                                        <c:if test="${!empty roleForm.roleId}">
                                            class="layui-btn layui-btn-green" onclick="saveRolePms();"
                                        </c:if>
                                        <c:if test="${empty roleForm.roleId}">
                                            class="layui-btn layui-btn-disabled"
                                        </c:if>
                                >保存</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--<div class="role-btn role-btn02">--%>
        <%--<a href="javascript:void(0);"--%>
        <%--<c:if test="${!empty roleForm.roleId}">--%>
        <%--class="layui-btn layui-btn-green" onclick="saveRolePms();"--%>
        <%--</c:if>--%>
        <%--<c:if test="${empty roleForm.roleId}">--%>
        <%--class="layui-btn layui-btn-disabled"--%>
        <%--</c:if>--%>
        <%-->保存</a>--%>
        <%--</div>--%>
        <div class="role-allBtns">
            <a type="button" onclick="addRole();" class="layui-btn layui-btn-normal">添加角色</a>

            <a type="button"
                    <c:if test="${!empty roleForm.roleId}">
                        onclick="updateRoleEnable();" class="layui-btn layui-btn-danger"
                    </c:if>
                    <c:if test="${empty roleForm.roleId}"> class="layui-btn layui-btn-disabled"</c:if>>
                <c:if test="${!empty roleForm and roleForm.enable==true}"> 禁用角色</c:if>
                <c:if test="${!empty roleForm and roleForm.enable==false}"> 启用角色</c:if>
                <c:if test="${empty roleForm }"> 启用/禁用角色</c:if>
                <c:if test="${!empty roleForm }"><input hidden="hidden" id="roleEnableId" style="display:none;"
                                                        value="${roleForm.roleId}"/></c:if>
            </a>
            <a type="button" href="javascript:void(0);"
                    <c:if test="${empty roleForm.roleId}"> class="layui-btn layui-btn-disabled"</c:if>
                    <c:if test="${!empty roleForm.roleId}"> onclick="updateRole();" class="layui-btn layui-btn-green"</c:if>>保存修改</a>


        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-2.1.1.min.js"
        charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plug/layui/layui.all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dgg.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/extend/layer.extend.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/permission/role.js"></script>
</body>
</html>

