<template>
  <div class="tb_content">
    <section class="panel pos-rlt   report_info">
        <div class="panel-heading b-b m-t-md">
            <ul class="nav nav-tabs" id="myTab">
                <li  v-for="(item,idx) in newArr" :key='idx'  :class="{'active':yearTab == idx}">
                    <a  @click="tab(idx)">
                        <span class=" font-15 text-dark"> {{item.year}}年度报告</span>
                    </a>
                </li>
            </ul>
        </div>
        <div class="panel-body tab-content">
            <transition-group name="el-fade-in">
                <div  v-for="(item,idx) in newArr" v-show="yearTab==idx" :key="idx">
                    <div class="text-gray m-b-sm m-t-sm">{{item.publishDate}}&nbsp;公布</div>
                    <div class="tcaption">
                        <span class="title">企业基本信息</span>
                        <span class="watermark"></span>
                    </div>
                    <table class="ntable">
                        <tbody>
                            <tr>
                                <td class="tb" width="20%">注册号</td>
                                <td width="30%">{{item.basicInfoData.regNo}}</td>
                                <td class="tb" width="20%">统一社会信用代码</td>
                                <td width="30%">{{commonCode}}</td>
                            </tr>
                            <tr>
                                <td class="tb" width="20%">企业经营状态</td>
                                <td width="30%">{{item.basicInfoData.status}}</td>
                                <td class="tb" width="20%">企业联系电话</td>
                                <td width="25%">{{item.basicInfoData.contactNo}}</td>
                            </tr>
                            <tr>
                                <td class="tb" width="20%">从业人数 </td>
                                <td width="25%">{{item.basicInfoData.employeeCount}}</td>
                                <td class="tb" width="20%">邮政编码</td>
                                <td width="25%">{{item.basicInfoData.postCode}}</td>
                            </tr>
                            <tr>
                                <td class="tb" width="20%">有限责任公司本年度是否发生股东股权转让</td>
                                <td>{{item.basicInfoData.isStockRightTransfer == "false" ? "否" : (item.basicInfoData.isStockRightTransfer == "true" ? "是" : item.basicInfoData.isStockRightTransfer )  }}</td>
                                <td class="tb" width="20%">企业是否有投资信息或购买其他公司股权</td>
                                <td colspan="3">{{item.basicInfoData.hasNewStockOrByStock == "false" ? "否" : (item.basicInfoData.hasNewStockOrByStock == "true" ? "是" : item.basicInfoData.hasNewStockOrByStock )  }}</td>
                            </tr>
                            <tr>
                                <td class="tb" width="20%">电子邮箱</td>
                                <td colspan="3">
                                    <a data-href="mailto:tangxian.gongtx@alibaba-inc.com" style="color:#555;">{{item.basicInfoData.emailAddress}}</a>
                                </td>
                            </tr>
                            <tr>
                                <td class="tb" width="20%">企业通讯地址</td>
                                <td colspan="3">
                                    <a>{{item.basicInfoData.address}}</a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div class="tcaption" v-if="item.webSite">
                        <span class="title">网站或网店信息</span>
                        <span class="watermark"></span>
                    </div>
                    <table class="ntable ntable-odd" v-if="item.webSite">
                        <tbody>
                            <tr>
                                <th class="tx">序号</th>
                                <th class="">类型</th>
                                <th class="">名称</th>
                                <th class="">网址</th>
                            </tr>
                            <tr>
                                <td class="tx">1</td>
                                <td class="text-center">无数据</td>
                                <td class="text-center">无数据</td>
                                <td>
                                    <a>网址看看</a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div class="tcaption" v-if="item.partner">
                        <span class="title">股东（发起人）出资信息</span>
                        <span class="watermark"></span>
                    </div>
                    <table class="ntable ntable-odd" v-if="item.partner">
                        <tbody>
                            <tr>
                                <th>序号</th>
                                <th>发起人</th>
                                <th>认缴出资额（万元）</th>
                                <th>认缴出资时间</th>
                                <th>认缴出资方式 </th>
                                <th>实缴出资额（万元）</th>
                                <th>实缴出资时间</th>
                                <th>实缴出资方式</th>
                            </tr>
                            <tr v-for="(item2,index) in item.partner" :key="index">
                                <td>{{index+1}}</td>
                                <td class="text-center">
                                    <a >{{item2.name}}</a>
                                </td>
                                <td class="text-center">{{item2.shouldCapi}}</td>
                                <td class="text-center">{{item2.shouldDate}}</td>
                                <td class="text-center">{{item2.shouldType}}</td>
                                <td class="text-center">{{item2.realCapi}}</td>
                                <td class="text-center">{{item2.realDate}}</td>
                                <td class="text-center">{{item2.realType}}</td>
                            </tr>
                        </tbody>
                    </table>
                    <div class="tcaption" v-if="item.investInfo">
                        <span class="title">对外投资信息</span>
                        <span class="watermark"></span>
                    </div>
                    <table class="ntable" v-if="item.investInfo">
                        <tbody>
                            <tr>
                                <td class="tx">序号</td>
                                <td class="tb" width="50%">投资设立企业或购买股权企业名称</td>
                                <td class="tb">注册号</td>
                            </tr>
                            <tr v-for="(item2,index) in item.investInfo" :key = "index">
                                <td class="tx">{{index+1}}</td>
                                <td>
                                    <a :title="item2.name">
                                        {{item2.name}}
                                    </a>
                                </td>
                                <td> {{item2.regNo}}</td>
                            </tr>
                        </tbody>
                    </table>
                    <div class="tcaption" v-if="item.assetsData">
                        <span class="title">企业资产状况信息</span>
                        <span class="watermark"></span>
                    </div>
                    <table class="ntable" v-if="item.assetsData">
                        <tbody>
                            <tr>
                                <td class="tb" width="22%">资产总额</td>
                                <td>{{item.assetsData.totalAssets}}</td>
                                <td class="tb" width="22%">所有者权益合计</td>
                                <td>{{item.assetsData.totalOwnersEquity}}</td>
                            </tr>
                            <tr>
                                <td class="tb">营业总收入 </td>
                                <td>{{item.assetsData.grossTradingIncome}}</td>
                                <td class="tb">利润总额 </td>
                                <td>{{item.assetsData.totalProfit}}</td>
                            </tr>
                            <tr>
                                <td class="tb">营业总收入中主营业务收入 </td>
                                <td>{{item.assetsData.mainBusinessIncome}}</td>
                                <td class="tb">净利润 </td>
                                <td>{{item.assetsData.netProfit}}</td>
                            </tr>
                            <tr>
                                <td class="tb">纳税总额 </td>
                                <td>{{item.assetsData.totalTaxAmount}}</td>
                                <td class="tb">负债总额 </td>
                                <td>{{item.assetsData.totalLiabilities}}</td>
                            </tr>
                            <tr>
                                <td class="tb">金融贷款 </td>
                                <td>{{item.assetsData.bankingCredit || "无"}}</td>
                                <td class="tb">获得政府扶持资金、补助 </td>
                                <td>{{item.assetsData.governmentSubsidy || "无"}}</td>
                            </tr>
                        </tbody>
                    </table>
                    <div class="tcaption">
                        <span class="title">社保信息</span>
                        <span class="watermark"></span>
                    </div>
                    <table class="ntable">
                        <tbody>
                            <tr>
                                <td class="tb" width="22%">城镇职工基本养老保险</td>
                                <td width="30%">企业选择不公示</td>
                                <td class="tb" width="20%">职工基本医疗保险</td>
                                <td width="30%">企业选择不公示</td>
                            </tr>
                            <tr>
                                <td class="tb" width="20%">生育保险</td>
                                <td width="25%">企业选择不公示</td>
                                <td class="tb" width="20%">失业保险</td>
                                <td width="30%">企业选择不公示</td>
                            </tr>
                            <tr>
                                <td class="tb" width="20%">工伤保险</td>
                                <td width="25%" colspan="3">3935人</td>
                            </tr>
                            <tr>
                                <td class="tb" width="20%" rowspan="5">单位缴纳基数</td>
                                <td width="30%">单位参加城镇职工基本养老保险缴费基数</td>
                                <td width="30%" colspan="2">企业选择不公示</td>
                            </tr>
                            <tr>
                                <td width="30%">单位参加失业保险缴费基数</td>
                                <td width="30%" colspan="2">企业选择不公示</td>
                            </tr>
                            <tr>
                                <td width="30%">单位参加职工基本医疗保险缴费基数</td>
                                <td width="30%" colspan="2">企业选择不公示</td>
                            </tr>
                            <tr>
                                <td width="30%">单位参加工伤保险缴费基数</td>
                                <td width="30%" colspan="2">-</td>
                            </tr>
                            <tr>
                                <td width="30%">单位参加生育保险缴费基数</td>
                                <td width="30%" colspan="2">企业选择不公示</td>
                            </tr>
                            <tr>
                                <td class="tb" width="20%" rowspan="5">本期实际缴费金额</td>
                                <td width="40%">参加城镇职工基本养老保险本期实际缴费金额</td>
                                <td width="30%" colspan="2">企业选择不公示</td>
                            </tr>
                            <tr>
                                <td width="30%">参加失业保险本期实际缴费金额</td>
                                <td width="30%" colspan="2">企业选择不公示</td>
                            </tr>
                            <tr>
                                <td width="30%">参加职工基本医疗保险本期实际缴费金额</td>
                                <td width="30%" colspan="2">企业选择不公示</td>
                            </tr>
                            <tr>
                                <td width="30%">参加工伤保险本期实际缴费金额</td>
                                <td width="30%" colspan="2">企业选择不公示</td>
                            </tr>
                            <tr>
                                <td width="30%">参加生育保险本期实际缴费金额</td>
                                <td width="30%" colspan="2">企业选择不公示</td>
                            </tr>
                            <tr>
                                <td class="tb" width="20%" rowspan="5">单位累计欠缴金额</td>
                                <td width="30%">单位参加城镇职工基本养老保险累计欠缴金额</td>
                                <td width="30%" colspan="2">企业选择不公示</td>
                            </tr>
                            <tr>
                                <td width="30%">单位参加失业保险累计欠缴金额</td>
                                <td width="30%" colspan="2">企业选择不公示</td>
                            </tr>
                            <tr>
                                <td width="30%">单位参加职工基本医疗保险累计欠缴金额</td>
                                <td width="30%" colspan="2">企业选择不公示</td>
                            </tr>
                            <tr>
                                <td width="30%">单位参加工伤保险累计欠缴金额</td>
                                <td width="30%" colspan="2">企业选择不公示</td>
                            </tr>
                            <tr>
                                <td width="30%">单位参加生育保险累计欠缴金额</td>
                                <td width="30%" colspan="2">企业选择不公示</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </transition-group>
        </div>
    </section>
  </div>
</template>
<script>
export default {
  name: "head1",
  props: {
    msg: "",
    yearTab:0
  },
  data() {
    return {
      newArr:[],
      commonCode:""
    };
  },
  created() {
      if(this.msg.nbArr){
        this.commonCode=this.msg.commonCode
        this.newArr=this.msg.nbArr.reverse();
      }
  },
  mounted(){
  
  },
  methods: {
    tab(num){
      this.$emit('onlyYear',num)
    }
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* //新增的css、 */
#myTab>li {
    position: relative;
    display: inline-block;
}

#myTab{
    border-bottom: 1px solid #eaeef1;
}
#myTab li a {
    padding: 10px 15px;
    padding-bottom: 15px;
    font-weight: 600;
    border-radius: 2px 2px 0 0;
    margin: 0;
    line-height: 1.42857143;
    border: 1px solid transparent;
    display: block;
    border-bottom: 1px solid #eaeef1;
    position: relative;
    top: 1px;
    background: #fff;
}
#myTab li.active a{ 
    border: 1px solid #eaeef1;
    border-bottom:1px solid #fff;
}

#loadReport {
    text-align: center;
    margin-top: 100px;
    display: none;
}

/*v3*/

#myTab li a:hover span {
    color: #128BED;
}

#myTab li.active a span {
    color: #128BED;
}
  

</style>
