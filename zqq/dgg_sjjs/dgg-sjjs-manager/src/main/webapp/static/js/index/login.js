var pageIndex = {};
/**
 * 点击登录过程
 */
pageIndex.onLogin = function () {
    //获取用户名密码
    var userName= $("#userName").val();
    var loginPwd=$("#password").val();
    //用户名不可为空
    if(userName==null || userName=='') {
        $("#spanMsg").text("* 用户名不可为空");
        $('#userName').focus();
        return false;
    }
    //用户名不可超过40长度
    if(userName.length>40){
        $("#spanMsg").text("* 用户名不可超过40长度");
        $('#userName').focus();
        return false;
    }
    //密码不可为空
    if(loginPwd==null || loginPwd=='') {
        $("#spanMsg").text("* 密码不可为空");
        $('#password').focus();
        return false;
    }
    //密码长度不可超过20
    if(loginPwd.length>20) {
        $("#spanMsg").text("* 密码长度不可超过20");
        $('#password').focus();
        return false;
    }
    //进行登录
    util.request("POST","/login/do_login.do",{
        username:userName,
        loginPwd:loginPwd
    },function (response) {
        if(response.code==1){
            $("#spanMsg").text("* "+response.msg);
            $('#password').focus();
            // layer.msg(response.msg);
        }else {
            window.location.href = '/index.html';
        }
    })

}

/*pageIndex.onLogin = function () {
    //获取用户名密码
    var userName= $("#userName").val();
    var loginPwd=$("#password").val();
    //用户名不可为空
    if(userName==null || userName=='') {
        $("#userNameError").text("* 用户名不可为空");
        return;
    }
    //用户名不可超过40长度
    if(userName.length>40){
        $("#userNameError").text("* 用户名不可超过40长度");
        return;
    }
    //密码不可为空
    if(loginPwd==null || loginPwd=='') {
        $("#passwordError").text("* 密码不可为空");
        return;
    }
    //密码长度不可超过20
    if(loginPwd.length>20) {
        $("#passwordError").text("* 密码长度不可超过20");
        return;
    }
    //进行登录
    util.request("POST","/login/do_login.do",{
        username:userName,
        loginPwd:loginPwd
    },function (response) {
        if(response.code==1){
            layer.msg(response.msg);
        }else {
            window.location.href="/index.html";
        }

    })

}*/