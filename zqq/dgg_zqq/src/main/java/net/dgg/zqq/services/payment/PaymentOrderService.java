package net.dgg.zqq.services.payment;

import net.dgg.zqq.entity.payment.PaymentOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by 李程 on 2018/10/9.
 */
public interface PaymentOrderService {

    boolean existsByCondition(Map<String, Object> condition);

    String generateTradeNo(String businessType);

    Map<String, Object> generateTrade(String businessNo, String businessType, String payType, String fee, String businessBody, String openid);

    boolean save(PaymentOrder order);

    PaymentOrder findByPaymentId(String paymentId);

    List<PaymentOrder> findByCondition(Map<String, Object> condition);

    /**
     * 生成签名
     *
     * @param businessNo
     * @param businessType
     * @param fee
     * @param businessBody
     * @return
     */
    String generateSign(String businessNo, String businessType, String fee, String businessBody);
}
