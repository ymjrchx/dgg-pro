<template>
    <div class="Detection_Wrap">
        <div class="Detection_Box" style="display: block;">
            <!-- 上-检测结果 star-->
            <div class="Detection_Top Detection_Top_low" :class="{'mid':score < 66, 'low':score <33}">
                <h2 class="title">
                    商标注册成功率测算
                    <div class="bdsharebuttonbox bdshare-button-style0-16" data-bd-bind="1539087218226">
                        <!-- <a class="bds_more fenxiang" data-cmd="more"></a> -->
                    </div>
                </h2>
                <div class="t_Wrap">
                    <div class="l">
                        <p class="num">{{score}}</p>
                        <p class="tet">
                            <span>%</span>
                            <span>成功率</span>
                        </p>
                    </div>
                    <div class="r">
                        <p>
                            “
                            <span style="font-size:24px;" class="name">{{name}}</span>”在“{{type}}”行业注册成功率为
                            <span class="register_rate_span">{{score}}%</span>
                        </p>
                        <p class="cx">
                            <span>{{msg}}</span>
                            <a href="/sbzc" class="requery">重新查询</a>
                        </p>
                    </div>
                    <!-- <div class="sugges">
                        <div class="line"></div>
                        <p>推荐使用:</p>
                        <button id="qm_btn">在线商标起名</button>
                    </div> -->
                </div>
            </div>
            <!-- 上-检测结果 end-->

            <!-- 下-检测结果 star-->
            <div class="Detection_Con">
                <div class="results_T_Wrap">
                    <div class="results_t">
                        <img src="../../assets/images/sbzc/points_icon.png">
                        <p>共检测到
                            <span class="orange sum_low">{{sumNum(data2Json)}}</span>个相似商标</p>
                        <p class="line"></p>
                        <img src="../../assets/images/sbzc/show_icon.png" class="show_toggle">
                    </div>
                    <div class="results_c">
                        <div class="box">
                            <div class="box_tit">
                                <p class="results_tit">非常近似商标
                                    <span class="orange very_sum_low">{{data2Json.L1.length}}</span>个</p>
                            </div>
                            <ul class="content very_ul_low" v-if="data2Json.L1.length > 0">
                                <li v-for="(item,idx) in data2Json.L1" :key='idx'>
                                    <p>商标名称:
                                        <span class="orange">{{item.name}}</span>
                                    </p>
                                    <p>类别：
                                        <span>{{item.intCls}}</span>
                                    </p>
                                    <p>注册号：
                                        <span>{{item.regNo}}</span>
                                    </p>
                                    <p :title="item.applicantCn">申请人：
                                        <span>{{item.applicantCn}}</span>
                                    </p>
                                    <a :href="initUrl(item.intCls,item.regNo)" target="_blank" class="fr col f12">详情</a>
                                </li>
                            </ul>
                        </div>
                        <div class="box">
                            <div class="box_tit">
                                <p class="results_tit">近似商标
                                    <span class="orange normal_sum_low">{{data2Json.L2.length}}</span>个</p>
                            </div>
                            <ul class="content normal_ul_low" v-if="data2Json.L2.length > 0">
                                <li v-for="(item,idx) in data2Json.L2" :key='idx'>
                                    <p>商标名称:
                                        <span class="orange">{{item.name}}</span>
                                    </p>
                                    <p>类别：
                                        <span>{{item.intCls}}</span>
                                    </p>
                                    <p>注册号：
                                        <span>{{item.regNo}}</span>
                                    </p>
                                    <p :title="item.applicantCn">申请人：
                                        <span>{{item.applicantCn}}</span>
                                    </p>
                                    <a :href="initUrl(item.intCls,item.regNo)" target="_blank" class="fr col f12">详情</a>
                                </li>
                            </ul>
                        </div>

                        <div class="box">
                            <div class="box_tit">
                                <p class="results_tit">筛除商标
                                    <span class="orange very_sum_low">{{data2Json.L3.length}}</span>个</p>
                            </div>
                            <ul class="content last_ul_low" v-if="data2Json.L3.length > 0">
                                <li v-for="(item,idx) in data2Json.L3" :key='idx'>
                                    <p>商标名称:
                                        <span class="orange">{{item.name}}</span>
                                    </p>
                                    <p>类别：
                                        <span>{{item.intCls}}</span>
                                    </p>
                                    <p>注册号：
                                        <span>{{item.regNo}}</span>
                                    </p>
                                    <p :title="item.applicantCn">申请人：
                                        <span>{{item.applicantCn}}</span>
                                    </p>
                                    <a :href="initUrl(item.intCls,item.regNo)" target="_blank" class="fr col f12">详情</a>
                                </li>
                            </ul>
                        </div>

                    </div>
                </div>
                <!-- <div class="results_F_Wrap">
                    <div class="results_F_left">
                        <p class="t1">人工查询：</p>
                        <p class="t2">交给专业顾问人工分析，
                            <span class="orange">1</span>分钟内获取更精准的评估结果。
                            <a id="submit_btn01">立即提交</a>
                        </p>
                        <p class="t3">
                            <span class="orange">*</span>此结果仅供参考，不作为商标注册真实依据。</p>
                    </div>
                    <div class="results_F_right">
                        <p>为您精心推荐：</p>
                        <ul class="results_low">
                            <li>
                                <button>习嬉希</button>
                            </li>
                            <li>
                                <button>硒桥溪</button>
                            </li>
                            <li>
                                <button>锡来硒</button>
                            </li>
                            <li>
                                <a>更多&gt;&gt;</a>
                            </li>
                            <li>
                                <button>商务</button>
                            </li>
                            <li>
                                <button>快务商</button>
                            </li>
                            <li>
                                <button>迅驰商务</button>
                            </li>
                        </ul>
                    </div>
                </div> -->
            </div>
            <!-- 下-检测结果 end-->
        </div>
    </div>
</template>

<script>
import { Http } from "~/plugins/axios.js";
import axios from "axios";
import qs from "qs";

export default {
    head() {
        return {
            title: `${
                this.name
            }-商标注册成功率测算-专注于商标查询、专利查询-知千秋官`,
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
            name: "",
            type: "",
            score: 99,
            msg: "",
            cause: "",
            dataScore: "",
            dataCause: "",
            data2Json: ""
        };
    },
    async asyncData({ params, redirect, error, env, route }) {
        let keyword = params.result.split(/_/)[1].split(",");
        let requireObj = qs.stringify({
            brand: keyword[0],
            industry: keyword[2]
        });

        let [data1, data2] = await Promise.all([
            axios.post(
                `${env.baseUrl}predicate/registerPassingRate`,
                requireObj
            ),
            axios.post(`${env.baseUrl}predicate/queryMessages`, requireObj)
        ]);

        if (data1.data.code == 0 && data2.data.code == 0) {
            return {
                allkey: keyword,
                name: keyword[0],
                type: keyword[1],
                dataScore: Math.round(data1.data.data.score * 100),
                dataCause: data1.data.data.cause,
                data2Json: data2.data.data,
                baseUrl: env.webSite + route.fullPath
            };
        } else {
            error({ statusCode: 500, message: data1.msg });
        }
    },
    mounted() {
        // console.log(this.data2Json);
        this.anmime();
    },
    methods: {
        anmime() {
            let timer = setInterval(() => {
                let score = this.dataScore;
                if (this.score == 0) {
                    this.msg = this.dataCause;
                    clearInterval(timer);
                }
                if (score == this.score) {
                    if (score < 90) this.msg = this.dataCause;
                    clearInterval(timer);
                } else {
                    this.score -= 1;
                }
            }, 50);
        },
        sumNum(json) {
            let num = 0;
            if (json.L1) num += json.L1.length;
            if (json.L2) num += json.L2.length;
            if (json.L3) num += json.L3.length;

            return num;
        },
        initUrl(intCls, regNo) {
            let str1 = (intCls + "").replace(/\D/g, "");
            let str = regNo + "_" + str1 || 0;
            return "/shangbiaoxq/" + str + ".html";
        }
    }
};
</script>

<style scoped>
@import url("../../assets/css/sbzc");
.Detection_Top.mid {
    background-color: #e6a23c;
}
.Detection_Top.low {
    background-color: #f7462f;
}
</style>

