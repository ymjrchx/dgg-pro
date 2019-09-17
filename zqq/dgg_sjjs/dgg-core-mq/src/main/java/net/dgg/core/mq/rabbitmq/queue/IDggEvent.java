package net.dgg.core.mq.rabbitmq.queue;

/**
 * 事件接口，实现该接口即可将命令发送到对应的广播频道上
 * 事件名称将会指定广播频道
 *
 * @author nature
 * @create 2017-12-22 10:40
 */
public interface IDggEvent {

//    String getEventName2();
    /**
     * 事件名称，会被当做对应的交换器名称，所以不同的事件要求相互之间是不重复的
     *
     * @return
     */
    String getEventName();
}
