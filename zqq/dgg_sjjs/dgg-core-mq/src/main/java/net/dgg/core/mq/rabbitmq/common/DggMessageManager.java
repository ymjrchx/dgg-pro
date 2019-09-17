package net.dgg.core.mq.rabbitmq.common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import net.dgg.core.mq.common.bean.DggConnectionContainer;
import net.dgg.core.mq.common.bean.DggConnectionInfoBean;
import net.dgg.core.mq.common.bean.DggMqWhiteBean;
import net.dgg.core.mq.common.bean.DggMqWhiteConfig;
import net.dgg.core.mq.common.constants.DggMqConstants;
import net.dgg.core.mq.common.exception.DggMqExeption;
import net.dgg.core.mq.rabbitmq.common.consumer.DggAbstractConsumer;
import net.dgg.core.spring.DggSpringContext;
import net.dgg.core.utils.DggStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 消息管理器
 *
 * @author 徐哲
 * @create 2017-11-23 19:50
 */
@Component
public class DggMessageManager implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(DggMessageManager.class);

    /**
     * 获取消息管理对象
     *
     * @return
     */
    public static DggMessageManager getMessageManager() {
        return DggSpringContext.getBean(DggMessageManager.class);
    }

    /**
     * 队列用户名
     */
    @Value("${mq.messagequeue.username:}")
    private String username;
    /**
     * 队列密码
     */
    @Value("${mq.messagequeue.password:}")
    private String password;
    /**
     * 队列端口
     */
    @Value("${mq.messagequeue.port:}")
    private Integer port;
    /**
     * 队列宿主机
     */
    @Value("${mq.messagequeue.host:}")
    private String host;
    /**
     * 虚拟主机
     */
    @Value("${mq.messagequeue.virtualHost:}")
    private String virtualHost;
    /**
     * 队列默认最大长度
     */
    private Integer maxLength = 100000;
    /**
     * 队列超时时间(单位:毫秒)
     */
    private Integer timeOut = 3600000;
    /**
     * 超时时间开关 1:关闭 2:打开
     */
    private Integer timeOutSwitch = 1;
    /**
     * 去重类型 1:reis 2:mongo 3:不去重
     */
    private Integer mqRepeatedType = 1;

    /**
     * 是否已启动，该管理器的init方法被重复调用会引发问题
     */
    private boolean started = false;

    /**
     * 获取默认连接信息
     *
     * @return
     */
    public DggConnectionInfoBean getConnectionInfo() {
        DggConnectionInfoBean dggConnectionInfoBean = new DggConnectionInfoBean();
        dggConnectionInfoBean.setConnectionName(DggMqConstants.DEFAULT_CONNECTION_NAME);
        dggConnectionInfoBean.setHost(host);
        dggConnectionInfoBean.setPassword(password);
        dggConnectionInfoBean.setUsername(username);
        if (DggStringUtils.isEmpty(virtualHost)) {
            virtualHost = DggMqConstants.VIRTUAL_HOST;
        }
        dggConnectionInfoBean.setVirtualHost(virtualHost);
        dggConnectionInfoBean.setPort(port);
        dggConnectionInfoBean.setMqRepeatedType(mqRepeatedType);
        return dggConnectionInfoBean;
    }

    /**
     * 获取死信交换器
     *
     * @param vHost 虚拟主机
     * @return
     */
    public static String getExchangeName(String vHost) {
        String deadExchangeName = vHost + DggMqConstants.DEAD_LETTER_EXCHANGE_SUFFIX;
        return deadExchangeName;
    }

    /**
     * 死信队列名称
     *
     * @param vHost 虚拟主机
     * @return
     */
    public static String getQueueName(String vHost) {
        String deadQueueName = vHost + DggMqConstants.DEAD_LETTER_QUEUE_SUFFIX;
        return deadQueueName;
    }

    /**
     * 初始化设置死信对象信息
     *
     * @param argsMap        参数Map
     * @param vHost          虚拟主机
     * @param maxQueueLength 队列最大长度
     * @param queueTimeOut   超时时间(单位:毫秒)
     */
    public void initDeadQueueMap(Map<String, Object> argsMap, String vHost, Integer maxQueueLength,
                                 Integer queueTimeOut) {
        if (argsMap != null) {
            argsMap.put("x-delayed-type", "direct");
            argsMap.put("x-dead-letter-exchange", getExchangeName(vHost));// 设置死信交换机
            // 队列长度
            if (maxQueueLength == null) {
                maxQueueLength = maxLength;
            }
            argsMap.put("x-max-length", maxQueueLength);// 队列最大长度
            // 超时开关
            if (timeOutSwitch == 2) {
                // 超时时间
                if (queueTimeOut == null) {
                    queueTimeOut = timeOut;
                }
                argsMap.put("x-message-ttl", queueTimeOut);
            }
        }
    }

    /**
     * 初始化设置死信对象信息
     *
     * @param argsMap
     * @param vHost
     */
    public void initDeadQueueMap(Map<String, Object> argsMap, String vHost) {
        initDeadQueueMap(argsMap, vHost, null, null);
    }

    /**
     * 通过绑定交换器和队列的方式监测死信队列是否正常
     *
     * @param connection
     * @param vhost
     * @return
     */
    public boolean exchangeQueueExist(Connection connection, String vhost) {
        boolean flag = false;
        Channel channel = null;
        try {
            // 判断是否建立连接
            if (connection != null) {
                String deadExchangeName = getExchangeName(vhost);
                String deadQueueName = getQueueName(vhost);
                // 创建一个通道
                channel = connection.createChannel();
                channel.queueBind(deadQueueName, deadExchangeName, "");
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 停止死信队列
     *
     * @param vHost
     * @param connection
     */
    public void stopDeadLetter(String vHost, Connection connection) throws DggMqExeption{
        Channel channel = null;
        try {
            // 判断是否建立连接
            if (connection != null) {
                String deadQueueName = getQueueName(vHost);
                // 创建一个通道
                channel = connection.createChannel();
                channel.queueDelete(deadQueueName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DggMqExeption("死信消费者队列停止异常", e);
        }
    }

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

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public Integer getTimeOutSwitch() {
        return timeOutSwitch;
    }

    public void setTimeOutSwitch(Integer timeOutSwitch) {
        this.timeOutSwitch = timeOutSwitch;
    }

    public Integer getMqRepeatedType() {
        return mqRepeatedType;
    }

    public void setMqRepeatedType(Integer mqRepeatedType) {
        this.mqRepeatedType = mqRepeatedType;
    }

    @Autowired
    private DggMessageConnection dggMessageConnection;

    public DggMessageConnection getDggMessageConnection() {
        return dggMessageConnection;
    }

    public void setDggMessageConnection(DggMessageConnection dggMessageConnection) {
        this.dggMessageConnection = dggMessageConnection;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //添加默认连接信息
        dggMessageConnection.addDefaultConnection(getConnectionInfo());
        // 从bean中获取消费者
        Map<String, DggAbstractConsumer> consumerMap = DggSpringContext.getBeansByClass(DggAbstractConsumer.class);
        //获取白名单配置
        DggMqWhiteConfig dggMqWhiteConfig = DggValidateMqWhiteUtils.getMqWhiteConfig();
        //白名单列表
        List<DggMqWhiteBean> dggMqWhiteBeanList = null;
        //启用白名单校验
        if (dggMqWhiteConfig.isEnableMqWhite()) {
            if (dggMqWhiteConfig.isEnableSysCode()) {
                if (!DggValidateMqWhiteUtils.validateSysCode(dggMqWhiteConfig)) {
                    throw new DggMqExeption("系统编码" + dggMqWhiteConfig.getSysCode() + "不合法！");
                }
            }
            if (dggMqWhiteConfig.isEnableIpPort()) {
                if (!DggValidateMqWhiteUtils.validateIpPort(dggMqWhiteConfig)) {
                    throw new DggMqExeption("系统编码" + dggMqWhiteConfig.getSysCode() + "没有加入白名单！");
                }
            }
            //获取白名单列表
            dggMqWhiteBeanList = DggValidateMqWhiteUtils.getMqWhiteList(dggMqWhiteConfig);
        }
        // 遍历消费者
        if (consumerMap != null && consumerMap.size() > 0) {
            DggConnectionContainer connectionContainer;
            for (DggAbstractConsumer consumer : consumerMap.values()) {
                boolean flag = false;
                if (dggMqWhiteConfig.isEnableMqWhite()) {
                    //验证白名单
                    if (DggValidateMqWhiteUtils.validateMqWhite(consumer, dggMqWhiteBeanList)) {
                        flag = true;
                    }
                } else {
                    flag = true;
                }
                //创建消费
                if (flag) {
                    connectionContainer = dggMessageConnection.getConnectionContainer(consumer.getConnectionName());
                    consumer.init(connectionContainer);
                    logger.info("--" + consumer.getConnectionName() + "---" + consumer + "-----");
                    //创建多个消费者
                    int defaultNum = DggMqConstants.DEFAULT_CONSUMER_NUM;
                    Integer consumerNum = consumer.getConsumerNum();
                    for (int i = defaultNum; i < consumerNum; i++){
                        consumer.clone().init(connectionContainer);
                    }
                }
            }
        }
    }
}