package net.dgg.dqc.backservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

/**
 * @Title: InitComponent
 * @Description:
 * @Auther: 汤宏
 * @Date: 2018/05/03 15:02
 */
@Component
public class InitComponent {
    @Autowired
    private ServletContext applicationScope;


    // 初始化时执行该方法
    @PostConstruct
    public void init() {
        //applicationScope.setAttribute("cmsBaseUrl", cmsBaseUrl);
    }
}
