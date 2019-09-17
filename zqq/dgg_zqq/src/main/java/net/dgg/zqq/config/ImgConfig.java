package net.dgg.zqq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class ImgConfig {

    @Value("${img.identify.idcard.idcardIdentificate}")
    private String idcardIdentificate; //身份证接口地址

    @Value("${img.identify.idcard.accessToken}")
    private String idCardAccessToken; //身份证token

    @Value("${img.identify.license.identificate}")
    private String licenseIdentificate; //营业执照接口地址

    @Value("${img.identify.license.accessToken}")
    private String licenseAccessToken; //营业执照token

    public String getLicenseIdentificate() {
        return licenseIdentificate;
    }

    public void setLicenseIdentificate(String licenseIdentificate) {
        this.licenseIdentificate = licenseIdentificate;
    }

    public String getLicenseAccessToken() {
        return licenseAccessToken;
    }

    public void setLicenseAccessToken(String licenseAccessToken) {
        this.licenseAccessToken = licenseAccessToken;
    }

    public String getIdcardIdentificate() {
        return idcardIdentificate;
    }

    public void setIdcardIdentificate(String idcardIdentificate) {
        this.idcardIdentificate = idcardIdentificate;
    }

    public String getIdCardAccessToken() {
        return idCardAccessToken;
    }

    public void setIdCardAccessToken(String idCardAccessToken) {
        this.idCardAccessToken = idCardAccessToken;
    }
}
