package net.dgg.bigdata.sjjs.web.service.impl.search;

import net.dgg.bigdata.common.constant.BConst;
import net.dgg.bigdata.sjjs.web.constant.ConstMethod;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;

import static net.dgg.bigdata.common.constant.BConst.DATESUFIX;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;

/**
 * @ClassName: EnterpriseSearchServiceImpl
 * @Description: TODO
 * @Author: jiangsh
 * @Date: 2018/12/11 17:07
 */
@Service
public class EnterpriseSearchServiceImpl implements ConstMethod {

    public SearchRequestBuilder searchRequestBuilder(String index, String type, Map<String, String> condition,
                                                     Map sortMap, int startCount, int endCount,
                                                     Map<String, String> map, boolean isphrase, String[] include, String[] exclude, TransportClient client, boolean isCount) {

        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

        BoolQueryBuilder andQuery = QueryBuilders.boolQuery();
        if (condition != null && condition.size() > 0)
            conditionNoNullIndex(andQuery, condition, isphrase);

        BoolQueryBuilder orQuery2 = QueryBuilders.boolQuery();
        orQuery2.must(andQuery);
        if (map != null && map.size() > 0)
            mapNoNullIndex(orQuery2, map, isphrase);

        if (sortMap != null) {
            Iterator<String> iterator = sortMap.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                SortOrder value = (SortOrder) sortMap.get(key);
                searchRequestBuilder.addSort(key, value);
            }
        }
        return searchRequestBuilder(searchRequestBuilder, orQuery2, include, exclude, startCount, endCount, isCount);
    }

    private static void conditionNoNullIndex(BoolQueryBuilder andQuery, Map condition, boolean isphrase) {
        if (condition != null && condition.size() > 0) {
            Iterator<String> it = condition.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                Object o = condition.get(key);
                if (o != null) {
                    String value = o.toString();
                    BoolQueryBuilder mapQuery = QueryBuilders.boolQuery();
                    keyCon(key, value, isphrase, mapQuery);
                    andQuery.should(mapQuery);
                }
            }
        }
    }

    private static void mapNoNullIndex(BoolQueryBuilder andQuery, Map<String, String> map, boolean isphrase) {
        if (map != null && map.size() > 0) {
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                Object o = map.get(key);
                if (o != null) {
                    String value = o.toString();
                    keyCon(key, value, isphrase, andQuery);
                }
            }
        }
    }

    private static void keyCon(String key, String value, boolean isphrase, BoolQueryBuilder orQuery) {
        if (key.equals(BConst.REG_MONEY) && StringUtils.isNotEmpty(value)) { //处理注册资金搜索问题
            dealRegCapi(value, orQuery, key);
        } else if (key.equals(BConst.REG_TIME) && StringUtils.isNotEmpty(value)) { //自定义成立日期
            startDate(value, orQuery,BConst.REG_TIME);
        } else if (key.equals(BConst.TEL)) {
            if (value.equals("1")) orQuery.must(QueryBuilders.existsQuery(key));
            else if (value.equals("0")) orQuery.mustNot(QueryBuilders.existsQuery(key));
            else orQuery.must(matchPhraseQuery(key, value));
        } else if (key.equals(BConst.EMAIL)) {
            if (value.equals("1")) orQuery.must(QueryBuilders.existsQuery(key));
            else if (value.equals("0")) orQuery.mustNot(QueryBuilders.existsQuery(key));
            else orQuery.must(matchPhraseQuery(key, value));
        } else if (key.equals(BConst.COMPANY_NAME)) {
            if (isphrase) orQuery.must(matchPhraseQuery(key, value).boost(3));
            else orQuery.must(QueryBuilders.matchQuery(key, value).boost(3));
        } else {
            if (isphrase) orQuery.must(matchPhraseQuery(key, value));
            else orQuery.must(QueryBuilders.matchQuery(key, value));
        }
    }

    protected static void dealRegCapi(String value, BoolQueryBuilder orQuery, String key) {
        if (value.contains("-")) {
            String[] reg = value.split("-");
            orQuery.must(QueryBuilders.rangeQuery(key).from(reg[0]).to(reg[1]));
        } else if (value.contains("<")) {
            orQuery.must(QueryBuilders.rangeQuery(key).lt(value.substring(1)));
        } else if (value.contains(">")) {
            orQuery.must(QueryBuilders.rangeQuery(key).gt(value.substring(1)));
        } else orQuery.must(QueryBuilders.rangeQuery(key).from(value));
    }

    protected static void startDate(String value, BoolQueryBuilder orQuery, String dataKey) {
        if (value.contains("-")) {
            String[] reg = value.split("-");
            if (reg[0].equals(reg[1])) dealStartDate(String.valueOf(year()), orQuery, dataKey);
            else {
                final int year0 = year()- Integer.valueOf(reg[1]);
                final int year1 = year()- Integer.valueOf(reg[0]);
                final String one = String.valueOf(Integer.valueOf(year0)).concat(DATESUFIX);
                final String two = String.valueOf(year1).concat(DATESUFIX);
                orQuery.must(QueryBuilders.rangeQuery(dataKey).from(one).to(two));
            }
        } else if (value.contains("<")) {
            orQuery.must(QueryBuilders.rangeQuery(dataKey).gt(String.valueOf(year() - Integer.valueOf(value.substring(1))).concat(DATESUFIX)));
        } else if (value.contains(">")) {
            orQuery.must(QueryBuilders.rangeQuery(dataKey).lte(String.valueOf(year() - Integer.valueOf(value.substring(1))).concat(DATESUFIX)));
        } else dealStartDate(value, orQuery, dataKey);
    }

    protected static void dealStartDate(String value, BoolQueryBuilder orQuery, String dataKey) {
        final String one = String.valueOf(Integer.valueOf(value) - 1).concat(DATESUFIX);
        final String two = value.concat(DATESUFIX);
        orQuery.must(QueryBuilders.rangeQuery(dataKey).from(one).to(two));
    }

    protected static int year() {
        return LocalDate.now().getYear();
    }
}
