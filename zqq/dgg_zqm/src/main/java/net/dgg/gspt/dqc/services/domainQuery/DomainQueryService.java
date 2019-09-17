package net.dgg.gspt.dqc.services.domainQuery;

import lombok.Data;
import net.dgg.gspt.dqc.utils.HttpUtility;
import net.dgg.gspt.dqc.utils.StringUtils;
import net.dgg.gspt.dqc.utils.Toolkit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <domainQuery>
 * @despriction 域名查询Service
 * @create 2018/12/14 15:39
 **/
@Service
public class DomainQueryService {
    @Value("${domainQuery.userId}")
    private String userId;

    @Value("${domainQuery.password}")
    private String password;

    @Value("${domainQuery.url}")
    private String url;

    private String charset_gb2312 = "GB2312";
    private String charset_gbk = "GBK";

    /**
     * 命令模版
     */
    private static String sperate = "\r\n";
    private static String strCmdTemplate = "domainname" + sperate + "check" + sperate + "entityname:domain-check" + sperate + "domainname:%s" + sperate + "suffix:%s" + sperate + "." + sperate;


    /**
     * 查询域名
     *
     * @param domainName
     * @param suffix
     * @return
     */
    public ResObj queryDomain(String domainName, String suffix) {
        Assert.hasText(domainName, "域名不能为空！");
        Assert.hasText(suffix, "后缀不能为空！");
        Map<String, Object> param = new HashMap<>();
        param.put("Userid", this.userId);
        String strCmd = this.getGbkStrCmd(domainName, suffix);
        Assert.hasText(strCmd, "命令转码失败！");
        param.put("strCmd", strCmd);
        param.put("versig", this.getSign(strCmd));
        String reStr = null;
        try {
            reStr = HttpUtility.postRest(url, param, this.charset_gb2312);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.hasText(reStr, "查询失败！");

        ResObj resObj = this.getResData(reStr);
        Assert.notNull(resObj, "查询结果获取失败!");

        return resObj;
    }

    private String getGbkStrCmd(String domainName, String suffix) {
        return String.format(strCmdTemplate, domainName, suffix);
        /*try {
            return URLEncoder.encode(String.format(strCmdTemplate, domainName, suffix), this.charset_gbk);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;*/
    }

    private String getSign(String strCmd) {
        String strCmd2 = strCmd.length() < 10 ? strCmd : strCmd.substring(0, 10);
        return Toolkit.MD5.getMessageDigest((this.userId + this.password + strCmd2).getBytes());
    }


    /**
     * 或去返回数据
     *
     * @param resStr
     * @return
     */
    private ResObj getResData(String resStr) {
        try {
            DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = parser.parse(new InputSource(new StringReader(resStr)));
            NodeList returnCode = document.getElementsByTagName("returncode");
            Assert.isTrue(returnCode.getLength() > 0, "未获取到状态码！");
            Assert.isTrue("200".equals(returnCode.item(0).getTextContent()), "查询失败！");

            ResObj resObj = new ResObj();

            NodeList allow = document.getElementsByTagName("allow");
            Assert.isTrue(allow.getLength() > 0, "未获取到注册域名！");
            String allowStr = allow.item(0).getTextContent();
            if (!StringUtils.isEmpty(allowStr)) {
                resObj.setAllow(allowStr.split(","));
            }

            NodeList registered = document.getElementsByTagName("registered");
            Assert.isTrue(registered.getLength() > 0, "未获取到预注册域名！");
            String registeredStr = registered.item(0).getTextContent();
            if (!StringUtils.isEmpty(registeredStr)) {
                resObj.setRegistered(registeredStr.split(","));
            }

            NodeList premium = document.getElementsByTagName("premium");
            Assert.isTrue(premium.getLength() > 0, "未获取到预注册域名！");
            String premiumStr = premium.item(0).getTextContent();
            if (!StringUtils.isEmpty(premiumStr)) {
                for (String tm : premiumStr.split(",")) {
                    Premium premium1 = new Premium();
                    String[] tmArr = tm.split("=");
                    premium1.setDomain(tmArr[0]);
                    premium1.setPrice(tmArr[1]);
                    resObj.add(premium1);
                }
            }

            return resObj;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    @Data
    private static class ResObj {
        /**
         * 可注册
         */
        private String[] allow;

        /**
         * 已被注册
         */
        private String[] registered;

        /**
         * 溢价域名
         */
        private List<Premium> premiums = new ArrayList<>();


        public void add(Premium premium) {
            this.premiums.add(premium);
        }

    }

    @Data
    private static class Premium {
        /**
         * 域名
         */
        private String domain;
        /**
         * 价格
         */
        private String price;
    }


}
