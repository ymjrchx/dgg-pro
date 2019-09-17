<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" uri="/fblock" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>资讯管理</title>
    <c:import url="../header.jsp"/>
    <link rel="stylesheet" href="${basePath}/static/kindeditor/themes/default/default.css" />
    <link rel="stylesheet" href="${basePath}/static/kindeditor/plugins/code/prettify.css" />
    <script charset="utf-8" src="${basePath}/static/kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" src="${basePath}/static/kindeditor/lang/zh-CN.js"></script>
    <script charset="utf-8" src="${basePath}/static/kindeditor/plugins/code/prettify.js"></script>
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
					<font>资讯录入</font>
				</span>
        <div class="title-tab">
            <a href="javascript:void(0);" <c:if test="${status == all}"> class="active"</c:if>
               data-status="${all}">更新资讯信息</a>
        </div>
    </div>

    <div class="titleSearch" style="border-bottom-width: 0px;">
        <table class="table" id="urlTable" style="width: 100%;">
            <tr>
                <td>
                    <div class="layui-inline" id="u">
                        <label class="required">服务大类：</label>
                        <input name="id" value="${news.id}" type="hidden">
                        <div class="layui-input-inline">
                            <select name="typeLevel1Name" class="select-style">
                                <option value="">请选择</option>
                                <option name="method" value="服务大类1"
                                        <c:if test="${news.typeLevel1Name == '服务大类1'}">selected</c:if>>服务大类1
                                </option>
                                <option name="method" value="服务大类2"
                                        <c:if test="${news.typeLevel1Name == '服务大类2'}">selected</c:if>>服务大类2
                                </option>
                            </select>
                        </div>
                    </div>
                </td>
                <td>
                    <div class="layui-inline">
                        <label class="required">标  题：</label>
                        <div class="layui-input-inline">
                            <input name="title" value="${news.title}" type="text" class="input-style" maxlength="50"
                                   placeholder="请输入标题">
                        </div>
                    </div>
                </td>
                <td>
                    <div class="layui-inline">
                        <label class="required"> 标  签：</label>
                        <div class="layui-input-inline select2-inline">
                            <input name="label" value="${news.label}" type="text" class="input-style" maxlength="50"
                                   placeholder="请输入标签">
                        </div>
                    </div>
                </td>
                <td>
                    <div class="layui-inline">
                        <label class="required">来  源：</label>
                        <div class="layui-input-inline select2-inline">
                            <input name="origin" value="${news.origin}" type="text" class="input-style" maxlength="50"
                                   placeholder="请输入来源">
                        </div>
                    </div>
                </td>
                <td>
                    <div class="layui-inline">
                        <label class="required">分  类：</label>
                        <div class="layui-input-inline select2-inline">
                            <select name="type" class="select-style" tree-book-select="news_types"
                                    data-value="${news.type}">
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                </td>
                <td>
                    <div class="layui-inline">
                        <label class="required"> 状态：</label>
                        <div class="layui-input-inline select2-inline">
                            <select name="status" class="select-style">
                                <option value="">请选择</option>
                                <option name="support" value="status2"
                                        <c:if test="${news.status == 'status2'}">selected</c:if>>禁用
                                </option>
                                <option name="support" value="status1"
                                        <c:if test="${news.status == 'status1'}">selected</c:if>>启用
                                </option>
                            </select>

                        </div>
                    </div>
                </td>
                <td>
                    <div class="layui-inline">
                        <label class="required">正常删除：</label>
                        <div class="layui-input-inline select2-inline">
                            <select name="flag" class="select-style">
                                <option value="">请选择</option>
                                <option name="flag" value="1" <c:if test="${news.flag == 1}">selected</c:if>>正常</option>
                                <option name="flag" value="0" <c:if test="${news.flag == 0}">selected</c:if>>删除</option>
                            </select>
                        </div>
                    </div>
                </td>
            </tr>

            <tr>
                <td colspan="3">
                    <div class="layui-inline">
                        <div style="display: block;text-align: center;margin-bottom: 10px">
                            <label class="required">资讯展示图片：</label>
                        </div>

                        <div class="layui-input-inline" style="width: 500px;">
                                <textarea name="newsPhoto" id="news_photo" type="text" class="input-style"
                                          maxlength="1000"
                                          style="height: 550px;width: inherit;min-width:800px;resize: none;"
                                          placeholder="请上传资讯展示图片"></textarea>
                        </div>
                    </div>
                </td>
                <td colspan=".5"></td>

                <td colspan="3">
                    <div class="layui-inline">
                        <div style="display: block;text-align: center;margin-bottom: 10px">
                            <label class="required">文本内容：</label>
                        </div>

                        <div class="layui-input-inline" style="width: 500px;">
                                <textarea name="content" id="editor_id" type="text" class="input-style" maxlength="1000"
                                          style="height: 550px;width:inherit;min-width:800px;resize: none;"
                                          placeholder="请输入文本内容"></textarea>
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
                <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="addPage()" style="float: left;">保存
                </button>
                <button class="layui-btn layui-btn-mini layui-btn-primary" onclick="cancel()" style="float: right;">取消
                </button>
            </div>

        </div>
    </div>
</div>


<c:import url="/WEB-INF/jsp/footer.jsp"/>

<script>
    KindEditor.ready(function(K) {
        window.editor1 = K.create('textarea[name="content"]', {
            cssPath : '${basePath}/static/kindeditor/plugins/code/prettify.css',
            uploadJson : "${pageContext.request.contextPath}/file/htmlEditorUpload.html?userId=${userId}&type=upload_file_type_01",
            fileManagerJson : '${basePath}/static/kindeditor/jsp/file_manager_json.jsp',
            allowFileManager : true,
            afterCreate : function() {
                var self = this;
                K.ctrl(document, 13, function() {
                    self.sync();
                    document.forms['content'].submit();
                });
                K.ctrl(self.edit.doc, 13, function(){
                    self.sync();
                    document.forms['content'].submit();
                });
            }
        });
        <%--editor1.html('${news.content}');--%>
        prettyPrint();
    });
</script>
<script>
    KindEditor.ready(function(K) {
        window.editor2 = K.create('textarea[name="newsPhoto"]', {
            cssPath : '${basePath}/static/kindeditor/plugins/code/prettify.css',
            uploadJson : "${pageContext.request.contextPath}/file/htmlEditorUpload.html?userId=${userId}&type=upload_file_type_01",
            fileManagerJson : '${basePath}/static/kindeditor/jsp/file_manager_json.jsp',
            allowFileManager : true,
            afterCreate : function() {
                var self = this;
                K.ctrl(document, 13, function() {
                    self.sync();
                    document.forms['newsPhoto'].submit();
                });
                K.ctrl(self.edit.doc, 13, function() {
                    self.sync();
                    document.forms['newsPhoto'].submit();
                });
            }
        });

        prettyPrint();
    });
</script>

<script>
    $.ajax({
        url: "${pageContext.request.contextPath}/news/data/${news.id}.do", //处理的页面
    data: {
    },
    type: "GET",  //提交方式
    dataType: "json",
    success: function (obj) { //回调函数，data为形参，是从页面返回的值
        editor1.html(obj.data.content);
        editor2.html(obj.data.newsPhoto)
    },error:function() {
            alert("")
        }
    });
</script>

<script src="${basePath}/static/js/tips.js?v=${version}" type="text/javascript">
</script>
<script>
    $('select[name="call"]').tips({
        side: 3,
        msg: "直接影响此外部接口是否能被用户调用！",
        bg: '#AE81FF',
        time: 0
    });

</script>
<script type="text/javascript">

    function cancel() {
        window.parent.layer.closeAll();
    }

    function addPage(dom) {
        /*var $dom = $(dom), $input = $dom.parents('div .layui-inline').find('input');*/
        var url = $("#urlTable").serializeJson();
        var content = editor1.html();
        var photo = editor2.html();
        DGG.Layer.loading.start();
        $.ajax({
            url: "${pageContext.request.contextPath}/news/update.do", //处理的页面
            data: {
                news: JSON.stringify(url),
                content:content,
                newsPhoto:photo
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