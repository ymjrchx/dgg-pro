package net.dgg.gspt.dqc.services;

import net.dgg.gspt.dqc.framework.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Random;

/**
 * @author 刘阳
 * @ClassName <SmsCodeService>
 * @despriction
 * @create 2018/12/27 18:14
 **/
@Service
public class SmsCodeService {
    private static String msgTemplate = "【智企名】您本次的验证码：%s，如非本人操作，请尽快修改密码。有效期10分钟。";


    private static String msgKey = "sms_";

    @Value("${sms.expire}")
    private Integer expire;

    @Autowired
    private SmsNewService smsNewService;


    public void sendCode(String phone, String token) {
        String code = this.getCode();
        String smsMsg = String.format(msgTemplate, code);
        smsNewService.sendMsg(phone, smsMsg);
        // 发送成功 , redis 记录 验证码
        String msgRedisKey = this.getMsgRedisKey(phone, token);
        RedisUtils.set(msgRedisKey, code);
        RedisUtils.expire(msgRedisKey, this.expire);
    }

    /**
     * 验证码 验证
     *
     * @param phone
     * @param token
     * @param code
     * @return
     */
    public boolean checkPhoneAndCode(String phone, String token, String code) {
        String msgRedisKey = this.getMsgRedisKey(phone, token);
        String redisCode = RedisUtils.get(msgRedisKey);
        Assert.hasText(redisCode, "验证码已过期！");
        Assert.isTrue(redisCode.equals(code), "验证码错误！");
        return true;
    }


    /**
     * 删除code 缓存
     *
     * @param phone
     * @param token
     */
    public void deleteCode(String phone, String token) {
        String msgRedisKey = this.getMsgRedisKey(phone, token);
        RedisUtils.del(msgRedisKey);
    }


    /**
     * 获取验证码
     *
     * @return
     */
    private String getCode() {
        return String.valueOf(1000 + new Random().nextInt(9000));
    }

    // 获取redis key
    private String getMsgRedisKey(String phone, String token) {
        return msgKey.concat(phone).concat("_").concat(token);
    }
}
