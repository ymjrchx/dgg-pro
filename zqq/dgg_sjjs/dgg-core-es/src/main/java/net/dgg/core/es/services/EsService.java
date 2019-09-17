package net.dgg.core.es.services;

import net.dgg.core.es.EsClientUtils;
import net.dgg.core.es.EsUtils;
import net.dgg.core.es.services.impl.EsServiceImpl;
import net.dgg.core.utils.DggJsonUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangsh on 2018/6/22 14:45
 */
@Service
public class EsService {
    Logger logger = LoggerFactory.getLogger(EsService.class);

    @Resource
    private EsServiceImpl esServiceImpl;

    /**
     * 部分完全匹配，简单 KV 格式，限数
     * @param index 索引库
     * @param type 类型
     * @param count 请求条数
     * @return
     */
    public List<Map> getMsgByKeyWord(String index, String type, int count, BoolQueryBuilder qb) {
        return esServiceImpl.getMsgByKeyWord(index, type, count, qb);
    }


    /**
     * 部分完全匹配，简单 KV 格式，限数
     * @param index 索引库
     * @param type 类型
     * @param count 请求条数
     * @return
     */
    public List<Map> getMsgByKeyWordFilterField(String index, String type, int count, String[] include, String[] exclude, BoolQueryBuilder qb) {
        return esServiceImpl.getMsgByKeyWordFilterField(index, type, count, include, exclude, qb);
    }


    /**
     * 添加文件进es
     * @param index 索引库
     * @param type 类型
     * @param id 文档id
     * @param object souce
     * @return
     */
    public boolean addDocument(String index, String type, String id, Object object) {
        return EsUtils.add(index, type, id, object);
    }

    /**
     * 添加文件进es
     * @param index 索引库
     * @param type 类型
     * @param id 文档id
     * @param object souce
     * @return
     */
    public boolean addDocumentClues(String index, String type, String id, Object object, String parentId) {
        return EsUtils.addParentChild(index, type, id, object, parentId);
    }

    /**
     * 获取前五且高亮关键字
     * @param index 索引库
     * @param type 类型
     * @param keyWord 关键字
     * @param count 条数
     * @param include 包含列表
     * @param m 条件
     * @param sortKey 排序
     * @return
     */
    public List<Map> getPart(String index, String type, String keyWord, int count, String[] include, Map<String, String> m, String sortKey) {
        return esServiceImpl.getPart(index, type, keyWord, count, include, m, sortKey);
    }

    /**
     * 分页获取列表数据
     * @param index 索引库
     * @param type 类型
     * @param startCount 开始条
     * @param endCount 结束条
     * @param searchRequestBuilder 查询请求builder
     * @return
     */
    public List<Map> getDataMapList(String index, String type, int startCount, int endCount, SearchRequestBuilder searchRequestBuilder) {
        return EsUtils.getDataMapList(index, type, startCount, endCount, searchRequestBuilder);
    }

    /**
     * 取总数
     * @param index 索引
     * @param type 类型
     * @param qb 请求builder
     * @return
     */
    public long totalCount(String index, String type, BoolQueryBuilder qb) {
        return EsUtils.getTotalCount(index, type, qb);
    }

    /**
     * 获取客户端
     * @return
     */
    public TransportClient getClient() {
        return EsClientUtils.getClient();
    }

    /**
     * 聚合
     * @param ab 聚合builder
     * @param qb 请求builder
     * @param index 索引库
     * @param type 类型
     * @param aggName 聚合名
     * @return
     */
    public Map<String, Long> aggreage(AggregationBuilder ab, BoolQueryBuilder qb, String index, String type, String aggName){
        return EsUtils.aggreage(ab, qb, index, type, aggName);
    }
}
