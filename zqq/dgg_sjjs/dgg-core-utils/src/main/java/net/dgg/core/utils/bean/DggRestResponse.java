package net.dgg.core.utils.bean;

import net.dgg.core.utils.rest.DggAbstractResponse;

/**
 * @since 1.0
 * @version 1.1
 */
public class DggRestResponse<T> extends DggAbstractResponse<T> {

	public final static int SUCCESS_CODE = 0;

	/**
	 * 返回中的数据
	 */
	private T data;

	public DggRestResponse() {
	}

	public DggRestResponse(String msg) {
		this.setMsg(msg);
	}

	public DggRestResponse(int code) {
		this.setCode(code);
	}

	public DggRestResponse(T data) {
		this.data = data;
	}

	public DggRestResponse(T data, int code) {
		this.data = data;
		this.setCode(code);
	}

	public DggRestResponse(T data, int code, String msg) {
		super(code, msg);
		this.data = data;
	}

	public DggRestResponse(T data, String msg) {
		this.data = data;
		this.setMsg(msg);
	}

	public DggRestResponse(int code, String msg) {
		super(code, msg);
	}

	/**
	 * @return {@link #data}
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data {@link #data}
	 */
	public DggRestResponse<T> setData(T data) {
		this.data = data;
		return this;
	}

}
