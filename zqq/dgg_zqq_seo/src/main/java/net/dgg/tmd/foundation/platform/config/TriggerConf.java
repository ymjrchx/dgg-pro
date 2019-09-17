package net.dgg.tmd.foundation.platform.config;

import lombok.extern.slf4j.Slf4j;
import net.dgg.tmd.foundation.platform.service.seo.SeoSiteMapBuildTask;
import net.dgg.tmd.foundation.platform.utils.Toolkit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.text.DecimalFormat;
import java.time.Clock;

/**
 * Created by 李程 on 2018/11/13.
 */
@Configuration
@Slf4j
public class TriggerConf implements SchedulingConfigurer {

    @Value("${seo.sitemap.time.schedule}")
    private String siteMapExecuteCron;

    @Autowired
    private SeoSiteMapBuildTask seoSiteMapBuildTask;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(() -> {
                    long c = Clock.systemUTC().millis();
                    log.info("开始执行生成网站地图，{}", Toolkit.DateUtils.format(c, "yyyy-MM-dd HH:mm:ss"));
                    long s = Clock.systemUTC().millis();
                    seoSiteMapBuildTask.generateSiteMap();
                    long en = Clock.systemUTC().millis();
                    log.info("生成任务总共耗时：{}秒", new DecimalFormat("#.000").format((en - s) / 1000D));
                }, getTrigger(siteMapExecuteCron)
        );
    }

    private Trigger getTrigger(String corn) {
        return triggerContext -> {
            CronTrigger trigger = new CronTrigger(corn);
            return trigger.nextExecutionTime(triggerContext);
        };
    }

    @Bean
    public SeoSiteMapBuildTask seoSiteMapBuildTask() {
        return new SeoSiteMapBuildTask();
    }
}
