package net.dgg.tmd.foundation.platform.user.exception;

import net.fblock.core.exception.BaseException;

public class UserModuleException extends BaseException {
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
