<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" uri="/fblock" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>问题列表</title>
    <c:import url="../header.jsp"/>
</head>
<body>
<div class="body-main">
    <div class="main-title">
				<span class="bus-state">
					<font>商标咨询列表</font>
				</span>
        <%--<div class="title-tab">
            <a href="javascript:void(0);" <c:if test="${status == all}"> class="active"</c:if>
               data-status="${all}">全部</a>

        </div>--%>

    </div>

    <div class="titleSearch">

        <div class="layui-inline">
            <label>商标名称：</label>
            <div class="layui-input-inline">
                <input id="trademarkerName" name="trademarkerName" type="text" class="input-style" maxlength="50"
                       placeholder="请输入问题关键字">
            </div>
        </div>

        <div class="layui-inline">
            <label>商标类型：</label>
            <div class="layui-input-inline">
                <input id="trademarkerType" name="trademarkerType" type="text" class="input-style" maxlength="50"
                       placeholder="请输入问题编号">
            </div>
        </div>

        <div class="layui-inline">
            <label>称呼：</label>
            <div class="layui-input-inline">
                <input id="userName" name="userName" type="text" class="input-style" maxlength="50"
                       placeholder="请输入问题概述">
            </div>
        </div>

        <div class="layui-inline">
            <label>联系号码：</label>
            <div class="layui-input-inline">
                <input id="phone" name="phone" type="text" class="input-style" maxlength="50"
                       placeholder="请输入问题概述">
            </div>
        </div>

        <div class="layui-inline">
            <label> 意向价格：</label>
            <div class="layui-input-inline select2-inline">
                <select id="intentionalPrice" name="intentionalPrice" class="select-style"
                        tree-book-select="intentional_price">
                    <option value="">请选择</option>
                </select>
            </div>
        </div>

        <div class="layui-inline">
            <label>是否已经联系：</label>
            <div class="layui-input-inline">
                <select id="flag" name="flag" class="select-style">
                    <option value="">请选择</option>
                    <option value="0">是</option>
                    <option value="1">否</option>
                </select>
            </div>
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="dataTableReload()">搜索</button>
            <button class="layui-btn layui-btn-mini layui-btn-primary" onclick="cleanSearch1()">清空</button>
        </div>
    </div>

    <%--<div class="table-conter">
        <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="openAddPage()">添加</button>
    </div>--%>


    <div class="table-body">
        <!--表格 start-->
        <table id="mytable" class="cell-border crm-table columnsHidden dataTable">
            <thead>
            <tr>
                <th width="50">序号</th>
                <th>商标名称</th>
                <th>商标类型</th>
                <th width="100">称呼</th>
                <th width="100">联系号码</th>
                <th width="65">邮箱</th>
                <th>意向价格</th>
                <th>需求描述</th>
                <th>申请号</th>
                <th width="50">是否联系</th>
                <th width="150">操作</th>
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
<script src="${basePath}/static/js/navigation.js?v=${version}" type="text/javascript"></script>

<script type="text/javascript">
    function cleanSearch1() {
        $('#trademarkerName').val('');
        $('#trademarkerType').val('');
        $('#userName').val('');
        $('#phone').val('');
        $('#intentionalPrice').val('');
        $('#flag').val('');
    }


    function htmlEscape(text) {
        return text.replace(/[<>"&]/g, function (match, pos, originalText) {
            switch (match) {
                case "<":
                    return "&lt;";
                case ">":
                    return "&gt;";
                case "&":
                    return "&amp;";
                case "\"":
                    return "&quot;";
            }
        });
    }
</script>

<script type="text/javascript">

    var table = null;

    //刷新考表格
    function dataTableReload2() {
        $('#myTable').dataTableExtend("reloadFalse");
    }

    function dataTableReload() {
        table.ajax.reload();
    }


    function deleteById(id, flag) {
        DGG.Layer.confirm("确定要将该商标咨询转换状态吗？", function () {
            DGG.Layer.loading.start();
            $.ajax({
                url: "${pageContext.request.contextPath}/trademarkConsult/delete.do", //处理的页面
                data: {
                    id: id,
                    flag: flag
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
            "ajax": {"url": "${pageContext.request.contextPath}/trademarkConsult/queryPage.do", type: "post"},
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
                    "render": function (data, type, full, meta) {
                        return meta.row + 1;
                    }
                }, {
                    "orderable": false,
                    "data": "trademarkerName",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "trademarkerType",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "userName",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + htmlEscape(data) + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "phone",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "email",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line2>' + data + '</p>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "intentionalPrice",
                    "render": function (data, type, full, meta) {
                        return data ? '<p class=line2>' + data + '</p>' : '';
                    }
                },
                {
                    "orderable": false,
                    "data": "requirementDesc",
                    "render": function (data, type, full, meta) {
                        return data ? '<p class=line2>' + htmlEscape(data) + '</p>' : '';
                    }
                },
                {
                    "orderable": false,
                    "data": "applicationNum",
                    "render": function (data, type, full, meta) {
                        return data ? '<p class=line2>' + data + '</p>' : '';
                    }
                },
                {
                    "orderable": false,
                    "data": "flag",
                    "render": function (data, type, full, meta) {
                        if (data == 0) {
                            return '已联系';
                        }
                        if (data == 1) {
                            return '未联系';
                        }
                    }
                },
                {
                    "orderable": false,
                    "data": "id",
                    "render": function (data, type, r, meta) {
                        var result = "";
                        if (r.flag == 0) {
                            result += '<a href=\'javascript:void(0)\' ti  onclick="deleteById(\'' + r.id + '\',\'' + r.flag + '\')" /><span class="layui-table-link">转为未联系</span></a>&nbsp;';
                        }
                        if (r.flag == 1) {
                            result += '<a href=\'javascript:void(0)\' ti  onclick="deleteById(\'' + r.id + '\',\'' + r.flag + '\')" /><span class="layui-table-link">转为已联系</span></a>&nbsp;';
                        }

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