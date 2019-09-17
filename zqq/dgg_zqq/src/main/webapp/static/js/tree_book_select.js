/**
 * 字典数据下拉
 */
var treeBookSelect = treeBookSelect || {};

treeBookSelect.label = 'tree-book-select';
treeBookSelect.default = {
    status: 1,
    type: 1,
    level: null
}

treeBookSelect.createOptions = function (optArr, haveTip) {
    var opts = haveTip ? '<option value="">请选择</option>' : '';
    for (var i in optArr) {
        opts += '<option value="' + optArr[i].code + '">' + optArr[i].name + '</option>'
    }
    return opts;
}

treeBookSelect.initSelect = function (dom, callback) {
    var $d = $(dom);
    var q = $.extend({}, this.default, $d.data(), {code: $d.attr(this.label)});
    treeBookService.getData(q.code, q.status, q.type, q.level, function (data) {
        if (data == null) {
            return;
        }
        $d.empty();
        $d.append(treeBookSelect.createOptions(data, true));
        if (callback && (typeof callback == 'function')) {
            callback($d);
        }
    });
}

treeBookSelect.init = function (callback) {
    var sel = 'select[' + treeBookSelect.label + ']';
    $(sel).each(function () {
        var $this = $(this);
        treeBookSelect.initSelect($this, function () {
            var val = $this.attr('value');
            if (val == null) {
                val = $this.attr('data-value');
            }
            $this.val(val == null ? '' : val);

            if (callback && (typeof callback == 'function')) {
                callback($this);
            }
        });

    });
}


$(function () {
    treeBookSelect.init(function (dom) {

    });
});








