package net.dgg.gspt.dqc.utils.es.services;

import com.alibaba.fastjson.JSON;
import net.dgg.gspt.dqc.dto.brandsearch.ApproParam;
import net.dgg.gspt.dqc.dto.brandsearch.ImgSearchReqParam;
import net.dgg.gspt.dqc.dto.brandsearch.Kv;
import net.dgg.gspt.dqc.framework.PTConst;
import net.dgg.gspt.dqc.services.search.SearchService;
import net.dgg.gspt.dqc.utils.es.EsUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * Created by jiangsh on 2018/6/22 14:45
 */
@Service
public class EsService {

    @Resource
    private SearchService searchService;

    Logger logger = LoggerFactory.getLogger(EsService.class);

    public List<Map> getMsgByKeyWord(String key, String companyId, String index, String type) {
        return EsUtils.getMsgByKeyWord(index, type, key, companyId, 1);
    }

    public List<Map> getMsgByKeyWordCommon(String key, String val, String index, String type) {
        return EsUtils.getMsgByKeyWord(index, type, key, val, 1);
    }

    public List<Map> getDataOneBrand(String index, String type, String typeVal, String appNoVal) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(matchPhraseQuery(PTConst.BRAND_TYPE, typeVal));
        qb.must(matchPhraseQuery(PTConst.BRAND_REGNO, appNoVal));
        return EsUtils.getDataOne(index, type, qb, 1);
    }


    public long getTotalCountBrand(Map<String, String> condition, Map<String, String> map, String index, String type, boolean isphrase, String[] include, String[] exclude, ApproParam ap) {
        return EsUtils.getTotalCountIndex(index, type, condition,
                null, null, null, 0, 0, map, isphrase, include, exclude, listConditionQb(ap));
    }

    public List<Map> getListMapBrand(Map<String, String> condition, int startCount, int endCount,
                                     Map<String, String> map, String index, String type, boolean isphrase, String[] include, String[] exclude, ApproParam ap) {
        Map sort = new HashMap();
        return EsUtils.getListMapByIndex(index, type, condition,
                null, null, sort, startCount, endCount, map, isphrase, include, exclude, listConditionQb(ap));
    }


    public long getTotalCountPatent(Map<String, String> m, Map<String, String> keyMap, String index, String type) {
        return EsUtils.getTotalCount(index, type, patentQb(m, keyMap));
    }

    public List<Map> getListMapPatent(int startCount, int endCount, Map<String, String> map, Map<String, String> keyMap, String index, String type, String[] include, String[] exclude, String sortKey) {
        return EsUtils.getMsgByKeyWord(index, type, patentQb(map, keyMap), startCount, endCount, include, exclude, sortKey);
    }


    public List<Map> getListMapBrandManager(String index, String type, int startCount, int endCount, String key, String[] applicantCn) {
        String[] include = {"tmiPath", "name", "applicantCn", "regNo", "intCls", "appDate", "status", "imageUrl"};
        String[] exclude = {"", ""};
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        for (int i = 0; i < applicantCn.length; i++)
            qb.should(matchPhraseQuery(key, applicantCn[i]));
        return EsUtils.getMsgByKeyWord(index, type, qb, startCount, endCount, include, exclude, null);
    }

    public List<Map> getListMap(String index, String type, Map<String, String> map, int startCount, int endCount, String[] include, String[] exclude, String sortKey) {
        return EsUtils.getMsgByKeyWord(index, type, taxQb(map), startCount, endCount, include, exclude, sortKey);
    }


    public List<String> getPart(String index, String type, String key, String keyWord, int count, String[] include, String sortKey) {
        List<String> result = new ArrayList<>();
        final String[] excludes = {"", ""};
        List<Map> list = EsUtils.getPart(index, type, matchPhraseQuery(key, keyWord), include, excludes, count, sortKey);
        if (list != null && list.size() > 0) {
            for (Map m : list) {
                final String cn = m.get(PTConst.BRAND_APPLICATCN).toString();
                if (!result.contains(cn))
                    result.add(cn);
            }
        }
        return result;
    }

    public List<Map> getPart(String index, String type, String keyWord, int count, String[] include, Map<String, String> m, String sortKey) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        String[] excludes = {"", ""};
        for (Map.Entry<String, String> entry : m.entrySet())
            qb.should(matchPhraseQuery(entry.getKey(), keyWord));
        return EsUtils.getPart(index, type, qb, include, excludes, count, sortKey);
    }

    //列表搜索聚合
    public Map<String, Long> filterAggregationCount(AggregationBuilder ab, boolean out,
                                                    Map<String, String> map, Map<String, String> tmpMap, String index, String type, ApproParam ap) {
        if (ab == null) return null;
        BoolQueryBuilder qb = listConditionQb(ap);
        BoolQueryBuilder orQuery2 = QueryBuilders.boolQuery();
        orQuery2.must(qb);

        if (map != null && map.size() > 0) tmpMap.putAll(map);
        logger.info("=============tmpMap===========" + JSON.toJSONString(tmpMap));

        for (Map.Entry<String, String> entry : tmpMap.entrySet())  //组装条件查询，根据不同的数据字典信息，制定处理方式
            if (entry.getKey().equals(PTConst.BRAND_APPDATE)) //申请年份
                EsUtils.startDate(entry.getValue(), orQuery2);

            else if (entry.getKey().equals(PTConst.BRAND_TYPE)) //申请类别
                moreValue(orQuery2, entry, "-");

            else if (out) //法律状态、申请小类别
                moreValue(orQuery2, entry, "-");

            else
                orQuery2.must(matchQuery(entry.getKey(), entry.getValue()));

        tmpMap.clear();
        return EsUtils.aggreage(ab, orQuery2, index, type, PTConst.AGGREATE_LIST);
    }

    //图片搜索聚合
    public Map<String, Long> filterAggregationCount(AggregationBuilder ab, String[] str, String index, String type) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        for (String s : str)
            qb.should(matchPhraseQuery(PTConst.BRAND_IMG_URL, s));

        return EsUtils.aggreage(ab, qb, index, type, PTConst.AGGREATE_LIST);
    }

    public List<Map> imgSearchList(String index, String type, String resultString, Map<String, String> map, int count, boolean flag, ImgSearchReqParam isr) {
        final String[] include = {"regNo", "status", "applicantCn", "intCls", "tmiPath"};
        final String[] exclude = {"", ""};
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        final String[] str = resultString.split(";");
        final int pn = Integer.valueOf(isr.getPn());
        final int rn = Integer.valueOf(isr.getRn());
        int start = (pn - 1) * rn;
        int end = pn * rn;
        for (int i = 0; i < str.length; i++)
            if (flag)
                query.should(matchPhraseQuery(PTConst.BRAND_IMG_URL, str[i]));
            else if (start <= i && end >= i)
                query.should(matchPhraseQuery(PTConst.BRAND_IMG_URL, str[i]));

        BoolQueryBuilder query2 = QueryBuilders.boolQuery();
        if (map.get(PTConst.BRAND_TYPE) != null)
            EsUtils.dealBrandApplyType(query2, map.get(PTConst.BRAND_TYPE), PTConst.BRAND_TYPE);

        if (map.get(PTConst.BRAND_STATE) != null)
            query2.should(matchPhraseQuery(PTConst.BRAND_STATE, map.get(PTConst.BRAND_STATE)));

        if (map.get(PTConst.BRAND_APPDATE) != null)
            EsUtils.startDate(map.get(PTConst.BRAND_APPDATE), query2);
        qb.filter(query).must(query2);
        List<Map> listMap = EsUtils.getAllNotLimit(index, type, include, exclude, qb, flag ? str.length : count);
        if (flag && listMap != null && listMap.size() > 0) {
            List<Map> result = new ArrayList<>();
            for (int i = 0; i < listMap.size(); i++)
                if (start <= i && end > i)
                    result.add(listMap.get(i));
            return result;
        } else return listMap;
    }

    public long getTotalCount(String index, String type, Map<String, String> m) {
        return EsUtils.getTotalCount(index, type, taxQb(m));
    }

    public static List<String> getXjz(String index, String type, String key, String keyWord, int count) {
        List<String> result = new ArrayList<>();
        final String[] include = {"value"};
        final String[] exclude = {"", ""};
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(matchPhraseQuery(key, keyWord));
        List<Map> list = EsUtils.getAllNotLimit(index, type, include, exclude, qb, count);
        if (list != null && list.size() > 0) {
            for (Map m : list) {
                final String v = m.get("value").toString();
                if (v.length() > 0) {
                    String[] vs = v.split("、");
                    for (int i = 0; i < vs.length; i++) {
                        result.add(vs[i]);
                    }
                }
            }
        }
        return result;
    }

    public static Object getGlc(String index, String type, String key, String keyWord, int count) {
        final String[] include = {"value"};
        final String[] exclude = {"", ""};
        List<Kv> result = new ArrayList<>();
        if (keyWord.contains("、")) {
            String[] kw = keyWord.split("、");
            for (String s : kw) {
                BoolQueryBuilder qb = QueryBuilders.boolQuery();
                qb.must(matchPhraseQuery(key, s));
                result.add(new Kv(s, EsUtils.getAllNotLimit(index, type, include, exclude, qb, count).get(0).get("value")));
            }
        } else {
            BoolQueryBuilder qb = QueryBuilders.boolQuery();
            qb.must(matchPhraseQuery(key, keyWord));
            result.add(new Kv(keyWord, EsUtils.getAllNotLimit(index, type, include, exclude, qb, count).get(0).get("value")));
        }
        return result;
    }

    private BoolQueryBuilder patentQb(Map<String, String> m, Map<String, String> keyMap) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        if (m != null && m.size() > 0) {
            if (StringUtils.isNotEmpty(m.get(PTConst.PATENT_DATE_TYPE))) //日期
                EsUtils.patentDateQuery(qb, m.get(PTConst.PATENT_DATE_TYPE), m.get(PTConst.PATENT_DATE_START), m.get(PTConst.PATENT_DATE_END));

            if (StringUtils.isNotEmpty(m.get(PTConst.INDEX_ZLLB))) { //专利类别
                BoolQueryBuilder orQuery2 = QueryBuilders.boolQuery();
                moreValue(orQuery2, m.get(PTConst.INDEX_ZLLB), PTConst.INDEX_ZLLB, "-", "should");
                qb.must(orQuery2);
            }

            if (StringUtils.isNotEmpty(m.get(PTConst.INDEX_FLZT))) { //法律状态
                BoolQueryBuilder orQuery2 = QueryBuilders.boolQuery();
                moreValue(orQuery2, m.get(PTConst.INDEX_FLZT), PTConst.INDEX_FLZT, "-", "should");
                qb.must(orQuery2);
            }

            //下面页面点击出现的条件
            if (StringUtils.isNotEmpty(m.get(PTConst.INDEX_ZLFMR))) { //发明人
                qb.must(matchPhraseQuery(PTConst.INDEX_ZLFMR, m.get(PTConst.INDEX_ZLFMR)));
            }

            if (StringUtils.isNotEmpty(m.get(PTConst.PATENT_APPLICATION_DATE))) { //申请日
                qb.must(matchPhraseQuery(PTConst.PATENT_APPLICATION_DATE, m.get(PTConst.PATENT_APPLICATION_DATE)));
            }

//            if (StringUtils.isNotEmpty(m.get(PTConst.INDEX_TYPE_NUM))) { //分类号
//                qb.must(matchPhraseQuery(PTConst.INDEX_TYPE_NUM, m.get(PTConst.INDEX_TYPE_NUM)));
//            }

        }

        if (keyMap != null && keyMap.size() > 0)
            Qb(qb, keyMap, "、");

        return qb;
    }

    private BoolQueryBuilder taxQb(Map<String, String> m) {
        return Qb(QueryBuilders.boolQuery(), m, "、");
    }

    private BoolQueryBuilder Qb(BoolQueryBuilder qb, Map<String, String> m, String fh) {
        for (Map.Entry<String, String> entry : m.entrySet()) {
            if (entry.getValue().contains(fh)) {
                String[] v = entry.getValue().split(fh);
                for (int i = 0; i < v.length; i++)
                    qb.should(matchPhraseQuery(entry.getKey(), v[i]));
            } else qb.should(matchPhraseQuery(entry.getKey(), entry.getValue()));
        }
        return qb;
    }

    public void dealQb(String ft, String kw, BoolQueryBuilder query) {

        BoolQueryBuilder qb = QueryBuilders.boolQuery();

        if (ft.contains("all")) {
            searchService.allItem(qb, kw, ft);
        } else {
            if (ft.contains(PTConst.FT_JQ)) //精确
                searchService.jqItem(qb, kw);
            if (ft.contains(PTConst.FT_BF)) //部分
                searchService.bfItem(qb, kw);
            if (ft.contains(PTConst.FT_JIAZ)) //加字
                searchService.jiazItem(qb, kw);
            if (ft.contains(PTConst.FT_JIANZ)) //减字
                searchService.jianzItem(qb, kw);
            if (ft.contains(PTConst.FT_BZ)) //变字
                searchService.bzItem(qb, kw);
            if (ft.contains(PTConst.FT_HX)) //换序
                searchService.hxItem(qb, kw);
            if (ft.contains(PTConst.FT_PY)) //拼音
                searchService.pinyinItem(qb, kw, ft);
            if (ft.contains(PTConst.FT_TSZF)) //特殊字符
                searchService.special(qb, kw, ft);
            if (ft.contains(PTConst.FT_XJZ))  //形近字
                searchService.xjzItem(qb, kw);
        }

        query.must(qb);
    }

    private void moreValue(BoolQueryBuilder orQuery2, Map.Entry<String, String> entry, String fh) {
        if (entry.getValue().contains(fh)) {
            String[] types = entry.getValue().split(fh);
            for (int i = 0; i < types.length; i++)
                orQuery2.must(matchPhraseQuery(entry.getKey(), types[i]));
        } else
            orQuery2.must(matchPhraseQuery(entry.getKey(), entry.getValue()));
    }

    private void moreValue(BoolQueryBuilder orQuery2, String str, String key, String fh, String method) {
        if (StringUtils.isNotEmpty(str)) {
            if (str.contains(fh)) {
                String[] s = str.split(fh);
                for (String o : s) {
                    if (method.equals("should"))
                        orQuery2.should(matchPhraseQuery(key, o));
                    else
                        orQuery2.must(matchPhraseQuery(key, o));
                }
            } else if (method.equals("should"))
                orQuery2.should(matchPhraseQuery(key, str));
            else
                orQuery2.must(matchPhraseQuery(key, str));
        }
    }

    private BoolQueryBuilder listConditionQb(ApproParam ap) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        //处理近似商标下面多个复选框查询条件
        if (ap.getKey().equals(PTConst.KEY_ITEM)) //近似商标
            dealQb(ap.getFilterType(), ap.getKeyWord(), qb); //通过ft,按关键字过滤匹配方式
        else if (ap.getKey().equals(PTConst.BRAND_NAME)) { //精准商标选项，精确匹配
//            qb.filter(QueryBuilders.matchQuery(PTConst.BRAND_NAME + ".keyword", ap.getKeyWord()));
            BoolQueryBuilder query = QueryBuilders.boolQuery();
            query.should(QueryBuilders.matchQuery(PTConst.BRAND_NAME + ".keyword", ap.getKeyWord())); //商标名
            query.should(QueryBuilders.matchQuery(PTConst.BRAND_APPLICATCN + ".keyword", ap.getKeyWord())); //申请人
            query.should(QueryBuilders.matchQuery(PTConst.BRAND_REGNO + ".keyword", ap.getKeyWord())); //申请号
            qb.filter(query);
        } else
            qb.should(matchPhraseQuery(ap.getKey(), ap.getKeyWord()));
        return qb;
    }

    public List<String> getApplyPerson(String index, String type, String key, String applicantCn, int count) {
        final String[] include = {PTConst.BRAND_APPLICATCN};
        final String[] excludes = {"", ""};
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(matchPhraseQuery(key, applicantCn));
        List<Map> list = EsUtils.getAllNotLimit(index, type, include, excludes, qb, count);
        List<String> result = new ArrayList<>();
        if (list != null && list.size() > 0)
            for (Map m : list)
                result.add(m.get(key).toString());

        return result;
    }
}
