<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<c:import url="/WEB-INF/jsp/header.jsp"/>
<head>
    <meta charset="UTF-8">
    <title>用户管理</title>
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
        <div class="table-title">
            <div class="layui-inline">
                <button class="layui-btn layui-btn-xs" onclick="showPopUp(this)" style="display: none" id="addRoles">
                    添加角色
                </button>
                <button class="layui-btn layui-btn-danger layui-btn-xs" onclick="showPopUp(this)" style="display: none"
                        id="delRoles">移除角色
                </button>
            </div>
            <div class="content-form fr">
                <div class="layui-inline">
                    <select id="searchValue">
                        <option value="3">当前部门及子部门</option>
                        <option value="1">当前部门</option>
                        <option value="2">全部部门</option>
                    </select>
                    <input id="userKeyWordInput" type="text" placeholder="请输入人员名称或工号"/>
                    <button class="layui-btn layui-btn-normal" onclick="userSearch()">搜索</button>
                </div>
            </div>
        </div>

        <table class="layui-hide" id="userTable" lay-filter="dataTableFilter"/>

        <script type="text/html" id="dataTableFilter">
            <a class="layui-btn layui-btn-normal" lay-event="editRole">管理角色</a>
        </script>
    </div>
</div>
<div class="layer-content" id="userAllot">
    <div class="user-allot">
        <div class="allot-left allot-left02">
            <div class="menu-up-left">未分配</div>
            <div class="allot-search allot-search02">
                <input type="text" placeholder="请输入角色名称" id="inputAllRoleList"/>
            </div>
            <ul class="user-list" id="allRoleList"></ul>
        </div>
        <div class="allot-mid">
            <a href="javascript:void(0)" class="addUser" onclick="moveUser(this)"><i class="layui-icon">&#xe602;</i></a>
            <a href="javascript:void(0)" class="removeUser" onclick="moveUser(this)"><i class="layui-icon">&#xe603;</i></a>
            <a href="javascript:void(0)" class="addAll" onclick="moveUser(this)"><i class="layui-icon">&#xe65b;</i></a>
            <a href="javascript:void(0)" class="removeAll" onclick="moveUser(this)"><i
                    class="layui-icon">&#xe65a;</i></a>
        </div>
        <div class="allot-right allot-right02">
            <div class="menu-up-left">已分配</div>
            <ul class="user-list" id="ownedRoleList"></ul>
        </div>
    </div>
</div>
<div class="layer-content" id="roleAllot">
    <div class="role-allot" id="role">
        <div class="allot-search">
            <input type="text" placeholder="请输入角色名称" id="inputAllRoles"/>
        </div>
        <ul class="user-list" id="allRoles"></ul>
    </div>
</div>
<c:import url="/WEB-INF/jsp/footer.jsp"/>
<script type="text/html" id="roleTpl">
    {{#  if(d.roleName != null){ }}
    <span >{{d.roleName}}</span>
    {{#  } else { }}
    <span>未分配</span>
    {{#  } }}
</script>
<script type="text/html" id="lockedTpl">
    {{#  if(d.locked == 0){ }}
    <span style="color: #66CD00;">正常</span>
    {{#  } else if (d.locked == 1){ }}
    <span style="color: #FF3030;">锁定</span>
    {{#  } else if (d.locked == 2){ }}
    <span style="color: #C1C1C1;">离职</span>
    {{#  } }}
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-2.1.1.min.js"
        charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plug/layui/layui.all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dgg.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/extend/layer.extend.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/permission/user.js"></script>
<script type="text/html" id="indexTpl">{{d.LAY_TABLE_INDEX+1}}</script>
</body>
</html>
