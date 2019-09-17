package net.dgg.bigdata.manager.index.service;

import net.dgg.bigdata.manager.session.CommonSession;
import net.dgg.bigdata.manager.session.SessionManager;
import net.dgg.core.spring.DggSpringContext;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户是否已经过验证过滤器
 *
 * @author nature
 * @create 2018-03-06 10:17
 */
@Deprecated
public class UserAuthorizedFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (this.beforeAction(servletRequest, servletResponse, filterChain)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 请求前执行的方法
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @return 判断是否通过了用户权限校验
     * @throws IOException
     */
    private boolean beforeAction(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if (!"/login/index.html".equals(httpServletRequest.getRequestURI())
                && !httpServletRequest.getRequestURI().endsWith(".do")) {

            SessionManager sessionManager = DggSpringContext.getBean(SessionManager.class);
            CommonSession session = sessionManager.getCurrentSession();
            String userId = session.getValue("userId", String.class);
            if (StringUtils.isEmpty(userId)) {
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpServletResponse.sendRedirect("/login/index.html");
                return false;
            }
        }

        return true;
    }
}
