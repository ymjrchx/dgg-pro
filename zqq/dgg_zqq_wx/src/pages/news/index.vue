<template>
    <div class="mainPageBox">
        <div class="container">
            <ul class="navBox">
                <li v-for="(item,idx) in titleNav" :key='idx' :class="{'active':navIdx == idx}" @click="switchTab(idx)">{{item.name}}</li>
            </ul>
            <div class="empt"></div>
            <div v-if="newsList"  @click="goShow(newsList[0].id)">
                <img :src="newsList[0].newsPhoto?newsList[0].newsPhoto: '/static/img/home_img_banner.png'" alt="" mode='widthFix' class="home_img" @click="brandRegister">
                <p class="hui lh72 f26">{{newsList[0].title}}</p>
            </div>
            <ul class="list" v-if="newsList">
                <li v-for="(item,idx) in newsList" :key='idx' v-if="idx>0" @click="goShow(item.id)">
                    <div class="list_2 fl">
                        <p class="text-delic3">
                            <wxParse :content="item.content" />
                        </p>
                        <div>
                            <small class="frist">{{item.label}}</small>
                            <small>{{item.createTime}}</small>
                        </div>
                    </div>
                    <div class="list_1 fl"><img @error="errorImg(idx)" :src="item.newsPhoto?item.newsPhoto:'/static/img/default.png'"  alt="" mode='widthFix'></div>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import wxParse from 'mpvue-wxparse'
export default {
    data() {
        return {
            motto: "Hello World",
            titleNav: [],
            navIdx: 0,
            pageNum: 1,
            newsList: ""
        };
    },
    components: {
        wxParse
    },
    created () {
        this.loadTitle()
    },
    methods: {
        brandRegister() {},
        switchTab(num) {
            this.navIdx = num;
            this.pageNum = 1
            this.loadByTypeList()
            wx.pageScrollTo({
                scrollTop: 0
              })
        },
        loadTitle () {
            this.$Http('/news/type2').then((res) => {
                this.titleNav = res.data
                this.loadByTypeList()
            })
        },
        loadByTypeList () {
            wx.showLoading({
              title: '加载中',
            })
            this.$Http(`/news/all?type=${this.titleNav[this.navIdx].code}&pageSize=15&pageNum=${this.pageNum}`, {}, 'POST').then((res) => {
                wx.stopPullDownRefresh()
                wx.hideLoading()
                if (this.pageNum == 1) {
                    this.newsList = res.data.data
                } else {
                    this.newsList = this.newsList.concat(res.data.data)
                }
            })
        },
        errorImg(i){
            this.newsList[i].newsPhoto = '/static/img/default.png'
        },
        goShow (id) {
            wx.navigateTo({
              url: "/pages/newInfo/main?id="+id
            })
        }
    },
    onReachBottom () {
        this.pageNum++
        this.loadByTypeList()
    },
    onPullDownRefresh() {
        this.pageNum = 1
        this.loadByTypeList()
    }
};
</script>

<style scoped>
.mainPageBox {
    padding: 20rpx 25rpx;
}
.home_img {
    width: 100%;
    height: 230rpx;
}
.navBox {
    display: flex;
    justify-content: flex-start;
    height: 60rpx;
    width: 100%;
    line-height: 60rpx;
    margin-bottom: 20rpx;
    position: fixed;
    padding: 0 25rpx;
    background: #fff;
    z-index: 6;
    top: 0;
    left: 0;
}
.empt {
    height: 60rpx;
    line-height: 60rpx;
    width: 100%;
    font-size: 0;
}
.navBox li {
    width: 120rpx;
    height: 100%;
    border-bottom: 2rpx solid transparent;
    font-size: 30rpx;
    text-align: center;
    color: #676767;
    margin-right: 40rpx;
}
.navBox .active {
    color: var(--col);
    border-bottom: 4rpx solid #fd7d22;
}
.list {
    border-top: 1rpx solid #eaeaea;
}
.list > li {
    padding: 26rpx 0;
    overflow: hidden;
    border-bottom: 1rpx solid #eaeaea;
}
.list_1 {
    width: 35%;
    height: 100%;
    border-radius: 4rpx;
    overflow: hidden;
}
.list_1 img {
    width: 100%;
    height: 210rpx;
}
.list_2 {
    box-sizing: border-box;
    width: 65%;
    padding-right: 20rpx;
    height: 100%;
}
.list h2 {
    font-size: 32rpx;
    margin-bottom: 4rpx;
}
.list p {
    margin-bottom: 8rpx;
    font-size: 30rpx;
    height: 120rpx;
}
.list div > small {
    display: inline-block;
    color: #999;
}
.list div > .frist {
    margin-right: 46rpx;
}
</style>
