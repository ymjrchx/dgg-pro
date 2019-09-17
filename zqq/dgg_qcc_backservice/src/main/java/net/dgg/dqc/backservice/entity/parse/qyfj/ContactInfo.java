package net.dgg.dqc.backservice.entity.parse.qyfj;

/**
 * 企业附加信息-公司联系信息
 * Created by jiangsh on 2018/6/4 17:45
 */
public class ContactInfo {
    private String PhoneNumber; //联系电话
    private String Email; //联系邮箱
    private String WebSiteName; //网站名称
    private String WebSiteUrl; //网站地址

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getWebSiteName() {
        return WebSiteName;
    }

    public void setWebSiteName(String webSiteName) {
        WebSiteName = webSiteName;
    }

    public String getWebSiteUrl() {
        return WebSiteUrl;
    }

    public void setWebSiteUrl(String webSiteUrl) {
        WebSiteUrl = webSiteUrl;
    }
}
