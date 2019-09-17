package net.dgg.zqq.services.payment;

import net.dgg.zqq.entity.payment.PaymentOrder;

/**
 * Created by 李程 on 2018/10/10.
 */
public interface PaymentCallbackAdapter {

    void onSuccess(String businessNo, String businessType, PaymentOrder paymentOrder);

    void onFailure(String businessNo, String busenessType,PaymentOrder paymentOrder);

}
