<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="/fblock" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>操作记录管理</title>
    <c:import url="../header.jsp"/>
</head>
<body>
<div class="body-main">
    <div class="main-title">
				<span class="bus-state">
					<font>操作记录管理</font>
				</span>
        <div class="title-tab">
            <a href="javascript:void(0);" <c:if test="${resultArr == all}"> class="active"</c:if>
               data-status="${all}">全部</a>
        </div>
    </div>

    <div class="titleSearch">
        <div class="layui-inline">
            <label> 用户操作：</label>
            <div class="layui-input-inline select2-inline">
                <select name="operationCode" class="select-style" tree-book-select="user_operation">
                    <option value="">请选择</option>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label>用户名：</label>
            <div class="layui-input-inline">
                <input name="username" type="text" class="input-style" maxlength="50"
                       placeholder="请输入用户名">
            </div>
        </div>
        <div class="layui-inline">
            <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="dataTableReload()">搜索</button>
            <button class="layui-btn layui-btn-mini layui-btn-primary" onclick="cleanSearch()">清空</button>
        </div>
        <%--</form>--%>
    </div>

    <div class="table-body">
        <!--表格 start-->
        <table id="mytable" class="cell-border crm-table columnsHidden dataTable">
            <thead>
            <tr>
                <th width="20"><input type="checkbox" class="checkall"/></th>
                <th width="50">序号</th>
                <th>用户名</th>
                <th>用户操作</th>
                <th>内容</th>
                <th>创建日期</th>
                <th width="120"></th>
            </tr>
            </thead>
        </table>
        <!--表格 end-->
    </div>


</div>


<c:import url="/WEB-INF/jsp/footer.jsp"/>
<script src="${basePath}/static/js/navigation.js?v=${version}" type="text/javascript"></script>
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

    //审核发票
    /*
        function changeStatusT(id) {
            var r = DGG.Layer.confirm('是否审核为成功？', function (i) {
                if (i) {
                    $.post("${pageContext.request.contextPath}/invoice/changeResultT.do",
                    {
                        id: id
                    },
                    function (data, status) {
                        window.dataTableReload();
                    }
                );
            }
        });

    }*/
    // 获取搜索数据
    function getSearchData() {
        return $.extend({}, $('.titleSearch').serializeJson(), $('.main-title').serializeJson());
    }


    $(document).ready(function () {
        //初始化dataTable列表
        //初始化dataTable列表
        table = $('#mytable').dataTableExtend({
            "ajax": {"url": "${pageContext.request.contextPath}/operation/showRecord.do", type: "post"},
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
                    "data": "userId",
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
                    "data": "username",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "operationCode",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "content",
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
                    "data": "id",
                    "render": function (data, type, r, meta) {
                        var result = "";
                        return result;

                    }
                }]
        });

        $('.title-tab').on('click', 'a', null, function () {
            var $this = $(this);
            var status = $this.attr('data-status');
            var href = window.location.href;
            if (href.indexOf('resultArr=') > -1) {
                href = href.substring(0, href.indexOf('resultArr=') - 1);
            }
            window.location.href = href + (href.indexOf('?') > -1 ? '&' : '?') + 'resultArr=' + status;
        });
    });

</script>
</body>

</html>