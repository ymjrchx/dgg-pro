<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" uri="/fblock" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>客留信息列表</title>
    <c:import url="../header.jsp"/>
</head>
<body>
<div class="body-main">
    <div class="main-title">
				<span class="bus-state">
					<font>客留信息列表</font>
				</span>
        <%--<div class="title-tab">
            <a href="javascript:void(0);" <c:if test="${status == all}"> class="active"</c:if>
               data-status="${all}">全部</a>

        </div>--%>
        <%--查询正常的模板--%>
    </div>
    <div class="titleSearch">

        <div class="layui-inline">
            <label>名称</label>
            <div class="layui-input-inline">
                <input name="name" type="text" class="input-style" maxlength="50"
                       placeholder="请名称">
            </div>
        </div>
        <div class="layui-inline">
            <label>电话</label>
            <div class="layui-input-inline">
                <input name="phone" type="text" class="input-style" maxlength="50"
                       placeholder="电话">
            </div>
        </div>

        <div class="layui-inline">
            <label>区域</label>
            <div class="layui-input-inline">
                <input name="area" type="text" class="input-style" maxlength="50"
                       placeholder="区域">
            </div>
        </div>

        <div class="layui-inline">
            <label>商标名字</label>
            <div class="layui-input-inline">
                <input name="trademarkName" type="text" class="input-style" maxlength="50"
                       placeholder="商标名字">
            </div>
        </div>

        <div class="layui-inline">
            <label>行业</label>
            <div class="layui-input-inline">
                <input name="industry" type="text" class="input-style" maxlength="50"
                       placeholder="行业">
            </div>
        </div>

        <div class="layui-inline">
            <label>用户状态：</label>
            <div class="layui-input-inline select2-inline">
                <select name="flag" class="select-style">
                    <option value="">请选择</option>
                    <option name="flag" value="-1">识别失败</option>
                    <option name="flag" value="0">无效</option>
                    <option name="flag" value="1">有效</option>
                </select>
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
        </form>
    </div>
    <div class="table-conter">
        <%--  <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="openAddPage()">添加</button>--%>
    </div>
    <div class="table-body">
        <!--表格 start-->
        <table id="mytable" class="cell-border crm-table columnsHidden dataTable">
            <thead>
            <tr>
                <th width="20"><input type="checkbox" class="checkall"/></th>
                <th width="50">序号</th>
                <th>名称</th>
                <th>电话</th>
                <th>行业</th>
                <th>商标名字</th>
                <th>区域</th>
                <th>状态</th>
                <th>创建时间</th>
                <th width="200">操作</th>
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

    function dataTableReload() {
        table.ajax.reload();
    }

    // 弹出添加页面
    function openAddPage() {
        DGG.Layer.open("${pageContext.request.contextPath}/template/addPage.html", {
            title: '添加模板',
            area: ['50%', '50%'],
            btn: ''
        });
    }


    String.prototype.endWith = function (endStr) {
        var d = this.length - endStr.length;
        return (d >= 0 && this.lastIndexOf(endStr) == d)
    }

    // 弹出模板加载页面
    function seeDetail(url) {
        DGG.Layer.open(url, {
            title: '模板详情',
            area: ['75%', '75%'],
            btn: ''
        });
    }


    // 弹出修改页面
    function openUpdatePage(id) {
        //alert(id);
        DGG.Layer.open("${pageContext.request.contextPath}/template/updatePage.html?id=" + id, {
            title: '修改模板',
            area: ['40%', '40%'],
            btn: null
        });
    }

    function deleteById(id) {
        DGG.Layer.confirm("删除【ID为" + id + "】的模板，删除后不能恢复，确认是否删除？", function () {
            DGG.Layer.loading.start();
            $.ajax({
                url: "${pageContext.request.contextPath}/template/delete.do", //处理的页面
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
            "ajax": {"url": "${pageContext.request.contextPath}/customerMsg/queryPage.do", type: "post"},
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
                    "data": "name",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "phone",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "industry",
                    "render": function (data, type, full, meta) {
                        return (data ? '<a class=line2>' + data + '</a>' : '注：客户主动留电，请尽快联系客户。');
                    }
                }, {
                    "orderable": false,
                    "data": "trademarkName",
                    "render": function (data, type, full, meta) {
                        return (data ? '<a class=line2>' + data + '</a>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "area",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "flag",
                    "render": function (data, type, full, meta) {
                        if (data == null) {
                            return "未识别";
                        }
                        switch (data) {
                            case -1:
                                return "识别失败";
                            case 0:
                                return "无效";
                            case 1:
                                return "有效";
                        }
                    }
                }, {
                    "orderable": false,
                    "data": "createTime",
                    "render": function (data, type, full, meta) {
                        return (data ? '<a class=line2>' + data + '</a>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "id",
                    "render": function (data, type, r, meta) {
                        var result = "";
                        //result += '<a href=\'javascript:void(0)\' ti  onclick="openUpdatePage(\'' + r.id + '\')" /><span class="layui-table-link">修改</span></a>&nbsp;';
                        //result += '<a href=\'javascript:void(0)\' ti  onclick="deleteById(\'' + r.id + '\',\'' + r.name + '\')" /><span class="layui-table-link">删除</span></a>&nbsp;';
                        return result;

                    }
                }]
        });


    });

</script>
</body>

</html>