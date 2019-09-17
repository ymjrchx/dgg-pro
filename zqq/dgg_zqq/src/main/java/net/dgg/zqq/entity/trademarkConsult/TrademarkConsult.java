package net.dgg.zqq.entity.trademarkConsult;

import net.dgg.zqq.entity.BaseEntity;

public class TrademarkConsult extends BaseEntity {

    /**
     * 用户ID
     */
    private String userId;


    /**
     * 商标名称
     */
    private String trademarkerName;

    /**
     * 商标类型
     */
    private String trademarkerType;

    /*
     * 你的称呼
     */
    private String userName;

    /*
     *  联系号码
     */
    private String phone;

    /*
     * 邮箱
     */
    private String email;

    /*
     * 意向价格字典编码
     */
    private String intentionalPrice;

    /*
     * 需求描述
     */
    private String requirementDesc;
    /*
     * 申请号
     */
    private String applicationNum;

    /*
     * 0删除（已联系）  1正常(未联系)
     */
    private Integer flag;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTrademarkerName() {
        return trademarkerName;
    }

    public void setTrademarkerName(String trademarkerName) {
        this.trademarkerName = trademarkerName;
    }

    public String getTrademarkerType() {
        return trademarkerType;
    }

    public void setTrademarkerType(String trademarkerType) {
        this.trademarkerType = trademarkerType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntentionalPrice() {
        return intentionalPrice;
    }

    public void setIntentionalPrice(String intentionalPrice) {
        this.intentionalPrice = intentionalPrice;
    }

    public String getRequirementDesc() {
        return requirementDesc;
    }

    public void setRequirementDesc(String requirementDesc) {
        this.requirementDesc = requirementDesc;
    }

    public String getApplicationNum() {
        return applicationNum;
    }

    public void setApplicationNum(String applicationNum) {
        this.applicationNum = applicationNum;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
