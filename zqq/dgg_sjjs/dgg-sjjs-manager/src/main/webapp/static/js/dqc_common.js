// 序列化成obj
// name与value为空或空字符串将忽略
$.fn.serializeJson = function () {
    var obj = {}, mulNumType = ['checkbox', 'radio'], notValue = ['button', 'submit', 'reset'];
    var getMulNumTypeSelector = function (mulTypes) {
        var s = '';
        for (var i in mulTypes) {
            s += 'input[type=' + mulTypes[i] + ']:checked,';
        }
        return s;
    }
    var getNotValueSelector = function (notValues) {
        var s = 'input';
        for (var i in notValues) {
            s += '[type!=' + notValues[i] + ']';
        }
        return s;
    }
    this.find(getMulNumTypeSelector(mulNumType) + getNotValueSelector([].concat(mulNumType, notValue)) + ',select,textarea').each(function () {
        var $this = $(this), n = $this.attr('name'), v = $this.val();
        if (!n || !v) {
            return;
        }
        if (obj[n] == null || obj[n] == undefined) {
            obj[n] = v;
            return;
        }
        if (obj[n] != null && !$.isArray(obj[n])) {
            obj[n] = [obj[n], v];
            return;
        }
        if (obj[n] != null && $.isArray(obj[n])) {
            obj[n].push(v);
            return;
        }
    });
    return obj;
};

// 清空搜索
function cleanSearch() {
    var $s = $('.titleSearch');
    $s.find('input').val('');
    $s.find('select').val('');
    $s.find('select').val(null).trigger("change");
    $s.find('input').attr("data-id", '');
}

var DateUtils = DateUtils || {};
DateUtils.DateConf = {
    DAY: 24 * 3600 * 1000, //天
    HOUR: 3600 * 1000, //小时
    MINUTE: 60 * 1000, //分
    SECOND: 1000 //秒
}

// 转换时间戳的差值 为 x天X时x分x秒
DateUtils.transTime = function (time) {
    if (time == null) {
        return null;
    }
    var d = Math.floor(time / this.DateConf.DAY),
        h = Math.floor((time % this.DateConf.DAY) / this.DateConf.HOUR),
        m = Math.floor((time % this.DateConf.HOUR) / this.DateConf.MINUTE),
        s = Math.floor((time % this.DateConf.MINUTE) / this.DateConf.SECOND);
    return (d > 0 ? '' + d + '天' : '')
        + (h > 0 ? '' + h + '小时' : '')
        + (m > 0 ? '' + m + '分' : '')
        + (s >= 0 ? '' + s + "秒" : '');
}

Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}

// 隐藏电话
function hidePhone(phoneStr) {
    if (null == phoneStr || undefined == phoneStr) {
        return
    }
    ;
    var length = phoneStr.length;
    if (length == 0) {
        return "";
    }
    if (length == 1) {
        return "*";
    }
    if (length > 10) {
        return phoneStr.substr(0, 3) + "****" + phoneStr.substr(length - 4, length);
    }
    if (length <= 10 && length > 3) {
        return phoneStr.substr(0, length - 4) + "****";
    }
    return phoneStr.substr(0, 1) + "*";

}

//导出excell  url
function export_table(url, params) {
    var form = $("<form>");//定义一个form表单
    form.attr("style", "display:none");
    form.attr("target", "_self");
    form.attr("method", "post");
    form.attr("accept-charset", "utf-8");
    if (params) {
        jQuery.each(params, function (i, val) {
            var $input = $(document.createElement('input'));
            $input.attr('name', i).val(val != null ? val : '');
            form.append($input);
        });
    }
    form.attr("action", sysInfo.basePath + url);
    $("body").append(form);//将表单放置在web
    form.submit();//表单提交

}

/**
 * 跳转到子生产订单详情
 * @param orderNo 编号
 * @param orderId id
 */
function openOrderInfoDetail(orderNo, orderId, btnArray) {
    var btnArray = new Array();
    var beforeTableName = window.top.getName();
    var beforeTableUrl = window.location.href;
    $(".table-conter").find("a").each(function () {
        var $this = $(this);
        var btn = $this.attr("btnauthCode");
        if (btn) {
            btnArray.push(btn);
        }
    })
    if (undefined == orderId && undefined == orderNo) {
        var parent = $(event.target).parents("tr").eq(0);
        orderId = parent.find("[name='id']").val();
        orderNo = parent.find("[name='no']").text();
    }
    window.parent.updateTab('生产详情:' + orderNo, sysInfo.basePath +
        "/sc/general/scBaseInfo/index.do?id=" + orderId + "&btnArray=" + btnArray + "&beforeTableName=" + beforeTableName + "&beforeTableUrl=" + beforeTableUrl);
}

/**
 * 跳转到销售主订单详情页面
 */
function openBusinessDetail(orderNo, orderId) {
    var type = 2;
    window.parent.updateTab('销售详情:' + orderNo, orfApiUrl + "/orf/order/orderDetailAudited.do?id=" + orderId + "&type=" + type);
}

/**
 * 弹出生产工单详情页面
 */
function openWorkOrderDetail(id, from) {
    if (undefined == id) {
        var parent = $(event.target).parents("tr").eq(0);
        id = parent.find("input[name='id']").val();
    }
    var url = sysInfo.basePath + "/sc/general/workOrder/detail.html?id=" + id;
    if (from == undefined) {
        from = this.window;
    } else if (from == "parent") {
        from = this.parent.window;
    }
    from.DGG.Layer.open(url, {
        title: "工单详情",
        area: ["50%", "90%"],
        btn: "",
        cancel: function (index, obj) {
            //    $("#btn-search-list", parent.document).trigger("click");
        }
    });
}

/**
 * 弹出新建工单页面,根据传入参数,初始化新建页面的一些数据(生产订单编号,客户信息)
 * 默认在id为myTable的表格中查找数据
 */
function newWorkOrderFromTable(tableId) {
    if (tableId == undefined) {
        tableId = "myTable";
    }
    var datas = $("#" + tableId).dataTableExtend("getCheck");

    var type, id, entity;
    if (datas.length > 0) {
        var data = datas[0];
        id = data.id;
        if ('scWorkorderNo' in data) {
            type = "workOrder";
            entity = "工单";
        }
        else if ('scProductOrderNo' in data) {
            type = "scSubOrder";
            entity = "生产子订单";
        }
    }
    if (datas.length > 1) {
        DGG.Layer.message("仅能选择一条" + entity + "!", {icon: 0});
        return;
    }
    newWorkOrder(id, type);

}

/**
 * 新建工单
 */
function newWorkOrder(id, type) {
    var para = "";
    if (type == undefined) type = "scSubOrder";
    if (id != undefined) {
        para = "?type=" + type + "&id=" + id;
    }
    DGG.Layer.open(sysInfo.basePath + "/sc/general/workOrder/new.html" + para, {
        title: "新建工单",
        area: ["45%", "90%"],
        btn: "",
        id: "layer-new-order",
        success: function (selector, index) {
        }
    });


}

/**
 * 加载<select>标签的option，根据code属性传入字典code
 */
$.fn.loadSelectOptions = function (option) {
    var setting = {};
    setting.codeLabel = "code";
    setting.after = undefined;
    setting.type = "append";
    $.extend(setting, option);
    var url = "/sc/general/com/getSelectOption.do";
    $(this).each(function (i, e) {
        var $select = $(this);
        var code = $select.attr(setting.codeLabel);
        if (code == undefined || code == "") return;
        $.ajax({
            url: url,
            type: "POST",
            data: {code: code},
            success: function (data) {
                if (setting.type == "replace") {
                    $select.empty();
                }
                $select.append(data);
                if (setting.after != undefined) {
                    setting.after($select);

                }
            }
        });
    });

}
/**
 *快速筛选tab效果
 * @param option
 */
$.fn.activeTab = function (option) {
    $(this).on("click", "a", function () {
        var a = $(this);
        if (a.hasClass("active")) return;
        a.parent().find("a").removeClass("active");
        a.addClass("active");
        if (option != undefined && option.fn != undefined) {
            option.fn(this);
        }

    });
}

/**
 * 重新加载表格数据,重置start 和limit参数
 */
function dataTableReload() {
    table.ajax.reload();
}

/**
 * 刷新表格页面数据
 */
function dataTableReFresh() {
    $('#myTable', document).dataTableExtend("reloadFalse");
}

/**
 * 清空 .titleSearch（默认） 的筛选项
 */
function clearSearchKeys(selector) {
    if (selector == undefined) {
        selector = ".titleSearch";
    }
    $(selector).clearForm();
    $(selector).find('select').val(null).trigger("change");


}

/**
 * 清空表单元素(包括不可见的),忽略具有fixed属性的
 * @param selector
 */
$.fn.clearForm = function () {
    $(this).find("input,select,textarea").each(function (i, e) {
        e = $(e);
        var fixed = e.attr("fixed");
        if (fixed == undefined) {
            e.val("");
        }
    });
}
