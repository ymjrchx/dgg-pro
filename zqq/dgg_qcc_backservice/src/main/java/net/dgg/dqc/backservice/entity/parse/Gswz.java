package net.dgg.dqc.backservice.entity.parse;

/**
 * 公司网站信息查询
 * Created by jiangsh on 2018/6/5 11:23
 */
public class Gswz {
    private String KeyNo; //KeyNo
    private String CompanyName; //公司名
    private String HomeSite; //网址
    private String HomeSite2; //网址2
    private String SDate; //审核日期
    private String Status; //状态
    private String TypeName; //类型名
    private String YuMing; //域名
    private String BeiAn; //备案号
    private String Title; //标题

    public String getHomeSite2() {
        return HomeSite2;
    }

    public void setHomeSite2(String homeSite2) {
        HomeSite2 = homeSite2;
    }

    public String getKeyNo() {
        return KeyNo;
    }

    public void setKeyNo(String keyNo) {
        KeyNo = keyNo;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getHomeSite() {
        return HomeSite;
    }

    public void setHomeSite(String homeSite) {
        HomeSite = homeSite;
    }

    public String getSDate() {
        return SDate;
    }

    public void setSDate(String SDate) {
        this.SDate = SDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public String getYuMing() {
        return YuMing;
    }

    public void setYuMing(String yuMing) {
        YuMing = yuMing;
    }

    public String getBeiAn() {
        return BeiAn;
    }

    public void setBeiAn(String beiAn) {
        BeiAn = beiAn;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
