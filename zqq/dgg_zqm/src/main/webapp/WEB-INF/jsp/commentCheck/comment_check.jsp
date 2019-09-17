<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="/fblock" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>评论审核管理</title>
    <c:import url="../header.jsp"/>
</head>
<script>

</script>
<body>
<div class="body-main">
    <div class="main-title">
				<span class="bus-state">
					<font>评论审核管理</font>
				</span>
        <div class="title-tab">
            <a href="javascript:void(0);" <c:if test="${resultArr == all}"> class="active"</c:if>
               data-status="${all}">全部</a>
        </div>
        <input type="hidden" name="resultArr" value="${resultArr}">
    </div>

    <div class="titleSearch">
        <div class="layui-inline">
            <label>用户名</label>
            <div class="layui-input-inline">
                <input name="username" type="text" class="input-style" maxlength="50"
                       placeholder="请输入用户名">
            </div>
        </div>
        <div class="layui-inline">
            <label>订单编号</label>
            <div class="layui-input-inline">
                <input name="orderNo" type="text" class="input-style" maxlength="50"
                       placeholder="请输入订单编号">
            </div>
        </div>
        <div class="layui-inline">
            <label class="required">所属三级类别：</label>
            <div class="layui-input-inline">
                <select id="serviceTypeLevel1Code" name="serviceTypeLevel1Code" class="select-style" three-level-select="navigation_data">
                    <option value="">请选择</option>
                </select>
                <select id="serviceTypeLevel2Code" name="serviceTypeLevel2Code" class="select-style">
                    <option value="">请选择</option>
                </select>
                <select id="serviceTypeLevel3Code" name="serviceTypeLevel3Code" class="select-style">
                    <option value="">请选择</option>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label>星级</label>
            <div class="layui-input-inline">
                <input name="level" type="text" class="input-style" maxlength="50"
                       placeholder="请输入星级（1-5）">
            </div>
        </div>
            <div class="layui-inline">
                <label> 审核结果：</label>
                <div class="layui-input-inline select2-inline">
                    <select name="flag" class="select-style" >
                        <option value="">请选择</option>
                        <option value="1">有效</option>
                        <option value="0">无效</option>
                    </select>
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
                <th width="150">用户名</th>
                <th width="200">订单编号</th>
                <th width="200">服务所属一级类别</th>
                <th width="200">服务所属三级类别</th>
                <th width="50">星级</th>
                <th style="text-align: center" width="500">评论文字</th>
                <th width="50">审核结果</th>
                <th width="120">操作</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
        <!--表格 end-->
    </div>


</div>


<c:import url="/WEB-INF/jsp/footer.jsp"/>

<script src="${basePath}/static/js/navigation.js?v=${version}" type="text/javascript"></script>

<script src="${basePath}/static/js/tree_book_select.js?v=${version}" type="text/javascript"></script>
<script type="text/javascript">

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
    var table = null;

    //刷新考表格
    function dataTableReload2() {
        $('#myTable').dataTableExtend("reloadFalse");
    }

    function dataTableReload() {
        table.ajax.reload();
    }


    // 获取搜索数据
    function getSearchData() {
        return $.extend({}, $('.titleSearch').serializeJson(), $('.main-title').serializeJson());
    }

    //改变审核状态
    function openCheckPage(id) {
        var r = DGG.Layer.confirm('是否转换审核状态？', function (i) {
            if (i) {
                $.post("${pageContext.request.contextPath}/comment/changeFlag.do",
                    {
                        id: id
                    },
                    function (data, status) {
                        window.dataTableReload();
                    }
                );

            }
        });

    }

    $(document).ready(function () {
        //初始化dataTable列表
        //初始化dataTable列表
        table = $('#mytable').dataTableExtend({
            "ajax": {"url": "${pageContext.request.contextPath}/comment/queryPage.do", type: "post"},
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
                }, {
                    "orderable": false,
                    "data": "orderNo",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line2>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "serviceTypeLevel1Code",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "serviceTypeLevel3Code",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "level",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },{
                    "orderable": false,
                    "data": "content",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p style="width: 500px" class=line3>' + htmlEscape(data) + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "flag",
                    "render": function (data, type, full, meta) {
                            data=(data==1)?'有效':'无效';
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "id",
                    "render": function (data, type, r, meta) {
                        var result = "";

                        result += '<a href=\'javascript:void(0)\' ti  onclick="openCheckPage(\'' + r.id + '\')" /><span class="layui-table-link">审核</span></a>&nbsp;';

                        return result;

                    }
                }],
            "columnDefs": [{
                targets: [4], //第1，2，3列
                createdCell: function (td, cellData, rowData, row, col) {
                    var receiveTimeStatus = rowData.receiveTimeStatus;
                    var nodeTimeStatus = rowData.nodeTimeStatus;
                    var orderTimeStatus = rowData.orderTimeStatus;
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