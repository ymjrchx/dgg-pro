package net.dgg.bigdata.manager.session;

import net.dgg.core.spring.DggSpringContext;
import net.dgg.core.utils.DggJsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象会话类，用来定义会话基本结构及属性存取基本方法，所有会话实体均需要继承该类
 *
 * 该内容被设计成，从缓存中读取出来，操作之后就抛弃的对象，下次将会重新读取，以保证数据与缓存中的及时性
 *
 * @author nature
 * @create 2018-02-22 21:35
 */
public class CommonSession {

	private String sessionId;

	private Map values=new HashMap();

	/**
	 * 向会话中放入值
	 * @param key
	 * @param value
	 * @param <T>
	 */
	public <T> void putValue(String key, T value){

		values.put(key,value);
		//把数据保存到会话中
		SessionManager sessionManager= DggSpringContext.getBean(SessionManager.class);
		sessionManager.saveSession(this);
	}

	/**
	 * 读取会话中的值
	 * @param key
	 * @param valueClass
	 * @param <T>
	 * @return
	 */
	public <T> T getValue(String key,Class<T> valueClass){
		return DggJsonUtils.json2Obj(DggJsonUtils.obj2Json(values.get(key)),valueClass);
	}

	/**
	 * 供序列化使用，不推荐手动调用
	 * @return
	 */
	public Map getValues() {
		return values;
	}

	/**
	 * 供序列化使用，不推荐手动调用
	 * @return
	 */
	public void setValues(Map values) {
		this.values = values;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
