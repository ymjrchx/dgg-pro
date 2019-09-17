package net.dgg.zqq.services.predicate.impl;

import lombok.SneakyThrows;
import net.dgg.zqq.services.predicate.BrandPredicateService;
import net.dgg.zqq.utils.IKAnalysisHelper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by 李程 on 2018/10/18.
 */
@Order(4)
@Component("wordRepeatDeductionRule")
public class WordRepeatDeductionRuleImpl implements BrandPredicateService.DeductionRule {

    @Override
    @SneakyThrows
    public BrandPredicateService.RuleApplyResponse deduction(String text) {
        List<String> words = IKAnalysisHelper.segment(text);
        Map<String, Integer> wordCount = new HashMap<>();
        words.stream().forEach(key -> wordCount.put(key, 0));
        words.stream().forEach(key -> wordCount.put(key, wordCount.get(key) + 1));
        List<String> messages = new ArrayList<>();
        for (String word : wordCount.keySet()) {
            if (wordCount.get(word) > 1) {
                messages.add("你注册的商标中，单词/字：\"" + word + "\" 出现了" + wordCount.get(word) + "次");
            }
        }
        if (messages.size() > 0) {
            return BrandPredicateService.RuleApplyResponse.builder().passed(false).score(0.55).messages(messages).relationShip(text).build();
        }
        return BrandPredicateService.RuleApplyResponse.builder().passed(true).score(0.9).build();
    }
}
