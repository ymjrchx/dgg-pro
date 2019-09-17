package net.dgg.dqc.backservice.util;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.ExceptionTranslationStrategy;
import org.springframework.data.redis.PassThroughExceptionTranslationStrategy;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import org.springframework.data.redis.connection.jedis.JedisConverters;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * redis连接工厂
 *
 * @author tanchaowen
 */
public class RConnectionFactory implements InitializingBean, DisposableBean, RedisConnectionFactory {

    private JedisCluster jedisCluster;

    private JedisClusterConnection jedisClusterConnection;

    private static final ExceptionTranslationStrategy EXCEPTION_TRANSLATION = new PassThroughExceptionTranslationStrategy(JedisConverters.exceptionConverter());

    public RConnectionFactory(String servers) {
        String servers1 = servers;
        String[] hostandports = servers.split(";");

        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        for (String recore : hostandports) {
            String server = recore.split(":")[0];
            String port = recore.split(":")[1];
            HostAndPort hostAndPort = new HostAndPort(server, Integer.valueOf(port));
            jedisClusterNodes.add(hostAndPort);
        }
        this.jedisCluster = new JedisCluster(jedisClusterNodes);
        this.jedisClusterConnection = new JedisClusterConnection(jedisCluster);
    }

    @Override
    public RedisConnection getConnection() {
        return jedisClusterConnection;
    }

    @Override
    public RedisClusterConnection getClusterConnection() {
        return jedisClusterConnection;
    }

    @Override
    public boolean getConvertPipelineAndTxResults() {
        return true;
    }

    @Override
    public RedisSentinelConnection getSentinelConnection() {
        return jedisClusterConnection.getSentinelConnection();
    }

    @Override
    public DataAccessException translateExceptionIfPossible(RuntimeException e) {
        return EXCEPTION_TRANSLATION.translate(e);
    }

    @Override
    public void destroy() throws Exception {
        this.jedisClusterConnection.close();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
