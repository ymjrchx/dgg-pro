package net.dgg.bigdata.sjjs.web.service;

import net.dgg.core.message.Sender.MqSendProvider;
import net.dgg.core.message.common.exception.DggMessageExeption;
import net.dgg.core.message.entity.SmsSendMsg;
import net.dgg.core.utils.common.DggKeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName: SmsService
 * @Description: TODO
 * @Author: zxc
 * @Date: 2018/12/14 10:32
 */

@Service
@Transactional
public class SendMsgService {

    @Autowired
    private MqSendProvider mqSendProvider;
    @Value("${dgg.appId}")
    private String appKey;
    private final String queueName = "net.dgg.backservice.message.sms.consumer.SmsConsumer";

    /**
     * 发送短信
     *
     * @param content 短信内容
     * @param phone   接收人电话
     */
    public void sendSmsMsg(String content, String phone) throws DggMessageExeption {
        mqSendProvider.setQueueName(queueName);
        SmsSendMsg smsSendMsg = new SmsSendMsg();
        smsSendMsg.setMsgId(DggKeyWorker.nextId());
        smsSendMsg.setContent(content);
        smsSendMsg.setSendName(appKey);
        smsSendMsg.setSendDate(new Date());
        smsSendMsg.setMobile(phone);
        smsSendMsg.setAppName(appKey);
        smsSendMsg.setChannel("JL");
        mqSendProvider.send(smsSendMsg);
    }


}