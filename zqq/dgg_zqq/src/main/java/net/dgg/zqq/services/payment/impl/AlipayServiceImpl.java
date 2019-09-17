package net.dgg.zqq.services.payment.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.extern.slf4j.Slf4j;
import net.dgg.zqq.entity.payment.PaymentOrder;
import net.dgg.zqq.services.payment.AlipayService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by 李程 on 2018/10/10.
 */

@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService {

    @Value("${com.alipay.timeout}")
    private String timeoutExpress;

    @Value("${com.alipay.notify.url}")
    private String notifyUrl;

    @Value("${com.alipay.return.url}")
    private String returnUrl;

    @Value("${com.alipay.service.url}")
    private String serviceUrl;

    @Value("${com.alipay.appId}")
    private String appId;

    @Value("${com.alipay.privateKey}")
    private String appPrivateKey;

    @Value("${com.alipay.publicKey}")
    private String appPublicKey;

    @Value("${com.alipay.charset}")
    private String charset;

    @Value("${com.alipay.format}")
    private String format;

    @Value("${com.alipay.sign.type}")
    private String signType;

    @Override
    public Map<String, Object> page(PaymentOrder paymentOrder, String tradeNo, Double feeMoney, String body) {
        AlipayClient alipayClient = new DefaultAlipayClient(serviceUrl, appId, appPrivateKey, format, charset, appPublicKey, signType);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        String returnUrl = this.returnUrl;
        returnUrl = returnUrl.replace("{tradeNo}", tradeNo);
        returnUrl = returnUrl.replace("{businessNo}", paymentOrder.getBusinessNo());
        returnUrl = returnUrl.replace("{businessType}", paymentOrder.getBusinessType());
        returnUrl = returnUrl.replace("{payType}", paymentOrder.getPayType());
        returnUrl = returnUrl.replace("{fee}", String.valueOf(paymentOrder.getFee()));
        returnUrl = returnUrl.replace("{tradeStatus}", paymentOrder.getTradeStatus());
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{'out_trade_no' : '").append(tradeNo).append("',");
        stringBuilder.append("'total_amount' : '").append(new DecimalFormat("#.##").format(feeMoney)).append("',");
        stringBuilder.append("'subject' : '").append("订单付款").append("',");
        stringBuilder.append("'body' : '").append(body).append("',");
        stringBuilder.append("'product_code' : 'FAST_INSTANT_TRADE_PAY'}");
        alipayRequest.setBizContent(stringBuilder.toString());
        Map<String, Object> ret = new HashMap<>();
        String result = null;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
            ret.put("result", result);
            return ret;
        } catch (AlipayApiException e) {
            log.error("{}", e);
        }
        return null;
    }
}
