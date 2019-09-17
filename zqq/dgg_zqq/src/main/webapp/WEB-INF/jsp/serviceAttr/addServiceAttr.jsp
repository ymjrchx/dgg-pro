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
            min-width: 80px;
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

        .required::after {
            top: 69%;
            margin-top: -10px;
            left: 30px;
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
                        <label>组合服务类别属性：</label>
                        <div id="result" class="layui-input-inline">

                        </div>
                        <div class="layui-input-inline">

                            <div class="layui-input-inline select2-inline">
                                <select name="service_attr" class="select-style">
                                    <option value="">请选择</option>
                                </select>
                                <select name="code" class="select-style">
                                    <option value="">请选择</option>
                                </select>
                            </div>
                            <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="addServiceAttrType()">
                                添加属性
                            </button>
                            <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="addServiceAttr()">
                                确定
                            </button>
                        </div>
                    </div>
                </td>

            </tr>
            <tr>
                <td>
                    <div class="body-main">
                        <div class="main-title">
				            <span class="bus-state">
					             <font>服务属性列表</font>
				            </span>
                            <div class="title-tab"></div>

                        </div>
                        <div class="titleSearch" style="border-width: 0px;">

                        </div>
                    </div>
                </td>
            </tr>

        </table>
    </div>


    <%--模板--%>
    <div style="height:50px">
        <div class="table-conter" style="width: 100%;text-align: center;padding-top: 15px;">
            <div style="width: 180px;height: auto;margin-left: auto;margin-right: auto;">
                <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="saveServiceAttr()"
                        style="float: left;">保存
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
        $('select[name=service_attr]').empty().append(opts);
    });

    $('select[name=service_attr]').change(function () {
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
<script>

    // 创建 单个 服务属性
    function create$obj(data, clickCallback) {
        var $text = $('<div class="layui-input-inline" style="padding-right: 25px; margin-right: 15px;"><input type="hidden" name="data" readonly ><label></label> <i style="color:#FD482C;cursor:pointer;" title="点击删除" class="fa fa-times fa-lg" aria-hidden="true"></i></div>');
        var $label = $text.find('label');
        $label.text(data.name);
        $text.find('i').click(function () {
            var $this = $(this);
            if (clickCallback && (typeof clickCallback == 'function')) {
                clickCallback($this);
            }
            $this.parent().remove();
        });

        var $input = $text.find('input');
        $input.attr('data-code', data.code);
        $input.attr('data-name', data.name);
        $input.attr('data-pCode', data.pCode);
        $input.attr('data-pName', data.pName);
        $input.attr('data-pSort', data.pSort);

        return $text;

    }

    // 添加类
    function addServiceAttrType() {
        var $pSel = $('select[name=service_attr]'),
            pCode = $pSel.val(),
            pName = $pSel.find("option:selected").text(),
            pSort = $pSel.find("option:selected").attr('data-sort');
        var $sel = $('select[name=code]'),
            code = $sel.val(),
            name = $sel.find("option:selected").text();

        if (!pCode) {
            DGG.Layer.message('请选择要添加的分类！', {icon: '2'});
            return;
        }
        if (!code) {
            DGG.Layer.message('请选择要添加的属性！', {icon: '2'});
            return;
        }

        if ($('#result').find('input[data-pCode="' + pCode + '"]').length > 0) {
            DGG.Layer.message('不能添加分类相同的服务属性！', {icon: '2'});
            return;
        }
        //
        var $dom = create$obj({
            pName: pName,
            pCode: pCode,
            pSort: pSort,
            name: name,
            code: code
        });
        var arr = [];
        arr.push($dom);

        var $domArr = $('#result').find('.layui-input-inline');
        $domArr.detach().each(function (index, dom) {
            arr.push($(dom))
        });

        arr = arr.sort(function compare(a, b) {
            var sortA = a.find('input').attr('data-pSort'),
                sortB = b.find('input').attr('data-pSort');
            sortA = isNaN(sortA) ? null : parseInt(sortA);
            sortB = isNaN(sortB) ? null : parseInt(sortB);
            if (sortA == null || sortB == null) {
                return 0;
            }
            return sortA == sortB ? 0 : (sortA < sortB ? -1 : 1);
        });

        for (var i in arr) {
            $('#result').append(arr[i]);
        }
    }

</script>
<script>
    // 创建服务属性
    function create$Attr(data) {
        var $text = $('<div class="layui-inline"><label class="title"></label><div class="layui-input-inline"><label class="required">服务费：</label><input name="servicePrice" type="number" min="0" style="width: 120px;max-width: 120px;min-width: 120px;" class="input-style"placeholder="服务费,最小为0"><label class="required">官费：</label><input name="price" type="number" min="0" style="width: 120px;max-width: 120px;min-width: 120px;" class="input-style"placeholder="官费,最小为0"></div><i style="color:#FD482C;cursor:pointer;" title="点击删除" class="fa fa-times fa-lg" aria-hidden="true"></i></div>')

        var $label = $text.find('.title');
        $label.text(data.name);
        $text.find('i').click(function () {
            var $this = $(this);
            $this.parent().remove();
        });

        var $input = $text.find('input');
        $input.attr('data-code', data.code);
        $input.attr('data-name', data.name);
        $input.attr('data-pCode', data.pCode);
        $input.attr('data-pName', data.pName);
        $input.attr('data-pSort', data.pSort);

        return $text;
    }

    function addServiceAttr() {
        var $result = $('#result');
        var codeArr = [], nameArr = [], pcodeArr = [], pNameArr = [];
        $result.find('.layui-input-inline').each(function (index, dom) {
            var $dom = $(dom);
            var $input = $dom.find('input');
            var code = $input.attr('data-code'),
                name = $input.attr('data-name'),
                pCode = $input.attr('data-pCode'),
                pName = $input.attr('data-pName');
            codeArr.push(code);
            nameArr.push(name);
            pcodeArr.push(pCode);
            pNameArr.push(pName);
        });
        if (codeArr.length == 0) {
            DGG.Layer.message('请选择要添加的属性！', {icon: '2'});
            return;
        }
        if ($('#typeTable').find('.titleSearch').find('input[data-code="' + codeArr.join(',') + '"]').length > 0) {
            DGG.Layer.message('不能重复添加相同属性！', {icon: '2'});
            return;
        }

        // 数据库验证
        DGG.Layer.loading.start();
        $.ajax({
            url: "${pageContext.request.contextPath}/serviceAttr/queryCode.do", //处理的页面
            data: {
                code: codeArr.join(',')
            },
            type: "POST",  //提交方式
            success: function (obj) { //回调函数，data为形参，是从页面返回的值
                DGG.Layer.loading.done();
                if (obj.code != 0) {
                    DGG.Layer.message(obj.msg, {icon: '2'});
                    return;
                }
                if (obj.data > 0) {
                    DGG.Layer.message("此服务属性在数据库中已存在！", {icon: '2'});
                    return;
                }
                var $dom = create$Attr({
                    name: nameArr.join(','),
                    code: codeArr.join(','),
                    pCode: pcodeArr.join(','),
                    pName: pNameArr.join(',')
                });
                $('#typeTable').find('.titleSearch').append($dom);
                $result.empty();
            }
        });
    }
</script>
<script>
    function saveServiceAttr() {
        var arr = [];
        $('#typeTable').find('.titleSearch').find('.layui-inline').each(function (index, dom) {
            var $dom = $(dom);
            var $input = $dom.find('input');
            var val = $input.val();
            val = '' == val ? null : val;
            var servicePrice = $dom.find('input[name=servicePrice]').val();
            servicePrice = '' == servicePrice ? null : servicePrice;

            var code = $input.attr('data-code'),
                name = $input.attr('data-name'),
                pCode = $input.attr('data-pCode'),
                pName = $input.attr('data-pName');
            arr.push({
                code: code,
                name: name,
                parentCode: pCode,
                parentName: pName,
                officialPrice: val,
                servicePrice: servicePrice
            });
        });
        if (arr.length == 0) {
            DGG.Layer.message("请先添加服务属性！", {icon: '2'});
            return;
        }
        DGG.Layer.loading.start();
        $.ajax({
            url: "${pageContext.request.contextPath}/serviceAttr/save.do", //处理的页面
            data: {
                data: JSON.stringify(arr)
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

    function cancel() {
        window.parent.layer.closeAll();
    }


</script>

</body>

</html>