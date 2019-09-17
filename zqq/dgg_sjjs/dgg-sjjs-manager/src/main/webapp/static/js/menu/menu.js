layui.use(['ztree', 'table'], function () {
    DGG.loading();
    var ztree = layui.ztree;
    var layer = layui.layer;
    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        view: {
            showLine: false,
            showIcon: false
        },
        callback: {
            onClick: zTreeOnClick
        },
        async: {
            enable: true,
            url: "/menu/menu_next_node.do",
            autoParam: ["id=menuId"],
            dataFilter: filter
        }
    };
    $.post("/menu/menu_next_node.do",{menuId:null},function (result) {
        ztree.ztreeInit('#leftTree', setting, result.data);
    });


    DGG.loading("close");
});

function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    return childNodes.data;
}

/**
 * 点击树事件
 */
function zTreeOnClick(event, treeId, treeNode) {
    DGG.loading();
    $.post("/menu/menu_info.do", {menuId: treeNode.id}, function (result) {
        if (result.code == 0) {
            $("#menuId").val(result.data.menuId);
            $("#menuName").val(result.data.menuName);
            $("#menuUrl").val(result.data.menuUrl);
            $("#icon").val(result.data.icon);
            $("#sortNum").val(result.data.sortNum);
            $("#menuCode").val(result.data.menuCode);
            $("#remark").val(result.data.remark);
            $("#updateBtn").attr("class", "layui-btn layui-btn-green");
            $("#updateBtn").attr("onclick", "updateMenu();");
        }
    });
    tableUpdate(treeNode.id);
    DGG.loading("close");
}

function reloadMenuJsp(node) {
    zTreeNodeShow(node);
    var table = layui.table;
    table.reload("idTest");
}

/**
 * 添加按钮功能
 */
function addMenu() {
    if (checkAll()) {
        return;
    }
    //var layer = layui.layer;
    var treeObj = $.fn.zTree.getZTreeObj("leftTree");
    var nodes = treeObj.getSelectedNodes();
    //获得节点
    var nodeIds = getNodesIds();
    if (nodeIds.length == 0) {
        DGG.msg_errors("请选择菜单");
        return;
    }
    $("#ancestorIds").val(nodeIds);

    $.post("/menu/add_menu.do", $("#menuForm").serialize(), function (result) {
        if (result.code == 1) {
            DGG.msg_errors(result.msg);
        } else {
            var node;
            if (nodes.length == 0) {
                node = treeObj.getNodeByParam("id", 1);
            } else {
                node = nodes[0];
            }
            DGG.msg_succeed('添加成功');
            reloadMenuJsp(node);
        }
    });
}

/**
 * 更新按钮功能
 */
function updateMenu() {
    if (checkAll()) {
        return;
    }
    var treeObj = $.fn.zTree.getZTreeObj("leftTree");
    //获得节点
    var nodeIds = getNodesIds();
    if (nodeIds.length == 0) {
        DGG.msg_errors("请选择菜单");
        return;
    }
    $("#ancestorIds").val(nodeIds);
    $.post("/menu/update_menu.do", $("#menuForm").serialize(), function (result) {
        if (result.code == 1) {
            DGG.msg_errors(result.msg);
        } else {
            var node = treeObj.getNodeByParam("id", $("#menuId").val());
            var nodeNew;
            if (node.pId == null) {
                nodeNew = treeObj.getNodeByParam("id", 1);
            } else {
                nodeNew = treeObj.getNodeByParam("id", node.pId);
            }
            DGG.msg_succeed('修改成功');
            reloadMenuJsp(nodeNew);
        }
    });
}

/**
 * 删除
 */
function deleteBtn() {
    var table = layui.table;
    var checkStatus = table.checkStatus('idTest')
        , data = checkStatus.data;
    var treeObj = $.fn.zTree.getZTreeObj("leftTree");
    var nodes = treeObj.getSelectedNodes();
    var menuIds = '';
    var msg = '删除后数据无法恢复，确定要进行删除吗?';
    for (var a = 0; a < data.length; a++) {
        menuIds += data[a].menuId + ',';
        var nodeTemp = treeObj.getNodeByParam("id", data[a].menuId);
        if (nodeTemp.isParent) {
            msg = '删除后数据无法恢复，确认删除选中菜单及其子菜单吗？';
        }
    }
    if (menuIds == null || menuIds == '') {
        DGG.msg_errors("请选择删除的菜单");
        return;
    }
    DGG.confire(msg, function () {
        $.post("/menu/delete_menu.do", {"menuIds": menuIds}, function (result) {
            if (result.code == 1) {
                DGG.msg_errors(result.msg);
            } else {
                if (nodes[0].id == 1) {
                    reloadMenuJsp(nodes[0]);
                    return;
                }
                var node = treeObj.getNodeByParam("id", nodes[0].pId)
                reloadMenuJsp(node);
            }
        });
    })

}

/**
 * 获得选择节点的父节点集合
 */
function getNodesIds() {
    var nodesIds = "";
    var treeObj = $.fn.zTree.getZTreeObj("leftTree");
    var nodes = treeObj.getSelectedNodes();
    if (nodes.length == 0) {
        return $("#menuId").val();
    }
    while (nodes[0].level > 0) {
        nodesIds += nodes[0].id + ",";
        nodes = treeObj.getNodesByParam("id", nodes[0].pId);
    }
    nodesIds += nodes[0].id + ",";
    return nodesIds;
}

function zTreeNodeShow(treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj("leftTree");
    treeObj.removeChildNodes(treeNode);
    $.ajax({
        type: "post",
        url: "/menu/menu_next_node.do?menuId=" + treeNode.id,
        data: "",
        dataType: "json",
        async: true,
        success: function (result) {
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

function tableUpdate(menuId) {
    var table = layui.table;
    table.render({
        elem: '#menuTable'
        , url: '/menu/menu_page.do?menuId=' + menuId
        , page: true
        , id: "idTest"
        , cols: [[
            {checkbox: true, fixed: true}
            , {field: 'menuName', title: '菜单名称'}
            , {field: 'menuUrl', title: '菜单地址'}
            , {field: 'icon', title: '菜单图标'}
            , {field: 'sortNum', title: '排序'}
            , {field: 'menuCode', title: '菜单编号'}
            , {field: 'remark', title: '菜单备注'}
        ]]
    });
}

function tableSearch() {
    DGG.loading();
    var treeObj = $.fn.zTree.getZTreeObj("leftTree");
    var nodes = treeObj.getSelectedNodes();
    var node;
    if (nodes.length == 0) {
        node = treeObj.getNodeByParam("id", 1);
    } else {
        node = nodes[0];
    }
    var menuUrlSearch = $("#menuUrlSearch").val();
    var menuNameSearch = $("#menuNameSearch").val();
    var table = layui.table;
    table.render({
        elem: '#menuTable'
        , url: '/menu/menu_page.do?menuId=' + node.id + '&menuUrl=' + menuUrlSearch + '&menuName=' + menuNameSearch
        , page: true
        , id: "idTest"
        , cols: [[
            {checkbox: true, fixed: true}
            , {field: 'menuName', title: '菜单名称'}
            , {field: 'menuUrl', title: '菜单地址'}
            , {field: 'icon', title: '菜单图标'}
            , {field: 'sortNum', title: '排序'}
            , {field: 'menuCode', title: '菜单编号'}
            , {field: 'remark', title: '菜单备注'}
        ]]
    });
    DGG.loading("close");
}

function checkAll() {
    var menuName = $.trim($("#menuName").val());
    var menuCode = $.trim($("#menuCode").val());
    var sortNum = $.trim($("#sortNum").val());
    var menuUrl = $.trim($("#menuUrl").val());
    var icon = $.trim($("#icon").val());
    var remark = $.trim($("#remark").val());

    if (menuName == undefined || menuName == "" || menuName == null) {
        DGG.msg_errors("请填写菜单名称");
        return true;
    }
    if (menuName.length > 8) {
        DGG.msg_errors("菜单名称最大限制8，输入超过限制，请重新输入");
        return true;
    }
    if (menuCode == undefined || menuCode == "" || menuCode == null) {
        DGG.msg_errors("请填写菜单编号");
        return true;
    }
    if (menuCode.length > 100) {
        DGG.msg_errors("菜单编号最大限制100，输入超过限制，请重新输入");
        return true;
    }
    if (sortNum == undefined || sortNum == "" || sortNum == null) {
        DGG.msg_errors("请填写菜单排序");
        return true;
    }

    var reg = /^\d{1,11}$/;
    if (!reg.test(sortNum)) {
        DGG.msg_errors("菜单排序不合法，请输入全数字的排序");
        return true;
    }

    if (menuUrl.length > 300) {
        DGG.msg_errors("菜单地址最大限制300，输入超过限制，请重新输入");
        return true;
    }
    if (icon.length > 100) {
        DGG.msg_errors("菜单图标最大限制100，输入超过限制，请重新输入");
        return true;
    }
    if (remark.length > 200) {
        DGG.msg_errors("菜单备注最大限制200，输入超过限制，请重新输入");
        return true;
    }
}