package net.dgg.dqc.backservice.entity.parse;

import java.util.Date;

/**
 * 企业经营异常
 * Created by jiangsh on 2018/6/4 17:25
 */
public class Jyyc {
    private String addReason; //列入经营异常名录原因
    private String addDate; //列入日期
    private String romoveReason; //移出经营异常名录原因
    private String removeDate; //移出日期
    private String decisionOffice; //作出决定机关
    private String removeDecisionOffice; //移除决定机关

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(String removeDate) {
        this.removeDate = removeDate;
    }

    public String getAddReason() {
        return addReason;
    }

    public void setAddReason(String addReason) {
        this.addReason = addReason;
    }


    public String getRomoveReason() {
        return romoveReason;
    }

    public void setRomoveReason(String romoveReason) {
        this.romoveReason = romoveReason;
    }


    public String getDecisionOffice() {
        return decisionOffice;
    }

    public void setDecisionOffice(String decisionOffice) {
        this.decisionOffice = decisionOffice;
    }

    public String getRemoveDecisionOffice() {
        return removeDecisionOffice;
    }

    public void setRemoveDecisionOffice(String removeDecisionOffice) {
        this.removeDecisionOffice = removeDecisionOffice;
    }
}
