package net.dgg.core.message.entity;

import java.util.Map;

/**
 * 短信发送消息实体
 *
 * @author tumq
 */
public class PmMsg extends AbstractMsgEntity {
    private Map<Long,String> receivers; //接受者ID，接受者工号
    private Integer msgType;//发送渠道
    private Long senderId; //发送者Id
    private String senderName; //发送者姓名
    private String title; //站内信标题

    private String appKey; //消息发送者所在系统

    public Map<Long, String> getReceivers() {
        return receivers;
    }

    public void setReceivers(Map<Long, String> receivers) {
        this.receivers = receivers;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }


    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
