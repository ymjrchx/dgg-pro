layui.use(['table', 'ztree', 'code'], function () {
    DGG.loading();
    var table = layui.table;
    var ztree = layui.ztree;
    layui.code(); //引用code方法
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
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
        async: {
            enable: true,
            url: "/role/usermanager/orgnextnode.do",
            autoParam: ["id=orgId"],
            dataFilter: filter
        },
        callback: {
            onClick: zTreeOnClick
        }
    };

    $.post("/role/usermanager/orgnextnode.do",{orgId:null},function (result) {
        ztree.ztreeInit('#leftTree', setting, result.data);
    });

    DGG.loading("close");
    /**
     * 监听工具条
     */
    table.on('tool(dataTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'editRole') {
            showEditPopUp(obj.data.id);
        }
    });

    $(document).on('keydown', function (e) {  //document为当前元素，限制范围，如果不限制的话会一直有事件
        if (e.keyCode == 13) {
            var mainKey = $("#userKeyWordInput").val();
            if (null != mainKey) {
                userSearch();
            } else {
                layer.msg('请输入内容后在进行搜索！');
            }
        }
    })
});

function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    return childNodes.data;
}

var table = layui.table;

function moveUser(obj) {
    var _this = $(obj).attr("class");
    var arrow = "";
    if (_this == "addUser" || _this == "addAll") {
        arrow = 'left';
    } else if (_this == "removeUser" || _this == "removeAll") {
        arrow = 'right';
    }
    ;
    $(".allot-" + arrow + "").find(".user-list li").each(function () {
        var hasClass = $(this).hasClass("active");
        if (_this == "removeAll" || _this == "addAll") {
            var _thisTxt = $(this).text();
            $(this).remove();
            if (arrow == "left") {
                $(".allot-right ul").append("<li data-id=" + this.dataset.id + ">" + _thisTxt + "</li>")
            } else if (arrow == "right") {
                $(".allot-left ul").append("<li data-id=" + this.dataset.id + ">" + _thisTxt + "</li>")
            }
        } else {
            if (hasClass) {
                var _thisTxt = $(this).text();
                $(this).remove();
                if (arrow == "left") {
                    $(".allot-right ul").append("<li data-id=" + this.dataset.id + ">" + _thisTxt + "</li>")
                } else if (arrow == "right") {
                    $(".allot-left ul").append("<li data-id=" + this.dataset.id + ">" + _thisTxt + "</li>")
                }
            }
        }
    });
}

/**
 * 点击树事件
 */
function zTreeOnClick(event, treeId, treeNode) {
    DGG.loading();
    tableUpdate(treeNode.id);
    $("#userKeyWordInput").val('');
    $("#addRoles").css("display", "");
    $("#delRoles").css("display", "");
    DGG.loading("close");
}

/**
 * 刷新数据表
 * @param id
 */
function tableUpdate(id) {
    table.render({
        elem: '#userTable'
        , url: '/role/usermanager/userpage.do?orgId=' + id
        , page: true
        , text: {none: '暂无人员信息'}
        , id: "userTable"
        , cols: [[
            {checkbox: true, fixed: true}
            , {title: '序号', templet: '#indexTpl'}
            , {field: 'id', title: 'ID', display: 'none'}
            , {field: 'loginName', title: '工号'}
            , {field: 'name', title: '姓名'}
            , {field: 'sex', title: '性别'}
            , {field: 'orgName', title: '部门'}
            , {field: 'roleName', title: '角色', templet: '#roleTpl'}
            , {field: 'locked', title: '用户状态', templet: '#lockedTpl'}
            , {title: "操作", toolbar: '#dataTableFilter'}
        ]]
        , done: function () {
            $("[data-field='id']").css('display', 'none');
        }
    });
}

/**
 * 用户搜索
 */
function userSearch() {
    var selectCode = $("#searchValue").val();
    var mainKey = $("#userKeyWordInput").val();
    var treeObj = $.fn.zTree.getZTreeObj("leftTree");
    var nodes = treeObj.getSelectedNodes();
    var layer = layui.layer;
    var orgId;

    if (selectCode == 1 && nodes.length == 0) {
        orgId = 1;
    } else if (selectCode == 2 && nodes.length == 0) {
        orgId = null;
    } else if (selectCode == 3 && nodes.length == 0) {
        orgId = 1;
    } else {
        orgId = nodes[0].id;
    }
    if ("" == mainKey || undefined == mainKey) {
        layer.msg('请输入内容后再进行搜索');
        return;
    }
    table.render({
        elem: '#userTable'
        , url: '/role/usermanager/search.do?selectCode=' + selectCode + '&mainKey=' + mainKey + '&orgId=' + orgId
        , page: true
        , id: "userTable"
        , text: {none: '暂无搜索结果'}
        , cols: [[
            {checkbox: true, fixed: true}
            , {title: '序号', templet: '#indexTpl'}
            , {field: 'id', title: 'ID'}
            , {field: 'loginName', title: '工号'}
            , {field: 'name', title: '姓名'}
            , {field: 'sex', title: '性别'}
            , {field: 'orgName', title: '部门'}
            , {field: 'roleName', title: '角色', templet: '#roleTpl'}
            , {field: 'locked', title: '用户状态', templet: '#lockedTpl'}
            , {title: "操作", toolbar: '#dataTableFilter'}
        ]]
        , done: function () {
            $("[data-field='locked']").children().each(function () {
                if ($(this).text() == '0') {
                    $(this).text("正常")
                } else if ($(this).text() == '1') {
                    $(this).text("锁定")
                } else if ($(this).text() == '2') {
                    $(this).text("离职")
                }
            })
            $("[data-field='id']").css('display', 'none');
            $("#addRoles").css("display", "");
            $("#delRoles").css("display", "");
        }
    });
}

/**
 * 点击分配角色后的弹框
 */
function showEditPopUp(id) {
    layers.capture('分配角色', 'userAllot', '600px', ["保存", "取消"], function yes(index, layero) {
        var list = $("#ownedRoleList li");
        var ownedRoleIds = "";
        if (null != list.length) {
            for (var i = 0; i < list.length; i++) {
                if (i == list.length - 1) {
                    ownedRoleIds += list[i].dataset.id;
                } else {
                    ownedRoleIds += list[i].dataset.id + ",";
                }
            }
        }
        $.ajax({
            url: sysInfo.basePath + "/role/role_manager/editroles.do?id=" + id + "&roleIds=" + ownedRoleIds,
            type: "POST",
            dataType: "json",
            success: function (obj) {
                if (obj.success == false) {
                    layer.msg(obj.msg);
                } else {
                    layer.msg("保存成功！");
                    table.reload("userTable");
                }
            }
        })

        layer.close(index);
    });
    $("#allRoleList").empty();
    $("#ownedRoleList").empty();
    $.ajax({
        url: sysInfo.basePath + "/role/role_manager/showownedroles.do?id=" + id,
        type: "POST",
        dataType: "json",
        success: function (obj) {
            var allRoleList = "";
            var ownedRoleList = "";
            if (null != obj.data.allRole) {
                for (var i = 0; i < obj.data.allRole.length; i++) {
                    allRoleList += "<li data-id=" + obj.data.allRole[i].roleId + ">" + obj.data.allRole[i].roleName + "</li>";
                }
                $("#allRoleList").append(allRoleList);
            }
            if (null != obj.data.ownedRole) {
                for (var i = 0; i < obj.data.ownedRole.length; i++) {
                    ownedRoleList += "<li data-id=" + obj.data.ownedRole[i].roleId + ">" + obj.data.ownedRole[i].roleName + "</li>";
                }
                $("#ownedRoleList").append(ownedRoleList);
            }
            DGG.search({inputEle: '#inputAllRoleList', exportEle: '#allRoleList'})
        }
    });
}

function showPopUp(obj) {
    var checkStatus = table.checkStatus('userTable');
    var ids = new Array();
    var msg = "";
    if (checkStatus.data.length > 0) {
        var title = "";
        var url = "";
        if (obj.id == "addRoles") {
            msg = "请选择需要添加的角色！";
            title = "添加角色";
            url = "/role/role_manager/addroles.do?ids=";
        } else {
            msg = "请选择需要移除的角色！";
            title = "移除角色";
            url = "/role/role_manager/delroles.do?ids=";
        }
        var data = checkStatus.data;
        for (var i = 0; i < data.length; i++) {
            ids.push(data[i].id);
        }

        $.ajax({
            url: sysInfo.basePath + "/role/role_manager/showallroles.do",
            type: "POST",
            dataType: "json",
            success: function (obj) {
                var allRoleList = "";
                if (null != obj.data) {
                    for (var i = 0; i < obj.data.length; i++) {
                        allRoleList += "<li data-id=" + obj.data[i].roleId + ">" + obj.data[i].roleName + "</li>";
                    }
                    $('#allRoles li').each(function () {
                        $(this).remove();
                    });
                    $("#allRoles").append(allRoleList);
                }
                DGG.search({inputEle: '#inputAllRoles', exportEle: '#allRoles'})
            }
        });

        layers.capture(title, 'roleAllot', '300px', ["保存", "取消"], function yes(index, layero) {
            var selectedRoleIds = "";
            $('#allRoles li').each(function () {
                var hasClass = $(this).hasClass("active");
                if (hasClass) {
                    selectedRoleIds += this.dataset.id + ",";
                }
            });
            if (selectedRoleIds.length == 0) {
                layer.msg(msg);
                return;
            } else {
                selectedRoleIds = selectedRoleIds.substring(0, selectedRoleIds.length - 1);
            }

            $.ajax({
                url: sysInfo.basePath + url + ids + "&roleIds=" + selectedRoleIds,
                type: "POST",
                dataType: "json",
                success: function (obj) {
                    if (obj.success == false) {
                        layer.msg(obj.msg);
                    } else {
                        layer.msg("保存成功！");
                        table.reload("userTable");
                    }
                }
            })

            layer.close(index);
        });
    } else {
        layer.msg('请选择需要分配角色的人员！');
        return;
    }
}

$(function () {
    $(".user-list").on("click", "li", function () {
        var has = $(this).hasClass("active");
        if (has) {
            $(this).removeClass("active");
        } else {
            $(this).addClass("active");
        }
    });
})