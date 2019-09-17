package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

/**
 * 环保处罚
 * Created by jiangsh on 2018/8/17 11:54
 */
public class Hbcf {
    private String epNuam; //决定文书号
    private String epType; //违法类型
    private String epOrg; //处罚单位
    private String epDate; //处罚日期

    public String getEpNuam() {
        return epNuam;
    }

    public void setEpNuam(String epNuam) {
        this.epNuam = epNuam;
    }

    public String getEpType() {
        return epType;
    }

    public void setEpType(String epType) {
        this.epType = epType;
    }

    public String getEpOrg() {
        return epOrg;
    }

    public void setEpOrg(String epOrg) {
        this.epOrg = epOrg;
    }

    public String getEpDate() {
        return epDate;
    }

    public void setEpDate(String epDate) {
        this.epDate = epDate;
    }
}
