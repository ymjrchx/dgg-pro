<template>
    <div class="hoverMenu drop-body" :class="{'showIng':isShow}" @mouseenter="showBox(1)" @mouseleave="showBox(0)" :style="styleObj">
        <ul class="cont cont-1">
            <li class="menu" v-for='(item,idx) in docArr' @mouseenter="menuTab =idx" @mouseleave="menuTab =100" :key='idx'>
                <h3 class="menu-title">
                    <a @click="gogo(item.code, item.ext4)">{{item.name}}
                        <i class="icon icon-link"></i>
                    </a>
                </h3>
                <div class="menu-bodyer" v-show="menuTab == idx">
                    <h4>
                        <a @click="gogo(item.code, item.ext4)">{{item.name}}</a>
                    </h4>
                    <dl v-for='(item2,idx2) in item.sonArr' :key='idx-idx2'>
                        <dt>
                            <a>{{item2.name}}</a>
                        </dt>
                        <dd>
                            <a v-for='(item3,idx3) in item2.sonArr' :key='idx-idx2-idx3' @click="gogo(item3.code, item3.ext4)">{{item3.name}}</a>
                        </dd>
                    </dl>
                </div>
            </li>
        </ul>
        <ul class="cont cont-2">

            <li class="menu">
                <h3 class="menu-title">
                    <a href="javascript:void(0);" @click="$store.commit('isShowToolBox',1)">商标工具</a>
                </h3>
            </li>
            <li class="menu">
                <h3 class="menu-title">
                    <a target="_blank" href="/about/4.html" rel="nofollow">帮助中心</a>
                </h3>
            </li>
            <li class="menu">
                <h3 class="menu-title">
                    <a href="/xw/trade_news.html" target="_blank">商标资讯</a>
                </h3>
            </li>
        </ul>
        <ul class="cont cont-3">
            <li class="menu">
                <h3 class="menu-title">
                    <a href="javascript:void(0)">
                        <i class="icon icon-telphone"></i>400-871-9995</a>
                </h3>
            </li>
            <li class="menu">
                <h3 class="menu-title">
                    <a href="javascript:void(0)" onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')">
                        <i class="icon icon-message"></i>商标咨询</a>
                </h3>
            </li>
        </ul>
    </div>
</template>
<script>
import { openNewPage } from "~/assets/js/common.js";
import { Http } from "~/plugins/axios.js";
export default {
    props: {
        isShow: 0,
        styleObj:""
    },
    data() {
        return {
            menuTab: 100,
            docArr: []
        };
    },
    created() {},
    mounted() {
        // document.domain = "zhiqianqiu.com";
        this.getDoc(); //获取字典
    },
    methods: {
        showBox(num) {
            this.$emit("mouseFunc", num);
        },
        getDoc() {
            Http("navigation/getTreeData", {}, "get").then(res => {
                // console.log("字典12", res.data.data.sonArr);
                if (res.data.code == 0) {
                    this.docArr = res.data.data.sonArr;
                    this.$store.commit("SET_DOCARR", this.docArr);
                }
            });
        },
        gogo(code, id) {
            let url = "/",
                obj = "";
            switch (code) {
                case "navigation_trademark":
                    url = "/extension";
                    break; //商标业务
                case "navigation_trademark_query_01":
                    url = "/shangbiao/freesearch";
                    break; //免费商标查询
                case "navigation_patent":
                    url = "/patent";
                    break; //专利业务
                case "navigation_copyright":
                    url = "/copyright";
                    break; //版权业务
                default:
                    url = "/show";
                    obj = { code: code, id: id };
                    break;
            }
            openNewPage(this, url, obj);
        }
    }
};
</script>
<style scoped>
* {
    box-sizing: border-box;
}
.hoverMenu {
    position: absolute;
    width: 280px;
    height: 900px;
    background-color: #f7f8fa;
    z-index: 101;
    top: 70px;
    left: -300px;
    transition: 0.3s all;
    opacity: 0;
    line-height: 1em;
    padding: 0 0;
}

.showIng.hoverMenu {
    left: 0;
    opacity: 1;
}

/*下拉全部导航*/

.drop-body .menu {
    padding: 0 20px;
}
.drop-body .menu .menu-title {
    position: relative;
    z-index: 0;
    cursor: pointer;
}
.drop-body .cont h3 {
    font-weight: 100;
}
.drop-body .cont.cont-1 .menu  .menu-title a {
    padding: 24px 0;
}
.drop-body .cont .menu .menu-title a {
    display: block;
    padding: 14px 0;
    color: #000;
    font-size: 14px;
    background-position: right center;
    background-repeat: no-repeat;
    background-size: 14px;
}
.drop-body .cont .menu .menu-title a:hover {
    color: #ff6600;
}
.drop-body .cont-1 .menu .menu-title a {
    background-image: url("../assets/images/main/right3.png");
    background-size: 14px;
}
.drop-body .cont-1 .menu:hover .menu-title a {
    background-image: url("../assets/images/main/right2.png");
    color: #ff6600 !important;
    background-size: 14px;
}
.drop-body .cont .menu .menu-title .icon {
    display: inline-block;
    width: 15px;
    height: 15px;
    vertical-align: middle;
    margin-right: 5px;
    position: relative;
    top: -2px;
    background-position: center;
    background-repeat: no-repeat;
}
.drop-body .cont-1 .menu .menu-title .icon-link {
    background-image: url("../assets/images/main/icon-link.png");
}
/* 闪现框 */
.drop-body .menu .menu-bodyer {
    display: block;
    position: absolute;
    top: 0px;
    left: 280px;
    width: 640px;
    height: 900px;
    overflow: auto;
    padding: 0 20px 20px;
    background-color: #f7f8fa;
    border-left: solid 1px #ccc;
    z-index: 1;
}
.drop-body .menu .menu-bodyer h4 {
    padding: 20px 0;
    border-bottom: solid 1px #ddd;
    color: #000;
    font-size: 16px;
}
.drop-body .menu .menu-bodyer dl dt {
    padding: 25px 0 10px;
}
.drop-body .menu .menu-bodyer dl dt a {
    color: #999;
}
.drop-body .menu .menu-bodyer dl dd {
    overflow: hidden;
}
.drop-body .menu .menu-bodyer dl dd a {
    font-size: 12px;
    color: #000;
    display: block;
    position: relative;
    float: left;
    margin: 10px 56px 10px 0;
}
.drop-body .menu .menu-bodyer dl dd a::after {
    content: "";
    position: absolute;
    right: -28px;
    top: 0;
    width: 1px;
    height: 100%;
    background-color: #ccc;
}
.drop-body .menu .menu-bodyer dl dd a:nth-last-child(1)::after {
    display: none;
}
.drop-body .menu .menu-bodyer dl dd a:hover {
    color: #f08b2f;
}

/* //第二个ul */
.drop-body .cont-2 {
    border-top: solid 1px #ddd;
    border-bottom: solid 1px #ddd;
    margin: 0 0;
    padding: 15px 0 15px;
}
/* //第三个 */
.drop-body .cont-3{
    padding-top: 15px;
}
.drop-body .cont-3 .menu .menu-title .icon.icon-telphone {
    background-image: url("../assets/images/main/icon-telphone.png");
}
.drop-body .cont-3 .menu .menu-title .icon.icon-message {
    background-image: url("../assets/images/main/icon-message.png");
}
</style>
