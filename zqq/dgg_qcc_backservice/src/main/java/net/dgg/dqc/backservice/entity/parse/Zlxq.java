package net.dgg.dqc.backservice.entity.parse;

import java.util.List;

/**
 * 专利查询-专利详情
 * Created by jiangsh on 2018/6/7 17:03
 */
public class Zlxq {
    private String  DocumentTypes;  //文档类型
    private String Agent; //代理人
    private String LegalStatusDate;   //法律状态日期
    private String PrimaryExaminer;   //审查官
    private String AssiantExaminer;   //助理审查官
    private String Cites; //引用
    private String OtherReferences;   //其他参考文献
    private String PatentImage;   //专利图片
    private String IPCList;   //国际专利分类号（IPC）
    private String ApplicationNumber; //申请号
    private String ApplicationDate;   //申请日
    private String PublicationNumber; //公开（公告）号
    private String PublicationDate;   //公开（公告）日
    private String LegalStatusDesc;   //法律状态详情
    private String Title; //标题
    private String Abstract;  //摘要介绍
    private String Agency;    //专利代理机构
    private String KindCodeDesc;  //类型名称
    private String IPCDesc;   //国际专利分类详情
    private String InventorString;    //发明人
    private String Assigneestring;    //申请人

    private List<PatentLegalHistory> patentLegalHistory;

    public String getDocumentTypes() {
        return DocumentTypes;
    }

    public void setDocumentTypes(String documentTypes) {
        DocumentTypes = documentTypes;
    }

    public String getAgent() {
        return Agent;
    }

    public void setAgent(String agent) {
        Agent = agent;
    }

    public String getLegalStatusDate() {
        return LegalStatusDate;
    }

    public void setLegalStatusDate(String legalStatusDate) {
        LegalStatusDate = legalStatusDate;
    }

    public String getPrimaryExaminer() {
        return PrimaryExaminer;
    }

    public void setPrimaryExaminer(String primaryExaminer) {
        PrimaryExaminer = primaryExaminer;
    }

    public String getAssiantExaminer() {
        return AssiantExaminer;
    }

    public void setAssiantExaminer(String assiantExaminer) {
        AssiantExaminer = assiantExaminer;
    }

    public String getCites() {
        return Cites;
    }

    public void setCites(String cites) {
        Cites = cites;
    }

    public String getOtherReferences() {
        return OtherReferences;
    }

    public void setOtherReferences(String otherReferences) {
        OtherReferences = otherReferences;
    }

    public String getPatentImage() {
        return PatentImage;
    }

    public void setPatentImage(String patentImage) {
        PatentImage = patentImage;
    }

    public String getIPCList() {
        return IPCList;
    }

    public void setIPCList(String IPCList) {
        this.IPCList = IPCList;
    }

    public String getApplicationNumber() {
        return ApplicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        ApplicationNumber = applicationNumber;
    }

    public String getApplicationDate() {
        return ApplicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        ApplicationDate = applicationDate;
    }

    public String getPublicationNumber() {
        return PublicationNumber;
    }

    public void setPublicationNumber(String publicationNumber) {
        PublicationNumber = publicationNumber;
    }

    public String getPublicationDate() {
        return PublicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        PublicationDate = publicationDate;
    }

    public String getLegalStatusDesc() {
        return LegalStatusDesc;
    }

    public void setLegalStatusDesc(String legalStatusDesc) {
        LegalStatusDesc = legalStatusDesc;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAbstract() {
        return Abstract;
    }

    public void setAbstract(String anAbstract) {
        Abstract = anAbstract;
    }

    public String getAgency() {
        return Agency;
    }

    public void setAgency(String agency) {
        Agency = agency;
    }

    public String getKindCodeDesc() {
        return KindCodeDesc;
    }

    public void setKindCodeDesc(String kindCodeDesc) {
        KindCodeDesc = kindCodeDesc;
    }

    public String getIPCDesc() {
        return IPCDesc;
    }

    public void setIPCDesc(String IPCDesc) {
        this.IPCDesc = IPCDesc;
    }

    public String getInventorString() {
        return InventorString;
    }

    public void setInventorString(String inventorString) {
        InventorString = inventorString;
    }

    public String getAssigneestring() {
        return Assigneestring;
    }

    public void setAssigneestring(String assigneestring) {
        Assigneestring = assigneestring;
    }

    public List<PatentLegalHistory> getPatentLegalHistory() {
        return patentLegalHistory;
    }

    public void setPatentLegalHistory(List<PatentLegalHistory> patentLegalHistory) {
        this.patentLegalHistory = patentLegalHistory;
    }

    public static class PatentLegalHistory {
        private String Desc;
        private String LegalStatus;
        private String LegalStatusDate;
    }
}
