package net.dgg.zqq.controller.predicate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.dgg.zqq.services.common.DatabaseService;
import net.dgg.zqq.services.predicate.BrandPredicateService;
import net.dgg.zqq.utils.ThreadHelper;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.util.List;
import java.util.Map;

/**
 * Created by 李程 on 2018/10/16.
 */
@Api(value = "通过率预测", description = "通过率预测接口")
@Slf4j
@RestController
@RequestMapping("/predicate")
public class RegisterPassingRateController extends BaseController {

    @Autowired
    BrandPredicateService brandPredicateService;

    @Autowired
    DatabaseService db;

    @ApiOperation(value = "商标预测", notes = "根据商标名称预测通过率", httpMethod = "POST")
    @RequestMapping(value = "/registerPassingRate", method = RequestMethod.POST)
    public RestResponse predicateBrandPassRate(
            @ApiParam(name = "brand", required = true, value = "商标") @RequestParam(name = "brand") String brand,
            @ApiParam(name = "industry", value = "行业") @RequestParam(name = "industry", required = false) String industry,
            @ApiParam(name = "type", value = "大类") @RequestParam(name = "type", required = false) String type
    ) {
        ThreadHelper.putThreadContextVar("industry", industry);
        ThreadHelper.putThreadContextVar("type", type);

        if (StringUtils.isNotEmpty(industry) && !industry.matches("\\d+")) {
            return this.getFailResponse("行业字段不为数字");
        } else if (StringUtils.isEmpty(brand)) {
            return this.getFailResponse("商标名称不能为空");
        }
        long start = Clock.systemUTC().millis();
        try {
            Map<String, Object> predicateRs = brandPredicateService.predicateByName(brand);
            Long end = Clock.systemUTC().millis();
            log.debug("预测方法总共耗时：" + (end - start) / 1000D + "秒");
            return this.getSuccessResponse(predicateRs);
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("错误");
        }
    }

    @ApiOperation(value = "行业列表", notes = "所有行业列表", httpMethod = "POST")
    @RequestMapping(value = "/allIndustries", method = RequestMethod.POST)
    public RestResponse predicateBrandPassRate() {
        return this.getSuccessResponse(brandPredicateService.queryAllIndustries());
    }


    @ApiOperation(value = "分类列表", notes = "所有分类列表", httpMethod = "POST")
    @RequestMapping(value = "/allTypes", method = RequestMethod.POST)
    public RestResponse types() {
        List<Map<String, Object>> cates = db.query("zqq_class_first", null);
        return this.getSuccessResponse(cates);
    }

    @ApiOperation(value = "查询预测提示信息", notes = "查询预测时提示信息", httpMethod = "POST")
    @RequestMapping(value = "/queryMessages", method = {RequestMethod.POST})
    public RestResponse queryMessages(
            @ApiParam(name = "brand", required = true, value = "商标") @RequestParam(name = "brand") String brand,
            @ApiParam(name = "industry", value = "行业") @RequestParam(name = "industry", required = false) String industry,
            @ApiParam(name = "type", value = "大类") @RequestParam(name = "type", required = false) String type
    ) {
        ThreadHelper.putThreadContextVar("type", type);
        ThreadHelper.putThreadContextVar("industry", industry);
        return this.getSuccessResponse(brandPredicateService.getMessage(brand));
    }


}
