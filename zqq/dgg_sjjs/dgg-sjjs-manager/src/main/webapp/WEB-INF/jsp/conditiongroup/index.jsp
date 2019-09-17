<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>行业模板</title>
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
                <c:forEach var="group" items="${groups}">
                    <li><a href="javascript:void(0)" data-industryId="${group.industryId}" data-filter='${group.filter}' data-info="${group.info}" data-id="${group.id}" data-sort="${group.sort}" onclick="clickGroup(this)">${group.name}</a></li>
                </c:forEach>
                <c:if test="${groups.size() == 0}">暂无数据</c:if>
            </ul>
        </div>
        <div class="content-right">
            <div class="main-title">
                <span>行业模板</span>
            </div>
            <a href="javascript:cacheUpdate();" class="layui-btn layui-btn-mini layui-btn-danger">更新行业模板缓存</a>
            <a href="javascript:cacheUpdateCompany();" class="layui-btn layui-btn-mini layui-btn-danger">更新企业搜索缓存</a>
            <form id="form">
                <input type="hidden" name="id"/>
                <table class="add-table">
                    <tbody>
                    <tr>
                        <th><span class="form-label required">选择行业分类：</span></th>
                        <td>
                            <select class="layui-select" name="industryId">
                                <c:forEach var="industry" items="${industrys}">
                                    <option  value="${industry.id}"  data-sort="${industry.sort}">${industry.applicableIndustryName}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="form-label required">名称：</span></th>
                        <td>
                            <input type="text" name="name"  placeholder="名称" maxlength="100" class="input-style">
                        </td>
                    </tr>

                    <tr>
                        <th><span class="form-label required">具体条件：</span></th>
                        <td>
                            <input type="text" name="filter"  placeholder="具体条件"  class="input-style">
                        </td>
                    </tr>
                    <tr>
                        <th><span class="form-label ">提示信息：</span></th>
                        <td>
                            <input type="text" name="info"  placeholder="提示信息" maxlength="200"  class="input-style">
                        </td>
                    </tr>
                    <tr>
                        <th><span class="form-label required">排序：</span></th>
                        <td>
                            <input type="number" max="999" min="0" name="sort"  placeholder="排序"  class="input-style" value="0">
                        </td>
                    </tr>
                <%--    <tr>
                        <th><span class="form-label required">使用次数：</span></th>
                        <td>
                            <input type="text" name="usedCount"  placeholder="使用次数"  class="input-style" value="0">
                        </td>
                    </tr>--%>


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
<script type="text/javascript" src="${basePath}/static/js/conditiongroup/conditiongroup.js?v=${version}"></script>
</body>
</html>

