package net.dgg.gspt.dqc.framework.redis;

import net.dgg.gspt.dqc.utils.ResourceUtils;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class RedisUtils {

    private static JedisCluster jedisCluster;

    private static JedisPool jedisPool;

    static {
        init();
    }

    private static void init() {
        jedisCluster = RedisFactory.getJedisCluster();
        jedisPool = RedisFactory.getJedisPool();
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

    public static String getRedisPriperty(String key) {
        return ResourceUtils.getResource("redis").getValue(key);
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
