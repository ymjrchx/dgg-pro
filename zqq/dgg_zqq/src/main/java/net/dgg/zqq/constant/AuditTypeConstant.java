package net.dgg.zqq.constant;

/**
 * @author 刘阳
 * @ClassName <OderAuditTypeConstant>
 * @despriction 订单审核类型
 * @create 2018/11/15 9:58
 **/
public interface AuditTypeConstant {
    /**
     * 直接提交
     */
    String NOT_AUDIT = "oder_audit_type_01";

    /**
     * 付费待审
     */
    String AUDIT = "oder_audit_type_02";

    /**
     * 审后付费
     */
    String FAST_AUDIT = "oder_audit_type_03";

    /**
     * 只是保存
     */
    String SAVE_ONLY = "oder_audit_type_04";
}
