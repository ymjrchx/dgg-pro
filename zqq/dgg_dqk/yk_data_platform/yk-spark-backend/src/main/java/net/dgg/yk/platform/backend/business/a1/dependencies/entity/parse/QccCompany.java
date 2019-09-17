package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;


import net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse.qyfj.BriefIntroductionInfo;
import net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse.qygs.QygsDetail;
import net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse.qynb.QynbDetail;

import java.io.Serializable;
import java.util.List;

/**
 * 企查查公司 API 信息
 * Created by jiangsh on 2018/6/5 13:49
 */
public class QccCompany implements Serializable {
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
    private Fptt fptts; //发票抬头
    private List<Bzxr> bzxr; //被执行人
    private List<Flss> flss; //法律诉讼
    private List<Sfxz> sfxz; //司法协助
    private List<Dcdy> dcdys; //动产抵押
    private List<Qsgg> qsgg; //欠税公告
    private List<Sfpm> sfpms; //司法拍卖
    private List<Hbcf> hbcfs; //环保处罚
    private List<Tddy> tddys; //土地抵押
    private List<Ggcg> ggcgs; //公告催告
    private List<Rzls> rzls; //融资历史
    private List<Tzsj> tzsjs; //投资事件
    private List<Jpxx> jpxxes; //竞品信息
    private List<Hxtd> hxtds; //核心团队
    private List<Qyyw> qyyws; //企业业务
    private List<Zpzzq> zpzzqs; //作品著作权
    private Past past; //历史信息

    private String deliveryCount; //送达公告
    private String severePunishment; //严重违法
    private String liquidationInfo; //清算信息
    private String propertyPledge; //知识产权出质
    private String taxIllegal; //税收违法

    private String updateTime; //更新时间

    public Past getPast() {
        return past;
    }

    public void setPast(Past past) {
        this.past = past;
    }

    public List<Zpzzq> getZpzzqs() {
        return zpzzqs;
    }

    public void setZpzzqs(List<Zpzzq> zpzzqs) {
        this.zpzzqs = zpzzqs;
    }

    public List<Qyyw> getQyyws() {
        return qyyws;
    }

    public void setQyyws(List<Qyyw> qyyws) {
        this.qyyws = qyyws;
    }

    public List<Hxtd> getHxtds() {
        return hxtds;
    }

    public void setHxtds(List<Hxtd> hxtds) {
        this.hxtds = hxtds;
    }

    public List<Jpxx> getJpxxes() {
        return jpxxes;
    }

    public void setJpxxes(List<Jpxx> jpxxes) {
        this.jpxxes = jpxxes;
    }

    public List<Tzsj> getTzsjs() {
        return tzsjs;
    }

    public void setTzsjs(List<Tzsj> tzsjs) {
        this.tzsjs = tzsjs;
    }

    public List<Rzls> getRzls() {
        return rzls;
    }

    public void setRzls(List<Rzls> rzls) {
        this.rzls = rzls;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<Ggcg> getGgcgs() {
        return ggcgs;
    }

    public void setGgcgs(List<Ggcg> ggcgs) {
        this.ggcgs = ggcgs;
    }

    public List<Tddy> getTddys() {
        return tddys;
    }

    public void setTddys(List<Tddy> tddys) {
        this.tddys = tddys;
    }

    public List<Hbcf> getHbcfs() {
        return hbcfs;
    }

    public void setHbcfs(List<Hbcf> hbcfs) {
        this.hbcfs = hbcfs;
    }

    public String getLiquidationInfo() {
        return liquidationInfo;
    }

    public void setLiquidationInfo(String liquidationInfo) {
        this.liquidationInfo = liquidationInfo;
    }

    public String getPropertyPledge() {
        return propertyPledge;
    }

    public void setPropertyPledge(String propertyPledge) {
        this.propertyPledge = propertyPledge;
    }

    public String getTaxIllegal() {
        return taxIllegal;
    }

    public void setTaxIllegal(String taxIllegal) {
        this.taxIllegal = taxIllegal;
    }

    public List<Sfpm> getSfpms() {
        return sfpms;
    }

    public void setSfpms(List<Sfpm> sfpms) {
        this.sfpms = sfpms;
    }

    public List<Qsgg> getQsgg() {
        return qsgg;
    }

    public void setQsgg(List<Qsgg> qsgg) {
        this.qsgg = qsgg;
    }

    public List<Dcdy> getDcdys() {
        return dcdys;
    }

    public void setDcdys(List<Dcdy> dcdys) {
        this.dcdys = dcdys;
    }

    public String getSeverePunishment() {
        return severePunishment;
    }

    public void setSeverePunishment(String severePunishment) {
        this.severePunishment = severePunishment;
    }

    public String getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(String deliveryCount) {
        this.deliveryCount = deliveryCount;
    }

    public List<Sfxz> getSfxz() {
        return sfxz;
    }

    public void setSfxz(List<Sfxz> sfxz) {
        this.sfxz = sfxz;
    }

    public List<Flss> getFlss() {
        return flss;
    }

    public void setFlss(List<Flss> flss) {
        this.flss = flss;
    }

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


    public Fptt getFptts() {
        return fptts;
    }

    public void setFptts(Fptt fptts) {
        this.fptts = fptts;
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
