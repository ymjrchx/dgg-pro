package net.dgg.zqq.controller.wechat;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.zqq.framework.PTConst;
import net.dgg.zqq.services.wechat.WechatService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <WechatController>
 * @despriction 微信登录
 * @create 2018/09/17 10:53
 **/
@Controller
@RequestMapping("/wechat")
@Api(description = "微信登录")
public class WechatController extends BaseController {

    @Autowired
    private WechatConfig wechatConfig;
    @Autowired
    private WechatService wechatService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("wechat", wechatConfig);
        return "wechat/wechat";
    }

    /**
     * 微信登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "微信登录接口", notes = "微信登录接口", httpMethod = "GET")
    public Object login(HttpServletRequest request,
                        @ApiParam(name = "code", value = "微信的重定向code参数", required = true) @RequestParam String code,
                        @ApiParam(name = "state", value = "微信的重定向state参数", required = true) @RequestParam String state) {

        try {
            Map reMap = this.wechatService.login(code, state);
            return this.getSuccessResponse(reMap);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "bingPhone", method = RequestMethod.GET)
    @ApiOperation(value = "微信绑定电话接口", notes = "微信绑定电话接口", httpMethod = "GET")
    @ResponseBody
    public Object qqLogin(HttpServletRequest request,
                          @ApiParam(name = "phone", value = "电话号码", required = true) @RequestParam String phone,
                          @ApiParam(name = "smsCode", value = "短信验证码", required = true) @RequestParam String smsCode,
                          @ApiParam(name = "userId", value = "QQ userId", required = true) @RequestParam Long userId) {
        try {
            String token = request.getHeader(PTConst.USER_TOKEN);
            Map reMap = this.wechatService.bindPhone(token, userId, phone, smsCode);
            return this.getSuccessResponse(reMap);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/checkBind", method = RequestMethod.GET)
    @ApiOperation(value = "微信绑定电话验证接口", notes = "微信绑定电话验证接口", httpMethod = "GET")
    @ResponseBody
    public Object checkBind(HttpServletRequest request,
                            @ApiParam(name = "phone", value = "电话号码", required = true) @RequestParam String phone,
                            @ApiParam(name = "userId", value = "QQ userId", required = true) @RequestParam Long userId) {
        try {
            String token = request.getHeader(PTConst.USER_TOKEN);
            Integer count = this.wechatService.checkPhoneBind(userId, phone);
            return this.getSuccessResponse(count);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

}
