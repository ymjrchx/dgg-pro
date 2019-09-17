package net.dgg.gspt.dqc.services;

import com.google.gson.Gson;
import lombok.Data;
import net.dgg.gspt.dqc.utils.HttpUtility;
import net.dgg.gspt.dqc.utils.MapUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * @author 刘阳
 * @ClassName <SmsNewService>
 * @despriction
 * @create 2018/12/27 16:36
 **/
@Service
public class SmsNewService {


    @Value("${sms.sendUrl}")
    private String url;


    @Value("${sms.sendName}")
    private String sendName;

    @Value("${sms.appName}")
    private String appName;

    @Value("${sms.channel}")
    private String channel;

    @Value("${sms.callbackUrl}")
    private String callbackUrl;

    @Value("${sms.direct}")
    private Boolean direct;


    /**
     * 发送消息
     *
     * @param phone
     * @return
     */
    public boolean sendMsg(String phone, String msg) {
        Assert.hasText(msg, "消息不能为空！");
        Assert.hasText(phone, "电话不能为空！");

        SmsMsgDto msgDto = new SmsMsgDto();
        msgDto.setMobile(phone);
        msgDto.setContent(msg);
        msgDto.setAppName(this.appName);
        msgDto.setSendName(this.sendName);
        msgDto.setChannel(this.channel);
        msgDto.setDirect(this.direct);
        msgDto.setCallbackUrl(null);
        String resStr = null;
        try {
            resStr = HttpUtility.postJson(this.url, MapUtils.convertBean(msgDto));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.hasText(resStr, "短信发送接口无返回，发送失败！");
        MsgSendRes res = new Gson().fromJson(resStr, MsgSendRes.class);
        Assert.notNull(res, "短信发送结果转换失败！");

        return res.code != null && res.code.intValue() == 0 ? true : false;

    }


    @Data
    public class SmsMsgDto {
        private String mobile; // "接收者手机号",
        private String content;//  "短信内容",
        private String channel;// "BW",
        private String sendName; //  "发送者",
        private String appName;//  "发送者应用名",
        private String callbackUrl;// "回调地址URL"
        private Boolean direct; //默认false，队列模式发送
    }

    @Data
    private class MsgSendRes {
        private String msg;
        private Integer code;
    }

}
