<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" uri="/fblock" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>修改服务属性</title>
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
					<font>修改服务属性</font>
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
                    <div class="layui-inline">
                        <input type="hidden" name="id" value="">
                        <div class="layui-input-inline">
                            <input type="hidden" name="id" value="${serviceAttr.id}">
                            <input type="text" value="${serviceAttr.name}" class="input-style" maxlength="50" readonly
                                   disabled style="background-color: transparent;border-width: 0px;">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="required">服务费：</label></label>
                        <div class="layui-input-inline">
                            <input name="servicePrice" type="number" min="0" class="input-style" maxlength="15"
                                   value="${serviceAttr.servicePrice}"
                                   placeholder="官费,最小为0">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="required">官费：</label></label>
                        <div class="layui-input-inline">
                            <input name="officialPrice" type="number" min="0" class="input-style" maxlength="15"
                                   value="${serviceAttr.officialPrice}"
                                   placeholder="官费,最小为0">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label>排序：</label></label>
                        <div class="layui-input-inline">
                            <input name="sort" type="number" class="input-style" maxlength="10"
                                   value="${serviceAttr.sort}"
                                   placeholder="排序,详情将会升序排列">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label> 状态：</label>
                        <div class="layui-input-inline select2-inline">
                            <select name="status" class="select-style" tree-book-select="status"
                                    data-value="${serviceAttr.status}">
                                <option value="">请选择</option>
                            </select>

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
    function saveServiceAttr() {
        var arr = [];
        arr.push($('#typeTable').serializeJson());

        DGG.Layer.loading.start();
        $.ajax({
            url: "${pageContext.request.contextPath}/serviceAttr/update.do", //处理的页面
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