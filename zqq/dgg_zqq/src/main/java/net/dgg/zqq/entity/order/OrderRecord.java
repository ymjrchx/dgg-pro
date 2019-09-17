package net.dgg.zqq.entity.order;

import net.dgg.zqq.entity.BaseEntity;

/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/11/13 18:36
 */
public class OrderRecord extends BaseEntity {

    /**
     * 订单Id
     */
    private Long orderId;

    /**
     * 改变后的状态值
     */
    private String status;

    /**
     * 操作
     */

    private String operation;

    /**
     * 审核人
     */
    private String auditor;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
