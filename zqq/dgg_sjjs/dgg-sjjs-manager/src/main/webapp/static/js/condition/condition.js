var setting = {
    data: {
        simpleData: {
            pIdKey: "pid",
            enable: true
        }
    },
    view: {
        showLine: false,
        showIcon: false
    },
    edit: {
        enable: true,
        showRemoveBtn: false,
        showRenameBtn: false,
        drag: {
            isCopy: true,
            isMove: false,
            prev: true,
            next: true,
            inner: true
        }
    },
    callback: {
        onClick: zTreeOnClick,
        beforeDrag: zTreeBeforeDrag,
        onDrop: zTreeOnDrop
    },
    async: {
        enable: true,
        url: "/tree_book/tree_book_nodes.do",
        autoParam: ["id=id"],
        dataFilter: filter
    }
};

$(document).ready(function () {
    DGG.Layer.loading.start();
    $.post(sysInfo.basePath + "/tree_book/tree_book_nodes.do", {id: -2}, function (result) {
        $.fn.zTree.init($('#leftTree'), setting, result.data);
        DGG.Layer.loading.done();
    });
});


function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    return childNodes.data;
}

/**
 * 拖曳组织机构树节点及子节点—拖拽之前的事件回调函数
 * @param treeId
 * @param treeNodes
 * @returns {boolean}
 */
var beforeTree;

function zTreeBeforeDrag(treeId, treeNodes) {
    beforeTree = treeNodes;
}

/**
 * 拖曳组织机构树节点及子节点—捕获节点拖拽操作结束的事件回调函数
 * @param event          标准的 js event 对象
 * @param treeId        目标节点 targetNode 所在 zTree 的 treeId
 * @param treeNodes     被拖拽的节点 JSON 数据集合
 * @param targetNode    成为 treeNodes 拖拽结束的目标节点 JSON 数据对象。
 * @param moveType     指定移动到目标节点的相对位置
 * @param isCopy       拖拽节点操作是 复制 或 移动
 */
function zTreeOnDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
    var treeObj = $.fn.zTree.getZTreeObj(treeId);
    if (treeNodes[0].id == 1) {
        return;
    }
    if (null == targetNode) {
        DGG.Layer.message("不允许拖为根节点!", {icon: '2'});
        treeObj.removeNode(treeNodes[0]);
        zTreeNodeShow(treeObj.getNodeByParam("id", 1));
        return;
    }
    $.each(treeNodes, function (index, treeNode) {
        DGG.Layer.confirm("确定移动到" + targetNode.name + "?", {
            'title': '确定移动字典？', 'cancel': function () {
                zTreeNodeShow(targetNode);
            }
        }, function () {
            DGG.Layer.loading.start();
            //保存拖动组织机构部门关联关系
            if (checkEmpty(targetNode.id) && checkEmpty(treeNodes[0].id)) {
                $.post("/tree_book/tree_book_drop.do", {pid: targetNode.id, id: treeNodes[0].id}, function (result) {
                    if (result.code == 1) {
                        DGG.Layer.message(result.msg, {icon: '2'});
                    } else {
                        treeObj.removeNode(beforeTree[0]);
                        DGG.Layer.message("修改成功!", {icon: '1'});
                    }
                    zTreeNodeShow(targetNode);
                    DGG.Layer.loading.done();
                });
            }
        }, function () {
            zTreeNodeShow(targetNode);
        });
    });

}

/**
 * 子节点展示
 * @param treeNode
 */
function zTreeNodeShow(treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj("leftTree");
    $.ajax({
        type: "post",
        url: "/tree_book/tree_book_nodes.do",
        data: {id: treeNode.id},
        dataType: "json",
        async: true,
        success: function (result) {
            treeObj.removeChildNodes(treeNode);
            if (result != null) {
                var data = result.data;
                if (data != null && data.length != 0) {
                    if (treeNode == undefined) {
                        treeObj.addNodes(null, data, true);// 如果是根节点，那么就在null后面加载数据
                    } else {
                        treeObj.addNodes(treeNode, data, true);//如果是加载子节点，那么就是父节点下面加载
                    }
                    treeObj.expandNode(treeNode, true, false, false);// 将新获取的子节点展开
                }
            }
        }
    });
}

/**
 * 新增字典
 */
function addNode(type) {
    restForm();
    var parentId = -1;
    var pName = "无上级";
    $("#sb_btn").text("新增根字典");
    if (1 == type) {
        var zTree = $.fn.zTree.getZTreeObj("leftTree");
        var nodes = zTree.getSelectedNodes();
        if (nodes.length <= 0) {
            DGG.Layer.message("请选择一个字典", {icon: '0'});
            return;
        } else {
            $("#sb_btn").text("新增子字典");
            var treeNode = nodes[0];
            parentId = treeNode.id;
            pName = treeNode.name;
        }
    }
    $("[name='pName']").val(pName);
    $("[name='pid']").val(parentId);
}

//点击树事件
function zTreeOnClick(event, treeId, treeNode) {
    DGG.Layer.loading.start();
    restForm();
    var parentNode = treeNode.getParentNode();
    var pName = "无上级";
    var pId = -1;
    if (null != parentNode) {
        pName = parentNode.name;
        pId = parentNode.id;
    }
    $("[name='pName']").val(pName);
    $("[name='pid']").val(pId);
    $.get(sysInfo.basePath + "/condition/getCondition.do", {treeBookId: treeNode.id}, function (result) {
        var data = result.data;
        if (result.code == 0) {

            //console.log(result.data);//actionType

            $("#actionType option").each(function () {
                $(this).removeAttr("selected");
                if (typeof(result.data.action) != "undefined" && $(this).val() == result.data.action.actionType){
                    $(this).attr("selected","");
                }
            });


            $("#className").text(treeNode.name);
            $("[name=treebookId]").val(treeNode.id);
            //将相关输入框隐藏



            $("[name=optionsLabelTr]").css("display", "none");
            $("[name=optionsLabelTr] input").attr('name', "");

            $("[name=optionsValueTr]").css("display", "none");
            $("[name=optionsValueTr] input").attr('name', "");

            $("#addOptionsTr").css("display", "none");
            $("#addOptionsTr input").attr('name', "");

            $("#inputTr").css("display", "none");
            $("#inputTr input").attr('name', "");

            $("#attrTr").css("display", "none");
            $("#attrTr input").attr('name', "");

            $("#dataTr").css("display", "none");
            $("#dataTr select").attr('name', "");

        } else if (code == 1) {
            DGG.Layer.message(data.msg, {icon: '0'});
        }
    });
    tableUpdate(treeNode.id);
    DGG.Layer.loading.done();
}

/**
 * 点击树事件，查询子节点的列表
 * @param bookId 当前点中的节点id
 */
function tableUpdate(bookId) {
    $("#bookTableDiv").show();
    var table = layui.table;
    table.render({
        elem: '#bookTable'
        , url: '/tree_book/table_search_books.do'
        , where: {id: bookId}
        , page: true
        , id: "idTest"
        , cols: [[
            {field: 'name', title: '字典名称', event: 'rolSign'}
            , {field: 'code', title: '字典编码', event: 'rolSign'}
            , {field: 'sort', title: '排序', event: 'rolSign'}
            , {field: 'status', title: '状态', event: 'rolSign'}
            , {field: 'description', title: '字典描述', event: 'rolSign'}
        ]],
        done: function () {
            $("[data-field='status']").children().each(function () {
                if ($(this).text() == '0') {
                    $(this).text("禁用")
                } else if ($(this).text() == '1') {
                    $(this).text("启用")
                }
            })
        }
    });
}

/**
 * 提交保存
 */
function saveSub() {
    var treeBookVal = $("[name=treebookId]").val();

    if (treeBookVal == "") {
        DGG.Layer.message("请选择一个分类", {icon: '0'});
        return;
    }
    var actText = $("#actionType").find("option:selected").val();

    if (actText == "") {
        DGG.Layer.message("ActionType不能为空", {icon: '0'});
        return;
    }
    var formObj = $("#form").serialize();

    DGG.Layer.confirm('确认提示', {'title': '确定保存?'}, function () {
        DGG.Layer.loading.start();
        $.ajax({
            type: "post",
            url: sysInfo.basePath + '/condition/addCondition.do',
            data: formObj,
            dataType: "json",
            success: function (result) {
                DGG.Layer.loading.done();
                if (result.code == 0) {
                    DGG.Layer.message("报存成功", {icon: '1'});
                    window.location.reload();
                } else {
                    DGG.Layer.message(result.msg, {icon: '0'});
                }

            }
        });
    });
}

/**
 * 修改
 */
function modifySub() {
    var treeBookVal = $("[name=treebookId]").val();

    if (treeBookVal == "") {
        DGG.Layer.message("请选择一个分类", {icon: '0'});
        return;
    }
    var actText = $("#actionType").find("option:selected").val();

    if (actText == "") {
        DGG.Layer.message("ActionType不能为空", {icon: '0'});
        return;
    }
    var formObj = $("#form").serialize();

    DGG.Layer.confirm('确认提示', {'title': '确定修改?'}, function () {
        DGG.Layer.loading.start();
        $.ajax({
            type: "post",
            url: sysInfo.basePath + '/condition/modifyCondition.do',
            data: formObj,
            dataType: "json",
            success: function (result) {
                DGG.Layer.loading.done();
                if (result.code == 0) {
                    DGG.Layer.message("修改成功", {icon: '1'});
                    window.location.reload();
                } else {
                    DGG.Layer.message(result.msg, {icon: '0'});
                }


            }
        });
    });
}

/**
 * 删除
 */
function deleteSub() {
    var treeBookVal = $("[name=treebookId]").val();

    if (treeBookVal == "") {
        DGG.Layer.message("请选择一个分类", {icon: '0'});
        return;
    }
    var formObj = $("#form").serialize();

    DGG.Layer.confirm('确认提示', {'title': '确定删除?'}, function () {
        DGG.Layer.loading.start();
        $.ajax({
            type: "post",
            url: sysInfo.basePath + '/condition/deleteCondition.do',
            data: formObj,
            dataType: "json",
            success: function (result) {
                DGG.Layer.loading.done();
                if (result.code == 0) {
                    DGG.Layer.message("删除成功", {icon: '1'});
                    window.location.reload();
                } else {
                    DGG.Layer.message(result.msg, {icon: '0'});
                }


            }
        });
    });
}

function restForm() {
    $("#form")[0].reset();
    $("[name='id']").val("");
    $("[name='pName']").val("");
    $("[name='pid']").val("");
    $("[name='name']").val("");
    $("[name='code']").val("");
    $("[name='code']").removeAttr("readonly");
    $("[name='sort']").val("0");
    $("[name='description']").val("");
    $("[name='ext1']").val("");
    $("[name='ext2']").val("");
    $("[name='ext3']").val("");
    $("[name='ext4']").val("");
    $("[name='ext5']").val("");
    $("[name='status']").val(1);
    $("#sb_btn").text("新增");
}

//更新字典缓存
function cacheUpdate() {
    DGG.Layer.confirm('确认提示', {'title': '确定更新缓存?'}, function () {
        DGG.Layer.loading.start();
        $.ajax({
            type: "get",
            url: "/condition/deleteRedisCache.do",
            dataType: "json",
            success: function (result) {
                DGG.Layer.loading.done();
                if (result.result == 1) {
                    DGG.Layer.message(result.msg, {icon: '1'});
                } else {
                    DGG.Layer.message(result.msg, {icon: '2'});
                }

            }
        });
    });
}

function bodyLoad() {
    DGG.Layer.loading.start();

    actionTypeOnload();
    inputType();
    optionsFrom()

    DGG.Layer.loading.done();

}

/**
 * 显示action
 */
function actionTypeOnload() {
    $.ajax({
        type: "get",
        url: sysInfo.basePath + "/condition/getActionType.do",
        dataType: "json",
        success: function (result) {
            var optionValues = result.data;
            for (var i = 0; i < optionValues.length; i++) {
                $("#actionType").append("<option data-limit=\"" + optionValues[i].ext1 + "\" value='" + optionValues[i].name + "'>" + optionValues[i].ext2 + "</option>");
            }
        }
    });
}

function inputType() {
    $.ajax({
        type: "get",
        url: sysInfo.basePath + "/condition/getInputType.do",
        dataType: "json",
        success: function (result) {
            var optionValues = result.data;
            for (var i = 0; i < optionValues.length; i++) {
                $("#inputType").append("<option data-limit=\"" + optionValues[i].ext1 + "\" value='" + optionValues[i].name + "'>" + optionValues[i].ext2 + "</option>");
            }
        }
    });
}

/**
 * 数据来源获取
 */
function optionsFrom() {
    $.ajax({
        type: "get",
        url: sysInfo.basePath + "/condition/getOptionsFrom.do",
        dataType: "json",
        success: function (result) {
            var optionValues = result.data;
            for (var i = 0; i < optionValues.length; i++) {
                $("#optionsFrom").append("<option  value='" + optionValues[i].code + "'>" + optionValues[i].name + "</option>");
            }
        }
    });
}

/**
 * 显示actiontype动态
 */
function actionTypeChange(obj) {


    $("[name=optionsLabelTr]").css("display", "none");
    $("[name=optionsLabelTr] input").attr('name', "");

    $("[name=optionsValueTr]").css("display", "none");
    $("[name=optionsValueTr] input").attr('name', "");

    $("#addOptionsTr").css("display", "none");
    $("#addOptionsTr input").attr('name', "");

    $("#inputTr").css("display", "none");
    $("#inputTr input").attr('name', "");

    $("#attrTr").css("display", "none");
    $("#attrTr input").attr('name', "");

    $("#dataTr").css("display", "none");
    $("#dataTr select").attr('name', "");


    var selectObj = $(obj);
    var option = selectObj.find("option:selected");

    $("[name=typeValue]").val(option.data("limit"));

    if (option.val() == 5) { //其他来源
        $("[name=optionsLabelTr]").css("display", "block");
        $("[name=optionsLabelTr] input").attr('name', $("[name=optionsLabelTr] input").data('name'));

        $("[name=optionsValueTr]").css("display", "block");
        $("[name=optionsValueTr] input").attr('name', $("[name=optionsValueTr] input").data('name'));

        $("#addOptionsTr").css("display", "block");
        $("#addOptionsTr input").attr('name', $("#addOptionsTr input").data('name'));
    } else if (option.val() == 4) { //文本包含任一/文本不包含所有
        $("#inputTr").css("display", "block");
        $("#inputTr input").attr('name', $("#inputTr input").data('name'));

    } else if (option.val() == 3) {//等于任一/不等于所有
        $("#inputTr").css("display", "block");
        $("#inputTr input").attr('name', $("#inputTr input").data('name'));

    } else if (option.val() == 2) {//包含任一/不包含所有
        $("#inputTr").css("display", "block");
        $("#inputTr input").attr('name', $("#inputTr input").data('name'));

    } else if (option.val() == 1) {//有/没有
        // $("#inputTr").css("display", "block");
        //$("#inputTr input").attr('name', $("#inputTr input").data('name'));


    } else if (option.val() == 6) {//时间范围，数字范围
        $("#inputTr").css("display", "block");
        $("#inputTr input").attr('name', $("#inputTr input").data('name'));

    }

}


function inputTypeChange(obj) {
    $("#attrTr").css("display", "none");
    $("#attrTr input").attr('name', "");

    $("#dataTr").css("display", "none");
    $("#dataTr select").attr('name', "");

    var selectObj = $(obj);
    var option = selectObj.find("option:selected");

    if (option.val() == 2 || option.val() == 4) {
        $("#dataTr").css("display", "block");
        $("#optionsFrom").attr("name", $("#optionsFrom").data("name"));
        $("#inputType").attr("name", $("#inputType").data("name"));
    } else if (option.val() == 5 || option.val() == 3) {
        $("#attrTr").css("display", "block");
        $("#inputType").attr("name", $("#inputType").data("name"));
        $("[data-name=placeholder]").attr("name", $("[data-name=placeholder]").data("name"));
        if (option.val() == 5) {
            $("[data-name=max]").attr("name", $("[data-name=max]").data("name"));
            $("[data-name=min]").attr("name", $("[data-name=min]").data("name"));

            $("[data-name=max]").css("display", "block");
            $("[data-name=min]").css("display", "block");

            $("[data-name=maxKeywordLength]").css("display", "none");
            $("[data-name=maxLength]").css("display", "none");
            $("[data-name=maxKeywordLength]").attr("name", "");
            $("[data-name=maxLength]").attr("name", "");

        } else if (option.val() == 3) {
            $("[data-name=maxKeywordLength]").attr("name", $("[data-name=maxKeywordLength]").data("name"));
            $("[data-name=maxLength]").attr("name", $("[data-name=maxLength]").data("name"));

            $("[data-name=maxKeywordLength]").css("display", "block");
            $("[data-name=maxLength]").css("display", "block");


            $("[data-name=max]").css("display", "none");
            $("[data-name=min]").css("display", "none");
            $("[data-name=max]").attr("name", "");
            $("[data-name=min]").attr("name", "");
        }
    }else if (option.val() == 6){
        $("#inputType").attr("name", $("#inputType").data("name"));
    }else{
        $("#inputType").attr("name", "");
    }
}

function addTr() {
    var tr = "<tr name=\"optionsLabelTr\" style=\"display:block;\">\n" +
        "                        <th>label名称：</th>\n" +
        "                        <td>\n" +
        "                            <input class=\"layui-input\" name=\"labelName\"  data-name = \"labelName\" style=\"width: 300px\" type=\"text\" placeholder=\"请输入label的值，如7到30天\"/>\n" +
        "                        </td>\n" +
        "                    </tr>\n" +
        "                    <tr name=\"optionsValueTr\" style=\"display:block;\">\n" +
        "                        <th>value值：</th>\n" +
        "                        <td>\n" +
        "                            <input class=\"layui-input\" name=\"value\" data-name = \"value\"  style=\"width: 300px\" type=\"text\" placeholder=\"请输入label值所对应的字符串，如{gte: 7, lt: 30}}\"/>\n" +
        "                        </td>\n" +
        "                    </tr>";

    $("#addOptionsTr").before(tr);

}
