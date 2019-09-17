package net.dgg.tmd.foundation.platform.user.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Envelope;
import net.dgg.tmd.foundation.platform.user.entity.MqDataDTO;
import net.dgg.tmd.foundation.platform.user.exception.UserModuleException;
import net.dgg.tmd.foundation.platform.user.service.OrganizationManager;
import net.dgg.tmd.foundation.platform.user.service.UserManager;
import net.fblock.core.exception.FblockCoreException;
import net.fblock.messagequeue.AbstractConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * 用户同步Consumer类
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/2/24
 * Time:11:08
 */

@Component
public class UserSynchronizeConsumer extends AbstractConsumer<MqDataDTO> {
    @Autowired
    private UserManager userManager;

    @Autowired
    private OrganizationManager organizationManager;

    @Override
    /**
     * 初始化消费者
     * @param connection 初始化消费者需要指定使用的连接，原则上一个连接只能处理一个消费者，但是，可以开启多个通道加速消费
     */
    public void init(Connection connection){
        try {
            this.connection=connection;
            //创建一个通道
            channel = connection.createChannel();
            channel.queueDeclare(this.getQueueName(), true, false, false, null);
            //绑定交换器
            if (!StringUtils.isEmpty(this.getExchangeName())) {
                channel.exchangeDeclare(this.getExchangeName(), "fanout");
                channel.queueBind(this.getQueueName(), this.getExchangeName(), "");
            }
            //判断是否存在用户数据表，没有则自动创建
            userManager.hasUserModuleTable();
            organizationManager.hasOrgModuleTable();
        } catch (Exception e) {
            e.printStackTrace();
            connection.abort();
            throw new FblockCoreException("创建队列监听失败",e);
        }
    }

    /**
     * 设置MQ的Exchange名称以及对应的Queue名称
     */
    public UserSynchronizeConsumer() {
        this.exchangeName = "net.dgg.dggbase.messagequeue.BaseExchange";
        this.queueName = "net.dgg.tmd.foundation.platform.user.consumer";
    }

    @Override
    protected void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, MqDataDTO data) throws IOException {
        logger.info("===================接收到人员部门信息变更请求===================");
        mqDataHandler(data);
        logger.info("Event:" + data.getEvent());//消息触发事件名称
        logger.info("Message:" + data.getMessage());//消息内容
    }

    /**
     * 根据不同的事件执行不同的操作
     * @param data
     */
    private void mqDataHandler(MqDataDTO data){
        if(null != data){
            if("ORG_SAVE_EVENT".equals(data.getEvent())){
                organizationManager.loadAndSaveOrgFromServer(Long.parseLong(data.getMessage()));
            }else if("ORG_DEL_EVENT".equals(data.getEvent())){
                organizationManager.removeOrgByOrgId(Long.parseLong(data.getMessage()));
            }else if("USER_SAVE_EVENT".equals(data.getEvent())){
                userManager.loadAndSaveUserFromServer(Long.parseLong(data.getMessage()));
            }else{
                return;
            }
        }else{
            throw new UserModuleException("消息队列传入数据不允许为空！");
        }
    }
}
