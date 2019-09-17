package net.dgg.dqc.backservice.parsedata;

import net.dgg.dqc.backservice.constant.PTConst;
import net.dgg.dqc.backservice.entity.parse.*;
import net.dgg.dqc.backservice.entity.parse.qyfj.BriefIntroductionInfo;
import net.dgg.dqc.backservice.entity.parse.qyfj.ContactInfo;
import net.dgg.dqc.backservice.entity.parse.qygs.QygsDetail;
import net.dgg.dqc.backservice.entity.parse.qynb.QynbDetail;
import net.dgg.dqc.backservice.utils.BaseUtil;
import net.dgg.dqc.backservice.utils.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangsh on 2018/6/5 13:49
 */
public class QccDetailData extends BaseUtil {

    private static final Logger logger = LoggerFactory.getLogger(QccDetailData.class);

    /**
     * 转换企查查详情数据
     *
     * @param o
     * @return
     */
    public static QccCompany conver(Object o) {
        QccCompany c = new QccCompany();
        try {
            if (o != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                StringBuffer sb = new StringBuffer();
                JSONObject jo = JSONObject.fromObject(o.toString());

                c.setCompanyId(oot(jo.get("url"), PTConst.QCC_WEBSITE_URL, PTConst.TARGET_PREFIX, ".html", PTConst.TARGET_SUFFIX));
                c.setName(oot(jo.get("title")));
                c.setLogo(oot(jo.get("logo")));
                c.setUpdateTime(curretTime());

                QygsDetail qd = new QygsDetail(); //企业工商信息
                if (jo.get("title") != null)
                    qd.setName(jo.get("title").toString()); //name
                if (jo.get("isalive") != null)
                    qd.setStatus(dealStatus(jo.get("isalive"))); //Status
                if (jo.get("tel") != null) {
                    sb.append(jo.get("tel").toString());
                    c.setTel(oot(jo.get("tel")));
                }
                if (jo.get("address") != null)
                    qd.setAddress(jo.get("address").toString()); //Address

                List<ContactInfo> contactInfoList = new ArrayList<>();
                ContactInfo contactInfo = new ContactInfo(); //联系人信息
                if (jo.get("email") != null) {
                    contactInfo.setEmail(jo.get("email").toString());
                    c.setEmail(oot(jo.get("email")));
                }
                if (jo.get("website") != null)
                    contactInfo.setWebSiteUrl(jo.get("website").toString());

                ////基本信息
                JSONObject jbxx = jo.getJSONObject("jiben_xinxi");
                if (!jbxx.isNullObject()) {
                    BriefIntroductionInfo bii = new BriefIntroductionInfo();
                    bii.setContent(oot(jbxx.get("gongsi_jianjie")));
                    c.setBriefIntroductionInfo(bii);
                    JSONArray ja = jbxx.getJSONArray("fading_daibiaoren");
                    if (!ja.isEmpty())
                        qd.setOperName(ja.get(0).toString()); //legalPersonName

                    //工商信息
                    JSONArray gsxx = jbxx.getJSONArray("gongshang_xinxi");
                    if (!gsxx.isEmpty())
                        putGsxx(gsxx, qd, jo);

                    //股东信息
                    JSONArray gdxx = jbxx.getJSONArray("gudong_xinxi");
                    if (!gdxx.isEmpty())
                        putGdxx(gdxx, qd);

                    //对外投资
                    JSONArray dwtz = jbxx.getJSONArray("duiwai_touzi");
                    if (!dwtz.isEmpty())
                        putDwtz(dwtz, c);

                    //主要人员
                    JSONArray zyry = jbxx.getJSONArray("zhuyao_renyuan");
                    if (!zyry.isEmpty())
                        putZyry(zyry, qd);

                    //分支机构
                    if (jbxx.has("fenzhi_jigou")) {
                        JSONArray fzjg = jbxx.getJSONArray("fenzhi_jigou");
                        if (!fzjg.isEmpty())
                            putFzjg(fzjg, qd);
                    }

                    //变更记录
                    JSONArray bgjl = jbxx.getJSONArray("biangeng_jilu");
                    if (!bgjl.isEmpty())
                        putBgjl(bgjl, qd);
                }

                ////法律诉讼
                JSONObject flss = jo.getJSONObject("falv_susong");
                if (!flss.isNullObject()) {
                    //裁判文书
                    JSONArray cpws = flss.getJSONArray("caipan_wenshu");
                    if (!cpws.isEmpty())
                        putCpws(cpws, c);

                    //法院公告
                    JSONArray fygg = flss.getJSONArray("fayuan_gonggao");
                    if (!fygg.isEmpty())
                        putFygg(fygg, c);

                    //开庭公告
                    JSONArray ktgg = flss.getJSONArray("kaiting_gonggao");
                    if (!ktgg.isEmpty())
                        putKtgg(ktgg, c);

                    //被执行人
                    JSONArray bzxr = flss.getJSONArray("beizhixingren_xinxi");
                    if (!bzxr.isEmpty())
                        putBzxr(bzxr, c);

                }

                ////经营状况
                JSONObject jyzk = jo.getJSONObject("jingying_zhuangkuang");
                if (!jyzk.isNullObject()) {
                    List<Jyzk> jyzkList = new ArrayList<>();
                    Jyzk jko = new Jyzk();
                    //行政许可 [工商局]
                    JSONArray xzxkgsj = jyzk.getJSONArray("xingzheng_xuke[gongshangju]");
                    if (!xzxkgsj.isEmpty())
                        putXzxkgsj(xzxkgsj, jko);

                    //行政许可 [信用中国]
                    JSONArray xyzg = jyzk.getJSONArray("xingzheng_xuke[gongshangju]");
                    if (!xyzg.isEmpty())
                        putXyzg(xyzg, jko);

                    //税务信用
                    JSONArray swxy = jyzk.getJSONArray("shuiwu_xinyong");
                    if (!swxy.isEmpty())
                        putSwxy(swxy, jko);

                    //产品信息
                    JSONArray cpxx = jyzk.getJSONArray("chanpin_xinxi");
                    if (!cpxx.isEmpty())
                        putCpxx(cpxx, jko);

                    //融资信息
                    JSONArray rzxx = jyzk.getJSONArray("rongzi_xinxi");
                    if (!rzxx.isEmpty())
                        putRzxx(rzxx, jko);

                    //招投标信息
                    JSONArray ztbxx = jyzk.getJSONArray("zhaotoubiao_xinxi");
                    if (!ztbxx.isEmpty())
                        putZtbxx(ztbxx, jko);

                    //招聘
                    JSONArray zp = jyzk.getJSONArray("zhaopin");
                    if (!zp.isEmpty())
                        putZp(zp, jko);

                    //财务总览
                    JSONArray cwzl = jyzk.getJSONArray("caiwu_zonglan");
                    if (!cwzl.isEmpty())
                        putCwzl(cwzl, jko);

                    //微信公众号
                    JSONArray wxgzh = jyzk.getJSONArray("wechat_gongzhonghao");
                    if (!wxgzh.isEmpty())
                        putWxgzh(wxgzh, jko);

                    jyzkList.add(jko);
                    c.setJyzk(jyzkList);

                }


                ////经营风险(获取里面的投资公司)
                JSONObject jyfx = jo.getJSONObject("jingying_fengxian");
                if (!jyfx.isNullObject()) {
                    Invest invest = new Invest();
                    //股权出质
                    JSONArray gqcz = jyfx.getJSONArray("guquan_chuzhi");
                    if (!gqcz.isEmpty())
                        putGqcz(gqcz, invest);

                    List<Invest> xkList = new ArrayList<>();
                    //行政处罚 [工商局]
                    JSONArray aa = jyfx.getJSONArray("xingzheng_chufa[gongshangju]");
                    if (!aa.isEmpty())
                        putAa(aa, invest);

                    //行政处罚 [信用中国]
                    JSONArray bb = jyfx.getJSONArray("xingzheng_chufa[xinyongzhongguo]");
                    if (!bb.isEmpty())
                        putBb(bb, invest);

                    xkList.add(invest);
                    c.setJyfx(xkList);
                }

                ////企业年报
                JSONObject qynb = jo.getJSONObject("qiye_nianbao");
                if (!qynb.isNullObject()) {
                    List<QynbDetail> annuRepYears = new ArrayList<>();
                    for (int i = 2013; i <= 2017; i++) {
                        final String str = "年度报告";
                        final String year = String.valueOf(i);
                        putQynb(qynb.getJSONObject(year.concat(str)), sdf, annuRepYears, sb, year);
                    }
                    c.setQynbDetail(annuRepYears);
                }

                ////知识产权
                JSONObject zscq = jo.getJSONObject("zhishi_chanquan");
                if (!zscq.isNullObject()) {
                    //商标信息
                    JSONArray sbxx = zscq.getJSONArray("shangbiao_xinxi");
                    if (!sbxx.isEmpty())
                        putSbxx(sbxx, c);

                    //证书信息
                    JSONArray zsxx = zscq.getJSONArray("zhengshu_xinxi");
                    if (!zsxx.isEmpty())
                        putZsxx(zsxx, c);

                    //网站信息
                    JSONArray wzxx = zscq.getJSONArray("wangzhan_xinxi");
                    if (!wzxx.isEmpty())
                        putWzxx(wzxx, c);

                    //专利信息
                    JSONArray zlxx = zscq.getJSONArray("zhuanli_xinxi");
                    if (!zlxx.isEmpty())
                        putZlxx(zlxx, c);

                    //软件著作权
                    JSONArray rzq = zscq.getJSONArray("ruanjian_zhuzuoquan");
                    if (!rzq.isEmpty())
                        putRzq(rzq, c);

                }

                //发票抬头详情
                JSONObject fp = jo.getJSONObject("fapiao_taitou");
                if (!fp.isNullObject())
                    putFp(fp, c);

                dealPhoneNo(sb.toString(), contactInfo, c);
                contactInfoList.add(contactInfo);
                qd.setContactInfoList(contactInfoList);
                c.setQygsDetail(qd);

            } else logger.info("qcc detail object exception, value is --> {}", o);

        } catch (Exception e) {
            saveError(c, e, "QccDetailData");
            logger.error("出现异常情况", e.getMessage());
        }
        return c;
    }

    public static void dealPhoneNo(String telno, ContactInfo contactInfo, QccCompany c) {
        if (telno.length() > 0) {
            contactInfo.setPhoneNumber(telno); //phoneNumber
            if (c.getTel().equals(TAG))
                if (telno.contains("、")) {
                    c.setTel(telno.split("、")[0]);
                } else c.setTel(telno);
        }
    }

    private static void putBzxr(JSONArray ja, QccCompany c) {
        if (!ja.isEmpty()) {
            List<Bzxr> xkList = new ArrayList<>();
            for (int i = 1; i < ja.size(); i++) {
                Bzxr xk = new Bzxr();
                JSONArray joo = ja.getJSONArray(i);
                if (!joo.isEmpty()) {
                    xk.setNo(oot(joo.getJSONArray(1).get(0)));
                    xk.setTime(oot(joo.get(2)));
                    xk.setCourt(oot(joo.get(3)));
                    xk.setNum(oot(joo.get(4)));
                    xkList.add(xk);
                }
            }
            c.setBzxr(xkList);
        }
    }

    private static void putFp(JSONObject ja, QccCompany c) {
        Fptt f = new Fptt();
        f.setName(oot(ja.get("Name")));
        f.setNo(oot(ja.get("CreditCode")));
        f.setAddress(oot(ja.get("Address")));
        f.setPhoneNo(oot(ja.get("PhoneNumber")));
        f.setBank(oot(ja.get("Bank")));
        f.setBankAccount(oot(ja.get("Bankaccount")));
        c.setFptt(f);
    }

    private static void putRzq(JSONArray ja, QccCompany c) {
        List<Zzqrz> zlxqList = new ArrayList<>();
        for (int i = 1; i < ja.size(); i++) {
            JSONArray jao = ja.getJSONArray(i);
            if (!jao.isEmpty()) {
                Zzqrz z = new Zzqrz();
                z.setName(oot(jao.get(1)));
                z.setVersionNo(oot(jao.get(2)));
                if (justDatelen(jao.get(3)))
                    z.setPublishDate(oot(jao.get(3)));
                z.setShortName(oot(jao.get(4)));
                z.setRegisterNo(oot(jao.get(5)));
                if (justDatelen(jao.get(6)))
                    z.setRegisterAperDate(oot(jao.get(6)));
                zlxqList.add(z);
            }
        }
        c.setZzqrz(zlxqList);
    }

    protected static void putZlxx(JSONArray ja, QccCompany c) {
        List<Zlxq> zlxqList = new ArrayList<>();
        for (int i = 1; i < ja.size(); i++) {
            JSONArray jao = ja.getJSONArray(i);
            if (!jao.isEmpty()) {
                Zlxq zlxq = new Zlxq();
                zlxq.setDocumentTypes(oot(jao.get(1)));
                zlxq.setPublicationNumber(oot(jao.get(2)));
                if (justDatelen(jao.get(3))) ;
                zlxq.setPublicationDate(oot(jao.get(3)));
                zlxq.setKindCodeDesc(oot(jao.get(4)));
                zlxq.setUrl(c.getCompanyId());
                zlxq.setAssigneestring(c.getName());
                zlxqList.add(zlxq);
            }
        }
        c.setZlxq(zlxqList);
    }

    private static void putBb(JSONArray ja, Invest inv) {
        if (!ja.isEmpty()) {
            List<Invest.Bb> xkList = new ArrayList<>();
            for (int i = 1; i < ja.size(); i++) {
                Invest.Bb xk = new Invest.Bb();
                JSONArray joo = ja.getJSONArray(i);
                if (!joo.isEmpty()) {
                    xk.setWsh(oot(joo.getJSONArray(1).get(1)));
                    xk.setName(oot(joo.get(2)));
                    xk.setArea(oot(joo.get(3)));
                    xk.setTime(oot(joo.get(4)));
                    xkList.add(xk);
                }
            }
            inv.setBbs(xkList);
        }
    }

    private static void putAa(JSONArray ja, Invest inv) {
        if (!ja.isEmpty()) {
            List<Invest.Aa> xkList = new ArrayList<>();
            for (int i = 1; i < ja.size(); i++) {
                Invest.Aa xk = new Invest.Aa();
                JSONArray joo = ja.getJSONArray(i);
                if (!joo.isEmpty()) {
                    xk.setWsh(oot(joo.get(1)));
                    xk.setWfxwlx(oot(joo.get(2)));
                    xk.setXzcflr(oot(joo.get(3)));
                    if (justDatelen(joo.get(4)))
                        xk.setGsdate(oot(joo.get(4)));
                    xk.setJdjg(oot(joo.get(5)));
                    if (justDatelen(joo.get(6)))
                        xk.setJddate(oot(joo.get(6).toString()));
                    xkList.add(xk);
                }
            }
            inv.setAas(xkList);
        }
    }

    private static void putWxgzh(JSONArray ja, Jyzk jko) {
        if (!ja.isEmpty()) {
            List<Jyzk.Wxgzh> xkList = new ArrayList<>();
            for (int i = 1; i < ja.size(); i++) {
                Jyzk.Wxgzh xk = new Jyzk.Wxgzh();
                JSONArray joo = ja.getJSONArray(i);
                if (!joo.isEmpty()) {
                    xk.setHead(oot(joo.get(1)));
                    xk.setGzh(oot(joo.get(2)));
                    xk.setWxh(oot(joo.get(3)));
                    xk.setEwm(oot(joo.get(4)));
                    if (joo.size() > 5)
                        xk.setJj(oot(joo.get(5)));
                    xkList.add(xk);
                }
            }
            jko.setWxgzh(xkList);
        }
    }

    private static void putCwzl(JSONArray ja, Jyzk jko) {
        if (!ja.isEmpty()) {
            List<Jyzk.Cwzl> xkList = new ArrayList<>();
            Jyzk.Cwzl xk = new Jyzk.Cwzl();
            for (int i = 0; i < ja.size(); i++) {
                JSONArray joo = ja.getJSONArray(i);
                if (!joo.isEmpty()) {
                    if (i == 1) {
                        xk.setLevel(oot(joo.get(1)));
                    } else if (i == 2) {
                        xk.setNsqj(oot(joo.get(1)));
                    } else if (i == 3) {
                        xk.setJlrl(oot(joo.get(1)));
                    } else if (i == 4) {
                        xk.setMll(oot(joo.get(1)));
                    }
                    xkList.add(xk);
                }
            }
            jko.setCwzl(xkList);
        }
    }

    private static void putZp(JSONArray ja, Jyzk jko) {
        if (!ja.isEmpty()) {
            List<Jyzk.Zp> xkList = new ArrayList<>();
            for (int i = 1; i < ja.size(); i++) {
                Jyzk.Zp xk = new Jyzk.Zp();
                JSONArray joo = ja.getJSONArray(i);
                if (!joo.isEmpty()) {
                    if (justDatelen(joo.get(1)))
                    xk.setDate(oot(joo.get(1)));
                    xk.setZpzw(oot(joo.get(2)));
                    xk.setYx(oot(joo.get(3)));
                    xk.setXl(oot(joo.get(4)));
                    xk.setJy(oot(joo.get(5)));
                    if (joo.size() > 6)
                        xk.setCity(oot(joo.get(6)));
                    xkList.add(xk);
                }
            }
            jko.setZp(xkList);
        }
    }

    private static void putZtbxx(JSONArray ja, Jyzk jko) {
        if (!ja.isEmpty()) {
            List<Jyzk.Zdbxx> xkList = new ArrayList<>();
            for (int i = 1; i < ja.size(); i++) {
                Jyzk.Zdbxx xk = new Jyzk.Zdbxx();
                JSONArray joo = ja.getJSONArray(i);
                if (!joo.isEmpty()) {
                    xk.setMsg(oot(joo.get(1)));
                    if (justDatelen(joo.get(2)))
                        xk.setDate(oot(joo.get(2)));
                    xk.setArea(oot(joo.get(3)));
                    xk.setType(oot(joo.get(4)));
                    xkList.add(xk);
                }
            }
            jko.setZdbxx(xkList);
        }
    }

    private static void putRzxx(JSONArray ja, Jyzk jko) {
        if (!ja.isEmpty()) {
            List<Jyzk.Rzxx> xkList = new ArrayList<>();
            for (int i = 0; i < ja.size(); i++) {
                Jyzk.Rzxx xk = new Jyzk.Rzxx();
                JSONArray joo = ja.getJSONArray(i);
                if (!joo.isEmpty()) {
                    if (justDatelen(joo.get(1))) ;
                    xk.setDate(oot(joo.get(1)));
                    xk.setName(oot(joo.get(2)));
                    xk.setLevel(oot(joo.get(3)));
                    xk.setMoney(oot(joo.get(4)));
                    xk.setDzf(oot(joo.get(5)));
                    xkList.add(xk);
                }
            }
            jko.setRzxxs(xkList);
        }
    }

    private static void putCpxx(JSONArray ja, Jyzk jko) {
        if (!ja.isEmpty()) {
            List<Jyzk.Product> xkList = new ArrayList<>();
            for (int i = 1; i < ja.size(); i++) {
                Jyzk.Product xk = new Jyzk.Product();
                JSONArray joo = ja.getJSONArray(i);
                if (!joo.isEmpty()) {
                    xk.setUrl(oot(joo.get(1)));
                    xk.setName(oot(joo.getJSONArray(2).get(0)));
                    xk.setRzxx(oot(joo.get(3)));
                    xk.setTime(oot(joo.get(4)));
                    xk.setArea(oot(joo.get(5)));
                    xk.setMsg(oot(joo.get(6)));
                    xkList.add(xk);
                }
            }
            jko.setProducts(xkList);
        }
    }

    private static void putSwxy(JSONArray ja, Jyzk jko) {
        if (!ja.isEmpty()) {
            List<Jyzk.Swxy> xkList = new ArrayList<>();
            for (int i = 1; i < ja.size(); i++) {
                Jyzk.Swxy xk = new Jyzk.Swxy();
                JSONArray joo = ja.getJSONArray(i);
                if (!joo.isEmpty()) {
                    xk.setYear(oot(joo.get(1)));
                    xk.setNsrsbh(oot(joo.get(2)));
                    xk.setNsrxydj(oot(joo.get(3)));
                    xk.setPjdw(oot(joo.get(4)));
                    xkList.add(xk);
                }
            }
            jko.setSwxys(xkList);
        }
    }

    private static void putXyzg(JSONArray ja, Jyzk jko) {
        if (!ja.isEmpty()) {
            List<Jyzk.Xzxkxyzg> xkList = new ArrayList<>();
            for (int i = 1; i < ja.size(); i++) {
                Jyzk.Xzxkxyzg xk = new Jyzk.Xzxkxyzg();
                JSONArray joo = ja.getJSONArray(i);
                if (!joo.isEmpty()) {
                    xk.setProjectName(oot(joo.get(1)));
                    xk.setArea(oot(joo.get(2)));
                    if (justDatelen(joo.get(3)))
                        xk.setDate(oot(joo.get(3)));
//                    xk.setConent(oot(joo.get(4)));
                    xkList.add(xk);
                }
            }
            jko.setXzxkxyzg(xkList);
        }
    }

    private static void putXzxkgsj(JSONArray ja, Jyzk jko) {
        if (!ja.isEmpty()) {
            List<Jyzk.Xzxkgsj> xkList = new ArrayList<>();
            for (int i = 1; i < ja.size(); i++) {
                Jyzk.Xzxkgsj xk = new Jyzk.Xzxkgsj();
                JSONArray joo = ja.getJSONArray(i);
                if (!joo.isEmpty()) {
                    xk.setFileNo(oot(joo.get(1)));
                    xk.setFileName(oot(joo.get(2)));
                    if (justDatelen(joo.get(3)))
                        xk.setStartDate(oot(joo.get(3)));
                    if (justDatelen(joo.get(4)))
                        xk.setEndDate(oot(joo.get(4)));
                    xk.setXkjg(oot(joo.get(5)));
                    xk.setXkConent(oot(joo.get(6)));
                    xkList.add(xk);
                }
            }
            jko.setXzxkgsjs(xkList);
        }
    }

    private static void putKtgg(JSONArray ja, QccCompany c) {
        List<Ktgg> ktggList = new ArrayList<>();
        for (int i = 1; i < ja.size(); i++) {
            Ktgg ktgg = new Ktgg();
            JSONArray jaktgg = ja.getJSONArray(i);
            if (!jaktgg.isEmpty()) {
                ktgg.setCase_No(oot(jaktgg.get(1)));
                ktgg.setOpen_Time(oot(jaktgg.get(2)));
                ktgg.setCase_Reason(oot(jaktgg.get(3)));

                List<Ktgg.Prosecutor> pList = new ArrayList<>();
                Ktgg.Prosecutor p = new Ktgg.Prosecutor();
                p.setName(oot(jaktgg.get(4)));
                pList.add(p);
                ktgg.setProsecutor(pList);

                List<Ktgg.Defendant> dList = new ArrayList<>();
                Ktgg.Defendant d = new Ktgg.Defendant();
                d.setName(oot(jaktgg.get(5)));
                dList.add(d);
                ktgg.setDefendant(dList);
            }
        }
        c.setKtgg(ktggList);
    }

    private static void putFygg(JSONArray ja, QccCompany c) {
        List<Fygg> fyggList = new ArrayList<>();
        for (int i = 1; i < ja.size(); i++) {
            Fygg lawSuit = new Fygg();
            JSONArray jafygg = ja.getJSONArray(i);
            if (!jafygg.isEmpty()) {
                lawSuit.setParty(jafygg.get(1).toString());
                lawSuit.setCategory(jafygg.get(2).toString());
                lawSuit.setCourt(jafygg.get(3).toString());
                if (justDatelen(jafygg.get(4))) ;
                lawSuit.setSubmitDate(jafygg.get(4).toString());
                JSONObject jofygg = jafygg.getJSONObject(5);
                if (!jofygg.isNullObject()) {
                    List<Fygg.NameKeyNoCollection> nkList = new ArrayList<>();
                    Fygg.NameKeyNoCollection nk = new Fygg.NameKeyNoCollection();
                    nk.setName(oot(jofygg.get("Party")));
                    nk.setKeyNo(oot(jofygg.get("KeyNo")));
                    nkList.add(nk);
                    lawSuit.setNameKeyNoColl(nkList);
                }
                fyggList.add(lawSuit);
            }
        }
        c.setFygg(fyggList);
    }

    private static void putFzjg(JSONArray ja, QygsDetail qd) {
        List<QygsDetail.Branches> branches = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            QygsDetail.Branches branch = new QygsDetail.Branches();
            JSONArray jao = ja.getJSONArray(i);
            branch.setName(oot(jao.get(1)));
            branches.add(branch);
        }
        qd.setBranchesList(branches);
    }

    public static void putWzxx(JSONArray wzxx, QccCompany c) {
        List<Gswz> tms = new ArrayList<>();
        for (int i = 1; i < wzxx.size(); i++) {
            Gswz tm = new Gswz();
            tm.setHomeSite(wzxx.getJSONArray(i).get(1).toString());
            tm.setTitle(wzxx.getJSONArray(i).get(2).toString());
            tm.setYuMing(wzxx.getJSONArray(i).get(3).toString());
            tm.setBeiAn(wzxx.getJSONArray(i).get(4).toString());
            if (justDatelen(wzxx.getJSONArray(i).get(5))) ;
            tm.setsDate(wzxx.getJSONArray(i).get(5).toString());
            tms.add(tm);
        }
        c.setGswz(tms);
    }

    private static void putGsxx(JSONArray gsxx, QygsDetail qd, JSONObject jo) {
        qd.setProvince(oot(jo.get("province")));
        qd.setRegistCapi(dealMoney(gsxx.getJSONArray(0).get(1))); //RegistCapi
        qd.setRealCapi(dealMoney(gsxx.getJSONArray(1).get(1))); //RealCapi
        if (justDatelen(gsxx.getJSONArray(3).get(1)))
            qd.setStartDate(dealDate(gsxx.getJSONArray(3).get(1).toString())); //StartDate
        qd.setCreditCode(gsxx.getJSONArray(4).get(1).toString()); //CreditCode
        qd.setNo(oot(gsxx.getJSONArray(6).get(1))); //No
        qd.setOrgNo(gsxx.getJSONArray(7).get(1).toString()); //OrgNo
        qd.setEconKind(gsxx.getJSONArray(8).get(1).toString()); //EconKind
        qd.setIndustry(gsxx.getJSONArray(9).get(1).toString()); //industry
        if (justDatelen(gsxx.getJSONArray(10).get(1)))
            qd.setCheckDate(dealDate(gsxx.getJSONArray(10).get(1).toString())); //CheckDate
        qd.setBelongOrg(gsxx.getJSONArray(11).get(1).toString()); //BelongOrg
        qd.setProvince(gsxx.getJSONArray(12).get(1).toString()); //Province
        qd.setEnglishName(gsxx.getJSONArray(13).get(1).toString()); //英文名
        qd.setOriginalName(gsxx.getJSONArray(14).get(1).toString()); //OriginalName
        qd.setCbPersonNum(gsxx.getJSONArray(15).get(1).toString()); //cbPersonNum
        qd.setPersonNum(gsxx.getJSONArray(16).get(1).toString()); //personNum
        String formTo = gsxx.getJSONArray(17).get(1).toString();
        if (!StringUtils.isNullOrEmpty(formTo)) {
            if (justDatelen(formTo.split("至")[0]))
                qd.setTermStart(oot(formTo.split("至")[0])); //TermStart
            if (justDatelen(formTo.split("至")[1]))
                qd.setTeamEnd(oot(formTo.split("至")[1]).replace("", "")); //TeamEnd
        }
        qd.setScope(gsxx.getJSONArray(19).get(1).toString()); //Scope
    }

    private static void putGdxx(JSONArray gdxx, QygsDetail qd) {
        List<QygsDetail.Partners> investors = new ArrayList<>();
        for (int i = 1; i < gdxx.size(); i++) {
            QygsDetail.Partners inv = new QygsDetail.Partners();
            JSONArray jagdxx = gdxx.getJSONArray(i);
            if (!jagdxx.isEmpty()) {
                inv.setStockName(jagdxx.size() > 1 ? jagdxx.get(1).toString() : TAG); //股东
                inv.setStockPercent(jagdxx.size() > 2 ? jagdxx.get(2).toString() : TAG); //出资比例
                inv.setShouldCapi(jagdxx.size() > 3 ? oot(jagdxx.get(3)) : TAG); //认缴出资额
                if (jagdxx.size() > 4 && justDatelen(jagdxx.get(4))) {
                    inv.setShoudDate(dealDate(jagdxx.get(4).toString())); //认缴出资时间
                }
                inv.setStockType(jagdxx.size() > 5 ? oot(jagdxx.get(5)) : TAG); //股东类型
                investors.add(inv);
            }
        }
        qd.setPartnersList(investors);
    }

    private static void putDwtz(JSONArray dwtz, QccCompany c) {
        List<Qyzp> invests = new ArrayList<>();
        for (int i = 1; i < dwtz.size(); i++) {
            Qyzp invest = new Qyzp();
            JSONArray jadwtz = dwtz.getJSONArray(i);
            if (!jadwtz.isEmpty()) {
                invest.setName(jadwtz.get(0).toString());
                invest.setOperName(jadwtz.get(1).toString());
                invest.setRegistCapi(dealMoney(jadwtz.get(2)));
                invest.setFundedRatio(jadwtz.get(3).toString());
                if (justDatelen(jadwtz.get(4)))
                    invest.setStartDate(dealDate(jadwtz.get(4).toString()));
                invest.setStatus(jadwtz.get(5).toString());
                invests.add(invest);
            }
        }
        c.setQyzp(invests);
    }

    private static void putZyry(JSONArray zyry, QygsDetail qd) {
        List<QygsDetail.Employees> employeesList = new ArrayList<>();
        for (int i = 1; i < zyry.size(); i++) {
            QygsDetail.Employees staff = new QygsDetail.Employees();
            JSONArray jazyry = zyry.getJSONArray(i);
            if (!jazyry.isEmpty()) {
                staff.setName(jazyry.get(1).toString());
                staff.setJob(jazyry.get(2).toString());
                employeesList.add(staff);
            }
        }
        qd.setEmployeesList(employeesList);
    }

    private static void putBgjl(JSONArray bgjl, QygsDetail qd) {
        List<QygsDetail.ChangeRecords> changeRecordsList = new ArrayList<>();
        for (int i = 1; i < bgjl.size(); i++) {
            QygsDetail.ChangeRecords cc = new QygsDetail.ChangeRecords();
            JSONArray jabgjl = bgjl.getJSONArray(i);
            if (!jabgjl.isEmpty()) {
                if (justDatelen(jabgjl.get(1)))
                    cc.setChangeDate(dealDate(jabgjl.get(1).toString()));
                cc.setProjectName(jabgjl.get(2).toString());
                cc.setBeforeContent(jabgjl.get(3).toString());
                cc.setAfterContent(jabgjl.get(4).toString());
                changeRecordsList.add(cc);
            }
        }
        qd.setChangeRecordsList(changeRecordsList);
    }

    private static void putCpws(JSONArray cpws, QccCompany c) {
        List<Cpws> cpwsList = new ArrayList<>();
        for (int i = 1; i < cpws.size(); i++) {
            Cpws lawSuit = new Cpws();
            JSONArray jacpws = cpws.getJSONArray(i);
            if (!jacpws.isEmpty()) {
                lawSuit.setCaseName(jacpws.getJSONArray(1).get(0).toString());
                lawSuit.setId(jacpws.getJSONArray(1).get(1).toString());
                if (jacpws.get(2) != null && !StringUtils.isNullOrEmpty(jacpws.get(2).toString())) {
                    lawSuit.setJudgeDate(jacpws.get(2).toString());
                }
                lawSuit.setCaseNo(jacpws.get(3).toString());
                lawSuit.setAjsf(jacpws.get(4).toString());
                lawSuit.setCourt(jacpws.get(5).toString());
                cpwsList.add(lawSuit);
            }
        }
        c.setCpws(cpwsList);
    }

    private static void putGqcz(JSONArray gqcz, Invest invest) {
        List<Invest.Gqcz> gqczs = new ArrayList<>();
        for (int i = 1; i < gqcz.size(); i++) {
            Invest.Gqcz inv = new Invest.Gqcz();
            JSONArray ja1 = gqcz.getJSONArray(i);
            inv.setDjno(oot(ja1.getJSONObject(1).get("RegistNo")));
            inv.setCzr(oot(ja1.getJSONObject(1).getJSONObject("PledgorInfo").get("Name")));
            inv.setZqr(oot(ja1.get(3)));
            inv.setCzgqse(oot(ja1.get(4)));
            if (justDatelen(ja1.get(5)))
                inv.setDjrq(oot(ja1.get(5)));
            inv.setStatus(oot(ja1.get(6)));
            gqczs.add(inv);
        }
        invest.setGqczs(gqczs);
    }

    public static void putSbxx(JSONArray sbxx, QccCompany c) {
        List<SbDetails> tms = new ArrayList<>();
        for (int i = 1; i < sbxx.size(); i++) {
            SbDetails tm = new SbDetails();
            tm.setUrl(c.getCompanyId());
            tm.setApplicantCn(c.getName());
            tm.setImageUrl(sbxx.getJSONArray(i).get(1).toString());
            tm.setName(sbxx.getJSONArray(i).get(2).toString());
            tm.setStatus(sbxx.getJSONArray(i).get(3).toString());
            if (justDatelen(sbxx.getJSONArray(i).get(4)))
                tm.setAppDate(dealDate(sbxx.getJSONArray(i).get(4).toString()));
            tm.setRegNo(sbxx.getJSONArray(i).get(5).toString());
            tm.setIntCls(sbxx.getJSONArray(i).get(6).toString());
            tm.setFlowStatusDesc(sbxx.getJSONArray(i).get(7).toString());
            tms.add(tm);
        }
        c.setSbDetails(tms);
    }

    public static void putZsxx(JSONArray zsxx, QccCompany c) {
        List<Qyzs> qyzsList = new ArrayList<>();
        for (int i = 1; i < zsxx.size(); i++) {
            Qyzs qyzs = new Qyzs();
            qyzs.setId(zsxx.getJSONArray(i).get(0).toString());
            qyzs.setName(zsxx.getJSONArray(i).get(1).toString());
            qyzs.setNo(zsxx.getJSONArray(i).get(2).toString());
            if (justDatelen(zsxx.getJSONArray(i).get(3)))
                qyzs.setStartDate(dealDate(zsxx.getJSONArray(i).get(3).toString()));
            if (justDatelen(zsxx.getJSONArray(i).get(4)))
                qyzs.setEndDate(dealDate(zsxx.getJSONArray(i).get(4).toString()));
            qyzsList.add(qyzs);
        }
        c.setQyzs(qyzsList);
    }

    private static void putQynb(JSONObject report, SimpleDateFormat sdf, List<QynbDetail> annuRepYears, StringBuffer sb, final String year) {
        if (!report.isNullObject()) {
            QynbDetail ary = new QynbDetail();
            ary.setYear(year);
            final String publish = report.get("publish").toString().replace(" 公布", "");
            if (publish.contains("/")) {
                if (justDatelen(publish))
                    ary.setPublishDate(publish);
            } else {
                if (!"-".equals(publish)) ary.setPublishDate(publish);
            }
            JSONObject data = null;
            try {
                data = (report.containsKey("data") && report.has("data")) ? report.getJSONObject("data") : null;
                if (data == null || data.isNullObject() || data.isEmpty()) {
                    return;
                }
            } catch (Exception e) {
//                e.printStackTrace();
                logger.error("data数据错误：" + e.getMessage());
            }

            if (!data.isNullObject()) {
                ary.setHasDetailInfo(true);

                if (data.has("企业基本信息")) {
                    JSONArray jda = data.getJSONArray("企业基本信息");
                    if (!jda.isEmpty()) {
                        QynbDetail.BasicInfoData bid = new QynbDetail.BasicInfoData();//企业基本信息
                        String no = jda.getJSONArray(0).get(1).toString();
                        bid.setRegNo(no);
                        ary.setNo(no);
                        bid.setStatus(jda.getJSONArray(2).get(1).toString());
                        String phoneNumber2016 = jda.getJSONArray(3).get(1).toString();
                        if (!StringUtils.isNullOrEmpty(phoneNumber2016)) {
                            bid.setContactNo(phoneNumber2016);
                            if (!sb.toString().contains(phoneNumber2016)) {
                                if (sb.toString().length() > 0) sb.append("、").append(phoneNumber2016);
                                else sb.append(phoneNumber2016);
                            }
                        }
                        bid.setEmployeeCount(jda.getJSONArray(4).get(1).toString());
                        bid.setPostCode(jda.getJSONArray(5).get(1).toString());
                        bid.setIsStockRightTransfer(jda.getJSONArray(6).get(1).toString());
                        bid.setHasNewStockOrByStock(jda.getJSONArray(7).get(1).toString());
                        bid.setEmailAddress(jda.getJSONArray(8).get(1).toString());
                        bid.setAddress(jda.getJSONArray(9).get(1).toString());
                        ary.setBasicInfoData(bid);
                    }
                }

                if (data.has("股东（发起人）出资信息")) {
                    JSONArray jgda = data.getJSONArray("股东（发起人）出资信息");
                    if (!jgda.isEmpty()) {
                        List<QynbDetail.PartnerList> p1List = new ArrayList<>();
                        for (int i = 1; i < jgda.size(); i++) {
                            QynbDetail.PartnerList pl = new QynbDetail.PartnerList();
                            pl.setNo(jgda.getJSONArray(i).get(0).toString());
                            pl.setName(jgda.getJSONArray(i).get(1).toString());
                            pl.setShouldCapi(jgda.getJSONArray(i).get(2).toString());
                            if (justDatelen(jgda.getJSONArray(i).get(3)))
                                pl.setShouldDate(dealDate(jgda.getJSONArray(i).get(3).toString()));
                            pl.setShouldType(jgda.getJSONArray(i).get(4).toString());
                            pl.setRealCapi(jgda.getJSONArray(i).get(5).toString());
                            if (justDatelen(jgda.getJSONArray(i).get(6)))
                                pl.setRealDate(dealDate(jgda.getJSONArray(i).get(6).toString()));
                            pl.setRealType(jgda.getJSONArray(i).get(7).toString());
                            p1List.add(pl);
                        }
                        ary.setPartner(p1List);
                    }
                }

                if (data.has("企业资产状况信息")) {
                    JSONArray jzca = data.getJSONArray("企业资产状况信息");
                    if (!jzca.isEmpty()) {
                        QynbDetail.AssetsData ad = new QynbDetail.AssetsData();
                        ad.setTotalAssets(jzca.getJSONArray(0).get(1).toString());
                        ad.setTotalOwnersEquity(jzca.getJSONArray(1).get(1).toString());
                        ad.setGrossTradingIncome(jzca.getJSONArray(2).get(1).toString());
                        ad.setTotalProfit(jzca.getJSONArray(3).get(1).toString());
                        ad.setMainBusinessIncome(jzca.getJSONArray(4).get(1).toString());
                        ad.setNetProfit(jzca.getJSONArray(5).get(1).toString());
                        ad.setTotalTaxAmount(jzca.getJSONArray(6).get(1).toString());
                        ad.setTotalLiabilities(jzca.getJSONArray(7).get(1).toString());
                        ary.setAssetsData(ad);
                    }
                }
            }
            annuRepYears.add(ary);
        }
    }
}
