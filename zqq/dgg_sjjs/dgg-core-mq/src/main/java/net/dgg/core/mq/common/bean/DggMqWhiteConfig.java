package net.dgg.core.mq.common.bean;

/**
 * Desc:   白名单配置实体类
 * Author: Li Xingjiang
 * Date:   2018/9/11 19:48
 * Version: 1.0
 **/
public class DggMqWhiteConfig {

    /**
     * 启用MQ白名单开关
     */
    private boolean enableMqWhite = false;
    /**
     * MQ白名单URL
     */
    private String mqWhiteUrl = "";
    /**
     * IP端口校验开关
     */
    private boolean enableIpPort = false;
    /**
     * 系统编码校验开关
     */
    private boolean enableSysCode = false;
    /**
     * 系统编号
     */
    private String sysCode = "";
    /**
     * 系统端口号
     */
    private Integer port = 0;

    public boolean isEnableMqWhite() {
        return enableMqWhite;
    }

    public void setEnableMqWhite(boolean enableMqWhite) {
        this.enableMqWhite = enableMqWhite;
    }

    public String getMqWhiteUrl() {
        return mqWhiteUrl;
    }

    public void setMqWhiteUrl(String mqWhiteUrl) {
        this.mqWhiteUrl = mqWhiteUrl;
    }

    public boolean isEnableIpPort() {
        return enableIpPort;
    }

    public void setEnableIpPort(boolean enableIpPort) {
        this.enableIpPort = enableIpPort;
    }

    public boolean isEnableSysCode() {
        return enableSysCode;
    }

    public void setEnableSysCode(boolean enableSysCode) {
        this.enableSysCode = enableSysCode;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
