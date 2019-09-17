package net.dgg.zqq.entity.order;

import net.dgg.zqq.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * @author 刘阳
 * @ClassName <Order>
 * @despriction 订单表
 * @create 2018/09/26 17:51
 **/
public class Order extends BaseEntity {

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 服务id
     */
    private Long serviceId;
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 服务所属一级级类别code
     */
    private String serviceTypeLevel1Code;

    /**
     * 服务所属二级级类别code
     */
    private String serviceTypeLevel2Code;

    /**
     * 服务所属三级类别code
     */
    private String serviceTypeLevel3Code;

    /**
     * 服务价格（元）
     */
    private BigDecimal servicePrice;

    /**
     * 服务官费价格（元）
     */
    private BigDecimal serviceOfficialPrice;

    /**
     * 服务属性ID
     */
    private Long serviceAttrId;

    /**
     * 服务属性名
     */
    private String serviceAttrName;

    /**
     * 服务属性code
     */
    private String serviceAttrCode;

    /**
     * 服务属性服务费(元)
     */
    private BigDecimal serviceAttrServicePrice;

    /**
     * 服务属性官费(元)
     */
    private BigDecimal serviceAttrOfficialPrice;

    /**
     * 服务件数
     */
    private Integer allNum;

    /**
     * 服务费总价（元）
     */
    private BigDecimal allServicePrice;

    /**
     * 官费总价（元）
     */
    private BigDecimal allOfficialPrice;

    /**
     * 智能注册商标分类总价格
     */
    private BigDecimal allTrademarkClassPrice;

    /**
     * 智能注册发票税费
     */
    private BigDecimal invoiceFee;

    /**
     * all_price 最终价格
     */
    private BigDecimal allPrice;


    /**
     * 联系人名
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactPhone;

    /**
     * 联系人邮箱
     */
    private String contactEmail;

    /**
     * 联系人电话座机
     */
    private String contactTelephone;


    /**
     * 备注
     */
    private String remark;


    /**
     * 订单类型,order_type1:普通订单，order_type2:智能注册订单
     */
    private String type;

    /**
     * 0未评论  1已评论
     */
    private Integer comment;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 0 已删除1正常
     */
    private Integer flag;

    /**
     * 发票状态
     */
    private String invoiceStatus;

    /**
     * 报件编号
     */
    private Long baoJianNo;

    /**
     * 报件状态
     */
    private String baoJianStatus;

    /**
     * 报件失败原因
     */
    private String baoJianReason;

    /**
     * 审核类型
     */
    private String auditType;


    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceTypeLevel1Code() {
        return serviceTypeLevel1Code;
    }

    public void setServiceTypeLevel1Code(String serviceTypeLevel1Code) {
        this.serviceTypeLevel1Code = serviceTypeLevel1Code;
    }

    public String getServiceTypeLevel3Code() {
        return serviceTypeLevel3Code;
    }

    public void setServiceTypeLevel3Code(String serviceTypeLevel3Code) {
        this.serviceTypeLevel3Code = serviceTypeLevel3Code;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    public BigDecimal getServiceOfficialPrice() {
        return serviceOfficialPrice;
    }

    public void setServiceOfficialPrice(BigDecimal serviceOfficialPrice) {
        this.serviceOfficialPrice = serviceOfficialPrice;
    }

    public Long getServiceAttrId() {
        return serviceAttrId;
    }

    public void setServiceAttrId(Long serviceAttrId) {
        this.serviceAttrId = serviceAttrId;
    }

    public String getServiceAttrName() {
        return serviceAttrName;
    }

    public void setServiceAttrName(String serviceAttrName) {
        this.serviceAttrName = serviceAttrName;
    }

    public String getServiceAttrCode() {
        return serviceAttrCode;
    }

    public void setServiceAttrCode(String serviceAttrCode) {
        this.serviceAttrCode = serviceAttrCode;
    }

    public BigDecimal getServiceAttrOfficialPrice() {
        return serviceAttrOfficialPrice;
    }

    public void setServiceAttrOfficialPrice(BigDecimal serviceAttrOfficialPrice) {
        this.serviceAttrOfficialPrice = serviceAttrOfficialPrice;
    }

    public Integer getAllNum() {
        return allNum;
    }

    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
    }

    public BigDecimal getAllServicePrice() {
        return allServicePrice;
    }

    public void setAllServicePrice(BigDecimal allServicePrice) {
        this.allServicePrice = allServicePrice;
    }

    public BigDecimal getAllOfficialPrice() {
        return allOfficialPrice;
    }

    public void setAllOfficialPrice(BigDecimal allOfficialPrice) {
        this.allOfficialPrice = allOfficialPrice;
    }

    public BigDecimal getInvoiceFee() {
        return invoiceFee;
    }

    public void setInvoiceFee(BigDecimal invoiceFee) {
        this.invoiceFee = invoiceFee;
    }

    public BigDecimal getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(BigDecimal allPrice) {
        this.allPrice = allPrice;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getServiceTypeLevel2Code() {
        return serviceTypeLevel2Code;
    }

    public void setServiceTypeLevel2Code(String serviceTypeLevel2Code) {
        this.serviceTypeLevel2Code = serviceTypeLevel2Code;
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public BigDecimal getAllTrademarkClassPrice() {
        return allTrademarkClassPrice;
    }

    public void setAllTrademarkClassPrice(BigDecimal allTrademarkClassPrice) {
        this.allTrademarkClassPrice = allTrademarkClassPrice;
    }

    public BigDecimal getServiceAttrServicePrice() {
        return serviceAttrServicePrice;
    }

    public void setServiceAttrServicePrice(BigDecimal serviceAttrServicePrice) {
        this.serviceAttrServicePrice = serviceAttrServicePrice;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }


    public Long getBaoJianNo() {
        return baoJianNo;
    }

    public void setBaoJianNo(Long baoJianNo) {
        this.baoJianNo = baoJianNo;
    }

    public String getBaoJianStatus() {
        return baoJianStatus;
    }

    public void setBaoJianStatus(String baoJianStatus) {
        this.baoJianStatus = baoJianStatus;
    }

    public String getBaoJianReason() {
        return baoJianReason;
    }

    public void setBaoJianReason(String baoJianReason) {
        this.baoJianReason = baoJianReason;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }
}
