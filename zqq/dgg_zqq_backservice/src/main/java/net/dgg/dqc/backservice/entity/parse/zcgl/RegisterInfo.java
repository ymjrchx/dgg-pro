package net.dgg.dqc.backservice.entity.parse.zcgl;

import java.util.List;

public class RegisterInfo {
        private String qymc; //企业名称
        private String name; //注册人员姓名
        private String url; //注册人员链接
        private String xb; //性别
        private String zjlx; //证件类型
        private String sfzh; //证件号码

        private List<PeopleZss> peopleZsseList; //执业注册信息

        public String getQymc() {
            return qymc;
        }

        public void setQymc(String qymc) {
            this.qymc = qymc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getXb() {
            return xb;
        }

        public void setXb(String xb) {
            this.xb = xb;
        }

        public String getZjlx() {
            return zjlx;
        }

        public void setZjlx(String zjlx) {
            this.zjlx = zjlx;
        }

        public String getSfzh() {
            return sfzh;
        }

        public void setSfzh(String sfzh) {
            this.sfzh = sfzh;
        }

        public List<PeopleZss> getPeopleZsseList() {
            return peopleZsseList;
        }

        public void setPeopleZsseList(List<PeopleZss> peopleZsseList) {
            this.peopleZsseList = peopleZsseList;
        }

    public static class PeopleZss {
        private String zclb; //证书等级
        private String zsbh; //注册编号
        private String zyyzh; //执业印章号
        private String yxq; //有效期
        private String zcdw; //注册单位
        private String zczy; //注册专业

        public String getZczy() {
            return zczy;
        }

        public void setZczy(String zczy) {
            this.zczy = zczy;
        }

        public String getZclb() {
            return zclb;
        }

        public void setZclb(String zclb) {
            this.zclb = zclb;
        }

        public String getZsbh() {
            return zsbh;
        }

        public void setZsbh(String zsbh) {
            this.zsbh = zsbh;
        }

        public String getZyyzh() {
            return zyyzh;
        }

        public void setZyyzh(String zyyzh) {
            this.zyyzh = zyyzh;
        }

        public String getYxq() {
            return yxq;
        }

        public void setYxq(String yxq) {
            this.yxq = yxq;
        }

        public String getZcdw() {
            return zcdw;
        }

        public void setZcdw(String zcdw) {
            this.zcdw = zcdw;
        }
    }

}
