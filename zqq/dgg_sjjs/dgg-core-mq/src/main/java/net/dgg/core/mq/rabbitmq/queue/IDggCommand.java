package net.dgg.core.mq.rabbitmq.queue;

/**
 * 命令接口，实现该接口即可发送命令
 * 所有的命令均会发送命令ID指定的消息队列上
 *
 * @author nature
 * @create 2017-12-22 10:39
 */
public interface IDggCommand {

    /**
     * 命令ID
     *
     * @return
     */
    String getCommandId();
}
