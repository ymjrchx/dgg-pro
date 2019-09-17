package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

/**
 * 公告催告
 * Created by jiangsh on 2018/8/17 13:47
 */
public class Ggcg {
    private String ilBillNum; //票据号
    private String ilpublishOrg; //发布机构
    private String ilpublishDate; //公告日期

    public String getIlBillNum() {
        return ilBillNum;
    }

    public void setIlBillNum(String ilBillNum) {
        this.ilBillNum = ilBillNum;
    }

    public String getIlpublishOrg() {
        return ilpublishOrg;
    }

    public void setIlpublishOrg(String ilpublishOrg) {
        this.ilpublishOrg = ilpublishOrg;
    }

    public String getIlpublishDate() {
        return ilpublishDate;
    }

    public void setIlpublishDate(String ilpublishDate) {
        this.ilpublishDate = ilpublishDate;
    }
}
