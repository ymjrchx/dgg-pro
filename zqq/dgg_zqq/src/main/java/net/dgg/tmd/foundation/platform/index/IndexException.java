package net.dgg.tmd.foundation.platform.index;

import net.fblock.core.exception.BaseException;

/**
 * @author nature
 * @create 2018-02-27 9:53
 */
public class IndexException extends BaseException {
	public IndexException(String message, Throwable cause) {
		super(message,  cause);
	}

	public IndexException(Throwable cause) {
		super( cause);
	}

	public IndexException(String message) {
		super(message);
	}

	public IndexException() {
		super();
	}
}
