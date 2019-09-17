<template>
    <div class="showInfo">
        <h1>{{showInfo.title}}</h1>
        <div class="des">
            <span>标签：{{showInfo.label}}</span><span>时间:{{showInfo.createTime}}</span>
        </div>
        <wxParse :content="showInfo.content" />
    </div>
</template>

<script>
import wxParse from 'mpvue-wxparse'
export default {
    data() {
        return {
            showInfo: ""
        };
    },
    components: {
        wxParse
    },
    methods: {
        loadInfo (id) {
            this.$Http('/news/content', {id: id}).then((res) => {
                this.showInfo = res.data.now
            })
        }
    },
    onLoad() {
        this.loadInfo(this.$root.$mp.query.id)
    }
};
</script>

<style scoped lang="stylus">
.showInfo
    padding: 30rpx;
    h1
        font-size: 48rpx;
        text-align: center;
        color: #000;
    .des
        color: #999;
        font-size: 24rpx;
        line-height: 100rpx;
        span
            margin-right: 20rpx;
</style>
