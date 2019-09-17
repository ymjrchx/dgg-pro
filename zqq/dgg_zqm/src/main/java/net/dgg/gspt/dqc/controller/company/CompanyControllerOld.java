package net.dgg.gspt.dqc.controller.company;

import net.dgg.gspt.dqc.entity.business.treebook.TreeBook;
import net.dgg.gspt.dqc.framework.PTConst;
import net.dgg.gspt.dqc.framework.PTConstOld;
import net.dgg.gspt.dqc.framework.interceptor.ConstMethod;
import net.dgg.gspt.dqc.framework.redis.RedisUtils;
import net.dgg.gspt.dqc.params.DetailParam;
import net.dgg.gspt.dqc.params.ItemParam;
import net.dgg.gspt.dqc.params.QueryParam;
import net.dgg.gspt.dqc.services.TreeBookService;
import net.dgg.gspt.dqc.utils.HidePhoneUtils;
import net.dgg.gspt.dqc.utils.esOld.EsConst;
import net.dgg.gspt.dqc.utils.esOld.EsUtils;
import net.dgg.gspt.dqc.utils.esOld.services.EsServiceOld;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filters.FiltersAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: CompanyController
 * @Description: 企业信息调用查询接口
 * @Author: jiangsh
 * @Date: 2018/9/5 20:34
 */

@RestController
@RequestMapping("/company")
public class CompanyControllerOld extends BaseController implements ConstMethod {

    Logger logger = LoggerFactory.getLogger(CompanyControllerOld.class);

    @Autowired
    private EsServiceOld esService;

    @Resource
    private TreeBookService treeBookService;

    /**
     * 获取前五条数据
     *
     * @param keyWord 关键字
     * @param count   条数
     * @param type    类型
     * @return
     */
    @RequestMapping(value = "/searchFive", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse searchFive(@RequestParam String keyWord
                                 , @RequestParam int count
                                 , @RequestParam String type
    ) {
        initTree();
        if (justKeyWord(keyWord)) return getFailResponse(PTConstOld.PROMPT_MORE);
        if (count <=0) count = 5;
        Map<String, String> m = putAllCondition(keyWord, type);
        String[] include = new String[m.size() + 2];
        include[0] = PTConstOld.INDEX_NAME;
        include[1] = PTConstOld.COMPANY_ID;
        if (m.size() > 0) {
            int i = 2;
            for (Map.Entry<String, String> entry : m.entrySet()) {
                include[i] = entry.getKey();
                i++;
            }
        }
        List<Map> dataList = esService.getPart(EsConst.INDEX, EsConst.TYPE_COMPANY, keyWord, count, include, m, PTConstOld.QYGS_REGISTCAPI);
        if (dataList != null && dataList.size() > 0)
            return getSuccessResponse(dataList);
        else return getFailResponse(PTConstOld.PROMPT);
    }

    /**
     * 注册用户根据该接口查询企业列表
     * pageIndex、pageSize、keyWord、type必传
     */
    @RequestMapping(value = "/searchList", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse search(@RequestBody QueryParam sqp, HttpServletRequest request) {
        String token = request.getHeader(PTConst.USER_TOKEN);
        if (StringUtils.isEmpty(token) || !RedisUtils.exists(token)) {
            return this.getFailResponse("请先登录！");
        }
        if (justKeyWord(sqp.getKeyWord())) return getFailResponse(PTConstOld.PROMPT_MORE);
//        logger.info("============searchList===========" + JSON.toJSONString(sqp));
        Map<String, Object> dataMap = pages(sqp.getPageIndex(), sqp.getPageSize());
        final int startCount = Integer.valueOf(dataMap.get("startCount").toString());
        final int endCount = Integer.valueOf(dataMap.get("endCount").toString());
        Map<String, String> condition = getContition(sqp);
        putDataMap(condition, startCount, endCount, dataMap, mapConditions(sqp), justLogin(request), PTConstOld.F_TRUE); //isphrase设置全局map多条件匹配模式
        List<Map> dataList = (List<Map>) dataMap.get("listData");
        if (dataList != null && dataList.size() > 0)
            return getSuccessResponse(dataMap);
        else return getFailResponse(PTConstOld.PROMPT);
    }

    /**
     * 列表页面 查询数据字典
     */
    @RequestMapping(value = "/searchTreeBook", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Map<String, List<TreeBook>>> searchTreeBook2(@RequestBody QueryParam sqp) throws Exception {
        final boolean keyFlag = justKeyWord(sqp.getKeyWord());
//        logger.info("============searchTreeBook===========" + JSON.toJSONString(sqp));
        Map<String, String> condition = getContition(sqp);
        Map<String, String> map = mapConditions(sqp);
        List<String> codes = treeBookService.getCodeByPid(PTConstOld.TREEBOOK_ROOT_PID);
        Map<String, List<TreeBook>> dataMap = new HashMap<>();
        if (codes != null && codes.size() > 0) {
            Map<String, String> tmpMap = new HashMap<>();
            for (String code : codes) {
                switch (code) {
                    case PTConstOld.CODE_QXFW: //查找范围
                        dataMap.put(PTConstOld.CODE_QXFW, treeBookService.getNameAndCodeByCode(code));
                        break;

                    case PTConstOld.CODE_QY_TYPE: //企业类型
                        dataMap.put(PTConstOld.CODE_QY_TYPE, treeBookService.getNameAndCodeByCode(code));
                        break;

                    case PTConstOld.CODE_QY_STATUS: //企业状态
                        List<TreeBook> status = treeBookService.getNameAndCodeByCode(code);
                        dataMap.put(PTConstOld.CODE_QY_STATUS, keyFlag ? status :
                                result(status, resultCountMap(condition, status, PTConstOld.QYGS_STATUS, map, tmpMap, PTConstOld.F_TRUE, PTConstOld.F_TRUE, PTConstOld.F_FALSE), PTConstOld.F_FALSE));
                        break;

                    case PTConstOld.CODE_ZCZB: //注册资本
                        dataMap.put(PTConstOld.CODE_ZCZB, treeBookService.getNameAndCodeByCode(code));
                        break;

                    case PTConstOld.CODE_CL_DATE: //成立日期
                        List<TreeBook> date = treeBookService.getNameAndCodeByCode(code);
                        dataMap.put(PTConstOld.CODE_CL_DATE, keyFlag ? date :
                                result(date, resultCountMap(condition, date, PTConstOld.QYGS_STARTDATE, map, tmpMap, PTConstOld.F_TRUE, PTConstOld.F_TRUE, PTConstOld.F_FALSE), PTConstOld.F_FALSE));
                        break;

                    case PTConstOld.CODE_PROVINCE: //省份
                        List<TreeBook> province = dealProvince(sqp, code);
                        dataMap.put("p".concat(PTConstOld.CODE_PROVINCE), keyFlag ? province : (province == null ? null :
                                result(province, resultCountMap(condition, province, PTConstOld.QYGS_PROVINCE, map, tmpMap, PTConstOld.F_TRUE, PTConstOld.F_TRUE, PTConstOld.F_FALSE), PTConstOld.F_FALSE)));
                        break;

                    case PTConstOld.CODE_INDUSTRY: //行业名称
                        List<TreeBook> industry = null;
                        if (StringUtils.isNotEmpty(sqp.getIndCode())) code = sqp.getIndCode();
                        if (!sqp.getIsLeafInd().equals("0")) industry = treeBookService.getNameAndCodeByCode(code);
                        dataMap.put(PTConstOld.CODE_INDUSTRY, keyFlag ? industry : (industry == null ? null :
                                result(industry, resultCountMap(condition, industry, PTConstOld.QYGS_INDUSTRY, map, tmpMap, PTConstOld.F_TRUE, PTConstOld.F_TRUE, PTConstOld.F_FALSE), PTConstOld.F_FALSE)));
                        break;

                    default:
                        break;
                }
            }
        }

        if (dataMap.size() > 0)
            return getSuccessResponse(dataMap);
        else
            return getFailResponse(PTConstOld.PROMPT);
    }

    /**
     * 处理省份
     * @param sqp 查询参数
     * @param code 数据库中code
     * @return 数据字典省份树集
     */
    private List<TreeBook> dealProvince(QueryParam sqp, String code) {
        if (sqp.getIsLeafPro().equals("0")) return null;
        if (StringUtils.isNotEmpty(sqp.getProCode())) code = sqp.getProCode();
        List<TreeBook> province = treeBookService.getNameAndCodeByCode(code);
        if (province != null && province.size() > 0)
            for (TreeBook t : province)
                t.setName(t.getName().replace("省", "").replace("市", "")
                        .replace("区", "").replace("县", ""));
        return province;
    }

    /**
     * 处理字典响应结果
     * @param list 数据集
     * @param map 数据字典查询条件
     * @param isCode 是否是code
     * @return 数据字典树集
     */
    private List<TreeBook> result(List<TreeBook> list, Map<String, Long> map, boolean isCode) {
//        logger.info("==================result map=========" + JSON.toJSONString(map));
        if (list != null && list.size() > 0 && map != null && map.size() > 0) {
            for (TreeBook tb : list)
                for (Map.Entry<String, Long> entry : map.entrySet())
                    if (!isCode)
                        if (tb.getName().equals(entry.getKey()))
                            tb.setExt1(String.valueOf(entry.getValue()));
                        else if (tb.getCode().equals(entry.getKey()))
                            tb.setExt1(String.valueOf(entry.getValue()));
            return list;
        } else return null;
    }

    /**
     * 获取数据字典选项数量结果集
     * @param in 判断聚合内qb匹配模式
     * @param out 判断聚合外条件匹配模式
     * @throws Exception
     */
    private Map<String, Long> resultCountMap(Map<String, String> con, List<TreeBook> list, String key,
                                             Map<String, String> map, Map<String, String> tmpMap, boolean in, boolean out, boolean isCode) throws Exception {
        List<String> values = new ArrayList<>();
        for (TreeBook t : list)
            if (!isCode) values.add(t.getName());
            else values.add(t.getCode());
        return esService.filterAggregationCount(filterAggregation(values, key, in), out, con, map, tmpMap, EsConst.INDEX, EsConst.TYPE_COMPANY, PTConstOld.AGGREATE_LIST);
    }

    /**
     * 分组过滤
     * @throws Exception
     */
    private AggregationBuilder filterAggregation(List<String> values, String key, boolean in) throws Exception {
        if (values != null && values.size() > 0) {
            FiltersAggregator.KeyedFilter[] filters = new FiltersAggregator.KeyedFilter[values.size()];
            int i = 0;
            for (String val : values) {
//                logger.info(key + "===================" + val);
                BoolQueryBuilder query = QueryBuilders.boolQuery();
                if (key.equals(PTConstOld.QYGS_STARTDATE)) EsUtils.startDate(val, query, PTConstOld.QYGS_STARTDATE);
                else if (key.equals(PTConstOld.QYGS_REGISTCAPI)) EsUtils.dealRegCapi(val, query);
                else if (in) query.must(QueryBuilders.matchPhraseQuery(key, val));
                else query.must(QueryBuilders.matchQuery(key, val));
                filters[i] = new FiltersAggregator.KeyedFilter(val, query);
                i++;
            }
            return AggregationBuilders.filters(PTConstOld.AGGREATE_LIST, filters);
        } else return null;
    }

    /**
     * 数据字典 拦截组装请求参数
     */
    private Map<String, String> mapConditions(QueryParam sqp) {
        Map<String, String> condition = new HashMap<>();
        if (StringUtils.isNotEmpty(sqp.getEconKind()))
            condition.put(PTConstOld.QYGS_ENCOKIND, sqp.getEconKind());

        if (StringUtils.isNotEmpty(sqp.getStatus()))
            condition.put(PTConstOld.QYGS_STATUS, sqp.getStatus());

        if (StringUtils.isNotEmpty(sqp.getRegistCapi()))
            condition.put(PTConstOld.QYGS_REGISTCAPI, sqp.getRegistCapi().replace("zczb", ""));

        if (StringUtils.isNotEmpty(sqp.getStartDate()))
            condition.put(PTConstOld.QYGS_STARTDATE, sqp.getStartDate());

        if (StringUtils.isNotEmpty(sqp.getProvince()))
            condition.put(PTConstOld.QYGS_PROVINCE, sqp.getProvince());

        if (StringUtils.isNotEmpty(sqp.getIndustry()))
            condition.put(PTConstOld.QYGS_INDUSTRY, sqp.getIndustry());

        return condition;
    }

    /**
     * 列表 详情
     * @return
     */
    @RequestMapping(value = "/searchDetail", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse detail(@RequestBody DetailParam detailParam, HttpServletRequest request) {
        List<Map> dataList = esService.getMsgByKeyWord(PTConstOld.COMPANY_ID, detailParam.getCompanyId(), EsConst.TYPE_COMPANY);
        if (dataList != null && dataList.size() > 0) {
            if (!justLogin(request)) { //未登录状态
                HidePhoneUtils.hidePhone(getColumns(), dataList);
            }

            final String[] exclude = {"", ""};
            Map<String, String> m = new HashMap<>();
            m.put("applicantCn", detailParam.getCompanyName());
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("list", esService.getListMap(EsConst.INDEX_BRAND, EsConst.TYPE_COMPANY_BRAND, m, 1, 10, includeBrand(), exclude, null));
            dataMap.put("total", esService.getTotalCount(EsConst.INDEX_BRAND, EsConst.TYPE_COMPANY_BRAND, m));

            List<Map> dataMapList = (List<Map>) dataMap.get("list");
            if (dataMapList.size() > 0) dataList.get(0).put("brandData", dataMap);

            return getSuccessResponse(dataList);
        } else return getFailResponse(PTConstOld.PROMPT);
    }

    /**
     * 列表 详情，未包含商标信息
     *
     * @return
     */
    @RequestMapping(value = "/searchSimpleDetail", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse searchSimpleDetail(@RequestParam String companyId, HttpServletRequest request) {
        List<Map> dataList = esService.getMsgByKeyWord(PTConstOld.COMPANY_ID, companyId, EsConst.TYPE_COMPANY);
        if (dataList != null && dataList.size() > 0) {
            if (!justLogin(request)) { //未登录状态
                HidePhoneUtils.hidePhone(getColumns(), dataList);
            }
            return getSuccessResponse(dataList);
        } else return getFailResponse(PTConstOld.PROMPT);
    }


    /**
     * 单独获取商标信息,（用于在查看详情时，获取商标信息）
     * 注意 ip.getKeyWord() 的值传 公司名称
     */
    @RequestMapping(value = "/brandMsg", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse brandMsg(@RequestBody ItemParam ip) {

        Map<String, Object> dataMap = pages(ip.getPageIndex(), ip.getPageSize());
        final int startCount = Integer.valueOf(dataMap.get("startCount").toString());
        final int endCount = Integer.valueOf(dataMap.get("endCount").toString());

        final String[] exclude = {"", ""};
        Map<String, String> m = new HashMap<>();
        m.put("applicantCn", ip.getKeyWord());
        List<Map> list = esService.getListMap(EsConst.INDEX_BRAND, EsConst.TYPE_COMPANY_BRAND, m, startCount, endCount, includeBrand(), exclude, null);
        dataMap.put("list", list);
        dataMap.put("total", esService.getTotalCount(EsConst.INDEX_BRAND, EsConst.TYPE_COMPANY_BRAND, m));
        List<Map> dataList = (List<Map>) dataMap.get("list");
        if (dataList != null && dataList.size() > 0)
            return getSuccessResponse(dataMap);
        else
            return getFailResponse(PTConstOld.PROMPT);
    }

    private String[] includeBrand() {
        return new String[]{"name", "imageUrl", "regNo", "appDate", "intCls", "applicantCn", "status", "url"};
    }


    public static Map<String, String> putAllCondition(String keyWord, String type) {
        Map<String, String> condition = new HashMap<>();
        switch (type) {
            case "all":
                condition.put(PTConstOld.INDEX_NAME, keyWord);
                condition.put(PTConstOld.INDEX_OPER_NAME, keyWord);
                condition.put(PTConstOld.INDEX_STOCK_NAME, keyWord);
                condition.put(PTConstOld.INDEX_GG_NAME, keyWord);
                condition.put(PTConstOld.INDEX_ADDRESS, keyWord);
                condition.put(PTConstOld.INDEX_TEL, keyWord);
                condition.put(PTConstOld.INDEX_EMAIL, keyWord);
                condition.put(PTConstOld.INDEX_WEBSITE_NAME, keyWord);
                condition.put(PTConstOld.INDEX_BUSI_SCOPE, keyWord);
                condition.put(PTConstOld.INDEX_PRODUCT_NAME, keyWord);
                break;

            case "qyn":
                condition.put(PTConstOld.INDEX_NAME, keyWord);
                break;

            case "fr": //法人
                condition.put(PTConstOld.INDEX_OPER_NAME, keyWord);
                condition.put(PTConstOld.INDEX_STOCK_NAME, keyWord);
                break;

            case "gg": //高管
                condition.put(PTConstOld.INDEX_GG_NAME, keyWord);
                break;

            case "pp": //品牌/产品
                condition.put(PTConstOld.INDEX_PRODUCT_NAME, keyWord);
                break;

            case "ap": //地址/电话、邮箱、网址
                condition.put(PTConstOld.INDEX_ADDRESS, keyWord);
                condition.put(PTConstOld.INDEX_TEL, keyWord);
                condition.put(PTConstOld.INDEX_EMAIL, keyWord);
                condition.put(PTConstOld.INDEX_WEBSITE_NAME, keyWord);
                break;

            case "bs": //经营范围
                condition.put(PTConstOld.INDEX_BUSI_SCOPE, keyWord);
                break;

            default:
                condition = new HashMap<>();
                break;
        }
        return condition;
    }

    private void putDataMap(Map<String, String> condition, int startCount, int endCount, Map<String, Object> dataMap, Map<String, String> map, boolean status, boolean isphrase) {
        final String[] include = getSearchListColumnOld();
        final String[] exclude = {""};
        dataMap.put("listData", justList(status,
                esService.getListMapByIndex(condition, startCount, endCount, map, EsConst.TYPE_COMPANY, isphrase, include, exclude)));
        dataMap.put("totalCount", esService.getTotalCountIndex(condition, map, EsConst.TYPE_COMPANY, isphrase, include, exclude));
    }

    /**
     * 获取补充数据（如商标等等）
     * @param companyId 公司id
     * @return
     */
    @RequestMapping(value = "/sbDetail", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse sbDetail(@RequestParam String companyId) {
        List<Map> dataList = esService.getMsgByKeyWord(PTConstOld.COMPANY_ID, companyId, EsConst.TYPE_COMPANY_BRAND);
        if (dataList != null && dataList.size() > 0)
            return getSuccessResponse(dataList);
        else return getFailResponse(PTConstOld.PROMPT);
    }

    /**
     * 初始化大区域名称至redis
     */
    private void initTree() {
        if (!RedisUtils.exists(PTConstOld.TREE_KEY)) {
            List<String> treeList = treeBookService.getChildNameByCode(PTConstOld.CODE_PROVINCE);
            if (treeList != null && treeList.size() > 0) {
                StringBuffer sb = new StringBuffer();
                for (String s : treeList)
                    sb.append(s).append("-");
                RedisUtils.set(PTConstOld.TREE_KEY, sb.toString());
                RedisUtils.expire(PTConstOld.TREE_KEY, Integer.parseInt(RedisUtils.getRedisPriperty(PTConstOld.TRKKKEY_EXPIRE)));
            }
        }
    }

    /**
     * 判断是否存在大区域名称
     */
    private boolean justKeyWord(String keyWord) {
        if (StringUtils.isNotEmpty(keyWord)) {
            if (!RedisUtils.exists(PTConstOld.TREE_KEY)) initTree();
            if (RedisUtils.get(PTConstOld.TREE_KEY).contains(keyWord)) return true;
        }
        return false;
    }

    /**
     * 列表 拦截组装查询条件
     */
    private Map<String, String> getContition(QueryParam sqp) {
        return putAllCondition(sqp.getKeyWord().trim(), sqp.getType());
    }

}
