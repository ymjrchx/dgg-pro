package net.dgg.gspt.dqc.controller.search;

import net.dgg.gspt.dqc.constant.LayStatusConstant;
import net.dgg.gspt.dqc.dto.brandsearch.ApproParam;
import net.dgg.gspt.dqc.framework.PTConst;
import net.dgg.gspt.dqc.framework.interceptor.ConstMethod;
import net.dgg.gspt.dqc.services.search.SearchService;
import net.dgg.gspt.dqc.utils.es.EsConst;
import net.dgg.gspt.dqc.utils.es.services.EsService;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: SearchExtensionController
 * @Description: 商标搜索扩展功能
 * @Author: jiangsh
 * @Date: 2018/11/21 15:11
 */
@RestController
@RequestMapping("/searchExtension")
public class SearchExtensionController extends BaseController implements ConstMethod {

    Logger logger = LoggerFactory.getLogger(SearchExtensionController.class);

    @Resource
    private EsService esService;
    @Resource
    private SearchService searchService;

    /**
     * 根据关键字搜索，统计商标申请情况，如不同状态等等
     */
    @RequestMapping(value = "/aggStatus", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse aggStatus(@RequestBody ApproParam ap) {
        if (StringUtils.isNotEmpty(ap.getKeyWord())) {
            try {
                return getSuccessResponse(searchService.dealAggStatus(
                        esService.filterAggregationCount(
                                BrandSearchController.filterAggregation(LayStatusConstant.extenAggCondition(), PTConst.BRAND_STATE, true),
                                true, BrandSearchController.mapConditions(ap), new HashMap<>(), EsConst.INDEX_BRAND, EsConst.TYPE_COMPANY_BRAND, ap)));
            } catch (Exception e) {
                return getFailResponse("系统异常！");
            }
        } else return getFailResponse("搜索关键字参数为空");
    }

    /**
     * 商标寓意查询
     */
    @RequestMapping(value = "/brandMoral", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse brandMoral(@RequestParam String name) {
        try {
            return getSuccessResponse(esService.getMsgByKeyWordCommon(PTConst.BRAND_MORAL_KEY, name, EsConst.INDEX_BRAND_YY, EsConst.TYPE_COMPANY_BRAND_YY));
        } catch (Exception e) {
            return getFailResponse("系统异常！");
        }
    }

    /**
     * 获取侧边栏前五“购买”信息
     */
    @RequestMapping(value = "/topFive", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse topFive(@RequestBody ApproParam ap) {
        Map<String, Object> dataMap = new HashMap<>();
        final String[] include = BrandSearchController.getSearchListColumn();
        final String[] exclude = {""};
        Map<String, String> condition = new HashMap<>(); //关键字搜索
        if (ap.getKey().equals(PTConst.KEY_ITEM)) condition.put(PTConst.BRAND_NAME, ap.getKeyWord());
        else condition.put(ap.getKey(), ap.getKeyWord());

        dataMap.put("imgPrefix", searchService.getFileUrlPrefix());
        dataMap.put("topFive", searchService.converFiveListMap(
                esService.getListMapBrand(
                        condition, 1, 20, BrandSearchController.mapConditions(ap), EsConst.INDEX_BRAND, EsConst.TYPE_COMPANY_BRAND, true, include, exclude, ap)));
        return getSuccessResponse(dataMap);
    }

}




























