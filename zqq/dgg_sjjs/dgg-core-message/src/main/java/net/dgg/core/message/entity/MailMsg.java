package net.dgg.core.message.entity;

import java.util.HashMap;
import java.util.List;

/**
 * 邮件发送消息实体
 *
 * @author tumq
 */
public class MailMsg extends AbstractMsgEntity {
    private String subject;//邮件标题
    private List<String> toAddress;//接收邮件地址
    private List<String> ccAddress;//抄送邮件地址
    private List<HashMap> attachments = null;
    private String appKey; //消息发送者所在系统

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getToAddress() {
        return toAddress;
    }

    public void setToAddress(List<String> toAddress) {
        this.toAddress = toAddress;
    }

    public List<String> getCcAddress() {
        return ccAddress;
    }

    public void setCcAddress(List<String> ccAddress) {
        this.ccAddress = ccAddress;
    }

    public List<HashMap> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<HashMap> attachments) {
        this.attachments = attachments;
    }

}
