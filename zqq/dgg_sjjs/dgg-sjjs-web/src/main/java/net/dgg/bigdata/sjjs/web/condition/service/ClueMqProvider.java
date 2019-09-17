package net.dgg.bigdata.sjjs.web.condition.service;

import net.dgg.core.mq.common.exception.DggMqExeption;
import net.dgg.core.mq.rabbitmq.queue.DggRabbitmqProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @Author: 陈万国
 * @Date: 2019/1/2 09:07
 * @Description: mq消息发送
 */
@Service
public class ClueMqProvider {

    /**
     * 设置生产者队列名称
     */
    @Value("${mq.clueProvider.queueName}")
    private String queueName;

    @Resource
    private DggRabbitmqProvider dggRabbitmqProvider;

    public void mqProvider(Object data) throws DggMqExeption {
        dggRabbitmqProvider.putMessage(queueName, data);
    }


}
