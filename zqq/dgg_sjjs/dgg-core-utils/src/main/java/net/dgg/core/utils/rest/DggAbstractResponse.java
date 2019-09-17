package net.dgg.core.utils.rest;

/**
 * @since 1.1 增加
 */
public abstract class DggAbstractResponse<T> {

	/**
	 * 成功
	 */
	public static final int SUCCESS = 0;

	/**
	 * 失败
	 */
	public static final int FAIL = 1;

	/**
	 * 返回值0：成功，1：失败
	 */
	private int code;

	/**
	 * 返回消息
	 */
	private String msg;

	public DggAbstractResponse() {
	}

	public DggAbstractResponse(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public DggAbstractResponse(String msg, int code) {
		this(code, msg);
	}

	/**
	 * @return {@link #code}
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code {@link #code}
	 */
	public DggAbstractResponse<T> setCode(int code) {
		this.code = code;
		return this;
	}

	/**
	 * @return {@link #msg}
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg {@link #msg}
	 */
	public DggAbstractResponse<T> setMsg(String msg) {
		this.msg = msg;
		return this;
	}

}
