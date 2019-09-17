<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="/fblock" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>搜索词管理</title>
    <c:import url="../header.jsp"/>
</head>
<body>
<div class="body-main">
    <div class="main-title">
				<span class="bus-state">
					<font>搜索词管理</font>
				</span>
        <div class="title-tab">
            <a href="javascript:void(0);" <c:if test="${resultArr == all}"> class="active"</c:if>
               data-status="${all}">全部</a>
        </div>
    </div>

    <div class="titleSearch">
        <div class="layui-inline">
            <label>搜索词</label>
            <div class="layui-input-inline">
                <input name="word" type="text" class="input-style" maxlength="50"
                       placeholder="请输入搜索词">
            </div>
        </div>
        <div class="layui-inline">
            <label>创建时间：</label>
            <div class="layui-inline">
                <input type="text" name="createTimeStart" placeholder="开始时间" class="Wdate input-style" id="d4313"
                       onFocus="var d4314=$dp.$('d4314'); WdatePicker({onpicked:function(){d4314.focus();}, maxDate:'#F{ $dp.$D(\'d4314\')}', dateFmt:'yyyy-MM-dd'})">
            </div>
            <span>~</span>
            <div class="layui-inline">
                <input type="text" name="createTimeEnd" placeholder="结束时间" class="Wdate input-style" id="d4314"
                       onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4313\')}', dateFmt:'yyyy-MM-dd'})">
            </div>
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="dataTableReload()">搜索</button>
            <button class="layui-btn layui-btn-mini layui-btn-primary" onclick="cleanSearch()">清空</button>
        </div>

        <button class="layui-btn layui-btn-mini layui-btn-danger" style="position: absolute;right: 120px;" onclick="openTemplateAddress()">下载模板</button>

        <div class="layui-inline" style="position: absolute;right: 200px;">
            <form id="addFile">
                <label> 批量导入：</label>
                <input name="file" type="file" id="file"/>
                <input id="inportFile" type="button" class="layui-btn layui-btn-mini layui-btn-normal" value="上传" onclick="addFile()"/>
            </form>
        </div>
        <%--</form>--%>
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
                <th>搜索词</th>
                <th>次数</th>
                <th>创建时间</th>
                <th>是否展示</th>
                <th width="120">操作</th>
            </tr>
            </thead>
        </table>
    </div>


</div>


<c:import url="/WEB-INF/jsp/footer.jsp"/>
<script type="text/javascript">

    var fileUrl ="${fileUrl}";
    // 下载页面
    function openTemplateAddress() {
        window.location.href = fileUrl;
    }

    var table = null;

    //刷新考表格
    function dataTableReload2() {
        $('#myTable').dataTableExtend("reloadFalse");
    }

    function dataTableReload() {
        table.ajax.reload();
    }



    function addFile() {
        //验证数据
        var file = $("#file").val();
        if (file == null || file == '') {
            DGG.Layer.message('不能上传空文件！', {icon: '2'}, function () {})

            return;
        }

        var form = new FormData(document.getElementById("addFile"));
        DGG.Layer.loading.start();
        $.ajax({
            url:"${pageContext.request.contextPath}/searchWord/importData.do",
            type:"post",
            data:form,
            processData:false,
            contentType:false,
            success: function (obj) { //回调函数，data为形参，是从页面返回的值
                DGG.Layer.loading.done();
                if (obj.code != 0) {
                    DGG.Layer.message(obj.msg, {icon: '2'}, function () {
                    });
                } else {
                    DGG.Layer.message('操作成功！', {icon: '2'}, function () {
                        dataTableReload();
                    });
                }
            }
        });


    }

    function deleteById(id) {
        DGG.Layer.confirm("是否删除该搜索词？", function () {
            DGG.Layer.loading.start();
            $.ajax({
                url: "${pageContext.request.contextPath}/searchWord/removeWord.do", //处理的页面
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
                        });
                    }
                }
            });
        })

    }

    function updateById(id) {
        DGG.Layer.confirm("是否启用或禁用该搜索词？", function () {
            DGG.Layer.loading.start();
            $.ajax({
                url: "${pageContext.request.contextPath}/searchWord/updateFlag.do", //处理的页面
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
                        });
                    }
                }
            });
        })

    }

    // 弹出添加页面
    function openAddPage() {
        DGG.Layer.open("${pageContext.request.contextPath}/searchWord/addPage.html", {
            title: '添加搜索词',
            area: ['50%', '50%'],
            btn: ''
        });
    }


    // 获取搜索数据
    function getSearchData() {
        return $.extend({}, $('.titleSearch').serializeJson(), $('.main-title').serializeJson());
    }


    $(document).ready(function () {
        //初始化dataTable列表
        //初始化dataTable列表
        table = $('#mytable').dataTableExtend({
            "ajax": {"url": "${pageContext.request.contextPath}/searchWord/showWord.do", type: "post"},
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
                },

                {
                    "orderable": false,
                    "data": "word",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line2>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "time",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "createTime",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "flag",
                    "render": function (data, type, full, meta) {
                        return (data == 1 ? '<p class=line1>' + '是' + '</p>' : '<p class=line1>' + '否' + '</p>');
                    }
                },
                {
                    "orderable": false,
                    "data": "id",
                    "render": function (data, type, r, meta) {
                        var result = "";
                        result += '<a id="a1"  href=\'javascript:void(0)\' ti  onclick="deleteById(\'' + r.id + '\')" /><span class="layui-table-link">删除</span></a>&nbsp;';
                        if (r.flag == 1) {
                            result += '<a id="a1"  href=\'javascript:void(0)\' ti  onclick="updateById(\'' + r.id + '\')" /><span class="layui-table-link">禁用</span></a>&nbsp;';
                        } else result += '<a id="a1"  href=\'javascript:void(0)\' ti  onclick="updateById(\'' + r.id + '\')" /><span class="layui-table-link">启用</span></a>&nbsp;';

                        return result;
                    }
                }]
        });
    });

</script>
</body>

</html>