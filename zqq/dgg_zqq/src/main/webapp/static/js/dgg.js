/*
 * Author xtt
 * 2018.03.02
 */
(function (){
    "use strict";
    var
        self = this,
        D;
    D = {
        __BUILD_TIEM: '2018-3-7',
        Version: '0.0.1',
        _mix: function (target, resource){
            for (var name in resource) {
                if(resource.hasOwnProperty(name)){
                    target[name] = resource[name];
                }
            }
        }
    };
    'DGG' in self || ( self['DGG'] = D);
}).call(this);

(function (W, $, D){
    "use strict";
    var
        $body = $('body'),
        // iframe DOM
        $iframeWarp = $('#iframe-warp'),
        _getIframeHmtl;
    /**
     * Git Iframe_Hmtl
     */
    _getIframeHmtl = function (url){
        var hei = ($(W).height() - $iframeWarp.offset().top - 10) - 29;
        return ' <iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:' + hei + 'px;"></iframe>';
    };
    D._mix(D, {
        /**
         * iframe
         * @param title
         * @param url
         * @param closable
         * @returns {boolean}
         */
        addTab: function (title, url, closable){
            if($iframeWarp.tabs("exists", title)){
                $iframeWarp.tabs("select", title);
                return !1;
            }
            url += (url.indexOf("?") > -1 ? "&" : "?") + "is_first_request=1";
            $iframeWarp.tabs('add', {
                title: title,
                content: _getIframeHmtl(url),
                closable: closable || !0
            });
        }
    });
    $body
        .on('click', '.dgg-tool-menu [data-url]', function (){
            
            var $this = $(this), menuData = $this.attr('data-url');
            try {
                // 将字符串解析成json对象(new Function("","return "+json))();
                menuData = new Function('return ' + menuData)();
            } catch (e) {
                console.error(' menuData element property data-url error' + menuData);
            }
            var options = $.extend({
                elem: this,
                data: []
            }, menuData);
            D.addTab(options.name, options.url);
        });

})(window, jQuery, DGG);

(function(W,$,D){
    D._mix(D,{
        /**
         * 操作成功消息提示
         * @param m 提示信息文字内容
         * @param options layer自带参数以对象的形式传入
         * @param c 点击“确认”后操作回调
         * @param d 点击“取消”后操作回调
         * @param e 关闭加载层，需传的参“close”
         */
        loading:function(close){
            if(close=='close') {
                layer.closeAll('loading');
                return false;
            }
            layer.load();
        },
        msg_succeed:function(m,options){
            var o={icon: 1};
            o = $.extend({}, o , options);
            layer.msg(m, o);
        },
        msg_errors:function (m,options) {
            var o={icon: 2};
            o = $.extend({}, o , options);
            layer.msg(m, o);
        },
        msg_alert:function (m,options) {
            var o={
                title:'操作提醒'
            };
            o = $.extend({}, o , options);
            var index=layer.alert(m,o)
            return index;
        },
        confire:function(m,c,options,d){
            var e=$.isFunction(m),
                o={
                    title:'操作提醒',
                    btn: ["&#x786E;&#x5B9A;", "&#x53D6;&#x6D88;"],
                    icon:0
                };
            e && (d=options,options=c,c=m,m='删除后数据无法恢复，您确定要进行删除吗？')
            o = $.extend({}, o , options);
            var index=layer.confirm(m, o, function(i){
                c && $.isFunction(c) && c.call(this),layer.close(i)
            }, function(){
                d && $.isFunction(d) && d.call(this)
            });
        }
    })

})(window,jQuery,DGG);

(function(W,$,D){
    D._mix(D,{
        /**
         * 前端搜索
         * @param options 带两个参数，inputEle输入框元素的id或class，exportEle展示筛选结果列表id或class
         */
        search:function(options){
            var o={
                inputEle:'',
                exportEle:''
            }
            o = $.extend({}, o , options);
            $(o.inputEle).keyup(function(){
                var i=$.trim($(o.inputEle).val());
                i=='' && $(o.exportEle).children().show();
                $(o.exportEle+' li:contains(' + i + ')').show();
                $(o.exportEle+' li:not(:contains('+i+'))').hide();
            })
        }
    })
})(window,jQuery,DGG);
