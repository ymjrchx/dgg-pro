package net.dgg.dqc.backservice.entity.parse.qyfj;

/**
 * 企业附加信息-公司联系信息
 * Created by jiangsh on 2018/6/4 17:45
 */
public class ContactInfo {
    private String phoneNumber; //联系电话
    private String email; //联系邮箱
    private String webSiteName; //网站名称
    private String webSiteUrl; //网站地址

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebSiteName() {
        return webSiteName;
    }

    public void setWebSiteName(String webSiteName) {
        this.webSiteName = webSiteName;
    }

    public String getWebSiteUrl() {
        return webSiteUrl;
    }

    public void setWebSiteUrl(String webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
    }
}
