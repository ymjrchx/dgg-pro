<template>
    <div>
        <ul class="handel">
            <li class="bline" @click="goEdit">
                <label>昵称</label>
                <p>{{nickname}}</p>
                <i class="left_icon"><img src="/static/img/left.png" alt=""></i>
            </li>
            <li class="none">
                <label>电话号码</label>
                <p>{{tel}}</p>
            </li>
        </ul>
        <!-- <div class="layout">
            退出当前账号
        </div> -->
    </div>
</template>

<script>
export default {
    data() {
        return {
            nickname: "昵称",
            tel: "189XXXX0312"
        };
    },
    components: {},
    methods: {
        goEdit() {
            wx.navigateTo({
              url: '/pages/name/main'
            })
        }
    },
    onLoad () {
        
    },
    onShow () {
        this.nickname = wx.getStorageSync("userInfo").nickname
        this.tel = wx.getStorageSync("userInfo").phoneNo
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

        label {
            float: left;
            width: 140rpx;
        }

        p {
            float: left;
            height: 100rpx;
            line-height: 100rpx;
            border: none;
            outline: none;
            padding-left: 10rpx;
            width: 520rpx;
            color: #888;
            text-align: right;
        }
    }

    .none {
        border-bottom: 1rpx solid #fff;
    }
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
