<template>
    <div>
        <div class="goods">
            <div class="imgBox">
                <img :src="serviceInfo.logoFile?(fhost+serviceInfo.logoFile):'/static/img/order.png'" alt="知千秋">
            </div>
            <div class="goods_detail">
                <h1 class="f28 mb20 ">{{serviceInfo.name}}</h1>
                <small class="f24 c999">{{serviceInfo.describle}}</small>
                <h2>￥
                    <span>{{total}}</span>
                    <i> x {{num}}</i>
                </h2>
            </div>
        </div>
        <ul class="handel">
            <li class="bline">
                <label>联系人</label>
                <input type="text" v-model="name" maxlength="20" placeholder="请输入姓名，便于平台联系你">
            </li>
            <li>
                <label>联系方式</label>
                <input type="number" v-model="phone" maxlength="11" placeholder="请输入手机号，便于平台联系你">
            </li>
            <li>
                <label>联系邮箱</label>
                <input type="number" v-model="email" maxlength="30" placeholder="请输入邮箱，便于平台联系你">
            </li>
            <li>
                <label>备注</label>
                <input type="text" v-model="remark" maxlength="500" placeholder="选填，可以填写您的一些要求">
            </li>
        </ul>
        <div class="submitBtn">
            <small class="f26">如有问题，请拨打{{telNumber}}</small>
        </div>
        <div class="footEmpt"></div>
        <div class="footBox footBtn">
            <div class="icon_b">
                总价：
                <span>￥
                    <i>{{total}}</i>
                </span>
            </div>
            <div class="icon_button" @click="submitOrder">立即付款</div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            phone: "",
            name: "",
            email: '',
            remark: "",
            serviceInfo: wx.getStorageSync("productInfo").serviceInfo,
            fhost: wx.getStorageSync("productInfo").fhost,
            num: wx.getStorageSync("productInfo").num
        };
    },
    computed: {
        telNumber() {
            return this.$store.state.telNumber;
        },
        total () {
            return this.num*(this.serviceInfo.officialPrice+this.serviceInfo.servicePrice).toFixed(2)
        }
    },
    components: {},
    methods: {
        submitOrder () {
            if (!this.name) {
                wx.showToast({
                  title: '请输入姓名',
                  icon: 'none',
                  duration: 2000
                })
                return false;
            }
            if (!this.phone) {
                wx.showToast({
                  title: '请输入电话',
                  icon: 'none',
                  duration: 2000
                })
                return false;
            }
            if (!/^1(3|4|5|7|8|9)\d{9}$/.test(this.phone)) {
                wx.showToast({
                  title: '请填写正确的电话号码',
                  icon: 'none',
                  duration: 2000
                })
                return;
            }
            if (!this.email) {
                wx.showToast({
                  title: '请输入邮箱',
                  icon: 'none',
                  duration: 2000
                })
                return false;
            }
            if (!/^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/.test(this.email)) {
                wx.showToast({
                  title: '请填写正确的邮箱',
                  icon: 'none',
                  duration: 2000
                })
                return;
            }

            

            let obj = {
                userId: wx.getStorageSync('userInfo').userId,
                serviceId: this.serviceInfo.id,
                serviceAttrId: this.serviceInfo.serviceAttrId,
                allPrice: this.total,
                contactName: this.name,
                contactPhone: this.phone,
                contactEmail: this.email,
                allNum: this.num,
                remark: this.remark
            };
            wx.showLoading({
              title: '提交中...',
              mask: true
            })
            this.$Http("/order/saveNormalOrder", obj, "POST", true).then(
                res => {
                  this.$Http(
                        `/order/queryUserOrderByNo?orderNo=${
                            res.data
                        }&userId=${wx.getStorageSync('userInfo').userId}`,
                        {},
                        "GET"
                    ).then((res2) => {
                        // 获取支付参数
                        this.$Http("/payment/create", {payType: 'wechat', businessNo: res2.data.orderNo, businessType: res2.data.serviceTypeLevel1Code, businessBody: res2.data.serviceName, fee: res2.data.allPrice, sign: res2.data.sign, openid: wx.getStorageSync('userInfo').openId}, "POST", false).then((payParams) => {
                            wx.hideLoading()
                            wx.requestPayment({
                              timeStamp: payParams.data.payOrder.timestamp,
                              nonceStr: payParams.data.payOrder.noncestr,
                              package: payParams.data.payOrder.package,
                              signType: 'MD5',
                              paySign: payParams.data.payOrder.sign,
                              success (res) {
                                wx.showToast({
                                  title: '支付成功',
                                  icon: 'none',
                                  mask: true,
                                  duration: 1500
                                })
                                setTimeout(() => {
                                    wx.redirectTo({
                                        url: "/pages/myOrder/main"
                                    });
                                }, 1500)
                              },
                              fail (res) {
                                console.log(res)
                                wx.showToast({
                                  title: '支付取消',
                                  icon: 'none',
                                  mask: true,
                                  duration: 1500
                                })
                                setTimeout(() => {
                                    wx.redirectTo({
                                        url: "/pages/myOrder/main"
                                    });
                                }, 1500)
                              }
                            })
                        })
                    })
                })
        }
    },
    onLoad() {
        this.serviceInfo = wx.getStorageSync("productInfo").serviceInfo
        this.fhost = wx.getStorageSync("productInfo").fhost
        this.num = wx.getStorageSync("productInfo").num
    }
};
</script>

<style scoped lang='stylus'>
.goods {
    padding: 48rpx 24rpx;
    overflow: hidden;
    background: #fff;

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
            color: #f95240;
            margin-top: 22rpx;

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

.handel {
    background: #fff;
    margin: 32rpx 0;
    border-top: 1rpx solid #e5e5e5;

    li {
        height: 100rpx;
        line-height: 100rpx;
        position: relative;
        padding: 0 24rpx;
        border-bottom: 1rpx solid #e5e5e5;
        box-sizing: border-box;

        label {
            float: left;
            width: 140rpx;
        }

        input {
            float: left;
            height: 100rpx;
            line-height: 100rpx;
            border: none;
            outline: none;
            padding-left: 10rpx;
            width: 500rpx;
        }
    }
}

.submitBtn {
    padding: 0 24rpx 30rpx;

    small {
        color: #c4c4c4;
    }
}

.footBtn {
    .icon_b {
        float: left;
        width: 55%;
        height: 100%;
        font-size: 24rpx;
        text-align: center;
        padding-right: 26rpx;
        box-sizing: border-box;
        line-height: 100rpx;

        span {
            color: #e35b59;

            i {
                font-size: 36rpx;
                display: inline;
            }
        }
    }

    .icon_button {
        float: left;
        width: 45%;
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
