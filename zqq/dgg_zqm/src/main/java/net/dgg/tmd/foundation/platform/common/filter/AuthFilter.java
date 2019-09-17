package net.dgg.tmd.foundation.platform.common.filter;

import net.dgg.tmd.foundation.platform.common.util.YmlUtils;
import net.dgg.tmd.foundation.platform.role.service.RoleService;
import net.dgg.tmd.foundation.platform.session.SessionManager;
import net.fblock.core.FblockContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wu on 2018-03-13.
 */
public class AuthFilter implements Filter {

	protected static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

	private static List<String> noAuthUrls;

	static {
		logger.info("加载监控配置文件开始");
		InputStream inputStream = AuthFilter.class.getResourceAsStream("/auth.yml");
		noAuthUrls = YmlUtils.load(inputStream, List.class);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		// 获取会话管理器
		SessionManager sessionManager = FblockContext.getBean("sessionManager", SessionManager.class);
		// 获取角色服务
		RoleService roleService = FblockContext.getBean("roleService", RoleService.class);
		// 获取菜单urls
		MenuUrlsCache menuUrlsCache = FblockContext.getBean(MenuUrlsCache.class);
		// 获取当前请求uri
		String url = httpServletRequest.getRequestURI();

		// 从缓存中读取menuUrls
		Object menuObj = menuUrlsCache.get("menuUrls");
		Set<String> menuUrls = null;
		if (menuObj instanceof List) {
			menuUrls = new HashSet<>();
			List<String> menuList = (List<String>) menuObj;
			menuUrls.addAll(menuList);
		} else {
			menuUrls = (Set<String>) menuObj;
		}

		//// 如果当前请求uri是菜单的uri的话进行权限验证
		if (menuUrls.contains(url)) {
			// 获取当前用户ID
			Long userId = sessionManager.getCurrentSession().getValue("userId", Long.class);

			// 判断该uri是否是不进行权限判断的uri
			if (!noAuthUrls.contains(url)) {
				// 如果需要进行权限判断，判断该用户是否具备该权限
				if (!roleService.existsMenuPermissionUser(userId, url)) {
					// 如果没有权限，返回403
					httpServletRequest.getRequestDispatcher("/403").forward(request, response);
					return;
				}
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
