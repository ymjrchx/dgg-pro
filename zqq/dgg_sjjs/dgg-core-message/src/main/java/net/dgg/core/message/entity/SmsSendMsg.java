package net.dgg.core.message.entity;

/**
 * 短信发送消息实体
 *
 * @author tumq
 */
public class SmsSendMsg extends AbstractMsgEntity {
    private String mobile; //发送目标手机号
    private String channel;//发送渠道
    private String appName;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setChannel(ESmsChannel channel) {
        this.channel = channel.toString();
    }
}
