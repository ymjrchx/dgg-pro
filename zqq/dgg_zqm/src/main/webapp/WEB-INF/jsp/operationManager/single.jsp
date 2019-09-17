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

        <span class="bus-state">
					<font>用户名：${username}</font>
				</span>
    </div>
    <div class="titleSearch">

        <div class="layui-inline">
            <div class="layui-input-inline">
                <input name="userId" type="text" value="${userId}" style="display:none;">
            </div>
        </div>

    </div>

    <div class="table-body">
        <!--表格 start-->
        <table id="mytable" class="cell-border crm-table columnsHidden dataTable">
            <thead>
            <tr>
                <th width="20"><input type="checkbox" class="checkall"/></th>
                <th width="50">序号</th>
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