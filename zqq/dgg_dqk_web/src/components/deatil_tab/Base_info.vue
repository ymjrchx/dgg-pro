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
                <!-- <a  class="text-primary kz_anim  ripple"  target="_blank">
                    查看工商官网快照
                </a> -->
                <span class="watermark"></span>
            </div>
            <div class="prot-gltu" id="protGltu"></div>
            <table class="ntable" style="margin-bottom: -1px;">
                <tr>
                    <td width="20%" class="tb">注册资本：</td>
                    <td width="30%" class="">{{msg.registCapi ? msg.registCapi+"万人民币元" : "-" }}</td>
                    <td width="20%" class="tb">实缴资本：</td>
                    <td width="30%" class=""> {{msg.realCapi ? msg.realCapi+"万人民币元" : "-" }}</td>
                </tr>
                <tr>
                    <td class="tb">经营状态：</td>
                    <td class=""> {{isHas(msg.status)}} </td>
                    <td class="tb" width="18%">成立日期：</td>
                    <td class="">{{isHas(msg.startDate)}}</td>
                </tr>
                <tr>
                    <td class="tb">统一社会信用代码：</td>
                    <td class="">
                        {{isHas(msg.creditCode)}}
                    </td>
                    <td class="tb">纳税人识别号：</td>
                    <td class="">
                         {{isHas(msg.creditCode)}}
                    </td>
                </tr>
                <tr>
                    <td class="tb">注册号：</td>
                    <td class="">
                        {{isHas(msg.no)}}
                    </td>
                    <td class="tb" width="15%">组织机构代码：</td>
                    <td class="">
                        {{isHas(msg.orgNo)}}
                    </td>
                </tr>
                <tr>
                    <td class="tb">公司类型：</td>
                    <td class="">
                         {{isHas(msg.econKind)}}
                    </td>
                    <td class="tb">所属行业：</td>
                    <td class="">
                       {{isHas(msg.industry)}}
                    </td>
                </tr>
                <tr>
                    <td class="tb">核准日期：</td>
                    <td class="" style="max-width:301px;">
                         {{isHas(msg.checkDate)}}
                    </td>
                    <td class="tb">登记机关：</td>
                    <td class="">
                        {{isHas(msg.belongOrg)}}
                    </td>
                </tr>
                <tr>
                    <td class="tb">所属地区：</td>
                    <td class="" style="max-width:301px;">
                         {{isHas(msg.province)}}
                    </td>
                    <td class="tb">英文名：</td>
                    <td class="">
                         {{isHas(msg.englishName)}}
                    </td>
                </tr>
                <tr>
                    <td class="tb">
                        曾用名
                    </td>
                    <td class="">
                       {{isHas(msg.originalName)}}
                    </td>
                    <td class="tb">
                        参保人数
                    </td>
                    <td class="">
                         {{isHas(msg.cbPersonNum)}}
                    </td>
                </tr>
                <tr>
                    <td class="tb">
                        人员规模
                    </td>
                    <td class="">
                         {{isHas(msg.personNum)}}
                    </td>
                    <td class="tb">
                        营业期限
                    </td>
                    <td class="">
                        {{isHas(msg.teamEnd)}}
                    </td>
                </tr>
                <tr>
                    <td class="tb">企业地址：</td>
                    <td class="" colspan="3">
                       {{isHas(msg.address)}}
                        <!-- <a onclick="showMapModal('中山市横栏镇新茂工业区康龙三路32号4楼之一','')" class="m-l c_a"> 查看地图</a>
                        <a data-href="/map?keyNo=7e652fc34541b7cdf0b354e5a444b363" class="m-l c_a"> 附近公司</a> -->
                    </td>
                </tr>
                <tr>
                    <td class="tb">经营范围：</td>
                    <td class="" colspan="3">
                        {{isHas(msg.scope)}}
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
                <a  class="text-primary kz_anim ripple">股权结构图&疑似实际控制人</a>
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
                        <a>{{item.stockName}}</a>
                        <a class="btn-touzi">他关联1家公司 > </a>
                    </td>
                    <td class="text-center">{{item.stockPercent || "-"}} </td>
                    <td class="text-center">{{item.shouldCapi || "-"}}</td>
                    <td class="text-center">{{isHas(item.shoudDate)}}</td>
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
                        <a class="c_a" >{{item.name}}</a>
                        <a class="btn-touzi pull-right" >他关联 1 家公司 > </a>
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
                    <th class="" width="320">被投资企业名称</th>
                    <th class="" width="250">被投资法定代表人</th>
                    <th class="">注册资本(万人民币元)</th>
                    <th class="">出资比例</th>
                    <th class="">成立日期</th>
                    <th class="">状态</th>
                </tr>
                <tr v-for="(item,index) in msg.dwtz" :key='index'> 
                    <td class="tx"><a >{{item.name}}</a></td>
                    <td width="">
                        <a class="c_a" >{{item.operName}}</a>
                        <a class="btn-touzi pull-right">他关联 1 家公司 > </a>
                    </td>
                     <td class="text-center">
                        {{item.registCapi}}
                    </td>
                    <td class="text-center">
                        {{isHas(item.fundedRatio)}}
                    </td>
                     <td class="text-center">
                        {{isHas(item.startDate)}}
                    </td>
                     <td class="text-center">
                        {{isHas(item.status)}}
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
  },
  mounted() {
  },
  methods: {
      hasEm(str){
          if(str){
              return str.replace(/退出/g,"<em>退出</em>").replace(/新增/g,"<em>新增</em>")  
          }
          return str    
      },
      scollTo(ele){
           this.$emit('scoll',ele)
      },
      isHas(str){
          return str ? str == 'null' ? "--" : str : "--"
      }
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style >


</style>
