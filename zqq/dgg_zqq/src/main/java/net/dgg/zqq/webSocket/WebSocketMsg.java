package net.dgg.zqq.webSocket;

/**
 * @author 刘阳
 * @ClassName <WeSocketMsg>
 * @despriction web socket 消息推送
 * @create 2018/11/13 16:31
 **/

public class WebSocketMsg {
    /**
     * 消息类型
     */
    String type;

    /**
     * 消息类容
     */
    String content;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
