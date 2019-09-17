package net.dgg.bigdata.manager.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import net.dgg.bigdata.manager.common.filter.AuthFilter;
import net.dgg.bigdata.manager.index.service.UserAuthorizedFilter;
import net.dgg.bigdata.manager.session.SessionFilter;
import net.dgg.core.spring.DggWebConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by wu on 2018-02-22.
 */
@Configuration
public class CustomWebMvcConfigurerAdapter extends DggWebConfig implements WebMvcConfigurer {

    @Value("${spring.mvc.view.prefix}")
    private String webPath="";

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseRegisteredSuffixPatternMatch(true);
    }

    /**
     *
     * @param registry
     */
    @Override
    public void addViewControllers( ViewControllerRegistry registry ) {
        registry.addViewController( "/" ).setViewName( "forward:/index.html" );
        registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
        super.addViewControllers( registry );
    }

    @Bean
    public FilterRegistrationBean userAuthorizedFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new UserAuthorizedFilter());//添加过滤器
        registration.addUrlPatterns("*.do");//设置过滤路径，*.do所有路径
        registration.addUrlPatterns("*.html");//设置过滤路径，*.html所有路径
        registration.setName("userAuthorizedFilter");
        registration.setOrder(2);
        return registration;
    }

    @Bean
    public FilterRegistrationBean sessionFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SessionFilter());//添加过滤器
        registration.addUrlPatterns("*.do");//设置过滤路径，*.do所有路径
        registration.addUrlPatterns("*.html");//设置过滤路径，*.html所有路径
        registration.setName("sessionFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean authFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AuthFilter());//添加过滤器
       // registration.addUrlPatterns("*.do");//设置过滤路径，*.do所有路径
        registration.addUrlPatterns("*.html");//设置过滤路径，*.html所有路径
        registration.setName("authFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public GlobalExceptionResolver GlobalExceptionResolver(){
        return new GlobalExceptionResolver();
    }


    /**
     * 跨域处理,映射URL
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .maxAge(3600);
    }

    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean registration = new ServletRegistrationBean(
                dispatcherServlet);
        registration.addUrlMappings("*.do");
        registration.addUrlMappings("*.html");
        registration.addUrlMappings("/");
        return registration;
    }



    /**
     * 日期格式化处理
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter=new MappingJackson2HttpMessageConverter();
        ObjectMapper dateMapper=new ObjectMapper();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateMapper.setDateFormat(dateFormat);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        dateMapper.registerModule(simpleModule);
        converter.setObjectMapper(dateMapper);
        converters.add(converter);
        super.configureMessageConverters(converters);
    }
}
