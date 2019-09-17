package net.dgg.dqc.backservice.entity.parse;

/**
 * 公司网站信息查询
 * Created by jiangsh on 2018/6/5 11:23
 */
public class Gswz {
    private String keyNo; //KeyNo
    private String companyName; //公司名
    private String homeSite; //网址
    private String homeSite2; //网址2
    private String sDate; //审核日期
    private String status; //状态
    private String typeName; //类型名
    private String yuMing; //域名
    private String beiAn; //备案号
    private String title; //标题
    private String wProperty; //单位性质
    private String wPermitNum; //许可证号
    private String wRegisterTime; //注册时间

    public String getwPermitNum() {
        return wPermitNum;
    }

    public void setwPermitNum(String wPermitNum) {
        this.wPermitNum = wPermitNum;
    }

    public String getwRegisterTime() {
        return wRegisterTime;
    }

    public void setwRegisterTime(String wRegisterTime) {
        this.wRegisterTime = wRegisterTime;
    }

    public String getwProperty() {
        return wProperty;
    }

    public void setwProperty(String wProperty) {
        this.wProperty = wProperty;
    }

    public String getKeyNo() {
        return keyNo;
    }

    public void setKeyNo(String keyNo) {
        this.keyNo = keyNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getHomeSite() {
        return homeSite;
    }

    public void setHomeSite(String homeSite) {
        this.homeSite = homeSite;
    }

    public String getHomeSite2() {
        return homeSite2;
    }

    public void setHomeSite2(String homeSite2) {
        this.homeSite2 = homeSite2;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getYuMing() {
        return yuMing;
    }

    public void setYuMing(String yuMing) {
        this.yuMing = yuMing;
    }

    public String getBeiAn() {
        return beiAn;
    }

    public void setBeiAn(String beiAn) {
        this.beiAn = beiAn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
