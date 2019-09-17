<template>
    <div>
        <div class="headNav">
            <ul>
                <li v-for="(item,idx) in titleNav" :class="{'active': idx == navIdx}" :key='idx' @click="switchFunc(idx)">{{item.name}}</li>
            </ul>
        </div>
        <ul class="goods">
            <li class="list" v-for="(item, index) in listData" :key="item.id">
                <div class="list_t">
                    <span class="fl">订单号：{{item.id}}</span>
                    <span class="fr c999">{{item.status}}</span>
                </div>
                <div class="list_c" @click="goDetail(item.orderNo)">
                    <div class="imgBox">
                        <img :src="item.serviceItem[1]?item.serviceItem[1]:'/static/img/default.png'" @error="imgError(index)" alt="知千秋">
                    </div>
                    <div class="goods_detail">
                        <h1 class="f28 mb20 ">{{item.serviceName}}</h1>
                        <small class="f24 c999">{{item.serviceItem[0]}}</small>
                        <h2>
                            实付款：
                            <span>￥{{item.allPrice}}.00</span>
                        </h2>
                    </div>
                    <i class="left_icon"><img src="/static/img/left.png" alt=""></i>
                </div>
                <div class="list_b">
                    <span class="btn fr pay" v-if="item.status == '未付款'" @click="pay">付款</span>
                    <span class="btn fr cancel" v-if="item.status == '未付款'" @click="cancel(item.id)">取消订单</span>
                    <span class="btn fr cancel" @click="goDetail(item.orderNo)">查看详情</span>
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
export default {
    data() {
        return {
            titleNav: [
                {name: "商标", code: "navigation_trademark"},
                {name: "专利", code: "navigation_patent"}, 
                {name: "版权", code: "navigation_copyright"}],
            navIdx: 0,
            listData: "",
            status: "",
            pageIndex: 1,

        };
    },
    components: {},
    methods: {
        switchFunc(num) {
            if (this.navIdx == num) return;
            this.navIdx = num;
            this.pageIndex = 1
            this.loadOrderList()
        },
        loadOrderList() {
            this.$Http("/order/queryUserOrder", {
                userId: wx.getStorageSync("userInfo").userId,
                pageIndex: this.pageIndex,
                pageNum: 20,
                status: this.status,
                code: this.titleNav[this.navIdx].code,
                type: "order_type1"
                }).then(res => {

                if (this.pageIndex === 1) {
                    this.listData = res.data.dateMap
                } else {
                    this.listData = this.listData.concat(res.data.dateMap)
                }
            });
        },
        goDetail(id) {
            wx.navigateTo({
                url: "/pages/orderDetail/main?id="+id
            });
        },
        cancel (id) {
            wx.showModal({
                title: '提示',
                content: "确认是否取消此条订单?",
                success: res => {
                    if (res.confirm) {
                        this.$Http(
                            "/order/cancelUserOrder",
                            {
                                userId: wx.getStorageSync("userInfo").userId,
                                orderId: id
                            },
                            "get",
                            true
                        ).then(res => {
                            wx.showToast({
                              title: '取消成功',
                              icon: 'none',
                              duration: 2000
                            })
                            this.loadOrderList();
                        });
                    }
                }
            });
        },
        imgError (i) {
            this.listData[i].serviceItem[1] = '/static/img/default.png'
        }
    },
    onLoad() {
        this.status = this.$root.$mp.query.status?this.$root.$mp.query.status: ''
        this.loadOrderList()
    },
    onReachBottom () {
        this.pageIndex++
        this.loadOrderList()
    },
};
</script>

<style scoped lang='stylus'>
.headNav {
    height: 80rpx;
    width: 100%;

    ul {
        display: flex;
        justify-content: space-between;
        position: fixed;
        height: 80rpx;
        line-height: 80rpx;
        background: #fff;
        margin-bottom: 20rpx;
        box-sizing: border-box;
        padding: 0 24rpx;
        left: 0;
        top: 0;
        width: 100%;

        li {
            width: 110rpx;
            height: 100%;
            border-bottom: 2rpx solid #fff;
            text-align: center;
        }

        .active {
            color: #fd7d22;
            border-bottom: 2rpx solid #fd7d22;
        }
    }
}

.list {
    margin-bottom: 20rpx;

    .list_t {
        height: 80rpx;
        line-height: 80rpx;
        background: #fff;
        box-sizing: border-box;
        padding: 0 24rpx;
    }

    .list_c {
        padding: 40rpx 24rpx;
        overflow: hidden;
        position: relative;

        .imgBox {
            float: left;
            width: 200rpx;
            height: 150rpx;
            overflow: hidden;

            img {
                width: 100%;
                height: 100%;
            }
        }

        .goods_detail {
            float: left;
            padding-left: 30rpx;
            width: 450rpx;

            h2 {
                margin-top: 16rpx;

                span {
                    color: #f95240;
                    font-size: 36rpx;
                }
            }
        }
    }

    .list_b {
        height: 110rpx;
        line-height: 110rpx;
        background: #fff;
        box-sizing: border-box;
        padding: 0 24rpx;

        span {
            height: 56rpx;
            margin-top: 26rpx;
            margin-left: 20rpx;
            background: #fff;
            color: #fd7d22;
            border: 1rpx solid #fd7d22;
            font-size: 26rpx;
            width: 110rpx;
            border-radius: 4rpx;
        }

        .cancel {
            width: 160rpx;
            color: #b9b9b9;
            border: 1rpx solid #b9b9b9;
        }
    }
}
</style>
