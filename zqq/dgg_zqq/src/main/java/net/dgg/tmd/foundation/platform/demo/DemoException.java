package net.dgg.tmd.foundation.platform.demo;

import net.fblock.core.exception.BaseException;

/**
 * Demo模块的异常
 *
 * @author nature
 * @create 2018-02-22 21:00
 */
public class DemoException extends BaseException {
	public DemoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DemoException( Throwable cause) {
		super(cause);
	}

	public DemoException(String message) {
		super(message);
	}

	private DemoException() {
		super("demo异常");
	}
}
