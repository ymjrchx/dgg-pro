package net.dgg.zqq.controller.search;

import net.dgg.zqq.dao.order.ClassThirdDao;
import net.dgg.zqq.dto.brandsearch.ApproParam;
import net.dgg.zqq.dto.brandsearch.ImgSearchReqParam;
import net.dgg.zqq.dto.brandsearch.ImgSearchRes2Param;
import net.dgg.zqq.dto.brandsearch.Kv;
import net.dgg.zqq.entity.business.treebook.TreeBook;
import net.dgg.zqq.entity.order.ClassThird;
import net.dgg.zqq.framework.PTConst;
import net.dgg.zqq.framework.interceptor.ConstMethod;
import net.dgg.zqq.framework.redis.RedisUtils;
import net.dgg.zqq.framework.xst.ClientAipOcr;
import net.dgg.zqq.services.TreeBookService;
import net.dgg.zqq.services.search.SearchService;
import net.dgg.zqq.services.searchWord.SearchWordService;
import net.dgg.zqq.utils.IpAddressUtils;
import net.dgg.zqq.utils.es.EsConst;
import net.dgg.zqq.utils.es.EsUtils;
import net.dgg.zqq.utils.es.services.EsService;
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
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.dgg.zqq.controller.search.CompanyItemController.indexs;
import static net.dgg.zqq.controller.search.CompanyItemController.types;

/**
 * @ClassName: BrandSearchController
 * @Description: 首页商标搜索
 * @Author: jiangsh
 * @Date: 2018/9/25 15:55
 */

@RestController
@RequestMapping("/brandSearch")
public class BrandSearchController extends BaseController implements ConstMethod {

    Logger logger = LoggerFactory.getLogger(BrandSearchController.class);

    @Resource
    private EsService esService;
    @Resource
    private SearchService searchService;
    @Resource
    private TreeBookService treeBookService;
    @Resource
    private ClassThirdDao classThirdDao;
    @Resource
    private SearchWordService searchWordService;


    /**
     * 查询商标列表
     * pageIndex、pageSize、keyWord必传
     */
    @RequestMapping(value = "/searchList", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse search(@RequestBody ApproParam ap, HttpServletRequest request) {
//        logger.info("--------------------search list----------" + JSON.toJSONString(ap));
        if (ap.getPageIndex() > 1) Assert.isTrue(justLogin(request), PTConst.LOGIN_ONE);

        searchWordService.search(ap.getKeyWord(), IpAddressUtils.getIP(request));

        Map<String, Object> dataMap = pages(ap.getPageIndex(), ap.getPageSize());
        final int startCount = Integer.valueOf(dataMap.get("startCount").toString());
        final int endCount = Integer.valueOf(dataMap.get("endCount").toString());

        Map<String, String> condition = new HashMap<>(); //关键字搜索
        if (ap.getKey().equals(PTConst.KEY_ITEM)) condition.put(PTConst.BRAND_NAME, ap.getKeyWord());
        else condition.put(ap.getKey(), ap.getKeyWord());

        putDataMap(condition, startCount, endCount, dataMap, mapConditions(ap, "list"), PTConst.F_TRUE, ap); //isphrase设置全局map多条件匹配模式
        List<Map> dataList = (List<Map>) dataMap.get("listData");
        if (dataList != null && dataList.size() > 0)
            return getSuccessResponse(dataMap);
        else return getSuccessResponse(PTConst.PROMPT);
    }

    private void putDataMap(Map<String, String> condition, int startCount, int endCount, Map<String, Object> dataMap, Map<String, String> map, boolean isphrase, ApproParam ap) {
        final String[] include = getSearchListColumn();
        final String[] exclude = {""};
        dataMap.put("listData", searchService.converListMap(esService.getListMapBrand(condition, startCount, endCount, map, EsConst.INDEX_BRAND, EsConst.TYPE_COMPANY_BRAND, isphrase, include, exclude, ap)));
        dataMap.put("totalCount", esService.getTotalCountBrand(condition, map, EsConst.INDEX_BRAND, EsConst.TYPE_COMPANY_BRAND, isphrase, include, exclude, ap));
        dataMap.put("imgPrefix", searchService.getFileUrlPrefix());
    }


    /**
     * 商标 列表展示列
     */
    protected static String[] getSearchListColumn() {
        final String[] columns = {"imageUrl", "name", "status", "intCls", "regNo", "appDate", "applicantCn", "announcementDate", "regDate", "tmiPath", "tmGoodsService"};
        return columns;
    }


    /**
     * 近似商标搜索展示,数据字典数量统计
     */
    @RequestMapping(value = "/approximate", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse approximate(@RequestBody ApproParam ap) throws Exception {
        Map<String, String> map = mapConditions(ap, "tree");
        final String k = ap.getKey();
        final String kw = ap.getKeyWord();
        final String layStatus = ap.getLayStatus();
        final String applayYear = ap.getApplyYear();
        if (kw.isEmpty()) throw new IllegalArgumentException("参数异常");

        List<String> codes = treeBookService.getCodeByPid(PTConst.BRAND_SEARCH);
        Map<String, Object> dataMap = new HashMap<>();
        if (codes != null && codes.size() > 0) {
            Map<String, String> tmpMap = new HashMap<>();
            if (k.equals(PTConst.BRAND_APPLICATCN)) { //如果是申请人选项卡，添加申请人信息
                dataMap.put(PTConst.APPLY_PERSON, resultCount(getApplyPerson(kw), PTConst.BRAND_APPLICATCN, map, tmpMap, PTConst.F_TRUE, PTConst.F_TRUE, ap));
            }
            if (k.equals(PTConst.BRAND_TMGOODSSERVICE)) {//如果是商品服务，则添加规范商品
                dataMap.put(PTConst.APPLY_TMGOODSSERVICE, resultCount(getGoodsName(kw), PTConst.BRAND_TMGOODSSERVICE, map, tmpMap, PTConst.F_TRUE, PTConst.F_TRUE, ap));
            }

            for (String code : codes) {
                switch (code) {

                    case PTConst.APPLY_TYPE: //申请类别
                        if (StringUtils.isNotEmpty(ap.getApplyType())) { //近似商标-统计申请小类别
                            dataMap.put(PTConst.APPLY_TYPE.concat("_SMALL"), nextNodeCount(ap.getApplyType(), tmpMap, map, ap));
                            break;
                        } else if (StringUtils.isNotEmpty(layStatus) || StringUtils.isNotEmpty(applayYear)) { //近似商标- 若点击法律状态 或者 成立日期 就不返回申请类别统计
                            break;
                        } else
                            dataMap.put(PTConst.APPLY_TYPE, resultCountMap(treeBookService.getNameAndCodeByCode(code), PTConst.BRAND_TYPE, map, tmpMap, PTConst.F_TRUE, PTConst.F_TRUE, ap));
                        break;

                    case PTConst.LAY_STATE: //法律状态
                        dataMap.put(PTConst.LAY_STATE, resultCountMap(treeBookService.getNameAndCodeByCode(code), PTConst.BRAND_STATE, map, tmpMap, PTConst.F_TRUE, PTConst.F_TRUE, ap));
                        break;

                    case PTConst.APPLY_YEAR: //申请年份
                        dataMap.put(PTConst.APPLY_YEAR, resultCountMap(treeBookService.getNameAndCodeByCode(code), PTConst.BRAND_APPDATE, map, tmpMap, PTConst.F_TRUE, PTConst.F_TRUE, ap));
                        break;

                    default:
                        break;
                }
            }
        }

        if (dataMap.size() > 0)
            return getSuccessResponse(dataMap);
        else
            return getSuccessResponse(PTConst.PROMPT);
    }

    private List<Kv> resultCount(List<String> list, String key,
                                 Map<String, String> map, Map<String, String> tmpMap, boolean in, boolean out, ApproParam ap) throws Exception {
        List<String> values = new ArrayList<>();
        for (String t : list) {
            if (key.equals(PTConst.BRAND_APPLICATCN)) { //过滤出选中的 申请人
                final Object cn = map.get(PTConst.BRAND_APPLICATCN);
                if (cn != null && !cn.toString().equals(t)) continue;
            }

            if (key.equals(PTConst.BRAND_TMGOODSSERVICE)) { //过滤出选中的 商品服务
                final Object goodService = map.get(PTConst.BRAND_TMGOODSSERVICE);
                if (goodService != null && !goodService.toString().equals(t)) continue;
            }

            values.add(t);
        }
        return converKv(esService.filterAggregationCount(filterAggregation(values, key, in), out, map, tmpMap, EsConst.INDEX_BRAND, EsConst.TYPE_COMPANY_BRAND, ap));
    }

    private List<Kv> resultCountMap(List<TreeBook> list, String key,
                                    Map<String, String> map, Map<String, String> tmpMap, boolean in, boolean out, ApproParam ap) throws Exception {
        List<String> values = new ArrayList<>();
        for (TreeBook t : list) {
            if (key.equals(PTConst.BRAND_APPDATE)) { //过滤出选中的 申请年份
                final Object appDate = map.get(PTConst.BRAND_APPDATE);
                if (appDate != null && !appDate.toString().equals(t.getName())) continue;
            }

            if (key.equals(PTConst.BRAND_STATE)) { //过滤出选中的 法律状态
                final Object status = map.get(PTConst.BRAND_STATE);
                if (status != null && !status.toString().equals(t.getName())) continue;
            }

            if (key.equals(PTConst.BRAND_SERVERNUM)) //申请小类别
                values.add(t.getCode().concat(" ").concat(t.getName()));
            else values.add(t.getName());
        }
        return converKv(esService.filterAggregationCount(filterAggregation(values, key, in), out, map, tmpMap, EsConst.INDEX_BRAND, EsConst.TYPE_COMPANY_BRAND, ap));
    }

    /**
     * 根据条件获取申请人信息
     * @param applicantCn 申请人
     */
    private List<String> getApplyPerson(String applicantCn) {
        return esService.getApplyPerson(EsConst.INDEX_BRAND, EsConst.TYPE_COMPANY_BRAND, PTConst.BRAND_APPLICATCN, applicantCn, 100);
    }


    /**
     * 统计申请小类别数量
     */
    private List<Kv> nextNodeCount(String code, Map<String, String> tmpMap, Map<String, String> map, ApproParam ap) throws Exception {
        List<Kv> list = new ArrayList<>();
        if (code.contains("-")) {
            String[] s = code.split("-");
            for (int i = 0; i < s.length; i++) {
                final String co = s[i].substring(0, 2);
                Kv kv = new Kv();
                kv.setKey(s[i]);
                kv.setVal(resultCountMap(treeBookService.getNameAndCodeByCode(zero(co)), PTConst.BRAND_SERVERNUM, map, tmpMap, PTConst.F_TRUE, PTConst.F_TRUE, ap));
                list.add(kv);
            }
        } else {
            Kv kv = new Kv(code, resultCountMap(treeBookService.getNameAndCodeByCode(zero(code.substring(0, 2))), PTConst.BRAND_SERVERNUM, map, tmpMap, PTConst.F_TRUE, PTConst.F_TRUE, ap));
            list.add(kv);
        }
        return list;
    }

    /**
     * 分组过滤
     */
    protected static AggregationBuilder filterAggregation(List<String> values, String key, boolean in) throws Exception {
        if (values != null && values.size() > 0) {
            FiltersAggregator.KeyedFilter[] filters = new FiltersAggregator.KeyedFilter[values.size()];
            int i = 0;
            for (String val : values) {
//                logger.info(key + "===================" + val);
                BoolQueryBuilder query = QueryBuilders.boolQuery();
                if (key.equals(PTConst.BRAND_APPDATE))
                    EsUtils.startDate(val, query); //申请年份
                else if (key.equals(PTConst.BRAND_TYPE))
                    query.must(QueryBuilders.matchPhraseQuery(key, EsUtils.converBrandType(val))); //申请类别
                else if (key.equals(PTConst.BRAND_SERVERNUM))
                    query.must(QueryBuilders.matchPhraseQuery(key, EsUtils.converBrandSmallType(val))); //申请小类别
                else if (key.equals(PTConst.BRAND_TMGOODSSERVICE))
                    query.must(QueryBuilders.matchPhraseQuery(key, val.substring(val.lastIndexOf("_") + 1)));
                else if (in)
                    query.must(QueryBuilders.matchPhraseQuery(key, val));
                else
                    query.must(QueryBuilders.matchQuery(key, val));
                filters[i] = new FiltersAggregator.KeyedFilter(val, query);
                i++;
            }
            return AggregationBuilders.filters(PTConst.AGGREATE_LIST, filters);
        } else return null;
    }

    /**
     * 数据字典 拦截组装请求参数
     */
    protected static Map<String, String> mapConditions(ApproParam sqp, String methodType) {
        Map<String, String> condition = new HashMap<>();

        if (StringUtils.isNotEmpty(sqp.getApplyType())) //申请类别
            condition.put(PTConst.BRAND_TYPE, sqp.getApplyType());

        if (StringUtils.isNotEmpty(sqp.getLayStatus())) //法律状态
            condition.put(PTConst.BRAND_STATE, sqp.getLayStatus());

        if (StringUtils.isNotEmpty(sqp.getApplyYear())) //申请年份
            condition.put(PTConst.BRAND_APPDATE, sqp.getApplyYear());

        if (StringUtils.isNotEmpty(sqp.getGoodsNum())) //申请小类别
            condition.put(PTConst.BRAND_SERVERNUM, sqp.getGoodsNum());

        if (StringUtils.isNotEmpty(sqp.getGoods())) //商品服务
            if (methodType.equals("list"))
                condition.put(PTConst.BRAND_TMGOODSSERVICE, sqp.getGoods().substring(sqp.getGoods().lastIndexOf("_") + 1));
            else condition.put(PTConst.BRAND_TMGOODSSERVICE, sqp.getGoods());

        if (StringUtils.isNotEmpty(sqp.getApplyPerson())) //申请人
            condition.put(PTConst.BRAND_APPLICATCN, sqp.getApplyPerson());

        return condition;
    }

    /**
     * 商标详情
     *
     * @param type  商标类别
     * @param appNo 申请号
     * @return 商标详情
     */
    @RequestMapping(value = "/brandDetail", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse brandDetail(@RequestParam String type,
                                    @RequestParam String appNo
            , HttpServletRequest request
    ) {
//        Assert.isTrue(justLogin(request), PTConst.LOGIN_TWO);
        final String t = PTConst.MIX_BRAND;
        List<Map> dataList = esService.getDataOneBrand(indexs(t), types(t), searchService.converApplyType(type), appNo);
        if (dataList != null && dataList.size() > 0) {
            Map m = new HashMap();
            m.put("imgPrefix", searchService.getFileUrlPrefix());
            dataList.add(m);
            return getSuccessResponse(dataList);
        } else return getFailResponse(PTConst.PROMPT);
    }

    /**
     * 商标 相似图片检索--数据字典
     * notice: pn 1, rn 1000 获取单页最大，便于聚合统计数量;
     */
    @RequestMapping(value = "/imgSearch", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse imgSearch(@RequestBody ImgSearchReqParam isr, HttpServletRequest request) throws Exception {
        if (!isr.getRn().equals(PTConst.BAIDU_RN)) isr.setRn(PTConst.BAIDU_RN);
//        final String resultString = baiduRes(isr, request).split("-")[0];
        final String resultString = baiduRes(isr, request).split("、")[0];
        Map<String, Object> dataMap = new HashMap<>();
        List<String> codes = treeBookService.getCodeByPid(PTConst.BRAND_SEARCH);
        if (codes != null && codes.size() > 0) {
            for (String code : codes) {
                switch (code) {
                    case PTConst.APPLY_TYPE: //申请类别
                        dataMap.put(PTConst.APPLY_TYPE, resImgCount(treeBookService.getNameAndCodeByCode(code), resultString, PTConst.BRAND_TYPE, PTConst.F_TRUE));
                        break;

                    case PTConst.LAY_STATE: //法律状态
                        dataMap.put(PTConst.LAY_STATE, resImgCount(treeBookService.getNameAndCodeByCode(code), resultString, PTConst.BRAND_STATE, PTConst.F_TRUE));
                        break;

                    case PTConst.APPLY_YEAR: //申请年份
                        dataMap.put(PTConst.APPLY_YEAR, resImgCount(treeBookService.getNameAndCodeByCode(code), resultString, PTConst.BRAND_APPDATE, PTConst.F_TRUE));
                        break;

                    default:
                        break;
                }
            }
        }
        return getSuccessResponse(dataMap);
    }

    /**
     * 商标 相似图片检索--列表
     * notice: 当点击条件时，pn 1, rn 1000 获取单页最大
     */
    @RequestMapping(value = "/imgSearchList", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse imgSearchList(@RequestBody ImgSearchReqParam isr, HttpServletRequest request) throws Exception {
//        Assert.isTrue(justLogin(request), PTConst.LOGIN_THREE);

        boolean flag = false;
        final String tmpRn = isr.getRn();
        if (StringUtils.isNotEmpty(isr.getApplyType()) || StringUtils.isNotEmpty(isr.getLayStatus()) || StringUtils.isNotEmpty(isr.getApplyYear())) { //判断是否有添加条件过滤
            flag = true;
        }
        isr.setRn(PTConst.BAIDU_RN);
        final String result = baiduRes(isr, request);
        isr.setRn(tmpRn);
//        final String resultString = result.split("-")[0];
        final String resultString = result.split("、")[0];
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("listData", esService.imgSearchList(EsConst.INDEX_BRAND, EsConst.TYPE_COMPANY_BRAND, resultString, imgConditions(isr), Integer.valueOf(isr.getRn()), flag, isr));
        return getSuccessResponse(dataMap);
    }

    /**
     * 从百度库获取图片信息
     */
    private String baiduRes(ImgSearchReqParam isr, HttpServletRequest request) {
        final String ip = IpAddressUtils.getIP(request);
        String resultString;
        final String key = ip.concat(isr.getUrl()).concat("_").concat(isr.getPn()).concat("_").concat(isr.getRn());
        if (!RedisUtils.exists(key)) {
            // 传入可选参数调用接口
            HashMap<String, String> options = new HashMap<>();
            options.put("tags", PTConst.BAIDU_TAGS);
            options.put("tag_logic", PTConst.BAIDU_TAG_LOGIC);
            options.put("pn", isr.getPn());
            options.put("rn", isr.getRn());
            final ImgSearchRes2Param res2Param = searchService.getImgSearchRes2(ClientAipOcr.getClient().similarSearchUrl(isr.getUrl(), options).toString(2));
//            logger.info("---------baidu----" + isr.getPn() + "-------" + JSON.toJSONString(res2Param));
            List<ImgSearchRes2Param.Result> results = res2Param.getResult();
            StringBuffer sb = new StringBuffer();
            if (!results.isEmpty()) {
                for (ImgSearchRes2Param.Result r : results) {
                    final ImgSearchRes2Param.Brief brief = r.getBrief();
//                    sb.append(brief.getEsId()).append(";");
                    sb.append(brief.getImgUrl()).append(";");
                }
//                sb.append("-").append(res2Param.isHasMore());
                sb.append("、").append(res2Param.isHasMore());
            }
            saveToRedis(sb.toString(), key, 600); //save to redis， 10分钟
            resultString = sb.toString();
        } else resultString = RedisUtils.get(key);
        return resultString;
    }

    /**
     * 存储百度相似图片关键信息至redis
     */
    private void saveToRedis(String result, String ip, int seconds) {
        if (!RedisUtils.exists(ip)) {
            RedisUtils.set(ip, result);
            RedisUtils.expire(ip, seconds);
        }
    }

    /**
     * 图片搜索聚合辅助
     */
    private List<Kv> resImgCount(List<TreeBook> list, String results, String key, boolean in) throws Exception {
        List<String> values = new ArrayList<>(); //过滤匹配项
        for (TreeBook t : list)
            values.add(t.getName());

        final String[] s = results.split(";");
        return converKv(esService.filterAggregationCount(filterAggregation(values, key, in), s, EsConst.INDEX_BRAND, EsConst.TYPE_COMPANY_BRAND));
    }

    /**
     * 数据字典 拦截组装请求参数
     */
    private Map<String, String> imgConditions(ImgSearchReqParam sqp) {
        Map<String, String> condition = new HashMap<>();

        if (StringUtils.isNotEmpty(sqp.getApplyType())) //申请类别
            condition.put(PTConst.BRAND_TYPE, sqp.getApplyType());

        if (StringUtils.isNotEmpty(sqp.getLayStatus())) //法律状态
            condition.put(PTConst.BRAND_STATE, sqp.getLayStatus());

        if (StringUtils.isNotEmpty(sqp.getApplyYear())) //申请年份
            condition.put(PTConst.BRAND_APPDATE, sqp.getApplyYear());

        return condition;
    }

    /**
     * 从mysql中，按条件获取商品规范种类列表
     *
     * @param key 搜索关键字
     * @return list数据集
     */
    private List<String> getGoodsName(String key) {
        List<String> result = new ArrayList<>();
        List<ClassThird> classThirds = classThirdDao.queryByKey(key);
        if (!classThirds.isEmpty())
            for (ClassThird c : classThirds)
                result.add(converGoods(c.getSecondNumber().concat("_").concat(c.getName())));
        return result;
    }

    private String zero(String co) {
        if (co.substring(0, 1).equals("0"))
            co = co.substring(1, 2);
        return co;
    }

    protected static List<Kv> converKv(Map<String, Long> map) {
        List<Kv> kvs = new ArrayList<>();
        if (map != null && map.size() > 0)
            for (Map.Entry<String, Long> e : map.entrySet()) {
                Kv kv = new Kv();
                kv.setKey(e.getKey());
                kv.setVal(String.valueOf(e.getValue()));
                kvs.add(kv);
            }
        return kvs;
    }

    private String converGoods(String s) {
        if (StringUtils.isNotEmpty(s)) {
            String one = s.substring(0, 2);
            if (one.substring(0, 1).equals("0"))
                one = one.substring(1, 2);
            return one.concat("_").concat(s);
        }
        return s;
    }

}
