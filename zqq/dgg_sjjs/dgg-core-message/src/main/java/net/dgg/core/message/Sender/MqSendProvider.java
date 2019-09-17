/**
 * FileName: MqSendProvider
 * Author:   tumq
 * Date:     2018/12/13 9:51
 * Description: 通过消息队列发送消息
 */
package net.dgg.core.message.Sender;

import net.dgg.core.mq.IDggMqProvider;
import net.dgg.core.utils.DggStringUtils;
import net.dgg.core.message.common.exception.DggMessageExeption;
import net.dgg.core.message.entity.IMsgEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br> 
 * 〈通过消息队列发送消息〉
 *
 * @author tumq
 * @create 2018/12/13
 */
@Component
public class MqSendProvider implements ISendProvider {

    @Autowired
    private IDggMqProvider mqProvider;
    @Value("${mq.messagequeue.default:}")
    private String queueName;

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    @Override
    public void send(IMsgEntity entity) throws DggMessageExeption {
        if(DggStringUtils.isEmpty(queueName)){
            throw new DggMessageExeption("发送的消息队列为空，不能发送消息，请设置消息队列!");
        }
        try {
            mqProvider.putMessage(queueName, entity);
        }catch(Exception e){
            throw new DggMessageExeption("发送消息失败！",e);
        }
    }
}