package net.dgg.gspt.dqc.dto.user;

import java.io.Serializable;

/**
 * Created by gene on 2017/9/13.
 * desc:验证码验证dto
 */
public class VerifyImageDto implements Serializable{
    private String identifycode;
    private String type;

    public String getIdentifycode() {
        return identifycode;
    }

    public void setIdentifycode(String identifycode) {
        this.identifycode = identifycode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
