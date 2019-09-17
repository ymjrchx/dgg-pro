<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" uri="/fblock" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>封禁列表</title>
    <c:import url="../header.jsp"/>
</head>
<script>
    var USER = '${USER}', IP = '${IP}';
</script>
<body>
<div class="body-main">
    <div class="main-title">
				<span class="bus-state">
					<font>封禁列表</font>
				</span>
        <div class="title-tab">
            <a href="javascript:void(0);" <c:if test="${status == all}"> class="active"</c:if>
               data-status="${all}">全部</a>

        </div>
        <input type="hidden" name="flag" value="1">
    </div>

    <div class="titleSearch">

        <div class="layui-inline">
            <label> 类型：</label>
            <div class="layui-input-inline select2-inline">
                <select name="type" class="select-style" tree-book-select="forbid_type">
                    <option value="">请选择</option>
                </select>

            </div>
        </div>


        <div class="layui-inline">
            <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="dataTableReload()">搜索</button>
            <button class="layui-btn layui-btn-mini layui-btn-primary" onclick="cleanSearch()">清空</button>
        </div>
        <%--</form>--%>
    </div>
    <div class="table-conter">

    </div>
    <div class="table-body">
        <!--表格 start-->
        <table id="mytable" class="cell-border crm-table columnsHidden dataTable">
            <thead>
            <tr>
                <th width="20"><input type="checkbox" class="checkall"/></th>
                <th width="50">序号</th>
                <th width="">ip或用户</th>
                <th width="">访问次数/每分</th>
                <th width="">类型</th>
                <th>创建时间</th>
                <th width="120px">操作</th>
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

    //刷新考表格
    function dataTableReload2() {
        $('#myTable').dataTableExtend("reloadFalse");
    }

    function dataTableReload() {
        table.ajax.reload();
    }


    function cancelForbid(id, type, name) {
        var msg = null;
        switch (type) {
            case USER:
                msg = "取消封禁后，此客户【" + name + "】后将可以登录确认是否继续？"
                break;
            case IP:
                msg = "取消封禁后，此IP【" + name + "】后将可以访问确认是否继续？"
                break;
        }
        DGG.Layer.confirm(msg, function () {
            DGG.Layer.loading.start();
            $.ajax({
                url: "${pageContext.request.contextPath}/forbidRecord/cancelForbid.do", //处理的页面
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
            "ajax": {"url": "${pageContext.request.contextPath}/forbidRecord/queryPage.do", type: "post"},
            "fnServerParams": function (aoData) {
                aoData = $.extend(aoData, getSearchData());
                return aoData;
            },

            "scrollX": "true",//是否开启横项滚动
            "scrollY": "true",
            "fixedColumns": {//锁定列，只有开启scrollX时才生效
                "leftColumns": 4,//左侧锁定9列
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
                    "data": "ipOrUserId",
                    "render": function (data, type, full, meta) {
                        return full.userName ? full.userName : data;
                    }
                }, {
                    "orderable": false,
                    "data": "times",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "typeName",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "createTime",
                    "render": function (data, type, full, meta) {
                        return (data != null ? '<p class=line2>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "id",
                    "render": function (data, type, r, meta) {
                        var result = "";

                        //result += '<a href=\'javascript:void(0)\' ti  onclick="openUpdatePage(\'' + r.id + '\')" /><span class="layui-table-link" style="white-space: nowrap;">修改</span></a>&nbsp;';

                        result += '<a href=\'javascript:void(0)\' ti  onclick="cancelForbid(\'' + r.id + '\',\'' + r.type + '\',\'' + (r.userName ? r.userName : r.ipOrUserId) + '\')" /><span class="layui-table-link" style="white-space: nowrap;">取消封禁</span></a>&nbsp;';

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