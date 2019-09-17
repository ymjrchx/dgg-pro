package net.dgg.dqc.backservice.parsedata;

import net.dgg.dqc.backservice.entity.parse.Cpws;
import net.dgg.dqc.backservice.utils.BaseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: JudgementDocData
 * @Description: 裁判文书json解析
 * @Author: jiangsh
 * @Date: 2018/9/11 9:11
 */
public class JudgementDocData extends BaseUtil{
    private static final Logger logger = LoggerFactory.getLogger(JudgementDocData.class);

    /**
     * 转换 裁判文书数据
     * @param o
     * @return
     */
    public static Cpws conver(Object o) {
        Cpws c = new Cpws();
        if (o != null) {
            JSONObject jo = JSONObject.fromObject(o.toString());
            c.setId(oot(jo.get("_id")));
            c.setCaseName(oot(jo.get("title")));
            c.setDocId(oot(jo.get("docId")));
            c.setLoadTime(oot(jo.get("loadTime")));
            if (jo.has("info")) {
                JSONObject info = jo.getJSONObject("info");
                if (!info.isNullObject()) {
                    c.setCourt(oot(info.get("court")));
                    c.setCaseNo(oot(info.get("caseNumber")));
                    c.setCaseType(oot(info.get("type")));
                    c.setCaseReason(oot(info.get("reason")));
                    if (justDatelen(info.get("judgementDate")))
                    c.setJudgeDate(oot(info.get("judgementDate")));

                    JSONArray judges = info.getJSONArray("judges");
                    if (!judges.isEmpty()) {
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < judges.size(); i++)
                            sb.append(oot(judges.get(i))).append("、");
                        if (sb.toString().length() > 0)
                            c.setJudges(sb.toString().substring(0, sb.toString().length()-1));
                    }
                    c.setTrialRound(oot(info.get("trialRound")));

                    JSONArray proponents = info.getJSONArray("proponents");
                    if (!proponents.isEmpty()) {
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < proponents.size(); i++)
                            sb.append(oot(proponents.get(i))).append("、");
                        if (sb.toString().length() > 0)
                            c.setProsecutorlist(sb.toString().substring(0, sb.toString().length()-1));
                    }

                    JSONArray opponents = info.getJSONArray("opponents");
                    if (!opponents.isEmpty()) {
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < opponents.size(); i++)
                            sb.append(oot(opponents.get(i))).append("、");
                        if (sb.toString().length() > 0)
                            c.setDefendantlist(sb.toString().substring(0, sb.toString().length()-1));
                    }

                    c.setAjsf(oot(info.get("judgementType")));
                }
            }

            if (jo.has("content")) {
                JSONArray content = jo.getJSONArray("content");
                if (!content.isEmpty())
                    c.setContent(content.toString());
            }
        }
        return c;
    }


    /**
     * 转换 裁判文书数据
     * @param o
     * @return
     */
    public static Cpws conver2(Object o) {
        Cpws c = new Cpws();
        if (o != null) {
            JSONObject jo = JSONObject.fromObject(o.toString());
            c.setId(oot(jo.get("_id")));
            c.setCaseName(oot(jo.get("title")));
            c.setDocId(oot(jo.get("docId")));
            c.setLoadTime(oot(jo.get("loadTime")));

            //基本信息
            if (jo.has("info")) {
                JSONObject info = jo.getJSONObject("info");
                if (!info.isNullObject()) {
                    c.setCourt(oot(info.get("court")));
                    c.setCaseNo(oot(info.get("caseNumber")));
                    c.setCaseType(converType(oot(info.get("type"))));
                    c.setCaseReason(oot(info.get("reason")));
                    if (justDatelen(info.get("judgementDate")))
                        c.setJudgeDate(oot(info.get("judgementDate")));

                    c.setTrialRound(oot(info.get("trialRound")));

                    List<String> appellor = new ArrayList<>();
                    appellor.add(info.get("appellor").toString());
                    c.setAppellor(appellor);

                    c.setAjsf(oot(info.get("judgementType")));
                }
            }

            //法律依据
            putLegalBase(jo, c);

            //内容
            putConent(jo, c);
        }
        return c;
    }

    private static String converType(String type) {
        String t;
        switch (type) {
            case "1": t = "刑事案件"; break;
            case "2": t = "民事案件"; break;
            case "3": t = "行政案件"; break;
            case "4": t = "赔偿案件"; break;
            case "5": t = "执行案件"; break;
            default:  t = "-";        break;
        }
        return t;
    }

    private static void putLegalBase(JSONObject jo, Cpws c) {
        if (jo.has("legalBase")) {
            JSONArray legalBase = jo.getJSONArray("legalBase");
            if (!legalBase.isEmpty()) {
                List<Cpws.LegalBase> legalBaseList = new ArrayList<>();
                for (int i = 0; i < legalBase.size(); i++) {
                    Cpws.LegalBase lb = new Cpws.LegalBase();
                    lb.setFgmc(oot(legalBase.getJSONObject(i).get("法规名称")));
                    JSONArray items = legalBase.getJSONObject(i).getJSONArray("Items");
                    if (!items.isEmpty()) {
                        List<Cpws.FgItems> listFi = new ArrayList<>();
                        for (int j = 0; j < items.size(); j++) {
                            Cpws.FgItems fgItems = new Cpws.FgItems();
                            fgItems.setFtmc(oot(items.getJSONObject(j).get("法条名称")));
                            fgItems.setFtnr(oot(items.getJSONObject(j).get("法条内容")));
                            listFi.add(fgItems);
                        }
                        lb.setFgItems(listFi);
                    }
                    legalBaseList.add(lb);
                }
                c.setLegalBase(legalBaseList);
            }
        }
    }

    private static void putConent(JSONObject jo, Cpws c) {
        //内容
        if (jo.has("content")) {
            JSONObject content = jo.getJSONObject("content");
            if (!content.isEmpty()) {
                if (content.has("WBSB")) {
                    JSONArray wbsb = content.getJSONArray("WBSB");
                    if (!wbsb.isEmpty()) {
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < wbsb.size(); i++)
                            sb.append(wbsb.get(i).toString()).append("、");
                        c.setWbsb(sb.toString());
                    }
                }

                if (content.has("DSRXX")) {
                    JSONArray dsrxx = content.getJSONArray("DSRXX");
                    if (!dsrxx.isEmpty()) {
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < dsrxx.size(); i++)
                            sb.append(dsrxx.get(i).toString()).append("、");
                        c.setDsrxx(sb.toString());
                    }
                }

                if (content.has("SSJL")) {
                    JSONArray ssjl = content.getJSONArray("SSJL");
                    if (!ssjl.isEmpty()) {
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < ssjl.size(); i++)
                            sb.append(ssjl.get(i).toString()).append("、");
                        c.setSsjl(sb.toString());
                    }
                }

                if (content.has("CPYZ")) {
                    JSONArray cpyz = content.getJSONArray("CPYZ");
                    if (!cpyz.isEmpty()) {
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < cpyz.size(); i++)
                            sb.append(cpyz.get(i).toString()).append("、");
                        c.setCpyz(sb.toString());
                    }
                }

                if (content.has("PJJG")) {
                    JSONArray pjjg = content.getJSONArray("PJJG");
                    if (!pjjg.isEmpty()) {
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < pjjg.size(); i++)
                            sb.append(pjjg.get(i).toString()).append("、");
                        c.setPjjg(sb.toString());
                    }
                }

                if (content.has("WBWB")) {
                    JSONArray wbwb = content.getJSONArray("WBWB");
                    if (!wbwb.isEmpty()) {
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < wbwb.size(); i++)
                            sb.append(wbwb.get(i).toString()).append("、");
                        c.setWbwb(sb.toString());
                    }
                }
            }

        }
    }

}
