<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>行业分类</title>
    <c:import url="../header.jsp"/>
</head>
<body>
<div class="body-main">
    <div class="content_wrap">
        <div class="content-left">
            <div class="allot-search">
                <%--<input type="text" id="permsNameSearch" placeholder="请输入行业名称" />--%>
                <br>
            </div>
            <ul id="role_ul" class="role-list">
                <c:forEach var="industry" items="${industrys}">
                    <li><a href="javascript:void(0)" data-id="${industry.id}" data-sort="${industry.sort}" onclick="clickIndustry(this)">${industry.applicableIndustryName}</a></li>
                </c:forEach>
            </ul>
        </div>
        <%--<a href="/role/opratepermission/index.html?operatePermissionId=${operatePermission.operatePermissionId}"
                       <c:if test="${operatePermission.operatePermissionId== operatePermissionId}">class="active"</c:if> >${operatePermission.operatePermissionName}(${operatePermission.code})</a>--%>
        <div class="content-right">
            <div class="main-title">
                <span>行业分类</span>
            </div>

            <form id="form">
                <input type="hidden" name="id"/>
                <table class="add-table">
                    <tbody>
                    <tr>
                        <th><span class="form-label required">行业分类名称：</span></th>
                        <td>
                            <input type="text" name="applicableIndustryName" value="" maxlength="100" placeholder="行业分类名称"  class="input-style">
                        </td>
                        <th><span class="form-label required">排序：</span></th>
                        <td>
                            <input type="number" min="0" max="999" name="sort" placeholder="排序"  class="input-style" value="0">
                        </td>
                    </tr>
                    <tr>
                        <th></th>
                        <td>
                            <button type="button" class="layui-btn layui-btn-mini" id="sb_btn" onclick="saveSub();">新增
                            </button>
                            <button type="button" class="layui-btn layui-btn-mini"  onclick="modifySub();">修改
                            </button>
                            <button type="button" class="layui-btn layui-btn-mini"  onclick="deleteSub();">删除
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>

        </div>
    </div>
</div>
<c:import url="../footer.jsp"/>
<script type="text/javascript" src="${basePath}/static/js/industrymodel/industrymodel.js?v=${version}"></script>
</body>
</html>

