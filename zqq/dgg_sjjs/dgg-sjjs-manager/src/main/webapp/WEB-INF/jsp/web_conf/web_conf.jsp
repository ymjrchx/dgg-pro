<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" uri="/fblock" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>网站配置</title>
    <c:import url="../header.jsp"/>
    <style>
        .body-main .titleSearch {
            border-bottom-width: 0px;
        }

        .titleSearch .layui-inline {
            width: 380px;
            text-align: right;
        }

    </style>
</head>
<body>
<div class="body-main">
    <div class="main-title">
				<span class="bus-state">
					<font>网站配置</font>
				</span>
    </div>
    <div class="titleSearch">

        <div class="layui-inline">
            <label>ip每分钟访问次数上限：</label>
            <div class="layui-input-inline">
                <input name="ip_per_minute_limit" type="number" class="input-style" maxlength="50"
                       placeholder="小于0时不限制" title="小于0时不限制" value="${ip_per_minute_limit}">
            </div>
            <div class="layui-input-inline">
                <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="save(this)">保存</button>
            </div>
        </div>

        <div class="layui-inline">
            <label>账户每分钟访问次数上限：</label>
            <div class="layui-input-inline">
                <input name="user_per_minute_limit" type="number" class="input-style" maxlength="50"
                       placeholder="小于0时不限制" title="小于0时不限制" value="${user_per_minute_limit}">
            </div>
            <div class="layui-input-inline">
                <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="save(this)">保存</button>
            </div>
        </div>


        <div class="layui-inline">
            <label>用户未登录时可查看的数据数量：</label>
            <div class="layui-input-inline">
                <input name="login_out_data_num_limit" type="number" class="input-style" maxlength="50"
                       placeholder="小于0时不限制" title="小于0时不限制" value="${login_out_data_num_limit}">
            </div>
            <div class="layui-input-inline">
                <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="save(this)">保存</button>
            </div>
        </div>

        <div class="layui-inline">
            <label>用户登录后可查看的数据数量：</label>
            <div class="layui-input-inline">
                <input name="login_data_num_limit" type="number" class="input-style" maxlength="50"
                       placeholder="小于0时不限制" title="小于0时不限制" value="${login_data_num_limit}">
            </div>
            <div class="layui-input-inline">
                <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="save(this)">保存</button>
            </div>
        </div>


    </div>

</div>

<c:import url="/WEB-INF/jsp/footer.jsp"/>


<script src="${basePath}/static/js/tips.js?v=${version}" type="text/javascript"></script>
<script>
    function trans(v) {
        var $d = $('input[name="trans"]');
        if (!v || v < 0) {
            $d.val('');
            return;
        }
        $d.val(DateUtils.transTime(parseInt(v)));

        var $v = $('input[name="customer_focus_cv_time"]');

        $v.tips({
            side: 1,
            msg: DateUtils.transTime(parseInt(v)),
            bg: '#AE81FF',
            time: 0
        });

    }

    $('input[name="customer_focus_cv_time"]').on('keyup , change', function () {
        trans($(this).val())
    });

    $(function () {
        trans($('input[name="customer_focus_cv_time"]').val());
    });

</script>

<script type="text/javascript">
    // click 事件
    function save(dom) {
        var $dom = $(dom), $input = $dom.parents('div .layui-inline').find('input');
        var data = {
            code: $input.attr('name'),
            value: $input.val()
        }

        //console.log(data);
        if (data.value == null || data.value == '') {
            DGG.Layer.message("配置不能为空！", {icon: '2'}, function () {
            });
            return;
        }
        DGG.Layer.loading.start();
        $.ajax({
            url: sysInfo.basePath + '/webConf/update.do',
            data: data,
            type: "POST",
            dataType: "json",
            success: function (obj) {
                DGG.Layer.loading.done();
                if (obj.code != 0) {
                    DGG.Layer.message(obj.msg, {icon: '2'}, function () {
                    });
                } else {
                    DGG.Layer.message('操作成功！', {icon: '1'}, function () {
                        window.location.reload();
                    });
                }
            }
        });
    }
</script>
</body>

</html>