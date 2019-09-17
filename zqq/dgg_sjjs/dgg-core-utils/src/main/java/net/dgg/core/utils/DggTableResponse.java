package net.dgg.core.utils;

import java.util.List;

import net.dgg.core.utils.rest.DggAbstractResponse;

/**
 * @since 1.0
 * @version 1.1
 */
public class DggTableResponse<T> extends DggAbstractResponse<T> {

	/**
	 * 返回的数据
	 */
	private List<T> data;

	/**
	 * 总数
	 */
	private Long count;

	public DggTableResponse<T> data(Long count, List<T> data) {
		this.count = count;
		this.data = data;
		return this;
	}

	public DggTableResponse<T> data(List<T> data, Long count) {
		this.count = count;
		this.data = data;
		return this;
	}

	public DggTableResponse() {
	}

	public DggTableResponse(List<T> data) {
		this(data, 0L);
	}

	public DggTableResponse(List<T> data, Long count) {
		this.data = data;
		this.count = count;
	}

	public DggTableResponse(List<T> data, int code, Long count) {
		this.data = data;
		this.count = count;
		this.setCode(code);
	}

	public DggTableResponse(List<T> data, Long count, int code, String msg) {
		this(code, data, count, msg);
	}

	public DggTableResponse(List<T> data, int code, Long count, String msg) {
		this(code, data, count, msg);
	}

	public DggTableResponse(int code, List<T> data, Long count, String msg) {
		super(code, msg);
		this.data = data;
		this.count = count;
	}

	/**
	 * @return {@link #data}
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * @param data {@link #data}
	 */
	public DggTableResponse<T> setData(List<T> data) {
		this.data = data;
		return this;
	}

	/**
	 * @return {@link #count}
	 */
	public Long getCount() {
		return count;
	}

	/**
	 * @param count {@link #count}
	 */
	public DggTableResponse<T> setCount(Long count) {
		this.count = count;
		return this;
	}

}
