package net.dgg.gspt.dqc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.gspt.dqc.dto.user.UserDto;
import net.dgg.gspt.dqc.entity.business.User;
import net.dgg.gspt.dqc.framework.PTConst;
import net.dgg.gspt.dqc.framework.redis.RedisUtils;
import net.dgg.gspt.dqc.framework.security.DESPlus;
import net.dgg.gspt.dqc.services.PersonInfoService;
import net.dgg.gspt.dqc.services.SmsMsgService;
import net.dgg.gspt.dqc.services.impl.UserServiceImpl;
import net.dgg.gspt.dqc.utils.StringUtils;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/9/30 14:37
 */

@Controller
@RequestMapping("/persional")
@Api(description = "个人信息")
public class PersonalInfoController extends BaseController {

    @Autowired
    PersonInfoService personInfoService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private SmsMsgService smsMsgService;

    private final static String TYPE_REGIST = "regist";
    private final static String TYPE_FORWARD = "fogetpwd";


    /**
     * 返回个人信息
     *
     * @return
     */

    @RequestMapping(value = "/Info", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "个人信息查询", notes = "个人信息查询")
    public Object selectInfo(@ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId) {
        Assert.hasText(userId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "登陆超期，请重新登录！");
        Map map = new HashMap();
        User user = this.personInfoService.findInfoById(userId);
        if (null != user) {
            map.put("userId", user.getUserId());
            map.put("nickname", user.getNickname());
            map.put("sex", user.getSex());
            map.put("email", user.getEmail());
            map.put("phoneNo", net.dgg.iboss.base.util.StringUtils.encryptionNumber(user.getUsername()));
            map.put("birthday", user.getBirthday());
            return this.getSuccessResponse(map);
        } else return this.getFailResponse("个人信息错误");
    }


    /**
     * 更新个人信息
     *
     * @return
     */

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "个人信息更新", notes = "个人信息更新")
    public Object updateInfo(@ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId,
                             @ApiParam(name = "nickname", value = "昵称", required = true) @RequestParam String nickname,
                             @ApiParam(name = "sex", value = "性别", required = true) @RequestParam int sex,
                             @ApiParam(name = "birthday", value = "生日", required = true) @RequestParam Date birthday) {
        try {
            //验证用户是否登录
            Assert.hasText(userId, "用户ID不能为空！");
            Assert.isTrue(RedisUtils.exists(userId), "登陆超期，请重新登录！");
            Assert.hasText(nickname, "昵称不能为空");
            if (nickname != null && nickname.length() > 20) {
                return this.getFailResponse("昵称不能过长！");
            }
            this.personInfoService.updateInfoById(userId, nickname, sex, birthday);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统出现异常！");
        }

    }

    /**
     * 更新邮箱
     */
    @RequestMapping(value = "/updateEmail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "邮箱地址更新", notes = "邮箱地址更新")
    public Object updateEmail(@ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId,
                              @ApiParam(name = "email", value = "新的邮箱地址", required = true) @RequestParam String email
    ) {


        try {
            //验证用户是否登录
            Assert.hasText(userId, "用户ID不能为空！");
            Assert.isTrue(RedisUtils.exists(userId), "登陆超期，请重新登录！");
            if (!email.matches("^\\w+@(\\w+\\.)+\\w+$")) {
                return this.getFailResponse("请输入正确的邮箱格式");
            }
            this.personInfoService.updateEmailByid(userId, email);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统出现异常！");
        }
    }


    /**
     * 更新账户密码
     */
    @RequestMapping(value = "/updateLoginPwd", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "账户密码更新", notes = "账户密码更新")
    public Object updateLoginPwd(@ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId,
                                 @ApiParam(name = "loginPwd", value = "新的密码", required = true) @RequestParam String loginPwd) {


        try {
            //验证用户是否登录
            Assert.hasText(userId, "用户ID不能为空！");
            Assert.isTrue(RedisUtils.exists(userId), "登陆超期，请重新登录！");
            this.personInfoService.updateLoginPwdByid(userId, loginPwd);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统出现异常！");
        }
    }

    /**
     * 更新手机号码
     */
    @RequestMapping(value = "/updateUsername", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "手机号码更新", notes = "手机号码更新")
    public Object updateUsername(@ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId,
                                 @ApiParam(name = "username", value = "新手机号", required = true) @RequestParam String username) {


        try {
            //验证用户是否登录
            Assert.hasText(userId, "用户ID不能为空！");
            Assert.hasText(username, "新手机号不能为空");
            Assert.isTrue(RedisUtils.exists(userId), "登陆超期，请重新登录！");
            User user = new User();
            user.setUsername(username);
            List<User> list = this.userService.findUser(user);
            if (list != null && list.size() > 0) {
                return this.getFailResponse("该手机已注册");
            }
            this.personInfoService.updateUsernamedByid(userId, username);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统出现异常！");
        }
    }


    /**
     * 修改绑定手机-密码验证
     */
    @RequestMapping(value = "/pwdVerify", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "修改绑定手机密码验证", notes = "修改绑定手机密码验证")
    public Object selectInfo(@ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId,
                             @ApiParam(name = "loginPwd", value = "密码", required = true) @RequestParam String loginPwd) {
        User user = this.personInfoService.findInfoById(userId);
        String pwd = new DESPlus(PTConst.PWD_KEY).encrypt(loginPwd);
        if (user.getLoginPwd().equals(pwd)) return this.getSuccessResponse("密码正确");
        else return this.getFailResponse("密码错误");
    }




    /**
     * 验证码验证
     */

    @RequestMapping(value = "/phoneVerify", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "验证码验证", notes = "验证码验证")
    public Object phoneVerify(@ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId,
                              @ApiParam(name = "verify", value = "验证码", required = true) @RequestParam String verify, HttpServletRequest request) {

        try {
            String token = request.getHeader(PTConst.USER_TOKEN);
            String msg = this.personInfoService.codeVerify(userId, verify, token);
            Map map = new HashMap();
            if ("success".equals(msg)) {
                RedisUtils.del(token + PTConst.VERIFY_SMS);
                return this.getSuccessResponse(msg);
            } else {
                return this.getFailResponse(msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统错误");
        }
    }

    /**
     * 获取验证码
     */
    @RequestMapping("/sms/personalCode")
    @ResponseBody
    @ApiOperation(value = "个人中心获取验证码", notes = "个人中心获取验证码")
    public Object getPersonalSmsCode(@RequestBody UserDto user, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNullOrEmpty(user.getPhoneNo())) {
            return this.getFailResponse("手机号码不能为空");
            /*return JsonUtils.getJsonString(-2, "手机号码不能为空", null);*/
        }
        if (StringUtils.isNullOrEmpty(user.getType())) {
            return this.getFailResponse("非法获取验证码");
            /* return JsonUtils.getJsonString(-2, "非法获取验证码", null);*/
        }
        User u = userService.findUserById(user.getUserId());
        String token = request.getHeader("token");
        String code = String.valueOf(1000 + new Random().nextInt(9000));
        try {
            if (RedisUtils.exists(token + PTConst.VERIFY_SMS) && RedisUtils.ttl(token + PTConst.VERIFY_SMS) < -1) {
                RedisUtils.del(token + PTConst.VERIFY_SMS);
            }

            StringBuffer smsContent = new StringBuffer();
            smsContent.append("【顶企查】" + "您本次的验证码：").append(code);
            smsContent.append("，如非本人操作，请尽快修改密码。有效期10分钟。");
            this.smsMsgService.sendSmsMsg(smsContent.toString(), u.getUsername());
            //new Client().sendMsgBatch(user.getPhoneNo(), smsContent.toString(), 1);
            //JedisCluster cluster = RedisFactory.getJedisCluster();
            RedisUtils.set(token + PTConst.VERIFY_SMS, code + "&" + u.getUsername());
            RedisUtils.expire(token + PTConst.VERIFY_SMS, Integer.parseInt(RedisUtils.getRedisPriperty(PTConst.SMS_VERIFY_EXPIRE)));
        } catch (Exception e) {
            return this.getFailResponse("失败");
            /*return JsonUtils.getJsonString(-3, "失败", null);*/
        }
        return this.getSuccessResponse("成功");
        /* return JsonUtils.getJsonString(0, "成功", null);*/
    }

    /**
     * 新手机号获取验证码
     */
    @RequestMapping("/sms/newPersonalCode")
    @ResponseBody
    @ApiOperation(value = "新手机号获取验证码", notes = "新手机号获取验证码")
    public Object getNewSmsCode(@RequestBody UserDto user, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNullOrEmpty(user.getPhoneNo())) {
            return this.getFailResponse("手机号码不能为空");
            /*return JsonUtils.getJsonString(-2, "手机号码不能为空", null);*/
        }
        if (StringUtils.isNullOrEmpty(user.getType())) {
            return this.getFailResponse("非法获取验证码");
            /* return JsonUtils.getJsonString(-2, "非法获取验证码", null);*/
        }
        User u = userService.findUserById(user.getUserId());
        String token = request.getHeader("token");
        String code = String.valueOf(1000 + new Random().nextInt(9000));
        try {
            if (RedisUtils.exists(token + PTConst.VERIFY_SMS) && RedisUtils.ttl(token + PTConst.VERIFY_SMS) < -1) {
                RedisUtils.del(token + PTConst.VERIFY_SMS);
            }

            StringBuffer smsContent = new StringBuffer();
            smsContent.append("【顶企查】" + "您本次的验证码：").append(code);
            smsContent.append("，如非本人操作，请尽快修改密码。").
                    append("有效期10分钟。");
            this.smsMsgService.sendSmsMsg(smsContent.toString(), user.getPhoneNo());
            //new Client().sendMsgBatch(user.getPhoneNo(), smsContent.toString(), 1);
            //JedisCluster cluster = RedisFactory.getJedisCluster();
            RedisUtils.set(token + PTConst.VERIFY_SMS, code + "&" + u.getUsername());
            RedisUtils.expire(token + PTConst.VERIFY_SMS, Integer.parseInt(RedisUtils.getRedisPriperty(PTConst.SMS_VERIFY_EXPIRE)));
        } catch (Exception e) {
            return this.getFailResponse("失败");
            /*return JsonUtils.getJsonString(-3, "失败", null);*/
        }
        return this.getSuccessResponse("成功");
        /* return JsonUtils.getJsonString(0, "成功", null);*/
    }




    /**
     * 手机号码是否能绑定
     */
    @RequestMapping(value = "/checkBind", method = RequestMethod.GET)
    @ApiOperation(value = "绑定电话验证", notes = "绑定电话验证")
    @ResponseBody
    public Object checkBind(@ApiParam(name = "username", value = "电话号码", required = true) @RequestParam String username,
                            @ApiParam(name = "userId", value = "userId", required = true) @RequestParam String userId) {
        try {
            Integer count = this.personInfoService.checkPhoneBind(userId, username);
            return this.getSuccessResponse(count);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    /**
     * 小程序—修改昵称
     */
    @RequestMapping(value = "/updateNick", method = RequestMethod.GET)
    @ApiOperation(value = "小程序—修改昵称", notes = "小程序—修改昵称")
    @ResponseBody
    public Object updateNickname(@ApiParam(name = "nickname", value = "电话号码", required = true) @RequestParam String nickname,
                                 @ApiParam(name = "userId", value = "userId", required = true) @RequestParam String userId) {
        try {
            this.personInfoService.updateNicknameById(userId, nickname);
            return this.getSuccessResponse("success");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }

    }






}



















