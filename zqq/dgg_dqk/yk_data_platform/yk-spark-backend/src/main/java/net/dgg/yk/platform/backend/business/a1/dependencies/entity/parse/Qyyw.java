package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

/**
 * 企业业务
 * Created by jiangsh on 2018/8/20 11:13
 */
public class Qyyw {
    private String pName; //业务名称
    private String pIntroduce; //业务介绍
    private String pType; //业务类型
    private String piUrl; //
    private String piName; //
    private String piPath; //

    public String getPiUrl() {
        return piUrl;
    }

    public void setPiUrl(String piUrl) {
        this.piUrl = piUrl;
    }

    public String getPiName() {
        return piName;
    }

    public void setPiName(String piName) {
        this.piName = piName;
    }

    public String getPiPath() {
        return piPath;
    }

    public void setPiPath(String piPath) {
        this.piPath = piPath;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpIntroduce() {
        return pIntroduce;
    }

    public void setpIntroduce(String pIntroduce) {
        this.pIntroduce = pIntroduce;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }
}
