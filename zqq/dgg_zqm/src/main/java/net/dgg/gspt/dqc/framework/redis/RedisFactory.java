package net.dgg.gspt.dqc.framework.redis;

import net.dgg.gspt.dqc.utils.ResourceUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by wu on 2017/8/30.
 */
public class RedisFactory {

    private static JedisCluster jedisCluster;

    private static JedisPool jedisPool;

    /**
     * 集群redis
     * @return
     */
    public static JedisCluster getJedisCluster() {
        if (jedisCluster == null) {
            String servers = RedisUtils.getRedisPriperty("redis.servers");
        	//String servers = "192.168.254.53:7001;192.168.254.53:7002;192.168.254.53:7003;192.168.254.53:7004;192.168.254.53:7005;192.168.254.53:7006";
            String[] nodes = servers.split(";");

            Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
            for (String node : nodes) {
                String server = node.split(":")[0];
                String port = node.split(":")[1];
                HostAndPort hostAndPort = new HostAndPort(server, Integer.valueOf(port));
                jedisClusterNodes.add(hostAndPort);
            }
            jedisCluster = new JedisCluster(jedisClusterNodes);
        }
        return jedisCluster;
    }

    /**
     * 单机redis
     * @return
     */
    public static JedisPool getJedisPool() {
        if (jedisPool == null) {
            Map<String, String> configMap = ResourceUtils.getResource("redis").getMap();
            jedisPool = new JedisPool(configMap.get("redis.host"), Integer.parseInt(configMap.get("redis.port")));
        }
        return jedisPool;
    }
}
