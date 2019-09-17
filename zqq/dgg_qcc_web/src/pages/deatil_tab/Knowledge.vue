<template>
    <div class="tb_content">
        <section class="panel  clear m_dataTab">
            <div class="panel-body">

                <template v-if="msg.sbDetails">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('brankMsg')">
                        商标信息&nbsp;{{msg.sbDetails.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        商标信息&nbsp;0
                    </a>
                </template>

                <template v-if="msg.zlxq">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('zhuanli')">
                        专利信息&nbsp;{{msg.zlxq.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        专利信息&nbsp;0
                    </a>
                </template>

                 <template v-if="msg.qyzs">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('qiyezs')">
                        证书信息&nbsp;{{msg.qyzs.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        证书信息&nbsp;0
                    </a>
                </template>

                <template v-if="msg.zpzzq">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('qiyezs')">
                        作品著作权&nbsp;{{msg.zpzzq.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        作品著作权(无)&nbsp;0
                    </a>
                </template>

                <template v-if="msg.zzqrz">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('rjzzq')">
                        软件著作权&nbsp;{{msg.zzqrz.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        软件著作权&nbsp;0
                    </a>
                </template>

                <template v-if="msg.gswz">
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm" @click="scollTo('websiteMsg')">
                        网站信息&nbsp;{{msg.gswz.length}}
                    </a>
                </template>
                <template v-else>
                    <a class="btn btn-sm btn-default m-t-sm  m-r-sm c_disable" >
                        网站信息&nbsp;0
                    </a>
                </template>

                <!-- <a class="btn btn-sm btn-default  m-r-sm" style="white-space:nowrap;">
                    商标信息&nbsp;{{msg.sbDetails ? msg.sbDetails.length : 0}}
                </a> -->
                <!-- <a class="btn btn-sm btn-default  m-r-sm c_disable" style="white-space:nowrap;cursor: pointer">
                    专利信息&nbsp;{{msg.zlxq ? msg.zlxq.length : 0}}
                </a>
                <a class="btn btn-sm btn-default  m-r-sm c_disable" style="white-space:nowrap;cursor: default">
                    证书信息&nbsp;{{msg.qyzs ? msg.qyzs.length : 0}}
                </a> -->
                <!-- <a class="btn btn-sm btn-default  m-r-sm" style="white-space:nowrap;">
                    作品著作权&nbsp;{{msg.sbDetails ? msg.sbDetails.length : 0}}
                </a> -->
                <!-- <a class="btn btn-sm btn-default  m-r-sm" style="white-space:nowrap;">
                    软件著作权&nbsp;{{msg.zzqrz ? msg.zzqrz.length : 0}}
                </a> -->
                <!-- <a class="btn btn-sm btn-default  m-r-sm" style="white-space:nowrap;">
                    网站信息&nbsp;{{msg.gswz ? msg.gswz.length : 0}}
                </a> -->
            </div>
        </section>
        <!-- 商标信息 开始 -->
        <section class="panel clear " id="brankMsg" v-if="msg.sbDetails">
            <div class="tcaption">
                <span class="title">商标信息</span>
                <span class="tbadge"> {{msg.sbDetails.total}} </span>
                <!-- <div class="chooseAssets" data-box="sb" style="float: right">
                    <div class="tdrop">
                        <span class="btn" data-toggle="dropdown">
                            <span>全部类别</span>
                            <span class="caret m-l"></span>
                        </span>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <li>
                                <a data-option="intcls" data-value="0">不限</a>
                            </li>
                            <li>
                                <a data-option="intcls" data-value="9">科学仪器(3)</a>
                            </li>
                            <li>
                                <a data-option="intcls" data-value="35">广告销售(5)</a>
                            </li>
                            <li>
                                <a data-option="intcls" data-value="38">通讯服务(5)</a>
                            </li>
                            <li>
                                <a data-option="intcls" data-value="42">设计研究(4)</a>
                            </li>
                        </ul>
                    </div>
                    <div class="tdrop">
                        <span class="btn" data-toggle="dropdown">
                            <span>流程状态</span>
                            <span class="caret m-l"></span>
                        </span>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <li>
                                <a data-option="flowno" data-value="0">不限</a>
                            </li>
                            <li>
                                <a data-option="flowno" data-value="1">
                                    打印驳回或部分驳回通知书 (2)
                                </a>
                            </li>
                            <li>
                                <a data-option="flowno" data-value="151">
                                    商标续展完成 (1)
                                </a>
                            </li>
                            <li>
                                <a data-option="flowno" data-value="198">
                                    商标变更完成 (1)
                                </a>
                            </li>
                            <li>
                                <a data-option="flowno" data-value="202">
                                    商标无效 (13)
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div class="tdrop">
                        <span class="btn" data-toggle="dropdown">
                            <span>申请状态</span>
                            <span class="caret m-l"></span>
                        </span>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <li>
                                <a data-option="status" data-value="0">不限</a>
                            </li>
                            <li>
                                <a data-option="status" data-value="1">有效(1)</a>
                            </li>
                            <li>
                                <a data-option="status" data-value="2">无效(16)</a>
                            </li>
                        </ul>
                    </div>
                    <div class="tdrop">
                        <span class="btn" data-toggle="dropdown">
                            <span>申请日期</span>
                            <span class="caret m-l"></span>
                        </span>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <li>
                                <a data-option="appdateyear" data-value="0">不限</a>
                            </li>
                            <li>
                                <a data-option="appdateyear" data-value="2002">2002(1)</a>
                            </li>
                            <li>
                                <a data-option="appdateyear" data-value="2003">2003(14)</a>
                            </li>
                            <li>
                                <a data-option="appdateyear" data-value="2004">2004(2)</a>
                            </li>
                        </ul>
                    </div>
                </div> -->
            </div>
            <div>
                <table class="ntable ntable-odd">
                    <tbody>
                        <tr>
                            <th>序号</th>
                            <th>商标</th>
                            <th>商标名</th>
                            <th>状态</th>
                            <th>申请时间</th>
                            <th>注册号</th>
                            <th>国际分类</th>
                            <th>内容</th>
                        </tr>
                        <tr v-for="(item,idx) in brandList" :key="idx">
                            <td class="tx">{{idx+1}}</td>
                            <td class="text-center">
                                <img :src="item.imageUrl ? item.imageUrl : '' "  :onerror="errorImg" style="max-width:80px;"> </td>
                            <td class="text-center">{{item.name}}</td>
                            <td width="200" class="text-center">{{item.status}}</td>
                            <td width="103">{{item.appDate}}</td>
                            <td width="90" class="text-center">{{item.regNo}}</td>
                            <td width="103" class="text-center">{{item.intCls}}</td>
                            <td width="90" class="text-center" >
                                <a @click="checkBrandDetail(item.regNo)">查看详情</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="pageination_s text-right border0" v-if="msg.sbDetails.total > 0">
                <el-pagination background @current-change="handleCurrentChange" layout="prev, pager, next" :pageSize='10' :total=" Number(msg.sbDetails.total)">
                </el-pagination>
            </div>
        </section>
         <!-- //商标详情 开始 -->
        <el-dialog title="商标详情" :visible.sync="brandDetailShow"  width="40%" top="5vh">
            <table class="ntable ntable-odd data" v-if="brandJson">
                    <tr>
                        <td colspan="4" class="text-center"><img :src="brandJson.imageUrl ? brandJson.imageUrl : '' "  :onerror="errorImg" style="max-width:120px;"></td>
                    </tr>
                     <tr>
                        <td width="20%" class="tb">商标名称</td> 
                        <td colspan="3">{{brandJson.name || " - "}}</td> 
                    </tr>
                    <tr>
                        <td width="20%" class="tb">注册号</td> 
                        <td width="30%">{{brandJson.regNo || " - "}}</td> 
                        <td width="20%" class="tb">状态</td>
                        <td width="30%">{{brandJson.status || " - "}}</td>
                    </tr>
                    <tr>
                        <td width="20%" class="tb">国际分类</td> 
                        <td width="30%">{{brandJson.intCls || " - "}}</td> 
                        <td width="20%" class="tb">申请时间</td>
                        <td width="30%">{{brandJson.appDate || " - "}}</td>
                    </tr>
                    <tr>
                        <td width="20%" class="tb">使用期限</td> 
                        <td width="30%">{{brandJson.validPeriod || " - "}}</td> 
                        <td width="20%" class="tb">代理机构</td>
                        <td width="30%">{{brandJson.tmSection || " - "}}</td>
                    </tr>
                    <tr>
                        <td width="20%" class="tb">初审公告号</td> 
                        <td width="30%">{{brandJson.announcementIssue || " - "}}</td> 
                        <td width="20%" class="tb">注册公告号</td>
                        <td width="30%">{{brandJson.regIssue || " - "}}</td>
                    </tr>
                    <tr>
                        <td width="20%" class="tb">申请人</td> 
                        <td width="30%">{{brandJson.applicantCn || " - "}} {{brandJson.applicantEn}}</td> 
                        <td width="20%" class="tb">申请地址</td>
                        <td width="30%">{{brandJson.tmApplyAddres || " - "}}</td>
                    </tr>
                     <tr>
                        <td width="20%" class="tb">商品服务列表</td> 
                        <td width="30%">{{brandJson.tmGoodsService || " - "}}</td> 
                        <td width="20%" class="tb">申请流程</td>
                        <td width="30%" v-html="addBr(brandJson.tmApplyFlow)"></td>
                    </tr>
            </table>
        </el-dialog>
        <!-- //商标详情 结束 -->
        <!-- 商标信息 结束 -->
         <!-- 专利信息 开始 -->
        <section class="panel clear " id="zhuanli" v-if="msg.zlxq">
            <div class="tcaption">
                <span class="title">专利信息</span>
                <span class="tbadge"> {{msg.zlxq.length}} </span>
                <!-- <div class="chooseAssets" data-box="sb" style="float: right">
                    <div class="tdrop">
                        <span class="btn" data-toggle="dropdown">
                            <span>全部类别</span>
                            <span class="caret m-l"></span>
                        </span>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <li>
                                <a data-option="intcls" data-value="0">不限</a>
                            </li>
                            <li>
                                <a data-option="intcls" data-value="9">科学仪器(3)</a>
                            </li>
                            <li>
                                <a data-option="intcls" data-value="35">广告销售(5)</a>
                            </li>
                            <li>
                                <a data-option="intcls" data-value="38">通讯服务(5)</a>
                            </li>
                            <li>
                                <a data-option="intcls" data-value="42">设计研究(4)</a>
                            </li>
                        </ul>
                    </div>
                    <div class="tdrop">
                        <span class="btn" data-toggle="dropdown">
                            <span>流程状态</span>
                            <span class="caret m-l"></span>
                        </span>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <li>
                                <a data-option="flowno" data-value="0">不限</a>
                            </li>
                            <li>
                                <a data-option="flowno" data-value="1">
                                    打印驳回或部分驳回通知书 (2)
                                </a>
                            </li>
                            <li>
                                <a data-option="flowno" data-value="151">
                                    商标续展完成 (1)
                                </a>
                            </li>
                            <li>
                                <a data-option="flowno" data-value="198">
                                    商标变更完成 (1)
                                </a>
                            </li>
                            <li>
                                <a data-option="flowno" data-value="202">
                                    商标无效 (13)
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div class="tdrop">
                        <span class="btn" data-toggle="dropdown">
                            <span>申请状态</span>
                            <span class="caret m-l"></span>
                        </span>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <li>
                                <a data-option="status" data-value="0">不限</a>
                            </li>
                            <li>
                                <a data-option="status" data-value="1">有效(1)</a>
                            </li>
                            <li>
                                <a data-option="status" data-value="2">无效(16)</a>
                            </li>
                        </ul>
                    </div>
                    <div class="tdrop">
                        <span class="btn" data-toggle="dropdown">
                            <span>申请日期</span>
                            <span class="caret m-l"></span>
                        </span>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <li>
                                <a data-option="appdateyear" data-value="0">不限</a>
                            </li>
                            <li>
                                <a data-option="appdateyear" data-value="2002">2002(1)</a>
                            </li>
                            <li>
                                <a data-option="appdateyear" data-value="2003">2003(14)</a>
                            </li>
                            <li>
                                <a data-option="appdateyear" data-value="2004">2004(2)</a>
                            </li>
                        </ul>
                    </div>
                </div> -->
            </div>
            <div>
                <table class="ntable ntable-odd">
                    <tbody>
                        <tr>
                            <th>序号</th>
                            <th>专利类型</th>
                            <th>公开（公告）号</th>
                            <th>公开（公告）日</th>
                            <th>名称</th>
                        </tr>
                        <tr v-for="(item,idx) in msg.zlxq" :key="idx">
                            <td class="tx">{{idx+1}}</td>
                            <td class="text-center">{{item.kindCodeDesc}}</td>
                            <td class="text-center">{{item.publicationNumber}}</td>
                            <td class="text-center">{{item.publicationDate}}</td>
                            <td class="text-center">
                                <a @click="test">{{item.kindCodeDesc}}</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </section>
        <!-- 专利信息 结束 -->
        <!-- 企业证书 开始 -->
        <section class="panel clear " id="qiyezs" v-if="msg.qyzs">
            <div class="tcaption">
                <span class="title">企业证书</span>
                <span class="tbadge"> {{msg.qyzs.length}} </span>
               
            </div>
            <div>
                <table class="ntable ntable-odd">
                    <tbody>
                        <tr>
                            <th>序号</th>
                            <th>证书名称</th>
                            <th>证书编号</th>
                            <th>证书类型</th>
                            <th>发证日期</th>
                            <th>截止日期</th>
                        </tr>
                        <tr v-for="(item,idx) in msg.qyzs" :key="idx">
                            <td class="tx">{{idx+1}}</td>
                            <td class="text-center">{{item.name}}</td>
                            <td class="text-center">{{item.no}}</td>
                            <td class="text-center">{{item.type || "-"}}</td>
                            <td class="text-center">{{item.startDate || "-"}}</td>
                            <td class="text-center">{{item.endDate || "-"}}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </section>
        <!-- 企业证书 结束 -->
        <!-- 作品著作权 开始 -->
        <section class="panel  clear" id="zzqlist" v-if="msg.zpzzq">
            <div class="tcaption">
                <span class="title">作品著作权</span>
                <span class="tbadge">
                    15
                </span>
                <span class="watermark"></span>
            </div>

            <table class="ntable ntable-odd">
                <tbody>
                    <tr>
                        <th class="tx">序号</th>
                        <th>作品名称</th>
                        <th>首次发表日期</th>
                        <th>创作完成日期</th>
                        <th>登记号</th>
                        <th>登记日期</th>
                        <th>登记类别</th>
                    </tr>
                    <tr>
                        <td class="tx">1</td>
                        <td width="182">网销宝客户</td>
                        <td width="112" class="text-center">2017-08-28</td>
                        <td width="112" class="text-center">2017-08-28</td>
                        <td>国作登字-2017-F-00482938</td>
                        <td width="103" class="text-center">2017-08-28</td>
                        <td class="text-center">美术</td>
                    </tr>
                    <tr>
                        <td class="tx">2</td>
                        <td width="182">网销宝男小宝-宝宝君</td>
                        <td width="112" class="text-center">2017-08-28</td>
                        <td width="112" class="text-center">2017-08-28</td>
                        <td>国作登字-2017-F-00482936</td>
                        <td width="103" class="text-center">2017-08-28</td>
                        <td class="text-center">美术</td>
                    </tr>
                    <tr>
                        <td class="tx">3</td>
                        <td width="182">网销宝女小宝-宝宝郡</td>
                        <td width="112" class="text-center">2017-08-28</td>
                        <td width="112" class="text-center">2017-08-28</td>
                        <td>国作登字-2017-F-00482937</td>
                        <td width="103" class="text-center">2017-08-28</td>
                        <td class="text-center">美术</td>
                    </tr>
                    <tr>
                        <td class="tx">4</td>
                        <td width="182">u一点竖简体</td>
                        <td width="112" class="text-center">2017-07-31</td>
                        <td width="112" class="text-center">2017-07-31</td>
                        <td>国作登字-2017-F-00483020</td>
                        <td width="103" class="text-center">2017-07-31</td>
                        <td class="text-center">美术</td>
                    </tr>
                    <tr>
                        <td class="tx">5</td>
                        <td width="182">u一点圆简体</td>
                        <td width="112" class="text-center">2017-07-31</td>
                        <td width="112" class="text-center">2017-07-31</td>
                        <td>国作登字-2017-F-00483021</td>
                        <td width="103" class="text-center">2017-07-31</td>
                        <td class="text-center">美术</td>
                    </tr>
                    <tr>
                        <td class="tx">6</td>
                        <td width="182">新外贸情报局icon</td>
                        <td width="112" class="text-center">2017-04-12</td>
                        <td width="112" class="text-center">2017-04-12</td>
                        <td>国作登字-2017-F-00375372</td>
                        <td width="103" class="text-center">2017-04-12</td>
                        <td class="text-center">美术</td>
                    </tr>
                    <tr>
                        <td class="tx">7</td>
                        <td width="182">实力商家LOGO 第二版</td>
                        <td width="112" class="text-center">2017-04-06</td>
                        <td width="112" class="text-center">2017-04-06</td>
                        <td>国作登字-2017-F-00374912</td>
                        <td width="103" class="text-center">2017-04-06</td>
                        <td class="text-center">美术</td>
                    </tr>
                    <tr>
                        <td class="tx">8</td>
                        <td width="182">产地优选LOGO</td>
                        <td width="112" class="text-center">2016-12-27</td>
                        <td width="112" class="text-center">2016-12-27</td>
                        <td>国作登字-2016-F-00348154</td>
                        <td width="103" class="text-center">2016-12-27</td>
                        <td class="text-center">美术</td>
                    </tr>
                    <tr>
                        <td class="tx">9</td>
                        <td width="182">实力商家LOGO</td>
                        <td width="112" class="text-center">2015-05-06</td>
                        <td width="112" class="text-center">2015-05-06</td>
                        <td>国作登字-2016-F-00326789</td>
                        <td width="103" class="text-center">2016-10-25</td>
                        <td class="text-center">美术</td>
                    </tr>
                    <tr>
                        <td class="tx">10</td>
                        <td width="182">百年学堂</td>
                        <td width="112" class="text-center">-</td>
                        <td width="112" class="text-center">2016-06-17</td>
                        <td>国作登字-2016-F-00302903</td>
                        <td width="103" class="text-center">2016-09-07</td>
                        <td class="text-center">美术</td>
                    </tr>
                </tbody>
            </table>
            <!-- <div class="m-b">
                <nav class="text-right">
                    <ul class="pagination">
                        <li class="active">
                            <a htef="#">1</a>
                        </li>
                        <li>
                            <a id="ajaxpage" href='javascript:getTabList(2,"assets","zzq")'>2</a>
                        </li>
                        <li>
                            <a id="ajaxpage" href='javascript:getTabList(2,"assets","zzq")'>&gt;</a>
                        </li>
                    </ul>
                </nav>
            </div> -->
        </section>
        <!-- 作品著作权 结束 -->
        <!-- 软件著作权 开始 -->
        <section class="panel  clear" id="rjzzq" v-if="msg.zzqrz">
            <div class="tcaption">
                <span class="title">软件著作权</span>
                <span class="tbadge">{{msg.zzqrz.length}}</span>
                <span class="watermark"></span>
            </div>
            <table class="ntable ntable-odd">
                <tbody>
                    <tr>
                        <th class="tx">序号</th>
                        <th>软件名称</th>
                        <th>版本号</th>
                        <th>发布日期</th>
                        <th>软件简称</th>
                        <th>登记号</th>
                        <th>登记批准日期</th>
                    </tr>
                    <tr v-for="(item,index) in msg.zzqrz" :key="index">
                        <td class="tx">{{index+1}}</td>
                        <td width="278">{{item.name}}</td>
                        <td>{{item.versionNo}}</td>
                        <td width="103">{{item.publishDate}}</td>
                        <td width="140">{{item.shortName}}</td>
                        <td>{{item.registerNo}}</td>
                        <td width="110" class="text-center">{{item.registerAperDate}}</td>
                    </tr>
                </tbody>
            </table>
            
        </section>
        <!-- 软件著作权 结束 -->
        <!-- 网站信息 开始 -->
        <section class="panel  clear" id="websiteMsg" v-if="msg.gswz">

            <div class="tcaption">
                <span class="title">网站信息
                    <span class="tbadge">{{msg.gswz.length}}</span>
                </span>
            </div>
            <table class="ntable ntable-odd">
                <tbody>
                    <tr>
                        <th class="tx">序号</th>
                        <th width="280" class="tl">网址</th>
                        <th class="tl">网站名称</th>
                        <th>域名</th>
                        <th width="145">网站备案/许可证号</th>
                        <th width="103">审核时间</th>
                    </tr>
                    <tr v-for="(item,index) in msg.gswz" :key="index">
                        <td class="tx">{{index+1}}</td>
                        <td v-html="splitStr(item.homeSite)">
                            <!-- <a class="c_a" href="http://www.ecopaidang.com" target="_blank" rel="nofollow">{{item.homeSite}}</a> -->
                        </td>
                        <td>{{item.title}}</td>
                        <td class="text-center">
                           {{item.yuMing}}
                            <br> </td>
                        <td class="text-center">{{item.beiAn}}</td>
                        <td class="text-center">{{item.sDate}}</td>
                    </tr>
                </tbody>
            </table>
        </section>
        <!-- 网站信息 结束 -->
    </div>
</template>
<script>
import NoImg from "@/assets/images/no_image.png";
import {IsLogin} from "@/util/common"
export default {
    name: "head1",
    props: {
        msg: {},
        keyWord:{}
    },
    data() {
        return {
            errorImg:'this.src="' + NoImg + '"',
            brandList:[],   //商标数组
            brandJson:"", //商标详情
            brandDetailShow:false
        };
    },
    created(){
        this.brandList = this.msg.sbDetails ? this.msg.sbDetails.list : [];  //商标数组
    },
    methods: {
      test(){
            this.$global.alertMessage()
      },
        scollTo(ele){
           this.$emit('scoll',ele)
      },
      splitStr(str){
         
          if(str){
              let arr=str.split("流量分析 >")
              let str2="";
              for(let item of arr){
                  str2+='<a class="c_a" href="http://'+item+'" target="_blank" rel="nofollow">'+item+'</a><br>'
              }
              return str2;
          }
      },
        handleCurrentChange(e) {
            //翻页 跳转到几页
              let that = this;
              if(IsLogin(that)) return;

              this.$axios({
                type:"post",
                url:"company/brandMsg",
                data:{ keyWord:this.keyWord.title, pageIndex:e,pageSize:10,type:""},
                success:res => {
                  
                    if(res.data.code == 0){
                        this.brandList = res.data.data.list;
                         console.log(22,res.data.code,this.brandList)
                    }
                }})
        },
        checkBrandDetail(id){  //商标详情
            let that = this;
              if(IsLogin(that)) return;
            let obj={
                type:"brand",
                key:"regNo",
                val:id,
           }
           this.$axios({"type":"get","url":"companyItem/mixDetail",data:obj,success:res=>{
                console.log("数据",res)
               if(res.data.code == 0){
                   this.brandJson=res.data.data[0]
                   this.brandDetailShow=true
               }
               else{
                   this.$alert(res.data.msg)
               }
           }},1)
        },
         addBr(str){
           if(!str || str==" ") return "-";
           if(str.indexOf("、") != -1){
               return str.replace(/、/g,"<br>")
           }
           return str
       }
    }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
