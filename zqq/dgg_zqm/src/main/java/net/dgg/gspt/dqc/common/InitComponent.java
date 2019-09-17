package net.dgg.gspt.dqc.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${baseStaicUrl}")
    private String baseStaicUrl;
    @Value("${site.domain}")
    private String siteDomain;
    @Value("${sys.version}")
    private String version;

    // 初始化时执行该方法
    @PostConstruct
    public void init() {
        System.out.println(baseStaicUrl);
        applicationScope.setAttribute("baseStaticUrl", baseStaicUrl);
        applicationScope.setAttribute("version", version);
        applicationScope.setAttribute("siteDomain", siteDomain);
    }
}
