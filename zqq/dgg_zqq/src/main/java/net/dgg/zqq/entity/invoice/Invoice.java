package net.dgg.zqq.entity.invoice;

import net.dgg.zqq.entity.BaseEntity;

/**
 * @ClassName:
 * @Description: 发票
 * @Author: huangl
 * @Date: 2018/11/9 11:30
 */
public class Invoice extends BaseEntity {

    /**
     * 订单Id
     */
    private String orderId;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 发票抬头
     */
    private String title;

    /**
     * 收件人
     */
    private String recipients;

    /**
     * 手机号
     */
    private String phoneNo;

    /**
     * 地址
     */
    private String address;

    /**
     * 申请结果
     */
    private String result;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
