package net.dgg.bigdata.sjjs.web.controller.sysUser;

import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.bigdata.common.constant.PTConst;
import net.dgg.bigdata.sjjs.web.entity.dto.UserDto;
import net.dgg.bigdata.sjjs.web.service.SysUserService;
import net.dgg.bigdata.sjjs.web.util.SysUserUtils;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 系统用户控制层
 *
 * @ClassName: SysUserController
 * @Description: TODO
 * @Author: zxc
 * @Date: 2018/12/11 10:56
 */

@Controller
@RequestMapping("/sysUser")
public class SysUserController extends DggBaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserService sysUserService;

    /**
     * 注册
     *
     * @param userJson
     * @param request
     * @return
     */
    @ApiOperation(value = "注册")
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ResponseBody
    public DggRestResponse regist(
            @ApiParam(name = "userJson", value = "必要用户信息：" +
                    "<br>phoneNo:手机号" +
                    "<br>loginPwd:密码" +
                    "<br>email:邮箱" +
                    "<br>smsVerifyCode:短信验证码," +
                    "<br>imageVerifyCode:图片验证码", required = true)
            @RequestParam(required = true) String userJson,
           /* @ApiParam(name = "companyJson", value = "必要企业信息：" +
                    "<br>companyName:企业全称 " +
                    "<br>contactName:联系人姓名," +
                    "<br>contactEmail:邮箱," +
                    "<br>customerManagerCode:客户经理代码", required = true)
            @RequestParam(required = true) String companyJson,*/
            HttpServletRequest request) {
        try {
            Assert.hasText(userJson, "userJson不能为空");
            //Assert.hasText(companyJson, "companyJson不能为空");
            String token = request.getHeader(PTConst.USER_TOKEN);
            Gson gson = new Gson();
            UserDto userDto = gson.fromJson(userJson, UserDto.class);
            //Company company = gson.fromJson(companyJson, Company.class);
            sysUserService.regist(token, userDto);
            return this.getSuccessResponse("注册成功");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }
    }


    /**
     * 登录
     *
     * @param
     * @return
     */
    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public DggRestResponse login(
            @ApiParam(name = "userJson", value = "必要订单信息：" +
                    "<br>phoneNo:手机号 " +
                    "<br>loginPwd:密码", required = true)
            @RequestBody(required = true) UserDto userDto, HttpServletRequest request) {
        try {
            Map map = sysUserService.login(userDto, request);
            return this.getSuccessResponse(map);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }
    }

    /**
     * 注销
     *
     * @param
     * @return
     */
    @ApiOperation(value = "注销")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public DggRestResponse logout(HttpServletRequest request) {
        try {
            sysUserService.logout(SysUserUtils.getUser(request));
            return this.getSuccessResponse("退出成功");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }
    }

    /**
     * 设置密码
     *
     * @param pwd
     * @return
     */
    @ApiOperation(value = "设置密码")
    @RequestMapping(value = "/setPwd", method = RequestMethod.GET)
    @ResponseBody
    public DggRestResponse setPwd(@ApiParam(name = "pwd", value = "用户密码", required = true) @RequestParam(required = true) String pwd,
                                  HttpServletRequest request) {
        try {
            sysUserService.setPwd(SysUserUtils.getUser(request), pwd);
            return this.getSuccessResponse("设置密码成功");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }
    }

    /**
     * 修改密码
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @ApiOperation(value = "修改密码")
    @RequestMapping(value = "/changePwd", method = RequestMethod.GET)
    @ResponseBody
    public DggRestResponse changePwd(@ApiParam(name = "oldPwd", value = "用户密码", required = true) @RequestParam(required = true) String oldPwd,
                                     @ApiParam(name = "newPwd", value = "用户密码", required = true) @RequestParam(required = true) String newPwd,
                                     HttpServletRequest request) {
        try {
            sysUserService.changePwd(SysUserUtils.getUser(request), oldPwd, newPwd);
            return this.getSuccessResponse("修改密码成功");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }
    }

    /**
     * 改绑手机
     *
     * @return
     */
    @ApiOperation(value = "改绑手机")
    @RequestMapping(value = "/changePhone", method = RequestMethod.GET)
    @ResponseBody
    public DggRestResponse changePhone(@ApiParam(name = "oldPhone", value = "用户老号码", required = true) String oldPhone,
                                       @ApiParam(name = "newPhone", value = "用户新号码", required = true) String newPhone,
                                       HttpServletRequest request) {
        try {
            sysUserService.changePhone(SysUserUtils.getUser(request), oldPhone, newPhone);
            return this.getSuccessResponse("改绑手机成功");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }
    }

    /**
     * 修改用户昵称和性别
     *
     * @param nickname
     * @param sex
     * @param request
     * @return
     */
    @ApiOperation(value = "修改用户昵称和性别")
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    public DggRestResponse update(@ApiParam(name = "nickname", value = "昵称", required = true) String nickname,
                                  @ApiParam(name = "sex(1为男性  2为女性)", value = "性别", required = true) Integer sex,
                                  HttpServletRequest request) {
        try {
            sysUserService.update(SysUserUtils.getUser(request), nickname, sex);
            return this.getSuccessResponse("修改成功");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }

    }


    /**
     * 绑定账号
     *
     * @param
     * @return
     */
    @ApiOperation(value = "绑定账号")
    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    @ResponseBody
    public DggRestResponse userBind(
            @ApiParam(name = "userDto", value = "必要订单信息：" +
                    "<br>ibossCode:iboss编号 " +
                    "<br>legenPerson:负责人 " +
                    "<br>legenDept:负责人部门", required = true)
            @RequestBody(required = true) UserDto userDto, HttpServletRequest request) {
        try {
            sysUserService.userBind(userDto, SysUserUtils.getUser(request));
            return this.getSuccessResponse("绑定成功");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }
    }

    /**
     * 判断是否绑定编号
     * @param
     * @return
     */
    @ApiOperation(value = "判断是否绑定编号")
    @RequestMapping(value = "/checkBind", method = RequestMethod.GET)
    @ResponseBody
    public DggRestResponse checkBind(HttpServletRequest request) {
        try {
            return this.getSuccessResponse(sysUserService.checkBind(SysUserUtils.getUser(request)));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }
    }

    /**
     * 获取随机token
     *
     * @return
     */
    @ApiOperation(value = "获取token")
    @RequestMapping(value = "/session/gettoken", method = RequestMethod.POST)
    @ResponseBody
    public Object gettoken() {
        try {
            return this.getSuccessResponse(sysUserService.gettoken());
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }
    }

    /**
     * 获得当前用户信息
     *
     * @return
     */
    @ApiOperation(value = "获得当前用户信息")
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    @ResponseBody
    public Object getUser(HttpServletRequest request) {
        try {
            return this.getSuccessResponse(sysUserService.getUser(SysUserUtils.getUser(request)));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }
    }

    /**
     * 获得所有用户
     *
     * @return
     */
    @ApiOperation(value = "获得所有用户")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public Object getAll() {
        try {
            return this.getSuccessResponse(sysUserService.getAll());
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }
    }

    /**
     * 修改姓名
     * @param name
     * @return
     */
    @ApiOperation(value = "获得所有用户")
    @RequestMapping(value = "/changeLegenPerson", method = RequestMethod.GET)
    @ResponseBody
    public Object changeLegenPerson(@ApiParam(name = "name", value = "用户密码", required = true) @RequestParam(required = true) String name,
                                    HttpServletRequest request) {
        try {
            sysUserService.changeLegenPerson(name, SysUserUtils.getUser(request));
            return this.getSuccessResponse("修改成功");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }
    }


}