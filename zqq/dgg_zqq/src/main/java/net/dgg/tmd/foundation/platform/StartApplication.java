package net.dgg.tmd.foundation.platform;

import net.fblock.cachemodule.CacheModuleManager;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 系统启动类
 *
 * @author nature
 * @create 2018-02-05 9:36
 */
@ServletComponentScan(basePackages = {"net.dgg.zqq.controller.common"})
@SpringBootApplication
@EnableScheduling
@ImportResource(locations = {"classpath:spring/spring-context.xml"})
public class StartApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StartApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
        // 初始化缓存模块
        CacheModuleManager.getModuleManager().init();
        //MessageManager.getMessageManager().init();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("payment.yml"), new ClassPathResource("app.yml"));
        configurer.setProperties(yaml.getObject());
        return configurer;
    }

}
