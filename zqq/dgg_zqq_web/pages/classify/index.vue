<template>
    <div>
        <mini/>
        <div class="page-search w">
            <span class="main-title">商品分类</span>
            <div class="search-input">
                <input name="" placeholder="请输入主营业务或行业关键词查看所对应的商标类别" class="text fl" type="text" v-model="keyWord" @keyup.enter='searchNotice'>
                <a @click="searchNotice" class="button search">搜索</a>
            </div>
            <div class="search-right">
                <a href="/notice" target="_blank" class="button button-white">初审公告</a>
            </div>
        </div>
        <div class="page-nav">
            <div class="page-nav-in w">
                <a href="javascript:void(0)">类似商品和服务区分表 基于尼斯分类</a>
            </div>
        </div>
        <!-- //商标的45大类 -->
        <section class="w" style="margin-top:20px">
            <ul class="nice-list">
                <li v-for='(item,idx) in listData' :key='idx'>
                    <div class="first-num">{{item.number}}</div>
                    <div class="first-name">{{item.name}}</div>
                    <div class="first-intro">
                        <div class="first-intro-in">
                            {{splitFunc(item.remark)}}
                            <a :href="initUrl(item.number)">[查看详情]</a>
                        </div>
                    </div>
                </li>

                <div class="clear"></div>
            </ul>
        </section>
        <toTop :isShow="'1'" />
    </div>
</template>
  <script>
import mini from "~/components/header/mini";
import { openNewPage } from "~/assets/js/common.js";
import toTop from "~/components/toTop";
import { Http } from "~/plugins/axios.js";
import axios from "axios";

export default {
    layout: "empt",
    head() {
        return {
            title: "商标分类表2018版_尼斯分类表_商标注册分类表明细-知千秋",
            meta: [
                {
                    name: "keywords",
                    hid: "keywords",
                    content:
                        "商标分类表,商标分类,尼斯分类表,尼斯分类,商标分类,商标注册分类,2018版,商标注册分类表,商标注册分类明细"
                },
                {
                    name: "description",
                    hid: "description",
                    content:
                        "【商标分类表】栏目，为您提供：2018版最新尼斯分类表，商标注册分类明细查询到知千秋商标查询系统，提供各类商标注册与申请、商标管理、极速网申（0元服务费）等服务。"
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
        let { data } = await axios.get(`${env.baseUrl}/classfirst/query`);
        if (data.code == 0) {
            return {
                listData: data.data,
                baseUrl: env.webSite + route.fullPath
            };
        } else {
            error({ statusCode: 500, message: data.msg });
        }
    },
    created() {},
    components: {
        mini,
        toTop
    },
    mounted() {
        // Http("classfirst/query", {}, "get").then(res => {
        //     console.log(res);
        // });
    },
    data() {
        return {
            listData: [],
            keyWord: ""
        };
    },
    methods: {
        splitFunc(str) {
            let str1 = "";
            if (str) {
                if (str.length > 85) {
                    str1 = ("" + str).substr(0, 85) + "...";
                } else str1 = str;
            }
            return str1;
        },
        initUrl(id) {
            return "/classify/" + id + ".html";
        },
        searchNotice() {
            this.keyWord = this.keyWord.replace(/ /g, "");
            if (this.keyWord) {
                if (
                    /[`~!@#$%^&*_\-+=<>?:"\/'\\[\]·~！@#￥%……&*——\-+=？：.]/im.test(
                        this.keyWord
                    )
                ) {
                    this.$Popup.created({
                        content: "关键字不能包含特殊字符",
                        time: 2000
                    });
                    return false;
                }
                this.$router.push({
                    path: "/classify/*" + this.keyWord + ".html"
                });
            }
        }
    }
};
</script>
<style scoped>
* {
    box-sizing: border-box;
}
/* //二级搜索 */
.page-search {
    padding: 30px 0;
}
.page-search .main-title {
    display: inline-block;
    margin-right: 45px;
    font-size: 36px;
    line-height: 1em;
    color: #ff5c00;
    vertical-align: middle;
}
.search-input {
    display: inline-block;
    position: relative;
    margin-right: 40px;
    border: 1px solid #ff5c00;
    border-radius: 0 2px 2px 0;
    overflow: hidden;
    vertical-align: middle;
    box-sizing: border-box;
}
.page-search input.text {
    padding-left: 20px;
    width: 520px;
    height: 38px;
    border: none;
    vertical-align: middle;
    padding-right: 40px;
}
.page-search .button {
    width: 130px;
    line-height: 36px;
    font-size: 18px;
    text-align: center;
    vertical-align: middle;
}

.search-input .icon-camera {
    display: block;
    width: 30px;
    height: 28px;
    position: absolute;
    right: 135px;
    top: 5px;
    z-index: 2;
    background: url("../../assets/images/icon_photo.png") no-repeat center;
    cursor: pointer;
}
.search-right {
    display: inline-block;
    margin-left: 20px;
}
.button.button-white {
    color: #ff5c00;
    background-color: #fff;
    border: 1px solid #ff5c00;
}
.page-search .button {
    width: 130px;
    line-height: 36px;
    font-size: 18px;
    text-align: center;
    vertical-align: middle;
    border-radius: 2px 0 0 2px;
}
.search-right .button {
    margin-left: 10px;
}
/* //黑底导航 */
.page-nav {
    line-height: 48px;
    background-color: #39414e;
}
.page-nav a {
    margin-right: 50px;
    font-size: 15px;
    color: #fff;
}
.page-nav a.active {
    font-size: 16px;
    color: #ff5c00;
    font-weight: bold;
}
/* //45个分类************************************************************************* */
.nice-list {
    margin-top: 10px;
}
.nice-list li {
    float: left;
    position: relative;
    padding: 20px 0;
    width: 124px;
    text-align: center;
}
.nice-list li:hover {
    color: #fff;
    background-color: #ff5c00;
}
.nice-list li .first-num {
    font-size: 36px;
    font-weight: bold;
    color: #666;
}
.nice-list li:hover .first-num {
    color: #fff;
}
.nice-list li .first-name {
    font-size: 18px;
}
.nice-list li .first-intro {
    display: none;
    position: absolute;
    text-align: left;
    top: 0;
    left: 100%;
    padding: 20px 15px;
    width: 360px;
    height: 100%;
    /* font-size: 0px; */
    border: 1px solid #ff5c00;
    background-color: #fff;
    color: #333;
    z-index: 9;
}
.nice-list li .first-intro:before {
    content: "";
    display: inline-block;
    height: 100%;
    width: 0;
    vertical-align: middle;
}
.nice-list li .first-intro .first-intro-in {
    display: inline-block;
    width: 100%;
    vertical-align: middle;
}
/* .nice-list li.right .first-intro {
    left: auto;
    right: 100%;
} */
.nice-list li:nth-child(9n) .first-intro,
.nice-list li:nth-child(9n-1) .first-intro,
.nice-list li:nth-child(9n-2) .first-intro {
    left: auto !important;
    right: 100% !important;
}

.nice-list li:hover .first-intro {
    display: block;
}
.nice-list li .first-intro a {
    display: inline-block;
    color: #ff5c00;
}
/*å°¼æ–¯åˆ†ç±»è¯¦æƒ…*/
.nice-detail {
    padding-top: 30px;
}
.nice-detail.fixed {
}
.nice-detail.fixed .nice-left {
    position: fixed;
    left: 50%;
    top: 30px;
    margin-left: -620px;
}
.nice-left {
    float: left;
    position: relative;
    width: 124px;
}
.nice-left ul {
    height: 682px;
    overflow: auto;
}
.nice-right {
    float: right;
    width: 1100px;
}
.nice-left li {
    margin-bottom: 2px;
}
.nice-left li:last-of-type {
    margin-bottom: 0px;
}
.nice-left a {
    display: block;
    padding: 0px 10px;
    line-height: 36px;
    font-size: 15px;
    background-color: #f0f0f0;
}
.nice-left a.active {
    color: #fff;
    background-color: #ff5c00;
}
.nice-left a i {
    font-weight: bold;
    font-size: 17px;
    vertical-align: -1px;
}
.nice-left a.active i {
    color: #fff;
}
.nice-right .nice-first-info {
    position: relative;
    margin-bottom: 20px;
    padding: 20px;
    /*height: 120px;*/
    overflow: hidden;
    background-color: #f6f6f6;
}
.nice-right .nice-first-info.open .tips {
    display: block;
}
.nice-right .nice-first-info.open .arrow {
    /* background: url(../images/narrow-up.png) no-repeat right center; */
}
.nice-right .nice-first-info .arrow {
    position: absolute;
    right: 15px;
    top: 15px;
    padding-right: 15px;
    /* background: url(../images/narrow-down.png) no-repeat right center; */
}
.nice-right .nice-first-info h2 {
    margin-bottom: 10px;
    font-size: 20px;
    color: #ff5c00;
}
.nice-right .nice-first-info p {
    font-size: 16px;
}
.nice-right .nice-first-info .tips {
    display: none;
    margin-top: 5px;
}
.nice-right .nice-first-info .tips span {
    display: inline-block;
    margin-top: 17px;
    margin-bottom: 12px;
    color: #ff5c00;
    font-size: 14px;
}
.nice-right .nice-first-info .tips p {
    font-size: 14px;
}
.nice-group.fixed {
    padding-left: 200px;
}
.nice-group.fixed .nice-group-list {
    position: fixed;
    left: 50%;
    top: 30px;
    margin-left: -480px;
    height: 682px;
    overflow: auto;
}
.nice-group-list {
    float: left;
    margin-right: 40px;
    width: 160px;
}
.nice-group-list a {
    display: block;
    padding-left: 10px;
    line-height: 36px;
    font-size: 15px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.nice-group-list a.active {
    position: relative;
    color: #ff5c00;
}
.nice-group-list a.active:before {
    content: "";
    display: block;
    position: absolute;
    top: 50%;
    left: 0px;
    margin-top: -10px;
    width: 3px;
    height: 20px;
    background-color: #ff5c00;
}
.nice-group-detail {
    float: left;
    padding-top: 10px;
    padding-bottom: 100px;
    width: 800px;
}
.nice-group-detail .keyword {
    display: inline-block;
    height: 16px;
    line-height: 16px;
    color: #fff;
    background-color: #ff5c00;
}
.nice-detail-list h3 {
    margin-bottom: 10px;
    padding-left: 10px;
    font-size: 16px;
    line-height: 1.1em;
    color: #ff5c00;
    border-left: 3px solid #ff5c00;
}
.nice-detail-list p {
    margin-bottom: 10px;
    line-height: 20px;
}
.nice-detail-list .ab-norm-goods span,
.history-goods span {
    display: block;
    margin-top: 20px;
    margin-bottom: 10px;
    padding-left: 10px;
    line-height: 28px;
}
.nice-detail-list .ab-norm-goods span {
    color: #659232;
    background-color: #ebfcef;
}
.history-more {
    float: right;
    margin-right: 5px;
    padding-right: 15px;
    color: #666;
    /* background: url(../images/narrow-down.png) no-repeat right center; */
}
.history-goods {
    margin-bottom: 10px;
    height: 120px;
    overflow: hidden;
}
.history-goods.open {
    height: auto;
}
.history-goods.open .history-more {
    /* background: url(../images/narrow-up.png) no-repeat right center; */
}
.nice-detail-list .history-goods span {
    color: #f08b2f;
    background-color: #fff8f5;
}
.nice-right .no-data {
    font-size: 20px;
    padding-top: 100px;
    text-align: center;
}
.nice-left-go {
    position: absolute;
    width: 100%;
    height: 30px;
    cursor: pointer;
}
.nice-left-top {
    top: -30px;
    /* background: url(../images/nice-au.png) no-repeat center; */
    background-size: 20px auto;
}
.nice-left-bottom {
    bottom: -30px;
    /* background: url(../images/nice-ad.png) no-repeat center; */
    background-size: 20px auto;
}
</style>
