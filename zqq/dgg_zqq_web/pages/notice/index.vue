<template>
    <div>
        <div class="page-form">
            <ul>
                <li>
                    <label>商标名称：</label> <input type="text" name="brandName" placeholder="请输入商标名称" v-model="name"></li>
                <li>
                    <label class="radiobox-box" @click="matchMethod = 0">
                        <label class="radiobox" :class="{'checked':matchMethod==0}"></label>
                        <span>精确+近似</span>
                    </label>
                    <label class="radiobox-box" @click="matchMethod = 1">
                        <label class="radiobox" :class="{'checked':matchMethod==1}"></label>
                        <span>精确</span>
                    </label>
                    <label class="radiobox-box" @click="matchMethod = 2">
                        <label class="radiobox" :class="{'checked':matchMethod==2}"></label>
                        <span>近似</span>
                    </label>
                </li>
                <li>
                    <label>申&nbsp;&nbsp;请&nbsp;号：</label> <input type="text" name="dataId" placeholder="请输入申请号" v-model="regNo"></li>
                <li>
                    <label>代理机构：</label>
                    <div class="page-select-box">
                        <span class="iconfont lo" v-show='tmSectionLoading'>&#xe61c;</span>
                        <input type="text" name="agency" placeholder="请输入代理机构" v-model="tmSection">
                        <div class="page-select-list scroll" v-if='tmSectionArr && tmSectionArr.length >  0'>
                            <span v-for="(item,idx) in tmSectionArr" :key='idx' @click="selectAbout(item.name,'tmSection','tmSectionArr')">{{item.name}}</span>
                        </div>
                    </div>
                </li>
                <li>
                    <label>申请人：</label>
                    <div class="page-select-box"><input type="text" name="applicants" placeholder="请输入申请人" v-model="applicantCn">
                        <span class="iconfont lo" v-show='applicantCnLoading'>&#xe61c;</span>
                        <div class="page-select-list scroll" v-if='applicantCnArr.length >  0'>
                            <span v-for="(item,idx) in applicantCnArr" :key='idx' @click="selectAbout(item.name,'applicantCn','applicantCnArr')">{{item.name}}</span>
                        </div>
                    </div>
                </li>

                <li>
                    <label>公告期号：</label>
                    <div class="page-select-box select-notice-issue">
                        <input type="text" name="noticeDate" placeholder="近12期全部初审公告" readonly="readonly" v-model="announcementIssue">
                        <div class="page-select-btn"></div>
                        <div class="page-select-list scroll">
                            <span @click="selectAnn('all')">近12期全部初审公告</span>
                            <span v-for="(item,idx) in listData" :key='idx' :class="{'active':isAnnIndex[idx] }" @click="selectAnn(idx,item.annNo)">{{item.annNo}} {{item.annDate}}</span>
                        </div>
                    </div>
                </li>
                <div class="clear"></div>
            </ul>
            <ul>
                <li class="search-category" :class="{'open': isOpen}">
                    <a href="javascript:void(0)" class="btn" @click="isOpen = !isOpen">更多</a>
                    <label>申请类别：</label>
                    <div class="category-show-box select-show-category">
                        <a href="javascript:void(0)" v-for="(item,idx) in classify45" :key='idx' @click="clickClassify(item.number,item.name,idx)" :class="{'active':c45Index[idx]}">{{item.number}} {{item.name}}</a>
                    </div>
                    <div class="clear"></div>
                </li>
            </ul>
            <div class="search-btns">
                <span class="search-num" v-if="totalNum">共为您查询到
                    <i>{{totalNum}}</i>条公告数据</span>
                <a href="javascript:void(0)" class="button button-white" @click='exportPage'>导出数据</a>
                <a href="javascript:void(0)" class="button search" @click="subMit">查询</a>
            </div>
        </div>
        <!-- //12期数************************************************* -->
        <div class="page-index" v-if="!pageStatus">
            <div class="page-index-title">最新12期初审公告数据</div>
            <table class="table">
                <thead>
                    <tr>
                        <th>公告期</th>
                        <th>初审公告日期</th>
                        <th>异议绝限申请日期</th>
                        <th>异议绝限剩余时间</th>
                        <th>初审商标数</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(item,idx) in listData" :key='idx'>
                        <td>第{{item.annNo}}期</td>
                        <td>{{item.annDate}}</td>
                        <td>{{item.exceptionAppDate}}</td>
                        <td>剩余{{item.exceptionAppLeaveDay}}天</td>
                        <td>{{item.total}}</td>
                        <td class="td-href">
                            <a @click="changgePage(item.annNo)">查看详情</a>
                        </td>
                    </tr>

                </tbody>
            </table>
        </div>
        <!-- *******************//12期数 详情************************************************* -->
        <div class="page-content" v-else>
            <table class="table" v-if='totalNum > 0'>
                <thead>
                    <tr>
                        <th>商标图样</th>
                        <th>商标名称</th>
                        <th>商品类别</th>
                        <th>申请号</th>
                        <th>申请人</th>
                        <th>初审公告日期</th>
                        <th>异议绝限日期</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr class="table-margin">
                        <td colspan="8"></td>
                    </tr>
                    <!-- <tr>
                        <td colspan="8" class="table-top">
                            提起异议绝限日期：
                            <i>2018-12-6</i>
                        </td>
                    </tr> -->
                    <tr v-for="(item,idx) in listArr" :key='idx'>
                        <td>
                            <div class="brand-img">
                                <a target="_blank" :href="initUrl(item.esId)">
                                    <img :src="item.imageUrl ? item.imageUrl : ''" :onerror="logo" />
                                </a>
                            </div>
                        </td>
                        <td>{{item.name}}</td>
                        <td>
                            <span class="td-btype">{{item.intCls}}</span>
                        </td>
                        <td>{{item.regNo}}</td>
                        <td>{{item.applicantCn}}</td>
                        <td>{{item.announcementDate}}</td>
                        <td>{{item.exceptionAppDate}}</td>
                        <td class="td-href">
                            <a target="_blank" :href="initUrl(item.esId)">查看详情</a><br>
                            <a href="javascript:;" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)">我要异议</a>
                        </td>
                    </tr>
                </tbody>
            </table>
            <noFind :keyWord="name || tmSection" :type="'商标'" v-else/>
            <div class="pagination">
                <!-- start 分页 -->
                <pagination :totalPage="totalNum" :pageSize="length" @pagechange="pagechange" />
                <!-- end 分页 -->
            </div>
        </div>
    </div>
</template>
<script>
import img from "~/assets/images/default.png";
import { Http } from "~/plugins/axios.js";
import axios from "axios";
import pagination from "~/components/pagination.vue";
import { Jump } from "~/assets/js/common";
import noFind from "~/components/noFind";

export default {
    layout: "notice",
    head() {
        return {
            title:
                "商标初审公告查询_商标初审公告期查询_商标初审公告异议-快速服务-知千秋",
            meta: [
                {
                    name: "keywords",
                    hid: "keywords",
                    content:
                        "商标初审公告,商标初审公告查询,商标初审公告期,商标初审公告异议"
                },
                {
                    name: "description",
                    hid: "description",
                    content:
                        "【商标初审公告查询-栏目】拥有最新商标初审公告信息查询，【快速】商标初审公告异议服务，商标初审公告信息期查询。"
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
    components: {
        pagination,
        noFind
    },
    async asyncData({ params, redirect, error, env, route }) {
        let { data } = await axios.post(`${env.baseUrl}term/last12terms`);
        if (data.code == 0) {
            return {
                listData: data.data,
                baseUrl: env.webSite + route.fullPath
            };
        } else {
            error({ statusCode: 500, message: data.msg });
        }
    },

    data() {
        return {
            classify45: [],
            listData: "",
            logo: 'this.src="' + img + '"',
            pageStatus: 0, //哪一个页面
            isOpen: 0,
            matchMethod: 0, //0精确加近似'full'，1精确like，2近似near,
            start: 0, //翻页页数
            length: 20, //每页多少条
            announcementIssue: "", //期号
            name: "", //商标名称检索字段
            regNo: "", //商标注册号
            applicantCn: "", //申请人
            tmSection: "", //代理机构
            intCls: "", //申请类别，支持多类，用逗号分割
            c45Index: [], //45类点击选中
            isAnnIndex: [], //多少期的选中的数组可以多选
            totalNum: 0, //总条数
            listArr: [],
            tmSectionArr: [],
            applicantCnArr: [],
            tmSectionLoading: false,
            applicantCnLoading: false
        };
    },
    watch: {
        tmSection() {
            //代理机构
            if (this.tmSection && this.tmSection.length > 1) {
                this.tmSectionLoading = true;
                this.aboutQuery(this.tmSection, "tmSection", res => {
                    this.tmSectionArr = res.data.data;
                    this.tmSectionLoading = false;
                });
            } else {
                this.tmSectionArr = [];
            }
        },
        applicantCn() {
            //代理人
            if (this.applicantCn && this.applicantCn.length > 1) {
                this.applicantCnLoading = true;

                this.aboutQuery(this.applicantCn, "applicationCn", res => {
                    this.applicantCnArr = res.data.data;
                    this.applicantCnLoading = false;
                });
            } else {
                this.applicantCnArr = [];
            }
        }
    },
    mounted() {
        Http("classfirst/query", {}, "get").then(res => {
            this.classify45 = res.data.data;
        });
    },
    methods: {
        subMit() {
            if (this.regNo && isNaN(Number(this.regNo))) {
                this.$Popup.created({
                    content: "申请号只能为全数字",
                    time: 2000
                });
                this.regNo = "";
                return;
            }
            this.start = 0;
            this.goList();
        },
        goList() {
            let requireObj = {
                matchMethod:
                    this.matchMethod == 0
                        ? "like"
                        : this.matchMethod == 1 ? "full" : "near", //0精确加近似'like'，1精确full，2近似near,
                start: this.start, //翻页页数
                length: this.length, //每页多少条
                announcementIssue: this.announcementIssue, //期号
                name: this.name, //商标名称检索字段
                regNo: this.regNo, //商标注册号,
                intCls: this.intCls, //申请类别，支持多类，用逗号分割
                applicantCn: this.applicantCn, //申请人
                tmSection: this.tmSection //代理机构
            };
            for (let key in requireObj) {
                if (requireObj[key] == "") {
                    delete requireObj[key];
                }
            }
            sessionStorage.setItem("isFrom", 1);
            Http("term/queryPage", requireObj, "post", 1).then(res => {
                sessionStorage.removeItem("isFrom");
                Jump(document.getElementsByTagName("body")[0], -72);
                this.totalNum = res.data.data.recordsTotal;
                this.listArr = res.data.data.data;
                this.pageStatus = 1;
            });
        },
        //进入通过
        changgePage(num) {
            this.announcementIssue = num;
            this.start = 0;
            this.length = 20;
            this.name = "";
            this.regNo = "";
            this.matchMethod = "";
            this.applicantCn = "";
            this.tmSection = "";
            this.intCls = "";

            this.goList();
        },
        //45类点击
        clickClassify(a, b, idx) {
            let str = a + "_" + b;
            let arr = [...this.c45Index];
            arr[idx] = arr[idx] ? 0 : str;
            this.c45Index = arr;
            let intCls = "";

            for (let item of arr) {
                if (item) {
                    intCls = intCls + item + ",";
                }
            }
            intCls = intCls.substr(0, intCls.length - 1);

            this.intCls = intCls;
        },
        //多少期的选中
        selectAnn(idx, qi) {
            let announcementIssueStr = "";

            if (idx == "all") {
                this.isAnnIndex = [];
            } else {
                let arr = [...this.isAnnIndex];
                arr[idx] = arr[idx] ? 0 : qi;
                this.isAnnIndex = arr;
                for (let item of arr) {
                    if (item) {
                        announcementIssueStr =
                            announcementIssueStr + item + ",";
                    }
                }
                announcementIssueStr = announcementIssueStr.substr(
                    0,
                    announcementIssueStr.length - 1
                );
            }
            this.announcementIssue = announcementIssueStr;
        },
        //翻页
        pagechange(page) {
            this.start = page - 1;
            this.goList();
        },
        initUrl(id) {
            return "/notice/" + id + ".html";
        },
        //导出数据$
        exportPage() {
            if (this.totalNum && this.totalNum <= 100) {
                let loading = this.$Popup.created({
                    content: "导出中，请稍等..."
                });
                let requireObj = {
                    matchMethod:
                        this.matchMethod == 0
                            ? "like"
                            : this.matchMethod == 1 ? "full" : "near", //0精确加近似'like'，1精确full，2近似near,
                    announcementIssue: this.announcementIssue, //期号
                    name: this.name, //商标名称检索字段
                    regNo: this.regNo, //商标注册号,
                    intCls: this.intCls, //45类
                    applicantCn: this.applicantCn, //申请人
                    tmSection: this.tmSection //代理机构
                };
                let exportUrl = this.$baseUrl + "term/export?";
                for (let key in requireObj) {
                    if (requireObj[key])
                        exportUrl += key + "=" + requireObj[key] + "&";
                }

                window.open(exportUrl);
                loading.close();
            } else {
                this.$Popup.created({
                    content: "当前数据量过大请筛选后再试",
                    time: 2000
                });
            }
        },
        //联想词
        aboutQuery(name, type, success) {
            sessionStorage.setItem("isFrom", 1);
            Http(
                "term/queryDistinctFields",
                { count: 10, field: type, text: name },
                "post"
            ).then(res => {
                success(res);
            });
        },
        selectAbout(str, type, arr) {
            this[type] = str;
            this[arr] = [];
        }
    }
};
</script>
<style scoped>
/* //新增 */
body.overflow {
    overflow: hidden;
}
.page-form {
    padding: 20px;
    background-color: #f8f8f8;
}
.page-form ul li {
    margin-bottom: 18px;
    min-width: 270px;
}
.page-form ul li > label {
    float: left;
    display: inline-block;
    margin-top: 2px;
    margin-right: 30px;
    width: 60px;
    font-size: 12px;
    color: #666;
}

.search-category {
    position: relative;
    height: 20px;
    overflow: hidden;
    transition: 0.1s;
}
.search-category.open {
    height: auto;
}
.search-category .btn {
    position: absolute;
    top: 0;
    right: 20px;
    padding-right: 15px;
    color: #666;
    background: url("../../assets/images/main/narrow-down.png") no-repeat right
        center;
}
.search-category.open .btn {
    background: url("../../assets/images/main/narrow-up.png") no-repeat right
        center;
}
.selected-category {
    margin-bottom: 5px;
}
.selected-category > label {
    float: left;
    margin-top: 5px;
    margin-right: 40px;
    font-size: 12px;
    color: #000;
}
.selected-category-box {
    float: left;
    width: 1100px;
    min-height: 36px;
}
.selected-category-box a {
    float: left;
    position: relative;
    margin-right: 10px;
    margin-bottom: 10px;
    width: 110px;
    height: 26px;
    line-height: 24px;
    font-size: 12px;
    text-align: center;
    color: #f08b2f;
    border: 1px solid #f08b2f;
    border-radius: 2px;
}
.selected-category-box a .service-list {
    text-align: left;
}
.selected-category-box a .service-list.service-button {
    padding-right: 15px;
    text-align: right;
}
.selected-category-box a .service-list.service-button .button {
    width: 40px;
    line-height: 20px;
    background-color: #f08b2f;
}
.selected-category-box a .service-list.service-button .button.button-white {
    color: #aaa;
    background-color: #fff;
    border: 1px solid #ddd;
}
.selected-category-box a .service-list .checkbox {
    margin-top: 3px;
    vertical-align: top;
}
.selected-category-box a .service-list span {
    display: inline-block;
    width: 300px;
    font-size: 12px;
    line-height: 1.5em;
    color: #666;
    vertical-align: top;
}
.selected-category-box a .service-list .disabled span {
    color: #999;
}
.selected-category-box a .service-list .checkbox-box {
    display: block;
}
.selected-category-box a.active {
    color: #fff;
    background-color: #f08b2f;
    border-color: #f08b2f;
}
.selected-category-box a:hover {
    border-bottom: none;
}
.selected-category-box a:hover .selected-service-box {
    display: block;
}
.selected-category-box a:not(.active):hover span.text {
    position: relative;
    float: left;
    width: 100%;
    background-color: #fff;
    z-index: 10;
}
.selected-category-box a span.text {
    display: block;
    padding-left: 10px;
    height: 24px;
    text-align: left;
    font-size: 12px;
}
.selected-category-box .icon-close {
    position: absolute;
    right: 3px;
    top: 1px;
    width: 9px;
    height: 9px;
    z-index: 11;
}
.selected-service-box {
    display: none;
    position: absolute;
    left: -1px;
    top: 23px;
    padding: 10px;
    border: 1px solid #f08b2f;
    background-color: #fff;
    z-index: 9;
}
.selected-service-box-in {
    width: 360px;
    height: 124px;
    overflow: auto;
}
.category-show-box {
    float: left;
    width: 1000px;
}
.category-show-box a {
    display: inline-block;
    margin-right: 30px;
    margin-bottom: 5px;
}
.category-show-box a.active {
    color: #ff5c00;
}
.category-show-box.select-show-category a {
    display: inline-block;
    width: 140px;
}
.category-show-box.select-show-category a:nth-child(6n) {
    margin-right: 0px;
}
.category-show-box.select-show-year a {
    display: inline-block;
    width: 85px;
}
.category-show-box.select-show-year a:nth-child(8n) {
    margin-right: 0px;
}
.no-result-top {
}
.no-result-top span {
    display: inline-block;
    padding-left: 5px;
    height: 1.05em;
    color: #333;
    line-height: 1em;
    border-left: 2px solid #f08b2f;
    vertical-align: middle;
}
.no-result-top a {
    display: inline-block;
    margin-left: 15px;
    padding-left: 25px;
    line-height: 1.2em;
    height: 18px;
    color: #f08b2f;
    vertical-align: middle;
}
.no-result-category .talk {
    /* background: url(../images/icon-talk.png) no-repeat 0 center; */
    background-size: 22px;
}
.no-result-category-box {
    margin-top: 18px;
    overflow: hidden;
}
.no-result-category-box a {
    float: left;
    margin-right: 10px;
    margin-bottom: 10px;
    width: 110px;
    line-height: 24px;
    font-size: 12px;
    text-align: center;
    color: #999;
    border: 1px solid #ccc;
    border-radius: 2px;
}
/* //单选的点击 */
.radiobox {
    display: inline-block;
    width: 15px;
    height: 15px;
    background-color: #fff;
    border: 1px solid #dddddd;
    border-radius: 50%;
    vertical-align: middle;
}
.radiobox.checked {
    border-color: #ff7200;
    background: url("../../assets/images/main/icon-radio.png") no-repeat center;
    background-size: 7px;
}
.radiobox-box span {
    display: inline-block;
    vertical-align: middle;
    margin-left: 4px;
}
/*search-content*/

/*å•†æ ‡äº¤æ˜“*/
.brand-img {
    margin: 10px auto;
    width: 100px;
    height: 100px;
    overflow: hidden;
    line-height: 100px;
}
.brand-img a img {
    max-width: 100px;
}
.no-search-data {
    padding: 120px 0;
    font-size: 18px;
    text-align: center;
}
.no-search-data img {
    width: 150px;
    vertical-align: middle;
}
.no-search-data .no-search-tips {
    display: inline-block;
    margin-left: 10px;
    width: 400px;
    font-size: 15px;
    vertical-align: middle;
    text-align: left;
    color: #bbb;
}
.no-search-data h2 {
    margin-bottom: 15px;
    color: #f08b2f;
    font-size: 18px;
}
.no-search-data .no-search-tips a {
    color: #f08b2f;
    text-decoration: underline;
}
.page-form ul li.list {
    margin-bottom: 0;
}
.search-help {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.6);
    z-index: 999999;
}
.search-help .tips {
    display: inline-block;
    position: absolute;
    left: 50%;
}
.search-help .tips .close {
    position: absolute;
    right: 0;
    top: 0;
    color: #fff;
    cursor: pointer;
}
.search-help .tips a.know {
    position: absolute;
    width: 132px;
    height: 60px;
}
.search-help .tips.tips1 {
    top: 62px;
    margin-left: 280px;
}
.search-help .tips.tips1 a.know {
    top: 320px;
    left: 123px;
}
.search-help .tips.tips2 {
    top: 222px;
    margin-left: -543px;
}
.search-help .tips.tips2 a.know {
    top: 351px;
    left: 229px;
}
.search-help .tips.tips3 {
    top: 221px;
    margin-left: -704px;
}
.search-help .tips.tips3 a.know {
    top: 201px;
    left: 24px;
}
.search-help .tips.tips4 {
    top: 61px;
    margin-left: 250px;
}
.search-help .tips.tips4 a.know {
    top: 321px;
    left: 76px;
}
/* //***************************************** */

.page-form {
    padding-bottom: 5px;
}

.page-form ul li:not(.search-category) {
    float: left;
    margin-right: 40px;
    margin-bottom: 10px;
}
.page-form ul li > label {
    margin-top: 5px;
    margin-right: 5px;
    width: auto;
    font-size: 14px;
    color: #333;
}
.page-form input[type="text"] {
    padding-left: 5px;
    width: 200px;
    height: 30px;
    border: 1px solid #dddddd;
}
.page-form .select-notice-issue {
    background-color: #ffffff;
}
.page-form .select-notice-issue input[type="text"] {
    position: relative;
    padding-right: 30px;
    z-index: 1;
    background-color: transparent;
}
.page-form ul li .radiobox-box {
    width: auto;
}
.page-form .radiobox-box {
    margin-right: 15px;
    cursor: pointer;
}
.search-btns {
    padding: 10px 0;
    text-align: right;
    border-top: 1px solid #e2e3e8;
}
.search-btns .search-num {
    float: left;
    margin-top: 5px;
}
.search-btns .search-num i {
    color: #ff7200;
}
.search-btns .button {
    width: 80px;
    line-height: 30px;
    text-align: center;
    color: #fff;
    background-color: #ff7200;
}
.search-category {
    height: 25px;
}
.category-show-box {
    padding-top: 5px;
}
.search-category .btn {
    top: 5px;
}
.page-detail h2 {
    font-size: 20px;
    font-weight: bold;
}
.page-detail .btns {
    float: right;
}
.page-detail .btns .button {
    display: inline-block;
    margin-left: 10px;
    width: 80px;
    text-align: center;
    line-height: 30px;
}

.search-btns .button-white {
    margin-right: 15px;
}
.page-detail .content {
    text-align: center;
}
.page-detail .content img {
    margin-top: 20px;
}
.page-detail .content span {
    display: inline-block;
    margin-top: 100px;
    font-size: 20px;
}
.page-select-box {
    display: inline-block;
    position: relative;
    vertical-align: middle;
}
.lo {
    position: absolute;
    top: 8px;
    right: 6px;
    font-size: 16px;
    animation: loading 0.5s linear infinite;
    -webkit-animation: loading 0.5s linear infinite;
}
@-webkit-keyframes loading {
    0% {
        transform: rotate(0deg);
    }
    50% {
        transform: rotate(180deg);
    }
    100% {
        transform: rotate(360deg);
    }
}
@keyframes loading {
    0% {
        transform: rotate(0deg);
    }
    50% {
        transform: rotate(180deg);
    }
    100% {
        transform: rotate(360deg);
    }
}

.page-select-box:hover .page-select-list {
    display: block;
}
.page-select-btn {
    position: absolute;
    right: 0;
    top: 4px;
    width: 30px;
    height: 22px;
    border-left: 1px solid #ddd;
    background: url("../../assets/images/main/narrow-down.png") no-repeat center;
}
.page-select-list {
    display: none;
    position: absolute;
    left: 0;
    width: 100%;
    max-height: 200px;
    overflow: auto;
    background-color: #fff;
    z-index: 9;
}
.page-select-list span {
    display: block;
    padding-left: 5px;
    height: 2.4em;
    line-height: 2.4em;
    font-size: 13px;
    overflow: hidden;
    cursor: pointer;
}
.page-select-list span.active {
    color: #fff;
    background-color: #ff7200;
}
.page-select-list span:hover {
    color: #fff;
    background-color: #ff7200;
}
/* /下面是表格 */
.page-index-title {
    position: relative;
    margin-top: 25px;
    margin-bottom: 23px;
}
.page-index-title:before {
    content: "";
    position: absolute;
    top: 50%;
    left: -15px;
    margin-top: -3px;
    width: 6px;
    height: 6px;
    background-color: #ff4422;
    border-radius: 50%;
}
.page-content {
    margin-top: 30px;
}
.table {
    width: 100%;
    text-align: center;
}
.table thead {
    background-color: #e5e5e5;
}
.table tr {
    height: 40px;
}
.table td.table-top {
    padding-left: 20px;
    text-align: left;
    background-color: #f5f5f5;
}
.table tr.table-margin {
    height: 15px;
}
.table td.table-top i {
    color: #ff4422;
}
.table td {
    width: 200px;
}
.table tr td.td-href a {
    display: inline-block;
    margin-bottom: 10px;
    color: #ff7200;
    text-decoration: underline;
}

.table tr td.td-href a:last-of-type {
    margin-bottom: 0px;
}
</style>

