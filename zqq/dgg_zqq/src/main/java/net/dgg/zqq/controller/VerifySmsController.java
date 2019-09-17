package net.dgg.zqq.controller;

import net.dgg.zqq.constant.StatusConstant;
import net.dgg.zqq.dto.user.UserDto;
import net.dgg.zqq.entity.business.User;
import net.dgg.zqq.framework.PTConst;
import net.dgg.zqq.framework.redis.RedisUtils;
import net.dgg.zqq.services.SmsMsgService;
import net.dgg.zqq.services.UserService;
import net.dgg.zqq.utils.StringUtils;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Random;

@Controller
public class VerifySmsController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private SmsMsgService smsMsgService;

    private final static String TYPE_REGIST = "regist";
    private final static String TYPE_FORWARD = "fogetpwd";

    @RequestMapping("/sms/sentidentifycode")
    @ResponseBody
    public Object getSmsCode(@RequestBody UserDto user, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNullOrEmpty(user.getPhoneNo())) {
            return this.getFailResponse("手机号码不能为空");
            /*return JsonUtils.getJsonString(-2, "手机号码不能为空", null);*/
        }
        if (StringUtils.isNullOrEmpty(user.getType())) {
            return this.getFailResponse("非法获取验证码");
            /* return JsonUtils.getJsonString(-2, "非法获取验证码", null);*/
        }
        User u = new User();
        u.setUsername(user.getPhoneNo());
        List<User> list = userService.findUser(u);
        if (TYPE_REGIST.equals(user.getType())) {
            if (list.size() > 0) {
                return this.getFailResponse("用户已存在");
                /* return JsonUtils.getJsonString(-2, "用户已存在", null);*/
            }
        } else if (TYPE_FORWARD.equals(user.getType())) {
            if (list == null || list.size() == 0) {
                return this.getFailResponse("用户不存在");
                /* return JsonUtils.getJsonString(-2, "用户不存在", null);*/
            }
        }
        if (!list.isEmpty() && !StatusConstant.ENABLE.equals(list.get(0).getStatus())) {
            return this.getFailResponse("用户已被禁用！");
        }
        String token = request.getHeader("token");
        String code = String.valueOf(1000 + new Random().nextInt(9000));
        try {
            if (RedisUtils.exists(token + PTConst.VERIFY_SMS) && RedisUtils.ttl(token + PTConst.VERIFY_SMS) < -1) {
                RedisUtils.del(token + PTConst.VERIFY_SMS);
            }

            StringBuffer smsContent = new StringBuffer();
            smsContent.append("【知千秋】" + "您本次的验证码：").append(code);
            smsContent.append("，如非本人操作，请尽快修改密码。有效期10分钟。");
            this.smsMsgService.sendSmsMsg(smsContent.toString(), user.getPhoneNo());
            //new Client().sendMsgBatch(user.getPhoneNo(), smsContent.toString(), 1);
            //JedisCluster cluster = RedisFactory.getJedisCluster();
            RedisUtils.set(token + PTConst.VERIFY_SMS, code + "&" + user.getPhoneNo());
            RedisUtils.expire(token + PTConst.VERIFY_SMS, Integer.parseInt(RedisUtils.getRedisPriperty(PTConst.SMS_VERIFY_EXPIRE)));
        } catch (Exception e) {
            return this.getFailResponse("失败");
            /*return JsonUtils.getJsonString(-3, "失败", null);*/
        }
        return this.getSuccessResponse("成功");
        /* return JsonUtils.getJsonString(0, "成功", null);*/
    }
}
