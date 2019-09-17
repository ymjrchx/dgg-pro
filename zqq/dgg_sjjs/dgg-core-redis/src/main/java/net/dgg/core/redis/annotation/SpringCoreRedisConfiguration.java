package net.dgg.core.redis.annotation;

import net.dgg.core.redis.DggRedisService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCoreRedisConfiguration {

    @Configuration
    @ConditionalOnMissingBean(DggRedisService.class)
    public static class RedisConfiguration {
        @Bean
        public DggRedisService dggRedisService() {
            return new DggRedisService();
        }
    }

}
