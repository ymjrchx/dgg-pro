package net.dgg.bigdata.sjjs.web.controller.sysUser;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.bigdata.common.constant.PTConst;
import net.dgg.bigdata.sjjs.web.constant.StatusConstant;
import net.dgg.bigdata.sjjs.web.entity.SysUser;
import net.dgg.bigdata.sjjs.web.entity.dto.UserDto;
import net.dgg.bigdata.sjjs.web.service.SendMsgService;
import net.dgg.bigdata.sjjs.web.service.SysUserService;
import net.dgg.core.redis.DggRedisService;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggBaseController;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/**
 * 短信验证码控制器
 */

@Controller
@RequestMapping("/smsCode")
public class VerifySmsController extends DggBaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final static String TYPE_REGIST = "regist";
    private final static String TYPE_FORWARD = "fogetpwd";
    private final static int smsTime = 600; //过期时间  10分钟

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SendMsgService sendMsgService;


    /**
     * 获取短信验证码
     *
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "获取短信验证码")
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public DggRestResponse get(@RequestBody UserDto user, HttpServletRequest request) {
        try {
            Assert.hasText(user.getPhoneNo(), "手机号码不能为空");
            Assert.isTrue(!StringUtils.isEmpty(user.getType()) && Arrays.asList(TYPE_REGIST, TYPE_FORWARD).contains(user.getType()), "非法获取验证码");
            //根据用户名(电话号码查询用户)
            SysUser sysUser = sysUserService.findByUsername(user.getPhoneNo());
            if (TYPE_REGIST.equals(user.getType())) {
                //注册时获得验证码
                Assert.isTrue(sysUser == null, "用户已存在");
            } else {
                //修改密码时获取密码
                Assert.isTrue(sysUser != null, "用户不存在");
                Assert.isTrue(StatusConstant.DISABLE.equals(sysUser.getStatus()), "用户已被禁用！不能获取验证码");
            }
            //获得token
            String token = request.getHeader(PTConst.USER_TOKEN);
            //生成验证码
            String code = RandomStringUtils.randomNumeric(6);

            //服务器中存储验证码
            String key = token.concat(PTConst.VERIFY_SMS);
            String value = code.concat("&").concat(user.getPhoneNo());
            if (DggRedisService.exists(key)) {
                DggRedisService.del(key);
            }
            DggRedisService.set(key, value);
            DggRedisService.expire(key, smsTime);

            //发送短信
            StringBuffer smsContent = new StringBuffer();
            smsContent.append("【顶企客】" + "您本次的验证码：").append(code);
            smsContent.append("，如非本人操作，请尽快处理。有效期10分钟。");
            sendMsgService.sendSmsMsg(smsContent.toString(), user.getPhoneNo());

            //方便测试用,暂时添加
            /*Map mapResult = new HashMap<>();
            mapResult.put("smsCode", code);
            return this.getSuccessResponse(mapResult);*/

            return this.getSuccessResponse("成功");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            return this.getFailResponse("失败");
        }
    }


    /**
     * 校验短信验证码
     *
     * @param code
     * @param phone
     * @param request
     * @return
     */
    @ApiOperation(value = "校验短信验证码")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    public DggRestResponse check(@ApiParam(name = "code", value = "短信验证码") String code,
                                 @ApiParam(name = "phone", value = "短信验证码") String phone,
                                 HttpServletRequest request) {
        try {
            Assert.hasText(code, "code不能为空");
            Assert.hasText(phone, "phone不能为空");
            /*SysUser sysUser = sysUserService.findByUsername(phone);
            Assert.notNull(sysUser, "非法电话");*/
            String token = request.getHeader(PTConst.USER_TOKEN);
            String key = token.concat(PTConst.VERIFY_SMS);
            String value = DggRedisService.get(key);
            Assert.hasText(value, "请获取短信验证码");
            String[] codes = value.split("&");
            Assert.isTrue(codes != null && codes.length == 2, "请获取短信验证码");
            Assert.isTrue(phone.equals(codes[1]), "短信验证码错误");
            Assert.isTrue(code.equals(codes[0]), "短信验证码错误");

            return this.getSuccessResponse("短信验证通过");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }
    }


}