package net.dgg.bigdata.sjjs.web.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @ClassName: newsWebSocketService
 * @Description: TODO
 * @Author: zxc
 * @Date: 2018/12/21 13:30
 */

@Service
public class NewsWebSocketService {

    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 及时推送操作记录给客户端
     *
     * @param msg
     * @param userId
     * @throws IOException
     */
    public void sendMsg(String msg, String userId) throws IOException {
        webSocketServer.sendtoUser(msg, userId);
    }

}