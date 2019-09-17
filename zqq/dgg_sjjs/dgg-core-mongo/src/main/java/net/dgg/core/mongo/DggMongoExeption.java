package net.dgg.core.mongo;

import net.dgg.core.utils.exception.DggFrameworkException;

/**
 * 消息队列异常
 *
 * @author nature
 * @create 2018-01-05 10:57
 */
public class DggMongoExeption extends RuntimeException {
    public DggMongoExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public DggMongoExeption(Throwable cause) {
        super(cause);
    }

    public DggMongoExeption(String message) {
        super(message);
    }

    public DggMongoExeption() {
        super();
    }
}