<template>
    <div class="content">
        <div class="news-lists-wrap">
            <div class="news-lists">
                <!-- start mytab -->
                <div class="mytab">
                    <ul class=" mytap-header">
                        <li class="list" :class="{'selected': !seletedType}">
                            <a href="/news">全部</a>
                        </li>
                        <li class="list" :class="{'selected': seletedType == index}" v-for="(item,index) in type" :key="index">
                            <a :href="'/news/'+ index">{{item}}</a>
                        </li>
                    </ul>
                    <div class="mytap-body">
                        <!-- content 全部 -->
                        <div class="content selected">
                            <p class="noData" v-if="!total">暂无相关数据</p>
                            <ul class="item" v-if="total">
                                <li class="list" v-for="item in list" :key="item.id">
                                    <div class="list-l">
                                        <span class="time-day">{{item.createTime.substr(8, 2)}}</span>
                                        <span class="time-month">{{item.createTime.substr(0, 7)}}</span>
                                    </div>
                                    <div class="list-m">
                                        <a :href="'/news/show/'+item.id+'.'+seletedType+'.'+pageNum+'.html'" target="_black"><img :src="item.newsPhoto" :onerror="errorImg" /></a>
                                    </div>
                                    <div class="list-r">
                                        <h3>
                                            <a :href="'/news/show/'+item.id+'.'+seletedType+'.'+pageNum+'.html'" target="_black">{{item.title}}</a>
                                        </h3>
                                        <p>
                                            <a class="a1" :href="'/news/show/'+item.id+'.'+seletedType+'.'+pageNum+'.html'" target="_black" v-html="item.content"></a>
                                            <a :href="'/news/show/'+item.id+'.'+seletedType+'.'+pageNum+'.html'" target="_black">［详细］</a>
                                        </p>
                                        <span class="t" v-if="0">{{item.typeLevel1Name}}</span>
                                    </div>
                                </li>
                            </ul>
                            <!-- <div class="myloading">
                            <em class="icon icon-loading"></em>
                            <span class="text">内容加载中</span>
                        </div> -->
                            <!-- start 分页 -->
                            <div class="paging">
                                <pagination :totalPage="total" :pageNum="pageNum" :pageSize="pageSize" @pagechange="pagechange" />
                            </div>
                            <!-- end 分页 -->
                        </div>
                        <!-- end content 全部 -->
                    </div>
                    <!-- end mytab -->
                </div>
            </div>
        </div>
        <to-top />
    </div>
</template>
<script>
import toTop from "@/components/toTop";
import axios from "axios";
import pagination from "~/components/pagination.vue";
import img from "~/assets/images/default_new.jpg";

export default {
    async asyncData({ params, redirect, error, env, store, route }) {
        let type = "";
        let pageSize = 10;
        let pageNum = 1;
        if (!params.type) {
            type = "";
        } else {
            let paramsTemp = params.type.split(".")[0];
            pageNum = paramsTemp.split(",")[1] || 1;
            type = paramsTemp.split(",")[0] || "";
            if (type == "all") {
                type = "";
            }
        }

        return Promise.all([
            axios.post(
                `${
                    env.baseUrl
                }/news/all?type=${type}&pageSize=${pageSize}&pageNum=${pageNum}`
            ),
            axios.get(`${env.baseUrl}/news/type`)
        ])
            .then(res => {
                let { data } = res[0];
                // 不存在数据
                return {
                    list: data.data.count ? data.data.data : [],
                    total: data.data.count ? data.data.count : 0,
                    pageNum: Number(pageNum),
                    pageSize,
                    type: res[1].data.data,
                    seletedType: type
                };
            })
            .catch(() => {
                error({ statusCode: 500, message: "参数错误" });
            });
    },
    head() {
        return {
            title: `${this.type[this.seletedType] ||
                "知千秋"}-商标资讯，商标注册流程及费用，商标注册权威资讯-知千秋官网`,
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
                        "知千秋致力于知千秋致力于运营大数据技术、人工智能等技术重新定义知识产权生态链。提供商标查询,商标注册,专利申请,专利查询,版权登记全流程服务。提供免费商标查询Saas工具,智能商标注册0服务费省钱到底,商标查询,商标注册监控,专利申请等服务获得客户的高度认可"
                }
            ]
        };
    },
    data() {
        return {
            errorImg: 'this.src="' + img + '"'
        };
    },
    components: {
        toTop,
        pagination
    },
    mounted() {
        console.log(this.list);
    },
    methods: {
        pagechange(page) {
            let type = "all";
            if (this.$route.params.type) {
                type = this.$route.params.type.split(".")[0].split(",")[0];
            }
            this.$router.push(`/news/${type},${page}.html`);
        },
        cancelOrder(id) {
            this.$Http(
                "/order/cancelUserOrder",
                { userId: this.$store.state.userInfo.userId, orderId: id },
                "get",
                true
            ).then(res => {
                popup.created({
                    content: "取消成功",
                    time: 2000
                });
                this.loadOrder();
            });
        }
    }
};
</script>
<style scoped>
.noData {
    text-align: center;
    color: #999;
    font-size: 14px;
    border-top: 1px solid #eee;
    padding-top: 30px;
}
.news-lists-wrap .news-lists {
    width: 100%;
}
.news-lists-wrap .news-lists {
    width: 1240px;
    margin: auto;
}
.news-lists-wrap .mytab .mytap-header {
    margin-top: 40px;
}
.news-lists-wrap .mytab .mytap-header .list {
    margin-right: 0;
}
.news-lists-wrap .mytab .mytap-header .list a {
    background-color: #f5f5f5;
}
.mytab .mytap-header .list a {
    -webkit-transition: 0.3s;
    -moz-transition: 0.3s;
    -ms-transition: 0.3s;
    -o-transition: 0.3s;
    transition: 0.3s;
}
.news-lists-wrap .mytab .mytap-body .content {
    padding-top: 25px;
}
.news-lists-wrap .mytab .mytap-body .content .list {
    *zoom: 1;
    padding: 20px 0;
    border-bottom: dotted 1px #ebebeb;
}
.news-lists-wrap .mytab .mytap-body .content .list:after {
    content: "";
    display: block;
    clear: both;
    height: 0;
    visibility: hidden;
}
.news-lists-wrap .mytab .mytap-body .content .list .list-l,
.news-lists-wrap .mytab .mytap-body .content .list .list-m,
.news-lists-wrap .mytab .mytap-body .content .list .list-r {
    display: block;
    float: left;
}
.news-lists-wrap .mytab .mytap-body .content .list .list-l {
    width: 92px;
    height: 135px;
    text-align: center;
    color: #dbdbdb;
    cursor: default;
}
.news-lists-wrap .mytab .mytap-body .content .list .list-l .time-day {
    display: block;
    font-size: 60px;
    font-weight: bold;
    line-height: 55px;
    margin: 25px 0 0;
}
.news-lists-wrap .mytab .mytap-body .content .list .list-l .time-month {
    display: block;
    font-size: 18px;
    line-height: 28px;
}
.news-lists-wrap .mytab .mytap-body .content .list .list-m {
    width: 235px;
    height: 135px;
    margin: 0 35px 0 80px;
    background-color: #f08c2f;
    overflow: hidden;
}
.news-lists-wrap .mytab .mytap-body .content .list .list-m img {
    width: 100%;
    height: 100%;
    -webkit-transition: 0.4s;
    -moz-transition: 0.4s;
    -ms-transition: 0.4s;
    -o-transition: 0.4s;
    transition: 0.4s;
}
.news-lists-wrap .mytab .mytap-body .content .list .list-r {
    width: 760px;
    height: 135px;
}
.news-lists-wrap .mytab .mytap-body .content .list .list-r h3 {
}
.news-lists-wrap .mytab .mytap-body .content .list .list-r h3 a {
    font-size: 20px;
    font-weight: bold;
    color: #555;
}
.news-lists-wrap .mytab .mytap-body .content .list .list-r p {
    padding: 12px 0 0;
    height: 80px;
    line-height: 27px;
    overflow: hidden;
    color: #999;
    font-size: 14px;
}
.news-lists-wrap .mytab .mytap-body .content .list .list-r p .a1 {
    color: #999;
}
.news-lists-wrap .mytab .mytap-body .content .list .list-r p a {
    color: #f08c2f;
}
.news-lists-wrap .mytab .mytap-body .content .list .list-r .t {
    color: #f08c2f;
    font-size: 12px;
}
.news-lists-wrap .mytab .mytap-body .content .list:hover .list-l {
    color: #f08c2f;
}
.news-lists-wrap .mytab .mytap-body .content .list .list-m:hover img {
    -webkit-transform: scale(1.1);
    -moz-transform: scale(1.1);
    -ms-transform: scale(1.1);
    -o-transform: scale(1.1);
    transform: scale(1.1);
}
.news-lists-wrap .mytab .mytap-body .content .list:hover .list-l {
    color: #f08c2f;
}
.news-lists-wrap .mytab .mytap-body .content .list:hover .list-r h3 a {
    color: #f08c2f;
}

.mytab .mytap-header {
    overflow: hidden;
    /* border-left: solid 1px #eee; */
}
.mytab .mytap-header .list {
    float: left;
    margin-left: -1px;
}
.mytab .mytap-header .list a {
    display: block;
    padding: 0 15px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    color: #555;
    border: solid 1px #eee;
}
.mytab .mytap-header .list a:hover {
    color: #fff;
    background-color: #f08c2f;
    border: solid 1px #f08c2f;
}
.mytab .mytap-header .list.selected a {
    /* color: #fff; */
    /* background-color: #f08c2f; */
    /* border: solid 1px #f08c2f; */
}
.mytab .mytap-body .content {
    display: none;
}
.mytab .mytap-body .content.selected {
    display: block;
}
</style>
