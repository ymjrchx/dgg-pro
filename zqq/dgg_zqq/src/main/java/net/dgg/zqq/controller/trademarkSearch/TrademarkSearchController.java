package net.dgg.zqq.controller.trademarkSearch;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.zqq.services.TrademarkSearch.TrademarkSearchService;
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
 * @ClassName <TrademarkSearch>
 * @despriction
 * @create 2018/10/12 10:12
 * 商标自助选择数据和搜索
 **/
@Controller
@RequestMapping("/industry")
@Api(description = "商标自助选择/搜索")
public class TrademarkSearchController extends BaseController {

    @Autowired
    private TrademarkSearchService trademarkSearchService;

    /**
     * 查询商标选择的领域
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/queryTrademarkList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商标45大类", notes = "查询商标45大类", httpMethod = "GET")
    public Object queryTrademarkList(@ApiParam(name = "userId", value = "用户id", required = false) @RequestParam(required = false) String userId) {
        try {
            List<Map> map = trademarkSearchService.queryTrademarkList(userId);
            return this.getSuccessResponse(map);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/queryTrademarkParticularList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "点击一级/二级类别查询下级商标数据(展开全部子项)", notes = "点击一级/二级类别查询下级商标数据", httpMethod = "GET")
    public Object queryTrademarkParticularList(@ApiParam(name = "userId", value = "用户id", required = false) @RequestParam(required = false) String userId,
                                               @ApiParam(name = "number", value = "类别编号", required = true) @RequestParam String number,
                                               @ApiParam(name = "numberType", value = "类别编号的类型(two:二级/three：三级)", required = true) @RequestParam String numberType) {
        try {
            List<Map> map = trademarkSearchService.queryTrademarkParticularList(userId, number, numberType);
            return this.getSuccessResponse(map);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    @RequestMapping(value = "/queryTrademarkSearchList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "模糊查询商品/服务名称", notes = "模糊查询商品/服务名称", httpMethod = "GET")
    public Object queryTrademarkSearchList(@ApiParam(name = "userId", value = "用户id", required = false) @RequestParam(required = false) String userId,
                                           @ApiParam(name = "searcKey", value = "搜索条件", required = true) @RequestParam String searcKey) {
        try {
            List<Map> map = trademarkSearchService.queryTrademarkSearchList(userId, searcKey);
            return this.getSuccessResponse(map);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

}