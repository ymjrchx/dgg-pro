package net.dgg.gspt.dqc.params;

/**
 * Created by jiangsh on 2018/7/9 14:54
 */
public class EntlibParam {
    private int pageIndex;
    private int pageSize;

    private String zzlb; //资质类别
    private String zzmc; //资质名称
    private String qymc; //企业名称
    private String xydm; //统一社会信用代码
    private String zsbh; //证书编号
    private String frdb; //企业法人代表
    private String ssgljg; //所属管理机构
    private String zcAddress; //企业注册属地

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getZzlb() {
        return zzlb;
    }

    public void setZzlb(String zzlb) {
        this.zzlb = zzlb;
    }

    public String getZzmc() {
        return zzmc;
    }

    public void setZzmc(String zzmc) {
        this.zzmc = zzmc;
    }

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    public String getXydm() {
        return xydm;
    }

    public void setXydm(String xydm) {
        this.xydm = xydm;
    }

    public String getZsbh() {
        return zsbh;
    }

    public void setZsbh(String zsbh) {
        this.zsbh = zsbh;
    }

    public String getFrdb() {
        return frdb;
    }

    public void setFrdb(String frdb) {
        this.frdb = frdb;
    }

    public String getSsgljg() {
        return ssgljg;
    }

    public void setSsgljg(String ssgljg) {
        this.ssgljg = ssgljg;
    }

    public String getZcAddress() {
        return zcAddress;
    }

    public void setZcAddress(String zcAddress) {
        this.zcAddress = zcAddress;
    }
}
