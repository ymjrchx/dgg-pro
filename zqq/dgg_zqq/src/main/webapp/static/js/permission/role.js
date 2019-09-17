layui.use(['form', 'layer', 'ztree'], function () {
    DGG.loading();
    var layer = layui.layer,
        form = layui.form,
        ztree = layui.ztree, zTreeObj;

    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        view: {
            showLine: false,
            showIcon: false,
            selectedMulti: true
        },
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: {"Y": "ps", "Y": "ps"},
            nocheckInherit: true
        },
        callback: {
            onClick: zTreeOnClick,
            //onNodeCreated: zTreeOnNodeCreated
            onAsyncSuccess: onAsyncSuccess
        },
        async: {
            enable: true,
            url: "/role/role_manager/menu_next_node.do",
            autoParam: ["id=menuId"],
            otherParam: {"roleId": $("#roleId").val()},
            dataFilter: filter
        }
    };
    $.post("/role/role_manager/menu_next_node.do",{menuId:null,roleId:$("#roleId").val()},function (result) {
        ztree.ztreeInit('#menuTree', setting, result.data);
        var treeObj = $.fn.zTree.getZTreeObj("menuTree");
        var nodes = treeObj.getNodesByParam("id",1,null);
        expandNodes(nodes);
    });
    DGG.loading("close");

    getOperatePermissions($("#roleId").val(),$("#operatePermissionSearch").val(),form);
    getRoles($("#roleSearch").val());

    $("#operatePermissionSearch").on('input', function () {
        getOperatePermissions($("#roleId").val(),$("#operatePermissionSearch").val(),form);
    });
    return false;
});
function onAsyncSuccess(event, treeId, treeNode, msg) {
    expandNodes(treeNode.children);
}

/**
 * 递归方法让默认勾选的树展开
 * @param nodes
 */
function expandNodes(nodes) {
    if (!nodes) return;
    var zTree = $.fn.zTree.getZTreeObj("menuTree");
    for (var i=0, l=nodes.length; i<l; i++) {
        if (nodes[i].isParent && nodes[i].checked) {
            zTree.expandNode(nodes[i], true, false, false);//展开节点就会调用后台查询子节点
            expandNodes(nodes[i].children);//递归
        }
    }
}

function getOperatePermissions(roleId,operatePermissionName,form) {
    $.post("/role/role_manager/operate_permission_search.do", {
        "roleId": roleId,
        "operatePermissionName": operatePermissionName
    }, function (result) {
        if (result.code == 0) {
            //$("#role_ul li").remove();
            $("#operatePermissionDiv").html("");
            for (var i = 0; i < result.data.length; i++) {
                var temphtml =
                    '<p><input type="checkbox" id="operatePermission" name="operatePermission" lay-skin="primary" ';
                if (result.data[i].roleOpm == 1) {
                    temphtml += 'checked="checked" '
                }
                temphtml += ' value="' + result.data[i].operatePermissionId + '" title="' +
                    result.data[i].operatePermissionName + '(' + result.data[i].code + ')">';
                temphtml += '</p>'
                $("#operatePermissionDiv").append(temphtml);
                form.render('checkbox')
            }
        }
    });
}

function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    return childNodes.data;
}

/**
 * 点击树事件
 */
function zTreeOnClick(event, treeId, treeNode) {
    operateTableShow();
}

function getNodeIds() {
    var nodeIds = "";
    var treeObj = $.fn.zTree.getZTreeObj("menuTree");
    var nodes = treeObj.getCheckedNodes(true);

    for (var i = 0; i < nodes.length; i++) {
        nodeIds += nodes[i].id + ",";

    }
    return nodeIds;
}

function getOperatePermission() {
    var check_val = "";
    $("input:checkbox[name='operatePermission']:checked").each(function () { // 遍历name=test的多选框
        // 每一个被选中项的值
        check_val += $(this).val() + ",";
    });
    return check_val;
}

function getOperateTable() {
    var table = layui.table;
    var checkStatus = table.checkStatus('operate_table_id')
        , data = checkStatus.data;
    var operateIds = "";
    for (var a = 0; a < data.length; a++) {
        operateIds += data[a].operatePermissionId + ",";
    }
    return operateIds;
}

/**
 * 添加角色
 */
function addRole() {
    DGG.confire("确认添加？", function () {
        if (checkAll()) {
            return;
        }
        var nodeIds = getNodeIds();
        var operatePermission = getOperatePermission() + getOperateTable();

        $.get("/role/role_manager/add_role.do",
            {
                "roleName": $("#roleNameForm").val(),
                "remark": $("#roleRemakForm").val(),
                "nodeIds": nodeIds,
                "operatePermission": operatePermission
            },
            function (result) {
                if (result.code == 1) {
                    DGG.msg_errors(result.msg);
                } else {
                    DGG.msg_succeed(result.data);
                    location.reload(true);
                }
            });
    });

}

/**
 * 更新角色
 */
function updateRole() {
    DGG.confire("确认修改？", function () {
        if (checkAll()) {
            return;
        }
        var nodeIds = getNodeIds();
        var operatePermission = getOperatePermission() + getOperateTable();
        $.get("/role/role_manager/update_role.do",
            {
                "roleId": $("#roleId").val(),
                "roleName": $("#roleNameForm").val(),
                "remark": $("#roleRemakForm").val(),
                "nodeIds": nodeIds,
                "operatePermission": operatePermission
            },
            function (result) {
                if (result.code == 1) {
                    DGG.msg_errors(result.msg);
                } else {
                    DGG.msg_succeed(result.data);
                    location.reload(true);
                }
            });
    });

}

/**
 * 保存角色菜单关联权限
 * */
function saveRolePms() {
    var operatePermission = getOperatePermission() + getOperateTable();
    $.get("/role/role_manager/save_rolepms.do",
        {
            "roleId": $("#roleId").val(),
            "menuId": getCheckMenuId(),
            "operatePermission": operatePermission
        },
        function (result) {
            if (result.code == 1) {
                DGG.msg_errors(result.msg);
            } else {
                DGG.msg_succeed(result.data);
                var table = layui.table;
                table.reload("operate_table_id");
            }
        });
}

/**
 * 启用和禁用按钮
 */
function updateRoleEnable() {
    var roleId = $("#roleEnableId").val();
    if (roleId == null || roleId == '') {
        DGG.msg_errors("请选择角色");
        return;
    }
    var warnflag = "${roleForm.enable}";
    var warn;
    if (warnflag == 'false') {
        warn = "确认启用？";
    } else {
        warn = "确认禁用？";
    }
    DGG.confire(warn, function () {
        $.post("/role/role_manager/update_role_enable.do", {"roleId": $("#roleEnableId").val()}, function (result) {
            if (result.code) {
                DGG.msg_errors(result.msg);
            } else {
                DGG.msg_succeed(result.data);
                location.reload(true);
            }
        });
    });
}

function getCheckMenuId() {
    var treeObj = $.fn.zTree.getZTreeObj("menuTree");
    var nodes = treeObj.getSelectedNodes();
    var menuId = "";
    if (nodes.length > 0) {
        menuId = nodes[0].id;
    }
    return menuId;
}

function operateTableShow() {
    var url = '/role/role_manager/menu_operate.do?menuId=' + getCheckMenuId();
    var roleId = $("#roleId").val();
    if (roleId.length > 0) {
        url += "&roleId=" + roleId;
    }
    var operate_table = layui.table;
    operate_table.render({
        elem: '#operate_table'
        , url: url
        , id: "operate_table_id"
        , cols: [[
            {checkbox: true}
            , {field: 'operatePermissionName', width: 246, title: '权限名称'}
            , {field: 'code', width: 220, title: '权限编码'}
        ]]
    });
}

//input value 改变事件
$("#roleSearch").on('input', function () {
    getRoles($("#roleSearch").val());
});

function getRoles(roleName) {
    $.post("/role/role_manager/role_search.do", {"roleName": roleName}, function (result) {
        if (result.code == 0) {
            //$("#role_ul li").remove();
            $("#role_ul").html("");
            for (var i = 0; i < result.data.length; i++) {
                var temphtml = $("#role_ul").html() + '<li> <a  href="/role/role_manager/index.html?roleId=' + result.data[i].roleId
                    + '&roleName=' + $("#roleSearch").val()
                    + '"> ' + result.data[i].roleName + '</a> </li>';
                $("#role_ul").html(temphtml);
            }
        }
    });
}

function checkAll() {
    var roleNameForm = $.trim($("#roleNameForm").val());//100
    var roleRemakForm = $.trim($("#roleRemakForm").val());
    if (roleNameForm == undefined || roleNameForm == "" || roleNameForm == null) {
        DGG.msg_errors("请填写角色名称");
        return true;
    }
    if (roleNameForm.length > 20) {
        DGG.msg_errors("角色名称最大20，输入超过限制，请重新输入");
        return true;
    }
    if (roleRemakForm.length > 200) {
        DGG.msg_errors("角色备注最大200，输入超过限制，请重新输入");
        return true;
    }
}