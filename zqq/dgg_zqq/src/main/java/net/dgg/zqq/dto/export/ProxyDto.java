package net.dgg.zqq.dto.export;

/**
 * @ClassName: ProxyDto
 * @Description: TODO
 * @Author: zxc
 * @Date: 2019/1/16 9:08
 */
public class ProxyDto {
    //类型 0个人 1企业
    private String type;

    //委托人
    private String client;

    //身份证号码
    private String idCard;

    //委托人地址 和营业执照地址一致
    private String clientAddress;

    //联系人姓名
    private String contactName;

    //电话
    private String contactPhone;

    //邮政编码
    private String postalCode;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }


    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}