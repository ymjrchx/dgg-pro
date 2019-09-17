package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

import java.util.List;

/**
 *
 * 经营状况查询
 * Created by jiangsh on 2018/6/5 11:20
 */
public class Jyzk {
    private List<Xzxkgsj> xzxkgsjs; //行政许可 [工商局]
    private List<Xzxkxyzg> xzxkxyzg; //行政许可 [信用中国]
    private List<Swxy> swxys; //税务信用
    private List<Product> products; //产品信息
    private List<Rzxx> rzxxs; //融资信息
    private List<Zdbxx> zdbxx; //招投标信息
    private List<Zp> zp; //招聘
    private List<Cwzl> cwzl; //财务总览
    private List<Wxgzh> wxgzh; //微信公众号
    private List<Ygxx> ygxxes; //员工信息
    private List<Gdxx> gdxxes; //购地信息
    private List<Zzzs> zzzs; //资质证书
    private List<Ccjc> ccjcs; //抽查检查
    private List<Swpj> swpjs; //税务评级

    private String landPublicity; //地块公示
    private String landTransfer; //土地转让
    private String obligations; //债权信息
    private String researchRepor; //公告研报

    public String getObligations() {
        return obligations;
    }

    public void setObligations(String obligations) {
        this.obligations = obligations;
    }

    public String getResearchRepor() {
        return researchRepor;
    }

    public void setResearchRepor(String researchRepor) {
        this.researchRepor = researchRepor;
    }

    public List<Swpj> getSwpjs() {
        return swpjs;
    }

    public void setSwpjs(List<Swpj> swpjs) {
        this.swpjs = swpjs;
    }

    public String getLandPublicity() {
        return landPublicity;
    }

    public void setLandPublicity(String landPublicity) {
        this.landPublicity = landPublicity;
    }

    public String getLandTransfer() {
        return landTransfer;
    }

    public void setLandTransfer(String landTransfer) {
        this.landTransfer = landTransfer;
    }

    public List<Ccjc> getCcjcs() {
        return ccjcs;
    }

    public void setCcjcs(List<Ccjc> ccjcs) {
        this.ccjcs = ccjcs;
    }

    public List<Zzzs> getZzzs() {
        return zzzs;
    }

    public void setZzzs(List<Zzzs> zzzs) {
        this.zzzs = zzzs;
    }

    private String bondCredit; //债券信用

    public List<Gdxx> getGdxxes() {
        return gdxxes;
    }

    public void setGdxxes(List<Gdxx> gdxxes) {
        this.gdxxes = gdxxes;
    }

    public String getBondCredit() {
        return bondCredit;
    }

    public void setBondCredit(String bondCredit) {
        this.bondCredit = bondCredit;
    }

    public List<Ygxx> getYgxxes() {
        return ygxxes;
    }

    public void setYgxxes(List<Ygxx> ygxxes) {
        this.ygxxes = ygxxes;
    }

    public List<Xzxkgsj> getXzxkgsjs() {
        return xzxkgsjs;
    }

    public void setXzxkgsjs(List<Xzxkgsj> xzxkgsjs) {
        this.xzxkgsjs = xzxkgsjs;
    }

    public List<Xzxkxyzg> getXzxkxyzg() {
        return xzxkxyzg;
    }

    public void setXzxkxyzg(List<Xzxkxyzg> xzxkxyzg) {
        this.xzxkxyzg = xzxkxyzg;
    }

    public List<Swxy> getSwxys() {
        return swxys;
    }

    public void setSwxys(List<Swxy> swxys) {
        this.swxys = swxys;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Rzxx> getRzxxs() {
        return rzxxs;
    }

    public void setRzxxs(List<Rzxx> rzxxs) {
        this.rzxxs = rzxxs;
    }

    public List<Zdbxx> getZdbxx() {
        return zdbxx;
    }

    public void setZdbxx(List<Zdbxx> zdbxx) {
        this.zdbxx = zdbxx;
    }

    public List<Zp> getZp() {
        return zp;
    }

    public void setZp(List<Zp> zp) {
        this.zp = zp;
    }

    public List<Cwzl> getCwzl() {
        return cwzl;
    }

    public void setCwzl(List<Cwzl> cwzl) {
        this.cwzl = cwzl;
    }

    public List<Wxgzh> getWxgzh() {
        return wxgzh;
    }

    public void setWxgzh(List<Wxgzh> wxgzh) {
        this.wxgzh = wxgzh;
    }

    public static class Swpj{
        private String tNum; //纳税人识别号
        private String tRater; //纳税评级
        private String tSectionr; //评价单位
        private String tYear; //年份

        public String gettNum() {
            return tNum;
        }

        public void settNum(String tNum) {
            this.tNum = tNum;
        }

        public String gettRater() {
            return tRater;
        }

        public void settRater(String tRater) {
            this.tRater = tRater;
        }

        public String gettSectionr() {
            return tSectionr;
        }

        public void settSectionr(String tSectionr) {
            this.tSectionr = tSectionr;
        }

        public String gettYear() {
            return tYear;
        }

        public void settYear(String tYear) {
            this.tYear = tYear;
        }
    }

    public static class Ccjc{
        private String ccOrgan; //检查实施机关
        private String ccType; //类型
        private String ccResult; //结果
        private String ccTime; //日期

        public String getCcOrgan() {
            return ccOrgan;
        }

        public void setCcOrgan(String ccOrgan) {
            this.ccOrgan = ccOrgan;
        }

        public String getCcType() {
            return ccType;
        }

        public void setCcType(String ccType) {
            this.ccType = ccType;
        }

        public String getCcResult() {
            return ccResult;
        }

        public void setCcResult(String ccResult) {
            this.ccResult = ccResult;
        }

        public String getCcTime() {
            return ccTime;
        }

        public void setCcTime(String ccTime) {
            this.ccTime = ccTime;
        }
    }

    public static class Zzzs{
        private String cName; // 证书名称
        private String cType; //证书类型
        private String cNum; //证书编号
        private String cGetDate; //发证日期
        private String cEndDate; //截止日期
        private String cstaet; //状态
        private String cbeizhu; //备注

        public String getcName() {
            return cName;
        }

        public void setcName(String cName) {
            this.cName = cName;
        }

        public String getcType() {
            return cType;
        }

        public void setcType(String cType) {
            this.cType = cType;
        }

        public String getcNum() {
            return cNum;
        }

        public void setcNum(String cNum) {
            this.cNum = cNum;
        }

        public String getcGetDate() {
            return cGetDate;
        }

        public void setcGetDate(String cGetDate) {
            this.cGetDate = cGetDate;
        }

        public String getcEndDate() {
            return cEndDate;
        }

        public void setcEndDate(String cEndDate) {
            this.cEndDate = cEndDate;
        }

        public String getCstaet() {
            return cstaet;
        }

        public void setCstaet(String cstaet) {
            this.cstaet = cstaet;
        }

        public String getCbeizhu() {
            return cbeizhu;
        }

        public void setCbeizhu(String cbeizhu) {
            this.cbeizhu = cbeizhu;
        }
    }

    public static class Gdxx{
        private String bliLocat; //土地坐落
        private String bliUse; //土地用途
        private String bliAcreage; //总面积（公顷）
        private String bliArea; //行政区
        private String bliWay; //供应方式
        private String bliDate; //签订日期

        public String getBliLocat() {
            return bliLocat;
        }

        public void setBliLocat(String bliLocat) {
            this.bliLocat = bliLocat;
        }

        public String getBliUse() {
            return bliUse;
        }

        public void setBliUse(String bliUse) {
            this.bliUse = bliUse;
        }

        public String getBliAcreage() {
            return bliAcreage;
        }

        public void setBliAcreage(String bliAcreage) {
            this.bliAcreage = bliAcreage;
        }

        public String getBliArea() {
            return bliArea;
        }

        public void setBliArea(String bliArea) {
            this.bliArea = bliArea;
        }

        public String getBliWay() {
            return bliWay;
        }

        public void setBliWay(String bliWay) {
            this.bliWay = bliWay;
        }

        public String getBliDate() {
            return bliDate;
        }

        public void setBliDate(String bliDate) {
            this.bliDate = bliDate;
        }
    }

    public static class Xzxkgsj {
        private String fileNo; //许可文件编号
        private String fileName; //许可文件名称
        private String startDate; //有效期自
        private String endDate; //有效期至
        private String xkjg; //许可机关
        private String xkConent; //许可内容

        public String getFileNo() {
            return fileNo;
        }

        public void setFileNo(String fileNo) {
            this.fileNo = fileNo;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getXkjg() {
            return xkjg;
        }

        public void setXkjg(String xkjg) {
            this.xkjg = xkjg;
        }

        public String getXkConent() {
            return xkConent;
        }

        public void setXkConent(String xkConent) {
            this.xkConent = xkConent;
        }
    }

    public static class Xzxkxyzg {
        private String projectName; //项目名称
        private String area; //地域
        private String date; //决定日期
        private String xyEndDate; //截止日期
        private String conent; //内容
        private String xyOrgan; //许可机关
        private String xyNum; //决定文书号
        private String xyState; //许可状态
        private String xyType; //审批类别

        public String getXyEndDate() {
            return xyEndDate;
        }

        public void setXyEndDate(String xyEndDate) {
            this.xyEndDate = xyEndDate;
        }

        public String getXyOrgan() {
            return xyOrgan;
        }

        public void setXyOrgan(String xyOrgan) {
            this.xyOrgan = xyOrgan;
        }

        public String getXyNum() {
            return xyNum;
        }

        public void setXyNum(String xyNum) {
            this.xyNum = xyNum;
        }

        public String getXyState() {
            return xyState;
        }

        public void setXyState(String xyState) {
            this.xyState = xyState;
        }

        public String getXyType() {
            return xyType;
        }

        public void setXyType(String xyType) {
            this.xyType = xyType;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getConent() {
            return conent;
        }

        public void setConent(String conent) {
            this.conent = conent;
        }
    }

    public static class Swxy {
        private String year; //评价年度
        private String nsrsbh; //纳税人识别号
        private String nsrxydj; //纳税信用等级
        private String pjdw; //评价单位

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getNsrsbh() {
            return nsrsbh;
        }

        public void setNsrsbh(String nsrsbh) {
            this.nsrsbh = nsrsbh;
        }

        public String getNsrxydj() {
            return nsrxydj;
        }

        public void setNsrxydj(String nsrxydj) {
            this.nsrxydj = nsrxydj;
        }

        public String getPjdw() {
            return pjdw;
        }

        public void setPjdw(String pjdw) {
            this.pjdw = pjdw;
        }
    }

    public static class Product {
        private String url; //产品图片
        private String imgName; //图片name
        private String path; //产品path
        private String name; //产品名
        private String rzxx; //融资信息
        private String time; //成立时间
        private String area; //所属地
        private String msg; //产品介绍

        public String getImgName() {
            return imgName;
        }

        public void setImgName(String imgName) {
            this.imgName = imgName;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRzxx() {
            return rzxx;
        }

        public void setRzxx(String rzxx) {
            this.rzxx = rzxx;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public static class Rzxx {
        private String date; //日期
        private String name; //产品名称
        private String level; //级别
        private String money; //金额
        private String dzf; //投资方

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getDzf() {
            return dzf;
        }

        public void setDzf(String dzf) {
            this.dzf = dzf;
        }
    }

    public static class Zdbxx {
        private String msg; //描述
        private String date; //发布时间
        private String area; //所属地区
        private String type; //项目分类
        private String bMan; //采购人

        public String getbMan() {
            return bMan;
        }

        public void setbMan(String bMan) {
            this.bMan = bMan;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Zp {
        private String date; //发布时间
        private String zpzw; //招聘职位
        private String yx; //月薪
        private String xl; //学历
        private String jy; //经验
        private String city; //所在城市

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getZpzw() {
            return zpzw;
        }

        public void setZpzw(String zpzw) {
            this.zpzw = zpzw;
        }

        public String getYx() {
            return yx;
        }

        public void setYx(String yx) {
            this.yx = yx;
        }

        public String getXl() {
            return xl;
        }

        public void setXl(String xl) {
            this.xl = xl;
        }

        public String getJy() {
            return jy;
        }

        public void setJy(String jy) {
            this.jy = jy;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }

    public static class Cwzl {
        private String level; //公司实力等级
        private String nsqj; //纳税区间
        private String jlrl; //销售净利润率
        private String mll; //销售毛利率

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getNsqj() {
            return nsqj;
        }

        public void setNsqj(String nsqj) {
            this.nsqj = nsqj;
        }

        public String getJlrl() {
            return jlrl;
        }

        public void setJlrl(String jlrl) {
            this.jlrl = jlrl;
        }

        public String getMll() {
            return mll;
        }

        public void setMll(String mll) {
            this.mll = mll;
        }
    }

    public static class Wxgzh {
        private String head; //头像图片名称
        private String headPath; //头像图片path
        private String gzh; //微信公众号
        private String wxh; //微信号
        private String ewm; //二维码图片名称
        private String jj; //简介

        private String headUrl; //头像url
        private String ewmUrl; //二维码url
        private String ewmPath; //二维码path

        public String getEwmPath() {
            return ewmPath;
        }

        public void setEwmPath(String ewmPath) {
            this.ewmPath = ewmPath;
        }

        public String getHeadPath() {
            return headPath;
        }

        public void setHeadPath(String headPath) {
            this.headPath = headPath;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getEwmUrl() {
            return ewmUrl;
        }

        public void setEwmUrl(String ewmUrl) {
            this.ewmUrl = ewmUrl;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getGzh() {
            return gzh;
        }

        public void setGzh(String gzh) {
            this.gzh = gzh;
        }

        public String getWxh() {
            return wxh;
        }

        public void setWxh(String wxh) {
            this.wxh = wxh;
        }

        public String getEwm() {
            return ewm;
        }

        public void setEwm(String ewm) {
            this.ewm = ewm;
        }

        public String getJj() {
            return jj;
        }

        public void setJj(String jj) {
            this.jj = jj;
        }
    }
}
