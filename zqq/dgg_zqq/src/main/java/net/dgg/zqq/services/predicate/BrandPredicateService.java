package net.dgg.zqq.services.predicate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Created by 李程 on 2018/10/16.
 * 商标成功率预测服务
 */
public interface BrandPredicateService {

    Map<String, Object> predicateByName(String brand);

    Map<String, Object> predicateByImage(String format, byte[] image);

    List<Map<String, Object>> queryAllIndustries();

    Map<String, Object> getMessage(String text);

    /**
     * 扣分规则
     */
    @FunctionalInterface
    interface DeductionRule {

        RuleApplyResponse deduction(String text);  //检测扣分

    }

    /**
     * 不允许通过规则
     */
    @FunctionalInterface
    interface NotAllowRule {

        RuleApplyResponse pass(String text);

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class RuleApplyResponse {

        private boolean passed;

        private List<String> messages;

        private double score;

        private Object relationShip;

    }

}
