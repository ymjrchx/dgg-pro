package net.dgg.zqq.controller.payment;

import com.alipay.api.internal.util.AlipaySignature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.dgg.zqq.entity.payment.PaymentOrder;
import net.dgg.zqq.services.payment.PaymentCallbackAdapter;
import net.dgg.zqq.services.payment.PaymentOrderService;
import net.dgg.zqq.utils.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by 李程 on 2018/10/10.
 */
@Api(value = "支付宝回调接口", description = "支付宝回调")
@RestController
@Slf4j
public class AlipayCallbackController {

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

    @Autowired
    PaymentOrderService paymentOrderService;

    @ApiOperation(value = "支持宝回调接口", notes = "回调接口，从公网上接收", httpMethod = "POST")
    @RequestMapping(value = {"/alipay/callback"})
    public String callback(HttpServletRequest request, HttpServletResponse response) {
        log.info("支付宝回调接口成功===");
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            /**
             * 乱码解决，这段代码在出现乱码时使用。
             */
            /**
             valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
             */
            params.put(name, valueStr);
        }
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        try {
            boolean flag = AlipaySignature.rsaCheckV1(params, appPublicKey, charset, signType);
            log.info("支付宝回调签名===" + flag);
            if (flag) {
                String outTradeNo = params.get("out_trade_no");
                String tstatus = params.get("trade_status");
                if ("TRADE_SUCCESS".equals(tstatus) || "TRADE_CLOSED".equals(tstatus)) {
                    //TODO 根据支付订单号找到业务订单，进行业务变更
                    Map<String, Object> queryCondition = new LinkedHashMap<>();
                    queryCondition.put("tradeNo", outTradeNo);
                    List<PaymentOrder> orders = paymentOrderService.findByCondition(queryCondition);
                    if (orders != null) {
                        orders.forEach(order -> {
                            order.setTradeStatus("SUCCESS");
                            paymentOrderService.save(order);
                            List<PaymentCallbackAdapter> paymentCallbackAdapters = SpringBeanUtil.getBeans(PaymentCallbackAdapter.class);
                            for (PaymentCallbackAdapter paymentCallbackAdapter : paymentCallbackAdapters) {
                                if (paymentCallbackAdapter != null) {
                                    paymentCallbackAdapter.onSuccess(order.getBusinessNo(), order.getBusinessType(), order);
                                }
                            }
                        });
                    }
                } else {
                    //TODO 根据支付订单号找到业务订单，进行业务变更
                    Map<String, Object> queryCondition = new LinkedHashMap<>();
                    queryCondition.put("tradeNo", outTradeNo);
                    List<PaymentOrder> orders = paymentOrderService.findByCondition(queryCondition);
                    if (orders != null) {
                        orders.forEach(order -> {
                            order.setTradeStatus("FAILURE");
                            paymentOrderService.save(order);
                            List<PaymentCallbackAdapter> paymentCallbackAdapters = SpringBeanUtil.getBeans(PaymentCallbackAdapter.class);
                            for (PaymentCallbackAdapter paymentCallbackAdapter : paymentCallbackAdapters) {
                                if (paymentCallbackAdapter != null) {
                                    paymentCallbackAdapter.onFailure(order.getBusinessNo(), order.getBusinessType(), order);
                                }
                            }
                        });
                    }
                }
            } else {
                return new String("false".getBytes("UTF-8"));
            }
            response.setStatus(200);
            return "success";
        } catch (Exception e) {
            response.setStatus(501);
            return "failure";
        }
    }

}
