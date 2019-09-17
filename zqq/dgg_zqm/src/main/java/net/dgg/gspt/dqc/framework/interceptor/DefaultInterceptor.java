package net.dgg.gspt.dqc.framework.interceptor;

import net.dgg.gspt.dqc.framework.PTConst;
import net.dgg.gspt.dqc.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认拦截器
 * Created by wu on 2017/8/29.
 */
public class DefaultInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(DefaultInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (org.apache.commons.lang.StringUtils.isEmpty(request.getHeader(PTConst.USER_TOKEN)))
            logger.info("token 为空");
        String reqUrl = request.getRequestURL().toString();//请求资源路径
        logger.info(reqUrl);
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        try {
            logger.info("进入拦截器..aaaa........");
            //服务器
            String hostIp = InetAddress.getLocalHost().getHostAddress();
            String clientPost = request.getRemoteHost();

            //400错误
            if (response.getStatus() == 404) {
                Map retMap = new HashMap();
                retMap.put(PTConst.RSP_CODE, 9);
                retMap.put(PTConst.RSP_MSG, "请求地址错误");
                PrintWriter printWriter = response.getWriter();
                printWriter.write(JsonUtils.obj2Json(retMap));
                printWriter.close();
                response.flushBuffer();
                response.setCharacterEncoding("UTF-8");
                logger.info(clientPost + "请求" + hostIp + "请求地址不存在");
            }
            //500错误
            if (response.getStatus() == 500) {
                Map retMap = new HashMap();
                retMap.put(PTConst.RSP_CODE, 10);
                retMap.put(PTConst.RSP_MSG, "内部服务器错误");
                PrintWriter printWriter = response.getWriter();
                printWriter.write(JsonUtils.obj2Json(retMap));
                printWriter.close();
                response.flushBuffer();
                response.setCharacterEncoding("UTF-8");
                logger.info(clientPost + "请求" + hostIp + "内部服务器错误");
            }

        } catch (Exception ex) {
            logger.error("拦截异常", ex);
        }
    }
}
