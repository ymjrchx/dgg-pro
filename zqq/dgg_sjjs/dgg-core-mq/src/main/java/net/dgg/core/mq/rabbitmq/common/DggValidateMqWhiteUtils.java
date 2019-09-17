package net.dgg.core.mq.rabbitmq.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.dgg.core.mq.common.bean.DggMqWhiteBean;
import net.dgg.core.mq.common.bean.DggMqWhiteConfig;
import net.dgg.core.mq.rabbitmq.common.consumer.DggAbstractConsumer;
import net.dgg.core.spring.DggSpringContext;
import net.dgg.core.utils.httpclient.DggHttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * MQ白名单校验工具类
 */
public class DggValidateMqWhiteUtils {

    /**
     * 工具类
     */
    private static final Logger logger = LoggerFactory.getLogger(DggValidateMqWhiteUtils.class);

    /**
     * 服务器系统编码
     *
     * @param dggMqWhiteConfig
     * @return
     */
    public static boolean validateSysCode(DggMqWhiteConfig dggMqWhiteConfig) {
        boolean flag = false;
        if (dggMqWhiteConfig == null || !dggMqWhiteConfig.isEnableSysCode()) {
            return false;
        }
        try {
            String url = dggMqWhiteConfig.getMqWhiteUrl();
            int port = dggMqWhiteConfig.getPort();
            String sysCode = dggMqWhiteConfig.getSysCode();
            String ip = InetAddress.getLocalHost().getHostAddress();
            if (port != 0 && sysCode != null && ip != null) {
                url = url + "/validate_syscode?sysCode=" + sysCode;
                //获得返回的结果
                String repsonseStr = DggHttpUtil.doHttpGet(url);
                if (repsonseStr != null) {
                    JSONObject jsonObject = JSON.parseObject(repsonseStr);
                    if (isSuccess(jsonObject)) {
                        flag = true;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取MQ白名单异常,异常原因：" + e);
        }
        return flag;
    }

    /**
     * 服务器校验
     *
     * @param dggMqWhiteConfig
     * @return
     */
    public static boolean validateIpPort(DggMqWhiteConfig dggMqWhiteConfig) {
        boolean flag = false;
        if (dggMqWhiteConfig == null || !dggMqWhiteConfig.isEnableIpPort()) {
            return false;
        }
        try {
            String url = dggMqWhiteConfig.getMqWhiteUrl();
            int port = dggMqWhiteConfig.getPort();
            String sysCode = dggMqWhiteConfig.getSysCode();
            String ip = InetAddress.getLocalHost().getHostAddress();
            if (port != 0 && sysCode != null && ip != null) {
                url = url + "/validate_ip_port?sysCode=" + sysCode + "&ip=" + ip + "&port=" + port;
                //获得返回的结果
                String repsonseStr = DggHttpUtil.doHttpGet(url);
                if (repsonseStr != null) {
                    JSONObject jsonObject = JSON.parseObject(repsonseStr);
                    if (isSuccess(jsonObject)) {
                        flag = true;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取MQ白名单异常,异常原因：" + e);
        }
        return flag;
    }

    /**
     * 获取白名单列表
     *
     * @return
     */
    public static List<DggMqWhiteBean> getMqWhiteList(DggMqWhiteConfig dggMqWhiteConfig) {
        List<DggMqWhiteBean> dggMqWhiteBeanList = null;
        if (dggMqWhiteConfig != null) {
            //白名单开关
            if (dggMqWhiteConfig.isEnableMqWhite()) {
                try {
                    String url = dggMqWhiteConfig.getMqWhiteUrl();
                    int port = dggMqWhiteConfig.getPort();
                    String sysCode = dggMqWhiteConfig.getSysCode();
                    String ip = InetAddress.getLocalHost().getHostAddress();
                    if (port != 0 && sysCode != null && ip != null) {
                        url = url + "/get_consumer_list?sysCode=" + sysCode;
                        //获得返回的结果
                        String repsonseStr = DggHttpUtil.doHttpGet(url);
                        if (repsonseStr != null) {
                            JSONObject jsonObject = JSON.parseObject(repsonseStr);
                            if (isSuccess(jsonObject)) {
                                String dataJsonStr = jsonObject.getString("data");
                                dggMqWhiteBeanList = JSON.parseArray(dataJsonStr, DggMqWhiteBean.class);
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("获取MQ白名单异常,异常原因：" + e);
                }
            }
        }
        if (dggMqWhiteBeanList == null) {
            dggMqWhiteBeanList = new ArrayList<>();
        }
        return dggMqWhiteBeanList;
    }

    /**
     * 判断是否成功
     *
     * @param jsonObject
     * @return
     */
    private static boolean isSuccess(JSONObject jsonObject) {
        boolean flag = false;
        if (jsonObject != null) {
            Integer code = (Integer) jsonObject.get("code");
            if (code == 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 验证白名单
     *
     * @return
     */
    public static boolean validateMqWhite(DggAbstractConsumer abstractConsumer, List<DggMqWhiteBean> dggMqWhiteBeanList) {
        boolean flag = true;
        //白名单开关
        DggMqWhiteConfig dggMqWhiteConfig = getMqWhiteConfig();
        if (dggMqWhiteConfig.isEnableMqWhite()) {
            flag = false;
            if (abstractConsumer != null && dggMqWhiteBeanList != null) {
                String queueName = abstractConsumer.getQueueName();
                String exchangeName = abstractConsumer.getExchangeName();
                for (DggMqWhiteBean dggMqWhiteBean : dggMqWhiteBeanList) {
                    if (queueName == null || exchangeName == null) {
                        continue;
                    }
                    if (queueName.equals(dggMqWhiteBean.getQueueName()) && exchangeName.equals(dggMqWhiteBean.getExchangeName())) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 获取白名单配置
     *
     * @return
     */
    public static DggMqWhiteConfig getMqWhiteConfig() {
        DggMqWhiteConfig dggMqWhiteConfig = null;
        try {
            dggMqWhiteConfig = DggSpringContext.getBean(DggMqWhiteConfig.class);
        } catch (Exception e) {
            logger.info("没有白名单配置项或没有启用白名单配置！");
        }
        if (dggMqWhiteConfig == null) {
            dggMqWhiteConfig = new DggMqWhiteConfig();
        }
        return dggMqWhiteConfig;
    }

}
