<template>
    <div class="smartRegister-page smartRegister3-page">
        <div class="paying-wrap">
            <!-- start 订单信息 -->
            <div class="section-orderInfo clearfix" v-if="orderInfo">
                <div class="order-info-box">
                    <img class="icon" src="../../../assets/images/order/icon-payform.png">
                    <div class="order-info-text">
                        <h2>
                            <span v-if="orderInfo.status == 'order_status_obligation'">订单提交成功，应付金额：</span>
                            <span v-if="orderInfo.status == 'order_status_spend' || orderInfo.status == 'order_status_inprocess' || orderInfo.status == 'order_status_completed' || orderInfo.status == 'order_status_evaluate'">支付成功！支付金额：</span>
                            <span v-if="orderInfo.status == 'order_status_not_audit'">订单提交成功，请等待审核，订单金额：</span>
                            <span v-if="orderInfo.status == 'order_status_waiting_submit'">订单保存成功，等待提交，订单金额：</span>
                            <span v-if="orderInfo.status == 'order_status_audit_faild'">订单审核失败，请修改后重新提交，订单金额：</span>
                            <strong class="row-sense">
                                <em>
                                    <i class="payable">￥{{orderInfo.allPrice}}</i>
                                </em>
                            </strong>
                            <span class="row-price">
                                (
                                <span class="row-officer">平台手续费：￥
                                    <i class="platformFee" id="webmoney">{{orderInfo.allServicePrice}}</i>
                                </span>，
                                <span class="row-officer">官费：￥
                                    <i class="discount">{{orderInfo.allOfficialPrice?orderInfo.allOfficialPrice:orderInfo.allTrademarkClassPrice}}</i>
                                </span>
                                <span class="row-officer" v-if="orderInfo.invoiceFee">，发票税费：￥
                                    <i class="discount">{{orderInfo.invoiceFee}}</i>
                                </span>
                                )
                            </span>
                        </h2>
                        <div class="order-info-detail">
                            <span>订单编号：{{orderInfo.orderNo}}</span>
                            <span>购买服务：
                                <i class="color">{{orderInfo.serviceName}}</i>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            <!-- start 选择支付方式 -->
            <div class="section-payMode" id="section-payMode" v-if="orderInfo.status == 'order_status_obligation'">
                <h3 class="cont-title clearfix">
                    <span class="s1">选择支付方式</span>
                    <span class="tips">付款账户如果是公司账户，则可以为付款的公司账户开具发票；如果是个人账户，则只能为付款人或申请人开具发票，请选择适合的付款账户</span>
                </h3>
                <div class="cont-bodyer clearfix" id="payways">
                    <ul class="item-payMode clearfix active">
                        <li class="list " :class="{'atthis': payType == 1}" @click="seletePay" data-payway="1">
                            <i class="icon icon-pay-weixn"></i>
                            <span class="text">微信支付</span>
                            <i class="icon-mianfei">限免</i>
                        </li>
                        <li class="list " :class="{'atthis': payType == 2}" @click="seletePay" data-payway="2">
                            <i class="icon icon-pay-zhifubao"></i>
                            <span class="text">支付宝支付</span>
                            <i class="icon-mianfei">限免</i>
                        </li>
                    </ul>
                </div>
            </div>
            <!-- end 选择支付方式 -->
            <div class="row-step main-button" v-if="orderInfo.status == 'order_status_obligation'">
                <a class="btn-next" href="javascript:;" @click="pay" id="alisubmit">确认支付</a>
            </div>
            <div class="row-step main-button" v-if="orderInfo && orderInfo.status != 'order_status_obligation'">
                <a class="btn-next" href="/center">前往订单中心</a>
            </div>
        </div>

        <div class="myModal payModal weixinPayModal show" id="weixinPayModal" v-if="wxPop">
            <i class="opacity-bg"></i>
            <div class="container">
                <a href="javascript:void(0)" @click="close" class="close">×</a>
                <div class="header">
                    <span class="title">
                        <i class="icon icon-pay-weixn"></i>微信支付</span>
                </div>
                <div class="bodyer clearfix">
                    <div class="wx-code-box">
                        <div class="wx-code" id="wxCode"><img :src="wximg"></div>
                        <div class="wx-code-tips">
                            <span>
                                打开手机微信扫描<br> 二维码以完成支付
                            </span>
                        </div>
                    </div>
                    <div class="wx-content">
                        <div class="price-box wx-price-box">
                            <p>扫一扫付款</p>
                            <span>
                                ￥
                                <i class="f-price wxPrice">{{orderInfo.allPrice}}</i>
                            </span>
                        </div>
                        <p class="wx-tips">如有问题请拨打热线400-871-9995</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="myModal payModal zhifubaoPayModal show" id="zhifubaoPayModal" v-if="aliPayPop">
            <i class="opacity-bg"></i>
            <div class="container">
                <i class="close" @click="close">×</i>
                <div class="header">
                    <span class="title">支付宝支付</span>
                </div>
                <div class="bodyer clearfix">
                    <p class="p1">
                        <img class="loading" src="../../../assets/images/order/loading.gif"> 正在支付中...
                    </p>
                    <p class="p2">支付完成前，请不要关闭此支付验证窗口。</p>
                    <div class="btns">
                        <a class="btn btn-next" href="/center">完成支付</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="alipay" ref="alipayHtml">

        </div>
        <to-top />
    </div>
</template>

<script>
import toTop from "@/components/toTop";
import axios from "axios";
import popup from "~/components/popup"; //消息

export default {
    middleware: "auth",
    head() {
        return {
            title: `${this.orderInfo.serviceName || ""}-知千秋`,
            meta: [
                {
                    name: "keywords ",
                    hid: "keywords ",
                    content:
                        "注册商标查询，中国商标官网查询，商标搜索，商标检索,知千秋,权大师 "
                },
                {
                    name: "description ",
                    hid: "description ",
                    content:
                        "免费精准的商标查询平台,中国更全的商标信息库,精准智能的商标相似查询结果,为商标申请人降低商标驳回风险,更快,更准的定位商标注册成功概率。知千秋 "
                }
            ]
        };
    },
    data() {
        return {
            orderInfo: "",
            payType: 1,
            aliPayPop: false,
            wxPop: false,
            wximg: "",
            alipayHtml: "",
            t: ""
        };
    },
    mounted() {
        if (!this.$route.params.orderNumber) {
            this.$router.replace("/404");
            return;
        }
        this.loadInfo();
    },
    methods: {
        async loadInfo() {
            let { data } = await this.$Http(
                `/order/queryUserOrderByNo?orderNo=${
                    this.$route.params.orderNumber
                }&userId=${this.$store.state.userInfo.userId}`,
                {},
                "GET"
            );
            this.orderInfo = data.data;
        },
        seletePay(e) {
            this.payType = e.currentTarget.dataset.payway;
        },
        close() {
            this.wxPop = false;
            this.aliPayPop = false;
        },
        polling() {
            this.t = setTimeout(() => {
                this.status();
            }, 2000);
        },
        status() {
            sessionStorage.setItem("isFrom", "");
            this.$Http(
                `/order/queryUserOrderPayStatus`,
                {
                    userId: this.$store.state.userInfo.userId,
                    orderId: this.orderInfo.id
                },
                "get"
            ).then(res => {
                if (res.data.data.payStatus == "pay_status_03") {
                    this.polling();
                } else if (res.data.data.payStatus == "pay_status_01") {
                    console.log(res.data.data.payStatus);
                    this.close();
                    this.loadInfo();
                    // this.$set(this.orderInfo, "Status", res.data.data.status);
                } else {
                    popup.created({
                        content: "支付失败，请刷新页面重试",
                        time: "3000"
                    });
                }
            });
        },
        pay() {
            let type = "wechat";
            if (this.payType != 1) {
                type = "alipay";
            }
            let loading = popup.created({
                type: "loading",
                icon: "&#xe61c",
                content: "支付中..."
            });
            sessionStorage.setItem("isFrom", true);
            this.$Http(
                `/payment/create`,
                {
                    payType: type,
                    businessNo: this.orderInfo.orderNo,
                    businessType: this.orderInfo.serviceTypeLevel1Code,
                    sign: this.orderInfo.sign,
                    businessBody: this.orderInfo.serviceName,
                    fee: this.orderInfo.allPrice
                },
                "post"
            ).then(res => {
                loading.close();
                if (this.payType == 1) {
                    // 轮询查看状态
                    this.polling();
                    this.wxPop = true;
                    this.wximg = res.data.data.qrcode;
                } else {
                    this.$refs.alipayHtml.innerHTML = res.data.data.result;
                    var regGetJS = /<script(.|\n)*?>((.|\n|\r\n)*)?<\/script>/im;
                    var jsSection = res.data.data.result.match(regGetJS);
                    this.aliPayPop = true;
                    eval(jsSection[2]);
                }
            });
        }
    },
    components: {
        toTop
    }
};
</script>
<style scoped>
.smartRegister-page {
    min-height: 600px;
}

/* 自定义模态窗 */
.myModal {
    display: none;
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
    z-index: 999999;
    font-size: 14px;
    font-family: "Microsoft Yahei", "微软雅黑";
}
.myModal.show {
    display: block;
}
.myModal .close {
    display: block;
    width: 20px;
    height: 20px;
    line-height: 20px;
    position: absolute;
    top: 10px;
    right: 10px;
    z-index: 1;
    color: #eee;
    font-style: normal;
    font-weight: normal;
    font-size: 24px;
    text-align: center;
    cursor: pointer;
    opacity: 1;
    text-shadow: none;
}
.myModal .opacity-bg {
    display: block;
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    background-color: #000;
    opacity: 0.5;
    filter: alpha(opacity=50);
    z-index: 0;
}
.myModal .container {
    width: 500px;
    position: absolute;
    top: 20%;
    left: 50%;
    margin-left: -175px;
    z-index: 1;
    padding: 0px;
    background-color: #f08b2f;
}
.myModal .header {
    color: #fff;
    padding: 9px 10px 12px;
    font-size: 15px;
}
.myModal .bodyer {
    padding: 15px 10px;
    font-size: 15px;
    background-color: #fff;
}
.myModal .footer {
    padding: 10px;
    padding-top: 20px;
    text-align: center;
    background-color: #fff;
}
.myModal .footer .btn {
    display: inline-block;
    text-align: center;
    line-height: 32px;
    padding: 0px 25px;
    background-color: #f08b30;
    color: #fff;
    cursor: pointer;
    font-size: 12px;
    margin: 0 8px;
    border: 0;
    border-radius: 1px;
}
.myModal .footer .btn-cancel {
    background-color: #ccc;
    color: #000;
    margin-right: 0;
}
.myModal .footer .btn .glyphicon {
    margin-right: 5px;
}

body {
    background: #f5f5f5;
}
* {
    box-sizing: border-box;
}
/*批量支付*/
.paying-wrap {
    margin: 40px auto;
    padding: 20px 100px;
    padding-top: 60px;
    width: 1200px;
    background-color: #fff;
    box-shadow: 0 4px 6px 0 rgba(113, 114, 119, 0.1);
}
.paying-wrap .section-orderInfo {
    margin-bottom: 20px;
}
.product-type-btn {
    display: inline-block;
    float: left;
    position: relative;
    margin-left: 15px;
    width: 90px;
    line-height: 32px;
    font-weight: bold;
    text-align: left;
    cursor: pointer;
}
.product-type-btn.active:after {
    top: 7px;
    border: 6px solid transparent;
    border-bottom-color: #f08b2f;
}
.product-type-btn:after {
    content: "";
    display: block;
    position: absolute;
    top: 12px;
    right: 10px;
    width: 0px;
    height: 0px;
    border: 6px solid transparent;
    border-top-color: #f08b2f;
}
.pay-order-list.section-orderInfo {
    margin: 0 auto;
    margin-bottom: 30px;
}
.pay-order-list table {
}
table.myDataTable .pl-total-price td {
    border-bottom: none;
}
.myDataTable span {
    display: inline-block;
}
.pl-order-list {
    max-height: 200px;
    overflow: auto;
}
/*支付页面*/
.payModal.myModal .header {
    padding-top: 10px;
    text-align: center;
    background-color: #fff;
}
.payModal.myModal .header .title {
    display: inline-block;
    margin-top: 8px;
    font-weight: bold;
    color: #333;
}
.payModal.myModal .header .icon {
    display: inline-block;
    margin-right: 5px;
    width: 30px;
    height: 21px;
    vertical-align: middle;
}
.payModal.myModal .bodyer {
    padding: 20px;
    text-align: center;
    box-sizing: border-box;
}
.payModal.myModal .wx-code-box {
    margin: 0 auto;
    padding: 10px;
    width: 194px;
    border: 1px solid #d8d8d8;
}
.payModal.myModal .wx-code {
    display: inline-block;
    width: 100%;
}
.payModal.myModal .wx-code img {
    width: 100%;
}
.wx-code-tips {
    margin-top: 10px;
}
.wx-code-tips span {
    display: inline-block;
    padding-left: 33px;
    font-size: 12px;
    color: #888;
    background: url(../../../assets/images/order/icon-scan.png) no-repeat 0
        center;
    background-size: 26px;
}
.payModal.myModal .price-box p {
    margin-top: 24px;
    margin-bottom: 8px;
    font-size: 12px;
    color: #888;
}
.payModal.myModal .price-box span {
    color: #333;
    font-size: 14px;
}
.payModal.myModal .price-box .f-price {
    font-size: 24px;
    height: 1.1em;
    line-height: 1.1em;
    vertical-align: top;
}
.payModal.myModal .btns {
    margin-top: 18px;
}
.payModal.myModal .btns .btn {
    padding: 0;
    width: 120px;
    line-height: 2.4em;
    font-size: 14px;
    height: auto;
}
.payModal.myModal .btns .btn:first-of-type {
}
.payModal.myModal .close {
    color: #aaa;
}
.payModal.myModal .wx-tips {
    margin-top: 24px;
    padding-top: 15px;
    font-size: 12px;
    color: #aaa;
    border-top: 1px solid #d8d8d8;
}
.payModal.myModal .loading {
    width: 16px;
}
.payModal.myModal .account-info {
    margin: 0 auto;
    width: 70%;
    text-align: left;
}
.payModal.myModal .account-info strong {
    display: inline-block;
    width: 60px;
    font-weight: bold;
    color: #333;
}
.payModal.myModal .account-info p {
    margin-bottom: 12px;
    font-size: 14px;
    color: #555;
}
.payModal.myModal .xx-price-box {
    padding-bottom: 24px;
    border-bottom: 1px solid #d8d8d8;
}
.payModal.myModal .xx-text {
    display: inline-block;
    margin-top: 10px;
    padding-left: 20px;
    text-align: left;
    font-size: 14px;
    color: #aaa;
    background: url(../../../assets/images/order/icon-warning-f.png) no-repeat 0
        3px;
    background-size: 15px;
}
.payModal.myModal .xx-text .color {
    font-weight: bold;
    color: #777;
}
.paying-wrap .order-info-box {
    float: left;
    padding-left: 30px;
    margin-bottom: 30px;
}
.paying-wrap .order-info-box .icon {
    display: inline-block;
    margin-right: 20px;
    width: 60px;
    vertical-align: middle;
}
.paying-wrap .order-info-box .order-info-text {
    display: inline-block;
    vertical-align: middle;
}
.paying-wrap .order-info-box .row-price {
    margin-left: 15px;
    font-size: 14px;
    color: #555;
}
.paying-wrap .order-info-box .order-info-text h2 {
    font-weight: normal;
    font-size: 18px;
}
.paying-wrap .order-info-box .order-info-text h2 strong {
    vertical-align: middle;
}
.paying-wrap .order-info-box .order-info-text h2 strong i {
    font-size: 24px;
    font-weight: bold;
    color: #f08b2f;
}
.paying-wrap .order-info-price {
    float: right;
}
.paying-wrap .order-info-detail {
    margin-top: 15px;
    color: #888;
}
.paying-wrap .order-info-detail span {
    display: inline-block;
    margin-right: 25px;
    vertical-align: middle;
}
.paying-wrap .order-info-detail span.order-server-name {
    width: 215px;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
}

/* 选择支付方式 */
.paying-wrap .section-payMode {
}
.paying-wrap .section-payMode .cont-title {
    font-weight: normal;
    margin-bottom: 20px;
    font-size: 14px;
}
.paying-wrap .section-payMode .cont-title .icon-leftBorder {
    display: block;
    float: left;
    width: 4px;
    height: 16px;
    background-color: #f08b2f;
    border-radius: 1px;
    margin: 3px 8px 0 0;
}
.paying-wrap .section-payMode .cont-title .s1 {
    display: block;
    float: left;
    padding-left: 10px;
    color: #555;
    font-size: 16px;
    height: 1.1em;
    line-height: 1.1em;
    border-left: 3px solid #f08c2f;
}

.paying-wrap .section-payMode .cont-title .tips {
    display: inline-block;
    font-size: 12px;
    color: #999;
    margin: 2px 0 0 20px;
}
.paying-wrap .section-payMode .cont-title .tips .icon {
    display: inline-block;
    font-size: 12px;
    margin-right: 4px;
    width: 13px;
    height: 13px;
    line-height: 11px;
    border-radius: 50%;
    color: #ff3600;
    border: solid 1px #ff3600;
    text-align: center;
}

.paying-wrap .section-payMode .cont-bodyer {
}
.paying-wrap .section-payMode .item-payMode {
    position: relative;
    margin-bottom: 15px;
    width: 670px;
    height: 55px;
}
.paying-wrap .section-payMode .item-payMode .cont-title {
    margin-top: 15px;
}
.paying-wrap .section-payMode .item-payMode .di-payform {
    display: none;
}
.paying-wrap .section-payMode .item-payMode.active {
    width: 100%;
}
.paying-wrap .section-payMode .item-payMode.active .di-payform {
    display: block;
}
.paying-wrap .section-payMode .item-payMode.active .pay-more {
    display: none;
}
.hhr-pay-tips {
    margin-top: 5px;
}
.hhr-pay-tips .tips {
    padding-left: 15px;
    font-size: 12px;
    color: #999;
    background: url(../../../assets/images/order/icon-warning-r.png) no-repeat 0
        center;
    background-size: 13px;
}
.paying-wrap .section-payMode .item-payMode .pay-more {
    position: absolute;
    right: 0px;
    top: 19px;
    font-size: 12px;
    color: #666;
    cursor: pointer;
}
.paying-wrap .section-payMode .cont-bodyer .list {
    float: left;
    width: 176px;
    height: 55px;
    margin-right: 20px;
    padding: 15px;
    padding-right: 0px;
    color: #999;
    border-radius: 2px;
    text-align: left;
    padding-top: 16px;
    cursor: pointer;
    position: relative;
    border: 1px solid #e3e3e3;
}
.paying-wrap .section-payMode .cont-bodyer .list:last-of-type {
    margin-right: 0px;
}
.paying-wrap .section-payMode .cont-bodyer .list .icon {
    display: inline-block;
    width: 30px;
    height: 20px;
    vertical-align: top;
}
.paying-wrap .section-payMode .cont-bodyer .list .text {
    display: inline-block;
    color: #666;
    margin-left: 3px;
    font-size: 15px;
    vertical-align: middle;
    line-height: 1em;
}
.paying-wrap .section-payMode .cont-bodyer .list .icon-mianfei {
    display: inline-block;
    font-size: 11px;
    background-color: #fc461e;
    color: #fff;
    height: 16px;
    line-height: 15px;
    padding: 0px 3px;
    border-radius: 2px;
    vertical-align: middle;
}
.paying-wrap .section-payMode .cont-bodyer .list .tips {
    display: none;
    position: absolute;
    top: 24px;
    left: -18px;
    border: solid 1px #ffce9d;
    background-color: #fffae9;
    border-radius: 1px;
    font-size: 12px;
    color: #666;
    text-align: left;
    padding: 10px 10px;
    white-space: nowrap;
}
.paying-wrap .section-payMode .cont-bodyer .list .tips .icon-hollow-uparrow {
    display: block;
    width: 8px;
    height: 6px;
    position: absolute;
    top: -6px;
    left: 20px;
    background: url("../../../assets/images/order/icon-sprite-sm.png") 0px -44px
        no-repeat;
}
.paying-wrap .section-payMode .cont-bodyer .list .icon.icon-qm {
    position: relative;
    margin-top: 3px;
    width: 16px;
    height: 16px;
}
.paying-wrap .section-payMode .cont-bodyer .list .icon-radio {
    margin-right: 5px;
    font-size: 13px;
}
.paying-wrap .section-payMode .cont-bodyer .list .icon-radio:before {
    content: "\e641";
}
.paying-wrap .section-payMode .cont-bodyer .list.active .icon-radio:before {
    content: "\e65c";
    color: #f08c2f;
}
.paying-wrap .section-payMode .cont-bodyer .list.atthis {
    border: 1px solid #f08b2f;
}
.paying-wrap .section-payMode .cont-bodyer .list.active .tips {
    display: block;
}
.paying-wrap .section-payMode .cont-bodyer .list:hover {
}
.paying-wrap .section-payMode .cont-bodyer .list .icon-qm:hover .tips {
    display: block;
}
.paying-wrap .main-button {
    margin-top: 80px;
    margin-bottom: 50px;
}
.paying-wrap .main-button {
    text-align: center;
}
/*支付宝支付弹窗*/
#zhifubaoPayModal .container {
    width: 350px;
    color: #777;
}
#zhifubaoPayModal .p1 {
    font-size: 20px;
    padding-bottom: 10px;
}
#zhifubaoPayModal .p2 {
    font-size: 14px;
}
#zhifubaoPayModal .p-btn {
    margin-top: 18px;
}
/*微信支付弹窗*/
#weixinPayModal .container {
    width: 512px;
}
#weixinPayModal .e-left {
    display: inline-block;
}
#weixinPayModal .e-right {
    color: #777;
}
#weixinPayModal .e-right .p2 {
    margin: 10px 0;
}
#weixinPayModal .e-right .p2 .color {
    display: inline-block;
    padding: 0 4px;
    font-size: 18px;
}
#weixinPayModal .e-right .p3 {
    font-size: 12px;
    color: #999;
    margin-top: 10px;
}
#weixinPayModal .e-right .p-btn {
    margin-top: 20px;
}
#weixinPayModal .e-right .p-btn a {
    margin-right: 20px;
    line-height: 28px;
    padding: 0 15px;
    font-size: 12px;
}
.btn-prev {
    display: inline-block;
    margin-right: 30px;
    line-height: 36px;
    padding: 0 42px;
    border: solid 1px #ff7200;
    color: #ff7200;
    border-radius: 2px;
}
.btn-next {
    display: inline-block;
    line-height: 36px;
    padding: 0 42px;
    background-color: #ff7200;
    color: #fff;
    border-radius: 2px;
}
.btn-next:hover {
    color: #fff;
}
.btn-next.disabled {
    opacity: 0.2;
    cursor: default;
}

.icon-pay-weixn {
    background-image: url(../../../assets/images/order/icon-sprite-sm.png);
    background-position: -23px -23px;
}
.icon-pay-zhifubao {
    background-image: url(../../../assets/images/order/icon-sprite-sm.png);
    background-position: -52px -24px;
}
</style>
