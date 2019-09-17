package net.dgg.zqq.services;

import net.dgg.backservice.message.messagecenter.ClientMsgSender;
import net.dgg.backservice.message.messagecenter.entity.sms.SmsSendMsgEntity;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author 刘阳
 * @ClassName <SmsMsgService>
 * @despriction 发送短信的服务
 * @create 2018/07/23 10:54
 **/
@Service
public class SmsMsgService {
    @Autowired
    private ClientMsgSender clientMsgSender;

    @Value("${dgg.appId}")
    private String appKey;
    @Value("${smsMsg.channel}")
    private String channel;


    /**
     * 发送短信
     *
     * @param content 短信内容
     * @param phone   接收人电话
     */
    public void sendSmsMsg(String content, String phone) {
        SmsSendMsgEntity msgEntity = new SmsSendMsgEntity();
        msgEntity.setContent(content);
        msgEntity.setTarget(phone);
        msgEntity.setAppKey(appKey);
        msgEntity.setChannel(channel);
        msgEntity.setMsgId(KeyWorker.nextId());
        msgEntity.setSender(appKey);
        clientMsgSender.smsMsgPush(msgEntity);

    }

}
