package net.dgg.core.utils.common;

import net.dgg.core.utils.exception.DggBaseException;

/**
 * 服务异常
 *
 * @author nature
 * @create 2017-12-20 20:03
 */
public class DggWebException extends DggBaseException {
    public DggWebException(String message, Throwable cause) {
        super(message, cause);
    }

    public DggWebException(Throwable cause) {
        super(cause);
    }

    public DggWebException(String message) {
        super(message);
    }

    public DggWebException() {
        super();
    }
}