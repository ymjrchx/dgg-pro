<template>
    <div class="content">
        <div class="banner">
            <a href="/copyright"><img width="1920" src="../../assets/images/copyright/bannerTop.jpg" alt=""></a>
        </div>
        <div class="tab-catalog-00">
            <div class="w1200">
                <a class="atthis" href="#handle">办理版权业务</a>
                <a href="#guarantee">作品版权保护</a>
                 <a href="#other">其他版权服务</a>
            </div>
        </div>
        <div id="handle" class="w1200 handle">
            <div class="title-catalog-00">办理版权业务</div>
            <ul>
                <li v-for="item in [0, 1]" v-if="priceData[item].serviceItem" :key="priceData[item].serviceItem.id">
                    <div class="line-animate">
                        <a class="none" target="_blank" :href="getUrl(priceData[item].serviceItem.typeLevel3Code, priceData[item].serviceItem.number)">
                            <img v-if="item == 0" src="../../assets/images/copyright/1.png" height="73" alt="">
                            <img v-if="item == 1" src="../../assets/images/copyright/2.png" height="73" alt="">
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
                <div class="title-catalog-00">作品版权保护</div>
                <ul>
                    <li v-for="item in [2, 3, 4, 5, 6, 7]" v-if="priceData[item].serviceItem" :key="priceData[item].serviceItem.id" class="line-animate">
                        <a target="_blank" :href="getUrl(priceData[item].serviceItem.typeLevel3Code, priceData[item].serviceItem.number)">
                            <h4>
                                <b>￥{{$priceCount(priceData[item])}}
                                    <span> /件</span>
                                </b>{{priceData[item].serviceItem.name}}</h4>
                            <p>{{priceData[item].serviceItem.describle}}</p>
                            <span></span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>

        <div id="other" class="objection defuse">
            <div class="w1200">
                <div class="title-catalog-00">其他版权服务</div>
                <ul>
                    <li v-for="item in [8, 9,10]" v-if="priceData[item].serviceItem" :key="priceData[item].serviceItem.id" class="line-animate">
                        <a target="_blank" :href="getUrl(priceData[item].serviceItem.typeLevel3Code, priceData[item].serviceItem.number)">
                            <h4>
                                <b>￥{{$priceCount(priceData[item])}}
                                    <span> /件</span>
                                </b>{{priceData[item].serviceItem.name}}</h4>
                            <p>{{priceData[item].serviceItem.describle}}</p>
                            <span></span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <floor :floorAttr="[{name: '办理版权', type: '#handle'},{name: '版权保护', type: '#guarantee'},{name:'其他服务',type:'#other'}]" />
        <to-top />
    </div>
</template>

<script>
import floor from "@/components/floor/floor";
import toTop from "@/components/toTop";
import axios from "axios";
export default {
    head() {
        return {
            title:
                "版权业务_商标查询_商标注册_专利查询_专利申请_版权登记_知千秋官网",
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
    async asyncData({ params, redirect, error, env, route }) {
        return Promise.all([
            axios.get(`${env.baseUrl}serviceItem/findServiceItemByNumber`, {
                params: {
                    numbers: `S7741448551415316481,S7741448858560004097,S7741449030811680769,S7741449180514779137,S7741449496912101377,S7741449641057746945,S7741449831181352961,S7741449994922786817,S7745126278087475200,S7745127032672124929,S7745127632407265281`
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
        toTop
    },
    data() {
        return {
            introIndex: 0
        };
    },
    mounted(){
        // console.log(this.priceData)
    },
    methods: {
        getUrl(code, id) {
            if (id) {
                return `/show/${code}&${id}.html`;
            }
            return `/show/${code}.html`;
        }
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
            width: 600px;
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
                    right: 40px;
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
                    right: 20px;
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
            width: 580px;
            border: 1px solid transparent;
        }
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

