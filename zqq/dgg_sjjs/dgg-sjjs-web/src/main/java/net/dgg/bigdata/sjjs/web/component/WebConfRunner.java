package net.dgg.bigdata.sjjs.web.component;


import net.dgg.bigdata.sjjs.web.entity.WebConfParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author 刘阳
 * @ClassName <WebConfRunner>
 * @despriction 系统启动时 初始化 bean 和 redis
 * @create 2018/08/10 16:15
 **/
@Component
public class WebConfRunner implements ApplicationRunner {
    boolean runed = false;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WebConfService webConfService;

    @Autowired
    private WebConf webConf;


    public void run(ApplicationArguments args) throws Exception {
        if (runed) {
            return;
        }
        runed = true;
        List<WebConfParam> webParamList = webConfService.queryAll();
        for (WebConfParam webConfParam : webParamList) {
            try {
                webConf.setSelfAndRedis(webConfParam.getCode(), webConfParam.getValue());
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(webConfParam.getName() + " 配置初始化失败！");
            }
        }
        logger.info("web配置 redis 初始化完成！");
    }

}
