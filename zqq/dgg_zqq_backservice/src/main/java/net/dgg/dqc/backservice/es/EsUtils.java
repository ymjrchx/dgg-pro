package net.dgg.dqc.backservice.es;

import com.alibaba.fastjson.JSON;
import net.dgg.dqc.backservice.constant.PTConst;
import net.dgg.dqc.backservice.utils.BaseUtil;
import net.dgg.dqc.backservice.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filters.Filters;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * Created by wu on 2017/8/24.
 */
public class EsUtils extends BaseUtil {

    public static Log logger = LogFactory.getLog(EsUtils.class);
    private static final String DATESUFIX = "-01-01";

    /**
     * 添加文档
     */
    public static void add(String index, String type, String id, String json) {
        try {
            EsClientUtils.getClient().prepareIndex(index,type,id).setSource(json, XContentType.JSON).get();
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
            EsClientUtils.getClient().prepareIndex(index,type,id).setSource(json, XContentType.JSON).get();
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
     * 获取列表数据
     */
    public static List<Map> getListMapByIndex(String index, String type, Map<String, String> condition, Map multiCondition,
                                              Map scopeCondition, Map sortMap, int startCount, int endCount, Map<String, String> map, boolean isphrase) {

        SearchRequestBuilder searchRequestBuilder =
                getSrbIndex(index, type, condition, multiCondition, scopeCondition, sortMap, startCount, endCount, PTConst.F_TRUE, map, isphrase);
        SearchHits hits = searchRequestBuilder.setSize(endCount - startCount).execute().actionGet().getHits();
        SearchHit[] hitArr = hits.getHits();
        List<Map> list = new ArrayList<>();
        for (SearchHit hit : hitArr) {
            list.add(hit.getSource());
        }
        return list;
    }


    private static SearchRequestBuilder getSrbIndex(String index, String type, Map condition, Map multiCondition,
                                                    Map scopeCondition, Map sortMap, int startCount, int endCount,
                                                    boolean isCount, Map<String, String> map, boolean isphrase) {
        SearchRequestBuilder searchRequestBuilder = EsClientUtils.getClient().prepareSearch(index)
                .setTypes(type).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.postTags("<h1 color='red'>");
        BoolQueryBuilder andQuery = QueryBuilders.boolQuery();

        if (condition != null) conditionNoNullIndex(highlightBuilder, andQuery, map, isphrase);

        if (multiCondition != null) multiCon(multiCondition, highlightBuilder, andQuery);

        if (scopeCondition != null) {
            Iterator<String> scopeIte = scopeCondition.keySet().iterator();
            while (scopeIte.hasNext()) {
                String key = scopeIte.next();
                String value = (String) scopeCondition.get(key);
                highlightBuilder.field(value);
                String[] values = value.split("#");
                RangeQueryBuilder rangeQery = QueryBuilders.rangeQuery(key).from(values[0]).to(values[1]);
                andQuery.must(rangeQery);
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
    public static Map<String, Long> aggreage(AggregationBuilder aggregation, BoolQueryBuilder qb, String index, String type) {
        Map<String, Long> m = new HashMap<>();
        SearchResponse sr = EsClientUtils.getClient().prepareSearch(index).setTypes(type)
                .addAggregation(aggregation)
                .setQuery(qb)
                .get();
        Filters agg = sr.getAggregations().get(PTConst.AGGREATE_LIST);
        for (Filters.Bucket entry : agg.getBuckets())
            m.put(entry.getKeyAsString(), entry.getDocCount());
        return m;
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
        if (key.equals(PTConst.QYGS_STARTDATE) && StringUtils.isNotEmpty(val)) {
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
                                          Map scopeCondition, Map sortMap, int startCount, int endCount, Map<String, String> map, boolean isphrase) {
        SearchRequestBuilder searchRequestBuilder =
                getSrbIndex(index, type, condition,
                        multiCondition, scopeCondition, sortMap, startCount, endCount, false, map, isphrase);
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
    public static List<Map> getMsgByKeyWord(String index, String type, String key, String val, int count, boolean isphrase) {
        BoolQueryBuilder orQuery = QueryBuilders.boolQuery();
        if (isphrase) orQuery.must(matchPhraseQuery(key, val));
        else orQuery.must(matchQuery(key, val));
        SearchResponse response = EsClientUtils.getClient().prepareSearch(index)
                .setTypes(type)
                .setQuery(orQuery)
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
     * 首页获取前五
     */
    public static List<Map> getPart(String index, String type, QueryBuilder qb, String[] include, String[] excludes, int count) {
        HighlightBuilder hiBuilder = new HighlightBuilder();
        hiBuilder.preTags("<i style=\"color:#fd485e\">");
        hiBuilder.postTags("</i>");
        if (include != null && include.length > 0)
            for (int i = 0; i < include.length; i++)
                hiBuilder.field(include[i]);
        SearchResponse response = EsClientUtils.getClient().prepareSearch(index)
                .setTypes(type)
                .setQuery(qb)
                .highlighter(hiBuilder)
                .setFetchSource(include, excludes)
                .addSort(PTConst.QYGS_REGISTCAPI, SortOrder.DESC)
                .setSize(count)
                .get();
        return getHighList(response.getHits().getHits(), new ArrayList<>());
    }

    private static void conIndexDeal(String key, String value, boolean isphrase, BoolQueryBuilder orQuery) {
        if (key.equals(PTConst.QYGS_REGISTCAPI) && StringUtils.isNotEmpty(value)) { //处理注册资金搜索问题
            dealRegCapi(value, orQuery);
        } else if (key.equals(PTConst.QYGS_STARTDATE) && StringUtils.isNotEmpty(value)) { //自定义成立日期
            startDate(value, orQuery);
        } else if (key.equals(PTConst.QYGS_PROVINCE) || key.equals(PTConst.QYGS_INDUSTRY) && StringUtils.isNotEmpty(value)) { //省份或行业
            dealProvinceOrIndustry(value, orQuery, key);
        } else {
            if (isphrase) orQuery.must(matchPhraseQuery(key, value));
            else orQuery.must(QueryBuilders.matchQuery(key, value));
        }
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
            else
                orQuery.must(QueryBuilders.rangeQuery(PTConst.QYGS_STARTDATE).from(reg[0].concat(DATESUFIX)).to(reg[1].concat(DATESUFIX)));
        } else dealStartDate(value, orQuery);
    }

    public static void dealStartDate(String value, BoolQueryBuilder orQuery) {
        final String two = String.valueOf(Integer.valueOf(value) + 1);
        orQuery.must(QueryBuilders.rangeQuery(PTConst.QYGS_STARTDATE).from(value.concat(DATESUFIX)).to(two.concat(DATESUFIX)));
    }

    private static void keyCon(String key, String value, boolean isphrase, BoolQueryBuilder orQuery) {
        if (key.equals(PTConst.QYGS_REGISTCAPI) && StringUtils.isNotEmpty(value)) { //处理注册资金搜索问题
            dealRegCapi(value, orQuery);
        } else if (key.equals(PTConst.QYGS_STARTDATE) && StringUtils.isNotEmpty(value)) { //自定义成立日期
            startDate(value, orQuery);
        } else if (key.equals(PTConst.QYGS_PROVINCE) || key.equals(PTConst.QYGS_INDUSTRY) && StringUtils.isNotEmpty(value)) { //省份或行业
            dealProvinceOrIndustry(value, orQuery, key);
        } else {
            if (isphrase) orQuery.must(matchPhraseQuery(key, value));
            else orQuery.must(QueryBuilders.matchQuery(key, value));
        }
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

    private static void conditionNoNullIndex(HighlightBuilder highlightBuilder, BoolQueryBuilder andQuery, Map<String, String> map, boolean isphrase) {
        if (map != null && map.size() > 0) {
            logger.info("==========map============" + JSON.toJSONString(map));
            Iterator<String> it = map.keySet().iterator();

            while (it.hasNext()) {
                String key = it.next();
                Object o = map.get(key);
                if (o != null) {
                    String value = o.toString();
                    highlightBuilder.field(key);
                    BoolQueryBuilder mapQuery = QueryBuilders.boolQuery();
                    conIndexDeal(key, value, isphrase, mapQuery);
                    andQuery.must(mapQuery);
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


    /**
     * 从类的字段映射处elasticsearch中的字段
     */
    public static String getClassMapping(Class cls) {
        StringBuilder fieldstring = new StringBuilder();
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fieldstring.append("\t\t\t\t\"" + fields[i].getName().toLowerCase() + "\": {\n");

            if (fields[i].getType().getSimpleName().equals("List")) {
                //list做特殊处理
                ParameterizedType pt = (ParameterizedType) fields[i].getGenericType();
                Class clz = (Class) pt.getActualTypeArguments()[0];
                fieldstring.append("\t\t\t\t\t\t\"type\": \"nested\"\n,\"properties\":{" + getClassMapping(clz) + "}\n");

            } else {
                fieldstring.append("\t\t\t\t\t\t\"type\": \""
                        + GetElasticSearchMappingType(fields[i].getType().getSimpleName()) + "\n");
            }

            if (i == fields.length - 1) {
                fieldstring.append("\t\t\t\t\t}\n");
            } else {
                fieldstring.append("\t\t\t\t\t},\n");
            }
        }
        return fieldstring.toString();
    }

    private static String GetElasticSearchMappingType(String varType) {
        String es;
        switch (varType) {
            case "Date":
                es = "date\"\n" + "\t\t\t\t\t\t,\"format\":\"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd \"\n" + "\t\t\t\t\t\t,\"null_value\":\"1900-01-01\"";
                break;
            case "Double":
                es = "double\"\n" + "\t\t\t\t\t\t,\"null_value\":\"NaN\"";
                break;
            case "Long":
                es = "long\"";
                break;
            case "Map":
                es = "object\"";
                break;
            default:
//                es = "string\"";
                es = "multi_field\"";
                break;
        }
        return es;
    }

    public static boolean exists(String index) {
        IndicesAdminClient adminClient = EsClientUtils.getClient().admin().indices();
        IndicesExistsRequest request = new IndicesExistsRequest(index);
        IndicesExistsResponse response = adminClient.exists(request).actionGet();
        if (response.isExists()) {
            return true;
        }
        return false;
    }

    public static void createMapping(String index, String type, Class cls, String key) {
        try {
            String template = "{\n"
                    + "\t\"" + type + "\":{\n"
                    + "\t\t\"date_detection\":\"false\","
                    + "\t\t\"properties\":{\n"
                    + "\t\t{#}\n"
                    + "\t\t\t\t}\n"
                    + "\t\t}\n"
                    + "}";
            String fieldString = getClassMapping(cls);
            template = template.replace("{#}", fieldString);
            System.out.println(template);
            IndicesAdminClient indicesAdminClient = EsClientUtils.getClient().admin().indices();
            if (StringUtils.isNotEmpty(key)) {
                PutMappingRequest mapping = Requests.putMappingRequest(index).type(type).source(createIKMapping(key));
                indicesAdminClient.prepareCreate(index).execute().actionGet();
                indicesAdminClient.putMapping(mapping).actionGet();
            }
            indicesAdminClient.preparePutMapping(index).setType(type).setSource(template, XContentType.JSON).get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static XContentBuilder createIKMapping(String key) {
        if (StringUtils.isEmpty(key)) key = "name";
        XContentBuilder mapping = null;
        try {
            mapping = XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject("properties")
                    .startObject(key).field("type", "text").field("analyzer", "ik_max_word").endObject()
                    .endObject()
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapping;

    }

    public static void main(String[] args) {
//        createMapping(EsConst.INDEX, EsConst.TYPE_COMPANY, QccCompany.class);
    }

}
