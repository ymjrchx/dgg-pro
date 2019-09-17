<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" uri="/fblock" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>用户列表</title>
    <c:import url="../header.jsp"/>
</head>
<body>
<div class="body-main">
    <div class="main-title">
				<span class="bus-state">
					<font>用户列表</font>
				</span>
        <div class="title-tab">
            <a href="javascript:void(0);" <c:if test="${status == all}"> class="active"</c:if>
               data-status="${all}">全部</a>
        </div>
    </div>

    <div class="titleSearch">
        <div class="layui-inline">
            <label>用户名：</label>
            <div class="layui-input-inline">
                <input name="username" type="text" class="input-style" maxlength="50"
                       placeholder="请输入用户名" >
            </div>
        </div>
        <div class="layui-inline">
            <label>用户类型：</label>
            <div class="layui-input-inline select2-inline">
                <select name="type" class="select-style">
                    <option value="">请选择</option>
                    <option name="type" value="1">普通用户</option>
                    <option name="type" value="2"> VIP用户</option>
                    <option name="type" value="3">企业用户</option>
                </select>
            </div>
        </div>

        <div class="layui-inline">
            <label>用户状态：</label>
            <div class="layui-input-inline select2-inline">
                <select name="status" class="select-style">
                    <option value="">请选择</option>
                    <option name="status" value="status1">启用</option>
                    <option name="status" value="status2">禁用</option>
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
                <th>用户名</th>
                <th>创建时间</th>
                <th>昵称</th>
                <th>邮箱</th>
                <th>生日</th>
                <th>性别</th>
                <%--<th>用户类型</th>--%>
                <th>状态</th>
                <th width="120">操作</th>
            </tr>
            </thead>
        </table>

    </div>


</div>


<c:import url="/WEB-INF/jsp/footer.jsp"/>
<script src="${basePath}/static/js/navigation.js?v=${version}" type="text/javascript"></script>


<script type="text/javascript">

    var table = null;

    //刷新考表格
    function dataTableReload2() {
        $('#myTable').dataTableExtend("reloadFalse");
    }

    function dataTableReload() {
        table.ajax.reload();
    }


    function changeStatus(userId) {
        var r = DGG.Layer.confirm('是否更改用户状态？', function (i) {
            if (i) {
                $.post("${pageContext.request.contextPath}/userManager/changeStatus.do",
                    {
                        userId: userId
                    },
                    function (data, status) {
                        window.dataTableReload();
                    }
                );

            }
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
            "ajax": {"url": "${pageContext.request.contextPath}/userManager/queryPage.do", type: "post"},
            "fnServerParams": function (aoData) {
                aoData = $.extend(aoData, getSearchData());
                return aoData;
            },

            "scrollX": "true",//是否开启横项滚动
            "scrollY":"true",
            "fixedColumns": {//锁定列，只有开启scrollX时才生效
                "leftColumns": 3,//左侧锁定2列
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
                }, {
                    "orderable": false,
                    "data": "username",
                    "render": function (data, type, full, meta) {
                        return (data ? data : '');
                    }
                }, {
                    "orderable": false,
                    "data": "createtime",
                    "render": function (data, type, full, meta) {
                        return (data ? data : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "nickname",
                    "render": function (data, type, full, meta) {
                        return data ? data : '';
                    }
                },
                {
                    "orderable": false,
                    "data": "email",
                    "render": function (data, type, full, meta) {
                        return data ? data : '';
                    }
                },
                {
                    "orderable": false,
                    "data": "birthday",
                    "render": function (data, type, full, meta) {
                        return data ? data : '';
                    }
                },
                {
                    "orderable": false,
                    "data": "sex",
                    "render": function (data, type, full, meta) {
                        if (data == 1) {
                            data = "男";
                        }
                        else (data = '女')
                        return data;
                    }
                },
                /*{
                    "orderable": false,
                    "data": "type",
                    "render": function (data, type, full, meta) {
                        if (data==1){
                            data="普通用户";
                        }
                        else if (data==2){
                            data="vip用户";
                        }
                        else if (data==3){
                            data="企业用户"
                        }
                        else (data='')
                        return data;
                    }
                },*/
                {
                    "orderable": false,
                    "data": "status",
                    "render": function (data, type, full, meta) {
                        return data ? data : '';
                    }
                },
                {
                    "orderable": false,
                    "data": "userId",
                    "render": function (data, type, r, meta) {
                        var result = "";

                        result += '<a href=\'javascript:void(0)\' ti  onclick="changeStatus(\'' + r.userId + '\')" /><span class="layui-table-link">启用/禁用</span></a>&nbsp;';

                        return result;

                    }
                }]
        });


        $('.title-tab').on('click', 'a', null, function () {
            var $this = $(this);
            var status = $this.attr('data-status');
            var href = window.location.href;
            if (href.indexOf('status=') > -1) {
                href = href.substring(0, href.indexOf('status=') - 1);
            }
            window.location.href = href + (href.indexOf('?') > -1 ? '&' : '?') + 'status=' + status;
        });
    });


</script>
</body>

</html>