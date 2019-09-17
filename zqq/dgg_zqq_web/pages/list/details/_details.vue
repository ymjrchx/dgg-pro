<template>
    <div>
        <miniHead/>
        <div class="page-brand" v-if='json'>
            <div class="page-brand-in w clear3">
                <div class="brand-left">
                    <div class="brand-img">
                        <img :src="json.imageUrl ? json.imageUrl : ''" :onerror="logo" />
                    </div>
                    <div class="brand-info">
                        <h2>{{json.name || '--'}}</h2>
                        <div>
                            <span>
                                当前状态：
                                <strong>
                                    {{json.status || '--'}} </strong>
                            </span>
                            <span>商品类别：{{json.intCls || '--'}}</span>
                            <span>申请号：{{json.regNo || '--'}}</span>
                        </div>
                    </div>
                </div>
                <!-- <div class="brand-right fr">
                    <div class="top">
                        <a href="javascript:void(0)" class="button buy">我要购买</a>
                        <a href="javascript:void(0)" class="button pdf">导出PDF</a>
                    </div>
                </div> -->
            </div>
        </div>
        <div class="brand-detail waterMark" v-if='json'>
            <div class="time-line">
                <ul>
                    <li :class="{'active':isStep(json.appDate)}">
                        <div class="time-line-content">
                            <p>商标申请</p>
                            <p class="time">{{json.appDate || '--'}}</p>
                        </div>
                        <i class="circle">
                            <i class="circle-in"></i>
                        </i>
                    </li>
                    <li :class="{'active':isStep(json.announcementDate)}">
                        <div class="time-line-content">
                            <p>初审公告</p>
                            <p class="time">{{json.announcementDate || '--'}}</p>
                        </div>
                        <i class="circle">
                            <i class="circle-in"></i>
                        </i>
                    </li>
                    <li :class="{'active':isStep(json.regDate)}">
                        <div class="time-line-content">
                            <p>已注册</p>
                            <p class="time">{{json.regDate || '--'}}</p>
                        </div>
                        <i class="circle">
                            <i class="circle-in"></i>
                        </i>
                    </li>
                    <li :class="{'active':isStep(stopTime(json.validPeriod))}">
                        <div class="time-line-content">
                            <p>终止</p>
                            <p class="time">{{stopTime(json.validPeriod)}}</p>
                        </div>
                        <i class="circle">
                            <i class="circle-in"></i>
                        </i>
                    </li>
                </ul>
            </div>
            <table class="brand-detail-table">
                <tbody>
                    <tr>
                        <td class="td-title">商标名称</td>
                        <td class="td-content" colspan="3">{{json.name || '--'}}</td>
                    </tr>
                    <tr>
                        <td class="td-title">法律状态</td>
                        <td class="td-content">
                            <strong> {{json.status || '--'}}</strong>
                        </td>
                        <td class="td-title">申请号</td>
                        <td class="td-content">{{json.regNo || '--'}}</td>
                    </tr>
                    <tr>
                        <td class="td-title">商品类别</td>
                        <td class="td-content">{{json.intCls || '--'}}</td>
                        <td class="td-title">申请日期</td>
                        <td class="td-content">{{json.appDate || '--'}}</td>
                    </tr>
                    <tr>
                        <td class="td-title">申请人名称</td>
                        <td class="td-content" colspan="3">
                            <a :href="initUrl(json.applicantCn,3)" target="_blank" class="col">
                                <i class="color">{{json.applicantCn || '--'}}</i>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td class="td-title">申请人地址</td>
                        <td class="td-content" colspan="3">{{json.addressCn || '--'}}</td>
                    </tr>
                    <tr>
                        <td class="td-title">申请人名称（英文）</td>
                        <td class="td-content" colspan="3">{{json.applicantEn || '--'}}</td>
                    </tr>
                    <tr>
                        <td class="td-title">申请人地址（英文）</td>
                        <td class="td-content" colspan="3">{{json.addressEn || '--'}}</td>
                    </tr>
                    <tr>
                        <td class="td-title">商标图片</td>
                        <td class="td-content">
                            <div class="brand-img">
                                <img :src="json.imageUrl ? json.imageUrl : ''" :onerror="logo" />
                            </div>
                        </td>
                        <td class="td-title">图片要素</td>
                        <td class="td-content">
                            <ul>
                                无
                            </ul>
                        </td>
                    </tr>
                    <tr>
                        <td class="td-title">商品/服务项目</td>
                        <td class="td-content" colspan="3">
                            <ul class="product-list">
                                <li class="list" v-for='(item,idx) in initServerArr(json.tmGoodsService)' :key='idx'>{{item}}</li>
                            </ul>
                        </td>
                    </tr>
                    <tr>
                        <td class="td-title">初审公告期号</td>
                        <td class="td-content">
                            {{json.announcementIssue || '--'}}
                        </td>
                        <td class="td-title">初审公告日期</td>
                        <td class="td-content">{{json.announcementDate || '--'}}</td>
                    </tr>
                    <tr>
                        <td class="td-title">注册公告期号</td>
                        <td class="td-content">
                            {{json.regIssue || '--'}}
                        </td>
                        <td class="td-title">注册公告日期</td>
                        <td class="td-content">{{json.regDate || '--'}}</td>
                    </tr>
                    <tr>
                        <td class="td-title">专用权期限</td>
                        <td class="td-content">{{json.validPeriod || '暂无'}}</td>
                        <td class="td-title">是否共有商标</td>
                        <td class="td-content">{{json.applicant1 || "否"}}</td>
                    </tr>
                    <tr>
                        <td class="td-title">后期制定日期</td>
                        <td class="td-content">{{json.houQiZhiDingDate || "--"}}</td>
                        <td class="td-title">国际注册日期</td>
                        <td class="td-content">{{json.guoJiZhuCeDate || "--"}}</td>
                    </tr>
                    <tr>
                        <td class="td-title">优先权日期</td>
                        <td class="td-content">{{json.youXianQuanDate || "--"}}</td>
                        <td class="td-title">代理人名称</td>
                        <td class="td-content">
                            <a :href="initUrl(json.tmSection,6)" target="_blank" class="col">
                                {{json.tmSection || "--"}}</a>
                        </td>
                    </tr>
                    <tr>
                        <td class="td-title">商标状态</td>
                        <td class="td-content" colspan="3">
                            <ul class="brandstate-list">
                                <li class="list" v-for='(item,idx) in initServerArr2(json.tmApplyFlow)' :key='idx'>{{item}}</li>

                            </ul>
                        </td>
                    </tr>
                    <tr>
                        <td class="td-title">商标公告</td>
                        <td class="td-content" colspan="3">
                            <ul class="brandnotice-list">
                                <li class="list" v-for='(item,idx) in initServerArr2(json.tmGongGaoyFlow)' :key='idx'>{{item}}</li>
                            </ul>
                        </td>
                    </tr>
                </tbody>
            </table>
            <span class="tips">*商标查询信息仅供参考，实际以国家商标局为准。</span>
            <div class="bottom-btns">
                <span class="btns-left">
                    <a href="/show/navigation_trademark_law_06&S7741113092604534784.html" class="mybtn mybtn-gray ml6" target="_blank">申请无效</a>
                    <a href="/show/navigation_trademark_law_02&S7741102661842345985.html" class="mybtn mybtn-gray ml6" target="_blank">申请撤三</a>
                    <a href="/show/navigation_trademark_change_05&S7741135255319453697.html" class="mybtn mybtn-gray ml6" target="_blank">商标转让</a>
                    <a href="/show/navigation_trademark_change_02&S7741134886166175745.html" class="mybtn mybtn-gray ml6" target="_blank">商标续展</a>
                </span>
                <span class="btns-right fr">
                    <!-- <a class="mybtn jiankong" href="javascript:;">监控该商标</a> -->
                    <a class="mybtn mybtn-inverse ml6" href="javascript:;" @click="openConsu(json.name,json.intCls,json.regNo)">
                        <i class="iconfont icon-gouwuche"></i>购买该商标</a>
                </span>
            </div>
        </div>
        <consultation :isShow="showConsu" :name="consuName" :type="consuType" :code="consuRegNO" @close='closeConsu' />
    </div>
</template>
  <script>
import miniHead from "~/components/header/mini";
import img from "~/assets/images/default.png";
import { Http } from "~/plugins/axios.js";
import axios from "axios";
import consultation from "~/components/consultation";
export default {
    layout: "empt",
    async asyncData({ params, redirect, error, env, route }) {
        let keyWord = params.details.split(/\.|_/)[1].split("|");
        let url = encodeURI(
            `${env.baseUrl}brandSearch/brandDetail?type=${keyWord[1]}&appNo=${
                keyWord[0]
            }`
        );
        let { data } = await axios.get(url);
        if (data.code == 0) {
            return {
                keyWord: keyWord,
                json: data.data[0],
                baseUrl: env.webSite + route.fullPath
            };
        } else {
            error({ statusCode: 500, message: data.msg });
        }
    },
    head() {
        return {
            title: `${this.json.name}-专注于商标查询、专利查询-知千秋官网`,
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
                        "知千秋致力于知千秋致力于运营大数据技术、人工智能等技术重新定义知识产权生态链。提供商标查询,商标注册,专利申请,专利查询,版权登记全流程服务。提供免费商标查询Saas工具,智能商标注册0服务费省钱到底,商标查询,商标注册监控,专利申请等服务获得客户的高度认可。"
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
    components: {
        miniHead,
        consultation
    },
    data() {
        return {
            showNav: 0,
            logo: 'this.src="' + img + '"',
            json: "",
            showConsu: 0,
            consuName: "", //咨询窗商标名
            consuType: "", //咨询窗商标类型
            consuRegNO: "" //咨询窗商标注册号
        };
    },
    created() {},
    mounted() {},
    methods: {
        initUrl(name, num) {
            //   this.$router.push({
            //         path: "/patentSearch/list_" + this.keyword + ".html"
            //     });
            return "/list/result_" + name + "." + num + ".html";
        },
        initServerArr(str) {
            return str ? str.split(" ") : "--";
        },
        initServerArr2(str) {
            return str ? str.split("、") : "--";
        },
        stopTime(str) {
            return str ? str.split("至")[1] : "--";
        },
        isStep(str) {
            let time = new Date(str).getTime();
            let now = new Date().getTime();
            return time < now;
        },
        //打开购买咨询窗口
        openConsu(name, type, code) {
            this.consuName = name;
            this.consuType = type;
            this.consuRegNO = code;
            this.showConsu = 1;
        },
        closeConsu() {
            this.showConsu = 0;
        }
    },
    destroyed() {}
};
</script>
<style scoped>
.page-brand {
    height: 150px;
    background: url("../../../assets/images/main/detailbg.png") no-repeat center;
    background-size: auto 150px;
    background-color: #222b38;
}
.brand-left {
    float: left;
    margin-top: 25px;
}
.brand-img {
    display: inline-block;
    margin-right: 20px;
    width: 100px;
    height: 100px;
    font-size: 0px;
    vertical-align: middle;
    overflow: hidden;
    text-align: center;
    background-color: #fff;
}
.brand-img img {
    display: inline-block;
    max-width: 100%;
    max-height: 100%;
    vertical-align: middle;
}
.brand-info {
    display: inline-block;
    color: #fff;
    vertical-align: middle;
}
.brand-info h2 {
    margin-bottom: 20px;
    width: 670px;
    font-size: 18px;
    font-weight: bold;
}
.brand-info span {
    margin-right: 40px;
}
.brand-right .top {
    padding-top: 50px;
}
.brand-right .top a {
    color: #fff;
}
.brand-right .top a:hover {
    color: #565656;
}
.brand-right .top .button {
    margin-left: 22px;
    width: 76px;
    line-height: 28px;
    text-align: center;
    border-radius: 0px;
}
.brand-right .top .button.buy {
    background-color: #f08b2f;
    border: 1px solid #f08b2f;
}

/* //详情正文 */
.brand-detail {
    margin: 0 auto;
    margin-top: 45px;
    margin-bottom: 100px;
    width: 850px;
}

.page.h5 .page-top {
    display: none;
}
.page.h5 .page-detail {
    display: none;
}
.page.h5 .page-detail.h5-page-detail {
    display: block !important;
    padding: 10px;
    overflow-x: hidden;
}
.page.h5 #rightFixBar {
    display: none;
}
.page.h5 .brand-detail-table td {
    padding: 5px;
    font-size: 12px;
}
.page.h5 .brand-detail-table td.td-title {
    width: 100px;
}
.page.h5 .page-brand {
    height: auto;
    background: none;
    background-color: #fff;
}
.page.h5 .brand-detail {
    margin-top: 15px;
    margin-bottom: 20px;
    width: 100%;
}
.page.h5 .brand-left {
    float: none;
    margin-top: 0px;
    width: 100%;
}
.page.h5 .brand-info {
    width: calc(100% - 120px);
    color: #333;
}
.page.h5 .brand-info h2 {
    margin-bottom: 10px;
}
.page.h5 .brand-info span {
    display: block;
    width: 100%;
    margin-right: 0px;
}
.page.h5 .brand-img {
    margin-right: 15px;
    border: 1px solid #ddd;
}
.page.h5 .product-list li {
    float: none;
    width: 100%;
}
.product-list li {
    float: left;
    width: 50%;
}
.page.h5 .list {
    font-size: 12px;
}

.page-brand-in {
    height: 100%;
}
.page-brand-in.w-center {
    width: 1000px;
}
.brand-left {
    float: left;
    margin-top: 25px;
}
.brand-right {
    float: right;
}
.brand-img {
    display: inline-block;
    margin-right: 20px;
    width: 100px;
    height: 100px;
    font-size: 0px;
    vertical-align: middle;
    overflow: hidden;
    text-align: center;
    background-color: #fff;
}
.brand-img:before {
    content: "";
    display: inline-block;
    width: 0;
    height: 100%;
    vertical-align: middle;
}
.brand-img img {
    display: inline-block;
    max-width: 100%;
    max-height: 100%;
    vertical-align: middle;
}
.brand-info {
    display: inline-block;
    color: #fff;
    vertical-align: middle;
}
.brand-info h2 {
    margin-bottom: 20px;
    width: 670px;
    font-size: 18px;
    font-weight: bold;
}
.brand-info strong {
    color: #f08b2f;
}
.brand-info span {
    margin-right: 40px;
}
.brand-right .top {
    padding-top: 50px;
}
.brand-right .top .button {
    margin-left: 22px;
    width: 76px;
    line-height: 28px;
    text-align: center;
    border-radius: 0px;
}
.brand-right .top .button.buy {
    background-color: #f08b2f;
    border: 1px solid #f08b2f;
}
.brand-right .bottom {
    margin-top: 16px;
    padding-left: 27px;
    color: #fff;
}
.brand-detail {
    margin: 0 auto;
    margin-top: 45px;
    margin-bottom: 100px;
    width: 850px;
}
.brand-detail strong {
    color: #f08b2f;
}
.time-line {
    margin: 0 auto;
    width: 100%;
}
.time-line .btn {
    float: right;
    margin-top: 30px;
    margin-right: 50px;
}
.time-line ul {
    text-align: left;
    overflow: hidden;
}
.time-line li {
    float: left;
    position: relative;
    width: 25%;
    font-size: 14px;
    text-align: center;
}
.time-line li .time-line-date {
    margin: 10px auto;
    width: 120px;
    line-height: 1.2em;
    text-align: center;
}
.time-line li:before {
    content: "";
    position: absolute;
    top: 51px;
    left: 0px;
    width: 100%;
    height: 2px;
    background-color: #dcdcdc;
    z-index: 0;
}
.time-line li:first-child:before {
    left: 50%;
    width: 50%;
}
.time-line li:last-child:before {
    right: 50%;
    width: 50%;
}
.time-line li.active:before {
    background-color: #ff9900;
}
.time-line li .circle {
    display: inline-block;
    position: relative;
    margin-top: 5px;
    width: 16px;
    height: 16px;
    border: 1px solid #ffffff;
    border-radius: 50%;
    z-index: 1;
}
.time-line li .circle-in {
    display: inline-block;
    width: 100%;
    height: 100%;
    background-color: #bbb;
    border-radius: 50%;
    border: 2px solid #fff;
    box-sizing: border-box;
    vertical-align: top;
}
.time-line li.active .circle {
}
.time-line li.active .circle-in {
    background-color: #ff9900;
}
.time-line li p {
    text-align: center;
}
.time-line li p.time {
    margin-bottom: 4px;
    font-size: 12px;
    height: 16px;
}
.time-line li.active p {
    color: #ff9900;
}
.time-line li p span {
    display: block;
    font-size: 12px;
}

.brand-detail-table {
    margin-top: 20px;
    width: 100%;
    /* background: url(../images/water.png); */
    background-size: 410px;
    background-position: 100px -60px;
}
.brand-detail-table td {
    padding: 12px 20px;
    padding-right: 0;
    border: 1px solid #d8d8d8;
}

.td-title {
    width: 160px;
    color: #666;
    background-color: #fcf9f2;
}
.bottom-btns {
    margin-top: 20px;
}
.brand-detail-table td.td-content {
    padding-right: 10px;
    min-width: 190px;
}
.brand-detail .tips {
    display: inline-block;
    margin-top: 5px;
    margin-left: 15px;
    font-size: 12px;
    color: #999;
}
.bottom-btns .mybtn {
    display: inline-block;
    text-align: center;
    line-height: 32px;
    padding: 0 15px;
    cursor: pointer;
    font-size: 14px;
    background-color: #fff;
    border: solid 1px #f08b2f;
    color: #f08b2f;
    border-radius: 3px;
}
</style>
