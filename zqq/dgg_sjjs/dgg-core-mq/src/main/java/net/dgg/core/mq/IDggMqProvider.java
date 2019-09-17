package net.dgg.core.mq;

import com.rabbitmq.client.Connection;
import net.dgg.core.mq.common.bean.DggMessageContainer;
import net.dgg.core.mq.common.exception.DggMqExeption;

public interface IDggMqProvider {

    public void putMessage(String queueName, Object data) throws DggMqExeption;

    public void putMessage(String connectionName, String queueName, Object data) throws DggMqExeption;

    public void publishEvent(String exchange, Object data) throws DggMqExeption;

    public void publishEvent(String connectionName, String exchange, Object data) throws DggMqExeption;

    public void putDeadQueueMessage(String queueName, Connection connection, DggMessageContainer dggMessageContainer, String vhost) throws DggMqExeption;

    public void publishDeadQueueEvent(String exchange, Connection connection, DggMessageContainer dggMessageContainer, String vhost) throws DggMqExeption;


}
