package net.dgg.bigdata.sjjs.web.constant;

import net.dgg.bigdata.common.constant.BConst;
import net.dgg.bigdata.sjjs.web.entity.dto.search.workbench.Kv;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filters.FiltersAggregator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by jiangsh on 2018/8/16 19:05
 */
public interface ConstMethod {

    /**
     * 简单分页封装
     * @param pageIndex 开始页
     * @param pageSize 多少条
     * @return Map<String, Object>
     */
    default Map<String, Object> pages(int pageIndex, int pageSize) {
        return dataMap(pageIndex, pageSize);
    }

    /**
     *  分页简单封装
     * @param pageIndex 开始页
     * @param pageSize 多少条
     * @param count 限制条数
     * @return Map<String, Object>
     */
    default Map<String, Object> pageService(int pageIndex, int pageSize, int count) {
        if (count > 0 && pageSize > count) pageSize = count;
        return dataMap(pageIndex, pageSize);
    }

    /**
     * 开始条
     * @param pageIndex 开始页
     * @param pageSize 多少条
     * @return
     */
    default int startCount(int pageIndex, int pageSize) {
        return Integer.valueOf(dataMap(pageIndex, pageSize).get("startCount").toString());
    }

    /**
     * 结束条
     * @param pageIndex 开始页
     * @param pageSize 多少条
     * @return
     */
    default int endCount(int pageIndex, int pageSize) {
        return Integer.valueOf(dataMap(pageIndex, pageSize).get("endCount").toString());
    }

    default Map<String, Object> dataMap(int pageIndex, int pageSize) {
        Map<String, Object> dataMap = new HashMap<>();
        int startCount = 1;
        int endCount = 10;
        if (pageIndex <= 0) pageIndex = 1;
        if (pageSize <= 0) pageSize = 10;
        else endCount = pageSize;
        if (pageIndex > 1) {
            startCount = (pageIndex - 1) * pageSize + 1;
            endCount = pageIndex * pageSize;
        }
        dataMap.put("pageIndex", pageIndex);
        dataMap.put("pageSize", pageSize);
        dataMap.put("startCount", startCount);
        dataMap.put("endCount", endCount);
        return dataMap;
    }

    /**
     *  获取 SearchRequestBuilder
     * @param searchRequestBuilder 请求builder
     * @param boolQueryBuilder 条件builder
     * @param include 包含列
     * @param exclude 排除列
     * @param startCount 开始条
     * @param endCount 结束条
     * @param isCount 标志判断
     * @return
     */
    default SearchRequestBuilder searchRequestBuilder(SearchRequestBuilder searchRequestBuilder, BoolQueryBuilder boolQueryBuilder,
                                                      String[] include, String[] exclude, int startCount, int endCount, boolean isCount) {
        searchRequestBuilder.setQuery(boolQueryBuilder);
        searchRequestBuilder.setFetchSource(include, exclude);

        if (isCount) {
            startCount = startCount - 1 < 0 ? 0 : startCount - 1;
            int size = endCount - startCount;
            searchRequestBuilder.setFrom(startCount).setSize(size);
        }
        return searchRequestBuilder;
    }

    /**
     * 简单K V 转换
     * @param map
     * @return
     */
    default List<Kv> converKv(Map<String, Long> map) {
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

    /**
     * 简单分组过滤
     */
    default AggregationBuilder filterAggregation(List<String> values, String key, boolean in) {
        if (values != null && values.size() > 0) {
            FiltersAggregator.KeyedFilter[] filters = new FiltersAggregator.KeyedFilter[values.size()];
            int i = 0;
            for (String val : values) {
                BoolQueryBuilder query = QueryBuilders.boolQuery();
                if (in)
                    query.must(QueryBuilders.matchQuery(key + ".keyword", val));
                else
                    query.must(QueryBuilders.matchQuery(key, val));
                filters[i] = new FiltersAggregator.KeyedFilter(val, query);
                i++;
            }
            return AggregationBuilders.filters(BConst.AGGLIST, filters);
        } else return null;
    }

}
