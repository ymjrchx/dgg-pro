package net.dgg.bigdata.manager.permission.exception;

import net.dgg.core.utils.exception.DggBaseException;

/**
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/2/24
 * Time:11:08
 */
public class OperatePermissionException extends DggBaseException {
    public OperatePermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperatePermissionException(Throwable cause) {
        super(cause);
    }

    public OperatePermissionException(String message) {
        super(message);
    }

    public OperatePermissionException() {
        super();
    }
}
