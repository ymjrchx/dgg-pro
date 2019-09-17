package net.dgg.bigdata.sjjs.web.config;

import net.dgg.bigdata.sjjs.web.interceptor.SysLoginIntecetor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @ClassName: ApplicationConfig
 * @Description: TODO
 * @Author: zxc
 * @Date: 2018/12/13 15:15
 */

@Configuration
public class ApplicationConfig extends WebMvcConfigurationSupport {
    @Autowired
    private SysLoginIntecetor sysLoginIntecetor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sysLoginIntecetor).
                addPathPatterns("/**").
//                addPathPatterns("/sysUser/**").
                        excludePathPatterns("/sysUser/login", "/imgCode/**", "/smsCode/**", "/sysUser/regist", "/sysUser/session/gettoken", "/swagger-resources/configuration/ui", "/swagger-resources", "/v2/api-docs", "/swagger-resources/configuration/security", "/");
//        "/workbench/**", "/enterprise/**", "/senior/**",
        //线索回调接口不拦截
//        registry.addInterceptor(sysLoginIntecetor).
//                        addPathPatterns("/clueApi/**").
//                excludePathPatterns("/clueApi/clueCallback.do","/clueApi/turnClue.do");
//        //线索回调接口不拦截
//        registry.addInterceptor(sysLoginIntecetor).
//                addPathPatterns("/enterprise/**").
//                excludePathPatterns("/enterprise/comSearchTreeBook");

    }

}