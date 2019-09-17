package net.dgg.zqq.services.predicate.impl;

import net.dgg.zqq.dao.JdbcDao;
import net.dgg.zqq.dao.predict.PredictHelperDao;
import net.dgg.zqq.services.predicate.BrandPredicateService;
import net.dgg.zqq.utils.IKAnalysisHelper;
import net.dgg.zqq.utils.ThreadHelper;
import net.dgg.zqq.utils.Toolkit;
import net.dgg.zqq.utils.es.EsClientUtils;
import net.dgg.zqq.utils.es.EsUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by 李程 on 2018/10/17.
 */
@Order(10)
@Component("chineseWordBaseSameSortDifferenceDeductionRule")
public class ChineseWordBaseSameSortDifferenceDeductionRuleImpl implements BrandPredicateService.DeductionRule {

    @Autowired
    PredictHelperDao predictHelperDao;

    @Value("${firstTrial.brand.index}")
    private String brandIndex;

    @Value("${firstTrial.brand.type}")
    private String brandType;

    @Autowired
    JdbcDao jdbcDao;

    @Override
    public BrandPredicateService.RuleApplyResponse deduction(String text) {
        List<String> words = IKAnalysisHelper.spliteSingleChineseAndSingleWord(text);
        for (String word : words) {
            if (word.length() > 1) {    //分词含有英文
                return BrandPredicateService.RuleApplyResponse.builder().score(0.9).passed(true).build();
            } else if (word.length() == 1 && word.toCharArray()[0] < 128) { //分词有一个英文
                return BrandPredicateService.RuleApplyResponse.builder().score(0.9).passed(true).build();
            }
        }
        if (words.size() > 1) {
            int BATCH_SIZE = 1000;
            int TIME_OUT = 200;
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", text).operator(Operator.AND);
            TransportClient transportClient = EsClientUtils.getClient();

            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(matchQueryBuilder);

            String industry = ThreadHelper.getThreadContextVar("industry");
            String type = ThreadHelper.getThreadContextVar("type");
            if (StringUtils.isNotEmpty(type)) {
                List<Map<String, Object>> cates = jdbcDao.query("zqq_class_first", new Toolkit.MapBuilder().put("number =", type).build());
                List<String> intCls = new ArrayList<>();
                for (Map<String, Object> category : cates) {
                    String number = (String) category.get("number");
                    Map<Integer, String> clsMap = ThreadHelper.getThreadContextVar("clsMap");
                    Integer numberCls = Integer.valueOf(number);
                    if (clsMap.containsKey(numberCls)) {
                        intCls.add(clsMap.get(numberCls));
                    } else {
                        String name = (String) category.get("name");
                        intCls.add("第" + number + "类-" + name);
                        intCls.add("第" + number + "类 " + name);
                        intCls.add(number + "类-" + name);
                        intCls.add(number + "类 " + name);
                        intCls.add("第" + number + "类");
                        intCls.add(number + "类");
                        intCls.add(number);
                    }
                }
                boolQueryBuilder.must(QueryBuilders.termsQuery("intCls.keyword", intCls));
            } else if (StringUtils.isNotEmpty(industry)) {
                List<Map<String, Object>> cates = predictHelperDao.queryByIndustryId(industry);
                List<String> intCls = new ArrayList<>();
                for (Map<String, Object> category : cates) {
                    String number = (String) category.get("number");
                    Map<Integer, String> clsMap = ThreadHelper.getThreadContextVar("clsMap");
                    Integer numberCls = Integer.valueOf(number);
                    if (clsMap.containsKey(numberCls)) {
                        intCls.add(clsMap.get(numberCls));
                    } else {
                        String name = (String) category.get("name");
                        intCls.add("第" + number + "类-" + name);
                        intCls.add("第" + number + "类 " + name);
                        intCls.add(number + "类-" + name);
                        intCls.add(number + "类 " + name);
                        intCls.add("第" + number + "类");
                        intCls.add(number + "类");
                        intCls.add(number);
                    }
                }
                boolQueryBuilder.must(QueryBuilders.termsQuery("intCls.keyword", intCls));
            }

            SearchResponse response = transportClient.prepareSearch(brandIndex).setTypes(brandType).setSize(BATCH_SIZE).setScroll(TimeValue.timeValueMinutes(200)).setQuery(boolQueryBuilder).execute().actionGet();
            long totalCount = response.getHits().getTotalHits();
            int page = (int) totalCount / BATCH_SIZE;
            Iterator<SearchHit> hits = response.getHits().iterator();
            while (hits.hasNext()) {
                SearchHit hit = hits.next();
                String name = (String) hit.getSource().get("name");
                List<String> targets = IKAnalysisHelper.spliteSingleChineseAndSingleWord(name);
                boolean notPass = targets.stream().map(words::contains).reduce((a, b) -> a && b).get();
                if (notPass) {
                    List<String> messages = new ArrayList<>();
                    messages.add("你的商标有相近的词如：\"" + name + "\"");
                    return BrandPredicateService.RuleApplyResponse.builder().score(0.55).relationShip(hit.getSource()).passed(false).messages(messages).build();
                }
            }
            for (int i = 0; i < page; i++) {
                response = transportClient.prepareSearchScroll(response.getScrollId())
                        .setScroll(new TimeValue(TIME_OUT)).execute()
                        .actionGet();
                hits = response.getHits().iterator();
                while (hits.hasNext()) {
                    SearchHit hit = hits.next();
                    String name = (String) hit.getSource().get("name");
                    List<String> targets = IKAnalysisHelper.spliteSingleChineseAndSingleWord(name);
                    boolean notPass = targets.stream().map(words::contains).reduce((a, b) -> a && b).get();
                    if (notPass) {
                        List<String> messages = new ArrayList<>();
                        messages.add("你的商标有相近的词如：\"" + name + "\"");
                        return BrandPredicateService.RuleApplyResponse.builder().relationShip(hit.getSource()).score(0.55).passed(false).messages(messages).build();
                    }
                }
            }
            transportClient.prepareClearScroll().addScrollId(response.getScrollId()).execute();
            return BrandPredicateService.RuleApplyResponse.builder().score(0.9).passed(true).build();
        } else {
            Map<String, Object> queryEqual = new LinkedHashMap<>();
            queryEqual.put("name keyword", text);

            String industry = ThreadHelper.getThreadContextVar("industry");
            if (StringUtils.isNotEmpty(industry)) {
                List<Map<String, Object>> cates = predictHelperDao.queryByIndustryId(industry);
                //intCls的几种形式
                List<String> intCls = new ArrayList<>();
                for (Map<String, Object> category : cates) {
                    String number = (String) category.get("number");
                    Map<Integer, List<String>> clsMap = ThreadHelper.getThreadContextVar("clsMap");
                    Integer numberCls = Integer.valueOf(number);
                    if (clsMap.containsKey(numberCls)) {
                        intCls.addAll(clsMap.get(numberCls));
                    } else {
                        String name = (String) category.get("name");
                        intCls.add("第" + number + "类-" + name);
                        intCls.add("第" + number + "类 " + name);
                        intCls.add(number + "类-" + name);
                        intCls.add(number + "类 " + name);
                        intCls.add("第" + number + "类");
                        intCls.add(number + "类");
                        intCls.add(number);
                    }
                }
                queryEqual.put("intCls keywordIn", intCls);
            }

            Long count = EsUtils.count(brandIndex, brandType, queryEqual);
            if (count > 0L) {
                List<String> messages = new ArrayList<>();
                messages.add("你的商标有相近的词如：\"" + text + "\"");
                return BrandPredicateService.RuleApplyResponse.builder().score(0.55).relationShip(text).passed(false).messages(messages).build();
            } else {
                return BrandPredicateService.RuleApplyResponse.builder().score(0.9).passed(true).build();
            }
        }
    }

}
