package net.dgg.tmd.foundation.platform.permission.exception;

import net.fblock.core.exception.BaseException;

/**
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/2/24
 * Time:11:08
 */
public class OperatePermissionException extends BaseException {
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
