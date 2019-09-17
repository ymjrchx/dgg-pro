package net.dgg.zqq.entity.serviceAndAttr;

import net.dgg.zqq.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * @author 刘阳
 * @ClassName <Service>
 * @despriction 服务项目
 * @create 2018/09/26 16:55
 **/
public class ServiceItem extends BaseEntity {
    /**
     * 服务名称
     */
    private String name;

    /**
     * 编号
     */
    private String number;
    /**
     * 服务所属一级类别code
     */
    private String typeLevel1Code;

    /**
     * 服务所属二级类别code
     */
    private String typeLevel2Code;

    /**
     * 服务所属三级类别code
     */
    private String typeLevel3Code;
    /**
     * 服务价格（元）
     */
    private BigDecimal servicePrice;
    /**
     * 官费价格（元）
     */
    private BigDecimal officialPrice;

    /**
     * 描述
     */
    private String describle;

    /**
     * 禁用启用
     */
    private String status;

    /**
     * 智能订单 是否计算服务项及服务属性价格
     */
    private String autoAddServicePrice;

    /**
     * 0 已删除 1正常
     */
    private Integer flag;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 产品logo
     */
    private String logoFile;

    /**
     * 产品详情图片
     */
    private String detailImgFile;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeLevel1Code() {
        return typeLevel1Code;
    }

    public void setTypeLevel1Code(String typeLevel1Code) {
        this.typeLevel1Code = typeLevel1Code;
    }

    public String getTypeLevel3Code() {
        return typeLevel3Code;
    }

    public void setTypeLevel3Code(String typeLevel3Code) {
        this.typeLevel3Code = typeLevel3Code;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    public BigDecimal getOfficialPrice() {
        return officialPrice;
    }

    public void setOfficialPrice(BigDecimal officialPrice) {
        this.officialPrice = officialPrice;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }

    public String getTypeLevel2Code() {
        return typeLevel2Code;
    }

    public void setTypeLevel2Code(String typeLevel2Code) {
        this.typeLevel2Code = typeLevel2Code;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getAutoAddServicePrice() {
        return autoAddServicePrice;
    }

    public void setAutoAddServicePrice(String autoAddServicePrice) {
        this.autoAddServicePrice = autoAddServicePrice;
    }

    public String getLogoFile() {
        return logoFile;
    }

    public void setLogoFile(String logoFile) {
        this.logoFile = logoFile;
    }

    public String getDetailImgFile() {
        return detailImgFile;
    }

    public void setDetailImgFile(String detailImgFile) {
        this.detailImgFile = detailImgFile;
    }
}
