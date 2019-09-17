package net.dgg.bigdata.sjjs.web.service.impl.search;

import net.dgg.bigdata.common.constant.BConst;
import net.dgg.bigdata.sjjs.web.constant.ConstMethod;
import net.dgg.bigdata.sjjs.web.controller.search.ConverRequest;
import net.dgg.bigdata.sjjs.web.config.EsConfig;
import net.dgg.bigdata.sjjs.web.entity.dto.search.senior.ResJudge;
import net.dgg.bigdata.sjjs.web.entity.dto.search.senior.SeniorParam;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.join.query.HasChildQueryBuilder;
import org.elasticsearch.join.query.JoinQueryBuilders;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SeniorSearchServiceImpl
 * @Description: TODO
 * @Author: jiangsh
 * @Date: 2018/12/13 11:26
 */
@Component
public class SeniorSearchServiceImpl implements ConstMethod {

    @Resource
    private ConverRequest cr;

    public BoolQueryBuilder boolQueryBuilder(SeniorParam ep, Map<String, String> sort, boolean isphrase, String fh, String accountName) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        ResJudge r = cr.judgeConver(qb, ep.getJudge()); //第一级判断
        BoolQueryBuilder qqb = r.getBoolQueryBuilder();
        List<SeniorParam.SO> list = ep.getParam();
        if (list != null && list.size() > 0)
            qbContion(list, r, qqb, fh);

        List<SeniorParam.Next> nexts = ep.getNexts();
        if (nexts != null && nexts.size() > 0) {
            for (SeniorParam.Next next : nexts) {
                ResJudge rNext = cr.judgeConver(qb, next.getnJudge()); //next级别判断
                BoolQueryBuilder qqbNext = rNext.getBoolQueryBuilder();
                List<SeniorParam.SO> listNext = next.getnParam();
                if (listNext != null && listNext.size() > 0)
                    qbContion(listNext, rNext, qqbNext, fh);
            }
        }

        HasChildQueryBuilder hcqb= JoinQueryBuilders
                .hasChildQuery(EsConfig.YK_COMMERCIAL_CHILD,
                        QueryBuilders.matchQuery(BConst.USERID + ".keyword", accountName),
                        ScoreMode.None);

        qb.mustNot(hcqb);
        return qb;
    }

    private void qbContion(List<SeniorParam.SO> list, ResJudge r, BoolQueryBuilder qqb, String fh) {
        for (SeniorParam.SO so : list) {
            BoolQueryBuilder inBqb = QueryBuilders.boolQuery();
            if (r.isFlag()) qqb.must(cr.relationConver(cr.nameConver(so.getOne()), so.getTwo(), inBqb, so.getThree(), fh, BConst.F_TRUE));
            else            qqb.should(cr.relationConver(cr.nameConver(so.getOne()), so.getTwo(), inBqb, so.getThree(), fh, BConst.F_TRUE));
        }
    }

    public SearchRequestBuilder searchRequestBuilder(String index, String type, int startCount, int endCount,
                                                     BoolQueryBuilder boolQueryBuilder, String[] include, String[] exclude, TransportClient client, boolean isCount) {

        final SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        return searchRequestBuilder(searchRequestBuilder, boolQueryBuilder, include, exclude, startCount, endCount, isCount);
    }
}
