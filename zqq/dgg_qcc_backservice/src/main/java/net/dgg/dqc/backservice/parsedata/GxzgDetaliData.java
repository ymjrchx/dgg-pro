package net.dgg.dqc.backservice.parsedata;

import net.dgg.dqc.backservice.entity.parse.*;
import net.dgg.dqc.backservice.entity.parse.qyfj.ContactInfo;
import net.dgg.dqc.backservice.entity.parse.qygs.QygsDetail;
import net.dgg.dqc.backservice.entity.parse.qynb.QynbDetail;
import net.dgg.dqc.backservice.utils.BaseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 公信中国详情数据
 * Created by jiangsh on 2018/6/7 09:17
 */
public class GxzgDetaliData extends BaseUtil {

    private static final Logger logger = LoggerFactory.getLogger(GxzgDetaliData.class);
    /**
     * 转换公信中国详情数据
     * @param o
     * @return
     */
    public static QccCompany conver(Object o) {

        QccCompany c = new QccCompany();
        if (o != null) {
            JSONObject jo = JSONObject.fromObject(o.toString());
//            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
            StringBuffer sb = new StringBuffer();

            QygsDetail qd = new QygsDetail(); //企业工商信息
            List<ContactInfo> ciList = new ArrayList<>();
            ContactInfo ci = new ContactInfo();
            c.setCompanyId(oot(jo.get("url")));
            //base info
            if (jo.has("base")) {
                JSONObject jbobj = jo.getJSONObject("base");
                if (!jbobj.isNullObject())
                    base(c, qd, jbobj, sb, ci);
            }

            //股东
            if (jo.has("investorList")) {
                JSONArray investorList = jo.getJSONArray("investorList");
                if (!investorList.isEmpty())
                    investorListDeal(qd, investorList);
            }

            //高管(主要人员)
            if (jo.has("staffList")) {
                JSONArray staffList = jo.getJSONArray("staffList");
                if (!staffList.isEmpty())
                    staffListDeal(qd, staffList);
            }

            //分支机构
            if (jo.has("branchList")) {
                JSONArray branchList = jo.getJSONArray("branchList");
                if (!branchList.isEmpty())
                    branchListDeal(qd, branchList);
            }

            //投资公司
            if (jo.has("investList")) {
                JSONArray investList = jo.getJSONArray("investList");
                if (!investList.isEmpty())
                    investListDeal(c, investList);
            }

            //变更信息
            if (jo.has("changeInfoList")) {
                JSONArray changeInfoList = jo.getJSONArray("changeInfoList");
                if (!changeInfoList.isEmpty())
                    changeInfoListDeal(qd, changeInfoList);
            }

            //法院公告
            if (jo.has("courtAnnouncementList")) {
                JSONArray courtAnnouncementList = jo.getJSONArray("courtAnnouncementList");
                if (!courtAnnouncementList.isEmpty())
                    courtAnnouncementListDeal(c, courtAnnouncementList);
            }

            //开庭公告
            if (jo.has("BulletinCourtList")) {
                JSONArray bulletinCourtList = jo.getJSONArray("BulletinCourtList");
                if (!bulletinCourtList.isEmpty())
                    bulletinCourtListDeal(c, bulletinCourtList);
            }

            //经营异常
            if (jo.has("abnoinfoList")) {
                JSONArray abnoinfoList = jo.getJSONArray("abnoinfoList");
                if (!abnoinfoList.isEmpty())
                    abnoinfoListDeal(c, abnoinfoList);
            }

            //商标信息
            if (jo.has("tradeMarkList")) {
                JSONArray tradeMarkList = jo.getJSONArray("tradeMarkList");
                if (!tradeMarkList.isEmpty())
                    tradeMarkListDeal(c, tradeMarkList);
            }

            //专利详情信息
            if (jo.has("patentList")) {
                JSONArray patentList = jo.getJSONArray("patentList");
                if (!patentList.isEmpty())
                    patentListDeal(c, patentList);
            }

            //著作查询
            if (jo.has("copyrightList")) {
                JSONArray copyrightList = jo.getJSONArray("copyrightList");
                if (!copyrightList.isEmpty())
                    copyrightListDeal(c, copyrightList);
            }

            //网站信息
            if (jo.has("getIcpList")) {
                JSONArray getIcpList = jo.getJSONArray("getIcpList");
                if (!getIcpList.isEmpty())
                    getIcpListDeal(c, getIcpList);
            }

            //产品查询
            if (jo.has("productInfoList")) {
                JSONArray productInfoList = jo.getJSONArray("productInfoList");
                if (!productInfoList.isEmpty())
                    productInfoListDeal(c, productInfoList);
            }

            //年报信息
            if (jo.has("annualList")) {
                JSONArray annualList = jo.getJSONArray("annualList");
                if (!annualList.isEmpty())
                    annualListDeal(c, annualList, sb);
            }

            if (sb.toString().length() > 0)
                ci.setPhoneNumber(sb.toString());
            ciList.add(ci);
            qd.setContactInfoList(ciList);

            c.setQygsDetail(qd);
        }
        return c;
    }

    private static void productInfoListDeal(QccCompany c, JSONArray ja) {
        List<Gscp> gscpList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Gscp gscp = new Gscp();
            JSONObject o = ja.getJSONObject(i);
            gscp.setDomain(oot(o.get("classes").toString()));
            gscp.setTags(oot(o.get("filterName").toString()));
            gscp.setImageUrl(oot(o.get("icon").toString()));
            gscp.setCategory(oot(o.get("type").toString()));
            gscp.setDescription(oot(o.get("brief").toString()));
            gscp.setName(oot(o.get("name").toString()));
            gscpList.add(gscp);
        }
        c.setGscp(gscpList);
    }

    private static void getIcpListDeal(QccCompany c, JSONArray ja) {
        List<Gswz> gswzList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Gswz gswz = new Gswz();
            JSONObject o = ja.getJSONObject(i);
            gswz.setHomeSite(oot(o.getJSONArray("webSite").get(0).toString()));
            gswz.setSDate(oot(o.get("examineDate").toString()));
            gswz.setTypeName(oot(o.get("companyType").toString()));
            gswz.setYuMing(oot(o.get("ym").toString()));
            gswz.setTitle(oot(o.get("webName").toString()));
            gswz.setCompanyName(oot(o.get("companyName").toString()));
            gswz.setBeiAn(oot(o.get("liscense").toString()));
            gswzList.add(gswz);
        }
        c.setGswz(gswzList);
    }

    private static void copyrightListDeal(QccCompany c, JSONArray ja) {
        List<Zzqrz> zzqrzList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Zzqrz zzqrz = new Zzqrz();
            JSONObject o = ja.getJSONObject(i);
            zzqrz.setRegisterAperDate(o.get("regtime").toString());
            zzqrz.setOwner(o.get("authorNationality").toString());
            zzqrz.setPublishDate(o.get("publishDate").toString());
            zzqrz.setRegisterNo(o.get("regnum").toString());
            zzqrz.setCategory(o.get("catnum").toString());
            zzqrz.setName(o.get("fullname").toString());
            zzqrz.setVersionNo(o.get("version").toString());
            zzqrz.setShortName(oot(o.get("simplename").toString()));
            zzqrzList.add(zzqrz);
        }
        c.setZzqrz(zzqrzList);
    }

    private static void patentListDeal(QccCompany c, JSONArray ja) {
        List<Zlxq> zlxqList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Zlxq zlxq = new Zlxq();
            JSONObject o = ja.getJSONObject(i);
            zlxq.setIPCList(oot(o.get("mainCatNum")));
            zlxq.setPublicationNumber(oot(o.get("applicationPublishNum")));
            zlxq.setAgency(oot(o.get("agency")));
            zlxq.setInventorString(oot(o.get("inventor")));
            zlxq.setAgent(oot(o.get("agent")));
            zlxq.setPublicationDate(oot(o.get("applicationPublishTime")));
            zlxq.setApplicationNumber(oot(o.get("patentNum")));
            zlxq.setTitle(oot(o.get("patentName")));
            zlxq.setAbstract(oot(o.get("abstracts")));
            zlxq.setApplicationDate(oot(o.get("applicationTime")));
            zlxq.setKindCodeDesc(oot(o.get("patentType")));
            zlxq.setAssigneestring(oot(o.get("applicantName")));
            zlxqList.add(zlxq);
        }
        c.setZlxq(zlxqList);
    }

    private static void abnoinfoListDeal(QccCompany c, JSONArray ja) {}

    private static void bulletinCourtListDeal(QccCompany c, JSONArray ja) {
        List<Ktgg> ktggList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Ktgg ktgg = new Ktgg();
            JSONObject o = ja.getJSONObject(i);

            ktgg.setCase_Reason(oot(o.get("caseReason").toString()));
            ktgg.setExecute_Gov(oot(o.get("court").toString()));
            ktgg.setExecute_Unite(oot(o.get("courtroom")));
            ktgg.setOpen_Time(oot(o.get("startDate").toString()));
            ktgg.setCase_No(oot(o.get("caseNo").toString()));

            JSONArray ja1 = o.getJSONArray("plaintiff");
            if (!ja1.isEmpty()) {
                List<Ktgg.Prosecutor> pList = new ArrayList<>();
                for (int j = 0; j < ja1.size(); j++) {
                    Ktgg.Prosecutor nk = new Ktgg.Prosecutor();
                    nk.setKeyNo(ja1.getJSONObject(j).get("id").toString());
                    nk.setName(ja1.getJSONObject(j).get("name").toString());
                    pList.add(nk);
                }
                ktgg.setProsecutor(pList);
            }
            JSONArray ja2 = o.getJSONArray("defendant");
            if (!ja2.isEmpty()) {
                List<Ktgg.Defendant> pList = new ArrayList<>();
                for (int j = 0; j < ja2.size(); j++) {
                    Ktgg.Defendant nk = new Ktgg.Defendant();
                    nk.setKeyNo(ja2.getJSONObject(j).get("id").toString());
                    nk.setName(ja2.getJSONObject(j).get("name").toString());
                    pList.add(nk);
                }
                ktgg.setDefendant(pList);
            }
            ktggList.add(ktgg);
        }
        c.setKtgg(ktggList);
    }

    private static void courtAnnouncementListDeal(QccCompany c, JSONArray ja) {
        List<Fygg> fyggList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Fygg fygg = new Fygg();
            JSONObject o = ja.getJSONObject(i);

            JSONArray ja1 = o.getJSONArray("companyList");
            if (!ja1.isEmpty()) {
                List<Fygg.NameKeyNoCollection> nkList = new ArrayList<>();
                for (int j = 0; j < ja1.size(); j++) {
                    Fygg.NameKeyNoCollection nk = new Fygg.NameKeyNoCollection();
                    nk.setKeyNo(ja1.getJSONObject(j).get("id").toString());
                    nk.setName(ja1.getJSONObject(j).get("name").toString());
                    nkList.add(nk);
                }
                fygg.setNameKeyNoColl(nkList);
            }
            fygg.setCourt(o.get("courtcode").toString());
            fygg.setContent(o.get("content").toString());
            fygg.setSubmitDate(o.get("publishdate").toString());
            fygg.setProvince(o.get("province").toString());
            fygg.setCategory(o.get("bltntypename").toString());
            fygg.setParty(o.get("party1").toString());
            fyggList.add(fygg);
        }
        c.setFygg(fyggList);
    }

    private static void base(QccCompany c, QygsDetail qd, JSONObject o, StringBuffer sb, ContactInfo ci) {
        qd.setUpdatedDate(oot(o.get("updatetime").toString()));
        qd.setTermStart(oot(o.get("fromTime").toString()));
        qd.setStockType(oot(o.get("type").toString()));
        qd.setCategoryScore(ooz(o.get("categoryScore").toString()));
        qd.setNo(oot(o.get("regNumber").toString()));

        qd.setPercentileScore(ooz(o.get("percentileScore").toString()));
        sb.append(oot(o.get("phoneNumber").toString()));
        ci.setPhoneNumber(oot(o.get("phoneNumber").toString()));
        ci.setWebSiteUrl(oot(o.get("websiteList").toString()));

        qd.setRegistCapi(dealMoney(o.get("regCapital")));
        qd.setBelongOrg(oot(o.get("regInstitute").toString()));
        qd.setName(oot(o.get("name").toString()));
        qd.setAddress(oot(o.get("regLocation").toString()));
        qd.setIndustry(oot(o.get("industry").toString()));
        qd.setCheckDate(oot(o.get("approvedDate").toString()));
        qd.setScope(oot(o.get("businessScope").toString()));
        qd.setOrgNo(oot(o.get("orgNumber").toString()));
        qd.setStatus(oot(o.get("regStatus").toString()));
        qd.setStartDate(oot(o.get("estiblishTime").toString()));
        qd.setOperName(oot(o.get("legalPersonName").toString()));
        qd.setTeamEnd(oot(o.get("toTime").toString()));
        qd.setEconKind(oot(o.get("companyOrgType").toString()));
        qd.setCreditCode(oot(o.get("creditCode").toString()));
    }

    private static void investorListDeal(QygsDetail qd, JSONArray ja1 ) {
        List<QygsDetail.Partners> investors = new ArrayList<>();
        for (int i = 0 ; i < ja1.size(); i++) {
            QygsDetail.Partners inv = new QygsDetail.Partners();
            JSONObject jo1 = ja1.getJSONObject(i);
            inv.setStockType(jo1.get("type").toString());
            inv.setStockName(jo1.get("name").toString());

            JSONArray ja2 = jo1.getJSONArray("capital");
            if (!ja2.isEmpty()) {
                JSONObject jo2 = ja2.getJSONObject(0);
                if (!jo2.isNullObject()) {
                    inv.setShouldCapi(oot(jo2.get("amomon")));
                    inv.setStockPercent(oot(jo2.get("percent").toString()));
                    inv.setShoudDate(oot(jo2.get("time").toString()));
                    inv.setRealCapi(oot(jo2.get("paymet")));
                }
            }
            investors.add(inv);
        }
        qd.setPartnersList(investors);
    }

    private static void staffListDeal(QygsDetail qd, JSONArray ja) {
        List<QygsDetail.Employees> staffs = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            QygsDetail.Employees staff = new QygsDetail.Employees();
            JSONObject o = ja.getJSONObject(i);
            staff.setName(o.get("name").toString());
            staff.setJob(o.get("position").toString());
            staffs.add(staff);
        }
        qd.setEmployeesList(staffs);
    }

    private static void branchListDeal(QygsDetail qd, JSONArray ja) {
        List<QygsDetail.Branches> branches = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            QygsDetail.Branches branch = new QygsDetail.Branches();
            JSONObject o = ja.getJSONObject(i);
            branch.setName(o.get("name").toString());
            branch.setCreditCode(o.get("tycId").toString());
            branch.setOperName(o.get("legalPersonName").toString());
            branches.add(branch);
        }
        qd.setBranchesList(branches);
    }

    private static void investListDeal(QccCompany c, JSONArray ja) {
        List<Qyzp> invests = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            Qyzp qyzp = new Qyzp();
            JSONObject o = ja.getJSONObject(i);
            qyzp.setName(o.get("name").toString());
            qyzp.setOperName(o.get("legalPersonName").toString());
            qyzp.setRegistCapi(dealMoney(o.get("amount")));
            qyzp.setFundedRatio(o.get("percent").toString());
            qyzp.setStatus(o.get("regStatus").toString());
            qyzp.setCreditCode(o.get("creditCode").toString());
            qyzp.setEconKind(o.get("orgType").toString());
            qyzp.setStartDate(oot(o.get("estiblishTime")));
            invests.add(qyzp);
        }
        c.setQyzp(invests);
    }

    private static void changeInfoListDeal(QygsDetail qd, JSONArray ja) {
        List<QygsDetail.ChangeRecords> comChanInfos = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            QygsDetail.ChangeRecords comChanInfo = new QygsDetail.ChangeRecords();
            JSONObject o = ja.getJSONObject(i);
            comChanInfo.setProjectName(o.get("changeItem").toString());
            comChanInfo.setChangeDate(o.get("changeTime").toString());
            comChanInfo.setBeforeContent(o.get("contentBefore").toString());
            comChanInfo.setAfterContent(o.get("contentAfter").toString());
            comChanInfos.add(comChanInfo);
        }
        qd.setChangeRecordsList(comChanInfos);
    }

    private static void tradeMarkListDeal(QccCompany c, JSONArray ja) {
        List<SbDetails> tms = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            SbDetails tm = new SbDetails();
            JSONObject o = ja.getJSONObject(i);
            tm.setId(o.get("id").toString());
            tm.setIntCls(o.get("intCls").toString());
            tm.setName(o.get("tmName").toString());
            tm.setImageUrl(o.get("tmPic").toString());
            tm.setStatus(o.get("status").toString());
            tm.setRegNo(o.get("regNo").toString());
            tm.setAppDate(o.get("appDate").toString());
            tm.setApplicantCn(o.get("applicantCn").toString());
            tms.add(tm);
        }
        c.setSbDetails(tms);
    }

    private static void annualListDeal(QccCompany c, JSONArray ja, StringBuffer sb) {
        List<QynbDetail> annuRepYears = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            QynbDetail ary = new QynbDetail();
            JSONObject o = ja.getJSONObject(i);
            ary.setCompanyId(o.get("companyId").toString());//

            JSONObject bio = o.getJSONObject("baseInfo"); //基本信息
            if (!bio.isNullObject()) {
                ary.setYear(oot(bio.get("reportYear").toString()));
                QynbDetail.BasicInfoData bid = new QynbDetail.BasicInfoData();
                bid.setRegNo(oot(bio.get("regNumber").toString()));
                bid.setCompanyName(oot(bio.get("companyName").toString()));
                bid.setOperatorName(oot(bio.get("operatorName").toString()));
                String ph = bio.get("phoneNumber").toString();
                if (StringUtils.isNotEmpty(ph)) {
                    bid.setContactNo(ph);
                    sb.append("、").append(ph);
                }
                bid.setPostCode(oot(bio.get("postcode").toString()));
                bid.setAddress(oot(bio.get("postalAddress").toString()));
                bid.setEmailAddress(oot(bio.get("email").toString()));
                bid.setStatus(oot(bio.get("manageState").toString()));
                bid.setEmployeeCount(oot(bio.get("employeeNum").toString()));
                bid.setCapitalAmount(oot(bio.get("totalAssets").toString()));
                ary.setBasicInfoData(bid);
                QynbDetail.AssetsData ad = new QynbDetail.AssetsData();///////////
                ad.setTotalAssets(oot(bio.get("totalAssets").toString()));
                ad.setTotalOwnersEquity(oot(bio.get("totalEquity").toString()));
                ad.setGrossTradingIncome(oot(bio.get("totalTax").toString()));
                ad.setTotalProfit(oot(bio.get("totalProfit").toString()));
                ad.setMainBusinessIncome(oot(bio.get("primeBusProfit").toString()));
                ad.setNetProfit(oot(bio.get("retainedProfit").toString()));
                ad.setTotalTaxAmount(oot(bio.get("totalSales").toString()));
                ad.setTotalLiabilities(oot(bio.get("totalLiability").toString()));
                ary.setAssetsData(ad);
            }

            JSONArray ja1 = o.getJSONArray("shareholderList"); //基本进出口信用信息-股东及出资信息
            if (!ja1.isEmpty()) {
                List<QynbDetail.PartnerList> partner = new ArrayList<>();
                for (int j = 0; j < ja1.size(); j++) {
                    QynbDetail.PartnerList pl = new QynbDetail.PartnerList();
                    pl.setName(oot(ja1.getJSONObject(j).get("investorName").toString()));
                    pl.setShouldCapi(oot(ja1.getJSONObject(j).get("subscribeAmount").toString()));
                    pl.setShouldDate(oot(ja1.getJSONObject(j).get("subscribeTime").toString()));
                    pl.setShouldType(oot(ja1.getJSONObject(j).get("subscribeType").toString()));
                    pl.setRealCapi(oot(ja1.getJSONObject(j).get("paidAmount").toString()));
                    pl.setRealDate(oot(ja1.getJSONObject(j).get("paidTime").toString()));
                    pl.setRealType(oot(ja1.getJSONObject(j).get("paidType").toString()));
                    pl.setForm(oot(ja1.getJSONObject(j).get("type").toString()));
                    partner.add(pl);
                }
                ary.setPartner(partner);
            }

            JSONArray ja2 = o.getJSONArray("WebSiteList"); //基本进出口信用信息-网站或网店信息
            if (!ja2.isEmpty()) {
                List<QynbDetail.WebSiteList> webSite = new ArrayList<>();
                for (int k = 0; k < ja2.size(); k++) {
                    QynbDetail.WebSiteList ws = new QynbDetail.WebSiteList();
                    ws.setName(oot(ja2.getJSONObject(k).get("name").toString()));
                    ws.setType(oot(ja2.getJSONObject(k).get("webType").toString()));
                    ws.setWebSite(oot(ja2.getJSONObject(k).get("website").toString()));
                    webSite.add(ws);
                }
                ary.setWebSite(webSite);
            }
            annuRepYears.add(ary);
        }
        c.setQynbDetail(annuRepYears);
    }

}
