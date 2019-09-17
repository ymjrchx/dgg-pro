package net.dgg.bigdata.manager;

import net.dgg.core.mongo.annotation.EnableCoreMongo;
import net.dgg.core.redis.annotation.EnableCoreRedis;
import net.dgg.core.spring.annotation.EnableCoreSpring;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by wu on 2017/8/23.
 */
@EnableCoreMongo
@EnableCoreRedis
@EnableCoreSpring
@SpringBootApplication
@MapperScan("net.dgg.base")
public class ApplicationStart {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ApplicationStart.class, args);
    }
}