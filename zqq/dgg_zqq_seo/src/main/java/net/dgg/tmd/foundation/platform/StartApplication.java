package net.dgg.tmd.foundation.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 系统启动类
 *
 * @author 李程
 * @create 2018-11-21
 */
@SpringBootApplication
@EnableScheduling
public class StartApplication {

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication app = new SpringApplication(StartApplication.class);
        app.run(args);
    }

}
