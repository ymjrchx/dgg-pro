package net.dgg.dqc.backservice.entity.parse.zcgl;

import java.util.List;

/**
 * 58资产管理model
 * Created by jiangsh on 2018/6/15 15:08
 */
public class ZcglModel {

    private String companyId; //公司ID
    private String qymc; //企业名称
    private String url; //url地址
    private String xydm; //信用代号
    private String fr; //法人
    private String zclx; //企业注册类型
    private String zcsd; //企业注册属地
    private String jydz; //企业经营地址

    private List<Zss> zssList; //企业资质资格
    private List<RegisterInfo> registerInfoList; //注册人员信息

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getXydm() {
        return xydm;
    }

    public void setXydm(String xydm) {
        this.xydm = xydm;
    }

    public String getFr() {
        return fr;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }

    public String getZclx() {
        return zclx;
    }

    public void setZclx(String zclx) {
        this.zclx = zclx;
    }

    public String getZcsd() {
        return zcsd;
    }

    public void setZcsd(String zcsd) {
        this.zcsd = zcsd;
    }

    public String getJydz() {
        return jydz;
    }

    public void setJydz(String jydz) {
        this.jydz = jydz;
    }

    public List<Zss> getZssList() {
        return zssList;
    }

    public void setZssList(List<Zss> zssList) {
        this.zssList = zssList;
    }

    public List<RegisterInfo> getRegisterInfoList() {
        return registerInfoList;
    }

    public void setRegisterInfoList(List<RegisterInfo> registerInfoList) {
        this.registerInfoList = registerInfoList;
    }

    public static class Zss {
        private String zzlb; //资质类别
        private String zzzsh; //资质证书号
        private String zzmc; //资质名称
        private String fzrq; //发证日期
        private String zsyxq; //证书有效期
        private String fzjg; //发证机关

        public String getZzlb() {
            return zzlb;
        }

        public void setZzlb(String zzlb) {
            this.zzlb = zzlb;
        }

        public String getZzzsh() {
            return zzzsh;
        }

        public void setZzzsh(String zzzsh) {
            this.zzzsh = zzzsh;
        }

        public String getZzmc() {
            return zzmc;
        }

        public void setZzmc(String zzmc) {
            this.zzmc = zzmc;
        }

        public String getFzrq() {
            return fzrq;
        }

        public void setFzrq(String fzrq) {
            this.fzrq = fzrq;
        }

        public String getZsyxq() {
            return zsyxq;
        }

        public void setZsyxq(String zsyxq) {
            this.zsyxq = zsyxq;
        }

        public String getFzjg() {
            return fzjg;
        }

        public void setFzjg(String fzjg) {
            this.fzjg = fzjg;
        }
    }
}
