/**
 * 查询字典公共服务
 * @type {{}}
 */

var treeBookService = treeBookService || {};
treeBookService.url = sysInfo.basePath + '/tree_book/get_tree_book_list.do';
treeBookService.getData = function (code, status, type, level, callback) {
    $.ajax({
        type: "POST",
        url: treeBookService.url,
        data: {
            code: code,
            status: status,
            type: type,
            level: level
        },
        success: function (result) {
            var data = result.code == 0 ? result.data : null;
            if (callback && (typeof callback == 'function')) {
                callback(data);
            }
        }
    });
}




