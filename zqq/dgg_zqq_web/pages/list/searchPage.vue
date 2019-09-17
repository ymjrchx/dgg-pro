<template>
    <div class="wrapper">
        <mini :hasFloor="1" />
        <div class="w zqq_b_center ">
            <h1 class=" mb8"><img src="../../assets/images/search-main-title.png" alt="知千秋"></h1>
            <small class="f16">以服务为基点，重新定义知识产权生态链</small>
            <div class="main_search mb60 clear2">
                <ul class="search_style clear2">
                    <li v-for="(item,idx) in navArr" @click=" styleIdx = idx" :key='idx' :class="{'active': styleIdx == idx}">{{item}}</li>
                </ul>
                <div class="searchBox clear2">
                    <input type="text" maxlength="50" :placeholder="holderArr[styleIdx]" class="fl" v-model="keyWord" @keyup.enter='goList'>
                    <span class="fl picImg">
                        <el-upload v-show='styleIdx == 0' :action="$store.state.upLoadUrl" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                            <span class="pic"></span>
                        </el-upload>
                    </span>
                    <input type="button" value="搜索" class="fl" @click="goList">
                </div>
                <ul class="items">
                    <li class="menu">
                        <a target="_blank" href="/sbzc">
                            <i class="icon icon-tools-znzc fade_swing"></i>
                            <span class="icon-text">注册成功率测算</span>
                        </a>
                    </li>
                    <li class="menu">
                        <a target="_blank" href="/classify">
                            <i class="icon icon-tools-dzdj fade_swing"></i>
                            <span class="icon-text">商标分类查询</span>
                        </a>
                    </li>
                    <li class="menu">
                        <a target="_blank" href="/order/order">
                            <i class="icon icon-tools-sbjk fade_swing"></i>
                            <span class="icon-text">商标智能注册</span>
                        </a>
                    </li>
                    <li class="menu">
                        <a target="_blank" href="/brandmanage">
                            <i class="icon icon-tools-sbmanage fade_swing"></i>
                            <span class="icon-text">商标管理</span>
                        </a>
                    </li>
                    <li class="menu">
                        <a target="_blank" href="/notice">
                            <i class="icon icon-tools-csgg fade_swing"></i>
                            <span class="icon-text">初审公告</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- <div class="roomBox" v-if='hotArr'>
            <span class="opti" v-for='(item,key,idx) in hotArr' :key='idx' :style="styleArr[idx]">{{key}}</span>
        </div> -->
        <canvas ref="search" width="1920" height="300"></canvas>

        <foot v-show="0" />
        <toTop :isShow="'1'" />
    </div>
</template>
<script>
import mini from "~/components/header/mini";
import foot from "~/components/foot/searchFoot";
import toTop from "~/components/toTop";
import popup from "~/components/popup";
import { uploadImg } from "~/assets/js/common.js";
import Cookies from "js-cookie";
export default {
    layout: "empt",
    head() {
        return {
            title: "专注于商标查询、专利查询-知千秋官",
            meta: [
                {
                    name: "keywords",
                    hid: "keywords",
                    content:
                        "商标注册，商标免费查询，商标注册查询，商标注册流程及费用，中国商标网，专利申请，专利检索，发明专利"
                },
                {
                    name: "description",
                    hid: "description",
                    content:
                        "知千秋致力于知千秋致力于运营大数据技术、人工智能等技术重新定义知识产权生态链。提供商标查询,商标注册,专利申请,专利查询,版权登记全流程服务。提供免费商标查询Saas工具,智能商标注册0服务费省钱到底,商标查询,商标注册监控,专利申请等服务获得客户的高度认可"
                }
            ]
        };
    },
    components: {
        mini,
        foot,
        toTop
    },
    data() {
        return {
            navArr: ["商标", "专利", "企业"],
            holderArr: [
                "请输入商标名称、申请号、申请人等信息",
                "请输入专利名称",
                "请输入企业名称"
            ],
            keyWord: "",
            styleIdx: 0,
            Loading: "",
            hotArr: [],
            styleArr: []
        };
    },
    mounted() {
        // this.produceArr();
        var search = this.$refs.search;
        var ctx = search.getContext("2d");
        var that = this;
        var timer;
        var pathObj = [
            {
                start: 0,
                defaultStart: 0,
                speed: 1,
                color: "#ffeedf"
            },
            {
                start: -200,
                defaultStart: -200,
                speed: 2,
                color: "#f3c195"
            },
            {
                start: -400,
                defaultStart: -400,
                speed: 3,
                color: "#fba75d"
            }
        ];

        var keywords = ["百度", "成都顶呱呱", "苦笑", "参数", "历史", "滴滴"];

        var keywordPosition = [];
        window.requestAnimationFrame =
            window.requestAnimationFrame ||
            function(fn) {
                return setTimeout(fn, 1000 / 60);
            };

        function initPath() {
            search.height = search.height;
            keywordPosition = [];
            pathObj.map(function(item, index) {
                creatPath(item, index + 1);
            });
            timer = requestAnimationFrame(initPath);
        }

        // 生成路劲
        function creatPath(obj, lever) {
            obj.start = obj.start - lever / 2;
            ctx.beginPath();
            ctx.moveTo(obj.start, 100 + lever * 50);
            ctx.quadraticCurveTo(
                400 + obj.start,
                50 + lever * 50,
                800 + obj.start,
                100 + lever * 50
            );
            ctx.quadraticCurveTo(
                1200 + obj.start,
                150 + lever * 50,
                1600 + obj.start,
                100 + lever * 50
            );
            ctx.quadraticCurveTo(
                2000 + obj.start,
                50 + lever * 50,
                2400 + obj.start,
                100 + lever * 50
            );
            ctx.quadraticCurveTo(
                2800 + obj.start,
                150 + lever * 50,
                3200 + obj.start,
                100 + lever * 50
            );
            ctx.quadraticCurveTo(
                3600 + obj.start,
                50 + lever * 50,
                4000 + obj.start,
                100 + lever * 50
            );
            ctx.lineTo(4000 + obj.start, 300);
            ctx.lineTo(0, 300);
            ctx.fillStyle = obj.color;
            ctx.closePath();
            ctx.fill();
            if (obj.start <= -1600 + obj.defaultStart) {
                obj.start = obj.defaultStart;
            }

            // 关键字
            keywords.map(function(item, index) {
                if (index >= (lever - 1) * 2 && index <= 2 * lever - 1) {
                    createText(
                        keywords[index],
                        400 + (index - 2 * (lever - 1)) * 800 + obj.start,
                        (index % 2 == 0 ? 50 : 100) + lever * 50
                    );
                    createText(
                        keywords[index],
                        400 + (index + 2 * (2 - lever)) * 800 + obj.start,
                        (index % 2 == 0 ? 50 : 100) + lever * 50
                    );
                    createText(
                        keywords[index],
                        400 + (index + 2 * (3 - lever)) * 800 + obj.start,
                        (index % 2 == 0 ? 50 : 100) + lever * 50
                    );
                }
            });
        }

        // 生成文字
        function createText(txt, x, y) {
            // 文字
            ctx.beginPath();
            ctx.fillStyle = "#d66000";
            ctx.font = "14px microsoft yahei";
            ctx.fillText(txt, x, y + 15);
            var w = ctx.measureText(txt).width;
            ctx.fillStyle = "#fdb277";
            ctx.arc(x + w / 2, y + 25, 6, 0, 2 * Math.PI);
            ctx.fill();
            ctx.beginPath();
            ctx.fillStyle = "#d66000";
            ctx.arc(x + w / 2, y + 25, 3, 0, 2 * Math.PI);
            ctx.fill();

            // 记录位置
            keywordPosition.push({
                name: txt,
                width: w,
                x: x,
                y: y
            });
            // requestAnimationFrame(createText(txt, x, y))
        }

        search.addEventListener("mousemove", function(e) {
            cancelAnimationFrame(timer);
            // e.target.style='cursor: pointer'
            var x = e.offsetX;
            var y = e.offsetY;
            keywordPosition.every(function(item) {
                if (
                    item.x < x &&
                    item.x + item.width > x &&
                    item.y < y &&
                    item.y + 30 > y
                ) {
                    e.target.style = "cursor: pointer";
                    return false;
                }
                e.target.style = "cursor: auto";
                return true;
            });
        });

        search.addEventListener("click", function(e) {
            var x = e.offsetX;
            var y = e.offsetY;
            keywordPosition.every(function(item) {
                if (
                    item.x < x &&
                    item.x + item.width > x &&
                    item.y < y &&
                    item.y + 30 > y
                ) {
                    that.keyWord = item.name;
                    that.goList();
                    return false;
                }
                return true;
            });
        });
        search.addEventListener("mouseout", function(e) {
            //e.target.style='cursor: auto'
            initPath();
        });

        initPath();
    },
    methods: {
        goList() {
            this.keyWord = this.keyWord.replace(/ /g, "");
            if (!this.keyWord) {
                popup.created({
                    content: "搜索关键字不能为空",
                    time: 2000
                });
                this.showLogin = true;
                return;
            }

            if (
                /[`~!@#$%^&*_\-+=<>?:"\/'\\[\]·~！@#￥%……&*——\-+=？：.]/im.test(
                    this.keyWord
                )
            ) {
                popup.created({
                    content: "关键字不能包含特殊字符",
                    time: 2000
                });
                return false;
            }
            if (this.styleIdx === 0)
                this.$router.push({
                    path: "/loading",
                    query: { url: "/shangbiao/" + this.keyWord + ".html" }
                });
            else if (this.styleIdx === 1)
                this.$router.push({
                    path: "/loading",
                    query: {
                        url: "/patentSearch/" + this.keyWord + ".html"
                    }
                });
            else if (this.styleIdx === 2) {
                window.open(
                    "https://dqc.dgg.net/#/Company?type=all&keyWord=" +
                        this.keyWord,
                    "_blank"
                );
                this.Loading.close();
            }
        },
        //upload上传图片
        handleAvatarSuccess(res, file) {
            this.Loading.close();
            uploadImg(this, res);
        },
        beforeAvatarUpload(file) {
            const isJPG =
                file.type === "image/jpeg" || file.type === "image/png";
            const isLt2M = file.size / 1024 / 1024 < 5;

            if (!isJPG) {
                popup.created({
                    content: "上传图片只能是JPG或PNG格式!",
                    time: 2000
                });
            }
            if (!isLt2M) {
                popup.created({
                    content: "上传图片大小不能超过 5MB!",
                    time: 2000
                });
            }
            if (isJPG && isLt2M) {
                this.Loading = this.$Popup.created({
                    content: "上传中...",
                    type: "loading",
                    icon: "&#xe61c"
                });
            }
            return isJPG && isLt2M;
        },
        getHotWords() {
            this.$Http(
                `seo/rankOfKeyword`,
                {
                    pullCount: 10,
                    searchType: "brand"
                },
                "post"
            ).then(res => {
                this.hotArr = res.data.data;
            });
        }
    },
    destroyed() {
        if (this.Loading) this.Loading.close();
    }
};
</script>
<style scoped>
canvas {
    position: fixed;
    bottom: 0;
}
.wrapper {
    width: 100%;
    overflow: hidden;
    min-width: 1300px;
}
.zqq_b_center {
    margin: auto;
    text-align: center;
    color: #565656;
    margin-top: 110px;
    position: relative;
    z-index: 9;
}

.zqq_b_center h1 {
    font-size: 56px;
    margin-bottom: 20px;
}

.searchBox {
    width: 900px;
    margin: auto;
}

.mb60 {
    margin-top: 56px;
}
/* 种类 */
.main_search {
    /*margin-bottom: 70px;*/
}
.search_style {
    width: 800px;
    margin: auto;
    padding-left: 16px;
    margin-bottom: 14px;
}

.search_style > li {
    float: left;
    margin-right: 66px;
    font-size: 16px;
    cursor: pointer;
    padding: 3px 12px;
    border-radius: 3px;
}

.search_style > li.active {
    background-color: #f08c2f;
    color: #fff;
    position: relative;
}
.search_style > li.active:before {
    content: " ";
    position: absolute;
    bottom: -12px;
    left: 50%;
    margin-left: -6px;
    width: 0px;
    border: 6px solid transparent;
    border-top-color: #f08c2f;
}
/* 搜索盒子 */
.searchBox input {
    height: 46px;
    line-height: 46px;
    outline: none;
    border: none;
}

.searchBox input:first-child {
    width: 670px;
    border-radius: 4px 0 0 4px;
    color: #333;
    padding: 0;
    padding-left: 18px;
    font-size: 16px;
    border: 1px solid #999;
    border-right: none;
}

.searchBox input:first-child::-webkit-input-placeholder {
    color: #999;
}
.searchBox .picImg {
    width: 60px;
    height: 46px;
    background: #fff;
    border-top: 1px solid #999;
    border-bottom: 1px solid #999;
}
.searchBox .pic {
    display: block;
    width: 60px;
    height: 44px;
    background: #fff url("../../assets/images/icon_photo.png") center center
        no-repeat;
    cursor: pointer;
}
.searchBox input:last-child {
    width: 166px;
    color: #fff;
    background: #fd7d22;
    border-radius: 0 4px 4px 0;
    font-size: 18px;
    letter-spacing: 3px;
    cursor: pointer;
}
* {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}
/* //图标 */
.items {
    text-align: center;
    padding: 100px 0 15px;
}
.items .menu {
    display: inline-block;
    width: 150px;
    height: 155px;
}
.items .menu .icon {
    display: inline-block;
    width: 92px;
    height: 92px;
    background-position: center;
}
.items .menu .icon-text {
    display: block;
    color: #f08b2f;
    font-size: 16px;
}
.msgs {
    text-align: center;
    color: #666;
    font-size: 12px;
    padding: 10px 0 30px;
}

.items {
    text-align: center;
}
.items > li {
    display: inline-block;
    margin: 0 30px;
}

.items .menu .icon-tools-znzc {
    background: url("../../assets/images/main/nav-znzc-hover.png") center center
        no-repeat;
    background-size: 70px;
}
.items .menu .icon-tools-dzdj {
    background: url("../../assets/images/main/nav-flcx-hover.png") center center
        no-repeat;
    background-size: 70px;
}
.items .menu .icon-tools-sbjk {
    background: url("../../assets/images/main/nav-jk-hover.png") center center
        no-repeat;
    background-size: 70px;
}
.items .menu .icon-tools-sbmanage {
    background: url("../../assets/images/main/nav-gl-hover.png") center center
        no-repeat;
    background-size: 70px;
}
.items .menu .icon-tools-csgg {
    background: url("../../assets/images/main/nav-csgg-hover.png") center center
        no-repeat;
    background-size: 70px;
}
/* //动画盒子 */
.roomBox {
    position: fixed;
    width: 100%;
    height: 300px;
    bottom: 0;
    left: 0;
}
.roomBox span {
    position: absolute;
}
</style>
