<template>
    <div class="news-article-wrap" v-if="showInfo">
        <div class="news-article">
            <!-- start 左 -->
            <div class="news-article-l">
                <div class="myselectJS">
                    <h1 class="myselect-header" @click="getNewsType">{{newsTypeList[showInfo.type]}}</h1>
                    <ul class="myselect-bodyer" v-show="newsType" @mouseover="showType" @mouseout="closeType" v-if="newsTypeList">
                        <li>
                            <a class="menu" href="/news">全部</a>
                        </li>
                        <li>
                            <a class="menu" :key="index" v-for="(item, index) in newsTypeList" :href="'/news/'+index+'.html'">{{item}}</a>
                        </li>
                    </ul>
                </div>
                <div class="n-1">
                    <span class="text">
                        当前位置：
                        <a href="/">首页</a>
                        <i class="songti">&gt;</i>
                        <a :href="'/news/'+showInfo.type">{{newsTypeList[showInfo.type]}}</a>
                        <i class="songti">&gt;</i>
                        {{showInfo.title}}</span>
                </div>
                <h1 class="n-title">{{showInfo.title}}</h1>
                <dl class="n-info clearfix">
                    <dt class="author">
                        <span class="s1">{{showInfo.createTime}}</span>
                        <span class="s2">来源：{{showInfo.origin}}</span>
                        <span class="s2">浏览量：{{showInfo.viewTimes}}</span>
                    </dt>
                </dl>
                <dl class="tags">
                    <dt>标签：</dt>
                    <dd>
                        <a class="tag" href="javascript:void(0);" v-for="(item,idx) in splitFlag(showInfo.label)" :key='idx'>{{item}}</a>
                    </dd>
                </dl>
                <div class="article">
                    <!-- <span class="pic"><img src="/admin/file/getfile?path=third%2F2018%2F09%2F26%2Fcfbb61eac7ca0c44ca7c7826df5e4229.jpg" title="外观设计专利,外观专利" alt="外观设计专利,外观专利" /></span> -->
                    <div class="p" v-html="showInfo.content">

                    </div>
                </div>
                <!--<div><pre></div>-->
                <div class="dian-zan">
                    <a class="icon icon-zan-bg" @click="zan" href="javascript:;" title="赞一个" rel="12875">
                        <em class="iconfont icon-zan-shou">&#xe607;</em>
                        <span class="text">{{showInfo.praiseTimes?showInfo.praiseTimes:0}}</span>
                    </a>
                </div>
                <div class="relevant-article">
                    <!-- start 上一篇 -->
                    <div class="relevant-article-item relevant-article-prev">
                        <a href="javascript:;" class="icon icon-arrow-prev"></a>
                        <div class="list-box">
                            <ul class="list">
                                <li v-if="perv">
                                    <div class="pic">
                                        <a :href="'/news/show/'+perv.id+'.'+allParams[1]+'.'+allParams[2]+'.html'"><img width="120" height="95" :src="perv.newsPhoto" :onerror="errorImg"></a>
                                    </div>
                                    <div class="text">
                                        <div class="t1">
                                            <a :href="'/news/show/'+perv.id+'.'+allParams[1]+'.'+allParams[2]+'.html'">上一篇</a>
                                        </div>
                                        <h3 class="t2">
                                            <a :href="'/news/show/'+perv.id+'.'+allParams[1]+'.'+allParams[2]+'.html'">{{perv.title}}</a>
                                        </h3>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!-- end 上一篇 -->
                    <!-- start 下一篇 -->
                    <div class="relevant-article-item relevant-article-next">
                        <div class="list-box">
                            <ul class="list">
                                <li v-if="next">
                                    <div class="pic">
                                        <a :href="'/news/show/'+next.id+'.'+allParams[1]+'.'+allParams[2]+'.html'"><img width="120" height="95" :src="next.newsPhoto" :onerror="errorImg"></a>
                                    </div>
                                    <div class="text">
                                        <div class="t1">
                                            <a :href="'/news/show/'+next.id+'.'+allParams[1]+'.'+allParams[2]+'.html'">下一篇</a>
                                        </div>
                                        <h3 class="t2">
                                            <a :href="'/news/show/'+next.id+'.'+allParams[1]+'.'+allParams[2]+'.html'">{{next.title}}</a>
                                        </h3>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!-- end 下一篇 -->
                </div>
                <!-- end relevant-article-->
            </div>
            <!-- end 左 -->
            <!-- start 右 -->
            <div class="news-article-r">
                <div class="hot-posts">
                    <h3 class="hot-posts-title">最新文章</h3>
                    <ul class="hot-posts-body" v-if="newsList">
                        <li class="list" v-for="item in newsList" :key="item.id">
                            <div class="pic">
                                <a :href="'/news/show/'+item.id+'..1.html'">
                                    <img height="62" :src="item.newsPhoto?item.newsPhoto:'/default_new.jpg'" :onerror="errorImg">
                                </a>
                            </div>
                            <div class="text">
                                <a :href="'/news/show/'+item.id+'..1.html'" class="text-delic3">{{item.title}}</a>
                            </div>
                        </li>

                    </ul>
                </div>
                <div class="topic-brand-search" v-if="0">
                    <div class="topic-brand-search-head">
                        <i>免费</i>商标查询
                    </div>
                    <div class="topic-brand-search-form">
                        <form id="add_from" method="post">
                            <ul>
                                <li>
                                    <span>商标名称</span>
                                    <input type="text" name="brandName" class="text">
                                </li>
                                <li>
                                    <span>您的姓名</span>
                                    <input type="text" name="username" class="text">
                                </li>
                                <li>
                                    <span>
                                        <i>*</i>您的电话</span>
                                    <input type="text" name="userphone" class="text" id="input-contactphone">
                                    <input type="hidden" name="type" value="1">
                                </li>
                                <li>
                                    <span>
                                        <i>*</i>验证码</span>
                                    <div class="yzmbox">
                                        <input class="text" id="input-yzm" type="text">
                                        <span class="btn-send" init-time="90" id="btn-sendyzm">获取验证码</span>
                                    </div>
                                </li>
                            </ul>
                        </form>
                        <button class="button submit" onclick="sureSubmit()">获取查询结果</button>
                    </div>
                </div>
                <!-- start 广告轮播图 -->
                <!-- end 广告轮播图 -->
                <div class="rank-article" v-if="0">
                    <h1 class="i-header">热文榜</h1>
                    <ul class="tab-header clearfix">
                        <a class="tab" :class="{'active': tabIndex == 0}" @click="() => {switchTab(0)}" href="javascript:;">周排行</a>
                        <a class="tab" :class="{'active': tabIndex == 1}" @click="() => {switchTab(1)}" href="javascript:;">月排行</a>
                    </ul>
                    <div class="tab-main">
                        <ul class="tab-content" :class="{'active': tabIndex == 0}">
                            <!--<li class="list"><a href="#">1. 申颜色发生变化还受保护?</a></li>-->
                            <li class="list">
                                <a href="/news/newdetail/12812.html">1. 2018年6月各类别商标申请量排名丨权大师发布</a>
                            </li>
                            <li class="list">
                                <a href="/news/newdetail/2017.html">2. 长沙分公司注册流程及条件</a>
                            </li>
                            <li class="list">
                                <a href="/news/newdetail/12811.html">3. 2018年6月各省（市）商标申请量排名丨权大师发布</a>
                            </li>
                            <li class="list">
                                <a href="/news/newdetail/1320.html">4. 2017年五金商标注册</a>
                            </li>
                            <li class="list">
                                <a href="/news/newdetail/4245.html">5. 现在不注册商标，未来只能买商标了</a>
                            </li>
                            <li class="list">
                                <a href="/news/newdetail/12798.html">6. 2018年5月全国商标代理机构申请量排名（TOP100）丨权大师发布</a>
                            </li>
                            <li class="list">
                                <a href="/news/newdetail/8153.html">7. 盘锦商标转让</a>
                            </li>
                            <li class="list">
                                <a href="/news/newdetail/1031.html">8. 商标电子化注册3月10日全部开通 今年增设上海注册中心</a>
                            </li>
                            <li class="list">
                                <a href="/news/newdetail/1617.html">9. 2017年杭州办理注册商标费用与流程</a>
                            </li>
                            <li class="list">
                                <a href="/news/newdetail/31.html">10. 日本专利申请，你需要知道这些。</a>
                            </li>
                        </ul>
                        <ul class="tab-content" :class="{'active': tabIndex == 1}">
                            <li class="list">
                                <a href="/news/newdetail/12812.html">1. 2018年6月各类别商标申请量排名丨权大师发布</a>
                            </li>
                            <li class="list">
                                <a href="/news/newdetail/12811.html">2. 2018年6月各省（市）商标申请量排名丨权大师发布</a>
                            </li>
                            <li class="list">
                                <a href="/news/newdetail/2017.html">3. 长沙分公司注册流程及条件</a>
                            </li>
                            <li class="list">
                                <a href="/news/newdetail/1128.html">4. 省级著名商标申请的条件是什么？</a>
                            </li>
                            <li class="list">
                                <a href="/news/newdetail/12798.html">5. 2018年5月全国商标代理机构申请量排名（TOP100）丨权大师发布</a>
                            </li>
                            <li class="list">
                                <a href="/news/newdetail/1824.html">6. 杭州公司申请商标流程</a>
                            </li>
                            <li class="list">
                                <a href="/news/newdetail/1617.html">7. 2017年杭州办理注册商标费用与流程</a>
                            </li>
                            <li class="list">
                                <a href="/news/newdetail/5289.html">8. 第32类和第33类商标注册类别的区别</a>
                            </li>
                            <li class="list">
                                <a href="/news/newdetail/2287.html">9. 2016年开公司注册资金</a>
                            </li>
                            <li class="list">
                                <a href="/news/newdetail/11359.html">10. 商标注册管理改革驶入快车道</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import toTop from "@/components/toTop";
import popup from "@/components/popup";
import img from "~/assets/images/default_new.jpg";
import axios from "axios";
export default {
    async asyncData({ params, redirect, error, env, store, route }) {
        if (!params.id) {
            error({ statusCode: 500, message: "参数错误" });
            return false;
        }
        let allParams = params.id.split(".");
        let paramsTemp = params.id.split(".")[0];
        return Promise.all([
            axios.get(
                `${env.baseUrl}/news/content/?id=${paramsTemp}&type=${
                    allParams[1]
                }&pageNum=${allParams[2]}`
            ),
            axios.post(`${env.baseUrl}/news/all?pageSize=4&pageNum=1`),
            axios.get(`${env.baseUrl}/news/type`)
        ])
            .then(res => {
                let { data } = res[0];
                let desc;
                // 不存在数据
                if (!data.data) {
                    error({ statusCode: 500, message: "暂无数据" });
                } else {
                    desc = data.data.now.content;
                    desc = desc.replace(/(\n)/g, "");
                    desc = desc.replace(/(\t)/g, "");
                    desc = desc.replace(/(\r)/g, "");
                    desc = desc.replace(/<\/?[^>]*>/g, "");
                    desc = desc.replace(/\s*/g, "");
                    return {
                        allParams: allParams, //所有的传递过来的参数
                        showInfo: data.data.now,
                        perv: data.data.previous ? data.data.previous : "",
                        next: data.data.next ? data.data.next : "",
                        newsList: res[1].data.data.data,
                        newsTypeList: res[2].data.data,
                        desc: desc.length < 120 ? desc : desc.substring(0, 120)
                    };
                }
            })
            .catch(() => {
                error({ statusCode: 500, message: "参数错误" });
            });
    },
    head() {
        return {
            title: `${this.showInfo.title}-知千秋`,
            meta: [
                {
                    name: "keywords",
                    hid: "keywords",
                    content: `${this.showInfo.label}`
                },
                {
                    name: "description",
                    hid: "description",
                    content: `${this.desc}`
                }
            ]
        };
    },
    data() {
        return {
            newsType: false,
            tabIndex: 0,
            errorImg: 'this.src="' + img + '"'
        };
    },
    components: {
        toTop
    },
    mounted() {
        // console.log("参数", this.showInfo);
    },
    methods: {
        getNewsType() {
            this.newsType = !this.newsType;
        },
        switchTab(i) {
            this.tabIndex = i;
        },
        closeType(e) {
            if (e.target.tagName == "A") {
                this.newsType = false;
            }
        },
        showType(e) {
            this.newsType = true;
        },
        zan() {
            this.$Http(`/news/praise/${this.showInfo.id}`, {}, "get").then(
                res => {
                    popup.created({
                        content: "点赞成功",
                        time: 2000
                    });
                    this.showInfo.praiseTimes++;
                }
            );
        },
        splitFlag(str) {
            return str ? str.split(/，|-/) : [];
        }
    }
};
</script>
<style>
/* ---------- start 新闻文章内容 ---------- */

.news-article-wrap {
    width: 100%;
}

.article img {
    max-width: 100%;
}
.news-article-wrap .news-article {
    width: 1240px;
    margin: auto;
    padding-top: 38px;
    overflow: hidden;
}

/* ----- start 左 ----- */

.news-article-wrap .news-article-l {
    width: 845px;
    float: left;
}

.news-article-wrap .news-article-l .n-1 {
    font-size: 0;
    margin-top: -10px;
}

.news-article-wrap .news-article-l .n-1 .icon {
    display: inline-block;
    width: 14px;
    height: 14px;
    margin-right: 8px;
    vertical-align: top;
}

.news-article-wrap .news-article-l .n-1 .text {
    display: inline-block;
    color: #666;
    font-size: 12px;
    line-height: 14px;
}

.news-article-wrap .news-article-l .n-1 .text a {
    color: #666;
}

.news-article-wrap .news-article-l .n-1 .text a:hover {
    color: #f08c2f;
}

.news-article-wrap .news-article-l .n-title {
    width: 80%;
    color: #333;
    font-size: 28px;
    font-weight: bold;
    padding: 20px 0 10px;
}

.news-article-wrap .news-article-l .n-info {
    padding-left: 5px;
}

.news-article-wrap .news-article-l .n-info .author span {
    display: block;
    float: left;
    font-size: 12px;
    color: #666;
}

.news-article-wrap .news-article-l .n-info .author .s2 {
    margin-left: 40px;
}

.news-article-wrap .news-article-l .n-info .share-to {
    float: right;
    font-size: 0;
}

.news-article-wrap .news-article-l .n-info .share-to .text {
    display: inline-block;
    vertical-align: top;
    margin-right: 2px;
    font-size: 12px;
}

.news-article-wrap .news-article-l .n-info .share-to .icon {
    display: inline-block;
    width: 23px;
    height: 23px;
    margin: -3px 0 0 8px;
}

.news-article-wrap .news-article-l .tags {
    margin: 10px 0 0 5px;
    overflow: hidden;
}

.news-article-wrap .news-article-l .tags dt,
.news-article-wrap .news-article-l .tags dd {
    float: left;
    color: #666;
    font-size: 12px;
}

.news-article-wrap .news-article-l .tags dt {
    margin-right: 4px;
}

.news-article-wrap .news-article-l .tags dd .tag {
    display: block;
    float: left;
    margin-right: 10px;
    border: 1px solid #e3e3e3;
    border-radius: 100px;
    height: 20px;
    line-height: 18px;
    padding: 0 12px;
    font-size: 12px;
    color: #666;
}

.news-article-wrap .news-article-l .tags dd .tag:hover {
    border: 1px solid #f08b2f;
    color: #f08b2f;
}

.news-article-wrap .news-article-l .article {
    padding: 30px 0 0;
}

.news-article-wrap .news-article-l .article .pic {
    display: block;
    text-align: center;
    overflow: hidden;
    margin: 0 0 25px;
}

.news-article-wrap .news-article-l .article img {
    max-width: 90%;
    margin-top: 15px;
    margin-bottom: 15px;
}

.news-article-wrap .news-article-l .article p {
    text-indent: 2em;
    line-height: 27px;
    color: #555;
}

.news-article-wrap .news-article-l .dian-zan {
    text-align: center;
    padding: 45px 0 0;
}

.news-article-wrap .news-article-l .dian-zan .icon-zan-bg {
    display: inline-block;
    width: 80px;
    height: 80px;
    text-align: center;
    border-radius: 50%;
    background: #ff6600;
}

.news-article-wrap .news-article-l .dian-zan .icon-zan-bg .icon-zan-shou {
    display: inline-block;
    width: 30px;
    height: 30px;
    margin: 12px 0 8px;
    font-size: 28px;
    color: #fff;
}

.news-article-wrap .news-article-l .dian-zan .icon-zan-bg .text {
    display: block;
    color: #fff;
}

.news-article-wrap .news-article-l .dian-zan .icon-zan-bg.visited {
    background-position: -80px 0;
}

.news-article-wrap .news-article-l .relevant-article {
    overflow: hidden;
    margin: 65px 0 35px;
}

.news-article-wrap .news-article-l .relevant-article .relevant-article-item {
    float: left;
    height: 95px;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-item
    .icon {
    float: left;
    width: 12px;
    height: 22px;
    margin: 40px 0 0 1px;
    display: none;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-item
    .list-box {
    float: left;
    width: 275px;
    height: 100%;
    margin-left: 26px;
    overflow: hidden;
    position: relative;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-item
    .list-box
    .list {
    width: 2000px;
    overflow: hidden;
    position: absolute;
    top: 0;
    left: 0;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-item
    .list-box
    .list
    li {
    float: left;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-item
    .list-box
    .pic {
    float: left;
    width: 160px;
    height: 100%;
    overflow: hidden;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-item
    .list-box
    .pic
    img {
    width: 100%;
    -webkit-transition: 0.3s;
    -moz-transition: 0.3s;
    -ms-transition: 0.3s;
    -o-transition: 0.3s;
    transition: 0.3s;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-item
    .list-box
    .text {
    float: left;
    margin: 0 0 0 12px;
    width: 103px;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-item
    .list-box
    .text
    .t1 {
    padding: 2px 0 5px;
    font-size: 14px;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-item
    .list-box
    .text
    .t1
    a {
    color: #555;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-item
    .list-box
    .text
    .t1
    a:hover {
    color: #f08c2f;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-item
    .list-box
    .text
    .t2 {
    height: 50px;
    line-height: 26px;
    margin-top: 8px;
    font-size: 12px;
    overflow: hidden;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-item
    .list-box
    .text
    .t2
    a {
    color: #999;
    font-weight: normal;
    font-size: 12px;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-item
    .list-box
    li:hover
    .pic
    img {
    -webkit-transform: scale(1.1);
    -moz-transform: scale(1.1);
    -ms-transform: scale(1.1);
    -o-transform: scale(1.1);
    transform: scale(1.1);
}

/*下一篇*/

.news-article-wrap .news-article-l .relevant-article .relevant-article-next {
    float: right;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-next
    .icon {
    float: right;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-next
    .list-box {
    margin: 0 26px 0 0;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-next
    .list-box
    .list {
    right: 0;
    left: auto;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-next
    .list-box
    .list
    li {
    float: right;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-next
    .list-box
    .pic {
    float: right;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-next
    .list-box
    .text {
    margin: 0 12px 0 0;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-next
    .list-box
    .text
    .t1 {
    text-align: right;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-next
    .list-box
    .text
    .t2 {
    text-align: right;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-item:hover
    .list-box
    .text
    .t1
    a {
    color: #f08c2f;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-item:hover
    .icon-arrow-prev {
    background-position: 0px -22px;
}

.news-article-wrap
    .news-article-l
    .relevant-article
    .relevant-article-item:hover
    .icon-arrow-next {
    background-position: -12px -22px;
}

/* ----- start 右 热门文章----- */

.news-article-wrap .news-article-r {
    float: right;
    width: 310px;
    margin-top: 30px;
}

.news-article-wrap .news-article-r .hot-posts {
    border: solid 1px #eee;
}

.news-article-wrap .news-article-r .hot-posts-title {
    padding: 0 14px;
    height: 40px;
    line-height: 40px;
    border-bottom: solid 1px #eee;
    font-size: 20px;
    color: #818181;
}

.news-article-wrap .news-article-r .hot-posts-body {
    overflow: hidden;
    padding: 0 15px;
}

.news-article-wrap .news-article-r .list {
    overflow: hidden;
    padding: 15px 0;
    border-top: dotted 1px #ebebeb;
    margin-top: -1px;
}

.news-article-wrap .news-article-r .list .pic,
.news-article-wrap .news-article-r .list .text {
    float: left;
}

.news-article-wrap .news-article-r .list .pic {
    width: 108px;
    height: 62px;
    overflow: hidden;
}

.news-article-wrap .news-article-r .list .pic img {
    width: 100%;
    -webkit-transition: 0.3s;
    -moz-transition: 0.3s;
    -ms-transition: 0.3s;
    -o-transition: 0.3s;
    transition: 0.3s;
}

.news-article-wrap .news-article-r .list .text {
    width: 140px;
    height: 60px;
    line-height: 20px;
    padding: 6px 0 0 15px;
    overflow: hidden;
}

.news-article-wrap .news-article-r .list .text a {
    color: #818181;
    font-size: 12px;
}

.news-article-wrap .news-article-r .list:hover .text a {
    color: #f08c2f;
}

.news-article-wrap .news-article-r .list:hover .pic img {
    -webkit-transform: scale(1.1);
    -moz-transform: scale(1.1);
    -ms-transform: scale(1.1);
    -o-transform: scale(1.1);
    transform: scale(1.1);
}

.news-article-wrap .r-ad {
    width: 100%;
    height: 150px;
    overflow: hidden;
}

.news-article-wrap .r-ad img {
    width: 100%;
    height: 100%;
}

.news-article-wrap .r-ad .swiper-container {
    width: 100%;
    height: 100%;
}

.news-article-wrap .r-ad .swiper-slide {
    width: 310px;
    height: 100%;
}

.news-article-wrap .r-ad .swiper-pagination {
    position: absolute;
    bottom: 6px;
    width: 100%;
    text-align: center;
}

.news-article-wrap .r-ad .swiper-pagination .swiper-pagination-switch {
    display: inline-block;
    width: 10px;
    height: 10px;
    border-radius: 50%;
    background-color: #fff;
    margin: 0 5px;
    cursor: pointer;
}

.news-article-wrap .r-ad .swiper-pagination .swiper-active-switch {
    background-color: #000;
}

/* ---------- end 新闻文章内容 ---------- */

/* 你可能喜欢 */

.recommend-article {
    border-top: solid 1px #e3e3e3;
    padding-top: 30px;
    margin-top: 45px;
}

.recommend-article .i-header {
    font-size: 18px;
    font-weight: bold;
    color: #333;
    padding-bottom: 12px;
}

.recommend-article .i-bodyer .list {
    height: 26px;
    line-height: 26px;
    font-size: 14px;
    position: relative;
    padding: 0 15px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.recommend-article .i-bodyer .list a {
    color: #333;
}

.recommend-article .i-bodyer .list a:before {
    content: "";
    display: block;
    clear: both;
    width: 6px;
    height: 6px;
    position: absolute;
    top: 10px;
    left: 0px;
    background-color: #333;
    border-radius: 50%;
}

.recommend-article .i-bodyer .list a:hover {
    color: #f08b2f;
}

.recommend-article .i-bodyer .list a:hover::before {
    background-color: #f08b2f;
}

/* 周排行 月排行 */

.rank-article {
    border: solid 1px #e3e3e3;
    padding: 0 4px;
    margin-top: 20px;
    margin-bottom: 30px;
}

.rank-article .i-header {
    padding: 0 15px;
    line-height: 42px;
    font-size: 18px;
    color: #333;
    border-bottom: solid 1px #e3e3e3;
}

.rank-article .tab-header {
    text-align: center;
    font-size: 0;
    padding: 6px 0;
}

.rank-article .tab-header .tab {
    display: inline-block;
    padding: 8px 25px;
    color: #333;
    font-size: 14px;
    border-bottom: solid 2px #e3e3e3;
}

.rank-article .tab-header .tab.active {
    color: #f08b2f;
    border-bottom: solid 2px #f08b2f;
}

.rank-article .tab-header .tab:hover {
    color: #f08b2f;
}

.rank-article .tab-content {
    padding: 7px 0 10px;
    opacity: 0;
    display: none;
    transition: all 0.5s;
}

.rank-article .tab-content.active {
    display: block;
    opacity: 1;
}

.rank-article .tab-content .list {
    height: 26px;
    line-height: 26px;
    font-size: 14px;
    position: relative;
    padding: 0 15px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    border-top: 0;
    margin-top: 0px;
}

.rank-article .tab-content .list a {
    color: #333;
}

.rank-article .tab-content .list a:hover {
    color: #f08b2f;
}

.news-article-l {
    position: relative;
}

.news-article .myselectJS {
    position: absolute;
    right: 0px;
    top: 30px;
}

.myselectJS {
    display: inline-block;
    position: relative;
    cursor: default;
}

.myselectJS .myselect-header {
    width: 120px;
    height: 28px;
    line-height: 28px;
    color: #333;
    padding: 0 5px;
    border-radius: 3px;
    border: solid 1px #e3e3e3;
    font-size: 14px;
    background-image: url(../../../assets/images/icon-selectjs.png);
    background-repeat: no-repeat;
    background-position: right center;
}

.myselectJS .myselect-header.active {
    color: #4a4a4a;
}

.myselectJS .myselect-bodyer {
    position: absolute;
    top: 28px;
    left: 0px;
    width: 105px;
    z-index: 2;
    max-height: 200px;
    overflow-y: auto;
    border: solid 1px #ddd;
    border-top: 0;
    background-color: #fff;
    font-size: 14px;
    user-select: none;
}

.myselectJS .myselect-bodyer .menu {
    display: block;
    height: 24px;
    line-height: 24px;
    padding: 0px 5px;
    color: #4a4a4a;
    overflow: hidden;
}

.myselectJS .myselect-bodyer .menu.active {
    background-color: #f08b2f;
    color: #fff;
}

.myselectJS .myselect-bodyer .menu:hover {
    background-color: #f08b2f;
    color: #fff;
}

.myselectJS.open .myselect-bodyer {
    display: block;
}

html.openMyselectJS {
    height: 100%;
    overflow: hidden;
}

.topic-brand-search {
    margin-top: 20px;
    border: 1px solid #f0f0f0;
}
.topic-brand-search-head {
    line-height: 2.4em;
    font-size: 18px;
    font-weight: bold;
    text-align: center;
    border-bottom: 1px solid #f0f0f0;
}
.topic-brand-search-head i {
    font-weight: bold;
    color: #ff7200;
}
.topic-brand-search-form {
    padding: 15px;
}
.topic-brand-search-form li {
    margin-bottom: 12px;
}
.topic-brand-search-form li span {
    display: inline-block;
    margin-bottom: 3px;
}
.topic-brand-search-form li span i {
    color: #ff0000;
}
.topic-brand-search-form input {
    padding-left: 5px;
    width: 100%;
    height: 26px;
    border: 1px solid #f0f0f0;
    border-radius: 2px;
}
.topic-brand-search-form .yzmbox input {
    width: 118px;
    vertical-align: middle;
}
.yzmbox .btn-send {
    display: inline-block;
    margin-bottom: 0;
    margin-left: 8px;
    width: 90px;
    line-height: 27px;
    background-color: #ff7200;
    color: #fff;
    text-align: center;
    vertical-align: middle;
    cursor: pointer;
    border-radius: 2px;
}
.topic-brand-search-form .submit {
    display: block;
    margin: 0 auto;
    margin-top: 25px;
    width: 128px;
    height: 2.2em;
    color: #fff;
    background: #ff7200;
    border: 1px solid #ff7200;
    border-radius: 4px;
    cursor: pointer;
}
</style>
