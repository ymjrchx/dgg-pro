package net.dgg.core.mq.rabbitmq.common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import net.dgg.core.mq.common.bean.DggConnectionContainer;
import net.dgg.core.mq.common.bean.DggConnectionInfoBean;
import net.dgg.core.mq.common.constants.DggMqConstants;
import net.dgg.core.mq.common.exception.DggMqExeption;
import net.dgg.core.utils.DggValidateUtil;
import net.dgg.core.utils.exception.DggBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 消息连接器
 */
@Component
public class DggMessageConnection {

    protected static Logger logger = LoggerFactory.getLogger(DggMessageConnection.class);
    /**
     * 连接池
     */
    private ConcurrentHashMap<String, List<DggConnectionContainer>> connectionMap = new ConcurrentHashMap<>();

    /**
     * 连接池
     */
    private ConcurrentHashMap<String, DggConnectionInfoBean> connectionInfoMap = new ConcurrentHashMap<>();


    private static final int POOL_SIZE = 20;

    public DggMessageConnection() {
        //监控连接状态
        monitorConnection();
    }

    /**
     * 添加默认连接
     *
     * @param dggConnectionInfoBean
     */
    public Connection addDefaultConnection(DggConnectionInfoBean dggConnectionInfoBean) throws DggMqExeption{
        Connection connection = null;
        if (dggConnectionInfoBean != null) {
            DggValidateUtil.strNotEmpty(dggConnectionInfoBean.getConnectionName(), DggBaseException.class, "连接名不可为空");
            DggValidateUtil.strNotEmpty(dggConnectionInfoBean.getHost(), DggBaseException.class, "主机IP不可为空");
            DggValidateUtil.strNotEmpty(dggConnectionInfoBean.getPassword(), DggBaseException.class, "密码不可为空");
            DggValidateUtil.notNull(dggConnectionInfoBean.getPort(), DggBaseException.class, "端口不可为空");
            DggValidateUtil.strNotEmpty(dggConnectionInfoBean.getUsername(), DggBaseException.class, "用户名不可为空");
            DggValidateUtil.strNotEmpty(dggConnectionInfoBean.getVirtualHost(), DggBaseException.class, "虚拟主机不可为空");

            List<DggConnectionContainer> connectionContainerList = builderContainerList(dggConnectionInfoBean);
            //获取连接名
            String connectionName = dggConnectionInfoBean.getConnectionName();
            connectionMap.put(connectionName, connectionContainerList);

            connection = getConnection(connectionName);
        }
        return connection;
    }

    /**
     * 添加连接信息
     *
     * @param dggConnectionInfoBean
     */
    private void addConnectionInfo(DggConnectionInfoBean dggConnectionInfoBean) {
        if (dggConnectionInfoBean != null) {
            if (!connectionInfoMap.containsKey(dggConnectionInfoBean.getConnectionName())) {
                connectionInfoMap.put(dggConnectionInfoBean.getConnectionName(), dggConnectionInfoBean);
            }
        }
    }

    /**
     * 添加连接信息
     *
     * @param dggConnectionInfoBean
     */
    public Connection addConnection(DggConnectionInfoBean dggConnectionInfoBean) throws DggMqExeption{
        Connection connection = null;
        if (dggConnectionInfoBean != null) {
            String defaultName = DggMqConstants.DEFAULT_CONNECTION_NAME;
            if (defaultName.equals(dggConnectionInfoBean.getConnectionName())) {
                throw new DggMqExeption("连接名不能为" + defaultName);
            }
            //添加连接信息
            addConnectionInfo(dggConnectionInfoBean);
            //添加连接
            connection = addDefaultConnection(dggConnectionInfoBean);
        }
        return connection;
    }

    /**
     * 根据名称获取队列连接
     *
     * @param connectionName
     * @return
     */
    public DggConnectionContainer getConnectionContainer(String connectionName) {
        DggConnectionContainer connectionContainer = null;
        List<DggConnectionContainer> connectionContainerList = connectionMap.get(connectionName);
        if (connectionContainerList != null && connectionContainerList.size() > 0) {
            int size = connectionContainerList.size();
            Random random = new Random();
            int local = random.nextInt(size);
            connectionContainer = connectionContainerList.get(local);
        }
        return connectionContainer;
    }

    /**
     * 根据名称获取队列连接,如果连接不存在或已断开，则重新创建连接
     *
     * @param connectionName
     * @return
     */
    public Connection getConnection(String connectionName) throws DggMqExeption{
        Connection connection = null;
        DggConnectionContainer connectionContainer = getConnectionContainer(connectionName);
        if (connectionContainer != null) {
            connection = connectionContainer.getConnection();
        }
        //判断连接是否为空
        if (connection == null || !connection.isOpen()) {
            connection = createConnection(connectionContainer);
        }
        return connection;
    }

    /**
     * @tumq 2018/10/9
     * @param dggConnectionInfoBean
     */
    public synchronized void reBuilderContainerList(DggConnectionInfoBean dggConnectionInfoBean) throws DggMqExeption{
        /*检测已存在的connectionContainer连接是否都打开，如果连接已关闭，则删除该连接*/
        List<DggConnectionContainer> connectionContainerList = connectionMap.get(dggConnectionInfoBean.getConnectionName());
        for (DggConnectionContainer connectionContainer:connectionContainerList) {
            if(connectionContainer.getConnection() ==null || !connectionContainer.getConnection().isOpen()){
                createConnection(connectionContainer);
            }
        }
        /*检测已存在的connectionContainer连接是否都打开，如果连接已关闭，则删除该连接*/
    }

    /**
     * 获取消息连接
     *
     * @return
     */
    public synchronized List<DggConnectionContainer> builderContainerList(DggConnectionInfoBean dggConnectionInfoBean) throws DggMqExeption{
        List<DggConnectionContainer> connectionContainerList = connectionMap.get(dggConnectionInfoBean.getConnectionName());
        int forSize = POOL_SIZE;
        if (connectionContainerList != null) {
            int size = connectionContainerList.size();
            forSize = POOL_SIZE - size;
        } else {
            connectionContainerList = new ArrayList<>();
        }

        for (int i = 0; i < forSize; i++) {
            DggConnectionContainer connectionContainer = new DggConnectionContainer();
            connectionContainer.setConnectionInfoBean(dggConnectionInfoBean);
            if (connectionContainer.getConnection() == null) {
                Connection connection = createConnection(connectionContainer);
                connectionContainerList.add(connectionContainer);
            }
        }
        return connectionContainerList;
    }

    /**
     * 获取消息连接
     *
     * @return
     */
    public DggConnectionContainer getConnectionContainerBean(DggConnectionInfoBean dggConnectionInfoBean) throws DggMqExeption{
        DggConnectionContainer connectionContainer = new DggConnectionContainer();
        connectionContainer.setConnectionInfoBean(dggConnectionInfoBean);

        if (connectionContainer.getConnection() == null || !connectionContainer.getConnection().isOpen()) {
            createConnection(connectionContainer);
        }
        return connectionContainer;
    }

    /**
     * 重新创建消息连接
     *
     * @return
     */
    private Connection createConnection(DggConnectionContainer connectionContainer) throws DggMqExeption{
        Connection connection = null;
        //循环3次创建连接
        int j = 0;
        Exception connectException = null;
        for (j = 0; j < 3; j++) {
            try {
                connection = createConnection(connectionContainer.getConnectionInfoBean());
                if (connection != null) {
                    connectionContainer.setConnection(connection);
                }
                break;
            } catch (Exception e) {
                connectException = e;
            }
        }
        if(3 == j){
            throw new DggMqExeption("消息队列创建连接失败", connectException);
        }
        return connection;
    }

    /**
     * 创建消息连接
     *
     * @return
     */
    private Connection createConnection(DggConnectionInfoBean dggConnectionInfoBean) throws IOException, TimeoutException {
        Connection connection = null;
        //创建一个连接
        ConnectionFactory factory = new ConnectionFactory();
        //连接本地，如果需要指定到服务，需在这里指定IP
        factory.setHost(dggConnectionInfoBean.getHost());
        factory.setUsername(dggConnectionInfoBean.getUsername());
        factory.setPassword(dggConnectionInfoBean.getPassword());
        factory.setPort(dggConnectionInfoBean.getPort());
        factory.setVirtualHost(dggConnectionInfoBean.getVirtualHost());
        connection = factory.newConnection();
        return connection;
    }

    /**
     * 关闭连接
     *
     * @param connection
     */
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                if (connection.isOpen()) {
                    connection.close();
                }
            }
        } catch (Exception e) {
            logger.error("dgg mq colse connection exception,this reason:" + e);
        }
    }

    /**
     * 关闭通道
     *
     * @param channel
     */
    public static void closeChannel(Channel channel) {
        try {
            if (channel != null) {
                if (channel.isOpen()) {
                    channel.close();
                }
            }
        } catch (Exception e) {
            logger.error("dgg mq colse channel exception,this reason:" + e);
        }
    }


    /**
     * 监控连接状态
     */
    private void monitorConnection() {
        ScheduledExecutorService service = Executors
.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (connectionMap != null && connectionMap.size() > 0) {
                    try {
                        for (ConcurrentHashMap.Entry<String, List<DggConnectionContainer>> entry : connectionMap.entrySet()) {
                            //判断是否连接
                            monitorConnected(entry.getKey(), entry.getValue());
                        }
                    }catch (DggMqExeption e){
                        logger.info("连接出现异常...");
                    }
                }else{
                    logger.info("连接状态监控中...");
                }

            }
        }, 0, 60, TimeUnit.SECONDS);
    }

    /**
     * 判断是否连接
     *
     * @param connectionName
     * @param connectionContainerList
     */
    private void monitorConnected(String connectionName, List<DggConnectionContainer> connectionContainerList) throws DggMqExeption{
        if (connectionContainerList != null) {
            for (DggConnectionContainer connectionContainer:connectionContainerList) {
                if (connectionContainer.getConnection() == null || !connectionContainer.getConnection().isOpen()) {
                    logger.info(connectionName + "连接已经断开,稍后重新连接");
                    createConnection(connectionContainer);
                }
            }
        }
    }

}
