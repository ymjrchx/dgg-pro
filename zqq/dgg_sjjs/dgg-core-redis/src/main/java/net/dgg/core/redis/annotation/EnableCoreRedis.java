package net.dgg.core.redis.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({SpringCoreRedisConfiguration.class, RedisConnectionFactoryConfiguration.class})
@Documented
@Inherited
public @interface EnableCoreRedis {
}
