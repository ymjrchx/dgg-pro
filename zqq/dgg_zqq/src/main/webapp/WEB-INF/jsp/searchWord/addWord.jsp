<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" uri="/fblock" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>添加搜索词</title>
    <c:import url="../header.jsp"/>
    <style>
        .titleSearch .input-style, .titleSearch .select-style {
            min-width: 180px;
            max-width: 300px;

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
					<font>添加搜索词</font>
				</span>
        <div class="title-tab">
            <a href="javascript:void(0);" <c:if test="${status == all}"> class="active"</c:if>
               data-status="${all}">填入添加信息</a>
        </div>
    </div>

    <div class="titleSearch" style="border-bottom-width: 0px;">
        <%--<form method="post",action="">--%>
        <table class="table" id="questionDataTable" style="width: 100%;">

            <tr>
                <td colspan="4">
                    <div class="layui-inline">
                        <label class="required">搜索词：</label>
                        <div class="layui-input-inline">
                            <input id="word" name="word" type="text" class="input-style" maxlength="35"
                                   min="0" style="width: 300px" placeholder="请输入搜索词(长度低于20)">
                        </div>
                    </div>
                </td>
            </tr>

            <tr>
                <td colspan="4">
                    <div class="layui-inline">
                        <label class="required"> 是否展示：</label>
                        <div class="layui-input-inline select2-inline">
                            <select id="flag" name="flag" class="select-style" style="width: 300px">
                                <option value="">请选择</option>
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select>
                        </div>
                    </div>
                </td>
            </tr>

        </table>
    </div>


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
<script src="${basePath}/static/js/navigation.js?v=${version}" type="text/javascript"></script>

</body>

<script>
    //取消
    function cancel() {
        window.parent.layer.closeAll();
    }

    function addPage() {
        //验证数据


        var word = $("#word").val();
        if (word == '') {
            DGG.Layer.message("请填写需要添加的搜索词", {icon: '2'}, function () {
            });
            return;
        }

        var flag = $("#flag").val();
        if (flag == '') {
            DGG.Layer.message("请选择启用/禁用", {icon: '2'}, function () {
            });
            return;
        }
        DGG.Layer.loading.start();
        $.ajax({
            url: "${pageContext.request.contextPath}/searchWord/add.do", //处理的页面
            data: {
                word: word,
                flag: flag
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

</html>
