package net.dgg.zqq.services.payment.impl;

import lombok.extern.slf4j.Slf4j;
import net.dgg.zqq.api.payment.Info;
import net.dgg.zqq.services.payment.WxPayService;
import net.dgg.zqq.utils.Toolkit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.net.ssl.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Created by 李程 on 2018/10/10.
 */
@Slf4j
@Service
public class WxPayServiceImpl implements WxPayService {

    @Value("${com.tencent.wechat.appKey}")
    private String appKey;

    @Value("${com.tencent.wechat.appName}")
    private String appName;

    @Value("${com.tencent.wechat.appId}")
    private String appid;

    @Value("${com.tencent.wechat.mchId}")
    private String mchId;

    @Value("${com.tencent.wechat.subAppId}")
    private String subAppid;

    @Value("${com.tencent.wechat.subMchId}")
    private String subMchId;

    @Value("${com.tencent.wechat.deviceInfo}")
    private String deviceInfo;

    @Value("${com.tencent.wechat.signType}")
    private String signType;

    @Value("${com.tencent.wechat.feeType}")
    private String feeType;

    @Value("${com.tencent.wechat.notifyUrl}")
    private String notifyUrl;

    @Value("${com.tencent.wechat.limitPay}")
    private String limitPay;

    @Value("${com.tencent.wechat.sceneInfo}")
    private String sceneInfo;

    @Value("${com.tencent.wechat.unifiedorder}")
    private String unifiedorder;

    @Value("${com.tencent.wechat.refund.url}")
    private String refund;

    @Value("${com.tencent.wechat.refund.key.path}")
    private String refundKeyPath;

    @Value("${com.tencent.wechat.refund.key.password}")
    private String refundKeyPasswd;

    @Value("${com.tencent.wechat.refund.store.path}")
    private String refundStorePath;

    @Value("${com.tencent.wechat.refund.store.password}")
    private String refundStorePasswd;

    @Value("${com.tencent.wechat.transfers}")
    private String transfers;

    @Value("${com.tencent.wechat.transfers_mch_appid}")
    private String transfersMchAppid;

    @Value("${com.tencent.wechat.transfers_mchid}")
    private String transfersMchid;

    @Value("${com.tencent.wechat.query}")
    private String query;

    @Value("${com.tencent.wechat.bill}")
    private String bill;

    @Override
    public Map<String, Object> payCreateOrder(Order pay, Boolean subMch) {
        try {
            pay.setAppid(appid);
            pay.setMchId(mchId);
            pay.setSubAppId(subAppid);
            pay.setSubMchid(subMchId);
            pay.setDeviceInfo(deviceInfo);//设备号默认传WEB
            pay.setNonceStr(Toolkit.StringHelper.uuid());
            pay.setSignType(signType);
            pay.setLimitPay(limitPay);
            pay.setFeeType(feeType);
            pay.setNotifyUrl(notifyUrl);
            Toolkit.FormatedDate now = new Toolkit.FormatedDate();
            Toolkit.FormatedDate expireTime = now.addMinute(30);
            pay.setTimeStart(now.getFormat("yyyyMMddHHmmss"));
            pay.setTimeExpire(expireTime.getFormat("yyyyMMddHHmmss"));
            Document document = getXml(getPropertiesMap(pay));
            StringWriter out = new StringWriter();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(out));
            String xmlText = out.toString();

            Toolkit.Https https = new Toolkit.Https();
            String resTxt = https.postData(unifiedorder, xmlText, "utf-8");
            Document resp = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(resTxt.getBytes()));
            Map<String, Object> res = document2map(resp);
            if (!res.get("return_code").equals("SUCCESS") || !res.get("result_code").equals("SUCCESS")) {
                throw new RuntimeException((String) res.get("return_msg"));
            }
            Map<String, Object> ret = new LinkedHashMap<>();
            if (subMch) {
                ret.put("appid", subAppid);
                ret.put("partnerid", subMchId);
            } else {
                ret.put("appid", appid);
                ret.put("partnerid", mchId);
            }
            ret.put("prepayid", res.get("prepay_id"));
            ret.put("package", "Sign=WXPay");
            ret.put("noncestr", Toolkit.StringHelper.uuid());
            if (pay.getTradeType().equals("NATIVE")) {
                ret.put("code_url", res.get("code_url"));
            }
            ret.put("timestamp", Long.valueOf(System.currentTimeMillis() / 1000));
            ret = getSignedMap(ret);
            return ret;
        } catch (Exception e) {
            log.info("{}-{}", pay, e);
            return null;
        }
    }


    @Override
    public Map<String, Object> query(String tradeNo) {
        try {
            Map<String, Object> condition = new LinkedHashMap<>();
            condition.put("appid", appid);
            condition.put("mch_id", mchId);
            condition.put("out_trade_no", tradeNo);
            condition.put("nonce_str", Toolkit.StringHelper.uuid());
            Document doc = getXml(condition);
            String reqBody = Toolkit.DomHelper.doc2str(doc);
            Toolkit.Https https = new Toolkit.Https();
            String resTxt = https.postData(this.query, reqBody, "utf-8");
            Document resp = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(resTxt.getBytes()));
            return document2map(resp);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Map<String, Object> payRefund(Map<String, Object> refund) {
        try {
            refund.put("appid", appid);
            refund.put("mch_id", mchId);
            refund.put("nonce_str", Toolkit.StringHelper.uuid());
            refund.put("sign_type", signType);
            refund.put("refund_fee_type", feeType);

            Document document = getXml(refund);
            String requestXml = Toolkit.DomHelper.doc2str(document);
            String response = post(this.refund, requestXml.getBytes());
            Document respXml = Toolkit.DomHelper.str2doc(response);
            return document2map(respXml);
        } catch (Exception e) {
            log.debug("错误", e);
            return null;
        }
    }

    @Override
    public Map<String, Object> payUser(Map<String, Object> pay) {
        try {
            pay.put("mch_appid", transfersMchAppid);
            pay.put("mchid", transfersMchid);
            pay.put("nonce_str", Toolkit.StringHelper.uuid());
            pay.put("spbill_create_ip", Toolkit.IpHelper.getIp());

            Document doc = getXml(pay);
            String xml = Toolkit.DomHelper.doc2str(doc);
            String response = post(transfers, xml.getBytes());
            return document2map(Toolkit.DomHelper.str2doc(response));
        } catch (Exception e) {
            log.debug("{}", e);
            return null;
        }

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

    private Document getXml(Map<String, Object> order) {
        Map<String, Object> signedMap = getSignedMap(order);
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = document.createElement("xml");
            document.appendChild(root);
            for (Map.Entry<String, Object> entry : signedMap.entrySet()) {
                Object val = signedMap.get(entry.getKey());
                Element element = document.createElement(entry.getKey());
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

    private Map<String, Object> getPropertiesMap(Order bean) {
        try {
            Map<String, Object> map = new LinkedHashMap<>();
            Class<?> clazz = bean.getClass();
            while (clazz != Object.class) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (!field.isAccessible()) field.setAccessible(true);
                    Object val = field.get(bean);
                    if (val != null && !"".equals(val)) {
                        Info info = field.getAnnotation(Info.class);
                        String name = info != null ? info.value() : field.getName();
                        map.put(name, field.get(bean));
                    }
                }
                clazz = clazz.getSuperclass();
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Object> getSignedMap(Map<String, Object> ord) {
        ord.remove("sign");
        Map<String, Object> signMap = new LinkedHashMap<>();
        List<String> keys = ord.keySet().stream().filter(a -> ord.get(a) != null).sorted((a, b) -> a.compareTo(b)).collect(Collectors.toList());
        log.debug("{}", keys);
        StringBuilder stringA = new StringBuilder("");
        for (int i = 0; i < keys.size(); i++) {
            if (i > 0) stringA.append("&");
            String key = keys.get(i);
            Object val = ord.get(key);
            if (val instanceof Integer) {
                stringA.append(key + "=" + (Integer) val);
            } else if (val instanceof String) {
                stringA.append(key + "=" + (String) val);
            } else if (val instanceof Long) {
                stringA.append(key + "=" + (Long) val);
            } else {
                throw new RuntimeException("不支持的属性类型");
            }
        }
        stringA.append("&key=" + appKey);
        String s = stringA.toString();
        String sign = Toolkit.MD5.getMessageDigest(s.getBytes());
        for (int i = 0; i < keys.size(); i++) {
            signMap.put(keys.get(i), ord.get(keys.get(i)));
        }
        signMap.put("sign", sign.toUpperCase());
        return signMap;
    }

    @Override
    public Map<String, Object> billGet(String date, String billType) throws Exception {

        String nonceStr = Toolkit.StringHelper.uuid().toUpperCase();
        Map<String, Object> billQuery = new HashMap<>();
        billQuery.put("appid", appid);
        billQuery.put("bill_date", date);
        billQuery.put("bill_type", billType);
        billQuery.put("mch_id", mchId);
        billQuery.put("nonce_str", nonceStr);
        Document requestXml = getXml(getSignedMap(billQuery));
        String reqBody = Toolkit.DomHelper.doc2str(requestXml);
        String resTxt = post(this.bill, reqBody.getBytes());
        try {
            Document resp = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(resTxt.getBytes()));
            return document2map(resp);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("return_code", "SUCCESS");
            result.put("data", resTxt);
            return result;
        }
    }

    public String post(String url, byte[] params) throws Exception {
        KeyStore clientStore = KeyStore.getInstance("PKCS12");
        clientStore.load(WxPayService.class.getResourceAsStream(refundKeyPath), refundKeyPasswd.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(clientStore, refundKeyPasswd.toCharArray());
        KeyManager[] kms = kmf.getKeyManagers();

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kms, new TrustManager[]{new Toolkit.RequestX509TrustManager(WxPayService.class.getResourceAsStream(refundStorePath), refundStorePasswd)}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        URL urlObj = new URL(url);
        HttpsURLConnection httpsConn = (HttpsURLConnection) urlObj.openConnection();
        httpsConn.setSSLSocketFactory(sslContext.getSocketFactory());
        httpsConn.setHostnameVerifier(new Toolkit.TrustAnyHostnameVerifier());
        httpsConn.setRequestMethod("POST");
        httpsConn.setRequestProperty("accept", "*/*");
        httpsConn.setRequestProperty("connection", "Keep-Alive");
        httpsConn.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

        httpsConn.setRequestProperty("Content-Length", String.valueOf(params.length));

        httpsConn.setDoOutput(true);
        httpsConn.setDoInput(true);
        httpsConn.connect();

        OutputStream outputStream = httpsConn.getOutputStream();
        outputStream.write(params);
        outputStream.flush();

        InputStream inputStream = httpsConn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        char[] buff = new char[1024];
        int readCnt;
        StringBuilder s = new StringBuilder();
        while ((readCnt = inputStreamReader.read(buff)) != -1) {
            s.append(buff, 0, readCnt);
        }
        return s.toString();
    }

}
