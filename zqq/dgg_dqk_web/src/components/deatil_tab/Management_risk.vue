<template>
    <div class="tb_content">
        <section class="panel  clear m_dataTab">
            <div class="panel-body" style="padding-top: 5px">
                <a  class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                    经营异常(无)&nbsp;0
                </a>

                <template v-if="msg.gqczs">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('pledgeList')">
                        股权出质&nbsp;{{msg.gqczs.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        股权出质&nbsp;0
                    </a>
                </template>

                <template v-if="msg.aas || msg.bbs">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('xzcf')">
                        行政处罚&nbsp;{{(msg.aas? msg.aas.length :0) + (msg.bbs? msg.bbs.length : 0) }}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        行政处罚&nbsp;0
                    </a>
                </template>
                <a  class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                    抽查检查(无)&nbsp;0
                </a>
                <a  class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                    税收违法(无)&nbsp;0
                </a>
                <a  class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                    动产抵押(无)&nbsp;0
                </a>
                <a  class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                    环保处罚(无)&nbsp;0
                </a>
                <a  class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                    清算信息(无)&nbsp;0
                </a>
                <a  class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                    司法拍卖(无)&nbsp;0
                </a>
                <a  class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                    土地抵押(无)&nbsp;0
                </a>
                <a  class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                    简易注销(无)
                </a>
                <a  class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                    公示催告(无)&nbsp;0
                </a>
            </div>
        </section>
        <!-- 股权出质 开始 -->
        <section class="panel clear  " id="pledgeList" v-if="msg.gqczs">
            <div class="tcaption">
                <span class="title"> 股权出质</span>
                <span class="tbadge">{{msg.gqczs.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tbody>
                    <tr>
                        <th class="tx">序号</th>
                        <th>登记编号</th>
                        <th width="140px;">出质人</th>
                        <th>质权人</th>
                        <th>出质股权数额</th>
                        <th>设立登记日期</th>
                        <th class="ma_center">状态</th>
                    </tr>
                    <tr v-for="(item,idx) in msg.gqczs" :key="idx">
                        <td class="tx">{{idx+1}}</td>
                        <td>
                            <a @click="test">{{item.djno}}</a>
                        </td>
                        <td>
                            <a  @click="test" class="c_a" title="洪锋">
                                {{item.czr}}
                            </a>
                        </td>
                        <td>
                            <a class="c_a"  @click="test" target="_blank"> {{item.zqr}} </a>
                        </td>
                        <td class="text-center">{{item.czgqse}}</td>
                        <td width="115" class="text-center">{{item.djrq}}</td>
                        <td class="text-center" width="60">{{item.status}}</td>
                    </tr>
                </tbody>
            </table>
        </section>
        <!-- 股权出质 结束 -->
        <!-- 行政处罚 [工商局] 开始 -->
        <section class="panel clear  " id="xzcf" v-if="msg.aas">
            <div class="tcaption">
                <span class="title"> 行政处罚 [工商局]</span>
                <span class="tbadger">{{msg.aas.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tbody>
                    <tr>
                        <th class="tx">序号</th>
                        <th>决定文书号</th>
                        <th>违法行为类型</th>
                        <th>行政处罚内容</th>
                        <th>公示日期</th>
                        <th>决定机关</th>
                        <th class="text-center">决定日期</th>
                    </tr>
                    <tr v-for="(item,index) in msg.aas" :key="index">
                        <td class="tx">{{index+1}}</td>
                        <td width="180">
                            {{item.wsh}}
                        </td>
                        <td class="text-center"> {{item.wfxwlx}}</td>
                        <td width="180">{{item.xzcflr}}</td>
                        <td width="103">{{item.gsdate}}</td>
                        <td width="120">
                            {{item.jdjg}}
                        </td>
                        <td width="103">
                            {{item.jddate}}
                        </td>
                    </tr>
                </tbody>
            </table>
        </section>
        <!-- 行政处罚 [工商局] 结束 -->
        <!-- 行政处罚 [信用中国] 开始   -->
        <section class="panel clear  " v-if="msg.bbs">
            <div class="tcaption">
                <span class="title"> 行政处罚 [信用中国]</span>
                <span class="tbadger">{{msg.bbs.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tbody>
                    <tr>
                        <th class="tx">序号</th>
                        <th>决定文书号</th>
                        <th>处罚名称</th>
                        <th width="10%">地域</th>
                        <th>决定时间</th>
                    </tr>
                    <tr v-for="(item,index) in msg.bbs">
                        <td class="tx">{{index+1}}</td>
                        <td>
                            <a  @click="test">{{item.wsh}}</a>
                        </td>
                        <td>{{item.name}}</td>
                        <td class="text-center"> {{item.area}}
                        </td>
                        <td width="103">
                            {{item.time}}
                        </td>
                    </tr>
                </tbody>
            </table>
        </section>
        <!-- 行政处罚 [信用中国] 结束 -->
        <!-- 抽查检查 开始 -->
        <section class="panel clear  " id="spotCheckList" v-if="msg.ccjc">
            <div class="tcaption">
                <span class="title"> 抽查检查</span>
                <span class="tbadger">3</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tbody>
                    <tr>
                        <th class="tx">序号</th>
                        <th>检查实施机关</th>
                        <th>类型</th>
                        <th>日期</th>
                        <th>结果</th>
                    </tr>
                    <tr> </tr>
                    <tr>
                        <td class="tx">1</td>
                        <td>-</td>
                        <td class="text-center">-</td>
                        <td width="103">2015-06-25</td>
                        <td class="text-center">-</td>
                    </tr>
                    <tr> </tr>
                    <tr>
                        <td class="tx">2</td>
                        <td>北京市工商行政管理局海淀分局</td>
                        <td class="text-center">-</td>
                        <td width="103"></td>
                        <td class="text-center">-</td>
                    </tr>
                    <tr> </tr>
                    <tr>
                        <td class="tx">3</td>
                        <td>-</td>
                        <td class="text-center">-</td>
                        <td width="103"></td>
                        <td class="text-center">正常</td>
                    </tr>
                </tbody>
            </table>
        </section>
        <!-- 抽查检查 结束 -->
    </div>
</template>
<script>
export default {
    props: {
        msg: {}
    },
    data() {
        return {};
    },
    created() {},
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
