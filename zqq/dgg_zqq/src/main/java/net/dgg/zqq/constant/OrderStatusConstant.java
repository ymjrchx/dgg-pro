package net.dgg.zqq.constant;

/**
 * @author 刘阳
 * @ClassName <OrderStatusConstant>
 * @despriction 订单状态
 * @create 2018/10/11 19:39
 **/
public interface OrderStatusConstant {
    /**
     * 未付款
     */
    String NOT_PAY = "order_status_obligation";

    /**
     * 已付款
     */
    String PAYED = "order_status_spend";

    /**
     * 办理中
     */
    String IN_PROCESS = "order_status_inprocess";

    /**
     * 已完结
     */
    String OVER = "order_status_completed";

    /**
     * 已评价
     */
    String COMMENTED = "order_status_evaluate";

    /**
     * 待审
     */
    String WAITING_AUDIT = "order_status_not_audit";

    /**
     * 审核失败
     */
    String AUDIT_FAILD = "order_status_audit_faild";


    /**
     * 待提交
     */
    String WAITING_SUBMIT = "order_status_waiting_submit";

}
