package net.dgg.zqq.entity.serviceAndAttr;

import net.dgg.zqq.entity.BaseEntity;

/**
 * @author 刘阳
 * @ClassName <ServiceAttrRelation>
 * @despriction 服务与服务属性关系表
 * @create 2018/09/26 17:49
 **/
public class ServiceAttrRelation extends BaseEntity {
    /**
     * 服务ID
     */
    private Long serviceItemId;

    /**
     * 服务属性ID
     */
    private Long serviceAttrId;


    public Long getServiceItemId() {
        return serviceItemId;
    }

    public void setServiceItemId(Long serviceItemId) {
        this.serviceItemId = serviceItemId;
    }

    public Long getServiceAttrId() {
        return serviceAttrId;
    }

    public void setServiceAttrId(Long serviceAttrId) {
        this.serviceAttrId = serviceAttrId;
    }
}
