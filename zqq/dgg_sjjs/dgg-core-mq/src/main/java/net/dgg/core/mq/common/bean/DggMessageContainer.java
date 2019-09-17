package net.dgg.core.mq.common.bean;

/**
 * 消息容器
 *
 * @author nature
 * @create 2018-01-17 13:11
 */
public class DggMessageContainer {

    private long id;

    private String data;

    private String exchangeName;

    private String queueName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

}
