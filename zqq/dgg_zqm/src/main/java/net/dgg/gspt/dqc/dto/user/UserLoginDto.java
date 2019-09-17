package net.dgg.gspt.dqc.dto.user;

/**
 * Created by gene on 2017/9/18.
 * desc:移动端登录dto
 */
public class UserLoginDto {
    private String username;//用户名
    private String loginPwd;//密码

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }
}
