package net.dgg.dqc.backservice.parsedata;

import net.dgg.dqc.backservice.constant.PTConst;
import net.dgg.dqc.backservice.entity.parse.*;
import net.dgg.dqc.backservice.utils.BaseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 商标数据
 * Created by jiangsh on 2018/8/22 09:19
 */
public class BrandData extends BaseUtil {

    private static final Logger logger = LoggerFactory.getLogger(BrandData.class);

    /**
     * 转换 易企查 商标 数据
     * @param o
     * @return
     */
    public static QccCompany converEqcBrand(Object o) {
        QccCompany c = new QccCompany();
        if (o != null) {
            try {
                JSONObject jo = JSONObject.fromObject(o.toString());
                c.setCompanyId(oot(jo.get("url"), PTConst.EQC_WEBSITE_URL, PTConst.TARGET_PREFIX, PTConst.HTML, PTConst.TARGET_SUFFIX));
                c.setName(oot(jo.get("company")));

                //商标信息
                if (jo.has("shangbiao_xinxi")) {
                    JSONArray shangbiao_xinxi = jo.getJSONArray("shangbiao_xinxi");
                    if (!shangbiao_xinxi.isEmpty())
                        EqcDetailData.shangbiao_xinxiDeal(c, shangbiao_xinxi);
                }
            } catch (Exception e) {
                saveError(c, e, "converEqcBrand");
                logger.error("出现异常情况", e.getMessage());
            }
        }
        return c;
    }


    /**
     * 转换 企查查 商标 数据
     * @param o
     * @return
     */
    public static QccCompany converQccBrand(Object o) {
        QccCompany c = new QccCompany();
        if (o != null) {
            try {
                JSONObject jo = JSONObject.fromObject(o.toString());
                c.setCompanyId(oot(jo.get("url"), PTConst.QCC_WEBSITE_URL, PTConst.TARGET_PREFIX, ".html", PTConst.TARGET_SUFFIX));
                c.setName(oot(jo.get("title")));

                ////知识产权
                JSONObject zscq = jo.getJSONObject("zhishi_chanquan");
                if (!zscq.isNullObject()) {
                    //商标信息
                    if (zscq.has("shangbiao_xinxi")) {
                        JSONArray sbxx = zscq.getJSONArray("shangbiao_xinxi");
                        if (!sbxx.isEmpty())
                            QccDetailData.putSbxx(sbxx, c);
                    }
                }
            } catch (Exception e) {
                saveError(c, e, "converQccBrand");
                logger.error("出现异常情况", e.getMessage());
            }
        }
        return c;
    }


    /**
     * 转换 公信中国 商标 数据
     * @param o
     * @return
     */
    public static QccCompany converGxzgBrand(Object o) {
        QccCompany c = new QccCompany();
        if (o != null) {
            try {
                JSONObject jo = JSONObject.fromObject(o.toString());
                c.setName(oot(jo.get("company")));
                c.setCompanyId(oot(jo.get("url"), PTConst.GXZG_WEBSITE_URL, PTConst.TARGET_PREFIX, PTConst.QCC_WEBSITE_URL_2, PTConst.TARGET_PREFIX,
                        ".html", PTConst.TARGET_SUFFIX));

                //商标信息
                if (jo.has("tradeMarkList")) {
                    JSONArray tradeMarkList = jo.getJSONArray("tradeMarkList");
                    if (!tradeMarkList.isEmpty())
                        GxzgDetaliData.tradeMarkListDeal(c, tradeMarkList);
                }
            } catch (Exception e) {
                saveError(c, e, "converGxzgBrand");
                logger.error("出现异常情况", e.getMessage());
            }
        }
        return c;
    }


    /**
     * 转换 标准程序 商标 数据
     * @param o
     * @return
     */
    public static QccCompany converStandBrand(Object o) {
        QccCompany c = new QccCompany();
        if (o != null) {
            try {
                JSONObject jo = JSONObject.fromObject(o.toString());
                c.setCompanyId(oot(jo.get("companyUrl"), PTConst.QCC_WEBSITE_URL, PTConst.TARGET_PREFIX, ".html", PTConst.TARGET_SUFFIX));
                c.setName(oot(jo.get("companyName")));

                //知识产权
                JSONObject knowledgeProperty = jo.getJSONObject("docs").getJSONObject("knowledgeProperty");
                if (!knowledgeProperty.isNullObject()) {
                    //商标信息
                    if (knowledgeProperty.has("trademarkInfo")) {
                        JSONArray trademarkInfo = knowledgeProperty.getJSONArray("trademarkInfo");
                        if (!trademarkInfo.isEmpty())
                            StandDetailData.putSanSbxx(trademarkInfo, c);
                    }
                }
            } catch (Exception e) {
                saveError(c, e, "converStandBrand");
                logger.error("出现异常情况", e.getMessage());
            }
        }
        return c;
    }


}
