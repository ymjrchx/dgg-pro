package net.dgg.zqq.framework.stack.io;

import com.rabbitmq.client.*;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.function.Function;

/**
 * Created by 李程 on 2018/11/9.
 */
@Slf4j
@Builder
@Data
public class RabbitMessageQueue {

    String host;

    String username;

    String password;

    Integer port;

    String vhost;

    ConnectionFactory connectionFactory;

    private Connection connection;

    private Channel commandChannel;

    @SneakyThrows
    public Channel getWorkChannel() {
        if (connection == null || !connection.isOpen()) {
            init();
        }
        return connection.createChannel();
    }

    @SneakyThrows
    public Channel getCommandChannel() {
        if (connection != null && connection.isOpen() && commandChannel.isOpen()) {
            return commandChannel;
        } else {
            if (connection == null || !connection.isOpen()) {
                init();
            }
            commandChannel = connection.createChannel();
        }
        return commandChannel;
    }

    @SneakyThrows
    private void init() {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(host);
            connectionFactory.setUsername(username);
            connectionFactory.setPassword(password);
            connectionFactory.setPort(port);
            connectionFactory.setVirtualHost(vhost);
        }
        if (connection == null || !connection.isOpen()) {
            connection = connectionFactory.newConnection();
        }
    }

    @SneakyThrows
    public void declareExchange(String exchange, BuiltinExchangeType type, boolean persistent) {
        getCommandChannel().exchangeDeclare(exchange, type, persistent);
    }

    @SneakyThrows
    public void declareQueue(String queue, boolean persistent) {
        getCommandChannel().queueDeclare(queue, persistent, false, false, null);
    }

    @SneakyThrows
    public void bindExchange2Queue(String exchange, String queue, String routeKey) {
        getCommandChannel().queueBind(queue, exchange, routeKey);
    }

    @SneakyThrows
    public void bindExchange2Exchange(String fromExchange, String destExchange, String routeKey) {
        getCommandChannel().exchangeBind(destExchange, fromExchange, routeKey);
    }

    @SneakyThrows
    public void produceText(String exchange, String routeKey, String body, boolean persistent) {
        getWorkChannel().basicPublish(exchange, routeKey, persistent ? MessageProperties.PERSISTENT_TEXT_PLAIN : MessageProperties.TEXT_PLAIN,
                body.getBytes(Charset.forName("UTF8")));
    }

    @SneakyThrows
    public void produceBin(String exchange, String routeKey, byte[] body, boolean persistent) {
        getWorkChannel().basicPublish(exchange, routeKey, persistent ? MessageProperties.PERSISTENT_BASIC : MessageProperties.BASIC,
                body);
    }

    @SneakyThrows
    public void consumeText(String queue, Function<String, Boolean> consumeEntity) {
        Channel channel = getWorkChannel();
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if (consumeEntity.apply(new String(body, "UTF-8"))) {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        channel.basicConsume(queue, false, consumer);
    }

    @SneakyThrows
    public void consumeBin(String queue, Function<byte[], Boolean> consumeEntity) {
        Channel channel = getWorkChannel();
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if (consumeEntity.apply(body)) {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        channel.basicConsume(queue, false, consumer);
    }

}
