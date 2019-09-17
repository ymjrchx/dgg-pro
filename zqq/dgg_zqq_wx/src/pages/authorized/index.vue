<template>
    <div class="backCol">
        <img src="/static/img/logo.png" mode='widthFix' alt="">

        <p class="words">申请获得你的公开信息（昵称、头像等）</p>
        <button class="btn quan" open-type="getUserInfo" lang="zh_CN" @getuserinfo="bindGetUserInfo">授权登录</button>

        <p class="goHome" @click="goHome">暂不绑定，返回首页看看</p>
    </div>
</template>
<script>
export default {
    data() {
        return {};
    },
    components: {},
    methods: {
        bindGetUserInfo(e) {
            let that = this
            wx.showLoading({
              title: '授权中...',
              mask: true
            })
            wx.login({
                success (res) {
                    if (e.mp.detail.rawData) {
                        that.$Http('/wechatApp/login', {
                            code: res.code, 
                            encryptedData: e.mp.detail.encryptedData, 
                            iv: e.mp.detail.iv}, 'POST', false).then((result) => {
                            wx.setStorageSync("userInfo", {...e.mp.detail.userInfo, ...result.data});
                            that.$store.commit("SET_USERINFO", {...e.mp.detail.userInfo, ...result.data});
                            wx.hideLoading()
                            if (result.data.phoneBind === 0) {
                                wx.redirectTo({
                                    url: "/pages/phone/main"
                                });
                            } else {
                                wx.switchTab({
                                    url: "/pages/mine/main"
                                });
                            }
                             
                        })
                    } else {
                        //用户按了拒绝按钮
                        console.log("用户按了拒绝按钮");
                    }
                }
            })
        },
        goHome () {
            wx.switchTab({
                url: "/pages/index/main"
            });
        }
    },
    mounted() {
    }
};
</script>

<style scoped lang='stylus'>
.backCol {
    width: 100vw;
    height: 100vh;
    background: #fff;
    text-align: center;
    box-sizing: border-box;
    padding: 120rpx;
    color: #888;

    img {
        width: 400rpx;
    }

    .words {
        margin: 200rpx 0 50rpx 0;
    }
    
    .goHome {
        text-align: center;
        color: #fd7d22;
        font-size: 32rpx;
        line-height: 160rpx;
    }
    .quan {
        border: none;
        outline: none;
        border-radius: 80rpx;
        box-shadow: 0 6rpx 6rpx 4rpx #FECFAD;
    }
}
</style>
