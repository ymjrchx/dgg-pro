<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" uri="/fblock" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>资讯列表</title>
    <c:import url="../header.jsp"/>
</head>
<body>
<div class="body-main">
    <div class="main-title">
				<span class="bus-state">
					<font>资讯列表</font>
				</span>
        <%--<div class="title-tab">
            <a href="javascript:void(0);" <c:if test="${status == all}"> class="active"</c:if>
               data-status="${all}">全部</a>

        </div>--%>
        <input type="hidden" name="flag" value="1">
    </div>

    <div class="titleSearch">
        <div class="layui-inline">
            <label>服务大类</label>
            <div class="layui-input-inline">
                <input id ="typeLevel1Name" name="typeLevel1Name" type="text" class="input-style" maxlength="50"
                       placeholder="请输入服务大类名">
            </div>
        </div>

        <div class="layui-inline">
            <label>标题：</label>
            <div class="layui-input-inline select2-inline">
                <input id ="title" name="title" type="text" class="input-style" maxlength="50"
                       placeholder="请输入名字">
            </div>
        </div>
        <div class="layui-inline">
            <label> 状态：</label>
            <div class="layui-input-inline select2-inline">
                <select id ="status" name="status" class="select-style" tree-book-select="status">
                    <option value="">请选择</option>
                </select>

            </div>
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="dataTableReload()">搜索</button>
            <button id="shangchuan" class="layui-btn layui-btn-mini layui-btn-primary" onclick="cleanSearch()">清空
            </button>
        </div>

        <div class="layui-inline" style="position: absolute;right: 100px;">
            <form action="${pageContext.request.contextPath}/news/importNews.do" method="post"
                  enctype="multipart/form-data">
                <label> 批量导入：</label>
                <input name="file" type="file" id="fileId"/>
                <input id="inportFile" type="submit" class="layui-btn layui-btn-mini layui-btn-normal" value="上传"
                       onclick="checkFileSizeAndType(['xls','xlsx'],'fileId',this, 5000)" ;/>
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
                <th>服务大类</th>
                <%--<th>大类编码</th>--%>
                <th>标题</th>
                <th>状态</th>
                <th>分类</th>
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
    $('select[name=status]').change(function () {
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

    function checkFileSizeAndType(allowType, fileId, obj, time) {
        obj.disabled = true;

        setTimeout(function () {
            var x = setInterval(function () {
                time = time - 1000; //reduce each second
                obj.value = (time / 1000) % 60;
                if (time == 0) {
                    clearInterval(x);
                    obj.value = "上传";
                    obj.disabled = false;
                    this.dataTableReload();
                }
            }, 1000);
        }, time - 10000); //倒计时

        //默认类型
        if (!allowType) {
            allowType = new Array('xls', 'xlsx');
        }
        //js通过id获取上传的文件对象
        var file = document.getElementById(fileId);
        var types = allowType;

        var fileInfo = file.files[0];
        if (!fileInfo) {
            alert("请选择文件！");
            return false;
        }
        var fileName = fileInfo.name;

        //获取文件后缀名
        var file_typename = fileName.substring(
            fileName.lastIndexOf('.') + 1, fileName.length);

        //定义标志是否可以提交上传
        var isUpload = true;
        //定义一个错误参数：1代表大小超出 2代表类型不支持
        var errNum = 0;
        if (fileInfo) {
            for (var i in types) {
                if (types[i] == file_typename) {
                    isUpload = true;
                    return isUpload;
                } else {
                    isUpload = false;
                    errNum = 2;
                }
            }
        }
        //对错误的类型进行对应的提示
        if (!isUpload) {
            if (errNum == 2) {
                alert("上传的" + file_typename + "文件类型不支持！只支持" + types.toString() + "格式");
            } else {
                alert("没有选择文件");
            }
            file.value = "";
            return isUpload;
        }
    }

    function foo(obj, time) {
        obj.disabled = true;

        setTimeout(function () {
            var x = setInterval(function () {
                time = time - 1000; //reduce each second
                obj.value = (time / 1000) % 60;
                if (time == 0) {
                    clearInterval(x);
                    obj.value = "上传";
                    obj.disabled = false;
                    this.dataTableReload();
                }
            }, 1000);
        }, time - 10000);
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
        DGG.Layer.open("${pageContext.request.contextPath}/news/addpage.html", {
            title: '添加资讯信息',
            area: ['90%', '95%'],
            btn: ''
        });
    }

    // 弹出修改页面
    function openUpdatePage(id) {
        DGG.Layer.open("${pageContext.request.contextPath}/news/updatepage.html?id=" + id, {
            title: '修改资讯信息',
            area: ['90%', '95%'],
            btn: null
        });
    }

    function deleteById(id, title) {
        DGG.Layer.confirm("删除资讯【" + title + "】 ,确认是否删除？", function () {
            DGG.Layer.loading.start();
            $.ajax({
                url: "${pageContext.request.contextPath}/news/delete/"+id+".do", //处理的页面
                data: {

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
            "ajax": {"url": "${pageContext.request.contextPath}/news/showNews.do", type: "post"},
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
                    "data": "typeLevel1Name",
                    "render": function (data, type, full, meta) {
                        return (data ? '' + data + '' : '');
                    }
                },
                {
                    "orderable": false,
                    "data": "title",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line2>' + data + '</p>' : '');
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
                    "data": "type",
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

                        /* result += '<a href=\'javascript:void(0)\' ti  onclick="deleteById(\'' + r.id + '\',\'' + r.title + '\')" /><span class="layui-table-link">删除</span></a>&nbsp;';*/

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