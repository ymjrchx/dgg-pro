//package net.dgg.dqc.backservice.es.services;
//
//import com.alibaba.fastjson.JSON;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.aggregations.AggregationBuilder;
//import org.elasticsearch.search.sort.SortOrder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
//import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
//
///**
// * Created by jiangsh on 2018/6/22 14:45
// */
//@Service
//public class EsService {
//
//    Logger logger = LoggerFactory.getLogger(EsService.class);
//
//    public List<Map> getMsgByKeyWord(String key, String companyId, String type) {
//        return EsUtils.getMsgByKeyWord(EsConst.INDEX, type, key, companyId, 1);
//    }
//
//    public long getTotalCountIndex(Map<String, String> condition, Map<String, String> map, String index, boolean isphrase) {
//        if (condition != null && condition.size() > 0)
//            map.putAll(condition);
//        return EsUtils.getTotalCountIndex(EsConst.INDEX, index, condition,
//                null, null, null, 0, 0, map, isphrase);
//    }
//
//    public long getTotalCount(Map<String, String> condition, Map<String, String> map, String index, boolean isphrase) {
//        if (condition != null && condition.size() > 0)
//            map.putAll(condition);
//        return EsUtils.getTotalCount(EsConst.INDEX, index, condition, 0, 0, map, isphrase);
//    }
//
//    public List<Map> getListMapByIndex(Map<String, String> condition, int startCount, int endCount,
//                                       Map<String, String> map, String type, boolean isphrase) {
//        if (condition != null && condition.size() > 0)
//            map.putAll(condition);
//        Map sort = new HashMap();
//        sort.put(PTConst.QYGS_REGISTCAPI, SortOrder.DESC);
////        sort.put(PTConst.INDEX_NAME, SortOrder.DESC);
//        return EsUtils.getListMapByIndex(EsConst.INDEX, type, condition,
//                null, null, sort, startCount, endCount, map, isphrase);
//    }
//
//    public List<Map> getListMap(Map<String, String> condition, int startCount, int endCount,
//                                Map<String, String> map, String type, boolean isphrase, int limit) {
//        if (condition != null && condition.size() > 0)
//            map.putAll(condition);
//        return EsUtils.get(EsConst.INDEX, type, condition, startCount, endCount, map, isphrase, limit);
//    }
//
//    public List<Map> getPart(String keyWord, int count, String[] include, Map<String, String> m) {
//        BoolQueryBuilder qb = QueryBuilders.boolQuery();
//        String[] excludes = {"", ""};
//        for (Map.Entry<String, String> entry : m.entrySet())
//            qb.should(matchPhraseQuery(entry.getKey(), keyWord));
//        return EsUtils.getPart(EsConst.INDEX, EsConst.TYPE_COMPANY, qb, include, excludes, count);
//    }
//
//    public Map<String, Long> filterAggregationCount(AggregationBuilder ab, boolean out, Map<String, String> con, Map<String, String> map, Map<String, String> tmpMap, String index, String type) {
//        if (ab == null) return null;
//        if (map != null && map.size() > 0) tmpMap.putAll(map);
//        if (con != null && con.size() > 0) tmpMap.putAll(con);
//        logger.info("=============tmpMap===========" + JSON.toJSONString(tmpMap));
//        BoolQueryBuilder qb = QueryBuilders.boolQuery();
//        for (Map.Entry<String, String> entry : tmpMap.entrySet())  //组装条件查询qb
//            if (entry.getKey().equals(PTConst.QYGS_REGISTCAPI)) EsUtils.dealRegCapi(entry.getValue(), qb); //注册资金
//            else if (entry.getKey().equals(PTConst.QYGS_STARTDATE)) EsUtils.startDate(entry.getValue(), qb);  //成立日期
//            else if (entry.getKey().equals(PTConst.QYGS_PROVINCE) || entry.getKey().equals(PTConst.QYGS_INDUSTRY))
//                EsUtils.dealProvinceOrIndustry(entry.getValue(), qb, entry.getKey());
//            else if (out) qb.must(matchPhraseQuery(entry.getKey(), entry.getValue()));
//            else qb.must(matchQuery(entry.getKey(), entry.getValue()));
//        tmpMap.clear();
//        return EsUtils.aggreage(ab, qb, index, type);
//    }
//
//    public long getTotalCount(String index, String type, String key, String val, boolean isphrase) {
//        return EsUtils.getTotalCount(index, type, key, val, isphrase);
//    }
//
//    public List<Map> getCheckNameList(String index, String type, String key, String val, int limit, boolean isphrase) {
//        return EsUtils.getMsgByKeyWord(index, type, key, val, limit, isphrase);
//    }
//}
