<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" uri="/fblock" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>添加服务属性</title>
    <c:import url="../header.jsp"/>
    <style>
        .titleSearch .input-style, .titleSearch .select-style {
            min-width: 180px;
            max-width: 210px;

        }

        .titleSearch .layui-inline label {
            min-width: 100px;
            display: inline-block;
            text-align: right;
        }

        .table-body .layui-inline label {
            min-width: 30px;
        }

        .table-body td {
            padding-top: 5px;;
            padding-bottom: 5px;
        }

        .layui-inline label {
            padding-left: 5px;
        }
    </style>
</head>
<body>

<div class="body-main">
    <div class="main-title">
				<span class="bus-state">
					<font>选择服务属性</font>
				</span>
        <div class="title-tab">
            <a href="javascript:void(0);" <c:if test="${status == all}"> class="active"</c:if>
               data-status="${all}"></a>
        </div>
    </div>

    <div class="body-main" style="border-bottom-width: 0px;">
        <table class="table" id="typeTable" style="width: 100%;">
            <tr>
                <td>
                    <div class="layui-inline" id="u">
                        <label>请先选择需要组合的属性类别：</label>
                        <div class="layui-input-inline">
                            <select name="addServiceAttrType" class="select-style">
                                <option value="">请选择</option>
                            </select>
                            <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="addServiceAttrType()">
                                添加
                            </button>
                        </div>
                    </div>
                </td>

            </tr>
            <tr>
                <td>
                    <table id="attrTable" class="table"
                           style="min-height:30px;width: 100%;table-layout: fixed;margin-top: 30px;">
                        <thead>
                        <tr style="height: 30px; font-weight: bold;font-size: 12px;">
                            <td style="width: 120px;max-width: 150px;font-weight: bold;text-align: center;">服务属性类别</td>
                            <td style="font-weight: bold;text-align: center;">服务属性</td>
                            <td style="width: 60px;text-align: center;">操作</td>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </td>
            </tr>

        </table>
    </div>
    <div class="body-main">
        <div class="main-title">
				<span class="bus-state">
					<font>服务属性类别组合</font>
				</span>
            <div class="title-tab">
                <a href="javascript:void(0);"></a>
            </div>
            <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="addServiceAttrTable()"
                    style="float: right;">进行组合
            </button>
        </div>
        <div class="table-body" style="border-bottom-width: 0px;">
            <table class="table" id="serviceAttrTable" style="width: 100%; table-layout: fixed">
                <tbody>
                <tr></tr>

                </tbody>
            </table>
        </div>

    </div>


    <%--模板--%>
    <div style="height:50px">
        <div class="table-conter" style="width: 100%;text-align: center;padding-top: 15px;">
            <div style="width: 180px;height: auto;margin-left: auto;margin-right: auto;">
                <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="addPage()" style="float: left;">保存
                </button>
                <button class="layui-btn layui-btn-mini layui-btn-primary" onclick="cancel()" style="float: right;">取消
                </button>
            </div>

        </div>
    </div>


</div>


<c:import url="/WEB-INF/jsp/footer.jsp"/>
<script src="${basePath}/static/js/tips.js?v=${version}" type="text/javascript"></script>
<script>
    // 初始化
    treeBookService.getData('service_attr', 1, 1, null, function (data) {
        var opts = '<option value="">请选择</option>';
        for (var i in data) {
            opts += '<option data-sort="' + data[i].sort + '" value="' + data[i].code + '">' + data[i].name + '</option>'
        }
        $('select[name=addServiceAttrType]').empty().append(opts);
    });
</script>
<script>
    // 添加类
    function addServiceAttrType() {
        var $sel = $('select[name=addServiceAttrType]'),
            code = $sel.val(),
            name = $sel.find("option:selected").text(),
            sort = $sel.find("option:selected").attr('data-sort');

        if (!code) {
            DGG.Layer.message('请选择要添加的分类！', {icon: '2'});
            return;
        }
        if ($('#attrTable').find('tbody').find('tr[data-code=' + code + ']').length > 0) {
            DGG.Layer.message('不能重复添加！', {icon: '2'});
            return;
        }
        if ($('#attrTable').find('tbody').find('tr') >= 2) {
            DGG.Layer.message('最多选择2类进行组合！', {icon: '2'});
            return;
        }
        treeBookService.getData(code, 1, 1, null, function (data) {
            addServiceAttrTypeTd({name: name, code: code, sort: sort}, data);
        });
    }

    // 添加数据到 表格
    function addServiceAttrTypeTd(typeData, typeArr) {
        var $tr = $('<tr style="height: 32px;"></tr>');
        var $td1 = $('<td class="type" style="width: 120px;max-width: 150px; font-weight: bold;text-align: center;"></td>');
        var $td2 = $('<td class="typeArr"></td>');
        var $td3 = $('<td class="typeArr"><button class="layui-btn layui-btn-mini layui-btn-normal">删除 </button></td>');

        $tr.attr('data-name', typeData.name).attr('data-code', typeData.code).attr('data-sort', typeData.sort);
        $td1.text(typeData.name).attr('data-code', typeData.code);
        $td2.attr('data-name', typeData.name).attr('data-code', typeData.code);

        for (var i in typeArr) {
            var temp = typeArr[i];
            var $text = $('<div class="layui-inline" style="padding-right: 25px; margin-right: 15px;"><input type="hidden" name="name" readonly ><input type="hidden" name="code" readonly ><label></label> <i style="color:#FD482C;cursor:pointer;" title="点击删除" class="fa fa-times fa-lg" aria-hidden="true"></i></div>');
            $text.find('label').text(temp.name);
            $text.find('input[name=code]').val(temp.code);
            $text.find('input[name=name]').val(temp.name);
            // 单个删除事件
            $text.find('i').click(function () {
                var $this = $(this), sel = '.layui-inline';
                if ($this.parent(sel).parent().parent().find(sel).length == 1) {
                    $this.parent(sel).parent().parent().find('button').click();
                } else {
                    $this.parent(sel).remove();
                }
            });
            $td2.append($text);
        }
        // 行 删除按钮
        $td3.find('button').click(function () {
            var $this = $(this);
            $this.parent().parent().remove()
        });
        $tr.append($td1).append($td2).append($td3);

        // 排序
        var arr = [];
        arr.push($tr);
        var $allTr = $('#attrTable').find('tbody').find('tr');
        $allTr.each(function (index, dom) {
            arr.push($(dom))
        });
        $('#attrTable').find('tbody').detach('tr');
        arr = arr.sort(function compare(a, b) {
            var sortA = a.attr('data-sort'),
                sortB = b.attr('data-sort');
            sortA = isNaN(sortA) ? null : parseInt(sortA);
            sortB = isNaN(sortB) ? null : parseInt(sortB);
            if (sortA == null || sortB == null) {
                return 0;
            }
            return sortA == sortB ? 0 : (sortA < sortB ? -1 : 1);
        });
        for (var i in arr) {
            $('#attrTable').find('tbody').append(arr[i]);
        }
    }


    function openAddChargingWayPage() {
        DGG.Layer.open("${pageContext.request.contextPath}/urlManage/addChargeWay.html", {
            title: '添加计费方式',
            area: ['800px', '400px'],
            btn: null
        });
    }


</script>
<script>
    // 获取服务熟悉数组
    function getServiceTypeArr() {
        var arr = [];
        var $tr = $('#attrTable').find('tbody').find('tr');

        $tr.each(function (index, tr) {
            var $text = $(tr).find('.layui-inline');
            var typeArr = [];
            $text.each(function (index, dom) {
                typeArr.push($(dom).serializeJson());
            });
            arr.push(typeArr);
        });
        return arr;
    }

    function createOneLevelTable(arr) {
        var $dom = $('#serviceAttrTable').find('tbody');
        $dom.empty();
        var $trLabel = $('<tr><td style="width: 80px;max-width: 80px; font-weight: bold;text-align: right;padding-right: 15px;">服务属性</td></tr>');
        var $trInput = $('<tr><td style="width: 80px;max-width: 80px;font-weight: bold;text-align: right;padding-right: 15px;">价格</td></tr>');
        var css = {width: '120px', 'max-width': '150px', 'min-width': '120px', textAlign: 'center'};
        for (var i in arr) {
            var $tdLabel = $('<td></td>');
            $tdLabel.text(arr[i].name).css(css);
            $trLabel.append($tdLabel);

            var $tdInput = $('<td><input type="number" class="input-style" style="width: 80px;max-width: 80px;min-width: 80px;"  min="1"><input type="hidden" name="code"><input type="hidden" name="name"></td>');
            $tdInput.find('input[name=code]').val(arr[i].code);
            $tdInput.find('input[name=name]').val(arr[i].name);
            $tdInput.css(css);
            $trInput.append($tdInput)
        }
        $trLabel.append($('<td></td>'));
        $trInput.append($('<td></td>'));
        $dom.append($trLabel).append($trInput);
    }


    // 组合服务属性类别
    function addServiceAttrTable() {
        var arr = getServiceTypeArr();
        if (arr.length == 0) {
            DGG.Layer.message('请先选择要添加的分类！', {icon: '2'});
            return;
        }
        if (arr.length == 1) {
            createOneLevelTable(arr[0]);
        } else {
            for (var i in arr[0]) {
                for (var j in arr[1]) {

                }
            }
        }


    }

</script>

<script type="text/javascript">

    var table = null;

    function reIndex(tableId, name) {
        var $tbody = $('#' + tableId).find('tbody');
        $tbody.find('tr').each(function (index) {
            $(this).find('.index').text(name + (index + 1) + '：');
        });
    }

    function addRow() {
        var $tbody = $('#paramTable').find('tbody');

        var $row = $('#addTable').find('tbody').find('tr').clone();

        $tbody.append($row);

        reIndex('paramTable', '参数');

    }

    function addReturnParamRow() {
        var $tbody = $('#returnParamTable').find('tbody');

        var $row = $('#addReturnParamTable').find('tbody').find('tr').clone();

        $tbody.append($row);

        reIndex('returnParamTable', '参数');

    }

    function addChargingWayRow(arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        var $tbody = $('#chargingWayTable').find('tbody');

        for (var i in arr) {
            var obj = arr[i];
            var $row = $('#addChargingWayTable').find('tbody').find('tr').clone();
            for (var j in obj) {
                $row.find('input[name="' + j + '"]').val(obj[j]);
            }
            $tbody.append($row);
        }
        reIndex('chargingWayTable', '方式');
    }


    /**
     *删除模板行
     */

    function deleteRow(obj, name) {
        var tableId = $(obj).parents('table').attr('id');
        $(obj).parents('tr').remove();
        reIndex(tableId, name);
    }


    function cancel() {
        window.parent.layer.closeAll();
    }


    /**
     *向模板中填充值
     */
    function setValue() {
        var tbody = addRow();
    }

    //刷新考表格
    function dataTableReload2() {
        $('#myTable').dataTableExtend("reloadFalse");
    }

    function dataTableReload() {
        table.ajax.reload();
    }

    function addPage(dom) {
        /*var $dom = $(dom), $input = $dom.parents('div .layui-inline').find('input');*/
        var url = $("#urlTable").serializeJson();
        var param = [];
        $('#paramTable').find('tr').each(function () {
            param.push($(this).serializeJson());
        });
        if (param.length == 0) {
            DGG.Layer.message('接口参数不能为空！', {icon: '2'});
            return;
        }

        var returnParam = [];
        $('#returnParamTable').find('tr').each(function () {
            returnParam.push($(this).serializeJson());
        });
        if (returnParam.length == 0) {
            DGG.Layer.message('接口返回参数不能为空！', {icon: '2'});
            return;
        }

        var chargingWay = [];
        $('#chargingWayTable').find('tr').each(function () {
            chargingWay.push($(this).serializeJson());
        });
        if (chargingWay.length == 0) {
            DGG.Layer.message('计费方式不能为空！', {icon: '2'});
            return;
        }
        DGG.Layer.loading.start();
        $.ajax({
            url: "${pageContext.request.contextPath}/urlManage/addUrl.do", //处理的页面
            data: {
                urlJson: JSON.stringify(url),
                paramJson: JSON.stringify(param),
                returnParamJson: JSON.stringify(returnParam),
                chargingWayJson: JSON.stringify(chargingWay)
            },
            type: "POST",  //提交方式
            success: function (obj) { //回调函数，data为形参，是从页面返回的值
                DGG.Layer.loading.done();
                if (obj.code != 0) {
                    DGG.Layer.message(obj.msg, {icon: '2'}, function () {
                    });
                } else {
                    DGG.Layer.message('操作成功！', {icon: '2'}, function () {
                        window.parent.dataTableReload();
                        window.parent.layer.closeAll();
                    });
                }
            }
        });
    }


</script>
</body>

</html>