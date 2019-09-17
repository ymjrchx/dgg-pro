package net.dgg.zqq.services.predicate.impl;

import net.dgg.zqq.services.predicate.BrandPredicateService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李程 on 2018/10/17.
 */
@Order(5)
@Component("twoOneSameAsciiCharDeductionRule")
public class TwoOneSameAsciiCharDeductionRuleImpl implements BrandPredicateService.DeductionRule {

    //为一个英文字母或两个相同的英文字母规则
    @Override
    public BrandPredicateService.RuleApplyResponse deduction(String text) {
        if (text.length() == 1) {
            if (text.toCharArray()[0] < 128) {
                List<String> messages = new ArrayList<>();
                messages.add("商标不能为一个英文字母");
                return BrandPredicateService.RuleApplyResponse.builder().score(0.25).passed(false).relationShip(text).messages(messages).build();
            }
        }
        if (text.length() == 2) {
            if (text.toCharArray()[0] == text.toCharArray()[1] && text.toCharArray()[0] < 128) {
                List<String> messages = new ArrayList<>();
                messages.add("商标不能为两个重复英文字母");
                return BrandPredicateService.RuleApplyResponse.builder().score(0.25).passed(false).relationShip(text).messages(messages).build();
            }
        }
        return BrandPredicateService.RuleApplyResponse.builder().score(0.9).passed(true).build();
    }
}
