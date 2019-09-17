package net.dgg.gspt.dqc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class QccConfig {

    @Value("${brand.measure.url}")
    private String measureUrl; //知千秋商标注册通过率

    public String getMeasureUrl() {
        return measureUrl;
    }

    public void setMeasureUrl(String measureUrl) {
        this.measureUrl = measureUrl;
    }
}