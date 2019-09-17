package net.dgg.gspt.dqc.framework.xst;

import com.baidu.aip.imagesearch.AipImageSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName: ClientAipOcr
 * @Description: 获取Aip client
 * @Author: jiangsh
 * @Date: 2018/9/19 9:42
 */
@Configuration
@PropertySource(value = "classpath:/application.properties")
public class ClientAipOcr {

    private static AipImageSearch client = null;
    private static String urlPrefix = null;

    private static Logger logger = LoggerFactory.getLogger(ClientAipOcr.class);

    @Value("${img.fileUrlPrefix}")
    private String fileUrlPrifix;

    @Value("${img.appId}")
    private String appId;

    @Value("${img.apiKey}")
    private String apiKey;

    @Value("${img.secretKey}")
    private String secretKey;

    @Bean
    public AipImageSearch init() {
        if (client == null)
            client = new AipImageSearch(appId, apiKey, secretKey);
        return client;
    }

    @Bean
    public String init2() {
        urlPrefix = fileUrlPrifix;
        return urlPrefix;
    }

    public static AipImageSearch getClient() {
        return client;
    }

    public static String getFileUrlPrefix() {
        return urlPrefix;
    }
}
