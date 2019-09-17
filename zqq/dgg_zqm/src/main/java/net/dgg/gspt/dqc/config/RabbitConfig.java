package net.dgg.gspt.dqc.config;

import lombok.Data;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author 刘阳
 * @ClassName <RabbitConfig>
 * @despriction
 * @create 2019/01/07 15:00
 **/
@Configuration
@Data
public class RabbitConfig {
    private String businessRouterKey = "net.dgg.resources.center.mq.zqm";

    @Bean
    public Queue Queue() {
        return new Queue(this.businessRouterKey);
    }

}
