<template>
    <div style="background:#fff">
        <div class="com_detail" v-if='titleJson'>
            <div class="page data_page">
                <dl class="data_list no_border">
                    <MathImg :str='apiName' />
                    <dd>
                        <div class="d_left">
                            <p>
                                <a class="d_ttl">{{apiName}}</a>
                            </p>
                            <p>
                                <label>接口状态：正常</label>

                                <label class="star">
                                    评分：
                                    <span>
                                        <em class="star0"></em>

                                    </span>
                                </label>
                            </p>
                            <p>
                                <label>服务商：成都顶联互动信息产业有限公司</label>
                            </p>
                            <p class="description">
                                <span :class="{'Ellipsis': Ellipsis }">
                                    描述：{{apidetail}}</span>
                                <a @click="Ellipsis=Ellipsis ? 0 : 1 " title="展开折叠描述" style="display: block;"><img src="../../assets/images/expand.png"></a>
                            </p>
                        </div>
                        <div class="d_right">
                            <p>
                                <a class="b_btn" @click="applyKey(applyId)">申请数据</a>
                            </p>
                            <!-- <p>
                                <a href="/Tool?apiId=f738e318-62b5-11e7-bbdd-00155d0ab207" class="y_btn">查看演示</a>
                            </p> -->
                        </div>
                    </dd>
                </dl>
            </div>
        </div>
        <div class="page">
            <div class="page_left">
                <!-- <div class="l_ttl">帮助</div>
                <div class="l_con" style="padding-top: 0;">
                    <p class="l_table">
                        <b class="l_l">内容</b>
                        <b>详细</b>
                    </p>
                    <p class="l_table">
                        <span class="l_l">接口测试：</span>
                        <span>
                            <a href="/Tool?apiId=f738e318-62b5-11e7-bbdd-00155d0ab207">API测试工具</a>
                        </span>
                    </p>
                    <p class="l_table">
                        <span class="l_l">技术支持：</span>
                        <span>
                            <a href="http://wpa.qq.com/msgrd?v=3&amp;uin=2729142779&amp;site=qq&amp;menu=yes">service@yjapi.com</a>
                        </span>

                    </p>
                    <p class="l_table">
                        <span class="l_l">在线客服：</span>
                        <span>
                            <a href="http://wpa.qq.com/msgrd?v=3&amp;uin=2729142779&amp;site=qq&amp;menu=yes" class="l_btn">客服咨询</a>
                        </span>
                    </p>
                </div> -->
            </div>
            <div class="page_right data_page">
                <div class="page_ttl">
                    <span>接口列表</span>
                </div>
                <div class="data_detail">
                    <ul class="api_list" v-if="totalArr.length > 0">
                        <li v-for='(item,idx) in totalArr' :key='idx' @click="switchTab(idx)">
                            <a style="display:block; float:left" :class="{'selected': idx == switchId} ">{{item.url.name}}</a>
                        </li>

                        <div class="clear"></div>
                    </ul>
                    <div class="data_con_wrap" id="data_con_wrap">
                        <div class="data_con show01">
                            <div id="tab01">
                                <div class="tabList">
                                    <ul class="tabul">
                                        <li v-for='(item,idx) in tab03Arr' :key='idx' @click="switchTab03(idx)" :class="{active:tab03 == idx }">{{item}}</li>
                                        <!-- <li class="active">接口信息</li>
                                        <li>资费说明</li> -->
                                    </ul>
                                </div>
                                <div class="docs_api_show" rel="apilists" style="margin-top:20px;" v-show="tab03 == 0">
                                    <div class="simpleline">
                                        <strong>接口地址：</strong>
                                        <span class="url">{{requirePath(1)}}</span>
                                    </div>
                                    <br>
                                    <div class="simpleline">
                                        <strong>支持格式：</strong>
                                        <span class="url">{{titleJson.support}}</span>
                                    </div>
                                    <br>
                                    <div class="simpleline">
                                        <strong>请求方式：</strong>
                                        <span class="url">{{titleJson.method}}</span>
                                    </div>
                                    <br>
                                    <div class="simpleline">
                                        <strong>请求示例：</strong>
                                        <span class="url">{{requirePath(2)}}</span>
                                    </div>
                                    <br>
                                    <div class="data_ttl">
                                        <strong>请求参数：</strong>
                                    </div>
                                    <div class="simplearea">
                                        <table class="ntable ntable-odd data" v-if="dataMsg">
                                            <tr>
                                                <th>名称</th>
                                                <th>类型</th>
                                                <th>是否必填</th>
                                                <th>描述</th>
                                            </tr>
                                            <tr v-for="(item,idx) in dataMsg" :key="idx">
                                                <td class="text-center">{{item.name}}</td>
                                                <td class="text-center">{{item.type}}</td>
                                                <td class="text-center">{{item.must == 1 ? "是" : "否"}}</td>
                                                <td class="text-center"> {{item.explain}}</td>
                                            </tr>

                                        </table>
                                    </div>
                                    <br>
                                    <div class="data_ttl">
                                        <strong>返回字段：</strong>

                                    </div>
                                    <div class="simplearea" v-if="backMsg">
                                        <table class="ntable ntable-odd data">
                                            <tr>
                                                <th>名称</th>
                                                <th>类型</th>
                                                <th>说明</th>
                                            </tr>
                                            <tr v-for="(item,index) in backMsg" :key="index">
                                                <td class="text-center">{{item.name}}</td>
                                                <td class="text-center"> {{item.type}}</td>
                                                <td class="text-center">{{item.explain}}</td>
                                            </tr>

                                        </table>

                                    </div>
                                    <br>
                                    <div class="data_ttl">
                                        <strong>JSON返回示例：</strong>
                                    </div>
                                    <div class="simplearea">

                                        <pre style="color:#3b97f5">{{titleJson.returnResultExample}}</pre>

                                    </div>
                                </div>
                                <div class="docs_api_show" rel="apilists" style="margin-top:20px;" v-show="tab03 == 1">
                                    <div class="fullsimpleline">
                                        <strong>收费方式：</strong>
                                    </div>
                                    <div class="fullsimplearea" v-if='tab03Json[switchId]'>
                                        <p>{{tab03Json[switchId].wayName}}:{{tab03Json[switchId].money}}元人民币可以使用{{tab03Json[switchId].sysTimes}}天</p>
                                    </div>
                                    <div class="fullsimplearea" v-else>

                                        按次收费标准：每个接口都会有一个标准的单价，如0.5元/次。其中200状态表示查询命中才收费，201状态表示没有查询到收费情况。其它状态或者未标明情况下都将不会收费。 包月收费标准：部分接口允许包月，系统会限定每天最大的调用量，包月需要客户通过认证后由后台客服修改才生效。接口默认都按计次为主要收费方式。

                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import { IsLogin } from "@/util/common";
import { urlPath } from "@/util/Http.js";
import MathImg from "@/pages/api/mathImg";
export default {
    name: "",
    components: { MathImg },
    data() {
        return {
            Ellipsis: 1,
            titleJson: "",
            dataMsg: "",
            backMsg: "",
            applyId: "", //申请固定ip
            totalArr: [], //总数组，
            switchId: 0,
            tab03Arr: ["接口详情", "资费说明"],
            tab03: 0, //接口详情，资费说明,
            tab03Json: []
        };
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage() {
            this.$axios({
                type: "get",
                url: "apiInfo/getApiDetail",
                data: { apiId: sessionStorage.getItem("apiId") },
                success: res => {
                    // console.log("sessionStorage", res);
                    if (res.data.code == 0) {
                        this.titleJson = res.data.data.url;
                        this.dataMsg = res.data.data.params;
                        this.backMsg = res.data.data.returnParams;
                        this.tab03Json = res.data.data.chargingWays;

                        this.applyId = res.data.data.url.id;
                        this.apiName = res.data.data.url.name;
                        this.apidetail = res.data.data.url.detailExplain;

                        let newArr = [res.data.data];
                        if (
                            res.data.data.extUrlArr &&
                            res.data.data.extUrlArr.length > 0
                        ) {
                            newArr.push(...res.data.data.extUrlArr);
                        }
                        this.totalArr = newArr;
                    } else {
                        this.$message({
                            message: "请求数据失败" + res.data.msg,
                            type: "error",
                            showClose: true,
                            duration: 0
                        });
                    }
                }
            });
        },
        requirePath(num) {
            // if(num == 1 || this.titleJson.method == "POST"){
            let newPath = urlPath.substring(0, urlPath.length - 1);
            if (num == 1) {
                return newPath + this.titleJson.url;
            } else if (num == 2) {
                let str = "";
                for (let item of this.dataMsg) {
                    str += item.name + "=" + item.example + "&";
                }

                return newPath + this.titleJson.url + "?" + str;
            }
        },
        tip() {
            this.$message({
                message: "内测中，敬请期待",
                type: "warning"
            });
        },
        applyKey(id) {
            let _this = this;
            if (IsLogin(_this)) return;
            this.$confirm("确认是否申请此接口？")
                .then(_ => {
                    this.$axios(
                        {
                            type: "get",
                            url: "api/userUrlApply/applyApi.do",
                            data: {
                                userId: this.$store.state.userInfo.userId,
                                apiIds: id
                            },
                            success: res => {
                                if (res.data.code === 0) {
                                    this.$message({
                                        message: "申请成功",
                                        type: "success",
                                        duration: 1500,
                                        onClose: () => {
                                            this.$router.push("/MyData");
                                        }
                                    });
                                } else {
                                    this.$alert(res.data.msg);
                                }
                            }
                        },
                        1
                    );
                })
                .catch(_ => {});
        },
        switchTab(num) {
            if (num == this.switchId) return;
            let arr = this.totalArr[num];
            this.switchId = num;
            this.titleJson = arr.url;
            this.dataMsg = arr.params;
            this.backMsg = arr.returnParams;
            this.tab03 = 0;
        },
        switchTab03(num) {
            if (this.tab03 == num) return;
            this.tab03 = num;
        }
    }
};
</script>
<style scoped>
@import "../../assets/table.css";
.page_left {
    width: 220px;
}
.page_right {
    width: 970px;
    margin-left: 0px;
    float: right;
    overflow: hidden;
    margin-bottom: 30px;
}
.data.ntable th {
    text-align: center;
    background: #f6f6f7;
    border: #dbdbde 1px solid;
    border-collapse: collapse;
    padding: 10px 12px 10px 12px;
    font-weight: normal;
    color: #444;
    line-height: 19px;
}
.data.ntable td {
    border: #dbdbde 1px solid;
}
.data tr:nth-child(odd) {
    background: #f6f6f7;
}
.tabul .active {
    color: #3b97f5;
    border-bottom: 3px solid #0094de !important;
    font-size: 18px;
}
</style>