package net.dgg.zqq.entity.serviceAndAttr;

import net.dgg.zqq.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * @author 刘阳
 * @ClassName <ServiceAttr>
 * @despriction 服务属性实体
 * @create 2018/09/26 16:08
 **/
public class ServiceAttr extends BaseEntity {

    /**
     * 服务属性组合名，英文逗号分隔
     */
    private String name;

    /**
     * 服务属性code组合，英文逗号分隔
     */
    private String code;

    /**
     * 父级code
     */
    private String parentCode;

    /**
     * 父级name
     */
    private String parentName;
    /**
     * 服务费用
     */
    private BigDecimal servicePrice;

    /**
     * 官费价格或优惠价格（元）
     */
    private BigDecimal officialPrice;

    /**
     * 启用禁用
     */
    private String status;

    /**
     * 0 已删除 1正常
     */
    private Integer flag;

    /**
     * 排序
     */
    private Integer sort;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }
}
