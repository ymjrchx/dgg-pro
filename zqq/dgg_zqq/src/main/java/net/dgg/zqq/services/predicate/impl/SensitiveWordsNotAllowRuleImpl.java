package net.dgg.zqq.services.predicate.impl;

import net.dgg.zqq.services.predicate.BrandPredicateService;
import net.dgg.zqq.utils.es.EsUtils;
import net.dgg.zqq.utils.es.stack.UserBoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 李程 on 2018/10/16.
 */
@Order(3)
@Component("sensitiveWordsNotAllowRule")
public class SensitiveWordsNotAllowRuleImpl implements BrandPredicateService.NotAllowRule {

    @Override
    public BrandPredicateService.RuleApplyResponse pass(String text) {
        Map<String, Object> condition = new LinkedHashMap<>();
        UserBoolQueryBuilder query = (field, boolQuery) -> {
            boolQuery.must(QueryBuilders.matchQuery(field, text).operator(Operator.OR));
        };
        condition.put("keyword user", query);
        List<Map<String, Object>> nears = EsUtils.execute("dgg_zqq_es_db_sensitive_words", "sensitiveWords", condition, (console, data) -> {
            String keyword = (String) data.getSource().get("keyword");
            if (text.toUpperCase().contains(keyword.toUpperCase())) {
                console.write(data);
                console.stop();
            }
        }, 10).stream().map(hit -> ((SearchHit) hit).getSource()).collect(Collectors.toList());
        for (Map<String, Object> data : nears) {
            String keyword = (String) data.get("keyword");
            return BrandPredicateService.RuleApplyResponse.builder().score(0).passed(false).messages(Arrays.asList("你注册的商标属于敏感词：\"".concat(keyword).concat("\""))).relationShip(data).build();
        }
        return BrandPredicateService.RuleApplyResponse.builder().score(0.9).passed(true).build();
    }
}
