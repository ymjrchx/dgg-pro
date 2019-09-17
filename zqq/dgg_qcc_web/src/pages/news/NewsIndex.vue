<template>
    <section id="news_content" class="m-t-lg" style="min-height:700px">
        <div class="container">
            <div class="row">
                <div class="col-sm-9">
                    <ul class="news_head">
                        <li v-for='(item,idx) in headArr' :key="idx" :class="{'active': activeHead == idx }" @click="goActive(idx)">{{item}}</li>
                    </ul>

                    <Recommend :pageIndex='pageIndex' :ajaxStr="ajaxStr" @updateIndex='updateIndex'/>
                </div>
                <div class="col-sm-3">
                    <section class="panel b-a">
                        <div class="panel-heading b-b">
                            <span class="pull-right">
                                <span class="text-muted">{{nowTime}}</span>
                            </span>
                            <span class="font-bold font-15 text-dark">最近浏览</span>
                        </div>
                        <div class="panel-body">
                            <div id="panel-news" style="max-height: 618px; overflow: hidden;">
                                <div style="position: relative; top: 0px;">
                                    <template v-if="historyList">
                                        <div class="fast-item" v-for="(item,idx) in historyList" :key="idx">
                                            <div class="item-inner">
                                                <div class="item-title" @click="goDetail(item.value)">{{item.value}}</div>
                                            </div>
                                        </div>
                                    </template>
                                    <div v-else>
                                        <div class="item-inner">
                                            <div class="item-title">暂无浏览记录</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
      
    </section>
</template>
<script>
import Head from "@/components/Head";
import Recommend from "@/pages/news/Recommend"; //推荐
// import Hot from "@/pages/news/Hot"; //热点
// import Technology from "@/pages/news/Technology"; //科技
// import Entertainment from "@/pages/news/Entertainment"; //娱乐
// import Game from "@/pages/news/Game"; //游戏
// import Sports from "@/pages/news/Sports"; //体育
// import Car from "@/pages/news/Car"; //汽车
// import Finance from "@/pages/news/Finance"; //财经
// import Funny from "@/pages/news/Funny"; //搞笑

import Common from "@/util/common";


export default {
    components: {
        Head,
        Recommend
        // Hot,
        // Technology,
        // Entertainment,
        // Game,
        // Sports,
        // Car,
        // Finance,
        // Funny
    },
    data() {
        return {
            // headArr: [
            //     "推荐",
            //     "热点",
            //     "企业",
            //     "娱乐",
            //     "游戏",
            //     "体育",
            //     "汽车",
            //     "财经",
            //     "搞笑"
            // ],
            headArr: [
                "科技",
                "财经",
                "专利",
                "商标",
                "版权",
                "法律",
                "榜单",
                "公司",
                "产品",
            ],
            activeHead: 0,
            nowTime: "",
            historyList:"",
            pageIndex:1,
            ajaxStr:"news_tech"
        };
    },
    created() {
        let now = new Date();
        this.nowTime =
            now.getFullYear() +
            "-" +
            (now.getMonth() + 1) +
            "-" +
            now.getDate();
        if(this.$route.query.type){
            switch(this.$route.query.type){
                case "finance":this.activeHead = 1;this.ajaxStr='news_finance';break;
                case "news_tech":this.activeHead = 0;break;
            }
        }
        let arr = Common.getHistory("news_history");
            this.historyList=arr
    },
    methods: {
        goActive(num) {
            if(this.activeHead == num) return;
            this.activeHead = num;
            this.pageIndex=1;
            switch(num){
                // case 0: this.ajaxStr='recommend';break;
                // case 1: this.ajaxStr='news_hot';break;
                // case 2: this.ajaxStr='news_tech';break;
                // case 3: this.ajaxStr='news_entertainment';break;
                // case 4: this.ajaxStr='news_game';break;
                // case 5: this.ajaxStr='news_sports';break;
                // case 6: this.ajaxStr='news_car';break;
                // case 7: this.ajaxStr='news_finance';break;
                // case 8: this.ajaxStr='funny';break;
                case 0: this.ajaxStr='news_tech';break;
                case 1: this.ajaxStr='news_finance';break;
                case 2: this.ajaxStr='patent';break;
                case 3: this.ajaxStr='trademark';break;
                case 4: this.ajaxStr='copyright';break;
                case 5: this.ajaxStr='droit';break;
                case 6: this.ajaxStr='list';break;
                case 7: this.ajaxStr='company';break;
                case 8: this.ajaxStr='product';break;
            }
          
           
        },
        getHistory() {
            this.historyList = Common.getHistory("news_history");
        },
        updateIndex(num){
            this.pageIndex=num;
            
        },
        goDetail(id){
            Common.newPage("NewsDetail", { title: id });
            
        }
    }
};
</script>
<style>
.newsPageBtn {
    text-align: center;
    margin-top: 40px;
    padding: 30px;
}
.newsList .newsName:hover {
    color: #409eff;
}
</style>
<style scoped>
/* @import "../../assets/test.css"; */
.news_head {
    width: 100%;
    height: 48px;
    background: #fff;
    padding: 4px 10px;
}
.news_head li {
    float: left;
    width: 9.11%;
    line-height: 40px;
    text-align: center;
    font-size: 15px;
    font-weight: 700;
    cursor: pointer;
    border-radius: 2px;
    color: #565656;
    margin: 0 1%;
    transition: all 0.3s;
}
.news_head li.active {
    color: #409eff;
    background-color: #ecf5ff;
    font-size: 16px;
}
.fast-item .item-title:hover {
    color: #409eff;
}
</style>