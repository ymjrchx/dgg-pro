<template>
    <div>
        <mini/>
        <div class="w">
            <div class="breadcrumb">
                <span>当前位置：</span>
                <span class="breadcrumb-title col">初审公告详情</span>
            </div>
            <div class="page-detail ">
                <div class="btns fr">
                    <a :href="downPdf()" class="button button-white">下载</a>
                    <!-- <a :href="initUrl(json.intCls,json.regNo)" class="button" target="_blank">商标详情</a> -->
                </div>
                <h2 class="f20 mb10">商标名称：{{json.name}}</h2>

                <div class="content text-center" v-if="json.ggImgLocalData && json.ggImgLocalData.ggUrl">
                    <img :src="json.ggImgLocalData.ggUrl" alt="">
                </div>
                <div v-else class="noMsg">
                    暂无初审公告数据！
                </div>
            </div>
        </div>
        <toTop :showNav="'1'" />
    </div>
</template>
<script>
import mini from "~/components/header/mini";
import toTop from "~/components/toTop";
import axios from "axios";
import { Http } from "~/plugins/axios.js";
import qs from "qs";

export default {
    layout: "empt",
    head() {
        return {
            title: "初审通告_专注于商标查询、专利查询-知千秋网——",
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
    async asyncData({ params, redirect, error, env, route}) {
        let ids = params.details.split(/\.|_/)[1];

        let requireOnj = qs.stringify({
            id: ids
        });

        let { data } = await axios.post(
            `${env.baseUrl}/term/getTradeMarkInfo`,
            requireOnj
        );

        if (data.code == 0) {
            return {
                json: data.data,
                id: ids,
                baseUrl: env.webSite + route.fullPath
            };
        } else {
            error({ statusCode: 500, message: data.msg });
        }
    },
    components: {
        mini,
        toTop
    },
    mounted() {
        this.getUrl = require("../../../nuxt.config").env.baseUrl;
    },
    data() {
        return {
            id: "",
            json: {},
            getUrl: ""
        };
    },
    methods: {
        initUrl(intCls, regNo) {
            let str = regNo + "_" + intCls;
            return "/shangbiaoxq/" + str + ".html";
        },
        downPdf() {
            return this.getUrl + "/term/download?id=" + this.id;
        }
    }
};
</script>
<style scoped>
* {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}
.breadcrumb {
    margin-top: 20px;
    margin-bottom: 20px;
}
.button.button-white {
    color: #ff5c00;
    background-color: #fff;
    border: 1px solid #ff5c00;
}
.page-detail .btns .button {
    display: inline-block;
    margin-left: 10px;
    width: 80px;
    text-align: center;
    line-height: 30px;
}
.content > img {
    max-width: 100%;
    margin-top: 16px;
}
.noMsg {
    width: 100%;
    line-height: 400px;
    text-align: center;
    font-size: 14px;
}
</style>
