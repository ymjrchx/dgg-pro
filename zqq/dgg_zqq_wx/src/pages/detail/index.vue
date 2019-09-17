<template>
    <div>
        <ul class="goods">
            <li class="list">
                <div class="list_t">
                    <span class="fl">订单号：54156454515</span>
                    <span class="fr c999">已付款</span>
                </div>
                <div class="list_c">
                    <div class="imgBox">
                        <img src="/static/img/pay.png" alt="知千秋">
                    </div>
                    <div class="goods_detail">
                        <h1 class="f28 mb20 ">商标担保注册</h1>
                        <small class="f24 c999">商标担保申请，当日提交注册无忧</small>
                        <h2>￥
                            <span>4800.00</span>
                            <i> x 1</i>
                        </h2>
                    </div>
                </div>
            </li>
            <li class="list content">
                <div class="list_t">
                    基本信息
                </div>
                <div class="list_c">
                    <div>
                        <label>联系人</label>
                        <p>阿达阿萨德</p>
                    </div>
                    <div>
                        <label>联系人</label>
                        <p>15236524585</p>
                    </div>
                </div>
            </li>
        </ul>
        <div class="footEmpt"></div>
        <div class="footBox footBtn">
            <div class="btn icon_cancel">
                取消订单
            </div>
            <div class="btn icon_button" @click="goBuy">立即购买</div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {};
    },
    components: {},
    methods: {
        switchFunc(num) {
            if (this.navIdx == num) return;
            this.navIdx = num;
        },
        goDetail() {
            wx.navigateTo({
                url: "/pages/orderDetail/main"
            });
        }
    }
};
</script>

<style scoped lang='stylus'>
.goods {
    padding: 20rpx;
    box-sizing: border-box;
}

.list {
    margin-bottom: 20rpx;
    background: #fff;
    border-radius: 4rpx;
    padding: 0 24rpx;

    .list_t {
        height: 80rpx;
        line-height: 80rpx;
        background: #fff;
        box-sizing: border-box;
        padding: 0 24rpx;
        border-bottom: 1rpx solid #e5e5e5;
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
            width: 386rpx;

            h2 {
                margin-top: 16rpx;
                color: #f95240;

                span {
                    font-size: 36rpx;
                }

                i {
                    font-size: 32rpx;
                    color: #565656;
                    float: right;
                    margin-top: 2rpx;
                }
            }
        }
    }
}

.content {
    .list_t {
        font-size: 34rpx;
        color: #333;
    }

    .list_c {
        >div {
            height: 80rpx;
            line-height: 80rpx;
            position: relative;
            box-sizing: border-box;
            background: #ffffff;
            color: #888;

            label {
                float: left;
                width: 140rpx;
                padding-right: 20rpx;
                color: #AAA;
            }

            p {
                float: left;
                width: 440rpx;
                text-align: left;
                color: #666;
            }
        }
    }
}

.footBtn {
    box-sizing: border-box;
    padding: 24rpx;
    border-top: 1rpx solid #e5e5e5;
    height: 120rpx;

    .icon_cancel {
        float: left;
        width: 250rpx;
        box-sizing: border-box;
        height: 100%;
        border: 1rpx solid #e5e5e5;
        color: #888;
        background: #fff;
        border-radius: 4rpx;
    }

    .icon_button {
        float: right;
        width: 420rpx;
        box-sizing: border-box;
        height: 100%;
        border-radius: 4rpx;
    }
}
</style>
