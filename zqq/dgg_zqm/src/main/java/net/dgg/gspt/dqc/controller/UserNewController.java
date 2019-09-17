package net.dgg.gspt.dqc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.gspt.dqc.dto.user.UserNewDto;
import net.dgg.gspt.dqc.entity.business.User;
import net.dgg.gspt.dqc.framework.PTConst;
import net.dgg.gspt.dqc.services.UserNewService;
import net.dgg.gspt.dqc.services.UserService;
import net.fblock.web.common.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/userNew")
@Api(description = "新登陆注册")
public class UserNewController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserNewService userNewService;
    @Autowired
    private UserService userService;

    /**
     * 用户注册
     *
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "注册", notes = "注册")
    public Object newRegister(@ApiParam(name = "user", value = "用户信息json,{username:电话,nickname:联系人名字}", required = true) @RequestBody UserNewDto user, HttpServletRequest request, HttpServletResponse response) {
        try {
            String token = request.getHeader(PTConst.USER_TOKEN);
            User userSys = userNewService.newRegister(token, user);
            return this.getSuccessResponse(userSys);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统出现异常！");
        }
    }

    /**
     * 用户登陆
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "登录", notes = "登录")
    public Object newlogin(@ApiParam(name = "user", value = "用户信息json,{username:电话}", required = true) @RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        try {
            // String token = request.getHeader(PTConst.USER_TOKEN);
            User userSys = userNewService.newLogin(user);
            return this.getSuccessResponse(userSys);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统出现异常！");
        }
    }


    /**
     * 用户登出
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public Object logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            String token = request.getHeader(PTConst.USER_TOKEN);
            userNewService.logout(token);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统出现异常！");
        }
    }

    /**
     * 用户验证
     *
     * @return
     */
    @RequestMapping(value = "/exist", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户验证", notes = "用户验证")
    public Object exist(HttpServletRequest request, HttpServletResponse respons,
                        @ApiParam(name = "phone", value = "电话", required = true) @RequestParam String phone) {
        try {
            return this.getSuccessResponse(userNewService.exit(phone));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统出现异常！");
        }
    }


}
