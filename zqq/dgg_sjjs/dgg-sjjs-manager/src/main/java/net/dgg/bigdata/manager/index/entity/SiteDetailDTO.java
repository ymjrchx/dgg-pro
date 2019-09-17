package net.dgg.bigdata.manager.index.entity;

/**
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/3/22
 * Time:17:37
 */
public class SiteDetailDTO {

    private long id;

    /**
     * 监控类型
     */
    private String monitorType;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 站点地址
     */
    private String siteUrl;

    /**
     * 端口
     */
    private int port;

    /**
     * 监控间隔
     */
    private long cron;

    /**
     * 启用状态
     */
    private int status;

    /**
     * 监控状态
     */
    private int monitorStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(String monitorType) {
        this.monitorType = monitorType;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public long getCron() {
        return cron;
    }

    public void setCron(long cron) {
        this.cron = cron;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMonitorStatus() {
        return monitorStatus;
    }

    public void setMonitorStatus(int monitorStatus) {
        this.monitorStatus = monitorStatus;
    }
}
