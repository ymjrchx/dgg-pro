/******************************************************************************
 * Copyright (C) 2014 ShenZhen YiHua Computer Co., Ltd.
 * All Rights Reserved.
 * 本软件为深圳怡化电脑股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package net.dgg.dqc.backservice.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author 汤宏
 * @ClassName ApplicationConfigLoader
 * @Description 配置装载类
 * @date 2018年1月3日 下午4:26:31
 */
public class ApplicationConfigLoader {
    /**
     * 写日志文件
     */
    public final static Logger logger = Logger.getLogger(ApplicationConfigLoader.class);

    //配置文件获取属性
    private static ApplicationConfigLoader configLoader;

    // 配置文件a
    private static Properties property = null;

    /**
     *
     * 初始化配置文件
     *
     */
    static {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
        property = new Properties();
        try {
            property.load(inputStream);
        } catch (IOException e) {
            logger.error("【异常提示信息】" + e.getMessage());
        }
    }

    public static ApplicationConfigLoader getConfigLoader() {
        if (configLoader == null) {
            configLoader = new ApplicationConfigLoader();
        }
        return configLoader;
    }

    /**
     * 获取配置文件值
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        return (String) property.get(key);
    }

    public static void main(String[] args) {
        String value = ApplicationConfigLoader.getConfigLoader().getValue("redis.servers");
        System.out.println(value);
    }

}
