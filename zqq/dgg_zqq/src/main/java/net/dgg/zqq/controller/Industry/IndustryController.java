package net.dgg.zqq.controller.Industry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.zqq.services.industry.IndustryService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <IndustryController>
 * @despriction
 * @create 2018/10/11 15:36
 **/
@Controller
@RequestMapping("/industry")
@Api(description = "商标分类智能推荐")
public class IndustryController extends BaseController {
    @Autowired
    private IndustryService industryService;

    /**
     * 查询商标选择的领域
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/queryDomain", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询所在领域", notes = "查询所在领域", httpMethod = "GET")
    public Object queryDomain(@ApiParam(name = "userId", value = "用户id", required = false) @RequestParam(required = false) String userId) {
        try {
            List<Map> map = industryService.queryDomain(userId);
            return this.getSuccessResponse(map);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    /**
     * 根据领域小类id查询出推荐的商品信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryCommodity", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据领域小类查询出推荐的商品信息", notes = "根据领域小类查询出推荐的商品信息", httpMethod = "GET")
    public Object queryCommodity(@ApiParam(name = "id", value = "领域小类id", required = true) @RequestParam Long id) {
        try {
            List<Map> map = industryService.queryCommodity(id);
            return this.getSuccessResponse(map);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }
}
