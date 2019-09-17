package net.dgg.core.mongo.cache;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * 缓存数据承载类
 *
 * @author nature
 * @create 2018-04-19 16:32
 */
class MongoCacheDataContainer<T> {

	//缓存的key值
	@Id
	private String key;
	//缓存的最后更新时间，缓存过期时间为该值加上对应的间隔
	private Date updateTime;
	private T data;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
