package net.dgg.bigdata.sjjs.web.condition.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.dgg.bigdata.sjjs.web.condition.service.ClueService;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/21 10:37
 * @Description:
 */
@RestController
@RequestMapping("/clueApi/")
@Api(description = "线索接口")
public class ClueApiController extends DggBaseController {

    @Autowired
    private ClueService clueService;

    private static final Logger logger = LoggerFactory.getLogger(DggBaseController.class);


    @ApiOperation("转线索接口")
    @PostMapping("turnClue.do")
    public DggRestResponse turnClue(@RequestParam String cluesJson, HttpServletRequest request) {
        // 如果转线索条数超过1000条则直接返回失败
        try {
            String[] clues = JSON.parseObject(cluesJson, String[].class);
            clueService.doClue(clues, request);
            return this.getSuccessResponse(null);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return this.getFailResponse(e.getMessage());
        }

    }

}
