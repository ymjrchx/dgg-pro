<template>
    <div class="news-article-wrap" v-if="showInfo">
        <div class="news-article">
            <!-- start 左 -->
            <div class="news-article-l">
                <h1 class="n-title">{{showInfo.title}}</h1>
                <dl class="n-info clearfix">
                    <dt class="author">
                        <span class="s1">时间：{{showInfo.pubdate}}</span>
                    </dt>
                </dl>
                <div class="article">
                    <div class="p" v-html="showInfo.content"></div>
                </div>
                <!-- end relevant-article-->
            </div>
            <!-- end 左 -->
        </div>
    </div>
</template>
<script>
export default {
    async asyncData({ params, redirect, error, env, store, route, $axios}) {
        if (!params.id) {
            error({ statusCode: 500, message: "参数错误" });
            return false;
        }
        let paramsTemp = params.id.split(".")[0];
        return Promise.all([
            $axios.$get(`/companyNews/newDetail?title=${encodeURI(params.id)}`)
        ]).then(res => {

                // 不存在数据
                if (!res[0].data) {
                    error({ statusCode: 500, message: "暂无数据" });
                } else {
                    return {
                        showInfo: res[0].data[0],
                    }
                }
            })
            .catch((err) => {
                console.log(err)
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
                    content: `${this.showInfo.title}`
                },
                {
                    name: "description",
                    hid: "description",
                    content: `${this.showInfo.part}`
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
    data() {
        return {
        };
    },
    components: {
    },
    methods: {
        splitFlag(str) {
            let newStr = (str || "").replace(/ /g, "");
            return newStr ? newStr.split(/，|-| /) : [];
        }
    }
};
</script>
<style scoped>
/* ---------- start 新闻文章内容 ---------- */

.news-article-wrap {
    width: 100%;
    /*background: #fff;*/
    padding-bottom: 100px;
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
    /*background: #fff*/
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
</style>
