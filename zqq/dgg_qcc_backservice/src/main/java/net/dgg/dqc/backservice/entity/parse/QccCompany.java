package net.dgg.dqc.backservice.entity.parse;


import net.dgg.dqc.backservice.entity.parse.qyfj.BriefIntroductionInfo;
import net.dgg.dqc.backservice.entity.parse.qygs.QygsDetail;
import net.dgg.dqc.backservice.entity.parse.qynb.QynbDetail;

import java.util.List;

/**
 * 企查查公司 API 信息
 * Created by jiangsh on 2018/6/5 13:49
 */
public class QccCompany {
    private String companyId; //公司id
    private String name; //公司名称
    private String logo; //公司logo url
    private String tel; //公司tel
    private String email; //公司email
    private QygsDetail qygsDetail; //企业工商
    private Swdj swdj;             //税务登记
    private List<QynbDetail> qynbDetail; //企业年报
    private List<Jyyc> jyyc;            //经营异常
    private BriefIntroductionInfo briefIntroductionInfo;            //企业附加信息-公司简介信息
    private CompanyVerify companyVerify; //企业三要素
    private List<SbDetails> sbDetails; //全国商标查询-商标详情
    private List<Zzqrz> zzqrz; //著作权软著查询-软件著作权多重查询
    private Qyxybg qyxybg; //企业信用报告
    private List<Qyzp> qyzp; //企业族谱查询-企业对外投资
    private List<Frdwtz> frdwtz; //法人对外投资
    private List<FrZwrz> frzwrz; //法人在外任职
    private List<Zzsyr> zzsyr; //最终受益人
    private List<Gscp> gscp; //公司产品查询
    private List<Jyzk> jyzk; //经营状况
    private List<Invest> jyfx; //经营风险
    private List<Gswz> gswz; //公司网站信息查询
    private List<Zlxq> zlxq; //专利详情查询
    private List<Ktgg> ktgg; //开庭公告
    private List<Cpws> cpws; //裁判文书
    private List<Fygg> fygg; //法院公告
    private List<Sxbzxr> sxbzxr; //失信被执行人
    private Qyrydjg qyrydjg; //企业人员董监高
    private List<Qyzs> qyzs; //企业证书
    private Fptt fptt; //发票抬头
    private List<Bzxr> bzxr; //被执行人

    public List<Zzsyr> getZzsyr() {
        return zzsyr;
    }

    public void setZzsyr(List<Zzsyr> zzsyr) {
        this.zzsyr = zzsyr;
    }

    public List<FrZwrz> getFrzwrz() {
        return frzwrz;
    }

    public void setFrzwrz(List<FrZwrz> frzwrz) {
        this.frzwrz = frzwrz;
    }

    public List<Frdwtz> getFrdwtz() {
        return frdwtz;
    }

    public void setFrdwtz(List<Frdwtz> frdwtz) {
        this.frdwtz = frdwtz;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Bzxr> getBzxr() {
        return bzxr;
    }

    public void setBzxr(List<Bzxr> bzxr) {
        this.bzxr = bzxr;
    }

    public Fptt getFptt() {
        return fptt;
    }

    public void setFptt(Fptt fptt) {
        this.fptt = fptt;
    }

    public String getTel() {
        if (tel == null) tel = "empty";
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Invest> getJyfx() {
        return jyfx;
    }

    public void setJyfx(List<Invest> jyfx) {
        this.jyfx = jyfx;
    }

    public List<Qyzs> getQyzs() {
        return qyzs;
    }

    public void setQyzs(List<Qyzs> qyzs) {
        this.qyzs = qyzs;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public QygsDetail getQygsDetail() {
        return qygsDetail;
    }

    public void setQygsDetail(QygsDetail qygsDetail) {
        this.qygsDetail = qygsDetail;
    }

    public Swdj getSwdj() {
        return swdj;
    }

    public void setSwdj(Swdj swdj) {
        this.swdj = swdj;
    }

    public List<QynbDetail> getQynbDetail() {
        return qynbDetail;
    }

    public void setQynbDetail(List<QynbDetail> qynbDetail) {
        this.qynbDetail = qynbDetail;
    }

    public List<Jyyc> getJyyc() {
        return jyyc;
    }

    public void setJyyc(List<Jyyc> jyyc) {
        this.jyyc = jyyc;
    }

    public BriefIntroductionInfo getBriefIntroductionInfo() {
        return briefIntroductionInfo;
    }

    public void setBriefIntroductionInfo(BriefIntroductionInfo briefIntroductionInfo) {
        this.briefIntroductionInfo = briefIntroductionInfo;
    }

    public CompanyVerify getCompanyVerify() {
        return companyVerify;
    }

    public void setCompanyVerify(CompanyVerify companyVerify) {
        this.companyVerify = companyVerify;
    }

    public List<SbDetails> getSbDetails() {
        return sbDetails;
    }

    public void setSbDetails(List<SbDetails> sbDetails) {
        this.sbDetails = sbDetails;
    }

    public List<Zzqrz> getZzqrz() {
        return zzqrz;
    }

    public void setZzqrz(List<Zzqrz> zzqrz) {
        this.zzqrz = zzqrz;
    }

    public Qyxybg getQyxybg() {
        return qyxybg;
    }

    public void setQyxybg(Qyxybg qyxybg) {
        this.qyxybg = qyxybg;
    }

    public List<Qyzp> getQyzp() {
        return qyzp;
    }

    public void setQyzp(List<Qyzp> qyzp) {
        this.qyzp = qyzp;
    }

    public List<Gscp> getGscp() {
        return gscp;
    }

    public void setGscp(List<Gscp> gscp) {
        this.gscp = gscp;
    }

    public List<Jyzk> getJyzk() {
        return jyzk;
    }

    public void setJyzk(List<Jyzk> jyzk) {
        this.jyzk = jyzk;
    }

    public List<Gswz> getGswz() {
        return gswz;
    }

    public void setGswz(List<Gswz> gswz) {
        this.gswz = gswz;
    }

    public List<Zlxq> getZlxq() {
        return zlxq;
    }

    public void setZlxq(List<Zlxq> zlxq) {
        this.zlxq = zlxq;
    }

    public List<Ktgg> getKtgg() {
        return ktgg;
    }

    public void setKtgg(List<Ktgg> ktgg) {
        this.ktgg = ktgg;
    }

    public List<Cpws> getCpws() {
        return cpws;
    }

    public void setCpws(List<Cpws> cpws) {
        this.cpws = cpws;
    }

    public List<Fygg> getFygg() {
        return fygg;
    }

    public void setFygg(List<Fygg> fygg) {
        this.fygg = fygg;
    }

    public List<Sxbzxr> getSxbzxr() {
        return sxbzxr;
    }

    public void setSxbzxr(List<Sxbzxr> sxbzxr) {
        this.sxbzxr = sxbzxr;
    }

    public Qyrydjg getQyrydjg() {
        return qyrydjg;
    }

    public void setQyrydjg(Qyrydjg qyrydjg) {
        this.qyrydjg = qyrydjg;
    }
}
