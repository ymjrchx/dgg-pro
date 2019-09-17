package net.dgg.core.mq.common.constants;

/**
 * Desc:   消息队列常量类
 * Author: Li Xingjiang
 * Date:   2018/9/12 9:58
 * Version: 1.0
 **/
public class DggMqConstants {

    /**
     * 默认消费者数量
     */
    public static final int DEFAULT_CONSUMER_NUM = 1;
    /**
     * 默认连接名
     */
    public static final String DEFAULT_CONNECTION_NAME = "default_connection";
    /**
     * 默认虚拟器
     */
    public static final String VIRTUAL_HOST = "/";
    /**
     * 设置消费者从消息队列获取最大消息数据
     */
    public static final int QOS_NUM = 400;
    /**
     * 去重失效时间,单位:秒
     */
    public static final int REPEATED_TIME_OUT = 3600;
    /**
     * 死信队列名称后缀
     */
    public static final String DEAD_LETTER_QUEUE_SUFFIX = ".fblock.dead.letter.queue";
    /**
     * 死信交换器后缀
     */
    public static final String DEAD_LETTER_EXCHANGE_SUFFIX = ".fblock.dead.letter.exchange";

}
