package net.dgg.zqq.services.predicate.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dgg.zqq.dao.JdbcDao;
import net.dgg.zqq.dao.predict.PredictHelperDao;
import net.dgg.zqq.services.predicate.BrandPredicateService;
import net.dgg.zqq.services.trademark.TrademarkService;
import net.dgg.zqq.utils.ThreadHelper;
import net.dgg.zqq.utils.Toolkit;
import net.dgg.zqq.utils.es.EsUtils;
import net.dgg.zqq.utils.es.stack.UserBoolQueryBuilder;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Clock;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by 李程 on 2018/10/16.
 */
@Service
@Slf4j
public class BrandPredicateServiceImpl implements BrandPredicateService {

    @Autowired
    PredictHelperDao predictHelperDao;

    @Value("${firstTrial.brand.index}")
    private String brandIndex;

    @Value("${firstTrial.brand.type}")
    private String brandType;

    @Autowired
    TrademarkService trademarkService;

    @Value("${debug}")
    Boolean debug;

    @Resource(name = "containSameWordAndSortSameNotAllowRule")
    NotAllowRule containSameWordAndSortSameNotAllowRule;

    @Resource(name = "sensitiveWordsNotAllowRule")
    NotAllowRule sensitiveWordsNotAllowRule;

    @Resource(name = "areaNotAllowRule")
    NotAllowRule areaNotAllowRule;

    @Resource(name = "chineseBrandNotAllowRule")
    NotAllowRule chineseBrandNotAllowRule;

    @Resource(name = "brandNotAllowRule")
    NotAllowRule brandNotAllowRule;

    @Resource(name = "wordRepeatDeductionRule")
    DeductionRule wordRepeatDeductionRule;

    @Resource(name = "twoOneSameAsciiCharDeductionRule")
    DeductionRule twoOneSameAsciiCharDeductionRule;

    @Resource(name = "twoForeignDeductionRule")
    DeductionRule twoForeignDeductionRule;

    @Resource(name = "threeGatherThreeEnglishCharDeductionRule")
    DeductionRule threeGatherThreeEnglishCharDeductionRule;

    @Resource(name = "foreignFourGatherThanFourOnlyCountDifferenceDeductionRule")
    DeductionRule foreignFourGatherThanFourOnlyCountDifferenceDeductionRule;

    @Resource(name = "chineseWordBaseSameSortDifferenceDeductionRule")
    DeductionRule chineseWordBaseSameSortDifferenceDeductionRule;

    @Resource(name = "chineseThreeGatherThanThreeOnlyOneDifferenceDeductionRule")
    DeductionRule chineseThreeGatherThanThreeOnlyOneDifferenceDeductionRule;

    @Autowired
    JdbcDao jdbcDao;

    @Override
    public Map<String, Object> predicateByName(String brand) {
        List<DeductionRule> deductionRules = Arrays.asList(wordRepeatDeductionRule, twoOneSameAsciiCharDeductionRule, twoForeignDeductionRule, threeGatherThreeEnglishCharDeductionRule, foreignFourGatherThanFourOnlyCountDifferenceDeductionRule, chineseWordBaseSameSortDifferenceDeductionRule, chineseThreeGatherThanThreeOnlyOneDifferenceDeductionRule);
        List<NotAllowRule> notAllowRules = Arrays.asList(sensitiveWordsNotAllowRule, areaNotAllowRule, chineseBrandNotAllowRule, brandNotAllowRule);
        Map<Integer, List<String>> clsMap = trademarkService.getDistinctNumericTypeMultiText(brandIndex, brandType, new HashMap<>(), "intCls");
        ThreadHelper.putThreadContextVar("clsMap", clsMap);
        Map<String, Object> response = new LinkedHashMap<>();
        for (NotAllowRule rule : notAllowRules) {
            RuleApplyResponse res = rule.pass(brand);
            if (!res.isPassed()) {
                response.put("score", res.getScore());
                response.put("cause", res.getMessages().get(0));
                response.put("relationShip", res.getRelationShip());
                return response;
            }
        }
        for (DeductionRule deductionRule : deductionRules) {
            RuleApplyResponse res = deductionRule.deduction(brand);
            if (!res.isPassed()) {
                response.put("score", res.getScore());
                response.put("cause", res.getMessages().get(0));
                response.put("relationShip", res.getRelationShip());
                return response;
            }
        }
        response.put("score", 0.9);
        response.put("cause", new ArrayList<>());
        return response;
    }

    @Override
    public Map<String, Object> predicateByImage(String format, byte[] image) {
        return new HashMap<>();
    }

    @Override
    public List<Map<String, Object>> queryAllIndustries() {
        return predictHelperDao.queryAllIndustry();
    }

    @Override
    public Map<String, Object> getMessage(String text) {
        String industry = ThreadHelper.getThreadContextVar("industry");
        String type = ThreadHelper.getThreadContextVar("type");
        Map<String, Object> result = new LinkedHashMap<>();
        Random rand = new Random();
        rand.setSeed(Clock.systemUTC().millis());
        for (int i = 1; i <= 3; i++) {
            if (i == 1) {
                Map<String, Object> condition = new LinkedHashMap<>();
                addClsIntCondition(condition, industry, type);
                UserBoolQueryBuilder builder = (field, boolQueryBuilder) -> {
                    //必须是满足包含关系的名称
                    BoolQueryBuilder bool1 = QueryBuilders.boolQuery();
                    bool1.must(QueryBuilders.wildcardQuery(field + ".keyword", "*" + text + "*"));
                    boolQueryBuilder.must(bool1);

                    //必须不满足等于
                    BoolQueryBuilder bool2 = QueryBuilders.boolQuery();
                    bool2.mustNot(QueryBuilders.termsQuery(field + ".keyword", text));
                    boolQueryBuilder.must(bool2);
                };
                condition.put("name user", builder);
                val maxCount = 5 + (int) (rand.nextDouble() * 5D);
                AtomicInteger counter = new AtomicInteger(0);
                List<SearchHit> list = EsUtils.execute(brandIndex, brandType, condition, (console, data) -> {
                    console.write(data);
                    if (counter.incrementAndGet() >= maxCount) {
                        console.stop();
                    }
                }, 20);
                result.put("L" + i, list.stream().map(SearchHit::getSource).collect(Collectors.toList()));
            }
            if (i == 2) {
                Map<String, Object> condition = new LinkedHashMap<>();
                addClsIntCondition(condition, industry, type);
                UserBoolQueryBuilder builder = (field, boolQueryBuilder) -> {
                    //必须不满足包含关系的名称
                    BoolQueryBuilder bool1 = QueryBuilders.boolQuery();
                    bool1.mustNot(QueryBuilders.wildcardQuery(field + ".keyword", "*" + text + "*"));
                    boolQueryBuilder.must(bool1);

                    //必须不满足等于
                    BoolQueryBuilder bool2 = QueryBuilders.boolQuery();
                    bool2.mustNot(QueryBuilders.termsQuery(field + ".keyword", text));
                    boolQueryBuilder.must(bool2);

                    //必须满足全量匹配
                    BoolQueryBuilder bool3 = QueryBuilders.boolQuery();
                    bool3.must(QueryBuilders.matchQuery(field, text).operator(Operator.AND));
                    boolQueryBuilder.must(bool3);
                };
                condition.put("name user", builder);
                val maxCount = 5 + (int) (rand.nextDouble() * 5D);
                AtomicInteger counter = new AtomicInteger(0);
                List<SearchHit> list = EsUtils.execute(brandIndex, brandType, condition, (console, data) -> {
                    console.write(data);
                    if (counter.incrementAndGet() >= maxCount) {
                        console.stop();
                    }
                }, 20);
                result.put("L" + i, list.stream().map(SearchHit::getSource).collect(Collectors.toList()));
            }
            if (i == 3) {
                Map<String, Object> condition = new LinkedHashMap<>();
                addClsIntCondition(condition, industry, type);
                UserBoolQueryBuilder builder = (field, boolQueryBuilder) -> {
                    //必须不满足包含关系的名称
                    BoolQueryBuilder bool1 = QueryBuilders.boolQuery();
                    bool1.mustNot(QueryBuilders.wildcardQuery(field + ".keyword", "*" + text + "*"));
                    boolQueryBuilder.must(bool1);

                    //必须不满足等于
                    BoolQueryBuilder bool2 = QueryBuilders.boolQuery();
                    bool2.mustNot(QueryBuilders.termsQuery(field + ".keyword", text));
                    boolQueryBuilder.must(bool2);

                    //必须不满足全量匹配
                    BoolQueryBuilder bool3 = QueryBuilders.boolQuery();
                    bool3.mustNot(QueryBuilders.matchQuery(field, text).operator(Operator.AND));
                    boolQueryBuilder.must(bool3);

                    //必须满足部分匹配
                    BoolQueryBuilder bool4 = QueryBuilders.boolQuery();
                    bool4.must(QueryBuilders.matchQuery(field, text).operator(Operator.OR));
                    boolQueryBuilder.must(bool4);
                };
                condition.put("name user", builder);
                val maxCount = 5 + (int) (rand.nextDouble() * 5D);
                AtomicInteger counter = new AtomicInteger(0);
                List<SearchHit> list = EsUtils.execute(brandIndex, brandType, condition, (console, data) -> {
                    console.write(data);
                    if (counter.incrementAndGet() >= maxCount) {
                        console.stop();
                    }
                }, 20);
                result.put("L" + i, list.stream().map(SearchHit::getSource).collect(Collectors.toList()));
            }
        }
        return result;
    }

    private void addClsIntCondition(Map<String, Object> condition, String industry, String type) {
        if (StringUtils.isNotEmpty(type)) {
            Map<Integer, List<String>> clsMap = trademarkService.getDistinctNumericTypeMultiText(brandIndex, brandType, new HashMap<>(), "intCls");
            List<Map<String, Object>> cates = jdbcDao.query("zqq_class_first", new Toolkit.MapBuilder().put("number =", type).build());
            List<String> intCls = new ArrayList<>();
            for (Map<String, Object> category : cates) {
                String number = (String) category.get("number");
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
            condition.put("intCls keywordIn", intCls);
        } else if (StringUtils.isNotEmpty(industry)) {
            Map<Integer, List<String>> clsMap = trademarkService.getDistinctNumericTypeMultiText(brandIndex, brandType, new HashMap<>(), "intCls");
            List<Map<String, Object>> cates = predictHelperDao.queryByIndustryId(industry);
            List<String> intCls = new ArrayList<>();
            for (Map<String, Object> category : cates) {
                String number = (String) category.get("number");
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
            condition.put("intCls keywordIn", intCls);
        }
    }

}
