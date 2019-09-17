package net.dgg.core.mq.common.bean;

/**
 * 接收消息的key值
 *
 * @author nature
 * @create 2018-01-17 11:40
 */
public class DggRecievedMessageKeyEntity {

    private long messageId;
    private String consumerName;

    public DggRecievedMessageKeyEntity(long messageId, String consumerName) {
        this.messageId = messageId;
        this.consumerName = consumerName;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }
}
