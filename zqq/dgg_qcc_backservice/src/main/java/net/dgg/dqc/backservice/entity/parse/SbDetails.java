package net.dgg.dqc.backservice.entity.parse;

import java.util.Date;
import java.util.List;

/**
 * 全国商标查询-商标详情
 * Created by jiangsh on 2018/6/5 10:53
 */
public class SbDetails {
    private String AddressCn; //申请人地址中文
    private String AddressEn; //申请人地址英文
    private Integer AnnouncementIssue; //初审公告期号
    private String AnnouncementDate; //初审公告日期
    private String Applicant1; //共有申请人1
    private String Applicant2; //共有申请人2
    private String Color; //指定颜色
    private Integer RegIssue; //注册公告期号
    private String RegDate; //注册公告日期
    private String HouQiZhiDingDate; //后期指定日期
    private String GuoJiZhuCeDate; //国际注册日期
    private String YouXianQuanDate; //优先权日期
    private String ValidPeriod; //有效期区间
    private List<String> ListGroupItems; //注册商标分组
    private String Id; //主键
    private String RegNo; //注册号
    private String IntCls; //国际分类
    private String Name; //商标名
    private String AppDate; //申请日期
    private String ApplicantCn; //申请人中文
    private String ApplicantEn; //申请人英文
    private String Agent; //代理人名称
    private String Status; //商标状态
    private String FlowStatus; //流程状态代码
    private String FlowStatusDesc; //流程状态描述
    private Boolean HasImage; //是否有图片
    private String ImageUrl; //图片地址
    private List<FlowItems> flowItem; //商标注册流程

    public String getAddressCn() {
        return AddressCn;
    }

    public void setAddressCn(String addressCn) {
        AddressCn = addressCn;
    }

    public String getAddressEn() {
        return AddressEn;
    }

    public void setAddressEn(String addressEn) {
        AddressEn = addressEn;
    }

    public Integer getAnnouncementIssue() {
        return AnnouncementIssue;
    }

    public void setAnnouncementIssue(Integer announcementIssue) {
        AnnouncementIssue = announcementIssue;
    }

    public String getAnnouncementDate() {
        return AnnouncementDate;
    }

    public void setAnnouncementDate(String announcementDate) {
        AnnouncementDate = announcementDate;
    }

    public String getApplicant1() {
        return Applicant1;
    }

    public void setApplicant1(String applicant1) {
        Applicant1 = applicant1;
    }

    public String getApplicant2() {
        return Applicant2;
    }

    public void setApplicant2(String applicant2) {
        Applicant2 = applicant2;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public Integer getRegIssue() {
        return RegIssue;
    }

    public void setRegIssue(Integer regIssue) {
        RegIssue = regIssue;
    }

    public String getRegDate() {
        return RegDate;
    }

    public void setRegDate(String regDate) {
        RegDate = regDate;
    }

    public String getHouQiZhiDingDate() {
        return HouQiZhiDingDate;
    }

    public void setHouQiZhiDingDate(String houQiZhiDingDate) {
        HouQiZhiDingDate = houQiZhiDingDate;
    }

    public String getGuoJiZhuCeDate() {
        return GuoJiZhuCeDate;
    }

    public void setGuoJiZhuCeDate(String guoJiZhuCeDate) {
        GuoJiZhuCeDate = guoJiZhuCeDate;
    }

    public String getYouXianQuanDate() {
        return YouXianQuanDate;
    }

    public void setYouXianQuanDate(String youXianQuanDate) {
        YouXianQuanDate = youXianQuanDate;
    }

    public String getValidPeriod() {
        return ValidPeriod;
    }

    public void setValidPeriod(String validPeriod) {
        ValidPeriod = validPeriod;
    }

    public List<String> getListGroupItems() {
        return ListGroupItems;
    }

    public void setListGroupItems(List<String> listGroupItems) {
        ListGroupItems = listGroupItems;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getRegNo() {
        return RegNo;
    }

    public void setRegNo(String regNo) {
        RegNo = regNo;
    }

    public String getIntCls() {
        return IntCls;
    }

    public void setIntCls(String intCls) {
        IntCls = intCls;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAppDate() {
        return AppDate;
    }

    public void setAppDate(String appDate) {
        AppDate = appDate;
    }

    public String getApplicantCn() {
        return ApplicantCn;
    }

    public void setApplicantCn(String applicantCn) {
        ApplicantCn = applicantCn;
    }

    public String getApplicantEn() {
        return ApplicantEn;
    }

    public void setApplicantEn(String applicantEn) {
        ApplicantEn = applicantEn;
    }

    public String getAgent() {
        return Agent;
    }

    public void setAgent(String agent) {
        Agent = agent;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getFlowStatus() {
        return FlowStatus;
    }

    public void setFlowStatus(String flowStatus) {
        FlowStatus = flowStatus;
    }

    public String getFlowStatusDesc() {
        return FlowStatusDesc;
    }

    public void setFlowStatusDesc(String flowStatusDesc) {
        FlowStatusDesc = flowStatusDesc;
    }

    public Boolean getHasImage() {
        return HasImage;
    }

    public void setHasImage(Boolean hasImage) {
        HasImage = hasImage;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public List<FlowItems> getFlowItem() {
        return flowItem;
    }

    public void setFlowItem(List<FlowItems> flowItem) {
        this.flowItem = flowItem;
    }

    public static class FlowItems{
        private String FlowId; //商标注册流程Id
        private String FlowItem; //商标注册流程步骤
        private Date FlowDate; //商标注册流程步骤日期

        public String getFlowId() {
            return FlowId;
        }

        public void setFlowId(String flowId) {
            FlowId = flowId;
        }

        public String getFlowItem() {
            return FlowItem;
        }

        public void setFlowItem(String flowItem) {
            FlowItem = flowItem;
        }

        public Date getFlowDate() {
            return FlowDate;
        }

        public void setFlowDate(Date flowDate) {
            FlowDate = flowDate;
        }
    }
}

