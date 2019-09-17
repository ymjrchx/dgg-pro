<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>数据字典</title>
    <c:import url="../header.jsp"/>
</head>
<body onload="bodyLoad()">
<div class="body-main">
    <div class="content_wrap">
        <div class="content-left">
            <ul id="leftTree" class="ztree"></ul>
        </div>
        <div class="content-right">
            <div class="main-title">
                <span>分类管理</span>
            </div>
            <div class="main-title">
                <span id="className"></span>
            </div>
            <a href="javascript:cacheUpdate();" class="layui-btn layui-btn-mini layui-btn-danger">更新前台分类缓存</a>
            <form id="form">

                <table class="add-table">
                    <tbody>
                    <tr>
                        <th><span class="form-label required">选择Action_type：</span></th>
                        <td>
                            <select class="select-style" id="actionType" name="actionType" onchange="actionTypeChange(this)">
                                <option>请选择</option>
                            </select>
                            <input type="hidden" name="typeValue" />
                        </td>
                    </tr>
                    <tr name="optionsLabelTr" style="display:none;"  >
                        <th>label名称：</th>
                        <td>
                            <input class="layui-input" name="" data-name="labelName" style="width: 300px" type="text"  maxlength="100" placeholder="请输入label的值，如7到30天"/>
                        </td>
                    </tr>
                    <tr name="optionsValueTr" style="display:none;">
                        <th>value值：</th>
                        <td>
                            <input class="layui-input" name="value" data-name="value" style="width: 300px" type="text" maxlength="100" placeholder="请输入label值所对应的字符串，如{gte: 7, lt: 30}}"/>
                        </td>
                    </tr>
                    <tr id="addOptionsTr" style="display:none;">
                        <th></th>
                        <td>
                            <input type="button" class="layui-btn" onclick="addTr()" value="+添加一个属性"/>
                        </td>
                    </tr>

                    <tr id="inputTr" style="display:none;">
                        <th><span class="form-label required">选择Input_type：</span></th>
                        <td>
                            <select class="select-style" id="inputType" name="" data-name="inputType" onchange="inputTypeChange(this)">
                                <option value="">请选择</option>

                            </select>
                        </td>
                    </tr>
                    <tr id="attrTr" style="display:none;">
                        <th><span class="form-label">属性控制：</span></th>
                        <td>
                            <span data-name="placeholder">提示信息</span><input class="layui-input" type="text" name="" data-name="placeholder"/>

                            <span data-name="max">最大(数字)</span><input class="layui-input" type="number" min="1" maxlength="5" name="" data-name="max"/>
                            <span data-name="min">最小(数字)</span><input class="layui-input" type="number" min="1"  maxlength="5" name="" data-name="min"/>

                            <span data-name="maxKeywordLength">最大关键词长度</span><input class="layui-input" type="number" min="1" maxlength="5" name="" data-name="maxKeywordLength"/>
                            <span data-name="maxLength">最大长度</span><input class="layui-input" type="number" min="1" maxlength="5" name="" data-name="maxLength"/>
                        </td>
                    </tr>
                    <tr id="dataTr" style="display:none;">
                        <th><span class="form-label">数据来源：</span></th>
                        <td>
                            <select class="select-style" id="optionsFrom" name="" data-name="optionsFrom">
                                <option value="">请选择</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th></th>
                        <td>
                            <input type="hidden" name="treebookId" />
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
<c:import url="footer.jsp"/>
<script type="text/javascript" src="${basePath}/static/js/condition/condition.js?v=${version}"></script>
</body>
</html>

