<template>
    <div>
        <div class="headBox">
            <img src="/static/img/set.png" alt="设置" @click="goSet" mode='widthFix'>
            <div class="headImgBox">
                <img :src="userInfo.avatarUrl" mode='widthFix' alt="">
            </div>
            <div class="headNameBox">
                <h2>{{userInfo.nickname}}</h2>
                <small>{{userInfo.phoneNo}}</small>
            </div>
        </div>
        <div class="mainPageBox">
            <ul class="iconBox">
                <li @click="goMyOrder('order_status_obligation')">
                    <img src="/static/img/icon1.png" mode='widthFix' alt="">
                    <h2>待付款</h2>
                </li>
                <li @click="goMyOrder('order_status_inprocess')">
                    <img src="/static/img/icon2.png" mode='widthFix' alt="">
                    <h2>服务中</h2>
                </li>
                <li @click="goMyOrder('order_status_completed')">
                    <img src="/static/img/icon3.png" mode='widthFix' alt="">
                    <h2>已完成</h2>
                </li>
            </ul>
            <ul class="handel">
                <li class="bline" @click="goMyOrder('')">
                    <img src="/static/img/icon4.png" alt="">
                    <p class="f28">我的订单</p>
                    <i class="left_icon"><img src="/static/img/left.png" alt=""></i>
                </li>
                <li @click="takeCall">
                    <img src="/static/img/icon5.png" alt="">
                    <p class="f28">服务热线</p>
                    <small>{{telNumber}}</small>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            userInfo: ""
        };
    },
    computed: {
        telNumber() {
            return this.$store.state.telNumber;
        }
    },
    components: {},
    methods: {
        brandRegister() {},
        switchTab(num) {
            this.navIdx = num;
        },
        takeCall() {
            wx.makePhoneCall({
                phoneNumber: this.telNumber
            });
        },
        goSet() {
            wx.navigateTo({
                url: "/pages/seting/main"
            });
        },
        goMyOrder(status) {
            wx.navigateTo({
                url: "/pages/myOrder/main?status=" + status
            });
        }
    },
    onShow() {
        this.$checkLogin()
        this.userInfo = wx.getStorageSync("userInfo")
        // setTimeout(() => {
        //     console.log("查看有无数据", this.userInfo);
        // }, 3000);
    }
};
</script>

<style scoped lang='stylus'>
.headBox {
    width: 100%;
    height: 310rpx;
    background: linear-gradient(to right, #FD9A53, #FD7E24);
    padding: 60rpx 25rpx 0rpx;
    position: relative;
    box-sizing: border-box;

    >img {
        position: absolute;
        right: 24rpx;
        top: 30rpx;
        width: 38rpx;
    }
}

.headImgBox {
    float: left;
    width: 124rpx;
    height: 124rpx;
    border-radius: 50%;
    overflow: hidden;
}

.headImgBox img {
    width: 124rpx;
}

.headNameBox {
    float: left;
    color: #fff;
    margin-left: 24rpx;
    padding-top: 10rpx;

    h2 {
        font-size: 32rpx;
        margin-bottom: 10rpx;
    }
}

.mainPageBox {
    padding: 20rpx 25rpx;
    top: -80rpx;
    z-index: 4;
    position: relative;

    .iconBox {
        width: 100%;
        background: #fff;
        border-radius: 8rpx;
        overflow: hidden;
        margin-bottom: 20rpx;

        li {
            float: left;
            width: 33.333%;
            text-align: center;
            padding: 38rpx 0;
            color: #777;

            img {
                width: 64rpx;
            }
        }
    }
}

.handel {
    border-radius: 8rpx;
    overflow: hidden;
    background: #fff;

    li {
        height: 108rpx;
        line-height: 108rpx;
        position: relative;
        padding-left: 30rpx;
        padding-right: 20rpx;

        >img {
            float: left;
            width: 38rpx;
            height: 38rpx;
            margin-top: 35rpx;
        }

        p {
            float: left;
            margin-left: 20rpx;
        }

        small {
            float: right;
            color: #888;
        }
    }

    .bline {
        border-bottom: 2rpx solid #f9f9f9;
    }
}
</style>
