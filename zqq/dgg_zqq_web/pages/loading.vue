<template>
    <div>
        <mini/>
        <div v-if='showTitle'>
            <div class="page-search w2">
                <span class="main-title">商标搜索</span>
                <div class="search-input">
                    <input placeholder="请输入商标名称" class="text fl" type="text" v-model="keyWord">
                    <a id="search" class="button search">搜索</a>
                    <div style="position: absolute;right: 135px; top: 5px;width: 30px;height: 28px;">
                        <i id="select-btn" class="icon-camera"></i>
                    </div>
                </div>
                <div class="search-right">
                    <a href="/classify" target="_blank" class="button button-white">商品分类</a>
                    <a href="/notice" target="_blank" class="button button-white">初审公告</a>
                </div>
            </div>
            <div class="page-nav" id="brandTitle">
                <div class="page-nav-in w2">
                    <a href="javascript:void(0)" v-for="(item,idx) in keyArr" :key="idx" :class="{'active':idx == keyIndex}">{{item.name}}</a>
                </div>
            </div>
        </div>

        <div class="w searchPatent" v-else>
            <div class="search-type-logo">
                <h2 class="weight">
                    <a data-href="http://so.quandashi.com?searchType=2">专利搜索</a>
                    <span class="icon-beta">beta</span>
                </h2>
                <p>丨最全数据库360°查询丨</p>
            </div>
            <div class="patent-searchBox-container search">
                <!-- start 专利搜索框 -->
                <div class="patent-searchBox clearfix">
                    <div class="qds-search-common">
                        <input type="text" class="search-text" placeholder="请输入搜索关键字，多个关键字用“、”号隔开" name="keyWords" v-model="keyWord">
                        <input type="submit" value="搜索" class="icon icon-search-btn search-btn button">
                        <div>
                            <ul class="keyWord-box songti"></ul>
                        </div>
                    </div>
                </div>
                <!-- end 专利搜索框 -->

                <!-- start 搜索框下面的5个条件 -->
                <div class="patent-condition clearfix selectAll">
                    <a class="list" v-for='(item,idx) in typeArr' :key='idx' :class="{'selected': !typeIndexArr[idx]}">
                        <i class="icon icon-checkbox-patent"></i>
                        <span class="text songti">{{item.name}}</span>
                    </a>
                </div>
                <!-- end 搜索框下面的5个条件 -->
            </div>
        </div>

        <div class="loader">
            <div class="loading-6">
                <i></i>
                <i></i>
                <i></i>
                <i></i>
                <i></i>
            </div>
            <p class="msg">数据量巨大，正在努力查询中.... <br><br>预计
                <span class="f20 col"> {{num}} </span>秒后出结果</p>
        </div>
        <div class="maskAll"></div>
    </div>
</template>
<script>
import mini from "~/components/header/mini";
export default {
    layout: "nothing",
    components: {
        mini
    },
    data() {
        return {
            keyIndex: 1, //搜索key，取值： 近似商标 nameJs; 精准商标 name; 申请号 regNo; 申请人 applicantCn; 代理公司 tmSection;
            keyArr: [
                { name: "近似商标", val: "" },
                { name: "精准商标", val: "" },
                { name: "申请号", val: "" },
                { name: "申请人", val: "" },
                { name: "商品服务", val: "" },
                { name: "图形搜索", val: "" },
                { name: "代理公司", val: "" }
            ],
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
            keyWord: "",
            num: 3,
            showTitle: 1
        };
    },
    head() {
        return {
            title: "搜索中-知千秋"
        };
    },
    created() {
        let url = this.$route.query.url;
        if (/patentSearch/.test(url)) {
            this.showTitle = 0;
        }
        if (url) this.keyWord = url.split(/\.|\//)[2];
    },
    mounted() {
        if (this.$route.query.url) this.$router.replace(this.$route.query.url);
        else {
            this.$router.replace("/");
        }
        let timer = setInterval(() => {
            if (this.num > 0) {
                this.num -= 1;
            } else {
                clearInterval(timer);
            }
        }, 1000);
    },
    methods: {}
};
</script>
<style scoped>
* {
    box-sizing: border-box;
}
.maskAll {
    position: fixed;
    left: 0;
    top: 0;
    background: transparent;
    z-index: 200;
    width: 100%;
    height: 100%;
}

.loader {
    width: 400px;
    height: 140px;
    /* border: 1px solid #ccc; */
    box-sizing: border-box;
    text-align: center;
    margin: auto;
    margin-top: 100px;
}
.msg {
    margin-top: 30px;
}
.loading-6 {
    width: 140px;
    height: 20px;
    position: relative;
    display: inline-block;
}
.loading-6 i {
    display: block;
    width: 20px;
    height: 20px;
    border-radius: 50%;
    background: #ff6600;
    margin-right: 10px;
    position: absolute;
}
.loading-6 i:nth-child(1) {
    animation: loading-6 2s linear 0s infinite;
    -webkit-animation: loading-6 2s linear 0s infinite;
}
.loading-6 i:nth-child(2) {
    animation: loading-6 2s linear -0.4s infinite;
    -webkit-animation: loading-6 2s linear -0.4s infinite;
}
.loading-6 i:nth-child(3) {
    animation: loading-6 2s linear -0.8s infinite;
    -webkit-animation: loading-6 2s linear -0.8s infinite;
}
.loading-6 i:nth-child(4) {
    animation: loading-6 2s linear -1.2s infinite;
    -webkit-animation: loading-6 2s linear -1.2s infinite;
}
.loading-6 i:nth-child(5) {
    animation: loading-6 2s linear -1.6s infinite;
    -webkit-animation: loading-6 2s linear -1.6s infinite;
}
@-webkit-keyframes loading-6 {
    0% {
        left: 100px;
        top: 0;
    }
    80% {
        left: 0;
        top: 0;
    }
    85% {
        left: 0;
        top: -20px;
        width: 20px;
        height: 20px;
    }
    90% {
        width: 40px;
        height: 20px;
    }
    95% {
        width: 40px;
        height: 20px;
        left: 100px;
        top: -20px;
    }
    100% {
        left: 100px;
        top: 0;
    }
}
@keyframes loading-6 {
    0% {
        left: 100px;
        top: 0;
    }
    80% {
        left: 0;
        top: 0;
    }
    85% {
        left: 0;
        top: -20px;
        width: 20px;
        height: 20px;
    }
    90% {
        width: 40px;
        height: 20px;
    }
    95% {
        width: 40px;
        height: 20px;
        left: 100px;
        top: -20px;
    }
    100% {
        left: 100px;
        top: 0;
    }
}
.page-checkbox {
    margin-top: 10px;
    margin-bottom: 20px;
    cursor: pointer;
}

.page-checkbox .checkbox-box {
    margin-right: 20px;
}

.page-checkbox span {
    font-size: 12px;
    display: inline-block;
    vertical-align: middle;
    margin-left: 4px;
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
    background: url("../assets/images/icon_photo.png") no-repeat center;
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

.checkbox.checked {
    background-image: url("../assets/images/searchList/icon-checked.png");
    border-color: #ff5c00;
    background-size: 90%;
}

.checkbox {
    display: inline-block;
    width: 13px;
    height: 13px;
    border-width: 1px;
    border-style: solid;
    border-color: #999;
    background-position: center;
    background-repeat: no-repeat;
    vertical-align: middle;
    user-select: none;
}
/* //专利导航 */
.searchPatent {
    margin-top: 30px;
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
.search {
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
/* ---------- 大搜索框 ---------- */
/* {
  margin: auto;
  padding-top: 30px;
}

.patent-search-Maintitle {
  float: left;
  width: 200px;
  height: 100px;
} */

.patent-searchBox-container {
    margin-left: 20px;
}

.patent-searchBox {
    margin-bottom: 10px;
    text-align: center;
    width: 100%;
}

.patent-searchBox .qds-search-common {
    float: left;
    position: relative;
}

.patent-searchBox .qds-search-common .search-text {
    position: relative;
    float: left;
    width: 725px;
    height: 50px;
    line-height: 50px;
    padding: 0 15px;
    border: solid 1px #f0f0f0;
    font-size: 16px;
    color: #999;
    background-color: #fff;
    width: 740px;
    border: solid 1px #f08c2f;
    color: #777;
}

.patent-searchBox .qds-search-common .search-text::-webkit-input-placeholder {
    font-size: 16px;
    color: #999999;
    font-family: "Microsoft Yahei", "微软雅黑";
}

.qds-search-common .search-btn {
    float: left;
    border: none;
    width: 140px;
    height: 50px;
    color: #fff;
    font-size: 24px;
    cursor: pointer;
    border-radius: 0 4px 4px 0;
    -webkit-border-radius: 0 4px 4px 0;
    -moz-border-radius: 0 4px 4px 0;
    -ms-border-radius: 0 4px 4px 0;
    -o-border-radius: 0 4px 4px 0;
}

.patent-searchBox .qds-search-common .clearAll {
    width: 20px;
    height: 20px;
    line-height: 18px;
    font-size: 20px;
    position: absolute;
    top: 14px;
    right: 150px;
    color: #bbb;
    text-align: center;
}

.patent-searchBox .qds-search-common .clearAll:hover {
    color: #f08b30;
}

.patent-searchBox .help {
    float: left;
    padding-right: 30px;
    position: relative;
    margin: 0 0 0 25px;
    line-height: 50px;
}

.patent-searchBox .help-tips {
    display: none;
    position: absolute;
    left: 80px;
    top: -50px;
    padding: 15px;
    width: 300px;
    text-align: justify;
    line-height: 1.2em;
    color: #888;
    background-color: #fff;
    border: 1px solid #ddd;
    border-radius: 5px;
}

.patent-searchBox .keyword-box {
    position: absolute;
    left: 12px;
    top: 12px;
    max-width: 925px;
    height: 28px;
    overflow: hidden;
}

.patent-searchBox .keyword-box .list {
    float: left;
    background-color: #fff;
    line-height: 24px;
    border: solid 1px #e9e9e9;
    padding: 0 8px;
    cursor: default;
    margin: 0 14px 4px 0;
    position: relative;
}

.patent-searchBox .keyword-box .list .text {
    display: block;
    float: left;
    font-size: 12px;
    color: #777;
    max-width: 125px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.patent-searchBox .keyword-box .list .icon-close {
    float: left;
    width: 14px;
    height: 14px;
    line-height: 14px;
    margin: 4px 0 0 6px;
    text-align: right;
    background: none;
    cursor: pointer;
    font-size: 20px;
    color: #bbb;
    overflow: hidden;
    font-family: "Microsoft Yahei", "微软雅黑";
}

.patent-searchBox .keyword-box .list:hover .icon-close {
    color: #f08b30;
}

.patent-searchBox .keyword-box .list .tipsMsg {
    display: none;
    border: solid 1px #e9e9e9;
    border-radius: 4px;
    z-index: 99;
    position: absolute;
    top: 34px;
    left: 0px;
    padding: 12px 15px;
    color: #777;
    background-color: #fff;
    font-size: 12px;
    white-space: nowrap;
    box-shadow: 0 0 12px #ddd;
}

.patent-searchBox .keyword-box .list .tipsMsg .icon-arrow {
    position: absolute;
    top: -8px;
    left: 20px;
    font-size: 20px;
    color: #e9e9e9;
    background-color: #fff;
    width: 8px;
    height: 9px;
    line-height: 15px;
    overflow: hidden;
    text-indent: -6px;
}
/* ---------- start 搜索框下面的5个条件 ---------- */
.icon {
    background-position: 0px 0px;
    background-repeat: no-repeat;
    display: block;
}

.icon-checkbox-patent {
    background-image: url("../assets/images/main/icon-patent.png");
    background-position: 0px 0px;
}

.patent-condition {
    margin-top: 10px;
    padding-left: 16px;
}

.patent-condition .list {
    display: block;
    float: left;
    margin: 0 22px 0 0;
    font-size: 13px;
    color: #333;
    cursor: pointer;
}

.patent-condition .list .icon-checkbox-patent {
    display: block;
    float: left;
    width: 13px;
    height: 13px;
    margin: 3px 5px 0 0;
}

.patent-condition .list.selected .icon-checkbox-patent {
    background-position: -15px 0px;
}

.patent-condition .list.selected {
    color: #f08c2f;
}
</style>
