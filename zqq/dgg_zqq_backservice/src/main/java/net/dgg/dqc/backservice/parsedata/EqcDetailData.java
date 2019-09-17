package net.dgg.dqc.backservice.parsedata;

import net.dgg.dqc.backservice.constant.PTConst;
import net.dgg.dqc.backservice.entity.parse.*;
import net.dgg.dqc.backservice.entity.parse.qyfj.ContactInfo;
import net.dgg.dqc.backservice.entity.parse.qygs.QygsDetail;
import net.dgg.dqc.backservice.entity.parse.qynb.QynbDetail;
import net.dgg.dqc.backservice.utils.BaseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static net.dgg.dqc.backservice.parsedata.QccDetailData.dealPhoneNo;

/**
 *  Created by jiangsh on 2018/6/5 13:49
 */
public class EqcDetailData extends BaseUtil {

    private static final Logger logger = LoggerFactory.getLogger(EqcDetailData.class);

    /**
     * 转换易企查详情数据
     * @param o
     * @return
     */
    public static QccCompany conver(Object o) {
        QccCompany c = new QccCompany();
        if (o != null) {
            try {
                StringBuffer sb = new StringBuffer();
                JSONObject jo = JSONObject.fromObject(o.toString());
                QygsDetail qd = new QygsDetail(); //企业工商信息
                List<ContactInfo> ciList = new ArrayList<>();
                ContactInfo ci = new ContactInfo();

                c.setCompanyId(oot(jo.get("url"), PTConst.EQC_WEBSITE_URL, PTConst.TARGET_PREFIX, PTConst.HTML, PTConst.TARGET_SUFFIX));
                c.setName(oot(jo.get("company")));
                c.setLogo(oot(jo.get("logo")));
                c.setUpdateTime(curretTime());

                //基本信息
                base(qd, jo, ci, sb, c);

                //主要人员
                if (jo.has("zhuyao_renyuan")) {
                    JSONArray zhuyao_renyuan = jo.getJSONArray("zhuyao_renyuan");
                    if (!zhuyao_renyuan.isEmpty())
                        zhuyao_renyuanDeal(qd, zhuyao_renyuan);
                }

                //股东信息
                if (jo.has("gudong_xinxi")) {
                    JSONArray gudong_xinxi = jo.getJSONArray("gudong_xinxi");
                    if (!gudong_xinxi.isEmpty())
                        gudong_xinxiDeal(qd, gudong_xinxi);
                }

                //对外投资
                if (jo.has("duiwai_touzi")) {
                    JSONArray duiwai_touzi = jo.getJSONArray("duiwai_touzi");
                    if (!duiwai_touzi.isEmpty())
                        duiwai_touziDeal(c, duiwai_touzi);
                }

                //变更记录
                if (jo.has("biangeng_jilu")) {
                    JSONArray biangeng_jilu = jo.getJSONArray("biangeng_jilu");
                    if (!biangeng_jilu.isEmpty())
                        biangeng_jiluDeal(qd, biangeng_jilu);
                }

                //企业年报
                List<QynbDetail> qynbList = new ArrayList<>();
                for (int i = 2013; i < 2017; i++) {
                    final QynbDetail qynbDetail = qynbDeal(String.valueOf(i), jo, sb);
                    if (qynbDetail != null)
                        qynbList.add(qynbDetail);
                }
                c.setQynbDetail(qynbList);

                //法院公告
                if (jo.has("fayuan_gonggao")) {
                    JSONArray fayuan_gonggao = jo.getJSONArray("fayuan_gonggao");
                    if (!fayuan_gonggao.isEmpty())
                        fayuan_gonggaoDeal(c, fayuan_gonggao);
                }

                //失信信息
                if (jo.has("shixin_xinxi")) {
                    JSONArray shixin_xinxi = jo.getJSONArray("shixin_xinxi");
                    if (!shixin_xinxi.isEmpty())
                        shixin_xinxiDeal(c, shixin_xinxi);
                }

                //商标信息
                if (jo.has("shangbiao_xinxi")) {
                    JSONArray shangbiao_xinxi = jo.getJSONArray("shangbiao_xinxi");
                    if (!shangbiao_xinxi.isEmpty())
                        shangbiao_xinxiDeal(c, shangbiao_xinxi);
                }

                //专利信息
                if (jo.has("zhuanli_xinxi")) {
                    JSONArray zhuanli_xinxi = jo.getJSONArray("zhuanli_xinxi");
                    if (!zhuanli_xinxi.isEmpty())
                        zhuanli_xinxiDeal(c, zhuanli_xinxi);
                }

                //域名信息（公司网站信息）
                if (jo.has("yuming_beian")) {
                    JSONArray yuming_beian = jo.getJSONArray("yuming_beian");
                    if (!yuming_beian.isEmpty())
                        yuming_beianDeal(c, yuming_beian);
                }
                dealPhoneNo(sb.toString(), ci, c);
                ciList.add(ci);
                qd.setContactInfoList(ciList);

                c.setQygsDetail(qd);
            } catch (Exception e) {
                saveError(c, e, "EqcDetailData");
                logger.error("出现异常情况", e.getMessage());
            }
        }
        return c;
    }

    protected static void zhuanli_xinxiDeal(QccCompany c, JSONArray ja) {
        List<Zlxq> list = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Zlxq g = new Zlxq();
            JSONObject obj = ja.getJSONObject(i);
            g.setiPCDesc(oot(obj.get("patentDetail")));
            g.setApplicationNumber(oot(obj.get("patentApplyNo")));
            g.setTitle(oot(obj.get("title")));
            if (justDatelen(obj.get("patentPubDate")))
                g.setPiApplyAnnounceDate(oot(obj.get("patentPubDate")));
            g.setAbstracts(oot(obj.get("patentType")));
            g.setAssigneestring(c.getName());
            g.setUrl(c.getCompanyId());

            if (obj.has("detail_data")) {
                JSONObject ws = obj.getJSONObject("detail_data");
                if (!ws.isNullObject()) {
                    if (justDatelen(obj.get("appDate")))
                        g.setApplicationDate(oot(ws.get("appDate")));
                    g.setPublicationNumber(oot(ws.get("patentPubNo")));
                    g.setAgency(oot(ws.get("agencyName")));
                    g.setPublicationDate(oot(ws.get("patentPubDate")));
                    g.setPiInventName(oot(ws.get("patentName")));
                    g.setInventorString(oot(ws.get("patentInventor")));
                    g.setLegalStatusDesc(oot(ws.get("lprs")));
                }
            }
            list.add(g);
        }
        c.setZlxq(list);
    }

    private static void yuming_beianDeal(QccCompany c, JSONArray ja) {
        List<Gswz> gList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Gswz g = new Gswz();
            JSONObject obj = ja.getJSONObject(i);

            g.setCompanyName(oot(obj.get("webRecordNum")));
            g.setYuMing(oot(obj.get("ym")));
            g.setBeiAn(oot(obj.get("ztBaxh")));
            if (justDatelen(obj.get("webICPDate")))
                g.setsDate(oot(obj.get("webICPDate")));
            g.setKeyNo(oot(obj.get("idx")));

            if (obj.has("webWebSite")) {
                JSONArray ws = obj.getJSONArray("webWebSite");
                if (!ws.isEmpty()) {
                    g.setHomeSite(ws.get(0).toString());
//               g.setHomeSite2(ws.get(1));
                }
                gList.add(g);
            }
        }
        c.setGswz(gList);
    }

    protected static void shangbiao_xinxiDeal(QccCompany c, JSONArray ja) {
        List<SbDetails> sdList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            SbDetails sd = new SbDetails();
            JSONObject obj = ja.getJSONObject(i);

            sd.setName(oot(obj.get("tmName")));
            sd.setIntCls(oot(obj.get("trademarkType")));
            sd.setStatus(oot(obj.get("tmStatus")));
            sd.setRegNo(oot(obj.get("tmRegNo")));
            if (justDatelen(obj.get("tmApplyDate")))
                sd.setAppDate(oot(obj.get("tmApplyDate")));
            sd.setId(oot(obj.get("idx")));
            sd.setUrl(c.getCompanyId());
            sd.setApplicantCn(c.getName());

            JSONObject d = obj.getJSONObject("detail_data");
            if (!d.isNullObject()) {
                if (d.get("ApplicantCn") != null)
                sd.setApplicantCn(oot(d.get("ApplicantCn")));
                if (justDatelen(d.get("announcementDate")))
                    sd.setAnnouncementDate(oot(d.get("announcementDate")));
                if (justDatelen(d.get("privateDateStart")))
                    sd.setRegDate(oot(d.get("privateDateStart")));
                if (justDatelen(d.get("privateDateEnd")))
                    sd.setHouQiZhiDingDate(oot(d.get("privateDateEnd")));
                sd.setAgent(oot(d.get("agent")));
            }
            sdList.add(sd);
        }
        c.setSbDetails(sdList);
    }

    private static void shixin_xinxiDeal(QccCompany c, JSONArray ja) {
        List<Sxbzxr> sxbzxrList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Sxbzxr sxbzxr = new Sxbzxr();
            JSONObject obj = ja.getJSONObject(i);
            if (justDatelen(obj.get("regDate")))
                sxbzxr.setLiandate(dealDate(obj.get("regDate").toString()));
            sxbzxr.setYiwu(oot(obj.get("duty")));
            sxbzxr.setExecutegov(oot(obj.get("gistUnit")));
            sxbzxr.setId(oot(obj.get("id")));
            sxbzxr.setExecuteno(oot(obj.get("gistId")));
            sxbzxr.setActionremark(oot(obj.get("performance")));
            sxbzxrList.add(sxbzxr);
        }
        c.setSxbzxr(sxbzxrList);
    }

    private static void fayuan_gonggaoDeal(QccCompany c, JSONArray ja) {
        List<Fygg> fyggList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            JSONObject o = ja.getJSONObject(i);
            Fygg fygg = new Fygg();
            fygg.setCourt(oot(o.get("courtcode")));
            if (justDatelen(o.get("publishdate")))
                fygg.setSubmitDate(oot(o.get("publishdate")));
            fygg.setParty(oot(o.get("party1")));

            List<Fygg.NameKeyNoCollection> nnList = new ArrayList<>();
            Fygg.NameKeyNoCollection nn = new Fygg.NameKeyNoCollection();
            nn.setName(oot(o.get("party2")));
            nn.setKeyNo(oot(o.get("id")));
            nnList.add(nn);
            fygg.setNameKeyNoColl(nnList);

            JSONObject jo1 = o.getJSONObject("detail_data");
            if (!jo1.isNullObject()) {
                fygg.setContent(oot(jo1.get("content")));
                if (justDatelen(jo1.get("publishdate")))
                    fygg.setPublishDate(oot(jo1.get("publishdate")));
            }

            fyggList.add(fygg);
        }
        c.setFygg(fyggList);
    }

    private static QynbDetail qynbDeal(String year, JSONObject jo, StringBuffer sb) {
        QynbDetail qynb = new QynbDetail();
        final String report = "report_";
        JSONObject o = jo.getJSONObject(report.concat(year));
        if (!o.isNullObject()) {
            if (o.has("changeRecord")) {
                JSONArray ja1 = o.getJSONArray("changeRecord"); //企业年报-变更信息
                if (!ja1.isEmpty())
                    changeRecord(qynb, ja1);
            }

            qynb.setYear(oot(o.get("reportYear")));

            if (o.has("shareholder")) {
                JSONArray ja2 = o.getJSONArray("shareholder"); //企业年报-股东及出资信息
                if (!ja2.isEmpty())
                    shareholder(qynb, ja2);
            }

            if (o.has("detail")) {
                JSONObject jo1 = o.getJSONObject("detail");
                if (!jo1.isNullObject())
                    detail(qynb, jo1, sb);
            }
        }
        return qynb;
    }

    private static void detail(QynbDetail qynb, JSONObject jo1, StringBuffer sb) {
        QynbDetail.BasicInfoData bid = new QynbDetail.BasicInfoData();
        bid.setStatus(oot(jo1.get("regStatus")));
        bid.setEmployeeCount(oot(jo1.get("employeeNum")));
        bid.setCompanyName(oot(jo1.get("companyName")));
        bid.setPostCode(oot(jo1.get("postcode")));
        bid.setHasNewStockOrByStock(oot(jo1.get("investor")));
        bid.setRegNo(oot(jo1.get("creditCode")));
        String ph = oot(jo1.get("phoneNumber"));
        if (org.apache.commons.lang.StringUtils.isNotEmpty(ph)) {
            bid.setContactNo(ph);
            if (!sb.toString().contains(ph)) {
                if (sb.toString().length() == 0) sb.append(ph);
                else sb.append("、").append(ph);
            }
        }
        bid.setAddress(oot(jo1.get("postalAddress")));
        bid.setHasWebSite(oot(jo1.get("icp")));
        bid.setEmailAddress(oot(jo1.get("email")));
        bid.setIsStockRightTransfer(oot(jo1.get("ratio")));

        QynbDetail.AssetsData ad = new QynbDetail.AssetsData();
        ad.setTotalAssets(oot(jo1.get("totalAssets")));
        ad.setTotalProfit(oot(jo1.get("totalProfit")));
        ad.setTotalLiabilities(oot(jo1.get("totalLiability")));
        ad.setGrossTradingIncome(oot(jo1.get("totalSales")));
        ad.setNetProfit(oot(jo1.get("retainedProfit")));
        ad.setTotalTaxAmount(oot(jo1.get("totalTax")));
        if (jo1.get("totalEquity") != null)
        ad.setTotalOwnersEquity(oot(jo1.get("totalEquity")));
        ad.setMainBusinessIncome(oot(jo1.get("primeBusProfit")));

        qynb.setBasicInfoData(bid);
        qynb.setAssetsData(ad);
    }

    private static void shareholder(QynbDetail qynb, JSONArray ja2) {
        List<QynbDetail.PartnerList> plList = new ArrayList<>();
        for (int i = 0; i < ja2.size(); i++) {
            QynbDetail.PartnerList pl = new QynbDetail.PartnerList();
            JSONObject obj = ja2.getJSONObject(i);
            pl.setShouldCapi(oot(obj.get("subscribeAmount")));
            pl.setShouldType(oot(obj.get("subscribeType")));
            if (justDatelen(obj.get("subscribeTime")))
            pl.setShouldDate(dealDate(obj.get("subscribeTime").toString()));
            pl.setRealType(oot(obj.get("paidType")));
            pl.setName(oot(obj.get("investorName")));
            if (justDatelen(obj.get("paidTime")))
            pl.setRealDate(dealDate(obj.get("paidTime").toString()));
            pl.setRealCapi(oot(obj.get("paidAmount")));
            plList.add(pl);
        }
        qynb.setPartner(plList);
    }

    private static void changeRecord(QynbDetail qynb, JSONArray ja1) {
        List<QynbDetail.ChangeList> clList = new ArrayList<>();
        for (int i = 0; i < ja1.size(); i++) {
            QynbDetail.ChangeList cl = new QynbDetail.ChangeList();
            JSONObject obj = ja1.getJSONObject(i);
            cl.setAfter(oot(obj.get("contentAfter")));
            cl.setBefore(oot(obj.get("contentBefore")));
            cl.setChangeName(oot(obj.get("changeItem")));
            if (justDatelen(obj.get("changeTime")))
            cl.setChangeDate(oot(obj.get("changeTime")));
            clList.add(cl);
        }
        qynb.setChangeList(clList);
    }

    private static void biangeng_jiluDeal(QygsDetail qd, JSONArray ja) {
        List<QygsDetail.ChangeRecords> bgjlList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            JSONObject o = ja.getJSONObject(i);
            QygsDetail.ChangeRecords p = new QygsDetail.ChangeRecords();
            if (justDatelen(o.get("changeTime")))
                p.setChangeDate(oot(o.get("changeTime")));
            p.setProjectName(oot(o.get("changeItem")));
            p.setBeforeContent(oot(o.get("contentBefore")));
            p.setAfterContent(oot(o.get("contentAfter")));
            bgjlList.add(p);
        }
        qd.setChangeRecordsList(bgjlList);
    }

    private static void duiwai_touziDeal(QccCompany c, JSONArray ja) {
        List<Qyzp> qyzpList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            JSONObject o = ja.getJSONObject(i);
            Qyzp p = new Qyzp();
            if (justDatelen(o.get("corpYear")))
            p.setStartDate(dealDate(o.get("corpYear").toString()));
            p.setEconKind(oot(o.get("corpIndustry")));
            p.setNo(oot(o.get("corpKey")));
            p.setStatus(oot(o.get("corpStatus")));
            p.setName(oot(o.get("corpName")));
            p.setCreditCode(oot(o.get("id")));
            p.setRegistCapi(dealMoney(o.get("corpRegistCapi")));
            p.setOperName(oot(o.get("corpOperName")));
            p.setFundedRatio(oot(o.get("equityRatio")));
            qyzpList.add(p);
        }
        c.setQyzp(qyzpList);
    }

    private static void gudong_xinxiDeal(QygsDetail qd, JSONArray ja) {
        List<QygsDetail.Partners> pList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            JSONObject o = ja.getJSONObject(i);
            QygsDetail.Partners p = new QygsDetail.Partners();
            p.setStockName(oot(o.get("chName")));
            p.setStockType(oot(o.get("chType")));
            p.setRealCapi(oot(o.get("amount")));
            if (justDatelen(o.get("chShouldTime")))
            p.setShoudDate(oot(o.get("chShouldTime")));
            p.setShouldCapi(oot(o.get("chShouldCapi")));
            pList.add(p);
        }
        qd.setPartnersList(pList);
    }

    private static void zhuyao_renyuanDeal(QygsDetail qd, JSONArray ja) {
        List<QygsDetail.Employees> eList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            JSONObject o = ja.getJSONObject(i);
            QygsDetail.Employees emp = new QygsDetail.Employees();
            emp.setName(oot(o.get("empName")));
            emp.setJob(oot(o.get("empJob")));
            eList.add(emp);
        }
        qd.setEmployeesList(eList);
    }

    protected static void base(QygsDetail qd, JSONObject jo, ContactInfo ci, StringBuffer sb, QccCompany c) throws Exception{
        if (jo.get("province") != null)
            qd.setProvince(oot(jo.get("province")));

        qd.setName(oot(jo.get("company")));
        if (jo.get("cell") != null) {
            sb.append(jo.get("cell"));
            c.setTel(oot(jo.get("cell")));
        }
        ci.setEmail(oot(jo.get("email")));
        c.setEmail(oot(jo.get("email")));
        qd.setCreditCode(oot(jo.get("xinyong_daima")));
        qd.setNo(oot(jo.get("gongshang_zhuce")));
        qd.setOrgNo(oot(jo.get("jigou_haima")));
        qd.setRegistCapi(dealMoney(jo.get("zhuce_ziben")));
        if (justDatelen(jo.get("chengli_riqi")))
        qd.setStartDate(dealDate(jo.get("chengli_riqi").toString()));
        if (justDatelen(jo.get("hezhun_riqi")))
        qd.setCheckDate(dealDate(jo.get("hezhun_riqi").toString()));
        qd.setIndustry(oot(jo.get("suoshu_hangye")));
        qd.setEconKind(oot(jo.get("dengji_leixing")));
        qd.setStatus(dealStatus(jo.get("dengji_zhuangtai")));
        qd.setBelongOrg(oot(jo.get("dengji_jiguan")));
        qd.setAddress(oot(jo.get("dengji_zhusuo")));
        qd.setScope(oot(jo.get("jingying_fanwei")));
        qd.setOperName(oot(jo.get("faren")));
        qd.setTermStart(oot(jo.get("jingying_qixian_b")));

        if (jo.get("jingying_qixian_e") != null)
        qd.setTeamEnd(oot(jo.get("jingying_qixian_e")));
    }

}
