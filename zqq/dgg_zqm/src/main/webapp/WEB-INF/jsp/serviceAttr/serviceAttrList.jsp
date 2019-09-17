<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" uri="/fblock" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>服务属性列表</title>
    <c:import url="../header.jsp"/>
</head>
<body>
<div class="body-main">
    <div class="main-title">
				<span class="bus-state">
					<font>服务属性列表</font>
				</span>
        <div class="title-tab">
            <a href="javascript:void(0);" <c:if test="${status == all}"> class="active"</c:if>
               data-status="${all}">全部</a>

        </div>
        <input type="hidden" name="flag" value="1">
    </div>

    <div class="titleSearch">
        <div class="layui-inline">
            <label>服务属性名</label>
            <div class="layui-input-inline">
                <input name="name" type="text" class="input-style" maxlength="50"
                       placeholder="请输入名字">
            </div>
        </div>

        <div class="layui-inline">
            <label>服务属性：</label>
            <div class="layui-input-inline select2-inline">
                <select name="parentCode" class="select-style" tree-book-select="service_attr">
                    <option value="">请选择</option>
                </select>
                <select name="code" class="select-style">
                    <option value="">请选择</option>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label>官费价格：</label>
            <div class="layui-input-inline">
                <input name="officialPrice" type="number" min="0" class="input-style" maxlength="50"
                       placeholder="请输入价格">
            </div>
        </div>
        <div class="layui-inline">
            <label> 状态：</label>
            <div class="layui-input-inline select2-inline">
                <select name="status" class="select-style" tree-book-select="status">
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
        <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="openAddPage()">添加</button>
    </div>
    <div class="table-body">
        <!--表格 start-->
        <table id="mytable" class="cell-border crm-table columnsHidden dataTable">
            <thead>
            <tr>
                <th width="20"><input type="checkbox" class="checkall"/></th>
                <th width="50">序号</th>
                <th width="120">服务属性名</th>
                <th>字典编码</th>
                <th>服务费(元)</th>
                <th>官费(元)</th>
                <th>状态</th>
                <th>创建人</th>
                <th>创建时间</th>
                <th>修改人</th>
                <th>修改时间</th>

                <th width="80">操作</th>
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
<script>
    $('select[name=parentCode]').change(function () {
        var $this = $(this), v = $(this).val();
        var $sonSel = $this.next();
        if (v) {
            $sonSel.attr('tree-book-select', v);
            treeBookSelect.initSelect($sonSel, function (dom) {
                $(dom).val('');
            })
        } else {
            $sonSel.empty();
            $sonSel.append('<option value="">请选择</option>');
            $sonSel.val('');
        }
    });
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
        DGG.Layer.open("${pageContext.request.contextPath}/serviceAttr/addPage.html", {
            title: '添加服务属性',
            area: ['90%', '80%'],
            btn: ''
        });
    }

    // 弹出修改页面
    function openUpdatePage(id) {
        DGG.Layer.open("${pageContext.request.contextPath}/serviceAttr/updatePage.html?id=" + id, {
            title: '修改服务属性',
            area: ['90%', '80%'],
            btn: null
        });
    }

    function deleteById(id, name) {
        DGG.Layer.confirm("删除服务属性【" + name + "】 ,将也将删除服务项与此服务属性的关系记录，确认是否删除？", function () {
            DGG.Layer.loading.start();
            $.ajax({
                url: "${pageContext.request.contextPath}/serviceAttr/delete.do", //处理的页面
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
            "ajax": {"url": "${pageContext.request.contextPath}/serviceAttr/queryPage.do", type: "post"},
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
                    "data": "name",
                    "render": function (data, type, full, meta) {
                        return (data ? '' + data + '' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "code",
                    "render": function (data, type, full, meta) {
                        return (data ? '' + data + '' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "servicePrice",
                    "render": function (data, type, full, meta) {
                        return data == null ? '' : data;
                    }
                }, {
                    "orderable": false,
                    "data": "officialPrice",
                    "render": function (data, type, full, meta) {
                        return data == null ? '' : data;
                    }
                }, {
                    "orderable": false,
                    "data": "status",
                    "render": function (data, type, full, meta) {
                        return data ? '<p class=line2>' + data + '</p>' : '';
                    }
                },
                {
                    "orderable": false,
                    "data": "createrName",
                    "render": function (data, type, full, meta) {
                        return data ? '<p class=line2>' + data + '</p>' : '';
                    }
                },
                {
                    "orderable": false,
                    "data": "createTime",
                    "render": function (data, type, full, meta) {
                        return data ? '<p class=line2>' + data + '</p>' : '';
                    }
                },
                {
                    "orderable": false,
                    "data": "updaterName",
                    "render": function (data, type, full, meta) {
                        return data ? '<p class=line2>' + data + '</p>' : '';
                    }
                },
                {
                    "orderable": false,
                    "data": "updateTime",
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
</body>

</html>