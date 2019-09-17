package net.dgg.bigdata.sjjs.web.interceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.dgg.bigdata.common.constant.PTConst;
import net.dgg.bigdata.sjjs.web.dao.SysUserDao;
import net.dgg.bigdata.sjjs.web.entity.SysUser;
import net.dgg.bigdata.sjjs.web.util.SysUserUtils;
import net.dgg.core.redis.DggRedisService;
import net.dgg.core.utils.DggJsonUtils;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: SysLoginIntecetor
 * @Description: TODO
 * @Author: zxc
 * @Date: 2018/12/12 17:13
 */

@Component
public class SysLoginIntecetor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(SysLoginIntecetor.class);
    @Autowired
    private SysUserDao sysUserDao;
    @Value("${jwt.config.key}")
    private String key;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("----------登录拦截器--------");
        //获得请求头
        final String token = request.getHeader(PTConst.SYSUSER_TOKEN);
        if (token != null) {
            //有token
            //按照与前端约定规则解析出token

            //token解析
            try {
                Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
                if (claims != null && claims.get("roles") != null && "user".equals(claims.get("roles"))) {
                    //有用户权限
                    String userId = claims.getId();
                    if (userId != null) {
                        SysUser sysUser = sysUserDao.findById(userId);
                        if (sysUser != null) {
                            //token正确
                            //获得到期时间
                            Date expiration = claims.getExpiration();
                            Date now = new Date();
                            //判断过期时间是否小于30分钟  如果是 则重新生成token 返回给客户端
                            if (expiration.getTime() - now.getTime() < 1000 * 60 * 30) {
                                logger.info("----token即将到期----");
                                String nowToken = getToken(sysUser);
                                DggRedisService.set(sysUser.getUserId(), nowToken);
                                Map map = new HashMap<>();
                                map.put("newToken", nowToken);

                                DggRestResponse restResponse = new DggRestResponse();
                                restResponse.setCode(DggConstants.NEW_TOKEN_CODE);
                                restResponse.setMsg("----token即将到期----");
                                restResponse.setData(map);
                                response.setContentType("text/html;charset=UTF-8");
                                PrintWriter printWriter = response.getWriter();
                                printWriter.write(DggJsonUtils.obj2Json(restResponse));
                                printWriter.close();
                                response.flushBuffer();
                                return false;
                            }

                            if (DggRedisService.exists(userId)) {
                                if (token.equals(DggRedisService.get(userId))) {
                                    request.setAttribute(SysUserUtils.KEY, sysUser);
                                    return true;
                                } else {
                                    DggRestResponse restResponse = new DggRestResponse();
                                    restResponse.setCode(DggConstants.NEED_LOGIN);
                                    restResponse.setMsg("您的账号已在其他设备上登录 请重新登录");
                                    response.setContentType("text/html;charset=UTF-8");
                                    PrintWriter printWriter = response.getWriter();
                                    printWriter.write(DggJsonUtils.obj2Json(restResponse));
                                    printWriter.close();
                                    response.flushBuffer();
                                    return false;
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }

        DggRestResponse restResponse = new DggRestResponse();
        restResponse.setCode(DggConstants.NEED_LOGIN);
        restResponse.setMsg("登录超时 请重新登录");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(DggJsonUtils.obj2Json(restResponse));
        printWriter.close();
        response.flushBuffer();
        return false;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private String getToken(SysUser sysUser) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setId(sysUser.getUserId())
                .claim("roles", "user")
                .setIssuedAt(now)
                .setExpiration(new Date(nowMillis + 1000 * 60 * 60 * 2))
                .signWith(SignatureAlgorithm.HS256, key);
        return builder.compact();
    }
}