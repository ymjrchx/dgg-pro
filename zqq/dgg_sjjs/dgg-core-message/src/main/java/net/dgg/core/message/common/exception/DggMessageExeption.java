package net.dgg.core.message.common.exception;

/**
 * 消息SDK异常处理类
 *@author tumq
 */
public class DggMessageExeption extends Exception {
    public DggMessageExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public DggMessageExeption(Throwable cause) {
        super(cause);
    }

    public DggMessageExeption(String message) {
        super(message);
    }

    public DggMessageExeption() {
        super();
    }
}