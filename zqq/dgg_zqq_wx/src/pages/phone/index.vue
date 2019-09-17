<template>
    <div>
        <ul class="handel">
            <li class="bline">
                <img src="/static/img/phone.png" alt="">
                <input type="number"  v-model="phone" maxlength="11" placeholder="请输入您的电话">
            </li>
            <li>
                <img src="/static/img/code.png" alt="">
                <input type="number" maxlength="4" v-model="code"  placeholder="验证码">
                <span class="codeBtn" :class="{'checked': isClick}" @click="getCode">{{codeStr}}</span>
            </li>
        </ul>
        <div class="submitBtn">
            <small class="f26">温馨提示：为了更好的为您服务，请绑定您的手机号</small>
            <div class="btn" @click="submit">确认</div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            phone: "",
            code: "",
            codeStr: '获取验证码',
            isClick: false,
            isHasPhone: 0
        };
    },
    components: {},
    methods: {
        async getCode () {
            if (this.isClick) {
                return false
            }
            if (!/^1(3|4|5|7|8|9)\d{9}$/.test(this.phone)) {
                wx.showToast({
                  title: '请输入正确电话号码',
                  icon: 'none',
                  duration: 2000
                })
                return;
            }
            let min = 60;
            await this.$Http('/wechat/checkBind', {
                phone: this.phone,
                userId: wx.getStorageSync("userInfo").userId
            }, "get").then(res => {
                if (res.data != 0) {
                    this.isHasPhone = 1;
                } else {
                    this.isHasPhone = 0;
                }
            });
            if (this.isHasPhone) {
                wx.showToast({
                  title: '此电话号码已绑定，请更换号码',
                  icon: 'none',
                  duration: 2000
                })
                return false
            }
            this.isClick = true;
            this.$Http(
                "sms/sentidentifycode",
                { phoneNo: this.phone, type: "123" },
                "post",
                true
            ).then(res => {
                //console.log("短信", json);
                if (res.code == 0) {
                    wx.showToast({
                      title: '发送成功,请注意查收',
                      icon: 'none',
                      duration: 2000
                    })
                    let timer = setInterval(() => {
                        if (min == 1) {
                            this.isClick = false;
                            clearInterval(timer);
                            this.codeStr = "重新获取";
                        } else {
                            min--;
                            if (min < 10) min = "0" + min;
                            this.codeStr = "重新获取( " + min + " )";
                        }
                    }, 1000);
                } else {
                    wx.showToast({
                      title: res.msg,
                      icon: 'none',
                      duration: 2000
                    })
                }
            }).catch(() => {
                this.isClick = false;
            });
        },
        submit () {
            if (!/^1(3|4|5|7|8|9)\d{9}$/.test(this.phone)) {
                wx.showToast({
                  title: '请填写正确的电话号码',
                  icon: 'none',
                  duration: 2000
                })
                return;
            }
            if (this.code == "") {
                wx.showToast({
                  title: '请填写短信验证码',
                  icon: 'none',
                  duration: 2000
                })
                return;
            }
            let obj = {
                phone: this.phone,
                smsCode: this.code,
                userId: wx.getStorageSync("userInfo").userId
            };

            this.$Http("/wechat/bingPhone", obj, "get", true).then(res => {
                wx.setStorageSync("userInfo", {...wx.getStorageSync("userInfo"), ...res.data});
                this.$store.commit("SET_USERINFO", {...wx.getStorageSync("userInfo"), ...res.data});
                wx.switchTab({
                    url: "/pages/mine/main",
                    fail (r) {
                        console.log(r)
                    }
                });
            });
        }
    }
};
</script>

<style scoped lang='stylus'>
.handel {
    background: #fff;
    margin: 32rpx 0;
    border-top: 1rpx solid #e5e5e5;

    li {
        height: 100rpx;
        line-height: 100rpx;
        position: relative;
        padding: 0 40rpx;
        border-bottom: 1rpx solid #e5e5e5;
        box-sizing: border-box;

        >img {
            float: left;
            width: 44rpx;
            height: 44rpx;
            margin-top: 28rpx;
        }

        input {
            float: left;
            height: 100rpx;
            line-height: 100rpx;
            border: none;
            outline: none;
            padding-left: 10rpx;
            width: 360rpx;
        }

        .codeBtn {
            color: #fd7d22;
            border: 1rpx solid #fd7d22;
            width: 200rpx;
            height: 60rpx;
            box-sizing: border-box;
            border-radius: 4rpx;
            text-align: center;
            line-height: 60rpx;
            float: right;
            margin-top: 20rpx;
            &.checked{
                color: #999
                border: 1rpx solid #999;
            }
                
        }
        
    }
}

.submitBtn {
    padding: 0 40rpx;

    .btn {
        margin-top: 50rpx;
        width: 100%;
        height: 94rpx;
    }
}
</style>
