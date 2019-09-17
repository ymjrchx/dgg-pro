<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>数据字典</title>
    <c:import url="../header.jsp"/>
</head>
<body>
<div class="body-main">
    <div class="content_wrap">
        <div class="content-left">
            <ul id="leftTree" class="ztree"></ul>
        </div>
        <div class="content-right">
            <div class="main-title">
                <span>数据字典管理</span>
            </div>
            <div class="buttons" style="padding: 5px;">
                <a href="javascript:addNode(0);" class="layui-btn layui-btn-mini">新增[根]</a>
                <a href="javascript:addNode(1);" class="layui-btn layui-btn-mini">新增[子]</a>
                <a href="javascript:cacheUpdate();" class="layui-btn layui-btn-mini layui-btn-danger">更新缓存</a>
            </div>
            <form id="form">
                <table class="add-table">
                    <tbody>
                    <tr>
                        <th><span class="form-label required">上级字典：</span></th>
                        <td>
                            <input type="text" name="pName" value="" placeholder="上级字典" readonly="" class="input-style">
                        </td>
                        <th><span class="form-label required">字典编码：</span></th>
                        <td>
                            <input type="text" name="code" value="" placeholder="字典编码" class="input-style">
                        </td>
                        <th><span class="form-label required">字典名称：</span></th>
                        <td>
                            <input type="text" name="name" value="" placeholder="字典名称" class="input-style">
                        </td>
                    </tr>
                    <tr>
                        <th><span class="form-label">扩展字段1：</span></th>
                        <td><input type="text" name="ext1" value="" placeholder="扩展字段1" class="input-style"></td>
                        <th><span class="form-label">扩展字段2：</span></th>
                        <td><input type="text" name="ext2" value="" placeholder="扩展字段2" class="input-style"></td>
                        <th><span class="form-label">扩展字段3：</span></th>
                        <td><input type="text" name="ext3" value="" placeholder="扩展字段3" class="input-style"></td>
                    </tr>
                    <tr>
                        <th><span class="form-label">扩展字段4：</span></th>
                        <td><input type="text" name="ext4" value="" placeholder="扩展字段4" class="input-style"></td>
                        <th><span class="form-label">扩展字段5：</span></th>
                        <td><input type="text" name="ext5" value="" placeholder="扩展字段5" class="input-style"></td>
                        <th><span class="form-label">排序：</span></th>
                        <td><input type="number" name="sort" value="0" placeholder="排序" class="input-style"></td>
                    </tr>
                    <tr>
                        <th><span class="form-label required">状态：</span></th>
                        <td>
                            <select class="select-style" name="status" data-placeholder="状态">
                                <option value="1">启用</option>
                                <option value="0">禁用</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="form-label">描述：</span></th>
                        <td colspan="3"><textarea type="text" id="description" name="description" class="textarea-style"
                                                  style="height: 60px;"></textarea></td>
                    </tr>
                    <tr>
                        <th></th>
                        <td>
                            <input type="hidden" name="id" value="">
                            <input type="hidden" name="pid" value="">
                            <button type="button" class="layui-btn layui-btn-mini" id="sb_btn" onclick="saveSub();">保存
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
            <div class="finan-panel" id="bookTableDiv" style="display: none;">
                <div class="finan-tit">字典列表</div>
                <div class="panel-table">
                    <table class="layui-table " id="bookTable" lay-filter="bookTableEvent"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="../footer.jsp"/>
<script type="text/javascript" src="${basePath}/static/js/tree_book/tree_book.js?v=${version}"></script>
</body>
</html>

