<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" uri="/fblock" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>修改模板</title>
    <c:import url="../header.jsp"/>
    <style>
        .titleSearch .input-style, .titleSearch .select-style {
            min-width: 180px;
            max-width: 500px;

        }

        .titleSearch .layui-inline label {
            min-width: 100px;
            display: inline-block;
            text-align: right;
        }

        .table-body .layui-inline label {
            min-width: 30px;
        }

        .table-body td {
            padding-top: 5px;;
            padding-bottom: 5px;
        }

        .layui-inline label {
            padding-left: 5px;
        }
    </style>
</head>
<body>

<div class="body-main">
    <div class="main-title">
				<span class="bus-state">
					<font>修改模板</font>
				</span>
        <div class="title-tab">
            <a href="javascript:void(0);" <c:if test="${status == all}"> class="active"</c:if>
               data-status="${all}">填入基本修改信息 (已上传的模板内容不可修改 只能删除后重新上传) </a>
        </div>
    </div>

    <div class="titleSearch" style="border-bottom-width: 0px;">
        <table class="table" id="templateDataTable" style="width: 100%;">

            <tr>
                <td colspan="8">
                    <div class="layui-inline">
                        <label class="required"> 模板类型：</label>
                        <input type="hidden" name="id" value="${template.id}">
                        <div class="layui-input-inline select2-inline">
                            <select style="width: 500px" id="templateType" name="templateType" class="select-style"
                                    tree-book-select="template_type" data-value="${template.templateType}">
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                </td>
            </tr>

            <tr>
                <td colspan="8">
                    <div class="layui-inline">
                        <label class="required">模板名称：</label>
                        <div class="layui-input-inline">
                            <input style="width: 500px" id="templateName" name="templateName" type="text"
                                   class="input-style" maxlength="50" min="0" value="${template.templateName}">
                        </div>
                    </div>
                </td>
            </tr>

            <tr>
                <td colspan="8">
                    <div class="layui-inline">
                        <label class="required"> 状态：</label>
                        <div class="layui-input-inline select2-inline">
                            <select style="width: 500px" id="status" name="status" class="select-style"
                                    tree-book-select="status" data-value="${template.status}">
                                <option value="">请选择</option>
                            </select>

                        </div>
                    </div>
                </td>
            </tr>


        </table>
    </div>


    <%--模板--%>
    <div style="height:50px">
        <div class="table-conter" style="width: 100%;text-align: center;padding-top: 15px;">
            <div style="width: 180px;height: auto;margin-left: auto;margin-right: auto;">
                <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="addPage()" style="float: left;">保存
                </button>
                <button class="layui-btn layui-btn-mini layui-btn-primary" onclick="cancel()" style="float: right;">取消
                </button>
            </div>

        </div>
    </div>

</div>


<c:import url="/WEB-INF/jsp/footer.jsp"/>
<script src="${basePath}/static/js/tips.js?v=${version}" type="text/javascript"></script>
<script src="${basePath}/static/js/tree_book_select.js?v=${version}" type="text/javascript"></script>

<script type="text/javascript">

    function getSelectData() {
        return $('#mytable1').dataTableExtend('getCheck');
    }

    //模板表格
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
                }, {
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
                        return data ? '<a class=line2 href=' + data + '>查看文件详情</a>' : '';
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

<script type="text/javascript">

    var table = null;


    /**
     *删除模板行
     */
    function cancel() {
        window.parent.layer.closeAll();
    }


    //刷新考表格
    function dataTableReload2() {
        $('#mytable1').dataTableExtend("reloadFalse");
    }

    function dataTableReload() {
        table.ajax.reload();
    }

    function addPage() {
        //验证数据
        var templateType = $("#templateType").val();
        if (templateType == '') {
            DGG.Layer.message("模板类型不能为空", {icon: '2'}, function () {
            });
            return;
        }

        var templateName = $("#templateName").val();
        if (templateName == '') {
            DGG.Layer.message("模板名称不能为空", {icon: '2'}, function () {
            });
            return;
        }

        var status = $("#status").val();
        if (status == '') {
            DGG.Layer.message("状态不能为空", {icon: '2'}, function () {
            });
            return;
        }

        var templateDataTable = $("#templateDataTable").serializeJson();
        DGG.Layer.loading.start();
        $.ajax({
            url: "${pageContext.request.contextPath}/template/update.do", //处理的页面
            data: {
                data: JSON.stringify(templateDataTable)
            },
            type: "POST",  //提交方式
            success: function (obj) { //回调函数，data为形参，是从页面返回的值
                DGG.Layer.loading.done();
                if (obj.code != 0) {
                    DGG.Layer.message(obj.msg, {icon: '2'}, function () {
                    });
                } else {
                    DGG.Layer.message('操作成功！', {icon: '2'}, function () {
                        window.parent.dataTableReload();
                        window.parent.layer.closeAll();
                    });
                }
            }
        });


    }


</script>

</body>

</html>
