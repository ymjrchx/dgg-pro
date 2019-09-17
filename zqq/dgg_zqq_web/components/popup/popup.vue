<template>
    <div class="baseZoom" :class="{'zoom': isFixed}" v-if="isShow">
        <transition name="fadeIn">
            <div class="content" v-if="type != 'confirm'" :class="{'w100': type == 'loading'}">
                <i class="iconfont" v-if="icon" :class="{'loading': type == 'loading'}" v-html="icon"></i>
                <p>{{content}}</p>
            </div>
        </transition>
        <transition name="fadeIn">
            <div class="content confirm" v-if="type == 'confirm'">
                <em class="icon-close" @click="close"></em>
                <span class="title">{{ title ? title : "提示"}}</span>
                <p v-html="content"></p>
                <div class="btnBox">
                    <button @click="sureFunc(false)" class="cancel" v-if='cancel'>取消</button>
                    <button @click="sureFunc(true)" class="sure">确认</button>
                </div>
            </div>
        </transition>
    </div>
</template>

<script>
export default {
    data() {
        return {
            isFixed: true,
            isShow: true,
            icon: undefined,
            type: "",
            title: "",
            cancel: true
        };
    },
    created() {},
    mounted() {
        if (this.time) {
            t && clearTimeout(t);
            let t = setTimeout(() => {
                this.close();
            }, this.time);
        }
    },
    methods: {
        close() {
            this.isShow = false;
        },
        sureFunc(bool) {
            this.isShow = false;
            this.success(bool);
        }
    }
};
</script>
<style scoped>
.baseZoom{
    position: fixed;
    z-index: 999;
    top: 30%;
    width: 200px;
    left: 50%;
    margin-left: -100px;
}
.zoom {
    position: fixed;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    width: 100%;
    margin: 0;
    z-index: 999;
    line-height: 100%;
    text-align: center;
}

.baseZoom .content.w100 {
    width: 80px;
    margin-left: -40px;
}
.baseZoom .content {
    box-sizing: content-box;
    background: rgba(0, 0, 0, 0.8);
    display: block;
    padding: 20px;
    width: 200px;
    border-radius: 5px;
    vertical-align: middle;
    line-height: 30px;
    z-index: 102;
    left: 50%;
    margin-left: -100px;
    position: fixed;
    top: 30%;
    text-align: center;
    color: #fff;
}
.baseZoom .content i {
    font-size: 40px;
    margin-top: 10px;
    display: inline-block;
}
@keyframes loading {
    0% {
        transform: rotate(0deg);
    }
    50% {
        transform: rotate(180deg);
    }
    100% {
        transform: rotate(360deg);
    }
}
.loading {
    animation: loading 2s linear infinite;
}
.baseZoom .confirm {
    position: relative;
    background: #fff;
    border: 1px solid #fd7d22;
    color: #303133;
    padding: 10px 20px;
    width: 350px;
}
.baseZoom .confirm .icon-close {
    display: block;
    width: 23px;
    height: 23px;
    position: absolute;
    top: 4px;
    right: 4px;
    background: url("../../assets/images/main/close.png") 50% no-repeat;
    cursor: pointer;
}
.baseZoom .confirm p {
    padding: 10px 0;
    text-align: left;
}
.baseZoom .confirm .title {
    display: block;
    text-align: left;
    font-size: 16px;
}
.confirm .btnBox {
    text-align: right;
}
.confirm .btnBox button {
    width: 60px;
    text-align: center;
    height: 24px;
    line-height: 24px;
    margin-left: 20px;
    cursor: pointer;
}
.confirm .btnBox .cancel {
    background: #dddddd;
}
.confirm .btnBox .sure {
    background: #fd7d22;
    color: #fff;
}
</style>
