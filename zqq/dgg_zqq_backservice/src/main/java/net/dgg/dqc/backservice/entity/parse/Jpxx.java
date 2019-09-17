package net.dgg.dqc.backservice.entity.parse;

/**
 * 竞品信息
 * Created by jiangsh on 2018/8/20 10:53
 */
public class Jpxx {
    private String jpName; //竞品名称
    private String jpArea; //地区
    private String jpNum; //当前轮次
    private String jpIndustry; //行业
    private String jpBusiness; //业务
    private String jpTime; //成立时间
    private String jpMoney; //估值
    private String jpiUrl; //图片url
    private String jpiName; //图片名称
    private String piPath; //图片path

    public String getPiPath() {
        return piPath;
    }

    public void setPiPath(String piPath) {
        this.piPath = piPath;
    }

    public String getJpName() {
        return jpName;
    }

    public void setJpName(String jpName) {
        this.jpName = jpName;
    }

    public String getJpArea() {
        return jpArea;
    }

    public void setJpArea(String jpArea) {
        this.jpArea = jpArea;
    }

    public String getJpNum() {
        return jpNum;
    }

    public void setJpNum(String jpNum) {
        this.jpNum = jpNum;
    }

    public String getJpIndustry() {
        return jpIndustry;
    }

    public void setJpIndustry(String jpIndustry) {
        this.jpIndustry = jpIndustry;
    }

    public String getJpBusiness() {
        return jpBusiness;
    }

    public void setJpBusiness(String jpBusiness) {
        this.jpBusiness = jpBusiness;
    }

    public String getJpTime() {
        return jpTime;
    }

    public void setJpTime(String jpTime) {
        this.jpTime = jpTime;
    }

    public String getJpMoney() {
        return jpMoney;
    }

    public void setJpMoney(String jpMoney) {
        this.jpMoney = jpMoney;
    }

    public String getJpiUrl() {
        return jpiUrl;
    }

    public void setJpiUrl(String jpiUrl) {
        this.jpiUrl = jpiUrl;
    }

    public String getJpiName() {
        return jpiName;
    }

    public void setJpiName(String jpiName) {
        this.jpiName = jpiName;
    }
}
