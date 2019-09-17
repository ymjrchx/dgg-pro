package net.dgg.zqq.services.predicate.impl;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import net.dgg.zqq.dao.JdbcDao;
import net.dgg.zqq.dao.predict.PredictHelperDao;
import net.dgg.zqq.services.predicate.BrandPredicateService;
import net.dgg.zqq.utils.IKAnalysisHelper;
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
 * Created by 李程 on 2018/10/18.
 */

@Order(7)
@Component("twoForeignDeductionRule")
public class TwoForeignDeductionRuleImpl implements BrandPredicateService.DeductionRule {

    @Autowired
    PredictHelperDao predictHelperDao;

    @Value("${firstTrial.brand.index}")
    private String brandIndex;

    @Value("${firstTrial.brand.type}")
    private String brandType;

    @Autowired
    JdbcDao jdbcDao;

    @SneakyThrows
    @Override
    public BrandPredicateService.RuleApplyResponse deduction(String text) {
        List<Character> characters = Toolkit.StringHelper.toCharArray(text.replaceAll("\\s+", ""));
        for (char c : characters) {
            if (c > 128) {
                return BrandPredicateService.RuleApplyResponse.builder().passed(true).score(0.9).build();
            }
        }
        List<String> segments = IKAnalysisHelper.segment(text);
        if (segments.size() != 2) {
            return BrandPredicateService.RuleApplyResponse.builder().passed(true).score(0.9).build();
        }
        List<String> reverse = Lists.reverse(segments);
        String new_word = Toolkit.StringHelper.joinUse(reverse.toArray(new String[0]), " ");

        Set<Character> differenceCharacters = characters.stream().filter(c -> c.charValue() != " ".toCharArray()[0]).map(c -> {
            Set<Character> cs = new HashSet<>();
            cs.add(c);
            return cs;
        }).reduce((a, b) -> {
            a.addAll(b);
            return a;
        }).get();

        Map<String, Object> queryContains = new LinkedHashMap<>();
        UserBoolQueryBuilder builder = (field, boolQueryBuilder) -> {
            try {
                for (Character c : differenceCharacters) {
                    BoolQueryBuilder bool = QueryBuilders.boolQuery();
                    int count = Toolkit.StringHelper.getFrequency(c, characters);
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
        for (Map<String, Object> map : list) {
            String name = (String) map.get("name");
            if (name.equalsIgnoreCase(new_word)) {
                List<String> messages = new ArrayList<>();
                messages.add("有英文反转双单词商标：\"" + name + "\"");
                return BrandPredicateService.RuleApplyResponse.builder().passed(false).score(0.45).relationShip(map).messages(messages).build();
            }
        }

        return BrandPredicateService.RuleApplyResponse.builder().passed(true).score(0.9).build();
    }
}
