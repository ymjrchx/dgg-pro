package net.dgg.dqc.backservice.entity.parse;

import java.util.Date;

/**
 * 企业经营异常
 * Created by jiangsh on 2018/6/4 17:25
 */
public class Jyyc {
    private String AddReason; //列入经营异常名录原因
    private Date AddDate; //列入日期
    private String RomoveReason; //移出经营异常名录原因
    private Date RemoveDate; //移出日期
    private String DecisionOffice; //作出决定机关
    private String RemoveDecisionOffice; //移除决定机关

    public String getAddReason() {
        return AddReason;
    }

    public void setAddReason(String addReason) {
        AddReason = addReason;
    }

    public Date getAddDate() {
        return AddDate;
    }

    public void setAddDate(Date addDate) {
        AddDate = addDate;
    }

    public String getRomoveReason() {
        return RomoveReason;
    }

    public void setRomoveReason(String romoveReason) {
        RomoveReason = romoveReason;
    }

    public Date getRemoveDate() {
        return RemoveDate;
    }

    public void setRemoveDate(Date removeDate) {
        RemoveDate = removeDate;
    }

    public String getDecisionOffice() {
        return DecisionOffice;
    }

    public void setDecisionOffice(String decisionOffice) {
        DecisionOffice = decisionOffice;
    }

    public String getRemoveDecisionOffice() {
        return RemoveDecisionOffice;
    }

    public void setRemoveDecisionOffice(String removeDecisionOffice) {
        RemoveDecisionOffice = removeDecisionOffice;
    }
}
