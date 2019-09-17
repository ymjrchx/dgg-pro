package net.dgg.dqc.backservice.entity.parse;

import java.util.List;

public class Invest {

    private List<Gqcz> gqczs;
    private List<Aa> aas; //行政处罚 [工商局]
    private List<Bb> bbs; //行政处罚 [信用中国]

    public List<Aa> getAas() {
        return aas;
    }

    public void setAas(List<Aa> aas) {
        this.aas = aas;
    }

    public List<Bb> getBbs() {
        return bbs;
    }

    public void setBbs(List<Bb> bbs) {
        this.bbs = bbs;
    }

    public List<Gqcz> getGqczs() {
        return gqczs;
    }

    public void setGqczs(List<Gqcz> gqczs) {
        this.gqczs = gqczs;
    }

    public static class Gqcz {
        private String djno; //登记编号
        private String czr; //出质人
        private String zqr; //质权人
        private String czgqse; //出质股权数额
        private String djrq; //设立登记日期
        private String status; //状态

        public String getDjno() {
            return djno;
        }

        public void setDjno(String djno) {
            this.djno = djno;
        }

        public String getCzr() {
            return czr;
        }

        public void setCzr(String czr) {
            this.czr = czr;
        }

        public String getZqr() {
            return zqr;
        }

        public void setZqr(String zqr) {
            this.zqr = zqr;
        }

        public String getCzgqse() {
            return czgqse;
        }

        public void setCzgqse(String czgqse) {
            this.czgqse = czgqse;
        }

        public String getDjrq() {
            return djrq;
        }

        public void setDjrq(String djrq) {
            this.djrq = djrq;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class Aa {
        private String wsh; //决定文书号
        private String wfxwlx; //违法行为类型
        private String xzcflr; //行政处罚内容
        private String gsdate; //公示日期
        private String jdjg; //决定机关
        private String jddate; //决定日期

        public String getWsh() {
            return wsh;
        }

        public void setWsh(String wsh) {
            this.wsh = wsh;
        }

        public String getWfxwlx() {
            return wfxwlx;
        }

        public void setWfxwlx(String wfxwlx) {
            this.wfxwlx = wfxwlx;
        }

        public String getXzcflr() {
            return xzcflr;
        }

        public void setXzcflr(String xzcflr) {
            this.xzcflr = xzcflr;
        }

        public String getGsdate() {
            return gsdate;
        }

        public void setGsdate(String gsdate) {
            this.gsdate = gsdate;
        }

        public String getJdjg() {
            return jdjg;
        }

        public void setJdjg(String jdjg) {
            this.jdjg = jdjg;
        }

        public String getJddate() {
            return jddate;
        }

        public void setJddate(String jddate) {
            this.jddate = jddate;
        }
    }

    public static class Bb {
        private String wsh; //决定文书号
        private String name; //处罚名称
        private String area; //地域
        private String time; //决定时间

        public String getWsh() {
            return wsh;
        }

        public void setWsh(String wsh) {
            this.wsh = wsh;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

}