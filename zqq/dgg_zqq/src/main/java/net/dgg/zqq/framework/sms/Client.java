package net.dgg.zqq.framework.sms;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/5/20.
 */
//TODO 需要补充类注释，说明类的作用
public class Client {

    private Logger logger = Logger.getLogger(Client.class);
    /*
    * webservice服务器定义
    */
    //调用注册方法可能不成功。
    //java.io.IOException: Server returned HTTP response code: 400 for URL: http://sdk2.zucp.net:8060/webservice.asmx。
    //如果出现上述400错误，请参考第102行。
    //如果您的系统是utf-8，收到的短信可能是乱码，请参考第102，295行
    //可以根据您的需要自行解析下面的地址
    //http://sdk2.zucp.net:8060/webservice.asmx?wsdl
    private String serviceURL = "http://sdk.entinfo.cn:8061/webservice.asmx/mdsmssend";
    private String sn = "SDK-WSS-010-09391";// 序列号
    private String pwd = "DFD5A3C3573B850BE0991C6C1EA5B4EC";// 密码

    private String apName = "bjdgg"; //用户名
    private String apPassword = "bjdgg2016"; //密码
    //    private String batchUrl = "https://120.55.160.111:4082/wgws/BatchSubmit"; //批量发送地址
    private String batchUrl = "http://139.224.36.226:1082/wgws/BatchSubmit"; //批量发送地址


    /**
     * 金伦短信批量发送 用于营销短信(行业短信一次只能提交一个号码，营销短信可以一次提交多个号码【多个号码以逗号隔开】)
     * 如果是行业短信多用户发送，就循环调用此方法
     * @param mobile 多个电话号码以逗号隔开
     * @param content 发送短信的内容
     * @param type 发送类型[1 行业短信，2营销短信](营销短信在发送时，密码前要加#，行业短信不需要)
     * @return 返回error 状态码，msg 状态说明 sum 发送总条数  success 成功条数
     */
    public Map<String,Object> sendMsgBatch(String mobile,String content,int type){
        Map<String,String> map = new HashMap<>();
        map.put("apName",this.apName);
        if(type==1){
            map.put("apPassword",this.apPassword);
        }else{
            map.put("apPassword","#"+this.apPassword);
        }
        map.put("srcId","");
        map.put("ServiceId","");
        map.put("calledNumber",mobile);
        map.put("content",content);
        map.put("sendTime","");
        return HttpClientUtils.URLPost(this.batchUrl, map);
    }


    /**
     * 解析返回的xml信息
     * @param rest xml字符串
     * @return
     */
    private Map<String,String> returnDescription(String rest){
        //判断传入的xml字符串是否为空
        if(org.springframework.util.StringUtils.isEmpty(rest)){
            logger.error("xml字符串为空!");
            return null;
        }
        List<Map<String,String>> list = new ArrayList<>();
        int success = 0;
        int sum = 0;
        try{
            Document doc = null;
            doc = DocumentHelper.parseText(rest); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            List<Element> elements = rootElt.elements();
            sum = elements.size();
            for (Element element:elements) {
                List<Element> eles = element.elements();
                Map<String,String> map = new HashMap<>();
                for (Element ele:eles) {
                    String key = ele.getName();
                    String value = ele.getText();
                    if(key.equals("error") && value.equals("0")){
                        success++;
                    }
                    map.put(key,value);
                }
                list.add(map);
            }
        }catch (Exception e){
            logger.error("数据返回xml解析错误!xml="+rest);
        }


        //判断用户发送的成功的条数
        Map<String,String> map = list.get(0);
        int error = Integer.parseInt(map.get("error"));
        String msg = map.get("message");

        Map<String,String> returnMap = new HashMap<>();
        returnMap.put("error",error+"");
        returnMap.put("msg",""==msg?"成功":msg);
        returnMap.put("sum",sum+"");
        returnMap.put("success",success+"");
        return returnMap;
    }

    public static void main(String[] args) {
        StringBuffer smsContent = new StringBuffer();
        smsContent.append("【顶呱呱集团】"+"您本次登录的验证码：").append("5468");
        smsContent.append("，如非本人操作，请尽快修改密码。");

        new Client().sendMsgBatch("17765551395",smsContent.toString(),1);
    }
}
