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
					<font>问题列表</font>
				</span>
        <%--<div class="title-tab">
            <a href="javascript:void(0);" <c:if test="${status == all}"> class="active"</c:if>
               data-status="${all}">全部</a>

        </div>--%>
        <%--查询正常的问题--%>
        <input type="hidden" name="flag" value="1">
    </div>

    <div class="titleSearch">

        <div class="layui-inline">
            <label>问题关键字</label>
            <div class="layui-input-inline">
                <input id="keyWord" name="keyWord" type="text" class="input-style" maxlength="50"
                       placeholder="请输入问题关键字">
            </div>
        </div>

        <div class="layui-inline">
            <label>问题编号</label>
            <div class="layui-input-inline">
                <input id="number" name="number" type="text" class="input-style" maxlength="50"
                       placeholder="请输入问题编号">
            </div>
        </div>

        <div class="layui-inline">
            <label>问题概述</label>
            <div class="layui-input-inline">
                <input id="question" name="question" type="text" class="input-style" maxlength="50"
                       placeholder="请输入问题概述">
            </div>
        </div>

        <div class="layui-inline">

        <div class="layui-inline">
            <label>创建方式：</label>
            <div class="layui-input-inline">
                <select id="createWay" name="createWay" class="select-style">
                    <option value="">请选择</option>
                    <option value="1">内部创建</option>
                    <option value="2">外部创建</option>
                </select>
            </div>
        </div>

        <div class="layui-inline">
            <label>是否推荐：</label>
            <div class="layui-input-inline">
                <select id="recommend" name="recommend" class="select-style">
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
                <th>编号</th>
                <th>服务一级大类</th>
                <th>服务二级大类</th>
                <th>服务三级大类</th>
                <th>创建时间</th>
                <th>创建人</th>
                <th>问题概述</th>
                <th>问题补充</th>
                <th>关键字</th>
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
            $('#keyWord').val('');
            $('#number').val('');
            $('#question').val('');
            $('#createWay').val('');
            $('#recommend').val('');
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

    // 弹出添加页面
    function openAddPage() {
        DGG.Layer.open("${pageContext.request.contextPath}/question/addPage.html", {
            title: '添加问题',
            area: ['90%', '80%'],
            btn: ''
        });
    }

    // 弹出修改页面
    function openUpdatePage(id) {
        //alert(id);
        DGG.Layer.open("${pageContext.request.contextPath}/question/updatePage.html?id=" + id, {
            title: '修改问题',
            area: ['90%', '80%'],
            btn: null
        });
    }

    //弹出问题关联页面
    function openAnswerPage(id) {
        DGG.Layer.open("${pageContext.request.contextPath}/answer/openAnswerPage.html?id=" + id, {
            title: '问题关联答案',
            area: ['90%', '80%'],
            btn: null
        });
    }

    function deleteById(id) {
        DGG.Layer.confirm("删除问题【ID为" + id + "】 ,将同时删除该问题下的答案，确认是否删除？", function () {
            DGG.Layer.loading.start();
            $.ajax({
                url: "${pageContext.request.contextPath}/question/delete.do", //处理的页面
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
            "ajax": {"url": "${pageContext.request.contextPath}/question/queryPage.do", type: "post"},
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
                    "data": "number",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
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
                    "data": "typeLevel2Code",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "typeLevel3Code",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "createTime",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line2>' + data + '</p>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "createrName",
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
                    "data": "id",
                    "render": function (data, type, r, meta) {
                        var result = "";
                        result += '<a href=\'javascript:void(0)\' ti  onclick="openUpdatePage(\'' + r.id + '\')" /><span class="layui-table-link">修改</span></a>&nbsp;';
                        result += '<a href=\'javascript:void(0)\' ti  onclick="openAnswerPage(\'' + r.id + '\')" /><span class="layui-table-link">关联答案</span></a>&nbsp;';
                        /* result += '<a href=\'javascript:void(0)\' ti  onclick="deleteById(\'' + r.id + '\',\'' + r.name + '\')" /><span class="layui-table-link">删除</span></a>&nbsp;';*/

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