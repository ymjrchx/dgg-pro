package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

/**
 * 欠税公告
 * Created by jiangsh on 2018/8/17 11:33
 */
public class Qsgg {
    private String owntcTaxType; //欠税税种
    private String owntcOver; //欠税余额
    private String owntcNowOver; //当前新发生的欠税余额
    private String owntcOrg; //发布单位(税务机关)
    private String owntcDate; //发布时间(日期)
    private String owntcTaxNum; //纳税人识别号

    public String getOwntcTaxType() {
        return owntcTaxType;
    }

    public void setOwntcTaxType(String owntcTaxType) {
        this.owntcTaxType = owntcTaxType;
    }

    public String getOwntcOver() {
        return owntcOver;
    }

    public void setOwntcOver(String owntcOver) {
        this.owntcOver = owntcOver;
    }

    public String getOwntcNowOver() {
        return owntcNowOver;
    }

    public void setOwntcNowOver(String owntcNowOver) {
        this.owntcNowOver = owntcNowOver;
    }

    public String getOwntcOrg() {
        return owntcOrg;
    }

    public void setOwntcOrg(String owntcOrg) {
        this.owntcOrg = owntcOrg;
    }

    public String getOwntcDate() {
        return owntcDate;
    }

    public void setOwntcDate(String owntcDate) {
        this.owntcDate = owntcDate;
    }

    public String getOwntcTaxNum() {
        return owntcTaxNum;
    }

    public void setOwntcTaxNum(String owntcTaxNum) {
        this.owntcTaxNum = owntcTaxNum;
    }
}
