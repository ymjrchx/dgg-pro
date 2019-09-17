package net.dgg.gspt.dqc.controller.search;

import net.dgg.gspt.dqc.dto.patentsearch.PatentParam;
import net.dgg.gspt.dqc.framework.PTConst;
import net.dgg.gspt.dqc.framework.interceptor.ConstMethod;
import net.dgg.gspt.dqc.framework.redis.RedisUtils;
import net.dgg.gspt.dqc.utils.es.EsConst;
import net.dgg.gspt.dqc.utils.es.services.EsService;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PatentSearchController
 * @Description: 首页专利搜索
 * @Author: jiangsh
 * @Date: 2018/9/25 15:55
 */

@RestController
@RequestMapping("/patentSearch")
public class PatentSearchController extends BaseController implements ConstMethod {

    Logger logger = LoggerFactory.getLogger(PatentSearchController.class);

    @Resource
    private EsService esService;

    /**
     * 获取搜索关键字
     */
    @RequestMapping(value = "/correlative", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse correlative(@RequestParam String keyWord, HttpServletRequest request) {
        return getSuccessResponse(esService.getGlc(EsConst.INDEX_GLC, EsConst.TYPE_COMPANY_GLC, "word", keyWord, 1));
    }

    /**
     * 专利搜索列表
     * pageIndex、pageSize、keyWord必传
     */
    @RequestMapping(value = "/searchList", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse search(@RequestBody PatentParam ap, HttpServletRequest request) {
        String token = request.getHeader(PTConst.USER_TOKEN);
        if (StringUtils.isEmpty(token) || !RedisUtils.exists(token)) {
            return this.getFailResponse("请先登录！");
        }
//        logger.info("==========================" + JSON.toJSONString(ap));
        if (ap.getPageIndex() > 1) Assert.isTrue(justLogin(request), PTConst.LOGIN_ONE);

        Map<String, Object> dataMap = pages(ap.getPageIndex(), ap.getPageSize());
        final int startCount = Integer.valueOf(dataMap.get("startCount").toString());
        final int endCount = Integer.valueOf(dataMap.get("endCount").toString());

        try {
            putDataMap(startCount, endCount, dataMap, getConditionPatent(ap), CompanyItemController.mixMap(PTConst.MIX_PATENT, ap.getKeyWord()));
        } catch (Exception e) {
            return getFailResponse(e.getMessage());
        }
        return getSuccessResponse(dataMap);
    }

    /**
     * 除 keyWord 的条件封装
     */
    private Map<String, String> getConditionPatent(PatentParam ap) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotEmpty(ap.getType())) //专利类别
            map.put(PTConst.INDEX_ZLLB, ap.getType());

        if (StringUtils.isNotEmpty(ap.getLayStatus())) //法律状态
            map.put(PTConst.INDEX_FLZT, ap.getLayStatus());

        if (StringUtils.isNotEmpty(ap.getDateType()) && StringUtils.isNotEmpty(ap.getDataStart()) && StringUtils.isNotEmpty(ap.getDataEnd())) { //日期查询
            map.put(PTConst.PATENT_DATE_TYPE, ap.getDateType());
            map.put(PTConst.PATENT_DATE_START, ap.getDataStart());
            map.put(PTConst.PATENT_DATE_END, ap.getDataEnd());
        }

        if (StringUtils.isNotEmpty(ap.getApplyPerson()))
            map.put(PTConst.INDEX_ZLFMR, ap.getApplyPerson());

        if (StringUtils.isNotEmpty(ap.getApplyDate()))
            map.put(PTConst.PATENT_APPLICATION_DATE, ap.getApplyDate());

//        if (StringUtils.isNotEmpty(ap.getTypeNum()))
//            map.put(PTConst.INDEX_TYPE_NUM, ap.getTypeNum());

        return map;
    }

    /**
     * 获取专利搜索-数据列表
     *
     * @param startCount 开始条数
     * @param endCount   结束条数
     * @param dataMap    响应数据集
     * @param map        除 keyWord 的条件封装
     * @param keyMap     keyWord条件集
     */
    private void putDataMap(int startCount, int endCount, Map<String, Object> dataMap, Map<String, String> map, Map<String, String> keyMap) throws Exception {
        Assert.isTrue((map.size() > 0 && keyMap.size() > 0), PTConst.INPUT_PARAMS);

        final String[] include = getSearchListColumn();
        final String[] exclude = {""};
        dataMap.put("listData", esService.getListMapPatent(startCount, endCount, map, keyMap, EsConst.INDEX_PATENT, EsConst.TYPE_COMPANY_PATENT, include, exclude, null));
        dataMap.put("totalCount", esService.getTotalCountPatent(map, keyMap, EsConst.INDEX_PATENT, EsConst.TYPE_COMPANY_PATENT));
    }

    /**
     * 专利详情
     *
     * @param key 传值情况： 专利 applicationNumber;
     * @param val 传值情况： 即为列表中上述key对应的val值;
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse detail(@RequestParam String key,
                               @RequestParam String val,
                               HttpServletRequest request) {

//        Assert.isTrue(justLogin(request), PTConst.LOGIN_TWO);
        List<Map> dataList = esService.getMsgByKeyWordCommon(key, val, EsConst.INDEX_PATENT, EsConst.TYPE_COMPANY_PATENT);
        if (dataList != null && dataList.size() > 0) {
            return getSuccessResponse(dataList);
        } else return getFailResponse(PTConst.PROMPT);
    }


    /**
     * 专利搜索 列表展示列
     *
     * @return
     */
    private String[] getSearchListColumn() {
        final String[] columns = {"applicationNumber", "abstracts", "piInventName", "legalStatusDesc",
                "inventorString", "applicationDate", "publicationNumber", "piClassifyNum", "iPCDesc", "kindCodeDesc", "keyWord", "publicationDate", "assigneestring"};
        return columns;
    }


}
