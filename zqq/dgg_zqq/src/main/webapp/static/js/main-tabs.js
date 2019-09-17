function addTab(title, url, closable) {
    // console.log(closable)
    if (url == "") {
        return false;
    }
    // if (title.length > 5) {
    //     title = title.substr(0, 4);
    // }
    closable = closable == undefined ? true : closable;
    if ($('#iframe-warp').tabs('exists', title)) {
        $('#iframe-warp').tabs('select', title);
    } else {
        //url后参数添加 is_first_request=1 这个条件代表页面是第一次请求。2017年9月7日 09:51:00
        url += url.indexOf("?") > -1 ? "&" : "?";
        url += "is_first_request=1";
        var content = getIframeHmtl(url);
        $('#iframe-warp').tabs('add', {
            title: title,
            content: content,
            closable: closable
//			closable:false
        });
        if(!closable) {
            $('#tabs').append('<li> <a href="javascript:void(0)" onclick="addTab(\''+title+'\',\''+url+'\')">'+title+'</a></li>');
        }else {
            $('#tabs').append('<li> <a href="javascript:void(0)" onclick="addTab(\''+title+'\',\''+url+'\')">'+title+'</a><a href="javascript:void(0)" class="layui-icon tabs-close" onclick="closeThis(this,\''+title+'\')">&#x1006;</a></li>');
        }
    }
}

//刷新
function refreshTab() {
    var url = "";
    $(".panel").each(function () {
        var thisobj = $(this);
        if ($(this).css("display") == "block") {
            url = thisobj.children("div").children("iframe").attr("src");
        }
    });
    var current_tab = $('#iframe-warp').tabs('getSelected');
    $('#iframe-warp').tabs('update', {
        tab: current_tab,
        options: {
            content: getIframeHmtl(url)
        }
    });
}

function closeAll() {
    // $(".tabs li").each(function(index, obj) {
    // 	//获取所有可关闭的选项卡
    // 	var tab = $(".tabs-closable", this).text();
    // 	debugger
    // 	$(".easyui-tabs").tabs('close', tab);
    // });
    $(".tabs li a.tabs-close").click();
}

//关闭当前tabs,刷新制定tabs
function tabsClose() {
    var close_tab = $("#iframe-warp").tabs('getSelected');//获取当前选中tabs
    var index = $("#iframe-warp").tabs('getTabIndex', close_tab);//获取当前选中tabs的index
    $("#iframe-warp").tabs('close', index);//关闭对应index的tabs
}

//更新tab,若存在刷新，不存在添加
function updateTab(title, url) {
    var $frame = $("#iframe-warp");
    $frame.tabs('select', title);
    var tab = $frame.tabs('getSelected');
    if ($frame.tabs('exists', title)) {
        var content = getIframeHmtl(url);
        $frame.tabs('update', {
                tab: tab,
                options: {
                    title: title,
                    content: content
                }
            }
        );
    } else {
        addTab(title, url);
    }
}

//刷新指定tabs
function tabsUpdate(title, url) {
    var $frame = $("#iframe-warp");
    var content = getIframeHmtl(url);
    var tab = $frame.tabs('getTab', title);
    var index = $("#iframe-warp").tabs('getTabIndex', tab);
    if ($frame.tabs('exists', title)) {
        $frame.tabs('update', {
                tab: tab,
                options: {
                    title: title,
                    content: content
                }
            }
        );
    } else {
        addTab(title, url);
    }

}

//关闭指定tabs
function assignClose(tabName) {
    $("#iframe-warp").tabs('close', tabName);
}
function otherTab(obj) {
    var tool=$(obj).attr("data-tools");
    var isShow=$(tool).css("display");
    var t=$(obj).offset().top+27;
    if(isShow=='none') {
        $(tool).show().css({"right":0,"top":t});
    }else {
        $(tool).hide();
    }
}
//点击桌面关闭
$('body').on("click",function(e){
    var case1=$(e.target).parents(".tabs-tool").length,
        case2=$(e.target).hasClass(".tabs-tool");
    // 判断当前点击的父级是否包含以下class，包含则不关闭侧滑层
    if(case1==0&&!case2) {
        $('#tabs').hide()
    }
});
function closeThis(obj,title) {
    assignClose(title);
    $(obj).parent().remove();
}
//iframe 高度设置
function winHei(hei) {
    if (hei == null || hei == undefined || hei == '') {
        hei = 0
    }
//	var menuTop = $('.mainMenu').offset().top;
//	var menuHei = $(window).height() - menuTop - 18+hei;
//	$('.mainMenu').css({height:menuHei});//设置菜单高度
    var iframeWid = $('#iframe-warp').width();
    var iframeHei = $('#iframe-warp').height() - 29 + hei;
    $('#iframe-warp').find('.tabs-header').css({width: iframeWid});
    $('#iframe-warp').find('.tabs-wrap').css({width: iframeWid});
    $('#iframe-warp').find('.tabs-panels').css({width: iframeWid});
    $('#iframe-warp').find('.panel-body').css({width: iframeWid});
    $('#iframe-warp').find('iframe').css({height: iframeHei, width: iframeWid});
}

/**
 * 获取IframeHmtl
 */
function getIframeHmtl(url) {
    var contTop = $(".kjdms-content ").offset().top;
    var contentHei = $(window).height() - contTop - 10;
    var hei = contentHei - 29;
    var content = ' <iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:' + hei + 'px;"></iframe>';
    return content;
}

function updateTabTitle(oldTitle, newTitle) {
    var $frame = $("#iframe-warp");
    // if ($frame.tabs('exists', newTitle)){
    //     // $("#iframe-warp").tabs('close',newTitle);
    //     $frame.tabs('select',newTitle);
    // }else{
    $frame.find('li').find('.tabs-title').filter(function () {
        return $(this).html() == oldTitle;
    }).html(newTitle);
    // }

}

//tab右键操作
function closeTab(menu, type) {
    var allTabs = $("#iframe-warp").tabs('tabs');
    var allTabtitle = [];
    $.each(allTabs, function (i, n) {
        var opt = $(n).panel('options');
        console.log(opt)
        if (opt.closable)
            allTabtitle.push(opt.title);
    });
    var curTabTitle = $(menu).data("tabTitle");
    var curTabIndex = $("#iframe-warp").tabs("getTabIndex", $("#iframe-warp").tabs("getTab", curTabTitle));
    switch (type) {
        case 1:
            $("#iframe-warp").tabs("close", curTabIndex);
            return false;
            break;
        case 2:
            console.log(allTabtitle)
            for (var i = 0; i < allTabtitle.length; i++) {
                $('#iframe-warp').tabs('close', allTabtitle[i]);
            }
            break;
        case 3:
            for (var i = 0; i < allTabtitle.length; i++) {
                if (curTabTitle != allTabtitle[i])
                    $('#iframe-warp').tabs('close', allTabtitle[i]);
            }
            $('#iframe-warp').tabs('select', curTabTitle);
            break;
        case 4:
            for (var i = curTabIndex; i < allTabtitle.length; i++) {
                $('#iframe-warp').tabs('close', allTabtitle[i]);
            }
            $('#iframe-warp').tabs('select', curTabTitle);
            break;
        case 5:
            for (var i = 0; i < curTabIndex - 1; i++) {
                $('#iframe-warp').tabs('close', allTabtitle[i]);
            }
            $('#iframe-warp').tabs('select', curTabTitle);
            break;
        case 6: //刷新
            var panel = $("#iframe-warp").tabs("getTab", curTabTitle).panel("refresh");
            break;
    }

}

//tab的标题更新
// function TabTitleUpdate() {
// 	var oldTitle = document.getElementsByClassName('tabs-title').innerText;
// 	var newTitle = document.getElementsByClassName('layui-input-block')[0].innerText;
// 	$iframeWarp.find('li').find('.tabs-title').filter(function(){
// 		return $(this).html()==oldTitle;
// 	}).html(newTitle);
// 	console.log(myTitle);
// };

// var myTitle=sessionStorage['myTitle'];
// function searchTitle(){
// 	for(var i=0;i<$(".tabs-title").length;i++){
// 		if($($(".tabs-title")[i]).html()==myTitle){
// 			return $(".tabs-title")[i];
// 		};
// 	};
// };
// function TabTitleUpdate() {
// 	// var oldTitle = document.getElementsByClassName('tabs-title').innerText;
// 	// var newTitle = document.getElementsByClassName('layui-input-block')[0].innerText;
// 	// $iframeWarp.find('li').find('.tabs-title').filter(function(){
// 	// 	return $(this).html()==oldTitle;
// 	// }).html(newTitle);
// 	var newTitle=$(".layui-form-item").find('.layui-input-block').innerText;
// 	var oldTitle=searchTitle();
// 	$iframeWarp.find('li').find('.tabs-title').filter(function(){
// 		return $(this).html()==oldTitle;
// 	}).html(newTitle);
// };

var myTitle = sessionStorage['myTitle'];

function searchTitle() {
    for (var i = 0; i < $(".tabs-title").length; i++) {
        if ($($(".tabs-title")[i]).html() == myTitle) {
            return $(".tabs-title")[i];
        }
        ;
    }
    ;
};

function TabTitleUpdate() {
    // var oldTitle = document.getElementsByClassName('tabs-title').innerText;
    // var newTitle = document.getElementsByClassName('layui-input-block')[0].innerText;
    // $iframeWarp.find('li').find('.tabs-title').filter(function(){
    // 	return $(this).html()==oldTitle;
    // }).html(newTitle);
    var newTitle = $(".layui-form-item").find('.layui-input-block').innerText;
    var oldTitle = searchTitle();
    $iframeWarp.find('li').find('.tabs-title').filter(function () {
        return $(this).html() == oldTitle;
    }).html(newTitle);
};

