<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" uri="/fblock" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>改变订单状态</title>
    <c:import url="../header.jsp"/>
    <%--<style>
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

        .titleSearch .layui-inline label::after {
            /*position: absolute;
            top: 71%;
            float: right;
            right: -4px;
            left: auto;*/
        }
    </style>--%>
</head>
<body>

<div class="body-main">
    <c:if test="${status=='已付款'}">
    <div class="titleSearch" style="border-bottom-width: 0px;">
        <div class="layui-inline">
            <label class="required">状态：</label>
            <div class="layui-input-inline select2-inline">
                <select name="status" class="select-style">
                    <option value="">请选择</option>
                    <option name="status" value="order_status_inprocess">办理中
                    </option>
                        <%--<option name="status" value="order_status_completed">已完成
                        </option>
                        <option name="status" value="order_status_evaluate">已评价
                        </option>--%>
                </select>
            </div>
        </div>
    </div>
    </c:if>
    <c:if test="${status=='办理中'}">
        <div class="titleSearch" style="border-bottom-width: 0px;">
            <div class="layui-inline">
                <label class="required">状态：</label>
                <div class="layui-input-inline select2-inline">
                    <select name="status" class="select-style">
                        <option value="">请选择</option>
                        <option name="status" value="order_status_completed">已完成
                        </option>
                            <%--<option name="status" value="order_status_evaluate">已评价
                            </option>--%>
                    </select>
                </div>
            </div>
        </div>
    </c:if>

    <div style="height:50px">
        <div class="table-conter" style="width: 100%;text-align: center;padding-top: 15px;">
            <div style="width: 180px;height: auto;margin-left: auto;margin-right: auto;">
                <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="change()" style="float: left;">确定
                </button>
                <button class="layui-btn layui-btn-mini layui-btn-primary" onclick="cancel()" style="float: right;">取消
                </button>
            </div>
        </div>
    </div>


</div>


<c:import url="/WEB-INF/jsp/footer.jsp"/>
<script src="${basePath}/static/js/tips.js?v=${version}" type="text/javascript"></script>


<script type="text/javascript">

    var table = null;



    function cancel() {
        window.parent.layer.closeAll();
    }


    //刷新考表格
    function dataTableReload2() {
        $('#myTable').dataTableExtend("reloadFalse");
    }

    function dataTableReload() {
        table.ajax.reload();
    }

    function change() {
        if (!$('select[name="status"]').val()) {
            DGG.Layer.message('请选择！', {icon: '2'});
            return;
        }
        var chargingWayTable = $("#chargingWayTable").serializeJson();
        DGG.Layer.loading.start();

        $.ajax({
            url: "${pageContext.request.contextPath}/orderDisplay/changeStatus.do", //处理的页面
            data: {
                id: '${id}',
                status:$('select[name="status"]').val()

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
        }
        )
        ;
    }


</script>
</body>

</html>