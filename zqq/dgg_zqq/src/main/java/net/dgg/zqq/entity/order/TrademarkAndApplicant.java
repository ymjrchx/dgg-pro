package net.dgg.zqq.entity.order;

import java.util.Date;

/**
 * @author 刘阳
 * @ClassName <TrademarkAndApplicant>
 * @despriction 智能注册订单商标信息和申请人信息
 * @create 2018/09/26 18:32
 **/
public class TrademarkAndApplicant {

    private Long id;
    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 商标类型，trademark_type1:文字，trademark_type2:图形，trademark_type3:文字及图形，trademark_type4:黑白
     */
    private String type;

    /**
     * 商标名称
     */
    private String name;

    /**
     * 商标说明
     */
    private String explain;

    /**
     * 注册号或申请号
     */
    private String registerNo;

    /**
     * 用户商标图样类型：example_type1:自动生成，example_type2:手动生成
     */
    private String exampleType;

    /**
     * 商标图样文件地址
     */
    private String exampleAddress;

    /**
     * +
     * 商标类别创建方式：class_create_way1：智能推荐，class_create_way2:自助选择。
     */
    private String classCreateWay;

    /**
     * 智能推荐的一级的领域code或ID
     */
    private String suggestedFirstLevel;

    /**
     * 智能推荐的二级的领域code或ID
     */
    private String suggestedSecondLevel;

    /**
     * 申请人类型，
     * applicant_type1：企业，applicant_type2：个体工商户
     */
    private String applicantType;

    /**
     * 申请人名字
     */
    private String applicantUserName;

    /**
     * 申请主体
     */
    private String applicantName;

    /**
     * 申请人身份证号
     */
    private String applicantCardNo;

    /**
     * 营业执照号
     */
    private String businessLicenceNo;

    /**
     * 营业执照所在地区
     */
    private String businessLicenceArea;

    /**
     * 邮政编码
     */
    private String postalcode;

    /**
     * 营业执照详情地址
     */
    private String businessLicenceAddress;

    /**
     * 身份证复印件
     */
    private String applicantCardFile;

    /**
     * 身份证复印件PDF
     */
    private String applicantCardPdfFile;

    /**
     * 营业执照
     */
    private String businessLicenceFile;

    /**
     * 营业执照PDF
     */
    private String businessLicencePdfFile;


    /**
     * 委托书
     */
    private String proxyFile;

    /**
     * 优先权证明
     */
    private String priorityFile;

    /**
     * 模板ID
     */
    private Long templateId;

    /**
     * 身份证地址
     */
    private String applicantCardAddress;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人ID
     */
    private String createrId;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 修改人ID
     */
    private String updaterId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExampleType() {
        return exampleType;
    }

    public void setExampleType(String exampleType) {
        this.exampleType = exampleType;
    }

    public String getExampleAddress() {
        return exampleAddress;
    }

    public void setExampleAddress(String exampleAddress) {
        this.exampleAddress = exampleAddress;
    }

    public String getClassCreateWay() {
        return classCreateWay;
    }

    public void setClassCreateWay(String classCreateWay) {
        this.classCreateWay = classCreateWay;
    }

    public String getSuggestedFirstLevel() {
        return suggestedFirstLevel;
    }

    public void setSuggestedFirstLevel(String suggestedFirstLevel) {
        this.suggestedFirstLevel = suggestedFirstLevel;
    }

    public String getSuggestedSecondLevel() {
        return suggestedSecondLevel;
    }

    public void setSuggestedSecondLevel(String suggestedSecondLevel) {
        this.suggestedSecondLevel = suggestedSecondLevel;
    }

    public String getApplicantType() {
        return applicantType;
    }

    public void setApplicantType(String applicantType) {
        this.applicantType = applicantType;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantCardNo() {
        return applicantCardNo;
    }

    public void setApplicantCardNo(String applicantCardNo) {
        this.applicantCardNo = applicantCardNo;
    }

    public String getBusinessLicenceArea() {
        return businessLicenceArea;
    }

    public void setBusinessLicenceArea(String businessLicenceArea) {
        this.businessLicenceArea = businessLicenceArea;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getBusinessLicenceAddress() {
        return businessLicenceAddress;
    }

    public void setBusinessLicenceAddress(String businessLicenceAddress) {
        this.businessLicenceAddress = businessLicenceAddress;
    }

    public String getApplicantCardFile() {
        return applicantCardFile;
    }

    public void setApplicantCardFile(String applicantCardFile) {
        this.applicantCardFile = applicantCardFile;
    }

    public String getBusinessLicenceFile() {
        return businessLicenceFile;
    }

    public void setBusinessLicenceFile(String businessLicenceFile) {
        this.businessLicenceFile = businessLicenceFile;
    }

    public String getProxyFile() {
        return proxyFile;
    }

    public void setProxyFile(String proxyFile) {
        this.proxyFile = proxyFile;
    }

    public String getPriorityFile() {
        return priorityFile;
    }

    public void setPriorityFile(String priorityFile) {
        this.priorityFile = priorityFile;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getApplicantCardAddress() {
        return applicantCardAddress;
    }

    public void setApplicantCardAddress(String applicantCardAddress) {
        this.applicantCardAddress = applicantCardAddress;
    }

    public String getApplicantUserName() {
        return applicantUserName;
    }

    public void setApplicantUserName(String applicantUserName) {
        this.applicantUserName = applicantUserName;
    }

    public String getBusinessLicenceNo() {
        return businessLicenceNo;
    }

    public void setBusinessLicenceNo(String businessLicenceNo) {
        this.businessLicenceNo = businessLicenceNo;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getBusinessLicencePdfFile() {
        return businessLicencePdfFile;
    }

    public void setBusinessLicencePdfFile(String businessLicencePdfFile) {
        this.businessLicencePdfFile = businessLicencePdfFile;
    }

    public String getApplicantCardPdfFile() {
        return applicantCardPdfFile;
    }

    public void setApplicantCardPdfFile(String applicantCardPdfFile) {
        this.applicantCardPdfFile = applicantCardPdfFile;
    }
}
