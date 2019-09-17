<template>
    <div class="tb_content">
        <section class="panel clear m_dataTab">
            <div class="panel-body" style="padding-top: 5px;">

                <a class="btn btn-sm btn-default m-t-sm  m-r-sm " @click="scollTo('Cominfo')">
                    工商信息
                </a>

                <template v-if="msg.partnersList">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('Sockinfo')">
                        股东信息&nbsp;{{msg.partnersList.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        股东信息&nbsp;0
                    </a>
                </template>
               
                <template v-if="msg.dwtz">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('touzilist')">
                        对外投资&nbsp;{{msg.dwtz.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        对外投资&nbsp;0
                    </a>
                </template>

                <template v-if="msg.employeesList">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('Mainmember')">
                        主要成员&nbsp;{{msg.employeesList.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        主要成员&nbsp;0
                    </a>
                </template>

                <template v-if="msg.branchesList">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('branchesList')">
                        分支机构&nbsp;{{msg.branchesList.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        分支机构(无)&nbsp;0
                    </a>
                </template>

                <template v-if="msg.changeRecordsList">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('updatelist')">
                        变更记录&nbsp;{{msg.changeRecordsList.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        变更记录&nbsp;0
                    </a>
                </template>

                <template v-if="msg.syr">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('fzjg')">
                        最终受益人(无)&nbsp;{{msg.syr.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        最终受益人(无)&nbsp;0
                    </a>
                </template>

                <template v-if="msg.kgqy">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('fzjg')">
                        控股企业(无)&nbsp;{{msg.kgqy.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        控股企业(无)&nbsp;0
                    </a>
                </template>
            </div>
        </section>
        <!-- //工商信息开始 -->
        <section class="panel" id="Cominfo" >
            <div class="tcaption">
                <span class="title">工商信息</span>
                <a  class="text-primary kz_anim  ripple"  target="_blank" @click="test">
                    查看工商官网快照
                </a>
                <span class="watermark"></span>
            </div>
            <div class="prot-gltu" id="protGltu"></div>
            <table class="ntable" style="margin-bottom: -1px;">
                <tr>
                    <td width="240" class="tb">
                        法定代表人信息
                    </td>
                    <td width="310" class="tb">企业关联图谱
                        <img class="m-l-sm" style="width: 30px;" src="../../assets/images/icon_hot.png">
                    </td>
                    <td class="tb">股权结构图</td>
                </tr>
                <tr>
                    <td height="180" class="ma_left">
                        <div class="boss-td">
                            <div class="clearfix" style="height: 108px;padding-top: 20px;overflow: hidden;">
                                <div class="pull-left">
                                    <img class="bheadimg" src="../../assets/images/boss_head.png"> </div>
                                <div class="pull-left" style="max-width: 235px;">
                                    <a data-href="/pl_pr40436349dd18b42d5dcc2b2a351323.html" class="bname" @click="test">{{msg.frname || '暂不公示' }}</a>
                                    <a class="btouzi" data-href="javascript:void(0)" @click="test">他关联的<span></span>公司 > </a>
                                </div>
                            </div>
                            <div class="ba-table-base" style="margin-left: -5px;">
                                <a data-href="javascript:void(0)" class="btn btn-primary basePageBt" @click="test">查看人物图谱</a>
                            </div>
                        </div>
                    </td>
                    <td class="">
                        <div style="height: 108px;text-align: center;cursor:pointer;">
                            <a data-href="javascript:void(0)">
                                <img style="width: 263px;" src="../../assets/images/icon_gltp.png">
                            </a>
                        </div>
                        <div class="ba-table-base">
                            <a data-href="javascript:void(0)" class="btn btn-primary basePageBt" @click="test">查看详情</a>
                        </div>
                    </td>
                    <td class="guquan-static-td">
                        <div id="guquanStatic" style="height: 108px;overflow: hidden;">
                            <svg width="301" height="480">
                                <g transform="translate(12,22) scale(0.8)">
                                    <path class="link" fill="none" stroke-width="0.5px" stroke="#80c3a8" d="M0,0C10,0 10,55 20,55"></path>
                                    <g class="node" transform="translate(0,0)" style="opacity: 1;">
                                        <rect y="-25" height="50" width="335" fill="#ffffff" fill-opacity="0" stroke-width="0.5" style="stroke: rgb(153, 153, 153);"></rect>
                                        <rect y="-25" height="50" width="5" style="fill: rgb(18, 139, 237); fill-opacity: 1;"></rect>
                                        <text dy="3.5" dx="30.5" fill="#333333" style="font-size: 12px; font-weight: bold;">{{msg.name}}</text>
                                        <text dy="15.5" dx="305" fill="#128bed" style="font-size: 12px; cursor: pointer;"></text>
                                        <text dy="20.5" dx="30.5" fill="#666666" style="font: 11px sans-serif;"></text>
                                        <text dy="20.5" dx="95.5" fill="#ff7e00" style="font: 11px sans-serif;"></text>
                                        <text dy="20.5" dx="150.5" fill="#666666" style="font: 11px sans-serif;"></text>
                                        <text dy="20.5" dx="215.5" fill="#ff7e00" style="font: 11px sans-serif;"></text>
                                        <path d="M7 -2 H17 V0 H7 Z" fill="#bbbbbb"></path>
                                        <circle cx="12" cy="-1" r="6" fill="none" stroke="#999999" stroke-width="0.5"></circle>
                                    </g>
                                    <g class="node" transform="translate(20,55)" style="opacity: 1;">
                                        <rect y="-15" height="50" width="335" fill="#ffffff" fill-opacity="0" stroke-width="0.5" style="stroke: rgb(153, 153, 153);"></rect>
                                        <rect y="-15" height="50" width="5" style="fill: rgb(74, 206, 177); fill-opacity: 1;"></rect>
                                        <text dy="3.5" dx="30.5" fill="#333333" style="font-size: 12px; font-weight: bold;">{{msg.frname || 'xxxxx'}}  </text>
                                        <text dy="15.5" dx="305" fill="#128bed" style="font-size: 12px; cursor: pointer;"></text>
                                        <text dy="20.5" dx="30.5" fill="#666666" style="font: 11px sans-serif;">股权比例：</text>
                                        <text dy="20.5" dx="95.5" fill="#ff7e00" style="font: 11px sans-serif;">xx.xx%</text>
                                        <text dy="20.5" dx="150.5" fill="#666666" style="font: 11px sans-serif;">认缴金额：</text>
                                        <text dy="20.5" dx="215.5" fill="#ff7e00" style="font: 11px sans-serif;">xxxxx.xx万元</text>
                                        <path fill="#bbbbbb"></path>
                                        <circle cx="12" cy="7" fill="none" stroke="#999999" stroke-width="0.5"></circle>
                                    </g>
                                </g>
                            </svg>
                        </div>
                        <div onclick="" class="mengban"></div>
                        <div class="ba-table-base">
                            <a data-href="javascript:void(0)" class="btn btn-primary basePageBt" @click="test">查看详情</a>
                        </div>
                    </td>
                </tr>
            </table>
            <table class="ntable">
                <tr>
                    <td width="20%" class="tb">注册资本：</td>
                    <td width="30%" class="">{{msg.registCapi ? msg.registCapi+"万人民币元" : "-" }}</td>
                    <td width="20%" class="tb">实缴资本：</td>
                    <td width="30%" class=""> {{msg.realCapi ? msg.realCapi+"万人民币元" : "-" }}</td>
                </tr>
                <tr>
                    <td class="tb">经营状态：</td>
                    <td class=""> {{msg.status || "-"}} </td>
                    <td class="tb" width="18%">成立日期：</td>
                    <td class="">{{msg.startDate || "-"}}</td>
                </tr>
                <tr>
                    <td class="tb">统一社会信用代码：</td>
                    <td class="">
                        {{msg.creditCode}}
                    </td>
                    <td class="tb">纳税人识别号：</td>
                    <td class="">
                         {{msg.creditCode}}
                    </td>
                </tr>
                <tr>
                    <td class="tb">注册号：</td>
                    <td class="">
                        {{msg.no || "-"}}
                    </td>
                    <td class="tb" width="15%">组织机构代码：</td>
                    <td class="">
                        {{msg.orgNo || "-"}}
                    </td>
                </tr>
                <tr>
                    <td class="tb">公司类型：</td>
                    <td class="">
                         {{msg.econKind || "-"}}
                    </td>
                    <td class="tb">所属行业：</td>
                    <td class="">
                       {{msg.industry || "-"}}
                    </td>
                </tr>
                <tr>
                    <td class="tb">核准日期：</td>
                    <td class="" style="max-width:301px;">
                         {{msg.checkDate || "-"}}
                    </td>
                    <td class="tb">登记机关：</td>
                    <td class="">
                        {{msg.belongOrg || "-"}}
                    </td>
                </tr>
                <tr>
                    <td class="tb">所属地区：</td>
                    <td class="" style="max-width:301px;">
                         {{msg.province || "-"}}
                    </td>
                    <td class="tb">英文名：</td>
                    <td class="">
                         {{msg.englishName || "-"}}
                    </td>
                </tr>
                <tr>
                    <td class="tb">
                        曾用名
                    </td>
                    <td class="">
                       {{msg.originalName || "-"}}
                    </td>
                    <td class="tb">
                        参保人数
                    </td>
                    <td class="">
                         {{msg.cbPersonNum || "-"}}
                    </td>
                </tr>
                <tr>
                    <td class="tb">
                        人员规模
                    </td>
                    <td class="">
                         {{msg.personNum || "-"}}
                    </td>
                    <td class="tb">
                        营业期限
                    </td>
                    <td class="">
                        {{msg.termStart}} - {{msg.teamEnd}}
                    </td>
                </tr>
                <tr>
                    <td class="tb">企业地址：</td>
                    <td class="" colspan="3">
                       {{msg.address}}
                        <!-- <a onclick="showMapModal('中山市横栏镇新茂工业区康龙三路32号4楼之一','')" class="m-l c_a"> 查看地图</a>
                        <a data-href="/map?keyNo=7e652fc34541b7cdf0b354e5a444b363" class="m-l c_a"> 附近公司</a> -->
                    </td>
                </tr>
                <tr>
                    <td class="tb">经营范围：</td>
                    <td class="" colspan="3">
                        {{msg.scope}}
                    </td>  
                </tr>
            </table>
        </section>
         <!-- //工商信息结束 -->
         <!-- 股东信息开始 -->
        <section class="panel  clear" id="Sockinfo"  v-if="msg.partnersList">
            <div class="tcaption">
                <span class="title">股东信息</span>
                <span class="tbadge">{{msg.partnersList.length}}</span>
                <a  class="text-primary kz_anim ripple" @click="test">股权结构图&疑似实际控制人</a>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tr>
                    <th class="tx">序号</th>
                    <th width="">股东（发起人）</th>
                    <th width="90">持股比例</th>
                    <th width="135">认缴出资额(万元)</th>
                    <th width="110">认缴出资日期</th>
                    <!-- <th width="110">实缴出资额(万元)</th>
                    <th width="110">实缴出资日期</th> -->
                </tr>
                <tr v-for="(item,index) in msg.partnersList" :key="index">
                    <td class="tx">{{index+1}}</td>
                    <td>
                        <a @click="test">{{item.stockName}}</a>
                        <a class="btn-touzi" @click="test">他关联1家公司 > </a>
                    </td>
                    <td class="text-center">{{item.stockPercent || "-"}} </td>
                    <td class="text-center">{{item.shouldCapi || "-"}}</td>
                    <td class="text-center">{{item.shoudDate || "-"}}</td>
                    <!-- <td class="text-center">{{item.RealCapi || "-"}}</td>
                    <td class="text-center">{{item.CapiDate || "-"}}</td> -->
                </tr>
            </table>
        </section>
        <!-- 股东信息结束 -->
        <!-- 主要人员开始 -->
        <section class="panel clear" id="Mainmember"  v-if="msg.employeesList">
            <div class="tcaption">
                <span class="title">主要人员</span>
                <span class="tbadge">{{msg.employeesList.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tr>
                    <th class="">序号</th>
                    <th class="">姓名</th>
                    <th class="">职务</th>
                </tr>
                <tr v-for="(item,index) in msg.employeesList" :key='index'> 
                    <td class="tx">{{index+1}}</td>
                    <td width="50%">
                        <a class="c_a" @click="test">{{item.name}}</a>
                        <a class="btn-touzi pull-right" @click="test">他关联 1 家公司 > </a>
                    </td>
                    <td class="text-center">
                        {{item.job}}
                    </td>
                </tr>
            </table>
        </section>
         <!-- 主要人员结束 -->
         <!-- 分支机构 开始 -->
        <section class="panel clear" id="branchesList"  v-if="msg.branchesList">
            <div class="tcaption">
                <span class="title">分支机构</span>
                <span class="tbadge">{{msg.branchesList.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tr>
                    <th class="">序号</th>
                    <th class="">分支机构名</th>
                </tr>
                <tr v-for="(item,index) in msg.branchesList" :key='index'> 
                    <td class="tx">{{index+1}}</td>
                    <td class="text-center">
                        {{item.name}}
                    </td>
                </tr>
            </table>
        </section>
         <!-- 分支机构结束 -->
         <!-- 对外投资分析开始 -->
        <section class="panel  clear" id="touzilist" v-if="msg.dwtz"> 
            <div class="tcaption">
                <span class="title">对外投资分析</span>
                <span class="tbadge">{{msg.dwtz.length}}</span>
                <span class="watermark"></span>
            </div>
           <table class="ntable ntable-odd">
                <tr>
                    <th class="" width="220">被投资企业名称</th>
                    <th class="" width="200">被投资法定代表人</th>
                    <th class="">注册资本(万人民币元)</th>
                    <th class="">出资比例</th>
                    <th class="">成立日期</th>
                    <th class="">状态</th>
                </tr>
                <tr v-for="(item,index) in msg.dwtz" :key='index'> 
                    <td class="tx"><a @click="test">{{item.name}}</a></td>
                    <td width="">
                        <a class="c_a" @click="test">{{item.operName}}</a>
                        <a class="btn-touzi pull-right" @click="test">他关联 1 家公司 > </a>
                    </td>
                     <td class="text-center">
                        {{item.registCapi}}
                    </td>
                    <td class="text-center">
                        {{item.fundedRatio}}
                    </td>
                     <td class="text-center">
                        {{item.startDate}}
                    </td>
                     <td class="text-center">
                        {{item.status}}
                    </td>
                </tr>
            </table>
        </section>
        <!-- 对外投资分析结束 -->
        <!-- 变更记录开始 -->
        <section class="panel " id="updatelist" v-if="msg.changeRecordsList">
            <div class="tcaption">
                <span class="title">变更记录</span>
                <span class="tbadge changelistcount">{{msg.changeRecordsList.length}}</span>
                <!-- <div class="tdrop pull-right">
                    <span class="btn" data-toggle="dropdown">
                        <span class="changelisttype">全部类型</span>
                        <span class="caret m-l"></span>
                    </span>
                    <ul class="dropdown-menu dropdown-menu-right">
                        <li>
                            <a onclick="changeRecordsFilter('',this)">全部类型</a>
                        </li>
                        <li>
                            <a onclick="changeRecordsFilter('法定代表人',this)">法定代表人</a>
                        </li>
                        <li>
                            <a onclick="changeRecordsFilter('自然人股东',this)">自然人股东</a>
                        </li>
                        <li>
                            <a onclick="changeRecordsFilter('企业类型',this)">企业类型</a>
                        </li>
                        <li>
                            <a onclick="changeRecordsFilter('住所/经营场所',this)">住所/经营场所</a>
                        </li>
                        <li>
                            <a onclick="changeRecordsFilter('经营范围',this)">经营范围</a>
                        </li>
                    </ul>
                </div> -->
            </div>
            <table class="ntable">
                <tr>
                    <th class="tx">序号</th>
                    <th>变更日期</th>
                    <th>变更项目</th>
                    <th>变更前</th>
                    <th>变更后</th>
                </tr>
                <tr v-for="(item,index) in msg.changeRecordsList" :key="index">
                    <td class="tx">{{index+1}}</td>
                    <td width="103" class="text-center">{{item.changeDate}}</td>
                    <td width="" class="text-center">
                        <!-- 法定代表人
                        <br>
                        <span class="text-gray" style="font-size: 12px">带有*标记的为法定代表人</span> -->
                        {{item.projectName}}
                    </td>
                    <td width="30%">
                        <div style="max-width: 300px;" v-html="hasEm(item.beforeContent)"></div>
                    </td>
                    <td width="30%">
                       <div style="max-width: 300px;" v-html="hasEm(item.afterContent)"></div>
                    </td>
                </tr>
            </table>
        </section>
        <!-- 变更记录结束 -->
    </div>
</template>
<script>

import Common,{Jump} from '@/util/common.js';

import option from "@/util/map-option"; //引入配置项

import "echarts/map/js/china.js";
import "echarts/theme/shine.js";


export default {
  name: "head1",
  props: {
        msg: {}
    },
  data() {
    return {
    };
  },
  created() {
      //console.log(2222,this.msg)
  },
  mounted() {
    // this.drawLine();
    // this.drawMap();
  },
  methods: {
     test(){
            this.$global.alertMessage()
      },
      hasEm(str){
          if(str){
              return str.replace(/退出/g,"<em>退出</em>").replace(/新增/g,"<em>新增</em>")  
          }
          return str    
      },
      scollTo(ele){
           this.$emit('scoll',ele)
      },
    drawLine() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(
        document.getElementById("myChart"),
        "shine"
      );
      myChart.setOption(option);

      // // 绘制图表
      // myChart.setOption({
      //     title: { text: '企业对外投资行业前五分布图' },
      //     tooltip: {},
      //     xAxis: {
      //         data: ["衬衫衬衫衬衫","羊毛衫羊毛衫羊毛衫","雪纺衫","裤子","高跟鞋","袜子"],
      //         boundaryGap:true,
      //         axisLabel:{interval:0, rotate:20 },
      //     },
      //     yAxis: {},
      //     series: [{
      //         name: '销量',
      //         type: 'bar',
      //         data: [5, 20, 36, 10, 10, 20],
      //         color:"#67AEF5"
      //     }]
      // });
    },
    drawMap() {
      let myMap = this.$echarts.init(document.getElementById("map"), "shine");
      myMap.setOption({
        title: { text: "企业对外投资行业前五分布图" },
        tooltip: {
          trigger: "item"
        },
        toolbox: {
          show: true,
          orient: "vertical",
          x: "right",
          y: "center",
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        series: [
          {
            name: "投资比例",
            type: "pie",
            radius: "55%",
            roseType: "angle",
            data: [
              { value: 235, name: "视频广告" },
              { value: 274, name: "联盟广告" },
              { value: 310, name: "邮件营销" },
              { value: 335, name: "直接访问" },
              { value: 400, name: "搜索引擎" }
            ]
          }
        ]
      });
      // myMap.registerMap('HK', hkJson);
      // myMap.setOption(option = {
      //     title: {
      //         text: '香港18区人口密度 （2011）',
      //         subtext: '人口密度数据来自Wikipedia',
      //         sublink: 'http://zh.wikipedia.org/wiki/%E9%A6%99%E6%B8%AF%E8%A1%8C%E6%94%BF%E5%8D%80%E5%8A%83#cite_note-12'
      //     },
      //     tooltip: {
      //         trigger: 'item',
      //         formatter: '{b}<br/>{c} (p / km2)'
      //     },
      //     toolbox: {
      //         show: true,
      //         orient: 'vertical',
      //         left: 'right',
      //         top: 'center',
      //         feature: {
      //             dataView: {readOnly: false},
      //             restore: {},
      //             saveAsImage: {}
      //         }
      //     },
      //     visualMap: {
      //         min: 800,
      //         max: 50000,
      //         text:['High','Low'],
      //         realtime: false,
      //         calculable: true,
      //         inRange: {
      //             color: ['lightskyblue','yellow', 'orangered']
      //         }
      //     },
      //     series: [
      //         {
      //             name: '香港18区人口密度',
      //             type: 'map',
      //             mapType: 'HK', // 自定义扩展图表类型
      //             itemStyle:{
      //                 normal:{label:{show:true}},
      //                 emphasis:{label:{show:true}}
      //             },
      //             data:[
      //                 {name: '中西区', value: 20057.34},
      //                 {name: '湾仔', value: 15477.48},
      //                 {name: '东区', value: 31686.1},
      //                 {name: '南区', value: 6992.6},
      //                 {name: '油尖旺', value: 44045.49},
      //                 {name: '深水埗', value: 40689.64},
      //                 {name: '九龙城', value: 37659.78},
      //                 {name: '黄大仙', value: 45180.97},
      //                 {name: '观塘', value: 55204.26},
      //                 {name: '葵青', value: 21900.9},
      //                 {name: '荃湾', value: 4918.26},
      //                 {name: '屯门', value: 5881.84},
      //                 {name: '元朗', value: 4178.01},
      //                 {name: '北区', value: 2227.92},
      //                 {name: '大埔', value: 2180.98},
      //                 {name: '沙田', value: 9172.94},
      //                 {name: '西贡', value: 3368},
      //                 {name: '离岛', value: 806.98}
      //             ],
      //             // 自定义名称映射
      //             nameMap: {
      //                 'Central and Western': '中西区',
      //                 'Eastern': '东区',
      //                 'Islands': '离岛',
      //                 'Kowloon City': '九龙城',
      //                 'Kwai Tsing': '葵青',
      //                 'Kwun Tong': '观塘',
      //                 'North': '北区',
      //                 'Sai Kung': '西贡',
      //                 'Sha Tin': '沙田',
      //                 'Sham Shui Po': '深水埗',
      //                 'Southern': '南区',
      //                 'Tai Po': '大埔',
      //                 'Tsuen Wan': '荃湾',
      //                 'Tuen Mun': '屯门',
      //                 'Wan Chai': '湾仔',
      //                 'Wong Tai Sin': '黄大仙',
      //                 'Yau Tsim Mong': '油尖旺',
      //                 'Yuen Long': '元朗'
      //             }
      //         }
      //     ]
      // });
    }
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style >


</style>
