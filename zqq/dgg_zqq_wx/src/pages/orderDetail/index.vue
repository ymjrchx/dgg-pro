<template>
    <div>
        <li class="item head">
            <label>订单编号</label>
            <p>{{orderInfo.orderNo}}</p>
            <span>{{statusTxts[orderInfo.status]}}</span>
        </li>
        <div class="itemBox">
            <h1>基本信息</h1>
            <ul>
                <li class="item">
                    <label>服务名称</label>
                    <p>{{orderInfo.serviceName}}</p>
                </li>
                <li class="item">
                    <label>申请人</label>
                    <p>{{orderInfo.contactName}}</p>
                </li>
                <li class="item">
                    <label>联系电话</label>
                    <p>{{orderInfo.contactPhone}}</p>
                </li>
                <li class="item">
                    <label>金额</label>
                    <p>{{orderInfo.allPrice}}</p>
                </li>
                <li class="item">
                    <label>创建时间</label>
                    <p>{{orderInfo.createTime}}</p>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            orderInfo: "",
            statusTxts: {
                order_status_obligation: "未付款",
                order_status_spend: "已付款",
                order_status_inprocess: "办理中",
                order_status_completed: "已完结",
                order_status_evaluate: "已评价",
                order_status_not_audit: "待审",
                order_status_audit_faild: "审核失败",
                order_status_waiting_submit: "待提交",
            }
        };
    },
    components: {},
    methods: {
        loadInfo (id) {
            this.$Http("/order/queryUserOrderByNo", {
                userId: wx.getStorageSync("userInfo").userId,
                orderNo: id
                }).then(res => {
                this.orderInfo = res.data
            });
        }
    },
    onLoad () {
        this.loadInfo(this.$root.$mp.query.id)
    }
};
</script>

<style scoped lang='stylus'>
.item {
    height: 80rpx;
    line-height: 80rpx;
    position: relative;
    padding: 0 24rpx;
    // border-bottom: 1rpx solid #e5e5e5;
    box-sizing: border-box;
    background: #ffffff;
    color: #888;
    font-size: 30rpx;

    label {
        float: left;
        width: 200rpx;
        padding-right: 20rpx;
        color: #AAA;
    }

    p {
        float: left;
        width: 460rpx;
        text-align: left;
        color: #666;
    }
}

.item.head {
    color: #333;
    margin-bottom: 20rpx;

    label {
        float: left;
        width: 130rpx;
        color: #333;
    }

    p {
        width: 340rpx;
        color: #333;
    }

    span {
        float: right;
        color: #999;
    }
}

.none {
    border-bottom: 1rpx solid #fff;
}

.itemBox {
    background: #fff;
    margin-bottom: 20rpx;

    h1 {
        height: 80rpx;
        line-height: 80rpx;
        position: relative;
        padding: 0 24rpx;
        border-bottom: 1rpx solid #e5e5e5;
        font-size: 32rpx;
        color: #333;
    }
}
</style>
