package net.dgg.dqc.backservice.framework.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangsh on 2018/5/18 10:33
 */
@Configuration
@PropertySource(value = "classpath:/mongo.properties")
public class MongoConfig {

    private static MongoClient mongoClient = null;

    private static Logger logger = LoggerFactory.getLogger(MongoConfig.class);

    @Value("${mongo.host}")
    private String host;

    @Value("${mongo.port}")
    private int port;

    @Value("${mongo.username}")
    private String username;

    @Value("${mongo.password}")
    private String password;

    @Bean
    public MongoClient init() {
        if (mongoClient == null) {
            if (host.equals("127.0.0.1") || host.equals("192.168.3.90")) {
                mongoClient = new MongoClient(host, port);
            } else {
                ServerAddress serverAddress = new ServerAddress(host, port);
                List<ServerAddress> addrs = new ArrayList<>();
                addrs.add(serverAddress);

                //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
                MongoCredential credential = MongoCredential.createScramSha1Credential(username, "admin", password.toCharArray());
                List<MongoCredential> credentials = new ArrayList<>();
                credentials.add(credential);
                //通过连接认证获取MongoDB连接
                mongoClient = new MongoClient(addrs, credentials);
            }
        }
        logger.info("Mongo地址：" + host +":"+ port);
        return mongoClient;
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoClient onlyTest() {
        ServerAddress serverAddress = new ServerAddress("10.0.0.121", 57017);
        List<ServerAddress> addrs = new ArrayList<>();
        addrs.add(serverAddress);
        MongoCredential credential = MongoCredential.createScramSha1Credential("rwuser", "admin", "48bb67d7996f327b".toCharArray());
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(credential);
        //通过连接认证获取MongoDB连接
        return new MongoClient(addrs, credentials);
    }
}
