package net.dgg.zqq.services.predicate.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 李程 on 2018/10/16.
 */
@Slf4j
@Order(3)
@Component("brandNotAllowRule")
public class BrandNotAllowRuleImpl implements BrandPredicateService.NotAllowRule {

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
    public BrandPredicateService.RuleApplyResponse pass(String text) {

        Map<String, Object> queryBrand = new HashMap<>();

        List<Character> characters = Toolkit.StringHelper.toCharArray(text.replaceAll("\\s+", ""));

        if (characters.size() >= 2) {

            /**
             Set<Character> differenceCharacters = characters.stream().map(c -> {
             Set<Character> cs = new HashSet<>();
             cs.add(c);
             return cs;
             }).reduce((a, b) -> {
             a.addAll(b);
             return a;
             }).get();
             */

            UserBoolQueryBuilder builder = (field, boolQueryBuilder) -> {
                List<String> words = IKAnalysisHelper.spliteSingleChineseAndSingleWord(text);
                for (String word : words) {
                    MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(field, word.toLowerCase()).operator(Operator.AND);
                    boolQueryBuilder.must(matchQueryBuilder);
                }
                try {
                    String code = new String(IOUtils.toByteArray(EsClientUtils.class.getResourceAsStream("/script/search_brand_reference.painless")));
                    boolQueryBuilder.must(QueryBuilders.scriptQuery(new Script(code.replace("${length}", "" + text.replaceAll("\\s+", "").length()))));
                } catch (Exception e) {
                    log.error("{}", e);
                    throw new IllegalStateException(e);
                }
                /**
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
                 bool.should(QueryBuilders.wildcardQuery(field.concat(".keyword"), lowcaseQuery));
                 bool.should(QueryBuilders.wildcardQuery(field.concat(".keyword"), upcaseQuery));
                 boolQueryBuilder.must(bool);
                 }
                 String code = new String(IOUtils.toByteArray(EsClientUtils.class.getResourceAsStream("/script/search_brand_reference.painless")));
                 boolQueryBuilder.must(QueryBuilders.scriptQuery(new Script(code.replace("${length}", "" + text.replaceAll("\\s+", "").length()))));
                 } catch (Exception e) {
                 e.printStackTrace();
                 }
                 */
            };

            queryBrand.put("name user", builder);

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
                queryBrand.put("intCls keywordIn", intCls);
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
                queryBrand.put("intCls keywordIn", intCls);
            }

            //List<Map<String, Object>> list = EsUtils.getAll(brandIndex, brandType, queryBrand);
            List<SearchHit> hits = EsUtils.execute(brandIndex, brandType, queryBrand, (console, data) -> {
                String name = (String) data.getSource().get("name");
                if (text.equalsIgnoreCase(name)) {
                    console.write(data);
                    console.stop();
                }
            }, 20);
            List<Map<String, Object>> list = hits.stream().map(hit -> hit.getSource()).collect(Collectors.toList());
            for (Map<String, Object> map : list) {
                String name = (String) map.get("name");
                if (name.equalsIgnoreCase(text)) {
                    List<String> messages = new ArrayList<>();
                    messages.add("已存在该商标： " + name);
                    return BrandPredicateService.RuleApplyResponse.builder().messages(messages).score(0).passed(false).relationShip(map).build();
                }
            }
        } else if (characters.size() == 1) {
            queryBrand.put("name keywordIn", new String[]{text.replaceAll("\\s", "").toLowerCase(), text.replaceAll("\\s", "").toUpperCase()});
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
                queryBrand.put("intCls keywordIn", intCls);
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
                queryBrand.put("intCls keywordIn", intCls);
            }
            long count = EsUtils.count(brandIndex, brandType, queryBrand);
            if (count > 0L) {
                List<String> messages = new ArrayList<>();
                messages.add("已存在该商标： " + text);
                return BrandPredicateService.RuleApplyResponse.builder().messages(messages).score(0).relationShip(text).passed(false).build();
            }
        }
        return BrandPredicateService.RuleApplyResponse.builder().passed(true).score(0.9).build();
    }
}
