package net.dgg.dqc.backservice.parsedata;

import net.dgg.dqc.backservice.constant.PTConst;
import net.dgg.dqc.backservice.entity.parse.*;
import net.dgg.dqc.backservice.entity.parse.qygs.QygsDetail;
import net.dgg.dqc.backservice.utils.BaseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 标准程序解析
 * Created by jiangsh on 2018/8/14 13:59
 */
public class StandDetailData extends BaseUtil {

    private static final Logger logger = LoggerFactory.getLogger(StandDetailData.class);

    public static QccCompany conver(Object o) {
        QccCompany c = new QccCompany();
        try {
            if (o != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                StringBuffer sb = new StringBuffer();
                JSONObject jo = JSONObject.fromObject(o.toString());

                c.setCompanyId(oot(jo.get("companyUrl"), PTConst.QCC_WEBSITE_URL, PTConst.TARGET_PREFIX, ".html", PTConst.TARGET_SUFFIX));
                c.setName(oot(jo.get("companyName")));

                QygsDetail qd = new QygsDetail(); //企业工商信息

                JSONObject background = jo.getJSONObject("docs").getJSONObject("background");
                //基本信息
                JSONObject base = background.getJSONObject("baseInfo");
                putBase(qd, base, c);

                //主要人员
                JSONArray staffCount = background.getJSONArray("staffCount");
                putZyry(qd, staffCount);

                //股东信息
                JSONArray holderInfo = background.getJSONArray("holderInfo");
                putGdxx(qd, holderInfo);

                //公司对外投资
                JSONArray inverstInfo = background.getJSONArray("inverstInfo");
                putDwtz(inverstInfo, c);

                //法人对外投资
                JSONArray legalManInverst = background.getJSONArray("legalManInverst");
                putFrDwtz(legalManInverst, c);

                //法人在外任职
                JSONArray legalManOutPost = background.getJSONArray("legalManOutPost");
                putFrZwrz(legalManOutPost, c);

                //最终受益人
                JSONArray shouyiren = background.getJSONArray("shouyiren");
                putZzsyr(shouyiren, c);

                //变更记录
                JSONArray changeInfo = background.getJSONArray("changeInfo");
                putBgjl(changeInfo, qd);

                //年报
                JSONObject reportInfo = background.getJSONObject("reportInfo");
                putQynb(reportInfo, c);

            }

        } catch (Exception e) {

        }
        return c;
    }

    private static void putQynb(JSONObject jo, QccCompany c) {

    }

    private static void putBgjl(JSONArray ja, QygsDetail qd) {
        if (!ja.isEmpty()) {
            List<QygsDetail.ChangeRecords> fList = new ArrayList<>();
            for (int i = 0; i < ja.size(); i++) {
                QygsDetail.ChangeRecords f = new QygsDetail.ChangeRecords();
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
                q.setRegistCapi(Double.valueOf(oot(ja.getJSONObject(i).get("iiCapital"))));
                q.setFundedRatio(oot(ja.getJSONObject(i).get("iiPerCent")));
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
                p.setShouldCapi(oot(ja.getJSONObject(i).get("hiContribu")));
                p.setShoudDate(oot(ja.getJSONObject(i).get("hiTime")));
                p.setStockType(oot(ja.getJSONObject(i).get("hiType")));
                p.setRealCapi(oot(ja.getJSONObject(i).get("hiRealPay")));
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
                elList.add(el);
            }
            qd.setEmployeesList(elList);
        }
    }

    private static void putBase(QygsDetail qd, JSONObject jo, QccCompany c) {
        qd.setOperName(oot(jo.get("legalMan")));
        qd.setRegistCapi(Double.valueOf(oot(jo.get("registerMoney"))));
        qd.setStartDate(oot(jo.get("registerTime")));
        qd.setCreditCode(oot(jo.get("creditCode")));
        qd.setNo(oot(jo.get("registerNum")));
        qd.setOrgNo(oot(jo.get("OrganizationCode")));
        qd.setEconKind(oot(jo.get("companyType")));
        qd.setStatus(oot(jo.get("businessState")));
        qd.setIndustry(oot(jo.get("industry")));
        qd.setTeamEnd(oot(jo.get("businessTimeout")));
        qd.setCheckDate(oot(jo.get("confirmTime")));
        qd.setEnglishName(oot(jo.get("englishName")));
        qd.setOriginalName(oot(jo.get("usedName")));
        qd.setBelongOrg(oot(jo.get("registOrgan")));
        qd.setAddress(oot(jo.get("registerAddress")));
        qd.setScope(oot(jo.get("businessScope")));
        c.setTel(oot(jo.get("companyTel")));
        c.setEmail(oot(jo.get("companyEmail")));
        qd.setRealCapi(Double.valueOf(oot(jo.get("indeedMoney"))));
        qd.setStockType(oot(jo.getJSONObject("stock").get("plate")));
        qd.setStockNumber(oot(jo.getJSONObject("stock").get("stockNum")));
        qd.setPersonNum(oot(jo.get("manNum")));
        qd.setCbPersonNum(oot(jo.get("securityNum")));
    }
}