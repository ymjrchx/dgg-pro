package net.dgg.gspt.dqc.controller;


import net.dgg.gspt.dqc.dto.user.VerifyImageDto;
import net.dgg.gspt.dqc.framework.PTConst;
import net.dgg.gspt.dqc.framework.redis.RedisUtils;
import net.dgg.gspt.dqc.utils.JsonUtils;
import net.dgg.gspt.dqc.utils.VerifyImageUtils;
import net.fblock.web.common.BaseController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/imageidentifycode")
public class VerifyImageController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    /**
     * 获取验证码
     *
     * @param
     * @param request
     * @param response
     */
    @RequestMapping("/send")
    @ResponseBody
    public Object get(String type, HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(PTConst.USER_TOKEN);
        log.info("imgtoken:" + token);
        //生成随机字串 
        String verifyCode = String.valueOf(1000 + new Random().nextInt(9000));
        //存入会话session 
        //生成图片 
        int w = 100, h = 40;
        try {
            String code = VerifyImageUtils.getBase64Image(w, h, verifyCode);
            //JedisCluster cluster = RedisFactory.getJedisCluster();
            RedisUtils.set(token + PTConst.VERIFY_IMG, verifyCode);
            RedisUtils.expire(token + PTConst.VERIFY_IMG, Integer.parseInt(RedisUtils.getRedisPriperty(PTConst.IMG_VERIFY_EXPIRE)));
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("verifycode", code);
            resultMap.put("key", token + PTConst.VERIFY_IMG);
            //方便测试用,暂时添加
            resultMap.put("valueCode", verifyCode);
            return this.getSuccessResponse(resultMap);
            /*return JsonUtils.getJsonString(0, "成功", resultMap);*/
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return this.getFailResponse("系统异常");
            /* return JsonUtils.getJsonString(0, "系统异常", null);*/
        }
    }

    /**
     * 用户校验验证码
     *
     * @param verifyImageDto
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/verify")
    @ResponseBody
    public String verifyImage(@RequestBody VerifyImageDto verifyImageDto, HttpServletRequest request, HttpServletResponse response) {
        String result = null;
        if (StringUtils.isEmpty(verifyImageDto.getIdentifycode())) {
            return JsonUtils.getJsonString(-2, "失败", null);
        }
        String token = request.getHeader(PTConst.USER_TOKEN);
        //JedisCluster cluster = RedisFactory.getJedisCluster();
        String code = RedisUtils.get(token + PTConst.VERIFY_IMG);
        if (verifyImageDto.getIdentifycode().equals(code)) {
            result = JsonUtils.getJsonString(0, "成功", null);
        } else {
            result = JsonUtils.getJsonString(-2, "验证码输入有误", null);
        }
        return result;
    }
}
