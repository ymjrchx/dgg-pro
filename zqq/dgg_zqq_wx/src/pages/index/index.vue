<template>
    <div class="mainPageBox">
        <div class="container">
            <swiper>
                <swiper-item>
                  <img src="/static/img/home_img_banner.png" alt="" mode='widthFix' class="home_img" @click="brandRegister">
                </swiper-item>
            </swiper>
            
            <ul class="navBox">
                <li v-for="(item,idx) in titleNav" :key='idx' :class="{'active':navIdx == idx}" @click="switchTab(idx)">{{item.name}}</li>
            </ul>
            <ul class="list">
                <li v-for="(item,idx) in listData" :key='idx' v-if="item.number != 'S7741102214293331969'">
                    <div class="list_1 fl"><img @error="errorImg(idx)" :src="item.fhost+item.logoFile" alt="" mode='widthFix'></div>
                    <div class="list_2 fl">
                        <h2>{{item.name}}</h2>
                        <p class="text-delic ">{{item.describle}}</p>
                        <small>
                            <span>￥</span>{{(item.officialPrice+item.servicePrice)}}.00</small>
                    </div>
                    <div class="list_3 fl">
                        <h2>.</h2>
                        <button class="btn" @click="goOrder(item.number)">立即办理</button>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            motto: "Hello World",
            titleNav: [
                {name: "商标", code: "navigation_trademark"},
                {name: "专利", code: "navigation_patent"}, 
                {name: "版权", code: "navigation_copyright"}],
            navIdx: 0,
            listData: ""
        };
    },
    components: {},
    methods: {
        brandRegister() {
            this.goOrder('S7741101919677030400')
        },
        switchTab(num) {
            this.navIdx = num;
            this.loadService()
        },
        goOrder(id) {
            wx.navigateTo({
                url: "/pages/order/main?id="+id
            });
        },
        loadService () {
            this.$Http("/serviceItem/findServiceItemByType", {typeLevel1Code: this.titleNav[this.navIdx].code}).then(res => {
                this.listData = res.data
            });
        },
        errorImg(i){
            this.listData[i].fhost = ''
            this.listData[i].logoFile = '/static/img/default.png'
        },
    },
    onShow() {
        this.loadService()
    }
};
</script>

<style scoped>
.mainPageBox {
    padding: 20rpx 25rpx;
}
swiper{
    height: 280rpx;
}
.home_img {
    width: 100%;
}
.navBox {
    display: flex;
    justify-content: space-between;
    height: 88rpx;
    line-height: 88rpx;
    border-bottom: 1rpx solid #eaeaea;
    margin-bottom: 16rpx;
}
.navBox li {
    width: 110rpx;
    height: 100%;
    border-bottom: 2rpx solid transparent;
    font-size: 34rpx;
    text-align: center;
    color: #676767;
}
.navBox .active {
    color: var(--col);
    border-bottom: 4rpx solid #fd7d22;
}
.list > li {
    padding: 20rpx 0;
    overflow: hidden;
}
.list_1 {
    width: 17%;
    height: 100%;
    border-radius: 4rpx;
    overflow: hidden;
}
.list_1 img {
    width: 100%;
}
.list_2 {
    box-sizing: border-box;
    width: 60%;
    padding: 0 15rpx;
    height: 100%;
}
.list h2 {
    font-size: 32rpx;
    margin-bottom: 4rpx;
}
.list p {
    color: #999;
    margin-bottom: 4px;
}
.list small {
    color: var(--red);
    font-size: 32rpx;
    /* font-weight: bold; */
}
.list small span {
    font-size: 26rpx;
}

.list_3 {
    width: 23%;
    height: 100%;
}
.list_3 .btn {
    width: 100%;
    height: 60rpx;
    font-size: 24rpx;
}
.list_3 h2 {
    color: transparent;
}
</style>
