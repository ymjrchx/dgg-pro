package net.dgg.bigdata.sjjs.web.webSocket;

import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @ClassName: SendMassageController
 * @Description: TODO
 * @Author: zxc
 * @Date: 2018/12/20 17:00
 */

@Controller
@RequestMapping("/send")
public class SendMassageController extends DggBaseController {

    @Autowired
    private NewsWebSocketService service;

    @RequestMapping(value = "/msg", method = RequestMethod.GET)
    @ResponseBody
    public DggRestResponse get(@RequestParam String msg, @RequestParam String id) {
        try {
            service.sendMsg(msg, id);
            return this.getSuccessResponse("成功");
        } catch (IOException e) {
            e.printStackTrace();
            return this.getFailResponse("失败");
        }

    }

}
