package net.dgg.gspt.dqc.framework;

import net.dgg.gspt.dqc.framework.interceptor.AntiCrawlerInterceptor;
import net.dgg.gspt.dqc.framework.interceptor.AuthcInteceptor;
import net.dgg.gspt.dqc.framework.interceptor.DefaultInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wu on 2017/8/25.
 */
@Configuration
public class DGGWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
    @Value("${requestRecord.urls}")
    private String requestRecordFilterUrls;
    @Value("${antiCrawler.urls}")
    private String antiCrawlerInterceptUrls;

    @Autowired
    private AntiCrawlerInterceptor antiCrawlerInterceptor;

    Logger logger = LoggerFactory.getLogger(DGGWebMvcConfigurerAdapter.class);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(defaultInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(authcInteceptor()).addPathPatterns("/**");
        //registry.addInterceptor(antiCrawlerInteceptor()).addPathPatterns(antiCrawlerInterceptUrls.split(","));
        //设置默认的拦截器，放置到最后
        super.addInterceptors(registry);
    }

    /*@Bean
    public RequestRecordFilter requestRecordFilter() {
        return new RequestRecordFilter();
    }*/

    @Bean
    public DefaultInterceptor defaultInterceptor() {
        return new DefaultInterceptor();
    }

    @Bean
    public AuthcInteceptor authcInteceptor() {
        return new AuthcInteceptor();
    }

    @Bean
    public AntiCrawlerInterceptor antiCrawlerInteceptor() {
        return this.antiCrawlerInterceptor;
    }

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
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
    //@Bean
    /*public FilterRegistrationBean filterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(requestRecordFilter());
        registration.addUrlPatterns(requestRecordFilterUrls.split(","));
        registration.setName("requestRecordFilter");
        registration.setOrder(1);
        return registration;
    }*/

}
