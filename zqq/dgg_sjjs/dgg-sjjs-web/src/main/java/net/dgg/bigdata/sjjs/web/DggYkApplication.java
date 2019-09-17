package net.dgg.bigdata.sjjs.web;

import net.dgg.core.es.annotation.EnableCoreEs;
import net.dgg.core.mongo.annotation.EnableCoreMongo;
import net.dgg.core.redis.annotation.EnableCoreRedis;
import net.dgg.core.spring.annotation.EnableCoreSpring;
import net.dgg.core.message.annotation.EnableCoreMessage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableCoreEs
@EnableCoreMongo
@EnableCoreRedis
@EnableCoreSpring
@EnableCoreMessage
@SpringBootApplication
@MapperScan({"net.dgg.base", "net.dgg.bigdata.sjjs"})
public class DggYkApplication {

    public static void main(String[] args) {
        SpringApplication.run(DggYkApplication.class, args);
    }
}
