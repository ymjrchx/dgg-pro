<template>
    <div>
        <div class="patentDetail-wrap w waterMark" v-if='jsonObj'>
            <!-- start 时间轴 -->
            <ul class="time-axis songti" :style="cssObj">
                <div class="time-axis-arrow" :style="{top:scollTop}"></div>
                <i class="bg-top"></i>
                <li class="time-axis-header clearfix cur" v-for='(item,idx) in rightNav' :key="idx" :class="{'active':index == idx}" @click="JumpEle(item.id,idx)">
                    <a>
                        <i class="icon icon-disc"></i>
                    </a>
                    <span class="text">{{item.name}}</span>
                </li>
                <!-- <li class="time-axis-header clearfix">
                    <a @click="JumpEle()">
                        <i class="icon icon-disc"></i>
                    </a>
                    <span class="text">信息查询</span>
                </li> -->
                <i class="bg-bottom"></i>
            </ul>
            <!-- end 时间轴 -->

            <!-- start 悬浮 -->
            <div class="xuanfuTop">
                <!-- start 标题 -->
                <h1 class="page-title clearfix">
                    <span class="type">
                        [{{jsonObj.kindCodeDesc || '--'}}]
                    </span>
                    <span class="title">
                        <a data-href="#">{{jsonObj.piInventName}}</a>
                    </span>
                    <span class="law-state bgcolor-1" v-if='jsonObj.legalStatusDesc'>
                        {{jsonObj.legalStatusDesc}}
                    </span>
                    <!-- <dl class="btn">
                        <a class="mybtn btn-related" target="_blank" data-href="/index/search?id=CN102047956A">
                            <i class="icon icon-related2"></i>相似专利</a>
                        <span class="mybtn btn-download" data-href="">
                            <i class="icon icon-download"></i>
                            <em>下载全文</em>
                            <div class="btn-bodyer">
                                <a class="mybtn" target="_blank" data-href="/index/img/8c5d5611d1ceee4ba5d5bfc93ec0bbcbbee378762a31ac91c681ed9c54ff9af40fbe6b6580075a5436691a372d66c526?down=download&amp;numNo=CN102047956A">下载pdf</a>
                                <a class="mybtn" target="_blank" data-href="/index/img/5686c651889d10cd6c932ec228370a9539005639f6e09f68fd9a363b4959be439184ff5e706f3302778b082b9c89fbcf?down=download&amp;numNo=CN102047956A">下载word</a>
                            </div>
                        </span>
                    </dl> -->
                </h1>
                <!-- end 标题 -->
                <!-- start 关键词 -->
                <div class="keyword-box songti clearfix" id="keyword-box">
                    <span class="main-title">关键词：</span>
                    <div class="fl" v-html="initKey(jsonObj.keyWord)" style="width:80%">
                        <!-- <a class="search-keyword" data-href="javascript:;" data-color="markcolor0">目赤肿痛夜盲</a> -->
                    </div>

                </div>
                <!-- end 关键词 -->
                <div class="mytab">
                    <ul class="mytap-header clearfix">
                        <li class="list selected">
                            <a data-href="/index/detail?apn=200910117555.3&amp;flag=pub">专利公开详情</a>
                        </li>
                    </ul>
                </div>
            </div>
            <!-- end 悬浮 -->
            <!-- start 公开详情 -->
            <div class="detail-box" id="detail-box">
                <!-- start 基本信息 -->
                <div class="base-info time-axis-body" id="base-info" style="overflow: hidden; height: 215px;">
                    <h3 class="list-title">基本信息</h3>
                    <table class="songti" width="100%">
                        <tbody>
                            <tr>
                                <td width="95px">申请号：</td>
                                <td>{{jsonObj.applicationNumber || '--'}}</td>
                                <td width="100px">公开/公告号：</td>
                                <td>{{jsonObj.publicationNumber || '--'}}</td>
                            </tr>
                            <tr>
                                <td>申请日：</td>
                                <td>{{jsonObj.applicationDate || '--'}}</td>
                                <td>公开/公告日：</td>
                                <td>{{jsonObj.publicationDate || '--'}}</td>
                            </tr>
                            <tr>
                                <td>主分类号：</td>
                                <td>{{jsonObj.iPCList || '--'}}</td>
                                <td>申请人：</td>
                                <td>
                                    <!-- <a class="s-brand" target="_blank" title="搜商标" data-href="http://so.quandashi.com/index/search?key=兰州工业研究院&amp;param=3">兰州工业研究院
                                        <span class="btn">《搜商标</span>
                                    </a> -->
                                    {{jsonObj.assigneestring || '--'}}
                                </td>
                            </tr>
                            <tr>
                                <td>分类号：</td>
                                <td>{{jsonObj.piClassifyNum || '--'}}</td>
                                <td>发明/设计人：</td>
                                <td>{{jsonObj.inventorString || '--'}}</td>
                            </tr>
                            <tr>
                                <td>地址：</td>
                                <td>{{jsonObj.piAddress || '--'}}</td>
                                <td>专利代理机构：</td>
                                <td>{{jsonObj.agency || '--'}}</td>

                            </tr>
                            <tr>
                                <td>优先权：</td>
                                <td>{{jsonObj.priority || '--'}}</td>
                                <td>代理人：</td>
                                <td>{{jsonObj.agent || '--'}}</td>
                            </tr>
                            <!-- 下面这些字段暂无 -->
                            <!-- <tr>
                                <td>颁证日：</td>
                                <td>{{jsonObj.agent || '--'}}</td>
                                <td>范畴分类：</td>
                                <td>{{jsonObj.agent || '--'}}</td>
                            </tr>
                            <tr>
                                <td>国际申请：</td>
                                <td>暂无信息</td>
                                <td>分案申请：</td>
                                <td>暂无信息</td>
                            </tr>
                            <tr>
                                <td>国际公布：</td>
                                <td>暂无信息</td>
                                <td>国省代码：</td>
                                <td>暂无信息</td>
                            </tr>
                            <tr>
                                <td>进入国家日期：</td>
                                <td>暂无信息</td>
                            </tr> -->
                        </tbody>
                    </table>
                    <!-- <a class="show-more songti" data-href="javascript:;">
                        <i class="icon-triangle-up icon-selected"></i>
                        <span>展开</span>
                    </a> -->
                </div>
                <!-- end 基本信息 -->

                <!-- start 法律状态 -->
                <div class="law-state time-axis-body" id="law-state">
                    <h3 class="list-title">法律状态</h3>
                    <ul class="list-content songti" v-if='jsonObj.lawInfos'>
                        <li class="item clearfix" v-for='(item,idx) in jsonObj.lawInfos' :key='idx'>
                            <i class="icon icon-disc"></i>
                            <span class="text">{{item.lawDate}}</span>
                            <span class="text">{{item.lawStatus}}</span>
                        </li>
                        <!-- <i class="bg-bottom"></i> -->
                    </ul>
                </div>

                <!-- end 法律状态 -->

                <!-- start 摘要 -->
                <div class="profiles clearfix time-axis-body" id="profiles">
                    <dl class="profiles-left">
                        <h3 class="list-title">摘要</h3>
                        <div class="list-content songti">{{jsonObj.abstracts || '暂无'}}</div>
                    </dl>
                    <dl class="profiles-right">
                        <h3 class="list-title">摘要附图</h3>
                        <div class="list-content">
                            <a><img :src="initImg(jsonObj.desDrawings)" :onerror="logo" alt="摘要附图" title="摘要附图"></a>
                        </div>
                    </dl>
                </div>
                <!-- end 摘要 -->

                <!-- start 权利要求 -->
                <div class="claims time-axis-body" id="claims">
                    <h3 class="list-title">权利要求</h3>
                    <div class="list-content" v-html="jsonObj.claInfo || '暂无'">
                        <!-- <div class="text songti">
                            <div class="graph">
                                <span class="article">小米(谷子)酸奶的工艺，其特征是具有如下特点</span>
                            </div>
                            <div class="graph">
                                <span class="article">1.小米加工成粥后经高压均质或不均质加入到鲜牛奶中按照酸奶加工工艺添加辅料蔗糖、稳定剂经杀菌(90℃，300s)、冷却(45℃)接入乳酸菌保温发酵得到的小米酸奶。</span>
                            </div>
                            <div class="graph">
                                <span class="article">2.充分利用小米(谷子)的营养特点(含铁量高、高价低钠、维生素和矿物元素含量丰富，但必需氨基酸中的赖氨酸含量低)与牛奶营养特点(营养丰富、蛋白含量高，但缺铁、维生素C等)的互补性经过乳酸发酵制成营养更均衡的、具有一定保健功能的新型酸奶。非常适宜幼儿、妇女及老年人群。</span>
                            </div>
                            <div class="graph">
                                <span class="article">3.本工艺既可在发酵之前添加小米粥制成凝固性酸奶；也可在发酵后再加小米粥制成搅拌型酸奶。也可整粒添加，增加酸奶的咀嚼感。</span>
                            </div>
                        </div> -->
                    </div>
                </div>
                <!-- end 权利要求 -->

                <!-- start 说明书 -->
                <div class="instructions time-axis-body" id="instructions">
                    <h3 class="list-title">说明书</h3>
                    <div class="list-content" v-html="jsonObj.desInfo || '暂无'">

                    </div>
                </div>
                <!-- end 说明书 -->

                <!-- start 附图 -->
                <div class="figure time-axis-body" id="figure">
                    <h3 class="list-title">附图</h3>
                    <div class="list-content" v-if='jsonObj.desImg'>
                        <dl class="songti" v-for='(item,idx) in jsonObj.desImg' :key='idx'>
                            <dt>
                                <a><img :src="item.clUrl" :onerror="logo" alt="附图" title="附图"></a>
                            </dt>
                            <dd>图{{idx+1}}</dd>
                        </dl>
                    </div>
                </div>
                <!-- end 附图 -->

                <!-- start 信息查询 -->
                <!-- <div class="information-search time-axis-body" id="information-search">
                    <h3 class="list-title">信息查询</h3>
                    <div class="list-content songti clearfix">
                        <a data-href="#">收费信息</a>
                        <a data-href="#">专利证书发文信息</a>
                        <a data-href="#">通知书发文信息</a>
                        <a data-href="#">退信信息</a>
                        <a data-href="#">事务性公告</a>
                    </div>
                </div> -->
                <!-- end 信息查询 -->

            </div>
            <!-- end 公开详情 -->
        </div>
        <toTop/>
    </div>
</template>
  <script>
import toTop from "~/components/toTop.vue";
import axios from "axios";
import img from "~/assets/images/default.png";
import { Jump } from "~/assets/js/common";
export default {
    components: {
        toTop
    },
    head() {
        return {
            title: `${
                this.jsonObj.piInventName
            }-专注于商标查询、专利查询-知千秋官网`,
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
    async asyncData({ params, redirect, error, env, route}) {
        let code = params.detail.split(".")[0];

        let { data } = await axios.get(
            `${
                env.baseUrl
            }patentSearch/detail?key=applicationNumber&val=${code}`
        );
        if (data.code == 0) {
            return {
                code: code,
                jsonObj: data.data[0],
                baseUrl: env.webSite + route.fullPath
            };
        } else {
            error({ statusCode: 500, message: data.msg });
        }
    },
    data() {
        return {
            rightNav: [
                { name: "基本信息", id: "base-info" },
                { name: "法律状态", id: "law-state" },
                { name: "摘要", id: "profiles" },
                { name: "权利要求", id: "claims" },
                { name: "说明书", id: "instructions" },
                { name: "附图", id: "figure" }
            ],
            openAll: 0,
            code: "",
            logo: 'this.src="' + img + '"',
            jsonObj: "",
            index: 0,
            scollTop: "17px",
            cssObj: {
                display: "block",
                right: 0
            }
        };
    },

    mounted() {
        // console.log(
        //     "jsonObj",
        //     document.documentElement.clientHeight,
        //     document.documentElement.clientWidth
        // );
        this.cssObj = {
            display: "block",
            right: (document.documentElement.clientWidth - 1200) / 2 + "px"
        };
        window.addEventListener("scroll", this.handleScroll);
    },
    methods: {
        initKey(allStr) {
            if (!allStr) return "";

            let arr = allStr.split("、");
            let html = "";
            for (let key of arr) {
                html += ` <a class="search-keyword">${key}</a>`;
            }
            return html;
        },
        initImg(arr) {
            if (arr == undefined || arr.length == 0) {
                return "";
            } else {
                return arr[0].clUrl;
            }
        },
        JumpEle(id, idx) {
            let ele = document.getElementById(id);
            if (ele) {
                this.index = idx;
                Jump(ele, -110);
                this.scollTop = 17 + idx * 47 + "px";
            }
        },
        handleScroll() {
            var scrollTop =
                (window.pageYOffset ||
                    document.documentElement.scrollTop ||
                    document.body.scrollTop) + 110;
            var offsetTop1 = document.getElementById("base-info")
                    ? document.getElementById("base-info").offsetTop
                    : "",
                offsetTop2 = document.getElementById("law-state")
                    ? document.getElementById("law-state").offsetTop
                    : "",
                offsetTop3 = document.getElementById("profiles")
                    ? document.getElementById("profiles").offsetTop
                    : "",
                offsetTop4 = document.getElementById("claims")
                    ? document.getElementById("claims").offsetTop
                    : "",
                offsetTop5 = document.getElementById("instructions")
                    ? document.getElementById("instructions").offsetTop
                    : "",
                offsetTop6 = document.getElementById("figure")
                    ? document.getElementById("figure").offsetTop
                    : "";
            if (scrollTop < offsetTop2) {
                this.scollTop = "17px";
                this.index = 0;
            } else if (scrollTop >= offsetTop2 && scrollTop < offsetTop3) {
                this.scollTop = 17 + 1 * 47 + "px";
                this.index = 1;
            } else if (scrollTop >= offsetTop3 && scrollTop < offsetTop4) {
                this.scollTop = 17 + 2 * 47 + "px";
                this.index = 2;
            } else if (scrollTop >= offsetTop4 && scrollTop < offsetTop5) {
                this.scollTop = 17 + 3 * 47 + "px";
                this.index = 3;
            } else if (scrollTop >= offsetTop5 && scrollTop < offsetTop6) {
                this.scollTop = 17 + 4 * 47 + "px";
                this.index = 4;
            } else if (scrollTop >= offsetTop6) {
                this.scollTop = 17 + 5 * 47 + "px";
                this.index = 5;
            }
        }
    },
    destroyed() {
        window.removeEventListener("scroll", this.handleScroll);
    }
};
</script>
<style scoped>
@import url("../../../assets/css/patentSearch");
* {
    box-sizing: border-box;
}
</style>
