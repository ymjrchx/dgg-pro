package net.dgg.zqq.controller.qq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.zqq.framework.PTConst;
import net.dgg.zqq.services.qq.QQService;
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
 * @ClassName <QQControner>
 * @despriction QQ 登录
 * @create 2018/10/15 17:31
 **/
@Controller
@RequestMapping("/qq")
@Api(description = "QQ登录")
public class QQController extends BaseController {
    @Autowired
    private QQConfig qqConfig;
    @Autowired
    private QQService qqService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("qq", qqConfig);
        return "qq/qq";
    }

    @RequestMapping(value = "qqLogin", method = RequestMethod.GET)
    @ApiOperation(value = "QQ登录接口", notes = "QQ登录接口", httpMethod = "GET")
    @ResponseBody
    public Object qqLogin(@ApiParam(name = "accessToken", value = "QQ获取的accessToken", required = true) @RequestParam String accessToken) {
        try {
            Map reMap = this.qqService.login(accessToken);
            return this.getSuccessResponse(reMap);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "bingPhone", method = RequestMethod.GET)
    @ApiOperation(value = "QQ绑定电话接口", notes = "QQ绑定电话接口", httpMethod = "GET")
    @ResponseBody
    public Object qqLogin(HttpServletRequest request,
                          @ApiParam(name = "phone", value = "电话号码", required = true) @RequestParam String phone,
                          @ApiParam(name = "smsCode", value = "短信验证码", required = true) @RequestParam String smsCode,
                          @ApiParam(name = "userId", value = "QQ userId", required = true) @RequestParam Long userId) {
        try {
            String token = request.getHeader(PTConst.USER_TOKEN);
            Map reMap = this.qqService.bindPhone(token, userId, phone, smsCode);
            return this.getSuccessResponse(reMap);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/checkBind", method = RequestMethod.GET)
    @ApiOperation(value = "QQ绑定电话验证接口", notes = "QQ绑定电话验证接口", httpMethod = "GET")
    @ResponseBody
    public Object checkBind(HttpServletRequest request,
                            @ApiParam(name = "phone", value = "电话号码", required = true) @RequestParam String phone,
                            @ApiParam(name = "userId", value = "QQ userId", required = true) @RequestParam Long userId) {
        try {
            String token = request.getHeader(PTConst.USER_TOKEN);
            Integer count = this.qqService.checkPhoneBind(userId, phone);
            return this.getSuccessResponse(count);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

}
