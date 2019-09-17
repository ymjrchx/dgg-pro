package net.dgg.zqq.controller.payment;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.dgg.zqq.entity.payment.PaymentOrder;
import net.dgg.zqq.services.payment.PaymentCallbackAdapter;
import net.dgg.zqq.services.payment.PaymentOrderService;
import net.dgg.zqq.utils.SpringBeanUtil;
import net.dgg.zqq.utils.Toolkit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;

/**
 * Created by 李程 on 2018/10/10.
 */
@Api(value = "微信回调接口", description = "微信回调接口")
@RestController
public class WechatPayCallbackController {

    @Value("${com.tencent.wechat.appKey}")
    private String secretKey;

    @Autowired
    PaymentOrderService paymentOrderService;

    @ApiOperation(value = "微信回调接口", notes = "接口支付后，接收通知", httpMethod = "POST")
    @RequestMapping(value = {"/wechat/callback"}, method = {RequestMethod.GET, RequestMethod.POST})
    public void wechatCall(@RequestBody String xml, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Document document = Toolkit.DomHelper.str2doc(xml);
        Map<String, Object> map = document2map(document);
        if (map.get("return_code").equals("SUCCESS")) {
            String outTradeNo = (String) map.get("out_trade_no");
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
            String outTradeNo = (String) map.get("out_trade_no");
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
        Map<String, Object> responseMap = new LinkedHashMap<>();
        responseMap.put("return_code", "SUCCESS");
        responseMap.put("return_msg", "OK");
        Document responseDocument = map2document(responseMap);
        response.getWriter().write(Toolkit.DomHelper.doc2str(responseDocument));
    }

    public Map<String, Object> document2map(Document doc) {
        Element root = doc.getDocumentElement();
        Node node = root.getFirstChild();
        Map<String, Object> map = new LinkedHashMap<>();
        while (node != null) {
            if (node.getNodeType() == 1) {
                map.put(node.getNodeName(), node.getTextContent());
            }
            node = node.getNextSibling();
        }
        return map;
    }

    public Document map2document(Map<String, Object> map) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = document.createElement("xml");
            document.appendChild(root);
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                Object val = map.get(key);
                Element element = document.createElement(key);
                if (val instanceof Integer) {
                    element.setTextContent((Integer) val + "");
                } else if (val instanceof String) {
                    element.setTextContent((String) val);
                } else if (val instanceof Long) {
                    element.setTextContent((Long) val + "");
                } else {
                    throw new RuntimeException("无法支持");
                }
                root.appendChild(element);
            }
            return document;
        } catch (Exception e) {
            return null;
        }
    }

}
