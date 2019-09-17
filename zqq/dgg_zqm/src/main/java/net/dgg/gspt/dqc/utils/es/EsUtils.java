package net.dgg.gspt.dqc.utils.es;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dgg.gspt.dqc.framework.PTConst;
import net.dgg.gspt.dqc.utils.JsonUtils;
import net.dgg.gspt.dqc.utils.es.stack.Console;
import net.dgg.gspt.dqc.utils.es.stack.ScanCallback;
import net.dgg.gspt.dqc.utils.es.stack.StoreConsole;
import net.dgg.gspt.dqc.utils.es.stack.UserBoolQueryBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filters.Filters;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * Created by wu on 2017/8/24.
 */
@Slf4j
public class EsUtils {

    public static Log logger = LogFactory.getLog(EsUtils.class);
    private static final String DATESUFIX = "-01-01";

    /**
     * 添加文档
     */
    public static void add(String index, String type, String id, String json) {
        try {
            EsClientUtils.getClient().prepareIndex(index, type, id).setSource(json, XContentType.JSON).get();
        } catch (Exception e) {
            logger.error("add文档时出现异常：e=" + e + " json=" + json, e);
        }
    }

    /**
     * 添加文档
     */
    public static void add(String index, String type, String id, Map<String, Object> source) {
        IndexResponse indexResponse = EsClientUtils.getClient().prepareIndex(index, type, id).setSource(source).get();
        System.out.println(indexResponse.getVersion());
    }

    /**
     * 添加文档
     */
    public static void add(String index, String type, String id, Object object) {
        try {
            String json = JsonUtils.obj2Json(object);
            EsClientUtils.getClient().prepareIndex(index, type, id).setSource(json, XContentType.JSON).get();
        } catch (Exception e) {
            logger.error("插入ES出错" + "index:" + index + ",type:" + type, e);
        }
    }

    /**
     * 更新文档
     */
    public static void update(String index, String type, String id, Map source) throws IOException {
        UpdateResponse updateResponse = EsClientUtils.getClient().prepareUpdate(index, type, id).setDoc(source).get();
        System.out.println(updateResponse.getVersion());
    }

    /**
     * 删除索引
     */
    public static void delete(String index, String type, String id) {
        DeleteResponse deleteResponse = EsClientUtils.getClient().prepareDelete(index, type, id).get();
        deleteResponse.status();
    }

    /**
     * 根据ID查询文档
     */
    public static Map get(String index, String type, String id) {
        GetResponse response = EsClientUtils.getClient().prepareGet(index, type, id).get();
        return response.getSource();
    }

    /**
     * 获取所有信息，不限制数量
     *
     * @param index
     * @param type
     * @param include
     * @param exclude
     * @param qb
     * @return
     */
    public static List<Map> getAllNotLimit(String index, String type, String[] include, String[] exclude, BoolQueryBuilder qb, int count) {
        SearchRequestBuilder searchRequestBuilder = EsClientUtils.getClient().prepareSearch(index)
                .setTypes(type).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

        searchRequestBuilder.setQuery(qb);
        searchRequestBuilder.setFetchSource(include, exclude);
        searchRequestBuilder.setSize(count);
        SearchHits hits = searchRequestBuilder.execute().actionGet().getHits();
        SearchHit[] hitArr = hits.getHits();
        List<Map> list = new ArrayList<>();
        for (SearchHit hit : hitArr) {
            list.add(hit.getSource());
        }
        return list;
    }

    /**
     * 获取列表数据
     */
    public static List<Map> getListMapByIndex(String index, String type, Map<String, String> condition, Map multiCondition,
                                              Map scopeCondition, Map sortMap, int startCount, int endCount,
                                              Map<String, String> map, boolean isphrase, String[] include, String[] exclude, BoolQueryBuilder qb) {

        SearchRequestBuilder searchRequestBuilder =
                getSrbIndex(index, type, condition, multiCondition, scopeCondition, sortMap, startCount, endCount, PTConst.F_TRUE, map, isphrase, include, exclude, qb);
        SearchHits hits = searchRequestBuilder.setSize(endCount - startCount + 1).execute().actionGet().getHits();
        SearchHit[] hitArr = hits.getHits();
        List<Map> list = new ArrayList<>();
        for (SearchHit hit : hitArr) {
            list.add(hit.getSource());
        }
        return list;
    }


    private static SearchRequestBuilder getSrbIndex(String index, String type, Map condition, Map multiCondition,
                                                    Map scopeCondition, Map sortMap, int startCount, int endCount,
                                                    boolean isCount, Map<String, String> map, boolean isphrase, String[] include, String[] exclude, BoolQueryBuilder andQuery) {
        SearchRequestBuilder searchRequestBuilder = EsClientUtils.getClient().prepareSearch(index)
                .setTypes(type).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.postTags("<h1 color='red'>");

        if (condition != null && condition.size() > 0)
            conditionNoNullIndex(highlightBuilder, andQuery, condition, isphrase);

        BoolQueryBuilder orQuery2 = QueryBuilders.boolQuery();
        orQuery2.must(andQuery);
        if (map != null && map.size() > 0)
            mapNoNullIndex(highlightBuilder, orQuery2, map, isphrase);

        if (multiCondition != null)
            multiCon(multiCondition, highlightBuilder, orQuery2);

        if (scopeCondition != null) {
            Iterator<String> scopeIte = scopeCondition.keySet().iterator();
            while (scopeIte.hasNext()) {
                String key = scopeIte.next();
                String value = (String) scopeCondition.get(key);
                highlightBuilder.field(value);
                String[] values = value.split("#");
                RangeQueryBuilder rangeQery = QueryBuilders.rangeQuery(key).from(values[0]).to(values[1]);
                orQuery2.must(rangeQery);
            }
        }

        if (sortMap != null) {
            Iterator<String> iterator = sortMap.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                SortOrder value = (SortOrder) sortMap.get(key);
                searchRequestBuilder.addSort(key, value);
            }
        }
        searchRequestBuilder.setQuery(orQuery2);
        searchRequestBuilder.setFetchSource(include, exclude);
        if (isCount) {
            startCount = startCount - 1 < 0 ? 0 : startCount - 1;
            int size = endCount - startCount;
            searchRequestBuilder.setFrom(startCount).setSize(size);
        }

        searchRequestBuilder.highlighter(highlightBuilder);
        return searchRequestBuilder;
    }

    private static void conditionNoNullIndex(HighlightBuilder highlightBuilder, BoolQueryBuilder andQuery, Map condition, boolean isphrase) {
        if (condition != null && condition.size() > 0) {
            logger.info("==========condition============" + JSON.toJSONString(condition));
            Iterator<String> it = condition.keySet().iterator();

            while (it.hasNext()) {
                String key = it.next();
                Object o = condition.get(key);
                if (o != null) {
                    String value = o.toString();
                    highlightBuilder.field(key);
                    BoolQueryBuilder mapQuery = QueryBuilders.boolQuery();
                    keyCon(key, value, isphrase, mapQuery);
                    andQuery.should(mapQuery);
                }
            }

        }
    }


    /**
     * 查询文档，根据index，type，以及查询条件
     */
    public static List<Map> get(String index, String type, Map<String, String> condition, int startCount, int endCount, Map<String, String> map, boolean isphrase, int limit) {
        SearchRequestBuilder searchRequestBuilder = getSrb(index, type, condition, startCount, endCount, PTConst.F_TRUE, map, isphrase);
        SearchHits hits = searchRequestBuilder.setSize(limit).execute().actionGet().getHits();
        SearchHit[] hitArr = hits.getHits();
        List<Map> list = new ArrayList<>();
        for (SearchHit hit : hitArr) {
            list.add(hit.getSource());
        }
        return list;
    }

    private static SearchRequestBuilder getSrb(String index, String type, Map condition, int startCount, int endCount,
                                               boolean isCount, Map<String, String> map, boolean isphrase) {
        SearchRequestBuilder searchRequestBuilder = EsClientUtils.getClient().prepareSearch(index)
                .setTypes(type).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.postTags("<h1 color='red'>");
        BoolQueryBuilder andQuery = QueryBuilders.boolQuery();

        if (condition != null) conditionNoNull(andQuery, map, isphrase);

        searchRequestBuilder.setQuery(andQuery);

        if (isCount) {
            startCount = startCount - 1 < 0 ? 0 : startCount - 1;
            int size = endCount - startCount;
            searchRequestBuilder.setFrom(startCount).setSize(size);
        }

        searchRequestBuilder.highlighter(highlightBuilder);
        return searchRequestBuilder;
    }

    /**
     * 聚合查询条数
     */
    public static Map<String, Long> aggreage(AggregationBuilder aggregation, BoolQueryBuilder qb, String index, String type, String aggName) {
        Map<String, Long> m = new HashMap<>();
        SearchResponse sr = EsClientUtils.getClient().prepareSearch(index).setTypes(type)
                .addAggregation(aggregation)
                .setQuery(qb)
                .get();

        Filters agg = sr.getAggregations().get(aggName);
        for (Filters.Bucket entry : agg.getBuckets())
            m.put(entry.getKeyAsString(), entry.getDocCount());

        Map<String, Long> finalMap = new LinkedHashMap<>();
        //Sort a map and add to finalMap
        m.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByKey()
                        .reversed()).forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
        return finalMap;
    }

    /**
     * 获取总条数
     */
    public static long getTotalCount(String index, String type, BoolQueryBuilder qb) {
        SearchResponse response = EsClientUtils.getClient().prepareSearch(index)
                .setTypes(type)
                .setQuery(qb)
                .get();
        return response.getHits().getTotalHits();
    }

    /**
     * 获取总条数
     */
    public static long getTotalCount(String index, String type, String key, String val, boolean isphrase) {
        BoolQueryBuilder orQuery = QueryBuilders.boolQuery();
        if (key.equals(PTConst.BRAND_APPDATE) && StringUtils.isNotEmpty(val)) {
            startDate(val, orQuery);
        } else if (isphrase) {
            orQuery.must(matchPhraseQuery(key, val));
        } else orQuery.must(matchQuery(key, val));
        SearchResponse response = EsClientUtils.getClient().prepareSearch(index)
                .setTypes(type)
                .setQuery(orQuery)
                .get();
        return response.getHits().getTotalHits();
    }


    /**
     * 获取总条数
     */
    public static long getTotalCountIndex(String index, String type, Map condition, Map multiCondition,
                                          Map scopeCondition, Map sortMap, int startCount, int endCount, Map<String, String> map,
                                          boolean isphrase, String[] include, String[] exclude, BoolQueryBuilder qb) {
        SearchRequestBuilder searchRequestBuilder =
                getSrbIndex(index, type, condition,
                        multiCondition, scopeCondition, sortMap, startCount, endCount, false, map, isphrase, include, exclude, qb);
        return searchRequestBuilder.get().getHits().getTotalHits();
    }

    /**
     * 获取总条数
     */
    public static long getTotalCount(String index, String type, Map condition, int startCount, int endCount, Map<String, String> map, boolean isphrase) {
        SearchRequestBuilder searchRequestBuilder =
                getSrb(index, type, condition, startCount, endCount, false, map, isphrase);
        return searchRequestBuilder.get().getHits().getTotalHits();
    }

    public static List<Map> getMsgByKeyWord(String index, String type, BoolQueryBuilder qb, int limit, String key) {
        HighlightBuilder hiBuilder = new HighlightBuilder();
        hiBuilder.preTags("<i>");
        hiBuilder.postTags("</i>");
        hiBuilder.field(key);
        SearchResponse response = EsClientUtils.getClient().prepareSearch(index)
                .setTypes(type)
                .setQuery(qb)
                .highlighter(hiBuilder)
                .setSize(limit)
                .get();
        return getHighList(response.getHits().getHits(), new ArrayList<>());
    }

    private static List<Map> getHighList(SearchHit[] hitArr, List<Map> list) {
        if (hitArr.length > 0)
            for (SearchHit hit : hitArr) {
                Map<String, Object> mm = hit.getSource();
                Map<String, HighlightField> m = hit.getHighlightFields();
                for (String h : m.keySet()) {
                    mm.put("highlight", m.get(h).getName());
                    StringBuffer sb = new StringBuffer();
                    Text[] text = hit.getHighlightFields().get(h).getFragments();
                    for (Text str : text)
                        sb.append(str.string());
                    mm.put("lightVal", sb.toString());
                }
                list.add(mm);
            }
        return list;
    }

    /**
     * 全匹配，简单 KV 格式，限数
     */
    public static List<Map> getMsgByKeyWord(String index, String type, String key, String val, int count) {
        SearchResponse response = EsClientUtils.getClient().prepareSearch(index)
                .setTypes(type)
                .setQuery(matchPhraseQuery(key, val))
                .setSize(count)
                .get();
        SearchHit[] hitArr = response.getHits().getHits();
        List<Map> list = new ArrayList<>();
        if (hitArr.length > 0)
            for (SearchHit hit : hitArr) {
                list.add(hit.getSource());
            }
        return list;
    }

    /**
     * 非全匹配，简单 KV 格式，限数
     */
    public static List<Map> getMsgByKeyWord(String index, String type, BoolQueryBuilder qb, int startCount, int endCount, String[] include, String[] excludes, String sortKey) {
        SearchRequestBuilder searchRequestBuilder = EsClientUtils.getClient().prepareSearch(index)
                .setTypes(type).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        startCount = startCount - 1 < 0 ? 0 : startCount - 1;
        searchRequestBuilder.setQuery(qb);
        searchRequestBuilder.setFetchSource(include, excludes);
        if (StringUtils.isNotEmpty(sortKey))
            searchRequestBuilder.addSort(sortKey, SortOrder.DESC);
        searchRequestBuilder.setFrom(startCount).setSize(endCount - startCount);
        SearchHits hits = searchRequestBuilder.execute().actionGet().getHits();
        SearchHit[] hitArr = hits.getHits();
        List<Map> list = new ArrayList<>();
        for (SearchHit hit : hitArr) {
            list.add(hit.getSource());
        }
        return list;
    }

    /**
     * 首页获取前五
     */
    public static List<Map> getPart(String index, String type, QueryBuilder qb, String[] include, String[] excludes, int count, String sortKey) {
        HighlightBuilder hiBuilder = new HighlightBuilder();
        hiBuilder.preTags("<i style=\"color:#fd485e\">");
        hiBuilder.postTags("</i>");
        if (include != null && include.length > 0)
            for (int i = 0; i < include.length; i++)
                hiBuilder.field(include[i]);
        SearchRequestBuilder searchRequestBuilder = EsClientUtils.getClient().prepareSearch(index).setTypes(type);
        searchRequestBuilder.setQuery(qb);
        searchRequestBuilder.highlighter(hiBuilder);
        searchRequestBuilder.setFetchSource(include, excludes);
        if (StringUtils.isNotEmpty(sortKey))
            searchRequestBuilder.addSort(sortKey, SortOrder.DESC);
        searchRequestBuilder.setSize(count);
        SearchResponse response = searchRequestBuilder.get();
        return getHighList(response.getHits().getHits(), new ArrayList<>());
    }


    public static void patentDateQuery(BoolQueryBuilder q, String queryKey, String startDate, String endDate) {
        q.must(QueryBuilders.rangeQuery(queryKey).from(startDate).to(endDate));
    }


    public static void dealProvinceOrIndustry(String value, BoolQueryBuilder orQuery, String key) {
        if (value.contains("-")) {
            String[] val = value.split("-");
            if (val.length > 0)
                for (int i = 0; i < val.length; i++)
                    orQuery.must(matchPhraseQuery(key, val[i]));
        } else orQuery.must(matchPhraseQuery(key, value));
    }

    public static void dealRegCapi(String value, BoolQueryBuilder orQuery) {
        if (value.contains("-")) {
            String[] reg = value.split("-");
            orQuery.must(QueryBuilders.rangeQuery(PTConst.QYGS_REGISTCAPI).from(reg[0]).to(reg[1]));
        } else orQuery.must(QueryBuilders.rangeQuery(PTConst.QYGS_REGISTCAPI).from(value));
    }

    public static void startDate(String value, BoolQueryBuilder orQuery) {
        if (value.contains("-")) {
            String[] reg = value.split("-");
            if (reg[0].equals(reg[1])) dealStartDate(reg[0], orQuery);
            else {
                final String one = reg[0].concat(DATESUFIX);
                final String two = String.valueOf(Integer.valueOf(reg[1])).concat(DATESUFIX);
                orQuery.must(QueryBuilders.rangeQuery(PTConst.BRAND_APPDATE).from(one).to(two));
            }
        } else dealStartDate(value, orQuery);
    }

    public static void dealStartDate(String value, BoolQueryBuilder orQuery) {
        final String one = value.concat(DATESUFIX);
        final String two = String.valueOf(Integer.valueOf(value) + 1).concat(DATESUFIX);
        orQuery.must(QueryBuilders.rangeQuery(PTConst.BRAND_APPDATE).from(one).to(two));
    }

    private static void keyCon(String key, String value, boolean isphrase, BoolQueryBuilder orQuery) {
        if (key.equals(PTConst.BRAND_TYPE) && StringUtils.isNotEmpty(value)) { //申请类别
            dealBrandApplyType(orQuery, value, key);
        } else if (key.equals(PTConst.BRAND_APPDATE) && StringUtils.isNotEmpty(value)) { //申请年份
            startDate(value, orQuery);
        } else if (key.equals(PTConst.QYGS_PROVINCE) || key.equals(PTConst.QYGS_INDUSTRY) && StringUtils.isNotEmpty(value)) { //省份或行业
            dealProvinceOrIndustry(value, orQuery, key);
        } else
            dealMoreValue(orQuery, key, value, isphrase);
    }

    public static void dealBrandApplyType(BoolQueryBuilder orQuery, String value, String key) {
        if (value.contains("-")) {
            String[] v = value.split("-");
            BoolQueryBuilder orQuery2 = QueryBuilders.boolQuery();
            for (int i = 0; i < v.length; i++)
                orQuery2.should(matchPhraseQuery(key, EsUtils.converBrandType(v[i])));
            orQuery.must(orQuery2);
        } else
            orQuery.must(QueryBuilders.matchPhraseQuery(key, EsUtils.converBrandType(value)));
    }

    public static void dealMoreValue(BoolQueryBuilder qb, String key, String value, boolean flag) {
        if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
            if (value.contains("-")) {
                String[] v = value.split("-");
                for (int i = 0; i < v.length; i++) {
                    if (flag)
                        qb.must(matchPhraseQuery(key, v[i]));
                    else
                        qb.must(matchQuery(key, v[i]));
                }
            } else if (flag) qb.must(matchPhraseQuery(key, value));
            else qb.must(matchQuery(key, value));
        }
    }

    public static String converBrandType(String brandType) {
        String bt = brandType.split("类")[0];
        if (bt.substring(0, 1).equals("0"))
            bt = bt.substring(1, 2);
        return bt.concat("类");
    }

    public static String converBrandSmallType(String brandSmallType) {
        return brandSmallType.substring(0, 4);
    }

    private static void keyNoCon(String key, String value, boolean isphrase, BoolQueryBuilder orQuery) {
        String[] values = value.split(",");
        for (String val : values) {
            if (val.contains(".")) {
                String[] keys = val.split("\\.");
                if (isphrase)
                    orQuery.must(QueryBuilders.nestedQuery(keys[0], matchPhraseQuery(val, value), ScoreMode.Max));
                else
                    orQuery.must(QueryBuilders.nestedQuery(keys[0], QueryBuilders.matchQuery(val, value), ScoreMode.Max));
            } else {
                if (key.contains(","))
                    orQuery.must(QueryBuilders.multiMatchQuery(val, key.split(",")));
                else {
                    if (isphrase) orQuery.must(matchPhraseQuery(key, val));
                    else orQuery.must(QueryBuilders.matchQuery(key, val));
                }
            }
        }
    }

    private static void multiCon(Map multiCondition, HighlightBuilder highlightBuilder, BoolQueryBuilder andQuery) {
        Iterator<String> miltiIte = multiCondition.keySet().iterator();
        while (miltiIte.hasNext()) {
            String key = miltiIte.next();
            highlightBuilder.field(key);
            String[] value = (String[]) multiCondition.get(key);
            andQuery.must(QueryBuilders.multiMatchQuery(key, value));
        }
    }

    private static void mapNoNullIndex(HighlightBuilder highlightBuilder, BoolQueryBuilder andQuery, Map<String, String> map, boolean isphrase) {
        if (map != null && map.size() > 0) {
            logger.info("==========map============" + JSON.toJSONString(map));
            Iterator<String> it = map.keySet().iterator();

            while (it.hasNext()) {
                String key = it.next();
                Object o = map.get(key);
                if (o != null) {
                    String value = o.toString();
                    highlightBuilder.field(key);
//                    conIndexDeal(key, value, isphrase, mapQuery);
                    keyCon(key, value, isphrase, andQuery);
                }
            }
        }
    }

    private static void conditionNoNull(BoolQueryBuilder andQuery, Map<String, String> map, boolean isphrase) {
        if (map != null && map.size() > 0) {
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                Object o = map.get(key);
                if (o != null) {
                    String value = o.toString();
                    BoolQueryBuilder mapQuery = QueryBuilders.boolQuery();
                    if (key.contains(".")) {
                        keyCon(key, value, isphrase, mapQuery);
                    } else {
                        keyNoCon(key, value, isphrase, mapQuery);
                    }
                    andQuery.must(mapQuery);
                }
            }
        }
    }


    public static List<Map> getDataOne(String index, String type, BoolQueryBuilder qb, int count) {
        SearchResponse response = EsClientUtils.getClient().prepareSearch(index)
                .setTypes(type)
                .setQuery(qb)
                .setSize(count)
                .get();
        SearchHit[] hitArr = response.getHits().getHits();
        List<Map> list = new ArrayList<>();
        if (hitArr.length > 0)
            for (SearchHit hit : hitArr) {
                list.add(hit.getSource());
            }
        return list;
    }


    public static Map<String, Long> getDistinctText(String index, String
            type, Map<String, Object> condition, String field, int count) {
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms(field).field(field.concat(".keyword")).size(count);
        AggregationBuilder countAggregationBuilder = AggregationBuilders.count("count").field(field);
        aggregationBuilder.subAggregation(countAggregationBuilder);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        EsUtils.addConditionToBoolQueryBuilder(boolQueryBuilder, condition);
        SearchRequestBuilder searchRequestBuilder = EsClientUtils.getClient().prepareSearch(index)
                .setTypes(type)
                .addAggregation(aggregationBuilder)
                .setQuery(boolQueryBuilder);
        searchRequestBuilder.setFrom(0).setSize(1);
        Map<String, Long> reduce = new LinkedHashMap<>();
        SearchResponse searchResponse = searchRequestBuilder.get();
        Aggregations terms = searchResponse.getAggregations();
        for (Aggregation a : terms) {
            StringTerms teamSum = (StringTerms) a;
            for (StringTerms.Bucket bucket : teamSum.getBuckets()) {
                reduce.put(bucket.getKeyAsString(), ((ValueCount) bucket.getAggregations().asMap().get("count")).getValue());
            }
        }
        return reduce;
    }

    /**
     * 查询最大的FIELD值
     */
    public static Double getMax(String index, String type, String fieldName, Map<String, Object> condition, int from, int size) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        addConditionToBoolQueryBuilder(boolQueryBuilder, condition);
        AggregationBuilder aggregationBuilder = AggregationBuilders.max(fieldName).field(fieldName);
        SearchRequestBuilder searchRequestBuilder = EsClientUtils.getClient().prepareSearch(index)
                .setTypes(type)
                .setQuery(boolQueryBuilder)
                .addAggregation(aggregationBuilder);
        searchRequestBuilder.setFrom(from);
        if (size > 0) {
            searchRequestBuilder.setSize(size);
        }
        SearchResponse searchResponse = searchRequestBuilder.get();
        Max agg = searchResponse.getAggregations().get(fieldName);
        val max = agg.getValue();
        return max;
    }

    /**
     * 查询数量
     *
     * @param index
     * @param type
     * @param condition
     * @return
     */
    public static Long count(String index, String type, Map<String, Object> condition) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        addConditionToBoolQueryBuilder(boolQueryBuilder, condition);
        SearchRequestBuilder searchRequestBuilder = EsClientUtils.getClient().prepareSearch(index)
                .setTypes(type)
                .setQuery(boolQueryBuilder);
        SearchResponse searchResponse = searchRequestBuilder.get();
        return searchResponse.getHits().getTotalHits();
    }

    public static List<Map<String, Object>> getList(String index, String type, Map<String, Object> condition, int from, int size) {
        return getList(index, type, condition, from, size, null, null);
    }

    /**
     * 根据匹配的数据项进行查询
     *
     * @param index
     * @param type
     * @return
     */
    public static List<Map<String, Object>> getList(String index, String type, Map<String, Object> condition, int from, int size, String[] orderByAsc, String[] orderByDesc) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        addConditionToBoolQueryBuilder(boolQueryBuilder, condition);
        SearchRequestBuilder searchRequestBuilder = EsClientUtils.getClient().prepareSearch(index)
                .setTypes(type)
                .setQuery(boolQueryBuilder);
        if (orderByAsc != null && orderByAsc.length > 0) {
            for (String field : orderByAsc) {
                searchRequestBuilder.addSort(field, SortOrder.ASC);
            }
        }
        if (orderByDesc != null && orderByDesc.length > 0) {
            for (String field : orderByDesc) {
                searchRequestBuilder.addSort(field, SortOrder.DESC);
            }
        }
        searchRequestBuilder.setFrom(from);
        if (size > 0) {
            searchRequestBuilder.setSize(size);
        }
        SearchResponse searchResponse = searchRequestBuilder.get();
        Iterator<SearchHit> hits = searchResponse.getHits().iterator();
        List<Map<String, Object>> obj = new ArrayList<>();
        while (hits.hasNext()) {
            SearchHit hit = hits.next();
            obj.add(hit.getSource());
        }
        return obj;
    }

    /**
     * @param index
     * @param type
     * @param matchData
     * @param field
     * @return
     */
    public static List<Object> getFieldValueList(String index, String type, Map<String, Object> matchData, String field) {
        int bat_size = 10000;
        int time_out = 2000;
        TransportClient client = EsClientUtils.getClient();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        addConditionToBoolQueryBuilder(boolQueryBuilder, matchData);
        SearchResponse searchResponse = client.prepareSearch(index).setSearchType(SearchType.QUERY_THEN_FETCH)
                .setTypes(type).setSize(bat_size)
                .setQuery(boolQueryBuilder).setScroll(TimeValue.timeValueMinutes(200)).execute().actionGet();
        long totalCount = searchResponse.getHits().getTotalHits();
        int page = (int) totalCount / bat_size;
        List<Object> obj = new ArrayList<>();
        Iterator<SearchHit> hits = searchResponse.getHits().iterator();
        while (hits.hasNext()) {
            SearchHit hit = hits.next();
            obj.add(hit.getSource().get(field));
        }
        for (int i = 0; i < page; i++) {
            searchResponse = client.prepareSearchScroll(searchResponse.getScrollId())
                    .setScroll(new TimeValue(time_out)).execute()
                    .actionGet();
            hits = searchResponse.getHits().iterator();
            while (hits.hasNext()) {
                SearchHit hit = hits.next();
                obj.add(hit.getSource().get(field));
            }
        }
        client.prepareClearScroll().addScrollId(searchResponse.getScrollId()).execute();
        return obj;
    }

    /**
     * @param index
     * @param type
     * @param matchData
     * @return
     */
    public static List<Map<String, Object>> getAll(String index, String type, Map<String, Object> matchData) {
        int bat_size = 10000;
        int time_out = 2000;
        TransportClient client = EsClientUtils.getClient();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        addConditionToBoolQueryBuilder(boolQueryBuilder, matchData);
        SearchResponse searchResponse = client.prepareSearch(index)
                .setTypes(type).setSize(bat_size)
                .setQuery(boolQueryBuilder).setScroll(TimeValue.timeValueMinutes(200)).execute().actionGet();
        long totalCount = searchResponse.getHits().getTotalHits();
        int page = (int) totalCount / bat_size;
        List<Map<String, Object>> obj = new ArrayList<>();
        Iterator<SearchHit> hits = searchResponse.getHits().iterator();
        while (hits.hasNext()) {
            SearchHit hit = hits.next();
            obj.add(hit.getSource());
        }
        for (int i = 0; i < page; i++) {
            searchResponse = client.prepareSearchScroll(searchResponse.getScrollId())
                    .setScroll(new TimeValue(time_out)).execute()
                    .actionGet();
            hits = searchResponse.getHits().iterator();
            while (hits.hasNext()) {
                SearchHit hit = hits.next();
                obj.add(hit.getSource());
            }
        }
        client.prepareClearScroll().addScrollId(searchResponse.getScrollId()).execute();
        return obj;
    }


    /**
     * 性能优化
     *
     * @param index
     * @param type
     * @param matchData
     * @param callback
     * @return
     */
    public static <T> List<T> execute(String index, String type, Map<String, Object> matchData, ScanCallback callback, int batch_size) {
        Console console = new StoreConsole();
        int bat_size = batch_size;
        int time_out = 2000;
        TransportClient client = EsClientUtils.getClient();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        addConditionToBoolQueryBuilder(boolQueryBuilder, matchData);
        SearchResponse searchResponse = client.prepareSearch(index)
                .setTypes(type).setSize(bat_size).addSort(SortBuilders.scoreSort()).addSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                .setQuery(boolQueryBuilder).setScroll(TimeValue.timeValueMinutes(200)).execute().actionGet();
        long totalCount = searchResponse.getHits().getTotalHits();
        int page = (int) totalCount / bat_size;
        Iterator<SearchHit> hits = searchResponse.getHits().iterator();
        while (hits.hasNext() && !console.isStop()) {
            SearchHit hit = hits.next();
            callback.arrived(console, hit);
        }
        for (int i = 0; i < page && !console.isStop(); i++) {
            searchResponse = client.prepareSearchScroll(searchResponse.getScrollId())
                    .setScroll(new TimeValue(time_out)).execute()
                    .actionGet();
            hits = searchResponse.getHits().iterator();
            while (hits.hasNext() && !console.isStop()) {
                SearchHit hit = hits.next();
                callback.arrived(console, hit);
            }
        }
        client.prepareClearScroll().addScrollId(searchResponse.getScrollId()).execute();
        return (List<T>) console.getList();
    }


    public static void addConditionToBoolQueryBuilder(BoolQueryBuilder boolQueryBuilder, Map<String, Object> condition) {
        for (String key : condition.keySet()) {
            Object val = condition.get(key);
            String[] keyArr = key.split(" ");
            String field = keyArr[0];
            String operation = "=";
            if (keyArr.length > 1) {
                operation = keyArr[1];
            }
            switch (operation) {
                case "keyword":
                    boolQueryBuilder.must(QueryBuilders.matchQuery(field.concat(".keyword"), val));
                    break;
                case "=":
                    if (val instanceof List) {
                        for (Object v : (List) val) {
                            boolQueryBuilder.must(QueryBuilders.termQuery(field, v));
                        }
                    } else if (val instanceof Object[]) {
                        for (Object v : (Object[]) val) {
                            boolQueryBuilder.must(QueryBuilders.termQuery(field, v));
                        }
                    } else {
                        boolQueryBuilder.must(QueryBuilders.termQuery(field, val));
                    }
                    break;
                case "<":
                    boolQueryBuilder.must(QueryBuilders.rangeQuery(field).lt(val));
                    break;
                case ">":
                    boolQueryBuilder.must(QueryBuilders.rangeQuery(field).gt(val));
                    break;
                case ">=":
                    boolQueryBuilder.must(QueryBuilders.rangeQuery(field).gte(val));
                    break;
                case "<=":
                    boolQueryBuilder.must(QueryBuilders.rangeQuery(field).lte(val));
                    break;
                case "like":
                    boolQueryBuilder.must(QueryBuilders.wildcardQuery(field.concat(".keyword"), (String) val));
                    break;
                case "near":
                    boolQueryBuilder.must(QueryBuilders.matchQuery(field, val));
                    break;
                case "regexp":
                    boolQueryBuilder.must(QueryBuilders.regexpQuery(field, (String) val));
                    break;
                case "keywordIn":
                    if (val instanceof Collection) {
                        boolQueryBuilder.must(QueryBuilders.termsQuery(field.concat(".keyword"), (Collection<?>) val));
                    } else if (val instanceof Object[]) {
                        boolQueryBuilder.must(QueryBuilders.termsQuery(field.concat(".keyword"), (Object[]) val));
                    }
                    break;
                case "in":
                    if (val instanceof Collection) {
                        boolQueryBuilder.must(QueryBuilders.termsQuery(field, (Collection<?>) val));
                    } else if (val instanceof Object[]) {
                        boolQueryBuilder.must(QueryBuilders.termsQuery(field, (Object[]) val));
                    }
                    break;
                case "contains":
                    if (val instanceof Collection) {
                        BoolQueryBuilder bool1 = QueryBuilders.boolQuery();
                        for (Object obj : (Collection) val) {
                            bool1.must(QueryBuilders.wildcardQuery(field, "*" + obj + "*"));
                        }
                        boolQueryBuilder.must(bool1);
                    } else if (val instanceof Object[]) {
                        BoolQueryBuilder bool1 = QueryBuilders.boolQuery();
                        for (Object obj : (Object[]) val) {
                            bool1.must(QueryBuilders.wildcardQuery(field, "*" + obj + "*"));
                        }
                        boolQueryBuilder.must(bool1);
                    }
                case "user":
                    if (val instanceof UserBoolQueryBuilder) {
                        ((UserBoolQueryBuilder) val).exec(field, boolQueryBuilder);
                    }
                    break;
            }
        }

    }

}
