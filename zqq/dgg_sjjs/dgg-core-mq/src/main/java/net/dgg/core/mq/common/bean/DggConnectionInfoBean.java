package net.dgg.core.mq.common.bean;

/**
 * 连接信息实体类
 */
public class DggConnectionInfoBean {

    /**
     * 连接名
     */
    private String connectionName;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 宿主机
     */
    private String host;
    /**
     * 虚拟主机
     */
    private String virtualHost = "/";
    /**
     * 去重类型 1:reis 2:mongo 3:不去重
     */
    private Integer mqRepeatedType = 1;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public Integer getMqRepeatedType() {
        return mqRepeatedType;
    }

    public void setMqRepeatedType(Integer mqRepeatedType) {
        this.mqRepeatedType = mqRepeatedType;
    }
}
