package net.dgg.bigdata.manager.common.exception;

/**
 * Created by wu on 2018-03-13.
 */
public class AuthOptException extends Exception {

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
