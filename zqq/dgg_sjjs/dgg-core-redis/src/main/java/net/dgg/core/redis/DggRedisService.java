package net.dgg.core.redis;

import net.dgg.core.spring.DggSpringContext;
import net.dgg.core.utils.DggStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;

public class DggRedisService {

    /**
     * Redis模板
     */
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * redis连接信息
     */
    @Value("${spring.redis.cluster.nodes:}")
    private String redisUrl;

    private static JedisCluster jedisCluster;
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public DggRedisService() {
        logger.info("dgg-core-redis DggRedisService执行初始化!");
    }

    @PostConstruct
    public void initCluster() {
        jedisCluster = (JedisCluster) redisTemplate.getConnectionFactory().getClusterConnection().getNativeConnection();
    }

    /**
     * 获取Redis模板
     *
     * @return
     */
    public RedisTemplate getRedisTemplate() {
        if (unAvailable()) {
            throw new DggRedisExeption("redis集群不可用!");
        }
        return redisTemplate;
    }

    /**
     * 判断集群是否可用
     */
    public boolean unAvailable() {
        boolean flag = false;
        if (DggStringUtils.isEmpty(redisUrl)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 获取Jedis集群连接
     *
     * @return
     */
    public JedisCluster getJedisCluster() {
        JedisCluster jedisCluster = (JedisCluster) redisTemplate.getConnectionFactory().getClusterConnection().getNativeConnection();
        return jedisCluster;
    }


    public static String get(String key) {
        return jedisCluster.get(key);
    }

    public static void set(String key, String value) {
        jedisCluster.set(key, value);
    }

    public static void expire(String key, int seconds) {
        jedisCluster.expire(key, seconds);
    }

    public static void del(String key) {
        jedisCluster.del(key);
    }

    public static boolean exists(String key) {
        return jedisCluster.exists(key);
    }

    public static void lpush(String key, String value) {
        jedisCluster.lpush(key, value);
    }

    public static String lpop(String key) {
        return jedisCluster.lpop(key);
    }

    public static void ltrim(String key, int start, int end) {
        jedisCluster.ltrim(key, start, end);
    }

    public static void append(String key, String value) {
        jedisCluster.append(key, value);
    }

    /**
     * 原子自加1
     *
     * @param key
     * @return
     */
    public static Long incr(String key) {
        return jedisCluster.incr(key);
    }

    /**
     * 原子自加1
     *
     * @param byteArr
     * @return
     */
    public static Long incr(byte[] byteArr) {
        return jedisCluster.incr(byteArr);
    }

    public static Long ttl(String key) {
        return jedisCluster.ttl(key);
    }

    public static Long ttl(byte[] byteArr) {
        return jedisCluster.ttl(byteArr);
    }


}
