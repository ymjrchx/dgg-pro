<template>
    <div class="content">
        <nav id="V3_Index_M_Nav">
            <div class="container">
                <a @click="goLink(0)" class="a3">
                    <h2>建筑资质</h2>
                    <small>100万+资质证书</small>
                </a>
                <a @click="goLink(2)" class="a4">
                    <h2>公司核名</h2>
                    <small>核名预查询 提高通过率</small>
                </a>
                <a @click="goLink(3)" class="a1">
                    <h2>查商标</h2>
                    <small>1000万+ 商标</small>
                </a>
                <a @click="goLink(4)" class="a2">
                    <h2>查专利</h2>
                    <small>500万+ 专利</small>
                </a>
                <a @click="goLink(1)" class="a5">
                    <h2>查税号</h2>
                    <small>两步搞定新规</small>
                </a>
            </div>
        </nav>
        <div class="container m-t-md">
            <div id="indexCarousel" data-ride="carousel" class="carousel slide">
                <div class="carousel-inner banner_img">
                    <template>
                        <el-carousel>
                            <el-carousel-item>
                                <a class="item active">
                                    <img src="../assets/images/new_banner2.png" alt="顶企查">
                                </a>
                            </el-carousel-item>
                            <el-carousel-item>
                                <a class="item active">
                                    <img src="../assets/images/new_banner1.png" alt="顶企查">
                                </a>
                            </el-carousel-item>
                        </el-carousel>
                    </template>
                </div>
            </div>
        </div>

        <div class="container m-t-md no-padding">
            <div class="col-sm-6  no-padding-right">
                <section class="panel b-a">
                    <div class="panel-heading b-b">
                        <span class="pull-right">
                            <a @click="goNewsList('news_tech')" class="text-muted v3e_more">查看更多></a>
                            <!-- <router-link to='/NewsIndex' class="text-muted v3e_more">查看更多></router-link> -->
                        </span>
                        <span class="font-bold font-15 text-dark">科技新闻</span>
                    </div>
                    <div class="panel-body" style="height: 594px; overflow: hidden;">
                        <ul class="list-group alt" v-if="recommendArr">
                            <li class="v3e_index_fixLeft" v-for="(item,idx) in recommendArr">
                                <div class="v3ef_left">
                                    <img :src="item.titleImg" :alt="item.title">
                                </div>
                                <div class="v3ef_right">
                                    <a @click="newsDetail(item.title)">
                                        {{item.title}}
                                    </a>
                                    <small>
                                        {{item.articleSub}}
                                    </small>
                                </div>
                            </li>
                        </ul>
                        <ul class="list-group alt" v-else>
                            <li class="v3e_index_fixLeft">
                                <div class="v3ef_left">
                                    <img src="../assets/images/1529027546811554.jpg" title="三年蒸发300亿！RIO的百亿泡沫神话" alt="三年蒸发300亿！RIO的百亿泡沫神话_顶企查" class=""> </div>
                                <div class="v3ef_right">
                                    <a target="_blank">
                                        三年蒸发300亿！RIO的百亿泡沫神话
                                    </a>
                                    <small>
                                        <div class="pull-left">来源：投资界</div>
                                    </small>
                                </div>
                            </li>
                            <li class="v3e_index_fixLeft">
                                <div class="v3ef_left">
                                    <img src="../assets/images/1528942821800567.jpg" title="“首富”陈天桥和盛大游戏过去的十年" alt="“首富”陈天桥和盛大游戏过去的十年_顶企查" class=""> </div>
                                <div class="v3ef_right">
                                    <a target="_blank">
                                        “首富”陈天桥和盛大游戏过去的十年
                                    </a>
                                    <small>
                                        <div class="pull-left">来源：品途商业评论 懂懂笔记</div>
                                    </small>
                                </div>
                            </li>
                            <li class="v3e_index_fixLeft">
                                <div class="v3ef_left">
                                    <img src="../assets/images/1528856166707250.jpg" title="“杠上”腾讯的抖音日活已经1.5亿" alt="“杠上”腾讯的抖音日活已经1.5亿_顶企查" class=""> </div>
                                <div class="v3ef_right">
                                    <a target="_blank">
                                        “杠上”腾讯的抖音日活已经1.5亿
                                    </a>
                                    <small>
                                        <div class="pull-left">来源：第一财经</div>

                                    </small>
                                </div>
                            </li>
                            <li class="v3e_index_fixLeft">
                                <div class="v3ef_left">
                                    <img src="../assets/images/1528767282771173.jpg" title="“被跑路”的华谊兄弟，这次冤不冤？" alt="“被跑路”的华谊兄弟，这次冤不冤？_顶企查" class=""> </div>
                                <div class="v3ef_right">
                                    <a target="_blank">
                                        “被跑路”的华谊兄弟，这次冤不冤？
                                    </a>
                                    <small>
                                        <div class="pull-left">来源：新芽</div>

                                    </small>
                                </div>
                            </li>
                        </ul>
                    </div>
                </section>
            </div>
            <div class="col-sm-6  no-padding-left">
                <section class="panel b-a" id="album">
                    <div class="panel-heading b-b">
                        <span class="pull-right">
                            <a @click="goNewsList('finance')" class="text-muted v3e_more">查看更多></a>
                        </span>
                        <span class="font-bold font-15 text-dark">{{textMsg}}财经频道</span>
                    </div>
                    <div class="panel-body" style="height: 594px; overflow: hidden;">
                        <ul class="list-group alt" v-if="financeArr">
                            <li class="v3e_index_fixLeft" v-for="(item,idx) in financeArr">
                                <div class="v3ef_left">
                                    <img :src="item.titleImg" :alt="item.title">
                                </div>
                                <div class="v3ef_right">
                                    <a @click="newsDetail(item.title)">
                                        {{item.title}}
                                    </a>
                                    <small>
                                        {{item.articleSub}}
                                    </small>
                                </div>
                            </li>
                        </ul>
                        <ul class="list-group alt" v-else>
                            <li class="v3e_index_fixLeft">
                                <div class="v3ef_left">
                                    <img src="../assets/images/1527840742600519.jpg" title="中国ICO领域的创业公司" alt="中国ICO领域的创业公司_顶企查" class=""> </div>
                                <div class="v3ef_right">
                                    <a target="_blank">
                                        中国ICO领域的创业公司
                                    </a>
                                    <small>
                                        收录 9 家公司
                                    </small>
                                </div>
                            </li>
                            <li class="v3e_index_fixLeft">
                                <div class="v3ef_left">
                                    <img src="../assets/images/1526019426552881.jpg" title="电动智能汽车领域探索的中国创业公司" alt="电动智能汽车领域探索的中国创业公司_顶企查" class=""> </div>
                                <div class="v3ef_right">
                                    <a target="_blank">
                                        电动智能汽车领域探索的中国创业公司
                                    </a>
                                    <small>
                                        收录 22 家公司
                                    </small>
                                </div>
                            </li>
                            <li class="v3e_index_fixLeft">
                                <div class="v3ef_left">
                                    <img src="../assets/images/152593059827079.jpg" title="生产自动化，工业机器人的领军企业" alt="生产自动化，工业机器人的领军企业_顶企查" class=""> </div>
                                <div class="v3ef_right">
                                    <a>
                                        生产自动化，工业机器人的领军企业
                                    </a>
                                    <small>
                                        收录 15 家公司
                                    </small>
                                </div>
                            </li>
                            <li class="v3e_index_fixLeft">
                                <div class="v3ef_left">
                                    <img src="../assets/images/1524817612876672.jpg" title="微软加速器·北京公布11期入选创新企业" alt="微软加速器·北京公布11期入选创新企业_顶企查" class=""> </div>
                                <div class="v3ef_right">
                                    <a target="_blank">
                                        微软加速器·北京公布11期入选创新企业
                                    </a>
                                    <small>
                                        收录 16 家公司
                                    </small>
                                </div>
                            </li>
                        </ul>
                    </div>
                </section>
            </div>
            <div id="V3_Index_Con_Tab" class="col-md-12 hidden-xs">
                <section class="panel b-a">
                    <div class="panel-heading b-b">
                        <ul class="nav nav-tabs nav-justified">
                            <!-- <li :class="{'active':tabNum == 1}" @click="switchTab(1)">
                                <a class="font-bold font-15 text-dark" data-toggle="tab">
                                    <h2 class="font-bold font-15 text-dark">最新认证公司</h2>
                                </a>
                            </li> -->
                            <li :class="{'active':tabNum == 2}" @click="switchTab(2)">
                                <a class="font-bold font-15 text-dark" data-toggle="tab">
                                    <h2 class="font-bold font-15 text-dark">按行业查公司</h2>
                                </a>
                            </li>
                            <li :class="{'active':tabNum == 1}" @click="switchTab(1)">
                                <a class="font-bold font-15 text-dark" data-toggle="tab">
                                    <h2 class="font-bold font-15 text-dark">按区域查公司</h2>
                                </a>
                            </li>
                            <li :class="{'active':tabNum == 0}" @click="switchTab(0)">
                                <a class="font-bold font-15 text-dark" data-toggle="tab">
                                    <h2 class="font-bold font-15 text-dark">最新注册公司</h2>
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div class="panel-body">
                        <div class="tab-content m-t">
                            <div :class="{'tab-pane':true,'fade':true,'active':tabNum == 0 , in:tabNum == 0}" id="new" style="min-height:130px;">
                                <!-- <div class="col-md-3  no-padding ">
                                    <section class="panel panel-default">
                                        <div class="panel-body m_ahover">
                                            <a data-href="/firm_70989326fce2bdb4a40b510707a1b338.html" target="_blank">山雨鸡饭(深圳)健康科技有限公...</a>
                                            <p class="text-muted m-t-xs text-xs">成立日期：2018-06-19</p>
                                        </div>
                                    </section>
                                </div>
                                <div class="col-md-3 no-padding-right">
                                    <section class="panel panel-default">
                                        <div class="panel-body m_ahover">
                                            <a data-href="/firm_a8fb333aa0e3b139cb0e3dc4924f6d42.html" target="_blank">深圳市鸿邦建设工程有限公司</a>
                                            <p class="text-muted m-t-xs text-xs">成立日期：2018-06-19</p>
                                        </div>
                                    </section>
                                </div>
                                <div class="col-md-3 no-padding-right">
                                    <section class="panel panel-default">
                                        <div class="panel-body m_ahover">
                                            <a data-href="/firm_b4442d98e3fd9ce353aa45849c3f721a.html" target="_blank">环球金福控股有限公司</a>
                                            <p class="text-muted m-t-xs text-xs">成立日期：2018-06-19</p>
                                        </div>
                                    </section>
                                </div>
                                <div class="col-md-3 no-padding-right">
                                    <section class="panel panel-default">
                                        <div class="panel-body m_ahover">
                                            <a data-href="/firm_e60ceabad5c81a23bbdf19e1f46a445d.html" target="_blank">深圳市新丝路产业运营管理有限公...</a>
                                            <p class="text-muted m-t-xs text-xs">成立日期：2018-06-19</p>
                                        </div>
                                    </section>
                                </div>
                                <div class="col-md-3  no-padding ">
                                    <section class="panel panel-default">
                                        <div class="panel-body m_ahover">
                                            <a data-href="/firm_e100220160f322bd3e0544b47ff7ee95.html" target="_blank">宽源乐阳(深圳)商业保理有限公...</a>
                                            <p class="text-muted m-t-xs text-xs">成立日期：2018-06-19</p>
                                        </div>
                                    </section>
                                </div>
                                <div class="col-md-3 no-padding-right">
                                    <section class="panel panel-default">
                                        <div class="panel-body m_ahover">
                                            <a data-href="/firm_6f1bd8405894d25553427b12a88a11dd.html" target="_blank">深圳市中菁环境科技有限公司</a>
                                            <p class="text-muted m-t-xs text-xs">成立日期：2018-06-19</p>
                                        </div>
                                    </section>
                                </div>
                                <div class="col-md-3 no-padding-right">
                                    <section class="panel panel-default">
                                        <div class="panel-body m_ahover">
                                            <a data-href="/firm_2dbee31da5e800526c48fb1c9f683415.html" target="_blank">深圳万合汇才企业管理咨询合伙企...</a>
                                            <p class="text-muted m-t-xs text-xs">成立日期：2018-06-19</p>
                                        </div>
                                    </section>
                                </div>
                                <div class="col-md-3 no-padding-right">
                                    <section class="panel panel-default">
                                        <div class="panel-body m_ahover">
                                            <a data-href="/firm_8d56f66ce66b7189c84b82c4a3f94fcc.html" target="_blank">深圳市金枫生物医药科技有限公司</a>
                                            <p class="text-muted m-t-xs text-xs">成立日期：2018-06-19</p>
                                        </div>
                                    </section>
                                </div> -->
                                <p class="expect">敬请期待！</p>
                            </div>
                            <!-- <div :class="{'tab-pane':true,'fade':true,'active':tabNum == 1 , in:tabNum == 1}" id="cert">
                                <div class="col-md-3  no-padding ">
                                    <section class="panel panel-default">
                                        <div class="panel-body m_ahover">
                                            <a data-href="/firm_70fae24185eb7d8cdfa8dbed9139c369.html" target="_blank">山西警网文化传媒有限公司</a>
                                            <p class="text-muted m-t-xs text-xs">认证人：</p>
                                        </div>
                                    </section>
                                </div>
                                <div class="col-md-3 no-padding-right">
                                    <section class="panel panel-default">
                                        <div class="panel-body m_ahover">
                                            <a data-href="/firm_9eaa749532b045a4c58f476d2a1a886e.html" target="_blank">湖北博仕通科技工程有限责任公...</a>
                                            <p class="text-muted m-t-xs text-xs">认证人：</p>
                                        </div>
                                    </section>
                                </div>
                                <div class="col-md-3 no-padding-right">
                                    <section class="panel panel-default">
                                        <div class="panel-body m_ahover">
                                            <a data-href="/firm_b07e7eafa12f76565d68ebca67c49261.html" target="_blank">沧州市嘉和物业服务有限公司</a>
                                            <p class="text-muted m-t-xs text-xs">认证人：</p>
                                        </div>
                                    </section>
                                </div>
                                <div class="col-md-3 no-padding-right">
                                    <section class="panel panel-default">
                                        <div class="panel-body m_ahover">
                                            <a data-href="/firm_6e8b1d7b9df42cfbe933839a9fa57eda.html" target="_blank">上海盘丝信息科技有限公司</a>
                                            <p class="text-muted m-t-xs text-xs">认证人：</p>
                                        </div>
                                    </section>
                                </div>
                                <div class="col-md-3  no-padding ">
                                    <section class="panel panel-default">
                                        <div class="panel-body m_ahover">
                                            <a data-href="/firm_b39a9152aaf08c80fbdec85d398572ab.html" target="_blank">重庆爱车营商贸有限公司城口分...</a>
                                            <p class="text-muted m-t-xs text-xs">认证人：</p>
                                        </div>
                                    </section>
                                </div>
                                <div class="col-md-3 no-padding-right">
                                    <section class="panel panel-default">
                                        <div class="panel-body m_ahover">
                                            <a data-href="/firm_fba54aef91621410fc5239257b746960.html" target="_blank">华粮（天津）食品有限公司</a>
                                            <p class="text-muted m-t-xs text-xs">认证人：</p>
                                        </div>
                                    </section>
                                </div>
                                <div class="col-md-3 no-padding-right">
                                    <section class="panel panel-default">
                                        <div class="panel-body m_ahover">
                                            <a data-href="/firm_063501c1eb5b80e6776a29ef62c07295.html" target="_blank">四川锦凯科技有限公司</a>
                                            <p class="text-muted m-t-xs text-xs">认证人：</p>
                                        </div>
                                    </section>
                                </div>
                                <div class="col-md-3 no-padding-right">
                                    <section class="panel panel-default">
                                        <div class="panel-body m_ahover">
                                            <a data-href="/firm_6b216eba2e0cc8587ab94a884eab63ca.html" target="_blank">长春市赢红商贸有限公司</a>
                                            <p class="text-muted m-t-xs text-xs">认证人：</p>
                                        </div>
                                    </section>
                                </div>
                            </div> -->
                            <!-- 省份 -->
                            <div :class="{'tab-pane':true,'fade':true,'active':tabNum == 1 , in:tabNum == 1}" id="area">
                                <ul class="sidebar-hangye-list" v-if="partOptions">
                                    <li class="area  text-center" v-for="(item,index) in partOptions" :key="index">
                                        <a :data-href="item.name" @click="goCompany(item.code,item.name)">{{item.name}}</a>
                                    </li>
                                </ul>
                            </div>
                            <!-- //行业类别 -->
                            <div :class="{'tab-pane':true,'fade':true,'active':tabNum == 2 , in:tabNum == 2}" id="hangye">
                                <template v-if="indestryClassify" v-for="(item, index) in indestryClassify">
                                    <div class="col-md-12" :class="{'m-t-lg':index>0}" v-for="(val,key,idx) in item" :key="index">
                                        <div class="col-md-4 columns">
                                            <h4 class="parent-jobs-category">
                                                <a data-href="/industry_A.html" @click="goIndustry(key,'')"> {{key}}</a>
                                            </h4>
                                        </div>
                                        <div class="col-md-8 columns">
                                            <ul>
                                                <li v-for="(item2,idx2) in val" :key="idx2">
                                                    <a @click="goIndustry(key,item2.name)">{{item2.name}}</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </template>
                                <!-- <div class="col-md-12" >
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_A.html" @click="goIndustry('农、林、牧、渔业','')"> 业 农、林、牧、渔</a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_A_01.html" @click="goIndustry('农、林、牧、渔业','农业')">农业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_A_02.html" @click="goIndustry('农、林、牧、渔业','林业')">林业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_A_03.html" @click="goIndustry('农、林、牧、渔业','畜牧业')">畜牧业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_A_04.html" @click="goIndustry('农、林、牧、渔业','渔业')">渔业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_A_05.html" @click="goIndustry('农、林、牧、渔业','农、林、牧、渔专业及辅助性活动')">农、林、牧、渔专业及辅助性活动</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>   
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_B.html"> 采矿业 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_B_06.html" @click="goIndustry('采矿业','煤炭开采和洗选业')">煤炭开采和洗选业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_B_07.html" @click="goIndustry('采矿业','石油和天然气开采业')">石油和天然气开采业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_B_08.html" @click="goIndustry('采矿业','黑色金属矿采选业')">黑色金属矿采选业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_B_09.html" @click="goIndustry('采矿业','有色金属矿采选业')">有色金属矿采选业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_B_10.html" @click="goIndustry('采矿业','非金属矿采选业')">非金属矿采选业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_B_11.html" @click="goIndustry('采矿业','开采专业及辅助性活动')">开采专业及辅助性活动</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_B_12.html" @click="goIndustry('采矿业','其他采矿业')">其他采矿业</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_C.html"> 制造业 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_C_13.html">农副食品加工业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_14.html">食品制造业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_15.html">酒、饮料和精制茶制造业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_16.html">烟草制品业 </a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_17.html">纺织业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_18.html"> 纺织服装、服饰业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_19.html">皮革、毛皮、羽毛及其制品和制鞋业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_20.html">木材加工和木、竹、藤、棕、草制品业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_21.html">家具制造业 </a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_22.html">造纸和纸制品业 </a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_23.html">印刷和记录媒介复制业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_24.html">文教、工美、体育和娱乐用品制造业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_25.html">石油、煤炭及其他燃料加工业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_26.html">化学原料和化学制品制造业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_27.html">医药制造业 </a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_28.html">化学纤维制造业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_29.html">橡胶和塑料制品业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_30.html">非金属矿物制品业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_31.html">黑色金属冶炼和压延加工业 </a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_32.html">有色金属冶炼和压延加工业 </a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_33.html">金属制品业 </a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_34.html">通用设备制造业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_35.html">专用设备制造业 </a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_36.html">汽车制造业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_37.html">铁路、船舶、航空航天和其他运输设备制造业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_38.html">电气机械和器材制造业 </a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_39.html">计算机、通信和其他电子设备制造业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_40.html">仪器仪表制造业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_41.html">其他制造业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_42.html">废弃资源综合利用业 </a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_C_43.html">金属制品、机械和设备修理业</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_D.html"> 电力、热力、燃气及水生产和供应业 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_D_44.html">电力、热力生产和供应业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_D_45.html">燃气生产和供应业 </a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_D_46.html">水的生产和供应业 </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_E.html"> 建筑业 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_E_47.html">房屋建筑业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_E_48.html">土木工程建筑业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_E_49.html">建筑安装业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_E_50.html">建筑装饰、装修和其他建筑业</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_F.html"> 批发和零售业 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_F_51.html">批发业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_F_52.html">零售业</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_G.html"> 交通运输、仓储和邮政业 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_G_53.html">铁路运输业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_G_54.html">道路运输业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_G_55.html">水上运输业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_G_56.html">航空运输业 </a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_G_57.html">管道运输业 </a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_G_58.html">多式联运和运输代理业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_G_59.html">装卸搬运和仓储业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_G_60.html">邮政业</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_H.html"> 住宿和餐饮业 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_H_61.html">住宿业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_H_62.html">餐饮业</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_I.html"> 信息传输、软件和信息技术服务业 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_I_63.html">电信、广播电视和卫星传输服务</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_I_64.html">互联网和相关服务</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_I_65.html">软件和信息技术服务业</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_J.html"> 金融业 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_J_66.html">货币金融服务</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_J_67.html">资本市场服务</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_J_68.html">保险业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_J_69.html">其他金融业</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_K.html"> 房地产业 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_K_70.html">房地产业</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_L.html"> 租赁和商务服务业 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_L_71.html">租赁业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_L_72.html">商务服务业</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_M.html"> 科学研究和技术服务业 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_M_73.html">研究和试验发展</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_M_74.html">专业技术服务业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_M_75.html">科技推广和应用服务业</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_N.html"> 水利、环境和公共设施管理业 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_N_76.html">水利管理业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_N_77.html">生态保护和环境治理业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_N_78.html">公共设施管理业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_N_79.html">土地管理业</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_O.html"> 居民服务、修理和其他服务业 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_O_80.html">居民服务业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_O_81.html">机动车、电子产品和日用产品修理业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_O_82.html">其他服务业</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_P.html"> 教育 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_P_83.html">教育</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_Q.html"> 卫生和社会工作 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_Q_84.html">卫生</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_Q_85.html">社会工作</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_R.html"> 文化、体育和娱乐业 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_R_86.html">新闻和出版业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_R_87.html">广播、电视、电影和录音制作业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_R_88.html">文化艺术业</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_R_89.html">体育</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_R_90.html">娱乐业</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_S.html"> 公共管理、社会保障和社会组织 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_S_91.html">中国共产党机关</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_S_92.html">国家机构</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_S_93.html">人民政协、民主党派</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_S_94.html">社会保障</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_S_95.html">群众团体、社会团体和其他成员组织</a>
                                            </li>
                                            <li>
                                                <a data-href="/industry_S_96.html">基层群众自治组织及其他组织</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-md-12 m-t-lg">
                                    <div class="col-md-4 columns">
                                        <h4 class="parent-jobs-category">
                                            <a data-href="/industry_T.html"> 国际组织 </a>
                                        </h4>
                                    </div>
                                    <div class="col-md-8 columns">
                                        <ul>
                                            <li>
                                                <a data-href="/industry_T_97.html">国际组织</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div> -->
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>
        <!-- 复制粘贴结束 -->
    </div>
</template>
<script>
import Common from "@/util/common.js";
import { GetNews } from "@/util/Http.js";
export default {
    name: "banner",
    data() {
        return {
            msg: "Welcome to Your Vue.js App111111111111",
            tabNum: 1,
            partOptions: "",
            indestryClassify: "",
            financeArr: "",
            recommendArr: "",
            textMsg: ""
        };
    },
    created() {
        this.initpage();
        this.initNews();
    },
    methods: {
        initpage() {
            // console.log("dqc_provence",JSON.parse(sessionStorage.getItem("dqc_provence")),JSON.parse(sessionStorage.getItem("dqc_classify")))

            if (sessionStorage.getItem("dqc_provence")) {
                this.partOptions = JSON.parse(
                    sessionStorage.getItem("dqc_provence")
                );
            } else {
                this.$axios({
                    type: "get",
                    url: "companyAssets/nextNodeName",
                    data: { code: "7453706779315408896" },
                    cancel404: true,
                    success: res => {
                        // console.log("省份",res)
                        if (res.data.code == 0) {
                            this.partOptions = res.data.data;
                            sessionStorage.setItem(
                                "dqc_provence",
                                JSON.stringify(res.data.data)
                            );
                        }
                    }
                });
            }
            if (sessionStorage.getItem("dqc_classify")) {
                this.indestryClassify = JSON.parse(
                    sessionStorage.getItem("dqc_classify")
                );
            } else {
                this.$axios({
                    type: "get",
                    url: "companyAssets/getIndustryMsg",
                    data: { code: "industry" },
                    cancel404: true,
                    success: res => {
                        //console.log("行业类别",res)
                        if (res.data.code == 0) {
                            this.indestryClassify = res.data.data;
                            sessionStorage.setItem(
                                "dqc_classify",
                                JSON.stringify(res.data.data)
                            );
                        }
                    }
                });
            }
        },
        initNews() {
            GetNews(
                { type: "news_tech", pageIndex: 1, pageSize: 4 },
                res => {
                    let arr = res.listData;
                    if (arr.length > 4) {
                        arr.splice(4, arr.length);
                    }
                    this.recommendArr = arr;
                },
                404
            );
            GetNews(
                { type: "news_finance", pageIndex: 1, pageSize: 4 },
                res => {
                    let arr = res.listData;
                    if (arr.length > 4) {
                        arr.splice(4, arr.length);
                    }
                    this.financeArr = arr;
                },
                404
            );
        },
        switchTab(num) {
            this.tabNum = num;
        },
        goCompany(code, str) {
            this.$router.push({
                path: "/Industry",
                query: { type: "pro", industry_o: str, industry_t: "" }
            });
        },
        goIndustry(str1, str2) {
            this.$router.push({
                path: "/Industry",
                query: { type: "classify", industry_o: str1, industry_t: str2 }
            });
        },
        goNewsList(str) {
            this.$router.push({ path: "/NewsIndex", query: { type: str } });
        },
        newsDetail(id) {
            Common.newPage("NewsDetail", { title: id });
        },
        goLink(num) {
            switch (num) {
                case 0:
                    this.$router.push("/Qualifications_box");
                    break;
                case 2:
                    this.$router.push("/Verify_company");
                    break;
                case 1:
                    this.$router.push({
                        path: "/Search",
                        query: { type: "tax" }
                    });
                    break;
                case 3:
                    this.$router.push({
                        path: "/Search",
                        query: { type: "brand" }
                    });
                    break;
                case 4:
                    this.$router.push({
                        path: "/Search",
                        query: { type: "patent" }
                    });
                    break;
                default:
                    Common.alertMessage("温馨提示", "此数据只对VIP用户开放");
            }
        }
    }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import "../assets/test.css";

.banner_img img {
    display: block;
    max-width: 100%;
    height: auto;
    line-height: 1;
}
.expect {
    margin: auto;
    margin-top: 60px;
    text-align: center;
    font-size: 16px;
}
.sidebar-hangye-list li {
    cursor: pointer;
    border-radius: 2px;
}
.sidebar-hangye-list li:hover {
    text-decoration: none;
    background-color: #1aae88;
}
.sidebar-hangye-list li:hover a {
    color: #fff !important;
}
</style>
