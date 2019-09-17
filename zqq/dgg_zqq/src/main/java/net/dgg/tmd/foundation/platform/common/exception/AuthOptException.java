package net.dgg.tmd.foundation.platform.common.exception;

import net.fblock.core.exception.BaseException;

/**
 * Created by wu on 2018-03-13.
 */
public class AuthOptException extends BaseException {

    public AuthOptException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthOptException(Throwable cause) {
        super(cause);
    }

    public AuthOptException(String message) {
        super(message);
    }

    public AuthOptException() {
        super();
    }
}
