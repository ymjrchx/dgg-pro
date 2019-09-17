<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" uri="/fblock" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>添加问题</title>
    <c:import url="../header.jsp"/>
    <style>
        .titleSearch .input-style, .titleSearch .select-style {
            min-width: 180px;
            max-width: 300px;

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
					<font>添加问题</font>
				</span>
        <div class="title-tab">
            <a href="javascript:void(0);" <c:if test="${status == all}"> class="active"</c:if>
               data-status="${all}">填入添加信息</a>
        </div>
    </div>

    <div class="titleSearch" style="border-bottom-width: 0px;">
        <%--<form method="post",action="">--%>
            <table class="table" id="questionDataTable" style="width: 100%;">

                <tr>
                    <td colspan="4">
                        <div class="layui-inline">
                            <label class="required">问题概述：</label>
                            <div class="layui-input-inline" style="width: 800px;">
                                <textarea id="question" name="question" type="text" class="input-style" maxlength="100"
                                          style="height: 80px;width:inherit;min-width:800px;resize: none;"
                                          placeholder="请输入问题概述(低于100字节)"></textarea>
                            </div>
                        </div>
                    </td>
                </tr>

                <tr>
                    <td colspan="4">
                        <div class="layui-inline">
                            <label class="required">问题补充：</label>
                            <div class="layui-input-inline" style="width: 800px;">
                                <textarea id="questionAppend" name="questionAppend" type="text" class="input-style" maxlength="300"
                                          style="height: 120px;width:inherit;min-width:800px;resize: none;"
                                          placeholder="请输入问题补充(低于300字节)"></textarea>
                            </div>
                        </div>
                    </td>
                </tr>

                <tr>
                    <td colspan="4">
                        <div class="layui-inline">
                            <label class="required">关键字：</label>
                            <div class="layui-input-inline">
                                <input id="keyWord" name="keyWord" type="text" class="input-style" maxlength="35"
                                       min="0" style="width: 300px" placeholder="请输入问题关键字(低于35字节)">
                            </div>
                        </div>
                    </td>
                </tr>

                <tr>
                    <td colspan="4">
                        <div class="layui-inline">
                            <label> 创建方式：</label>
                            <div class="layui-input-inline select2-inline">
                                <select name="createWay" class="select-style" style="width: 300px">
                                    <option value="">请选择</option>
                                    <option value="1">内部创建</option>
                                    <option value="2">外部创建</option>
                                </select>
                            </div>
                        </div>
                    </td>
                </tr>

                <tr>
                    <td colspan="4">
                        <div class="layui-inline">
                            <label> 是否推荐：</label>
                            <div class="layui-input-inline select2-inline">
                                <select name="recommend" class="select-style" style="width: 300px">
                                    <option value="">请选择</option>
                                    <option value="0">是</option>
                                    <option value="1">否</option>
                                </select>
                            </div>
                        </div>
                    </td>
                </tr>


                <tr>
                    <td colspan="4">
                        <div class="layui-inline">
                            <label class="required">所属三级类别：</label>
                            <div class="layui-input-inline">
                                <select id="typeLevel1Code" name="typeLevel1Code" class="select-style" three-level-select="navigation_data">
                                    <option value="">请选择</option>
                                </select>
                                <select id="typeLevel2Code" name="typeLevel2Code" class="select-style">
                                    <option value="">请选择</option>
                                </select>
                                <select id="typeLevel3Code" name="typeLevel3Code" class="select-style">
                                    <option value="">请选择</option>
                                </select>
                            </div>
                        </div>
                    </td>
                </tr>

            </table>
        <%--</form>--%>
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
<script src="${basePath}/static/js/navigation.js?v=${version}" type="text/javascript"></script>

<script type="text/javascript">

    function getSelectData() {
        return $('#mytable1').dataTableExtend('getCheck');
    }

   //问题表格
    $(document).ready(function () {
        //初始化dataTable列表
        //初始化dataTable列表
        table = $('#mytable1').dataTableExtend({
            "ajax": {"url": "${pageContext.request.contextPath}/question/queryPage.do", type: "post"},
            "fnServerParams": function (aoData) {
                aoData = $.extend(aoData, {status: '${ENABLE}'});
                return aoData;
            },

            "scrollX": "true",//是否开启横项滚动
            "scrollY": "true",
            "fixedColumns": {//锁定列，只有开启scrollX时才生效
                "leftColumns": 3,//左侧锁定9列
                "rightColumns": 0//右侧锁定1列
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
                },

                {
                    "orderable": false,
                    "data": "typeLevel1Code",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "typeLevel1Name",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "typeLevel3Code",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line2>' + data + '</p>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "typeLevel3Name",
                    "render": function (data, type, full, meta) {
                        return data ? '<p class=line2>' + data + '</p>' : '';
                    }
                },
                {
                    "orderable": false,
                    "data": "question",
                    "render": function (data, type, full, meta) {
                        return data ? '<p class=line2>' + data + '</p>' : '';
                    }
                },
                {
                    "orderable": false,
                    "data": "questionAppend",
                    "render": function (data, type, full, meta) {
                        return data ? '<p class=line2>' + data + '</p>' : '';
                    }
                },
                {
                    "orderable": false,
                    "data": "keyWord",
                    "render": function (data, type, full, meta) {
                        return data ? '<p class=line2>' + data + '</p>' : '';
                    }
                },
                {
                    "orderable": false,
                    "data": "bestAnswerId",
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
        var question = $("#question").val();
        if(question==''){
            DGG.Layer.message("问题概述不能为空", {icon: '2'}, function () {});
            return;
        }

        var questionAppend = $("#questionAppend").val();
        if(questionAppend==''){
            DGG.Layer.message("问题描述不能为空", {icon: '2'}, function () {});
            return;
        }

        var keyWord = $("#keyWord").val();
        if(keyWord==''){
            DGG.Layer.message("关键字不能为空", {icon: '2'}, function () {});
            return;
        }

        var typeLevel1Code = $("#typeLevel1Code").val();
        if(typeLevel1Code==''){
            DGG.Layer.message("请选择一级分类", {icon: '2'}, function () {});
            return;
        }

        var typeLevel2Code = $("#typeLevel2Code").val();
        if(typeLevel2Code==''){
            DGG.Layer.message("请选择二级分类", {icon: '2'}, function () {});
            return;
        }
        var typeLevel3Code = $("#typeLevel3Code").val();
        if(typeLevel3Code==''){
            DGG.Layer.message("请选择三级分类", {icon: '2'}, function () {});
            return;
        }


        var questionDataTable = $("#questionDataTable").serializeJson();
        //DGG.Layer.loading.start();
        $.ajax({
            url: "${pageContext.request.contextPath}/question/save.do", //处理的页面
            data: {
                data: JSON.stringify(questionDataTable)
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
