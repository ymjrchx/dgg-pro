package net.dgg.zqq.webSocket;

import net.dgg.zqq.framework.redis.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <WebSocketInterceptor>
 * @despriction 将参数传递到 websocket
 * @create 2018/11/13 13:58
 **/

public class WebSocketInterceptor implements HandshakeInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        logger.debug("用户开始建立websocket连接。。。");
        if (request instanceof ServletServerHttpRequest) {
            String userId = ((ServletServerHttpRequest) request).getServletRequest().getParameter(WebSocketUserKey.USER_ID_KEY);
            String wToken = ((ServletServerHttpRequest) request).getServletRequest().getParameter(WebSocketUserKey.USER_TOKEN_KEY);
            if (!StringUtils.isEmpty(userId) && RedisUtils.exists(userId)) {
                attributes.put(WebSocketUserKey.USER_ID_KEY, userId);
                attributes.put(WebSocketUserKey.USER_TOKEN_KEY, wToken);
                return true;
            }
            logger.debug("websocket连接建立失败，用户唯一标识:" + userId);
            return false;
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
