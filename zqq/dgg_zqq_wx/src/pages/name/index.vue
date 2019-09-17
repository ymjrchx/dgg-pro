<template>
    <div>
        <ul class="handel">
            <li class="bline">
                <label>昵称</label>
                <input type="text" v-model="nickname">
            </li>
        </ul>
        <button @click="save">保存</button>
    </div>
</template>

<script>
export default {
    data() {
        return {
            nickname: "昵称",
        };
    },
    components: {},
    methods: {
        save() {
            this.$Http("/persional/updateNick", {
                userId: wx.getStorageSync("userInfo").userId,
                nickname: this.nickname
                }).then(res => {
                    wx.showToast({
                      title: '保存成功',
                      icon: 'none',
                      duration: 2000
                    })
                    wx.setStorageSync("userInfo", {...wx.getStorageSync("userInfo"), nickname: this.nickname})
                    setTimeout(() => {
                        wx.navigateBack({
                            delta: 1
                        })
                    }, 2000)
            });
        }
    },
    onLoad () {
        this.nickname = wx.getStorageSync("userInfo").nickname
    }
};
</script>

<style scoped lang='stylus'>
.handel {
    background: #fff;
    margin: 32rpx 0;

    li {
        height: 100rpx;
        line-height: 100rpx;
        position: relative;
        padding: 0 24rpx;
        border-bottom: 1rpx solid #e5e5e5;
        box-sizing: border-box;
        display: flex;
        label {
            width: 140rpx;
        }

        input {
            height: 100rpx;
            line-height: 100rpx;
            border: none;
            outline: none;
            padding-left: 10rpx;
            color: #888;
            flex: 1;
            text-align: right;
        }
    }

    .none {
        border-bottom: 1rpx solid #fff;
    }
}


button{
    margin: 30rpx;
    background: #fd7d22;
    color: #fff;
}
.layout {
    height: 100rpx;
    line-height: 100rpx;
    background: #fff;
    text-align: center;
    color: #E35B59;
    font-size: 28rpx;
}
</style>
