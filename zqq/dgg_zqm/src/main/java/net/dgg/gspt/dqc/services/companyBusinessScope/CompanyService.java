package net.dgg.gspt.dqc.services.companyBusinessScope;

import com.google.gson.Gson;
import net.dgg.gspt.dqc.config.QccConfig;
import net.dgg.gspt.dqc.controller.company.CompanyControllerOld;
import net.dgg.gspt.dqc.entity.CpwsAgg;
import net.dgg.gspt.dqc.entity.business.search.BrandHealth;
import net.dgg.gspt.dqc.entity.business.search.Measure;
import net.dgg.gspt.dqc.framework.PTConstOld;
import net.dgg.gspt.dqc.framework.interceptor.ConstMethod;
import net.dgg.gspt.dqc.services.TreeBookService;
import net.dgg.gspt.dqc.services.impl.CompanyServiceImpl;
import net.dgg.gspt.dqc.utils.HttpUtility;
import net.dgg.gspt.dqc.utils.esOld.EsConst;
import net.dgg.gspt.dqc.utils.esOld.services.EsServiceOld;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filters.FiltersAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static net.dgg.gspt.dqc.controller.search.CompanyItemController.includes;
import static net.dgg.gspt.dqc.controller.search.CompanyItemController.mixMap;

/**
 * @ClassName: CompanyService
 * @Description: 公司接口服务层
 * @Author: jiangsh
 * @Date: 2018/9/12 13:46
 */

@Service
public class CompanyService implements ConstMethod {

    Logger logger = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private EsServiceOld esService;
    /*@Resource
    private UserCallRecordService userCallRecordService;*/
    @Resource
    private TreeBookService treeBookService;
    @Resource
    private CompanyServiceImpl csi;
    @Resource
    private QccConfig qccConfig;

    /**
     * 资源生成器
     */
    @Transactional
    public Map<String, Object> resourceProGet(String appKey, String url, String ip, Gson gson, int pageIndex, int pageSize, String city, String industry) {
        Map<String, Object> dataMap = pageService(pageIndex, pageSize);
        final int startCount = Integer.valueOf(dataMap.get("startCount").toString());
        final int endCount = Integer.valueOf(dataMap.get("endCount").toString());

        final String[] exclude = {"", ""};
        dataMap.put("listData", esService.getListMapResPro(EsConst.INDEX, EsConst.TYPE_COMPANY, maps(city, industry), startCount, endCount, includesPro(), exclude, null));
        dataMap.put("totalCount", esService.getTotalCountResPro(EsConst.INDEX, EsConst.TYPE_COMPANY, maps(city, industry)));
        //userCallRecordService.save(recordRequest(url, appKey, ip, gson, 0, maps(city, industry)));
        return dataMap;
    }

    /**
     * 商标、专利 等等服务接口
     */
    @Transactional
    public Map<String, Object> mix(String appKey, String url, String ip, Gson gson, int pageIndex, int pageSize, String keyWord, String t, String caseType) {
        Map<String, Object> dataMap = pageService(pageIndex, pageSize);
        final int startCount = Integer.valueOf(dataMap.get("startCount").toString());
        final int endCount = Integer.valueOf(dataMap.get("endCount").toString());

        final String[] exclude = {"", ""};
        final List<Map> list = esService.getListMapCpws(indexsOld(t), typesOld(t), mixMap(t, keyWord), caseType, startCount, endCount, includes(t), exclude, null);
        dataMap.put("listData", list);
        dataMap.put("totalCount", esService.getTotalCountCpws(indexsOld(t), typesOld(t), mixMap(t, keyWord), caseType));

        // userCallRecordService.save(recordRequest(url, appKey, ip, gson, 0, mixMap(t, keyWord)));
        return dataMap;
    }

    /**
     * 商标、专利 等等详情服务接口
     */
    @Transactional
    public List<Map> mixDetail(String appKey, String url, String val, String ip, Gson gson, String typeKey, String t) {
        Map<String, String> map = new HashMap<>();
        map.put(typeKey, val);
        //userCallRecordService.save(recordRequest(url, appKey, ip, gson, 0, map));
        List<Map> dataList = esService.getMsgByKeyWordCommon(typeKey, val, indexsOld(t), typesOld(t));
        return dataList;
    }

    /**
     * 分类统计 裁判文书 相关类别数量
     */
    @Transactional
    public List<CpwsAgg> aggCount(String appKey, String url, String ip, Gson gson, String type, String key, String keyWord, int caseType) throws Exception {
        // userCallRecordService.save(recordRequest(url, appKey, ip, gson, 0, mixMap(type, keyWord)));
        return converCpwsData(esService.filterAggregationCount(filterAggregation(getCpwsType(), key, PTConstOld.F_TRUE), EsConst.INDEX_CPWS_TS, EsConst.TYPE_COMPANY_CPWS_TS, mixMap(type, keyWord)), caseType);
    }

    /**
     * 商标成功率测算
     */
    @Transactional
    public Measure brandMeasure(String appKey, String url, String ip, Gson gson, String brand, String industry) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("name", brand);
        map.put("intCls", industry);
        // userCallRecordService.save(recordRequest(url, appKey, ip, gson, 0, map));
        Map<String, Object> condition = new HashMap<>();
        condition.put("brand", brand);
        condition.put("industry", industry);
        final String result = HttpUtility.postRest(qccConfig.getMeasureUrl(), condition);
        final Measure measure = new Measure();
        if (StringUtils.isNotEmpty(result) && !result.equals("")) {
            JSONObject jo = JSONObject.fromObject(result);
            measure.setCause(jo.getJSONObject("data").get("cause").toString());
            measure.setScore(Float.valueOf(jo.getJSONObject("data").get("score").toString()));
        }
        return measure;
    }

    /**
     * 企业查询
     */
    @Transactional
    public Map<String, Object> comSearch(String appKey, String url, String ip, Gson gson, Map<String, String> map, String keyWord, int pageIndex, int pageSize) {
        Map<String, Object> dataMap = pageService(pageIndex, pageSize);
        final int startCount = Integer.valueOf(dataMap.get("startCount").toString());
        final int endCount = Integer.valueOf(dataMap.get("endCount").toString());

        final String[] include = listColumnOld();
        final String[] exclude = {""};
        Map<String, String> condition = CompanyControllerOld.putAllCondition(keyWord, "all");
        dataMap.put("listData", esService.getListMapByIndex(condition, startCount, endCount, map, EsConst.TYPE_COMPANY, true, include, exclude));
        dataMap.put("totalCount", esService.getTotalCountIndex(condition, map, EsConst.TYPE_COMPANY, true, include, exclude));
        //userCallRecordService.save(recordRequest(url, appKey, ip, gson, 0, map));
        return dataMap;
    }

    /**
     * 企业详情查询
     */
    @Transactional
    public List<Map> comSearchDetail(String appKey, String url, String ip, Gson gson, String companyId) {
        final String[] include = getSearchDetailColumnOld();
        final String[] exclude = {""};
        Map<String, String> map = new HashMap<>();
        map.put("companyId", companyId);
        //userCallRecordService.save(recordRequest(url, appKey, ip, gson, 0, map));
        return esService.getMsgByKeyWord(PTConstOld.COMPANY_ID, companyId, EsConst.TYPE_COMPANY, include, exclude);
    }

    /**
     * 商标健康检查
     */
    @Transactional
    public BrandHealth brandHealth(String appKey, String url, String ip, Gson gson, String regNo, String type) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("regNo", regNo);
        map.put("type", type);
        //userCallRecordService.save(recordRequest(url, appKey, ip, gson, 0, map));
        final String t = PTConstOld.MIX_BRAND;
        final String[] includes = {"tmApplyFlow"};
        final String[] excludes = {""};
        List<Map> listMap = esService.getDataOneBrand(indexsOld(t), typesOld(t), type, regNo, includes, excludes);
        if (listMap.size() > 0)
            return csi.checkBrandHealth(listMap.get(0), regNo, type, "tmApplyFlow");
        else return new BrandHealth();
    }

    /**
     * 分组过滤
     */
    private AggregationBuilder filterAggregation(List<String> values, String key, boolean in) throws Exception {
        if (values != null && values.size() > 0) {
            FiltersAggregator.KeyedFilter[] filters = new FiltersAggregator.KeyedFilter[values.size()];
            int i = 0;
            for (String val : values) {
                BoolQueryBuilder query = QueryBuilders.boolQuery();
                if (in) query.must(QueryBuilders.matchPhraseQuery(key, val));
                else query.must(QueryBuilders.matchQuery(key, val));
                filters[i] = new FiltersAggregator.KeyedFilter(val, query);
                i++;
            }
            return AggregationBuilders.filters(PTConstOld.AGGREATE_LIST_CPWS, filters);
        } else return null;
    }

    /**
     * 记录服务接口请求相关信息
     */
   /* private UserCallRecord recordRequest(String requstUrl, String key, String ipAddress, Gson gson, int status, Map<String, String> map) {
        UserCallRecord record = new UserCallRecord();
        record.setId(KeyWorker.nextId());
        record.setCreateTime(new Date());
        record.setUrl(requstUrl);
        if (map != null && map.size() > 0)
            record.setParamJson(gson.toJson(map));
        record.setKey(key);
        record.setResult(status);
        record.setIpAddress(ipAddress);
        return record;
    }*/
    private List<CpwsAgg> converCpwsData(Map<String, Long> m, int caseType) {
        List<CpwsAgg> list = new ArrayList<>();
        if (m != null && m.size() > 0) {
            for (Map.Entry<String, Long> e : m.entrySet()) {
                CpwsAgg cpwsAgg = new CpwsAgg();
                if (e.getKey().contains(PTConstOld.CPWS_XS)) cpwsAgg.setType("1");
                else if (e.getKey().contains(PTConstOld.CPWS_MS)) cpwsAgg.setType("2");
                else if (e.getKey().contains(PTConstOld.CPWS_XZ)) cpwsAgg.setType("3");
                else if (e.getKey().contains(PTConstOld.CPWS_PC)) cpwsAgg.setType("4");
                else if (e.getKey().contains(PTConstOld.CPWS_ZX)) cpwsAgg.setType("5");
                cpwsAgg.setCount(e.getValue());
                cpwsAgg.setName(e.getKey());
                list.add(cpwsAgg);
            }
        }
        if (caseType > 0 && caseType < 6) {
            list = list.stream().filter(t -> t.getType().equals(String.valueOf(caseType))).collect(Collectors.toList());
        }
        return list;
    }

    public Map<String, String> maps(final String city, final String industry) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotEmpty(city))
            map.put(PTConstOld.INDEX_ADDRESS, city);

        if (StringUtils.isNotEmpty(industry))
            map.put(PTConstOld.QYGS_INDUSTRY, industry);

        return map;
    }

    public Map<String, String> comSearchMap(String econKind, String status, String registCapi, String startDate, String province, String industry) {
        Map<String, String> condition = new HashMap<>();
        if (StringUtils.isNotEmpty(econKind))
            condition.put(PTConstOld.QYGS_ENCOKIND, econKind);

        if (StringUtils.isNotEmpty(status))
            condition.put(PTConstOld.QYGS_STATUS, status);

        if (StringUtils.isNotEmpty(registCapi))
            condition.put(PTConstOld.QYGS_REGISTCAPI, registCapi);

        if (StringUtils.isNotEmpty(startDate))
            condition.put(PTConstOld.QYGS_STARTDATE, startDate);

        if (StringUtils.isNotEmpty(province))
            condition.put(PTConstOld.QYGS_PROVINCE, province);

        if (StringUtils.isNotEmpty(industry))
            condition.put(PTConstOld.QYGS_INDUSTRY, industry);

        return condition;
    }

    /**
     * 资源生成器响应字段
     */
    public String[] includesPro() {
//        return new String[]{"name", "companyId", "qygsDetail.scope"};
        return new String[]{"name", "companyId", "qygsDetail.scope", "qygsDetail.address", "qygsDetail.industry"};
    }

}
