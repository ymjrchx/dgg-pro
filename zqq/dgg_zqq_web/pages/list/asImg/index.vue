<template>
    <div>
        <mini/>
        <div class="page-search w2">
            <span class="main-title">商标搜索</span>
            <div class="search-input">
                <input name="" placeholder="请输入商标名称" class="text fl" type="text" v-model="keyWord" @keyup.enter='goSearch'>
                <a @click="goSearch" id="search" class="button search">搜索</a>

                <div style="position: absolute;right: 135px; top: 5px;width: 30px;height: 28px;">
                    <el-upload :action="$store.state.upLoadUrl" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                        <i id="select-btn" class="icon-camera"></i>
                    </el-upload>
                </div>

            </div>
            <div class="search-right">
                <a href="/classify" target="_blank" class="button button-white">商品分类</a>
                <a href="/notice" target="_blank" class="button button-white">初审公告</a>
            </div>
        </div>
        <div class="page-nav" id="brandTitle">
            <div class="page-nav-in w2">
                <a href="javascript:void(0)" v-for="(item,idx) in keyArr" :key="idx" :class="{'active':idx == keyIndex}" @click="switchTab(idx)">{{item.name}}</a>
            </div>
        </div>
        <!-- //搜索正文大头开始 -->
        <section class="w2" style="margin-top:20px">
            <!-- //字典 开始 -->
            <div class="page-form clear2">
                <ul>
                    <li class="search-category" v-for='(val,key,idx) in docData' :class="{'open':openDoc[idx]}" :key='idx'>
                        <a class="btn" @click="openLevel(idx)">更多</a>
                        <label>{{matchName(key)}}</label>
                        <div class="category-show-box">
                            <a href="javascript:void(0)" v-for='(item,idx2) in val' :key='idx2' :class="{'active': (key == 'apply_type_p' && docStyleIdxArr[idx2]) ||
                             ( key == 'apply_year' && applyYear == item.key) ||   ( key == 'lay_status' && layStatus == item.key)}" @click="selectOption(key,idx2,item.key)" v-if=" item.val>0">{{item.key}}({{item.val}})</a>
                        </div>
                    </li>
                </ul>
            </div>
            <!-- 字典结束 -->
            <!-- 列表开始 -->
            <div class="page-content clear2">
                <!-- //左边列表层级 -->
                <div class="page-content-left imglistBox">
                    <template v-if="listData">
                        <div class="search-top" style="height:20px">
                            <!-- 知千秋为您找到相关结果<i>{{total.length}}</i> 个 -->
                        </div>
                        <div class="search-list graphList-result clear2">
                            <dl class="list" v-for='(item,idx) in listData' :key='idx'>
                                <dt>
                                    <img :src="item.tmiPath ? 'http://upload.dgg.net/'+ item.tmiPath : ''" :onerror="logo" alt='知千秋' />
                                    <div class="bgtext">
                                        <a target="_blank" :href="initUrl(item.regNo,item.intCls)">
                                            <ul class="text">
                                                <span>类别： {{item.intCls}}
                                                </span>
                                                <span>当前状态：{{item.status}}</span>
                                                <span>申请人：{{item.applicantCn}}</span>
                                            </ul>
                                        </a>
                                    </div>
                                </dt>
                                <dd>申请号：
                                    <i>{{item.regNo}}</i>
                                </dd>
                            </dl>
                        </div>
                        <!-- start 分页 -->
                        <div class="requireMore" v-show='isRequire'>
                            <span>
                                <i class="iconfont loading">&#xe61c;</i>正在加载更多</span>
                        </div>
                        <!-- end 分页 -->
                    </template>
                    <noFind :keyWord="keyWord" :type="'商标'" v-else/>
                </div>
            </div>
            <!-- 列表结束 -->
        </section>
        <div class="fixedRight" :style="BoxMove">
            <h2 @mousedown="clickDown" @mouseup="clickUp" ref='moveH2'>图形对比</h2>

            <el-upload :action="$store.state.upLoadUrl" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                <div class="moveBox" title="点击更换图片">
                    <img v-if='imgUrl' :src="imgUrl" :onerror="logo" alt="知千秋" title='知千秋'>
                    <img v-else src="../../../assets/images/default.png" alt="知千秋" title='知千秋'>
                </div>
            </el-upload>
        </div>
    </div>
</template>
<script>
import mini from "~/components/header/mini";
import { Http } from "~/plugins/axios.js";
import popup from "~/components/popup";
import img from "~/assets/images/default.png";
import noFind from "~/components/noFind";
import { Jump, uploadImg } from "~/assets/js/common";
import axios from "axios";
import Cookies from "js-cookie";

export default {
    layout: "empt",
    head() {
        return {
            title: `专注于商标查询、专利查询-知千秋官网`,
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
                        "知千秋致力于知千秋致力于运营大数据技术、人工智能等技术重新定义知识产权生态链。提供商标查询,商标注册,专利申请,专利查询,版权登记全流程服务。提供免费商标查询Saas工具,智能商标注册0服务费省钱到底,商标查询,商标注册监控,专利申请等服务获得客户的高度认可。"
                }
            ],
            link:[
                {
                    rel: "canonical",
                    content: ''
                }
            ]
        };
    },
    components: {
        mini,
        noFind
    },
    data() {
        return {
            imgUrl: "",
            openDoc: [], //展开字典的初始关闭
            listData: [], //列表数组
            keyWord: "",
            total: 1,
            pn: 1,
            rn: 30,
            logo: 'this.src="' + img + '"',
            docData: {}, //字典对象
            docStyleIdxArr: [], //45类选中的下标
            keyIndex: 5, //搜索key，取值： 近似商标 nameJs; 精准商标 name; 申请号 regNo; 申请人 applicantCn; 代理公司 tmSection;
            keyArr: [
                { name: "近似商标", val: "" },
                { name: "精准商标", val: "" },
                { name: "申请号", val: "" },
                { name: "申请人", val: "" },
                { name: "商品服务", val: "" },
                { name: "图形搜索", val: "" },
                { name: "代理公司", val: "" }
            ],
            applyType: "", //申请45类别，传值多个值直接用“-”分隔；并获取子级
            layStatus: "", //法律状态
            applyYear: "", //申请年份
            isRequire: 0, //1 加载更多 0 显示没有下一页了
            isMove: 0, //是否可以拖动
            startx: 0,
            starty: 0,
            startLeft: 0,
            startTop: 0,
            BoxMove: { left: "0", top: "200px" }
        };
    },
    mounted() {
        window.addEventListener("scroll", this.handleScroll);
        window.addEventListener("mousemove", this.mouseMove);
        this.imgUrl = Cookies.get("imgUrl");
        this.httpRequire();
        this.BoxMove = {
            left: 0,
            top: document.documentElement.clientHeight / 2 + "px"
        };
    },
    methods: {
        getList(requireObj, state) {
            Http("brandSearch/imgSearchList", requireObj, "post", 1).then(
                res => {
                    // this.isRequire = res.data.data.hasMore == "true" ? 1 : 0;
                    // console.log("图片数据", res.data.data);
                    this.isRequire =
                        res.data.data.listData.length == this.rn ? 1 : 0;

                    if (state) {
                        let dataArr = [...this.listData];
                        this.listData = dataArr.concat(res.data.data.listData);
                    } else {
                        this.listData = res.data.data.listData;
                    }
                }
            );
        },
        getDoc(requireObj) {
            let obj = Object.assign({}, requireObj, { rn: 1000 });
            Http("brandSearch/imgSearch", obj, "post").then(res => {
                //console.log("字典数据", res);
                this.docData = res.data.data;
            });
        },
        httpRequire(bool, state) {
            // state 状态 空 全部刷新 ； 为真就是翻页

            let requireObj = {
                url: this.imgUrl,
                pn: this.pn,
                rn: this.rn,
                applyType: this.applyType,
                layStatus: this.layStatus,
                applyYear: this.applyYear
            };
            if (!bool && !state) this.getDoc(requireObj);
            this.getList(requireObj, state);
        },
        //如果有选中的条件这里分析
        isHasOption(key, obj, key2) {
            if (this[key]) {
                for (let item of obj[key2]) {
                    if (item.key == this[key]) {
                        obj[key2] = [item];
                    }
                }
            }
            return obj;
        },
        openLevel(num) {
            let newArr = [...this.openDoc];
            newArr[num] = newArr[num] ? 0 : 1;
            this.openDoc = newArr;
        },
        goSearch() {
            //去搜索页面
            this.keyWord = this.keyWord.replace(/ /g, "");
            if (!this.keyWord) {
                return;
            }
            if (
                /[`~!@#$%^&*_\-+=<>?:"\/'\\[\]·~！@#￥%……&*——\-+=？：.]/im.test(
                    this.keyWord
                )
            ) {
                this.$Popup.created({
                    content: "关键字不能包含特殊字符",
                    time: 2000
                });
                return false;
            }
            this.Loading = this.$Popup.created({
                content: "搜索中...",
                type: "loading",
                icon: "&#xe61c"
            });
            this.$router.push({
                path: "/shangbiao/" + this.keyWord + ".html"
            });
        },
        //跳转到详情页面
        initUrl(regNo, intCls) {
            let str = regNo + "|" + intCls;
            return "/shangbiaoxq/" + str + ".html";
        },
        //黑底导航条分类
        switchTab(num) {
            if (num != 5) {
                this.$Popup.created({
                    content: "请输入关键字后，点击搜索按钮",
                    time: 2000
                });
            }
        },
        //匹配字典名
        matchName(name) {
            let str = "其他";
            switch (name) {
                case "apply_type_p":
                    str = "申请类别";
                    break;
                case "apply_year":
                    str = "申请年份";
                    break;
                case "lay_status":
                    str = "法律状态";
                    break;
            }
            return str;
        },
        //字典的分类选中
        selectOption(key, num, code) {
            //45大类的选中与取消选中

            if (key == "apply_type_p") {
                let newArr = [...this.docStyleIdxArr];
                newArr[num] = newArr[num] ? 0 : 1;
                this.docStyleIdxArr = newArr;
                this.get45Select(newArr);
                this.applyYear = "";
                this.layStatus = "";
            } else if (key == "apply_year") {
                //年的选中
                this.applyYear = this.applyYear == code ? "" : code;
                this.layStatus = "";
                this.docStyleIdxArr = [];
                this.applyType = "";
            } else if (key == "lay_status") {
                //法律状态的选中
                this.layStatus = this.layStatus == code ? "" : code;
                this.applyYear = "";
                this.docStyleIdxArr = [];
                this.applyType = "";
            }
            this.pn = 1;
            this.isRequire = 1;
            this.httpRequire(1);
        },
        //45类的选中 产生applyType
        get45Select(arr) {
            let str = "",
                _45Arr = this.docData.apply_type_p;

            for (let i = 0; i < _45Arr.length; i++) {
                if (arr[i]) {
                    str += _45Arr[i].key + "-";
                }
            }
            if (str) str = str.substr(0, str.length - 1);
            this.applyType = str;
        },
        //判断是否滚到底部
        handleScroll() {
            var scrollTop =
                window.pageYOffset ||
                document.documentElement.scrollTop ||
                document.body.scrollTop;
            let pageHeight = document.documentElement.clientHeight;

            if (scrollTop + pageHeight == this.getWindowHeight()) {
                if (this.isRequire) {
                    this.pn += 1;
                    this.httpRequire(1, true);
                }
            }
        },
        getWindowHeight() {
            var scrollHeight = 0,
                bodyScrollHeight = 0,
                documentScrollHeight = 0;
            if (document.body) {
                bodyScrollHeight = document.body.scrollHeight;
            }
            if (document.documentElement) {
                documentScrollHeight = document.documentElement.scrollHeight;
            }
            scrollHeight =
                bodyScrollHeight - documentScrollHeight > 0
                    ? bodyScrollHeight
                    : documentScrollHeight;
            return scrollHeight;
        },
        //upload上传图片
        handleAvatarSuccess(res, file) {
            this.Loading.close();
            uploadImg(this, res);
            window.location.reload();
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
        //拖动
        mouseMove(e) {
            e = e ? e : window.event;
            if (this.isMove) {
                let obj = {
                    left: e.clientX - (this.startx - this.startLeft) + "px",
                    top: e.clientY - (this.starty - this.startTop) + "px"
                };
                this.BoxMove = obj;
            }
        },
        clickDown(e) {
            e = e ? e : window.event;
            this.isMove = true;
            this.startx = e.clientX || 0;
            this.starty = e.clientY || 0;
            this.startLeft = parseInt(this.BoxMove.left);
            this.startTop = parseInt(this.BoxMove.top);
        },
        clickUp() {
            this.isMove = 0;
        }
    },
    destroyed() {
        window.removeEventListener("scroll", this.handleScroll);
        if (this.Loading) this.Loading.close();
    }
};
</script>
<style scoped>
@import url("../../../assets/css/search");
* {
    box-sizing: border-box;
}
.page-content {
    padding-bottom: 0 !important;
}
.page-content-left.imglistBox {
    width: 100% !important;
}
.graphList-result .list {
    float: left;
    margin-right: 48px;
    text-align: center;
    width: 160px;
}
.graphList-result .list:nth-child(6n) {
    margin-right: 0;
}
.graphList-result .list dt {
    width: 160px;
    height: 160px;
    overflow: hidden;
    position: relative;
    cursor: pointer;
    line-height: 160px;
}
.graphList-result .list dt img {
    width: 98%;
    vertical-align: middle;
    max-width: 98%;
    max-height: 98%;
}
.graphList-result .list dt .bgtext {
    opacity: 0;
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
    height: 100%;
    color: #fff;
    padding: 0px 15px;
    text-align: left;
    background-color: rgba(0, 0, 0, 0.7);
    transition: 0.4s all;
    text-align: center;
}
.graphList-result .list:hover dt .bgtext {
    opacity: 1;
}
.graphList-result .bgtext a {
    line-height: 1.3;
    display: inline-block;
    width: 96%;
    text-align: left;
    color: #fff;
    vertical-align: middle;
}
.graphList-result .list dt .bgtext .text {
    display: inline-block;
    width: 97%;
    vertical-align: middle;
}
.graphList-result .list dt .bgtext .text span {
    display: block;
    padding: 1px 0;
    font-size: 13px;
}
.graphList-result .list dt .bgtext .text span {
    display: block;
    padding: 1px 0;
    font-size: 13px;
}
.graphList-result .list dt .bgtext .text span {
    display: block;
    padding: 1px 0;
    font-size: 13px;
}
.graphList-result .list dd {
    height: 50px;
    padding-top: 15px;
}

.requireMore {
    width: 100%;
    height: 30px;
    background: #f1f1f1;
    font-size: 12px;
    text-align: center;
    margin-top: 20px;
    color: #666;
    transition: 0.5s all;
}

.requireMore > span {
    display: inline-block;
    line-height: 30px;
}
.requireMore .iconfont {
    font-size: 34px;
    margin-right: 10px;
    display: inline-block;
    vertical-align: middle;
}
.loading {
    animation: zzzz 1.4s linear infinite;
}
@keyframes zzzz {
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
/* 左侧固定图片 */
.fixedRight {
    position: fixed;
    transform: translateY(-50%);
    width: 160px;
    background-color: #f3f3f3;
    border: 2px solid #ff5c00;
}
.fixedRight h2 {
    height: 30px;
    line-height: 30px;
    text-align: center;
    font-size: 14px;
    background-color: #e1e1e1;
    cursor: move;
}
.fixedRight .moveBox {
    width: 156px;
    min-height: 120px;
    line-height: 120px;
    cursor: pointer;
    text-align: center;
}

.fixedRight .moveBox img {
    max-width: 100%;
    min-width: 50%;
    vertical-align: middle;
}
</style>
