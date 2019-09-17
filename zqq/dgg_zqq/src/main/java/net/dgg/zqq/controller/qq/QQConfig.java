package net.dgg.zqq.controller.qq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author 刘阳
 * @ClassName <QQConfig>
 * @despriction
 * @create 2018/10/18 15:13
 **/
@Configuration
@Component
public class QQConfig {
    @Value("${qq.appId}")
    private String appId;

    @Value("${qq.appSecret}")
    private String appSecret;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
