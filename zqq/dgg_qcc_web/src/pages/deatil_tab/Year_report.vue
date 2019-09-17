<template>
  <div class="tb_content">
    <section class="panel pos-rlt   report_info">
        <div class="panel-heading b-b m-t-md">
            <ul class="nav nav-tabs" id="myTab">
                <li  v-for="(item,idx) in newArr"  :class="{'active':yearTab == idx}">
                    <a  @click="tab(idx)">
                        <span class=" font-15 text-dark"> {{item.year}}年度报告</span>
                    </a>
                </li>
                <!-- <li :class="{'active':isTab == 1}">
                     <a onclick="javascript:void(0)"  @click="tab(1)">
                        <span class=" font-15 text-dark"> 2016年度报告</span>
                    </a>
                </li>
                 <li :class="{'active':isTab == 2}">
                     <a onclick="javascript:void(0)"  @click="tab(2)">
                        <span class=" font-15 text-dark"> 2015年度报告</span>
                    </a>
                </li>
                  <li :class="{'active':isTab == 3}">
                     <a onclick="javascript:void(0)"  @click="tab(3)">
                        <span class=" font-15 text-dark"> 2014年度报告</span>
                    </a>
                </li>
                  <li :class="{'active':isTab == 4}">
                     <a onclick="javascript:void(0)"  @click="tab(4)">
                        <span class=" font-15 text-dark"> 2013年度报告</span>
                    </a>
                </li> -->
            </ul>
        </div>
        <div class="panel-body tab-content">
            <div  v-for="(item,idx) in newArr" :class="{'tab-pane':true,'fade':true,'in':yearTab==idx ,'active':yearTab==idx}" :key="idx">
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
                                <a data-href="mailto:tangxian.gongtx@alibaba-inc.com" style="color:#555;" @click="test">{{item.basicInfoData.emailAddress}}</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">企业通讯地址</td>
                            <td colspan="3">
                                <a @click="test">{{item.basicInfoData.address}}</a>
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
                                <a @click="test">网址看看</a>
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
                                <a @click="test" target="_blank">{{item2.name}}</a>
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
                                <a :title="item2.name" @click="test">
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
            <!-- <div :class="{'tab-pane':true,'fade':true,'in':true, 'active':isTab==1}" id="1">
                <div class="text-gray m-b-sm m-t-sm">2017-06-01&nbsp;公布</div>
                <div class="tcaption">
                    <span class="title">企业基本信息</span>
                    <span class="watermark"></span>
                </div>
                <table class="ntable">
                    <tbody>
                        <tr>
                            <td class="tb" width="20%">注册号</td>
                            <td width="30%">330100400015575</td>
                            <td class="tb" width="20%">统一社会信用代码</td>
                            <td width="30%">91330100716105852F</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">企业经营状态</td>
                            <td width="30%">正常开业</td>
                            <td class="tb" width="20%">企业联系电话</td>
                            <td width="25%">0571-85022088</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">从业人数 </td>
                            <td width="25%">企业选择不公示</td>
                            <td class="tb" width="20%">邮政编码</td>
                            <td width="25%">310012</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">有限责任公司本年度是否发生股东股权转让</td>
                            <td>否</td>
                            <td class="tb" width="20%">企业是否有投资信息或购买其他公司股权</td>
                            <td colspan="3">是</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">电子邮箱</td>
                            <td colspan="3">
                                <a data-href="mailto:sophy.ly@alibaba-inc.com" style="color:#555;">sophy.ly@alibaba-inc.com</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">企业通讯地址</td>
                            <td colspan="3">
                                <a onclick="showMapModal('浙江省杭州市滨江区长河街道网商路699号','')">浙江省杭州市滨江区长河街道网商路699号</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="tcaption">
                    <span class="title">网站或网店信息</span>
                    <span class="watermark"></span>
                </div>
                <table class="ntable ntable-odd">
                    <tbody>
                        <tr>
                            <th class="tx">序号</th>
                            <th class="">类型</th>
                            <th class="">名称</th>
                            <th class="">网址</th>
                        </tr>
                        <tr>
                            <td class="tx">1</td>
                            <td class="text-center">网站</td>
                            <td class="text-center">华东活动管理系统</td>
                            <td>
                                <a data-href="http://http://www.alibaba-cneast.com/" target="_blank" rel="nofollow">http://www.alibaba-cneast.com/</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tx">2</td>
                            <td class="text-center">网站</td>
                            <td class="text-center">静态资源服务管理系统</td>
                            <td>
                                <a data-href="http://http://www.aliunicorn.com" target="_blank" rel="nofollow">http://www.aliunicorn.com</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tx">3</td>
                            <td class="text-center">网站</td>
                            <td class="text-center">阿里客邮箱账号PC登陆页</td>
                            <td>
                                <a data-href="http://http://www.alimails.com/" target="_blank" rel="nofollow">http://www.alimails.com/</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tx">4</td>
                            <td class="text-center">网站</td>
                            <td class="text-center">阿里巴巴B2B开源网站</td>
                            <td>
                                <a data-href="http://http://www.alibabatech.com" target="_blank" rel="nofollow">http://www.alibabatech.com</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tx">5</td>
                            <td class="text-center">网站</td>
                            <td class="text-center">阿里巴巴网站静态文件输出系统</td>
                            <td>
                                <a data-href="http://http://www.aliimg.com" target="_blank" rel="nofollow">http://www.aliimg.com</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tx">6</td>
                            <td class="text-center">网站</td>
                            <td class="text-center">外贸圈</td>
                            <td>
                                <a data-href="http://https://waimaoquan.com/" target="_blank" rel="nofollow">https://waimaoquan.com/</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tx">7</td>
                            <td class="text-center">网站</td>
                            <td class="text-center">阿里巴巴国际站App下载短连接</td>
                            <td>
                                <a data-href="http://http://www.aliapp.co/" target="_blank" rel="nofollow">http://www.aliapp.co/</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tx">8</td>
                            <td class="text-center">网站</td>
                            <td class="text-center">旺铺装修平台wiki</td>
                            <td>
                                <a data-href="http://http://www.alibaba.org/" target="_blank" rel="nofollow">http://www.alibaba.org/</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="tcaption">
                    <span class="title">股东（发起人）出资信息</span>
                    <span class="watermark"></span>
                </div>
                <table class="ntable ntable-odd">
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
                        <tr>
                            <td>1</td>
                            <td class="text-center">
                                <a data-href="/firm_hb3bb4f2f8e434f4400f1a355fb384db.html" target="_blank">Alibaba.com China Limited</a>
                            </td>
                            <td class="text-center">57690</td>
                            <td class="text-center">2016-12-31</td>
                            <td class="text-center">货币</td>
                            <td class="text-center">57690</td>
                            <td class="text-center">2016-12-15</td>
                            <td class="text-center">货币</td>
                        </tr>
                    </tbody>
                </table>
                <div class="tcaption">
                    <span class="title">对外投资信息</span>
                    <span class="watermark"></span>
                </div>
                <table class="ntable">
                    <tbody>
                        <tr>
                            <td class="tx">序号</td>
                            <td class="tb" width="50%">投资设立企业或购买股权企业名称</td>
                            <td class="tb">注册号</td>
                        </tr>
                        <tr>
                            <td class="tx">1</td>
                            <td>
                                <a data-href="/firm_19e555318867c1906810c85892b693a7.html" target="_blank" title="北京缔元信互联网数据技术有限公司">
                                    北京缔元信互联网数据技术有限公司
                                </a>
                            </td>
                            <td>91110101799011412Q</td>
                        </tr>
                        <tr>
                            <td class="tx">2</td>
                            <td>
                                <a data-href="/firm_239eb8fb069f308e403e18d9a7af4cf7.html" target="_blank" title="上海驻云信息科技有限公司">
                                    上海驻云信息科技有限公司
                                </a>
                            </td>
                            <td>913100000747549729</td>
                        </tr>
                        <tr>
                            <td class="tx">3</td>
                            <td>
                                <a data-href="/firm_714bd59ffc7e92427bce708b54e7f14a.html" target="_blank" title="北京接我科技有限公司">
                                    北京接我科技有限公司
                                </a>
                            </td>
                            <td>91110108089611245W</td>
                        </tr>
                        <tr>
                            <td class="tx">4</td>
                            <td>
                                <a data-href="/firm_79ad09c2c1230484a98aa5a48435b4fb.html" target="_blank" title="上海幻橙网络科技有限公司">
                                    上海幻橙网络科技有限公司
                                </a>
                            </td>
                            <td>91310105563089520E</td>
                        </tr>
                        <tr>
                            <td class="tx">5</td>
                            <td>
                                <a data-href="/firm_8668ef3688bfb202028d8b8d7b23bfba.html" target="_blank" title="厦门又一城软件科技有限公司">
                                    厦门又一城软件科技有限公司
                                </a>
                            </td>
                            <td>350203200212475</td>
                        </tr>
                        <tr>
                            <td class="tx">6</td>
                            <td>
                                <a data-href="/firm_43287b9d5c8e51756cd2dfc08c64758f.html" target="_blank" title="北京摸摸宠儿科技有限公司">
                                    北京摸摸宠儿科技有限公司
                                </a>
                            </td>
                            <td>91110105071714684X</td>
                        </tr>
                        <tr>
                            <td class="tx">7</td>
                            <td>
                                <a data-href="/firm_3e755b49933ea5be741f8bcbbebd9008.html" target="_blank" title="堆糖信息科技（上海）有限公司">
                                    堆糖信息科技（上海）有限公司
                                </a>
                            </td>
                            <td>310115001831935</td>
                        </tr>
                        <tr>
                            <td class="tx">8</td>
                            <td>
                                <a data-href="/firm_486d704b82095f3b1b751c5e0c65d656.html" target="_blank" title="杭州如涵控股股份有限公司">
                                    杭州如涵控股股份有限公司
                                </a>
                            </td>
                            <td>9133010072654578XU</td>
                        </tr>
                        <tr>
                            <td class="tx">9</td>
                            <td>
                                <a data-href="/firm_15d2b7f7ae7e1ee05a0a790e989e53a8.html" target="_blank" title="上海庆科信息技术有限公司">
                                    上海庆科信息技术有限公司
                                </a>
                            </td>
                            <td>913101075500969161</td>
                        </tr>
                        <tr>
                            <td class="tx">10</td>
                            <td>
                                <a data-href="/search?key=浙江五一技术股份有限公司#index:2&amp;" target="_blank" title="浙江五一技术股份有限公司">
                                    浙江五一技术股份有限公司
                                </a>
                            </td>
                            <td>330000000074954</td>
                        </tr>
                        <tr>
                            <td class="tx">11</td>
                            <td>
                                <a data-href="/firm_06f76ae987abbb6f927f8568596d8477.html" target="_blank" title="微鲸科技有限公司">
                                    微鲸科技有限公司
                                </a>
                            </td>
                            <td>9131010433262010XX</td>
                        </tr>
                        <tr>
                            <td class="tx">12</td>
                            <td>
                                <a data-href="/firm_79572f4829e1464aa4fd5870ae8370aa.html" target="_blank" title="深圳市一达通企业服务有限公司">
                                    深圳市一达通企业服务有限公司
                                </a>
                            </td>
                            <td>91440300734141160H</td>
                        </tr>
                        <tr>
                            <td class="tx">13</td>
                            <td>
                                <a data-href="/firm_ad6066c22b1e6d47cd026910c84143c7.html" target="_blank" title="上海多维度网络科技股份有限公司">
                                    上海多维度网络科技股份有限公司
                                </a>
                            </td>
                            <td>91310000599767485G</td>
                        </tr>
                        <tr>
                            <td class="tx">14</td>
                            <td>
                                <a data-href="/firm_55886944a9f99bbc3da1286ad860cb97.html" target="_blank" title="永杨安风（北京）科技有限公司">
                                    永杨安风（北京）科技有限公司
                                </a>
                            </td>
                            <td>110105014108491</td>
                        </tr>
                        <tr>
                            <td class="tx">15</td>
                            <td>
                                <a data-href="/firm_97334afe301cbc9329f37e48b2fc0f64.html" target="_blank" title="重庆猫宁电子商务有限公司">
                                    重庆猫宁电子商务有限公司
                                </a>
                            </td>
                            <td>91500000MA5U8L3U94</td>
                        </tr>
                        <tr>
                            <td class="tx">16</td>
                            <td>
                                <a data-href="/firm_bbfbb98d148d51fcfa06822cf3ff3fa5.html" target="_blank" title="广州恒大淘宝足球俱乐部有限公司">
                                    广州恒大淘宝足球俱乐部有限公司
                                </a>
                            </td>
                            <td>91440101783794467C</td>
                        </tr>
                        <tr>
                            <td class="tx">17</td>
                            <td>
                                <a data-href="/firm_083bd6188d6531369546438eb6451858.html" target="_blank" title="径圆（上海）信息技术有限公司">
                                    径圆（上海）信息技术有限公司
                                </a>
                            </td>
                            <td>913101155931754979</td>
                        </tr>
                        <tr>
                            <td class="tx">18</td>
                            <td>
                                <a data-href="/firm_ad0e79e0f7dcb369830f80ae78d28e87.html" target="_blank" title="广东心怡科技物流有限公司">
                                    广东心怡科技物流有限公司
                                </a>
                            </td>
                            <td>440000000043734</td>
                        </tr>
                        <tr>
                            <td class="tx">19</td>
                            <td>
                                <a data-href="/firm_c6f3c7fe43a2d4948a754a90d18c8deb.html" target="_blank" title="南京晟邦物流有限公司">
                                    南京晟邦物流有限公司
                                </a>
                            </td>
                            <td>320106000148572</td>
                        </tr>
                        <tr>
                            <td class="tx">20</td>
                            <td>
                                <a data-href="/firm_c211cfb26d6fdcbc7b3e7138e029483f.html" target="_blank" title="遛友宠物信息科技（上海）有限公司">
                                    遛友宠物信息科技（上海）有限公司
                                </a>
                            </td>
                            <td>310115002423157</td>
                        </tr>
                        <tr>
                            <td class="tx">21</td>
                            <td>
                                <a data-href="/firm_7486b14b8ff4dfc5905e934def4153ee.html" target="_blank" title="北京码上游科技服务有限公司">
                                    北京码上游科技服务有限公司
                                </a>
                            </td>
                            <td>91110113576948910Y</td>
                        </tr>
                        <tr>
                            <td class="tx">22</td>
                            <td>
                                <a data-href="/firm_67b615edb270fc5ee7442a20a25ed89a.html" target="_blank" title="北京星宝时尚科技有限公司">
                                    北京星宝时尚科技有限公司
                                </a>
                            </td>
                            <td>110105015358929</td>
                        </tr>
                        <tr>
                            <td class="tx">23</td>
                            <td>
                                <a data-href="/firm_13de6978d7079ff5471ebe2602bbd8dc.html" target="_blank" title="墨迹风云（北京）软件科技发展有限公司">
                                    墨迹风云（北京）软件科技发展有限公司
                                </a>
                            </td>
                            <td>110108012698483</td>
                        </tr>
                        <tr>
                            <td class="tx">24</td>
                            <td>
                                <a data-href="/firm_754acd29b416390928b4dccae8b6cc47.html" target="_blank" title="上海易果电子商务有限公司">
                                    上海易果电子商务有限公司
                                </a>
                            </td>
                            <td>310105000320742</td>
                        </tr>
                        <tr>
                            <td class="tx">25</td>
                            <td>
                                <a data-href="/firm_3adac8c0b3859baebd6be029f7a69a67.html" target="_blank" title="杭州阿里巴巴泽泰信息技术有限公司">
                                    杭州阿里巴巴泽泰信息技术有限公司
                                </a>
                            </td>
                            <td>91330110MA28054B7Q</td>
                        </tr>
                        <tr>
                            <td class="tx">26</td>
                            <td>
                                <a data-href="/firm_a236d9d2bca4f19b08dabd8369096d61.html" target="_blank" title="杭州数梦工场科技有限公司">
                                    杭州数梦工场科技有限公司
                                </a>
                            </td>
                            <td>91330106328122968A</td>
                        </tr>
                        <tr>
                            <td class="tx">27</td>
                            <td>
                                <a data-href="/firm_6d266b0db5f8e8957c460929064c4a81.html" target="_blank" title="友盟同欣（北京）科技有限公司">
                                    友盟同欣（北京）科技有限公司
                                </a>
                            </td>
                            <td>91110108575155222E</td>
                        </tr>
                        <tr>
                            <td class="tx">28</td>
                            <td>
                                <a data-href="/firm_d36ccea98681988ffd5627c0c4daa8aa.html" target="_blank" title="厦门享动网络科技有限公司">
                                    厦门享动网络科技有限公司
                                </a>
                            </td>
                            <td>91350200MA344F7G4N</td>
                        </tr>
                        <tr>
                            <td class="tx">29</td>
                            <td>
                                <a data-href="/firm_459a9bc416b65887ae9a520183e43594.html" target="_blank" title="杭州魅投信息技术有限公司">
                                    杭州魅投信息技术有限公司
                                </a>
                            </td>
                            <td>91330108MA27WRWT68</td>
                        </tr>
                        <tr>
                            <td class="tx">30</td>
                            <td>
                                <a data-href="/firm_c9824aab8139bcf6784cd15060b4361e.html" target="_blank" title="百越文化创意有限公司">
                                    百越文化创意有限公司
                                </a>
                            </td>
                            <td>91330100MA27X12D97</td>
                        </tr>
                        <tr>
                            <td class="tx">31</td>
                            <td>
                                <a data-href="/firm_b039cb6fd974d1fde268f7ae87385bb6.html" target="_blank" title="上海智篆文化传播有限公司">
                                    上海智篆文化传播有限公司
                                </a>
                            </td>
                            <td>310114002401163</td>
                        </tr>
                        <tr>
                            <td class="tx">32</td>
                            <td>
                                <a data-href="/firm_b780537a41377c304bbdb08238876862.html" target="_blank" title="武汉元光科技有限公司">
                                    武汉元光科技有限公司
                                </a>
                            </td>
                            <td>914201006983443814</td>
                        </tr>
                        <tr>
                            <td class="tx">33</td>
                            <td>
                                <a data-href="/firm_e78154a2c2d1cf032241c5a7d237470f.html" target="_blank" title="北京雅观科技有限公司">
                                    北京雅观科技有限公司
                                </a>
                            </td>
                            <td>110000450170864</td>
                        </tr>
                        <tr>
                            <td class="tx">34</td>
                            <td>
                                <a data-href="/firm_9d9ac71c465d8bc3bda17db27f5ec8bd.html" target="_blank" title="杭州源牛电子商务有限公司">
                                    杭州源牛电子商务有限公司
                                </a>
                            </td>
                            <td>91330108MA27YKK98X</td>
                        </tr>
                        <tr>
                            <td class="tx">35</td>
                            <td>
                                <a data-href="/firm_db0f163de0a2d2996d16ee1faebee965.html" target="_blank" title="北京百舜华年文化传播有限公司">
                                    北京百舜华年文化传播有限公司
                                </a>
                            </td>
                            <td>110107011106880</td>
                        </tr>
                        <tr>
                            <td class="tx">36</td>
                            <td>
                                <a data-href="/firm_2e6c498caee06e7d94c0dad66de0e8e6.html" target="_blank" title="苏州思必驰信息科技有限公司">
                                    苏州思必驰信息科技有限公司
                                </a>
                            </td>
                            <td>91320594668384120B</td>
                        </tr>
                        <tr>
                            <td class="tx">37</td>
                            <td>
                                <a data-href="/firm_b1a8325e7fa879cc8ac472e9cbb140e3.html" target="_blank" title="深圳市瑞云科技有限公司">
                                    深圳市瑞云科技有限公司
                                </a>
                            </td>
                            <td>91440300761988340L</td>
                        </tr>
                    </tbody>
                </table>
                <div class="tcaption">
                    <span class="title">企业资产状况信息</span>
                    <span class="watermark"></span>
                </div>
                <table class="ntable">
                    <tbody>
                        <tr>
                            <td class="tb" width="20%">资产总额</td>
                            <td>企业选择不公示</td>
                            <td class="tb" width="20%">所有者权益合计</td>
                            <td>企业选择不公示</td>
                        </tr>
                        <tr>
                            <td class="tb">营业总收入 </td>
                            <td>企业选择不公示</td>
                            <td class="tb">利润总额 </td>
                            <td>企业选择不公示</td>
                        </tr>
                        <tr>
                            <td class="tb">营业总收入中主营业务收入 </td>
                            <td>企业选择不公示</td>
                            <td class="tb">净利润 </td>
                            <td>企业选择不公示</td>
                        </tr>
                        <tr>
                            <td class="tb">纳税总额 </td>
                            <td>企业选择不公示</td>
                            <td class="tb">负债总额 </td>
                            <td>企业选择不公示</td>
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
                            <td width="30%">6636人</td>
                            <td class="tb" width="20%">职工基本医疗保险</td>
                            <td width="30%">6636人</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">生育保险</td>
                            <td width="25%">6636人</td>
                            <td class="tb" width="20%">失业保险</td>
                            <td width="30%">6636人</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">工伤保险</td>
                            <td width="25%" colspan="3">6636人</td>
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
            <div :class="{'tab-pane':true,'fade':true, 'in':true, 'active':isTab==2}" id="2">
                <div class="text-gray m-b-sm m-t-sm">2016-06-14&nbsp;公布</div>
                <div class="tcaption">
                    <span class="title">企业基本信息</span>
                    <span class="watermark"></span>
                </div>
                <table class="ntable">
                    <tbody>
                        <tr>
                            <td class="tb" width="20%">注册号</td>
                            <td width="30%">330100400015575</td>
                            <td class="tb" width="20%">统一社会信用代码</td>
                            <td width="30%">-</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">企业经营状态</td>
                            <td width="30%">开业</td>
                            <td class="tb" width="20%">企业联系电话</td>
                            <td width="25%">0571-85022088</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">从业人数 </td>
                            <td width="25%">企业选择不公示</td>
                            <td class="tb" width="20%">邮政编码</td>
                            <td width="25%">310012</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">有限责任公司本年度是否发生股东股权转让</td>
                            <td>否</td>
                            <td class="tb" width="20%">企业是否有投资信息或购买其他公司股权</td>
                            <td colspan="3">是</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">电子邮箱</td>
                            <td colspan="3">
                                <a data-href="mailto:sophy.ly@alibaba-inc.com" style="color:#555;">sophy.ly@alibaba-inc.com</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">企业通讯地址</td>
                            <td colspan="3">
                                <a onclick="showMapModal('杭州市滨江区网商路699号','')">杭州市滨江区网商路699号</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="tcaption">
                    <span class="title">股东（发起人）出资信息</span>
                    <span class="watermark"></span>
                </div>
                <table class="ntable ntable-odd">
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
                        <tr>
                            <td>1</td>
                            <td class="text-center">
                                <a data-href="/firm_hb3bb4f2f8e434f4400f1a355fb384db.html" target="_blank">Alibaba.com China Limited</a>
                            </td>
                            <td class="text-center">354338.68</td>
                            <td class="text-center">2016-02-29</td>
                            <td class="text-center">货币</td>
                            <td class="text-center">354338.68</td>
                            <td class="text-center">2015-12-31</td>
                            <td class="text-center">货币</td>
                        </tr>
                    </tbody>
                </table>
                <div class="tcaption">
                    <span class="title">对外投资信息</span>
                    <span class="watermark"></span>
                </div>
                <table class="ntable">
                    <tbody>
                        <tr>
                            <td class="tx">序号</td>
                            <td class="tb" width="50%">投资设立企业或购买股权企业名称</td>
                            <td class="tb">注册号</td>
                        </tr>
                        <tr>
                            <td class="tx">1</td>
                            <td>
                                <a data-href="/firm_db0f163de0a2d2996d16ee1faebee965.html" target="_blank" title="北京百舜华年文化传播有限公司">
                                    北京百舜华年文化传播有限公司
                                </a>
                            </td>
                            <td>110107011106880</td>
                        </tr>
                        <tr>
                            <td class="tx">2</td>
                            <td>
                                <a data-href="/firm_79572f4829e1464aa4fd5870ae8370aa.html" target="_blank" title="深圳市一达通企业服务有限公司">
                                    深圳市一达通企业服务有限公司
                                </a>
                            </td>
                            <td>440301103275393</td>
                        </tr>
                        <tr>
                            <td class="tx">3</td>
                            <td>
                                <a data-href="/firm_083bd6188d6531369546438eb6451858.html" target="_blank" title="径圆（上海）信息技术有限公司">
                                    径圆（上海）信息技术有限公司
                                </a>
                            </td>
                            <td>913101155931754979</td>
                        </tr>
                        <tr>
                            <td class="tx">4</td>
                            <td>
                                <a data-href="/firm_b780537a41377c304bbdb08238876862.html" target="_blank" title="武汉元光科技有限公司">
                                    武汉元光科技有限公司
                                </a>
                            </td>
                            <td>914201006983443814</td>
                        </tr>
                        <tr>
                            <td class="tx">5</td>
                            <td>
                                <a data-href="/firm_714bd59ffc7e92427bce708b54e7f14a.html" target="_blank" title="北京接我科技有限公司">
                                    北京接我科技有限公司
                                </a>
                            </td>
                            <td>91110108089611245W</td>
                        </tr>
                        <tr>
                            <td class="tx">6</td>
                            <td>
                                <a data-href="/firm_754acd29b416390928b4dccae8b6cc47.html" target="_blank" title="上海易果电子商务有限公司">
                                    上海易果电子商务有限公司
                                </a>
                            </td>
                            <td>310105000320742</td>
                        </tr>
                        <tr>
                            <td class="tx">7</td>
                            <td>
                                <a data-href="/firm_c6f3c7fe43a2d4948a754a90d18c8deb.html" target="_blank" title="南京晟邦物流有限公司">
                                    南京晟邦物流有限公司
                                </a>
                            </td>
                            <td>320106000148572</td>
                        </tr>
                        <tr>
                            <td class="tx">8</td>
                            <td>
                                <a data-href="/firm_3e755b49933ea5be741f8bcbbebd9008.html" target="_blank" title="堆糖信息科技（上海）有限公司">
                                    堆糖信息科技（上海）有限公司
                                </a>
                            </td>
                            <td>310115001831935</td>
                        </tr>
                        <tr>
                            <td class="tx">9</td>
                            <td>
                                <a data-href="/firm_ad6066c22b1e6d47cd026910c84143c7.html" target="_blank" title="上海多维度网络科技股份有限公司">
                                    上海多维度网络科技股份有限公司
                                </a>
                            </td>
                            <td>91310000599767485G</td>
                        </tr>
                        <tr>
                            <td class="tx">10</td>
                            <td>
                                <a data-href="/firm_c211cfb26d6fdcbc7b3e7138e029483f.html" target="_blank" title="遛友宠物信息科技（上海）有限公司">
                                    遛友宠物信息科技（上海）有限公司
                                </a>
                            </td>
                            <td>310115002423157</td>
                        </tr>
                        <tr>
                            <td class="tx">11</td>
                            <td>
                                <a data-href="/firm_55886944a9f99bbc3da1286ad860cb97.html" target="_blank" title="永杨安风（北京）科技有限公司">
                                    永杨安风（北京）科技有限公司
                                </a>
                            </td>
                            <td>110105014108491</td>
                        </tr>
                        <tr>
                            <td class="tx">12</td>
                            <td>
                                <a data-href="/firm_a236d9d2bca4f19b08dabd8369096d61.html" target="_blank" title="杭州数梦工场科技有限公司">
                                    杭州数梦工场科技有限公司
                                </a>
                            </td>
                            <td>91330106328122968A</td>
                        </tr>
                        <tr>
                            <td class="tx">13</td>
                            <td>
                                <a data-href="/firm_2e6c498caee06e7d94c0dad66de0e8e6.html" target="_blank" title="苏州思必驰信息科技有限公司">
                                    苏州思必驰信息科技有限公司
                                </a>
                            </td>
                            <td>91320594668384120B</td>
                        </tr>
                        <tr>
                            <td class="tx">14</td>
                            <td>
                                <a data-href="/firm_e78154a2c2d1cf032241c5a7d237470f.html" target="_blank" title="北京雅观科技有限公司">
                                    北京雅观科技有限公司
                                </a>
                            </td>
                            <td>110000450170864</td>
                        </tr>
                        <tr>
                            <td class="tx">15</td>
                            <td>
                                <a data-href="/firm_15d2b7f7ae7e1ee05a0a790e989e53a8.html" target="_blank" title="上海庆科信息技术有限公司">
                                    上海庆科信息技术有限公司
                                </a>
                            </td>
                            <td>310107000581907</td>
                        </tr>
                        <tr>
                            <td class="tx">16</td>
                            <td>
                                <a data-href="/firm_bbfbb98d148d51fcfa06822cf3ff3fa5.html" target="_blank" title="广州恒大淘宝足球俱乐部有限公司">
                                    广州恒大淘宝足球俱乐部有限公司
                                </a>
                            </td>
                            <td>440101000059092</td>
                        </tr>
                        <tr>
                            <td class="tx">17</td>
                            <td>
                                <a data-href="/firm_6d266b0db5f8e8957c460929064c4a81.html" target="_blank" title="友盟同欣（北京）科技有限公司">
                                    友盟同欣（北京）科技有限公司
                                </a>
                            </td>
                            <td>110000450186489</td>
                        </tr>
                        <tr>
                            <td class="tx">18</td>
                            <td>
                                <a data-href="/firm_ad0e79e0f7dcb369830f80ae78d28e87.html" target="_blank" title="广东心怡科技物流有限公司">
                                    广东心怡科技物流有限公司
                                </a>
                            </td>
                            <td>440000000043734</td>
                        </tr>
                        <tr>
                            <td class="tx">19</td>
                            <td>
                                <a data-href="/firm_43287b9d5c8e51756cd2dfc08c64758f.html" target="_blank" title="北京摸摸宠儿科技有限公司">
                                    北京摸摸宠儿科技有限公司
                                </a>
                            </td>
                            <td>91110105071714684X</td>
                        </tr>
                        <tr>
                            <td class="tx">20</td>
                            <td>
                                <a data-href="/firm_67b615edb270fc5ee7442a20a25ed89a.html" target="_blank" title="北京星宝时尚科技有限公司">
                                    北京星宝时尚科技有限公司
                                </a>
                            </td>
                            <td>110105015358929</td>
                        </tr>
                        <tr>
                            <td class="tx">21</td>
                            <td>
                                <a data-href="/search?key=浙江五一技术股份有限公司#index:2&amp;" target="_blank" title="浙江五一技术股份有限公司">
                                    浙江五一技术股份有限公司
                                </a>
                            </td>
                            <td>330000000074954</td>
                        </tr>
                        <tr>
                            <td class="tx">22</td>
                            <td>
                                <a data-href="/firm_13de6978d7079ff5471ebe2602bbd8dc.html" target="_blank" title="墨迹风云（北京）软件科技发展有限公司">
                                    墨迹风云（北京）软件科技发展有限公司
                                </a>
                            </td>
                            <td>110108012698483</td>
                        </tr>
                        <tr>
                            <td class="tx">23</td>
                            <td>
                                <a data-href="/firm_b039cb6fd974d1fde268f7ae87385bb6.html" target="_blank" title="上海智篆文化传播有限公司">
                                    上海智篆文化传播有限公司
                                </a>
                            </td>
                            <td>310114002401163</td>
                        </tr>
                        <tr>
                            <td class="tx">24</td>
                            <td>
                                <a data-href="/firm_8668ef3688bfb202028d8b8d7b23bfba.html" target="_blank" title="厦门又一城软件科技有限公司">
                                    厦门又一城软件科技有限公司
                                </a>
                            </td>
                            <td>350203200212475</td>
                        </tr>
                        <tr>
                            <td class="tx">25</td>
                            <td>
                                <a data-href="/firm_79ad09c2c1230484a98aa5a48435b4fb.html" target="_blank" title="上海幻橙网络科技有限公司">
                                    上海幻橙网络科技有限公司
                                </a>
                            </td>
                            <td>91310105563089520E</td>
                        </tr>
                    </tbody>
                </table>
                <div class="tcaption">
                    <span class="title">企业资产状况信息</span>
                    <span class="watermark"></span>
                </div>
                <table class="ntable">
                    <tbody>
                        <tr>
                            <td class="tb" width="20%">资产总额</td>
                            <td>企业选择不公示</td>
                            <td class="tb" width="20%">所有者权益合计</td>
                            <td>企业选择不公示</td>
                        </tr>
                        <tr>
                            <td class="tb">营业总收入 </td>
                            <td>企业选择不公示</td>
                            <td class="tb">利润总额 </td>
                            <td>企业选择不公示</td>
                        </tr>
                        <tr>
                            <td class="tb">营业总收入中主营业务收入 </td>
                            <td>企业选择不公示</td>
                            <td class="tb">净利润 </td>
                            <td>企业选择不公示</td>
                        </tr>
                        <tr>
                            <td class="tb">纳税总额 </td>
                            <td>企业选择不公示</td>
                            <td class="tb">负债总额 </td>
                            <td>企业选择不公示</td>
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
                            <td width="30%">-</td>
                            <td class="tb" width="20%">职工基本医疗保险</td>
                            <td width="30%">-</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">生育保险</td>
                            <td width="25%">-</td>
                            <td class="tb" width="20%">失业保险</td>
                            <td width="30%">-</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">工伤保险</td>
                            <td width="25%" colspan="3">-</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%" rowspan="5">单位缴纳基数</td>
                            <td width="30%">单位参加城镇职工基本养老保险缴费基数</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加失业保险缴费基数</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加职工基本医疗保险缴费基数</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加工伤保险缴费基数</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加生育保险缴费基数</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%" rowspan="5">本期实际缴费金额</td>
                            <td width="40%">参加城镇职工基本养老保险本期实际缴费金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">参加失业保险本期实际缴费金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">参加职工基本医疗保险本期实际缴费金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">参加工伤保险本期实际缴费金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">参加生育保险本期实际缴费金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%" rowspan="5">单位累计欠缴金额</td>
                            <td width="30%">单位参加城镇职工基本养老保险累计欠缴金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加失业保险累计欠缴金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加职工基本医疗保险累计欠缴金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加工伤保险累计欠缴金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加生育保险累计欠缴金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div :class="{'tab-pane':true,'fade':true, 'in':true, 'active':isTab==3}" id="3">
                <div class="text-gray m-b-sm m-t-sm">2015-06-25&nbsp;公布</div>
                <div class="tcaption">
                    <span class="title">企业基本信息</span>
                    <span class="watermark"></span>
                </div>
                <table class="ntable">
                    <tbody>
                        <tr>
                            <td class="tb" width="20%">注册号</td>
                            <td width="30%">330100400015575</td>
                            <td class="tb" width="20%">统一社会信用代码</td>
                            <td width="30%">-</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">企业经营状态</td>
                            <td width="30%">开业</td>
                            <td class="tb" width="20%">企业联系电话</td>
                            <td width="25%">0571-85022088</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">从业人数 </td>
                            <td width="25%">企业选择不公示</td>
                            <td class="tb" width="20%">邮政编码</td>
                            <td width="25%">310012</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">有限责任公司本年度是否发生股东股权转让</td>
                            <td>否</td>
                            <td class="tb" width="20%">企业是否有投资信息或购买其他公司股权</td>
                            <td colspan="3">是</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">电子邮箱</td>
                            <td colspan="3">
                                <a data-href="mailto:tangxian.gongtx@alibaba-inc.com" style="color:#555;">tangxian.gongtx@alibaba-inc.com</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">企业通讯地址</td>
                            <td colspan="3">
                                <a onclick="showMapModal('杭州市滨江区网商路699号','')">杭州市滨江区网商路699号</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="tcaption">
                    <span class="title">股东（发起人）出资信息</span>
                    <span class="watermark"></span>
                </div>
                <table class="ntable ntable-odd">
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
                        <tr>
                            <td>1</td>
                            <td class="text-center">
                                <a data-href="/firm_hb3bb4f2f8e434f4400f1a355fb384db.html" target="_blank">Alibaba.com China Limited</a>
                            </td>
                            <td class="text-center">50870</td>
                            <td class="text-center">2015-06-30</td>
                            <td class="text-center">货币</td>
                            <td class="text-center">50870</td>
                            <td class="text-center">2015-06-10</td>
                            <td class="text-center">货币</td>
                        </tr>
                    </tbody>
                </table>
                <div class="tcaption">
                    <span class="title">对外投资信息</span>
                    <span class="watermark"></span>
                </div>
                <table class="ntable">
                    <tbody>
                        <tr>
                            <td class="tx">序号</td>
                            <td class="tb" width="50%">投资设立企业或购买股权企业名称</td>
                            <td class="tb">注册号</td>
                        </tr>
                        <tr>
                            <td class="tx">1</td>
                            <td>
                                <a data-href="/firm_6d266b0db5f8e8957c460929064c4a81.html" target="_blank" title="友盟同欣（北京）科技有限公司">
                                    友盟同欣（北京）科技有限公司
                                </a>
                            </td>
                            <td>110000450186489</td>
                        </tr>
                        <tr>
                            <td class="tx">2</td>
                            <td>
                                <a data-href="/firm_b780537a41377c304bbdb08238876862.html" target="_blank" title="武汉元光科技有限公司">
                                    武汉元光科技有限公司
                                </a>
                            </td>
                            <td>420100000181084</td>
                        </tr>
                        <tr>
                            <td class="tx">3</td>
                            <td>
                                <a data-href="/firm_8668ef3688bfb202028d8b8d7b23bfba.html" target="_blank" title="厦门又一城软件科技有限公司">
                                    厦门又一城软件科技有限公司
                                </a>
                            </td>
                            <td>350203200212475</td>
                        </tr>
                        <tr>
                            <td class="tx">4</td>
                            <td>
                                <a data-href="/firm_e78154a2c2d1cf032241c5a7d237470f.html" target="_blank" title="北京雅观科技有限公司">
                                    北京雅观科技有限公司
                                </a>
                            </td>
                            <td>110000450170864</td>
                        </tr>
                        <tr>
                            <td class="tx">5</td>
                            <td>
                                <a data-href="/firm_15d2b7f7ae7e1ee05a0a790e989e53a8.html" target="_blank" title="上海庆科信息技术有限公司">
                                    上海庆科信息技术有限公司
                                </a>
                            </td>
                            <td>310107000581907</td>
                        </tr>
                        <tr>
                            <td class="tx">6</td>
                            <td>
                                <a data-href="/firm_db0f163de0a2d2996d16ee1faebee965.html" target="_blank" title="北京百舜华年文化传播有限公司">
                                    北京百舜华年文化传播有限公司
                                </a>
                            </td>
                            <td>110107011106880</td>
                        </tr>
                        <tr>
                            <td class="tx">7</td>
                            <td>
                                <a data-href="/firm_f86548a1866ec753ef2eafad76c89df3.html" target="_blank" title="福建海都小鱼网络股份有限公司">
                                    福建海都小鱼网络股份有限公司
                                </a>
                            </td>
                            <td>350298100001205</td>
                        </tr>
                        <tr>
                            <td class="tx">8</td>
                            <td>
                                <a data-href="/search?key=浙江五一技术股份有限公司#index:2&amp;" target="_blank" title="浙江五一技术股份有限公司">
                                    浙江五一技术股份有限公司
                                </a>
                            </td>
                            <td>330000000074954</td>
                        </tr>
                        <tr>
                            <td class="tx">9</td>
                            <td>
                                <a data-href="/firm_79572f4829e1464aa4fd5870ae8370aa.html" target="_blank" title="深圳市一达通企业服务有限公司">
                                    深圳市一达通企业服务有限公司
                                </a>
                            </td>
                            <td>440301103275393</td>
                        </tr>
                        <tr>
                            <td class="tx">10</td>
                            <td>
                                <a data-href="/firm_3e755b49933ea5be741f8bcbbebd9008.html" target="_blank" title="堆糖信息科技（上海）有限公司">
                                    堆糖信息科技（上海）有限公司
                                </a>
                            </td>
                            <td>310115001831935</td>
                        </tr>
                        <tr>
                            <td class="tx">11</td>
                            <td>
                                <a data-href="/firm_ad0e79e0f7dcb369830f80ae78d28e87.html" target="_blank" title="广东心怡科技物流有限公司">
                                    广东心怡科技物流有限公司
                                </a>
                            </td>
                            <td>440000000043734</td>
                        </tr>
                        <tr>
                            <td class="tx">12</td>
                            <td>
                                <a data-href="/firm_13de6978d7079ff5471ebe2602bbd8dc.html" target="_blank" title="墨迹风云（北京）软件科技发展有限公司">
                                    墨迹风云（北京）软件科技发展有限公司
                                </a>
                            </td>
                            <td>110108012698483</td>
                        </tr>
                        <tr>
                            <td class="tx">13</td>
                            <td>
                                <a data-href="/firm_67b615edb270fc5ee7442a20a25ed89a.html" target="_blank" title="北京星宝时尚科技有限公司">
                                    北京星宝时尚科技有限公司
                                </a>
                            </td>
                            <td>110105015358929</td>
                        </tr>
                        <tr>
                            <td class="tx">14</td>
                            <td>
                                <a data-href="/firm_b039cb6fd974d1fde268f7ae87385bb6.html" target="_blank" title="上海智篆文化传播有限公司">
                                    上海智篆文化传播有限公司
                                </a>
                            </td>
                            <td>310114002401163</td>
                        </tr>
                        <tr>
                            <td class="tx">15</td>
                            <td>
                                <a data-href="/firm_bbfbb98d148d51fcfa06822cf3ff3fa5.html" target="_blank" title="广州恒大淘宝足球俱乐部有限公司">
                                    广州恒大淘宝足球俱乐部有限公司
                                </a>
                            </td>
                            <td>440101000059092</td>
                        </tr>
                        <tr>
                            <td class="tx">16</td>
                            <td>
                                <a data-href="/firm_c6f3c7fe43a2d4948a754a90d18c8deb.html" target="_blank" title="南京晟邦物流有限公司">
                                    南京晟邦物流有限公司
                                </a>
                            </td>
                            <td>320106000148572</td>
                        </tr>
                        <tr>
                            <td class="tx">17</td>
                            <td>
                                <a data-href="/firm_55886944a9f99bbc3da1286ad860cb97.html" target="_blank" title="永杨安风（北京）科技有限公司">
                                    永杨安风（北京）科技有限公司
                                </a>
                            </td>
                            <td>110105014108491</td>
                        </tr>
                        <tr>
                            <td class="tx">18</td>
                            <td>
                                <a data-href="/firm_754acd29b416390928b4dccae8b6cc47.html" target="_blank" title="上海易果电子商务有限公司">
                                    上海易果电子商务有限公司
                                </a>
                            </td>
                            <td>310105000320742</td>
                        </tr>
                    </tbody>
                </table>
                <div class="tcaption">
                    <span class="title">企业资产状况信息</span>
                    <span class="watermark"></span>
                </div>
                <table class="ntable">
                    <tbody>
                        <tr>
                            <td class="tb" width="20%">资产总额</td>
                            <td>企业选择不公示</td>
                            <td class="tb" width="20%">所有者权益合计</td>
                            <td>企业选择不公示</td>
                        </tr>
                        <tr>
                            <td class="tb">营业总收入 </td>
                            <td>企业选择不公示</td>
                            <td class="tb">利润总额 </td>
                            <td>企业选择不公示</td>
                        </tr>
                        <tr>
                            <td class="tb">营业总收入中主营业务收入 </td>
                            <td>企业选择不公示</td>
                            <td class="tb">净利润 </td>
                            <td>企业选择不公示</td>
                        </tr>
                        <tr>
                            <td class="tb">纳税总额 </td>
                            <td>企业选择不公示</td>
                            <td class="tb">负债总额 </td>
                            <td>企业选择不公示</td>
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
                            <td width="30%">-</td>
                            <td class="tb" width="20%">职工基本医疗保险</td>
                            <td width="30%">-</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">生育保险</td>
                            <td width="25%">-</td>
                            <td class="tb" width="20%">失业保险</td>
                            <td width="30%">-</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">工伤保险</td>
                            <td width="25%" colspan="3">-</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%" rowspan="5">单位缴纳基数</td>
                            <td width="30%">单位参加城镇职工基本养老保险缴费基数</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加失业保险缴费基数</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加职工基本医疗保险缴费基数</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加工伤保险缴费基数</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加生育保险缴费基数</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%" rowspan="5">本期实际缴费金额</td>
                            <td width="40%">参加城镇职工基本养老保险本期实际缴费金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">参加失业保险本期实际缴费金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">参加职工基本医疗保险本期实际缴费金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">参加工伤保险本期实际缴费金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">参加生育保险本期实际缴费金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%" rowspan="5">单位累计欠缴金额</td>
                            <td width="30%">单位参加城镇职工基本养老保险累计欠缴金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加失业保险累计欠缴金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加职工基本医疗保险累计欠缴金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加工伤保险累计欠缴金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加生育保险累计欠缴金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div :class="{'tab-pane':true,'fade':true, 'in':true, 'active':isTab==4}" id="4">
                <div class="text-gray m-b-sm m-t-sm">2014-12-17&nbsp;公布</div>
                <div class="tcaption">
                    <span class="title">企业基本信息</span>
                    <span class="watermark"></span>
                </div>
                <table class="ntable">
                    <tbody>
                        <tr>
                            <td class="tb" width="20%">注册号</td>
                            <td width="30%">330100400015575</td>
                            <td class="tb" width="20%">统一社会信用代码</td>
                            <td width="30%">-</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">企业经营状态</td>
                            <td width="30%">开业</td>
                            <td class="tb" width="20%">企业联系电话</td>
                            <td width="25%">0571-85022088</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">从业人数 </td>
                            <td width="25%">企业选择不公示</td>
                            <td class="tb" width="20%">邮政编码</td>
                            <td width="25%">310012</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">有限责任公司本年度是否发生股东股权转让</td>
                            <td>否</td>
                            <td class="tb" width="20%">企业是否有投资信息或购买其他公司股权</td>
                            <td colspan="3">是</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">电子邮箱</td>
                            <td colspan="3">
                                <a data-href="mailto:tangxian.gongtx@alibaba-inc.com" style="color:#555;">tangxian.gongtx@alibaba-inc.com</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">企业通讯地址</td>
                            <td colspan="3">
                                <a onclick="showMapModal('杭州市滨江区网商路699号','')">杭州市滨江区网商路699号</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="tcaption">
                    <span class="title">网站或网店信息</span>
                    <span class="watermark"></span>
                </div>
                <table class="ntable ntable-odd">
                    <tbody>
                        <tr>
                            <th class="tx">序号</th>
                            <th class="">类型</th>
                            <th class="">名称</th>
                            <th class="">网址</th>
                        </tr>
                        <tr>
                            <td class="tx">1</td>
                            <td class="text-center">网站</td>
                            <td class="text-center">企业融资网</td>
                            <td>
                                <a data-href="http://http://www.rongzichina.com.cn" target="_blank" rel="nofollow">http://www.rongzichina.com.cn</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tx">2</td>
                            <td class="text-center">网站</td>
                            <td class="text-center">阿里巴巴B2B内网</td>
                            <td>
                                <a data-href="http://http://www.alibaba-inc.com" target="_blank" rel="nofollow">http://www.alibaba-inc.com</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tx">3</td>
                            <td class="text-center">网站</td>
                            <td class="text-center">来往短网址</td>
                            <td>
                                <a data-href="http://http://www.lwurl.to" target="_blank" rel="nofollow">http://www.lwurl.to</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tx">4</td>
                            <td class="text-center">网站</td>
                            <td class="text-center">静态资源服务管理系统</td>
                            <td>
                                <a data-href="http://http://www.aliunicorn.com" target="_blank" rel="nofollow">http://www.aliunicorn.com</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tx">5</td>
                            <td class="text-center">网站</td>
                            <td class="text-center">旺铺装修平台wiki</td>
                            <td>
                                <a data-href="http://http://www.alibaba.org" target="_blank" rel="nofollow">http://www.alibaba.org</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tx">6</td>
                            <td class="text-center">网站</td>
                            <td class="text-center">国际展览产业联盟</td>
                            <td>
                                <a data-href="http://http://www.expo-ieia.com" target="_blank" rel="nofollow">http://www.expo-ieia.com</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tx">7</td>
                            <td class="text-center">网站</td>
                            <td class="text-center">阿里巴巴网站静态文件输出系统</td>
                            <td>
                                <a data-href="http://http://www.aliimg.com" target="_blank" rel="nofollow">http://www.aliimg.com</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tx">8</td>
                            <td class="text-center">网站</td>
                            <td class="text-center">阿里巴巴B2B开源网站</td>
                            <td>
                                <a data-href="http://http://www.alibabatech.com" target="_blank" rel="nofollow">http://www.alibabatech.com</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="tcaption">
                    <span class="title">股东（发起人）出资信息</span>
                    <span class="watermark"></span>
                </div>
                <table class="ntable ntable-odd">
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
                        <tr>
                            <td>1</td>
                            <td class="text-center">
                                <a data-href="/firm_hb3bb4f2f8e434f4400f1a355fb384db.html" target="_blank">Alibaba.com China Limited</a>
                            </td>
                            <td class="text-center">40880</td>
                            <td class="text-center">2014-06-18</td>
                            <td class="text-center">货币</td>
                            <td class="text-center">40880</td>
                            <td class="text-center">2014-06-18</td>
                            <td class="text-center">货币</td>
                        </tr>
                    </tbody>
                </table>
                <div class="tcaption">
                    <span class="title">对外投资信息</span>
                    <span class="watermark"></span>
                </div>
                <table class="ntable">
                    <tbody>
                        <tr>
                            <td class="tx">序号</td>
                            <td class="tb" width="50%">投资设立企业或购买股权企业名称</td>
                            <td class="tb">注册号</td>
                        </tr>
                        <tr>
                            <td class="tx">1</td>
                            <td>
                                <a data-href="/firm_8668ef3688bfb202028d8b8d7b23bfba.html" target="_blank" title="厦门又一城软件科技有限公司">
                                    厦门又一城软件科技有限公司
                                </a>
                            </td>
                            <td>350203200212475</td>
                        </tr>
                        <tr>
                            <td class="tx">2</td>
                            <td>
                                <a data-href="/firm_13de6978d7079ff5471ebe2602bbd8dc.html" target="_blank" title="墨迹风云（北京）软件科技发展有限公司">
                                    墨迹风云（北京）软件科技发展有限公司
                                </a>
                            </td>
                            <td>110108012698483</td>
                        </tr>
                        <tr>
                            <td class="tx">3</td>
                            <td>
                                <a data-href="/firm_55886944a9f99bbc3da1286ad860cb97.html" target="_blank" title="永杨安风(北京) 科技有限公司">
                                    永杨安风(北京) 科技有限公司
                                </a>
                            </td>
                            <td>110105014108491</td>
                        </tr>
                    </tbody>
                </table>
                <div class="tcaption">
                    <span class="title">企业资产状况信息</span>
                    <span class="watermark"></span>
                </div>
                <table class="ntable">
                    <tbody>
                        <tr>
                            <td class="tb" width="20%">资产总额</td>
                            <td>企业选择不公示</td>
                            <td class="tb" width="20%">所有者权益合计</td>
                            <td>企业选择不公示</td>
                        </tr>
                        <tr>
                            <td class="tb">营业总收入 </td>
                            <td>企业选择不公示</td>
                            <td class="tb">利润总额 </td>
                            <td>企业选择不公示</td>
                        </tr>
                        <tr>
                            <td class="tb">营业总收入中主营业务收入 </td>
                            <td>企业选择不公示</td>
                            <td class="tb">净利润 </td>
                            <td>企业选择不公示</td>
                        </tr>
                        <tr>
                            <td class="tb">纳税总额 </td>
                            <td>企业选择不公示</td>
                            <td class="tb">负债总额 </td>
                            <td>企业选择不公示</td>
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
                            <td width="30%">-</td>
                            <td class="tb" width="20%">职工基本医疗保险</td>
                            <td width="30%">-</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">生育保险</td>
                            <td width="25%">-</td>
                            <td class="tb" width="20%">失业保险</td>
                            <td width="30%">-</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%">工伤保险</td>
                            <td width="25%" colspan="3">-</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%" rowspan="5">单位缴纳基数</td>
                            <td width="30%">单位参加城镇职工基本养老保险缴费基数</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加失业保险缴费基数</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加职工基本医疗保险缴费基数</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加工伤保险缴费基数</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加生育保险缴费基数</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%" rowspan="5">本期实际缴费金额</td>
                            <td width="40%">参加城镇职工基本养老保险本期实际缴费金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">参加失业保险本期实际缴费金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">参加职工基本医疗保险本期实际缴费金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">参加工伤保险本期实际缴费金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">参加生育保险本期实际缴费金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td class="tb" width="20%" rowspan="5">单位累计欠缴金额</td>
                            <td width="30%">单位参加城镇职工基本养老保险累计欠缴金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加失业保险累计欠缴金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加职工基本医疗保险累计欠缴金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加工伤保险累计欠缴金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                        <tr>
                            <td width="30%">单位参加生育保险累计欠缴金额</td>
                            <td width="30%" colspan="2">-</td>
                        </tr>
                    </tbody>
                </table>
            </div> -->
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
        test(){
            this.$global.alertMessage()
      },
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

#myTab li a {
    padding-top: 15px;
    padding-bottom: 15px;
    font-weight: 600;
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
