package net.dgg.core.es.services.impl;

import net.dgg.core.es.EsUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;

/**
 * @ClassName: EsServiceImpl
 * @Description: es服务实现层
 * @Author: jiangsh
 * @Date: 2018/12/6 16:18
 */
@Component
public class EsServiceImpl {

    public List<Map> getMsgByKeyWord(String index, String type, int count, BoolQueryBuilder qb) {
        return EsUtils.getMsgByKeyWord(index, type, count, qb);
    }

    public List<Map> getPart(String index, String type, String keyWord, int count, String[] include, Map<String, String> m, String sortKey) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        final String[] excludes = {"", ""};
        for (Map.Entry<String, String> entry : m.entrySet())
            qb.should(matchPhraseQuery(entry.getKey(), keyWord));

        return EsUtils.getPart(index, type, qb, include, excludes, count, sortKey);
    }

    public List<Map> getMsgByKeyWordFilterField(String index, String type, int count, String[] include, String[] exclude, BoolQueryBuilder qb) {
        return EsUtils.getMsgByKeyWordFilterField(index, type, count, include, exclude, qb);
    }
}
