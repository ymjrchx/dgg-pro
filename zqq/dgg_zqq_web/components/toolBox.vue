<template>
    <div>
        <transition name="fadeIn">
            <div id="websiteTools" class="show" v-show='$store.state.toolBox'>
                <i class="opacity"></i>
                <div class="cont">
                    <i class="icon-close" @click="close(0)"></i>
                    <h3 class="cont-header">
                        <span class="f20 weight">商标工具
                            <i class="icon"></i>
                        </span>
                    </h3>
                    <ul class="items">
                        <!-- <li class="menu">
                            <a target="_blank" href="/shangbiao/freesearch">
                                <i class="icon icon-tools-sssearch"></i>
                                <span class="icon-text">商标查询</span>
                            </a>
                        </li> -->
                        <li class="menu">
                            <a target="_blank" href="/sbzc">
                                <i class="icon icon-tools-znzc"></i>
                                <span class="icon-text">注册成功率测算</span>
                            </a>
                        </li>
                        <li class="menu">
                            <a target="_blank" href="/classify">
                                <i class="icon icon-tools-dzdj"></i>
                                <span class="icon-text">商标分类查询</span>
                            </a>
                        </li>
                       
                        <!-- <li class="menu" @click="goOrder">
                            <a href="javascript:void(0);">
                                <i class="icon icon-tools-sbjk"></i>
                                <span class="icon-text">商标智能注册</span>
                            </a>
                        </li> -->
                        <!-- <li class="menu">
                            <a target="_blank" href="/brandmanage">
                                <i class="icon icon-tools-sbmanage"></i>
                                <span class="icon-text">商标管理</span>
                            </a>
                        </li> -->
                        <li class="menu">
                            <a target="_blank" href="/notice">
                                <i class="icon icon-tools-csgg"></i>
                                <span class="icon-text">初审公告</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </transition>
    </div>
</template>
  <script>
import Cookies from "js-cookie";
export default {
    data() {
        return {
            showNav: 0,
            showInfo: ""
        };
    },
    created() {},
    mounted() {
        this.loadZn();
    },
    methods: {
        close(num) {
            this.$store.commit("isShowToolBox", 0);
        },
        loadZn() {
            this.$Http(`serviceItem/findServiceItemByNumber`, {
                numbers: "S7741102214293331969"
            }).then(res => {
                this.showInfo = res.data.data[0];
            });
        },
        goOrder() {
            if (!this.showInfo) {
                return;
            }
            Cookies.set("product", {
                name: this.showInfo.serviceItem.name,
                serviceId: this.showInfo.serviceItem.id,
                serviceAttrId: "",
                num: 1,
                officialPrice: this.showInfo.serviceItem.officialPrice,
                servicePrice: this.showInfo.serviceItem.servicePrice,
                total:
                    this.showInfo.serviceItem.officialPrice +
                    this.showInfo.serviceItem.servicePrice
            });
            window.open("/order/auto");
        }
    },
    destroyed() {}
};
</script>
<style scoped>
#websiteTools {
    /* display: none; */
    position: fixed;
    top: 0px;
    left: 0px;
    width: 100%;
    height: 100%;
    z-index: 110;
}
#websiteTools .opacity {
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
    height: 100%;
    z-index: 2;
    background-color: #000;
    opacity: 0.2;
    filter: alpha(opacity=20);
}
#websiteTools .cont {
    position: absolute;
    top: 42%;
    left: 50%;
    width: 600px;
    z-index: 7;
    background-color: #fff;
    margin: -210px 0 0 -380px;
    border: solid 1px #ddd;
}
#websiteTools .cont .icon-close {
    display: block;
    width: 23px;
    height: 23px;
    position: absolute;
    top: 20px;
    right: 20px;
    background: url("../assets/images/main/close.png") center no-repeat;
    cursor: pointer;
}
#websiteTools .cont .cont-header {
    text-align: center;
    font-size: 20px;
    color: #000;
    margin-top: 20px;
}
#websiteTools .cont .cont-header span {
    display: inline-block;
    position: relative;
    border-bottom: solid 1px #ccc;
    padding: 0 150px 20px;
}
#websiteTools .cont .cont-header .icon {
    display: block;
    width: 0px;
    height: 0px;
    border: solid 9px transparent;
    border-top: solid 10px #f08b2f;
    position: absolute;
    top: 46px;
    left: 50%;
    margin-left: -8px;
}
#websiteTools .cont .items {
    text-align: center;
    padding: 45px 0 15px;
}
#websiteTools .cont .items .menu {
    display: inline-block;
    width: 180px;
    height: 140px;
}
#websiteTools .cont .items .menu .icon {
    display: inline-block;
    width: 92px;
    height: 92px;
    background-position: center;
}
#websiteTools .cont .items .menu .icon-text {
    display: block;
    color: #333;
    padding-top: 3px;
}
#websiteTools .cont .msgs {
    text-align: center;
    color: #666;
    font-size: 12px;
    padding: 10px 0 30px;
}
#websiteTools .cont .items .menu:hover .icon-text {
    color: #f08b2f;
}
#websiteTools .cont .items .menu:hover .icon-tools-sssearch {
    background-image: url("../assets/images/main/nav-search-hover.png");
    background-size: 92px;
}
#websiteTools .cont .items .menu:hover .icon-tools-znzc {
    background-image: url("../assets/images/main/nav-znzc-hover.png");
    background-size: 92px;
}
#websiteTools .cont .items .menu:hover .icon-tools-dzdj {
    background-image: url("../assets/images/main/nav-flcx-hover.png");
    background-size: 92px;
}
#websiteTools .cont .items .menu:hover .icon-tools-sbjk {
    background-image: url("../assets/images/main/nav-jk-hover.png");
    background-size: 92px;
}
#websiteTools .cont .items .menu:hover .icon-tools-sbmanage {
    background-image: url("../assets/images/main/nav-gl-hover.png");
    background-size: 92px;
}
#websiteTools .cont .items .menu:hover .icon-tools-csgg {
    background-image: url("../assets/images/main/nav-csgg-hover.png");
    background-size: 92px;
}

.icon-tools-znzc {
    background-image: url("../assets/images/main/nav-znzc.png");
    background-position: center center;
    background-repeat: no-repeat;
}
.icon-tools-sbjk {
    background-image: url("../assets/images/main/nav-jk.png");
    background-position: center center;
    background-repeat: no-repeat;
}
.icon-tools-sssearch {
    background-image: url("../assets/images/main/nav-search.png");
    background-position: center center;
    background-repeat: no-repeat;
}
.icon-tools-sbmanage {
    background-image: url("../assets/images/main/nav-gl.png");
    background-position: center center;
    background-repeat: no-repeat;
}
.icon-tools-csgg {
    background-image: url("../assets/images/main/nav-csgg.png");
    background-position: center center;
    background-repeat: no-repeat;
}
.icon-tools-dzdj {
    background-image: url("../assets/images/main/nav-flcx.png");
    background-position: center center;
    background-repeat: no-repeat;
}
</style>
