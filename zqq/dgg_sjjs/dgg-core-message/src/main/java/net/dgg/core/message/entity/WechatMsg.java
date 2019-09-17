package net.dgg.core.message.entity;

import java.util.Date;

/**
 * 微信发送消息实体
 *
 * @author tumq
 */
public class WechatMsg extends AbstractMsgEntity {
    private String url;//消息点击时的连接地址，为空没有连接效果
    private String tempid;//消息模板ID
    private String openid;//接收方openid
    private Date sendDate;//发送时间
    private String sendResult;//发送结果

    private String appKey; //消息发送者所在系统


    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTempid() {
        return tempid;
    }

    public void setTempid(String tempid) {
        this.tempid = tempid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Date getSendDate() { return sendDate; }

    public void setSendDate(Date sendDate) { this.sendDate = sendDate; }

    public String getSendResult() { return sendResult; }

    public void setSendResult(String sendResult) { this.sendResult = sendResult; }
}
