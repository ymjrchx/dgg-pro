package net.dgg.bigdata.sjjs.web.condition.service;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import net.dgg.bigdata.common.constant.ConditionConstant;
import net.dgg.bigdata.common.constant.ConditionEnum;
import net.dgg.bigdata.sjjs.web.condition.dao.MqMsgDao;
import net.dgg.bigdata.sjjs.web.condition.dto.MqConsumerDto;
import net.dgg.bigdata.sjjs.web.condition.entity.MqMsg;
import net.dgg.core.mq.common.bean.DggMessageContainer;
import net.dgg.core.mq.rabbitmq.common.consumer.DggAbstractConsumer;
import net.dgg.core.utils.common.DggKeyWorker;
import net.dgg.core.utils.exception.DggBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/29 16:56
 * @Description: 线索mq消费
 */
@Component
public class ClueMqConsumer extends DggAbstractConsumer {


    private static final Logger logger = LoggerFactory.getLogger(ClueMqConsumer.class);

    @Resource
    private ClueService clueService;

    @Resource
    private MqMsgDao mqMsgDao;

    /**
     * 正式mq 队列名称
     */
     private String queueName = "net.dgg.iboss.bus.service.widelyResourceConsumer";

    private ClueMqConsumer() {
        super.setQueueName(this.queueName);
        //要连接无死循队列需要设置为false
        super.setHasDeadQueue(false);
    }


    @Override
    protected void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, DggMessageContainer dggMessageContainer) {
        logger.warn("接收到消息----> consumerTag：" + consumerTag + "\nenvelope：" + envelope + "\nproperties：" + properties + "\ndggMessageContainer：" + dggMessageContainer);
        if (dggMessageContainer != null) {
            try {
                logger.info("-------------->处理转线索消息：" + dggMessageContainer.getData());
                //转换消息
                MqConsumerDto mqConsumerDto = JSON.parseObject(dggMessageContainer.getData(), MqConsumerDto.class);
                //处理消息
                handleClue(mqConsumerDto);
                logger.info("处理转线索消息：" + dggMessageContainer.getData() + " 处理成功");
            } catch (Exception e) {
                //错误消息处理
                String errMsg = e.getMessage();
                if (!StringUtils.isEmpty(errMsg)) {
                    if (errMsg.length() > ConditionConstant.INT_1000) {
                        errMsg = errMsg.substring(ConditionConstant.INT_0, ConditionConstant.INT_1000);
                    }
                }
                //当错误时记录信息
                MqMsg mqMsg = new MqMsg();
                mqMsg.setId(DggKeyWorker.nextId());
                mqMsg.setError_reason(errMsg);
                mqMsg.setMessage(dggMessageContainer.getData());
                mqMsg.setDate(new Date());
                mqMsg.setStatus(ConditionConstant.INT_2);
                mqMsgDao.save(mqMsg);
                logger.warn("##################消息处理错误####################");
                logger.warn("错误原因：" + errMsg);
                logger.warn("消息体：" + dggMessageContainer.getData());
            }

        } else {
            logger.warn("消息错误--> consumerTag：" + consumerTag + "\nenvelope：" + envelope + "\nproperties：" + properties + "\ndggMessageContainer：" + dggMessageContainer);
        }
    }


    /**
     * 处理线索回调消息
     *
     * @param mqConsumerDto 接收的消息体
     */

    public void handleClue(MqConsumerDto mqConsumerDto) throws Exception {

        //任意一个参数为空
        if (StringUtils.isEmpty(mqConsumerDto.getCompanyId())
                || StringUtils.isEmpty(mqConsumerDto.getBuyUser())
                || StringUtils.isEmpty(mqConsumerDto.getStatus())) {
            throw new DggBaseException("参数缺失");
        }
        //状态是否正确
        if (StringUtils.isEmpty(ConditionEnum.forKeyToValue(mqConsumerDto.getStatus()))) {
            throw new DggBaseException("状态错误");
        }

        clueService.clueToEs(mqConsumerDto);
        //System.out.println("companyId：" + mqConsumerDto.getCompanyId() + "；buyUser：" + mqConsumerDto.getBuyUser() + "；status：" + mqConsumerDto.getStatus());
    }
}
