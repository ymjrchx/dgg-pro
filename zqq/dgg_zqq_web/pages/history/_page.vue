<template>
    <div class="history-article-wrap">
        <div class="history-article">
            <!-- start 左 -->
            <div class="history-article-l">
                <div class="n-1">
                    <span class="text">
                        当前位置：
                        <a href="/" rel="nofollow">首页</a>
                        <i class="songti">&gt;</i>
                        <span>商标查询历史记录</span>
                    </span>    
                </div>
                <div class="article">
                    <div class="p" v-if='keyArr'>
                        <ul><li v-for="(item,idx) in keyArr" :key="idx" class="text-delic"><a :href="'/shangbiao/'+item.keyWord+'.html'" target="_blank" :title='item.keyWord'>{{item.keyWord}}</a></li></ul>
                    </div>
                </div>
                <div class="switchBtn clear2">
                    <div class="paging" v-if="total">
                        <ul class="pagination">
                            <li class="prev">
                                <a :href="seoUrl+'1.html'">首页</a>
                            </li>
                            <li class="prev">
                                <a :href="returnUrl(nowPage,-1)" v-if='nowPage>1'>上一页</a>
                                <span v-else>上一页</span>
                            </li>

                            <li v-show="efont">
                                <span style="font-size:12px;">••••</span>
                            </li>

                            <li v-for="(num,idx) in indexs" class="pagesNum" :class="{'active':nowPage==num}" :key='idx'>
                                <a :href="returnUrl(num)">{{num}}</a>
                            </li>

                            <li v-show="efont && nowPage < allPage-4">
                                <span style="font-size:12px;">••••</span>
                            </li>
                            <li class="next">
                                <a :href="returnUrl(nowPage,1)" v-if="nowPage < allPage">下一页</a>
                                <span v-else>下一页</span>
                            </li>
                            <li class="prev">
                                <a :href="returnUrl(allPage)">尾页</a>
                            </li>
                            <li style="height:26px;color:#777;margin-left:52px;">到第</li>
                            <input type="text" @keyup.enter="jumpPage2" v-model="changePage" class="j-input">
                            <input class="j-btn" type="button" value="确定" @click="jumpPage2">
                        </ul>
                    </div>
                </div>
            </div>
            <!-- end 左 -->
            <!-- start 右 -->
            <div class="history-article-r">
                <div class="item-yw">
                    <h6 class="i-header t2">
                        <span class="text">最新商标查询</span>
                        <a class="icon icon-refresh" href="javascript:;" title="换一组" style="display: none;"></a>
                    </h6>
                    <div class="i-body" v-if="product">
                        <ul class="hot-posts-body">
                            <li v-for="(item,idx) in key20" :key="idx" class="text-delic"><a :href="'/shangbiao/'+item.keyWord+'.html'" 
                                target="_blank" :title='item.keyWord'>{{item.keyWord}}</a></li>
                        </ul>
                    </div>
                </div>
                <div class="item-yw">
                    <h6 class="i-header t2">
                        <span class="text ">热门商标查询</span>
                        <a class="icon icon-refresh" href="javascript:;" title="换一组" style="display: none;"></a>
                    </h6>
                    <div class="i-body" v-if="hotArr">
                       <ul class="hot-posts-body">
                            <li class="list text-delic" v-for="(val,key,idx) in hotArr" :key="idx">
                                <a :href="'/shangbiao/'+key+'.html'" target="_blank" :title='key'>{{key}}</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <!-- //业务推荐 -->
                <div class="item-yw">
                    <h6 class="i-header">
                        <span class="text">您可能需要的业务</span>
                        <a class="icon icon-refresh" href="javascript:;" title="换一组" style="display: none;"></a>
                    </h6>
                    <div class="i-body" v-if="product">
                        <dl v-for="item in [0,1,2]" :key="product[item].serviceItem.id">
                            <dt>
                                <a :href="getUrl(product[item].serviceItem.typeLevel3Code, product[item].serviceItem.number)"><img src="../../assets/images/show/img_product.jpg" :alt="product[item].serviceItem.name" :title="product[item].serviceItem.name"></a>
                            </dt>
                            <dd>
                                <a :href="getUrl(product[item].serviceItem.typeLevel3Code, product[item].serviceItem.number)">
                                    <span class="s1">{{product[item].serviceItem.name}}</span>
                                    <span class="s2" v-if="product[item].serviceItem.servicePrice">服务费：￥{{product[item].serviceItem.servicePrice}}</span>
                                    <span class="s3" v-if="product[item].serviceItem.officialPrice">官费：￥{{product[item].serviceItem.officialPrice}}</span>
                                </a>
                            </dd>
                        </dl>
                    </div>
                </div>
            </div>
        </div>
        <toTop/>
    </div>
</template>
<script>
import toTop from "@/components/toTop";
import popup from "@/components/popup";
import {pushBaidu } from "~/assets/js/common.js";
import axios from "axios";
export default {
    async asyncData({ params, redirect, error, env, store, route }) {
         let page = params.page.split('.')[0]-1
        return Promise.all([
            axios.post(`${env.baseUrl}seo/nearWords`,{page:page,size:666}),
            axios.post(`${env.baseUrl}seo/rankOfKeyword`, {pullCount: 20,searchType: "brand"}),
            axios.get(`${env.baseUrl}serviceItem/findServiceItemByNumber`, {
                params: {
                    numbers:
                        "S7741101919677030400,S7741102108546539521,S7741102510755127297"
                }
            })
        ]).then(res => {
                let { data } = res[0];
               
                // 不存在数据
                if (!data) {
                    error({ statusCode: 500, message: "暂无数据" });
                } else {
                    let total= Number(data.data.total)
                   return {
                        res:data,
                        total,
                        keyArr:data.data.list,
                        hotArr:res[1].data.data,
                        product: res[2].data ? res[2].data.data : "",
                        nowPage:Number(page)+1,
                        allPage: Math.ceil(total / 666)
                    }
                }
            })
            .catch(() => {
                error({ statusCode: 500, message: "参数错误" });
            });
    },
    head() {
        return {
            title: `商标注册查询官网_商标查询网站_商标查询结果_知千秋`,
            meta: [
                {
                    name: "keywords",
                    hid: "keywords",
                    content: `知千秋致力于运营大数据技术、人工智能等技术重新定义知识产权生态链`
                },
                {
                    name: "description",
                    hid: "description",
                    content: `提供商标查询,商标注册,专利申请,专利查询,版权登记全流程服务。为您提供全面的商标查询信息网站`
                }
            ],
            link: [
                {
                    rel: "canonical",
                    content:'123'
                }
            ]
        };
    },
    data() {
        return {
            newsType: false,
            tabIndex: 0,
            keyArr:"",
            changePage: "", //跳转页
            nowIndex: 0,
            seoUrl:'/history/'
        };
    },
    computed:{
        key20(){
            if(this.keyArr.length > 16){
                    return this.keyArr.slice(0,16)
            }
            return this.keyArr
        },
        efont() {
            if (this.allPage <= 7) return false;
            return this.nowPage > 5;
        },
        indexs() {
            var left = 1,
                right = this.allPage,
                arr = [];
            if (this.allPage >= 7) {
                if (
                    this.nowPage > 5 &&
                    this.nowPage < this.allPage - 4
                ) {
                    left = Number(this.nowPage) - 3;
                    right = Number(this.nowPage) + 3;
                } else {
                    if (this.nowPage <= 5) {
                        left = 1;
                        right = 7;
                    } else {
                        right = this.allPage;

                        left = this.allPage - 6;
                    }
                }
            }
            while (left <= right) {
                arr.push(left);
                left++;
            }
            return arr;
        }
    },
    components: {
        toTop
    },
    mounted(){
        pushBaidu()
       
    },
    methods: {
        getUrl(code, id) {
            if (id) {
                return `/show/${code}&${id}.html`;
            }
            return `/show/${code}.html`;
        },
        jumpPage2() {
            let pages = Number(this.changePage);
            if (isNaN(pages) || pages < 1) this.changePage = "";
            else {
                if (pages <= this.allPage) {
                    this.$router.push(`${this.seoUrl}${pages}.html`)
                } else {
                    this.$router.push(`${this.seoUrl}${this.allPage}.html`)
                }
            }
        },
        returnUrl(num,now){
            let page = num
            now && (page+=now)
            return this.seoUrl+page+'.html'
        }
    }
};
</script>
<style scoped>
/* ---------- start 新闻文章内容 ---------- */

.history-article-wrap {
    width: 100%;
}

.article img {
    max-width: 100%;
}
.history-article-wrap .history-article {
    width: 1200px;
    margin: auto;
    padding-top: 38px;
    overflow: hidden;
}

/* ----- start 左 ----- */

.history-article-wrap .history-article-l {
    width: 900px;
    float: left;
}

.history-article-wrap .history-article-l .article {
    padding: 30px 0 0;
}

.article  ul{
    border: solid 1px #eee;
    overflow: hidden;
    padding:15px 20px;
}
.article  ul li{
    /* float: left; */
    display: inline-block;
    margin-right: 54px;
    margin-bottom: 10px;
    transition: all .2s;
    width: 250px;
}
.article  ul li:nth-child(3n){
    margin-right: 0px;
}
.article  ul li a{
     color: #777;
}
.article  ul li a:hover{
    color: #fd7d22;
}

/* ----- start 右 热门文章----- */

.history-article-wrap .history-article-r {
    float: right;
    width: 250px;
    margin-top: 32px;
}

.hot-posts {
    border: solid 1px #eee;
}

/* //需要的业务 */
.item-yw{
    width: 100%;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    border: 1px solid #f0f0f0;
    margin-top: 15px;
}
.i-header {
    padding: 0 10px;
    height: 34px;
    border-bottom: 1px solid #f0f0f0;
    color: #818181;
}
.i-header.t2{
    height: 40px;
    color: #565656;
    font-size: 16px;
}


.i-header .text {
    display: block;
    float: left;
    margin: 10px 0 0;
}
.i-body {
    padding: 0 10px;
}
.i-body dl {
    overflow: hidden;
    padding: 12px 0;
    margin-bottom: -1px;
    border-bottom: 1px solid #ececec;
    white-space: nowrap;
}
.i-body dl dt {
    float: left;
    width: 110px;
    height: 80px;
    overflow: hidden;
}
.i-body dl dt img {
    width: 100%;
    min-height: 100%;
}
.i-body dl dd {
    float: right;
    width: 108px;
    height: 80px;
    padding: 6px 0 0;
    font-size: 12px;
    overflow: hidden;
}
.i-body dl dd span {
    display: block;
    margin: 5px 0;
    color: #818181
}
.hot-posts-body{
    overflow: hidden;
    padding-top: 10px
}
.hot-posts-body li{
    float: left;
    margin: 0 10px 8px;
    width: calc(50% -20px);
}
.hot-posts-body li a{
    color: #565656
}
.hot-posts-body li a:hover{
     color: #fd7d22;
}
/* //换一批 */
.switchBtn{
    text-align: center;
    padding: 20px 0 40px;
}



</style>
