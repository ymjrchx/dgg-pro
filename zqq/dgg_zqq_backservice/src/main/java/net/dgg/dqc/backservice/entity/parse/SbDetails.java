package net.dgg.dqc.backservice.entity.parse;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 全国商标查询-商标详情
 * Created by jiangsh on 2018/6/5 10:53
 */
public class SbDetails {
    private String esId; //id
    private String addressCn; //申请人地址中文
    private String addressEn; //申请人地址英文
    private Integer announcementIssue; //初审公告期号
    private String announcementDate; //初审公告日期
    private String applicant1; //共有申请人1
    private String applicant2; //共有申请人2
    private String color; //指定颜色
    private Integer regIssue; //注册公告期号
    private String regDate; //注册公告日期
    private String houQiZhiDingDate; //后期指定日期
    private String guoJiZhuCeDate; //国际注册日期
    private String youXianQuanDate; //优先权日期
    private String validPeriod; //有效期区间
    private List<String> listGroupItems; //注册商标分组
    private String id; //主键
    private String regNo; //注册号
    private String intCls; //国际分类
    private String name; //商标名
    private String appDate; //申请日期
    private String applicantCn; //申请人中文
    private String applicantEn; //申请人英文
    private String agent; //代理人名称
    private String tmSection; //代理机构
    private String status; //商标状态
    private String flowStatus; //流程状态代码
    private String flowStatusDesc; //流程状态描述
    private Boolean hasImage; //是否有图片
    private String imageUrl; //图片地址
    private String tmiName; //图片名称
    private String tmiPath; //图片path
    private String sbType; //商标类别
    private String serverProject; //服务项目(转至tmGoodsService)
    private String serverNum; //服务编号
    private String tmApplyAddres; //申请地址
    private String tmGoodsService; //商品服务列表
    private String tmApplyFlow; //申请流程
    private String tmGongGaoyFlow; //公告流程
    private List<FlowItems> flowItem; //商标注册流程
    private String url; //公司url
    private String loadTime; //加载时间

    public String getEsId() {
        return esId;
    }

    public void setEsId(String esId) {
        this.esId = esId;
    }

    public String getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(String loadTime) {
        this.loadTime = loadTime;
    }

    public String getTmGongGaoyFlow() {
        return tmGongGaoyFlow;
    }

    public void setTmGongGaoyFlow(String tmGongGaoyFlow) {
        this.tmGongGaoyFlow = tmGongGaoyFlow;
    }

    public String getSbType() {
        return sbType;
    }

    public void setSbType(String sbType) {
        this.sbType = sbType;
    }

    public String getServerProject() {
        return serverProject;
    }

    public void setServerProject(String serverProject) {
        this.serverProject = serverProject;
    }

    public String getServerNum() {
        return serverNum;
    }

    public void setServerNum(String serverNum) {
        this.serverNum = serverNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTmiName() {
        return tmiName;
    }

    public void setTmiName(String tmiName) {
        this.tmiName = tmiName;
    }

    public String getTmiPath() {
        return tmiPath;
    }

    public void setTmiPath(String tmiPath) {
        this.tmiPath = tmiPath;
    }

    public String getTmApplyFlow() {
        return tmApplyFlow;
    }

    public void setTmApplyFlow(String tmApplyFlow) {
        this.tmApplyFlow = tmApplyFlow;
    }

    public String getTmGoodsService() {
        return tmGoodsService;
    }

    public void setTmGoodsService(String tmGoodsService) {
        this.tmGoodsService = tmGoodsService;
    }

    public String getTmApplyAddres() {
        return tmApplyAddres;
    }

    public void setTmApplyAddres(String tmApplyAddres) {
        this.tmApplyAddres = tmApplyAddres;
    }

    public String getTmSection() {
        return tmSection;
    }

    public void setTmSection(String tmSection) {
        this.tmSection = tmSection;
    }

    public String getAddressCn() {
        return addressCn;
    }

    public void setAddressCn(String addressCn) {
        this.addressCn = addressCn;
    }

    public String getAddressEn() {
        return addressEn;
    }

    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }

    public Integer getAnnouncementIssue() {
        return announcementIssue;
    }

    public void setAnnouncementIssue(Integer announcementIssue) {
        this.announcementIssue = announcementIssue;
    }

    public String getAnnouncementDate() {
        return announcementDate;
    }

    public void setAnnouncementDate(String announcementDate) {
        this.announcementDate = announcementDate;
    }

    public String getApplicant1() {
        return applicant1;
    }

    public void setApplicant1(String applicant1) {
        this.applicant1 = applicant1;
    }

    public String getApplicant2() {
        return applicant2;
    }

    public void setApplicant2(String applicant2) {
        this.applicant2 = applicant2;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getRegIssue() {
        return regIssue;
    }

    public void setRegIssue(Integer regIssue) {
        this.regIssue = regIssue;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getHouQiZhiDingDate() {
        return houQiZhiDingDate;
    }

    public void setHouQiZhiDingDate(String houQiZhiDingDate) {
        this.houQiZhiDingDate = houQiZhiDingDate;
    }

    public String getGuoJiZhuCeDate() {
        return guoJiZhuCeDate;
    }

    public void setGuoJiZhuCeDate(String guoJiZhuCeDate) {
        this.guoJiZhuCeDate = guoJiZhuCeDate;
    }

    public String getYouXianQuanDate() {
        return youXianQuanDate;
    }

    public void setYouXianQuanDate(String youXianQuanDate) {
        this.youXianQuanDate = youXianQuanDate;
    }

    public String getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(String validPeriod) {
        this.validPeriod = validPeriod;
    }

    public List<String> getListGroupItems() {
        return listGroupItems;
    }

    public void setListGroupItems(List<String> listGroupItems) {
        this.listGroupItems = listGroupItems;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getIntCls() {
        return intCls;
    }

    public void setIntCls(String intCls) {
        this.intCls = intCls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getApplicantCn() {
        return applicantCn;
    }

    public void setApplicantCn(String applicantCn) {
        this.applicantCn = applicantCn;
    }

    public String getApplicantEn() {
        return applicantEn;
    }

    public void setApplicantEn(String applicantEn) {
        this.applicantEn = applicantEn;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(String flowStatus) {
        this.flowStatus = flowStatus;
    }

    public String getFlowStatusDesc() {
        return flowStatusDesc;
    }

    public void setFlowStatusDesc(String flowStatusDesc) {
        this.flowStatusDesc = flowStatusDesc;
    }

    public Boolean getHasImage() {
        return hasImage;
    }

    public void setHasImage(Boolean hasImage) {
        this.hasImage = hasImage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<FlowItems> getFlowItem() {
        return flowItem;
    }

    public void setFlowItem(List<FlowItems> flowItem) {
        this.flowItem = flowItem;
    }

    public static class FlowItems{
        private String flowId; //商标注册流程Id
        private String flowItem; //商标注册流程步骤
        private Date flowDate; //商标注册流程步骤日期

        public String getFlowId() {
            return flowId;
        }

        public void setFlowId(String flowId) {
            this.flowId = flowId;
        }

        public String getFlowItem() {
            return flowItem;
        }

        public void setFlowItem(String flowItem) {
            this.flowItem = flowItem;
        }

        public Date getFlowDate() {
            return flowDate;
        }

        public void setFlowDate(Date flowDate) {
            this.flowDate = flowDate;
        }
    }
}

