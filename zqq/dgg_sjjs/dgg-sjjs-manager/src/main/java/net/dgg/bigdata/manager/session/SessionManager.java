package net.dgg.bigdata.manager.session;

import net.dgg.core.mongo.cache.AbstractMongoCacheModule;
import net.dgg.core.utils.common.DggKeyWorker;
import org.springframework.stereotype.Component;

/**
 * 会话管理器
 *
 * @author nature
 * @create 2018-02-22 21:41
 */
@Component
public class SessionManager extends AbstractMongoCacheModule<CommonSession> {

	/**
	 * 会话闲置过期时间间隔，单位毫秒
	 */
	private long sessionExpireInterval = 20 * 60 * 1000;

	private ThreadLocal<Long> currentSessionId = new ThreadLocal<>();

	public SessionManager() {
		this.setDataSecondDefault(sessionExpireInterval);
		this.setCacheCollectionKey("net.dgg.bigdata.manager.session.SessionCacheModule");
	}

	/**
	 * 创建新的会话，并返回会话ID
	 * 
	 * @return
	 */
	public long newSession() {

		// 创建session对象
		long sessionId = DggKeyWorker.nextId();
		CommonSession session = new CommonSession();
		session.setSessionId(String.valueOf(sessionId));

		// 将session内容存进缓存
		this.saveToCache(String.valueOf(sessionId), session);

		// 返回sessionId
		return sessionId;
	}

	/**
	 * 关闭会话
	 * 
	 * @param sessionId
	 */
	public void closeSession(long sessionId) {
		this.clear(String.valueOf(sessionId));
	}

	/**
	 * 获取会话对象，如果未找到，则返回null
	 * 
	 * @param sessionId
	 * @return
	 */
	public CommonSession getSession(long sessionId) {
		return this.get(String.valueOf(sessionId));
	}

	/**
	 * 刷新会话
	 * 
	 * @param sessionId
	 */
	public void flushSession(long sessionId) {
		// 读取动作会刷新会话时间
		this.get(String.valueOf(sessionId));
	}

	/**
	 * 会话是否存在
	 * 
	 * @param sessionId
	 * @return
	 */
	public boolean sessionExist(long sessionId) {
		if (this.get(String.valueOf(sessionId)) == null) {
			return false;
		}
		return true;
	}

	/**
	 * 当没有查到合法的session时，我们不会再次创建该session
	 * 
	 * @param sessionId
	 * @return
	 */
	@Override
	protected CommonSession loadValue(String sessionId) {
		return null;
	}

	/**
	 * 获取当前sessionId
	 * 
	 * @return
	 */
	public Long getCurrentSessionId() {
		return currentSessionId.get();
	}

	/**
	 * 获取当前session
	 * 
	 * @return
	 */
	public CommonSession getCurrentSession() {
		Long sessionId = currentSessionId.get();
		if (sessionId == null) {
			return null;
		}
		return this.getSession(sessionId);
	}

	/**
	 * 设置当前sessionId，由于是保护方法，可在当前包内使用
	 * 
	 * @param sessionId
	 */
	protected void setCurrentSessionId(long sessionId) {
		currentSessionId.set(sessionId);
	}

	protected void saveSession(CommonSession commonSession) {
		this.saveToCache(commonSession.getSessionId(), commonSession);
	}
}
