package net.dgg.bigdata.sjjs.web.controller.search;

import net.dgg.bigdata.common.constant.BConst;
import net.dgg.bigdata.sjjs.web.entity.dto.search.senior.ResJudge;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @ClassName: ConverRequest
 * @Description: 请求转换
 * @Author: jiangsh
 * @Date: 2018/12/13 13:59
 */
@Component
public class ConverRequest {

    public ResJudge judgeConver(BoolQueryBuilder qb, String v) {
        ResJudge r = new ResJudge();
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        if (StringUtils.isNotEmpty(v)) {
            if (v.equals("must")) {
                qb.must(bqb);
                r.setFlag(true);
            } else {
                qb.should(bqb);
                r.setFlag(false);
            }
        }
        r.setBoolQueryBuilder(bqb);
        return r;
    }

    /**
     * 名称conver
     * @param v 原始值
     * @return
     */
    public String nameConver(String v) {
        String result = null;
        if (StringUtils.isNotEmpty(v)) {
            switch (v) {
                //招聘
                case "recruitingName": result = "recruitment.hirings.brJobTitle"; break;
                case "recruitingDesc": result = "recruitment.hirings.brDescribe"; break;
                case "recruitingSource": result = "recruitment.brzpqd"; break;

                //工商信息
                case "name": result = "commercial.companyName"; break;
                case "desc": result = "commercial.companyBrief"; break;
                case "operStatus": result = "commercial.businessState"; break;
                case "business":      result = "commercial.businessScope"; break;
                case "address":       result = "commercial.registerAddress"; break;
                case "addressValue":       result = "commercial.address"; break;
                case "contactNo":       result = "commercial.companyTel"; break;
                case "enterpriseType": result = "commercial.companyType"; break;
                case "industries": result = "commercial.industry"; break;
                case "foundTime": result = "commercial.registerTime"; break;
                case "regCapitalRmb": result = "commercial.registerMoney"; break;
                case "holderTypeList": result = "commercial.shareholders.hiName"; break;
                default:break;
            }
        }
        return result;
    }

    /**
     * 匹配关系转换
     * @param key 关键字名称，如 name
     * @param k 搜索关键字key，如 in, eq等等
     * @param v 原始值，如 “百度、京东”
     * @return
     */
    public BoolQueryBuilder relationConver(String key, String k, BoolQueryBuilder bqb, String v, String fh, boolean flag) {

        if (StringUtils.isNotEmpty(k)) {
            switch (k) {
                case "in":  //包含任一
                    containEqualOne(key, bqb, v, fh, 1);
                    break;

                case "nin": //不包含所有
                    notContainEqualAll(key, bqb, v, fh, 1);
                    break;

                case "gte": //大于等于
                    gteQb(key, bqb, v, flag);
                    break;

                case "lte": //小于等于
                    lteQb(key, bqb, v, flag);
                    break;

                case "gte_lte": //范围内
                    range(key, bqb, v, fh, flag);
                    break;

                case "eq": //等于任一
                    containEqualOne(key, bqb, v, fh, 3);
                    break;

                case "neq": //不等于所有
                    notContainEqualAll(key, bqb, v, fh, 3);
                    break;

                case "exist": //有
                    bqb.must(QueryBuilders.existsQuery(key));
                    break;

                case "not_exist": //没有
                    bqb.mustNot(QueryBuilders.existsQuery(key));
                    break;

                default:break;
            }
        }
        return bqb;
    }

    //范围段
    protected void range(String key, BoolQueryBuilder bqb, String v, String fh, boolean flag) {
        String[] val = v.split(fh);
        String one = val[0].concat(BConst.T_DATESUFIX);
        String two = val[1].concat(BConst.T_DATESUFIX_END);
        if (key.equals(BConst.REG_MONEY)) {
            one = val[0];
            two = val[1];
        }
        if (flag) bqb.must(QueryBuilders.rangeQuery(key).from(one).to(two));
        else      bqb.should(QueryBuilders.rangeQuery(key).from(one).to(two));
    }

    //大于等于
    protected void gteQb(String key, BoolQueryBuilder bqb, String v, boolean flag) {
        if (flag) bqb.must(QueryBuilders.rangeQuery(key).gte(v));
        else      bqb.should(QueryBuilders.rangeQuery(key).gte(v));
    }

    //小于等于
    protected void lteQb(String key, BoolQueryBuilder bqb, String v, boolean flag) {
        if (flag) bqb.must(QueryBuilders.rangeQuery(key).lte(v));
        else      bqb.should(QueryBuilders.rangeQuery(key).lte(v));
    }

    //包含/等于任一
    protected void containEqualOne(String key, BoolQueryBuilder bqb, String value, String fh, int status) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        if (value.contains(fh)) {
            String[] v = value.split(fh);
            for (int i = 0; i < v.length; i++) {
                if (status == 1)
                    qb.should(matchPhraseQuery(key, v[i]));
                else if (status == 2)
                    qb.should(matchQuery(key, v[i]));
                else
                    qb.should(termQuery(key, v[i]));
            }
        } else
            if (status == 1)
                qb.should(matchPhraseQuery(key, value));
            else if (status == 2)
                qb.should(matchQuery(key, value));
            else
                qb.should(termQuery(key, value));
        bqb.must(qb);
    }

    //不包含/等于所有
    protected void notContainEqualAll(String key, BoolQueryBuilder bqb, String value, String fh, int status) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        if (value.contains(fh)) {
            String[] v = value.split(fh);
            for (int i = 0; i < v.length; i++) {
                if (status == 1)
                    qb.must(matchPhraseQuery(key, v[i]));
                else if (status == 2)
                    qb.must(matchQuery(key, v[i]));
                else
                    qb.must(termQuery(key, v[i]));
            }
        } else
            if (status == 1)
                qb.must(matchPhraseQuery(key, value));
            else if (status == 2)
                qb.must(matchQuery(key, value));
            else
                qb.must(termQuery(key, value));
        bqb.mustNot(qb);
    }

}
