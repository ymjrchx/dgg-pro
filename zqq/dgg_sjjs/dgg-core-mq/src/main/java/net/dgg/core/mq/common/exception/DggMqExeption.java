package net.dgg.core.mq.common.exception;

import net.dgg.core.utils.exception.DggFrameworkException;

/**
 * @Desc 消息队列异常
 * @Date 19:20 2018/9/11
 * @Author Li Xingjiang
 **/
public class DggMqExeption extends Exception {
    public DggMqExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public DggMqExeption(Throwable cause) {
        super(cause);
    }

    public DggMqExeption(String message) {
        super(message);
    }

    public DggMqExeption() {
        super();
    }
}