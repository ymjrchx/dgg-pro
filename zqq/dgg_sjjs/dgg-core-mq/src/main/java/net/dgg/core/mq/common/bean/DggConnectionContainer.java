package net.dgg.core.mq.common.bean;

import com.rabbitmq.client.Connection;
import net.dgg.core.mq.rabbitmq.common.DggMessageConnection;

/**
 * 消息容器
 */
public class DggConnectionContainer {

    /**
     * 消息连接
     */
    private Connection connection;
    /**
     * 连接信息
     */
    private DggConnectionInfoBean connectionInfoBean;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public DggConnectionInfoBean getConnectionInfoBean() {
        return connectionInfoBean;
    }

    public void setConnectionInfoBean(DggConnectionInfoBean connectionInfoBean) {
        this.connectionInfoBean = connectionInfoBean;
    }
}
