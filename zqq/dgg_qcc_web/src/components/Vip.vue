<template>
    <div class="search_body vipBox" id="vipBox">
        <transition name="el-fade-in">
            <div class="modal-dialog nmodal" @click.stop="stopEvent">
                <div class="modal-content">
                    <div class="vip-top">
                        <button type="button" class="close" data-dismiss="modal">
                            <span aria-hidden="true" @click="hiddenBOx">×</span>
                        </button>
                        <div class="vip-buy-panel">
                            <div class="vip-year-list clearfix">
                                <div data-id="17" class="vip-kuang vip-year ">
                                    <div class="price">
                                        <span>720元</span>
                                        <span style="color: #F9552A">/</span>
                                        3年
                                    </div>
                                    <div class="origin-price">原价：2160元</div>
                                    <!-- <div class="vip-rec"></div> -->
                                </div>
                                <div data-id="7" class="vip-kuang vip-year active">
                                    <div class="price">
                                        <span>免费</span>
                                        <!-- <span style="color: #F9552A">/</span>
                                        1年 -->
                                    </div>
                                    <div>
                                        <span style="color:#999">限时申请</span>
                                    </div>
                                </div>
                                <div data-id="6" class="vip-kuang vip-year">
                                    <div class="price">
                                        <span>360元</span>
                                        <span style="color: #F9552A">/</span>
                                        1年
                                    </div>
                                    <div class="origin-price">原价：720元</div>
                                </div>
                            </div>
                            <a @click="applyVip" class="vip-btn">立即开通</a>
                            <div class="vip-publicity">支付后可开发票</div>
                        </div>
                    </div>
                    <div class="vip-container">
                        <div class="table-caption"> <img style="width: 51px;" src="@/assets/images/vip_tq@2x.png">
                            <span>查企业、查资质....</span>
                            <!-- <a href="/vip" class="pull-right">查看全部&gt;</a> -->
                        </div>
                        <table class="vip-table table table-striped">
                            <tbody>
                                <tr>
                                    <td width="32%">功能介绍</td>
                                    <td class="vip-text-re" width="34%">VIP会员</td>
                                    <td width="34%">普通会员</td>
                                </tr>
                                <tr>
                                    <td class="vip-text-bl">企业查询结果显示条数</td>
                                    <td class="vip-text-re">5000条</td>
                                    <td>100</td>
                                </tr>
                                <tr>
                                    <td class="vip-text-bl">资质查询结果显示条数</td>
                                    <td class="vip-text-re">5000条</td>
                                    <td>100</td>
                                </tr>
                                <tr>
                                    <td class="vip-text-bl">简历查询结果显示条数</td>
                                    <td class="vip-text-re">5000条</td>
                                    <td>100</td>
                                </tr>
                                <!-- <tr>
                                    <td class="vip-text-bl">老板查询</td>
                                    <td class="vip-text-re">不限次</td>
                                    <td>
                                        <span class="x">×</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="vip-text-bl">历史信息</td>
                                    <td class="vip-text-re">不限次</td>
                                    <td>
                                        <span class="x">×</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="vip-text-bl">雷达监控</td>
                                    <td class="vip-text-re">企业监控100家、人员监控100位</td>
                                    <td>
                                        <span class="x">×</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="vip-text-bl">风险扫描</td>
                                    <td class="vip-text-re">不限次</td>
                                    <td>
                                        <span class="x">×</span>
                                    </td>
                                </tr> -->
                                <!-- <tr>
                                    <td class="vip-text-bl">更多号码</td>
                                    <td class="vip-text-re">不限次</td>
                                    <td>
                                        <span class="x">×</span>
                                    </td>
                                </tr> -->
                            </tbody>
                        </table>
                    </div> <input name="goods_id" type="hidden" value="17"> </div>
            </div>
        </transition>
    </div>
</template>
<script>
import Store from "@/store";
export default {
    name: "",
    components: {},
    data() {
        return {};
    },
    created() {},
    methods: {
        upData(num) {
            if (this.iaActive != num) this.iaActive = num;
        },
        hiddenBOx() {
            Store.commit("toggleVipBox", 0);
            // document.removeEventListener("click",this.showCustom,false);
        },
        stopEvent() {
            event.preventDefault();
            event.stopPropagation();
        },
        applyVip() {
            this.$axios({
                type: "get",
                url: "vipApply/apply.do",
                data: { userId: this.$store.state.userInfo.userId },
                success: res => {
                    if (res.data.code == 0) {
                        this.hiddenBOx();
                        // Store.commit("closeVipBtn");
                        this.$notify({
                            title: "申请成功",
                            message: "请耐心等待后台审核",
                            type: "success",
                            duration: 0
                        });
                    } else {
                        this.$notify({
                            title: "提示",
                            message: res.data.msg,
                            type: "error",
                            duration: 0
                        });
                        //  this.hiddenBOx();
                    }
                }
            });
        }
    }
};
</script>
<style scoped>
.vipBox {
    position: fixed;
    width: 100%;
    height: 100%;
    left: 0;
    top: 0;
    overflow: hidden;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 999;
}
.modal-dialog {
    width: 750px;
    margin: auto;
    margin-top: 10vh;
}
.modal-content {
    height: 708px;
    border-radius: 0px;
    text-align: center;
}
.close {
    color: #fff;
    font-size: 40px;
    opacity: 0.8;
    font-weight: normal;
    margin-right: 10px;
}
.vip-top {
    padding-top: 5px;
    background: url("../assets/images/vip_modal_bg@2x.png") center top;
    background-size: 750px 290px;
}
.vip-buy-panel {
    width: 650px;
    height: 200px;
    border: solid 1px #eee;
    border-radius: 6px;
    margin: 85px auto 20px auto;
}
.vip-year-list {
    margin: 20px auto;
    width: 554px;
}
.vip-kuang {
    width: 114px;
    height: 70px;
    float: left;
    border: solid 2px #cdcdcd;
    margin-left: 35px;
    margin-right: 35px;
    cursor: pointer;
}
.vip-kuang .price {
    margin-top: 8px;
    color: #666;
}
.vip-kuang .price > span {
    font-size: 18px;
}
.vip-kuang .origin-price {
    margin-top: -3px;
    color: #999;
    text-decoration: line-through;
}
.vip-kuang.active {
    border: solid 2px #f9552a;
    cursor: default;
}
.vip-kuang .price > span {
    color: #f9552a;
}
.vip-kuang.active .price > span {
    color: #f9552a;
}
.vip-btn {
    display: block;
    width: 242px;
    background: #f9552a;
    border-radius: 2px;
    font-size: 18px;
    color: #fff !important;
    line-height: 44px;
    margin: auto;
}
.vip-publicity {
    color: #999999;
    margin-top: 13px;
}
.vip-container {
    width: 650px;
    margin: auto;
}
.vip-container .table-caption {
    border-bottom: solid 1px #e9e9e9;
    text-align: left;
}
.vip-container .table-caption img {
    display: inline-block;
    padding-bottom: 6px;
    border-bottom: solid 2px #128bed;
}
.vip-container .table-caption span {
    font-size: 13px;
    color: #666666;
    display: inline-block;
    padding-left: 16px;
    font-weight: 600;
}
.vip-container .table-caption a {
    font-size: 13px;
    color: #128bed;
    padding-top: 8px;
}
.vip-table td {
    text-align: left;
    padding: 0px 10px !important;
    height: 42px;
    border-top: none !important;
    font-size: 13px;
    font-weight: 400;
    vertical-align: middle !important;
}
.vip-table td a {
    color: #788288;
}
.vip-table tr:nth-child(1) > td {
    color: #333;
    font-size: 16px;
    font-weight: 600;
}
.vip-table > tbody > tr:nth-child(odd) > td,
.vip-table > tbody > tr:nth-child(odd) > th {
    background-color: #fff !important;
}
.vip-table > tbody > tr:nth-child(even) > td,
.vip-table > tbody > tr:nth-child(even) > th {
    background-color: #f5f9ff !important;
}
.vip-table .x {
    font-size: 28px;
    font-weight: normal;
    color: #999;
}
.vip-text-re {
    color: #ff2550 !important;
}
.vip-rec {
    position: relative;
    background: url("../assets/images/vip_rec.png");
    background-size: 50px 18px;
    width: 50px;
    height: 18px;
    top: -60px;
    left: -2px;
}
@media only screen {
    .vip-rec {
        background-image: url("../assets/images/vip_rec@2x.png");
    }
}
@media only screen and (-webkit-min-device-pixel-ratio: 2) {
    .vip-rec {
        background-image: url("../assets/images/vip_rec@2x.png");
    }
}
@media only screen and (min-device-pixel-ratio: 2) {
    .vip-rec {
        background-image: url("../assets/images/vip_rec@2x.png");
    }
}
@media only screen and (min-device-pixel-ratio: 3) {
    .vip-rec {
        background-image: url("../assets/images/vip_rec@2x.png");
    }
}
@media print and (min-resolution: 144dpi) {
    .vip-rec {
        background-image: url("../assets/images/vip_rec@2x.png");
    }
}
</style>