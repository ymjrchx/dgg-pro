package net.dgg.bigdata.sjjs.web.controller.sysUser;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.bigdata.common.constant.PTConst;
import net.dgg.core.redis.DggRedisService;
import net.dgg.core.utils.VerifyImageUtils;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 图片验证码控制器
 */

@Controller
@RequestMapping("/imgCode")
public class VerifyImageController extends DggBaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final static int imgTime = 300;  //过期时间 5分钟

    /**
     * 获取图片验证码
     *
     * @param
     * @param request
     */
    @ApiOperation(value = "获取图片验证码")
    @RequestMapping(value = "/send", method = RequestMethod.GET)
    @ResponseBody
    public DggRestResponse send(HttpServletRequest request) {
        String token = request.getHeader(PTConst.USER_TOKEN);
        log.info("imgtoken:" + token);
        //生成随机字串
        String verifyCode = String.valueOf(1000 + new Random().nextInt(9000));
        //生成图片
        int w = 100, h = 40;
        try {
            String code = VerifyImageUtils.getBase64Image(w, h, verifyCode);
            //将验证码存入redis
            String key = token + PTConst.VERIFY_IMG;
            if (DggRedisService.exists(key)) {
                DggRedisService.del(key);
            }
            DggRedisService.set(key, verifyCode);
            //设置过期时间
            DggRedisService.expire(key, imgTime);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("verifycode", code);
            resultMap.put("key", token + PTConst.VERIFY_IMG);
            /*//方便测试用,暂时添加
            resultMap.put("valueCode", verifyCode);*/
            return this.getSuccessResponse(resultMap);

        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }
    }


    /**
     * 校验图片验证码
     *
     * @param code
     * @param request
     * @return
     */
    @ApiOperation(value = "校验图片验证码")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    public DggRestResponse check(@ApiParam(name = "code", value = "图片验证码") String code, HttpServletRequest request) {
        try {
            Assert.hasText(code, "code不能为空");
            String token = request.getHeader(PTConst.USER_TOKEN);
            String key = token + PTConst.VERIFY_IMG;
            String value = DggRedisService.get(key);
            Assert.hasText(value, "请获取图片验证码");
            Assert.isTrue(code.equals(value), "验证码输入不正确");
            return this.getSuccessResponse("图片验证通过");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }
    }

}