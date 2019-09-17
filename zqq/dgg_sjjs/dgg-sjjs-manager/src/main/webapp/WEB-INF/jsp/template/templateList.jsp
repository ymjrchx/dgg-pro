<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" uri="/fblock" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>模板列表</title>
    <c:import url="../header.jsp"/>
</head>
<body>
<div class="body-main">
    <div class="main-title">
				<span class="bus-state">
					<font>模板列表</font>
				</span>
        <%--<div class="title-tab">
            <a href="javascript:void(0);" <c:if test="${status == all}"> class="active"</c:if>
               data-status="${all}">全部</a>

        </div>--%>
        <%--查询正常的模板--%>
        <input type="hidden" name="flag" value="1">
    </div>

    <div class="titleSearch">

        <div class="layui-inline">
            <label> 模板类型：</label>
            <div class="layui-input-inline select2-inline">
                <select name="templateType" class="select-style" tree-book-select="template_type">
                    <option value="">请选择</option>
                </select>

            </div>
        </div>

        <div class="layui-inline">
            <label>模板名称</label>
            <div class="layui-input-inline">
                <input name="templateName" type="text" class="input-style" maxlength="50"
                       placeholder="请输入模板名称">
            </div>
        </div>

        <div class="layui-inline">
            <label>文件名称</label>
            <div class="layui-input-inline">
                <input name="fileName" type="text" class="input-style" maxlength="50"
                       placeholder="请输入文件名称">
            </div>
        </div>

        <div class="layui-inline">
            <label> 状态：</label>
            <div class="layui-input-inline select2-inline">
                <select name="status" class="select-style" tree-book-select="status">
                    <option value="">请选择</option>
                </select>
            </div>
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="dataTableReload()">搜索</button>
            <button class="layui-btn layui-btn-mini layui-btn-primary" onclick="cleanSearch()">清空</button>
        </div>
        </form>
    </div>
    <div class="table-conter">
        <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="openAddPage()">添加</button>
    </div>
    <div class="table-body">
        <!--表格 start-->
        <table id="mytable" class="cell-border crm-table columnsHidden dataTable">
            <thead>
            <tr>
                <th width="20"><input type="checkbox" class="checkall"/></th>
                <th width="50">序号</th>
                <th>模板ID</th>
                <th>模板类型</th>
                <th>模板名称</th>
                <th>文件名称</th>
                <th>文件详情</th>
                <th>状态</th>
                <th width="200">操作</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
        <!--表格 end-->
    </div>


</div>


<c:import url="/WEB-INF/jsp/footer.jsp"/>

<script src="${basePath}/static/js/tree_book_select.js?v=${version}" type="text/javascript"></script>

<script type="text/javascript">

    var table = null;

    function dataTableReload() {
        table.ajax.reload();
    }

    // 弹出添加页面
    function openAddPage() {
        DGG.Layer.open("${pageContext.request.contextPath}/template/addPage.html", {
            title: '添加模板',
            area: ['50%', '50%'],
            btn: ''
        });
    }


    String.prototype.endWith = function (endStr) {
        var d = this.length - endStr.length;
        return (d >= 0 && this.lastIndexOf(endStr) == d)
    }

    // 弹出模板加载页面
    function seeDetail(url) {
        DGG.Layer.open(url, {
            title: '模板详情',
            area: ['75%', '75%'],
            btn: ''
        });
    }


    // 弹出修改页面
    function openUpdatePage(id) {
        //alert(id);
        DGG.Layer.open("${pageContext.request.contextPath}/template/updatePage.html?id=" + id, {
            title: '修改模板',
            area: ['40%', '40%'],
            btn: null
        });
    }

    function deleteById(id) {
        DGG.Layer.confirm("删除【ID为" + id + "】的模板，删除后不能恢复，确认是否删除？", function () {
            DGG.Layer.loading.start();
            $.ajax({
                url: "${pageContext.request.contextPath}/template/delete.do", //处理的页面
                data: {
                    id: id
                },
                type: "POST",  //提交方式
                success: function (obj) { //回调函数，data为形参，是从页面返回的值
                    DGG.Layer.loading.done();
                    if (obj.code != 0) {
                        DGG.Layer.message(obj.msg, {icon: '2'}, function () {
                        });
                    } else {
                        DGG.Layer.message('操作成功！', {icon: '2'}, function () {
                            dataTableReload();
                            window.parent.layer.closeAll();
                        });
                    }
                }
            });
        })

    }

    // 获取搜索数据
    function getSearchData() {
        return $.extend({}, $('.titleSearch').serializeJson(), $('.main-title').serializeJson());
    }

    $(document).ready(function () {
        //初始化dataTable列表
        //初始化dataTable列表
        table = $('#mytable').dataTableExtend({
            "ajax": {"url": "${pageContext.request.contextPath}/template/queryPage.do", type: "post"},
            "fnServerParams": function (aoData) {
                aoData = $.extend(aoData, getSearchData());
                return aoData;
            },

            "scrollX": "true",//是否开启横项滚动
            "scrollY": "true",
            "fixedColumns": {//锁定列，只有开启scrollX时才生效
                "leftColumns": 3,//左侧锁定9列
                "rightColumns": 1//右侧锁定1列
            },
            "aoColumns": [
                {
                    "orderable": false,
                    "data": "id",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  name="ids" class="checkchild"  value="' + data + '" />';
                    }
                }, {
                    "orderable": false,
                    "render": function (data, type, full, meta) {
                        return meta.row + 1;
                    }
                },{
                    "orderable": false,
                    "data": "id",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "templateType",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "templateName",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "fileName",
                    "render": function (data, type, full, meta) {
                        return (data ? '<a class=line2>' + data + '</a>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "fileUrl",
                    "render": function (data, type, full, meta) {
                        if (data.endWith(".jpg") || data.endWith(".png")) {
                            var result = "";
                            result += '<a href=\'javascript:void(0)\' ti  onclick="seeDetail(\'' + data + '\')" /><span class="layui-table-link">查看图片详情</span></a>&nbsp;';
                            return result;
                        } else {
                            return data ? '<a class=line2 href=' + data + '>查看文件详情</a>' : '';
                        }

                    }
                },
                {
                    "orderable": false,
                    "data": "status",
                    "render": function (data, type, full, meta) {
                        return data ? '<p class=line2>' + data + '</p>' : '';
                    }
                },
                {
                    "orderable": false,
                    "data": "id",
                    "render": function (data, type, r, meta) {
                        var result = "";
                        result += '<a href=\'javascript:void(0)\' ti  onclick="openUpdatePage(\'' + r.id + '\')" /><span class="layui-table-link">修改</span></a>&nbsp;';
                        result += '<a href=\'javascript:void(0)\' ti  onclick="deleteById(\'' + r.id + '\',\'' + r.name + '\')" /><span class="layui-table-link">删除</span></a>&nbsp;';
                        return result;

                    }
                }],
            "columnDefs": [{
                targets: [4], //第1，2，3列
                createdCell: function (td, cellData, rowData, row, col) {

                }
            }]
        });


    });

</script>
</body>

</html>