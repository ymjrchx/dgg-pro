util={};
util.getHeader=function() {
    return {
        "Content-Type": "application/json",
        "authorize": "",
        "timestamp": (new Date()).valueOf()
    };
};
/**
 * 请求工具方法
 * @param requestType
 * @param url
 * @param requestData
 * @param successFunction
 */
util.request=function(requestType,url,requestData,successFunction){
    // Layer.loading();
    $.ajax({
        type:requestType,
        url:url,
        dataType:'json',
        headers:util.getHeader(),
        data:JSON.stringify(requestData),
        success:function(response) {
            if (response.code == -1) {
                // Layer.message('系统异常，请联系管理员','2');
            }else if (response.code == -2) {
                // Layer.message('未登录','5');
            }
            else{
                successFunction(response);
            }
            // layer.closeAll('loading');
        },
        error:function(error){
            // layer.closeAll('loading');
             Msg.errors('服务器异常，请联系管理员')
        }
    });
};
util.requestParams=function(requestType,url,requestData,successFunction){
    // Layer.loading();
    $.ajax({
        type:requestType,
        url:url,
        dataType:'json',
        headers:util.getHeader(),
        data:requestData,
        success:function(response) {
            if (response.code == -1) {
                // Layer.message('系统异常，请联系管理员','2');
            }else if (response.code == -2) {
                // Layer.message('未登录','5');
            }
            else{
                successFunction(response);
            }
            // layer.closeAll('loading');
        },
        error:function(error){
            // layer.closeAll('loading');
            Msg.errors('服务器异常，请联系管理员')
        }
    });
}