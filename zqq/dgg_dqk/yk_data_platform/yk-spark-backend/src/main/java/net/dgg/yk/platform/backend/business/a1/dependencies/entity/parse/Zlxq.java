package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

import java.util.List;

/**
 * 专利查询-专利详情
 * Created by jiangsh on 2018/6/7 17:03
 */
public class Zlxq {
    private String documentTypes;  //文档类型
    private String agent; //代理人
    private String legalStatusDate;   //法律状态日期
    private String primaryExaminer;   //审查官
    private String assiantExaminer;   //助理审查官
    private String cites; //引用
    private String otherReferences;   //其他参考文献
    private String patentImage;   //专利图片
    private String iPCList;   //国际专利分类号（IPC）
    private String piApplyPublishNum; //申请公布号
    private String piClassifyNum; //分类号
    private String piInventName; //发明名称
    private String applicationNumber; //申请号
    private String applicationDate;   //申请日
    private String piApplyAnnounceDate;   //申请公布日
    private String publicationNumber; //公开（公告）号
    private String publicationDate;   //公开（公告）日
    private String legalStatusDesc;   //法律状态详情
    private String title; //标题
    private String piAddress; //地址
    private String abstracts;  //摘要介绍
    private String agency;    //专利代理机构
    private String kindCodeDesc;  //类型名称
    private String iPCDesc;   //国际专利分类详情
    private String inventorString;    //发明人
    private String assigneestring;    //申请人
    private String url; //公司url

    //以下是按专利爬取网站添加
    private String patType; //类型，为一个列表
    private String areaCode; //申请人区域代码
    private String patentee; //专利权
    private String zjnClassify; //洛迦诺分类
    private String IPCCode; //IPC分类号
    private String CPCCode; //CPC分类号
    private String priority; //优先权
    private String gjsq; //国际申请
    private String gjgk; //国际公开（公告）
    private String jrgjrq; //进入国家日期
    private String fasq; //分案申请
    private String keyWord; //关键字
    private List<LawInfo> lawInfos; //法律信息
    private String claInfo; //权利要求
    private String desInfo; //说明书

    public String getClaInfo() {
        return claInfo;
    }

    public void setClaInfo(String claInfo) {
        this.claInfo = claInfo;
    }

    public String getDesInfo() {
        return desInfo;
    }

    public void setDesInfo(String desInfo) {
        this.desInfo = desInfo;
    }

    public List<LawInfo> getLawInfos() {
        return lawInfos;
    }

    public void setLawInfos(List<LawInfo> lawInfos) {
        this.lawInfos = lawInfos;
    }

    public String getPatType() {
        return patType;
    }

    public void setPatType(String patType) {
        this.patType = patType;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getFasq() {
        return fasq;
    }

    public void setFasq(String fasq) {
        this.fasq = fasq;
    }

    public String getJrgjrq() {
        return jrgjrq;
    }

    public void setJrgjrq(String jrgjrq) {
        this.jrgjrq = jrgjrq;
    }

    public String getGjgk() {
        return gjgk;
    }

    public void setGjgk(String gjgk) {
        this.gjgk = gjgk;
    }

    public String getGjsq() {
        return gjsq;
    }

    public void setGjsq(String gjsq) {
        this.gjsq = gjsq;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCPCCode() {
        return CPCCode;
    }

    public void setCPCCode(String CPCCode) {
        this.CPCCode = CPCCode;
    }

    public String getIPCCode() {
        return IPCCode;
    }

    public void setIPCCode(String IPCCode) {
        this.IPCCode = IPCCode;
    }

    public String getZjnClassify() {
        return zjnClassify;
    }

    public void setZjnClassify(String zjnClassify) {
        this.zjnClassify = zjnClassify;
    }

    public String getPatentee() {
        return patentee;
    }

    public void setPatentee(String patentee) {
        this.patentee = patentee;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPiAddress() {
        return piAddress;
    }

    public void setPiAddress(String piAddress) {
        this.piAddress = piAddress;
    }

    public String getPiApplyAnnounceDate() {
        return piApplyAnnounceDate;
    }

    public void setPiApplyAnnounceDate(String piApplyAnnounceDate) {
        this.piApplyAnnounceDate = piApplyAnnounceDate;
    }

    public String getPiInventName() {
        return piInventName;
    }

    public void setPiInventName(String piInventName) {
        this.piInventName = piInventName;
    }

    public String getPiClassifyNum() {
        return piClassifyNum;
    }

    public void setPiClassifyNum(String piClassifyNum) {
        this.piClassifyNum = piClassifyNum;
    }

    public String getPiApplyPublishNum() {
        return piApplyPublishNum;
    }

    public void setPiApplyPublishNum(String piApplyPublishNum) {
        this.piApplyPublishNum = piApplyPublishNum;
    }

    private List<PatentLegalHistory> patentLegalHistory;

    public String getDocumentTypes() {
        return documentTypes;
    }

    public void setDocumentTypes(String documentTypes) {
        this.documentTypes = documentTypes;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getLegalStatusDate() {
        return legalStatusDate;
    }

    public void setLegalStatusDate(String legalStatusDate) {
        this.legalStatusDate = legalStatusDate;
    }

    public String getPrimaryExaminer() {
        return primaryExaminer;
    }

    public void setPrimaryExaminer(String primaryExaminer) {
        this.primaryExaminer = primaryExaminer;
    }

    public String getAssiantExaminer() {
        return assiantExaminer;
    }

    public void setAssiantExaminer(String assiantExaminer) {
        this.assiantExaminer = assiantExaminer;
    }

    public String getCites() {
        return cites;
    }

    public void setCites(String cites) {
        this.cites = cites;
    }

    public String getOtherReferences() {
        return otherReferences;
    }

    public void setOtherReferences(String otherReferences) {
        this.otherReferences = otherReferences;
    }

    public String getPatentImage() {
        return patentImage;
    }

    public void setPatentImage(String patentImage) {
        this.patentImage = patentImage;
    }

    public String getiPCList() {
        return iPCList;
    }

    public void setiPCList(String iPCList) {
        this.iPCList = iPCList;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getPublicationNumber() {
        return publicationNumber;
    }

    public void setPublicationNumber(String publicationNumber) {
        this.publicationNumber = publicationNumber;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getLegalStatusDesc() {
        return legalStatusDesc;
    }

    public void setLegalStatusDesc(String legalStatusDesc) {
        this.legalStatusDesc = legalStatusDesc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getKindCodeDesc() {
        return kindCodeDesc;
    }

    public void setKindCodeDesc(String kindCodeDesc) {
        this.kindCodeDesc = kindCodeDesc;
    }

    public String getiPCDesc() {
        return iPCDesc;
    }

    public void setiPCDesc(String iPCDesc) {
        this.iPCDesc = iPCDesc;
    }

    public String getInventorString() {
        return inventorString;
    }

    public void setInventorString(String inventorString) {
        this.inventorString = inventorString;
    }

    public String getAssigneestring() {
        return assigneestring;
    }

    public void setAssigneestring(String assigneestring) {
        this.assigneestring = assigneestring;
    }

    public List<PatentLegalHistory> getPatentLegalHistory() {
        return patentLegalHistory;
    }

    public void setPatentLegalHistory(List<PatentLegalHistory> patentLegalHistory) {
        this.patentLegalHistory = patentLegalHistory;
    }

    public static class PatentLegalHistory {
        private String desc;
        private String legalStatus;
        private String legalStatusDate;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getLegalStatus() {
            return legalStatus;
        }

        public void setLegalStatus(String legalStatus) {
            this.legalStatus = legalStatus;
        }

        public String getLegalStatusDate() {
            return legalStatusDate;
        }

        public void setLegalStatusDate(String legalStatusDate) {
            this.legalStatusDate = legalStatusDate;
        }
    }

    public static class LawInfo {
        private String lawDate;
        private String lawStatus;
        private String lawStatusInfo;

        public String getLawDate() {
            return lawDate;
        }

        public void setLawDate(String lawDate) {
            this.lawDate = lawDate;
        }

        public String getLawStatus() {
            return lawStatus;
        }

        public void setLawStatus(String lawStatus) {
            this.lawStatus = lawStatus;
        }

        public String getLawStatusInfo() {
            return lawStatusInfo;
        }

        public void setLawStatusInfo(String lawStatusInfo) {
            this.lawStatusInfo = lawStatusInfo;
        }
    }
}
