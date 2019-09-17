package net.dgg.core.redis.annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class RedisConnectionFactoryConfiguration {

    @Value("${spring.redis.cluster.nodes}")
    String redisCluster;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        for (String host : redisCluster.split("[,;]")) {
            String add = host.split(":")[0];
            Integer port = Integer.valueOf(host.split(":")[1]);
            redisClusterConfiguration.addClusterNode(new RedisClusterNode(add, port));
        }
        return new JedisConnectionFactory(redisClusterConfiguration);
    }

}
