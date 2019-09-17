package net.dgg.zqq.webSocket;

import net.dgg.zqq.utils.GsonUtils;
import net.dgg.zqq.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <WebSocketPushHandler>
 * @despriction
 * @create 2018/11/13 14:24
 **/
@Component
public class WebSocketPushHandler extends TextWebSocketHandler {

    private static String key = "&";

    private static final int size = 10000;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final Map<String, List<String>> userTokenListMap = new HashMap(size);
    private static final Map<String, String> sessionIdToUserIdTokenMap = new HashMap<>(size);
    private static final Map<String, String> userIdTokenToSessionIdMap = new HashMap(size);
    private static final Map<String, WebSocketSession> sessionMap = new HashMap<>(size);


    /**
     * 用户进入系统监听
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attrMap = session.getAttributes();
        String userId = String.valueOf(attrMap.get(WebSocketUserKey.USER_ID_KEY));
        String token = String.valueOf(attrMap.get(WebSocketUserKey.USER_TOKEN_KEY));

        this.addUserSession(userId, token, session);

        logger.debug("websocket连接建立成功，用户唯一标识:" + userId);
    }

    /**
     * 处理用户请求
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // logger.info("系统处理xxx用户的请求信息。。。");
    }

    /**
     * 用户退出后的处理
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (session.isOpen()) {
            session.close();
        }

        this.deleteUserSession(session.getId());

        logger.debug("websocket连接断开");
    }

    /**
     * 自定义函数
     * 给所有的在线用户发送消息
     */
    public void sendMessagesToUsers(TextMessage message) {
        for (WebSocketSession user : sessionMap.values()) {
            try {
                // isOpen()在线就发送
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getLocalizedMessage());
            }
        }
    }

    /**
     * 自定义函数
     * 发送消息给指定的在线用户
     */
    public void sendMessageToUser(String userId, WebSocketMsg msg) {
        TextMessage textMessage = new TextMessage(GsonUtils.toJson(msg));
        for (String token : userTokenListMap.get(userId)) {
            String userIdToken = this.getUserTokenKey(userId, token);
            String sessionId = userIdTokenToSessionIdMap.get(userIdToken);
            WebSocketSession socketSession = StringUtils.isEmpty(sessionId) ? null : sessionMap.get(sessionId);
            if (socketSession == null) {
                continue;
            }
            try {
                socketSession.sendMessage(textMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 自定义函数
     * 发送消息给指定的在线用户
     */
    public void sendMessageToUser(String userId, String token, WebSocketMsg msg) {
        Assert.hasText(userId, "userId不能为空");
        Assert.hasText(token, "token不能为空！");
        TextMessage textMessage = new TextMessage(GsonUtils.toJson(msg));
        String userIdToken = this.getUserTokenKey(userId, token);
        String sessionId = userIdTokenToSessionIdMap.get(userIdToken);
        WebSocketSession socketSession = StringUtils.isEmpty(sessionId) ? null : sessionMap.get(sessionId);
        if (socketSession == null) {
            return;
        }
        try {
            socketSession.sendMessage(textMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getUserTokenKey(String userId, String token) {
        return userId.concat(key).concat(token);
    }

    /**
     * 用户建立连接的缓存
     *
     * @param userId
     * @param token
     * @param session
     */
    private void addUserSession(String userId, String token, WebSocketSession session) {
        userIdTokenToSessionIdMap.put(this.getUserTokenKey(userId, token), session.getId());
        sessionIdToUserIdTokenMap.put(session.getId(), this.getUserTokenKey(userId, token));
        sessionMap.put(session.getId(), session);
        List<String> userTokenList = userTokenListMap.get(userId);
        userTokenList = userTokenList == null ? new ArrayList<>() : userTokenList;
        userTokenList.add(token);
        userTokenListMap.put(userId, userTokenList);
    }


    /**
     * 用户断开连接的缓存
     *
     * @param sessionId
     */
    private void deleteUserSession(String sessionId) {
        sessionMap.remove(sessionId);
        String userToken = sessionIdToUserIdTokenMap.get(sessionId);
        sessionIdToUserIdTokenMap.remove(sessionId);
        userIdTokenToSessionIdMap.remove(userToken);

        String userId = userToken.split(key)[0], token = userToken.split(key)[1];

        List<String> userTokenList = userTokenListMap.get(userId);
        userTokenList.remove(token);
    }


}
