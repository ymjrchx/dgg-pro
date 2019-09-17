package net.dgg.core.utils.exception;

/**
 * 基础异常类，不可被实例化
 *
 * @author nature
 * @create 2017-12-20 8:20
 */
public class DggBaseException extends RuntimeException {
    public DggBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DggBaseException(Throwable cause) {
        super(cause);
    }

    public DggBaseException(String message) {
        super(message);
    }

    public DggBaseException() {
        super("dgg framework base异常");
    }

}