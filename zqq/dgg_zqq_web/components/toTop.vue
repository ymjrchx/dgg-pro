<template>
    <div>
        <transition name="fadeIn">
            <div class="fixedNav" v-show="showNav" :class="{'right':widthF < 1300}">
                <div class="ask">
                    建<br/>议<br/>咨<br/>询
                    <div class="list">
                        <div class="bdoyer">
                            <dl onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')">
                                <dt><img src="../assets/images/icon14.png"></dt>
                                <dd>
                                    <h6>售前咨询</h6>
                                    <p>商标、专利、版权业务</p>
                                </dd>
                            </dl>
                            <dl onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')">
                                <dt><img src="../assets/images/icon15.png"></dt>
                                <dd>
                                    <h6>售后服务</h6>
                                    <p>业务进度、官文与发票业务</p>
                                </dd>
                            </dl>
                            <dl>
                                <dt><img src="../assets/images/icon17.png"></dt>
                                <dd>
                                    <h6>服务热线</h6>
                                    <p class="color">400-871-9995</p>
                                </dd>
                            </dl>
                            <dl onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')">
                                <dt><img src="../assets/images/icon18.png"></dt>
                                <dd>
                                    <h6>意见反馈</h6>
                                    <p>投诉、建议</p>
                                </dd>
                            </dl>
                        </div>
                    </div>
                </div>

                <div class="topIcon" @click="goTop"></div>
            </div>
        </transition>
    </div>
</template>
  <script>
import { Jump } from "~/assets/js/common";
import {push_l} from "~/assets/js/common.js";
export default {
    props: {
        isShow: 0
    },
    data() {
        return {
            showNav: 0,
            widthF: 1300
        };
    },
    created() {},
    mounted() {
         push_l()
        this.widthF = document.documentElement.clientWidth;
        if (!this.isShow) window.addEventListener("scroll", this.handleScroll);
        else this.showNav = this.isShow;
    },
    methods: {
        handleScroll() {
            var scrollTop =
                window.pageYOffset ||
                document.documentElement.scrollTop ||
                document.body.scrollTop;
            if (scrollTop >= 200) {
                this.showNav = 1;
            } else {
                this.showNav = 0;
            }
        },
        goTop() {
            Jump(document.getElementsByTagName("body")[0]);
        }
    },
    destroyed() {
        window.removeEventListener("scroll", this.handleScroll);
    }
};
</script>
<style scoped>
    *{
        box-sizing: border-box;
    }
.fixedNav {
    position: fixed;
    bottom: 60px;
    right: 60px;
    background-color: #fff;
    z-index: 99;
    width: 46px;
/*    border: 1px solid #eaeaea;*/
}
.fixedNav.right {
    right: 0;
}
.fixedNav .ask {
    text-align: center;
    padding: 16px;
    color: #fd7d22;
    cursor: pointer;
    font-weight:700;
    font-size:16px;
    background: #FE9547;
    color: #fff;
    transition: all .2s;
}
.fixedNav .ask:hover {
    background: #fd7d22;
}

.fixedNav .ask:hover .list {
    display: block;
}
.topIcon {
    background: #fff url("../assets/images/top.png") center center no-repeat;
    background-size: 28px;
    width: 100%;
    height: 46px;
    cursor: pointer;
/*    border-top: 1px solid #eaeaea;*/
        border: 1px solid #eaeaea;
}
.topIcon:hover {
    background: #fff url("../assets/images/top2.png") center center no-repeat;
    background-size: 28px;
}
.list {
    display: none;
    width: 250px;
    position: absolute;
    right: 46px;
    top: -30px;
}
.bdoyer {
    width: 230px;
    padding: 5px 0 15px;
    background: #fff;
    text-align: left;
    box-sizing: border-box;
    box-shadow: 0 0 10px 3px #ddd;
}
.bdoyer:after {
    content: "";
    position: absolute;
    width: 0;
    height: 0;
    display: block;
    border: 10px solid #fff;
    right: 0;
    top: 40%;
    border-color: transparent transparent transparent #fff;
}
.bdoyer dl {
    padding: 14px 0 0;
    overflow: hidden;
    cursor: pointer;
    box-sizing: border-box;
}
.bdoyer dt {
    float: left;
    width: 55px;
    padding: 4px 0 0 25px;
    box-sizing: border-box;
}
.bdoyer dd {
    float: left;
    width: 155px;
    box-sizing: border-box;
}
.bdoyer dd h6 {
    font-size: 16px;
    color: #333;
}
.bdoyer dd p {
    font-size: 10px;
    color: #999;
}
.bdoyer dd .color {
    font-size: 10px;
    color: #ff7200;
}
</style>
