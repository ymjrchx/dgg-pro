<template>
    <div class="content">
        <div class="qianzhidaoQA-wrap">
            <div class="qianzhidaoQA">
                <div class="n-box">
                    <!-- start 搜索框 -->
                    <div class="qds-search-common">
                        <dl class="search-select" id="search-select">
                            <dt class="select-title" @click="showType">
                                <span class="text" id="1">{{docArr.length?docArr[typeIndex].name.substr(0, 2): ''}}</span>
                                <!-- <em v-if="docArr.length" class="iconfont">&#xe6b9;</em> -->
                            </dt>
                            <!-- <dd class="select-body" :class="{'atthis': isShow}" @mouseover="showType" @mouseout="hideType">
                                <span v-if="docArr.length" v-for="(item,index) in docArr" :key="item.code" @click="() => selectType(index)" class="item" id="1">{{item.name.substr(0, 2)}}</span>
                            </dd> -->
                        </dl>
                        <input type="text" maxlength="50" class="search-text" @keyup.enter="search" v-model="keywords" name="keywords" placeholder="请输入您要搜索的关键字">
                        <i class="iconfont search-btn" @click="search">&#xe678;</i>
                    </div>
                    <!-- end 搜索框 -->
                </div>
                <!-- start mytab -->
                <div class="mytab">
                    <!-- <div class="mytap-header mytap-header-1" v-if="$route.params.type">
                        <div class="box">
                            <ul class="item">
                                <li v-for="item in docArr" :key="item.code" class="list" :class="{'selected': item.code == $route.params.type.split('.')[0].split(',')[0]}">
                                    <a :href="'/question/'+item.code+'.html'">{{item.name == '商标业务' ? '商标问答' : item.name == '专利业务' ? '专利问答':"版权问答" }}</a>
                                </li>
                            </ul>
                        </div>
                    </div> -->
                    <div class="mytap-body mytap-body-1">
                        <!-- start 商标业务 -->
                        <div class="content selected">
                            <!-- start mytab -->
                            <div class="mytab">
                                <!-- start mytap-body -->
                                <div class="mytap-body bbs-post">
                                    <!-- start 左侧内容 -->
                                    <div class="have-answer">
                                        <!-- start item-->
                                        <p class="noData" v-if="!total">暂无相关数据</p>
                                        <ul class="item" v-if="total">
                                            <li class="list" v-for="item in list" :key="item.question.id">
                                                <div class="t-title">
                                                    <div class="c-td t-1 col">
                                                        [
                                                        <a :href="'/questionxq/'+item.question.id+'.html'" class="col f16">
                                                            <em>{{item.question.question}}</em>
                                                        </a>]
                                                    </div>
                                                     <div class="c-td t-4 t-icon f14" style="width:180px">
                                                        <!-- 
                                                        <span class="s1">{{item.answer.createTime}}</span> -->
                                                        <span class="s1"><em class="iconfont f14 mr5">&#xe629;</em>
                                                        <i class="color">{{item.answer.createTime}}</i></span>
                                                    </div>
                                                    <div class="c-td t-4 t-icon">
                                                        <span class="s1">已有
                                                            <i class="color">{{item.question.answerCount}}</i> 人回答</span>
                                                    </div>
                                                </div>
                                                <div class="t-p" v-if="item.answer">
                                                    <span>{{item.answer.answer}}</span>
                                                </div>
                                                <!-- <dl class="t-bottom" v-if="item.answer">
                                                    <dt class="t-b-1">{{item.question.keyWord}}</dt>
                                                    <dt class="t-b-2">
                                                        <em class="iconfont">&#xe661;</em>
                                                        <span class="s1">{{item.question.viewTimes}}次</span>
                                                    </dt>
                                                    <dt class="t-b-3">
                                                        
                                                    </dt>
                                                </dl> -->
                                            </li>
                                        </ul>
                                        <!-- end item -->
                                        <!-- start 分页 -->
                                        <div class="paging">
                                            <pagination :totalPage="total" :pageNum="pageNum" :pageSize="pageSize" @pagechange="pagechange" />
                                        </div>
                                        <!-- end 分页 -->
                                    </div>
                                    <!-- end 左侧内容 -->
                                    <!-- start 右侧内容 -->
                                    <div class="c-right">
                                     
                                        <!-- end 代理人 -->
                                        <div class="item item2">
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
                                    <!-- end 右侧内容 -->
                                </div>
                                <!-- end mytap-body -->
                            </div>
                            <!-- end mytab -->
                        </div>
                        <!-- end 商标业务 -->
                    </div>
                    <!-- end mytap-body -->
                </div>
                <!-- end mytab -->
            </div>
        </div>
    </div>
</template>
<script>
import toTop from "@/components/toTop";
import axios from "axios";
import pagination from "~/components/pagination.vue";

export default {
    asyncData({ params, redirect, error, env, store, route }) {
        if (!params.type) {
            error({ statusCode: 500, message: "参数错误" });
            return false;
        }
        if (isNaN(params.type)) {
            let paramsTemp = params.type.split(".")[0];
            let pageSize = 10;
            let pageNum = paramsTemp.split(",")[1] || 1;
            let keyword = paramsTemp.split(",")[2] || "";
            let numberArr = [
                "S7741101919677030400,S7741102108546539521,S7741102510755127297",
                "S7741445307712569344,S7741445546913726465,S7741445695501139969",
                "S7741448551415316481,S7741448858560004097,S7741449030811680769"
            ];
            let idxNum =
                paramsTemp.split(",")[0] == "navigation_patent"
                    ? 1
                    : paramsTemp.split(",")[0] == "navigation_copyright"
                        ? 2
                        : 0;
            let result = "";
            let product = "";
            if (keyword) {
                let url = encodeURI(
                    `${env.baseUrl}question/pageQueryByKeyWord?typeLevel1Code=${
                        paramsTemp.split(",")[0]
                    }&pageSize=${pageSize}&pageNum=${pageNum}&keyword=${keyword}`
                );
                return Promise.all([
                    axios.get(url),
                    axios.get(
                        `${env.baseUrl}serviceItem/findServiceItemByNumber`,
                        {
                            params: {
                                numbers: numberArr[idxNum]
                            }
                        }
                    ),
                    axios.get(`${env.baseUrl}navigation/getTreeData`)
                ]).then(res => {
                    result = res[0];
                    product = res[1].data.data;
                    return {
                        list: result.data.data ? result.data.data.list : [],
                        total: result.data.data ? result.data.data.count : 0,
                        pageNum: Number(pageNum),
                        pageSize,
                        product,
                        docArr: res[2].data.data.sonArr,
                        baseUrl: env.webSite + route.fullPath,
                        typeName: keyword
                    };
                });
            } else {
                return Promise.all([
                    axios.get(
                        `${
                            env.baseUrl
                        }/question/pageQueryByTypeLevel1Code?typeLevel1Code=${
                            paramsTemp.split(",")[0]
                        }&pageSize=${pageSize}&pageNum=${pageNum}`
                    ),
                    axios.get(
                        `${env.baseUrl}serviceItem/findServiceItemByNumber`,
                        {
                            params: {
                                numbers: numberArr[idxNum]
                            }
                        }
                    ),
                    axios.get(`${env.baseUrl}navigation/getTreeData`)
                ]).then(res => {
                    result = res[0];
                    product = res[1].data.data;

                    return {
                        list: result.data.data ? result.data.data.list : [],
                        total: result.data.data ? result.data.data.count : 0,
                        pageNum: Number(pageNum),
                        pageSize,
                        product,
                        docArr: res[2].data.data.sonArr,
                        baseUrl: env.webSite + route.fullPath,
                        typeName:
                            paramsTemp.split(",")[0] == "navigation_trademark"
                                ? "商标"
                                : paramsTemp.split(",")[0] ==
                                  "navigation_patent"
                                    ? "专利"
                                    : "版权"
                    };
                });
            }
        }
    },
    head() {
        return {
            title: `【${this.typeName}百科问答】${this.typeName}知识问答_${
                this.typeName
            }注册申请疑问-知千秋百科`,
            meta: [
                {
                    name: "keywords",
                    hid: "keywords",
                    content: `${this.typeName}知识问答,${this.typeName}百科,${
                        this.typeName
                    }百科问答`
                },
                {
                    name: "description",
                    hid: "description",
                    content: `【${this.typeName}知识问答】栏目${
                        this.typeName
                    }百科为您提供；常见${
                        this.typeName
                    }注册申请疑问解答信息大全。有${
                        this.typeName
                    }注册疑问找知千秋百科。`
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
            isShow: false,
            typeIndex: 0,
            keywords: ""
        };
    },
    components: {
        toTop,
        pagination
    },
    layout: "question",
    mounted() {},
    methods: {
        pagechange(page) {
            let keyWord = this.$route.params.type.split(".")[0].split(",");
            if (keyWord.length == 3) {
                keyWord = keyWord[2];
            } else {
                keyWord = "";
            }
            this.$router.push(
                `/question/${
                    this.list[0].question.typeLevel1Code
                },${page},${keyWord}.html`
            );
        },
        showType() {
            this.isShow = true;
        },
        getUrl(code, id) {
            if (id) {
                return `/show/${code}&${id}.html`;
            }
            return `/show/${code}.html`;
        },
        hideType() {
            this.isShow = false;
        },
        selectType(value) {
            this.typeIndex = value;
        },
        search() {
            this.$router.push(
                `/question/${this.$store.state.docArr[this.typeIndex].code},1,${
                    this.keywords
                }.html`
            );
        }
    }
};
</script>
<style scoped>
/* ---------- start 权知道 ---------- */
.noData {
    text-align: center;
    color: #999;
    font-size: 14px;
    border-top: 1px solid #eee;
    padding-top: 30px;
}

/* 已回答、等我回答tab列表 */

.qianzhidaoQA-wrap .mytab .mytap-body .mytab .mytap-header-2 .list {
    margin-right: -1px;
    padding: 0;
    border-bottom: 0;
}

.qianzhidaoQA-wrap .mytab .mytap-body .mytab .mytap-header-2 .list:hover {
    background-color: #fff;
}

.qianzhidaoQA-wrap .mytab .mytap-body .mytab .mytap-header-2 .list a {
    padding: 0 50px;
    background-color: #fff;
    color: #999;
    border: solid 1px #f0f0f0;
}

.qianzhidaoQA-wrap .mytab .mytap-body .mytab .mytap-header-2 .list.selected a {
    background-color: #fff;
    color: #f08b30;
    border: solid 1px #f0f0f0;
    border-top: solid 3px #f08b30;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list {
    padding: 25px 15px;
    border-bottom: dashed 1px #f0f0f0;
    overflow: hidden;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list:hover {
    background-color: #f9f9f9;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list .c-td {
    float: left;
    height: 18px;
    line-height: 18px;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list .c-td .icon {
    float: left;
    width: 14px;
    height: 14px;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list .c-td .s1 {
    display: block;
    float: left;
    width: 100%;
    margin-left: -14px;
    padding: 0 10px 0 20px;
    font-size: 14px;
    color: #999;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list .c-td .s1 a {
    color: #999;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list .c-td .s1 a:hover {
    color: #f08c2e;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list .t-1 {
    width: 810px;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list .t-1 .icon {
    width: auto;
    line-height: 17px;
    margin-right: 10px;
    font-size: 12px;
    color: #f08c2e;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list .t-1 .s1 {
    color: #777;
    font-size: 14px;
    margin-left: -160px;
    padding-left: 160px;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list .t-2 {
    width: 160px;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list .t-2 .icon {
    margin: 2px 0 0;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list .t-3 {
    width: 110px;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list .t-3 .icon {
    width: 16px;
    height: 9px;
    margin: 4px 0 0;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list .t-3 .s1 {
    margin-left: -16px;
    padding: 0 10px 0 22px;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list .t-4 {
    width: 150px;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list .t-4 .icon {
    margin: 2px 0 0;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list .t-5 {
    width: 120px;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list .t-5 .s1 a {
    color: #f08b30;
}

.qianzhidaoQA-wrap .mytab .mytap-body .list .t-5 .icon {
    margin: 2px 0 0;
}

.qianzhidaoQA-wrap .mytab .mytap-body .mypaging {
    margin-top: 35px;
    text-align: left;
}

/*已回答*/

.qianzhidaoQA-wrap .mytab .mytap-body .content {
    overflow: hidden;
}

.qianzhidaoQA-wrap .mytab .mytap-body .have-answer {
    width: 970px;
    float: left;
}

.qianzhidaoQA-wrap .mytab .mytap-body .have-answer .list {
}

.qianzhidaoQA-wrap .mytab .mytap-body .have-answer .list .t-title {
    overflow: hidden;
}

.qianzhidaoQA-wrap .mytab .mytap-body .have-answer .list .t-title .t-1 {
    width: 600px;
}

.qianzhidaoQA-wrap .mytab .mytap-body .have-answer .list .t-title .t-3 {
    width: 100px;
}

.qianzhidaoQA-wrap .mytab .mytap-body .have-answer .list .t-title .t-4 {
    width: 120px;
}

.qianzhidaoQA-wrap .mytab .mytap-body .have-answer .list .t-title .t-4 .s1 {
    margin: 0;
    padding: 0 10px 0 0px;
}

.qianzhidaoQA-wrap .mytab .mytap-body .have-answer .list .t-p {
    padding: 12px 0px 0;
    font-size: 14px;
    color: #999;
    line-height: 24px;
}

.qianzhidaoQA-wrap .mytab .mytap-body .have-answer .list .t-p a {
    color: #999;
}

.qianzhidaoQA-wrap .mytab .mytap-body .have-answer .list .t-p a:hover {
    color: #f08b30;
}

.qianzhidaoQA-wrap .mytab .mytap-body .have-answer .list .t-bottom {
    overflow: hidden;
    padding: 15px 0 0;
    font-size: 12px;
    color: #999;
}

.qianzhidaoQA-wrap .mytab .mytap-body .have-answer .list .t-bottom dt {
    float: left;
}

.qianzhidaoQA-wrap .mytab .mytap-body .have-answer .list .t-bottom .t-b-1 {
    width: 240px;
}

.qianzhidaoQA-wrap .mytab .mytap-body .have-answer .list .t-bottom .t-b-2 {
    width: 160px;
}

.qianzhidaoQA-wrap .mytab .mytap-body .have-answer .list .t-bottom .icon {
    float: left;
    width: 14px;
    height: 14px;
}

.qianzhidaoQA-wrap .mytab .mytap-body .have-answer .list .t-bottom .s1 {
    width: 100%;
    margin-left: -14px;
    padding: 0 10px 0 20px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

/*搜索无结果*/

.qianzhidaoQA-wrap .no-search-result {
    height: 80px;
    color: #777;
    border: dashed 1px #f0f0f0;
    padding: 10px 22px;
}

.qianzhidaoQA-wrap .no-search-result dl {
    height: 100%;
}

.qianzhidaoQA-wrap .no-search-result dl dt {
    float: left;
    height: 100%;
}

.qianzhidaoQA-wrap .no-search-result dl dt .icon {
    width: 54px;
    height: 54px;
}

.qianzhidaoQA-wrap .no-search-result dl dd {
    float: left;
    height: 100%;
    margin-left: 22px;
}

.qianzhidaoQA-wrap .no-search-result dl dd .p1 {
    padding: 6px 0 5px;
}

.qianzhidaoQA-wrap .no-search-result dl dd .p1 .color {
    color: #555;
    font-size: 16px;
}

.qianzhidaoQA-wrap .no-search-result dl dd .p2 {
    font-size: 12px;
}

.qianzhidaoQA-wrap .no-search-result dl dd .p2 .color {
    color: #ee8c3c;
    display: inline-block;
    padding: 0 3px;
}

/*搜索数量*/

.qianzhidaoQA-wrap .search-number {
    padding: 28px 2px 20px;
    color: #999;
    font-size: 12px;
    border-bottom: solid 1px #f0f0f0;
}

.qianzhidaoQA-wrap .search-number .color {
    color: #ee8c3c;
    display: inline-block;
    padding: 0 2px;
}
</style>
