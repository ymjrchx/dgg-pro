package net.dgg.gspt.dqc.controller.webSoctet;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import net.dgg.gspt.dqc.webSocket.WebSocketMsg;
import net.dgg.gspt.dqc.webSocket.WebSocketPushHandler;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 刘阳
 * @ClassName <WebSocketController>
 * @despriction
 * @create 2018/11/13 19:57
 **/
@Controller
@RequestMapping("/webSocket")
@Api(description = "WebSocket")
public class WebSocketController extends BaseController {
    @Autowired
    private WebSocketPushHandler webSocketPushHandler;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model) {

        return "websocket/websocket";
    }


    @RequestMapping(value = "/sendMsgToUser", method = RequestMethod.POST)
    @ResponseBody
    public Object sendMsgToUser(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "token", value = "token", required = true) @RequestParam String token,
            @ApiParam(name = "msgType", value = "消息类型", required = true) @RequestParam String msgType,
            @ApiParam(name = "msg", value = "消息", required = true) @RequestParam String msg) {
        try {
            WebSocketMsg wMsg = new WebSocketMsg();
            wMsg.setContent(msg);
            wMsg.setType(msgType);
            this.webSocketPushHandler.sendMessageToUser(userId, token, wMsg);
            return this.getSuccessResponse("success");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统错误");
        }


    }

}
