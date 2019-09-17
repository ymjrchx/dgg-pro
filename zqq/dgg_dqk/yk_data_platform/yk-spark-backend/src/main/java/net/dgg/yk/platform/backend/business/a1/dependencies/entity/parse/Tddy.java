package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

/**
 * 土地抵押
 * Created by jiangsh on 2018/8/17 13:40
 */
public class Tddy {
    private String lmLocation; //土地坐落
    private String lmDate; //起止时间
    private String lmArea; //行政区
    private String lmAcreage; //抵押面积（公顷）
    private String lmUse; //抵押土地用途

    public String getLmLocation() {
        return lmLocation;
    }

    public void setLmLocation(String lmLocation) {
        this.lmLocation = lmLocation;
    }

    public String getLmDate() {
        return lmDate;
    }

    public void setLmDate(String lmDate) {
        this.lmDate = lmDate;
    }

    public String getLmArea() {
        return lmArea;
    }

    public void setLmArea(String lmArea) {
        this.lmArea = lmArea;
    }

    public String getLmAcreage() {
        return lmAcreage;
    }

    public void setLmAcreage(String lmAcreage) {
        this.lmAcreage = lmAcreage;
    }

    public String getLmUse() {
        return lmUse;
    }

    public void setLmUse(String lmUse) {
        this.lmUse = lmUse;
    }
}
