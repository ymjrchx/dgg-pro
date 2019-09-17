<template>
    <div class="tb_content">
        <section class="panel  clear m_dataTab">
            <div class="panel-body">

                <template v-if="msg.bzxr">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('zhixinglist')">
                        被执行人信息&nbsp;{{msg.bzxr.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        被执行人信息&nbsp;0
                    </a>
                </template>

                <template v-if="msg.sxbzxr">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('noticelist')">
                        失信被执行人&nbsp;{{msg.sxbzxr.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        失信被执行人&nbsp;0
                    </a>
                </template>

                <template v-if="msg.cpws">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('wenshulist')">
                        裁判文书&nbsp;{{msg.cpws.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        裁判文书&nbsp;0
                    </a>
                </template>

                <template v-if="msg.fygg">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('gonggaolist')">
                        法院公告&nbsp;{{msg.fygg.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        法院公告&nbsp;0
                    </a>
                </template>

                <template v-if="msg.ktgg">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('fynoticelist')">
                        开庭公告&nbsp;{{msg.ktgg.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        开庭公告&nbsp;0
                    </a>
                </template>

                <template v-if="msg.ssxz">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('ssxz')">
                        司法协助（无）&nbsp;{{msg.ssxz.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        司法协助（无）&nbsp;0
                    </a>
                </template>


                <!-- <a data-href="javascript:;" class="btn btn-sm btn-default  m-r-sm c_disable" style="white-space:nowrap;cursor: default">
                    被执行人信息&nbsp;{{msg.bzxr ? msg.bzxr.length : 0}}
                </a>
                <a data-href="javascript:;" class="btn btn-sm btn-default  m-r-sm c_disable" style="white-space:nowrap;cursor: default">
                    失信被执行人&nbsp;{{ msg.sxbzxr ? msg.sxbzxr.length : 0}}
                </a> -->
                <!-- <a data-href="javascript:;" class="btn btn-sm btn-default  m-r-sm" style="white-space:nowrap;">
                    裁判文书&nbsp;{{ msg.cpws ? msg.cpws.length : 0}}
                </a> -->
                <!-- <a data-href="javascript:;" class="btn btn-sm btn-default  m-r-sm" style="white-space:nowrap;">
                    法院公告&nbsp;{{ msg.fygg ? msg.fygg.length : 0}}
                </a> -->
                <!-- <a data-href="javascript:;" class="btn btn-sm btn-default  m-r-sm" style="white-space:nowrap;">
                    开庭公告&nbsp;{{ msg.ktgg ? msg.ktgg.length : 0}}
                </a> -->
                <!-- <a data-href="javascript:;" class="btn btn-sm btn-default  m-r-sm c_disable" style="white-space:nowrap;cursor: default">
                    司法协助（无）&nbsp;{{ msg.ssxz ? msg.cpws.length : 0}}
                </a> -->
            </div>
        </section>
        <!-- this.Law_info_msg =Object.assign({},{"cpws":params.cpws,'fygg':params.fygg,'ktgg':params.ktgg,'sxbzxr':params.sxbzxr}) ; -->
        <!-- 被执行人信息开始 -->
        <section class="panel  clear" id="zhixinglist" v-if="msg.bzxr">
            <div class="tcaption">
                <span class="title"> 被执行人信息</span>
                <span class="tbadger">{{msg.bzxr.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tr>
                    <th class="tx">序号</th>
                    <th>案号</th>
                    <th>立案时间</th>
                    <th>执行法院</th>
                    <th>执行标的</th>
                </tr>
                <tr v-for="(item,idx) in msg.bzxr" :key="idx">
                    <td class="tx">{{idx+1}}</td>
                    <td width="120">
                        <a class="text-primary" @click="test">{{item.no}}</a>
                    </td>
                    <td width="103">{{item.time}}</td>
                    <td width="130">{{item.court}}</td>
                    <td width="205">{{item.num}}</td>
                </tr>
            </table>
        </section>
        <!-- 被执行人信息结束 -->
        <!-- 失信被执行人开始 -->
        <section class="panel  clear" id="noticelist" v-if="msg.sxbzxr">
            <div class="tcaption">
                <span class="title"> 失信被执行人</span>
                <span class="tbadger">{{msg.sxbzxr.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tr>
                    <th class="tx">序号</th>
                    <th>案号</th>
                    <th>立案时间</th>
                    <th>执行法院</th>
                    <th>执行标的</th>
                </tr>
                <tr v-for="(item,idx) in msg.sxbzxr" :key="idx">
                    <td class="tx">1</td>
                    <td width="120">
                        <a class="text-primary" @click="test">(2018)浙01民终4356号</a>
                    </td>
                    <td width="103">2018-06-27 02:30:00</td>
                    <td width="130">网络服务合同纠纷</td>
                    <td width="205">邱庆亚 阿里巴巴(中国)网络技术有限公司</td>
                </tr>
            </table>
        </section>
        <!-- 失信被执行人结束 -->
        <!-- //裁判文书开始 -->
        <section class="panel clear  " id="wenshulist" v-if="msg.cpws">
            <div class="tcaption">
                <span class="title"> 裁判文书</span>
                <span class="tbadger">{{msg.cpws.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tr>
                    <th class="tx">序号</th>
                    <th>案件名称</th>
                    <th>发布时间</th>
                    <th>案件编号</th>
                    <th>案件身份</th>
                    <th>执行法院</th>
                </tr>
                <tr v-for="(item,idx) in msg.cpws" :key="idx">
                    <td class="tx">{{idx+1}}</td>
                    <td width="">
                        <a @click="test">{{item.caseName}}</a>
                    </td>
                    <td width="103">{{item.judgeDate}}</td>
                    <td width="15%">{{item.caseNo}}</td>
                    <td width="24%">
                        <!-- <div class="m-b-xs">原告 -
                          <a data-href="/search?key=%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4%EF%BC%88%E4%B8%AD%E5%9B%BD%EF%BC%89%E7%BD%91%E7%BB%9C%E6%8A%80%E6%9C%AF%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8"
                              class="c_a" target="_blank">阿里巴巴（中国）网络技术有限公司</a>
                      </div>
                      <div class="m-b-xs">代理律师事务所 -
                          <a data-href="/search?key=%E4%B8%8A%E6%B5%B7%E9%82%A6%E4%BF%A1%E9%98%B3%E4%B8%AD%E5%BB%BA%E4%B8%AD%E6%B1%87%EF%BC%88%E6%9D%AD%E5%B7%9E%EF%BC%89%E5%BE%8B%E5%B8%88%E4%BA%8B%E5%8A%A1%E6%89%80"
                              class="c_a" target="_blank">上海邦信阳中建中汇（杭州）律师事务所</a>
                      </div>
                      <div class="m-b-xs">被告 -
                          <a data-href="/search?key=%E4%B8%96%E7%BA%AA%E9%BE%99%E4%BF%A1%E6%81%AF%E7%BD%91%E7%BB%9C%E6%9C%89%E9%99%90%E8%B4%A3%E4%BB%BB%E5%85%AC%E5%8F%B8"
                              class="c_a" target="_blank">世纪龙信息网络有限责任公司</a>
                      </div> -->
                        {{item.ajsf}}
                    </td>
                    <td width="15%">{{item.court}}</td>
                </tr>
            </table>
            <!-- <div>
                <nav class="text-right">
                    亲，
                    <a data-toggle="modal" data-target="#loginModal" class="text-primary">登录</a>后可以查看更多数据哦
                </nav>
            </div> -->
        </section>
        <!-- //裁判文书结束 -->
        <!-- 法律公告开始 -->
        <section class="panel  clear" id="gonggaolist" v-if="msg.fygg">
            <div class="tcaption">
                <span class="title"> 法院公告</span>
                <span class="tbadger">{{msg.fygg.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tr>
                    <th class="tx">序号</th>
                    <th>当事人</th>
                    <th>公告类型</th>
                    <th>公告人</th>
                    <th>刊登时间</th>
                    <th>内容</th>
                </tr>
                <tr v-for="(item,index) in msg.fygg">
                    <td class="tx">{{index+1}}</td>
                    <td width="320">{{item.party}}</td>
                    <td class="text-center" width="15%">{{item.category}}</td>
                    <td class="text-center">{{item.court}}</td>
                    <td class="text-center" width="103">{{item.submitDate}}</td>
                    <td class="text-center" width="90">
                        <a data-toggle="modal" data-target="#gonggaoModal" class="c_a" @click="test">查看详情</a>
                    </td>
                </tr>
            </table>
            <!-- <div>
            <nav class="text-right">
                亲，
                <a data-toggle="modal" data-target="#loginModal" onclick="getCaptcha()" class="text-primary">登录</a>后可以查看更多数据哦
            </nav>
        </div> -->
        </section>
        <!-- 法律公告结束 -->
        <!-- 开庭公告 开始 -->
        <section class="panel  clear" id="fynoticelist" v-if="msg.ktgg">
            <div class="tcaption">
                <span class="title"> 开庭公告</span>
                <span class="tbadger">{{msg.ktgg.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tr>
                    <th class="tx">序号</th>
                    <th>案号</th>
                    <th>开庭日期</th>
                    <th>案由</th>
                    <th>公诉人/原告/上诉人/申请人</th>
                    <th>被告人/被告/被上诉人/被申请人</th>
                </tr>
                <tr v-for="(item,idx) in msg.ktgg" :key="idx">
                    <td class="tx">{{idx+1}}</td>
                    <td width="120">
                        <a class="text-primary" @click="test">{{item.case_No}}</a>
                    </td>
                    <td width="103">{{item.open_Time}}</td>
                    <td width="130">{{item.case_Reason}}</td>
                    <td width="205">{{item.prosecutor.name}}</td>
                    <td>{{item.defendant.name}}</td>
                </tr>
            </table>

        </section>
        <!-- 开庭公告 结束 -->
    </div>
</template>
<script>
import option from "@/util/map-option"; //引入配置项

import "echarts/map/js/china.js";
import "echarts/theme/shine.js";

export default {
    props: {
        msg: {}
    },
    data() {
        return {};
    },
    created() {
        //   console.log(2222,this.msg)
    },
    mounted() {},
    methods: {
        test(){
            this.$global.alertMessage()
      },
        scollTo(ele){
           this.$emit('scoll',ele)
        },
        drawLine() {
            // 基于准备好的dom，初始化echarts实例
            let myChart = echarts.init(
                document.getElementById("myChart"),
                "shine"
            );
            myChart.setOption(option);
        },
        drawMap() {
            let myMap = this.$echarts.init(document.getElementById("map"));
            myMap.setOption({
                title: { text: "企业对外投资行业前五分布图" },
                series: [
                    {
                        name: "企业对外投资行业前五分布图",
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
        }
    }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
