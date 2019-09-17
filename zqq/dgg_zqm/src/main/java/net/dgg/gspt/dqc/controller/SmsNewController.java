package net.dgg.gspt.dqc.controller;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.gspt.dqc.services.SmsNewService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <SmsNewController>
 * @despriction 新短信发送
 * @create 2018/12/27 16:29
 **/
@Controller
@RequestMapping("/sms")
@Api(description = "新消息发送")
public class SmsNewController extends BaseController {
    @Autowired
    private SmsNewService smsNewService;


    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新消息发送", notes = "新消息发送")
    public Object getSmsCode(HttpServletRequest request, HttpServletResponse response,
                             @ApiParam(name = "phone", value = "电话", required = true) @RequestParam String phone) {
        try {
            //String token = request.getHeader(PTConst.USER_TOKEN);
            this.smsNewService.sendMsg(phone, "__");
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }

    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "消息回调", notes = "消息回调")
    public Object getSmsCode(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {
        try {
            logger.info(new Gson().toJson(map));
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }

}
