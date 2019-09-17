<template>
    <div class="all">
        <mini/>
        <div class="page-search w">
            <span class="main-title">商品分类</span>
            <div class="search-input">
                <input name="" placeholder="请输入主营业务或行业关键词查看所对应的商标类别" class="text fl" type="text" v-model="keyWord" @keyup.enter='searchNotice'>
                <a @click="searchNotice" class="button search">搜索</a>
            </div>
            <div class="search-right">
                <a href="/classify" class="button button-white">商品分类</a>
                <a href="/notice" target="_blank" class="button button-white">初审公告</a>
            </div>
        </div>
        <div class="page-nav">
            <div class="page-nav-in w">
                <a href="javascript:void(0)">类似商品和服务区分表 基于尼斯分类</a>
            </div>
        </div>
        <!-- //商标的45大类  第一类全部的类容-->
        <section class="w" style="margin-top:20px" v-if="!isSearch">
            <div class="nice-detail" :class="{'fixed':isFixed}" id="allBrand">
                <!-- //右边最大类 -->
                <div class="nice-left">
                    <div class="nice-left-go nice-left-top" @click="firstULBox(0)"></div>
                    <ul class="scroll" ref="scrollBox">
                        <li>
                            <a v-for="(item,idx) in menuData" :class="{'active':menuChenckIndex == idx}" :key="idx" :href='"/classify/" + item.number + ".html"'>
                                <i>{{item.number}}</i>
                                {{item.name}}</a>
                        </li>
                    </ul>
                    <div class="nice-left-go nice-left-bottom" @click="firstULBox(1)"></div>
                </div>
                <!-- //右边最大类  end-->
                <!-- 右边详情类 -->
                <div class="nice-right">
                    <!-- 上半部分 -->
                    <div class="nice-first-info" :class="{'open':openNotes}">
                        <a href="javascript:void(0)" class="arrow" @click="openNotes = !openNotes">收起</a>
                        <h2> {{menuData[menuChenckIndex].number}}-{{menuData[menuChenckIndex].name}}</h2>
                        <!-- <p>工业用油和油脂；润滑剂；吸收、润湿和粘结灰尘用合成物；燃料(包括马达用燃料)和照明材料；照明用蜡烛和灯芯。</p> -->
                        <div class="tips">
                            <span>【注释】</span>
                            <p>
                                {{menuData[menuChenckIndex].remark}}
                            </p>
                        </div>
                    </div>
                    <!-- 上半部分 end-->
                    <div class="nice-group clear2" :class="{'fixed':isFixed2}" id="groupBrand">
                        <div class="nice-group-list scroll">
                            <ul>

                                <li v-for="(item,idx) in listData1" :key="idx">
                                    <a @click="minScroll(item,idx)" :class="{'active':listData1Index == idx}">{{item}}</a>
                                </li>
                            </ul>
                        </div>
                        <div class="nice-group-detail">
                            <div v-for="(val,key,idx) in listData2" :key="idx" :id="key" class="nice-detail-list">
                                <h3>{{key}}</h3>
                                <p>
                                    <span v-for="(item,idx2) in val" :key="idx2">{{returnHtml(item.number,item.name)}}</span>
                                </p>
                                <!-- <p>注：1.石油（原油或精炼油）与0402燃料，挥发性混合燃料，照明用油脂，轻石油，柴油，粗柴油，汽油，煤油，发动机燃料，燃料油，石油气类似，与第十版及以前版本0402挥发性燃料混合物，汽车燃料交叉检索； 2.皮革保护用油脂，皮革保护油与0303皮革保护剂（上光），皮革膏，皮革用蜡类似，与第十版及以前版本0303皮革防腐剂（抛光剂）交叉检索 3.皮革用油脂与0114制革用油，鞣革用油，皮革整理用油类似，与第九版及以前版本0114皮革加脂用油交叉检索；
                                </p>
                                <div class="ab-norm-goods">
                                    <span>非规范商品项——商标局可接受</span>
                                    <p>保护皮革用油和脂,发动机油用非化学添加剂,工业润滑剂,工业用矿物油脂（非燃料）,机动车发动机润滑油,润滑剂用非化学添加剂,石墨润滑剂,脱模油,硬化油（工业用氢化油）,钻井润滑剂,工业用非矿物油（非燃料）,工业用非矿物油脂（非燃料）,合成润滑油,机器用润滑剂,机器用润滑油,汽车润滑脂,枪管用油,润滑油脂用非化学添加剂,靴用油脂,人造石油,汽车润滑油,工业用动物油,工业用棉花籽油,工业用熟油,矿物润滑油,工业用玉米油,工业用橄榄油,原油,石油,车辆用发动机油,工业用鱼油,工业用固体润滑剂,润滑油（工业润滑剂）,工业用亚麻籽油,工业用羊毛脂,工业用花生油,工业用紫苏油,工业用黄豆油,工业用芝麻油,工业用牛脂,工业用椰子油,工业用核桃油</p>
                                </div> -->
                                <!---->
                            </div>
                        </div>
                    </div>
                    <!-- 下半部分 -->
                </div>
                <!-- 右边详情类 end-->

            </div>
        </section>
        <!-- //商标的45大类  搜索出来的类容-->
        <section class="w" style="margin-top:20px" v-else>
            <div class="nice-detail" :class="{'fixed':isFixed}" id="allBrand" v-if="menuDataS && menuDataS[secKey]">
                <!-- //左边最大类 -->
                <div class="nice-left">
                    <div class="nice-left-go nice-left-top" @click="firstULBox(0)"></div>
                    <ul class="scroll" ref="scrollBox">
                        <li>
                            <a v-for="(val,key,idx) in menuDataS" :class="{'active':menuChenckIndex == idx}" :key="idx" @click="getSec(key,idx)">
                                {{key}}</a>
                        </li>
                    </ul>
                    <div class="nice-left-go nice-left-bottom" @click="firstULBox(1)"></div>
                </div>
                <!-- //左边最大类  end-->
                <!-- 右边详情类 -->
                <div class="nice-right">
                    <!-- 上半部分 -->
                    <div class="nice-first-info" :class="{'open':openNotes}">
                        <a href="javascript:void(0)" class="arrow" @click="openNotes = !openNotes">收起</a>
                        <h2>{{secKey}}</h2>
                        <!-- <p>工业用油和油脂；润滑剂；吸收、润湿和粘结灰尘用合成物；燃料(包括马达用燃料)和照明材料；照明用蜡烛和灯芯。</p> -->
                        <div class="tips">
                            <span>【注释】</span>
                            <p>
                                {{menuDataS[secKey]['annotation']}}
                            </p>
                        </div>
                    </div>
                    <!-- 上半部分 end-->
                    <div class="nice-group clear2" :class="{'fixed':isFixed2}" id="groupBrand">
                        <div class="nice-group-list scroll">
                            <ul>
                                <li v-for="(item,idx) in menuDataS[secKey]['data']" :key="idx">
                                    <a @click="minScroll2(item.number,item.name,idx)" :class="{'active':listData1Index == idx}">{{item.number}} {{item.name}}</a>
                                </li>
                            </ul>
                        </div>
                        <div class="nice-group-detail">
                            <div v-for="(item,idx) in listDataS[menuChenckIndex]" :key="idx" :id="item.mane" class="nice-detail-list">
                                <h3>{{item.name}}</h3>
                                <p class="allClassify">
                                    <span v-for="(item2,idx2) in item.data" :key="idx2" v-html="returnHtml(item2.number,item2.name)"></span>
                                </p>
                                <!-- <p>注：1.石油（原油或精炼油）与0402燃料，挥发性混合燃料，照明用油脂，轻石油，柴油，粗柴油，汽油，煤油，发动机燃料，燃料油，石油气类似，与第十版及以前版本0402挥发性燃料混合物，汽车燃料交叉检索； 2.皮革保护用油脂，皮革保护油与0303皮革保护剂（上光），皮革膏，皮革用蜡类似，与第十版及以前版本0303皮革防腐剂（抛光剂）交叉检索 3.皮革用油脂与0114制革用油，鞣革用油，皮革整理用油类似，与第九版及以前版本0114皮革加脂用油交叉检索；
                                </p>
                                <div class="ab-norm-goods">
                                    <span>非规范商品项——商标局可接受</span>
                                    <p>保护皮革用油和脂,发动机油用非化学添加剂,工业润滑剂,工业用矿物油脂（非燃料）,机动车发动机润滑油,润滑剂用非化学添加剂,石墨润滑剂,脱模油,硬化油（工业用氢化油）,钻井润滑剂,工业用非矿物油（非燃料）,工业用非矿物油脂（非燃料）,合成润滑油,机器用润滑剂,机器用润滑油,汽车润滑脂,枪管用油,润滑油脂用非化学添加剂,靴用油脂,人造石油,汽车润滑油,工业用动物油,工业用棉花籽油,工业用熟油,矿物润滑油,工业用玉米油,工业用橄榄油,原油,石油,车辆用发动机油,工业用鱼油,工业用固体润滑剂,润滑油（工业润滑剂）,工业用亚麻籽油,工业用羊毛脂,工业用花生油,工业用紫苏油,工业用黄豆油,工业用芝麻油,工业用牛脂,工业用椰子油,工业用核桃油</p>
                                </div> -->
                                <!---->
                            </div>
                        </div>
                    </div>
                    <!-- 下半部分 -->
                </div>
                <!-- 右边详情类 end-->

            </div>
            <noFind :keyWord="keyWord" :type="'商标'" v-else/>
        </section>
    </div>
</template>
  <script>
import mini from "~/components/header/mini";
import { Jump } from "~/assets/js/common";
import { Http } from "~/plugins/axios.js";
import noFind from "~/components/noFind";

import axios from "axios";

export default {
    layout: "empt",
    head() {
        return {
            title: `商标${this.classifyNumber}类_${
                this.classifyNumber
            }类商标的经营范围_商标${this.classifyNumber}大类明细_${
                this.classifyNumber
            }类商标商标注册/申请-知千秋官方网站`,
            meta: [
                {
                    name: "keywords",
                    hid: "keywords",
                    content: `商标${this.classifyNumber}类,${
                        this.classifyNumber
                    }类商标,商标${this.classifyNumber}大类明细,${
                        this.classifyNumber
                    }类商标的经营范围,${this.classifyNumber}类商标商标注册,${
                        this.classifyNumber
                    }类商标商标申请`
                },
                {
                    name: "description",
                    hid: "description",
                    content: `【知千秋商标查询系统】【${
                        this.classifyNumber
                    }类商标】大类明细入口页，为您提供${
                        this.classifyNumber
                    }类商标的经营范围信息提供${
                        this.classifyNumber
                    }类商标的注册与申请、商标管理、极速网申（0元服务费）等服务。`
                }
            ],
            link: [
                {
                    rel: "canonical",
                    content: this.baseUrl
                }
            ]
        };
    },
    components: {
        mini,
        noFind
    },

    async asyncData({ params, redirect, error, env, route }) {
        // let { data } = await axios.get(`${env.baseUrl}/classfirst/query`);
        let typeId = params.all.split(/\./)[0];

        if (typeId.indexOf("*") == -1) {
            let [data1, data2] = await Promise.all([
                axios.get(`${env.baseUrl}classfirst/query?key=${typeId}`),
                axios.get(`${env.baseUrl}classsecond/query?id=${typeId}`)
            ]);
            let listData1 = [];
            for (let key in data2.data.data[0]) {
                listData1.push(key);
            }
            return {
                classifyNumber: typeId,
                menuChenckIndex: Number(typeId) - 1,
                menuData: data1.data.data,
                listData1: listData1,
                listData2: data2.data.data[0],
                baseUrl: env.webSite + route.fullPath
            };
        } else {
            typeId = typeId.replace("*", "");
            let url = encodeURI(
                `${env.baseUrl}classthird/keyquery?key=${typeId}`
            );
            let { data } = await axios.get(url);

            let secKey = "";
            if (data.code == 0) {
                for (let key in data.data.first) {
                    secKey = key;
                    break;
                }
                let listDataS = [];
                let oldNum = "";
                let thirdArr = data.data.second;
                for (let key in thirdArr) {
                    let num = key.substr(0, 2);
                    if (num == oldNum) {
                        listDataS[listDataS.length - 1].push({
                            name: key,
                            data: thirdArr[key]
                        });
                    } else {
                        oldNum = num;
                        listDataS.push([{ name: key, data: thirdArr[key] }]);
                    }
                }

                return {
                    classifyNumber: typeId,
                    keyWord: typeId,
                    isSearch: 1,
                    secKey: secKey,
                    menuDataS: data.data.first,
                    listDataS: listDataS,
                    menuChenckIndex: 0,
                    baseUrl: env.webSite + route.fullPath
                };
            } else {
                return {
                    classifyNumber: typeId,
                    keyWord: typeId
                };
                error({ statusCode: 500, message: data.msg });
            }
        }
    },
    data() {
        return {
            isSearch: 0,
            keyWord: "",
            openNotes: true,
            menuChenckIndeStr: "01",
            menuChenckIndex: 0,
            menuData: [],
            listData1: [],
            listData1Index: 0,
            listData2: [],
            isFixed: 0, //第一大类出现固定窗口
            isFixed2: 0, //第二大类出现固定窗口
            menuDataS: [], //搜索出来的额菜单
            secKey: "",
            listDataS: [] //搜索出来最小类
        };
    },
    mounted() {
        //监听滚动条
        window.addEventListener("scroll", this.handleScroll);
    },
    methods: {
        searchNotice() {
            this.keyWord = this.keyWord.replace(/ /g, "");
            if (this.keyWord) {
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
                this.$router.push({
                    path: "/classify/*" + this.keyWord + ".html"
                });
            }
        },
        getChild(typeId, idx) {
            Http("classsecond/query", { id: typeId }, "get", 1).then(res => {
                let listData1 = [];
                for (let key in res.data.data[0]) {
                    listData1.push(key);
                }
                Jump(document.getElementsByTagName("body")[0]);
                this.menuChenckIndex = idx;
                this.listData1 = listData1;
                this.listData1Index = 0;
                this.listData2 = res.data.data[0];
            });
        },
        handleScroll() {
            var scrollTop =
                window.pageYOffset ||
                document.documentElement.scrollTop ||
                document.body.scrollTop;
            var offsetTop = document.getElementById("allBrand").offsetTop - 70;
            var offsetTop2 =
                document.getElementById("groupBrand").offsetTop - 110;

            if (scrollTop > offsetTop) {
                this.isFixed = 1;
            } else {
                this.isFixed = 0;
            }
            if (scrollTop > offsetTop2) {
                this.isFixed2 = 1;
            } else {
                this.isFixed2 = 0;
            }
        },

        //第一大类的盒子滚动
        firstULBox(bool) {
            let box = this.$refs.scrollBox;

            let step = 0,
                timer = setInterval(() => {
                    if (bool) {
                        box.scrollTop += 2;
                    } else {
                        box.scrollTop -= 2;
                    }
                    if (step++ >= 100) {
                        clearInterval(timer);
                    }
                }, 10);
        },
        //最小分类的滚动效果
        minScroll(id, idx) {
            this.listData1Index = idx;
            Jump(document.getElementById(id), -110);
        },
        //搜索页面的方法
        getSec(key, idx) {
            this.secKey = key;
            this.menuChenckIndex = idx;
            this.listData1Index = 0;
        },
        minScroll2(num, str, idx) {
            this.listData1Index = idx;
            Jump(document.getElementById(num + str), -110);
        },
        returnHtml(str1, str2) {
            if (str1) {
                return str1 + "-" + str2 + ";";
            } else {
                return str2 + ";";
            }
        }
    },
    destroyed() {
        window.removeEventListener("scroll", this.handleScroll);
    }
};
</script>
<style scoped>
* {
    box-sizing: border-box;
}
.allClassify .keyword {
    display: inline-block;
    height: 16px;
    line-height: 16px;
    color: #fff;
    background-color: #ff5c00;
}
/* //二级搜索 */
.page-search {
    padding: 30px 0;
}
.page-search .main-title {
    display: inline-block;
    margin-right: 45px;
    font-size: 36px;
    line-height: 1em;
    color: #ff5c00;
    vertical-align: middle;
}
.search-input {
    display: inline-block;
    position: relative;
    margin-right: 40px;
    border: 1px solid #ff5c00;
    border-radius: 0 2px 2px 0;
    overflow: hidden;
    vertical-align: middle;
    box-sizing: border-box;
}
.page-search input.text {
    padding-left: 20px;
    width: 520px;
    height: 38px;
    border: none;
    vertical-align: middle;
    padding-right: 40px;
}
.page-search .button {
    width: 130px;
    line-height: 36px;
    font-size: 18px;
    text-align: center;
    vertical-align: middle;
}

.search-input .icon-camera {
    display: block;
    width: 30px;
    height: 28px;
    position: absolute;
    right: 135px;
    top: 5px;
    z-index: 2;
    background: url("../../assets/images/icon_photo.png") no-repeat center;
    cursor: pointer;
}
.search-right {
    display: inline-block;
    margin-left: 20px;
}
.button.button-white {
    color: #ff5c00;
    background-color: #fff;
    border: 1px solid #ff5c00;
}
.page-search .button {
    width: 130px;
    line-height: 36px;
    font-size: 18px;
    text-align: center;
    vertical-align: middle;
    border-radius: 2px 0 0 2px;
}
.search-right .button {
    margin-left: 10px;
}
/* //黑底导航 */
.page-nav {
    line-height: 48px;
    background-color: #39414e;
}
.page-nav a {
    margin-right: 50px;
    font-size: 15px;
    color: #fff;
}
.page-nav a.active {
    font-size: 16px;
    color: #ff5c00;
    font-weight: bold;
}
/* //所有分类************************************************************************* */
.nice-list {
    margin-top: 10px;
}
.nice-list li {
    float: left;
    position: relative;
    padding: 20px 0;
    width: 124px;
    text-align: center;
}
.nice-list li:hover {
    color: #fff;
    background-color: #ff5c00;
}
.nice-list li .first-num {
    font-size: 36px;
    font-weight: bold;
    color: #666;
}
.nice-list li:hover .first-num {
    color: #fff;
}
.nice-list li .first-name {
    font-size: 18px;
}
.nice-list li .first-intro {
    display: none;
    position: absolute;
    text-align: left;
    top: 0;
    left: 100%;
    padding: 20px 15px;
    width: 360px;
    height: 100%;
    font-size: 0px;
    border: 1px solid #ff5c00;
    background-color: #fff;
    color: #333;
    z-index: 9;
}
.nice-list li .first-intro:before {
    content: "";
    display: inline-block;
    height: 100%;
    width: 0;
    vertical-align: middle;
}
.nice-list li .first-intro .first-intro-in {
    display: inline-block;
    width: 100%;
    vertical-align: middle;
}
.nice-list li.right .first-intro {
    left: auto;
    right: 100%;
}
.nice-list li:hover .first-intro {
    display: block;
}
.nice-list li .first-intro a {
    display: inline-block;
    color: #ff5c00;
}
/* /乱码/ */
.nice-detail {
    padding-top: 30px;
}
.nice-detail.fixed {
}
.nice-detail.fixed .nice-left {
    position: fixed;
    left: 50%;
    top: 110px;
    margin-left: -600px;
}
.nice-left {
    float: left;
    position: relative;
    width: 124px;
}
.nice-left ul {
    height: 606px;
    overflow: auto;
}
.nice-right {
    float: right;
    width: 1060px;
}
.nice-left li {
    margin-bottom: 2px;
}
.nice-left li:last-of-type {
    margin-bottom: 0px;
}
.nice-left a {
    display: block;
    padding: 0px 10px;
    line-height: 36px;
    font-size: 15px;
    background-color: #f0f0f0;
}
.nice-left a.active {
    color: #fff;
    background-color: #ff5c00;
}
.nice-left a i {
    font-weight: bold;
    font-size: 17px;
    vertical-align: -1px;
}
.nice-left a.active i {
    color: #fff;
}
.nice-right .nice-first-info {
    position: relative;
    margin-bottom: 20px;
    padding: 20px;
    /*height: 120px;*/
    overflow: hidden;
    background-color: #f6f6f6;
}
.nice-right .nice-first-info.open .tips {
    display: block;
}
.nice-right .nice-first-info.open .arrow {
    background: url("../../assets/images/main/narrow-up.png") no-repeat right
        center;
}
.nice-right .nice-first-info .arrow {
    position: absolute;
    right: 15px;
    top: 15px;
    padding-right: 15px;
    background: url("../../assets/images/main/narrow-down.png") no-repeat right
        center;
}
.nice-right .nice-first-info h2 {
    margin-bottom: 10px;
    font-size: 20px;
    color: #ff5c00;
}
.nice-right .nice-first-info p {
    font-size: 16px;
}
.nice-right .nice-first-info .tips {
    display: none;
    margin-top: 5px;
}
.nice-right .nice-first-info .tips span {
    display: inline-block;
    margin-top: 17px;
    margin-bottom: 12px;
    color: #ff5c00;
    font-size: 14px;
}
.nice-right .nice-first-info .tips p {
    font-size: 14px;
}
.nice-group.fixed {
    padding-left: 200px;
}
.nice-group.fixed .nice-group-list {
    position: fixed;
    left: 50%;
    top: 110px;
    margin-left: -460px;
    height: 600px;
    overflow: auto;
}
.nice-group-list {
    float: left;
    margin-right: 40px;
    width: 160px;
}
.nice-group-list a {
    display: block;
    padding-left: 10px;
    line-height: 36px;
    font-size: 15px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.nice-group-list a.active {
    position: relative;
    color: #ff5c00;
}
.nice-group-list a.active:before {
    content: "";
    display: block;
    position: absolute;
    top: 50%;
    left: 0px;
    margin-top: -10px;
    width: 3px;
    height: 20px;
    background-color: #ff5c00;
}
.nice-group-detail {
    float: left;
    padding-top: 10px;
    padding-bottom: 100px;
    width: 800px;
}
.nice-group-detail .keyword {
    display: inline-block;
    height: 16px;
    line-height: 16px;
    color: #fff;
    background-color: #ff5c00;
}
.nice-detail-list h3 {
    margin-bottom: 10px;
    padding-left: 10px;
    font-size: 16px;
    line-height: 1.1em;
    color: #ff5c00;
    border-left: 3px solid #ff5c00;
}
.nice-detail-list p {
    margin-bottom: 10px;
    line-height: 26px;
}
.nice-detail-list .ab-norm-goods span,
.history-goods span {
    display: block;
    margin-top: 20px;
    margin-bottom: 10px;
    padding-left: 10px;
    line-height: 28px;
}
.nice-detail-list .ab-norm-goods span {
    color: #659232;
    background-color: #ebfcef;
}
.history-more {
    float: right;
    margin-right: 5px;
    padding-right: 15px;
    color: #666;
    background: url("../../assets/images/main/narrow-down.png") no-repeat right
        center;
}
.history-goods {
    margin-bottom: 10px;
    height: 120px;
    overflow: hidden;
}
.history-goods.open {
    height: auto;
}
.history-goods.open .history-more {
    background: url("../../assets/images/main/narrow-up.png") no-repeat right
        center;
}
.nice-detail-list .history-goods span {
    color: #f08b2f;
    background-color: #fff8f5;
}
.nice-right .no-data {
    font-size: 20px;
    padding-top: 100px;
    text-align: center;
}
.nice-left-go {
    position: absolute;
    width: 100%;
    height: 30px;
    cursor: pointer;
}
.nice-left-top {
    top: -30px;
    background: url("../../assets/images/main/nice-au.png") no-repeat center;
    background-size: 20px auto;
}
.nice-left-bottom {
    bottom: -30px;
    background: url("../../assets/images/main/nice-ad.png") no-repeat center;
    background-size: 20px auto;
}
</style>
