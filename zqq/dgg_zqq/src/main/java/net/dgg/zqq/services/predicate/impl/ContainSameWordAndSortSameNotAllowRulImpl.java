package net.dgg.zqq.services.predicate.impl;

import net.dgg.zqq.services.predicate.BrandPredicateService;
import net.dgg.zqq.utils.IKAnalysisHelper;
import net.dgg.zqq.utils.es.EsUtils;
import net.dgg.zqq.utils.es.stack.UserBoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 李程 on 2018/10/22.
 */
@Component("containSameWordAndSortSameNotAllowRule")
public class ContainSameWordAndSortSameNotAllowRulImpl implements BrandPredicateService.NotAllowRule {

    @Value("${firstTrial.brand.index}")
    private String brandIndex;

    @Value("${firstTrial.brand.type}")
    private String brandType;

    @Override
    public BrandPredicateService.RuleApplyResponse pass(String text) {
        List<String> words = IKAnalysisHelper.spliteSingleChineseAndSingleWord(text).stream().map(String::toLowerCase).collect(Collectors.toList());
        Map<String, Object> mapCondition = new LinkedHashMap<>();
        UserBoolQueryBuilder condition = (field, boolQueryBuilder) -> {
            boolQueryBuilder.must(QueryBuilders.matchQuery(field, text).operator(Operator.AND));
        };
        mapCondition.put("name user", condition);
        List<SearchHit> hits = EsUtils.execute(brandIndex, brandType, mapCondition, (console, data) -> {
            String name = (String) data.getSource().get("name");
            List<String> nameWords = IKAnalysisHelper.spliteSingleChineseAndSingleWord(name);
            if (nameWords.size() == words.size()) {
                boolean allEqual = true;
                for (int i = 0; i < nameWords.size(); i++) {
                    allEqual = allEqual && nameWords.get(i).equalsIgnoreCase(words.get(i));
                }
                if (allEqual) {
                    console.write(data);
                    console.stop();
                }
            }
        }, 25);
        if (hits.isEmpty()) {
            return BrandPredicateService.RuleApplyResponse.builder().score(0.9).passed(true).build();
        } else {
            List<String> messages = new ArrayList<>();
            messages.add("你的商标与商标 \"" + hits.get(0).getSource().get("name") + "\"重名");
            return BrandPredicateService.RuleApplyResponse.builder().passed(false).messages(messages).score(0).relationShip(hits.get(0).getSource()).build();
        }
    }
}
