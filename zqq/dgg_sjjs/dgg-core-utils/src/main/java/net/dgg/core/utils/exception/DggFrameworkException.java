package net.dgg.core.utils.exception;

/**
 * 框架模块的异常类
 *
 * @author nature
 * @create 2017-12-20 8:52
 */
public class DggFrameworkException extends DggBaseException {
    public DggFrameworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public DggFrameworkException(Throwable cause) {
        super(cause);
    }

    public DggFrameworkException(String message) {
        super(message);
    }

    public DggFrameworkException() {
        super();
    }
}
