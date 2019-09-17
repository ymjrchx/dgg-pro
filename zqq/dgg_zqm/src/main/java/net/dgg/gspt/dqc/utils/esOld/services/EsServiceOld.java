package net.dgg.gspt.dqc.utils.esOld.services;

import com.alibaba.fastjson.JSON;
import net.dgg.gspt.dqc.framework.PTConstOld;
import net.dgg.gspt.dqc.utils.esOld.EsConst;
import net.dgg.gspt.dqc.utils.esOld.EsUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * Created by jiangsh on 2018/6/22 14:45
 */
@Service
public class EsServiceOld {

    Logger logger = LoggerFactory.getLogger(EsServiceOld.class);

    public List<Map> getMsgByKeyWord(String key, String companyId, String type) {
        return EsUtils.getMsgByKeyWord(EsConst.INDEX, type, key, companyId, 1);
    }

    public List<Map> getMsgByKeyWord(String key, String companyId, String type, String[] includes, String[] excludes) {
        return EsUtils.getMsgByKeyWord(EsConst.INDEX, type, key, companyId, 1, includes, excludes);
    }

    public List<Map> getMsgByKeyWordCommon(String key, String val, String index, String type) {
        return EsUtils.getMsgByKeyWord(index, type, key, val, 1);
    }

    public List<Map> getDataOneBrand(String index, String type, String typeVal, String appNoVal, String[] includes, String[] excludes) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(matchPhraseQuery(PTConstOld.BRAND_TYPE, typeVal));
        qb.must(matchPhraseQuery(PTConstOld.BRAND_REGNO, appNoVal));
        return EsUtils.getDataOne(index, type, qb, 1, includes, excludes);
    }

    public long getTotalCountIndex(Map<String, String> condition, Map<String, String> map, String type, boolean isphrase, String[] include, String[] exclude) {
        return EsUtils.getTotalCountIndex(EsConst.INDEX, type, condition,
                null, null, null, 0, 0, map, isphrase, include, exclude);
    }

    public long getTotalCount(String index, Map<String, String> condition, Map<String, String> map, String type, boolean isphrase) {
        if (condition != null && condition.size() > 0)
            map.putAll(condition);
        return EsUtils.getTotalCount(index, type, condition, 0, 0, map, isphrase);
    }

    public List<Map> getListMapByIndex(Map<String, String> condition, int startCount, int endCount,
                                       Map<String, String> map, String type, boolean isphrase, String[] include, String[] exclude) {
        Map sort = new HashMap();
        sort.put(PTConstOld.QYGS_REGISTCAPI, SortOrder.DESC);
//        sort.put(PTConstOld.INDEX_NAME, SortOrder.DESC);
        return EsUtils.getListMapByIndex(EsConst.INDEX, type, condition,
                null, null, sort, startCount, endCount, map, isphrase, include, exclude);
    }

    public List<Map> getListMap(String index, Map<String, String> condition, int startCount, int endCount,
                                Map<String, String> map, String type, boolean isphrase, int limit) {
        if (condition != null && condition.size() > 0)
            map.putAll(condition);
        return EsUtils.get(index, type, condition, startCount, endCount, map, isphrase, limit);
    }

    public List<Map> getListMapCpws(String index, String type, Map<String, String> map, String caseType, int startCount, int endCount, String[] include, String[] exclude, String sortKey) {
        return EsUtils.getMsgByKeyWord(index, type, cpwsQb(map, caseType), startCount, endCount, include, exclude, sortKey);
    }

    public List<Map> getListMap(String index, String type, Map<String, String> map, int startCount, int endCount, String[] include, String[] exclude, String sortKey) {
        return EsUtils.getMsgByKeyWord(index, type, taxQb(map, PTConstOld.SHOULD), startCount, endCount, include, exclude, sortKey);
    }

    public List<Map> getListMapResPro(String index, String type, Map<String, String> map, int startCount, int endCount, String[] include, String[] exclude, String sortKey) {
        return EsUtils.getMsgByKeyWord(index, type, resProQb(map), startCount, endCount, include, exclude, sortKey);
    }

    public List<Map> getListMap(String index, String type, String key, String val, int startCount, int endCount, String[] include, String[] exclude, String timeKey) {
        return EsUtils.getMsgByKeyWord(index, type, newsQb(key, val, timeKey), startCount, endCount, include, exclude, timeKey);
    }

    public List<Map> getPart(String index, String type, String keyWord, int count, String[] include, Map<String, String> m, String sortKey) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        String[] excludes = {"", ""};
        for (Map.Entry<String, String> entry : m.entrySet())
            qb.should(matchPhraseQuery(entry.getKey(), keyWord));
        return EsUtils.getPart(index, type, qb, include, excludes, count, sortKey);
    }

    public Map<String, Long> filterAggregationCount(AggregationBuilder ab,
                                                    boolean out, Map<String, String> con, Map<String, String> map, Map<String, String> tmpMap, String index, String type, String aggFlag) {
        if (ab == null) return null;
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        if (con != null && con.size() > 0) {
            for (Map.Entry<String, String> entry : con.entrySet())
                qb.should(matchPhraseQuery(entry.getKey(), entry.getValue()));
        }

        BoolQueryBuilder orQuery2 = QueryBuilders.boolQuery();
        orQuery2.must(qb);
        if (map != null && map.size() > 0) tmpMap.putAll(map);
        logger.info("=============tmpMap===========" + JSON.toJSONString(tmpMap));
        for (Map.Entry<String, String> entry : tmpMap.entrySet())  //组装条件查询qb
            if (entry.getKey().equals(PTConstOld.QYGS_REGISTCAPI))
                EsUtils.dealRegCapi(entry.getValue(), orQuery2); //注册资金
            else if (entry.getKey().equals(PTConstOld.QYGS_STARTDATE))
                EsUtils.startDate(entry.getValue(), orQuery2, PTConstOld.QYGS_STARTDATE);  //成立日期
            else if (entry.getKey().equals(PTConstOld.CPWS_JUDGEDATE))
                EsUtils.startDate(entry.getValue(), orQuery2, PTConstOld.CPWS_JUDGEDATE);  //裁定年份（裁判文书里面）
            else if (entry.getKey().equals(PTConstOld.QYGS_PROVINCE) || entry.getKey().equals(PTConstOld.QYGS_INDUSTRY))
                EsUtils.dealProvinceOrIndustry(entry.getValue(), orQuery2, entry.getKey());
            else if (out)
                orQuery2.must(matchPhraseQuery(entry.getKey(), entry.getValue()));
            else
                orQuery2.must(matchQuery(entry.getKey(), entry.getValue()));

        tmpMap.clear();
        return EsUtils.aggreage(ab, orQuery2, index, type, aggFlag);
    }

    public Map<String, Long> filterAggregationCount(AggregationBuilder ab, String index, String type, Map<String, String> map) {
        if (ab == null) return null;
        return EsUtils.aggreage(ab, taxQb(map, PTConstOld.SHOULD), index, type, PTConstOld.AGGREATE_LIST_CPWS);
    }

    public long getTotalCount(String index, String type, String key, String city, String name, String industry, String companyType) {
        return EsUtils.getTotalCount(index, type, getBoolQueryBuilder(key, city, name, industry, companyType));
    }

    public List<Map> getCheckNameList(String index, String type, String key, String city, String name, String industry, String companyType, int limit) {
        return EsUtils.getMsgByKeyWord(index, type, getBoolQueryBuilder(key, city, name, industry, companyType), limit, key);
    }

    public long getTotalCount(String index, String type, String key, String val, String timeKey) {
        return EsUtils.getTotalCount(index, type, newsQb(key, val, timeKey));
    }

    public long getTotalCountCpws(String index, String type, Map<String, String> m, String caseType) {
        return EsUtils.getTotalCount(index, type, cpwsQb(m, caseType));
    }

    public long getTotalCount(String index, String type, Map<String, String> m) {
        return EsUtils.getTotalCount(index, type, taxQb(m, PTConstOld.SHOULD));
    }

    public long getTotalCountResPro(String index, String type, Map<String, String> m) {
        return EsUtils.getTotalCount(index, type, resProQb(m));
    }

    private BoolQueryBuilder getBoolQueryBuilder(String key, String city, String name, String industry, String companyType) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(matchPhraseQuery(key, city));
        qb.must(matchQuery(key, name));
        qb.must(matchQuery(key, industry));
        qb.must(matchPhraseQuery(key, companyType));
        return qb;
    }

    private BoolQueryBuilder taxQb(Map<String, String> m, String method) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        for (Map.Entry<String, String> entry : m.entrySet()) {
            if (entry.getValue().contains("、")) {
                String[] v = entry.getValue().split("、");
                for (int i = 0; i < v.length; i++) {
                    if (method.equals(PTConstOld.SHOULD))
                        qb.should(matchPhraseQuery(entry.getKey(), v[i]));
                    else
                        qb.must(matchPhraseQuery(entry.getKey(), v[i]));
                }
            } else if (method.equals(PTConstOld.SHOULD)) qb.should(matchPhraseQuery(entry.getKey(), entry.getValue()));
            else qb.must(matchPhraseQuery(entry.getKey(), entry.getValue()));
        }
        return qb;
    }

    private BoolQueryBuilder resProQb(Map<String, String> m) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(matchPhraseQuery(PTConstOld.INDEX_ADDRESS, m.get(PTConstOld.INDEX_ADDRESS)));
        qb.must(matchQuery(PTConstOld.INDEX_BUSI_SCOPE, m.get(PTConstOld.QYGS_INDUSTRY)));
        qb.must(QueryBuilders.existsQuery(PTConstOld.QYGS_INDUSTRY)); //查为非空的数据
        return qb;
    }

    private BoolQueryBuilder newsQb(String key, String val, String timeKey) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -10); //前10天
        qb.must(matchPhraseQuery(key, val));
//        qb.must(QueryBuilders.rangeQuery(timeKey).gt(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()).concat("T00:00:00")));
        return qb;
    }

    private BoolQueryBuilder cpwsQb(Map<String, String> map, String caseType) {
        BoolQueryBuilder qb = taxQb(map, PTConstOld.SHOULD);
        if (StringUtils.isNotEmpty(caseType)) {
            qb.must(matchPhraseQuery("caseType", caseType));
        }
        return qb;
    }


    //裁判文书工具（大）
    public Object getListMapCpwsMax(Map<String, String> condition, int startCount, int endCount,
                                    Map<String, String> map, String index, String type, boolean isphrase, String[] include, String[] exclude, Map<String, String> sort) {
        Map sortMap = null;
        if (sort != null && sort.size() > 0) {
            sortMap = new HashMap();
            Object cprq = sort.get(PTConstOld.CPWS_JUDGEDATE);
            if (cprq != null)
                sortMap.put(PTConstOld.CPWS_JUDGEDATE, cprq.toString().equals("1") ? SortOrder.ASC : SortOrder.DESC);
        }
        return EsUtils.getListMapByIndex(index, type, condition,
                null, null, sortMap, startCount, endCount, map, isphrase, include, exclude);
    }

    public Object getTotalCountCpwsMax(Map<String, String> condition, Map<String, String> map,
                                       String index, String type, boolean isphrase, String[] include, String[] exclude) {
        return EsUtils.getTotalCountIndex(index, type, condition,
                null, null, null, 0, 0, map, isphrase, include, exclude);
    }
}
