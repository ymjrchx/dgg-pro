package net.dgg.dqc.backservice.parsedata;

import net.dgg.dqc.backservice.entity.parse.SbDetails;
import net.dgg.dqc.backservice.utils.BaseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解析商标详情数据
 * Created by jiangsh on 2018/8/22 17:40
 */
public class BrandDetailData extends BaseUtil{

    private static final Logger logger = LoggerFactory.getLogger(BrandDetailData.class);

    public static SbDetails conver(Object o) {
        SbDetails s = new SbDetails();
        if (o != null) {
            try {
                JSONObject jo = JSONObject.fromObject(o.toString());
                s.setEsId(oot(jo.get("_id")));
                s.setName(oot(jo.get("sbName")));
                s.setRegNo(oot(jo.get("sbNum")));
                s.setStatus(oot(jo.get("state")));
                if (justDatelen(jo.get("applicationDate")))
                s.setAppDate(oot(jo.get("applicationDate")));
                if (jo.has("loadTime"))
                s.setLoadTime(oot(jo.get("loadTime"))); //记录存储时间

                if (jo.has("head")) {
                    JSONObject head = jo.getJSONObject("head");
                    if (!head.isNullObject()) {
                        s.setIntCls(oot(head.get("sbType")));
                        s.setApplicantCn(oot(head.get("userName")));
                        s.setApplicantEn(oot(head.get("userNameEn")));
                        s.setAddressCn(oot(head.get("userAddress")));
                        s.setAddressEn(oot(head.get("userAddressEn")));
                        s.setImageUrl(oot(head.get("imgUrl")));
                        s.setTmiPath(oot(head.get("imgLocal")));
                    }
                }

                if (jo.has("serviceItem")) {
                    JSONObject serviceItem = jo.getJSONObject("serviceItem");
                    if (!serviceItem.isNullObject()) {
                        s.setTmGoodsService(oot(serviceItem.get("goods")));
                        s.setServerNum(oot(serviceItem.get("goodsNum")));
                    }
                }

                if (jo.has("sbRegisterData")) {
                    JSONObject sbRegisterData = jo.getJSONObject("sbRegisterData");
                    if (!sbRegisterData.isNullObject()) {
                        s.setAnnouncementIssue(Integer.valueOf(oon(sbRegisterData.get("firstSbggNum"))));
                        if (justDatelen(sbRegisterData.get("firstSbggTime")))
                        s.setAnnouncementDate(oot(sbRegisterData.get("firstSbggTime")));
                        s.setRegIssue(Integer.valueOf(oon(sbRegisterData.get("registerSbggNum"))));
                        if (justDatelen(sbRegisterData.get("registerSbggTime")))
                        s.setRegDate(oot(sbRegisterData.get("registerSbggTime")));
                        s.setValidPeriod(oot(sbRegisterData.get("manageTime")));
                        s.setApplicant1(oot(sbRegisterData.get("otherUser")));
                        if (justDatelen(sbRegisterData.get("lateSpecifiedDate")))
                            s.setHouQiZhiDingDate(oot(dates(sbRegisterData.get("lateSpecifiedDate"))));
                        if (justDatelen(sbRegisterData.get("overseasDate")))
                            s.setGuoJiZhuCeDate(oot(dates(sbRegisterData.get("overseasDate"))));
                        if (justDatelen(sbRegisterData.get("priority")))
                            s.setYouXianQuanDate(oot(dates(sbRegisterData.get("priority"))));
                        s.setTmSection(oot(sbRegisterData.get("agency")));
                        s.setColor(oot(sbRegisterData.get("color")));
                        s.setSbType(oot(sbRegisterData.get("sbType")));
                    }
                }

                if (jo.has("sbProcess")) {
                    JSONArray sbProcess = jo.getJSONArray("sbProcess");
                    if (!sbProcess.isEmpty()) {
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < sbProcess.size(); i++) {
                            sb.append(sbProcess.get(i).toString()).append("、");
                        }
                        s.setTmApplyFlow(sb.toString().substring(0, sb.toString().length()-1));
                    }
                }

                if (jo.has("sbggProcess")) {
                    JSONArray sbggProcess = jo.getJSONArray("sbggProcess");
                    if (!sbggProcess.isEmpty()) {
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < sbggProcess.size(); i++) {
                            sb.append(sbggProcess.get(i).toString()).append("、");
                        }
                        s.setTmGongGaoyFlow(sb.toString().substring(0, sb.toString().length()-1));
                    }
                }

            } catch (Exception e) {
//                saveError(s, e, "BrandDetailData");
                e.printStackTrace();
            }
        }
        return s;
    }
}
