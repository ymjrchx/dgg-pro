<template>
    <div class="content">
        <div class="order-page">
            <h2 class="order-main-title">我的商标</h2>
            <div class="order-page-form">
                <ul class="clearfix">
                    <li>
                        <label>订单编号：</label>
                        <input type="text" v-model="orderNo" class="text s-input" name="orderNo" autocomplete="off" placeholder="请输入订单编号" value="">
                    </li>
                    <li>
                        <label>服务类型：</label>
                        <select name="productId" v-model="serviceTypeLevel3Code">
                            <option value="">全部</option>
                            <option v-for="item in serviceTypeLevel3s" :key="item.id" :value="item.code">{{item.name}}</option>
                        </select>
                    </li>
                    <li>
                        <label>商标名称：</label>
                        <input type="text" v-model="name" class="text m-input" name="bizName" autocomplete="off" placeholder="请输入商标名称" value="">
                    </li>
                    <li>
                        <label>申请人：</label>
                        <input type="text" v-model="contactName" class="text s-input" name="applicantName" autocomplete="off" placeholder="请输入订单申请人" value="">
                    </li>
                    <li>
                        <label>支付方式：</label>
                        <select name="payType" v-model="payWay">
                            <option value="">全部</option>
                            <option value="alipay">微信支付</option>
                            <option value="wechat">支付宝支付</option>
                        </select>
                    </li>
                    <li>
                        <div class="block">
                            <el-date-picker v-model="time" :picker-options="pickerOptions" size="small" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
                            </el-date-picker>
                        </div>
                    </li>
                    <li>
                        <input @click="loadOrder(1)" type="submit" value="查询" class="button">
                    </li>
                </ul>
            </div>
            <div class="tabsPanel">
                <ul class="tabs-header clearfix">
                    <li class="list " :class="{'selected': !$route.query.tab}">
                        <a :href="'/center/smartOrder?serviceTypeLevel3Code='+serviceTypeLevel3Code+'&contactName='+contactName+'&payWay='+payWay+'&createTimeStart='+createTimeStart+'&createTimeEnd='+createTimeEnd">全部</a>
                    </li>
                    <li class="list " :class="{'selected': $route.query.tab == 'order_status_obligation'}">
                        <a :href="'/center/smartOrder?tab=order_status_obligation'+'&serviceTypeLevel3Code='+serviceTypeLevel3Code+'&contactName='+contactName+'&payWay='+payWay+'&createTimeStart='+createTimeStart+'&createTimeEnd='+createTimeEnd">未付款</a>
                    </li>
                    <li class="list " :class="{'selected': $route.query.tab == 'order_status_inprocess'}">
                        <a :href="'/center/smartOrder?tab=order_status_inprocess'+'&serviceTypeLevel3Code='+serviceTypeLevel3Code+'&contactName='+contactName+'&payWay='+payWay+'&createTimeStart='+createTimeStart+'&createTimeEnd='+createTimeEnd">服务中</a>
                    </li>
                    <li class="list " :class="{'selected': $route.query.tab == 'order_status_completed'}">
                        <a :href="'/center/smartOrder?tab=order_status_completed'+'&serviceTypeLevel3Code='+serviceTypeLevel3Code+'&contactName='+contactName+'&payWay='+payWay+'&createTimeStart='+createTimeStart+'&createTimeEnd='+createTimeEnd">已完成</a>
                    </li>
                    <li class="list " :class="{'selected': $route.query.tab == 'order_status_evaluate'}">
                        <a :href="'/center/smartOrder?tab=order_status_evaluate'+'&serviceTypeLevel3Code='+serviceTypeLevel3Code+'&contactName='+contactName+'&payWay='+payWay+'&createTimeStart='+createTimeStart+'&createTimeEnd='+createTimeEnd">已评价</a>
                    </li>
                </ul>
                <!-- start tabs-bodyer -->
                <div class="tabs-bodyer">
                    <!-- start section1 -->
                    <div class="section1 clearfix">
                        <p class="noneOrder" v-if="orderList && !orderList.dateMap.length">暂无订单</p>
                        <!-- //商标列表的表格 -->
                        <table width="100%" class="myDataTable brandListTable" v-if="orderList && orderList.dateMap.length">
                            <thead>
                                <tr>
                                    <th width='320px'>商标信息</th>
                                    <th>订单总额</th>
                                    <th>订单状态</th>
                                    <th>商标状态</th>
                                    <th width='260px'>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <template v-for="(item,idx) in orderList.dateMap">
                                    <tr :key='idx' class="tdTitle">

                                        <td colspan="5">
                                            <span>类型:
                                                <em>{{item.serviceName}}</em>
                                            </span>
                                            <span class="">订单编号: {{item.orderNo}}</span>
                                            <span class="">下单时间: {{item.createTime}}</span>
                                        </td>
                                    </tr>
                                    <tr :key='idx+100'>
                                        <td style="padding-left:20px">
                                            <a :href="initImg(item.host,item.exampleAddress)" target='_blank' class="fl" style="border: solid 1px #e2e3e8;  width:64px">
                                                <img :src="initImg(item.host,item.exampleAddress)" alt="商标" width="100%">
                                            </a>
                                            <div class="fl ml10">
                                                <span class="f16 col">{{item.trademarkName}}</span>
                                                <div class="moBox">
                                                    <p class="clear2">
                                                        查看所选类别
                                                    </p>
                                                    <div class="faBox">
                                                        <span v-for='(item,idx) in item.classList' :key='idx'>第{{item.classLevel1Code}}类:{{item.classLevel1Name}};</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <p class="mb5 f18" style="color:#ff0000">￥{{item.allPrice}}</p>
                                            <p class="f12">服务费：￥{{item.allServicePrice}}</p>
                                            <p class="f12">官费：￥{{item.allTrademarkClassPrice}}</p>
                                        </td>
                                        <td>
                                            <div class="order-status text-center">
                                                <span class="td-process-status col-r">
                                                    {{item.status}}
                                                </span>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="order-status text-center">
                                                <span class="td-process-status">
                                                    {{item.baoJianStatus}}
                                                </span>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="td-handle">
                                                <a v-if="item.status == '未付款'" :href="'/order/pay/'+item.orderNo" class="button button-white pay">
                                                    立即付款 </a>
                                                <a v-if="item.status == '待提交'" @click="submitOrder(item.id)" class="button button-white pay" href="javascript:void(0)">
                                                    立即提交 </a>
                                                <a v-if="item.status == '已完成'" href="javascript:void(0)" @click="evalOrder(item.id)" class="order-cancel button button-white green">评价订单</a>
                                                <a v-if="item.serviceName == '智能自助注册'" :href="'/center/orderDetail?id='+item.id" class="mt5 col f12 button button-white detailBtn">
                                                    查看详情
                                                </a>
                                                <a v-if="item.status == '未付款' || item.status == '待审核'" href="javascript:void(0)" @click="cancelOrder(item.id)" class="order-cancel button button-white cancel">取消订单</a>
                                            </div>
                                        </td>
                                    </tr>
                                </template>
                            </tbody>
                        </table>
                        <!-- //商标列表的表格  end-->
                        <!-- start 分页 -->
                        <div class="myPaging">
                            <pagination :totalPage="total" :pageNum="pageNum" :pageSize="pageSize" @pagechange="loadOrder" />
                        </div>
                        <!-- end 分页 -->
                    </div>
                    <!-- end section1 -->
                </div>
            </div>
            <!-- end tabsPanel -->
        </div>
        <div class="model" v-if="isEavl">
            <div class="content">
                <i class="iconfont" @click="isEavl=false">&#xe7fc;</i>
                <h1>评价</h1>
                <table width="100%" class="pj-info">
                    <tbody>
                        <tr>
                            <td valign="middle">星级</td>
                            <td>
                                <i @mouseover="startHover" v-for="(itemStar,index) in [0,1,2,3,4]" :class="{'full': index<=level}" :key="itemStar" :data-index="itemStar">{{itemStar}}</i>
                            </td>
                        </tr>
                        <tr>
                            <td valign="top">内容</td>
                            <td>
                                <textarea name="content" maxlength="300" placeholder="请输入评价内容" v-model="evalContent" cols="30" rows="10"></textarea>
                                <span class="tips">你还可以输入
                                    <b>{{(300-evalContent.length>0)?300-evalContent.length:0}}</b>个字</span>
                            </td>
                        </tr>
                    </tbody>

                </table>

                <div class="btn">
                    <a href="javascript:;" @click="submit">提交</a>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import pagination from "~/components/pagination.vue";
import popup from "~/components/popup"; //消息
import axios from "axios";

export default {
    head() {
        return {
            title: "个人中心-知千秋官网",
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
            ]
        };
    },
    layout: "center",
    components: {
        pagination
    },
    data() {
        return {
            pickerOptions: {
                disabledDate(time) {
                    return time.getTime() > Date.now();
                }
            },
            code: "navigation_trademark",
            time: "",
            orderList: "",
            name: "",
            orderNo: "",
            serviceTypeLevel3Code: "",
            contactName: "",
            payWay: "",
            markName: "",
            status: this.$route.query.tab ? this.$route.query.tab : "",
            total: 0,
            pageNum: 1,
            pageSize: 10,
            currpage: 1,
            isEavl: false,
            evalContent: "",
            level: 4
        };
    },
    async asyncData({ params, query, redirect, error, env }) {
        return Promise.all([
            axios.get(`${env.baseUrl}/navigation/getAllTypeLevel3Data`, {
                params: {
                    code: "navigation_trademark"
                }
            })
        ]).then(res => {
            let { data } = res[0];
            if (data.code == 0) {
                return {
                    serviceTypeLevel3s: data.data,
                    type: "navigation_trademark"
                };
            }
        });
    },
    created() {
        this.contactName = this.$route.query.contactName
            ? this.$route.query.contactName
            : "";
        this.payWay = this.$route.query.payWay ? this.$route.query.payWay : "";
        this.serviceTypeLevel3Code = this.$route.query.serviceTypeLevel3Code
            ? this.$route.query.serviceTypeLevel3Code
            : "";
        this.createTimeStart = this.$route.query.createTimeStart
            ? this.$route.query.createTimeStart
            : "";
        this.createTimeEnd = this.$route.query.createTimeEnd
            ? this.$route.query.createTimeEnd
            : "";
    },
    mounted() {
        this.loadOrder(1);
    },
    methods: {
        loadOrder(page) {
            if (
                new Date(this.createTimeEnd).getTime() <
                new Date(this.createTimeStart).getTime()
            ) {
                popup.created({
                    content: "开始时间不能大于结束时间",
                    time: 2000
                });
                return false;
            }
            if (page) {
                this.currpage = page;
            }
            this.$Http(
                "/order/queryUserOrder",
                {
                    userId: this.$store.state.userInfo.userId,
                    pageIndex: this.currpage,
                    pageNum: this.pageSize,
                    code: this.code,
                    serviceTypeLevel3Code: this.serviceTypeLevel3Code,
                    orderNo: this.orderNo,
                    contactName: this.contactName,
                    payWay: this.payWay,
                    markName: this.name,
                    createTimeStart: this.time[0] ? this.time[0] : "",
                    createTimeEnd: this.time[1] ? this.time[1] : "",
                    status: this.status,
                    type: "order_type2"
                },
                "get"
            ).then(res => {
                this.orderList = res.data.data;
                this.total = res.data.data.count;
            });
        },
        initImg(str1, str) {
            return str1 + str;
        },
        cancelOrder(id) {
            popup.created({
                type: "confirm",
                content: "确认是否取消此条订单?",
                success: bool => {
                    if (bool) {
                        this.$Http(
                            "/order/cancelUserOrder",
                            {
                                userId: this.$store.state.userInfo.userId,
                                orderId: id
                            },
                            "get",
                            true
                        ).then(res => {
                            popup.created({
                                content: "取消成功",
                                time: 2000
                            });
                            this.loadOrder();
                        });
                    }
                }
            });
        },
        evalOrder(id) {
            this.isEavl = true;
            this.id = id;
        },
        startHover(e) {
            this.level = e.target.dataset.index;
        },
        submit() {
            if (!this.evalContent) {
                popup.created({
                    content: "必须输入评价内容",
                    time: 2000
                });
                return false;
            }
            if (this.evalContent.length > 300) {
                popup.created({
                    content: "评价内容不能超过300",
                    time: 2000
                });
                return false;
            }
            let obj = {
                userId: this.$store.state.userInfo.userId,
                orderId: this.id,
                level: isNaN(parseInt(this.level))
                    ? 1
                    : parseInt(this.level) + 1,
                content: this.evalContent
            };
            sessionStorage.setItem("isFrom", true);
            this.$Http("/order/commentUserOrder", obj, "post", true).then(
                res => {
                    popup.created({
                        content: "评价成功",
                        time: 2000
                    });
                    this.loadOrder();
                    this.isEavl = false;
                    this.level = 4;
                    this.evalContent = "";
                }
            );
        },
        submitOrder(orederNum) {
            this.$Http(
                "order/changeStatus",
                {
                    userId: this.$store.state.userInfo.userId,
                    orderId: orederNum
                },
                "get"
            ).then(res => {
                // console.log(res);
                popup.created({
                    content: "提交成功",
                    time: 1000
                });
                setTimeout(() => {
                    this.loadOrder();
                }, 1000);
                // this.loadOrder();
            });
        }
    }
};
</script>
<style scoped>
.block .el-input__inner {
    border: 1px solid #bbb;
    border-radius: 2px;
    height: 30px;
    line-height: 30px;
}

.cancel {
    border: 1px solid #777 !important;
    color: #777 !important;
}
.green {
    border: 1px solid #0bbb5a !important;
    color: #0bbb5a !important;
}
.detailBtn {
    border: 1px solid #579ae8 !important;
    margin-bottom: 0 !important;
    color: #579ae8 !important;
}
* {
    box-sizing: border-box;
}
.pj-info {
    text-align: left;
    overflow: hidden;
}
.pj-info i {
    padding: 7px;
    vertical-align: middle;
    margin-right: 5px;
    font-size: 0;
    cursor: pointer;
    background: transparent url("../../assets/images/show/icon_star.jpg")
        no-repeat left center;
}
.pj-info i.full {
    background-image: url("../../assets/images/show/icon_star_full.jpg");
}
.pj-info textarea {
    border: 1px solid #eee;
    width: 100%;
    padding: 10px;
    box-sizing: border-box;
}
.pj-info span.tips {
    display: block;
    text-align: right;
    color: #666;
    font-size: 12px;
}
.pj-info span.tips b {
    color: red;
}
table {
    width: 100%;
}
table thead th {
    font-size: 14px;
    background-color: #f7f8fb;
}
table tr {
    height: 2.4em;
}
table tbody tr:last-of-type {
    border-bottom: none;
}
table tr:last-of-type td {
}
.tabsPanel .tabs-header .list a {
    padding: 0px 30px;
    border: solid 1px transparent;
}

.button {
    display: inline-block;
    color: #fff;
    background-color: #ff7200;
    border: 1px solid #ff7200;
    border-radius: 2px;
    cursor: pointer;
}
.button.disabled {
    cursor: default;
    background-color: #ddd;
    border: 1px solid #ddd;
}
.button-white.button {
    background-color: #fff;
    color: #ff7200;
}
.td-handle {
    margin: 0 auto;
    font-size: 14px;
    text-align: center;
}
.td-handle a {
    display: inline-block;
    color: #ff7200;
    font-size: 12px;
    margin-top: 4px;
    margin-left: 10px;
}
.td-handle a.order-cancel:not(.button) {
    color: #666;
}
.td-handle a.t-href {
    color: #666;
    font-size: 12px;
}
.td-handle a.button {
    margin-bottom: 5px;
    padding: 0 5px;
    font-size: 13px;
}
.order-main-title {
    margin-bottom: 15px;
    font-size: 16px;
    line-height: 2.8em;
    font-weight: normal;
    border-bottom: 1px solid #e2e3e8;
}
.order-page {
    padding: 20px;

    min-height: 700px;
    background-color: #fff;
    box-shadow: 0 4px 6px 0 rgba(113, 114, 119, 0.1);
}
.order-page .no-pay-tips {
    margin-top: -10px;
    margin-bottom: 15px;
    font-size: 12px;
    color: #999;
}
.order-page .no-pay-tips .icon {
    margin-right: 2px;
    width: 12px;
    height: 12px;
}
.order-page .no-pay-tips span {
    display: inline-block;
    vertical-align: middle;
}
.order-page .price-box {
    padding-right: 20px;
    text-align: right;
}
.order-page .price-box .button {
    margin-left: 20px;
    width: 120px;
    line-height: 32px;
    text-align: center;
}
.order-page-form ul li {
    float: left;
    margin-right: 10px;
    margin-bottom: 12px;
    height: 32px;
}
.order-page-form ul li label {
    display: inline-block;
    min-width: 80px;
    text-align: right;
}
.order-page-form ul li input.text {
    padding: 5px;
    height: 30px;
    border-radius: 2px;
    border: 1px solid #bbb;
}
.order-page-form ul li input.text.s-input {
    width: 150px;
}
.order-page-form ul li input.text.m-input {
    width: 230px;
}
.order-page-form ul li input.text.l-input {
    width: 300px;
}
.order-page-form ul li select {
    width: 160px;
    height: 30px;
    border-radius: 2px;
    border: 1px solid #bbb;
}
.order-page-form ul li .button {
    display: inline-block;
    padding: 0 20px;
    height: 30px;
}

/* start tabsPanel */
.tabsPanel .tabs-header {
    border-bottom: solid 2px #ff7200;
    margin-bottom: 10px;
}
.tabsPanel .tabs-header .list {
    float: left;
    margin-right: -1px;
}
.tabsPanel .tabs-header .list a {
    display: block;
    height: 34px;
    line-height: 35px;
    text-align: center;
    color: #333;
    border-width: 1px;
    border-color: #eee;
    border-style: solid;
    border-bottom-width: 0;
    cursor: pointer;
}
.tabsPanel .tabs-header .list a:hover {
    color: #fff;
    background-color: #ff7200;
    border-color: #ff7200;
}
.tabsPanel .tabs-header .list.selected a {
    color: #fff;
    background-color: #ff7200;
    border: solid 1px #ff7200;
}
.tabsPanel .tabs-bodyer .bodyer-panel {
    display: none;
}
.tabsPanel .tabs-bodyer .bodyer-panel.selected {
    display: block;
}
/* end tabsPanel */

/* 数据表格 */
table.myDataTable {
}
table.myDataTable caption {
    padding: 10px 0;
    position: relative;
}
table.myDataTable td {
    padding: 11px 5px;
    text-align: center;
    font-size: 12px;
}
table.myDataTable thead {
    border: solid 1px #e3e3e3;
}
table.myDataTable thead th {
    border: 0;
    background-color: #f0f0f0;
    color: #666;
}
table.myDataTable thead td {
    border: 0;
    background-color: #f0f0f0;
    color: #666;
}
table.myDataTable thead td .icon-arrow {
    display: inline-block;
    width: 12px;
    height: 12px;
    cursor: pointer;
    margin: 0px 0 0 2px;
}
table.myDataTable thead td .down {
    background-position: 0px 4px;
}
table.myDataTable thead td .up {
    background-position: 0px -10px;
}
table.myDataTable tbody td {
}
table.myDataTable tbody td a.hover {
    color: #ff7200;
}
table.myDataTable tbody td a.hover:hover {
    color: #ff7200;
}
table.myDataTable tbody tr.odd {
    background-color: #f9f9f9;
}
table.myDataTable tbody tr.active {
    background-color: #f9f9f9;
}
table.myDataTable tbody .bgcolor {
    background-color: #f3f3f3;
}
table.tableNoborder tbody td {
    border: 0px;
    padding: 8px 5px;
}
table tbody tr.noborder td {
    border: 0;
    padding: 8px 5px;
}
table.myDataTable .align-left {
    text-align: left;
}
table.align-left tbody td {
    text-align: left;
}

table.myDataTable tbody td {
    line-height: 22px;
    word-break: break-all;
}
table.myDataTable tbody .td-selectSingle {
}
table.myDataTable tbody .td-selectSingle input {
    width: 15px;
    height: 15px;
    margin-left: 3px;
}
table.myDataTable thead {
    border: 0;
}
table.myDataTable td {
    font-size: 14px;
    border-left: 0;
    border-right: 0;
    border-bottom: solid 1px #e2e3e8;
}
/*下拉框*/
.m-select-box {
    display: inline-block;
    width: 120px;
    position: relative;
    border: 1px solid #bbb;
    border-radius: 2px;
}
.m-select-box .icon {
    display: inline-block;
    position: absolute;
    right: 7px;
    top: 12px;
    width: 8px;
    height: 6px;
    background: url(../../assets/images/center/icon-hdown.png) no-repeat center;
    background-size: cover;
    z-index: 5;
}
.m-select-box .text {
    width: calc(100% - 20px);
    border: none !important;
}
.m-select-box dl {
    display: none;
    position: absolute;
    left: 0px;
    width: 100%;
    max-height: 300px;
    overflow: auto;
    background-color: #fff;
    border: 1px solid #ddd;
    z-index: 9;
}
.m-select-box dl dd {
    padding: 5px;
    font-size: 12px;
    line-height: 2em;
    height: 3em;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    cursor: pointer;
}
.m-select-box dl dd:hover {
    color: #fff;
    background-color: #ff7200;
}
.layui-layer-title {
    position: relative;
    padding-left: 0px;
    padding-right: 0px;
    padding-top: 13px;
    height: 52px;
    line-height: 42px;
    border-bottom: none;
    font-size: 16px;
    text-align: center;
    font-weight: bold;
}
.layui-layer-title .file-upload-warning {
    position: absolute;
    top: 15px;
}
select {
    height: 30px;
}

.input {
    display: inline-block;
    *display: inline;
    *zoom: 1;
    width: 120px;
}

.noneOrder {
    text-align: center;
    color: #999;
}

.model {
    position: fixed;
    background: rgba(0, 0, 0, 0.6);
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    z-index: 100;
}
.model .content {
    width: 450px;
    margin: auto;
    top: 50%;
    transform: translateY(-50%);
    background: #fff;
    padding: 15px;
    border-radius: 5px;
    position: relative;
}
.model .content h1 {
    color: #f08b2f;
    text-align: center;
    font-size: 16px;
    margin-bottom: 10px;
}
.model .content > i {
    position: absolute;
    right: 15px;
    top: 15px;
    font-size: 20px;
    cursor: pointer;
}
.color {
    color: #f08b2f;
    cursor: pointer;
}
.model .content > p {
    line-height: 26px;
}
.model .content .color {
    cursor: text;
}

.model .content .btn {
    overflow: hidden;
    text-align: center;
    margin-top: 15px;
}

.model .content .btn a {
    display: inline-block;
    *display: inline;
    *zoom: 1;
    width: 120px;
    height: 34px;
    line-height: 32px;
    text-align: center;
    border: 1px solid #f08b2f;
    color: #f08b2f;
    margin: 0 10px;
}
.model .content .btn a.active {
    background: #f08b2f;
    color: #fff;
}
/* //商标订单列表 */
table.brandListTable.myDataTable td {
    padding: 11px 5px;
    text-align: left;
    font-size: 14px;
    color: #666;
    /* border: solid 1px #e2e3e8; */
}
table.brandListTable.myDataTable td:first-child {
    border-left: solid 1px #e2e3e8;
}
table.brandListTable.myDataTable td:last-child {
    border-right: solid 1px #e2e3e8;
}
.tdTitle td {
    background: #f9f9f9;
}
.tdTitle td span {
    margin-right: 20px;
    color: #999;
}
.tdTitle td span:first-child em {
    color: #f08b2f;
}
/* //显示小类 */
.moBox {
    position: relative;
}
.moBox p {
    display: inline-block;
    cursor: pointer;
    margin-top: 5px;
    padding: 2px 5px;
    background: #eaeaea;
    border-radius: 2px;
    padding-right: 28px;
    background: url("../../assets/images/icon_search.png") 96% center no-repeat;
    background-size: 16px auto;
    font-size: 12px;
    border: 1px solid #eaeaea;
}
.moBox:hover p {
    color: #f08b2f;
    border: 1px solid #f08b2f;
}
.moBox:hover .faBox {
    display: block;
}
.faBox {
    display: none;
    position: absolute;
    z-index: 10;
    background: #fff;
    left: 0;
    top: 32px;
    width: 200px;
    padding: 0 10px;
    border: 1px solid #f08b2f;
    /* border-top: none; */
}
.faBox::before {
    content: " ";
    position: absolute;
    width: 105px;
    background: #fff;
    top: -2px;
    height: 4px;
    left: 0;
    z-index: 12;
}
.faBox span {
    display: block;
    height: 28px;
    line-height: 28px;
    font-size: 12px;
}
</style>
