layui.use(['form', 'layer'], function () {
    var form = layui.form;
    var layer = layui.layer;
    getOpratePermissions($("#permsNameSearch").val());

    form.on("submit(addFilter)", function (data) {
        //验证通脱提交表单
        console.info(data.field);
        add(data.field);
    });
    form.on("submit(delFilter)", function (data) {
        //验证通脱提交表单
        console.info(data.field);
        del(data.field);
    });
    form.on("submit(updateFilter)", function (data) {
        //验证通脱提交表单
        console.info(data.field);
        update(data.field);
    });

});

function add(data) {
    DGG.confire("确认添加？", function () {
        if (checkAll()) {
            return;
        }
        $.post("/role/opratepermission/add.do", data, function (result) {
            if (result.code == 1) {
                DGG.msg_errors(result.msg);
            } else {
                DGG.msg_succeed("操作成功");
                location.reload();
            }
        });
    })
}

function del(data) {
    DGG.confire(function () {
        if (data.operatePermissionId == null || data.operatePermissionId == "") {
            DGG.msg_errors("请选择需要删除的权限");
            return;
        }
        $.post("/role/opratepermission/delete.do", data, function (result) {
//                console.log(result)
            if (result.code == 1) {
                DGG.msg_errors(result.msg);
            } else {
                DGG.msg_succeed(result.data);
                location.reload();
            }
        });
    })

}

function update(data) {
    DGG.confire("确认修改？", function () {
        if (checkAll()) {
            return;
        }
        if (data.operatePermissionId == null || data.operatePermissionId == "") {
            DGG.msg_errors("请选择需要更新的权限");
            return;
        }
        $.post("/role/opratepermission/update.do", data, function (result) {
            if (result.code == 1) {
                DGG.msg_errors(result.msg);
            } else {
                DGG.msg_succeed(result.data);
                location.reload();
            }
        });
    })
}

$("#permsNameSearch").on('input', function () {
    getOpratePermissions($("#permsNameSearch").val());
});

function getOpratePermissions(operatePermissionName) {
    $.post("/role/opratepermission/search.do", {"operatePermissionName": operatePermissionName}, function (result) {
        if (result.code == 0) {
            //$("#role_ul li").remove();
            $("#role_ul").html("");
            for (var i = 0; i < result.data.length; i++) {
                var temphtml = $("#role_ul").html() + '<li> <a  href="/role/opratepermission/index.html?operatePermissionId=' + result.data[i].operatePermissionId
                    + '&operatePermissionName=' + $("#permsNameSearch").val()
                    + '"> ' + result.data[i].operatePermissionName + '(' + result.data[i].code + ')</a> </li>';
                $("#role_ul").html(temphtml);
            }
        }
    });
}

function checkAll() {
    var operatePermissionName = $.trim($("#operatePermissionName").val());//100
    var code = $.trim($("#code").val());//100
    var remark = $.trim($("#remark").val());

    if (operatePermissionName == undefined || operatePermissionName == "" || operatePermissionName == null) {
        DGG.msg_errors("请填写权限名称");
        return true;
    }
    if (operatePermissionName.length > 20) {
        DGG.msg_errors("权限名称最大限制20，输入超过限制，请重新输入");
        return true;
    }
    if (code == undefined || code == "" || code == null) {
        DGG.msg_errors("请填写权限编号");
        return true;
    }
    if (code.length > 100) {
        DGG.msg_errors("权限编号最大限制100，输入超过限制，请重新输入");
        return true;
    }
    if (remark.length > 200) {
        DGG.msg_errors("权限备注最大限制200，输入超过限制，请重新输入");
        return true;
    }
}