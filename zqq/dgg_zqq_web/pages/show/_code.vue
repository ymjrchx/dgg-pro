<template>
    <div class="show-content" v-if="showInfo[0]">
        <div class="w1200">
            <div class="product">
                <div class="photo">
                    <img src="../../assets/images/show/img_product.jpg" width="520" alt="" />
                    <div>
                        <b>{{showInfo[type].name}}</b>
                    </div>
                </div>
                <div class="hidden">
                    <h1>{{showInfo[type].name}}</h1>
                    <p>{{showInfo[type].describle}}</p>
                    <div class="price">
                        <table width="440" cellpadding="0" cellspacing="0">
                            <tbody>
                                <tr>
                                    <td width="60" class="title">服务费：</td>
                                    <td width="178">
                                        <b class="price-text">
                                            <span>¥</span>{{servicePriceTotal}}</b>
                                    </td>
                                    <td width="60" class="title">官费：</td>
                                    <td>
                                        <b class="price-text">
                                            <span>¥</span>{{officialPrice}}</b>
                                    </td>
                                </tr>

                                <tr v-if="showInfo[type].attrData.detail && showInfo[type].attrData.detail.ext1 ==0">
                                    <td class="title">{{showInfo[type].attrData.detail.name}}：</td>
                                    <td class="descrip" colspan="3">
                                        <div class="officerSlowBox">
                                            <label @click="checkAttr" class="label" v-for="(childItem) in showInfo[type].attrData.children" :key="childItem.detail.name" :data-name="childItem.detail.code">
                                                <input readOnly type="checkbox" :data-name="childItem.detail.code" :value="childItem.detail.code" v-model="upAttrs">{{childItem.detail.name}}</label>
                                        </div>
                                    </td>
                                </tr>

                                <tr v-if="showInfo[type].attrData.detail && showInfo[type].attrData.children[typeEj].parent && showInfo[type].attrData.children[typeEj].parent.ext1 == 0">
                                    <td class="title">{{showInfo[type].attrData.children[typeEj].parent.name}}：</td>
                                    <td class="descrip" colspan="3">
                                        <div class="officerSlowBox">
                                            <label @click="checkAttrType" class="label" v-for="(childItem, index) in showInfo[type].attrData.children[typeEj].children" :key="childItem.name" :data-id="index" :data-name="childItem.code">
                                                <input readOnly type="checkbox" :data-id="index" :data-name="childItem.code" :value="index" v-model="typeSjAttrs">{{childItem.name}}</label>
                                        </div>
                                    </td>
                                </tr>

                            </tbody>
                        </table>
                    </div>
                    <div class="type">
                        <dl>
                            <dt>服务项目：</dt>
                            <dd @click="seletType">
                                <span v-for="(item, index) in showInfo" :key="item.name" :data-id="index" data-type="type" class="type-item" :class="{'atthis': type==index}">{{item.name}}</span>
                            </dd>
                        </dl>
                        <dl v-if="showInfo[type].attrData.detail && showInfo[type].attrData.detail.ext1!=0">
                            <dt>{{showInfo[type].attrData.detail.name}}：</dt>
                            <dd @click="seletType">
                                <span v-for="(childItem, index) in showInfo[type].attrData.children" :key="childItem.detail.name" :data-id="index" data-type="typeEj" class="type-item-li" :class="{'atthis': typeEj==index}">{{childItem.detail.name}}</span>
                            </dd>
                        </dl>

                        <dl v-if="showInfo[type].attrData.detail && showInfo[type].attrData.children[typeEj].parent && showInfo[type].attrData.children[typeEj].parent.ext1!=0">
                            <dt>{{showInfo[type].attrData.children[typeEj].parent.name}}：</dt>
                            <dd @click="seletType">
                                <span v-for="(childItem, index) in showInfo[type].attrData.children[typeEj].children" :key="childItem.name" :data-id="index" data-type="typeSj" class="type-item-li" :class="{'atthis': typeSj==index}">{{childItem.name}}</span>
                            </dd>
                        </dl>

                        <dl>
                            <dt v-if="showInfo[type].name.indexOf('注册')>-1">大类数量：</dt>
                            <dt v-if="showInfo[type].name.indexOf('注册')==-1">服务数量：</dt>
                            <dd>
                                <b class="btn-num" @click="subNum">-</b><input v-model="num" readonly="readonly" type="number">
                                <b class="btn-num" @click="addNum">+</b>
                                <span class="type-tips" v-if="showInfo[type].name.indexOf('注册')>-1">每个大类下面有10个小类。</span>
                            </dd>
                        </dl>
                        <dl>
                            <dt>总&nbsp;费 &nbsp;用：</dt>
                            <dd>
                                <b class="price-text">
                                    <span>¥</span>{{total}}</b>
                            </dd>
                        </dl>
                    </div>

                    <a href="javascript:;" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)" class="btn">问题咨询</a>
                    <a href="javascript:void(0);" v-show="showInfo[type].name != '担保无忧注册'" @click="getOrder" class="btn atthis">立即办理</a>
                    <div class="tips">服务承诺： 按时按质&nbsp;&nbsp;&nbsp;&nbsp;全程公开透明</div>
                </div>
            </div>
            <div class="overflow">
                <div class="agent" id="agent" :class="{'fixed': isFixed}">
                    <h6 class="agent-header">
                        <i class="icon iconfont icon-consult">&#xe61d;</i>顾问与帮助</h6>
                    <ul class="agent-bodyer">
                        <li class="list">
                            <dl class="clearfix">
                                <dt><img src="../../assets/images/show/p4.jpg" alt="代理人"></dt>
                                <dd>
                                    <span class="r-1">刘朝阳
                                        <i class="icon-renzheng">已认证</i>
                                    </span>
                                    <span class="r-2">好评率：97.26%</span>
                                    <a class="r-3" href="javascript:;" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)">咨询一下</a>
                                </dd>
                            </dl>
                        </li>
                        <li class="list">
                            <dl class="clearfix">
                                <dt><img src="../../assets/images/show/p2.jpg" alt="代理人"></dt>
                                <dd>
                                    <span class="r-1">李亚南
                                        <i class="icon-renzheng">已认证</i>
                                    </span>
                                    <span class="r-2">好评率：100%</span>
                                    <a class="r-3" href="javascript:;" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)">咨询一下</a>
                                </dd>
                            </dl>
                        </li>
                        <li class="list">
                            <dl class="clearfix">
                                <dt><img src="../../assets/images/show/p3.jpg" alt="代理人"></dt>
                                <dd>
                                    <span class="r-1">高峰
                                        <i class="icon-renzheng">已认证</i>
                                    </span>
                                    <span class="r-2">好评率：100%</span>
                                    <!--<a class="r-3" href="/userim/im?agent=5a7234566f6a7855736d6973687334487366677079773d3d" target="_blank">咨询一下</a>-->
                                    <a class="r-3" href="javascript:;" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)">咨询一下</a>
                                </dd>
                            </dl>
                        </li>
                    </ul>
                </div>

                <div class="w990">
                    <div id="introduce" class="border">
                        <div class="tagbar">
                            <a class="atthis" href="javascript:;" @click="scrollFn('#introduce')">服务介绍</a>
                            <a href="javascript:;" @click="scrollFn('#evaluate')">用户评价({{evaluateData?evaluateData.data.length:0}})</a>
                            <a href="javascript:;" @click="scrollFn('#service')">服务保障</a>
                            <a href="javascript:;" @click="scrollFn('#QA')">热门问答</a>
                            <a href="javascript:;" @click="scrollFn('#about')">关于知千秋</a>
                        </div>
                        <!-- <img src="../../assets/images/showImg/navigation_patent_domestic_01.jpg" width="100%" alt=""> -->
                        <img :src="mathImg(code)" width="100%" alt="">
                    </div>

                    <div id="evaluate" class="border">
                        <div class="tagbar">
                            <a href="javascript:;" @click="scrollFn('#introduce')">服务介绍</a>
                            <a class="atthis" href="javascript:;" @click="scrollFn('#evaluate')">用户评价({{evaluateData?evaluateData.data.length:0}})</a>
                            <a href="javascript:;" @click="scrollFn('#service')">服务保障</a>
                            <a href="javascript:;" @click="scrollFn('#QA')">热门问答</a>
                            <a href="javascript:;" @click="scrollFn('#about')">关于知千秋</a>
                        </div>

                        <div class="pj" v-if="evaluateData">
                            <div class="left">
                                <b>{{(((evaluateData.count[0].good+evaluateData.count[0].mid)/evaluateData.count[0].total)*100).toFixed(1)}}%</b>
                                <p>与描述相符</p>
                            </div>

                            <div class="right">
                                <p>好评（{{((evaluateData.count[0].good/evaluateData.count[0].total)*100).toFixed(1)}}%）
                                    <span>
                                        <i :style="'width:'+((evaluateData.count[0].good/evaluateData.count[0].total)*100)+'%;'"></i>
                                    </span>
                                </p>
                                <p>中评（{{((evaluateData.count[0].mid/evaluateData.count[0].total)*100).toFixed(1)}}%）
                                    <span>
                                        <i :style="'width:'+((evaluateData.count[0].mid/evaluateData.count[0].total)*100)+'%;'"></i>
                                    </span>
                                </p>
                                <p>差评（{{((evaluateData.count[0].bad/evaluateData.count[0].total)*100).toFixed(1)}}%）
                                    <span>
                                        <i :style="'width:'+((evaluateData.count[0].bad/evaluateData.count[0].total)*100)+'%;'"></i>
                                    </span>
                                </p>
                            </div>
                        </div>

                        <ul class="pj-cont" v-if="evaluateData">
                            <li v-for="item in evaluateData.data" :key="item.id">
                                <div class="title"><img src="../../assets/images/bg-6.png" alt="">用户：{{item.username}}</div>
                                <div class="pj-info">
                                    <i v-for="(itemStar,index) in [0,1,2,3,4]" v-if="index<item.level" :key="itemStar" class="full">1</i>
                                    <i v-for="(itemStar,index) in [0,1,2,3,4]" v-if="index>=item.level" :key="itemStar"></i>
                                    <p>{{item.getContent}}</p>
                                    <div class="note">
                                        <span>{{item.serviceTypeLevel3Code}}</span>
                                        <b>{{item.createTime}}</b>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>

                    <div id="service" class="border">
                        <div class="tagbar">
                            <a href="javascript:;" @click="scrollFn('#introduce')">服务介绍</a>
                            <a href="javascript:;" @click="scrollFn('#evaluate')">用户评价({{evaluateData?evaluateData.data.length:0}})</a>
                            <a class="atthis" href="javascript:;" @click="scrollFn('#service')">服务保障</a>
                            <a href="javascript:;" @click="scrollFn('#QA')">热门问答</a>
                            <a href="javascript:;" @click="scrollFn('#about')">关于知千秋</a>
                        </div>
                        <ul class="service">
                            <li>
                                <img src="../../assets/images/show/img_chengnuo.png" alt="">
                                <h5>我们承诺</h5>
                                <p>服务质量第一时间反馈<br>专人处理，保证及时解决问题</p>
                            </li>
                            <li>
                                <img src="../../assets/images/show/img_tousu.png" alt="">
                                <h5>投诉渠道</h5>
                                <p>拨打客服电话投诉<br>400-871-9995</p>
                            </li>
                            <li>
                                <img src="../../assets/images/show/img_baozhang.png" alt="">
                                <h5>平台保障</h5>
                                <p>因过失行为导致用户直接损失<br>均可享受“平台责任保障”</p>
                            </li>
                            <li>
                                <img src="../../assets/images/show/img_fapiao.png" alt="">
                                <h5>开具发票</h5>
                                <p>所有产品均可开具发票<br>请在下单时选择填写</p>
                            </li>
                        </ul>
                    </div>

                    <div id="QA" class="border">
                        <div class="tagbar">
                            <a href="javascript:;" @click="scrollFn('#introduce')">服务介绍</a>
                            <a href="javascript:;" @click="scrollFn('#evaluate')">用户评价({{evaluateData?evaluateData.data.length:0}})</a>
                            <a href="javascript:;" @click="scrollFn('#service')">服务保障</a>
                            <a class="atthis" href="javascript:;" @click="scrollFn('#QA')">热门问答</a>
                            <a href="javascript:;" @click="scrollFn('#about')">关于知千秋</a>
                        </div>
                        <ul class="qa">
                            <li v-for="item in QAList" :key="item.questionId">
                                <dl class="title">
                                    <dt>Q：</dt>
                                    <dd>{{item.questionText}}</dd>
                                </dl>
                                <dl>
                                    <dt>A：</dt>
                                    <dd>{{item.answerText}}</dd>
                                </dl>
                            </li>
                        </ul>
                    </div>

                    <div id="about" class="border">
                        <div class="tagbar">
                            <a href="javascript:;" @click="scrollFn('#introduce')">服务介绍</a>
                            <a href="javascript:;" @click="scrollFn('#evaluate')">用户评价({{evaluateData?evaluateData.data.length:0}})</a>
                            <a href="javascript:;" @click="scrollFn('#service')">服务保障</a>
                            <a href="javascript:;" @click="scrollFn('#QA')">热门问答</a>
                            <a class="atthis" href="javascript:;" @click="scrollFn('#about')">关于知千秋</a>
                        </div>

                        <div class="about-me-block">
                            <div class="text-intro">
                                <p>
                                    知千秋致力于运营大数据技术、人工智能等 技术重新定义知识产权生态链。我们是一群充满激情的互联网从业者，我们期望为让知产发挥最大价值而努力。知千秋秉承共享经济，以创新为发展理念，肩负知识产权价值最大化的重大使命，目前知千秋主营业务：商标注册、专利申请、版权交易等相关业务。
                                </p>
                            </div>
                            <div class="down-img">
                                <img width="950" src="../../assets/images/show/temp_tupian.jpg" alt="关于知千秋" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <floor :floorAttr="[{name: '服务介绍', type: '#introduce'},{name: '用户评价', type: '#evaluate'},{name: '服务保障', type: '#service'},{name: '热门问答', type: '#QA'},{name: '关于我们', type: '#about'}]" />
        <to-top />
    </div>
</template>

<script>
import floor from "@/components/floor/floor";
import toTop from "@/components/toTop";
import axios from "axios";
import Cookies from "js-cookie";
import popup from "~/components/popup"; //消息

export default {
    validate({ params }) {
        return !!params.code;
    },
    head() {
        return {
            title: `${
                this.showInfo[this.type].name
            }_专注于商标查询、专利查询-知千秋官网`,
            meta: [
                {
                    name: "keywords",
                    hid: "keywords",
                    content: `${this.showInfo[this.type].name}`
                },
                {
                    name: "description",
                    hid: "description",
                    content: `${this.showInfo[this.type].name}:${
                        this.showInfo[this.type].describle
                    }`
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
    asyncData({ params, redirect, error, env, route }) {
        let arr = params.code.split(".")[0].split("&");
        let code = arr[0];
        let id = arr[1];
        let typeIndex = 0;
        return Promise.all([
            axios.get(
                `${
                    env.baseUrl
                }/serviceItem/queryServiceItemList?code=${code}&serviceItemNum=${id}`
            ),
            axios.get(
                `${
                    env.baseUrl
                }/question/pageQueryByTypeLevel3Code?typeLevel3Code=${code}&pageSize=12&pageNum=1`
            ),
            axios.get(
                `${
                    env.baseUrl
                }/Comment/Record?serviceTypeLevel3Code=${code}&pageSize=16&pageIndex=1`
            )
        ]).then(res => {
            let { data } = res[0];
            let QAData = res[1];
            let evaluate = res[2].data;
            // 不存在数据
            if (!data.data || !data.data.length) {
                error({ statusCode: 500, message: "暂无数据" });
            } else {
                let arr = data.data;
                let typeEj = "";
                arr.map((item, index) => {
                    let dataFormat = {};
                    let attrType = {};
                    if (parseInt(item.choosed)) {
                        typeIndex = index;
                    }
                    item.serviceAttrType.map((childItem, childIndex) => {
                        attrType[childItem.code] = {
                            name: childItem.name,
                            code: childItem.code,
                            id: childItem.id,
                            ext1: childItem.ext1
                        };
                        childItem.sonAttrArr.map((threeItem, threeIndex) => {
                            attrType[threeItem.code] = {
                                name: threeItem.name,
                                code: threeItem.code,
                                id: threeItem.id
                            };
                        });
                    });

                    item.serviceAttr.map((childItem, childIndex) => {
                        let tempCode = childItem.code.split(",");
                        let tempParentCode = childItem.parentCode.split(",");
                        tempCode.map((threeItem, threeIndex) => {
                            if (threeIndex === 0) {
                                let temp = Object.assign(
                                    {},
                                    attrType[tempCode[1]]
                                );
                                temp.officialPrice = childItem.officialPrice;
                                temp.servicePrice = childItem.servicePrice;
                                temp.serviceAttrId = childItem.id;
                                dataFormat.detail =
                                    attrType[tempParentCode[threeIndex]];
                                if (!dataFormat.children) {
                                    dataFormat.children = {};
                                }
                                if (!dataFormat.children[threeItem]) {
                                    dataFormat.children[threeItem] = {};
                                }
                                if (!dataFormat.children[threeItem].children) {
                                    dataFormat.children[
                                        threeItem
                                    ].children = [];
                                    dataFormat.children[threeItem].parent =
                                        attrType[
                                            tempParentCode[threeIndex + 1]
                                        ];
                                }
                                if (!typeEj) {
                                    typeEj = tempCode[0];
                                }
                                if (item.choosed && childIndex == 0) {
                                    typeEj = tempCode[0];
                                }
                                dataFormat.children[threeItem].children.push(
                                    temp
                                );
                                // console.log(dataFormat.children[threeItem].children)
                                dataFormat.children[threeItem].detail =
                                    attrType[threeItem];
                            }
                        });
                    });
                    item.attrData = dataFormat;
                });
                // console.log(arr);
                return {
                    code: code,
                    showInfo: arr,
                    typeEj,
                    type: typeIndex, // 服务类型
                    upAttrs: [typeEj],
                    QAList: QAData.data.data,
                    evaluateData: evaluate.data ? evaluate.data : "",
                    baseUrl: env.webSite + route.fullPath
                };
            }
        });
    },
    components: {
        floor,
        toTop
    },
    data() {
        return {
            num: 1,
            typeSj: 0,
            typeEj: "", // 二级服务
            upAttrs: [],
            QAList: [],
            isFixed: false,
            typeSjAttrs: [0]
        };
    },
    methods: {
        scrollFn(type) {
            let temp = type.substr(1);
            let height = document.getElementById(temp).offsetTop - 70;
            let documentH = document.body.scrollHeight;
            let clientH = document.documentElement.clientHeight;

            this.timer && clearInterval(this.timer);
            this.timer = setInterval(() => {
                let top =
                    document.documentElement.scrollTop ||
                    window.pageYOffset ||
                    document.body.scrollTop;
                if (height >= top && height <= top + 30) {
                    clearInterval(this.timer);
                }
                if (height > top) {
                    if (document.documentElement.scrollTop != top) {
                        document.body.scrollTop += 30;
                    } else {
                        document.documentElement.scrollTop += 30;
                    }
                } else {
                    if (document.documentElement.scrollTop != top) {
                        document.body.scrollTop -= 30;
                    } else {
                        document.documentElement.scrollTop -= 30;
                    }
                }
                // 上边已经修改了滚动位置，优化bugs
                top =
                    document.documentElement.scrollTop ||
                    window.pageYOffset ||
                    document.body.scrollTop;
                //滚动到底部
                if (top + clientH >= documentH) {
                    console.log(top + clientH, documentH);
                    this.timer && clearInterval(this.timer);
                }
            }, 0);
        },
        subNum() {
            this.num > 1 && this.num--;
        },
        addNum() {
            this.num < 99 && this.num++;
        },
        seletType(e) {
            if (e.target.tagName === "SPAN") {
                this[
                    e.target.getAttribute("data-type")
                ] = e.target.getAttribute("data-id");
                if (e.target.getAttribute("data-type") == "type") {
                    for (let i in this.showInfo[
                        e.target.getAttribute("data-id")
                    ].attrData.children) {
                        this.typeEj = this.showInfo[
                            e.target.getAttribute("data-id")
                        ].attrData.children[i].detail.code;
                        this.typeSjAttrs = [0];
                        this.upAttrs = [this.typeEj];
                        return false;
                    }
                }
            }
            console.log(this.typeEj);
        },
        checkAttr(e) {
            if (e.target.tagName === "INPUT") {
                e.target.checked = true;
            }
            this.upAttrs = [];
            this.typeEj = e.target.getAttribute("data-name");
            this.upAttrs.push(e.target.getAttribute("data-name"));
        },
        checkAttrType(e) {
            if (e.target.tagName === "INPUT") {
                e.target.checked = true;
            }
            this.typeSjAttrs = [];
            this.typeSj = e.target.getAttribute("data-id");
            this.typeSjAttrs.push(e.target.getAttribute("data-id"));
        },
        getOrder() {
            if (this.num < 1) {
                popup.created({
                    content: "商品数量不能小于1",
                    time: 2000
                });
                return false;
            }
            Cookies.set("product", {
                name: this.showInfo[this.type].name,
                serviceId: this.showInfo[this.type].id,
                serviceAttrId: this.serviceAttrId,
                num: this.num,
                officialPrice: this.officialPrice,
                servicePrice: this.showInfo[this.type].servicePrice,
                total: this.total
            });
            console.log(this.showInfo[this.type].id);
            if (
                this.showInfo[this.type].id == "7741102214293331968" ||
                this.showInfo[this.type].id == "7741102108546539520"
            ) {
                this.$router.push("/order/auto");
            } else {
                this.$router.push("/order/ordinary");
            }
        },
        async loadQAData() {},
        mathImg(code) {
            let imgUrl = "";
            switch (code) {
                case "navigation_patent_domestic_01":
                    imgUrl += "navigation_patent_domestic_01.jpg";
                    break;
                case "navigation_patent_domestic_02":
                    imgUrl += "navigation_patent_domestic_02.jpg";
                    break;
                case "navigation_patent_domestic_03":
                    imgUrl += "navigation_patent_domestic_03.jpg";
                    break;

                case "navigation_patent_international_01":
                    imgUrl += "navigation_patent_international_01.jpg";
                    break;
                case "navigation_patent_international_02":
                    imgUrl += "navigation_patent_international_02.jpg";
                    break;
                case "navigation_patent_international_03":
                    imgUrl += "navigation_patent_international_02.jpg";
                    break; //暂无

                case "navigation_patent_law_01":
                    imgUrl += "navigation_patent_law_01.jpg";
                    break;
                case "navigation_patent_law_02":
                    imgUrl += "navigation_patent_law_02.jpg";
                    break;
                case "navigation_patent_law_03":
                    imgUrl += "navigation_patent_law_03.jpg";
                    break;
                case "navigation_patent_law_04":
                    imgUrl += "navigation_patent_law_04.jpg";
                    break;
                case "navigation_patent_law_05":
                    imgUrl += "navigation_patent_law_05.jpg";
                    break;
                case "navigation_patent_law_06":
                    imgUrl += "navigation_patent_law_06.jpg";
                    break;

                case "navigation_patent_other_01":
                    imgUrl += "navigation_patent_other_01.jpg";
                    break;
                // case 'navigation_patent_other_02':  imgUrl+='navigation_patent_other_02.jpg';break;//暂无
                case "navigation_patent_other_03":
                    imgUrl += "navigation_patent_other_03.jpg";
                    break; //暂无
                // case 'navigation_patent_other_04':  imgUrl+='navigation_patent_other_04.jpg';break;//暂无
                // case 'navigation_patent_other_05':  imgUrl+='navigation_patent_other_05.jpg';break;//暂无
                // case 'navigation_patent_other_06':  imgUrl+='navigation_patent_other_06.jpg';break;//暂无
                // case 'navigation_patent_other_07':  imgUrl+='navigation_patent_other_07.jpg';break;//暂无
                // case 'navigation_patent_other_08':  imgUrl+='navigation_patent_other_08.jpg';break;//暂无
                //商标
                case "navigation_trademark_query_02":
                    imgUrl += "navigation_trademark_query_02.png";
                    break;

                case "navigation_trademark_register_01":
                    imgUrl += "navigation_trademark_register_01.png";
                    break;
                case "navigation_trademark_register_02":
                    imgUrl += "navigation_trademark_register_01.png";
                    break;
                case "navigation_trademark_register_03":
                    imgUrl += "navigation_trademark_register_01.png";
                    break;
                case "navigation_trademark_register_04":
                    imgUrl += "navigation_trademark_register_01.png";
                    break;

                case "navigation_trademark_law_01":
                    imgUrl += "navigation_trademark_law_01.png";
                    break;
                case "navigation_trademark_law_02":
                    imgUrl += "navigation_trademark_law_02.png";
                    break;
                case "navigation_trademark_law_03":
                    imgUrl += "navigation_trademark_law_03.png";
                    break;
                case "navigation_trademark_law_04":
                    imgUrl += "navigation_trademark_law_04.png";
                    break;
                case "navigation_trademark_law_05":
                    imgUrl += "navigation_trademark_law_05.png";
                    break;
                case "navigation_trademark_law_06":
                    imgUrl += "navigation_trademark_law_06.png";
                    break;

                case "navigation_trademark_change_01":
                    imgUrl += "navigation_trademark_change_01.png";
                    break;
                case "navigation_trademark_change_02":
                    imgUrl += "navigation_trademark_change_02.png";
                    break;
                case "navigation_trademark_change_03":
                    imgUrl += "navigation_trademark_change_03.png";
                    break;
                case "navigation_trademark_change_04":
                    imgUrl += "navigation_trademark_change_04.png";
                    break;
                case "navigation_trademark_change_05":
                    imgUrl += "navigation_trademark_change_05.png";
                    break;
                case "navigation_trademark_change_06":
                    imgUrl += "navigation_trademark_change_06.png";
                    break;
                case "navigation_trademark_change_07":
                    imgUrl += "navigation_trademark_change_07.png";
                    break;
                case "navigation_trademark_change_08":
                    imgUrl += "navigation_trademark_change_08.png";
                    break;

                case "navigation_trademark_out_01":
                    imgUrl += "navigation_trademark_out_01.png";
                    break;
                case "navigation_trademark_out_02":
                    imgUrl += "navigation_trademark_out_02.png";
                    break;

                //版权
                case "navigation_copyright_register_01":
                    imgUrl += "navigation_copyright_register_01.jpg";
                    break;
                case "navigation_copyright_register_02":
                    imgUrl += "navigation_copyright_register_02.jpg";
                    break;
                case "navigation_copyright_register_03":
                    imgUrl += "navigation_copyright_register_03.jpg";
                    break;
                case "navigation_copyright_register_04":
                    imgUrl += "navigation_copyright_register_04.jpg";
                    break;
                case "navigation_copyright_register_05":
                    imgUrl += "navigation_copyright_register_05.jpg";
                    break;
                case "navigation_copyright_register_06":
                    imgUrl += "navigation_copyright_register_06.jpg";
                    break;
                case "navigation_copyright_register_07":
                    imgUrl += "navigation_copyright_register_07.jpg";
                    break;
                case "navigation_copyright_register_08":
                    imgUrl += "navigation_copyright_register_08.jpg";
                    break;
                case "navigation_copyright_other_01":
                    imgUrl += "navigation_copyright_other_01.png";
                    break;
                case "navigation_copyright_other_02":
                    imgUrl += "navigation_copyright_other_02.png";
                    break;
                case "navigation_copyright_other_03":
                    imgUrl += "navigation_copyright_other_03.png";
                    break;

                default:
                    imgUrl += "navigation_trademark_register_01.png";
                    break;
            }
            let Url = require("~/assets/images/showImg/" + imgUrl);
            return Url;
        }
    },
    created() {},
    mounted() {
        // 滚动监听
        let t;
        window.addEventListener("scroll", () => {
            let top = document.documentElement.scrollTop;
            if (top >= 450) {
                this.isFixed = true;
            } else {
                this.isFixed = false;
            }
        });
        // 删除默认数据
        Cookies.remove("product");
        sessionStorage.removeItem("brandInfo");
        sessionStorage.removeItem("concatInfo");
    },
    computed: {
        serviceAttrId() {
            let attrId = "";
            if (this.showInfo[this.type].attrData.detail) {
                attrId = this.showInfo[this.type].attrData.children[this.typeEj]
                    .children[this.typeSj].serviceAttrId;
            }
            return attrId;
        },
        officialPrice() {
            let attrPrice = 0;
            if (this.showInfo[this.type].attrData.detail) {
                console.log(this.showInfo[this.type].attrData.detail);
                attrPrice = this.showInfo[this.type].attrData.children[
                    this.typeEj
                ].children[this.typeSj].officialPrice;
            }
            return attrPrice + this.showInfo[this.type].officialPrice;
        },
        servicePriceTotal() {
            let price = 0;
            if (this.showInfo[this.type].attrData.detail) {
                price = this.showInfo[this.type].attrData.children[this.typeEj]
                    .children[this.typeSj].servicePrice;
            }
            return this.showInfo[this.type].servicePrice + price;
        },
        total() {
            return (this.officialPrice + this.servicePriceTotal) * this.num;
        }
    }
};
</script>

<style scoped lang="stylus">
* {
    box-sizing: border-box;
}

.overflow {
    overflow: hidden;
}

.w1200 {
    position: relative;
    overflow: hidden;
}

.w990 {
    width: 990px;
    float: right;
}

.show-content {
    background: transparent url('../../assets/images/show/bg.jpg') repeat-x left top;
    padding-bottom: 60px;

    .w1200 {
        padding-top: 40px;
    }

    .price-text {
        font-size: 26px;
        color: #ff6000;

        span {
            font-size: 20px;
        }
    }

    .product {
        overflow: hidden;

        .photo {
            float: left;
            border: 1px solid #ddd;
            overflow: hidden;
            position: relative;
            width: 520px;

            img {
                width: 100%;
            }

            div {
                display: block;
                position: absolute;
                z-index: 9;
                left: 50%;
                top: 50%;
                user-select: none;

                b {
                    display: block;
                    font-size: 40px;
                    color: #fff;
                    padding: 10px 20px;
                    white-space: nowrap;
                    position: relative;
                    left: -50%;
                    top: -38px;
                    letter-spacing: 4px;
                    background-color: rgba(0, 0, 0, 0.3);
                }
            }
        }

        .hidden {
            padding-left: 35px;

            h1 {
                font-size: 28px;
                line-height: 32px;
                color: #333;
            }

            p {
                font-size: 14px;
                line-height: 26px;
                color: #999;
                margin-top: 3px;
            }

            .price {
                background: #f5f5f5;
                margin: 10px 0;
                padding: 10px;

                table {
                    line-height: 30px;
                    font-size: 12px;
                    color: #999;

                    .title {
                        width: 90px;
                    }

                    .descrip {
                        color: #333;
                        font-size: 12px;
                        user-select: none;

                        i {
                            display: inline-block;
                            height: 18px;
                            border: 1px solid #ff0000;
                            color: #ff0000;
                            line-height: 16px;
                            padding: 0 10px;
                            margin-right: 5px;
                        }

                        a {
                            color: #999;
                            margin-left: 5px;

                            &:hover {
                                color: #ff6000;
                            }
                        }

                        .officerSlowBox {
                            overflow: hidden;

                            >label {
                                vertical-align: middle;
                                margin-right: 10px;
                                line-height: 22px;
                            }

                            input {
                                vertical-align: middle;
                                margin-right: 5px;
                                position: relative;
                                top: -2px;
                            }
                        }
                    }
                }
            }
        }

        .type {
            font-size: 14px;
            user-select: none;

            dl {
                overflow: hidden;
                line-height: 40px;

                dt {
                    float: left;
                    width: 80px;
                    padding-left: 10px;
                    color: #888;
                }

                dd {
                    overflow: hidden;

                    .type-item {
                        display: inline-block;
                        height: 30px;
                        line-height: 28px;
                        border: 1px solid #ccc;
                        color: #666;
                        margin: 0 10px 10px 0;
                        padding: 0 18px;
                        cursor: pointer;

                        &.atthis {
                            background: transparent url('../../assets/images/show/icon_checked.png') no-repeat right top;
                            border-color: #ff6000;
                            color: #ff6000;
                        }
                    }

                    .type-item-li {
                        display: inline-block;
                        height: 24px;
                        line-height: 22px;
                        border: 1px solid #ccc;
                        color: #666;
                        margin: 0 10px 10px 0;
                        padding: 0 18px;
                        cursor: pointer;

                        &.atthis {
                            border-color: #ff6000;
                            color: #ff6000;
                        }
                    }

                    .btn-num {
                        display: inline-block;
                        vertical-align: top;
                        height: 30px;
                        border: 1px solid #ccc;
                        color: #666;
                        width: 28px;
                        line-height: 30px;
                        text-align: center;
                        cursor: pointer;
                        margin-top: 5px;
                    }

                    >input {
                        width: 44px;
                        vertical-align: top;
                        text-align: center;
                        color: #333;
                        height: 30px;
                        line-height: 30px;
                        border: 1px solid #ccc;
                        border-width: 1px 0;
                        margin-top: 5px;
                    }

                    .type-tips {
                        padding-left: 10px;
                        color: #666;
                        font-size: 12px;
                    }
                }
            }
        }

        .btn {
            display: inline-block;
            height: 38px;
            line-height: 36px;
            text-align: center;
            border: 1px solid #fd7d22;
            padding: 0 48px;
            color: #fd7d22;
            margin-left: 10px;
            margin-right: 20px;
            margin-top: 5px;

            &.atthis {
                background: #fd7d22;
                color: #fff;
            }
        }

        .tips {
            font-size: 12px;
            color: #999;
            padding-left: 10px;
            margin-top: 15px;
        }
    }

    .tagbar {
        font-size: 16px;
        line-height: 50px;
        border: 1px solid #ddd;
        border-width: 1px 0;
        overflow: hidden;
        padding-left: 24px;

        a {
            float: left;
            margin-right: 85px;

            &.atthis {
                font-weight: bold;
                border-bottom: 3px solid #ff6000;
            }
        }
    }

    .border {
        border: 1px solid #ddd;
        border-top: 0;
        margin-top: 35px;
    }

    .pj {
        overflow: hidden;
        padding: 35px 0 40px 40px;

        .left {
            float: left;
            border: 1px solid #ff6000;
            border-radius: 50%;
            text-align: center;
            height: 100px;
            font-size: 0;
            width: 100px;

            b {
                display: block;
                font: bold 22px / 20px 'microsoft yahei';
                color: #ff6000;
                margin-top: 33px;
            }

            p {
                font: normal 12px / 14px 'microsoft yahei';
                margin-top: 8px;
                color: #999;
            }

            i {
                display: inline-block;
                width: 14px;
                height: 14px;
                font-size: 0;
                background: transparent url('../../assets/images/show/icon_star_0.jpg') no-repeat left center;
            }

            i.full {
                background-image: url('../../assets/images/show/icon_star_full.jpg');
            }

            i.half {
                background-image: url('../../assets/images/show/icon_star_half_0.jpg');
            }
        }

        .right {
            float: left;
            overflow: hidden;
            width: 650px;
            text-align: left;

            p {
                position: relative;
                font: normal 16px / 16px 'microsoft yahei';
                color: #656565;
                padding: 12px 0 12px 35px;
                margin-top: -9px;

                span {
                    position: absolute;
                    left: 147px;
                    top: 16px;
                    background: #f1f1f1;
                    display: block;
                    width: 500px;
                    height: 10px;
                    border-radius: 10px;

                    i {
                        position: absolute;
                        left: 0;
                        top: 0;
                        background: #fd7d22;
                        display: block;
                        height: 10px;
                        border-radius: 10px;
                    }
                }
            }
        }
    }

    .pj-cont {
        overflow: hidden;
        padding: 0 40px 0 40px;

        li {
            overflow: hidden;
            padding: 18px 0;
            border-top: 1px solid #ececec;

            .title {
                float: left;
                width: 190px;
                text-align: left;
                font: normal 12px / 20px 'microsoft yahei';
                color: #999;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;

                img {
                    width: 40px;
                    height: 40px;
                    border-radius: 50%;
                    margin-right: 10px;
                }
            }

            .pj-info {
                text-align: left;
                overflow: hidden;

                i {
                    padding: 7px;
                    font-size: 0;
                    background: transparent url('../../assets/images/show/icon_star.jpg') no-repeat left center;
                }

                i.full {
                    background-image: url('../../assets/images/show/icon_star_full.jpg');
                }

                i.half {
                    background-image: url('../../assets/images/show/icon_star_half.jpg');
                }

                p {
                    font: normal 14px / 24px 'microsoft yahei';
                    color: #000;
                    margin: 12px 0;
                }

                .note {
                    font: normal 12px / 20px 'microsoft yahei';
                    color: #999;

                    span {
                        display: inline-block;
                        margin-right: 40px;
                        color: #999;
                    }

                    b {
                        margin-right: 10px;
                        font-weight: normal;
                    }
                }
            }
        }
    }

    .service {
        overflow: hidden;

        li {
            float: left;
            width: 25%;
            text-align: center;
            padding: 60px 0;

            h5 {
                font-size: 16px;
                color: #fd7d22;
                font-weight: normal;
                margin: 10px 0;
            }

            p {
                color: #999;
                font-size: 12px;
            }
        }
    }

    .qa {
        overflow: hidden;
        text-align: left;
        padding: 20px 40px;

        li {
            padding: 15px 0;
            border-bottom: 1px solid #ececec;
            font: normal 14px / 24px 'microsoft yahei';
            color: #999;

            dl {
                color: #999;
                padding-bottom: 10px;
                overflow: hidden;

                dt {
                    float: left;
                }

                dd {
                    overflow: hidden;
                }
            }

            dl.title {
                font-weight: normal;
                color: #ff6000;
            }
        }
    }

    .about-me-block {
        padding-bottom: 40px;

        .text-intro {
            text-align: left;
            font-size: 14px;
            color: #666;
            line-height: 40px;
            margin: 40px 0 30px;
            padding: 0 45px;
            text-align: justify;
        }

        .down-img {
            text-align: center;
        }
    }
}

.agent {
    position: absolute;
    top: 455px;
    z-index: 3;
    background-color: #fff;
    white-space: nowrap;
}

.agent.fixed {
    position: fixed;
    top: 80px;
}

.agent .agent-header {
    color: #666;
    padding-bottom: 5px;
    font-size: 16px;
}

.agent .agent-header .icon-consult {
    display: block;
    float: left;
    width: 19px;
    height: 18px;
    margin: 0 7px 0 0;
    color: #fd7d22;
    font-size: 18px;
}

.agent .agent-bodyer .list {
    padding: 11px 0;
    font-size: 13px;
}

.agent .agent-bodyer .list dt {
    float: left;
    width: 80px;
    height: 80px;
    border: solid 1px #ddd;
    border-radius: 4px;
    overflow: hidden;
}

.agent .agent-bodyer .list dt img {
    width: 100%;
}

.agent .agent-bodyer .list dd {
    float: left;
    margin-left: 18px;
    padding-top: 2px;
}

.agent .agent-bodyer .list dd .icon-renzheng {
    display: inline-block;
    padding: 0 4px;
    height: 15px;
    line-height: 14px;
    margin-left: 5px;
    font-size: 10px;
    color: #fff;
}

.agent .agent-bodyer .list dd span {
    display: block;
}

.agent .agent-bodyer .list dd .r-1 {
    color: #666;
}

.agent .agent-bodyer .list dd .r-2 {
    color: #888;
    padding: 4px 0 8px;
}

.agent .agent-bodyer .list dd .r-3 {
    display: block;
    width: 78px;
    border: solid 1px #f08b2f;
    text-align: center;
    line-height: 25px;
    border-radius: 3px;
    color: #f08b2f;
}
</style>

