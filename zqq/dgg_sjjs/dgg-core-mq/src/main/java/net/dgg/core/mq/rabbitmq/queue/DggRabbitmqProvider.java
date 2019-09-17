package net.dgg.core.mq.rabbitmq.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import net.dgg.core.mq.IDggMqProvider;
import net.dgg.core.mq.common.bean.DggMessageContainer;
import net.dgg.core.mq.common.constants.DggMqConstants;
import net.dgg.core.mq.common.exception.DggMqExeption;
import net.dgg.core.mq.rabbitmq.common.DggMessageConnection;
import net.dgg.core.utils.DggJsonUtils;
import net.dgg.core.utils.common.DggKeyWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Desc:   消息发送类
 * Author: tumq
 **/
@Component
public class DggRabbitmqProvider implements IDggMqProvider {

    /**
     * 日志工具类
     */
    private static final Logger logger = LoggerFactory.getLogger(DggRabbitmqProvider.class);

    @Autowired
    private DggMessageConnection dggConnectionManager;

    /**
     * 发送点对点消息
     *
     * @param queueName 队列名称
     * @param data      数据
     */
    @Override
    public void putMessage(String queueName, Object data) throws DggMqExeption{
        putMessage(DggMqConstants.DEFAULT_CONNECTION_NAME, queueName, data);
    }

    /**
     * 发送点对点消息
     *
     * @param connectionName 连接名
     * @param queueName      队列名称
     * @param data           数据
     */
    @Override
    public void putMessage(String connectionName, String queueName, Object data) throws DggMqExeption {
        Connection connection = null;
        Channel channel = null;
        DggMessageContainer dggMessageContainer = null;
        try {
            // 创建一个连接
            connection = dggConnectionManager.getConnection(connectionName);
            // //创建一个通道
            channel = connection.createChannel();
            // 构造消息
            dggMessageContainer = getMessage(data);
            // 发布消息，不配置exchange，使用默认交换器，指定队列名称
            String exchangeName = "";
            dggMessageContainer.setExchangeName(exchangeName);
            dggMessageContainer.setQueueName(queueName);
            String message = DggJsonUtils.obj2Json(dggMessageContainer);
            channel.txSelect();
            channel.basicPublish(exchangeName, queueName, MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes(Charset.forName("UTF8")));
            channel.txCommit();
            logger.info("发送消息到队列：" + queueName + "\n 消息内容为：" + message);
        } catch (Exception e) {
            try {
                channel.txRollback();
            } catch (IOException e1) {
                e1.printStackTrace();
                throw new DggMqExeption("消息发送失败", e);
            }
            e.printStackTrace();
            throw new DggMqExeption("发送失败", e);
        } finally {
            // 消息发送完成后，关闭通道
            DggMessageConnection.closeChannel(channel);
        }
    }

    /**
     * 发布时间信息
     *
     * @param exchange 队列交换器名称
     * @param data     数据
     */
    @Override
    public void publishEvent(String exchange, Object data) throws DggMqExeption{
        publishEvent(DggMqConstants.DEFAULT_CONNECTION_NAME, exchange, data);
    }

    /**
     * 发布时间信息
     *
     * @param connectionName 连接名
     * @param exchange       队列交换器名称
     * @param data           数据
     */
    @Override
    public void publishEvent(String connectionName, String exchange, Object data) throws DggMqExeption{

        Connection connection = null;
        Channel channel = null;
        DggMessageContainer dggMessageContainer = null;
        try {
            // 创建一个连接
            connection = dggConnectionManager.getConnection(connectionName);
            // 创建一个通道
            channel = connection.createChannel();
            // 申明通道发送消息的队列，把消息发送至交换器里
            channel.exchangeDeclare(exchange, "fanout");

            dggMessageContainer = getMessage(data);
            dggMessageContainer.setExchangeName(exchange);
            String message = DggJsonUtils.obj2Json(dggMessageContainer);
            // 发布消息
            channel.txSelect();
            channel.basicPublish(exchange, "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
            channel.txCommit();
            logger.info("发布消息到队列：" + exchange + "\n 消息内容为：" + message);

        } catch (Exception e) {
            try {
                channel.txRollback();
            } catch (IOException e1) {
                e1.printStackTrace();
                throw new DggMqExeption("事件发布失败", e);
            }
            e.printStackTrace();
            throw new DggMqExeption("发送失败", e);
        } finally {
            // 消息发送完成后，关闭通道
            DggMessageConnection.closeChannel(channel);
        }
    }


    /**
     * 发送点对点消息
     *
     * @param queueName 队列名称
     */
    @Override
    public void putDeadQueueMessage(String queueName, Connection connection, DggMessageContainer dggMessageContainer, String vhost) throws DggMqExeption{
        Channel channel = null;
        try {
            // //创建一个通道
            channel = connection.createChannel();
            // 构造消息
            String message = DggJsonUtils.obj2Json(dggMessageContainer);
            // 发布消息，不配置exchange，使用默认交换器，指定队列名称
            String exchangeName = "";
            channel.txSelect();
            channel.basicPublish(exchangeName, queueName, MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes(Charset.forName("UTF8")));
            channel.txCommit();
            logger.info("发送消息到队列：" + queueName + "\n 消息内容为：" + message);

        } catch (Exception e) {
            try {
                channel.txRollback();
            } catch (IOException e1) {
                e1.printStackTrace();
                throw new DggMqExeption("消息发送失败", e);
            }
            e.printStackTrace();
            throw new DggMqExeption("发送失败", e);
        } finally {
            // 消息发送完成后，关闭通道
            DggMessageConnection.closeChannel(channel);
        }
    }

    /**
     * 发布时间信息
     *
     * @param exchange 队列交换器名称
     */
    @Override
    public void publishDeadQueueEvent(String exchange, Connection connection, DggMessageContainer dggMessageContainer, String vhost) throws DggMqExeption{
        Channel channel = null;
        try {
            // 创建一个通道
            channel = connection.createChannel();
            // 申明通道发送消息的队列，把消息发送至交换器里
            channel.exchangeDeclare(exchange, "fanout");
            // 构造消息
            String message = DggJsonUtils.obj2Json(dggMessageContainer);
            // 发布消息
            channel.txSelect();
            channel.basicPublish(exchange, "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
            channel.txCommit();
            logger.info("发布消息到队列：" + exchange + "\n 消息内容为：" + message);

        } catch (Exception e) {
            try {
                channel.txRollback();
            } catch (IOException e1) {
                e1.printStackTrace();
                throw new DggMqExeption("事件发布失败", e);
            }
            e.printStackTrace();
            throw new DggMqExeption("发送失败", e);
        } finally {
            // 消息发送完成后，关闭通道
            DggMessageConnection.closeChannel(channel);
        }
    }

    /**
     * 封装消息实体
     *
     * @param data
     * @return
     */
    private static DggMessageContainer getMessage(Object data) {
        DggMessageContainer dggMessageContainer = new DggMessageContainer();
        dggMessageContainer.setId(DggKeyWorker.nextId());
        dggMessageContainer.setData(DggJsonUtils.obj2Json(data));
        return dggMessageContainer;
    }

}
