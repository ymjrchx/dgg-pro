package net.dgg.tmd.foundation.platform.utils.es;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dgg.tmd.foundation.platform.service.es.EsClientService;
import net.dgg.tmd.foundation.platform.utils.es.stack.Console;
import net.dgg.tmd.foundation.platform.utils.es.stack.ScanCallback;
import net.dgg.tmd.foundation.platform.utils.es.stack.StoreConsole;
import net.dgg.tmd.foundation.platform.utils.es.stack.UserBoolQueryBuilder;
import net.dgg.tmd.foundation.platform.utils.spring.SpringBeanUtil;
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
    private static final Gson gson = new Gson();

    /**
     * 添加文档
     */
    public static void add(String index, String type, String id, String json) {
        try {
            SpringBeanUtil.getBean(EsClientService.class).getClient().prepareIndex(index, type, id).setSource(json, XContentType.JSON).get();
        } catch (Exception e) {
            logger.error("add文档时出现异常：e=" + e + " json=" + json, e);
        }
    }

    /**
     * 添加文档
     */
    public static void add(String index, String type, String id, Map<String, Object> source) {
        IndexResponse indexResponse = SpringBeanUtil.getBean(EsClientService.class).getClient().prepareIndex(index, type, id).setSource(source).get();
        System.out.println(indexResponse.getVersion());
    }

    /**
     * 添加文档
     */
    public static void add(String index, String type, String id, Object object) {
        try {
            String json = gson.toJson(object);
            SpringBeanUtil.getBean(EsClientService.class).getClient().prepareIndex(index, type, id).setSource(json, XContentType.JSON).get();
        } catch (Exception e) {
            logger.error("插入ES出错" + "index:" + index + ",type:" + type, e);
        }
    }

    /**
     * 更新文档
     */
    public static void update(String index, String type, String id, Map source) throws IOException {
        UpdateResponse updateResponse = SpringBeanUtil.getBean(EsClientService.class).getClient().prepareUpdate(index, type, id).setDoc(source).get();
        System.out.println(updateResponse.getVersion());
    }

    /**
     * 删除索引
     */
    public static void delete(String index, String type, String id) {
        DeleteResponse deleteResponse = SpringBeanUtil.getBean(EsClientService.class).getClient().prepareDelete(index, type, id).get();
        deleteResponse.status();
    }

    /**
     * 根据ID查询文档
     */
    public static Map get(String index, String type, String id) {
        GetResponse response = SpringBeanUtil.getBean(EsClientService.class).getClient().prepareGet(index, type, id).get();
        return response.getSource();
    }


    public static List<Map> getDataOne(String index, String type, BoolQueryBuilder qb, int count) {
        SearchResponse response = SpringBeanUtil.getBean(EsClientService.class).getClient().prepareSearch(index)
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
        SearchRequestBuilder searchRequestBuilder = SpringBeanUtil.getBean(EsClientService.class).getClient().prepareSearch(index)
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
        SearchRequestBuilder searchRequestBuilder = SpringBeanUtil.getBean(EsClientService.class).getClient().prepareSearch(index)
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
        SearchRequestBuilder searchRequestBuilder = SpringBeanUtil.getBean(EsClientService.class).getClient().prepareSearch(index)
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
        SearchRequestBuilder searchRequestBuilder = SpringBeanUtil.getBean(EsClientService.class).getClient().prepareSearch(index)
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
        TransportClient client = SpringBeanUtil.getBean(EsClientService.class).getClient();
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
        TransportClient client = SpringBeanUtil.getBean(EsClientService.class).getClient();
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
        TransportClient client = SpringBeanUtil.getBean(EsClientService.class).getClient();
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
