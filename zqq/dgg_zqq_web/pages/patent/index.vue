<template>
    <div class="content">
        <div class="banner">
            <a href="/show/navigation_patent_domestic_01&S7741445307712569344.html"><img width="1920" src="../../assets/images/patent/bannerTop.jpg" alt=""></a>
        </div>
        <div class="tab-catalog-00">
            <div class="w1200">
                <a class="atthis" href="#handle">办理专利业务</a>
                <a href="#guarantee">专利担保业务</a>
                <a href="#other">其他专利服务</a>
            </div>
        </div>
        <div class="zqq-intro">
            <dl>
                <dt>
                    <img src="../../assets/images/show/p1.jpg" alt="知千秋专利负责人江胜辉">
                </dt>
                <dd class="">
                    <b>知千秋专利负责人江胜辉</b>
                    <div class="zqq-intro-sts">
                        <p :class="{'s_show': introIndex==0, 's_hide': introIndex!=0}">好的专利，质量一定是能让客户感受到的，说客户不懂，说专利质量无法衡量，都是不想对质量负责。</p>
                        <p :class="{'s_show': introIndex==1, 's_hide': introIndex!=1}"> 专利之美，在于逻辑的严谨，保护的合理，用词的准确。</p>
                        <p :class="{'s_show': introIndex==2, 's_hide': introIndex!=2}">未来的专利服务，处理进度要实时可见，案件质量要直观可见，代理人能力要真实可见。</p>
                        <p :class="{'s_show': introIndex==3, 's_hide': introIndex!=3}">商品消费升级，是从能用到既好用又好看；专利服务升级，是从能授权，到既能授权，又能合理保护还富有美感。</p>
                    </div>
                </dd>
            </dl>

        </div>
        <div id="handle" class="w1200 handle">
            <div class="title-catalog-00">办理专利业务</div>
            <ul>
                <li v-for="item in [0, 1, 2]" v-if="priceData[item].serviceItem" :key="priceData[item].serviceItem.id">
                    <div class="line-animate">
                        <a class="none" target="_blank" :href="getUrl(priceData[item].serviceItem.typeLevel3Code, priceData[item].serviceItem.number)">
                            <img v-if="item == 0" src="../../assets/images/patent/1.png" height="72" alt="">
                            <img v-if="item == 1" src="../../assets/images/patent/2.png" height="72" alt="">
                            <img v-if="item == 2" src="../../assets/images/patent/3.png" height="72" alt="">

                            <h3>{{priceData[item].serviceItem.name}}</h3>
                            <b>￥{{$priceCount(priceData[item])}}
                                <span>/件</span>
                            </b>
                            <p>{{priceData[item].serviceItem.describle}}</p>
                        </a>
                        <a target="_blank" :href="getUrl(priceData[item].serviceItem.typeLevel3Code, priceData[item].serviceItem.number)">立即办理</a>
                    </div>
                </li>
            </ul>
        </div>

        <div id="guarantee" class="objection defuse">
            <div class="w1200">
                <div class="title-catalog-00">专利担保服务</div>
                <div class="assure-msg">专利不授权，再好的技术也没用；针对专利授权，知千秋提供授权担保服务，如果专利不授权，全额退还服务费，知千秋的授权担保服务是通过自有的专利大数据及智能工具指导专利代理人进行保护范围设计，并规避现有技术方案，从而大大提高了专利授权几率并降低人工成本，实现超高性价比的担保服务</div>
                <ul>
                    <li v-for="item in [3, 4, 5]" v-if="priceData[item].serviceItem" :key="priceData[item].serviceItem.id" class="line-animate">
                        <a :href="getUrl(priceData[item].serviceItem.typeLevel3Code, priceData[item].serviceItem.number)">
                            <h4>
                                <b>￥{{$priceCount(priceData[item])}}
                                    <span> /件</span>
                                </b>{{priceData[item].serviceItem.name}}</h4>
                            <span></span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <div id="other" class="defuse w1200">
            <div class="title-catalog-00">其他专利服务</div>
            <ul>

                <li v-for="item in [6, 7, 8, 9, 10,11,12]" v-if="priceData[item].serviceItem" :key="priceData[item].serviceItem.id" class="line-animate">
                    <a :href="getUrl(priceData[item].serviceItem.typeLevel3Code, priceData[item].serviceItem.number)">
                        <h4>
                            <b>￥{{$priceCount(priceData[item])}}
                                <span> /件</span>
                            </b>{{priceData[item].serviceItem.name}}</h4>
                        <p>{{priceData[item].serviceItem.describle}}</p>
                        <span></span>
                    </a>
                </li>
            </ul>

            <div class="zqq-zx">
                <p>了解更多专利知识，顾问一对一解答</p>
                <a href="javascript:;" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)">立即咨询</a>
            </div>
        </div>

        <div class="zqq-ele-wrap">
            <dl class="zqq-ele">
                <dt>知产可传世，千秋岁月长</dt>
                <dd>技术驱动，数据未来</dd>
                <dd class="eles">
                    <a href="">
                        <img width="140" src="../../assets/images/patent/img_ds_00.png" alt="四川大学">
                    </a>
                    <a href="">
                        <img width="140" src="../../assets/images/patent/img_ds_01.png" alt="电子科技大学">
                    </a>
                    <a href="">
                        <img width="140" src="../../assets/images/patent/img_ds_02.png" alt="爱迪眼科">
                    </a>
                    <a href="">
                        <img width="140" src="../../assets/images/patent/img_ds_03.png" alt="成都信息工程大学">
                    </a>
                    <a href="">
                        <img width="140" src="../../assets/images/patent/img_ds_04.png" alt="中国石油">
                    </a>
                    <a href="">
                        <img width="140" src="../../assets/images/patent/img_ds_05.png" alt="西南交通大学">
                    </a>
                    <a href="">
                        <img width="140" src="../../assets/images/patent/img_ds_06.png" alt="中国中铁">
                    </a>
                    <a href="">
                        <img width="140" src="../../assets/images/patent/img_ds_07.png" alt="国家电网">
                    </a>
                </dd>
            </dl>
        </div>

        <floor :floorAttr="[{name: '办理专利', type: '#handle'},{name: '专利担保', type: '#guarantee'},{name: '其他专利', type: '#other'}]" />
        <to-top />
        <consult :isShow="isShow" @closeConsult="closeConsult" />
    </div>
</template>

<script>
import floor from "@/components/floor/floor";
import toTop from "@/components/toTop";
import consult from "@/components/consult";
import axios from "axios";
export default {
    head() {
        return {
            title:
                "专利业务-_商标查询_商标注册_专利查询_专利申请_版权登记_知千秋官网",
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
            ],
            link:[
                {
                    rel: "canonical",
                    content: this.baseUrl
                }
            ]
        };
    },
    async asyncData({ params, redirect, error, env, route}) {
        return Promise.all([
            axios.get(`${env.baseUrl}serviceItem/findServiceItemByNumber`, {
                params: {
                    numbers: `S7741445307712569344,S7741445546913726465,S7741445695501139969,S7742594014019878913,S7742594952902246401,S7742595304011628544,S7741446516741668865,S7741446873093931009,S7741447048860434433,S7741448119309729793,S7741448337275125761,S7743996762398294016,S7741445996140462081`
                }
            })
        ]).then(res => {
            let { data } = res[0];
            if (data.code == 0) {
                return {
                    priceData: data.data,
                    baseUrl: env.webSite + route.fullPath
                };
            } else {
                error({ statusCode: 500, message: data.msg });
            }
        });
    },
    components: {
        floor,
        toTop,
        consult
    },
    data() {
        return {
            introIndex: 0,
            isShow: false
        };
    },
    methods: {
        getUrl(code, id) {
            if (id) {
                return `/show/${code}&${id}.html`;
            }
            return `/show/${code}.html`;
        },
        showConsult(e) {
            this.isShow = true;
        },
        closeConsult() {
            this.isShow = false;
        }
    },
    mounted() {
        // 滚动显示
        setInterval(() => {
            if (this.introIndex > 2) {
                this.introIndex = 0;
            } else {
                this.introIndex++;
            }
        }, 3000);
        console.log(this.priceData);
    }
};
</script>

<style scoped lang="stylus">
.content {
    overflow: hidden;
}

.banner {
    width: 1920px;
    position: relative;
    left: 50%;
    margin-left: -960px;
}

.tab-catalog-00 {
    border-bottom: 1px solid #d8d8d8;
    line-height: 58px;
    font-size: 14px;
    height: 58px;
    margin-top: 30px;
    text-align: center;

    a {
        margin: 0 81px;
        padding: 0 3px;
        display: inline-block;
        line-height: 55px;

        &.atthis {
            border-bottom: 4px solid #fd7d22;
        }
    }
}

.title-catalog-00 {
    font-size: 20px;
    line-height: 48px;
    color: #333;
    font-weight: bold;
    padding-left: 5px;
    padding-top: 30px;
}

.handle {
    ul {
        overflow: hidden;
        padding-top: 40px;
        padding-bottom: 45px;

        li {
            float: left;
            width: 400px;
            box-sizing: border-box;
            text-align: center;

            >div {
                width: 255px;
                padding-top: 35px;
                margin: 0 auto;
                border: 1px solid #fff;
            }

            h3 {
                font: normal 18px / 40px 'microsoft yahei';
                color: #333;
                margin-top: 10px;
            }

            b {
                font: bold 24px / 30px 'microsoft yahei';
                color: #ff6000;

                span {
                    font-size: 14px;
                    font-weight: normal;
                }
            }

            p {
                font-size: 14px;
                color: #999;
                margin-top: 10px;
            }

            a {
                padding: 0 15px;
                display: inline-block;
                border: 1px solid #ff6000;
                border-radius: 30px;
                color: #ff6000;
                font-size: 14px;
                line-height: 26px;
                height: 28px;
                vertical-align: middle;
                margin: 25px 0 30px;
                position: relative;
                z-index: 9;

                &:hover {
                    background: #ff6000;
                    color: #fff;
                }
            }
        }
    }
}

.search {
    background: #f9f9f9;
    overflow: hidden;

    .search-content {
        background: #fff;
        overflow: hidden;
        margin: 30px 0 35px;

        .right {
            float: right;
            padding-right: 45px;
            padding-top: 50px;

            b {
                font-size: 24px;
                color: #ff6000;

                span {
                    font-size: 14px;
                    color: #999;
                    font-weight: normal;
                }

                margin-right: 25px;
            }

            a {
                display: inline-block;
                height: 28px;
                line-height: 26px;
                border: 1px solid #ff6000;
                color: #ff6000;
                border-radius: 30px;
                padding: 0 15px;

                &:hover {
                    background: #ff6000;
                    color: #fff;
                }
            }
        }

        >div {
            img {
                float: left;
                margin: 27px 15px 35px 47px;
            }

            >div {
                padding-top: 37px;

                h4 {
                    font-size: 18px;
                    line-height: 30px;
                    color: #333;
                    font-weight: normal;
                }

                p {
                    font-size: 14px;
                    line-height: 28px;
                    color: #666;
                }
            }
        }
    }
}

.defuse {
    ul {
        overflow: hidden;
        padding-top: 25px;
        padding-bottom: 50px;

        li {
            width: 380px;
            height: 140px;
            box-sizing: border-box;
            float: left;
            margin: 0 10px 20px;
            border: 1px solid #ddd;
            padding: 10px;
            position: relative;

            h4 {
                font-size: 18px;
                color: #333;
                padding: 20px 0 10px;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: norwap;
                font-weight: normal;

                b {
                    float: right;
                    font-size: 24px;
                    color: #ff6000;

                    span {
                        font-size: 14px;
                        color: #999;
                        font-weight: normal;
                    }
                }
            }

            p {
                font-size: 14px;
                color: #666;
            }

            a {
                display: block;
                height: 100%;
                position: relative;
                z-index: 9;

                >span {
                    display: block;
                    position: absolute;
                    width: 23px;
                    height: 16px;
                    left: 300px;
                    top: 100px;
                    opacity: 0;
                    background: transparent url('../../assets/images/brand/icon_right.png') no-repeat left top;
                    transition: all 0.5s;
                    z-index: 9;
                }
            }

            &:hover {
                a>span {
                    opacity: 1;
                    left: 320px;
                }
            }
        }
    }
}

.objection {
    background: #f9f9f9;

    ul {
        li {
            background: #fff;
            height: 90px;
            border: 1px solid transparent;
        }
    }
}

.assure-msg {
    margin-top: 19px;
    font-size: 14px;
    color: #333333;
    line-height: 20px;
}

/* 负责人介绍 */
.zqq-intro {
    position: relative;
    width: 100%;
    padding: 60px 0;
    border-bottom: 1px solid #ddd;

    &:after {
        content: '';
        position: absolute;
        left: 0;
        bottom: 0;
        width: 100%;
        height: 1px;
        background-size: 100% 1px;
    }

    dl {
        width: 1200px;
        margin: 0 auto;
        overflow: hidden;

        dt {
            float: left;
            margin-left: 50px;
            width: 88px;
            height: 88px;
            border-radius: 50%;
            overflow: hidden;

            img {
                display: block;
                width: 100%;
                height: 100%;
            }
        }

        dd {
            float: left;
            margin-left: 56px;
            width: 1000px;

            b {
                font-size: 20px;
                color: #333;
                letter-spacing: 0;
                font-weight: normal;
            }

            p {
                font-size: 24px;
                color: #222;
                letter-spacing: 0;
                line-height: 36px;
            }
        }
    }
}

.zqq-intro-sts {
    position: relative;
    margin-top: 16px;
    width: 100%;
    height: 72px;

    p {
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
    }

    p.s_show {
        opacity: 1;
        animation: zl_scroll_show 2s infinite;
        animation-iteration-count: 1;
    }

    p.s_hide {
        opacity: 0;
        transform: translateY(-20px);
        transition: 1s;
    }
}

@keyframes zl_scroll_show {
    0% {
        transform: translateY(20px);
        opacity: 0;
    }

    100% {
        transform: translateY(0px);
        opacity: 1;
    }
}

@keyframes zl_scroll_hide {
    0% {
        transform: translateY(0px);
        opacity: 1;
    }

    100% {
        transform: translateY(-20px);
        opacity: 0;
    }
}

.zqq-zx {
    margin-top: 0;
    overflow: hidden;

    p {
        font-size: 24px;
        color: #333333;
        text-align: center;
        line-height: 31px;
    }

    a {
        display: block;
        margin: 26px auto 40px;
        width: 260px;
        height: 42px;
        border: 1px solid #ff7200;
        font-size: 20px;
        text-align: center;
        line-height: 42px;
        color: #ff7200;
    }
}

.zqq-ele-wrap {
    width: 100%;
    height: 290px;
    margin-top: 30px;
    overflow: hidden;
    background: #f5f5f5;
}

.zqq-ele {
    width: 1200px;
    margin: 0 auto;
    overflow: hidden;

    dt {
        font-size: 32px;
        color: #222222;
        text-align: center;
        line-height: 42px;
        margin-top: 35px;
    }

    dd:nth-child(2) {
        font-size: 20px;
        color: #666666;
        text-align: center;
        line-height: 26px;
        margin-top: 12px;
    }
}

.eles {
    margin-top: 43px;

    a {
        display: inline-block;
        width: 12.19%;
        font-size: 0;
        vertical-align: middle;

        img {
            display: block;
            margin: 0 auto;
        }
    }
}
</style>

