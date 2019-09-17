package net.dgg.dqc.backservice.parsedata;

import net.dgg.dqc.backservice.constant.PTConst;
import net.dgg.dqc.backservice.entity.parse.QccCompany;
import net.dgg.dqc.backservice.entity.parse.Zlxq;
import net.dgg.dqc.backservice.utils.BaseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 专利数据
 * Created by jiangsh on 2018/8/22 14:15
 */
public class PatentData extends BaseUtil{
    private static final Logger logger = LoggerFactory.getLogger(PatentData.class);

    /**
     * 转换 易企查 专利 数据
     * @param o
     * @return
     */
    public static QccCompany converEqcPatent(Object o) {
        QccCompany c = new QccCompany();
        if (o != null) {
            try {
                JSONObject jo = JSONObject.fromObject(o.toString());
                c.setCompanyId(oot(jo.get("url"), PTConst.EQC_WEBSITE_URL, PTConst.TARGET_PREFIX, PTConst.HTML, PTConst.TARGET_SUFFIX));
                c.setName(oot(jo.get("company")));

                //专利信息
                if (jo.has("zhuanli_xinxi")) {
                    JSONArray zhuanli_xinxi = jo.getJSONArray("zhuanli_xinxi");
                    if (!zhuanli_xinxi.isEmpty())
                        EqcDetailData.zhuanli_xinxiDeal(c, zhuanli_xinxi);
                }
            } catch (Exception e) {
                saveError(c, e, "converEqcPatent");
                logger.error("出现异常情况", e.getMessage());
            }
        }
        return c;
    }


    /**
     * 转换 企查查 专利 数据
     * @param o
     * @return
     */
    public static QccCompany converQccPatent(Object o) {
        QccCompany c = new QccCompany();
        if (o != null) {
            try {
                JSONObject jo = JSONObject.fromObject(o.toString());
                c.setCompanyId(oot(jo.get("url"), PTConst.QCC_WEBSITE_URL, PTConst.TARGET_PREFIX, ".html", PTConst.TARGET_SUFFIX));
                c.setName(oot(jo.get("title")));

                ////知识产权
                JSONObject zscq = jo.getJSONObject("zhishi_chanquan");
                if (!zscq.isNullObject()) {
                    //专利信息
                    JSONArray zlxx = zscq.getJSONArray("zhuanli_xinxi");
                    if (!zlxx.isEmpty())
                        QccDetailData.putZlxx(zlxx, c);
                }
            } catch (Exception e) {
                saveError(c, e, "converQccPatent");
                logger.error("出现异常情况", e.getMessage());
            }
        }
        return c;
    }


    /**
     * 转换 标准json 专利 数据
     * @param o
     * @return
     */
    public static Zlxq converStandPatent(Object o) {
        Zlxq c = new Zlxq();
        if (o != null) {
            try {
                JSONObject jo = JSONObject.fromObject(o.toString());
                c.setPiInventName(oot(jo.get("inventName")));
                JSONArray patType = jo.getJSONArray("patType");
                if (!patType.isEmpty()) {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < patType.size(); i++)
                        sb.append(patType.get(i)).append("、");
                    c.setPatType(sb.toString());
                }

                //基本信息
                JSONObject b = jo.getJSONObject("baseInfo");
                if (!b.isNullObject()) {
                    c.setApplicationNumber(oot(b.get("applyCode")));
                    if (justDatelen(b.get("applyDate")))
                        c.setApplicationDate(oot(b.get("applyDate")));
                    c.setPublicationNumber(oot(b.get("publicCode")));
                    c.setPublicationDate(oot(b.get("publicDate")));
                    c.setAssigneestring(oot(b.get("applicant")));
                    c.setInventorString(oot(b.get("inventor")));
                    c.setPiAddress(oot(b.get("address")));
                    c.setAreaCode(oot(b.get("areaCode")));
                    c.setPatentee(oot(b.get("patentee")));
                    c.setZjnClassify(oot(b.get("zjnClassify")));
                    c.setIPCCode(oot(b.get("IPCCode")));
                    c.setCPCCode(oot(b.get("CPCCode")));
                    c.setPriority(oot(b.get("priority")));
                    c.setAgency(oot(b.get("patAgency")));
                    c.setAgent(oot(b.get("patAgent")));
                    c.setAssiantExaminer(oot(b.get("examiner")));
                    c.setGjsq(oot(b.get("gjsq")));
                    c.setGjgk(oot(b.get("gjgk")));
                    c.setJrgjrq(oot(b.get("jrgjrq")));
                    c.setFasq(oot(b.get("fasq")));
                }

                //关键字
                JSONArray keyWords = jo.getJSONArray("keyWords");
                if (!keyWords.isEmpty()) {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < keyWords.size(); i++)
                        sb.append(oot(keyWords.get(i))).append("、");
                    c.setKeyWord(sb.toString());
                }

                //法律信息
                JSONArray lawInfo = jo.getJSONArray("lawInfo");
                if (!lawInfo.isEmpty()) {
                    List<Zlxq.LawInfo> lawInfos = new ArrayList<>();
                    for (int i = 0; i < lawInfo.size(); i++) {
                        Zlxq.LawInfo li = new Zlxq.LawInfo();
                        JSONObject obj = lawInfo.getJSONObject(i);
                        li.setLawDate(oot(obj.get("lawDate")));
                        li.setLawStatus(oot(obj.get("lawStatus")));
                        li.setLawStatusInfo(oot(obj.get("lawStatusInfo")));
                        lawInfos.add(li);
                    }
                    c.setLawInfos(lawInfos);
                }

                c.setAbstracts(oot(jo.get("absInfo")));
//                c.setClaInfo(oot(jo.get("claInfo")));
//                c.setDesInfo(oot(jo.get("desInfo")));

            } catch (Exception e) {
                logger.error("出现异常情况", e.getMessage());
            }
        }
        return c;
    }
}
