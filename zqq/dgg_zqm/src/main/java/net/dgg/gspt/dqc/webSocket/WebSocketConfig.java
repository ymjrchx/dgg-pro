package net.dgg.gspt.dqc.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * @author 刘阳
 * @ClassName <WebSocketConfig>
 * @despriction
 * @create 2018/11/13 14:25
 **/
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private WebSocketPushHandler webSocketPushHandler;

    /**
     * 注册WebSocket处理类
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(createWebSocketPushHandler(), "/webSocketServer")
                .addInterceptors(createHhandshakeInterceptor()).setAllowedOrigins("*");
        registry.addHandler(createWebSocketPushHandler(), "/sockjs/webSocketServer")
                .addInterceptors(createHhandshakeInterceptor()).withSockJS();
    }

    /**
     * @return
     * @Title: createHhandshakeInterceptor
     * @Description: 握手拦截器
     */
    @Bean
    public HandshakeInterceptor createHhandshakeInterceptor() {
        return new WebSocketInterceptor();
    }

    /**
     * @return
     * @Title: createWebSocketPushHandler
     * @Description: 处理类
     */
    @Bean
    public WebSocketHandler createWebSocketPushHandler() {
        return this.webSocketPushHandler;
    }

}
