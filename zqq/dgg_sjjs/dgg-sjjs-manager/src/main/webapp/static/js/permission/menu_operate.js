layui.use(['table', 'ztree'], function () {
    DGG.loading();
    var ztree = layui.ztree;
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
        menuOperateTableShow();
        operateTableShow();
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
    menuOperateTableShow();
    operateTableShow();
    DGG.loading("close");
}

function getCheckMenuId() {
    var treeObj = $.fn.zTree.getZTreeObj("leftTree");
    var nodes = treeObj.getSelectedNodes();
    var menuId = "";
    if (nodes.length > 0) {
        menuId = nodes[0].id;
    }
    return menuId;
}

function menuOperateTableShow(flag) {
    var url = "/menu/operate/menu_operate.do?menuId=" + getCheckMenuId();
    if (flag == 1) {
        url += "&menuOperateName=" + $("#menuOperateName").val();
    }
    var menu_operate_table = layui.table;
    menu_operate_table.render({
        elem: '#menu_operate_table'
        , url: url
        , id: "menu_operate_table_id"
        , cols: [[
            {checkbox: true, fixed: true}
            , {field: 'operatePermissionName', minWidth: 150, title: '权限名称'}
            , {field: 'code', minWidth: 100, title: '权限编码'}
        ]]
    });
}

function operateTableShow(flag) {
    var url = '/menu/operate/operate.do?menuId=' + getCheckMenuId();
    if (flag == 1) {
        url += '&operateName=' + $("#operateName").val();
    }
    var operate_table = layui.table;
    operate_table.render({
        elem: '#operate_table'
        , url: url
        , id: "operate_table_id"
        , cols: [[
            {checkbox: true, fixed: true}
            , {field: 'operatePermissionName', minWidth: 150, title: '权限名称'}
            , {field: 'code', minWidth: 100, title: '权限编码'}
        ]]
    });
}

/**
 * 移除菜单权限
 */
function move() {
    var table = layui.table;
    var checkStatus = table.checkStatus('menu_operate_table_id')
        , data = checkStatus.data;
    var operateIds = "";
    for (var a = 0; a < data.length; a++) {
        operateIds += data[a].operatePermissionId + ",";
    }
    if (operateIds == "") {
        DGG.msg_errors("请选择要移除的权限");
        return;
    }
    $.post("/menu/operate/move.do", {"operateIds": operateIds, "menuId": getCheckMenuId()}, function (result) {
        if (result.code == 1) {
            DGG.msg_errors(result.msg);
        } else {
            DGG.msg_succeed(result.data);
            table.reload('menu_operate_table_id');
            table.reload('operate_table_id');
        }
    });

}

/**
 * 清楚角色所有的监控站点
 */
function moveAll() {
    var table = layui.table;
    $.post("/menu/operate/move_all.do", {"menuId": getCheckMenuId()}, function (result) {
        if (result.code == 1) {
            DGG.msg_errors(result.msg);
        } else {
            DGG.msg_succeed(result.data);
            table.reload('menu_operate_table_id');
            table.reload('operate_table_id');
        }
    });

}

/**
 * 给菜单分配所有权限
 */
function saveAll() {
    var table = layui.table;
    $.post("/menu/operate/save_all.do", {"menuId": getCheckMenuId()}, function (result) {
        if (result.code == 1) {
            DGG.msg_errors(result.msg);
        } else {
            DGG.msg_succeed(result.data);
            table.reload('menu_operate_table_id');
            table.reload('operate_table_id');
        }
    });
}

/**
 * 给菜单分配权限
 */
function save() {
    var table = layui.table;
    var checkStatus = table.checkStatus('operate_table_id')
        , data = checkStatus.data;
    var operateIds = "";
    for (var a = 0; a < data.length; a++) {
        operateIds += data[a].operatePermissionId + ",";
    }
    if (operateIds == "") {
        DGG.msg_errors("请选择要添加的权限");
        return;
    }
    $.post("/menu/operate/save.do", {"operateIds": operateIds, "menuId": getCheckMenuId()}, function (result) {
        if (result.code == 1) {
            DGG.msg_errors(result.msg);
        } else {
            DGG.msg_succeed(result.data);
            table.reload('menu_operate_table_id');
            table.reload('operate_table_id');
        }
    });

}

function tableSearch(flag) {
    if (flag == 1) {
        operateTableShow(1);
    } else {
        menuOperateTableShow(1);
    }
}

//input value 改变事件
$("#roleSearch").on('input', function () {
    $.post("/role/role_manager/role_search.do", {"roleName": $("#roleSearch").val()}, function (result) {
        if (result.code == 0) {
            //$("#role_ul li").remove();
            $("#role_ul").html("");
            for (var i = 0; i < result.data.length; i++) {
                var temphtml = $("#role_ul").html() + '<li> <a  href="/role/monitor/index.html?roleId=' + result.data[i].roleId
                    + '&roleName=' + $("#roleSearch").val()
                    + '"> ' + result.data[i].roleName + '</a> </li>';
                $("#role_ul").html(temphtml);
            }
        }
    });
});
