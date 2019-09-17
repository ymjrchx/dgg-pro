package net.dgg.yk.platform.backend.business.a1;

import net.dgg.yk.platform.backend.business.a1.dependencies.PTConst;
import net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse.*;
import net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse.qyfj.ContactInfo;
import net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse.qygs.QygsDetail;
import net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse.qynb.QynbDetail;
import net.dgg.yk.platform.backend.business.a1.dependencies.util.BaseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.bson.BSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static net.dgg.yk.platform.backend.business.a1.dependencies.util.QccDetailData.dealPhoneNo;

/**
 * 标准程序解析
 * Created by jiangsh on 2018/8/14 13:59
 */
public class StandDetailData extends BaseUtil {

    private static final Logger logger = LoggerFactory.getLogger(StandDetailData.class);

    public static QccCompany conver(BSONObject o) {
        QccCompany c = new QccCompany();
        try {
            if (o != null) {
                StringBuffer sb = new StringBuffer();
                JSONObject jo = JSONObject.fromObject(o.toString());

                //c.setCompanyId(oot(jo.get("companyUrl"), PTConst.STAND_WEBSITE_URL, PTConst.TARGET_PREFIX, PTConst.QCC_WEBSITE_URL, PTConst.TARGET_PREFIX, ".html", PTConst.TARGET_SUFFIX));
                c.setCompanyId((String) o.get("_id"));
                c.setName(oot(jo.get("companyName")));

                List<ContactInfo> contactInfoList = new ArrayList<>();
                ContactInfo contactInfo = new ContactInfo(); //联系人信息

                QygsDetail qd = new QygsDetail(); //企业工商信息

                //公司背景（基本信息）
                JSONObject background = jo.getJSONObject("docs").getJSONObject("background");
                if (!background.isNullObject())
                    background(background, qd, c, sb);

                //司法风险（法律诉讼）
//                JSONObject lawDangerous = jo.getJSONObject("docs").getJSONObject("lawDangerous");
//                if (!lawDangerous.isNullObject())
//                    lawDangerous(lawDangerous, c);
//
//                //经营风险
//                JSONObject manageDangerous = jo.getJSONObject("docs").getJSONObject("manageDangerous");
//                if (!manageDangerous.isNullObject())
//                    manageDangerous(manageDangerous, c);
//
//                //经营状况
//                JSONObject businessStatus = jo.getJSONObject("docs").getJSONObject("businessStatus");
//                if (!businessStatus.isNullObject())
//                    businessStatus(businessStatus, c);
//
//                //公司发展
//                JSONObject develope = jo.getJSONObject("docs").getJSONObject("develope");
//                if (!develope.isNullObject())
//                    develope(develope, c);
//
//                //知识产权
//                JSONObject knowledgeProperty = jo.getJSONObject("docs").getJSONObject("knowledgeProperty");
//                if (!knowledgeProperty.isNullObject())
//                    knowledgeProperty(knowledgeProperty, c);
//
//                //历史信息
//                JSONObject past = jo.getJSONObject("docs").getJSONObject("past");
//                if (!past.isNullObject())
//                    past(past, c);

                dealPhoneNo(sb.toString(), contactInfo, c);
                contactInfoList.add(contactInfo);
                qd.setContactInfoList(contactInfoList);
                c.setQygsDetail(qd);
            }

        } catch (Exception e) {
            saveError(c, e, "StandDetailData");
            e.printStackTrace();
            logger.error("出现异常情况", e.getMessage());
//            e.printStackTrace();
        }
        return c;
    }

    private static void past(JSONObject past, QccCompany c) {
        Past p = new Past();
        p.setPastAnnouncementCount(oot(past.get("pastAnnouncementCount")));
        p.setPastICInfo(oot(past.get("pastICInfo")));
        p.setPastHolder(oot(past.get("pastHolder")));
        p.setPastInverst(oot(past.get("pastInverst")));
        p.setPastLawsuit(oot(past.get("pastLawsuit")));
        p.setPastCourtCount(oot(past.get("pastCourtCount")));
        p.setPastDshonest(oot(past.get("pastDshonest")));
        p.setPastzhixing(oot(past.get("pastzhixing")));
        p.setPastPunishment(oot(past.get("pastPunishment")));
        p.setPastEquity(oot(past.get("pastEquity")));
        p.setPastChattelMortgage(oot(past.get("pastChattelMortgage")));
        p.setPastlicense(oot(past.get("pastlicense")));
        c.setPast(p);
    }

    private static void knowledgeProperty(JSONObject knowledgeProperty, QccCompany c) {
        //商标信息
        JSONArray trademarkInfo = knowledgeProperty.getJSONArray("trademarkInfo");
        if (!trademarkInfo.isEmpty()) putSanSbxx(trademarkInfo, c);

        //软件著作权
        JSONArray SoftwareWorks = knowledgeProperty.getJSONArray("SoftwareWorks");
        if (!SoftwareWorks.isEmpty()) putSanRjzzq(SoftwareWorks, c);

        //专利
        JSONArray patentInfo = knowledgeProperty.getJSONArray("patentInfo");
        if (!patentInfo.isEmpty()) putSanZl(patentInfo, c);

        //作品著作权
        JSONArray productionWorks = knowledgeProperty.getJSONArray("productionWorks");
        if (!productionWorks.isEmpty()) putSanZpzzq(productionWorks, c);

        //网站备案
        JSONArray webCount = knowledgeProperty.getJSONArray("webCount");
        if (!webCount.isEmpty()) putSanWzba(webCount, c);

    }

    private static void putSanWzba(JSONArray ja, QccCompany c) {
        List<Gswz> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Gswz aa = new Gswz();
            if (justDatelen(ja.getJSONObject(i).get("wCheckTime")))
                aa.setsDate(oot(ja.getJSONObject(i).get("wCheckTime")));
            aa.setCompanyName(oot(ja.getJSONObject(i).get("wName")));
            aa.setHomeSite(oot(ja.getJSONObject(i).get("wHomeUrl")));
            aa.setYuMing(oot(ja.getJSONObject(i).get("wDomainName")));
            aa.setBeiAn(oot(ja.getJSONObject(i).get("wRecordNum")));
            aa.setStatus(oot(ja.getJSONObject(i).get("wStatus")));
            aa.setwProperty(oot(ja.getJSONObject(i).get("wProperty")));
            aa.setwPermitNum(oot(ja.getJSONObject(i).get("wPermitNum")));
            aa.setwRegisterTime(oot(ja.getJSONObject(i).get("wRegisterTime")));
            list.add(aa);
        }
        c.setGswz(list);
    }

    private static void putSanZpzzq(JSONArray ja, QccCompany c) {
        List<Zpzzq> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Zpzzq aa = new Zpzzq();
            aa.setPwCategory(oot(ja.getJSONObject(i).get("pwCategory")));
            aa.setPwFinishDate(oot(ja.getJSONObject(i).get("pwFinishDate")));
            aa.setPwName(oot(ja.getJSONObject(i).get("pwName")));
            aa.setPwPublishDate(oot(ja.getJSONObject(i).get("pwPublishDate")));
            aa.setPwRegisterDate(oot(ja.getJSONObject(i).get("pwRegisterDate")));
            aa.setPwregisterNum(oot(ja.getJSONObject(i).get("pwregisterNum")));
            aa.setPwauthor(oot(ja.getJSONObject(i).get("pwauthor")));
            list.add(aa);
        }
        c.setZpzzqs(list);
    }

    private static void putSanZl(JSONArray ja, QccCompany c) {
        List<Zlxq> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Zlxq aa = new Zlxq();
            aa.setPiApplyPublishNum(oot(ja.getJSONObject(i).get("piApplyPublishNum")));
            aa.setApplicationNumber(oot(ja.getJSONObject(i).get("piApplyNum")));
            aa.setPiClassifyNum(oot(ja.getJSONObject(i).get("piClassifyNum")));
            aa.setPiInventName(oot(ja.getJSONObject(i).get("piInventName")));
            aa.setInventorString(oot(ja.getJSONObject(i).get("piInventMan")));
            aa.setAssigneestring(oot(ja.getJSONObject(i).get("piApplyMan")));
            aa.setApplicationDate(oot(ja.getJSONObject(i).get("piApplyDate")));
            aa.setPiApplyAnnounceDate(oot(ja.getJSONObject(i).get("piApplyAnnounceDate")));
            aa.setAgency(oot(ja.getJSONObject(i).get("piSection")));
            aa.setAgent(oot(ja.getJSONObject(i).get("piProxyMan")));
            aa.setPiAddress(oot(ja.getJSONObject(i).get("piAddress")));
            aa.setAbstracts(oot(ja.getJSONObject(i).get("piAbstract")));
            aa.setTitle(oot(ja.getJSONObject(i).get("piTitle")));
            aa.setKindCodeDesc(oot(ja.getJSONObject(i).get("piType")));
            aa.setLegalStatusDesc(oot(ja.getJSONObject(i).get("piLawState")));
            list.add(aa);
        }
        c.setZlxq(list);
    }

    private static void putSanRjzzq(JSONArray ja, QccCompany c) {
        List<Zzqrz> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Zzqrz aa = new Zzqrz();
            aa.setName(oot(ja.getJSONObject(i).get("swAllName")));
            aa.setShortName(oot(ja.getJSONObject(i).get("swName")));
            aa.setRegisterNo(oot(ja.getJSONObject(i).get("swregisterNum")));
            aa.setCategory(oot(ja.getJSONObject(i).get("swClassifyNum")));
            aa.setVersionNo(oot(ja.getJSONObject(i).get("swVersionNum")));
            aa.setOwner(oot(ja.getJSONObject(i).get("swOwner")));
            aa.setPublishDate(oot(ja.getJSONObject(i).get("swPublishDate")));
            aa.setRegisterAperDate(oot(ja.getJSONObject(i).get("swRegisterDate")));
            list.add(aa);
        }
        c.setZzqrz(list);
    }

    protected static void putSanSbxx(JSONArray ja, QccCompany c) {
        List<SbDetails> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            SbDetails aa = new SbDetails();
            aa.setUrl(c.getCompanyId());
            aa.setApplicantCn(c.getName());
            aa.setName(oot(ja.getJSONObject(i).get("tmName")));
            aa.setRegNo(oot(ja.getJSONObject(i).get("tmRegisterNum")));
            aa.setStatus(oot(ja.getJSONObject(i).get("tmStatus")));
            aa.setIntCls(oot(ja.getJSONObject(i).get("tmClassify")));
            aa.setAppDate(oot(ja.getJSONObject(i).get("tmApplyTime")));
            aa.setValidPeriod(oot(ja.getJSONObject(i).get("tmDeadline")));
            aa.setTmSection(oot(ja.getJSONObject(i).get("tmSection")));
            aa.setAnnouncementIssue(ooz(ja.getJSONObject(i).get("tmAnnounceNum"), 1));
            aa.setAnnouncementDate(oot(ja.getJSONObject(i).get("tmAnnounceDate")));
            aa.setRegIssue(ooz(ja.getJSONObject(i).get("tmrAnnounceNum"), 1));
            aa.setRegDate(oot(ja.getJSONObject(i).get("tmrAnnounceDate")));
//            aa.setApplicantCn(oot(ja.getJSONObject(i).get("tmApplyMan")));
            aa.setTmApplyAddres(oot(ja.getJSONObject(i).get("tmApplyAddres")));
            aa.setTmGoodsService(oot(ja.getJSONObject(i).get("tmGoodsService")));
            aa.setTmApplyFlow(oot(ja.getJSONObject(i).get("tmApplyFlow")));
            JSONObject tmImage = ja.getJSONObject(i).getJSONObject("tmImage");
            if (!tmImage.isNullObject()) {
                aa.setImageUrl(oot(tmImage.get("tmiUrl")));
                aa.setTmiName(oot(tmImage.get("tmiName")));
                aa.setTmiPath(oot(tmImage.get("tmiPath")));
            }
            list.add(aa);
        }
        c.setSbDetails(list);
    }

    private static void develope(JSONObject develope, QccCompany c) {
        //融资历史
        JSONArray companyRongZi = develope.getJSONArray("companyRongZi");
        if (!companyRongZi.isEmpty()) putRzls(companyRongZi, c);

        //投资事件
        JSONArray companyIvest = develope.getJSONArray("companyIvest");
        if (!companyIvest.isEmpty()) putTzsj(companyIvest, c);

        //竞品信息
        JSONArray companyJingpin = develope.getJSONArray("companyJingpin");
        if (!companyJingpin.isEmpty()) putJpxx(companyJingpin, c);

        //核心团队
        JSONArray companyTeam = develope.getJSONArray("companyTeam");
        if (!companyTeam.isEmpty()) putHxtd(companyTeam, c);

        //企业业务
        JSONArray companyProduct = develope.getJSONArray("companyProduct");
        if (!companyProduct.isEmpty()) putQyyw(companyProduct, c);

    }

    private static void putQyyw(JSONArray ja, QccCompany c) {
        List<Qyyw> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Qyyw aa = new Qyyw();
            aa.setpName(oot(ja.getJSONObject(i).get("pName")));
            aa.setpIntroduce(oot(ja.getJSONObject(i).get("pIntroduce")));
            aa.setpType(oot(ja.getJSONObject(i).get("pType")));
            JSONObject pImage = ja.getJSONObject(i).getJSONObject("pImage");
            if (!pImage.isNullObject()) {
                aa.setpType(oot(pImage.get("piUrl")));
                aa.setpType(oot(pImage.get("piName")));
                aa.setpType(oot(pImage.get("piPath")));
            }
            list.add(aa);
        }
        c.setQyyws(list);
    }

    private static void putHxtd(JSONArray ja, QccCompany c) {
        List<Hxtd> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Hxtd aa = new Hxtd();
            aa.settName(oot(ja.getJSONObject(i).get("tName")));
            aa.settRole(oot(ja.getJSONObject(i).get("tRole")));
            aa.settIntroduce(oot(ja.getJSONObject(i).get("tIntroduce")));
            list.add(aa);
        }
        c.setHxtds(list);
    }

    private static void putJpxx(JSONArray ja, QccCompany c) {
        List<Jpxx> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Jpxx aa = new Jpxx();
            aa.setJpName(oot(ja.getJSONObject(i).get("jpName")));
            aa.setJpArea(oot(ja.getJSONObject(i).get("jpArea")));
            aa.setJpNum(oot(ja.getJSONObject(i).get("jpNum")));
            aa.setJpIndustry(oot(ja.getJSONObject(i).get("jpIndustry")));
            aa.setJpBusiness(oot(ja.getJSONObject(i).get("jpBusiness")));
            if (justDatelen(ja.getJSONObject(i).get("jpTime")))
                aa.setJpTime(oot(ja.getJSONObject(i).get("jpTime")));
            aa.setJpMoney(oot(ja.getJSONObject(i).get("jpMoney")));
            JSONObject jpImage = ja.getJSONObject(i).getJSONObject("jpImage");
            if (!jpImage.isNullObject()) {
                aa.setJpiUrl(oot(jpImage.get("jpiUrl")));
                aa.setJpiName(oot(jpImage.get("jpiName")));
                aa.setPiPath(oot(jpImage.get("jpiPath")));
            }
            list.add(aa);
        }
        c.setJpxxes(list);
    }

    private static void putTzsj(JSONArray ja, QccCompany c) {
        List<Tzsj> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Tzsj aa = new Tzsj();
            aa.setiTime(oot(ja.getJSONObject(i).get("iTime")));
            aa.setiNum(oot(ja.getJSONObject(i).get("iNum")));
            aa.setiMoney(oot(ja.getJSONObject(i).get("iMoney")));
            aa.setiName(oot(ja.getJSONObject(i).get("iName")));
            aa.setiPoduct(oot(ja.getJSONObject(i).get("iPoduct")));
            aa.setiArea(oot(ja.getJSONObject(i).get("iArea")));
            aa.setiIndustry(oot(ja.getJSONObject(i).get("iIndustry")));
            aa.setiBusiness(oot(ja.getJSONObject(i).get("iBusiness")));
            list.add(aa);
        }
        c.setTzsjs(list);
    }

    private static void putRzls(JSONArray ja, QccCompany c) {
        List<Rzls> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Rzls aa = new Rzls();
            aa.setRzGoodsName(oot(ja.getJSONObject(i).get("rzGoodsName")));
            aa.setRzRank(oot(ja.getJSONObject(i).get("rzRank")));
            aa.setRzTime(oot(ja.getJSONObject(i).get("rzTime")));
            aa.setRzValue(oot(ja.getJSONObject(i).get("rzValue")));
            aa.setRzMoney(oot(ja.getJSONObject(i).get("rzMoney")));
            aa.setRzRatio(oot(ja.getJSONObject(i).get("rzRatio")));
            aa.setRzName(oot(ja.getJSONObject(i).get("rzName")));
            aa.setRzNews(oot(ja.getJSONObject(i).get("rzNews")));
            list.add(aa);
        }
        c.setRzls(list);
    }

    private static void businessStatus(JSONObject businessStatus, QccCompany c) {
        //招聘信息
        JSONArray recruitInfo = businessStatus.getJSONArray("recruitInfo");
        List<Jyzk> jyzks = new ArrayList<>();
        Jyzk jyzk = new Jyzk();
        if (!recruitInfo.isEmpty()) putZpxx(recruitInfo, jyzk);

        //员工信息
        JSONArray staffInfo = businessStatus.getJSONArray("staffInfo");
        if (!staffInfo.isEmpty()) putYgxx(staffInfo, jyzk);

        //招投标信息
        JSONArray bidInfo = businessStatus.getJSONArray("bidInfo");
        if (!bidInfo.isEmpty()) putZtbxx(bidInfo, jyzk);

        //债券信用
        jyzk.setBondCredit(oot(businessStatus.get("bondCredit")));

        //购地信息
//        JSONArray buyingLandInfo = businessStatus.getJSONArray("buyingLandInfo");哈哈哈
//        if (!buyingLandInfo.isEmpty()) putJyzkGdxx(buyingLandInfo, jyzk);

        //产品信息
        JSONArray productInfo = businessStatus.getJSONArray("productInfo");
        if (!productInfo.isEmpty()) putJyzkCpxx(productInfo, jyzk);

        //资质信息
        JSONArray certificate = businessStatus.getJSONArray("certificate");
        if (!certificate.isEmpty()) putJyzkZzzs(certificate, jyzk);

        //抽查检查
        JSONArray checkCount = businessStatus.getJSONArray("checkCount");
        if (!checkCount.isEmpty()) putJyzkCcjc(checkCount, jyzk);

        //进出口信用、新闻舆情....

        //地块公示
//        jyzk.setLandPublicity(oot(businessStatus.get("landPublicity")));
        //土地转让
//        jyzk.setLandTransfer(oot(businessStatus.get("landTransfer")));

        //行政许可
        JSONObject licenseAll = businessStatus.getJSONObject("licenseAll");
        if (!licenseAll.isNullObject()) putJyzkXzxk(licenseAll, jyzk);

        //税务评级
        JSONArray taxCredit = businessStatus.getJSONArray("taxCredit");
        if (!taxCredit.isEmpty()) putJyzkSwpj(taxCredit, jyzk);

        jyzk.setObligations(oot(businessStatus.get("obligations")));
        jyzk.setResearchRepor(oot(businessStatus.get("researchRepor")));

        //税务总览
        JSONObject taxPandect = businessStatus.getJSONObject("taxPandect");
        if (!taxPandect.isNullObject()) putJyzkSwzl(taxPandect, jyzk);

        //微信公众号
//        JSONArray weChat = businessStatus.getJSONArray("weChat");哈哈哈
//        if (!weChat.isEmpty()) putJyzkWxgzh(weChat, jyzk);

        jyzks.add(jyzk);
        c.setJyzk(jyzks);
    }

    private static void putJyzkWxgzh(JSONArray ja, Jyzk jyzk) {
        List<Jyzk.Wxgzh> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Jyzk.Wxgzh aa = new Jyzk.Wxgzh();
            aa.setGzh(oot(ja.getJSONObject(i).get("wcName")));
            aa.setWxh(oot(ja.getJSONObject(i).get("wcNum")));
            aa.setJj(oot(ja.getJSONObject(i).get("wcIntroduc")));
            aa.setHeadUrl(oot(ja.getJSONObject(i).getJSONObject("wcImage").get("wciUrl")));
            aa.setHead(oot(ja.getJSONObject(i).getJSONObject("wcImage").get("wciName")));
            aa.setHeadPath(oot(ja.getJSONObject(i).getJSONObject("wcImage").get("wciPath")));
            aa.setEwm(oot(ja.getJSONObject(i).getJSONObject("wcCodeImage").get("wcciUrl")));
            aa.setEwmUrl(oot(ja.getJSONObject(i).getJSONObject("wcCodeImage").get("wcciName")));
            aa.setEwmPath(oot(ja.getJSONObject(i).getJSONObject("wcCodeImage").get("wcciPath")));
            list.add(aa);
        }
        jyzk.setWxgzh(list);
    }

    private static void putJyzkSwzl(JSONObject taxCredit, Jyzk jyzk) {
        List<Jyzk.Cwzl> list = new ArrayList<>();
        Jyzk.Cwzl cwzl = new Jyzk.Cwzl();
        cwzl.setLevel(oot(taxCredit.get("tRank")));
        cwzl.setNsqj(oot(taxCredit.get("tRange")));
        cwzl.setJlrl(oot(taxCredit.get("tRate")));
        cwzl.setMll(oot(taxCredit.get("tRofit")));
        list.add(cwzl);
        jyzk.setCwzl(list);
    }

    private static void putJyzkSwpj(JSONArray ja, Jyzk jyzk) {
        List<Jyzk.Swpj> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Jyzk.Swpj aa = new Jyzk.Swpj();
            aa.settNum(oot(ja.getJSONObject(i).get("tNum")));
            aa.settRater(oot(ja.getJSONObject(i).get("tRater")));
            aa.settSectionr(oot(ja.getJSONObject(i).get("tSectionr")));
            aa.settYear(oot(ja.getJSONObject(i).get("tYear")));
            list.add(aa);
        }
        jyzk.setSwpjs(list);
    }

    private static void putJyzkXzxk(JSONObject licenseAll, Jyzk jyzk) {
        if (licenseAll.has("gongShang")) {
            JSONArray gongShang = licenseAll.getJSONArray("gongShang");
            if (!gongShang.isEmpty()) {
                List<Jyzk.Xzxkgsj> xzxkgsjs = new ArrayList<>();
                for (int i = 0; i < gongShang.size(); i++) {
                    Jyzk.Xzxkgsj x = new Jyzk.Xzxkgsj();
                    x.setEndDate(oot(gongShang.getJSONObject(i).get("gsDeadlineEnd")));
                    x.setStartDate(oot(gongShang.getJSONObject(i).get("gsDeadlineStart")));
                    x.setXkConent(oot(gongShang.getJSONObject(i).get("gsInfo")));
                    x.setFileName(oot(gongShang.getJSONObject(i).get("gsLicenceName")));
                    x.setFileNo(oot(gongShang.getJSONObject(i).get("gsLicenceNum")));
                    x.setXkjg(oot(gongShang.getJSONObject(i).get("gsOrgan")));
                    xzxkgsjs.add(x);
                }
                jyzk.setXzxkgsjs(xzxkgsjs);
            }
        }

        if (licenseAll.has("xinYongZG")) {
            JSONArray xinYongZG = licenseAll.getJSONArray("xinYongZG");
            if (!xinYongZG.isEmpty()) {
                List<Jyzk.Xzxkxyzg> xzxkxyzgs = new ArrayList<>();
                for (int i = 0; i < xinYongZG.size(); i++) {
                    Jyzk.Xzxkxyzg x = new Jyzk.Xzxkxyzg();
                    x.setProjectName(oot(xinYongZG.getJSONObject(i).get("xyItemName")));
                    x.setXyOrgan(oot(xinYongZG.getJSONObject(i).get("xyOrgan")));
                    x.setXyNum(oot(xinYongZG.getJSONObject(i).get("xyNum")));
                    x.setDate(oot(xinYongZG.getJSONObject(i).get("xySureDate")));
                    x.setXyState(oot(xinYongZG.getJSONObject(i).get("xyState")));
                    x.setConent(oot(xinYongZG.getJSONObject(i).get("xyInfo")));
                    x.setXyEndDate(oot(xinYongZG.getJSONObject(i).get("xyEndDate")));
                    x.setXyType(oot(xinYongZG.getJSONObject(i).get("xyType")));
                    x.setArea(oot(xinYongZG.getJSONObject(i).get("xyArea")));
                    xzxkxyzgs.add(x);
                }
                jyzk.setXzxkxyzg(xzxkxyzgs);
            }
        }

    }

    private static void putJyzkCcjc(JSONArray ja, Jyzk jyzk) {
        List<Jyzk.Ccjc> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Jyzk.Ccjc aa = new Jyzk.Ccjc();
            aa.setCcOrgan(oot(ja.getJSONObject(i).get("ccOrgan")));
            aa.setCcType(oot(ja.getJSONObject(i).get("ccType")));
            aa.setCcResult(oot(ja.getJSONObject(i).get("ccResult")));
            if (justDatelen(ja.getJSONObject(i).get("ccTime")))
                aa.setCcTime(oot(ja.getJSONObject(i).get("ccTime")));
            list.add(aa);
        }
        jyzk.setCcjcs(list);
    }

    private static void putJyzkZzzs(JSONArray ja, Jyzk jyzk) {
        List<Jyzk.Zzzs> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Jyzk.Zzzs aa = new Jyzk.Zzzs();
            aa.setcName(oot(ja.getJSONObject(i).get("cName")));
            aa.setcType(oot(ja.getJSONObject(i).get("cType")));
            aa.setcNum(oot(ja.getJSONObject(i).get("cNum")));
            aa.setcGetDate(oot(ja.getJSONObject(i).get("cGetDate")));
            aa.setcEndDate(oot(ja.getJSONObject(i).get("cEndDate")));
            aa.setCstaet(oot(ja.getJSONObject(i).get("cstaet")));
            aa.setCbeizhu(oot(ja.getJSONObject(i).get("cbeizhu")));
            list.add(aa);
        }
        jyzk.setZzzs(list);
    }

    private static void putJyzkCpxx(JSONArray ja, Jyzk jyzk) {
        List<Jyzk.Product> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Jyzk.Product aa = new Jyzk.Product();
            aa.setName(oot(ja.getJSONObject(i).get("pAllName")));
            aa.setArea(oot(ja.getJSONObject(i).get("pField")));
            aa.setMsg(oot(ja.getJSONObject(i).get("pDescribe")));
            aa.setUrl(oot(ja.getJSONObject(i).getJSONObject("pImage").get("piUrl")));
            aa.setImgName(oot(ja.getJSONObject(i).getJSONObject("pImage").get("piName")));
            aa.setPath(oot(ja.getJSONObject(i).getJSONObject("pImage").get("piPath")));
            list.add(aa);
        }
        jyzk.setProducts(list);
    }

    private static void putJyzkGdxx(JSONArray ja, Jyzk jyzk) {
        List<Jyzk.Gdxx> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Jyzk.Gdxx aa = new Jyzk.Gdxx();
            aa.setBliLocat(oot(ja.getJSONObject(i).get("bliLocat")));
            aa.setBliAcreage(oot(ja.getJSONObject(i).get("bliAcreage")));
            aa.setBliUse(oot(ja.getJSONObject(i).get("bliUse")));
            aa.setBliArea(oot(ja.getJSONObject(i).get("bliArea")));
            aa.setBliWay(oot(ja.getJSONObject(i).get("bliWay")));
            aa.setBliDate(oot(ja.getJSONObject(i).get("bliDate")));
            list.add(aa);
        }
        jyzk.setGdxxes(list);
    }

    private static void putZtbxx(JSONArray ja, Jyzk jyzk) {
        List<Jyzk.Zdbxx> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Jyzk.Zdbxx aa = new Jyzk.Zdbxx();
            aa.setArea(oot(ja.getJSONObject(i).get("bArea")));
            aa.setType(oot(ja.getJSONObject(i).get("bClassify")));
            aa.setbMan(oot(ja.getJSONObject(i).get("bMan")));
            aa.setDate(oot(ja.getJSONObject(i).get("bPublishTime")));
            aa.setMsg(oot(ja.getJSONObject(i).get("bTitle")));
            list.add(aa);
        }
        jyzk.setZdbxx(list);
    }

    private static void putYgxx(JSONArray ja, Jyzk jyzk) {
        List<Ygxx> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Ygxx aa = new Ygxx();
            aa.setSiAge(oot(ja.getJSONObject(i).get("siAge")));
            aa.setSiGender(oot(ja.getJSONObject(i).get("siGender")));
            aa.setSiSchool(oot(ja.getJSONObject(i).get("siSchool")));
            aa.setSiJob(oot(ja.getJSONObject(i).get("siJob")));
            aa.setSiMoney(oot(ja.getJSONObject(i).get("siMoney")));
            aa.setSiTime(oot(ja.getJSONObject(i).get("siTime")));
            list.add(aa);
        }
        jyzk.setYgxxes(list);
    }

    private static void putZpxx(JSONArray ja, Jyzk jyzk) {
        List<Jyzk.Zp> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Jyzk.Zp aa = new Jyzk.Zp();
            aa.setXl(oot(ja.getJSONObject(i).get("brEducate")));
            aa.setJy(oot(ja.getJSONObject(i).get("brExperience")));
            aa.setCity(oot(ja.getJSONObject(i).get("brArea")));
            aa.setDate(oot(ja.getJSONObject(i).get("brPublishTime")));
            aa.setYx(oot(ja.getJSONObject(i).get("brSalary")));
            aa.setZpzw(oot(ja.getJSONObject(i).get("brJobTitle")));
            list.add(aa);
        }
        jyzk.setZp(list);
    }

    private static void manageDangerous(JSONObject manageDangerous, QccCompany c) {
        //经营异常
        JSONArray runUnusual = manageDangerous.getJSONArray("runUnusual");
        if (!runUnusual.isEmpty()) putJyyc(runUnusual, c);

        //行政处罚
        JSONObject punishment = manageDangerous.getJSONObject("punishment");
        JSONArray gongshangju = punishment.getJSONArray("gongshangju"); //工商局
        List<Invest> invests = new ArrayList<>();
        Invest invest = new Invest();
        if (!gongshangju.isEmpty()) putGsj(gongshangju, invest);

        if (punishment.has("suiwuju")) {
            JSONArray suiwuju = punishment.getJSONArray("suiwuju"); //税务局
            if (!suiwuju.isEmpty()) putSwj(suiwuju, invest);
        }

        if (punishment.has("xinYongZG")) {
            JSONArray xinYongZG = punishment.getJSONArray("xinYongZG"); //信用中国
            if (!xinYongZG.isEmpty()) putXyzg(xinYongZG, invest);
        }

        JSONArray equityCount = manageDangerous.getJSONArray("equityCount");//股权出质
        if (!equityCount.isEmpty()) putGqcz(equityCount, invest);

        invests.add(invest);
        c.setJyfx(invests);

        c.setSeverePunishment(oot(manageDangerous.get("severePunishment")));

        JSONArray chattelMortgage = manageDangerous.getJSONArray("chattelMortgage");//动产抵押
        if (!chattelMortgage.isEmpty()) putDcdy(chattelMortgage, c);

//        if (manageDangerous.has("ownTaxCount")) {哈哈哈
//            JSONArray ownTaxCount = manageDangerous.getJSONArray("ownTaxCount");//欠税公告
//            if (!ownTaxCount.isEmpty()) putQsgg(ownTaxCount, c);
//        }

        if (manageDangerous.has("judicialSale")) {
            JSONArray judicialSale = manageDangerous.getJSONArray("judicialSale");//司法拍卖
            if (!judicialSale.isEmpty()) putSfpm(judicialSale, c);
        }

//        if (manageDangerous.has("environmentPunishment")) {哈哈哈
//            JSONArray environmentPunishment = manageDangerous.getJSONArray("environmentPunishment");//环保处罚
//            if (!environmentPunishment.isEmpty()) putHbcf(environmentPunishment, c);
//        }

        c.setLiquidationInfo(oot(manageDangerous.get("liquidationInfo")));
        c.setPropertyPledge(oot(manageDangerous.get("propertyPledge")));
        c.setTaxIllegal(oot(manageDangerous.get("taxIllegal")));

//        if (manageDangerous.has("landMortgage")) {哈哈哈
//            JSONArray landMortgage = manageDangerous.getJSONArray("landMortgage");//土地抵押
//            if (!landMortgage.isEmpty()) putTddy(landMortgage, c);
//        }

//        JSONObject simpleCancellation = manageDangerous.getJSONObject("simpleCancellation");//简易注销

//        if (manageDangerous.has("interpellationCount")) {哈哈哈
//            JSONArray interpellationCount = manageDangerous.getJSONArray("interpellationCount");//公告催告
//            if (!interpellationCount.isEmpty()) putGgcg(interpellationCount, c);
//        }

    }

    private static void putGgcg(JSONArray ja, QccCompany c) {
        List<Ggcg> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Ggcg aa = new Ggcg();
            aa.setIlBillNum(oot(ja.getJSONObject(i).get("ilBillNum")));
            aa.setIlpublishOrg(oot(ja.getJSONObject(i).get("ilpublishOrg")));
            aa.setIlpublishDate(oot(ja.getJSONObject(i).get("ilpublishDate")));
            list.add(aa);
        }
        c.setGgcgs(list);
    }

    private static void putTddy(JSONArray ja, QccCompany c) {
        List<Tddy> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Tddy aa = new Tddy();
            aa.setLmLocation(oot(ja.getJSONObject(i).get("lmLocation")));
            aa.setLmDate(oot(ja.getJSONObject(i).get("lmDate")));
            aa.setLmArea(oot(ja.getJSONObject(i).get("lmArea")));
            aa.setLmAcreage(oot(ja.getJSONObject(i).get("lmAcreage")));
            aa.setLmUse(oot(ja.getJSONObject(i).get("lmUse")));
            list.add(aa);
        }
        c.setTddys(list);
    }

    private static void putHbcf(JSONArray ja, QccCompany c) {
        List<Hbcf> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Hbcf aa = new Hbcf();
            aa.setEpNuam(oot(ja.getJSONObject(i).get("epNuam")));
            aa.setEpType(oot(ja.getJSONObject(i).get("epType")));
            aa.setEpOrg(oot(ja.getJSONObject(i).get("epOrg")));
            aa.setEpDate(oot(ja.getJSONObject(i).get("epDate")));
            list.add(aa);
        }
        c.setHbcfs(list);
    }

    private static void putSfpm(JSONArray ja, QccCompany c) {
        List<Sfpm> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Sfpm aa = new Sfpm();
            aa.setaName(oot(ja.getJSONObject(i).get("aName")));
            aa.setaPrice(oot(ja.getJSONObject(i).get("aPrice")));
            aa.setaTime(oot(ja.getJSONObject(i).get("aTime")));
            aa.setaCourt(oot(ja.getJSONObject(i).get("aCourt")));
            aa.setAcHtml(oot(ja.getJSONObject(i).get("acHtml")));
            list.add(aa);
        }
        c.setSfpms(list);
    }

    private static void putQsgg(JSONArray ja, QccCompany c) {
        List<Qsgg> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Qsgg aa = new Qsgg();
            aa.setOwntcTaxType(oot(ja.getJSONObject(i).get("owntcTaxType")));
            aa.setOwntcOver(oot(ja.getJSONObject(i).get("owntcOver")));
            aa.setOwntcNowOver(oot(ja.getJSONObject(i).get("owntcNowOver")));
            aa.setOwntcOrg(oot(ja.getJSONObject(i).get("owntcOrg")));
            aa.setOwntcDate(oot(ja.getJSONObject(i).get("owntcDate")));
            aa.setOwntcTaxNum(oot(ja.getJSONObject(i).get("owntcTaxNum")));
            list.add(aa);
        }
        c.setQsgg(list);
    }

    private static void putDcdy(JSONArray ja, QccCompany c) {
        List<Dcdy> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Dcdy aa = new Dcdy();
            aa.setCmRegisterNum(oot(ja.getJSONObject(i).get("cmRegisterNum")));
            aa.setCmRegisterTime(oot(ja.getJSONObject(i).get("cmRegisterTime")));
            aa.setCmPublicTime(oot(ja.getJSONObject(i).get("cmPublicTime")));
            aa.setCmRegisterOrgan(oot(ja.getJSONObject(i).get("cmRegisterOrgan")));
            aa.setCmClaimNum(oot(ja.getJSONObject(i).get("cmClaimNum")));
            aa.setCmState(oot(ja.getJSONObject(i).get("cmState")));
            list.add(aa);
        }
        c.setDcdys(list);
    }

    private static void putGqcz(JSONArray ja, Invest invest) {
        List<Invest.Gqcz> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Invest.Gqcz aa = new Invest.Gqcz();
            aa.setStatus(oot(ja.getJSONObject(i).get("eyStatus")));
            aa.setCzr(oot(ja.getJSONObject(i).get("eyoName")));
            aa.setEyoMan(oot(ja.getJSONObject(i).get("eyoMan")));
            aa.setCzgqse(oot(ja.getJSONObject(i).get("eyoMoneyNum")));
            aa.setZqr(oot(ja.getJSONObject(i).get("eyMan")));
            aa.setEyNum(oot(ja.getJSONObject(i).get("eyNum")));
            aa.setDjrq(oot(ja.getJSONObject(i).get("eyTime")));
            aa.setDjno(oot(ja.getJSONObject(i).get("eyRegisterNum")));
            list.add(aa);
        }
        invest.setGqczs(list);
    }

    private static void putXyzg(JSONArray ja, Invest invest) {
        List<Invest.Bb> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Invest.Bb aa = new Invest.Bb();
            aa.setWsh(oot(ja.getJSONObject(i).get("xyzgNum")));
            aa.setName(oot(ja.getJSONObject(i).get("xyzgName")));
            aa.setArea(oot(ja.getJSONObject(i).get("xyzgArea")));
            aa.setTime(oot(ja.getJSONObject(i).get("xyzgDate")));
            list.add(aa);
        }
        invest.setBbs(list);
    }

    private static void putSwj(JSONArray ja, Invest invest) {
        List<Invest.Swj> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Invest.Swj aa = new Invest.Swj();
            aa.setWsswNum(oot(ja.getJSONObject(i).get("wsswNum")));
            aa.setWsswDate(oot(ja.getJSONObject(i).get("wsswDate")));
            aa.setWsswReason(oot(ja.getJSONObject(i).get("wsswReason")));
            list.add(aa);
        }
        invest.setSwjs(list);
    }

    private static void putGsj(JSONArray ja, Invest invest) {
        List<Invest.Aa> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Invest.Aa aa = new Invest.Aa();
            aa.setWsh(oot(ja.getJSONObject(i).get("wsgsNum")));
            aa.setWfxwlx(oot(ja.getJSONObject(i).get("wsgsType")));
            aa.setXzcflr(oot(ja.getJSONObject(i).get("wsgsInfo")));
            aa.setGsdate(oot(ja.getJSONObject(i).get("wsgsDate")));
            aa.setJdjg(oot(ja.getJSONObject(i).get("wsgsSection")));
            aa.setJddate(oot(ja.getJSONObject(i).get("wsgsjdDate")));
            aa.setWgssLegal(oot(ja.getJSONObject(i).get("wgssLegal")));
            aa.setWsgsBeizhu(oot(ja.getJSONObject(i).get("wsgsBeizhu")));
            list.add(aa);
        }
        invest.setAas(list);
    }

    private static void putJyyc(JSONArray ja, QccCompany c) {
        List<Jyyc> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Jyyc jyyc = new Jyyc();
            jyyc.setAddReason(oot(ja.getJSONObject(i).get("ruInCause")));
            jyyc.setAddDate(oot(ja.getJSONObject(i).get("ruInTime")));
            jyyc.setRomoveReason(oot(ja.getJSONObject(i).get("ruOutCause")));
            jyyc.setRemoveDate(oot(ja.getJSONObject(i).get("ruOutTime")));
            jyyc.setDecisionOffice(oot(ja.getJSONObject(i).get("ruSection")));
            list.add(jyyc);
        }
        c.setJyyc(list);
    }

    private static void lawDangerous(JSONObject lawDangerous, QccCompany c) {
        //开庭公告
        JSONArray announcementCount = lawDangerous.getJSONArray("announcementCount");
        if (!announcementCount.isEmpty()) putKtgg(announcementCount, c);

        //法律诉讼
        JSONArray lawsuit = lawDangerous.getJSONArray("lawsuit");
        if (!announcementCount.isEmpty()) putFlss(lawsuit, c);

        //法院公告
        JSONArray courtCount = lawDangerous.getJSONArray("courtCount");
        if (!courtCount.isEmpty()) putFygg(courtCount, c);

        //失信被执行人
        JSONArray dishonestInfo = lawDangerous.getJSONArray("dishonestInfo");
        if (!dishonestInfo.isEmpty()) putSxbzxr(dishonestInfo, c);

        //被执行人信息
        JSONArray zhixingInfo = lawDangerous.getJSONArray("zhixingInfo");
        if (!zhixingInfo.isEmpty()) putBzxrxx(zhixingInfo, c);

        //司法协助
        JSONArray judicialaAid = lawDangerous.getJSONArray("judicialaAid");
        if (!judicialaAid.isEmpty()) putSfxz(judicialaAid, c);

        //司法协助
        JSONArray caipanwenshuInfo = lawDangerous.getJSONArray("caipanwenshuInfo");
        if (!caipanwenshuInfo.isEmpty()) putCpws(caipanwenshuInfo, c);

        c.setDeliveryCount(oot(lawDangerous.get("deliveryCount")));
    }

    private static void putCpws(JSONArray ja, QccCompany c) {
        List<Cpws> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Cpws cpws = new Cpws();
            cpws.setCaseName(oot(ja.getJSONObject(i).get("cpwsName")));
            cpws.setSubmitDate(oot(ja.getJSONObject(i).get("cpwsTime")));
            cpws.setCaseNo(oot(ja.getJSONObject(i).get("cpwsNum")));
            cpws.setAjsf(oot(ja.getJSONObject(i).get("cpwsIdentity")));
            cpws.setCourt(oot(ja.getJSONObject(i).get("cpwsJudge")));

            List<String> appellor = new ArrayList<>();
            appellor.add(oot(ja.getJSONObject(i).get("cpwsMaster")));
            cpws.setAppellor(appellor);

            cpws.setCaseType(oot(ja.getJSONObject(i).get("cpwsType")));
            cpws.setJudgeDate(oot(ja.getJSONObject(i).get("cpwsJudgeTime")));
            cpws.setContent(oot(ja.getJSONObject(i).get("cpwsResult")));
            list.add(cpws);
        }
        c.setCpws(list);
    }

    private static void putSfxz(JSONArray ja, QccCompany c) {
        List<Sfxz> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Sfxz sfxz = new Sfxz();
            sfxz.setShZhiXing(oot(ja.getJSONObject(i).get("shZhiXing")));
            sfxz.setShEquityNum(oot(ja.getJSONObject(i).get("shEquityNum")));
            sfxz.setShNoticeNum(oot(ja.getJSONObject(i).get("shNoticeNum")));
            sfxz.setShType(oot(ja.getJSONObject(i).get("shType")));
            list.add(sfxz);
        }
        c.setSfxz(list);
    }

    private static void putBzxrxx(JSONArray ja, QccCompany c) {
        List<Bzxr> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Bzxr bzxr = new Bzxr();
            bzxr.setNo(oot(ja.getJSONObject(i).get("zCaseNum")));
            bzxr.setTime(oot(ja.getJSONObject(i).get("zCaseTime")));
            bzxr.setNum(oot(ja.getJSONObject(i).get("zTarget")));
            bzxr.setCourt(oot(ja.getJSONObject(i).get("zCourt")));
            list.add(bzxr);
        }
        c.setBzxr(list);
    }

    private static void putSxbzxr(JSONArray ja, QccCompany c) {
        List<Sxbzxr> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Sxbzxr sxbzxr = new Sxbzxr();
            sxbzxr.setAnno(oot(ja.getJSONObject(i).get("diNum")));
            sxbzxr.setExecutestatus(oot(ja.getJSONObject(i).get("diPerform")));
            sxbzxr.setYiwu(oot(ja.getJSONObject(i).get("diDuty")));
            sxbzxr.setExecutegov(oot(ja.getJSONObject(i).get("diCourt")));
            sxbzxr.setExecuteunite(oot(ja.getJSONObject(i).get("diUnit")));
            sxbzxr.setPublicdate(oot(ja.getJSONObject(i).get("diPublishTime")));
            sxbzxr.setLiandate(oot(ja.getJSONObject(i).get("dFilingDate")));
            sxbzxr.setProvince(oot(ja.getJSONObject(i).get("dProvince")));
            list.add(sxbzxr);
        }
        c.setSxbzxr(list);
    }

    private static void putFygg(JSONArray ja, QccCompany c) {
        List<Fygg> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Fygg fygg = new Fygg();
            fygg.setPublishDate(oot(ja.getJSONObject(i).get("cDate")));
            fygg.setcPage(oot(ja.getJSONObject(i).get("cPage")));
            fygg.setaAppellor(oot(ja.getJSONObject(i).get("aAppellor")));
            fygg.setParty(oot(ja.getJSONObject(i).get("aDefendant")));
            fygg.setCategory(oot(ja.getJSONObject(i).get("cType")));
            fygg.setcJudge(oot(ja.getJSONObject(i).get("cJudge")));
            fygg.setContent(oot(ja.getJSONObject(i).get("cInfo")));
            list.add(fygg);
        }
        c.setFygg(list);
    }

    private static void putFlss(JSONArray ja, QccCompany c) {
        List<Flss> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Flss flss = new Flss();
            flss.setlDate(oot(ja.getJSONObject(i).get("lDate")));
            flss.setlCaipanwenshu(oot(ja.getJSONObject(i).get("lCaipanwenshu")));
            flss.setlCaseReason(oot(ja.getJSONObject(i).get("lCaseReason")));
            flss.setlCaseIdentity(oot(ja.getJSONObject(i).get("lCaseIdentity")));
            flss.setlCaseNum(oot(ja.getJSONObject(i).get("lCaseNum")));
            flss.setlCaseType(oot(ja.getJSONObject(i).get("lCaseType")));
            list.add(flss);
        }
        c.setFlss(list);
    }

    private static void putKtgg(JSONArray ja, QccCompany c) {
        List<Ktgg> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Ktgg ktgg = new Ktgg();
            ktgg.setCase_Reason(oot(ja.getJSONObject(i).get("aCaseReason")));
            ktgg.setCase_No(oot(ja.getJSONObject(i).get("aCaseNum")));

            List<Ktgg.Prosecutor> pList = new ArrayList<>();
            Ktgg.Prosecutor prosecutor = new Ktgg.Prosecutor();
            prosecutor.setName(oot(ja.getJSONObject(i).get("aAppellor")));
            pList.add(prosecutor);
            ktgg.setProsecutor(pList);

            List<Ktgg.Defendant> dList = new ArrayList<>();
            Ktgg.Defendant defendant = new Ktgg.Defendant();
            defendant.setName(oot(ja.getJSONObject(i).get("aDefendant")));
            dList.add(defendant);
            ktgg.setDefendant(dList);

            ktgg.setOpen_Time(oot(ja.getJSONObject(i).get("aLawfulDay")));
            ktgg.setPerson(oot(ja.getJSONObject(i).get("aParties")));
            ktgg.setUndertake_Department(oot(ja.getJSONObject(i).get("aHandleSection")));
            ktgg.setChief_Judge(oot(ja.getJSONObject(i).get("aJudge")));
            ktgg.setExecute_Unite(oot(ja.getJSONObject(i).get("aCourt")));
            ktgg.setExecute_Gov(oot(ja.getJSONObject(i).get("aTarget")));
            ktgg.setSchedule_Time(oot(ja.getJSONObject(i).get("aFiling")));
            list.add(ktgg);
        }
        c.setKtgg(list);
    }

    private static void background(JSONObject background, QygsDetail qd, QccCompany c, StringBuffer sb) {
        //基本信息
        if (background.has("baseInfo")) {
            JSONObject base = background.getJSONObject("baseInfo");
            if (!base.isNullObject()) putBase(qd, base, c, sb);
        }

        //主要人员
        if (background.has("staffCount")) {
            if (background.optJSONArray("staffCount") != null) {
                JSONArray staffCount = background.getJSONArray("staffCount");
                if (!staffCount.isEmpty()) putZyry(qd, staffCount);
            }
        }

        //股东信息
        if (background.has("holderInfo")) {
            if (background.optJSONArray("holderInfo") != null) {
                JSONArray holderInfo = background.getJSONArray("holderInfo");
                if (!holderInfo.isEmpty()) putGdxx(qd, holderInfo);
            }
        }

        //公司对外投资
        if (background.has("inverstInfo")) {
            JSONArray inverstInfo = background.getJSONArray("inverstInfo");
            if (!inverstInfo.isEmpty()) putDwtz(inverstInfo, c);
        }

        //法人对外投资
        if (background.has("legalManInverst")) {
            JSONArray legalManInverst = background.getJSONArray("legalManInverst");
            if (!legalManInverst.isEmpty()) putFrDwtz(legalManInverst, c);
        }

        //法人在外任职
        if (background.has("legalManOutPost")) {
            JSONArray legalManOutPost = background.getJSONArray("legalManOutPost");
            if (!legalManOutPost.isEmpty()) putFrZwrz(legalManOutPost, c);
        }

        //最终受益人
        if (background.has("shouyiren")) {
            JSONArray shouyiren = background.getJSONArray("shouyiren");
            if (!shouyiren.isEmpty()) putZzsyr(shouyiren, c);
        }

        //变更记录
        if (background.has("changeInfo")) {
            JSONArray changeInfo = background.optJSONArray("changeInfo");
            if (changeInfo != null) {
                if (!changeInfo.isEmpty()) putBgjl(changeInfo, qd);
            }
        }

        //分支机构
        if (background.has("branch")) {
            if (background.optJSONArray("branch") != null) {
                JSONArray branch = background.getJSONArray("branch");
                if (!branch.isEmpty()) putFzjg(branch, qd);
            }
        }

        //发票抬头
        if (background.has("ionvoiceMsg")) {
            JSONObject ionvoiceMsg = background.getJSONObject("ionvoiceMsg");
            if (!ionvoiceMsg.isEmpty()) putFptt(ionvoiceMsg, c);
        }

        //年报
        if (background.has("reportInfo")) {
            Object testObj = background.get("reportInfo");
            if (testObj != null) {
                if (background.has("reportInfo")) {
                    JSONObject reportInfo = background.optJSONObject("reportInfo");
                    if (reportInfo != null) {
                        putQynb(reportInfo, c, PTConst.QYNB_START, PTConst.QYNB_END, PTConst.QYNB_REPORTPREFIX, sb);
                    }
                }
            }
        }

        c.setQygsDetail(qd);
    }

    private static void putFptt(JSONObject ja, QccCompany c) {
        Fptt f = new Fptt();
        f.setName(oot(ja.get("imName")));
        f.setNo(oot(ja.get("imNum")));
        f.setAddress(oot(ja.get("imAdress")));
        f.setPhoneNo(oot(ja.get("imTel")));
        f.setBank(oot(ja.get("imBankName")));
        f.setBankAccount(oot(ja.get("imBankUser")));
        c.setFptts(f);
    }

    private static void putFzjg(JSONArray ja, QygsDetail qd) {
        if (!ja.isEmpty()) {
            List<QygsDetail.Branches> fList = new ArrayList<>();
            for (int i = 0; i < ja.size(); i++) {
                QygsDetail.Branches f = new QygsDetail.Branches();
                f.setName(oot(ja.getJSONObject(i).get("bCompanyName")));
                f.setOperName(oot(ja.getJSONObject(i).get("bName")));
                f.setRegStatus(oot(ja.getJSONObject(i).get("bState")));
                f.setLicense(oot(ja.getJSONObject(i).get("bLicense")));
                f.setBelongOrg(oot(ja.getJSONObject(i).get("bRegistOrgan")));
                f.setRegMoney(oot(ja.getJSONObject(i).get("bMoney")));
                fList.add(f);
            }
            qd.setBranchesList(fList);
        }
    }

    private static void putQynb(JSONObject jo, QccCompany c, int start, int end, final String reportPrefix, StringBuffer sb) {
        List<QynbDetail> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            final String key = reportPrefix.concat(String.valueOf(i));
            if (!jo.getJSONObject(key).isNullObject()) {
                QynbDetail qynb = new QynbDetail();
                putQynbBaseInfo(jo, key, qynb, sb);//年报基本信息
                putQynbWebSite(jo, key, qynb); //网站信息
                putQynbPartner(jo, key, qynb);//股东出资信息
                putQynbInvestInfo(jo, key, qynb);//对外投资信息
                putQynbStockChange(jo, key, qynb);//股权变更信息
                putQynbAssetsData(jo, key, qynb);//企业资产状况信息
                list.add(qynb);
            }
        }
        c.setQynbDetail(list);
    }

    private static void putQynbAssetsData(JSONObject jsonObject, String key, QynbDetail qynb) {
        QynbDetail.AssetsData ad = new QynbDetail.AssetsData();
        JSONObject jo = jsonObject.getJSONObject(key).getJSONObject("rAssetInfo");
        ad.setTotalAssets(oot(jo.isNullObject() || !jo.has("rAssetsNum") ? null : jo.get("rAssetsNum")));
        ad.setGrossTradingIncome(oot(jo.isNullObject() || !jo.has("rSalesNum") ? null : jo.get("rSalesNum")));
        ad.setMainBusinessIncome(oot(jo.isNullObject() || !jo.has("rMainBusinessNum") ? null : jo.get("rMainBusinessNum")));
        ad.setTotalTaxAmount(oot(jo.isNullObject() || !jo.has("rTaxNum") ? null : jo.get("rTaxNum")));
        ad.setTotalOwnersEquity(oot(jo.isNullObject() || !jo.has("rRightsNum") ? null : jo.get("rRightsNum")));
        ad.setTotalProfit(oot(jo.isNullObject() || !jo.has("rProfitsNum") ? null : jo.get("rProfitsNum")));
        ad.setNetProfit(oot(jo.isNullObject() || !jo.has("rNetProfitNum") ? null : jo.get("rNetProfitNum")));
        ad.setTotalLiabilities(oot(jo.isNullObject() || !jo.has("rDebtNum") ? null : jo.get("rDebtNum")));
        qynb.setAssetsData(ad);
    }

    private static void putQynbStockChange(JSONObject jo, String key, QynbDetail qynb) {
        if (jo.optJSONObject(key) != null && jo.getJSONObject(key).optJSONArray("rRightInfo") != null) {
            JSONArray ja = jo.getJSONObject(key).getJSONArray("rRightInfo");
            if (!ja.isEmpty()) {
                List<QynbDetail.StockChangeList> stockChange = new ArrayList<>();
                for (int i = 0; i < ja.size(); i++) {
                    QynbDetail.StockChangeList wb = new QynbDetail.StockChangeList();
                    wb.setName(oot(ja.getJSONObject(i).get("rriHolder")));
                    wb.setBefore(oot(ja.getJSONObject(i).get("rriBeforeRatio")));
                    wb.setAfter(oot(ja.getJSONObject(i).get("rriAferRatio")));
                    if (justDatelen(ja.getJSONObject(i).get("rriDate")))
                        wb.setChangeDate(oot(ja.getJSONObject(i).get("rriDate")));
                    stockChange.add(wb);
                }
                qynb.setStockChange(stockChange);
            }
        }
    }

    private static void putQynbInvestInfo(JSONObject jo, String key, QynbDetail qynb) {
        if (jo.optJSONObject(key) != null && jo.getJSONObject(key).optJSONArray("rFundInfo") != null) {
            JSONArray ja = jo.getJSONObject(key).getJSONArray("rFundInfo");
            if (!ja.isEmpty()) {
                List<QynbDetail.InvestInfoList> investInfo = new ArrayList<>();
                for (int i = 0; i < ja.size(); i++) {
                    QynbDetail.InvestInfoList wb = new QynbDetail.InvestInfoList();
                    wb.setName(oot(ja.getJSONObject(i).get("rfiName")));
                    wb.setRegNo(oot(ja.getJSONObject(i).get("rfiNum")));
                    investInfo.add(wb);
                }
                qynb.setInvestInfo(investInfo);
            }
        }
    }

    private static void putQynbPartner(JSONObject jo, String key, QynbDetail qynb) {
        if(jo.optJSONObject(key)!=null && jo.getJSONObject(key).optJSONArray("rHolderInfo")!=null) {
            JSONArray ja = jo.getJSONObject(key).getJSONArray("rHolderInfo");
            if (!ja.isEmpty()) {
                List<QynbDetail.PartnerList> partner = new ArrayList<>();
                for (int i = 0; i < ja.size(); i++) {
                    QynbDetail.PartnerList wb = new QynbDetail.PartnerList();
                    wb.setName(oot(ja.getJSONObject(i).get("rHolder")));
                    wb.setShouldCapi(String.valueOf(dealMoney(oot(ja.getJSONObject(i).get("rConfirmNum")))));
                    wb.setShouldDate(dealDate(oot(ja.getJSONObject(i).get("rConfirmTime"))));
                    wb.setShouldType(oot(ja.getJSONObject(i).get("rConfirmWay")));
                    wb.setRealCapi(String.valueOf(dealMoney(oot(ja.getJSONObject(i).get("rIndeedNum")))));
                    wb.setRealDate(dealDate(oot(ja.getJSONObject(i).get("rIndeedTime"))));
                    wb.setRealType(oot(ja.getJSONObject(i).get("rIndeedWay")));
                    partner.add(wb);
                }
                qynb.setPartner(partner);
            }
        }
    }

    private static void putQynbWebSite(JSONObject jo, String key, QynbDetail qynb) {
        if (jo.optJSONObject(key) != null && jo.optJSONObject(key).optJSONArray("rWebInfo") != null) {
            JSONArray rwi = jo.getJSONObject(key).getJSONArray("rWebInfo");
            if (!rwi.isEmpty()) {
                List<QynbDetail.WebSiteList> webSite = new ArrayList<>();
                for (int i = 0; i < rwi.size(); i++) {
                    QynbDetail.WebSiteList wb = new QynbDetail.WebSiteList();
                    wb.setName(oot(rwi.getJSONObject(i).get("rName")));
                    wb.setType(oot(rwi.getJSONObject(i).get("rWebType")));
                    wb.setWebSite(oot(rwi.getJSONObject(i).get("rWeb")));
                    webSite.add(wb);
                }
                qynb.setWebSite(webSite);
            }
        }
    }

    private static void putQynbBaseInfo(JSONObject jo, String key, QynbDetail qynb, StringBuffer sb) {
        QynbDetail.BasicInfoData base = new QynbDetail.BasicInfoData();
        JSONObject rbi = jo.getJSONObject(key).getJSONObject("rBaseInfo");
        base.setCompanyName(oot(rbi.get("rName")));
        base.setRegNo(oot(rbi.get("rRegister")));
        base.setPostCode(oot(rbi.get("rPostalCode")));
        base.setEmailAddress(oot(rbi.get("rEmail")));
        final String tel = oot(rbi.get("rTel"));
        base.setContactNo(tel);
        if (!sb.toString().contains(tel)) {
            if (sb.toString().length() > 0) sb.append("、").append(tel);
            else sb.append(tel);
        }
        base.setAddress(oot(rbi.get("rAddress")));
        base.setStatus(oot(rbi.get("rState")));
        base.setEmployeeCount(oot(rbi.get("rWorkNum")));
        base.setHasWebSite(oot(rbi.get("rHasWeb")));
        base.setHasNewStockOrByStock(oot(rbi.get("rHasBuy")));
        base.setIsStockRightTransfer(oot(rbi.get("rHasmove")));
        base.setApprovedOperationItem(oot(rbi.get("rBusinessScope")));
        qynb.setBasicInfoData(base);
    }

    private static void putBgjl(JSONArray ja, QygsDetail qd) {
        if (!ja.isEmpty()) {
            List<QygsDetail.ChangeRecords> fList = new ArrayList<>();
            for (int i = 0; i < ja.size(); i++) {
                QygsDetail.ChangeRecords f = new QygsDetail.ChangeRecords();
                if (justDatelen(ja.getJSONObject(i).get("changeTime")))
                    f.setChangeDate(oot(ja.getJSONObject(i).get("changeTime")));
                f.setProjectName(oot(ja.getJSONObject(i).get("changeItem")));
                f.setBeforeContent(oot(ja.getJSONObject(i).get("changeBefore")));
                f.setAfterContent(oot(ja.getJSONObject(i).get("changeAfter")));
                fList.add(f);
            }
            qd.setChangeRecordsList(fList);
        }
    }

    private static void putZzsyr(JSONArray ja, QccCompany c) {
        if (!ja.isEmpty()) {
            List<Zzsyr> fList = new ArrayList<>();
            for (int i = 0; i < ja.size(); i++) {
                Zzsyr f = new Zzsyr();
                f.setSyName(oot(ja.getJSONObject(i).get("syName")));
                f.setSyPerCent(oot(ja.getJSONObject(i).get("syPerCent")));
                fList.add(f);
            }
            c.setZzsyr(fList);
        }
    }

    private static void putFrZwrz(JSONArray ja, QccCompany c) {
        if (!ja.isEmpty()) {
            List<FrZwrz> fList = new ArrayList<>();
            for (int i = 0; i < ja.size(); i++) {
                FrZwrz f = new FrZwrz();
                f.setOpCompany(oot(ja.getJSONObject(i).get("opCompany")));
                f.setOplegalMan(oot(ja.getJSONObject(i).get("oplegalMan")));
                f.setOpNum(oot(ja.getJSONObject(i).get("opNum")));
                f.setOpPost(oot(ja.getJSONObject(i).get("opPost")));
                f.setOpState(oot(ja.getJSONObject(i).get("opState")));
                fList.add(f);
            }
            c.setFrzwrz(fList);
        }
    }

    private static void putFrDwtz(JSONArray ja, QccCompany c) {
        if (!ja.isEmpty()) {
            List<Frdwtz> fList = new ArrayList<>();
            for (int i = 0; i < ja.size(); i++) {
                Frdwtz f = new Frdwtz();
                f.setLmCompany(oot(ja.getJSONObject(i).get("lmCompany")));
                f.setLmConfirmNum(oot(ja.getJSONObject(i).get("lmConfirmNum")));
                f.setLmNum(oot(ja.getJSONObject(i).get("lmNum")));
                f.setLmOpenTime(oot(ja.getJSONObject(i).get("lmOpenTime")));
                f.setLmPerCent(oot(ja.getJSONObject(i).get("lmPerCent")));
                f.setLmstate(oot(ja.getJSONObject(i).get("lmstate")));
                fList.add(f);
            }
            c.setFrdwtz(fList);
        }
    }

    private static void putDwtz(JSONArray ja, QccCompany c) {
        if (!ja.isEmpty()) {
            List<Qyzp> dwtzList = new ArrayList<>();
            for (int i = 0; i < ja.size(); i++) {
                Qyzp q = new Qyzp();
                q.setName(oot(ja.getJSONObject(i).get("iiCompany")));
                q.setOperName(oot(ja.getJSONObject(i).get("iiName")));
                q.setRegistCapi(dealMoney(ja.getJSONObject(i).get("iiCapital")));
                q.setFundedRatio(oot(ja.getJSONObject(i).get("iiPerCent")));
                if (justDatelen(ja.getJSONObject(i).get("iiTime")))
                    q.setStartDate(oot(ja.getJSONObject(i).get("iiTime")));
                q.setStatus(oot(ja.getJSONObject(i).get("iistate")));
                q.setNo(oot(ja.getJSONObject(i).get("iiNum")));
                dwtzList.add(q);
            }
            c.setQyzp(dwtzList);
        }
    }

    private static void putGdxx(QygsDetail qd, JSONArray ja) {
        if (!ja.isEmpty()) {
            List<QygsDetail.Partners> pList = new ArrayList<>();
            for (int i = 0; i < ja.size(); i++) {
                QygsDetail.Partners p = new QygsDetail.Partners();
                p.setStockName(oot(ja.getJSONObject(i).get("hiName")));
                p.setStockPercent(oot(ja.getJSONObject(i).get("hiRatio")));
                p.setShouldCapi(String.valueOf(dealMoney(oot(ja.getJSONObject(i).get("hiContribu")))));
                p.setShoudDate(oot(ja.getJSONObject(i).get("hiTime")));
                p.setStockType(oot(ja.getJSONObject(i).get("hiType")));
                p.setRealCapi(String.valueOf(dealMoney(oot(ja.getJSONObject(i).get("hiRealPay")))));
                pList.add(p);
            }
            qd.setPartnersList(pList);
        }
    }

    private static void putZyry(QygsDetail qd, JSONArray ja) {
        if (!ja.isEmpty()) {
            List<QygsDetail.Employees> elList = new ArrayList<>();
            for (int i = 0; i < ja.size(); i++) {
                QygsDetail.Employees el = new QygsDetail.Employees();
                el.setJob(oot(ja.getJSONObject(i).get("scPosition")));
                el.setName(oot(ja.getJSONObject(i).get("scName")));
                el.setSciUrl(oot(ja.getJSONObject(i).get("sciUrl")));
                el.setSciName(oot(ja.getJSONObject(i).get("sciName")));
                el.setSciPath(oot(ja.getJSONObject(i).get("sciPath")));
                elList.add(el);
            }
            qd.setEmployeesList(elList);
        }
    }

    private static void putBase(QygsDetail qd, JSONObject jo, QccCompany c, StringBuffer sb) {
        c.setTel(oot(jo.get("companyTel")));
        sb.append(oot(jo.get("companyTel")));
        c.setEmail(oot(jo.get("companyEmail")));
        qd.setOperName(oot(jo.get("legalMan")));
        qd.setRegistCapi(dealMoney(jo.get("registerMoney")));
        if (justDatelen(jo.get("registerTime")))
            qd.setStartDate(dealDate(jo.get("registerTime").toString()));
        qd.setCreditCode(oot(jo.get("creditCode")));
        qd.setNo(oot(jo.get("registerNum")));
        qd.setOrgNo(oot(jo.get("OrganizationCode")));
        qd.setEconKind(oot(jo.get("companyType")));
        qd.setStatus(dealStatus(jo.get("businessState")));
        qd.setIndustry(oot(jo.get("industry")));
        qd.setTeamEnd(oot(jo.get("businessTimeout")));
        if (justDatelen(jo.get("confirmTime")))
            qd.setCheckDate(oot(jo.get("confirmTime")));
        qd.setEnglishName(oot(jo.get("englishName")));
        qd.setOriginalName(oot(jo.get("usedName")));
        qd.setBelongOrg(oot(jo.get("registOrgan")));
        qd.setAddress(oot(jo.get("registerAddress")));
        qd.setScope(oot(jo.get("businessScope")));
        qd.setRealCapi(dealMoney(jo.get("indeedMoney")));
        if (jo.has("stock") && jo.getJSONObject("stock") != null) {
            qd.setStockType(oot(jo.getJSONObject("stock").get("plate")));
            qd.setStockNumber(oot(jo.getJSONObject("stock").get("stockNum")));
        }
        qd.setPersonNum(oot(jo.get("manNum")));
        qd.setCbPersonNum(oot(jo.get("securityNum")));
        qd.setOrgType(oot(jo.get("OrganizationType")));
        qd.setProvince(oot(jo.get("companyProvince")));
    }
}