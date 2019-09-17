package net.dgg.yk.platform.backend.business.a6;

import net.dgg.yk.common.Toolkit;
import net.dgg.yk.platform.backend.business.a1.dependencies.util.BaseUtil;
import net.dgg.yk.platform.backend.business.a6.dependencies.SbDetails;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解析商标详情数据
 * Created by jiangsh on 2018/8/22 17:40
 */
public class BrandDetailData extends BaseUtil {

    private static final Logger logger = LoggerFactory.getLogger(BrandDetailData.class);

    public static SbDetails convert(Document o) {
        SbDetails s = new SbDetails();
        if (o != null) {
            try {
                /**JSONObject jo = JSONObject.fromObject(o.toJson());*/
                s.setId(oot(o.getString("_id")));
                s.setEsId(oot(o.getString("_id")));
                s.setName(oot(o.getString("tmName")));
                s.setRegNo(oot(o.getString("regNo")));
                s.setStatus(oot("-"));
                if (justDatelen(o.getString("applicantDate")))
                    s.setAppDate(oot(o.getString("applicantDate")));
                /**
                 if (jo.has("loadTime"))
                 s.setLoadTime(oot(jo.get("loadTime"))); //记录存储时间
                 */

                /**
                 if (jo.has("head")) {
                 JSONObject head = jo.getJSONObject("head");
                 if (!head.isNullObject()) {
                 */
                s.setIntCls(dealApplyType(o.getString("intcls")));
                if (o.get("applicant_detail_results") != null) {
                    Document[] applicant_detail_results = (Document[]) o.get("applicant_detail_results");
                    if (applicant_detail_results.length > 0) {
                        s.setApplicantCn(oot(applicant_detail_results[0].get("applicantName")));
                        s.setApplicantEn(oot(applicant_detail_results[0].get("applicantNameEn")));
                        s.setAddressCn(oot(applicant_detail_results[0].get("applicantAddress")));
                        s.setAddressEn(oot(applicant_detail_results[0].get("applicantAddressEn")));
                    }
                }
                s.setImageUrl(String.format("%s.jpg", o.getString("regNo")));

                /**
                 if (jo.has("serviceItem")) {
                 JSONObject serviceItem = jo.getJSONObject("serviceItem");
                 if (!serviceItem.isNullObject()) {
                 s.setTmGoodsService(oot(serviceItem.get("goods")));
                 s.setServerNum(oot(serviceItem.get("goodsNum")));
                 }
                 }
                 */
                if (o.get("service_detail_results") != null) {
                    Document[] service_detail_results = (Document[]) o.get("service_detail_results");
                    StringBuilder good = new StringBuilder();
                    StringBuilder num = new StringBuilder();
                    for (int i = 0; i < service_detail_results.length; i++) {
                        if (i > 0) {
                            good.append(",");
                            num.append(",");
                        }
                        String serviceName = service_detail_results[i].getString("serviceName");
                        String similarGroup = service_detail_results[i].getString("similarGroup");
                        good.append(similarGroup + "-" + serviceName);
                        num.append(similarGroup);
                    }
                    s.setTmGoodsService(good.toString());
                    s.setServerNum(num.toString());
                }

                /**
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
                 */
                if (Toolkit.StringHelper.notNullAndEmpty(o.getString("announcementNo")))
                    s.setAnnouncementIssue(Integer.valueOf(o.getString("announcementNo")));
                String announcementDate = o.getString("announcementDate");
                if (StringUtils.isNotEmpty(announcementDate)) {
                    s.setAnnouncementDate(announcementDate);
                }
                if (Toolkit.StringHelper.notNullAndEmpty(o.getString("registAnnNo")))
                    s.setRegIssue(Integer.valueOf(o.getString("registAnnNo")));
                if (Toolkit.StringHelper.notNullAndEmpty(o.getString("registAnnDate")))
                    s.setRegDate(o.getString("registAnnDate"));
                String termStartDate = o.getString("termStartDate");
                String termEndDate = o.getString("termEndDate");
                if (StringUtils.isNotEmpty(termStartDate) && StringUtils.isNotEmpty(termEndDate)) {
                    s.setValidPeriod(String.format("%s至%s", termStartDate, termEndDate));
                }
                if (o.get("collective_detail_results") != null) {
                    Document[] collective_detail_results = (Document[]) o.get("collective_detail_results");
                    if (collective_detail_results.length > 0) {
                        s.setApplicant1(collective_detail_results[0].getString("collectiveName"));
                        if (collective_detail_results.length > 1) {
                            s.setApplicant2(collective_detail_results[1].getString("collectiveName"));
                        }
                    }
                }
                if (o.get("international_results_new") != null) {
                    Document[] international_results_new = (Document[]) o.get("international_results_new");
                    if (international_results_new.length > 0) {
                        String internationalRegDate = international_results_new[0].getString("internationalRegDate");
                        if (StringUtils.isNotEmpty(internationalRegDate)) {
                            s.setGuoJiZhuCeDate(internationalRegDate);
                        }
                    }
                }
                if (o.get("priority_detail_results") != null) {
                    Document[] priority_detail_results = (Document[]) o.get("priority_detail_results");
                    if (priority_detail_results.length > 0) {
                        String priorityDate = priority_detail_results[0].getString("priorityDate");
                        if (StringUtils.isNotEmpty(priorityDate)) {
                            s.setYouXianQuanDate(priorityDate);
                        }
                    }
                }
                if (o.get("agency_results_new") != null) {
                    Document[] agency_results_new = (Document[]) o.get("agency_results_new");
                    if (agency_results_new.length > 0) {
                        s.setTmSection(agency_results_new[0].getString("agency"));
                    }
                }
                s.setColor(o.getString("colorInfo"));
                s.setSbType(o.getString("tmStyle"));


                /**
                 if (jo.has("sbProcess")) {
                 JSONArray sbProcess = jo.getJSONArray("sbProcess");
                 if (!sbProcess.isEmpty()) {
                 StringBuffer sb = new StringBuffer();
                 for (int i = 0; i < sbProcess.size(); i++) {
                 sb.append(sbProcess.get(i).toString()).append("、");
                 }
                 s.setTmApplyFlow(sb.toString().substring(0, sb.toString().length() - 1));
                 }
                 }

                 if (jo.has("sbggProcess")) {
                 JSONArray sbggProcess = jo.getJSONArray("sbggProcess");
                 if (!sbggProcess.isEmpty()) {
                 StringBuffer sb = new StringBuffer();
                 for (int i = 0; i < sbggProcess.size(); i++) {
                 sb.append(sbggProcess.get(i).toString()).append("、");
                 }
                 s.setTmGongGaoyFlow(sb.toString().substring(0, sb.toString().length() - 1));
                 }
                 }
                 */

            } catch (Exception e) {
//                saveError(s, e, "BrandDetailData");
                e.printStackTrace();
            }
        }
        return s;
    }

    public static String dealApplyType(String type) {
        String result;
        switch (type) {
            case "01": result = "01类 化学原料"; break;
            case "1": result = "01类 化学原料"; break;
            case "02": result = "02类 颜料油漆"; break;
            case "2": result = "02类 颜料油漆"; break;
            case "03": result = "03类 日化用品"; break;
            case "3": result = "03类 日化用品"; break;
            case "04": result = "04类 燃料油脂"; break;
            case "4": result = "04类 燃料油脂"; break;
            case "05": result = "05类 医药"; break;
            case "5": result = "05类 医药"; break;
            case "06": result = "06类 金属材料"; break;
            case "6": result = "06类 金属材料"; break;
            case "07": result = "07类 机械设备"; break;
            case "7": result = "07类 机械设备"; break;
            case "08": result = "08类 手工器械"; break;
            case "8": result = "08类 手工器械"; break;
            case "09": result = "09类 科学仪器"; break;
            case "9": result = "09类 科学仪器"; break;
            case "10": result = "10类 医疗器械"; break;
            case "11": result = "11类 灯具空调"; break;
            case "12": result = "12类 运输工具"; break;
            case "13": result = "13类 军火烟火"; break;
            case "14": result = "14类 珠宝钟表"; break;
            case "15": result = "15类 乐器"; break;
            case "16": result = "16类 办公用品"; break;
            case "17": result = "17类 橡胶制品"; break;
            case "18": result = "18类 皮革皮具"; break;
            case "19": result = "19类 建筑材料"; break;
            case "20": result = "20类 家具"; break;
            case "21": result = "21类 厨房洁具"; break;
            case "22": result = "22类 绳网袋蓬"; break;
            case "23": result = "23类 纱线丝"; break;
            case "24": result = "24类 布料床单"; break;
            case "25": result = "25类 服装鞋帽"; break;
            case "26": result = "26类 钮扣拉链"; break;
            case "27": result = "27类 地毯席垫"; break;
            case "28": result = "28类 健身器材"; break;
            case "29": result = "29类 食品"; break;
            case "30": result = "30类 方便食品"; break;
            case "31": result = "31类 饲料种籽"; break;
            case "32": result = "32类 啤酒饮料"; break;
            case "33": result = "33类 酒"; break;
            case "34": result = "34类 烟草烟具"; break;
            case "35": result = "35类 广告销售"; break;
            case "36": result = "36类 金融物管"; break;
            case "37": result = "37类 建筑修理"; break;
            case "38": result = "38类 通讯服务"; break;
            case "39": result = "39类 运输贮藏"; break;
            case "40": result = "40类 材料加工"; break;
            case "41": result = "41类 教育娱乐"; break;
            case "42": result = "42类 科技服务"; break;
            case "43": result = "43类 餐饮住宿"; break;
            case "44": result = "44类 医疗园艺"; break;
            case "45": result = "45类 社会服务"; break;
            default: result = ""; break;
        }
        return result;
    }
}
