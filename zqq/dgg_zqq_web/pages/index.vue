<template>
    <div>
        <main-head/>
        <fixNav/>
        <section class="zqq_banner">
            <!-- 头部导航 开始 -->
            <div class="headNavBar mainHeadNav clear2 pos_re">
                <!-- <div class="drop_nav fl" @mouseover="isShow=1" @mouseout="isShow=0">
                    <h3 class="drop-title">
                        <i class="icon icon-item"></i>
                        <span class="text">全部导航</span>
                    </h3>
                </div> -->
                <ul class="navbar fl clear2">
                    <li class="menu">
                        <a href="/">首页</a>
                    </li>
                    <!-- <li class="menu">
                        <a href="/brand/reg" target="_blank">商标注册
                            <i class="icon icon-hot"></i>
                        </a>
                    </li> -->
                     <li class="menu">
                        <a href="/extension" target="_blank">商标注册
                            <i class="icon icon-hot"></i>
                        </a>
                    </li>
                  
                    <li class="menu">
                        <a href="javascript:void(0)" @click="$store.commit('isShowToolBox',1)">商标工具</a>
                    </li>
                    <!-- <li class="menu">
                        <a href="/question/navigation_trademark.html" target="_blank">商标问答</a>
                    </li> -->
                    <li class="menu">
                       <a href="/xw/trade_news.html" target="_blank">商标资讯</a>
                    </li>
                    <li class="menu">
                        <a href="javascript:;" onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')">商标咨询
                            <i class="icon icon-hot"></i>
                        </a>
                    </li>
                </ul>
            </div>
            <!-- 头部导航 结束 -->
            <div class="w zqq_b_center ">
                <h1 class="weight mb8">专注于商标注册查询</h1>
                <small class="f16">重新定义商标注册全流程</small>
                <div class="main_search mb60">
                    <ul class="search_style clear2" style='visibility: hidden'>
                        <li v-for="(item,idx) in navArr" @click="switchStyle(idx)" :key='idx' :class="{'active': styleIdx == idx}">{{item}}</li>
                    </ul>
                    <div class="searchBox ">
                        <input type="text" maxlength="50" :placeholder="holderArr[styleIdx]" class="fl" v-model="keyWord" @keyup.enter='goSearch'>
                        <span class="fl picImg  cur">
                            <el-upload v-show='styleIdx == 0 ' :action="$store.state.upLoadUrl" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
<!--                                <span class="pic"></span>-->
                            </el-upload>
                        </span>
                        <input type="button" value="查询" class="fl" @click="goSearch">
                    </div>
                    <div class="hot_search" v-show='styleIdx == 0'>
                        <span class="hot_search_t">热门<a href='/shangbiao/freesearch' title='商标查询' style="color:#fff"><b style='font-weight:100'>商标查询</b></a>:</span>
                        <div class="hot_search_box">
                            <a v-for="(item,idx) in hotArr" :key='idx' :href="'/shangbiao/'+item+'.html'" target="_blank">{{item}}</a>
                           <a href='/history/1.html' target="_blank"> 更多</a>
                        </div>
                        <!-- <span class="hot_search_btn" @click="refreshFunc"><img src="../assets/images/icon-refresh.png" alt="知千秋" :class="{sque:refresh}"> 换一批</span> -->
                    </div>
                </div>
            </div>
            <div class="piao piao1">
                <img src="../assets/images/main/piao1.png" class="piao-1" />
            </div>
            <img src="../assets/images/main/piao2.png" class="piao piao2" />
            <img src="../assets/images/main/piao5.png" class="piao piao3" />
            <img src="../assets/images/main/piao3.png" class="piao piao4" />
            <img src="../assets/images/main/piao5.png" class="piao piao5" />
        </section>
        <!-- 三张banner图开始 -->
        <!-- 三张banner图开始 -->
        <section style="height:auto;padding:20px 0;    padding-bottom: 60px;" id='three_box'> 
            <div class="w2 w1400">
                <div class="service_title text-center" style="margin:20px auto 10px">
                    <h1>智能自助商标注册</h1>
                    <small>AI自助申请，3分钟送达商标局</small>
                </div>

                <div class="brand">
                        <div class="content">
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
                                ￥<b>{{$priceCount(newPriceData[1])}}</b>
                                <p class="tips">（先审核后付费）</p>
                            </div>

                            <button @click="goOrder('S7741102214293331969')">立即注册</button>
                        </div>

                        <h3>为什么<b>仅{{$priceCount(newPriceData[1])}}元</b>低价商标注册？</h3>
                        <ul>
                            <li>
                                <img src="../assets/images/extension/icon_tz.png" alt="4000万风投">
                                <div class="title">4000万风投</div>
                                <p>顶呱呱4000万天使轮风投知千秋</p>
                            </li>
                            <li>
                                <img src="../assets/images/extension/icon_ai.png" alt="AI重构商标注册全流程">
                                <div class="title">AI重构商标注册全流程</div>
                                <p>简单、易懂、好操作、提交快捷</p>
                            </li>
                            <li>
                                <img src="../assets/images/extension/icon_dsj.png" alt="大数据分析">
                                <div class="title">大数据分析</div>
                                <p>30万+案件验证商标大数据</p>
                            </li>
                        </ul>
                    </div>
                <div class="three_box clear marB30" v-if="0">
                    <div v-show="0" class="light" :class="{'banner-hover':hoverIdx == 2}" style="margin-left:0" @mouseenter='hoverIdx = 2'>
                        <div class="pad fp">
                            <a class="imgBox" href="javascript:;" onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')">
                                <img src="../assets/images/banner/3.png" alt="">
                            </a>
                            <h1 class="title">【商标快速注册】</h1>
                        </div>
                        <div class="pad mofu">
                            <ul>
                                <li>
                                    <i class="iconRight"></i>全方位审核
                                </li>
                                <li>
                                    <i class="iconRight"></i> 专注疑难商标注册
                                </li>
                                <li>
                                    <i class="iconRight"></i> 规避盲查
                                </li>
                            </ul>
                            <p class="price">
                                ￥
                                <span>{{newPriceData[2].serviceItem.servicePrice + newPriceData[2].serviceItem.officialPrice}}</span>/件
                            </p>
                            <span class="ps">【疑难商标注册】</span>
                            <div class="btnBox">
                                <!-- <a href="javascript:;" onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')">了解详情</a> -->
                                <a @click="goOrder('S7741102108546539521')">马上注册</a>
                                <a class="colBtn" onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')">联系顾问</a>
                            </div>
                        </div>
                    </div>
                    <div v-show="0" class="light" :class="{'banner-hover':hoverIdx == 1}" style="margin-left:0" @mouseenter='hoverIdx = 1'>
                        <div class="pad fp">
                            <a class="imgBox" href="javascript:;" onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')">
                                <img src="../assets/images/banner/2.png" alt="">
                            </a>
                            <h1 class="title">【商标自助注册】</h1>
                        </div>
                        <div class="pad mofu">
                            <ul>
                                <li>
                                    <i class="iconRight"></i> 全流程自助注册
                                </li>
                                <li>
                                    <i class="iconRight"></i> 先审核后付款
                                </li>
                                <li>
                                    <i class="iconRight"></i> 价格比官费低一元
                                </li>
                                <li>
                                    <i class="iconRight"></i> 0服务费
                                </li>
                                <li>
                                    <i class="iconRight"></i> 3分钟极速提交
                                </li>
                                <li>
                                    <i class="iconRight"></i> 自助流程风险自测
                                </li>
                            </ul>
                            <p class="price">
                                ￥
                                <span>{{$priceCount(newPriceData[1])}}</span>/件
                            </p>
                            <span class="ps">【0服务费 性价比高】</span>
                            <div class="btnBox">
                                <a class="colBtn" @click="goOrder('S7741102214293331969')">马上注册</a>
                            </div>
                        </div>
                    </div>
                    <div v-show="0"  class="light" :class="{'banner-hover':hoverIdx == 0}" @mouseenter='hoverIdx = 0'>
                        <div class="pad fp">
                            <a class="imgBox" href="javascript:;" onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')">
                                <img src="../assets/images/banner/1.png" alt="">
                            </a>
                            <h1 class="title">【顾问注册】</h1>
                        </div>
                        <div class="pad mofu">
                            <ul>
                                <li>
                                    <i class="iconRight"></i> 专业顾问一对一服务
                                </li>
                                <li>
                                    <i class="iconRight"></i> 所有流程代办
                                </li>
                                <li>
                                    <i class="iconRight"></i> 省钱省时更省心
                                </li>
                            </ul>
                            <p class="price">
                                ￥
                                <span>{{newPriceData[0].serviceItem.servicePrice + newPriceData[0].serviceItem.officialPrice}}</span>/件
                            </p>
                            <span class="ps">【省心推荐】</span>
                            <div class="btnBox">
                                <a href="javascript:;" onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')">了解详情</a>
                                <a class="colBtn" href="javascript:;" onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')">联系顾问</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- 三张banner图结束 -->
        <!-- 三张banner图结束 -->
        <!-- 收费版块开始 -->
        <!-- <section class="service" id="hot_service" v-if="priceData">
            <div class="w2">
                <div class="service_title">
                    <h1>提供全国领先的知识产权智能服务平台</h1>
                    <small>商标注册、专利申请、版权登记更便捷</small>
                </div>
                <section class="server_part">
                    <div class="part_title clear2 ">
                        <h2 class="fl">商标服务</h2>
                        <span class="fl">Trademark Service</span>
                        <a class="fr hoverCol" href="/brand">更多+</a>
                    </div>
                    <div class="server_type clear2">
                        <a target="_blank" v-if='priceData[0].serviceItem' :href="getUrl(priceData[0].serviceItem.typeLevel3Code, priceData[0].serviceItem.number)" class="server_di brand clrear3 line-animate fl">
                            <div class="fl clear2">
                                <img src="../assets/images/main/brand_03.png" :alt="priceData[0].serviceItem.name">
                                <span class="col-b f24 server_di_ti">{{priceData[0].serviceItem.name}}</span>
                            </div>
                            <div class="fr">
                                <span class="col f24 server_di_ti">免费服务</span>
                            </div>
                        </a>
                        <a target="_blank" v-if='priceData[1].serviceItem' :href="getUrl(priceData[1].serviceItem.typeLevel3Code, priceData[1].serviceItem.number)" class="server_di brand clrear3 line-animate fl">
                            <div class="fl clear2">
                                <img src="../assets/images/main/brand_05.png" :alt="priceData[1].serviceItem.name">
                                <span class="col-b f24 server_di_ti">{{priceData[1].serviceItem.name}}</span>
                            </div>
                            <div class="fr ">
                                <span class="col server_di_ti f24">{{priceData[1].serviceItem.servicePrice}}元</span>
                                <span class="col-h f24">/件</span>
                            </div>
                        </a>
                    </div>
                </section>
                <section class="server_part">
                    <div class="part_title clear2">
                        <h2 class="fl">专利服务</h2>
                        <span class="fl">Patent Service</span>
                        <a class="fr hoverCol" href="/patent">更多+</a>
                    </div>
                    <div class="server_type clear2">
                        <a target="_blank" v-if='priceData[2].serviceItem' :href="getUrl(priceData[2].serviceItem.typeLevel3Code, priceData[2].serviceItem.number)" class="server_di fming clrear3 line-animate fl">
                            <div class="fl clear2">
                                <img src="../assets/images/main/faming.png" :alt="priceData[2].serviceItem.name">
                                <span class="col-b f24 server_di_ti">{{priceData[2].serviceItem.name.trim().substr(0, 4)}}</span>
                            </div>
                            <div class="fr">
                                <span class="col server_di_ti f24">{{priceData[2].serviceItem.servicePrice}}元</span>
                                <span class="col-h f24">/件</span>
                                <span class="col-h f18">&nbsp;起</span>
                            </div>
                        </a>
                        <a target="_blank" v-if='priceData[3].serviceItem' :href="getUrl(priceData[3].serviceItem.typeLevel3Code, priceData[3].serviceItem.number)" class="server_di fming clrear3 line-animate  fl">
                            <div class="fl clear2">
                                <img src="../assets/images/main/shiyong.png" :alt="priceData[3].serviceItem.name">
                                <span class="col-b f24 server_di_ti">{{priceData[3].serviceItem.name.trim().substr(0, 4)}}</span>
                            </div>
                            <div class="fr">
                                <span class="col server_di_ti f24">{{priceData[3].serviceItem.servicePrice}}元</span>
                                <span class="col-h f24">/件</span>
                            </div>
                        </a>
                        <a target="_blank" v-if='priceData[4].serviceItem' :href="getUrl(priceData[4].serviceItem.typeLevel3Code, priceData[4].serviceItem.number)" class="server_di fming clrear3 line-animate fl">
                            <div class="fl clear2">
                                <img src="../assets/images/main/waiguan.png" :alt="priceData[4].serviceItem.name">
                                <span class="col-b f24 server_di_ti">{{priceData[4].serviceItem.name.trim().substr(0, 4)}}</span>
                            </div>
                            <div class="fr">
                                <span class="col server_di_ti f24">{{priceData[4].serviceItem.servicePrice}}元</span>
                                <span class="col-h f24">/件</span>
                            </div>
                        </a>
                    </div>
                </section>
                <section class="server_part" id="server_part">
                    <div class="part_title clear2">
                        <h2 class="fl">版权服务</h2>
                        <span class="fl">Copyright Service</span>
                        <a class="fr hoverCol" href="/copyright">更多+</a>
                    </div>
                    <div class="server_type clear2">
                        <a target="_blank" v-if='priceData[5].serviceItem' :href="getUrl(priceData[5].serviceItem.typeLevel3Code, priceData[5].serviceItem.number)" class="server_di brand clrear3 line-animate fl">
                            <div class="fl clear2">
                                <img src="../assets/images/main/ruanjian.png" :alt="priceData[5].serviceItem.name">
                                <span class="col-b f24 server_di_ti">{{priceData[5].serviceItem.name}}</span>
                            </div>
                            <div class="fr">
                                <span class="col server_di_ti f24">{{priceData[5].serviceItem.servicePrice}}元</span>
                                <span class="col-h f24">/件</span>
                            </div>
                        </a>
                        <a target="_blank" v-if='priceData[6].serviceItem' :href="getUrl(priceData[6].serviceItem.typeLevel3Code, priceData[6].serviceItem.number)" class="server_di brand clrear3 line-animate fl">
                            <div class="fl clear2">
                                <img src="../assets/images/main/zuopin.png" :alt="priceData[6].serviceItem.name">
                                <span class="col-b f24 server_di_ti">{{priceData[6].serviceItem.name}}</span>
                            </div>
                            <div class="fr ">
                                <span class="col server_di_ti f24">{{priceData[6].serviceItem.servicePrice}}元</span>
                                <span class="col-h f24">/件</span>
                            </div>
                        </a>
                    </div>
                </section>
            </div>
        </section> -->
        <!-- 收费版块结束 -->
        <!-- 服务工具 start -->
        <section class="service" id="hot_service" v-if="priceData">
            <div class="w2">
                <div class="service_title">
                    <h1>提供一体化商标注册查询服务工具</h1>
                    <small>商标查询、商标智能注册等工具服务更便捷</small>
                </div>
                <ul class='serverTool'>
                    <li>
                        <a href='/shangbiao/freesearch/' target='_blank'>
                            <div>
                                <img src='../assets/images/add/add1.png'/>
                            </div>
                            <h2>商标查询</h2>
                            <p>
                            同步商标局数据<br>保证商标查询数据准确及时！
                            </p>
                        </a>
                    </li>
                    <li>
                        <a href='/sbzc' target='_blank'>
                            <div>
                                <img src='../assets/images/add/add2.png'/>
                            </div>
                            <h2>注册成功率测算</h2>
                            <p>
                                测算商标注册成功率<br>查询商标是否相同、查询商标是否近似!
                            </p>
                        </a>
                    </li>
                    <li>
                        <a href='/classify' target='_blank'>
                            <div>
                                <img src='../assets/images/add/add3.png'/>
                            </div>
                            <h2>商标分类查询</h2>
                            <p>
                            2018版最新尼斯分类表<br>商标注册分类明细表!
                            </p>
                        </a>
                    </li>
                    <li>
                       <a class="colBtn" @click="goOrder('S7741102214293331969')">
                            <div>
                                <img src='../assets/images/add/add4.png'/>
                            </div>
                            <h2>商标智能注册</h2>
                            <p>
                            快速提交订单<br>专业顾问1对1服务，省钱省时更省心!
                            </p>
                         </a>
                    </li>
                    <li>
                        <a href='/brandmanage' target='_blank'>
                            <div> 
                                <img src='../assets/images/add/add5.png'/>
                            </div>
                            <h2>商标管理</h2>
                            <p>
                            智能管理自己的商标系统
                            </p>
                        </a>   
                    </li>
                    <li>
                        <a href='/notice' target='_blank'>
                            <div>
                                <img src='../assets/images/add/add6.png'/>
                            </div>
                            <h2>初审公告</h2>
                            <p>
                            商标公告数据按照<br>官方公告及时更新，数据准确全面!
                            </p>
                        </a>   
                    </li>
                </ul>
            </div>
        </section>
        <!-- 服务工具 end -->
        <!-- AI重构 开始 -->
        <section class="service" id="platform" style='background:#fff'>
            <div class="service_title w">
                <h1>AI重构商标注册全流程</h1>
                <small>大数据、人工智能助力商标注册查询更高效</small>
            </div>
            <div class='newAi w2'>
                <div class='newAi-l'>
                    <ul>
                        <li :class="{'active':aiTabIdx == 0}" @mouseover=" aiTabIdx= 0">
                           商标智能注册系统 
                        </li>
                         <li :class="{'active':aiTabIdx == 1}" @mouseover=" aiTabIdx= 1">
                           商标快速报件系统 
                        </li>
                        <li :class="{'active':aiTabIdx == 2}" @mouseover=" aiTabIdx= 2">
                           最新商标分类表 
                        </li>
                     </ul>   
                </div>
                <div class='newAi-r junFadeIn' v-show='aiTabIdx == 0'>
                    <h2>商标智能注册系统</h2>
                    <div>
                        <div class='ai-desc'>
                            <p>通过AI算法检索、智能类别推荐、通过率的科学分析，让商标注册脱离时间、空间和人的限制。4小时极速网申，办理状态与商标局注册进度同步更新，第一时间为你传达流程节点短信、微信和邮件通知，不仅服务费为0，服务也更优质。</p>
                            <a href='javascript:void(0)' @click='goOrder("S7741102214293331969")' target='_blank'>立即注册</a>
                        </div>
                        <div class='ai-gif'>
                            <img src='../assets/images/add/01.gif'/>
                        </div>    
                    </div>
                </div>
                 <div class='newAi-r junFadeIn' v-show='aiTabIdx == 1'>
                    <h2>商标快速报件系统</h2>
                    <div>
                        <div class='ai-desc'>
                            <p>全球首个商标递交开放系统，经由30万+案件验证的高效智能化商标处理后台赋能。插入事务所Ukey，即启即用，支持事务所一键实现知千秋智能高效的业务处理能力。</p>
                            <a onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')">立即咨询</a>
                        </div>
                        <div class='ai-gif'>
                            <img src='../assets/images/add/02.gif'/>
                        </div>    
                    </div>
                </div>
                 <div class='newAi-r junFadeIn' v-show='aiTabIdx == 2'>
                    <h2>最新商标分类表</h2>
                    <div>
                        <div class='ai-desc'>
                            <p>通过对历史所有的商品服务项进行分析，不仅提供标准尼斯商品的查询，更可以支持历史以来被官方认可的非标准商品查询，呈现更全面、更精准的商标分类信息，是你护品牌周全、保品牌无忧的最佳选择</p>
                            <a href='/classify' target='_blank'>立即查看</a>
                        </div>
                        <div class='ai-gif'>
                            <img src='../assets/images/add/03.gif'/>
                        </div>    
                    </div>
                </div>
            </div>
        </section>
        <!-- AI重构 结束 -->
        
        <!-- 开发平台 开始 -->
        <section class="service platfrom" id="data">
            <div class="w">
                <div class="service_title">
                    <h1>全面的商标数据库平台</h1>
                    <small>同步商标局数据，保证商标查询数据准确及时！</small>
                </div>
                <ul class="platfrom_num clear2" id="count">
                    <li class="fl">
                        <h1 class="f28 col mb8">{{brandNum}}万+</h1>
                        <small class="f16 v-align-m"><img src="../assets/images/main/plat_03.png" alt="知千秋"> 商标数据</small>
                    </li>
                    <li class="fl">
                        <h1 class="f28 col mb5">{{dimension}}°</h1>
                        <small class="f16 v-align-m"><img src="../assets/images/main/plat_05.png" alt="知千秋"> 企业维度数据</small>
                    </li>
                    <li class="fl">
                        <h1 class="f28 col mb5">{{transactioNum}}万+</h1>
                        <small class="f16 v-align-m"><img src="../assets/images/main/plat_07.png" alt="知千秋"> 商标申请人</small>
                    </li>
                    <li class="fl">
                        <h1 class="f28 col mb5">{{userNum}}万+</h1>
                        <small class="f16 v-align-m"><img src="../assets/images/main/plat_09.png" alt="知千秋"> 代理机构</small>
                    </li>
                </ul>
            </div>
        </section>
        <!-- 开发平台 结束 -->
        <!-- 咨询开始 -->
        <section class="service" style="background:#fff;" id="news" v-if='newsJson && newsJson.three'>
            <div class="w">
                <div class="service_title">
                    <h1>商标注册最新资讯</h1>
                    <small>学习，成长，分享，共建商标注册社区</small>
                </div>
                <ul class="consultation clear2">
                    <li v-if='newsJson.one.length > 0'>
                        <div class="firstBox">
                            <span class="flag">行业新闻</span>
                            <a :href="initNewUrl((newsJson.one)[0].number, 1)" target="_blank" :title="((newsJson.one)[0]).title">
                                <img :src="((newsJson.one)[0]).newsPhoto ? newsJson.one[0].newsPhoto : ''" :onerror="logo" alt="知识产权社区" />
                                <p class="text-delic">{{((newsJson.one)[0]).title}}</p>
                            </a>
                        </div>
                        <ul class="consult_list mt10">
                            <li class="text-delic" v-for="(item,idx) in newsJson.one" :key='idx' v-if='idx != 0'>
                                <a :href="initNewUrl(item.number, 1)" :title="item.title" v-html="initNewsHTml(item.type_c,item.title)" class="text-delic db" style="height:20px" target="_blank"></a>
                            </li>
                        </ul>
                        <p class="text-right col cur">
                            <a href="/xw/trade_news.html" class="col" target="_blank" rel="nofollow"> 更多+</a>
                        </p>
                    </li>
                    <li v-if='newsJson.two.length > 0'>
                        <div class="firstBox">
                            <span class="flag">政策法规</span>
                            <a :href="initNewUrl((newsJson.two)[0].number, 2)" target="_blank" :title="((newsJson.two)[0]).title">
                                <img :src="((newsJson.two)[0]).newsPhoto ? newsJson.two[0].newsPhoto : ''" :onerror="logo" alt="知识产权社区" />
                                <p class="text-delic">{{((newsJson.two)[0]).title}}</p>
                            </a>
                        </div>
                        <ul class="consult_list mt10">
                            <li class="text-delic" v-for="(item,idx) in newsJson.two" :key='idx' v-if='idx != 0'>
                                <a :href="initNewUrl(item.number, 2)" :title="item.title" v-html="initNewsHTml(item.type_c,item.title)" class="text-delic db" style="height:20px" target="_blank"></a>
                            </li>
                        </ul>
                        <p class="text-right col cur">
                            <a href="/xw/first_sound.html" class="col" target="_blank" rel="nofollow"> 更多+</a>
                        </p>
                    </li>
                    <li v-if='newsJson.three.length > 0'>
                        <div class="firstBox">
                            <span class="flag">知产知识</span>
                            <a :href="initNewUrl((newsJson.three)[0].number, 3)" target="_blank" :title="((newsJson.three)[0]).title">
                                <img :src="((newsJson.three)[0]).newsPhoto ? newsJson.three[0].newsPhoto : ''" :onerror="logo" alt="知识产权社区" />
                                <p class="text-delic">{{((newsJson.three)[0]).title}}</p>
                            </a>
                        </div>
                        <ul class="consult_list mt10">
                            <li class="text-delic" v-for="(item,idx) in newsJson.three" :key='idx' v-if='idx != 0'>
                                <a :href="initNewUrl(item.number, 3)" :title="item.title" v-html="initNewsHTml(item.type_c,item.title)" class="text-delic db" style="height:20px" target="_blank"></a>
                            </li>
                        </ul>
                        <p class="text-right col cur">
                            <a href="/xw/knowledge.html" class="col" target="_blank" rel="nofollow"> 更多+</a>
                        </p>
                    </li>
                </ul>
            </div>
        </section>
        <!-- 咨询 结束 -->
        <!-- 新生态开始 -->
        <section class="service" id="logoBanner" style="background:#f5f7f8">
            <div class="w">
                <div class="service_title">
                    <h1>助力各行各业客户成功注册商标</h1>
                    <small>加入知千秋，共同开创新业务、重建商标注册新生态</small>
                </div>
                <ul class="logoBanner clear2">
                    <li>
                        <a :href="'http://www.mcc5.com.cn/'" target="_blank" rel="nofollow"><img src="../assets/images/main/logo_03.jpg" alt="新生态"></a>
                    </li>
                    <li>
                        <a href="http://www.cnpc.com.cn/cnpc/index.shtml" target="_blank" rel="nofollow"> <img src="../assets/images/main/logo_05.png" alt="新生态"></a>
                    </li>
                    <li>
                        <a href="http://www.cas.cn/" target="_blank" rel="nofollow"><img src="../assets/images/main/logo_07.png" alt="新生态"></a>
                    </li>
                    <li>
                        <a href="http://www.scu.edu.cn/" target="_blank" rel="nofollow"><img src="../assets/images/main/logo_09.jpg" alt="新生态"></a>
                    </li>
                    <li>
                        <a href="http://www.sgcc.com.cn/" target="_blank" rel="nofollow"><img src="../assets/images/main/logo_11.png" alt="新生态"></a>
                    </li>
                    <li>
                        <a href="http://www.crecg.com/" target="_blank" rel="nofollow"><img src="../assets/images/main/logo_18.png" alt="新生态"></a>
                    </li>
                    <li>
                        <a href="http://xsc.cuit.edu.cn/Sys/WebSite/Web/Default.html" target="_blank" rel="nofollow"><img src="../assets/images/main/logo_19.png" alt="新生态"></a>
                    </li>
                    <li>
                        <a href="http://www.swjtu.edu.cn/" target="_blank" rel="nofollow"><img src="../assets/images/main/logo_20.png" alt="新生态"></a>
                    </li>
                    <li>
                        <a href="http://www.uestc.edu.cn/" target="_blank" rel="nofollow"> <img src="../assets/images/main/logo_21.jpg" alt="新生态"></a>
                    </li>
                    <li>
                        <a href="http://www.028aidi.com" target="_blank" rel="nofollow"><img src="../assets/images/main/logo_22.png" alt="新生态"></a>
                    </li>
                </ul>
            </div>
        </section>
        <!-- 新生态 结束 -->
        <!-- Foot -->
        <div>
            <div class="foot_top">
                <div class="w link_flag">
                    <ul>
                        <li class="nav_us">
                            <a target="_blank" href="/about/1.html" rel="nofollow">关于我们</a>
                            <a target="_blank" href="/about/2.html" rel="nofollow">加入我们</a>
                            <a target="_blank" href="/about/3.html" rel="nofollow">联系我们</a>
                            <a target="_blank" href="/about/4.html" rel="nofollow">常见问题</a>
                            <a target="_blank" href="/about/5.html" rel="nofollow">服务说明</a>
                            <a target="_blank" href="/about/6.html" rel="nofollow">模板下载</a>
                            <a target="_blank" href="/map" rel="nofollow">网站地图</a>
                        </li>
                        <li>
                            <span>热门产品：</span>
                            <a target="_blank" href='/extension'>商标注册</a>
                            <!-- <a target="_blank" href='/patent'>专利申请</a> -->
                            <!-- <a target="_blank" href='/copyright'>版权登记</a> -->
                            <a target="_blank" href='/show/navigation_patent_law_01.html'>驳回复审</a>
                            <a target="_blank" href='/show/navigation_trademark_law_03.html'>撤三答辩</a>
                            <a target="_blank" href='/show/navigation_trademark_law_08.html'>异议复审</a>
                            <a target="_blank" href='/show/navigation_patent_international_01.html'>国际专利PCT申请</a>
                            <a target="_blank" href='/show/navigation_copyright_register_01.html'>软件著作权登记</a>
                        </li>
                        <li>
                            <span>热门栏目：</span>
                            <a target="_blank" href='/shangbiao/freesearch'>商标查询</a>
                            <a target="_blank" href='/xw/first_sound.html'>政策法规</a>
                            <a target="_blank" href='/xw/knowledge.html'>商标知识</a>
                            <a target="_blank" href='/xw/data_report.html'>商标报告</a>
                            <a target="_blank" href='/question/navigation_trademark.html'>商标问答</a>
                            <!-- <a target="_blank" href='/question/navigation_patent.html'>专利问答</a>
                            <a target="_blank" href='/question/navigation_copyright.html'>版权问答</a> -->
                            <a target="_blank" href='/notice'>商标初审公告</a>
                            <a target="_blank" href='/classify'>商标分类表</a>
                            <a target="_blank" href='/shangbiao/freesearch'>查询商标是否已注册</a>
                            <a target="_blank" href='/history/1.html'>查询结果</a>
                        </li>
                        <li>
                            <span>关于我们：</span>
                            <a target="_blank" href="/about/1.html" rel="nofollow">关于我们</a>
                            <a target="_blank" href="/about/2.html" rel="nofollow">加入我们</a>
                            <a target="_blank" href="/about/3.html" rel="nofollow">联系我们</a>
                        </li>
                    </ul>
                    <div class="code">
                        <div class="useCode">
                            <p class="white mb8">关注知千秋</p>
                            <img src="../assets/images/main/code_wx.png" alt="微信"><br>
                            <em class="fr" style="display:block;margin-top:8px;color:#fff;font-size:16px">服务热线：400-871-9995</em>
                        </div>
                    </div>
                </div>
            </div>
            <div class="foot_down">
                <div class="w friendLink">
                    <nav class="mb5" v-if="$route.fullPath == '/' && footArr.length > 0">
                        <span>友情链接：</span>
                        <template v-for="(item,idx) in footArr">
                            <a :href="item.ext1" target="_blank" :key='idx'>{{item.name}}</a>
                            <span :key='idx+100'> | </span>
                        </template>
                    </nav>
                    <span>Copyright 2018 四川先锋商标事务所有限公司 版权所有 | 京ICP备16023355号-2 地址：成都市金牛区金府路666号30楼3011、3013室</span>
                </div>
            </div>
        </div>
        <!-- foot end-->

        <floor :floorAttr="[{name: '热门服务', type: '#three_box'},{name: '商标工具', type: '#hot_service'},{name: '智能平台', type: '#platform'},{name: '全面数据', type: '#data'},{name: '权威资讯', type: '#news'},{name: '合作伙伴', type: '#logoBanner'}]" />
    </div>
</template>
<script>
import mainHead from "~/components/head/mainHead";
import fixNav from "~/components/fixNav";

import floor from "~/components/floor/floor";
import { openNewPage, uploadImg} from "~/assets/js/common.js";
import { Http } from "~/plugins/axios.js";
import axios from "axios";
import popup from "~/components/popup";
import img from "~/assets/images/default_new.jpg";
import Cookies from "js-cookie";
import qs from "qs";

export default {
    layout: "empt",
    head() {
        return {
            title: this.title,
            meta: [
                {
                    name: "keywords",
                    hid: "keywords",
                    content: this.keywords
                },
                {
                    name: "description",
                    hid: "description",
                    content: this.description
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
    data() {
        return {
            navArr: ["商标", "专利", "企业"],
            holderArr: [
                "请输入商标名称、申请号、申请人等信息",
                "请输入专利名称",
                "请输入企业名称"
            ],
            logo: 'this.src="' + img + '"',
            keyWord: "",
            styleIdx: 0,
            aiTab: ["商标智能注册系统", "商标快速报件系统", "商标分类表"],
            aiTabIdx: 0,
            newsJson: "",
            newsJson1: "",
            brandNum: 1000, //商标数据
            dimension: 0, //维度
            transactioNum: 0, //商标交易数据
            userNum: 0, //用户数据
            totalData: 0,
            totalUser: 0,
            loading: "", //跳转的loading
            numArr: [],
            hotArr: [
            ],
            hotArrIdx: 0,
            refresh: false, //刷新动画
            footArr: [], //页脚链接
            showInfo: "",
            newPriceData: [],
            hoverIdx: 1
        };
    },
    components: {
        mainHead,
        fixNav,
        floor
    },
    async asyncData({ params, redirect, error, env, route }) {
        let requireObj = qs.stringify({
            typeOne: "trade_news",
            typeTwo: "first_sound",
            typeThree: "knowledge"
        });
        return Promise.all([
            axios.get(`${env.baseUrl}serviceItem/findServiceItemByNumber`, {
                params: {
                    numbers:
                        "S7741102214293331969,S7741101919677030400,S7741445307712569344,S7741445546913726465,S7741445695501139969,S7741448551415316481,S7741448858560004097"
                }
            }),
            axios.post(`${env.baseUrl}news/protal`, requireObj),
            axios.get(`${env.baseUrl}Count/showTitle`),
            axios.get(`${env.baseUrl}Count/showLink`),
            axios.get(`${env.baseUrl}serviceItem/findServiceItemByNumber`, {
                params: {
                    numbers: `S7741101919677030400,S7741102214293331969,S7741102108546539521`
                }
            }),
            axios.post(`${env.baseUrl}seo/rankOfKeyword`, {pullCount: 100,searchType: "brand"})
        ])
            .then(res => {
                let { data } = res[0];

                if (data.code == 0) {
                    let hotArr=[];
                    for (let key in res[5].data.data) {
                        if(hotArr.length == 8) break;
                        if(key.length < 5){
                            hotArr.push(key);
                        }
                    }
                    return {
                        numArr: data.data,
                        priceData: res[0].data.data,
                        newsJson: res[1].data.data,
                        title: res[2].data.data.ext1,
                        keywords: res[2].data.data.ext2,
                        description: res[2].data.data.description,
                        footArr: res[3].data.data,
                        newPriceData: res[4].data.data,
                        baseUrl: env.webSite + route.fullPath,
                        hotArr
                    };
                } else {
                    error({ statusCode: 500, message: data.msg });
                }
            })
            .catch(data => {
                error({ statusCode: 500, message: data });
            });
    },
    mounted() {
        
        //监听滚动条
        window.addEventListener("scroll", this.handleScroll);
        this.animatNum(parseInt(this.numArr[0] || 25453456), "totalData");
        this.animatNum(parseInt(this.numArr[1] || 521521), "totalUser");
        sessionStorage.removeItem("isFrom");
       
        if (this.$store.state.userInfo == "") {
            sessionStorage.removeItem("userId");
        } else {
            sessionStorage.setItem("userId", this.$store.state.userInfo.userId);
        }
        this.loadZn();
        // this.goOrder('S7741102214293331969'); //智能订单需要
    },
    methods: {
        // 格式化数字
        formatNum(num) {
            var num = (num || 0).toString();
            var result = "";
            while (num.length > 3) {
                result = "," + num.slice(-3) + result;
                num = num.slice(0, num.length - 3);
            }
            if (num) {
                result = num + result;
            }
            return result;
        },
        // 数字加载动画
        animatNum(num, type) {
            let total = 0;
            let time = setInterval(() => {
                if (total >= num) {
                    total = num;
                    clearInterval(time);
                } else {
                    total += parseInt(num / 200);
                }
                this[type] = this.formatNum(total);
            }, 20);
        },
        //刷新热搜
        // refreshFunc() {
        //     if (this.refresh) return;
        //     this.refresh = true;
        //     setTimeout(() => {
        //         this.hotArrIdx = this.hotArrIdx == 1 ? 0 : 1;
        //         this.refresh = false;
        //     }, 2000);
        // },
        switchStyle(idx) {
            this.styleIdx = idx;
        },
        getUrl(code, id) {
            if (id) {
                return `/show/${code}&${id}.html`;
            }
            return `/show/${code}.html`;
        },
        handleScroll() {
            var scrollTop =
                window.pageYOffset ||
                document.documentElement.scrollTop ||
                document.body.scrollTop;
            var offsetTop =
                document.getElementById("count").offsetTop -
                document.documentElement.clientHeight;
            if (scrollTop > offsetTop) {
                window.removeEventListener("scroll", this.handleScroll);
                let timer = setInterval(() => {
                    if (this.dimension < 360)
                        this.dimension = this.dimension + 2;
                    if (this.transactioNum < 300)
                        this.transactioNum = this.transactioNum + 1;
                    if (this.userNum < 3) this.userNum = this.userNum + 1;
                    if (this.brandNum < 3000) this.brandNum = this.brandNum + 8;
                    if (
                        this.dimension >= 360 &&
                        this.transactioNum >= 300 &&
                        this.userNum >= 3 &&
                        this.brandNum >= 3000
                    ) {
                        clearInterval(timer);
                    }
                }, 20);
            }
        },

        goSearch() {
            //去搜索页面
            this.keyWord = this.keyWord.replace(/ /g, "");
            if (!this.keyWord) {
                openNewPage(this, "/shangbiao/freesearch");
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
                return;
            }

            if (this.styleIdx === 0) {
                this.$router.push({
                    path: "/loading",
                    query: { url: "/shangbiao/" + this.keyWord + ".html" }
                });
            } else if (this.styleIdx === 1)
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
        initNewUrl(id, typeId) {
            return "/xwxq/" + id + ".html";
        },
        initNewsHTml(str, str2) {
            return `${str2}`;
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
                newTab.location.href="/order/auto"

            }).catch(() => {
                // window.open(
                //     "/show/navigation_trademark_register_04&S7741102214293331969.html"
                // , "_blank");
            })
        },
        smartOrder(){
            if(Cookies.get("product")){
                    // this.$router.push("/order/auto")
                window.open("/order/auto")
            }
            else{
                  window.open(
                    "/show/navigation_trademark_register_04&S7741102214293331969.html"
                , "_blank");
            }
        }
    },
    destroyed() {
        window.removeEventListener("scroll", this.handleScroll);
        if (this.Loading) this.Loading.close();
    }
};
</script>

<style scoped lang="stylus">
@import url('../assets/css/main.css');

* {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

.hot_search {
    font-size: 12px;
    padding: 20px 18px 0;
    width: 830px;
    margin: auto;

    .hot_search_t {
        float: left;
    }

    .hot_search_box {
        float: left;

        a {
            margin-left: 20px;
            color: #fff;
        }

        a:hover {
            color: #ff6600;
        }
    }

    .hot_search_btn {
        float: left;
        margin-left: 40px;
        cursor: pointer;

        img {
            margin-right: 2px;
            transform: rotate(0deg);
            position: relative;
            top: -1px;
        }

        .sque {
            transition: all 3s;
            transform: rotate(1080deg);
            -webkit-transform: rotate(1080deg);
            -moz-transform: rotate(1080deg);
            -ms-transform: rotate(1080deg);
            -o-transform: rotate(1080deg);
        }
    }
}

// /新增页脚
.foot_top {
    background: #2b2e33;
}

.foot_top a, .foot_top span {
    color: #999999;
    margin-right: 18px;
    font-size: 12px;
}

.foot_top a:hover {
    color: #fd7d22;
}

.link_flag {
    padding-top: 30px;
    padding-bottom: 1px;
    position: relative;
}

.link_flag li {
    margin-bottom: 20px;
}

.nav_us a {
    color: #fff;
    font-size: 14px;
    margin-right: 32px;
}

.code {
    position: absolute;
    right: 0;
    top: 10px;
}

.useCode {
    text-align: center;
}

.useCode img {
    width: 100px;
    max-width: 100px;
}

/* //下面脚部 */
.foot_down {
    background: #24272b;
}

.friendLink {
    padding: 12px 0;
}

.friendLink span, .friendLink a {
    color: #999999;
    font-size: 12px;
}

.friendLink a:hover {
    color: #fd7d22;
}

// 更换
.three_box {
    padding: 12px;
    background: #fff;
    text-align: center;
    font-size: 0;
    padding: 40px 0;

    > div {
        display: inline-block;
        width: 380px;
        height: 450px;
        text-align: center;
        // padding: 38px 14px 20px;
        box-sizing: border-box;
        position: relative;
        font-size: 14px;

        .pad {
            padding: 10px 14px 20px;

            ul {
                padding: 6px 26px 30px;
                // width: 260px;
                margin: auto;
                overflow hidden

                li {
                    height: 46px;
                    line-height: 46px;
                    border-bottom: 1px dashed #ededef;
                    text-align: left;
                    float left;
                    width 33.3%;

                    .iconRight {
                        margin: 0 12px 0 35px;
                        background: url('../assets/images/banner/4.png') center center no-repeat;
                        display: inline-block;
                        width: 16px;
                        height: 16px;
                        position: relative;
                        top: 3px;
                    }
                }
            }
        }

        .mofu {
            // box-shadow: 0 0 5px 0 #e1e1e1;
            border:1px solid #EDEDED;
            border-top:none;
        }

        .pad.fp {
            padding-top: 24px;
            padding-bottom: 20px;
            // transition: all 0.5s;
            height: 130px;
            background: #282b3a;
            color: #fff;
        }

        .imgBox {
            display: inline-block;
            border-radius: 50%;
            position: relative;
            z-index: 4;

            img {
                height: 48px;
            }
        }

        .title {
            font-size: 18px;
            font-weight: 100;
            margin-top: 14px;
            // margin-bottom: 20px;
        }

        small {
            color: #565656;
            font-size: 14px;
        }

        .iconP {
            background: #FFFBFA;
            color: #FF4800;
            border-top: 1px solid #FFDDCF;
            border-bottom: 1px solid #FFDDCF;
            padding: 10px 0;
            margin-top: 22px;
            margin-bottom: 28px;
            font-size: 12px;

            span {
                display: inline-block;
                width: 16px;
                height: 100%;
            }
        }

        .price {
            font-size: 12px;

            span {
                font-size: 40px;
                color: #ff0000;
            }
        }

        .ps {
            color: #FF3145;
            font-size: 12px;
        }

        .btnBox {
            width: 340px;
            margin: auto;
            margin-top: 16px;

            a {
                display: inline-block;
                border-radius: 2px;
                font-size: 14px;
                border: 1px solid #282b3a;
                color: #282b3a;
                width: 164px;
                height: 44px;
                line-height: 44px;
                text-align: center;
                position: relative;
                z-index: 4;
            }

            .colBtn {
                margin-left: 12px;
                border: 1px solid #ff6600;
                color: #ff6600;
            }
        }

        .flag {
            position: absolute;
            right: 0;
            top: 0;
        }
    }
}

div.banner-hover {
    position: relative;
    top: -20px;
    width: 900px !important;

    .pad.fp {
        background: #ff6600 !important;
    }

    .pad.mofu {
        padding: 20px 14px 60px !important;
          box-shadow: 0 0 5px 0 #e1e1e1;
    }

    .btnBox {
        a {
            color: #fff;
            background: #282b3a;
        }

        .colBtn {
            background: #ff6600;
            color: #fff;
        }
    }
}
//一体化工具
.serverTool{
    overflow:hidden;
    a{
        display:block;
    }
    li{
        float:left;
        width:33.333%;
        height:240px;
        div{
            height:100px;
            line-height:100px;
        }
        h2{
            font-size:18px;
            color:#333333;
            margin-bottom:18px;
        }
        p{
            font-size:14px;
            color:#999;
            font-weight:100;
            line-height:1.8em;
        }
    }    
}
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
        padding 30px 20px 20px
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
        button
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
</style>
