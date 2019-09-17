package net.dgg.dqc.backservice.entity.parse;

/**
 * 投资事件
 * Created by jiangsh on 2018/8/20 10:02
 */
public class Tzsj {
    private String iTime; //时间
    private String iNum; //轮次
    private String iMoney; //金额
    private String iName; //投资方
    private String iPoduct; //产品
    private String iArea; //地区
    private String iIndustry; //行业
    private String iBusiness; //业务

    public String getiTime() {
        return iTime;
    }

    public void setiTime(String iTime) {
        this.iTime = iTime;
    }

    public String getiNum() {
        return iNum;
    }

    public void setiNum(String iNum) {
        this.iNum = iNum;
    }

    public String getiMoney() {
        return iMoney;
    }

    public void setiMoney(String iMoney) {
        this.iMoney = iMoney;
    }

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }

    public String getiPoduct() {
        return iPoduct;
    }

    public void setiPoduct(String iPoduct) {
        this.iPoduct = iPoduct;
    }

    public String getiArea() {
        return iArea;
    }

    public void setiArea(String iArea) {
        this.iArea = iArea;
    }

    public String getiIndustry() {
        return iIndustry;
    }

    public void setiIndustry(String iIndustry) {
        this.iIndustry = iIndustry;
    }

    public String getiBusiness() {
        return iBusiness;
    }

    public void setiBusiness(String iBusiness) {
        this.iBusiness = iBusiness;
    }
}
