package net.dgg.core.mq.rabbitmq.common.consumer;

import com.rabbitmq.client.*;
import net.dgg.core.mq.common.bean.DggConnectionContainer;
import net.dgg.core.mq.common.bean.DggConnectionInfoBean;
import net.dgg.core.mq.common.bean.DggMessageContainer;
import net.dgg.core.mq.common.constants.DggMqConstants;
import net.dgg.core.mq.common.exception.DggMqExeption;
import net.dgg.core.mq.common.repeated.DggMqRepeatedEnum;
import net.dgg.core.mq.rabbitmq.common.DggMessageManager;
import net.dgg.core.mq.rabbitmq.common.DggMessageConnection;
import net.dgg.core.utils.DggJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Desc:   抽象消费者基类
 * Author: Li Xingjiang
 * Date:   2018/9/12 9:47
 * Version: 1.0
 **/
public abstract class DggAbstractConsumer implements Consumer,Cloneable {

    private static final Logger logger = LoggerFactory.getLogger(DggAbstractConsumer.class);

    /**
     * 队列名称
     */
    protected String queueName = "";
    /**
     * 交换器名称
     */
    protected String exchangeName = "";
    /**
     * 消费者tag，mq会使用
     */
    private volatile String _consumerTag;
    /**
     * 判断是否需要死信队列
     */
    protected boolean hasDeadQueue = true;
    /**
     * 设置消费者从消息队列获取最大消息数据
     */
    protected int qosNum = DggMqConstants.QOS_NUM;
    /**
     * 队列是否自动删除
     */
    protected boolean autoDelete = false;
    /**
     * 队列是否持久化
     **/
    protected boolean durable = true;
    /**
     * 没有消费者是否删除队列
     */
    protected boolean exclusive = false;
    /**
     * 幂等性判断类型
     */
    protected DggMqRepeatedEnum consumerRepeatedEnum;
    /**
     * 连接名
     */
    protected String connectionName = DggMqConstants.DEFAULT_CONNECTION_NAME;
    /**
     * 通道
     */
    protected Channel channel = null;
    /**
     * 连接
     */
    protected Connection connection = null;
    /**
     * 连接信息
     */
    protected DggConnectionContainer connectionContainer;
    /**
     * 虚拟主机
     */
    protected String virtualHost = "/";
    /**
     * 消费者数量
     */
    protected int consumerNum = DggMqConstants.DEFAULT_CONSUMER_NUM;

    /**
     * 是否启用发送ACK
     */
    private boolean enableAck = true;


    @Autowired
    private DggMessageConnection dggMessageConnection;

    public DggMessageConnection getDggMessageConnection() {
        return dggMessageConnection;
    }

    public void setDggMessageConnection(DggMessageConnection dggMessageConnection) {
        this.dggMessageConnection = dggMessageConnection;
    }

    @Override
    public DggAbstractConsumer clone() throws CloneNotSupportedException{
        DggAbstractConsumer clone = (DggAbstractConsumer) super.clone();
        return clone;
    }


    public boolean isEnableAck() {
        return enableAck;
    }

    public void setEnableAck(boolean enableAck) {
        this.enableAck = enableAck;
    }

    /**
     * 获取消费者名称
     *
     * @return
     */
    protected String getConsumerName() {
        return this.getClass().getName();
    }

    /**
     * 子类实现消费方法
     *
     * @param consumerTag
     * @param envelope
     * @param properties
     * @param dggMessageContainer
     * @throws IOException
     */
    protected abstract void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, DggMessageContainer dggMessageContainer) throws IOException;

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//        logger.info("dgg-core-mq DggAbstractConsumer-接收到消息");
        try {
            //反序列化消息容器，并从容器中反序列化数据
            String message = new String(body, "UTF-8");
            DggMessageContainer dggMessageContainer = DggJsonUtils.json2Obj(message, DggMessageContainer.class);
            dggMessageContainer.setQueueName(queueName);
            this.handleDelivery(consumerTag, envelope, properties, dggMessageContainer);
            if(enableAck) {
                //确认消息
                this.channel.basicAck(envelope.getDeliveryTag(), false);
            }
        } catch (
                Exception e) {
            logger.error("dgg-core-mq处理消息异常,异常原因：{}", e);
            //确认处理失败，将消息重新放回队列
            boolean requeue = true;
            if (this.isHasDeadQueue()) {
                requeue = false;
            }
            if (this.channel != null) {
                if(enableAck) {
                    this.channel.basicNack(envelope.getDeliveryTag(), false, requeue);
                }
            }
        }
//        logger.info("dgg-core-mq DggAbstractConsumer-处理完成消息");
    }

    /**
     * 初始化消费者
     *
     * @param connectionContainer 初始化消费者需要指定使用的连接，原则上一个连接只能处理一个消费者，但是，可以开启多个通道加速消费
     */
    public void init(DggConnectionContainer connectionContainer) throws DggMqExeption{
        try {
            if (connectionContainer == null) {
                throw new DggMqExeption("消费者连接容器为空。");
            }
            this.connectionContainer = connectionContainer;
            //获取连接
            connection = connectionContainer.getConnection();
            if (connection == null) {
                throw new DggMqExeption("消费者连接为空。");
            }
            DggConnectionInfoBean dggConnectionInfoBean = connectionContainer.getConnectionInfoBean();
            if (connectionContainer == null) {
                throw new DggMqExeption("消费者连接信息为空。");
            }
            this.virtualHost = dggConnectionInfoBean.getVirtualHost();

            if (StringUtils.isEmpty(this.getQueueName())) {
                autoDelete = true;
                exclusive = true;
            }
            if(channel == null || !channel.isOpen()) {
                //创建一个通道
                channel = connection.createChannel();
                //创建队列
                Map<String, Object> arguments = null;
                if (this.isHasDeadQueue()) {
                    arguments = new HashMap<>();
                    DggMessageManager.getMessageManager().initDeadQueueMap(
                            arguments,
                            this.virtualHost);
                }
                //申明通道发送消息的队列，把消息发送至消息队列queueName，
                channel.basicQos(this.getQosNum());
                channel.queueDeclare(this.getQueueName(), this.isDurable(), this.isExclusive(), this.isAutoDelete(), arguments);
                //绑定交换器
                if (!StringUtils.isEmpty(this.getExchangeName())) {
                    channel.exchangeDeclare(this.getExchangeName(), "fanout");
                    channel.queueBind(this.getQueueName(), this.getExchangeName(), "");
                }
                logger.info("-" + this.getConsumerName() + "-开启消费者监听");
                channel.basicConsume(this.getQueueName(), !enableAck, this);
            }
        } catch (Exception e) {
            logger.error("dgg framework mq init exception,this reason:" + e);
            if (connection != null) {
                connection.abort();
            }
            throw new DggMqExeption("创建队列监听失败", e);
        }
    }

    @Override
    public void handleConsumeOk(String consumerTag) {
        logger.info(this.getConsumerName() + " consume ok! "+";consumerTag:"+consumerTag);
        this._consumerTag = consumerTag;
    }

    @Override
    public void handleCancelOk(String consumerTag) {
        logger.info(this.getConsumerName() + "cancel ok");
    }

    @Override
    public void handleCancel(String consumerTag) throws IOException {
        logger.info(this.getConsumerName() + "cancel");
        try {
            //重新连接
            dggMessageConnection.reBuilderContainerList(connectionContainer.getConnectionInfoBean());
        }catch (DggMqExeption e){
            throw new IOException(e);
        }
        logger.info(this.getQueueName() + "handleCancel重新连接 "+this);
    }

    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        try {
            //重新连接
            dggMessageConnection.reBuilderContainerList(connectionContainer.getConnectionInfoBean());
        }catch (DggMqExeption e){
            logger.info(this.getQueueName() + "handleShutdownSignal重新连接失败");
        }
        logger.info(this.getQueueName() + "handleShutdownSignal重新连接");
    }

    @Override
    public void handleRecoverOk(String consumerTag) {
        logger.info(this.getConsumerName() + "recover ok");
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String get_consumerTag() {
        return _consumerTag;
    }

    public void set_consumerTag(String _consumerTag) {
        this._consumerTag = _consumerTag;
    }

    public boolean isHasDeadQueue() {
        return hasDeadQueue;
    }

    public void setHasDeadQueue(boolean hasDeadQueue) {
        this.hasDeadQueue = hasDeadQueue;
    }

    public int getQosNum() {
        return qosNum;
    }

    public void setQosNum(int qosNum) {
        this.qosNum = qosNum;
    }

    public boolean isAutoDelete() {
        return autoDelete;
    }

    public void setAutoDelete(boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    public boolean isDurable() {
        return durable;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    public boolean isExclusive() {
        return exclusive;
    }

    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public DggConnectionContainer getConnectionContainer() {
        return connectionContainer;
    }

    public void setConnectionContainer(DggConnectionContainer connectionContainer) {
        this.connectionContainer = connectionContainer;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public int getConsumerNum() {
        return consumerNum;
    }

    public void setConsumerNum(int consumerNum) {
        this.consumerNum = consumerNum;
    }

    public DggMqRepeatedEnum getConsumerRepeatedEnum() {
        return consumerRepeatedEnum;
    }

    public void setConsumerRepeatedEnum(DggMqRepeatedEnum consumerRepeatedEnum) {
        this.consumerRepeatedEnum = consumerRepeatedEnum;
    }
}
