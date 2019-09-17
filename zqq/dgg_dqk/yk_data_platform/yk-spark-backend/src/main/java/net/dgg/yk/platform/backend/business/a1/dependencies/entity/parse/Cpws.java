package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

import java.util.List;

/**
 * 查询裁判文书-裁判文书详情查询
 * Created by jiangsh on 2018/6/5 11:34
 */
public class Cpws {
    private String id; //Id
    private String caseName; //裁判文书名字
    private String caseNo; //裁判文书编号
    private String caseType; //裁判文书类型
    private String caseTypeCode; //裁判文书类型编号
    private String content; //裁判文书内容
    private String court; //执行法院
    private String createDate; //创建时间
    private String submitDate; //提交时间
    private String updateDate; //修改时间
    private List<String> appellor; //当事人
    private String judgeDate; //裁判时间
    private String caseReason; //案由
    private String trialRound; //审理程序
    private String defendantlist; //被告
    private String prosecutorlist; //原告
    private String ajsf; // 案件身份

    private String loadTime; //存储时间
    private String judges; //合议庭（法官名称）
    private String docId; //文书ID

    private List<LegalBase> legalBase; //法律依据
    private String wbsb; //文本首部
    private String dsrxx; //当事人信息
    private String ssjl; //申诉经历
    private String cpyz; //裁判由证
    private String pjjg; //判决结果
    private String wbwb; //尾部文本

    public String getWbsb() {
        return wbsb;
    }

    public void setWbsb(String wbsb) {
        this.wbsb = wbsb;
    }

    public String getDsrxx() {
        return dsrxx;
    }

    public void setDsrxx(String dsrxx) {
        this.dsrxx = dsrxx;
    }

    public String getSsjl() {
        return ssjl;
    }

    public void setSsjl(String ssjl) {
        this.ssjl = ssjl;
    }

    public String getCpyz() {
        return cpyz;
    }

    public void setCpyz(String cpyz) {
        this.cpyz = cpyz;
    }

    public String getPjjg() {
        return pjjg;
    }

    public void setPjjg(String pjjg) {
        this.pjjg = pjjg;
    }

    public String getWbwb() {
        return wbwb;
    }

    public void setWbwb(String wbwb) {
        this.wbwb = wbwb;
    }

    public List<LegalBase> getLegalBase() {
        return legalBase;
    }

    public void setLegalBase(List<LegalBase> legalBase) {
        this.legalBase = legalBase;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getJudges() {
        return judges;
    }

    public void setJudges(String judges) {
        this.judges = judges;
    }

    public String getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(String loadTime) {
        this.loadTime = loadTime;
    }

    public String getAjsf() {
        return ajsf;
    }

    public void setAjsf(String ajsf) {
        this.ajsf = ajsf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseTypeCode() {
        return caseTypeCode;
    }

    public void setCaseTypeCode(String caseTypeCode) {
        this.caseTypeCode = caseTypeCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public List<String> getAppellor() {
        return appellor;
    }

    public void setAppellor(List<String> appellor) {
        this.appellor = appellor;
    }

    public String getJudgeDate() {
        return judgeDate;
    }

    public void setJudgeDate(String judgeDate) {
        this.judgeDate = judgeDate;
    }

    public String getCaseReason() {
        return caseReason;
    }

    public void setCaseReason(String caseReason) {
        this.caseReason = caseReason;
    }

    public String getTrialRound() {
        return trialRound;
    }

    public void setTrialRound(String trialRound) {
        this.trialRound = trialRound;
    }

    public String getDefendantlist() {
        return defendantlist;
    }

    public void setDefendantlist(String defendantlist) {
        this.defendantlist = defendantlist;
    }

    public String getProsecutorlist() {
        return prosecutorlist;
    }

    public void setProsecutorlist(String prosecutorlist) {
        this.prosecutorlist = prosecutorlist;
    }

    public static class LegalBase{
        private String fgmc; //法规名称
        private List<FgItems> fgItems; //法规选项

        public String getFgmc() {
            return fgmc;
        }

        public void setFgmc(String fgmc) {
            this.fgmc = fgmc;
        }

        public List<FgItems> getFgItems() {
            return fgItems;
        }

        public void setFgItems(List<FgItems> fgItems) {
            this.fgItems = fgItems;
        }
    }

    public static class FgItems{
        private String ftmc; //法条名称
        private String ftnr; //法条内容

        public String getFtmc() {
            return ftmc;
        }

        public void setFtmc(String ftmc) {
            this.ftmc = ftmc;
        }

        public String getFtnr() {
            return ftnr;
        }

        public void setFtnr(String ftnr) {
            this.ftnr = ftnr;
        }
    }
}
