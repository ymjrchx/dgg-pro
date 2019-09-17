/**
 * 提交保存
 */
function saveSub() {

    var actText = $("[name=industryId]").find("option:selected").val();

    if (actText == "") {
        DGG.Layer.message("行业分类不能为空！！", {icon: '0'});
        return;
    }
    if (!isNull('[name="name"]')) return;
    if (!isNull('[name="filter"]')) return;

    var formObj = $("#form").serialize();
    DGG.Layer.confirm('确认提示', {'title': '确定保存?'}, function () {
        DGG.Layer.loading.start();
        $.ajax({
            type: "post",
            url: sysInfo.basePath + '/conditiongroup/add.do',
            data: formObj,
            dataType: "json",
            success: function (result) {
                if (result.code == 0) {
                    DGG.Layer.loading.done();
                    DGG.Layer.message("保存成功!", {icon: '1'});
                    window.location.reload();
                }
                if (result.code == 1) {
                    DGG.Layer.loading.done();
                    DGG.Layer.message(result.msg, {icon: '0'});
                }
            }
        });
    });
}

function modifySub() {

    var id = $("[name=id]").val();
    if (id == "") {
        DGG.Layer.message("请选择要修改的行业模板", {icon: '0'});
        return;
    }
    var actText = $("[name=industryId]").find("option:selected").val();

    if (actText == "") {
        DGG.Layer.message("行业分类不能为空！！", {icon: '0'});
        return;
    }
    if (!isNull('[name="name"]')) return;
    if (!isNull('[name="filter"]')) return;

    var formObj = $("#form").serialize();
    DGG.Layer.confirm('确认提示', {'title': '确定修改?'}, function () {
        DGG.Layer.loading.start();
        $.ajax({
            type: "post",
            url: sysInfo.basePath + '/conditiongroup/modify.do',
            data: formObj,
            dataType: "json",
            success: function (result) {

                if (result.code == 0) {
                    DGG.Layer.loading.done();
                    DGG.Layer.message("修改成功!", {icon: '1'});
                    window.location.reload();
                }
                if (result.code == 1) {
                    DGG.Layer.loading.done();
                    DGG.Layer.message(result.msg, {icon: '0'});
                }
            }
        });
    });
}

function deleteSub() {

    var id = $("[name=id]").val();
    if (id == "") {
        DGG.Layer.message("请选择要删除的行业模板", {icon: '0'});
        return;
    }
    var formObj = $("#form").serialize();
    DGG.Layer.confirm('确认提示', {'title': '确定删除?'}, function () {
        DGG.Layer.loading.start();
        $.ajax({
            type: "post",
            url: sysInfo.basePath + '/conditiongroup/delete.do',
            data: formObj,
            dataType: "json",
            success: function (result) {
                if (result.code == 0) {
                    DGG.Layer.loading.done();
                    DGG.Layer.message("删除成功!", {icon: '1'});
                    window.location.reload();
                }
                if (result.code == 1) {
                    DGG.Layer.loading.done();
                    DGG.Layer.message("删除失败!", {icon: '0'});
                }

            }
        });
    });
}

function clickGroup(obj) {
    $("[name=id]").val($(obj).attr("data-id"));
    $("[name=name]").val($(obj).text());
    $("[name=sort]").val($(obj).attr("data-sort"));
    $("[name=filter]").val($(obj).attr("data-filter"));
    $("[name=info]").val($(obj).attr("data-info"));
    $("[name=sort]").val($(obj).attr("data-sort"));

    $("[name=industryId] option").each(function () {
        $(this).removeAttr("selected");
    });
    $("[name=industryId] option").each(function () {
        if ($(this).val()== $(obj).attr("data-industryId")){
            $(this).attr("selected","");
        }
    });


}

function cacheUpdate() {
    DGG.Layer.confirm('确认提示', {'title': '确定更新缓存?'}, function () {
        DGG.Layer.loading.start();
        $.ajax({
            type: "get",
            url: "/conditiongroup/deleteCache.do",
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

function cacheUpdateCompany() {
    DGG.Layer.confirm('确认提示', {'title': '确定更新缓存?'}, function () {
        DGG.Layer.loading.start();
        $.ajax({
            type: "get",
            url: "/conditiongroup/deleteCompanyCache.do",
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