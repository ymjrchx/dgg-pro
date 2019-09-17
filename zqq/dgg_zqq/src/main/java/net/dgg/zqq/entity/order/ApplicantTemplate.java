package net.dgg.zqq.entity.order;

import java.util.Date;

/**
 * @ClassName: ApplicantTemplate
 * @Description: TODO
 * @Author: zxc
 * @Date: 2018/11/14 9:21
 */
public class ApplicantTemplate {

    private Long id;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人号码
     */
    private String contactPhone;

    /**
     * 联系人邮箱
     */
    private String contactEmail;

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
     * 申请人身份证地址
     */
    private String applicantCardAddress;

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
     * 营业执照
     */
    private String businessLicenceFile;

    /**
     * 委托书
     */
    private String proxyFile;

    /**
     * 优先权证明
     */
    private String priorityFile;


    /**
     * 禁用启用
     */
    private String status;

    /**
     * 0删除  1正常
     */
    private Integer flag;

    /**
     * 0未默认  1默认
     */
    private Integer defaultSign;

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

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
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

    public String getApplicantCardAddress() {
        return applicantCardAddress;
    }

    public void setApplicantCardAddress(String applicantCardAddress) {
        this.applicantCardAddress = applicantCardAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getDefaultSign() {
        return defaultSign;
    }

    public void setDefaultSign(Integer defaultSign) {
        this.defaultSign = defaultSign;
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
}