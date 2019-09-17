<template>
    <div>
        <toolBox/>
        <mini :hasFloor="1" />
        <div class="w">
            <div class="patentSearchList-wrap searchList-wrap w">
                <div class="search-type-logo">
                    <h2 class="weight">
                        <a>专利搜索</a>
                        <span class="icon-beta">beta</span>
                    </h2>
                    <p>丨最全数据库360°查询丨</p>
                </div>
                <div class="patent-searchBox-container search">
                    <!-- start 专利搜索框 -->
                    <div class="patent-searchBox clearfix">
                        <div class="qds-search-common">
                            <input type="text" class="search-text" placeholder="请输入搜索关键字，多个关键字用“、”号隔开" name="keyWords" v-model="keyWord" @keyup.enter='submitList'>
                            <input type="submit" value="搜索" class="icon icon-search-btn search-btn button" @click="submitList">
                            <a data-href="javascript:;" class="clearAll" @click=" keyWord = '' " title="清除所有条件" v-show="keyWord">×</a>
                            <div>
                                <ul class="keyWord-box songti"></ul>
                            </div>
                        </div>
                    </div>
                    <!-- end 专利搜索框 -->

                    <!-- start 搜索框下面的5个条件 -->
                    <div class="patent-condition clearfix selectAll">
                        <a class="list" v-for='(item,idx) in typeArr' :key='idx' :class="{'selected': !typeIndexArr[idx]}" @click="typeSelect(idx)">
                            <i class="icon icon-checkbox-patent"></i>
                            <span class="text songti">{{item.name}}</span>
                        </a>
                    </div>
                    <!-- end 搜索框下面的5个条件 -->
                </div>
                <div class="clearfix"></div>
                <!-- start category-box 关联词 -->
                <!-- <div class="related-words songti">
                    <dl class="clearfix">
                        <dt>关联词：</dt>
                        <dd>
                            <a class="show-more" data-href="javascript:;">
                                <i class="icon-triangle-up icon-selected"></i>
                                <span>显示全部</span>
                            </a>
                        </dd>

                    </dl>
                </div> -->
                <!-- end category-box 关联词 -->
                <!-- start 筛选条件 -->
                <div class="query-condition" id="query-condition">
                    <ul class="list-box clearfix">
                        <li class="list">筛选</li>
                        <li class="list arrow">&gt;</li>
                        <li class="list" v-if='applyPerson'>
                            <a class="text-border" @click=" applyPerson =''">
                                <span class="text">申请人/专利权人：{{applyPerson}}</span>
                                <i class="icon icon-close" title="关闭">x</i>
                            </a>
                        </li>
                        <li class="list" v-if='this.applyDate'>
                            <a class="text-border" @click=" applyDate =''">
                                <span class="text" href="#">申请日:{{applyDate}}</span>
                                <i class="icon icon-close" title="关闭">x</i>
                            </a>
                        </li>
                        <!-- <li class="list" v-if='typeNum'>
                            <a class="text-border" @click=" typeNum =''">
                                <span class="text" href="#">主分类号:{{typeNum}}</span>
                                <i class="icon icon-close" title="关闭">x</i>
                            </a>
                        </li> -->
                    </ul>
                </div>
                <!-- end 筛选条件 -->

                <!-- start 排序条件 -->
                <div class="sort-condition songti">
                    <i class="icon-masking"></i>
                    <div class="masking-text">
                        <div class="s-left">
                            <dl>
                                <dt>知千秋为您找到相关结果
                                    <i class="col">{{totalNum}}</i> 个</dt>
                            </dl>
                        </div>
                        <div class="s-right">
                            <dl class="s-right-dl">
                                <dt>默认排序：
                                    <a class="selected">相似度</a>
                                </dt>
                            </dl>
                            <!-- <dl>
                                <dt>
                                    <div class="downup">
                                    </div>
                                </dt>
                            </dl> -->
                            <dl class="s-right-dl">
                                <dt>法律状态：</dt>
                            </dl>
                            <dl class="select-price cur" :class="{'open' :openAll}">
                                <dt class="select-title" @mouseover="openAll = 1" @mouseout="openAll = 0">
                                    <span class="text">{{layStatus[layStatusIndex].name}}</span>
                                    <em class="icon icon-arrow-down"></em>
                                </dt>
                                <dd class="select-body" @mouseover="openAll = 1" @mouseout="openAll = 0">
                                    <span class="item" v-for='(item,idx) in layStatus' :key='idx' :class="{'selected': layStatusIndex == idx}" @click.stop=" changgeLayStatusIndex(idx)">{{item.name}}</span>
                                </dd>
                            </dl>
                            <dl class="select-price s-right-dl cur" :class="{'open':openDate}">
                                <dt class="select-title" @mouseover="openDate = 1" @mouseout="openDate = 0">
                                    <span class="text">
                                        {{ dateType ? dateType == 1 ? '公开/公告日': '优先权日':'申请日'}}</span>
                                    <em class="icon icon-arrow-down"></em>
                                </dt>
                                <dd class="select-body" @mouseover="openDate = 1" @mouseout="openDate = 0">
                                    <span class="item" :class="{'selected':dateType == 0}" @click="dateType = 0; openDate = 0">申请日</span>
                                    <span class="item" :class="{'selected':dateType == 1}" @click="dateType = 1; openDate = 0">公开/公告日</span>
                                    <span class="item" :class="{'selected':dateType == 2}" @click="dateType = 2; openDate = 0">优先权日</span>
                                </dd>
                            </dl>
                            <dl class="fill-price">
                                <el-date-picker v-model="timeInterval" type="daterange" size="small" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerBeginDateBefore">
                                </el-date-picker>
                            </dl>
                            <dl class="btn">
                                <dt>
                                    <a class="queding" @click="sureSub">确定</a>
                                </dt>
                                <dt>
                                    <a class="clear" @click="clearAll">清除</a>
                                </dt>
                            </dl>

                        </div>
                    </div>
                </div>
                <!-- end 排序条件 -->
                <!-- start 结果列表展示 -->
                <ul class="result-wrap" style="min-height:600px" v-if='totalNum'>
                    <li class="list" v-for='(item,idx) in listArr' :key='idx'>
                        <h1 class="row-1 clearfix">
                            <span class="type">【{{item.kindCodeDesc}}】</span>
                            <span class="title">
                                <a :href="initUrl(item.applicationNumber)" target="_blank" v-html="heigtCol(item.piInventName)">
                                </a>
                            </span>
                            <!-- 法律状态  -->
                            <span class="law-state bgcolor-1">{{item.legalStatusDesc}}</span>
                        </h1>
                        <div class="row-2 songti clearfix">
                            <a class="application-number sqh" target="_blank" data-href="/index/detail?apn=201330499018.7">申请号：{{item.applicationNumber}}</a>
                            <a class="application-master sqr col" :class="{'active': applyPerson == item.inventorString}" @click="applyPerson = applyPerson ? '': item.inventorString">专利权人:{{item.inventorString}}</a>
                            <a class="application-date sqd col" :class="{'active': applyDate == item.applicationDate}" @click="applyDate = applyDate? '':item.applicationDate">申请日：{{item.applicationDate}}</a>
                            <a target="_blank" data-href="/index/detail?apn=201330499018.7"> 公开/公告号：{{item.publicationNumber}}</a>
                            <a class="main-classification-number flh" data-href="javascript:;">公开（公告）日：{{item.publicationDate}}</a>
                        </div>
                        <div class="row-3 songti">
                            <span class="profiles introduce">
                                <a class="brief" v-html="heigtCol(item.abstracts)">
                                    <!-- {{item.abstracts}} -->
                                </a>
                                <!-- <a class="show-more" data-href="javascript:;">[展开]</a>-->
                            </span>
                        </div>
                        <div class="row-4 songti clearfix">
                            <span class="title">关键词：</span>
                            <a class="search-keyWord word " v-for='(item,idx) in spitArr(item.keyWord)' :key='idx'>{{item}}</a>
                        </div>
                    </li>
                    <!-- start 分页 -->
                    <pagination :totalPage="totalNum" :pageNum="pageIndex" :pageSize="pageSize" @pagechange="pagechange" />
                    <!-- end 分页 -->
                </ul>
                <div style="color:#777;padding:20px 0;height:45%;" v-else>
                    <noFind :keyWord="keyWord" :type="'专利'" />
                </div>
                <!-- start 结果列表展示 -->
            </div>
        </div>
    </div>

</template>

<script>
import mini from "~/components/header/mini";
import pagination from "~/components/pagination.vue";
import { Http } from "~/plugins/axios.js";
import noFind from "~/components/noFind";
import { Jump } from "~/assets/js/common";
import toolBox from "~/components/toolBox";
import axios from "axios";

export default {
    components: {
        pagination,
        noFind,
        mini,
        toolBox
    },
    layout: "foot",
    head() {
        return {
            title: `${this.oldKeyWord}-专注于商标查询、专利查询-知千秋官网`,
            meta: [
                {
                    name: "keywords",
                    hid: "keywords",
                    content:
                        "商标注册，商标免费查询，商标注册查询，商标注册流程及费用，中国商标网，专利申请，专利检索，发明专利"
                },
                {
                    name: "description",
                    hid: "description",
                    content:
                        "知千秋致力于知千秋致力于运营大数据技术、人工智能等技术重新定义知识产权生态链。提供商标查询,商标注册,专利申请,专利查询,版权登记全流程服务。提供免费商标查询Saas工具,智能商标注册0服务费省钱到底,商标查询,商标注册监控,专利申请等服务获得客户的高度认可。"
                }
            ],
            link: [
                {
                    rel: "canonical",
                    content: this.baseUrl
                }
            ]
        };
    },
    async asyncData({ params, redirect, error, env, route }) {
        let keyWord = params.list.split(/\./)[0];

        let requireObj = {
            pageIndex: 1, //开始页
            pageSize: 20, //每页多少条
            type: "发明-发明公开-发明授权-实用新型-外观设计", //类别，all 全部, fmgk 发明公开, fmsq 发明授权, syxx 实用新型, wgsj 外观设计； 若多个，之间用“-”隔开；
            keyWord: keyWord, ////关键字，若多个，之间用“、”隔开；
            dateType: "applicationDate", //日期类型, 传值情况：  申请日：applicationDate， 公开/公告日：publicationDate， 优先权日：piApplyAnnounceDate
            dataStart: "", //日期起
            dataEnd: "", //日期止
            layStatus: "有权-无权-在审", //法律状态
            applyPerson: "", //申请人
            applyDate: ""
        };
        let { data } = await axios.post(
            `${env.baseUrl}patentSearch/searchList`,
            requireObj
        );

        if (data.code == 0) {
            return {
                keyWord: keyWord,
                oldKeyWord: keyWord,
                listArr: data.data.listData,
                totalNum: Number(data.data.totalCount || 0),
                baseUrl: env.webSite + route.fullPath
            };
        } else {
            error({ statusCode: 500, message: "数据获取失败" });
        }

        // 不存在数据
    },
    data() {
        return {
            listArr: [],
            openAll: 0, //打开法律状态
            openDate: 0, //打开申请日
            totalNum: 0,
            pageIndex: 1, //开始页
            pageSize: 20, //每页多少条
            typeArr: [
                {
                    name: "全部",
                    val: "发明-发明公开-发明授权-实用新型-外观设计"
                },
                { name: "发明", val: "发明" },
                { name: "发明公开", val: "发明公开" },
                { name: "发明授权", val: "发明授权" },
                { name: "实用新型", val: "实用新型" },
                { name: "外观设计", val: "外观设计" }
            ],
            typeIndexArr: [0, 0, 0, 0, 0, 0],
            type: "发明-发明公开-发明授权-实用新型-外观设计", //类别, 发明公开,  发明授权,  实用新型, 外观设计； 直接中文若多个，之间用“-”隔开；
            keyWord: "", ////关键字，若多个，之间用“、”隔开；
            dateType: 0, //日期类型, 传值情况：  申请日：applicationDate， 公开/公告日：publicationDate， 优先权日：piApplyAnnounceDate
            layStatus: [
                { name: "所有状态", val: "有权-无权-在审" },
                { name: "有权", val: "有权" },
                { name: "无权", val: "无权" },
                { name: "在审", val: "在审" }
            ], //法律状态
            layStatusIndex: 0,
            applyPerson: "", //申请人
            applyDate: "", //申请日
            // typeNum: "" //分类号
            timeInterval: "", //时间间隔
            pickerBeginDateBefore: {
                disabledDate: time => {
                    let _now = Date.now();
                    return time.getTime() > _now;
                }
            },
            Loading: "",
            clickTwice: false
        };
    },
    mounted() {
        // console.log("关键字", this.keyWord);
        // this.getListData();
        Jump(document.getElementsByTagName("body")[0]);
    },
    watch: {
        applyPerson() {
            this.pageIndex = 1;
            this.getListData();
        },
        applyDate() {
            this.pageIndex = 1;
            this.getListData();
        }
    },
    methods: {
        getListData() {
            if (!this.keyWord) return;
            let requireObj = {
                pageIndex: this.pageIndex, //开始页
                pageSize: this.pageSize,
                type: this.type,
                keyWord: this.keyWord,
                dateType:
                    this.dateType == 0
                        ? "applicationDate"
                        : this.dateType == 1
                            ? "publicationDate"
                            : "piApplyAnnounceDate",
                dataStart: this.timeInterval ? this.timeInterval[0] : "",
                dataEnd: this.timeInterval ? this.timeInterval[1] : "",
                layStatus: this.layStatus[this.layStatusIndex].val,
                applyPerson: this.applyPerson,
                applyDate: this.applyDate
            };
            // console.log("layStatus", requireObj);
            Http("patentSearch/searchList", requireObj, "post", 1).then(res => {
                // console.log("list", res.data.data);
                this.listArr = res.data.data.listData;
                this.totalNum = Number(res.data.data.totalCount || 0);
                Jump(document.getElementsByTagName("body")[0]);
            });
        },
        initUrl(code) {
            return "/patentDetail/" + code + ".html";
        },
        pagechange(page) {
            this.pageIndex = page;
            if (this.$store.state.userInfo) {
                this.getListData();
            } else {
                this.$Popup.created({
                    content: "请先进行登录",
                    time: 2000
                });
                setTimeout(() => {
                    this.pageIndex = 1;
                    this.$router.push({
                        path: "/passport/login",
                        query: {
                            backUrl: `/patentSearch/${this.keyWord}.html`
                        }
                    });
                }, 2000);
            }
        },
        //搜
        submitList() {
            // console.log(1111111, this.keyWord);

            if (this.keyWord == "") return;

            if (
                /[`~!@#$%^&*_\-+=<>?:"\/'\\[\]·~！@#￥%……&*——\-+=？：.]/im.test(
                    this.keyWord
                )
            ) {
                this.$Popup.created({
                    content: "专利名称不能包含特殊字符",
                    time: 2000
                });
                return false;
            }
            if (this.clickTwice) return;
            else {
                this.clickTwice = true;
            }
            this.Loading = this.$Popup.created({
                content: "搜索中...",
                type: "loading",
                icon: "&#xe61c"
            });
            this.$router.push({
                path: "/patentSearch/" + this.keyWord + ".html"
            });
        },
        //type 的选中
        typeSelect(idx) {
            let newIdxArr = [...this.typeIndexArr];
            let typeStr = "";

            if (idx == 0) {
                newIdxArr = newIdxArr[0]
                    ? [0, 0, 0, 0, 0, 0]
                    : [1, 1, 1, 1, 1, 1];
            } else {
                newIdxArr[idx] = newIdxArr[idx] ? 0 : 1;
            }
            for (let i = 1; i < newIdxArr.length; i++) {
                if (newIdxArr[i] === 1) {
                    newIdxArr[0] = 1;
                } else {
                    typeStr += this.typeArr[i].val + "-";
                }
            }
            if (!newIdxArr[0])
                typeStr = "发明-发明公开-发明授权-实用新型-外观设计";
            else typeStr = typeStr.substr(0, typeStr.length - 1);
            this.pageIndex = 1;

            if (!typeStr) {
                typeStr = "发明-发明公开-发明授权-实用新型-外观设计";
            }
            this.typeIndexArr = newIdxArr;
            this.type = typeStr;

            this.getListData();
        },
        //提交表单
        sureSub() {
            if (this.timeInterval) {
                this.getListData();
            } else {
                this.$Popup.created({
                    content: "请先选择日期",
                    time: 2000
                });
            }
        },
        //改变法律状态
        changgeLayStatusIndex(idx) {
            this.layStatusIndex = idx;
            this.openDate = 0;
            this.getListData();
        },
        //清空
        clearAll() {
            this.dateType = 0;
            this.layStatusIndex = 0;
            this.timeInterval = "";
            this.applyPerson = "";
            this.applyDate = "";
            this.pageIndex = 1;
            this.getListData();
        },
        //高亮
        heigtCol(str) {
            let key = this.keyWord + "";
            if (key == "") {
                return str == null ? "暂无" : str;
            }
            let re = new RegExp(key, "g");
            let newStr = (str + "").replace(
                re,
                "<i class='heightCol'>" + key + "</i>"
            );

            return newStr == "null" ? "暂无" : newStr;
        },
        //分开关键字
        spitArr(str) {
            if (str) {
                return str.split("、");
            }
            return [];
        }
    },
    destroyed() {
        if (this.Loading) this.Loading.close();
    }
};
</script>
<style scoped>
@import url("../../assets/css/patentSearch");
* {
    box-sizing: border-box;
}
/*搜索优化*/
.search-top {
    text-align: center;
}
.search-top h1 {
    margin-bottom: 22px;
    font-size: 55px;
    font-family: "宋体", "\5B8B\4F53", SimSun;
    font-weight: bold;
    color: #000;
}
.search-top h1 i {
    color: #f08c2f;
    font-family: "宋体", "\5B8B\4F53", SimSun;
    font-weight: bold;
}
.search-top p {
    font-size: 16px;
    color: #666666;
}
.search-main-tab {
    margin-bottom: 10px;
    padding: 5px 100px;
}
.search-main-tab li {
    display: inline-block;
    margin-right: 50px;
    padding: 5px 12px;
    border-radius: 3px;
    cursor: pointer;
}
.search-main-tab li.active {
    position: relative;
    background-color: #f08c2f;
    color: #fff;
}
.search-main-tab li.active:before {
    content: "";
    position: absolute;
    bottom: -10px;
    left: 50%;
    margin-left: -5px;
    width: 0px;
    border: 5px solid transparent;
    border-top-color: #f08c2f;
}

.brandSearch2-page .searchPanel .index-btn-search.btn-search {
    background: none;
    background-color: #f08c2f;
}

.search-type-logo {
    display: inline-block;
    margin-left: 35px;
    margin-right: 60px;
    vertical-align: middle;
}
.search-type-logo h2 {
    position: relative;
    margin-bottom: 5px;
    font-size: 36px;
    color: #f08c2f;
}
.search-type-logo h2 a {
    color: #f08c2f;
    letter-spacing: 1px;
    font-size: 36px;
}
.search-type-logo p {
    font-size: 13px;
    color: #4a4a4a;
}
.search-type-logo .icon-beta {
    display: inline-block;
    position: absolute;
    right: -40px;
    bottom: 1px;
    width: 35px;
    text-align: center;
    font-size: 12px;
    line-height: 17px;
    background-color: #f08c2f;
    border-radius: 3px;
    color: #fff;
    opacity: 0.8;
}
.searchList-wrap .search {
    display: inline-block;
    vertical-align: middle;
}
.index-search-wrap.search-wrap {
    padding-top: 105px;
}
.index-search-wrap.search-wrap .search {
    margin: 0 auto;
    margin-top: 100px;
    margin-bottom: 250px;
    width: 940px;
}
</style>

