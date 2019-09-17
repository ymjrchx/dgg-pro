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
    $.post(sysInfo.basePath + "/tree_book/tree_book_nodes.do", {id: -1}, function (result) {
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
    $.post(sysInfo.basePath + "/tree_book/one_book.do", {id: treeNode.id}, function (result) {
        var data = result.data;
        if (result.code == 0) {
            $("[name='id']").val(data.id);
            $("[name='name']").val(data.name);
            $("[name='code']").val(data.code);
            // $("[name='code']").attr("readonly", "readonly");
            $("[name='sort']").val(data.sort);
            $("[name='description']").val(data.description);
            $("[name='ext1']").val(data.ext1);
            $("[name='ext2']").val(data.ext2);
            $("[name='ext3']").val(data.ext3);
            $("[name='ext4']").val(data.ext4);
            $("[name='ext5']").val(data.ext5);
            $("[name='status']").val(data.status);
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
    var formObj = $("#form").serialize();
    if (!isNull('[name="code"]')) return;
    if (!isNull('[name="name"]')) return;
    if (!isInterval('[name="sort"]', 999, 0)) return;
    if (!isNull('[name="status"]')) return;
    DGG.Layer.confirm('确认提示', {'title': '确定保存?'}, function () {
        DGG.Layer.loading.start();
        $.ajax({
            type: "post",
            url: sysInfo.basePath + '/tree_book/save_tree_book.do',
            data: formObj,
            dataType: "json",
            success: function (result) {
                DGG.Layer.loading.done();
                if (result.code == 0) {
                    DGG.Layer.message(result.msg, {icon: '1'});
                    var zTree = $.fn.zTree.getZTreeObj("leftTree");
                    var id = $("#form").find("[name='id']").val();//判断是新增还是修改
                    var resNode = result.data;
                    if ("" == id) {
                        var parentId = resNode.pid;
                        var node = {id: resNode.id, name: resNode.name};
                        if (parentId == 0) {
                            zTree.addNodes(null, node);
                        } else {
                            var parentZNode = zTree.getNodeByParam("id", parentId, null); //获取父字典
                            zTree.addNodes(parentZNode, node);
                        }
                    } else {
                        var nodes = zTree.getNodeByParam("id", id, null);
                        nodes.name = resNode.name;
                        zTree.updateNode(nodes);
                    }
                    restForm();
                } else {
                    DGG.Layer.message(result.msg, {icon: '2'});
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
    $("[name='ext5']").val(1);
    $("[name='status']").val(1);
    $("#sb_btn").text("保存");
}

//更新字典缓存
function cacheUpdate() {
    DGG.Layer.confirm('确认提示', {'title': '确定更新缓存?'}, function () {
        DGG.Layer.loading.start();
        $.ajax({
            type: "get",
            url: "/tree_book/update_tree_book_cache.do",
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