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
            title: `最新${this.json.tmName}初审公告:【${this.json.intCls}】${
                this.json.exceptionAppDate
            }日前可异议-【${this.json.tmName}商标异议-快速申请服务】-知千秋`,
            meta: [
                {
                    name: "keywords",
                    hid: "keywords",
                    content: `${this.json.tmName}初审公告,最新${
                        this.json.tmName
                    }初审公告,${this.json.tmName}商标异议,${this.json.tmName}${
                        this.json.intCls
                    }商标异议`
                },
                {
                    name: "description",
                    hid: "description",
                    content: `${this.json.tmName}初审公告:【${
                        this.json.intCls
                    }】【${this.json.tmName}商标异议-快速申请服务】需在${
                        this.json.exceptionAppDate
                    }日前，该公告申请人为${
                        this.json.applicantCn
                    }【点击了解详情】。`
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
    async asyncData({ params, redirect, error, env, route }) {
        let ids = params.code.split(/\./)[0];

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
        this.getUrl = require("../../nuxt.config").env.baseUrl;
        console.log(this.json);
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
