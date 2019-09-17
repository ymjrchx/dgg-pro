<template>
    <div class="content">
        <div class="qianzhidaoQA-wrap">
            <div class="qianzhidaoQA">
                <div class="n-box">
                    <!-- start 搜索框 -->
                    <div class="qds-search-common">
                        <dl class="search-select" id="search-select">
                            <dt class="select-title" @click="showType">
                                <span class="text" id="1">{{docArr.length?docArr[typeIndex].name.substr(0, 2): ''}}</span>
                                <em v-if="docArr.length" class="iconfont">&#xe6b9;</em>
                            </dt>
                            <dd class="select-body" :class="{'atthis': isShow}" @mouseover="showType" @mouseout="hideType">
                                <span v-if="docArr.length" v-for="(item,index) in docArr" :key="item.code" @click="() => selectType(index)" class="item" id="1">{{item.name.substr(0, 2)}}</span>
                            </dd>
                        </dl>
                        <input type="text" class="search-text" @keyup.enter="search" v-model="keywords" name="keywords" placeholder="请输入您要搜索的关键字">
                        <i class="iconfont search-btn" @click="search">&#xe678;</i>
                    </div>
                    <!-- end 搜索框 -->
                </div>
                <!-- start mytab -->
                <div class="mytab">
                    <div class="mytap-header mytap-header-1" v-if="$route.params.type">
                        <div class="box">
                            <ul class="item">
                                <li v-for="item in docArr" :key="item.code" class="list" :class="{'selected': item.code == $route.params.type.split('.')[0].split(',')[0]}">
                                    <a :href="'/question/'+item.code+'.html'">{{item.name}}</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="mytap-body mytap-body-1">
                        <!-- start 商标业务 -->
                        <div class="content selected">
                            <!-- start mytab -->
                            <div class="mytab">
                                <!-- start mytap-body -->
                                <div class="mytap-body bbs-post">
                                    <!-- start 左侧内容 -->
                                    <div class="have-answer">
                                        <!-- start 左侧内容 -->
                                        <div class="bbs-post-left" v-if="showInfo">
                                            <div class="site">
                                                <a href="/">首页</a>
                                                <i class="songti"> &gt; </i>
                                                <a :href="'/question/'+ showInfo.question.typeLevel1Code">{{type}}
                                                    <i class="songti"> &gt;</i>
                                                </a>
                                                <i class="songti"> </i>
                                                <span>{{showInfo.question.question}}</span>
                                            </div>
                                            <!-- start 主帖子-->
                                            <div class="main-post">
                                                <h1 class="t-title">{{showInfo.question.question}}</h1>
                                                <p class="t-p">{{showInfo.question.question}}</p>
                                                <dl class="t-bottom">
                                                    <dt class="t-b">
                                                        <em class="iconfont pr5">&#xe6a4;</em>
                                                        <span class="s1">{{showInfo.question.keyWord}}</span>
                                                    </dt>
                                                    <dt class="t-b">已有
                                                        <i class="color">{{showInfo.question.answerCount}}</i>人回答</dt>
                                                    <dt class="t-b">
                                                    </dt>
                                                    <dt class="t-b">提问时间：{{showInfo.question.createTime}}</dt>
                                                    <dt class="t-b t-b-shoucang">
                                                    </dt>
                                                </dl>
                                                <dl class="t-bottom">
                                                    <dd class="t-b">
                                                        <span>标签：{{showInfo.question.keyWord}}</span>
                                                    </dd>
                                                </dl>
                                            </div>
                                            <!-- end 主帖子-->
                                            <!-- start 二级回贴 -->
                                            <div class="sub-reply" v-if="showInfo.answer">
                                                <dl class="agent clearfix">
                                                    <dt class="agent-head">
                                                        <img class="pic" src="../../../assets/images/bg-6.png" :alt="showInfo.answer.createrName" :title="showInfo.answer.createrName">
                                                    </dt>
                                                    <dd class="agent-intro">
                                                        <div class="p1">
                                                            <span class="s1">
                                                                <em class="iconfont">&#xe661;</em> {{showInfo.question.viewTimes}}次</span>
                                                        </div>
                                                        <dl class="p2">
                                                            <dd class="p2-text">
                                                                <div class="t-top">
                                                                    <span>{{showInfo.answer.answer}}</span>
                                                                </div>
                                                                <ul class="t-bottom">
                                                                    <li class="t-b-2">
                                                                        <em class="iconfont pr5">&#xe629;</em>
                                                                        <span class="s1">{{showInfo.answer.createTime}}</span>
                                                                    </li>
                                                                </ul>
                                                            </dd>
                                                        </dl>
                                                    </dd>
                                                </dl>
                                            </div>
                                        </div>
                                        <!-- end 左侧内容 -->
                                        <!-- start 右侧内容 -->
                                        <div class="c-right">
                                            <div class="agent">
                                                <h6 class="agent-header">
                                                    <i class="icon icon-consult"></i>顾问与帮助</h6>
                                                <ul class="agent-bodyer">
                                                    <li class="list">
                                                        <dl class="clearfix">
                                                            <dt><img src="../../../assets/images/show/p4.jpg" alt="代理人"></dt>
                                                            <dd>
                                                                <span class="r-1">刘朝阳
                                                                    <i class="icon-renzheng">已认证</i>
                                                                </span>
                                                                <span class="r-2">好评率：97.26%</span>
                                                                <a class="r-3" href="javascript:;" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)">咨询一下</a>
                                                            </dd>
                                                        </dl>
                                                    </li>
                                                    <li class="list">
                                                        <dl class="clearfix">
                                                            <dt><img src="../../../assets/images/show/p2.jpg" alt="代理人"></dt>
                                                            <dd>
                                                                <span class="r-1">李亚南
                                                                    <i class="icon-renzheng">已认证</i>
                                                                </span>
                                                                <span class="r-2">好评率：100%</span>
                                                                <a class="r-3" href="javascript:;" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)">咨询一下</a>
                                                            </dd>
                                                        </dl>
                                                    </li>
                                                    <li class="list">
                                                        <dl class="clearfix">
                                                            <dt><img src="../../../assets/images/show/p3.jpg" alt="代理人"></dt>
                                                            <dd>
                                                                <span class="r-1">高峰
                                                                    <i class="icon-renzheng">已认证</i>
                                                                </span>
                                                                <span class="r-2">好评率：100%</span>
                                                                <!--<a class="r-3" href="/userim/im?agent=5a7234566f6a7855736d6973687334487366677079773d3d" target="_blank">咨询一下</a>-->
                                                                <a class="r-3" href="javascript:;" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)">咨询一下</a>
                                                            </dd>
                                                        </dl>
                                                    </li>
                                                </ul>
                                            </div>
                                            <!-- end 代理人 -->
                                            <div class="item item2">
                                                <h6 class="i-header">
                                                    <span class="text">您可能需要的业务</span>
                                                    <a class="icon icon-refresh" href="javascript:;" title="换一组" style="display: none;"></a>
                                                </h6>
                                                <div class="i-body" v-if="product">
                                                    <dl v-for="item in [0,1,2]" :key="product[item].serviceItem.id">
                                                        <dt>
                                                            <a :href="getUrl(product[item].serviceItem.typeLevel3Code, product[item].serviceItem.number)"><img src="../../../assets/images/show/img_product.jpg" :alt="product[item].serviceItem.name" :title="product[item].serviceItem.name"></a>
                                                        </dt>
                                                        <dd>
                                                            <a href="/sb/bmkszc">
                                                                <span class="s1">{{product[item].serviceItem.name}}</span>
                                                                <span class="s2" v-if="product[item].serviceItem.servicePrice">服务费：￥{{product[item].serviceItem.servicePrice}}</span>
                                                                <span class="s3" v-if="product[item].serviceItem.officialPrice">官费：￥{{product[item].serviceItem.officialPrice}}</span>
                                                            </a>
                                                        </dd>
                                                    </dl>
                                                    <!-- 如果专利业务 -->
                                                    <!-- 如果版权业务 -->
                                                </div>
                                            </div>
                                        </div>
                                        <!-- end 右侧内容 -->
                                    </div>
                                    <!-- end mytap-body -->
                                </div>
                                <!-- end mytab -->
                            </div>
                            <!-- end 商标业务 -->
                        </div>
                        <!-- end mytap-body -->
                    </div>
                    <!-- end mytab -->
                </div>
            </div>
        </div>
    </div>
    <!-- end 左侧内容 -->
</template>
<script>
import axios from "axios";

export default {
    head() {
        return {
            title: `${
                this.showInfo ? this.showInfo.question.question : ""
            }-问答专区-知千秋官网`,
            meta: [
                {
                    name: "keywords",
                    hid: "keywords",
                    content: `${
                        this.showInfo ? this.showInfo.question.keyWord : ""
                    }`
                },
                {
                    name: "description",
                    hid: "description",
                    content: `${
                        this.showInfo ? this.showInfo.question.question : ""
                    }`
                }
            ],
            link:[
                {
                    rel: "canonical",
                    content: this.baseUrl
                }
            ]
        };
    },
    async asyncData({ params, redirect, error, env, store, route }) {
        if (!params.id) {
            error({ statusCode: 500, message: "参数错误" });
            return false;
        }
        let paramsTemp = params.id.split(".")[0];

        return Promise.all([
            axios.get(
                `${env.baseUrl}/question/findByQuestionId?id=${paramsTemp}`
            ),
            axios.get(`${env.baseUrl}serviceItem/findServiceItemByNumber`, {
                params: {
                    numbers:
                        "S7741101919677030400,S7741102108546539521,S7741102510755127297"
                }
            }),
            axios.get(`${env.baseUrl}navigation/getTreeData`)
        ]).then(res => {
            // 不存在数据
            if (!res[0].data.data) {
                error({ statusCode: 500, message: "暂无数据" });
            } else {
                let typeArr = {
                    navigation_trademark: "商标业务",
                    navigation_patent: "专利业务",
                    navigation_copyright: "版权业务"
                };
                return {
                    showInfo: res[0].data.data,
                    product: res[1].data ? res[1].data.data : "",
                    typeIndex: 0,
                    docArr: res[2].data.data.sonArr,
                    type: typeArr[res[0].data.data.question.typeLevel1Code],
                    baseUrl: env.webSite + route.fullPath
                };
            }
        });
    },
    layout: "question",
    components: {},
    mounted() {},
    data() {
        return {
            isShow: false,
            typeIndex: 0,
            keywords: ""
        };
    },
    methods: {
        showType() {
            this.isShow = true;
        },
        getUrl(code, id) {
            if (id) {
                return `/show/${code}&${id}.html`;
            }
            return `/show/${code}.html`;
        },
        hideType() {
            this.isShow = false;
        },
        selectType(value) {
            this.typeIndex = value;
        },
        search() {
            this.$router.push(
                `/question/${this.$store.state.docArr[this.typeIndex].code},1,${
                    this.keywords
                }.html`
            );
        }
    }
};
</script>
<style scoped>
/* ---------- start 权知道 ---------- */

/*代理人回帖*/

.qianzhidaoQA-wrap .bbs-post {
    width: 1240px;
    margin: auto;
}

.qianzhidaoQA-wrap .bbs-post .bbs-post-left {
    width: 950px;
    float: left;
}

/*主帖子*/

.qianzhidaoQA-wrap .bbs-post .bbs-post-left .main-post {
}

.qianzhidaoQA-wrap .bbs-post .bbs-post-left .main-post .t-title {
    font-size: 28px;
    color: #333;
}

.qianzhidaoQA-wrap .bbs-post .bbs-post-left .main-post .t-p {
    padding: 14px 0px 0;
    font-size: 16px;
    color: #666;
    line-height: 26px;
}

.qianzhidaoQA-wrap .bbs-post .bbs-post-left .main-post .t-p a {
    color: #999;
}

.qianzhidaoQA-wrap .bbs-post .bbs-post-left .main-post .t-p a:hover {
    color: #f08b30;
}

.qianzhidaoQA-wrap .bbs-post .bbs-post-left .main-post .t-bottom {
    overflow: hidden;
    padding: 14px 0 0;
    font-size: 12px;
    color: #999;
}

.qianzhidaoQA-wrap .bbs-post .bbs-post-left .main-post .t-bottom a {
    color: #999;
}

.qianzhidaoQA-wrap .bbs-post .bbs-post-left .main-post .t-bottom .t-b {
    float: left;
    margin-right: 25px;
    margin-bottom: 5px;
    color: #666;
}

.qianzhidaoQA-wrap .bbs-post .bbs-post-left .main-post .t-bottom dd.t-b a {
    display: ininline-block;
    margin-right: 10px;
    padding: 3px 15px;
    color: #666;
    border: 1px solid #e3e3e3;
    border-radius: 20px;
}

.qianzhidaoQA-wrap
    .bbs-post
    .bbs-post-left
    .main-post
    .t-bottom
    dd.t-b
    a:hover {
    color: #f08b2f;
    border: 1px solid #f08b2f;
}

.qianzhidaoQA-wrap .bbs-post .bbs-post-left .main-post .t-bottom .icon {
    float: left;
    width: 14px;
    height: 14px;
    margin-right: 8px;
}

.qianzhidaoQA-wrap .bbs-post .bbs-post-left .main-post .t-bottom .icon-eye {
    width: 16px;
    height: 9px;
    margin: 2px 8px 0 0;
}

.qianzhidaoQA-wrap .bbs-post .bbs-post-left .main-post .t-bottom .icon-share-1 {
    width: 16px;
    height: 16px;
    margin-top: -2px;
}

.qianzhidaoQA-wrap .bbs-post .bbs-post-left .main-post .t-bottom .color {
    color: #f08b2f;
    display: inline-block;
    padding: 0 4px;
}

/*最佳回贴*/

.qianzhidaoQA-wrap .main-reply {
}

.qianzhidaoQA-wrap .main-reply .agent {
    height: 100px;
    margin-top: 50px;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-head {
    float: left;
    width: 100px;
    height: 100%;
    border-radius: 50%;
    overflow: hidden;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-head img {
    width: 100%;
    min-height: 100%;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro {
    float: left;
    height: 100%;
    margin-left: 20px;
    width: 788px;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro .p1 {
    overflow: hidden;
    margin: 5px 0px 10px;
    font-size: 0;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro .p1 span {
    display: block;
    float: left;
    font-size: 12px;
    color: #999;
    margin: 4px 0 0;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro .p1 .s1 {
    font-size: 16px;
    color: #555;
    margin-top: -1px;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro .p1 .s2 {
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro .p1 .s3 {
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro .p1 .icon {
    display: block;
    float: left;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro .p1 .icon-renzheng {
    width: 33px;
    height: 13px;
    margin: 4px 30px 0px 5px;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro .p1 .icon-zan {
    width: 12px;
    height: 12px;
    margin: 4px 4px 0px 25px;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro .p1 a:hover .icon-zan {
    background-position: -316px 0px;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro .p1 a .icon-zan-selected {
    background-position: -316px 0px;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro .p2 {
    font-size: 12px;
    color: #999;
    height: 20px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro .p-btn {
    margin-top: 15px;
    font-size: 0;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro .p-btn .btn {
    display: inline-block;
    margin: 0 12px 0 0;
    height: 30px;
    line-height: 28px;
    border: solid 1px #f08b30;
    color: #f08b30;
    text-align: center;
    font-size: 0;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro .p-btn .btn .s1 {
    display: inline-block;
    height: 100%;
    font-size: 12px;
    vertical-align: middle;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro .p-btn .btn .icon-message {
    display: inline-block;
    width: 16px;
    height: 14px;
    vertical-align: middle;
    margin-right: 6px;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro .p-btn .btn-1 {
    width: 130px;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro .p-btn .btn-2 {
    width: 86px;
}

.qianzhidaoQA-wrap .main-reply .agent .agent-intro .color {
    color: #ee8c3c;
}

/*帖子的内容*/

.qianzhidaoQA-wrap .main-reply .post-content {
    margin-top: 20px;
    padding: 15px 15px 20px;
    border: solid 1px #f0f0f0;
    position: relative;
}

.qianzhidaoQA-wrap .main-reply .post-content .icon-arrow-up-1 {
    width: 22px;
    height: 15px;
    position: absolute;
    top: -15px;
    left: 36px;
    background-color: #fff;
}

.qianzhidaoQA-wrap .main-reply .post-content .t-p {
    font-size: 12px;
    color: #999;
    line-height: 26px;
}

.qianzhidaoQA-wrap .main-reply .post-content .t-bottom {
    overflow: hidden;
    padding: 10px 0 0;
    font-size: 12px;
    color: #999;
}

.qianzhidaoQA-wrap .main-reply .post-content .t-bottom a {
    color: #999;
}

.qianzhidaoQA-wrap .main-reply .post-content .t-bottom dt {
    float: left;
    margin-right: 45px;
}

.qianzhidaoQA-wrap .main-reply .post-content .t-bottom .icon {
    float: left;
    width: 14px;
    height: 14px;
}

.qianzhidaoQA-wrap .main-reply .post-content .t-bottom .s1 {
    width: 100%;
    margin-left: -14px;
    padding: 0 10px 0 20px;
}

.qianzhidaoQA-wrap .main-reply .post-content .t-bottom a:hover {
    color: #ee8c3c;
}

.qianzhidaoQA-wrap .main-reply .post-content .t-bottom a:hover .icon-pinglun {
    background-position: -287px 0px;
}

.qianzhidaoQA-wrap .main-reply .post-content .er-reply-box {
    width: 100%;
    margin-top: 10px;
    display: none;
}

.qianzhidaoQA-wrap .main-reply .post-content .er-reply-box .er-reply-input {
    width: 100%;
    height: 52px;
    padding: 8px 10px;
    border: solid 1px #eee;
    resize: none;
}

.qianzhidaoQA-wrap .main-reply .post-content .er-reply-box .btn-box {
    text-align: right;
    padding: 10px 0 0;
}

.qianzhidaoQA-wrap .main-reply .post-content .er-reply-box .btn-box .btn {
    height: 26px;
    line-height: 24px;
    padding: 0 20px;
    font-size: 12px;
    margin: 0 0 0 15px;
}

.qianzhidaoQA-wrap .main-reply .post-content .reply-content-box .reply-content {
}

.qianzhidaoQA-wrap .main-reply .post-content .reply-content-box .reply-content {
    width: 100%;
    padding: 15px 15px 15px 0px;
    border-top: solid 1px #f0f0f0;
    margin-top: 20px;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .p1 {
    overflow: hidden;
    margin: 0px 0px 9px;
    color: #777;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .p1
    .s1 {
    display: block;
    float: left;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .p1
    .s2 {
    float: right;
    padding-top: 3px;
    text-align: right;
    font-size: 0px;
    color: #999;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .p1
    .s2
    a {
    color: #999;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .p1
    .s2
    li {
    display: inline-block;
    margin-left: 45px;
    font-size: 12px;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .p1
    .s2
    .icon {
    float: left;
    width: 14px;
    height: 14px;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .p1
    .s2
    .s1 {
    width: 100%;
    margin-left: -14px;
    padding: 0 0px 0 20px;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .p1
    .s2
    a:hover {
    color: #ee8c3c;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .p1
    .s2
    a:hover
    .icon-pinglun {
    background-position: -287px 0px;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .p1
    .s2
    a:hover
    .icon-delete {
    background-position: -345px 0px;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .t-bottom {
    overflow: hidden;
    padding: 10px 0 0;
    font-size: 12px;
    color: #999;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .t-bottom
    a {
    color: #999;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .t-bottom
    li {
    float: left;
    margin-right: 45px;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .t-bottom
    .icon {
    float: left;
    width: 14px;
    height: 14px;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .t-bottom
    .s1 {
    width: 100%;
    margin-left: -14px;
    padding: 0 10px 0 20px;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .t-bottom
    a:hover {
    color: #ee8c3c;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .t-bottom
    a:hover
    .icon-pinglun {
    background-position: -287px 0px;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .color {
    color: #ee8c3c;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .p2 {
    font-size: 12px;
    color: #999;
    overflow: hidden;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .p2
    .p2-text {
    float: right;
    width: 100%;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .p2
    .p2-text
    .t-top {
    line-height: 24px;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .p2.have-uploadPic {
    height: 72px;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .p2.have-uploadPic
    .p2-pic {
    float: left;
    width: 110px;
    height: 100%;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .p2.have-uploadPic
    .p2-pic
    img {
    width: 100%;
    height: 100%;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .p2.have-uploadPic
    .p2-text {
    width: 690px;
}

.qianzhidaoQA-wrap
    .main-reply
    .post-content
    .reply-content-box
    .reply-content
    .p2.have-uploadPic
    .p2-text
    .t-top {
    margin-top: -5px;
}

/*二级回贴*/

.qianzhidaoQA-wrap .sub-reply {
    margin: 15px 0 30px;
    padding-left: 30px;
}

.qianzhidaoQA-wrap .sub-reply .agent {
    padding: 15px 15px 15px 0px;
    border: solid 1px #f0f0f0;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-head {
    float: left;
    width: 50px;
    margin-left: -25px;
    overflow: hidden;
    text-align: center;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-head .pic {
    width: 100%;
    height: 50px;
    border-radius: 50%;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-head .t1 {
    display: inline-block;
    width: 32px;
    height: 32px;
    margin-top: 12px;
    border: solid 1px #f08c2e;
    background-color: #fff;
    border-radius: 2px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-head .t1 .icon {
    display: inline-block;
    width: 9px;
    height: 6px;
    margin-top: 4px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-head .t1 .s1 {
    display: block;
    height: 16px;
    margin-top: 4px;
    color: #f08c2e;
    font-size: 12px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-head .t1:hover {
    background-color: #f08c2e;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-head .t1:hover .icon {
    background-position: -83px 0px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-head .t1:hover .s1 {
    color: #fff;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-head .vote-up-selected {
    background-color: #f08c2e;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-head .vote-up-selected .icon {
    background-position: -83px 0px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-head .vote-up-selected .s1 {
    color: #fff;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-head .t2 {
    display: inline-block;
    width: 32px;
    height: 14px;
    margin-top: 4px;
    border: solid 1px #f0f0f0;
    background-color: #fff;
    border-radius: 2px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-head .t2 .icon {
    display: inline-block;
    width: 9px;
    height: 6px;
    margin-top: 3px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro {
    float: right;
    width: 818px;
    padding-bottom: 10px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .p1 {
    overflow: hidden;
    margin: 0px 0px 9px;
    color: #777;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .p1 .s1 {
    display: block;
    float: left;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .p1 .s2 {
    float: right;
    padding-top: 3px;
    text-align: right;
    font-size: 0px;
    color: #999;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .p1 .s2 a {
    color: #999;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .p1 .s2 li {
    display: inline-block;
    margin-left: 45px;
    font-size: 12px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .p1 .s2 .icon {
    float: left;
    width: 14px;
    height: 14px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .p1 .s2 .s1 {
    width: 100%;
    margin-left: -14px;
    padding: 0 0px 0 20px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .p1 .s2 a:hover {
    color: #ee8c3c;
}

.qianzhidaoQA-wrap
    .sub-reply
    .agent
    .agent-intro
    .p1
    .s2
    a:hover
    .icon-pinglun {
    background-position: -287px 0px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .p1 .s2 a:hover .icon-delete {
    background-position: -345px 0px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .t-bottom {
    overflow: hidden;
    padding: 10px 0 0;
    font-size: 12px;
    color: #999;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .t-bottom a {
    color: #999;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .t-bottom li {
    float: left;
    margin-right: 45px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .t-bottom .icon {
    float: left;
    width: 14px;
    height: 14px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .t-bottom .s1 {
    width: 100%;
    margin-left: -14px;
    padding: 0 10px 0 13px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .t-bottom a:hover {
    color: #ee8c3c;
}

.qianzhidaoQA-wrap
    .sub-reply
    .agent
    .agent-intro
    .t-bottom
    a:hover
    .icon-pinglun {
    background-position: -287px 0px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .color {
    color: #ee8c3c;
}

.qianzhidaoQA-wrap .sub-reply .agent .reply-three-level {
    border-top: solid 1px #f0f0f0;
    padding: 18px 0 0px;
    margin-top: 18px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .p2 {
    font-size: 12px;
    color: #999;
    overflow: hidden;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .p2 .p2-text {
    float: right;
    width: 100%;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .p2 .p2-text .t-top {
    line-height: 24px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .p2.have-uploadPic {
    height: 72px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .p2.have-uploadPic .p2-pic {
    float: left;
    width: 110px;
    height: 100%;
}

.qianzhidaoQA-wrap
    .sub-reply
    .agent
    .agent-intro
    .p2.have-uploadPic
    .p2-pic
    img {
    width: 100%;
    height: 100%;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .p2.have-uploadPic .p2-text {
    width: 690px;
}

.qianzhidaoQA-wrap
    .sub-reply
    .agent
    .agent-intro
    .p2.have-uploadPic
    .p2-text
    .t-top {
    margin-top: -5px;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .er-reply-box {
    width: 100%;
    margin-top: 10px;
    display: none;
}

.qianzhidaoQA-wrap
    .sub-reply
    .agent
    .agent-intro
    .er-reply-box
    .er-reply-input {
    width: 100%;
    height: 52px;
    padding: 8px 10px;
    border: solid 1px #eee;
    resize: none;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .er-reply-box .btn-box {
    text-align: right;
    padding: 10px 0 0;
}

.qianzhidaoQA-wrap .sub-reply .agent .agent-intro .er-reply-box .btn-box .btn {
    height: 26px;
    line-height: 24px;
    padding: 0 20px;
    font-size: 12px;
    margin: 0 0 0 15px;
}

.m-question-container {
    margin-bottom: 30px;
}

.m-question-container h2 {
    margin-bottom: 15px;
    padding-bottom: 15px;
    font-size: 18px;
    color: #333;
    font-weight: bold;
    border-bottom: 1px solid #e3e3e3;
}

.m-question-container h2 a {
    float: right;
    margin-top: 5px;
    color: #666;
    font-size: 14px;
    font-weight: normal;
}

.m-question-container ul {
}

.m-question-container ul li {
    margin-bottom: 15px;
}

.m-question-container ul li a {
    display: inline-block;
    position: relative;
    padding-left: 30px;
    width: 680px;
    height: 1.4em;
    font-size: 14px;
    color: #333;
    overflow: hidden;
}

.m-question-container ul li a:before {
    content: "";
    position: absolute;
    left: 10px;
    top: 50%;
    margin-top: -3px;
    width: 6px;
    height: 6px;
    border-radius: 50%;
    background-color: #333;
}

.m-question-container ul li a:hover:before {
    background-color: #f08b2f;
}

.m-question-container a:hover {
    color: #f08b2f;
}

.m-question-container .m-question-view {
    float: right;
    color: #666;
}

.pr5 {
    margin-right: 5px;
    font-size: 14px;
}

.site {
    font-size: 12px;
    color: #666;
    margin-bottom: 12px;
}

.site a {
    color: #666;
}

.site i {
    color: #888;
}
</style>
