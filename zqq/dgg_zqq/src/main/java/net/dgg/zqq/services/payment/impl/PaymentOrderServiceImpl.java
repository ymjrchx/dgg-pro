package net.dgg.zqq.services.payment.impl;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dgg.zqq.dao.payment.PaymentOrderDao;
import net.dgg.zqq.entity.payment.PaymentOrder;
import net.dgg.zqq.services.payment.AlipayService;
import net.dgg.zqq.services.payment.PaymentCallbackAdapter;
import net.dgg.zqq.services.payment.PaymentOrderService;
import net.dgg.zqq.services.payment.WxPayService;
import net.dgg.zqq.utils.DateUtils;
import net.dgg.zqq.utils.PrimaryKeyUtils;
import net.dgg.zqq.utils.SpringBeanUtil;
import net.dgg.zqq.utils.Toolkit;
import net.dgg.zqq.utils.payment.QRCodeHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.resource.NotSupportedException;
import java.util.*;

/**
 * Created by 李程 on 2018/10/9.
 */
@Service
@Slf4j
public class PaymentOrderServiceImpl implements PaymentOrderService {

    @Autowired
    PaymentOrderDao paymentOrderDao;

    @Autowired
    WxPayService wxPayService;

    @Autowired
    AlipayService alipayService;

    @Value("${com.tencent.wechat.appName}")
    private String appName;

    @Value("${com.tencent.wechat.logo}")
    private String logo;

    private static final String secret = "ABCDEFGHfdsajfklaspl;fs;afkd;sa";

    @Override
    public String generateSign(@NonNull String businessNo, @NonNull String businessType, @NonNull String fee, @NonNull String businessBody) {
        return Toolkit.MD5.getMessageDigest((businessNo + businessType + fee + businessBody + secret).getBytes());
    }

    @Override
    public boolean existsByCondition(Map<String, Object> condition) {
        List<PaymentOrder> list = paymentOrderDao.findByProps(condition);
        if (list != null && !list.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String generateTradeNo(String businessType) {
        String tradeNo;
        int idx = 1;
        while (true) {
            tradeNo = businessType.concat("-").concat(DateUtils.getCurrentByFormat("yyyyMMddHHmmss")).concat("-").concat(String.format("%06d", idx));
            Map<String, Object> tradeNoQuery = new LinkedHashMap<>();
            tradeNoQuery.put("tradeNo", tradeNo);
            if (!existsByCondition(tradeNoQuery)) {
                return tradeNo;
            } else {
                idx++;
            }
        }
    }

    @Override
    @SneakyThrows
    public Map<String, Object> generateTrade(@NonNull java.lang.String businessNo, @NonNull java.lang.String businessType, @NonNull String payType, @NonNull String fee, @NonNull java.lang.String businessBody, String openid) {
        String generateTradeNo = generateTradeNo(businessType);
        val order = PaymentOrder.builder()
                .businessNo(businessNo)
                .businessType(businessType)
                .createTime(new Date())
                .payType(payType)
                .fee(Double.valueOf(fee))
                .paymentId(PrimaryKeyUtils.getId())
                .tradeNo(generateTradeNo)
                .updateTime(new Date())
                .tradeStatus("NEW")
                .build();
        paymentOrderDao.create(order);
        switch (payType) {
            case "wechat":
                val payOrder = new WxPayService.Order();
                payOrder.setBody(appName + "-" + "订单：[" + businessNo + "]");
                payOrder.setOutTradeNo(generateTradeNo);
                payOrder.setSpbillCreateIp(Toolkit.IpHelper.getIp());
                payOrder.setTotalFee((int) (Double.valueOf(fee) * 100));
                payOrder.setTradeType("NATIVE");
                payOrder.setProductId(businessNo);
                try {
                    val resp = wxPayService.payCreateOrder(payOrder, false);
                    if (resp == null) {
                        onFailure(generateTradeNo);
                        return null;
                    } else {
                        Map<String, Object> sdk = new LinkedHashMap<>();
                        sdk.put("tradeNo", generateTradeNo);
                        sdk.put("payOrder", resp);
                        Base64.Encoder encoder = Base64.getEncoder();
                        String code = (String) resp.get("code_url");
                        byte[] data = QRCodeHelper.makeArr(code, null, PaymentOrder.class.getResourceAsStream("/temps/logo.png"));
                        sdk.put("qrcode", "data:image/png;base64,".concat(new String(encoder.encode(data))));
                        return sdk;
                    }
                } catch (Exception e) {
                    onFailure(generateTradeNo);
                    return null;
                }
            case "alipay":
                val obj = alipayService.page(order, generateTradeNo, Double.valueOf(fee), appName + "-" + "订单：[" + businessNo + "]");
                if (obj != null) {
                    obj.put("tradeNo", generateTradeNo);
                    return obj;
                } else {
                    onFailure(generateTradeNo);
                    return null;
                }
            case "js":
                val payOrder1 = new WxPayService.Order();
                payOrder1.setBody(appName + "-" + "订单：[" + businessNo + "]");
                payOrder1.setOutTradeNo(generateTradeNo);
                payOrder1.setSpbillCreateIp(Toolkit.IpHelper.getIp());
                payOrder1.setTotalFee((int) (Double.valueOf(fee) * 100));
                payOrder1.setTradeType("JSAPI");
                payOrder1.setProductId(businessNo);
                payOrder1.setOpenid(openid);
                try {
                    val resp1 = wxPayService.payCreateOrder(payOrder1, false);
                    if (resp1 == null) {
                        onFailure(generateTradeNo);
                        return null;
                    } else {
                        Map<String, Object> sdk1 = new LinkedHashMap<>();
                        sdk1.put("tradeNo", generateTradeNo);
                        sdk1.put("payOrder", resp1);
                        return sdk1;
                    }
                } catch (Exception e) {
                    onFailure(generateTradeNo);
                    return null;
                }
            default:
                throw new NotSupportedException("不支支持的支付类型");
        }
    }

    private void onFailure(String tradeNo) {
        Map<String, Object> queryCondition = new LinkedHashMap<>();
        queryCondition.put("tradeNo", tradeNo);
        List<PaymentOrder> orders = findByCondition(queryCondition);
        if (orders != null) {
            orders.forEach(order -> {
                order.setTradeStatus("FAILURE");
                save(order);
                List<PaymentCallbackAdapter> paymentCallbackAdapters = SpringBeanUtil.getBeans(PaymentCallbackAdapter.class);
                for (PaymentCallbackAdapter paymentCallbackAdapter : paymentCallbackAdapters) {
                    if (paymentCallbackAdapter != null) {
                        try {
                            paymentCallbackAdapter.onFailure(order.getBusinessNo(), order.getBusinessType(), order);
                        } catch (Exception e) {
                            log.error("{}", e);
                        }
                    }
                }
            });
        }
    }

    @Override
    public boolean save(PaymentOrder order) {
        int operation;
        if (StringUtils.isNotEmpty(order.getPaymentId())) {
            operation = paymentOrderDao.update(order);
        } else {
            operation = paymentOrderDao.create(order);
        }
        return operation > 0;
    }

    @Override
    public PaymentOrder findByPaymentId(String paymentId) {
        Map<String, Object> condition = new LinkedHashMap<>();
        condition.put("paymentId", paymentId);
        List<PaymentOrder> orders = paymentOrderDao.findByProps(condition);
        if (orders == null || orders.isEmpty()) {
            return null;
        }
        return orders.get(0);
    }

    @Override
    public List<PaymentOrder> findByCondition(Map<String, Object> condition) {
        return paymentOrderDao.findByProps(condition);
    }
}
