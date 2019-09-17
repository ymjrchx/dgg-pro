package net.dgg.zqq.controller.common;

import net.dgg.zqq.framework.wrapper.DelegateServletInputStream;
import net.dgg.zqq.utils.ThreadHelper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by 李程 on 2018/11/8.
 */
@Order(0)
@Component
@WebFilter(urlPatterns = {"/**"})
public class RequestDispatcherFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ThreadHelper.putThreadContextVar("requestURI", ((HttpServletRequest) servletRequest).getRequestURI());
        String contentType = servletRequest.getContentType();
        ThreadHelper.putThreadContextVar("request", servletRequest);
        if (StringUtils.isNotEmpty(contentType) && contentType.toUpperCase().contains("MULTIPART")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (((HttpServletRequest) servletRequest).getMethod().equalsIgnoreCase("POST")) {
            byte[] requestBody = IOUtils.toByteArray(servletRequest.getInputStream());
            ThreadHelper.putThreadContextVar("requestBody", requestBody);
            HttpServletRequest httpServletRequest = (HttpServletRequest) Proxy.newProxyInstance(RequestDispatcherFilter.class.getClassLoader(), new Class[]{HttpServletRequest.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (method.getName().equalsIgnoreCase("getInputStream") && method.getParameterCount() == 0) {
                        return new DelegateServletInputStream(new ByteArrayInputStream(requestBody), requestBody.length);
                    } else {
                        return method.invoke(servletRequest, args);
                    }
                }
            });
            filterChain.doFilter(httpServletRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        ThreadHelper.removeThreadContextVar("request");
    }

    @Override
    public void destroy() {

    }
}
