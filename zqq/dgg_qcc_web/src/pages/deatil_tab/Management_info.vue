<template>
    <div class="tb_content">
        <section class="panel  clear m_dataTab">
            <div class="panel-body" style="padding-top: 5px">

                 <template v-if="msg.xzxkgsjs || msg.xzxkxyzg">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('permissionlist')">
                        行政许可&nbsp;{{msg.xzxkgsjs.length + msg.xzxkxyzg.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        行政许可&nbsp;0
                    </a>
                </template>

                 <template v-if="msg.swxys ">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('taxCreditList')">
                        税务信用&nbsp;{{msg.swxys.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        税务信用&nbsp;0
                    </a>
                </template>

                <template v-if="msg.products ">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('productlist')">
                        产品信息&nbsp;{{msg.products.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        产品信息&nbsp;0
                    </a>
                </template>

                <template v-if="msg.rzxxs ">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('financingList')">
                        融资信息&nbsp;{{msg.rzxxs.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        融资信息&nbsp;0
                    </a>
                </template>

                <template v-if="msg.zdbxx ">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('tenderlist')">
                        招投标信息&nbsp;{{msg.zdbxx.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        招投标信息&nbsp;0
                    </a>
                </template>

                <template v-if="msg.zp ">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('joblist')">
                        招聘&nbsp;{{msg.zp.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        招聘&nbsp;0
                    </a>
                </template>

                <template v-if="msg.cwzl ">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('V3_cwzl')">
                        财务总览&nbsp;{{msg.cwzl.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        财务总览&nbsp;0
                    </a>
                </template>

                 <template v-if="msg.jckxy ">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('V3_cwzl')">
                        进出口信用(无)&nbsp;{{msg.jckxy.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        进出口信用(无)&nbsp;0
                    </a>
                </template>

                <template v-if="msg.wxgzh ">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('wechatlist')">
                        微信公众号&nbsp;{{msg.wxgzh.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        微信公众号&nbsp;0
                    </a>
                </template>

                <template v-if="msg.jckxy ">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('V3_cwzl')">
                        新闻舆情(无)&nbsp;{{msg.jckxy.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        新闻舆情(无)&nbsp;0
                    </a>
                </template>

                 <template v-if="msg.jckxy ">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('V3_cwzl')">
                        公告研报(无)&nbsp;{{msg.jckxy.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        公告研报(无)&nbsp;0
                    </a>
                </template>

                 <template v-if="msg.jckxy ">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('V3_cwzl')">
                        地块公示(无)&nbsp;{{msg.jckxy.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        地块公示(无)&nbsp;0
                    </a>
                </template>

                 <template v-if="msg.jckxy ">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('V3_cwzl')">
                        购地信息(无)&nbsp;{{msg.jckxy.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        购地信息(无)&nbsp;0
                    </a>
                </template>

                 <template v-if="msg.jckxy ">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('V3_cwzl')">
                        土地转让(无)&nbsp;{{msg.jckxy.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        土地转让(无)&nbsp;0
                    </a>
                </template>

                 <template v-if="msg.jckxy ">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('V3_cwzl')">
                        债券信息(无)&nbsp;{{msg.jckxy.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        债券信息(无)&nbsp;0
                    </a>
                </template>
                <!-- <a class="btn btn-sm btn-default  m-r-sm m-t-sm" style="white-space:nowrap;">
                    行政许可&nbsp;{{ (msg.xzxkxyzg ? msg.xzxkxyzg.length : 0) + (msg.xzxkgsjs ? msg.xzxkgsjs.length : 0) }}
                </a> -->
                <!-- <a class="btn btn-sm btn-default  m-r-sm m-t-sm" style="white-space:nowrap;">
                    税务信用&nbsp;{{msg.swxys ? msg.swxys.length : 0}}
                </a> -->
                <!-- <a class="btn btn-sm btn-default  m-r-sm m-t-sm" style="white-space:nowrap;">
                    产品信息&nbsp;{{msg.products ? msg.products.length : 0}}
                </a> -->
                <!-- <a class="btn btn-sm btn-default  m-r-sm m-t-sm" style="white-space:nowrap;">
                    融资信息&nbsp;{{msg.rzxxs ? msg.rzxxs.length : 0}}
                </a> -->
                <!-- <a class="btn btn-sm btn-default  m-r-sm m-t-sm" style="white-space:nowrap;">
                    招投标信息&nbsp;{{msg.zdbxx ? msg.zdbxx.length : 0}}
                </a> -->
                <!-- <a class="btn btn-sm btn-default  m-r-sm m-t-sm" style="white-space:nowrap;">
                    招聘&nbsp;{{msg.zp ? msg.zp.length : 0}}
                </a> -->
                <!-- <a class="btn btn-sm btn-default  m-r-sm m-t-sm" style="white-space:nowrap;">
                    财务总览&nbsp;{{msg.cwzl ? msg.cwzl.length : 0}}
                </a> -->
                <!-- <a class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                    进出口信用(无)&nbsp;{{msg.jckxy ? msg.jckxy.length : 0}}
                </a>
                <a class="btn btn-sm btn-default  m-r-sm m-t-sm" style="white-space:nowrap;">
                    微信公众号&nbsp;{{msg.wxgzh ? msg.wxgzh.length : 0}}
                </a> -->
                <!-- <a class="btn btn-sm btn-default  m-r-sm m-t-sm" style="white-space:nowrap;">
                    新闻舆情（无）&nbsp;{{msg.xwyq ? msg.xwyq.length : 0}}
                </a>
                <a class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                    公告研报（无）&nbsp;{{msg.ggyb ? msg.ggyb.length : 0}}
                </a>
                <a class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                    地块公示（无）&nbsp;{{msg.dkgs ? msg.dkgs.length : 0}}
                </a>
                <a class="btn btn-sm btn-default  m-r-sm m-t-sm" style="white-space:nowrap;">
                    购地信息（无）&nbsp;{{msg.gdxx ? msg.gdxx.length : 0}}
                </a>
                <a class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                    土地转让（无）&nbsp;{{msg.tdzr ? msg.tdzr.length : 0}}
                </a>
                <a class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                    债券信息（无）&nbsp;{{msg.zjxx ? msg.zjxx.length : 0}}
                </a> -->
            </div>
        </section>
        <!-- 行政许可-工商局开始 -->
        <section class="panel clear " id="permissionlist" v-if="msg.xzxkgsjs">
            <div class="tcaption">
                <span class="title"> 行政许可 [工商局]</span>
                <span class="tbadge">{{msg.xzxkgsjs.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tbody>
                    <tr>
                        <th class="tx">序号</th>
                        <th>许可文件编号</th>
                        <th>许可文件名称</th>
                        <th>有效期自</th>
                        <th>有效期至</th>
                        <th>许可机关</th>
                        <th>许可内容</th>
                    </tr>
                    <tr v-for="(item,index) in msg.xzxkgsjs">
                        <td class="tx">{{index+1}}</td>
                        <td>{{item.fileNo}}</td>
                        <td class="text-center">
                            {{item.fileName}}
                        </td>
                        <td >
                           {{item.startDate}}
                        </td>
                        <td  class="text-center">
                           {{item.endDate}}
                        </td>
                        <td  class="text-center">
                           {{item.xkjg}}
                        </td>
                        <td  class="text-center">
                           {{item.xkConent}}
                        </td>
                    </tr>
                </tbody>
            </table>
        </section>
        <!-- 行政许可-工商局结束 -->
        <!-- 行政许可-信用中国开始 -->
        <section class="panel clear " v-if="msg.xzxkxyzg">
            <div class="tcaption">
                <span class="title"> 行政许可 [信用中国]</span>
                <span class="tbadge">{{msg.xzxkxyzg.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tbody>
                    <tr>
                        <th class="tx">序号</th>
                        <th>项目名称</th>
                        <th >地域</th>
                        <th>决定日期</th>
                        <th>内容</th>
                    </tr>
                    <tr v-for="(item,index) in msg.xzxkxyzg">
                        <td class="tx">{{index+1}}</td>
                        <td>{{item.projectName}}</td>
                        <td class="text-center">
                            {{item.area}}
                        </td>
                        <td class="text-center">
                            {{item.date}}
                        </td>
                        <td width="90" class="text-center">
                            <a class="ma_court c-max600" @click="test">
                                查看详情
                            </a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </section>
        <!-- 行政许可-信用中国结束 -->
        <!-- 税务信用 开始 -->
        <section class="panel clear  " id="taxCreditList" v-if="msg.swxys">
            <div class="tcaption">
                <span class="title">税务信用</span>
                <span class="tbadge">{{msg.swxys.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tbody>
                    <tr>
                        <th class="tx">序号</th>
                        <th>评价年度</th>
                        <th>纳税人识别号</th>
                        <th>纳税信用等级</th>
                        <th>评价单位</th>
                    </tr>
                      <tr v-for="(item,index) in msg.swxys">
                        <td class="tx">{{index+1}}</td>
                        <td class="text-center">{{item.year}}</td>
                        <td class="text-center">{{item.nsrsbh}}</td>
                        <td class="text-center">{{item.nsrxydj}}</td>
                        <td class="text-center">{{item.pjdw}}</td>
                    </tr>
                </tbody>
            </table>
        </section>
        <!-- 税务信用 结束 -->
        <!-- 产品信息 开始 -->
        <section class="panel  run_info" id="productlist" v-if="msg.products">
            <div class="tcaption">
                <span class="title">产品信息</span>
                <span class="tbadge">{{msg.products.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tbody>
                    <tr>
                        <th class="tx">序号</th>
                        <th>产品图片</th>
                        <th width="10%">产品名</th>
                        <th width="10%">融资信息</th>
                        <th width="15%">成立时间</th>
                        <th width="10%">所属地</th>
                        <th class="ma_center">产品介绍</th>
                    </tr>
                   <tr v-for="(item,index) in msg.products">
                         <td class="tx">{{index+1}}</td>
                        <td>
                            <img :src="item.url ? item.url : '' " style="width: 66px;max-height: 66px;" :onerror="errorImg">
                        </td>
                        <td class="text-center">
                            <a class="c_a"  @click="test">{{item.name}}</a>
                        </td>
                        <td class="text-center">{{item.rzxx}}</td>
                        <td class="text-center">{{item.time}}</td>
                        <td class="text-center">{{item.area}}</td>
                        <td>{{item.msg}}</td>
                    </tr>
                </tbody>
            </table>
        </section>
        <!-- 产品信息 结束 -->
        <!-- 融资信息 7 开始 -->
        <section class="panel  run_info" id="financingList" v-if="msg.rzxxs">
            <div class="tcaption">
                <span class="title">融资信息</span>
                <span class="tbadge">{{msg.rzxxs.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tbody>
                    <tr>
                        <th>序号</th>
                        <th>日期</th>
                        <th>产品名称</th>
                        <th>级别</th>
                        <th>金额</th>
                        <th>投资方</th>
                    </tr>
                    <tr v-for="(item,index) in msg.rzxxs">
                         <td class="tx">{{index+1}}</td>
                        <td width="103">{{item.date}}</td>
                        <td width="103">{{item.name}}</td>
                        <td width="80" class="text-center">{{item.level}}</td>
                        <td class="text-center">{{item.money}}</td>
                        <td class="text-center">
                            <a @click="test">{{item.dzf}}</a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </section>
        <!-- 融资信息 7 结束 -->
        <!-- 招投标信息 1 开始 -->
        <section class="panel  run_info" id="tenderlist" v-if="msg.zdbxx">
            <div class="tcaption">
                <span class="title">招投标信息</span>
                <span class="tbadge">{{msg.zdbxx.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tbody>
                    <tr>
                        <th class="tx">序号</th>
                        <th>描述</th>
                        <th>发布时间</th>
                        <th>所属地区</th>
                        <th>项目分类</th>
                    </tr>
                    <tr v-for="(item,index) in msg.zdbxx">
                        <td class="tx">{{index+1}}</td>
                        <td style="width:50%">
                            <a class="text-blue" @click="test">{{item.msg}}</a>
                        </td>
                        <td width="103">
                            {{item.date}}
                        </td>
                        <td width="100" class="text-center"> {{item.area}}</td>
                        <td class="text-center">{{item.type}}</td>
                    </tr>
                </tbody>
            </table>
            <div> </div>
        </section>
        <!-- 招投标信息 1 结束 -->
        <!-- 进出口信用开始 -->
    <section class="panel  run_info" v-if="msg.jckxy">
            <div class="tcaption">
                <span class="title">进出口信用(无数据)</span>
                <span class="tbadge">{{msg.jckxy.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tbody>
                    <tr>
                        <th class="tx">序号</th>
                        <th>注册海关</th>
                        <th>经营类别</th>
                        <th>注册日期</th>
                        <th>内容</th>
                    </tr>
                    <tr>
                        <td class="tx">
                            1
                        </td>
                        <td style="width:50%">
                            <a data-toggle="modal" data-target="#appDownloadModal" class="text-blue" @click="test">数据 数据数据</a>
                        </td>
                        <td width="103">
                            数据 数据数据
                        </td>
                        <td width="100" class="text-center"> 数据 数据数据</td>
                        <td class="text-center">数据 数据数据</td>
                    </tr>
                </tbody>
            </table>
            <div> </div>
        </section>
        <!-- 进出口信用结束 -->
        <!-- 招聘 开始 -->
        <section class="panel  run_info" id="joblist" v-if="msg.zp">
            <div class="tcaption">
                <span class="title">招聘</span>
                <span class="tbadge">{{msg.zp.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tbody>
                    <tr>
                        <th>序号</th>
                        <th>发布时间</th>
                        <th>招聘职位</th>
                        <th>月薪</th>
                        <th>学历</th>
                        <th>经验</th>
                        <th>所在城市</th>
                    </tr>
                    <tr v-for="(item,index) in msg.zp">
                        <td class="tx">{{index+1}}</td>
                        <td width="103">
                            {{item.date}}
                        </td>
                        <td>
                           {{item.zpzw}}
                        </td>
                        <td class="text-center">
                             {{item.yx}}
                        </td>
                        <td class="text-center">
                           {{item.xl}}
                        </td>
                        <td class="text-center">
                           {{item.jy}}
                        </td>
                         <td class="text-center">
                           {{item.city}}
                        </td>
                    </tr>
                </tbody>
            </table>
        </section>
        <!-- 招聘 1结束 -->
        <!-- 财务总览 开始 -->
        <section class="panel  clear finance_info" id="V3_cwzl" v-if="msg.cwzl">
            <div class="tcaption">
                <span class="title">财务总览</span>&nbsp;&nbsp;(相关数据来源企业年报估算，仅供参考)
                <span class="watermark"></span>
            </div>
            <table class="ntable">
                <tbody>
                    <tr>
                        <td width="20%" class="tb">公司实力等级：</td>
                        <td width="30%">10亿-100亿</td>
                        <td width="20%" class="tb">纳税区间：</td>
                        <td width="30%">1亿以上</td>
                    </tr>
                    <tr>
                        <td class="tb">销售净利润率：</td>
                        <td>10%到50%</td>
                        <td class="tb">销售毛利率：</td>
                        <td>大于50%</td>
                    </tr>
                </tbody>
            </table>
        </section>
        <!-- 财务总览 结束 -->
         <!-- 公告研报 开始 -->
        <section class="panel  clear finance_info" v-if="msg.ggyb">
            <div class="tcaption">
                <span class="title">公告研报</span>&nbsp;&nbsp;
                <span class="watermark">{{msg.cwzl.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable">
                <tbody>
                    <tr>
                        <td width="20%" class="tb">公司实力等级：</td>
                        <td width="30%">10亿-100亿</td>
                        <td width="20%" class="tb">纳税区间：</td>
                        <td width="30%">1亿以上</td>
                    </tr>
                    <tr>
                        <td class="tb">无数据</td>
                        <td>无数据</td>
                        <td class="tb">无数据</td>
                        <td>无数据</td>
                    </tr>
                </tbody>
            </table>
        </section>
        <!-- 公告研报 结束 -->
         <!-- 地块公示 开始 -->
        <section class="panel  clear finance_info" v-if="msg.dkgs">
            <div class="tcaption">
                <span class="title">地块公示</span>&nbsp;&nbsp;
                <span class="watermark">{{msg.dkgs.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable">
                <tbody>
                    <tr>
                        <td width="20%" class="tb">公司实力等级：</td>
                        <td width="30%">10亿-100亿</td>
                        <td width="20%" class="tb">纳税区间：</td>
                        <td width="30%">1亿以上</td>
                    </tr>
                    <tr>
                        <td class="tb">无数据</td>
                        <td>无数据</td>
                        <td class="tb">无数据</td>
                        <td>无数据</td>
                    </tr>
                </tbody>
            </table>
        </section>
        <!-- 地块公示 结束 -->
        
          <!-- 土地转让 开始 -->
        <section class="panel  clear finance_info" v-if="msg.tdzr">
            <div class="tcaption">
                <span class="title">土地转让</span>&nbsp;&nbsp;
                <span class="watermark">{{msg.tdzr.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable">
                <tbody>
                    <tr>
                        <td width="20%" class="tb">公司实力等级：</td>
                        <td width="30%">10亿-100亿</td>
                        <td width="20%" class="tb">纳税区间：</td>
                        <td width="30%">1亿以上</td>
                    </tr>
                    <tr>
                        <td class="tb">无数据</td>
                        <td>无数据</td>
                        <td class="tb">无数据</td>
                        <td>无数据</td>
                    </tr>
                </tbody>
            </table>
        </section>
        <!-- 土地转让 结束 -->
        <!-- 债券信息 开始 -->
        <section class="panel  clear finance_info" v-if="msg.zjxx">
            <div class="tcaption">
                <span class="title">债券信息</span>&nbsp;&nbsp;
                <span class="watermark">{{msg.zjxx.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable">
                <tbody>
                    <tr>
                        <td width="20%" class="tb">公司实力等级：</td>
                        <td width="30%">10亿-100亿</td>
                        <td width="20%" class="tb">纳税区间：</td>
                        <td width="30%">1亿以上</td>
                    </tr>
                    <tr>
                        <td class="tb">无数据</td>
                        <td>无数据</td>
                        <td class="tb">无数据</td>
                        <td>无数据</td>
                    </tr>
                </tbody>
            </table>
        </section>
        <!-- 债券信息 结束 -->
        <!-- 微信公众号 开始 -->
        <section class="panel clear  " id="wechatlist" v-if="msg.wxgzh">
            <div class="tcaption">
                <span class="title"> 微信公众号</span>
                <span class="tbadge">{{msg.wxgzh.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tbody>
                    <tr>
                        <th class="tx">序号</th>
                        <th width="90">头像</th>
                        <th width="174">微信公众号</th>
                        <th width="134">微信号</th>
                        <th width="80">二维码</th>
                        <th>简介</th>
                    </tr>
                    <tr v-for="(item,index) in msg.wxgzh">
                        <td class="tx">{{index+1}}</td>
                        <td>
                            <img :src="item.head ? item.head :'' " style="width: 66px;" :onerror="errorImg"> </td>
                        <td>{{item.gzh}}</td>
                        <td>{{item.wxh}}</td>
                        <td class="text-center">
                            <!-- <img src="../../assets/images/icon_qrcode.png" class="wechat-img">
                            <div class="text-primary wechart-hover">详情
                                <div class="wechat-kuang">
                                    <div class="title">阿里巴巴国际北方</div>
                                    <div class="subtitle">alibababf</div>
                                    <img class="wechat-qrcode" src="http://img.qichacha.com/Wechat/Qrcode/0041962128fafdb27ae509e101b02ad5.jpg"> </div>
                            </div> -->
                            暂无
                        </td>
                        <td>
                            {{item.ewm}}
                        </td>
                    </tr>
                </tbody>
            </table>
            <div> </div>
        </section>
        <!-- 微信公众号 结束 -->
        
        <!-- 购地信息 开始 -->
        <section class="panel clear " v-if="msg.gdxx">
            <div class="tcaption">
                <span class="title">购地信息</span>
                <span class="tbadge">{{msg.gdxx.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tbody>
                    <tr>
                        <th>序号</th>
                        <th>土地坐落</th>
                        <th>土地用途</th>
                        <th>总面积（公顷）</th>
                        <th>行政区</th>
                        <th>供应方式</th>
                        <th>签订日期</th>
                    </tr>
                    <tr>
                        <td class="tx">
                            1
                        </td>
                        <td>
                            <a onclick="landpurchaseDetail(&quot;f0bc1d41e9fc2d49c51ab46a1b7a1caa0&quot;)" data-toggle="modal" data-target="#landpurchaseModal" @click="test">
                                滨江区网商路与滨兴路交叉口西北角(杭高新工业[2011]3号地块
                            </a>
                        </td>
                        <td width="133">工业用地</td>
                        <td width="133" class="text-center">1.898800</td>
                        <td width="103" class="text-center">杭州市本级</td>
                        <td width="103" class="text-center">挂牌出让</td>
                        <td width="103" class="text-center">
                            2012-10-26
                        </td>
                    </tr>
                </tbody>
            </table>
            <div> </div>
        </section>
        <!-- 购地信息 结束 -->
        <!-- 新闻舆情 开始 -->
        <section class="panel clear " id="newslist" v-if="msg.xwyq">
            <div class="tcaption">
                <span class="title">新闻舆情</span>
                <span class="tbadge">{{msg.xwyq.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-list">
                <tbody>
                    <tr>
                        <td>
                            <a class="clearfix" rel="nofollow" @click="test">
                                <div>
                                    <span class="title">蒙牛如何用一瓶酸奶开启世界杯的真情时刻</span>
                                    <small class="clear subtitle m-t-lg">
                                        <label>来源：</label> 中国国际电子商务网
                                        <span class="pull-right">
                                            <label>发布时间：</label> 2018-06-29 </span>
                                    </small>
                                </div>
                            </a>
                        </td>
                    </tr>
                </tbody>
            </table>
            <!-- <div style="margin-bottom: 30px;">
                <nav class="text-right">
                    <ul class="pagination">
                        <li class="active">
                            <a htef="#">1</a>
                        </li>
                        <li>
                            <a id="ajaxpage" href='javascript:getTabList(2,"run","news")'>2</a>
                        </li>
                        <li>
                            <a id="ajaxpage" href='javascript:getTabList(3,"run","news")'>3</a>
                        </li>
                        <li>
                            <a id="ajaxpage" href='javascript:getTabList(4,"run","news")'>4</a>
                        </li>
                        <li>
                            <a id="ajaxpage" href='javascript:getTabList(5,"run","news")'>5</a>
                        </li>
                        <li>
                            <a id="ajaxpage" href='javascript:getTabList(2,"run","news")'>&gt;</a>
                        </li>
                        <li>
                            <a id="ajaxpage" href='javascript:getTabList(50,"run","news")'> ...50</a>
                        </li>
                    </ul>
                </nav>
            </div> -->
        </section>
        <!-- 新闻舆情 结束 -->
    </div>
</template>
<script>


import NoImg from "@/assets/images/no_image.png";


export default {
    name: "head1",
    props: {
        msg: {}
    },
    data() {
        return {
            errorImg:'this.src="' + NoImg + '"',
        };
    },
    created() {
        
    },
    mounted() {},
    methods: {
          test(){
            this.$global.alertMessage()
      },
       scollTo(ele){
           this.$emit('scoll',ele)
      },
    }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
