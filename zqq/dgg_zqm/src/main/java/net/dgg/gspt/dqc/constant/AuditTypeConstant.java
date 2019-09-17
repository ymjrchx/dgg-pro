package net.dgg.gspt.dqc.constant;

/**
 * @author 刘阳
 * @ClassName <OderAuditTypeConstant>
 * @despriction 订单审核类型
 * @create 2018/11/15 9:58
 **/
public interface AuditTypeConstant {
    /**
     * 免审核
     */
    String NOT_AUDIT = "oder_audit_type_01";

    /**
     * 待审
     */
    String AUDIT = "oder_audit_type_02";

    /**
     * 快速审核
     */
    String FAST_AUDIT = "oder_audit_type_03";

    /**
     * 只是保存
     */
    String SAVE_ONLY = "oder_audit_type_04";
}
