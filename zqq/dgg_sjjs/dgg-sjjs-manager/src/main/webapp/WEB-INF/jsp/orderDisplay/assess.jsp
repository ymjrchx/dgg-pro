<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="/fblock" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>订单评估</title>
    <c:import url="../header.jsp"/>
    <link rel="stylesheet" href="${basePath}/static/kindeditor/themes/default/default.css"/>
    <link rel="stylesheet" href="${basePath}/static/kindeditor/plugins/code/prettify.css"/>
    <script charset="utf-8" src="${basePath}/static/kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" src="${basePath}/static/kindeditor/lang/zh-CN.js"></script>
    <script charset="utf-8" src="${basePath}/static/kindeditor/plugins/code/prettify.js"></script>

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
                <p class="p-style">申请主体名称：${applicantTemplate.applicantName}</p>
                <p class="p-style">地址：${applicantTemplate.businessLicenceAddress}</p>
                <p class="p-style">邮政编码：${applicantTemplate.postalcode}</p>
                <p class="p-style">联系人：${applicantTemplate.contactName}</p>
                <p class="p-style">联系电话：${applicantTemplate.contactPhone}</p>
                <p class="p-style">联系邮箱：${applicantTemplate.contactEmail}</p>
                <p class="p-style">证件号码：${applicantTemplate.applicantCardNo}</p>
            </td>

            <td>
                <div class="style-image">
                    <img class="open-img" alt="个体户执照复印件签字" width="150" height="150"
                         src="${host}${applicantTemplate.businessLicenceFile}">
                    <p class="">（个体户执照复印件签字）</p>
                </div>

                <c:if test="${tra.applicantType=='applicant_type2'}">
                    <div class="style-image">
                        <img class="open-img" alt="身份证正反面复印件签字" width="150" height="150"
                             src="${host}${applicantTemplate.applicantCardFile}">
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


    <div class="body-main" id="upTable" style="margin-top: 10px">

        <table class="table1" style="width: 100%;">
            <div>
                <tr>
                    <td colspan="3" class="up-center">
                        <div class="layui-inline" style="display: block;margin-bottom: 50px">
                            <label class="required">文本内容：</label>

                            <div class="layui-input-inline" style="width: 800px;">
                                <textarea name="content" id="content" type="text" class="input-style" maxlength="300"
                                          style="height: 200px;width:inherit;min-width:400px;resize: none;"
                                          placeholder="文本内容最大长度为300字"></textarea>
                            </div>
                        </div>
                    </td>
                </tr>

            </div>
            <%-- <div style="margin-top: 50px;display: none;">
                 <tr>
                     <td colspan="3" class="up-center">
                         <div class="layui-inline" style="display: block;margin-bottom: 50px">

                             <label class="required">评估图片：</label>

                             <div class="layui-input-inline" style="width: 800px;">
                                 <textarea name="photo" id="photo" type="text" class="input-style"
                                           maxlength="800"
                                           style="height: 380px;width: inherit;min-width:400px;resize: none;"
                                           placeholder="请上传评估图片"></textarea>
                             </div>
                         </div>
                     </td>
                 </tr>
             </div>--%>
            <div style="margin-top: 50px">
                <tr>
                    <td colspan="3" class="up-center">
                        <div class="layui-inline" style="display: block;margin-bottom: 50px">

                            <label class="required">评估图片：</label>

                            <div class="layui-input-inline" style="width: 800px;">
                                <div class="bz-upload" id="fileUpload">
                                    <ul class="data-warp" id="remarksFilePreview">
                                        <li>
                                            <a href="javascript:void(0)" class="upload-button upload" id="upload"></a>
                                            <p>上传附件</p>
                                        </li>
                                        <%--<li title="653cfcb9gy1fkftbbrn5gj21p412whdv.jpg">
                                            <div class="data-img"><a href="javascript:void(0)" class="remove-upload"
                                                                     title="删除"
                                                                     onclick="delUploadFile('https://tibosscms.dgg.net','sccenter_gssc_7753060048219078656_GsscProductFlowOperating_remark_orderInfoRemark_fileId1','653cfcb9gy1fkftbbrn5gj21p412whdv.jpg', this)"></a>
                                                <div class="center"
                                                     data-path="https://fastdfs.dgg.net/group9/M00/03/E0/CgIBv1vzw5mEDO0cAAAAAHKIGSs063.jpg"
                                                     data-groupPath="group9/M00/03/E0/CgIBv1vzw5mEDO0cAAAAAHKIGSs063.jpg"
                                                     data-filename="653cfcb9gy1fkftbbrn5gj21p412whdv.jpg">
                                                    <img data-fileid="sccenter_gssc_7753060048219078656_GsscProductFlowOperating_remark_orderInfoRemark_fileId1"
                                                         src="https://fastdfs.dgg.net/group9/M00/03/E0/CgIBv1vzw5mEDO0cAAAAAHKIGSs063.jpg"
                                                         alt="653cfcb9gy1fkftbbrn5gj21p412whdv.jpg"
                                                         class="layui-upload-img">
                                                </div>
                                            </div>
                                            &lt;%&ndash;<a href="javascript:void(0)"
                                               onclick="downloadFile('https://tibosscms.dgg.net','https://fastdfs.dgg.net/group9/M00/03/E0/CgIBv1vzw5mEDO0cAAAAAHKIGSs063.jpg','653cfcb9gy1fkftbbrn5gj21p412whdv.jpg')">653cfcb9gy1fkftbbrn5gj21p412whdv.jpg</a>&ndash;%&gt;
                                        </li>--%>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
            </div>
        </table>


        <div class="table-conter" style="width: 100%;text-align: center;padding-top: 15px;">
            <div class="layui-inline">
                <%--<button class="layui-btn layui-btn-mini layui-btn-danger" onclick="sendBaoJian('${id}')"
                        style="float: left;margin-right: 30px">自动报件
                </button>--%>
                <button class="layui-btn layui-btn-mini layui-btn-danger" onclick="judge(1)"
                        style="float: left;margin-right: 30px">通过
                </button>
                <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="judge(0)"
                        style="float: left;margin-right: 30px;">未通过
                </button>
                <button class="layui-btn layui-btn-mini layui-btn-primary" onclick="cancel()"
                        style="float: left;margin-right: 30px;">取消
                </button>
            </div>
        </div>


    </div>

    <c:import url="/WEB-INF/jsp/footer.jsp"/>

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


        KindEditor.ready(function (K) {
            window.editor2 = K.create('textarea[name="photo"]', {
                cssPath: '${basePath}/static/kindeditor/plugins/code/prettify.css',
                uploadJson: "${pageContext.request.contextPath}/file/htmlEditorUpload.html?userId=${userId}&type=upload_file_type_01",
                fileManagerJson: '${basePath}/static/kindeditor/jsp/file_manager_json.jsp',
                allowFileManager: true,
                afterCreate: function () {
                    var self = this;
                    K.ctrl(document, 13, function () {
                        self.sync();
                        document.forms['photo'].submit();
                    });
                    K.ctrl(self.edit.doc, 13, function () {
                        self.sync();
                        document.forms['photo'].submit();
                    });
                }
            });
            prettyPrint();
        });


        // 发送报件
        function sendBaoJian(orderId) {
            DGG.Layer.confirm("确定提交报件系统？", function () {
                DGG.Layer.loading.start();
                $.ajax({
                    url: sysInfo.basePath + '/order/baojian.do',
                    data: {orderId: orderId},
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
            });
        }
    </script>
    <script src="${basePath}/static/js/navigation.js?v=${version}" type="text/javascript"></script>
    <script src="${basePath}/static/js/tree_book_select.js?v=${version}" type="text/javascript"></script>
    <script src="${basePath}/static/js/tips.js?v=${version}" type="text/javascript"></script>
    <script type="text/javascript">

        var table = null;


        function cancel() {
            window.parent.layer.closeAll();
        }

        function judge(i) {
            var content = $("#content").val();
            if (content == null || content == "") {
                DGG.Layer.message('请填写文本内容！', {icon: '2'});
                return;
            }
            if (content.length > 300) {
                DGG.Layer.message('文本内容不能超过300！', {icon: '2'});
                return;
            }
            var photo = [];

            $('#remarksFilePreview').find('.center').each(function (index, dom) {
                photo.push($(dom).attr('data-grouppath'))
            })
            if (photo.length == 0) {
                DGG.Layer.message('请上传图片！', {icon: '2'});
                return;
            }
            if (photo.length > 3) {
                DGG.Layer.message('评估只允许上传3张图片！', {icon: '2'});
                return;
            }

            /*if (photo.length > 1000) {
                DGG.Layer.message('图片太多或者图片地址长度过长！', {icon: '2'});
                return;
            }*/
            var table = $("#upTable").serializeJson();
            DGG.Layer.loading.start();

            $.ajax({
                    url: "${pageContext.request.contextPath}/orderDisplay/examine.do", //处理的页面
                    data: {
                        id: '${id}',
                        content: content,
                        photo: photo.join(','),
                        operate: i
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


        //刷新考表格
        function dataTableReload2() {
            $('#myTable').dataTableExtend("reloadFalse");
        }

        function dataTableReload() {
            table.ajax.reload();
        }


    </script>


    <script type="text/javascript">
        /**
         * 删除文件
         */
        function delUploadFile(groupPath, dom) {
            DGG.Layer.confirm("确认是否删除？", function () {
                DGG.Layer.loading.start();
                $.ajax({
                    url: "${pageContext.request.contextPath}/file/delete.do", //处理的页面
                    data: {
                        groupPath: groupPath
                    },
                    type: "POST",  //提交方式
                    success: function (obj) { //回调函数，data为形参，是从页面返回的值
                        DGG.Layer.loading.done();
                        if (obj.code != 0) {
                            DGG.Layer.message(obj.msg, {icon: '2'}, function () {
                            });
                        } else {
                            DGG.Layer.message('操作成功！', {icon: '2'}, function () {
                                $(dom).parent().parent().remove();
                            });
                        }
                    }
                });
            })
        }

        /**
         * 添加dom
         * @param obj
         */
        function addUploadFile(obj) {
            var $dom = $('<li title="653cfcb9gy1fkftbbrn5gj21p412whdv.jpg">\n' +
                '                                            <div class="data-img"><a href="javascript:void(0)" class="remove-upload"\n' +
                '                                                                     title="删除"\n' +
                '                                                                     onclick="delUploadFile(\'653cfcb9gy1fkftbbrn5gj21p412whdv.jpg\', this)"></a>\n' +
                '                                                <div class="center"\n' +
                '                                                     data-path="https://fastdfs.dgg.net/group9/M00/03/E0/CgIBv1vzw5mEDO0cAAAAAHKIGSs063.jpg"\n' +
                '                                                     data-filename="653cfcb9gy1fkftbbrn5gj21p412whdv.jpg"\n' + '' +
                '                                                     data-groupPath="group9/M00/03/E0/CgIBv1vzw5mEDO0cAAAAAHKIGSs063.jpg">\n' + '' +
                '                                                    <img data-fileid="1"\n' +
                '                                                         src=""\n' +
                '                                                         alt="653cfcb9gy1fkftbbrn5gj21p412whdv.jpg"\n' +
                '                                                         class="layui-upload-img">\n' +
                '                                                </div>\n' +
                '                                            </div>\n' +
                '                                            <%--<a href="javascript:void(0)"\n'+
'                                               onclick="downloadFile(\'https://tibosscms.dgg.net\',\'https://fastdfs.dgg.net/group9/M00/03/E0/CgIBv1vzw5mEDO0cAAAAAHKIGSs063.jpg\',\'653cfcb9gy1fkftbbrn5gj21p412whdv.jpg\')">653cfcb9gy1fkftbbrn5gj21p412whdv.jpg</a>--%>\n' +
                '                                        </li>');
            $dom.find('li').attr('title', obj.name);
            $dom.find('.remove-upload').attr('onclick', 'delUploadFile(\'' + obj.fsPath + '\', this)');
            $dom.find('.center').attr('data-path', obj.fsHost + obj.fsPath);
            $dom.find('.center').attr('data-grouppath', obj.fsPath);
            $dom.find('.center').attr('data-filename', obj.name);
            $dom.find('img').attr('src', obj.fsHost + obj.fsPath);
            $dom.find('img').attr('alt', obj.name);
            $('#remarksFilePreview').append($dom);

        }


    </script>

    <script type="text/javascript">
        var upload = layui.upload; //得到 upload 对象

        //创建一个上传组件
        upload.render({
            elem: '#upload',
            url: sysInfo.basePath + '/file/multiUploadBackground.do',
            multiple: true,
            number: 3,
            drag: true,
            filed: 'files',
            accept: 'images', //允许上传的文件类型
            size: 1024 * 10, //最大允许上传的文件大小 10M
            data: {
                type: '${type}',
                dataId: '${id}'
            },
            before: function () {
                DGG.Layer.loading.start();
            },
            done: function (res, index, upload) { //上传后的回调
                DGG.Layer.loading.done();
                if (res.code != 0) {
                    DGG.Layer.message(res.msg, {icon: '2'}, function () {
                    });
                    return;
                }
                // 创建dom
                for (var i in res.data) {
                    addUploadFile(res.data[i])
                }
            }

            //,……
        })
    </script>

</body>

</html>