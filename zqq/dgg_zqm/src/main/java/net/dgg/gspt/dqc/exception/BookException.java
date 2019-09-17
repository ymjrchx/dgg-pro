package net.dgg.gspt.dqc.exception;

import net.fblock.core.exception.BaseException;

/**
 * 数据字典异常类
 * @author zyj
 *
 */
public class BookException extends BaseException {
	private static final long serialVersionUID = 6261710677375699532L;

	public BookException(String message, Throwable cause) {
		super(message, cause);
	}

	public BookException(Throwable cause) {
		super(cause);
	}

	public BookException(String message) {
		super(message);
	}

	public BookException() {
		super();
	}
}
