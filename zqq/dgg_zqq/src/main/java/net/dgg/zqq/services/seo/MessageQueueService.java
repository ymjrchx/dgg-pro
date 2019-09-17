package net.dgg.zqq.services.seo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * Created by 李程 on 2018/11/9.
 */
@Slf4j
@Builder
@Data
public class MessageQueueService {

    String host;

    String username;

    String password;

    Integer port;

    String vhost;

    String queue;

    ConnectionFactory connectionFactory;

    private Connection connection;

    private Channel channel;

    @SneakyThrows
    public Channel getChannel() {
        if (channel == null) {
            init();
        }
        return channel;
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
        channel = connection.createChannel();
        channel.queueDeclare(queue, true, false, false, null);
        channel.exchangeDeclare("static.task", "direct", true);
        channel.queueBind(queue, "static.task", queue);
    }

    @SneakyThrows
    public void putMessage(String queue, String body) {
        if (channel == null) {
            init();
        }
        channel.basicPublish("static.task", queue, MessageProperties.PERSISTENT_TEXT_PLAIN,
                body.getBytes(Charset.forName("UTF8")));
    }

}
