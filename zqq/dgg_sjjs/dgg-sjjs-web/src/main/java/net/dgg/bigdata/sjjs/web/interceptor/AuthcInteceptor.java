package net.dgg.bigdata.sjjs.web.interceptor;

import net.dgg.bigdata.common.constant.PTConst;
import net.dgg.core.redis.DggRedisService;
import net.dgg.core.spring.DggSpringContext;
import net.dgg.core.utils.DggJsonUtils;
import net.dgg.core.utils.DggSwapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录验证拦截器
 * Created by wu on 2017-09-04.
 */
public class AuthcInteceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(AuthcInteceptor.class);

    @Autowired
    DggRedisService redisService;

    /**
     * <p>
     * 匿名用户免费搜索次数，来自数据字典
     * </p>
     */
    public static final String SEARCH_COUNT_NAME = "匿名用户免费搜索次数";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String token = null;//用户token,从header和param中都取，使用不为空的token
        if (request.getHeader(PTConst.USER_TOKEN) != null) {
            token = request.getHeader(PTConst.USER_TOKEN);
        } else {
            if (request.getParameter(PTConst.USER_TOKEN) != null)
                token = request.getParameter(PTConst.USER_TOKEN);
        }

//        String token = request.getHeader(PTConst.USER_TOKEN);
        String reqUrl = request.getRequestURL().toString();//请求资源路径

        String servletPath = request.getServletPath();
        String[] urls = {"/order/updateBaoJianStatus", "/webSocketServer", "/sockjs/webSocketServer", "/wechat/callback", "/alipay/callback", "/session/gettoken", "/mobile/userlogin", "/sysuser/login", "/registuser/login", "/imageidentifycode/send", "/sms/sentidentifycode", "/api/", "/journal/recentversion", "/dept/subdept", "task/search", "/registuser/forgetpwd", "/registuser/regist", "/company/recommend_comp", "/regist/loginByCode"};
        for (String url : urls) {
            if (servletPath.startsWith(url)) {
                return true;
            }
        }
        logger.info(reqUrl);
        //TODO 这种例外应该以配置的形式存在，否则我们无法便利的管理例外的uri
        //TODO 非空的时候也应该验证token的有效性
        boolean checkToken = true;
        DggRedisService dggRedisService = DggSpringContext.getBean(DggRedisService.class);
        if (StringUtils.isEmpty(token) || "null".equalsIgnoreCase(token) || !dggRedisService.exists(token)) {
            checkToken = false;
        }
        if (!checkToken) {
            String searchCountParam = request.getHeader("searchCount");
            if (!StringUtils.isEmpty(searchCountParam)) {
                if (searchCountParam.matches("^\\d+$")) {
                    Integer searchCount = Integer.valueOf(searchCountParam);
//                    Dictionary dictionary = dictionaryService.selectByName(SEARCH_COUNT_NAME);
                    int searchSum = 5;
//                    if (dictionary != null) {
//                        if (dictionary.getCode().matches("^\\d+$")) {
//                            searchSum = Integer.valueOf(dictionary.getCode());
//                        }
//                    }
                    if (searchCount > searchSum) {
                        Map retMap = new HashMap();
                        retMap.put(PTConst.RSP_CODE, -3);
                        retMap.put(PTConst.RSP_MSG, "匿名用户只能免费搜索" + searchSum + "次");
                        PrintWriter printWriter = response.getWriter();
                        printWriter.write(DggJsonUtils.obj2Json(retMap));
                        printWriter.close();
                        response.flushBuffer();
                        response.setCharacterEncoding("UTF-8");
                        return false;
                    } else {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        if (!checkToken) {
            Map retMap = new HashMap();
            retMap.put(PTConst.RSP_CODE, -1);
            retMap.put(PTConst.RSP_MSG, "用户未登录");
            PrintWriter printWriter = response.getWriter();
            printWriter.write(DggJsonUtils.obj2Json(retMap));
            printWriter.close();
            response.flushBuffer();
            response.setCharacterEncoding("UTF-8");
            return false;
        }
        String username = redisService.getJedisCluster().get(token);
        DggSwapUtils.put("USER_NAME", username);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
