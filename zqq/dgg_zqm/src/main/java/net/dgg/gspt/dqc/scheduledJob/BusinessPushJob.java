package net.dgg.gspt.dqc.scheduledJob;

import net.dgg.gspt.dqc.services.businessPush.BusinessPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 刘阳
 * @ClassName <BusinessPushJob>
 * @despriction 商机资源推送任务
 * @create 2019/01/07 16:07
 **/
@Component
public class BusinessPushJob {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BusinessPushService businessPushService;


    @Scheduled(cron = "0 */1 * * * ?")
    public void cronJob() {
        Integer s = this.businessPushService.queryAndSend();
        logger.info(String.format("商机资源推送任务推送%s条资源", s));
    }


}
