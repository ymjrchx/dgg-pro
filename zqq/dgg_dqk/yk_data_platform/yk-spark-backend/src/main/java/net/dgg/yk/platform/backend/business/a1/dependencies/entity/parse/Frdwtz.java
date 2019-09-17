package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

/**
 *  法人对外投资
 * Created by jiangsh on 2018/8/14 17:21
 */
public class Frdwtz {
    private String lmNum;
    private String lmCompany;
    private String lmPerCent;
    private String lmstate;
    private String lmOpenTime;
    private String lmConfirmNum;

    public String getLmCompany() {
        return lmCompany;
    }

    public void setLmCompany(String lmCompany) {
        this.lmCompany = lmCompany;
    }

    public String getLmNum() {
        return lmNum;
    }

    public void setLmNum(String lmNum) {
        this.lmNum = lmNum;
    }

    public String getLmOpenTime() {
        return lmOpenTime;
    }

    public void setLmOpenTime(String lmOpenTime) {
        this.lmOpenTime = lmOpenTime;
    }

    public String getLmConfirmNum() {
        return lmConfirmNum;
    }

    public void setLmConfirmNum(String lmConfirmNum) {
        this.lmConfirmNum = lmConfirmNum;
    }

    public String getLmPerCent() {
        return lmPerCent;
    }

    public void setLmPerCent(String lmPerCent) {
        this.lmPerCent = lmPerCent;
    }

    public String getLmstate() {
        return lmstate;
    }

    public void setLmstate(String lmstate) {
        this.lmstate = lmstate;
    }
}
