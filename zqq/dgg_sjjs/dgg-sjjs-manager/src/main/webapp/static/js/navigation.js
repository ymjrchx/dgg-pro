// 导航业务数据
var navigation = navigation || {};
navigation.url = sysInfo.basePath + '/navigation/getDataByParentCode.do';
navigation.label = 'three-level-select';
navigation.initLabel = 'data-init';

navigation.getData = function (code, callback) {
    $.ajax({
        type: "POST",
        url: navigation.url,
        data: {
            code: code,
            type: 1,
            dataType: 2
        },
        success: function (result) {
            var data = result.code == 0 ? result.data : null;
            if (callback && (typeof callback == 'function')) {
                callback(data);
            }
        }
    });
}
navigation.createOptions = function (optArr, haveTip) {
    var opts = haveTip ? '<option value="">请选择</option>' : '';
    for (var i in optArr) {
        opts += '<option value="' + optArr[i].code + '">' + optArr[i].name + '</option>'
    }
    return opts;
}

navigation.initSelect = function (dom, callback) {
    var $d = $(dom);
    var q = $.extend({}, $d.data(), {code: $d.attr(navigation.label)});
    navigation.getData(q.code, function (data) {
        if (data == null) {
            return;
        }
        $d.empty();
        $d.append(navigation.createOptions(data, true));
        if (callback && (typeof callback == 'function')) {
            callback($d);
        }
    });
}

navigation.initChangeEvent = function (dom) {
    var $d = $(dom), $sonSel = $d.next();
    if ($sonSel == null || $sonSel.length == 0) {
        return;
    }
    $(dom).change(function () {
        var $this = $(this), v = $this.val(), $sonSel = $this.next();
        $this.attr(navigation.initLabel, 'true');
        if (v) {
            $sonSel.attr(navigation.label, v);
            navigation.initSelect($sonSel, function (d) {
                var $d = $(d);
                var val = $d.attr('value');
                $d.removeAttr('value');
                if (val == null) {
                    val = $d.attr('data-value');
                    $d.removeAttr('data-value');
                }
                $d.val(val == null ? '' : val);
                $d.change();
            });
        } else {
            $sonSel.empty();
            $sonSel.append('<option value="">请选择</option>');
            $sonSel.val('');
        }
        if (!$sonSel.attr(navigation.initLabel)) {
            navigation.initChangeEvent($sonSel);
        }
    });
}

navigation.init = function (callback) {
    var sel = 'select[' + navigation.label + ']';
    $(sel).each(function () {
        var $this = $(this);
        navigation.initSelect($this, function (dom) {
            var $dom = $(dom);
            var val = $dom.attr('value');
            $dom.removeAttr('value');
            if (val == null) {
                val = $this.attr('data-value');
                $dom.removeAttr('data-value');
            }
            $dom.val(val == null ? '' : val);
            navigation.initChangeEvent($dom);
            $dom.change();

            if (callback && (typeof callback == 'function')) {
                callback($dom);
            }


        });

    });
}

$(function () {
    navigation.init(function (dom) {

    });
});