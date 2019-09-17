package net.dgg.zqq.services.payment;

import lombok.Data;
import net.dgg.zqq.api.payment.Info;

import java.util.Map;

/**
 * Created by 李程 on 2018/10/9.
 */
public interface WxPayService {

    //TODO 增加微信支付第三方接口

    public Map<String, Object> payCreateOrder(Order pay, Boolean subMch);

    public Map<String, Object> payRefund(Map<String, Object> refund);

    public Map<String, Object> payUser(Map<String, Object> pay);

    public Map<String, Object> billGet(String date, String billType) throws Exception;

    public Map<String, Object> query(String tradeNo);

    @Data
    public static class Order {

        @Info("appid")
        private String appid;
        @Info("mch_id")
        private String mchId;
        @Info("subAppId")
        private String subAppId;
        @Info("subMchid")
        private String subMchid;
        @Info("device_info")
        private String deviceInfo;
        @Info("nonce_str")
        private String nonceStr;
        @Info("sign")
        private String sign;
        @Info("sign_type")
        private String signType;
        @Info("body")
        private String body;
        @Info("detail")
        private String detail;
        @Info("attach")
        private String attach;
        @Info("out_trade_no")
        private String outTradeNo;
        @Info("fee_type")
        private String feeType;
        @Info("total_fee")
        private Integer totalFee;
        @Info("spbill_create_ip")
        private String spbillCreateIp;
        @Info("time_start")
        private String timeStart;
        @Info("time_expire")
        private String timeExpire;
        @Info("goods_tag")
        private String goodsTag;
        @Info("notify_url")
        private String notifyUrl;
        @Info("trade_type")
        private String tradeType;
        @Info("limit_pay")
        private String limitPay;
        @Info("scene_info")
        private String sceneInfo;
        @Info("product_id")
        private String productId;
        @Info("openid")
        private String openid;

    }

}
