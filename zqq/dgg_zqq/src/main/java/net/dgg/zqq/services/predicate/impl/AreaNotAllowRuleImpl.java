package net.dgg.zqq.services.predicate.impl;

import net.dgg.zqq.services.predicate.BrandPredicateService;
import net.dgg.zqq.utils.es.EsUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by 李程 on 2018/10/16.
 */
@Order(1)
@Component("areaNotAllowRule")
public class AreaNotAllowRuleImpl implements BrandPredicateService.NotAllowRule {

    @Override
    public BrandPredicateService.RuleApplyResponse pass(String text) {
        Map<String, Object> condition = new LinkedHashMap<>();
        condition.put("area_name =", text);
        Long count = EsUtils.count("dgg_zqq_es_db_areas", "area", condition);
        if (count > 0L) {
            return BrandPredicateService.RuleApplyResponse.builder().passed(false).messages(Arrays.asList("商标名称不能为省、市、县等地区名")).relationShip(text).score(0).build();
        } else {
            return BrandPredicateService.RuleApplyResponse.builder().passed(true).messages(null).score(0.9).build();
        }
    }
}
