<template>
    <div class="content">
        <div class="home-page userInfo">
            <!-- start article1 -->
            <div class="article1 home-page-top clearfix">
                <!-- start article1-left  -->
                <dl class="article-left clearfix">
                    <dt>
                        <div class="pic">
                            <img width="100%" height="100%" class="zhanshipic" src="../../assets/images/bg-6.png" onerror="this.src='../../assets/images/bg-6.png'" alt="用户头像" title="用户头像">
                        </div>
                    </dt>
                    <dd>
                        <div class="row-1 clearfix">
                            <span class="s1">Hi,
                                <i class="color">
                                    {{userInfo.nickname?userInfo.nickname:userInfo.phoneNo}}</i>
                            </span>

                        </div>
                        <div class="row-2 songti">
                            <a class="safe-level">
                                <span class="s1">安全级别：</span>
                                <span class="s2" v-if="safeInfo>=3">
                                    <i class="bgcolor" :style="'width:'+ (safeInfo/5)*100 +'%;background-color:#FF9900'"></i>
                                </span>
                                <span class="s2" v-if="safeInfo<3">
                                    <i class="bgcolor" :style="'width:'+ (safeInfo/5)*100 +'%'"></i>
                                </span>
                                <span class="s3">
                                    <i class="icon icon-di"></i>
                                    <span v-if="safeInfo<3">较低</span>
                                    <span v-if="safeInfo==3">中</span>
                                    <span v-if="safeInfo==4">一般</span>
                                    <span v-if="safeInfo==5">高</span>
                                </span>
                            </a>
                            <a class="safe-level-complete" href="/center/account" target="_blank">完善资料&gt;</a>
                        </div>
                    </dd>
                </dl>
                <!-- end article1-left  -->
                <!-- start article1-right -->
                <ul class="article-right">
                    <li>
                        <a href="/shangbiao/freesearch">
                            <em class="icon icon-tool-ss"></em>
                            <span class="s1">商标搜索</span>
                        </a>
                    </li>
                    <li v-if="0">
                        <a href="#">
                            <em class="icon icon-tool-jk"></em>
                            <span class="s1">商标监控</span>
                        </a>
                    </li>
                    <li>
                        <a href="/brandmanage">
                            <em class="icon icon-tool-gl"></em>
                            <span class="s1">商标管理</span>
                        </a>
                    </li>
                </ul>
                <!-- end article1-right -->
            </div>
            <!-- end article1 -->
            <!-- start page  -->
            <div class="home-page-bottom clearfix">
                <!-- start 左  -->
                <div class="page-left">
                    <!-- start article2 -->
                    <div class="article2">
                        <div class="caption">
                            <span class="s1">最近订单</span>
                        </div>
                        <p class="noneOrder" v-if="orderList && !orderList.dateMap.length">暂无订单</p>
                        <table width="100%" class="myDataTable" v-if="orderList && orderList.dateMap.length">
                            <thead>
                                <tr>
                                    <th>订单编号</th>
                                    <th>业务名称</th>
                                    <th>申请人</th>
                                    <th>订单金额</th>
                                    <th width="100px">下单时间</th>
                                    <th>
                                        订单状态
                                    </th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="item in orderList.dateMap" :key="item.id">
                                    <td>
                                        <span class="order-no">{{item.orderNo}}</span>
                                    </td>
                                    <td>
                                        <div class="order-product-name">
                                            <p>{{item.serviceName}}</p>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="td-applicantName">
                                            {{item.contactName}} </div>
                                    </td>
                                    <td>
                                        <div class="td-total-price">
                                            <span>总额：￥{{item.allPrice}}</span>
                                        </div>
                                    </td>
                                    <td width="160">
                                        <div class="td-date">{{item.createTime}}</div>
                                    </td>
                                    <td>
                                        <div class="order-status">
                                            <div class="td-process">
                                                <span class="td-process-status">
                                                    {{item.status}} </span>
                                                <div class="td-process-detail mt5">
                                                    <a v-if="item.serviceName == '智能自助注册' || item.status == '已评价' " :href="'/center/orderDetail?id='+item.id" class="mt5 col f12">
                                                        查看详情
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="td-handle">
                                            <a v-if="item.status == '未付款'" :href="'/order/pay/'+item.orderNo" class="button button-white pay">
                                                立即付款 </a>
                                            <a v-if="item.status == '待提交'" @click="submitOrder(item.id)" class="button button-white pay">
                                                立即提交 </a>
                                            <a v-if="item.status == '未付款'" href="javascript:void(0)" @click="cancelOrder(item.id)" class="order-cancel button button-white cancel">取消订单</a>
                                            <a v-if="item.status == '已完成'" href="javascript:void(0)" @click="evalOrder(item.id)" class="order-cancel button button-white">评价订单</a>
                                        </div>
                                    </td>
                                </tr>

                            </tbody>
                        </table>
                    </div>
                    <!-- end article2 -->
                    <!--广告 -->
                </div>
            </div>
            <!-- end page  -->
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
    </div>
</template>
<script>
import popup from "~/components/popup"; //消息
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
    data() {
        return {
            userInfo: "",
            safeInfo: 0,
            orderList: "",
            isEavl: false,
            level: 4,
            evalContent: ""
        };
    },
    layout: "center",
    mounted() {
        this.loadUserInfo();
        this.loadSafeInfo();
        this.loadOrder(true);
    },
    components: {},
    methods: {
        loadUserInfo() {
            this.$Http(
                "/persional/Info",
                { userId: this.$store.state.userInfo.userId },
                "get",
                true
            ).then(res => {
                this.userInfo = res.data.data;
            });
        },
        loadSafeInfo() {
            this.$Http(
                "/persional/security",
                { userId: this.$store.state.userInfo.userId },
                "get",
                true
            ).then(res => {
                let obj = res.data.data;
                for (let k in obj) {
                    if (obj[k]) {
                        this.safeInfo++;
                    }
                }
            });
        },
        loadOrder(isBool) {
            this.$Http(
                "/order/queryUserOrder",
                {
                    userId: this.$store.state.userInfo.userId,
                    pageIndex: 1,
                    pageNum: 5
                },
                "get",
                isBool
            ).then(res => {
                this.orderList = res.data.data;
            });
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
        submitOrder(orederNum) {
            this.$Http(
                "order/changeStatus",
                {
                    userId: this.$store.state.userInfo.userId,
                    orderId: orederNum
                },
                "get"
            ).then(res => {
                popup.created({
                    content: "提交成功",
                    time: 2000
                });
                this.loadOrder();
            });
        },
        //评价
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
        }
    }
};
</script>
<style scoped>
.cancel {
    border: 0 !important;
    margin-top: 8px;
    margin-bottom: 0 !important;
    color: #333 !important;
}
.noneOrder {
    text-align: center;
    color: #999;
}
.home-page-top {
    margin-bottom: 20px;
    padding: 20px;
    background-color: #fff;
    box-shadow: 0 4px 6px 0 rgba(113, 114, 119, 0.1);
}

.home-page-bottom {
    padding: 20px;
    background-color: #fff;
    box-shadow: 0 4px 6px 0 rgba(113, 114, 119, 0.1);
}

.home-page .page-left {
    float: left;
    width: 100%;
}

.icon-tool-ss {
    margin-top: 24px;
    width: 40px;
    height: 40px;
    background: url(../../assets/images/center/icon-ss.png) no-repeat center;
    background-size: cover;
}

.icon-tool-jk {
    margin-top: 24px;
    width: 50px;
    height: 40px;
    background: url(../../assets/images/center/icon-jk.png) no-repeat center;
    background-size: cover;
}

.icon-tool-gl {
    margin-top: 22px;
    width: 46px;
    height: 46px;
    background: url(../../assets/images/center/icon-gl.png) no-repeat center;
    background-size: cover;
}

/* 头像、安全等级 */
.home-page .article1 .article-left {
    float: left;
}

.home-page .article1 .article-left dt {
    float: left;
}

.home-page .article1 .article-left dt .pic {
    display: block;
    margin-top: -40px;
    padding: 8px;
    width: 140px;
    height: 140px;
    overflow: hidden;
    position: relative;
    background-color: #fff;
    box-shadow: 0 2px 4px 0 rgba(113, 114, 119, 0.17);
}

.home-page .article1 .article-left dt .pic img {
    border: 1px solid #eee;
}

.home-page .article1 .article-left dt .pic .uploadPic-btn {
    display: none;
    cursor: pointer;
    width: 100%;
    height: 50px;
    line-height: 40px;
    background-color: #000;
    color: #fff;
    opacity: 0.6;
    filter: alpha(opacity=60);
    text-align: center;
    position: absolute;
    bottom: 0;
    left: 0;
    font-size: 12px;
}

.home-page .article1 .article-left dt .pic:hover .uploadPic-btn {
    display: block;
}

.home-page .article1 .article-left dd {
    float: left;
    margin-left: 20px;
}

.home-page .article1 .article-left dd .row-1 {
    margin: 12px 0px 0px;
}

.home-page .article1 .article-left dd .row-1 .s1 {
    display: block;
    float: left;
    font-size: 18px;
    color: #999;
}

.home-page .article1 .article-left dd .row-1 .s2 {
    position: relative;
    display: block;
    float: left;
}

.home-page .article1 .article-left dd .row-1 .icon-erweima03 {
    float: left;
    margin: 0px 0 0 58px;
    font-size: 21px;
    color: #777;
    cursor: pointer;
}

.home-page .article1 .article-left dd .row-1 .icon {
    float: left;
    width: 33px;
    height: 13px;
    margin: 6px 0 0 9px;
}

.home-page .article1 .article-left dd .row-1 .icon-usergrade {
    width: 56px;
    height: 17px;
    margin-top: 5px;
}

.home-page .article1 .article-left dd .row-2 {
    font-size: 12px;
    padding: 12px 0 16px;
    overflow: hidden;
}

.home-page .article1 .article-left dd .row-2 .s1 {
    font-size: 14px;
    color: #666;
}

.home-page .article1 .article-left dd .row-2 .s2 {
    width: 100px;
    height: 12px;
    background-color: #ddd;
}

.home-page .article1 .article-left dd .row-2 .s2 .bgcolor {
    display: block;
    height: 100%;
    background-color: #ff0000;
}

.home-page .article1 .article-left dd .row-3 {
}

.home-page .article1 .article-left dd .row-3 .s1 {
    display: block;
    float: left;
    font-size: 12px;
}

.home-page .article1 .article-left dd .row-3 .s2 {
    display: block;
    float: left;
    font-size: 17px;
    color: #ff7200;
    line-height: 16px;
}

/* article1-right */
.home-page .article1 .article-right {
    float: right;
}

.home-page .article1 .article-right li {
    float: left;
    margin: 5px 0px 0px 25px;
    width: 100px;
    height: 100px;
    border: solid 1px #e9e9e9;
    text-align: center;
    position: relative;
}

.home-page .article1 .article-right li a {
    display: block;
    height: 100%;
}

.home-page .article1 .article-right li a .icon {
    display: inline-block;
}

.home-page .article1 .article-right li a .s1 {
    display: block;
    color: #555;
}

.home-page .article1 .article-right li a .icon-number {
    display: block;
    margin: 0px;
    width: 22px;
    height: 22px;
    line-height: 22px;
    position: absolute;
    top: -11px;
    right: -11px;
    background-color: #f86b4f;
    color: #fff;
    font-size: 12px;
    border-radius: 50%;
}

/* 分享二维码弹窗 */
.erweimaModal {
    display: none;
    width: 280px;
    position: absolute;
    top: 37px;
    left: 50%;
    margin-left: -140px;
    z-index: 2;
    box-shadow: 0 0 3px 0 #aaa;
}

.erweimaModal .close {
    display: block;
    width: 20px;
    height: 20px;
    line-height: 20px;
    position: absolute;
    top: 5px;
    right: 5px;
    z-index: 1;
    color: #ff7200;
    font-style: normal;
    font-weight: normal;
    font-size: 24px;
    text-align: center;
    cursor: pointer;
    opacity: 1;
    text-shadow: none;
}

.erweimaModal .bodyer {
    padding: 26px 40px;
    background-color: #fff;
}

.erweimaModal .bodyer .tr-1 td {
    color: #666;
}

.erweimaModal .bodyer .tr-1 td img {
    width: 70px;
    height: 70px;
    border-radius: 2px;
    margin-right: 12px;
    display: inline-block;
    border: solid 1px #eee;
    vertical-align: middle;
}

.erweimaModal .bodyer .tr-2 td {
    text-align: center;
    color: #999;
    padding: 6px 0 15px;
}

.erweimaModal .bodyer .tr-2 td .iconfont {
    font-size: 15px;
    margin-right: 3px;
}

.erweimaModal .bodyer .tr-3 td .imgbox {
    display: inline-block;
    margin-top: 15px;
    width: 200px;
    height: 200px;
    position: relative;
    border: 1px solid #ddd;
}

.erweimaModal .bodyer .tr-3 td .imgbox .img1 {
    width: 100%;
}

.erweimaModal .bodyer .tr-3 td .imgbox .img2 {
    width: 70px;
    height: 70px;
    position: absolute;
    top: 50%;
    left: 50%;
    margin: -35px 0 0 -35px;
    border-radius: 50%;
}

.erweimaModal .bodyer .tr-4 {
    font-size: 12px;
    text-align: center;
}

.erweimaModal .bodyer .tr-4 td {
    color: #666;
    vertical-align: middle;
    padding-top: 10px;
}

.erweimaModal .bodyer .tr-4 td img {
    display: block;
    float: left;
    width: 50px;
    border-radius: 50%;
    margin-right: 10px;
    display: inline-block;
}

.erweimaModal .bodyer .tr-4 span {
    display: block;
    margin-top: 5px;
}

.erweimaModal .bodyer .tr-5 td {
    text-align: center;
    padding-top: 20px;
}

.erweimaModal .bodyer .tr-5 td .mybtn {
    border: solid 1px #ccc;
    color: #999;
    border-radius: 2px;
}

.erweimaModal .bodyer .tr-5 td .mybtn .iconfont {
    font-size: 18px;
    margin-right: 2px;
    vertical-align: -1px;
}

.erweimaModal .bodyer .down a {
}

.erweimaModal .bodyer .down a span {
    display: inline;
    font-size: 12px;
    color: #999;
    text-decoration: underline;
    vertical-align: top;
}

.erweimaModal .bodyer .down a:before {
}

.erweimaModal .icon-arrowup {
    display: block;
    position: absolute;
    top: -21px;
    left: 50%;
    margin-left: -5px;
    width: 0;
    height: 0;
    border: 10px solid transparent;
    border-bottom: 10px solid #ddd;
}

.erweimaModal .icon-arrowup b {
    display: block;
    position: absolute;
    bottom: -11px;
    left: -10px;
    width: 0;
    height: 0;
    border: 10px solid transparent;
    border-bottom: 10px solid #fff;
}

.s2.active .erweimaModal {
    display: block;
}

#personalCenter2-wrap
    .home-page
    .page-left
    .article1
    .article-left
    dd
    .row-1
    .s2.active
    .icon-erweima03 {
    color: #ff7200;
}

/* 我的订单 */
.home-page .page-left .article2 {
}

.home-page .page-left .article2 .btns .mybtn {
    padding: 0 8px;
}

.home-page .page-left .article2 .caption {
    margin-bottom: 12px;
    color: #333;
    text-align: left;
}

.home-page .page-left .article2 .caption .s1 {
    margin-left: 10px;
    font-size: 16px;
}

.home-page .page-left .article2 .caption .s2 {
    display: block;
    float: right;
    margin-top: 5px;
    font-size: 12px;
    text-decoration: underline;
}

.home-page .page-left .article2 .caption .s2:hover {
    color: #ff7200;
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

.btns .mybtn {
    font-size: 13px;
}

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

.td-handle {
    margin: 0 auto;
    width: 90px;
    font-size: 14px;
}

.td-handle a {
    display: inline-block;
    color: #ff7200;
    font-size: 12px;
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
    background: transparent;
}

.td-process-status {
    display: inline-block;
    margin-top: 5px;
    margin-bottom: 5px;
    font-size: 14px;
    color: #ff7200;
}

.td-process span {
}

.safe-level {
    overflow: hidden;
    display: inline-block;
    vertical-align: middle;
}

.safe-level-complete {
    display: inline-block;
    margin-left: 15px;
    color: #ff7200;
    vertical-align: middle;
}

.safe-level .s1 {
    display: inline-block;
    vertical-align: middle;
}

.safe-level .s2 {
    display: inline-block;
    vertical-align: middle;
}

.safe-level .s3 {
    display: inline-block;
    line-height: 12px;
    color: #ff0000;
    margin-left: 8px;
    vertical-align: middle;
}

.safe-level .s3 .icon {
    margin-right: 3px;
    vertical-align: middle;
}

.safe-level .s3 span {
    vertical-align: middle;
}

.color {
    color: #ff7200;
}
/* //评价 */
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
.block .el-input__inner {
    border: 1px solid #bbb;
    border-radius: 2px;
    height: 30px;
    line-height: 30px;
}
.cancel {
    border: 1px solid #777 !important;
    margin-top: 8px;
    margin-bottom: 0 !important;
    color: #777 !important;
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
</style>
