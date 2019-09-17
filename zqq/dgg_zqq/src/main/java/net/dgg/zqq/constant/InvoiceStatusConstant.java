package net.dgg.zqq.constant;

/**
 * @ClassName:
 * @Description: 发票申请状态
 * @Author: huangl
 * @Date: 2018/11/9 16:01
 */
public interface InvoiceStatusConstant {

    /**
     * 未申请
     */
    String NOTAPPLY = "invoice_status_01";

    /**
     * 申请中
     */
    String APPLYING = "invoice_status_02";

    /**
     * 申请成功
     */
    String SUCCESS = "invoice_status_03";

    /**
     * 申请失败
     */
    String FAIL = "invoice_status_04";

}
