package net.dgg.zqq.entity.order;

import net.dgg.zqq.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * @author 刘阳
 * @ClassName <PayRecord>
 * @despriction 用户订单支付记录
 * @create 2018/09/27 10:13
 **/
public class PayRecord extends BaseEntity {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 支付金额（元）
     */
    private BigDecimal money;

    /**
     * 支付方式code
     */
    private String payWay;

    /**
     * 支付结果
     */
    private String result;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
