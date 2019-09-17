<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="/fblock" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>订单管理</title>
    <c:import url="../header.jsp"/>
</head>
<body>
<div class="body-main">
    <div class="main-title">
				<span class="bus-state">
					<font>订单管理</font>
				</span>
        <div class="title-tab">
            <a href="javascript:void(0);" <c:if test="${resultArr == all}"> class="active"</c:if>
               data-status="${all}">全部</a>
        </div>
        <input type="hidden" name="resultArr" value="${resultArr}">
    </div>

    <div class="titleSearch">
        <div class="layui-inline">
            <label>订单编号</label>
            <div class="layui-input-inline">
                <input name="orderNo" type="text" class="input-style" maxlength="50"
                       placeholder="请输入订单编号">
            </div>
        </div>
        <div class="layui-inline">
            <label>用户名</label>
            <div class="layui-input-inline">
                <input name="username" type="text" class="input-style" maxlength="50"
                       placeholder="请输入用户名">
            </div>
        </div>

        <%--<div class="layui-inline">
            <label class="layui-input-inline">所属三级类别：</label>
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
            <label> 订单类型：</label>
            <div class="layui-input-inline select2-inline">
                <select name="type" class="select-style" >
                    <option value="">请选择</option>
                    <option value="order_type1">普通订单</option>
                    <option value="order_type2">智能注册订单</option>
                </select>
            </div>
        </div>--%>
        <div class="layui-inline">
            <label> 订单状态：</label>
            <div class="layui-input-inline select2-inline">
                <select name="status" class="select-style">
                    <option value="">请选择</option>
                    <option value="order_status_obligation">未付款</option>
                    <option value="order_status_spend">已付款</option>
                    <option value="order_status_inprocess">办理中</option>
                    <option value="order_status_completed">已完成</option>
                    <option value="order_status_evaluate">已评价</option>
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
                <th>订单编号</th>
                <th>用户名</th>
                <th>服务名称</th>
                <th>服务所属一级类别</th>
                <th>服务所属三级类别</th>
                <th>服务属性名</th>
                <th>购买件数</th>
                <th>总价(元)</th>
                <th>联系人</th>
                <th>联系电话</th>
                <th>订单类型</th>
                <th>报件编号</th>
                <th>报件状态</th>
                <th>订单状态</th>
                <th>操作</th>
            </tr>
            </thead>
        </table>
        <!--表格 end-->
    </div>


</div>


<c:import url="/WEB-INF/jsp/footer.jsp"/>
<script src="${basePath}/static/js/navigation.js?v=${version}" type="text/javascript"></script>

<script src="${basePath}/static/js/tree_book_select.js?v=${version}" type="text/javascript"></script>
<script>
    var PAYED = '${PAYED}', IN_PROCESS = '${IN_PROCESS}', WAITING_AUDIT = '${WAITING_AUDIT}',
        IN_PROCESS = '${IN_PROCESS}';

    // 发送报件
    //修改
    function sendBaojian(id) {
        DGG.Layer.open("${pageContext.request.contextPath}/orderDisplay/baojianOrder.html?id=" + id, {
            title: '发送报件',
            area: ['65%', '95%'],
            btn: '',
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

    //修改
    function updateStatus(id, status) {
        DGG.Layer.open("${pageContext.request.contextPath}/orderDisplay/changePage.html?id=" + id + "&status=" + status, {
            title: '修改订单状态',
            area: ['250px', '195px'],
            btn: '',
        });
    }

    //评估
    function assess(id) {
        DGG.Layer.open("${pageContext.request.contextPath}/orderDisplay/assessOrder.html?id=" + id, {
            title: '订单评估',
            area: ['65%', '95%'],
            btn: '',
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
            "ajax": {"url": "${pageContext.request.contextPath}/orderDisplay/znQueryPage.do", type: "post"},
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
                    "data": "orderNo",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line2>' + data + '</p>' : '');
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
                    "data": "serviceName",
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
                    "data": "serviceAttrName",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line2>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "allNum",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "allPrice",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "contactName",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "contactPhone",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "type",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "baoJianNo",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "baoJianStatus",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "statusName",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "id",
                    "render": function (data, type, r, meta) {
                        var result = "";
                        if (PAYED == r.status || IN_PROCESS == r.status) {
                            result += '<a id="a1"  href=\'javascript:void(0)\' ti  onclick="updateStatus(\'' + r.id + '\',\'' + r.statusName + '\')" /><span class="layui-table-link" style="display: inline-block;word-break: normal;white-space: nowrap">修改状态</span></a>&nbsp;';
                        }
                        if (PAYED == r.status || IN_PROCESS == r.status) {
                            result += '<a   href=\'javascript:void(0)\' ti  onclick="sendBaojian(\'' + r.id + '\')" /><span class="layui-table-link"style="display: inline-block;word-break: normal;white-space: nowrap">报件</span></a>&nbsp;';
                        }
                        if (WAITING_AUDIT == r.status) {
                            result += '<a id="a1"  href=\'javascript:void(0)\' ti  onclick="assess(\'' + r.id + '\')" /><span class="layui-table-link" style="display: inline-block;word-break: normal;white-space: nowrap">评估</span></a>&nbsp;';
                        }

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