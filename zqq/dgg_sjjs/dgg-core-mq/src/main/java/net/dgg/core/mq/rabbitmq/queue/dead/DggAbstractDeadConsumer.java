package net.dgg.core.mq.rabbitmq.queue.dead;

import net.dgg.core.mq.common.constants.DggMqConstants;
import net.dgg.core.mq.rabbitmq.common.consumer.DggAbstractConsumer;

/**
 * Desc:   死信队列消费者基类
 * Author: Li Xingjiang
 * Date:   2018/9/12 11:18
 * Version: 1.0
 **/
public abstract class DggAbstractDeadConsumer extends DggAbstractConsumer {

    public DggAbstractDeadConsumer() {
        exclusive = false;
        autoDelete = false;
        hasDeadQueue = false;
        this.exchangeName = this.virtualHost + DggMqConstants.DEAD_LETTER_EXCHANGE_SUFFIX;
        this.queueName = this.virtualHost + DggMqConstants.DEAD_LETTER_QUEUE_SUFFIX;
    }

}
