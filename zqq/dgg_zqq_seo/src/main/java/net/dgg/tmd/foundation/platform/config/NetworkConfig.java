package net.dgg.tmd.foundation.platform.config;

import net.dgg.tmd.foundation.platform.stack.https.HttpsClientRequestFactory;
import net.dgg.tmd.foundation.platform.stack.ssh.SecureShellService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by 李程 on 2018/11/9.
 */
@Configuration
public class NetworkConfig {

    @Value("${seo.upload.path.root}")
    private String rootPath;

    @Value("${seo.upload.host.add}")
    private String host;

    @Value("${seo.upload.host.port}")
    private Integer port;

    @Value("${seo.upload.host.user}")
    private String user;

    @Value("${seo.upload.host.password}")
    private String password;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
        return restTemplate;
    }

    @Bean
    public SecureShellService ssh() {
        return SecureShellService.builder().rootPath(rootPath).host(host).port(port).user(user).password(password).build();
    }

}
