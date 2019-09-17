function initMenu() {
    $('.left-menu-list li').each(function () {
        var isSub = $(this).find('.sub-menu').length;
        if (isSub == 0) {
            $(this).addClass('oneMenu');
        }
    });
    $('.left-menu-list>li>a').click(function () {
        var isOpen = $(this).parent().hasClass('open');
        var isSub = $(this).parent().hasClass('oneMenu');
        var addSub02 = $(this).find(".oneMenu").length;
        if (addSub02 == 0) {
//                location.reload(false)
        }
        ;
        $(".left-menu-list li a").removeClass("active");
        $(this).addClass("active");
        if (!isOpen && isSub) {
            //$(this).parent().addClass('active');
        } else if (isOpen && !isSub) {
            $(this).parent().removeClass('open');
            $(this).next().hide();
        } else if (!isOpen && !isSub) {
            $(this).parent().addClass('open');
            $(this).next().show();
        }
    });
    $(".sub-menu>li>a").click(function () {
        var has = $(this).parent().children(".last-menu-main").length;
        $(this).parent().parent().find("a").removeClass("active");
        $(this).addClass("active");
        if (has != 0) {
            $(this).parents(".left-menu").animate({"left": "-175px"});
            $(this).parent().children(".last-menu-main").show();
            $(this).parent().children(".last-menu-main").animate({"left": "0px"});
        }
//            else (
//                    location.reload(false)
//            )
    });
    $(".menu-pot").click(function () {
        $(this).parent().fadeOut().animate({"left": "175px"});
        $(".left-menu").animate({"left": "0"});
    });
}

$(document).ready(function () {
    /*addTab("工作台", sysInfo.basePath + "workSpace/index.html", false);*/
    addTab("分类管理", sysInfo.basePath + "condition/index.html", false);
    initMenu();
    var indexMenu = $("#indexMenu").val();
    if(indexMenu!=undefined && indexMenu!=null){
        loadSecMenu(indexMenu);
    }

    /**
     * 状态切换
     * */
    $(document).click(function () {
        $(".state-list,.closeLayer").hide();
    });
    $(".state-now").click(function (event) {
        $(".state-list,.closeLayer").stop().toggle();
        event.stopPropagation();
    });
    $(".state-list li").click(function () {
        var thisState = $(this).html();
        $(".state-now").find("span").empty().html(thisState);
        $(".state-list").stop().toggle();
    });
    /**
     *提示关闭
     **/
    $(".close-warning").click(function () {
        $(this).parent().hide();
    });
//              var tab=$('#iframe-warp').tabs().tabs('tabs')[0];
    $('#iframe-warp').tabs({
        onContextMenu: function (e, title, index) {
            e.preventDefault();
            if (index > 0) {
                $('#mm').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                }).data("tabTitle", title);
            }
        }
    });
    //右键菜单click
    $("#mm").menu({
        onClick: function (item) {
            closeTab(this, item.name);
//	                	if(item.id='close') {
//
//	                	}
        }
    });
});

//删除Tabs
function closeTab(menu, type) {
    var allTabs = $("#iframe-warp").tabs('tabs');
    var allTabtitle = [];
    $.each(allTabs, function (i, n) {
        var opt = $(n).panel('options');
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
        case 6:
            $("#iframe-warp").tabs("getTab", curTabTitle).panel("refresh");
            break;
    }

}

window.addEventListener('resize', function () {
    winHei();
});

/**
 * 加载二级菜单
 * @param pId
 */
function loadSecMenu(pId) {
    $.ajax({
        url: baseUrl + "find_sub_menu_tree.do",
        data: {pId: pId},
        success: function (data) {

            $('.left-menu').css('left', 0);

            //处理二级菜单
            $("#secMenu").empty();
            if (data.code == 0) {
                //请求成功
                var menus = data.data;
                if (menus.length > 0) {
                    var menuAppend = "";
                    for (var i = 0; i < menus.length; i++) {
                        var secMenu = menus[i];
                        //循环处理信息
                        if (secMenu.children != null && secMenu.children.length > 0) {
                            menuAppend = menuAppend + "<li><a href=\"javascript:void(0)\">" + secMenu.menuName + "</a><ul class=\"sub-menu\">";
                            //添加子元素
                            for (var j = 0; j < secMenu.children.length; j++) {
                                //循环3级菜单
                                var thrMenu = secMenu.children[j];
                                if (thrMenu.children != null && thrMenu.children.length > 0) {
                                    menuAppend = menuAppend + "<li><a href=\"javascript:void(0)\">" + thrMenu.menuName + "</a><div class=\"last-menu-main\"><ul class=\"sub-menu last-menu\">";
                                    for (var k = 0; k < thrMenu.children.length; k++) {
                                        var fouMenu = thrMenu.children[k];
                                        if (k == 0) {
                                            menuAppend = menuAppend + "<li><a href=\"javascript:void(0);\" class=\"prev-menu\"><span></span>" + thrMenu.menuName + "</a></li>";
                                        }
                                        menuAppend = menuAppend + "<li><a href=\"javascript:void(0)\" onclick=\"addTab('" + fouMenu.menuName + "','" + fouMenu.menuUrl + "')\">" + fouMenu.menuName + "</a></li>";
                                    }
                                    menuAppend = menuAppend + "</ul>";
                                    menuAppend = menuAppend + "<div class=\"menu-pot\"></div>";
                                    menuAppend = menuAppend + "</div>";
                                    menuAppend = menuAppend + "</li>";
                                } else {
                                    menuAppend = menuAppend + "<li><a href=\"javascript:void(0)\" onclick=\"addTab('" + thrMenu.menuName + "','" + thrMenu.menuUrl + "')\">" + thrMenu.menuName + "</a></li>";
                                }
                            }
                            menuAppend = menuAppend + "</ul>"
                            menuAppend = menuAppend + "</li>";
                        } else {
                            menuAppend = menuAppend + "<li><a href=\"javascript:void(0)\" onclick=\"addTab('" + secMenu.menuName + "','" + secMenu.menuUrl + "')\">" + secMenu.menuName + "</a></li>";
                        }

                    }

                    $("#secMenu").append(menuAppend);
                    initMenu();
//                        console.log(menuAppend);
                } else {
                    //没有子菜单
                }
            } else {
                console.log("请求失败");
            }

        }
    });
}

function onLogout() {
    $.post("/login/do_logout.do", function (result) {

        if (result.code == 1) {
            layer.msg(result.msg);
            console.log(result)
        } else {
            location.reload(true);
            /* layer.alert(result.data,function () {
             });*/
        }
    })
}

//    var Totalwidth = parseInt($(".head-nav").width());
window.onload = function () {
    menuSize();
    $(".head-nav a.CheckMore").click(function (e) {
        e.stopPropagation();
        $(this).addClass("on");
        $(".clickCheckMore01>.hidden-div").toggle();
    });
};
$(window).resize(function () {
    menuSize();
    $(".clickCheckMore01>.hidden-div").hide();
})

function menuSize() {
    var winWidth = $(window).width();
    var leftWidth = parseInt($(".head-logo").width());
    var rightWidth = parseInt($(".head-main").width()) + parseInt($(".user-state").width());
    var allWidth = 0;
    var htm = '';
    $(".head-nav>.add-div-width>a").each(function () {
        var that = $(this);
        var w = parseInt($(this).width() + 10);
        allWidth += w;
        var restWidth = winWidth - leftWidth - rightWidth - 30;
//            console.log(that.context.outerHTML)
        if (allWidth > restWidth) {
//            console.log(that.context.outerHTML)
            $(".head-nav a.CheckMore").show();
            htm += that.context.outerHTML;
        }
        else {
            $(".head-nav a.CheckMore").hide();
        }
        ;
    });

    $(".clickCheckMore01>.hidden-div").empty().append(htm);
}

//    $("body").click(function() {
//        $(".clickCheckMore01>.hidden-div").hide();
//    });
$(".clickCheckMore01>.hidden-div").mouseleave(function () {
    $(".clickCheckMore01>.hidden-div").hide();
});
var navNull = $(".add-div-width").is(':empty');
if (navNull == null || navNull == "") {
    $(".head-nav a.CheckMore").hide();
};
