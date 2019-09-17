package net.dgg.core.utils.exception;

/**
 * 工具类异常
 */
public abstract class DggUtilException extends RuntimeException {
    public DggUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public DggUtilException(Throwable cause) {
        super(cause);
    }

    public DggUtilException(String message) {
        super(message);
    }

    public DggUtilException() {
        super("dgg framework工具类异常");
    }

}