package net.dgg.bigdata.manager.session;

import net.dgg.core.spring.DggSpringContext;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 会话过滤器
 *
 * @author nature
 * @create 2018-03-06 10:01
 */
public class SessionFilter implements Filter {


	private static final String SESSION_COOKIE_NAME = "FblockSession";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		if(this.beforeAction(servletRequest,servletResponse,filterChain)){
			filterChain.doFilter(servletRequest, servletResponse);
		}
		this.afterAction(servletRequest,servletResponse,filterChain);
	}

	@Override
	public void destroy() {

	}

	private void afterAction(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {

		//把request中的sessionId保存到response中去
		SessionManager sessionManager = DggSpringContext.getBean(SessionManager.class);
		long sessionId=sessionManager.getCurrentSessionId();
		//this.getSessionId(httpServletRequest);
		this.saveSessionIdToResponse((HttpServletResponse)servletResponse,sessionId);

	}

	/**
	 * 过滤器前操作
	 * @param servletRequest
	 * @param servletResponse
	 * @param filterChain
	 * @return
	 * @throws Exception
	 */
	private boolean beforeAction(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {

		SessionManager sessionManager = DggSpringContext.getBean(SessionManager.class);

		//获取sessionId
		Long sessionId = this.getSessionId((HttpServletRequest) servletRequest);
		if (sessionId == null ||
				sessionManager.getSession(sessionId) == null) {
			//如果无法获取到当前sessionId或者获取到的session为空的话
			sessionId = sessionManager.newSession();
			this.saveSessionIdToResponse((HttpServletResponse) servletResponse, sessionId);
		} else {
			sessionManager.flushSession(sessionId);
		}

		//设置当前sessionId
		sessionManager.setCurrentSessionId(sessionId);

		return true;
	}

	/**
	 * 从request中获取sessionid
	 * @param request
	 * @return
	 */
	private Long getSessionId(HttpServletRequest request) {
		//从cookie中读取sessionId
		Cookie[] cookies = request.getCookies();
		if(cookies==null){
			return null;
		}
		for (Cookie cookie : cookies) {
			if (SESSION_COOKIE_NAME.equals(cookie.getName())) {
				return Long.valueOf(cookie.getValue());
			}
		}
		return null;
	}

	/**
	 * 将sessionId存入response中
	 * @param response
	 * @param sessionId
	 */
	private void saveSessionIdToResponse(HttpServletResponse response, long sessionId) {
		Cookie cookie = new Cookie(SESSION_COOKIE_NAME, String.valueOf(sessionId));
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}
