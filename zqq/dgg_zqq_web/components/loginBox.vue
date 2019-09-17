<template>
    <transition name="fadeIn">
        <div class="ntlk-main masking" v-show="isShow">
            <div class="ntalk-wrap">
                <div class="layui-layer-title">登录知千秋</div>
                <div class="layui-layer-content">
                    <loginTemplate :_that='this'  @backFn="loginBack" />
                </div>
                <span class="layui-layer-setwin" @click="close">
                </span>
            </div>
        </div>
    </transition>
</template>

<script>
import { Http } from "~/plugins/axios.js";
import loginTemplate from "~/pages/passport/login";
export default {
    props: {
        isShow: {
            type: Boolean,
            default: true
        }
    },
    components: {
        loginTemplate
    },
    data() {
        return {};
    },
    watch: {
        isShow() {
            if (this.isShow) {
                document.getElementsByTagName("body")[0].style.cssText =
                    "overflow:hidden";
            } else {
                document.getElementsByTagName("body")[0].style.cssText =
                    "overflow:auto";
            }
        }
    },
    mounted() {},
    methods: {
        close() {
            this.$emit("close");
        },
        loginBack () {
            this.$emit("backFn");
        }
    }
};
</script>
<style scoped>
.masking {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.3);
    z-index: 110;
}
.ntalk-wrap {
    position: fixed;
    left: 50%;
    top: 50%;
    margin-left: -230px;
    transform: translateY(-50%);
    width: 460px;
    height: 540px;
    padding-bottom: 14px;
    background: #fff;
    box-shadow: 0 0 5px 1px rgba(9, 9, 10, 0.1);
    border-radius: 4px;
    z-index: 120;
}
.layui-layer-title {
    height: 52px;
    line-height: 42px;
    border-bottom: none;
    font-size: 18px;
    color: #f08b2f;
    text-align: left;
    overflow: hidden;
    background-color: #fff;
    border-radius: 4px 4px 0 0;
    padding: 0 20px;
    padding-top: 8px;
    border-bottom: 1px solid #eeeeee;
}
.layui-layer-setwin {
    position: absolute;
    right: 15px;
    top: 15px;
    font-size: 0;
    line-height: initial;
    width: 23px;
    height: 23px;
    background: url("../assets/images/main/close.png") center no-repeat;
    cursor: pointer;
}
.layui-layer-content {
    position: relative;
}
.layui-layer-content .login-panel {
    box-shadow: 0 0 0 #fff;
    margin: auto;
}

/* //表单 */
</style>
