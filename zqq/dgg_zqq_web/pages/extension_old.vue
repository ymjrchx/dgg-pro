<template>
    <div class="show">
        <div class="banner">
            <img src="../assets/images/extension/solgan.png" alt="知千秋专注于商标注册">
            <div class="search" @mouseleave="isIndustry=false">
                <input type="text" @keyup="replaceCn" maxlength="20" v-model="keyword" placeholder="请输入商标名称">
                <div  @click="isIndustry =!isIndustry" class="hy">{{industry!==''?industryList[industry].name:'请选择行业'}}</div>
                <button @click="submit">通过率AI查询</button>

                <div class="industryArea" v-if="isIndustry">
                    <ul>
                      <li>
                        <dl>
                          <dt>所有行业</dt>
                          <dd>
                            <a v-for="(item, index) in industryList" :key="item.id" @click="seletedIndustry(index)" :class="{'atthis': industry === index}" href="javascript:void(0);">{{item.name}}</a>
                          </dd>
                        </dl>
                      </li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="reg">
            已有注册成功用户
            <p><b>13,658</b>人</p>
        </div>

        <div class="newReg">
            <dl>
                <dt>最新注册：</dt>
                <dd class="swiper-container">
                    <div class="swiper-wrapper">
                        <a class="swiper-slide" href="javascript:void(0);"><span>7分钟前</span>134****5486<i>/</i></a>   
                        <a class="swiper-slide" href="javascript:void(0);"><span>20分钟前</span> 137****5456 <i>/</i></a>    
                        <a class="swiper-slide" href="javascript:void(0);"><span>20分钟前</span> 158****1446 <i>/</i></a>    
                        <a class="swiper-slide" href="javascript:void(0);"><span>35分钟前</span> 134****2488 <i>/</i></a>
                        <a class="swiper-slide" href="javascript:void(0);"><span>40分钟前</span> 132****2476 <i>/</i></a>
                        <a class="swiper-slide" href="javascript:void(0);"><span>1小时前</span> 189****4466  <i>/</i></a>
                        <a class="swiper-slide" href="javascript:void(0);"><span>1小时40分钟前</span> 131****5686  <i>/</i></a>
                        <a class="swiper-slide" href="javascript:void(0);"><span>2小时04分钟前</span> 138****4656  <i>/</i></a>
                    </div>
                </dd>
            </dl>
        </div>

        <div class="brand">
            <h1>智能自助商标注册</h1>
            <div class="content">
                <h2>服务特色：0服务费、注册更高效</h2>
                <p>注册资料：3分钟送达商标局<br>
                    注册进度：商标局同步<br>
                    智能服务：AI短信、微信和邮件通知
                </p>
                <div class="tag">
                    <a href="javascript:void(0);">0服务费</a>
                    <a href="javascript:void(0);">3分钟极速提交</a>
                    <a href="javascript:void(0);">通过率分析</a>
                    <a href="javascript:void(0);">智能类别</a><br>
                    <a href="javascript:void(0);">AI算法</a>
                    <a href="javascript:void(0);">AI通知</a>
                    <a href="javascript:void(0);">商标局同步</a>
                    <a href="javascript:void(0);">图样快速生成</a>
                </div>
                <div class="price">
                    ￥<b>299</b>
                    <p class="tips">（先审核后付费）</p>
                </div>
                <!-- <a href="/order/auto?360sem&zqqpc?&guanggao" target="_blank" class="regBtn">立即注册</a> -->
                <button @click="goOrder('S7741102214293331969')">立即注册</button>
            </div>

            <h3>为什么<b>仅299元</b>低价商标注册？</h3>
            <ul>
                <li>
                    <img src="../assets/images/extension/icon_tz.png" alt="4000万风投">
                    <div class="title">4000万风投</div>
                    <p>顶呱呱4000万天使轮风投知千秋</p>
                </li>
                <li>
                    <img src="../assets/images/extension/icon_ai.png" alt="重构注册流程：简单、易懂、快捷">
                    <div class="title">重构注册流程：简单、易懂、快捷</div>
                    <p>商标注册仅需3步：填写资料、付款、同步商标局</p>
                </li>
                <li>
                    <img src="../assets/images/extension/icon_dsj.png" alt="大数据分析">
                    <div class="title">大数据分析</div>
                    <p>30万+案件验证商标大数据</p>
                </li>
            </ul>
        </div>

        <div class="td">
            <div class="wrapper">
                <div class="left item">
                    <div class="title">商标行业痛点</div>
                    <ul>
                        <li>行业门槛高</li>
                        <li>企业or个人入门难</li>
                        <li>传统注册</li>
                        <li>成本虚高</li>
                        <li>步骤繁琐</li>
                        <li>注册较多商标，管理难，易流失</li>
                    </ul>
                </div>

                <div class="right item">
                    <div class="title">知千秋解决方案</div>
                    <ul>
                        <li>简单、易懂、快捷</li><br>
                        <li>重构注册流程</li>
                        <li>节省人力</li>
                        <li>AI智能报件</li>
                        <li>商标管理、监控、AI智能提醒</li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="vs">
            <div class="left item">
                <div class="title">
                    智能自助商标注册
                    <p>以服务为基点，重新定义商标注册全流程</p>
                </div>
                <ul>
                    <li>填写资料</li>
                    <li>付款下单</li>
                    <li>同步商标局</li>
                    <li class="last"><span>（递交后）</span>实时跟踪系统<p>商标局实时同步、AI短信、微信和邮件通知</p></li>
                </ul>
            </div>

            <div class="right item">
                <div class="title">
                    传统商标注册
                </div>
                <ul>
                    <li>寻找商标代理机构</li>
                    <li>对洽谈</li>
                    <li>筛选确定合作代理公司</li>
                    <li>签订代理合同</li>
                    <li>代理递交资料</li>
                    <li><span>（递交后）</span>人工跟进</li>
                    <li><span>（递交后）</span>商标最新状态</li>
                    <li class="last"><span>（递交后）</span>反馈客户</li>
                </ul>
            </div>
        </div>

        <div class="flow">
            <div class="wrapper">
                <div class="title">您自己到商标局：注册办理流程</div>
                <ul>
                    <li>商标取名</li>
                    <li>商标近似查询</li>
                    <li>规避盲查</li>
                    <li>准备资料</li>
                    <li class="last">递交官方<button onclick="window.open('/flow.png');">点击查看<br>官方流程图</button></li>
                </ul>
                <div class="kn">
                    <div class="sign-title">您自己注册<span>困难</span></div>
                    <a href="javascript:void(0);">不懂得，如何避免盲查</a>
                    <a href="javascript:void(0);">不清楚，必要资料准备</a>
                    <a href="javascript:void(0);">太复杂，官方递交盲区</a><br>
                    <a href="javascript:void(0);">商标选取，本就不容易，历经千辛万苦，最后递交，却直接被驳回</a>
                </div>
            </div>
        </div>

        <div class="qa wrapper">
            <div class="left">
                商标注册申请常见问题指南
                <a target="_blank" href="https://www.zhiqianqiu.com/xwxq/903.html">点击查看</a>
            </div>
            <div class="left right">
                商标注册失败六大原因
                <a target="_blank" href="https://www.zhiqianqiu.com/xwxq/902.html">点击查看</a>
            </div>
        </div>

        <div class="tz wrapper">
            <div class="title">战略投资</div>
            <p>顶呱呱战略投资知千秋<b>4000万</b>天使轮</p>
            <div class="tips">相关媒体报道</div>
            <ul>
                <li><a href="javascript:void(0);"><img src="../assets/images/extension/logo_00.png" alt="新闻logo"></a></li>
                <li><a href="javascript:void(0);"><img src="../assets/images/extension/logo_01.jpg" alt="新闻logo"></a></li>
                <li><a href="javascript:void(0);"><img src="../assets/images/extension/logo_02.jpg" alt="新闻logo"></a></li>
                <li><a href="javascript:void(0);"><img src="../assets/images/extension/logo_03.jpg" alt="新闻logo"></a></li>
                <li><a href="javascript:void(0);"><img src="../assets/images/extension/logo_04.jpg" alt="新闻logo"></a></li><br>
                <li><a href="javascript:void(0);"><img src="../assets/images/extension/logo_05.jpg" alt="新闻logo"></a></li>
                <li><a href="javascript:void(0);"><img src="../assets/images/extension/logo_06.jpg" alt="新闻logo"></a></li>
                <li><a href="javascript:void(0);"><img src="../assets/images/extension/logo_07.jpg" alt="新闻logo"></a></li>
                <li><a href="javascript:void(0);"><img src="../assets/images/extension/logo_08.jpg" alt="新闻logo"></a></li>
                <li><a href="javascript:void(0);"><img src="../assets/images/extension/logo_09.jpg" alt="新闻logo"></a></li>
            </ul>
            <div class="tips">投资签约仪式现场</div>
            <div class="swiper-cont">
            <div class="swiper-container-01">
                <div class="swiper-wrapper">
                  <div class="swiper-slide"><img src="../assets/images/extension/img_qy_00.jpg" alt="签约仪式"></div>
                  <div class="swiper-slide"><img src="../assets/images/extension/img_qy_01.jpg" alt="签约仪式"></div>
                  <div class="swiper-slide"><img src="../assets/images/extension/img_qy_02.jpg" alt="签约仪式"></div>
                  <div class="swiper-slide"><img src="../assets/images/extension/img_qy_03.jpg" alt="签约仪式"></div>
                  <div class="swiper-slide"><img src="../assets/images/extension/img_qy_04.jpg" alt="签约仪式"></div>
                  <div class="swiper-slide"><img src="../assets/images/extension/img_qy_05.jpg" alt="签约仪式"></div>
                </div>
                <!-- Add Arrows -->
                <div class="swiper-button-next"></div>
                <div class="swiper-button-prev"></div>
              </div>
            </div>
        </div>

        <div class="al">
            <div class="wrapper">
                <div class="title">荣誉资质</div>
                <div class="zz">
                    <img src="../assets/images/extension/img_zz_00.png" alt="资质">
                    <img src="../assets/images/extension/img_zz_01.png" alt="资质">
                </div>

                <div class="title">服务客户</div>
                <ul>
                    <li><a href="javascript:void(0);"><img src="../assets/images/main/logo_03.jpg" alt="logo"></a></li>
                    <li><a href="javascript:void(0);"><img src="../assets/images/main/logo_05.png" alt="logo"></a></li>
                    <li><a href="javascript:void(0);"><img src="../assets/images/main/logo_07.png" alt="logo"></a></li>
                    <li><a href="javascript:void(0);"><img src="../assets/images/main/logo_09.jpg" alt="logo"></a></li>
                    <li><a href="javascript:void(0);"><img src="../assets/images/main/logo_11.png" alt="logo"></a></li><br>
                    <li><a href="javascript:void(0);"><img src="../assets/images/main/logo_18.png" alt="logo"></a></li>
                    <li><a href="javascript:void(0);"><img src="../assets/images/main/logo_19.png" alt="logo"></a></li>
                    <li><a href="javascript:void(0);"><img src="../assets/images/main/logo_20.png" alt="logo"></a></li>
                    <li><a href="javascript:void(0);"><img src="../assets/images/main/logo_21.jpg" alt="logo"></a></li>
                    <li><a href="javascript:void(0);"><img src="../assets/images/main/logo_22.png" alt="logo"></a></li>
                </ul>
            </div>
        </div>

        <div class="down wrapper">
            <div class="title">资料准备</div>
            <div class="left item">
                <div class="sign-title">企业（大陆）</div>
                <div class="bz">
                    <p><i>01</i>营业执照复印件<a href="javascript:void(0);">（加盖鲜章）</a></p>
                    <p><i>02</i>清晰商标图样<a href="javascript:void(0);">（加盖鲜章）</a></p>
                    <p><i>03</i>委托书<a href="javascript:void(0);">（加盖鲜章）</a></p>
                </div>
                <img src="../assets/images/extension/img_cl_00.jpg" alt="营业执照资料">
                <img src="../assets/images/extension/img_wts.jpg" alt="委托书">
            </div>

            <div class="right item">
                <div class="sign-title">个体工商户（大陆）</div>
                <div class="bz">
                    <p><i>01</i>个体工商户营业执照副本复印件<a href="javascript:void(0);">（需签名）</a></p>
                    <p><i>02</i>身份证正、反面复印件<a href="javascript:void(0);">（需签名）</a></p>
                    <p><i>03</i>清晰商标图样</p>
                    <p><i>04</i>委托书<a href="javascript:void(0);">（需签名）</a></p>
                </div>
                <img src="../assets/images/extension/img_cl_00.jpg" alt="营业执照资料">
                <img src="../assets/images/extension/img_cl_01.jpg" alt="身份证复印件资料">
            </div>

            <div class="title">其他商标服务</div>

            
            <div id="objection" class="objection defuse">
                <div class="w1200">
                    <div class="title-catalog-00">异议他人商标</div>
                    <ul>
                        <li v-for="item in [7, 8, 9]" :key="priceData[item].serviceItem.id" class="line-animate" v-if="priceData[item].serviceItem">
                            <a href="javascript:void(0);"  onclick="openZoosUrl('chatwin');">
                                <h4>
                                    <b>了解详情</b>{{priceData[item].serviceItem.name}}</h4>
                                <p>{{priceData[item].serviceItem.describle}}</p>
                                <span></span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>

            <div id="change" class="defuse w1200">
                <div class="title-catalog-00">商标信息变更</div>
                <ul>
                    <li v-for="item in [10, 11, 12, 13, 14, 15, 16, 17]" :key="priceData[item].serviceItem.id" class="line-animate" v-if="priceData[item].serviceItem">
                        <a href="javascript:void(0);" onclick="openZoosUrl('chatwin');">
                            <h4>
                                <b>了解详情</b>{{priceData[item].serviceItem.name}}</h4>
                            <p>{{priceData[item].serviceItem.describle}}</p>
                            <span></span>
                        </a>
                    </li>
                </ul>
            </div>

            <div id="international" class="objection defuse">
                <div class="w1200">
                    <div class="title-catalog-00">国际商标业务</div>
                    <ul>
                        <li v-for="item in [18, 19]" :key="priceData[item].serviceItem.id" class="line-animate" v-if="priceData && priceData[item].serviceItem">
                            <a href="javascript:void(0);" onclick="openZoosUrl('chatwin');">
                                <h4>
                                    <b>了解详情</b>{{priceData[item].serviceItem.name}}</h4>
                                <p>{{priceData[item].serviceItem.describle}}</p>
                                <span></span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="team">
            <div class="title">团队概况</div>
            <p>商标注册全流程的重新定义者<br>
<span>我们更专注：</span>专注商标申请十年，注册商标游刃有余<br>
<span>我们更专业：</span>拥有详尽的知识储备，经多见广的资深顾问团队<br>
<span>我们更专一：</span>我们只做商标注册，目标笃定，水滴石穿<br>
<span>因为专注，所以专业，因为专一，所以卓越</span></p>
        </div>

        <div class="fixAd" v-show="isFixedAd">
            <div class="fixed">
                <div class="sign">商标注册仅<span>299元</span>，0服务费</div>
                <del>（官方收费300元）</del>

                <div class="input">
                    <input type="text" v-model="name" maxlength="10"  placeholder="姓名" >
                    <input type="text" class="phone" maxlength="11" v-model="phone" placeholder="手机号" >
                    <button @click="customerMsg">提交联系</button>
                </div>

                <div class="colse" @click="isFixedAd = false"><img src="../assets/images/extension/icon_close.png" alt="关闭"></div>
            </div>
        </div>

    </div>
</template>

<script>
import Cookies from "js-cookie";    
import popup from "~/components/popup"; //消息
import axios from "axios";
export default {
    head() {
        return {
            title: "商标自助注册-知千秋",
            meta: [
                {
                    name: "keywords",
                    hid: "keywords",
                    content:
                        "注册商标查询，中国商标官网查询，商标搜索，商标检索,知千秋,权大师"
                },
                {
                    name: "description",
                    hid: "description",
                    content:
                        "免费精准的商标查询平台,中国更全的商标信息库,精准智能的商标相似查询结果,为商标申请人降低商标驳回风险,更快,更准的定位商标注册成功概率。知千秋"
                }
            ],
            script: [
                {
                   src: 'https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.0.2/js/swiper.min.js'
                },
                {
                    src: "https://pwt.zoosnet.net/JS/LsJS.aspx?siteid=PWT96947637&float=1&lng=cn"
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
                    numbers: `S7741101919677030400,S7741102108546539521,S7741102214293331969,S7742588669033742336,S7741102510755127297,S7741103061622431745,S7741102770822946817,S7745508708399087616,S7741102891203665921,S7741113092604534784,S7741134790099836929,S7741134886166175745,S7741135000645509121,S7741135103628255233,S7741135255319453697,S7741135368016207873,S7741135498580697089,S7741135599600508929,S7743984847540617217,S7741136128074424321`
                }
            })
        ]).then(res => {
            let { data } = res[0];
            if (data.code == 0) {
                return {
                    priceData: data.data,
                    axiosUrl: env.baseUrl,
                    baseUrl: env.webSite + route.fullPath
                };
            } else {
                error({ statusCode: 500, message: data.msg });
            }
        });
    },
    data() {
        return {
          isFixedAd: true,
          isIndustry: false,
          industry: '',
          industryList: [],
          keyword: '',
          name: "",
          phone: ""
        };
    },
    mounted () {
        var mySwiper = new Swiper ('.swiper-container', {
            direction: 'horizontal',
            slidesPerView: 3,
            autoplay: true,//可选选项，自动滑动
            loop: true, // 循环模式选项
          }) 

        var swiper = new Swiper('.swiper-container-01', {
          navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
          },
          slidesPerView: 4,
          autoplay: true,//可选选项，自动滑动
          loop: true, // 循环模式选项
        });

        //行业分类
        this.$Http("predicate/allIndustries", {}, "post").then(res => {
            this.industryList = res.data.data;
        });    
    },
    computed: {
        
    },
    methods: {
        seletedIndustry (i) {
          this.industry = i
          this.isIndustry = false
        },
        priceCount (obj) {
          return obj.serviceItem.servicePrice + obj.serviceItem.officialPrice
        },
        replaceCn (e) {
          this.keyword = e.target.value.replace(/[^\u4e00-\u9fa5·A-Za-z0-9]/g, '')
        },
        getUrl(code, id) {
            if (id) {
                return `/show/${code}&${id}.html`;
            }
            return `/show/${code}.html`;
        },
        loadZn(numbers) {
            return this.$Http(`serviceItem/findServiceItemByNumber`, {
                numbers
            }).then(res => {
                this.showInfo = res.data.data[0];
            });
        },
        goOrder(numbers) {
            var newTab=window.open();
            this.loadZn(numbers).then(() => {
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
                newTab.location.href="/order/auto?360sem&zqqpc?&guanggao"

            }).catch(() => {
                // window.open(
                //     "/show/navigation_trademark_register_04&S7741102214293331969.html"
                // , "_blank");
            })
        },
        submit () {
            if (!this.keyword) {
                popup.created({
                    content: '请输入您需要查询的商标名称',
                    time: 2000
                })
                return false
              }
              if (!/^[\u4e00-\u9fa5·A-Za-z0-9]*$/g.test(this.keyword)) {
                popup.created({
                    content: '商标名称不能包含特殊字符',
                    time: 2000
                })
                return false
              }
              if (this.industry==='') {
                popup.created({
                    content: '请输入所在行业',
                    time: 2000
                })
                return false
              }
            let that = this  
            popup.loginPop({
              title: "联系方式",
              type: 'register',
              loginFn (name, phone, backFn) {
                var newTab=window.open();
                this.$Http('/customerMsg/save', {
                    name: name,
                    trademarkName: that.keyword,
                    industry: that.industryList[that.industry].name,
                    phone: phone
                }, 'POST').then((res) => {
                   backFn()
                   newTab.location.href = `/sbzc/result_${that.keyword},${that.industryList[that.industry].name},${that.industryList[that.industry].id},html`
                   that.keyword =''
                   that.industry =''
                   popup.created({
                    content: "提交成功",
                    time: 3000
                  });

                })
              },
            })
        },
        customerMsg () {
            if (!this.name) {
                popup.created({
                    content: '请输入您的姓名',
                    time: 2000
                })
                return false
              }
              if (!this.phone) {
                popup.created({
                    content: '请输入您的电话',
                    time: 2000
                })
                return false
              }
              if (!/^1(3|4|5|6|7|8|9)\d{9}$/.test(this.phone)) {
                popup.created({
                    content: '请输入正确电话',
                    time: 2000
                })
                return false
              }
            this.$Http('/customerMsg/save', {
                name: this.name,
                phone: this.phone
            }, 'POST').then((res) => {
                this.name = ''
                this.phone = ''
               popup.created({
                content: "提交成功",
                time: 3000
              });
            })
        }
    }
};
</script>

<style scoped lang="stylus">
@import('https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.0.2/css/swiper.min.css')
.wrapper
    width 1200px
    margin 0 auto
.title-catalog-00
    font-size 20px
    font-weight bold
    line-height 20px
    color #333
    border-left 5px solid #ff6600
    padding-left 15px
.banner
    height 550px
    background transparent url('../assets/images/extension/banner.jpg') center top no-repeat
    text-align center
    img
        margin-top 135px
    .search
        width 905px
        margin 77px auto 0
        height 70px
        background rgba(16, 24, 45, .75) 
        padding 10px
        position relative
        input
            float left
            height 70px
            line-height 70px
            width 360px
            text-indent 20px
            position relative
        .hy
            float left
            height 70px
            text-align left
            line-height 70px
            width 360px
            text-indent 20px
            position relative
            cursor pointer
            background #fff url('../assets/images/extension/icon_arr.png') no-repeat 320px center
        &:after
            position absolute
            left 360px
            top 30px
            content ''
            height 30px
            width 1px
            background #eee
        button
            background #ff6600
            height 70px
            line-height 70px
            font-size 18px
            color #fff
            float left
            width 185px
            text-align center
            border-radius 0
            cursor pointer
        .industryArea
            overflow hidden
            background #fff
            text-align left
            padding 20px
            border 1px solid #eee
            dl
                overflow hidden
                dt
                    width 100px
                    padding-top 5px
                    float left
                    color #ff6600
                    text-align right
                    font-weight bold
                dd
                    overflow hidden
                    padding-left 15px
                    a
                        display inline-block
                        margin 2px 5px
                        padding 3px 10px
                        &.atthis
                            background #ff6600
                            color #fff
.reg
    background #f5f5f5
    text-align center
    font-size 18px
    padding 25px 0 17px
    color #333
    p      
        font-size 14px
        color #ff6600
        b
            font-size 38px
.newReg
    background #f5f5f5
    text-align center
    dl
        width 1200px
        border-top 1px solid #e5e5e5
        margin 0 auto
        overflow hidden
        padding 20px 218px;
        box-sizing border-box
        font-size 14px
        dt
            float left
            width 80px
            color #ff6600
        dd
            overflow hidden
            text-align left
            a
                color #555
                display inline-block
                i
                    margin 0 12px
                span
                    color #888
.brand
    text-align center
    padding 25px
    h1
        line-height 80px
        font-size 32px
        color #333
    .content
        width 880px
        margin 0 auto
        box-sizing border-box
        padding 15px 20px 20px
        overflow hidden
        border 1px solid #e5e5e5
        h2
            line-height 70px
            font-size 24px
            color #333
            font-weight normal
            position relative
            &:after
                content ''
                position absolute
                width 60px
                height 2px
                background #333
                left 50%
                margin-left -30px
                bottom 0
        >p
            font-size 14px
            line-height 24px
            color #666
            margin 15px 0
        .tag
            overflow hidden
            a
                display inline-block
                width 158px
                height 58px
                border 1px solid #ff6600
                background #ffece0
                color #ff6600
                margin 5px
                line-height 58px
                font-size 16px
        .price
            overflow hidden
            font-size 18px
            color #ff6600
            line-height 48px
            margin-top 35px
            b
                font-size 48px
                font-weight normal
            .tips
                font-size 14px
                color #999
                line-height 30px
                margin-top -15px
        button,.regBtn
            display:inline-block;
            width 220px
            height 52px
            line-height 52px
            font-size 18px
            color #fff
            background #ff6600
            box-shadow 0 5px 15px 5px rgba(255, 102, 0, .25)
            border-radius 0
            margin 20px 0 
            cursor pointer
            &:hover
                box-shadow 0 5px 15px 5px rgba(255, 102, 0, .55)
    h3
        font-size 32px
        color #333
        b
            color #ff6600
        margin 45px 0 35px
    >ul
        overflow hidden
        width 1200px
        margin 0 auto
        li
            float left
            width 33.3%
            .title
                font-size 18px
                color #333
                line-height 30px
                margin-top 5px
            p
                font-size 14px
                color #888
                line-height 24px         
.td
    margin-top 40px
    height 500px
    background transparent url('../assets/images/extension/bg_td.jpg') center top no-repeat
    .item
        float left
        width 500px
        height 290px
        border 1px solid #fff
        margin-top 100px
        margin-left 40px
        padding 20px 0 0 50px
        box-sizing border-box
        .title
            font-size 24px
            color #fff
            line-height 60px
        li
            display inline-block
            padding 13px 33px
            background #fff
            margin 5px
        &.right
            float right
            margin-right 40px;
            border 1px solid #fc7e18
            .title
               color #fc7e18
            li
                background #fc7e18
                color #fff
.vs
    padding 60px 0 15px
    width 1200px
    margin 0 auto
    position relative
    overflow hidden
    &:after
        content ''
        position absolute
        left 50%
        margin-left -55.5px
        top 70px
        width 111px
        height 78px
        background transparent url('../assets/images/extension/bg_vs.png') left top no-repeat
    .item
        width 50%
        float left
        box-sizing border-box
        padding 0 120px 0 215px
        .title
            font-size 32px
            color #ff6600
            font-weight bold
            height 90px
            text-align center
            p
                font-size 14px
                line-height 40px
                font-weight normal 
        ul
            overflow hidden
            padding-top 10px
            li
                text-align center
                border 1px solid #ff6600
                line-height 30px
                padding 10px
                margin-bottom 30px
                position relative
                font-size 16px
                span
                    color #ff6600
                p
                    font-size 12px
                    color #888
                &:last-child,&.last
                    &:after
                        content ''
                        display none
                &:after
                    content ''
                    position absolute
                    bottom -20px
                    left 50%
                    margin-left -6px
                    width 12px
                    height 7px
                    background transparent url('../assets/images/extension/icon_arr.png') left top no-repeat
        &.right
            float right
            padding 0 215px 0 120px
            ul
                li
                    border-color #e5e5e5   
.flow
    height 520px
    background transparent url('../assets/images/extension/bg_flow.png') center top no-repeat
    text-align center
    .title
        font-size 30px
        font-weight bold
        color #fff
        padding-top 35px
        line-height 80px
    ul
        overflow hidden
        padding-top 15px
        li
            display inline-block
            width 140px
            height 140px
            border 1px solid #717ca2
            color #fff
            line-height 140px
            background #1d2951
            margin 0 25px
            position relative
            vertical-align top
            &.last
                line-height 50px
                box-sizing border-box
                padding-top 10px
                &:after
                    display none
                button
                    display block
                    width 100px
                    border-radius 3px
                    margin 0 auto
                    background #ff7426
                    color #fff
                    font-size 12px
                    line-height 18px
                    padding 5px 0
                    margin-top 5px
                    cursor pointer
            &:after
                content ''
                position absolute
                right -33px
                top 63px
                width 18px
                height 15px
                background transparent url('../assets/images/extension/icon_left.png') left top no-repeat
    .kn
        margin-top 45px
        height 270px
        background transparent url('../assets/images/extension/bg_kn.png') center top no-repeat
        .sign-title
            font-size 24px
            color #26325d
            line-height 55px
            padding-top 30px
            padding-bottom 15px
            span
                color #ff7426
        a
            display inline-block
            background #26325d
            border-radius 52px
            font-size 16px
            padding 14px 48px
            color #fff
            white-space nowrap
            margin 8px 10px
.qa
    margin-top 140px
    .left
        float left
        width 580px
        height 150px
        background #ff6600
        box-sizing border-box
        text-align center
        font-size 32px
        color #fff
        font-weight bold
        padding 25px 0
        a
            display block
            width 110px
            height 35px
            line-height 35px
            border-radius 3px
            background #fff
            color #333
            margin 18px auto 0
            font-size 16px
        &.right
            float right
.tz
    text-align center
    clear both
    padding-top 70px
    .title
        font-size 32px
        color #333
        line-height 32px
        font-weight bold
    p
        font-size 24px
        line-height 70px
        color #333
        b
            color #ff6600
    .tips
        font-size 16px
        color #333
    ul
        padding-top 10px
        padding-bottom 25px
        li
          display inline-block
          width 150px
          height 90px
          background #fff
          margin 5px
          line-height 90px
          box-sizing border-box
          padding 0 10px
          img
            max-width 100%
    .swiper-cont
        padding 15px 0 70px
        position relative
        .swiper-container-01
            width 1200px
            overflow hidden
        .swiper-button-next
            position absolute
            right -70px
            top 100px
            width 35px
            height 73px
            background transparent url('../assets/images/extension/icon_side_right.png') left top no-repeat
        .swiper-button-prev
            position absolute
            left -70px
            top 100px
            width 35px
            height 73px
            background transparent url('../assets/images/extension/icon_side_left.png') left top no-repeat
        img
            width 290px
            height 200px
.al
    background #f5f5f5
    padding 35px 0 15px
    text-align center
    .title
        font-size 32px
        font-weight bold
        color #333
        line-height 60px
    p
        font-size 16px
        color #888
    .zz
        padding-top 30px
        padding-bottom 30px
        img
            width 398px
            height 270px
    ul
        padding-top 15px
        padding-bottom 25px
        li
          display inline-block
          width 150px
          height 90px
          background #fff
          margin 5px
          line-height 90px
          box-sizing border-box
          padding 0 10px
          img
            max-width 100%
.down
    overflow hidden
    text-align center
    padding 30px 0
    .title
        font-size 32px
        font-weight bold
        color #333
        line-height 70px
    .item
        float left
        width 546px
        margin-top 10px
        border 1px solid #e5e5e5
        height 520px
        margin-left 37px
        margin-bottom 40px
        img
            margin 20px 30px 0
        &.right
            img
                margin-top 10px
        .sign-title
            background #1d2951
            line-height 50px
            font-size 18px
            color #fff
            padding-left 28px
            text-align left
        .bz
            overflow hidden
            padding 23px 0 13px
            p
                font-size 14px
                line-height 30px
                color #333
                text-align left
                padding-left 30px
                i
                    display inline-block
                    font-size 12px
                    background #1d2951
                    width 22px
                    height 22px
                    border-radius 50%
                    color #fff
                    line-height 22px
                    margin-right 5px
                    text-align center
                a
                    color #ff6600
.defuse
    text-align left
    padding-top 30px
    ul
        overflow: hidden;
        padding-top: 10px;
        padding-bottom: 0;
        li
            width: 380px;
            height: 140px;
            box-sizing: border-box;
            float: left;
            margin: 0 10px 20px 0;
            border: 1px solid #ddd;
            padding: 10px;
            position: relative;
            h4
                font-size: 18px;
                color: #333;
                padding: 20px 0 10px;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: norwap;
                font-weight: normal;
                b
                    float: right;
                    font-size: 16px;
                    color: #ff6000;
                    span
                        font-size: 14px;
                        color: #999;
                        font-weight: normal;
            p
                font-size: 14px;
                color: #666;
            a
                display: block;
                height: 100%;
                position: relative;
                z-index: 9;
                >span
                    display: block;
                    position: absolute;
                    width: 23px;
                    height: 16px;
                    left: 300px;
                    top: 100px;
                    opacity: 0;
                    background: transparent url('../assets/images/brand/icon_right.png') no-repeat left top;
                    transition: all 0.5s;
                    z-index: 9;
            &:hover
                a>span
                    opacity: 1;
                    left: 330px;
.team
    height 300px
    background transparent url('../assets/images/extension/bg_team.jpg') center top no-repeat
    text-align center
    .title
        font-size 32px
        font-weight bold
        color #fff
        padding-top 20px 
        position relative
        &:after
            content ''
            width 60px
            height 2px
            background #fff
            position absolute
            left 50%
            margin-left -30px
            bottom -20px 
    p
        font-size 16px
        line-height 36px
        font-weight bold
        color #fff
        margin-top 35px
        span
            color #ff6600           
.fixAd
    .fixed
        position fixed
        bottom 0
        left 0
        width 100%
        min-width 1200px
        height 215px
        z-index 99
        background transparent url('../assets/images/extension/bg_fixed.png') center top  no-repeat
    .sign
        font-size 28px
        color #fff
        position absolute
        left 50%
        top 100px
        margin-left -210px
        span
            color #ff6600
    del
        position absolute
        left 50%
        top 140px
        margin-left -210px
        color #fff
        font-size 16px
    .input
        position absolute
        width 340px
        left 50%
        margin-left 200px
        top 85px
        input
            line-height 46px
            border-radius 3px
            height 46px
            background #fff
            width 130px
            text-indent 10px
            &.phone
                width 200px
                margin-left 10px   
        button
            width 100%
            height 46px
            color #fff
            border-radius 3px
            line-height 46px
            margin-top  10px
            cursor pointer
            background transparent url('../assets/images/extension/bg_button.jpg') left top  no-repeat
    .colse
        position absolute
        cursor pointer
        left 50%
        margin-left 564px
        top 65px
</style>

