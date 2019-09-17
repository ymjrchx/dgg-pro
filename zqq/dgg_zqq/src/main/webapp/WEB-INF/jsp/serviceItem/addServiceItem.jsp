<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" uri="/fblock" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>添加服务项</title>
    <c:import url="../header.jsp"/>
    <style>
        .titleSearch .input-style, .titleSearch .select-style {
            min-width: 180px;
            max-width: 210px;

        }

        .titleSearch .layui-inline label {
            min-width: 120px;
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
					<font>添加服务项</font>
				</span>
        <div class="title-tab">
            <a href="javascript:void(0);" <c:if test="${status == all}"> class="active"</c:if>
               data-status="${all}">填入将修改的信息</a>
        </div>
    </div>

    <div class="titleSearch" style="border-bottom-width: 0px;">
        <table class="table" id="dataTable" style="width: 100%;">
            <tr>
                <td>
                    <div class="layui-inline">
                        <label class="required">服务名称：</label>
                        <div class="layui-input-inline">
                            <input type="hidden" name="id" value="${serviceItem.id}">
                            <input name="name" type="text" class="input-style" maxlength="50"
                                   value="${serviceItem.name}"
                                   placeholder="请输入服务名称">
                        </div>
                    </div>
                </td>
                <td>
                    <div class="layui-inline">
                        <label class="required">服务价格（元）：</label>
                        <div class="layui-input-inline">
                            <input name="servicePrice" type="number" class="input-style" maxlength="50" min="0"
                                   value="${serviceItem.servicePrice}"
                                   placeholder="最小为0">
                        </div>
                    </div>
                </td>
                <td>
                    <div class="layui-inline">
                        <label class="required">官费价格（元）：</label>
                        <div class="layui-input-inline">
                            <input name="officialPrice" type="number" class="input-style" maxlength="50" min="0"
                                   value="${serviceItem.officialPrice}"
                                   placeholder="最小为0">
                        </div>
                    </div>
                </td>
                <td>
                    <div class="layui-inline">
                        <label> 状态：</label>
                        <div class="layui-input-inline select2-inline">
                            <select name="status" class="select-style" tree-book-select="status"
                                    data-value="${serviceItem.status}">
                                <option value="">请选择</option>
                            </select>

                        </div>
                    </div>
                </td>

            </tr>

            <tr>
                <td>
                    <div class="layui-inline">
                        <label class="required">排序：</label>
                        <div class="layui-input-inline">
                            <input name="sort" type="number" class="input-style" maxlength="50" min="0"
                                   value="${serviceItem.sort}"
                                   placeholder="最小为0,将按升序排列">
                        </div>
                    </div>


                </td>
                <td>
                    <div class="layui-inline">
                        <label class="required"> 计入服务及属性价格：</label>
                        <div class="layui-input-inline select2-inline">
                            <select name="autoAddServicePrice" class="select-style" tree-book-select="yes_or_no"
                                    data-value="${serviceItem.autoAddServicePrice}">
                                <option value="">请选择</option>
                            </select>

                        </div>
                    </div>
                </td>
                <td colspan="2">
                    <div class="layui-inline">
                        <label class="required">所属三级类别：</label>
                        <div class="layui-input-inline">
                            <select name="typeLevel1Code" class="select-style" three-level-select="navigation_data"
                                    data-value="${serviceItem.typeLevel1Code}">
                                <option value="">请选择</option>
                            </select>
                            <select name="typeLevel2Code" class="select-style" style="margin-left: 13px"
                                    data-value="${serviceItem.typeLevel2Code}">
                                <option value="">请选择</option>
                            </select>
                            <select name="typeLevel3Code" class="select-style" style="margin-left: 13px"
                                    data-value="${serviceItem.typeLevel3Code}">
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="4">
                    <div class="layui-inline">
                        <label class="required">描述：</label>
                        <div class="layui-input-inline" style="width: 800px;">
                                <textarea name="describle" type="text" class="input-style" maxlength="300"
                                          style="height: 80px;width:inherit;min-width:552px;resize: none;"
                                          placeholder="请输入描述">${serviceItem.describle}</textarea>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <div class="layui-inline">
                        <label class="required">产品logo：</label>
                        <div class="layui-input-inline" style="width: 539px;">
                            <div class="bz-upload" id="logoFileUpload">
                                <ul class="data-warp" id="logoFilePreview">
                                    <li>
                                        <a href="javascript:void(0)" class="upload-button upload"></a>
                                        <p>上传附件</p>
                                    </li>
                                    <c:if test="${serviceItem.logoFile != null}">
                                    <li title="产品logo.jpg">
                                        <div class="data-img"><a href="javascript:void(0)" class="remove-upload"
                                                                 title="删除"
                                                                 onclick="delUploadFile('${serviceItem.logoFile}', this)"></a>
                                            <div class="center"
                                                 data-path="${host}${serviceItem.logoFile}"
                                                 data-groupPath="${serviceItem.logoFile}"
                                                 data-filename="产品logo.jpg">
                                                <img data-fileid="1"
                                                     src="${host}${serviceItem.logoFile}"
                                                     alt="产品logo.jpg"
                                                     class="layui-upload-img">
                                            </div>
                                        </div>
                                        </c:if>

                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
                <td colspan="2">
                    <div class="layui-inline">
                        <label class="required">产品详情图：</label>
                        <div class="layui-input-inline" style="width: 539px;">
                            <div class="bz-upload" id="detailImgFileUpload">
                                <ul class="data-warp" id="detailImgFilePreview">
                                    <li>
                                        <a href="javascript:void(0)" class="upload-button upload"></a>
                                        <p>上传附件</p>
                                    </li>
                                    <c:if test="${serviceItem.detailImgFile != null}">
                                    <li title="产品详情图.jpg">
                                        <div class="data-img"><a href="javascript:void(0)" class="remove-upload"
                                                                 title="删除"
                                                                 onclick="delUploadFile('${serviceItem.detailImgFile}', this)"></a>
                                            <div class="center"
                                                 data-path="${host}${serviceItem.detailImgFile}"
                                                 data-groupPath="${serviceItem.detailImgFile}"
                                                 data-filename="产品详情图.jpg">
                                                <img data-fileid="1"
                                                     src="${host}${serviceItem.detailImgFile}"
                                                     alt="产品详情图.jpg"
                                                     class="layui-upload-img">
                                            </div>
                                        </div>
                                        </c:if>
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="4">
                    <div class="layui-inline">
                        <label class="">关联的服务属性：</label>
                        <div class="layui-input-inline">
                            <div class="body-main">
                                <div id="result" class="titleSearch " style="border-width: 0;">

                                </div>
                            </div>
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
    <div class="body-main">
        <div class="main-title">
				<span class="bus-state">
					<font>添加服务属性</font>
				</span>
            <div class="title-tab">
                <a href="javascript:void(0);" <c:if test="${status == all}"> class="active"</c:if>
                   data-status="${all}">选择服务属性</a>
            </div>
        </div>
        <div class="titleSearch titleSearch1" style="border-width: 0px;">
            <div class="layui-inline">
                <label>服务属性名</label>
                <div class="layui-input-inline">
                    <input name="name" type="text" class="input-style" maxlength="50"
                           placeholder="请输入名字">
                </div>
            </div>

            <div class="layui-inline">
                <label>服务属性：</label>
                <div class="layui-input-inline select2-inline">
                    <select name="parentCode" class="select-style" tree-book-select="service_attr">
                        <option value="">请选择</option>
                    </select>
                    <select name="code" class="select-style">
                        <option value="">请选择</option>
                    </select>
                </div>
            </div>
            <div class="layui-inline">
                <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="dataTableReload()">搜索</button>
                <button class="layui-btn layui-btn-mini layui-btn-primary" onclick="cleanSearch()">清空</button>
            </div>
        </div>
        <div class="table-conter">
            <button class="layui-btn layui-btn-mini layui-btn-normal" onclick="addServiceItem()">添加</button>
        </div>
        <div class="table-body">
            <!--表格 start-->
            <table id="mytable1" class="cell-border crm-table columnsHidden dataTable">
                <thead>
                <tr>
                    <th width="20"><input type="checkbox" class="checkall"/></th>
                    <th width="50">序号</th>
                    <th>服务属性名</th>
                    <th>字典编码</th>
                    <th>服务费(元)</th>
                    <th>官费(元)</th>
                    <th>状态</th>
                    <th>创建人</th>
                    <th>创建时间</th>
                    <th>修改人</th>
                    <th>修改时间</th>


                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
            <!--表格 end-->
        </div>
    </div>




</div>


<c:import url="/WEB-INF/jsp/footer.jsp"/>
<script src="${basePath}/static/js/tips.js?v=${version}" type="text/javascript"></script>
<script src="${basePath}/static/js/navigation.js?v=${version}" type="text/javascript"></script>
<script>
    $('select[name=parentCode]').change(function () {
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
<script type="text/javascript">
    function cancel() {
        window.parent.layer.closeAll();
    }

    function dataTableReload() {
        table.ajax.reload();
    }

    function getSelectData() {
        return $('#mytable1').dataTableExtend('getCheck');
    }

    // 服务项表格
    var table = null;
    $(document).ready(function () {
        //初始化dataTable列表
        //初始化dataTable列表
        table = $('#mytable1').dataTableExtend({
            "ajax": {"url": "${pageContext.request.contextPath}/serviceAttr/queryPage.do", type: "post"},
            "fnServerParams": function (aoData) {
                aoData = $.extend(aoData, $('.titleSearch1').serializeJson(), {status: '${ENABLE}'});
                return aoData;
            },
            "scrollX": "true",//是否开启横项滚动
            "scrollY": "true",
            "fixedColumns": {//锁定列，只有开启scrollX时才生效
                "leftColumns": 3,//左侧锁定9列
                "rightColumns": 0//右侧锁定1列
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
                    "data": "name",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "code",
                    "render": function (data, type, full, meta) {
                        return (data ? '<p class=line1>' + data + '</p>' : '');
                    }
                }, {
                    "orderable": false,
                    "data": "servicePrice",
                    "render": function (data, type, full, meta) {
                        return data == null ? '' : data;
                    }
                }, {
                    "orderable": false,
                    "data": "officialPrice",
                    "render": function (data, type, full, meta) {
                        return data == null ? '' : data;
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
                }],
            "columnDefs": [{
                targets: [4], //第1，2，3列
                createdCell: function (td, cellData, rowData, row, col) {

                }
            }]
        });
    });

</script>
<script>
    // 创建服务属性
    function create$Attr(data) {
        var $text = $('<div class="layui-inline" style=" width: 350px;"><label data-label></label><div class="layui-input-inline"><label data-data style="text-align: left;min-width: 30px;width:auto;max-width:150px;"></label><input name="price" type="hidden" min="0" readonly  style="width: 120px;max-width: 120px;min-width: 120px;" class="input-style"placeholder="官费,最小为0"></div><i style="color:#FD482C;cursor:pointer;" title="点击删除" class="fa fa-times fa-lg" aria-hidden="true"></i></div>')

        var $label = $text.find('label[data-label]');
        $label.text(data.name + '：');
        $text.find('i').click(function () {
            var $this = $(this);
            $this.parent().remove();
        });

        var $input = $text.find('input');
        $input.attr('data-id', data.id);
        $input.attr('data-code', data.code);
        $input.attr('data-name', data.name);
        $input.attr('data-pCode', data.pCode);
        $input.attr('data-pName', data.pName);
        $input.attr('data-officialPrice', data.officialPrice);
        $text.find('label[data-data]').html('服务费:' + data.servicePrice + '元&nbsp;&nbsp;&nbsp;&nbsp;官费:' + data.officialPrice + '元');

        return $text;
    }

    // 添加服务项目
    function addServiceItem() {
        var dataArr = getSelectData();
        if (dataArr.length == 0) {
            DGG.Layer.message("请选择服务属性", {icon: '2'});
            return;
        }
        for (var i in dataArr) {
            if ($('#result').find('input[data-id=' + dataArr[i].id + ']').length > 0) {
                DGG.Layer.message("服务属性：【" + dataArr[i].name + "】已存在，不能重复添加", {icon: '2'});
                return;
            }
        }
        var $re = $('#result');
        for (var i in dataArr) {
            $re.append(create$Attr(dataArr[i]));
        }
    }
</script>
<script>
    <c:if test="${serviceAttrArrJson!=null}">
    var dataArr = $.parseJSON('${serviceAttrArrJson}');
    var $re = $('#result');
    for (var i in dataArr) {
        $re.append(create$Attr(dataArr[i]));
    }
    </c:if>

</script>
<script>
    function addPage() {
        var data = $('#dataTable').serializeJson();
        var attrIdArr = [];
        $('#result').find('.layui-inline').each(function (index, dom) {
            var $input = $(dom).find('input');
            attrIdArr.push($input.attr('data-id'));
        });

        /*if (attrIdArr.length == 0) {
            DGG.Layer.message("服务属性不能为空！", {icon: '2'});
            return;
        }*/
        if ($('#logoFilePreview').find('.center').length == 0) {
            DGG.Layer.message('服务项logo不能为空！', {icon: '2'});
            return;
        }
        ;
        if ($('#logoFilePreview').find('.center').length > 1) {
            DGG.Layer.message('服务项logo只能上传一张！', {icon: '2'});
            return;
        }
        ;
        data.logoFile = $('#logoFilePreview').find('.center').attr('data-groupPath');

        if ($('#detailImgFilePreview').find('.center').length == 0) {
            DGG.Layer.message('详情图片不能为空！', {icon: '2'});
            return;
        }
        ;
        if ($('#detailImgFilePreview').find('.center').length > 1) {
            DGG.Layer.message('详情图片只能上传一张！', {icon: '2'});
            return;
        }
        ;
        data.detailImgFile = $('#detailImgFilePreview').find('.center').attr('data-groupPath');

        var url = data.id ? "${pageContext.request.contextPath}/serviceItem/update.do" : "${pageContext.request.contextPath}/serviceItem/save.do";

        DGG.Layer.loading.start();
        $.ajax({
            url: url, //处理的页面
            data: {
                data: JSON.stringify(data),
                serviceAttrId: attrIdArr.join(',')
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
    function addUploadFile(id, obj) {
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
        $('#' + id).append($dom);

    }


</script>

<script type="text/javascript">
    var upload = layui.upload; //得到 upload 对象

    function render(domId) {
        upload.render({
            elem: $('#' + domId).find('.upload'),
            url: sysInfo.basePath + '/file/multiUploadBackground.do',
            multiple: false,
            number: 1,
            drag: true,
            filed: 'files',
            accept: 'images', //允许上传的文件类型
            size: 1024 * 10, //最大允许上传的文件大小 10M
            data: {
                type: '${type}',
                dataId: '${serviceItem.id}' ? '${serviceItem.id}' : null
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
                    addUploadFile(domId, res.data[i])
                }
            }

            //,……
        })
    }

    render('logoFilePreview');
    render('detailImgFilePreview');

</script>
</body>

</html>