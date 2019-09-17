package net.dgg.core.mq.common.bean;

/**
 * MQ白名单实体类
 */
public class DggMqWhiteBean {
    //队列名称
    private String queueName;
    //交换器名称
    private String exchangeName;

    public DggMqWhiteBean() {
    }

    public DggMqWhiteBean(String queueName, String exchangeName) {
        this.queueName = queueName;
        this.exchangeName = exchangeName;
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
}
