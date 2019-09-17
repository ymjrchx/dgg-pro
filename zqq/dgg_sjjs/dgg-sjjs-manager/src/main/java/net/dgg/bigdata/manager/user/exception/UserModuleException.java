package net.dgg.bigdata.manager.user.exception;

import net.dgg.core.utils.exception.DggBaseException;

public class UserModuleException extends DggBaseException {
    public UserModuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserModuleException(Throwable cause) {
        super(cause);
    }

    public UserModuleException(String message) {
        super(message);
    }

    public UserModuleException() {
        super();
    }
}
