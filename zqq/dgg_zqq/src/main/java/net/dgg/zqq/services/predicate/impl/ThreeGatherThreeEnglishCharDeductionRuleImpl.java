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

import java.util.*;

/**
 * Created by 李程 on 2018/10/17.
 */
@Order(8)
@Component("threeGatherThreeEnglishCharDeductionRule")
public class ThreeGatherThreeEnglishCharDeductionRuleImpl implements BrandPredicateService.DeductionRule {

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
        if (text.replaceAll("\\s+", "").length() >= 3) {
            List<Character> chars = Toolkit.StringHelper.toCharArray(text);
            for (char ch : chars) {
                if (ch > 128) {
                    return BrandPredicateService.RuleApplyResponse.builder().passed(true).score(0.9).build();
                }
            }

            //优化性能，测试是否三个字母是否有重复
            Set<Character> differenceCharacters = chars.stream().filter(c -> c.charValue() != " ".toCharArray()[0]).map(c -> {
                Set<Character> cs = new HashSet<>();
                cs.add(c);
                return cs;
            }).reduce((a, b) -> {
                a.addAll(b);
                return a;
            }).get();

            //不同的字母数量大于等于2
            if (differenceCharacters.size() >= 2) {
                Map<String, Object> queryContains = new LinkedHashMap<>();
                UserBoolQueryBuilder builder = (field, boolQueryBuilder) -> {
                    try {
                        for (Character c : differenceCharacters) {
                            BoolQueryBuilder bool = QueryBuilders.boolQuery();
                            int count = Toolkit.StringHelper.getFrequency(c, chars);
                            String lowcaseQuery = "*";
                            String upcaseQuery = "*";
                            for (int i = 0; i < count; i++) {
                                lowcaseQuery += new String(new char[]{c}).toLowerCase() + "*";
                                upcaseQuery += new String(new char[]{c}).toUpperCase() + "*";
                            }
                            bool.should(QueryBuilders.wildcardQuery(field, lowcaseQuery));
                            bool.should(QueryBuilders.wildcardQuery(field, upcaseQuery));
                            boolQueryBuilder.must(bool);
                        }
                        String code = new String(IOUtils.toByteArray(EsClientUtils.class.getResourceAsStream("/script/search_brand_reference.painless")));
                        boolQueryBuilder.must(QueryBuilders.scriptQuery(new Script(code.replace("${length}", "" + text.replaceAll("\\s+", "").length()))));
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new IllegalStateException(e);
                    }
                };
                queryContains.put("name user", builder);

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
                    queryContains.put("intCls keywordIn", intCls);
                } else if (StringUtils.isNotEmpty(industry)) {
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
                    queryContains.put("intCls keywordIn", intCls);
                }

                List<Map<String, Object>> list = EsUtils.getAll(brandIndex, brandType, queryContains);
                if (list == null || list.isEmpty()) {
                    return BrandPredicateService.RuleApplyResponse.builder().passed(true).score(0.9).build();
                } else {
                    for (Map<String, Object> nameObj : list) {
                        String name = ((String) nameObj.get("name")).toUpperCase().replaceAll("\\s+", "");
                        List<Character> nameChars = Toolkit.StringHelper.toCharArray(name);
                        List<Character> textChars = Toolkit.StringHelper.toCharArray(text.toUpperCase().replaceAll("\\s+", ""));
                        if (nameChars.size() == textChars.size()) {
                            boolean a1 = nameChars.stream().map(textChars::contains).reduce((a, b) -> a && b).get();
                            boolean b1 = textChars.stream().map(nameChars::contains).reduce((a, b) -> a && b).get();
                            if (a1 && b1) {
                                List<String> messages = new ArrayList<>();
                                messages.add("发现近似单词：\"" + name + "\"");
                                return BrandPredicateService.RuleApplyResponse.builder().passed(false).score(0.25).relationShip(nameObj).messages(messages).build();
                            }
                        }
                    }
                }
            } else if (differenceCharacters.size() == 1) {
                Map<String, Object> queryContains = new LinkedHashMap<>();
                queryContains.put("name keywordIn", new String[]{text.replaceAll("\\s+", "").toLowerCase(), text.replaceAll("\\s+", "").toUpperCase()});
                Long count = EsUtils.count(brandIndex, brandType, queryContains);
                if (count >= 1) {
                    List<String> messages = new ArrayList<>();
                    messages.add("发现相同商标：" + text);
                }
            }
        }
        return BrandPredicateService.RuleApplyResponse.builder().

                passed(true).

                score(0.9).

                build();
    }
}
