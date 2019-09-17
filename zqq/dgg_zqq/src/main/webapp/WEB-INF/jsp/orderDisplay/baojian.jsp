<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="/fblock" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>自动报件</title>
    <c:import url="../header.jsp"/>

    <style>
        .p-style2 {
            padding-top: 10px;
            font-size: 25px;
        }

        .tableDiv {
            margin-left: 200px;
        }

        .p-style {
            font-size: 20px;
        }

        .body-main .layui-inline label {
            min-width: 100px;
            display: inline-block;
            text-align: center;
        }

        .table-body .layui-inline label {
            min-width: 30px;
            text-align: center;
        }

        .up-center {
            text-align: center;
        }

        #myImg {
            border-radius: 5px;
            cursor: pointer;
            transition: 0.3s;
        }

        #myImg:hover {
            opacity: 0.7;
        }

        /* The Modal (background) */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            padding-top: 100px; /* Location of the box */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0, 0, 0); /* Fallback color */
            background-color: rgba(0, 0, 0, 0.9); /* Black w/ opacity */
        }

        /* Modal Content (image) */
        .modal-content {
            margin: auto;
            display: block;
            width: 80%;
            max-width: 700px;
        }

        /* Caption of Modal Image */
        #caption {
            margin: auto;
            display: block;
            width: 80%;
            max-width: 700px;
            text-align: center;
            color: #ccc;
            padding: 10px 0;
            height: 150px;
        }

        /* Add Animation */
        .modal-content, #caption {
            -webkit-animation-name: zoom;
            -webkit-animation-duration: 0.6s;
            animation-name: zoom;
            animation-duration: 0.6s;
        }

        @-webkit-keyframes zoom {
            from {
                -webkit-transform: scale(0)
            }
            to {
                -webkit-transform: scale(1)
            }
        }

        @keyframes zoom {
            from {
                transform: scale(0.1)
            }
            to {
                transform: scale(1)
            }
        }

        /* The Close Button */
        .close {
            position: absolute;
            top: 15px;
            right: 35px;
            color: #f1f1f1;
            font-size: 40px;
            font-weight: bold;
            transition: 0.3s;
        }

        .close:hover,
        .close:focus {
            color: #bbb;
            text-decoration: none;
            cursor: pointer;
        }

        /* 100% Image Width on Smaller Screens */
        @media only screen and (max-width: 700px) {
            .modal-content {
                width: 100%;
            }
        }
    </style>
</head>
<body>
<div class="tableDiv">
    <table class="table" style="width: 100%;">
        <thead>
        <p class="p-style2" style="border-bottom: 1px solid black"><span>申请主体</span></p>
        </thead>
        <tr>
            <td>
                <p class="p-style">申请主体名称：${tra.applicantName}</p>
                <p class="p-style">地址：${tra.businessLicenceAddress}</p>
                <p class="p-style">邮政编码：${tra.postalcode}</p>
                <p class="p-style">联系人：${order.contactName}</p>
                <p class="p-style">联系电话：${order.contactPhone}</p>
                <p class="p-style">联系邮箱：${order.contactEmail}</p>

                <c:if test="${tra.applicantType == ENTERPRISE}">
                    <p class="p-style">营业执照号：${tra.businessLicenceNo}</p>
                </c:if>
                <c:if test="${tra.applicantType == PERSON}">
                    <p class="p-style">身份证件号码：${tra.applicantCardNo}</p>
                </c:if>
            </td>

            <td>
                <div class="style-image">
                    <img class="open-img" alt="个体户执照复印件签字" width="150" height="150"
                         src="${host}${tra.businessLicenceFile}">
                    <p class="">（个体户执照复印件签字）</p>
                </div>

                <c:if test="${tra.applicantType=='applicant_type2'}">
                    <div class="style-image">
                        <img class="open-img" alt="身份证正反面复印件签字" width="150" height="150"
                             src="${host}/${tra.applicantCardFile}">
                        <p class="">（身份证正反面复印件签字）</p>
                    </div>
                </c:if>


            </td>
        </tr>
    </table>
    <table class="table" style="width: 100%;">
        <thead>
        <p class="p-style2" style="border-bottom: 1px solid black"><span>注册商标信息</span></p>
        </thead>
        <tr>
            <td>
                <p class="p-style">商标名称：${tra.name}</p>
                <p class="p-style">商标说明：${tra.explain}</p>
                <p class="p-style">注册号：${tra.registerNo}</p>
                <p class="p-style">商标分类：</p>
                <table class="table" style="width: 540px;table-layout: fixed;">
                    <thead>
                    <tr>
                        <td style="width: 90px;text-align: center;">一级分类</td>
                        <td style="text-align: center;">三级分类</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${dataList}" var="data">
                        <tr style="border-top: 1px solid olive ;border-bottom: 1px solid olive">
                            <td style="width: 90px;text-align: center;">${data.classLevel1Name} </td>
                            <td style="text-align: center;">
                                <c:forEach items="${data.sonArr}" var="son">
                                    <label style="word-break: keep-all;word-wrap:normal;display: inline-block;"> ${son.classLevel3Name}&nbsp;&nbsp;</label>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </td>

            <td>
                <div class="style-image">
                    <img class="open-img" alt="商标图样" width="150" height="150"
                         src="${host}${tra.exampleAddress}">
                    <p class="">（商标图样）</p>
                </div>
                <div class="style-image">
                    <img id="myImg" class="open-img" alt="委托书" width="150" height="150"
                         src="${host}${tra.proxyFile}">
                    <p class="">（委托书）</p>
                </div>
            </td>
        </tr>

    </table>
    <div id="myModal" class="modal">
        <span class="close">×</span>
        <img class="modal-content" id="img01">
        <div id="caption"></div>
    </div>

    <div class="table-conter" style="width: 100%;text-align: center;padding-top: 50px;padding-bottom: 50px">
        <div class="layui-inline">
            <%--<button class="layui-btn layui-btn-mini layui-btn-danger" onclick="sendBaoJian('${id}')"
                    style="float: left;margin-right: 30px">自动报件
            </button>--%>
            <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="sendBaoJian()"
                    style="float: left;margin-right: 30px">发送报件
            </button>
            <button class="layui-btn layui-btn-mini layui-btn-primary" onclick="cancel()"
                    style="float: left;margin-right: 30px;">取消
            </button>
        </div>
    </div>


    <script>
        var a =
        ${list1}.parse();
        for (var i = 0; i < a.length; i++) {
            console
        }

    </script>

    <script src="${basePath}/static/js/navigation.js?v=${version}" type="text/javascript"></script>

    <script>
        // 获取模态窗口
        var modal = document.getElementById('myModal');
        // 获取图片模态框，alt 属性作为图片弹出中文本描述
        var img = document.getElementsByClassName("open-img");
        var modalImg = document.getElementById("img01");
        var captionText = document.getElementById("caption");

        for (var i = 0; i < img.length; i++) {
            img[i].onclick = function () {
                modal.style.display = "block";
                modalImg.src = this.src;
                modalImg.alt = this.alt;
                captionText.innerHTML = this.alt;
            }
        }

        // 获取 <span> 元素，设置关闭模态框按钮
        var span = document.getElementsByClassName("close")[0];

        // 点击 <span> 元素上的 (x), 关闭模态框
        span.onclick = function () {
            modal.style.display = "none";
        }


        // 发送报件
        function sendBaoJian() {
            DGG.Layer.confirm("确定提交报件系统？", function () {
                DGG.Layer.loading.start();
                $.ajax({
                    url: sysInfo.basePath + '/order/baojian.do',
                    data: {orderId: '${id}'},
                    type: "POST",
                    dataType: "json",
                    success: function (obj) {
                        DGG.Layer.loading.done();
                        if (obj.code != 0) {
                            DGG.Layer.message(obj.msg, {icon: '2'}, function () {
                            });
                        } else {
                            DGG.Layer.message('操作成功！', {icon: '1'}, function () {
                                window.parent.dataTableReload();
                                window.parent.layer.closeAll();
                            });
                        }
                    }
                });
            });
        }
    </script>


    <c:import url="/WEB-INF/jsp/footer.jsp"/>
    <script src="${basePath}/static/js/navigation.js?v=${version}" type="text/javascript"></script>
    <script src="${basePath}/static/js/tree_book_select.js?v=${version}" type="text/javascript"></script>
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


    </script>
</body>

</html>