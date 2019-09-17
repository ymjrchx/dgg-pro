package net.dgg.dqc.backservice.entity.parse;

import java.util.List;

/**
 * 查询裁判文书-裁判文书详情查询
 * Created by jiangsh on 2018/6/5 11:34
 */
public class Cpws {
    private String Id; //Id
    private String CaseName; //裁判文书名字
    private String CaseNo; //裁判文书编号
    private String CaseType; //裁判文书类型
    private String CaseTypeCode; //裁判文书类型编号
    private String Content; //裁判文书内容
    private String Court; //执行法院
    private String CreateDate; //创建时间
    private String SubmitDate; //提交时间
    private String UpdateDate; //修改时间
    private List<String> Appellor; //当事人
    private String JudgeDate; //裁判时间
    private String CaseReason; //案由
    private String TrialRound; //审理程序
    private List<String> Defendantlist; //被告
    private List<String> Prosecutorlist; //原告
    private String ajsf; //

    public String getAjsf() {
        return ajsf;
    }

    public void setAjsf(String ajsf) {
        this.ajsf = ajsf;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCaseName() {
        return CaseName;
    }

    public void setCaseName(String caseName) {
        CaseName = caseName;
    }

    public String getCaseNo() {
        return CaseNo;
    }

    public void setCaseNo(String caseNo) {
        CaseNo = caseNo;
    }

    public String getCaseType() {
        return CaseType;
    }

    public void setCaseType(String caseType) {
        CaseType = caseType;
    }

    public String getCaseTypeCode() {
        return CaseTypeCode;
    }

    public void setCaseTypeCode(String caseTypeCode) {
        CaseTypeCode = caseTypeCode;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getCourt() {
        return Court;
    }

    public void setCourt(String court) {
        Court = court;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getSubmitDate() {
        return SubmitDate;
    }

    public void setSubmitDate(String submitDate) {
        SubmitDate = submitDate;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public List<String> getAppellor() {
        return Appellor;
    }

    public void setAppellor(List<String> appellor) {
        Appellor = appellor;
    }

    public String getJudgeDate() {
        return JudgeDate;
    }

    public void setJudgeDate(String judgeDate) {
        JudgeDate = judgeDate;
    }

    public String getCaseReason() {
        return CaseReason;
    }

    public void setCaseReason(String caseReason) {
        CaseReason = caseReason;
    }

    public String getTrialRound() {
        return TrialRound;
    }

    public void setTrialRound(String trialRound) {
        TrialRound = trialRound;
    }

    public List<String> getDefendantlist() {
        return Defendantlist;
    }

    public void setDefendantlist(List<String> defendantlist) {
        Defendantlist = defendantlist;
    }

    public List<String> getProsecutorlist() {
        return Prosecutorlist;
    }

    public void setProsecutorlist(List<String> prosecutorlist) {
        Prosecutorlist = prosecutorlist;
    }
}
