package net.dgg.bigdata.manager.index;

import net.dgg.core.utils.exception.DggBaseException;

/**
 * @author nature
 * @create 2018-02-27 9:53
 */
public class IndexException extends DggBaseException {
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
