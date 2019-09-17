/**
 * 提交保存
 */
function saveSub() {

    var actText = $("[name=applicableIndustryName]").val();

    if (actText == "") {
        DGG.Layer.message("行业分类名称不能为空！！", {icon: '0'});
        return;
    }
    var formObj = $("#form").serialize();
    DGG.Layer.confirm('确认提示', {'title': '确定保存?'}, function () {
        DGG.Layer.loading.start();
        $.ajax({
            type: "post",
            url: sysInfo.basePath + '/industry/add.do',
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
                    DGG.Layer.message(result.msg ? result.msg : "提交失败", {icon: '0'});

                }
            }
        });
    });
}

function modifySub() {

    var id = $("[name=id]").val();
    if (id == "") {
        DGG.Layer.message("请选择要修改的行业分类", {icon: '0'});
        return;
    }
    var actText = $("[name=applicableIndustryName]").val();

    if (actText == "") {
        DGG.Layer.message("行业分类名称不能为空！！", {icon: '0'});
        return;
    }
    var formObj = $("#form").serialize();
    DGG.Layer.confirm('确认提示', {'title': '确定修改?'}, function () {
        DGG.Layer.loading.start();
        $.ajax({
            type: "post",
            url: sysInfo.basePath + '/industry/modify.do',
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
                    DGG.Layer.message(result.msg ? result.msg : "提交失败", {icon: '0'});
                }
            }
        });
    });
}

function deleteSub() {

    var id = $("[name=id]").val();
    if (id == "") {
        DGG.Layer.message("请选择要修改的行业分类", {icon: '0'});
        return;
    }
    var formObj = $("#form").serialize();
    DGG.Layer.confirm('确认提示', {'title': '确定删除?'}, function () {
        DGG.Layer.loading.start();
        $.ajax({
            type: "post",
            url: sysInfo.basePath + '/industry/delete.do',
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
                    DGG.Layer.message(result.msg, {icon: '0'});
                }

            }
        });
    });
}

function clickIndustry(obj) {

    $("[name=id]").val($(obj).attr("data-id"));
    $("[name=applicableIndustryName]").val($(obj).text());
    $("[name=sort]").val($(obj).attr("data-sort"));

}