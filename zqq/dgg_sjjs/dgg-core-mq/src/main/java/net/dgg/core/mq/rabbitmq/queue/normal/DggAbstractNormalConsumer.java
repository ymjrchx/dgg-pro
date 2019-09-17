package net.dgg.core.mq.rabbitmq.queue.normal;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import net.dgg.core.mq.common.bean.DggMessageContainer;
import net.dgg.core.mq.rabbitmq.common.consumer.DggAbstractConsumer;
import net.dgg.core.utils.DggJsonUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

/**
 * Desc:   正常队列消费者基类
 * Author: Li Xingjiang
 * Date:   2018/9/12 11:21
 * Version: 1.0
 **/
public abstract class DggAbstractNormalConsumer<T> extends DggAbstractConsumer {

    @Override
    protected void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, DggMessageContainer dggMessageContainer) throws IOException {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T data = DggJsonUtils.json2Obj(dggMessageContainer.getData(), entityClass);
        this.handleDelivery(consumerTag, envelope, properties, data);

    }

    /**
     * 消费者业务处理
     *
     * @param consumerTag
     * @param envelope
     * @param properties
     * @param data
     * @throws IOException
     */
    protected abstract void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, T data) throws IOException;

}
