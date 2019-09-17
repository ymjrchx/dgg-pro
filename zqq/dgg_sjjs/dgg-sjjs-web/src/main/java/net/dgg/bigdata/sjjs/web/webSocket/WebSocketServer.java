package net.dgg.bigdata.sjjs.web.webSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: WebSocketController
 * @Description: TODO
 * @Author: zxc
 * @Date: 2018/12/20 10:30
 */

@ServerEndpoint(value = "/socket/webSocketServer/{id}")
@Component
@EnableScheduling
public class WebSocketServer {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static int onlineCount = 0;
    private static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Session> sessionSet = new ConcurrentHashMap<>();


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam(value = "id") String id, Session session) {
        webSocketSet.put(id, this);     //加入set中
        sessionSet.put(id, session);
        addOnlineCount();           //在线数加1
        log.info("用户" + id + "加入！当前在线人数为" + getOnlineCount());
        try {
            sendMessage("连接成功", id);
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }


    /**
     * 连接关闭调用的方法
     */

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }


    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
        //可以自己约定字符串内容，比如 内容|0 表示信息群发，内容|X 表示信息发给id为X的用户
        try {

        /*String sendMessage = message.split("[|]")[0];
            String sendUserId = message.split("[|]")[1];
            sendtoUser(sendMessage,sendUserId);*/

            System.err.println("收到客户端信息");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param session
     * @param error
     */

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message, String userId) throws IOException {
        if (sessionSet.get(userId) != null) {
            sessionSet.get(userId).getBasicRemote().sendText(message);
        }
    }


    /**
     * 发送信息给指定ID用户，如果用户不在线则返回不在线信息给自己
     *
     * @param message
     * @param userId
     * @throws IOException
     */
    public void sendtoUser(String message, String userId) throws IOException {
        if (webSocketSet.get(userId) != null) {
            webSocketSet.get(userId).sendMessage(message, userId);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }


}
