package net.dgg.zqq.services.search;

import com.alibaba.fastjson.JSON;
import net.dgg.zqq.constant.LayStatusConstant;
import net.dgg.zqq.dto.brandsearch.ImgSearchRes2Param;
import net.dgg.zqq.framework.PTConst;
import net.dgg.zqq.framework.xst.AsciiUtils;
import net.dgg.zqq.framework.xst.ClientAipOcr;
import net.dgg.zqq.utils.BrandUtil;
import net.dgg.zqq.utils.es.EsConst;
import net.dgg.zqq.utils.es.services.EsService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @ClassName: SearchService
 * @Description: 搜索服务
 * @Author: jiangsh
 * @Date: 2018/10/9 20:19
 */

@Service
public class SearchService {

    /**
     * 图片搜索服务2
     * 响应前端
     *
     * @param result 响应集
     */
    public ImgSearchRes2Param getImgSearchRes2(String result) {
        ImgSearchRes2Param isr = new ImgSearchRes2Param();
        if (StringUtils.isNotEmpty(result)) {
            JSONObject jo = JSONObject.fromObject(result);
            JSONArray ja = jo.getJSONArray("result");
            if (!ja.isEmpty()) {
                List<ImgSearchRes2Param.Result> list = new ArrayList<>();
                for (int i = 0; i < ja.size(); i++) {
                    ImgSearchRes2Param.Result res = new ImgSearchRes2Param.Result();
                    res.setBrief(briefs(AsciiUtils.decode(ja.getJSONObject(i).get("brief").toString(), AsciiUtils.key)));
                    res.setScore(ja.getJSONObject(i).get("score").toString());
                    res.setContSign(ja.getJSONObject(i).get("cont_sign").toString());
                    list.add(res);
                }
                isr.setResult(list);
            }

            isr.setHasMore(Boolean.valueOf(jo.get("has_more").toString()));
            isr.setLogId(jo.get("log_id").toString());
            isr.setResultNum(Long.valueOf(jo.get("result_num").toString()));

        }
        return isr;
    }

    private ImgSearchRes2Param.Brief briefs(String brief) {
        ImgSearchRes2Param.Brief b = new ImgSearchRes2Param.Brief();
        if (StringUtils.isNotEmpty(brief)) {
            JSONObject jo = JSONObject.fromObject(brief);
            b.setEsId(jo.get("esId").toString());
            b.setImgUrl(jo.get("imgUrl").toString());
        }
        return b;
    }


    /**
     * 精确
     */
    public void jqItem(BoolQueryBuilder qb, String kw) {
        qb.should(QueryBuilders.matchQuery(PTConst.BRAND_NAME + ".keyword", kw).boost(2.0f)); //商标名
    }


    /**
     * 部分
     */
    public void bfItem(BoolQueryBuilder qb, String kw) {
        qb.should(matchPhraseQuery(PTConst.BRAND_NAME, kw));
    }


    /**
     * 加字
     */
    public void jiazItem(BoolQueryBuilder qb, String kw) {
        qb.should(regexpQuery(PTConst.BRAND_NAME, kw.concat("*")));
//        qb.mustNot(termQuery(PTConst.BRAND_NAME, kw));
    }


    /**
     * 减字
     */
    public void jianzItem(BoolQueryBuilder qb, String kw) {
        qb.should(fuzzyQuery(PTConst.BRAND_NAME, kw).fuzziness(Fuzziness.ONE));
    }


    /**
     * 变字
     */
    public void bzItem(BoolQueryBuilder qb, String kw) {
        final int count = 10;
        if (kw.length() > 1) {  //根据最后一个字的形近字变化，在结合前面的字符串，一起去匹配名称
            final String key = kw.substring(kw.length() - 1);
            final String one = kw.substring(0, kw.length() - 1);
            List<String> result = EsService.getXjz(EsConst.INDEX_XJZ, EsConst.TYPE_COMPANY_XJZ, "word", key, count);
            for (int i = 0; i < result.size(); i++)
                qb.should(matchPhraseQuery(PTConst.BRAND_NAME, one.concat(result.get(i))));

        } else { //只有一个字时，按第一个字的形近字，去匹配名称
            List<String> result = EsService.getXjz(EsConst.INDEX_XJZ, EsConst.TYPE_COMPANY_XJZ, "word", kw, count);
            for (int i = 0; i < result.size(); i++)
                qb.should(matchPhraseQuery(PTConst.BRAND_NAME, result.get(i)));
        }
    }

    /**
     * 换序
     */
    public void hxItem(BoolQueryBuilder qb, String kw) {
        qb.should(matchPhraseQuery(PTConst.BRAND_NAME, BrandUtil.hxSort(kw)));
    }

    /**
     * 拼音
     */
    public void pinyinItem(BoolQueryBuilder qb, String kw, String ft) {
        BoolQueryBuilder orQuery = QueryBuilders.boolQuery();
        if (!BrandUtil.flagChar(kw)) { //不包含汉字
            orQuery.should(matchPhraseQuery(PTConst.BRAND_NAME, kw.toLowerCase()));
            orQuery.should(matchPhraseQuery(PTConst.BRAND_NAME, kw.toUpperCase()));
        } else {
            orQuery.must(termQuery(PTConst.BRAND_NAME, kw.concat("y")));
        }
        qb.should(orQuery);
    }

    /**
     * 特殊字符
     */
    public void special(BoolQueryBuilder qb, String kw, String ft) {
        if (BrandUtil.specialChar().contains(kw))
            qb.should(matchPhraseQuery(PTConst.BRAND_NAME, kw));
        else if (ft.equals(PTConst.FT_TSZF) || ft.equals("py-tszf"))
            qb.must(termQuery(PTConst.BRAND_NAME, kw.concat("y")));
    }

    /**
     * 形近字
     */
    public void xjzItem(BoolQueryBuilder qb, String kw) {
        final int count = 10;
        if (kw.length() > 1) {  //根据第一个字的形近字变化，在结合后面的字符串，一起去匹配名称
            String two = kw.substring(1);
            List<String> result = EsService.getXjz(EsConst.INDEX_XJZ, EsConst.TYPE_COMPANY_XJZ, "word", kw.substring(0, 1), count);
            for (int i = 0; i < result.size(); i++)
                qb.should(matchPhraseQuery(PTConst.BRAND_NAME, result.get(i).concat(two)));

        } else { //只有一个字时，按第一个字的形近字，去匹配名称
            List<String> result = EsService.getXjz(EsConst.INDEX_XJZ, EsConst.TYPE_COMPANY_XJZ, "word", kw, count);
            for (int i = 0; i < result.size(); i++)
                qb.should(matchPhraseQuery(PTConst.BRAND_NAME, result.get(i)));
        }
    }


    private String converBrief(String s) {
        if (StringUtils.isNotEmpty(s))
            return s.replace("=", ":");
        else return "-";
    }

    public void allItem(BoolQueryBuilder qb, String kw, String ft) {
        jqItem(qb, kw);
        bfItem(qb, kw);
        jiazItem(qb, kw);
        jianzItem(qb, kw);
        bzItem(qb, kw);
        hxItem(qb, kw);
        pinyinItem(qb, kw, ft);
        special(qb, kw, ft);
        xjzItem(qb, kw);
    }

    public List<Map> converListMap(List<Map> list) {
        if (list != null && list.size() > 0) {
            for (Map m : list) {
                if (m.get(PTConst.BRAND_STATE) != null) {
                    final String status = m.get(PTConst.BRAND_STATE).toString();
                    if (LayStatusConstant.one().contains(status))
                        m.put(PTConst.LAW_RULES, LayStatusConstant.oneResult());
                    else if (LayStatusConstant.two().contains(status))
                        m.put(PTConst.LAW_RULES, LayStatusConstant.twoResult());
                    else if (LayStatusConstant.three().contains(status))
                        m.put(PTConst.LAW_RULES, LayStatusConstant.threeResult());
                    else if (LayStatusConstant.four().contains(status))
                        m.put(PTConst.LAW_RULES, LayStatusConstant.fourResult());
                    else
                        m.put(PTConst.LAW_RULES, "-");
                }
            }
        }
        return list;
    }

    //获取前五“购买”热门商标
    public List<Map> converFiveListMap(List<Map> list) {
        List<Map> result = new ArrayList<>();
        if (list != null && list.size() > 0)
            for (Map m : list)
                if (m.get(PTConst.BRAND_STATE) != null)
                    if (LayStatusConstant.two().contains(m.get(PTConst.BRAND_STATE).toString()))
                        result.add(m);

        if (result.size() > 5) { //根据排序获取5个
            List<Map> result2 = new ArrayList<>();
            for (int i = result.size() - 1; i >= 0; i--) {
                result2.add(result.get(i));
                if (result2.size() == 5) break;
            }
            return result2;
        } else return result;
    }

    public String converApplyType(String type) {
        String result;
        switch (type) {
            case "01":
                result = "01类 化学原料";
                break;
            case "1":
                result = "01类 化学原料";
                break;
            case "02":
                result = "02类 颜料油漆";
                break;
            case "2":
                result = "02类 颜料油漆";
                break;
            case "03":
                result = "03类 日化用品";
                break;
            case "3":
                result = "03类 日化用品";
                break;
            case "04":
                result = "04类 燃料油脂";
                break;
            case "4":
                result = "04类 燃料油脂";
                break;
            case "05":
                result = "05类 医药";
                break;
            case "5":
                result = "05类 医药";
                break;
            case "06":
                result = "06类 金属材料";
                break;
            case "6":
                result = "06类 金属材料";
                break;
            case "07":
                result = "07类 机械设备";
                break;
            case "7":
                result = "07类 机械设备";
                break;
            case "08":
                result = "08类 手工器械";
                break;
            case "8":
                result = "08类 手工器械";
                break;
            case "09":
                result = "09类 科学仪器";
                break;
            case "9":
                result = "09类 科学仪器";
                break;
            case "10":
                result = "10类 医疗器械";
                break;
            case "11":
                result = "11类 灯具空调";
                break;
            case "12":
                result = "12类 运输工具";
                break;
            case "13":
                result = "13类 军火烟火";
                break;
            case "14":
                result = "14类 珠宝钟表";
                break;
            case "15":
                result = "15类 乐器";
                break;
            case "16":
                result = "16类 办公用品";
                break;
            case "17":
                result = "17类 橡胶制品";
                break;
            case "18":
                result = "18类 皮革皮具";
                break;
            case "19":
                result = "19类 建筑材料";
                break;
            case "20":
                result = "20类 家具";
                break;
            case "21":
                result = "21类 厨房洁具";
                break;
            case "22":
                result = "22类 绳网袋蓬";
                break;
            case "23":
                result = "23类 纱线丝";
                break;
            case "24":
                result = "24类 布料床单";
                break;
            case "25":
                result = "25类 服装鞋帽";
                break;
            case "26":
                result = "26类 钮扣拉链";
                break;
            case "27":
                result = "27类 地毯席垫";
                break;
            case "28":
                result = "28类 健身器材";
                break;
            case "29":
                result = "29类 食品";
                break;
            case "30":
                result = "30类 方便食品";
                break;
            case "31":
                result = "31类 饲料种籽";
                break;
            case "32":
                result = "32类 啤酒饮料";
                break;
            case "33":
                result = "33类 酒";
                break;
            case "34":
                result = "34类 烟草烟具";
                break;
            case "35":
                result = "35类 广告销售";
                break;
            case "36":
                result = "36类 金融物管";
                break;
            case "37":
                result = "37类 建筑修理";
                break;
            case "38":
                result = "38类 通讯服务";
                break;
            case "39":
                result = "39类 运输贮藏";
                break;
            case "40":
                result = "40类 材料加工";
                break;
            case "41":
                result = "41类 教育娱乐";
                break;
            case "42":
                result = "42类 科技服务";
                break;
            case "43":
                result = "43类 餐饮住宿";
                break;
            case "44":
                result = "44类 医疗园艺";
                break;
            case "45":
                result = "45类 社会服务";
                break;
            default:
                result = "";
                break;
        }
        return result;
    }

    public String dealAggStatus(Map<String, Long> dataMap) {
        return JSON.toJSONString(dataMap).replace("已注册", "register").replace("无效", "invalid").replace("申请中", "applying");
    }

    /**
     * 获取文件地址前缀
     *
     * @return
     */
    public String getFileUrlPrefix() {
//        return fileUploadService.getHost();
        return ClientAipOcr.getFileUrlPrefix();
    }
}
