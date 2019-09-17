package net.dgg.zqq.services.payment;

import net.dgg.zqq.entity.payment.PaymentOrder;

import java.util.Map;

/**
 * Created by 李程 on 2018/10/9.
 */
public interface AlipayService {

    Map<String, Object> page(PaymentOrder paymentOrder,String tradeNo, Double feeMoney, String body);

}
