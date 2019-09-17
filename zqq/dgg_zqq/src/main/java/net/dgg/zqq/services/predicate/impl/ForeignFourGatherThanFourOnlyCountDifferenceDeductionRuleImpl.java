package net.dgg.zqq.services.predicate.impl;

import lombok.SneakyThrows;
import net.dgg.zqq.dao.JdbcDao;
import net.dgg.zqq.dao.predict.PredictHelperDao;
import net.dgg.zqq.services.predicate.BrandPredicateService;
import net.dgg.zqq.utils.ThreadHelper;
import net.dgg.zqq.utils.Toolkit;
import net.dgg.zqq.utils.es.EsClientUtils;
import net.dgg.zqq.utils.es.EsUtils;
import net.dgg.zqq.utils.es.stack.UserBoolQueryBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李程 on 2018/10/18.
 */
@Order(19)
@Component("foreignFourGatherThanFourOnlyCountDifferenceDeductionRule")
public class ForeignFourGatherThanFourOnlyCountDifferenceDeductionRuleImpl implements BrandPredicateService.DeductionRule {

    @Autowired
    PredictHelperDao predictHelperDao;

    @Value("${firstTrial.brand.index}")
    private String brandIndex;

    @Value("${firstTrial.brand.type}")
    private String brandType;

    @Autowired
    JdbcDao jdbcDao;

    @Override
    @SneakyThrows
    public BrandPredicateService.RuleApplyResponse deduction(String text) {
        String searchIndex = brandIndex;
        String searchType = brandType;
        if (Toolkit.StringHelper.isAllEnglishChar(text)) {
            if (text.replaceAll("\\s", "").length() >= 4) {
                List<Character> characters = Toolkit.StringHelper.toCharArray(text.replaceAll("\\s", ""));
                Map<String, Object> query = new LinkedHashMap<>();
                UserBoolQueryBuilder builder = (field, boolQueryBuilder) -> {
                    try {
                        BoolQueryBuilder shouldBuilder = QueryBuilders.boolQuery();
                        for (int i = 0; i < characters.size(); i++) {
                            BoolQueryBuilder bool = QueryBuilders.boolQuery();
                            for (int j = 0; j < characters.size(); j++) {
                                if (j != i) {
                                    bool.must(QueryBuilders.wildcardQuery(field, "*" + characters.get(j) + "*"));
                                }
                            }
                            shouldBuilder.should(bool);
                        }
                        boolQueryBuilder.must(shouldBuilder);
                        String code = new String(IOUtils.toByteArray(EsClientUtils.class.getResourceAsStream("/script/search_brand_reference.painless")));
                        boolQueryBuilder.must(QueryBuilders.scriptQuery(new Script(code.replace("${length}", "" + text.replaceAll("\\s+", "").length()))));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
                query.put("name user", builder);

                String industry = ThreadHelper.getThreadContextVar("industry");
                String type = ThreadHelper.getThreadContextVar("type");
                if (StringUtils.isNotEmpty(type)) {
                    List<Map<String, Object>> cates = jdbcDao.query("zqq_class_first", new Toolkit.MapBuilder().put("number =", type).build());
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
                    query.put("intCls keywordIn", intCls);
                } else if (StringUtils.isNotEmpty(industry)) {
                    List<Map<String, Object>> cates = predictHelperDao.queryByIndustryId(industry);
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
                    query.put("intCls keywordIn", intCls);
                }
                List<Map<String, Object>> list = EsUtils.getAll(searchIndex, searchType, query);
                for (Map<String, Object> map : list) {
                    String name1 = (String) map.get("name");
                    String name = ((String) map.get("name")).replaceAll("\\s", "");
                    String compare = text.replaceAll("\\s", "");
                    if (name.length() == compare.length()) {
                        int diff = 0;
                        int diffIndex = -1;
                        for (int i = 0; i < compare.length(); i++) {
                            if (name.charAt(i) != compare.charAt(i)) {
                                diffIndex = i;
                                diff++;
                            }
                        }
                        if (diff == 1) {
                            List<String> messages = new ArrayList<>();
                            if (diffIndex != 0) {
                                messages.add("你的商标中只有一个字母与商标：\"" + name1 + "\"  不同");
                                return BrandPredicateService.RuleApplyResponse.builder().score(0.35).relationShip(map).passed(false).messages(messages).build();
                            } else {
                                messages.add("你的商标中首个字母与商标：\"" + name1 + "\" 不同");
                                return BrandPredicateService.RuleApplyResponse.builder().score(0.8).relationShip(map).passed(false).messages(messages).build();
                            }
                        }
                    }
                }
            }
        }
        return BrandPredicateService.RuleApplyResponse.builder().passed(true).score(0.9).build();
    }
}
