package net.dgg.dqc.backservice.entity;


import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author 刘阳
 * @ClassName <test>
 * @despriction 企业名录
 * @create 2018/07/24 15:18
 **/
//@org.springframework.data.elasticsearch.annotations.Document(indexName = "test", type = "new_results")
@Document(collection = "all_results")
public class CompanyNameResult extends MongoBaseEntity implements Serializable {

    /**
     * webSource : https://www.tianyancha.com/
     * docs : {"background":{"baseInfo":{"legalMan":"赵保具","registerAddress":"河南省济源市御驾北六巷21号","creditCode":"92419001MA45ER324N","companyEmail":"","registerTime":"2018-07-02","companyTel":"","businessScope":"百货零售（涉及许可经营项目，应取得相关部门许可后方可经营）","registerMoney":"-","companyWebeUrl":"","companyProvince":"省直辖县级行政区划"}}}
     * companyName : 济源市翔和百货经营部
     * reportFlag : 0
     * _id : ed2dc0d60440387febbca718545cfe59
     * companyUrl :
     * allTime : {"usedWeb":{"qixin_com":{"flag":0,"getTime":0,"state":0,"endTime":0},"weimao_com":{"flag":0,"getTime":0,"state":0,"endTime":0},"qichacha_com":{"flag":0,"getTime":0,"state":0,"endTime":0},"gxzg_com":{"flag":0,"getTime":0,"state":0,"endTime":0},"tyc_com":{"flag":0,"getTime":"1533532198209","state":0,"endTime":0},"eqicha_com":{"flag":0,"getTime":0,"state":0,"endTime":0}},"enterTime":{"collectTime":"1532580027461","updateTime":0}}
     */
    private String webSource;
    private DocsEntity docs;
    private String companyName;
    private String reportFlag;

    private String companyUrl;
    private AllTimeEntity allTime;

    public void setWebSource(String webSource) {
        this.webSource = webSource;
    }

    public void setDocs(DocsEntity docs) {
        this.docs = docs;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setReportFlag(String reportFlag) {
        this.reportFlag = reportFlag;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public void setAllTime(AllTimeEntity allTime) {
        this.allTime = allTime;
    }

    public String getWebSource() {
        return webSource;
    }

    public DocsEntity getDocs() {
        return docs;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getReportFlag() {
        return reportFlag;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public AllTimeEntity getAllTime() {
        return allTime;
    }

    public class DocsEntity {
        /**
         * background : {"baseInfo":{"legalMan":"赵保具","registerAddress":"河南省济源市御驾北六巷21号","creditCode":"92419001MA45ER324N","companyEmail":"","registerTime":"2018-07-02","companyTel":"","businessScope":"百货零售（涉及许可经营项目，应取得相关部门许可后方可经营）","registerMoney":"-","companyWebeUrl":"","companyProvince":"省直辖县级行政区划"}}
         */
        private BackgroundEntity background;

        public void setBackground(BackgroundEntity background) {
            this.background = background;
        }

        public BackgroundEntity getBackground() {
            return background;
        }

        public class BackgroundEntity {
            /**
             * baseInfo : {"legalMan":"赵保具","registerAddress":"河南省济源市御驾北六巷21号","creditCode":"92419001MA45ER324N","companyEmail":"","registerTime":"2018-07-02","companyTel":"","businessScope":"百货零售（涉及许可经营项目，应取得相关部门许可后方可经营）","registerMoney":"-","companyWebeUrl":"","companyProvince":"省直辖县级行政区划"}
             */
            private BaseInfoEntity baseInfo;

            public void setBaseInfo(BaseInfoEntity baseInfo) {
                this.baseInfo = baseInfo;
            }

            public BaseInfoEntity getBaseInfo() {
                return baseInfo;
            }

            public class BaseInfoEntity {
                /**
                 * legalMan : 赵保具
                 * registerAddress : 河南省济源市御驾北六巷21号
                 * creditCode : 92419001MA45ER324N
                 * companyEmail :
                 * registerTime : 2018-07-02
                 * companyTel :
                 * businessScope : 百货零售（涉及许可经营项目，应取得相关部门许可后方可经营）
                 * registerMoney : -
                 * companyWebeUrl :
                 * companyProvince : 省直辖县级行政区划
                 */
                private String legalMan;
                private String registerAddress;
                private String creditCode;
                private String companyEmail;
                private String registerTime;
                private String companyTel;
                private String businessScope;
                private String registerMoney;
                private String companyWebeUrl;
                private String companyProvince;

                public void setLegalMan(String legalMan) {
                    this.legalMan = legalMan;
                }

                public void setRegisterAddress(String registerAddress) {
                    this.registerAddress = registerAddress;
                }

                public void setCreditCode(String creditCode) {
                    this.creditCode = creditCode;
                }

                public void setCompanyEmail(String companyEmail) {
                    this.companyEmail = companyEmail;
                }

                public void setRegisterTime(String registerTime) {
                    this.registerTime = registerTime;
                }

                public void setCompanyTel(String companyTel) {
                    this.companyTel = companyTel;
                }

                public void setBusinessScope(String businessScope) {
                    this.businessScope = businessScope;
                }

                public void setRegisterMoney(String registerMoney) {
                    this.registerMoney = registerMoney;
                }

                public void setCompanyWebeUrl(String companyWebeUrl) {
                    this.companyWebeUrl = companyWebeUrl;
                }

                public void setCompanyProvince(String companyProvince) {
                    this.companyProvince = companyProvince;
                }

                public String getLegalMan() {
                    return legalMan;
                }

                public String getRegisterAddress() {
                    return registerAddress;
                }

                public String getCreditCode() {
                    return creditCode;
                }

                public String getCompanyEmail() {
                    return companyEmail;
                }

                public String getRegisterTime() {
                    return registerTime;
                }

                public String getCompanyTel() {
                    return companyTel;
                }

                public String getBusinessScope() {
                    return businessScope;
                }

                public String getRegisterMoney() {
                    return registerMoney;
                }

                public String getCompanyWebeUrl() {
                    return companyWebeUrl;
                }

                public String getCompanyProvince() {
                    return companyProvince;
                }
            }
        }
    }

    public class AllTimeEntity {
        /**
         * usedWeb : {"qixin_com":{"flag":0,"getTime":0,"state":0,"endTime":0},"weimao_com":{"flag":0,"getTime":0,"state":0,"endTime":0},"qichacha_com":{"flag":0,"getTime":0,"state":0,"endTime":0},"gxzg_com":{"flag":0,"getTime":0,"state":0,"endTime":0},"tyc_com":{"flag":0,"getTime":"1533532198209","state":0,"endTime":0},"eqicha_com":{"flag":0,"getTime":0,"state":0,"endTime":0}}
         * enterTime : {"collectTime":"1532580027461","updateTime":0}
         */
        private UsedWebEntity usedWeb;
        private EnterTimeEntity enterTime;

        public void setUsedWeb(UsedWebEntity usedWeb) {
            this.usedWeb = usedWeb;
        }

        public void setEnterTime(EnterTimeEntity enterTime) {
            this.enterTime = enterTime;
        }

        public UsedWebEntity getUsedWeb() {
            return usedWeb;
        }

        public EnterTimeEntity getEnterTime() {
            return enterTime;
        }

        public class UsedWebEntity {
            /**
             * qixin_com : {"flag":0,"getTime":0,"state":0,"endTime":0}
             * weimao_com : {"flag":0,"getTime":0,"state":0,"endTime":0}
             * qichacha_com : {"flag":0,"getTime":0,"state":0,"endTime":0}
             * gxzg_com : {"flag":0,"getTime":0,"state":0,"endTime":0}
             * tyc_com : {"flag":0,"getTime":"1533532198209","state":0,"endTime":0}
             * eqicha_com : {"flag":0,"getTime":0,"state":0,"endTime":0}
             */
            private Qixin_comEntity qixin_com;
            private Weimao_comEntity weimao_com;
            private Qichacha_comEntity qichacha_com;
            private Gxzg_comEntity gxzg_com;
            private Tyc_comEntity tyc_com;
            private Eqicha_comEntity eqicha_com;

            public void setQixin_com(Qixin_comEntity qixin_com) {
                this.qixin_com = qixin_com;
            }

            public void setWeimao_com(Weimao_comEntity weimao_com) {
                this.weimao_com = weimao_com;
            }

            public void setQichacha_com(Qichacha_comEntity qichacha_com) {
                this.qichacha_com = qichacha_com;
            }

            public void setGxzg_com(Gxzg_comEntity gxzg_com) {
                this.gxzg_com = gxzg_com;
            }

            public void setTyc_com(Tyc_comEntity tyc_com) {
                this.tyc_com = tyc_com;
            }

            public void setEqicha_com(Eqicha_comEntity eqicha_com) {
                this.eqicha_com = eqicha_com;
            }

            public Qixin_comEntity getQixin_com() {
                return qixin_com;
            }

            public Weimao_comEntity getWeimao_com() {
                return weimao_com;
            }

            public Qichacha_comEntity getQichacha_com() {
                return qichacha_com;
            }

            public Gxzg_comEntity getGxzg_com() {
                return gxzg_com;
            }

            public Tyc_comEntity getTyc_com() {
                return tyc_com;
            }

            public Eqicha_comEntity getEqicha_com() {
                return eqicha_com;
            }

            public class Qixin_comEntity {
                /**
                 * flag : 0
                 * getTime : 0
                 * state : 0
                 * endTime : 0
                 */
                private String flag;
                private String getTime;
                private String state;
                private String endTime;

                public void setFlag(String flag) {
                    this.flag = flag;
                }

                public void setGetTime(String getTime) {
                    this.getTime = getTime;
                }

                public void setState(String state) {
                    this.state = state;
                }

                public void setEndTime(String endTime) {
                    this.endTime = endTime;
                }

                public String getFlag() {
                    return flag;
                }

                public String getGetTime() {
                    return getTime;
                }

                public String getState() {
                    return state;
                }

                public String getEndTime() {
                    return endTime;
                }
            }

            public class Weimao_comEntity {
                /**
                 * flag : 0
                 * getTime : 0
                 * state : 0
                 * endTime : 0
                 */
                private String flag;
                private String getTime;
                private String state;
                private String endTime;

                public void setFlag(String flag) {
                    this.flag = flag;
                }

                public void setGetTime(String getTime) {
                    this.getTime = getTime;
                }

                public void setState(String state) {
                    this.state = state;
                }

                public void setEndTime(String endTime) {
                    this.endTime = endTime;
                }

                public String getFlag() {
                    return flag;
                }

                public String getGetTime() {
                    return getTime;
                }

                public String getState() {
                    return state;
                }

                public String getEndTime() {
                    return endTime;
                }
            }

            public class Qichacha_comEntity {
                /**
                 * flag : 0
                 * getTime : 0
                 * state : 0
                 * endTime : 0
                 */
                private String flag;
                private String getTime;
                private String state;
                private String endTime;

                public void setFlag(String flag) {
                    this.flag = flag;
                }

                public void setGetTime(String getTime) {
                    this.getTime = getTime;
                }

                public void setState(String state) {
                    this.state = state;
                }

                public void setEndTime(String endTime) {
                    this.endTime = endTime;
                }

                public String getFlag() {
                    return flag;
                }

                public String getGetTime() {
                    return getTime;
                }

                public String getState() {
                    return state;
                }

                public String getEndTime() {
                    return endTime;
                }
            }

            public class Gxzg_comEntity {
                /**
                 * flag : 0
                 * getTime : 0
                 * state : 0
                 * endTime : 0
                 */
                private String flag;
                private String getTime;
                private String state;
                private String endTime;

                public void setFlag(String flag) {
                    this.flag = flag;
                }

                public void setGetTime(String getTime) {
                    this.getTime = getTime;
                }

                public void setState(String state) {
                    this.state = state;
                }

                public void setEndTime(String endTime) {
                    this.endTime = endTime;
                }

                public String getFlag() {
                    return flag;
                }

                public String getGetTime() {
                    return getTime;
                }

                public String getState() {
                    return state;
                }

                public String getEndTime() {
                    return endTime;
                }
            }

            public class Tyc_comEntity {
                /**
                 * flag : 0
                 * getTime : 1533532198209
                 * state : 0
                 * endTime : 0
                 */
                private String flag;
                private String getTime;
                private String state;
                private String endTime;

                public void setFlag(String flag) {
                    this.flag = flag;
                }

                public void setGetTime(String getTime) {
                    this.getTime = getTime;
                }

                public void setState(String state) {
                    this.state = state;
                }

                public void setEndTime(String endTime) {
                    this.endTime = endTime;
                }

                public String getFlag() {
                    return flag;
                }

                public String getGetTime() {
                    return getTime;
                }

                public String getState() {
                    return state;
                }

                public String getEndTime() {
                    return endTime;
                }
            }

            public class Eqicha_comEntity {
                /**
                 * flag : 0
                 * getTime : 0
                 * state : 0
                 * endTime : 0
                 */
                private String flag;
                private String getTime;
                private String state;
                private String endTime;

                public void setFlag(String flag) {
                    this.flag = flag;
                }

                public void setGetTime(String getTime) {
                    this.getTime = getTime;
                }

                public void setState(String state) {
                    this.state = state;
                }

                public void setEndTime(String endTime) {
                    this.endTime = endTime;
                }

                public String getFlag() {
                    return flag;
                }

                public String getGetTime() {
                    return getTime;
                }

                public String getState() {
                    return state;
                }

                public String getEndTime() {
                    return endTime;
                }
            }
        }

        public class EnterTimeEntity {
            /**
             * collectTime : 1532580027461
             * updateTime : 0
             */
            private String collectTime;
            private String updateTime;

            public void setCollectTime(String collectTime) {
                this.collectTime = collectTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getCollectTime() {
                return collectTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }
        }
    }
}
