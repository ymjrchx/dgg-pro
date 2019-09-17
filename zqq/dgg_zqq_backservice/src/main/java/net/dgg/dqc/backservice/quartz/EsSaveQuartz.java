package net.dgg.dqc.backservice.quartz;

import net.dgg.dqc.backservice.service.ThreadSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 刘阳
 * @ClassName <EsSaveQuartz>
 * @despriction es 保存定时器
 * @create 2018/07/27 15:46
 **/
@Component
public class EsSaveQuartz {
    @Autowired
    private ThreadSaveService threadSaveService;

    //@Scheduled(cron = "0/1 * *  * * ? ")
    public void run() {
        threadSaveService.threadSave();
    }

}
