<template>
    <div class="Detection_Wrap">
        <div class="Detection_Box">
            <h2 class="title">
                知千秋--大数据+知识产权智能化服务平台
                <div class="bdsharebuttonbox bdshare-button-style0-16" data-bd-bind="1539081748590">
                    <!-- <a href="javascript:return false;" class="bds_more fenxiang" data-cmd="more"></a> -->
                </div>
            </h2>
            <div class="index_Top">
                <div class="index_l_icon">
                    <img src="../../assets/images/sbzc/index_l_icon.png" alt="商标注册成功率logo">
                </div>
                <div class="index_r">
                    <h1>商标注册成功率测算</h1>
                    <div class="search_Wrap">
                        <!-- <form> -->
                        <img src="../../assets/images/sbzc/search_icon.png">
                        <input type="text" placeholder="请输入中文商标名称" v-model="keyword">
                        <span class="xzhy" :class="{act:isShow}" @click=" isShow= !isShow">{{style}}</span>
                        <button class="btn cur" @click="gogo">测一下</button>
                        <transition name="fadeIn">
                            <ul class="wrap" v-show="isShow" @click.stop="isShow = 1">
                                <li v-for='(item,idx) in classifyArr' :key='idx' :title="item.name" @click.stop="celectClassify(item.id,item.name)">{{item.name}}</li>
                            </ul>
                        </transition>
                        <span class="ts"></span>
                        <!-- </form>	 -->
                    </div>
                    <div class="data_Wrap">
                        <p class="tit">最新查询动态：</p>
                        <div id="design" style="position:relative">
                            <div class="clear2">
                                <ul id="design_1" class="scrollOver">
                                    <li>
                                        用户
                                        <span>130****8887</span>的商标注册成功率为
                                        <span class="n">20%</span>，赶快测算一下你的商标吧！
                                    </li>

                                    <li>
                                        用户
                                        <span>158****9326</span>的商标注册成功率为
                                        <span class="n">80%</span>，赶快测算一下你的商标吧！
                                    </li>

                                    <li>
                                        用户
                                        <span>158****9326</span>的商标注册成功率为
                                        <span class="n">20%</span>，赶快测算一下你的商标吧！
                                    </li>

                                    <li>
                                        用户
                                        <span>158****9326</span>的商标注册成功率为
                                        <span class="n">26%</span>，赶快测算一下你的商标吧！
                                    </li>

                                    <li>
                                        用户
                                        <span>173****3078</span>的商标注册成功率为
                                        <span class="n">20%</span>，赶快测算一下你的商标吧！
                                    </li>
                                    <li>
                                        用户
                                        <span>130****8887</span>的商标注册成功率为
                                        <span class="n">20%</span>，赶快测算一下你的商标吧！
                                    </li>

                                    <li>
                                        用户
                                        <span>158****9326</span>的商标注册成功率为
                                        <span class="n">80%</span>，赶快测算一下你的商标吧！
                                    </li>

                                    <li>
                                        用户
                                        <span>158****9326</span>的商标注册成功率为
                                        <span class="n">20%</span>，赶快测算一下你的商标吧！
                                    </li>

                                    <li>
                                        用户
                                        <span>158****9326</span>的商标注册成功率为
                                        <span class="n">26%</span>，赶快测算一下你的商标吧！
                                    </li>

                                    <li>
                                        用户
                                        <span>173****3078</span>的商标注册成功率为
                                        <span class="n">20%</span>，赶快测算一下你的商标吧！
                                    </li>
                                    <li>
                                        用户
                                        <span>130****8887</span>的商标注册成功率为
                                        <span class="n">20%</span>，赶快测算一下你的商标吧！
                                    </li>

                                    <li>
                                        用户
                                        <span>158****9326</span>的商标注册成功率为
                                        <span class="n">80%</span>，赶快测算一下你的商标吧！
                                    </li>

                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="index_Foot">
                <ul>
                    <li class="li01 ">
                        <a target="_blank" href="/shangbiao/freesearch" class="line-animate"></a>
                        商标搜索
                    </li>
                    <li class="li02">
                        <a target="_blank" href="/classify" class="line-animate"></a>
                        分类查询
                    </li>
                    <li class="li03">
                        <a target="_blank" href="/show/navigation_trademark_register_04&S7741102214293331969.html" class="line-animate"></a>
                        智能注册
                    </li>
                    <li class="li04">
                        <a target="_blank" href="/notice" class="line-animate"></a>
                        初审通告
                    </li>
                </ul>
                <div>
                    <img src="../../assets/images/main/code_wx.png" alt="微信" width="88px">
                    <p>关注知千秋</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { Http } from "~/plugins/axios.js";
export default {
    head() {
        return {
            title: "商标注册成功率测算-专注于商标查询、专利查询-知千秋官",
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
            ],
            link:[
                {
                    rel: "canonical",
                    content: this.baseUrl
                }
            ]
        };
    },
    async asyncData({ params, redirect, error, env, route }) {
        return {
            baseUrl: env.webSite + route.fullPath
        }
    },
    data() {
        return {
            isShow: 0,
            keyword: "",
            style: "选择行业",
            id: "",
            classifyArr: [],
            loading: ""
        };
    },
    watch: {
        isShow() {
            if (this.isShow) {
                document.addEventListener("click", this.hidePartBox, false);
            } else {
                document.removeEventListener("click", this.hidePartBox, false);
            }
        }
    },
    mounted() {
        sessionStorage.setItem("isFrom", 1);
        Http("predicate/allIndustries", {}, "post").then(res => {
            sessionStorage.removeItem("isFrom");
            this.classifyArr = res.data.data;
            // console.log("数据", res.data.data);
        });
        if (sessionStorage.getItem("industryId"))
            this.id = sessionStorage.getItem("industryId");
        if (sessionStorage.getItem("industryStr"))
            this.style = sessionStorage.getItem("industryStr");
    },
    methods: {
        hidePartBox() {
            this.isShow = 0;
        },
        gogo() {
            if (!this.keyword) {
                this.$Popup.created({
                    content: "请先填写商标名",
                    time: 1500
                });
                return;
            }
            if (this.keyword.length < 1) {
                this.$Popup.created({
                    content: "请至少填写两个字段",
                    time: 1500
                });
                return;
            }
            var reg = /^[\u4e00-\u9fa5]+$/;
            if (!reg.test(this.keyword)) {
                this.$Popup.created({
                    content: "注意：商标名称只能使用简体中文",
                    time: 1500
                });
                return;
            }
            if (this.style == "选择行业") {
                this.$Popup.created({
                    content: "请选择行业类型",
                    time: 1500
                });
                return;
            }
            this.loading = this.$Popup.created({
                content: "计算中...",
                type: "loading",
                icon: "&#xe61c"
            });
            this.$router.push(
                "/sbzc/result_" +
                    this.keyword +
                    "," +
                    this.style +
                    "," +
                    this.id +
                    ",html"
            );
        },
        celectClassify(id, str) {
            this.style = str;
            this.id = id;
            this.isShow = 0;
            sessionStorage.setItem("industryId", id);
            sessionStorage.setItem("industryStr", str);
        }
    },
    destroyed() {
        document.removeEventListener("click", this.hidePartBox, false);
        if (this.loading) this.loading.close();
    }
};
</script>

<style scoped>
@import url("../../assets/css/sbzc");
.scrollOver {
    position: absolute;
    top: 0;
    animation: gun 15s linear infinite;
}
@keyframes gun {
    0% {
        top: 0;
    }
    100% {
        top: -260px;
    }
}
</style>

