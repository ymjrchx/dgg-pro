<template>
    <div>
        <div class="headBox">
            <img :src="serviceInfo.logoFile?(fhost+serviceInfo.logoFile):'/static/img/order.png'" @error="errorImg" alt="">
        </div>
        <div class="desc">
            <h2>￥
                <span>{{(serviceInfo.officialPrice+serviceInfo.servicePrice)}}.00</span>
            </h2>
            <h1>{{serviceInfo.name}}</h1>
            <small>{{serviceInfo.describle}}</small>
        </div>
        <div class="count">
            <span>数量</span>
            <div class="fr contMo">
                <div class="countBox">
                    <span class="numBtn" @click="countFunc(-1)" :class="{hui: numCount == 1 }">-</span>
                    <input type="number" class="num numBtn" readonly v-model="numCount" @blur="countFunc2" />
                    <span class="numBtn" @click="countFunc(1)">+</span>
                </div>
            </div>
        </div>
        <div class="contentImg">
            <h1>服务详情</h1>
            <img :src="serviceInfo.detailImgFile?(fhost+serviceInfo.logoFile):'/static/img/order.png'" mode='widthFix' :alt="serviceInfo.name">
        </div>
        <div class="footEmpt"></div>
        <div class="footBox footBtn">
            <div class="icon_b" @click="back">
                <img src="/static/img/foot1.png" alt="" mode='widthFix'>
                <p>首页</p>
            </div>
            <div class="icon_b" @click="consultation">
                <img src="/static/img/foot2.png" alt="" mode='widthFix'>
                <p>咨询</p>
                <span class="line"></span>
            </div>
            <div class="icon_button" @click="goBuy">立即购买</div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            numCount: 1,
            serviceInfo: "",
            fhost: ''
        };
    },
    components: {},
    methods: {
        countFunc(num) {
            if (this.numCount == 1 && num == -1) return;
            if (this.numCount <= 99) {
                this.numCount += num;
            }
        },
        countFunc2() {
            if (isNaN(Number(this.numCount))) {
                this.numCount = 1;
                return;
            }
            if (this.numCount > 99) this.numCount = 99;
            if (this.numCount < 1) this.numCount = 1;
        },
        goBuy() {
            if (this.$checkLogin()) {
                wx.setStorageSync("productInfo", {serviceInfo: this.serviceInfo, fhost: this.fhost, num: this.numCount});
                wx.navigateTo({
                    url: "/pages/pay/main"
                }); 
            }
        },
        consultation() {
            wx.makePhoneCall({
                phoneNumber: this.$store.state.telNumber
            });
        },
        back() {
            wx.switchTab({
                url: "/pages/index/main"
            });
        },
        loadService (numbers) {
            this.$Http('/serviceItem/findServiceItemByNumber', {numbers: numbers}).then((res) => {
                this.serviceInfo = res.data[0].serviceItem
                this.fhost = res.data[0].fhost
            })
        }
    },
    onLoad () {
        this.loadService(this.$root.$mp.query.id)
    }
};
</script>

<style scoped lang='stylus'>
.headBox {
    width: 100%;
    margin-bottom: 20rpx;
    height: 300rpx;
    overflow: hidden;
    position: relative;

    img {
        width: 100%;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translateY(-50%);
        margin-left: -375rpx;
    }
}

.desc {
    padding: 38rpx 20rpx 46rpx 20rpx;
    background: #fff;
    border-bottom: 2rpx solid #f9f9f9;

    h2 {
        color: #f95240;
        margin-bottom: 20rpx;

        span {
            font-size: 36rpx;
            font-weight: bold;
        }
    }

    h1 {
        font-size: 30rpx;
        margin-bottom: 16rpx;
    }

    small {
        color: #888;
        font-size: 24rpx;
    }
}

.count {
    padding: 0 20rpx;
    background-color: #fff;
    height: 106rpx;
    line-height: 106rpx;
    margin-bottom: 20rpx;
    font-size: 30rpx;

    >div {
        display: inline-block;
        margin-top: 18rpx;
        font-size: 0;
    }
}

.countBox {
    display: inline-block;
    border: 1rpx solid #eaeaea;
    border-radius: 6px;
    overflow: hidden;
    border-left: none;
    font-size: 34rpx;

    .numBtn {
        width: 100rpx;
        height: 70rpx;
        line-height: 70rpx;
        border-left: 1rpx solid #eaeaea;
        float: left;
        text-align: center;
    }

    .num {
        color: #fd7d22;
    }

    .hui {
        background-color: #f9f9f9;
    }
}

.contentImg {
    padding: 42rpx 20rpx;
    background: #fff;

    h1 {
        font-size: 28rpx;
        margin-bottom: 20rpx;
    }

    img {
        width: 100%;
    }
}

.footBtn {
    .icon_b {
        width: 25%;
        height: 100%;
        float: left;
        text-align: center;
        font-size: 22rpx;
        padding-top: 10rpx;
        position: relative;
        border-top: 1px solid #e5e5e5;

        img {
            width: 48rpx;
        }

        .line {
            position: absolute;
            content: ' ';
            height: 40rpx;
            width: 1rpx;
            border-left: 1rpx solid #e5e5e5;
            left: 0;
            top: 30rpx;
        }

        p {
            position: relative;
            top: -6rpx;
        }
    }

    .icon_button {
        float: left;
        width: 50%;
        height: 100%;
        background: #E35B59;
        color: #fff;
        font-size: 30rpx;
        text-align: color;
        line-height: 100rpx;
        text-align: center;
    }
}
</style>
