package net.dgg.bigdata.sjjs.web.service.search;

import net.dgg.bigdata.common.constant.BConst;
import net.dgg.bigdata.sjjs.web.constant.CluesStatus;
import net.dgg.bigdata.sjjs.web.constant.ConstMethod;
import net.dgg.bigdata.sjjs.web.config.EsConfig;
import net.dgg.bigdata.sjjs.web.entity.dto.search.senior.SeniorParam;
import net.dgg.bigdata.sjjs.web.service.impl.search.SeniorSearchServiceImpl;
import net.dgg.core.es.services.EsService;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: SeniorSearchService
 * @Description: 高级搜索
 * @Author: jiangsh
 * @Date: 2018/12/12 10:55
 */
@Service
public class SeniorSearchService implements ConstMethod {

    @Resource
    private EsService esService;
    @Resource
    private SeniorSearchServiceImpl sssi;

    public List<Map> search(String index, String type, int startCount, int endCount, SeniorParam ep, boolean isphrase, String accountName) {
        Map sort = new HashMap();
        sort.put(BConst.REG_MONEY, SortOrder.DESC);
        final String[] exclude = {""};
        final SearchRequestBuilder searchRequestBuilder = sssi.searchRequestBuilder(index, type,
                startCount, endCount, boolQueryBuilder(ep, sort, isphrase, accountName), include(), exclude, esService.getClient(), BConst.F_TRUE);
        //list data
        final List<Map> list = esService.getDataMapList(index, type, startCount, endCount, searchRequestBuilder);
        if (list.size() > 0 && list.get(0).size() > 0) {
            //clues times
            final Map<String, Long> map = filterAggregationCount(filterAggregation(values(list, BConst.COMPANY_ID), BConst.CLUSE_RECORD_COMID, BConst.F_TRUE),
                    BConst.RECORD_TOWHERE, CluesStatus.CLUES_GET, EsConfig.YK_COMMERCIAL_CLUES_RECORD, EsConfig.YK_COMMERCIAL_CLUES_RECORD);
            return combination(list, map);
        } else return list;
    }

    public long totalCount(String index, String type, int startCount, int endCount, SeniorParam ep, boolean isphrase, String accountName) {
        final String[] exclude = {""};
        final SearchRequestBuilder searchRequestBuilder = sssi.searchRequestBuilder(index, type,
                startCount, endCount, boolQueryBuilder(ep, null, isphrase, accountName), include(), exclude, esService.getClient(), BConst.F_FALSE);
        return searchRequestBuilder.get().getHits().getTotalHits();
    }

    private BoolQueryBuilder boolQueryBuilder(SeniorParam ep, Map<String, String> sort, boolean isphrase, String accountName) {
        return sssi.boolQueryBuilder(ep, sort, isphrase, "、", accountName);
    }

    /**
     * 列表展示列
     */
    private static String[] include() {
        final String[] include = {BConst.COMPANY_ID, BConst.COMPANY_NAME, BConst.LEGEN_PERSON, BConst.INDUSTRY, BConst.ADDRESS,
                BConst.WEB_SITE_URL, BConst.REG_MONEY, BConst.REG_TIME, BConst.BUSINESS_CB, BConst.TEL};
        return include;
    }

    /**
     * 聚合转线索次数
     */
    private Map<String, Long> filterAggregationCount(AggregationBuilder ab, String key, String val, String index, String type) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.matchQuery(key + ".keyword", val));
        return esService.aggreage(ab, qb, index, type, BConst.AGGLIST);
    }

    /**
     * 列表详情
     */
    public List<Map> searchSimpleDetail(String key, String companyId, String index, String type) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.matchQuery(key + ".keyword", companyId));
        return esService.getMsgByKeyWord(index, type, 1, qb);
    }

    private List<String> values(List<Map> list, String key) {
        List<String> result = new ArrayList<>();
        for (Map m : list)
            result.add(m.get(key).toString());
        return result;
    }

    private List<Map> combination(List<Map> list, Map<String, Long> map) {
        if (!list.isEmpty() && !map.isEmpty()) {
            for (Map m : list) {
                final String comId = m.get(BConst.COMPANY_ID).toString();
                for (String mm : map.keySet()) {
                    if (comId.equals(mm))
                        m.put(BConst.CLUES_TIMES, map.get(mm));
                }
            }
        }
        return list;
    }

    /**
     * 公司详情 需加密信息
     * @return
     */
    public String[] getColumns() {
        final String[] columns = {
                //详情
                BConst.INDEX_TEL, BConst.BASICINFO_CONTACTNO,
                BConst.BASICINFO_PHONENUMBER, BConst.BASICINFO_EMAIL, BConst.BASICINFO_TEL, BConst.BASICINFO_QYNB_EMAIL,
                BConst.BASICINFO_FPTT_PHONENO, BConst.INDEX_EMAIL,

                //列表
                BConst.TEL, BConst.EMAIL
        };
        return columns;
    }

}
