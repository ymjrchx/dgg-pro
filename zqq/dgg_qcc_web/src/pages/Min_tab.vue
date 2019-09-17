<template>
    <ul class="hover_box">
        <li :class="{'active': isActive == 0}">
            <a class=" " @click="scollTo(0,'Cominfo')">
                工商信息
            </a>
            <template v-if="Base_info_msg.partnersList">
                <a class="" @click="scollTo(0,'Sockinfo')">
                    股东信息&nbsp;
                    <span>{{Base_info_msg.partnersList.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    股东信息&nbsp;0
                </a>
            </template>

            <template v-if="Base_info_msg.dwtz">
                <a class="" @click="scollTo(0,'touzilist')">
                    对外投资&nbsp;
                    <span>{{Base_info_msg.dwtz.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    对外投资&nbsp;0
                </a>
            </template>

            <template v-if="Base_info_msg.employeesList">
                <a class="" @click="scollTo(0,'Mainmember')">
                    主要成员&nbsp;
                    <span>{{Base_info_msg.employeesList.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    主要成员&nbsp;0
                </a>
            </template>

            <template v-if="Base_info_msg.branchesList">
                <a class="" @click="scollTo(0,'branchesList')">
                    分支机构&nbsp;
                    <span>{{Base_info_msg.branchesList.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    分支机构(无)&nbsp;0
                </a>
            </template>

            <template v-if="Base_info_msg.changeRecordsList">
                <a class="" @click="scollTo(0,'updatelist')">
                    变更记录&nbsp;
                    <span>{{Base_info_msg.changeRecordsList.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    变更记录&nbsp;0
                </a>
            </template>

            <template v-if="Base_info_msg.syr">
                <a class="" @click="scollTo(0,'fzjg')">
                    最终受益人(无)&nbsp;
                    <span>{{Base_info_msg.syr.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    最终受益人(无)&nbsp;0
                </a>
            </template>

            <template v-if="Base_info_msg.kgqy">
                <a class="" @click="scollTo(0,'fzjg')">
                    控股企业(无)&nbsp;
                    <span>{{Base_info_msg.kgqy.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    控股企业(无)&nbsp;0
                </a>
            </template>
        </li>
        <li :class="{'active': isActive == 1}">
            <template v-if="Law_info_msg.bzxr">
                <a class="" @click="scollTo(1,'zhixinglist')">
                    被执行人信息&nbsp;
                    <span>{{Law_info_msg.bzxr.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    被执行人信息&nbsp;0
                </a>
            </template>

            <template v-if="Law_info_msg.sxbzxr">
                <a class="" @click="scollTo(1,'noticelist')">
                    失信被执行(无)&nbsp;
                    <span>{{Law_info_msg.sxbzxr.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    失信被执行(无)&nbsp;0
                </a>
            </template>

            <template v-if="Law_info_msg.cpws">
                <a class="" @click="scollTo(1,'wenshulist')">
                    裁判文书&nbsp;
                    <span>{{Law_info_msg.cpws.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    裁判文书&nbsp;0
                </a>
            </template>

            <template v-if="Law_info_msg.fygg">
                <a class="" @click="scollTo(1,'gonggaolist')">
                    法院公告&nbsp;
                    <span>{{Law_info_msg.fygg.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    法院公告&nbsp;0
                </a>
            </template>

            <template v-if="Law_info_msg.ktgg">
                <a class="" @click="scollTo(1,'fynoticelist')">
                    开庭公告&nbsp;
                    <span>{{Law_info_msg.ktgg.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    开庭公告&nbsp;0
                </a>
            </template>

            <template v-if="Law_info_msg.ssxz">
                <a class="" @click="scollTo(1,'ssxz')">
                    司法协助(无)&nbsp;
                    <span>{{Law_info_msg.ssxz.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    司法协助(无)&nbsp;0
                </a>
            </template>
        </li>
        <li :class="{'active': isActive == 2}">
            <template v-if="Management_info_msg.xzxkgsjs || Management_info_msg.xzxkxyzg">
                <a class="" @click="scollTo(2,'permissionlist')">
                    行政许可&nbsp;
                    <span>{{Management_info_msg.xzxkgsjs.length + Management_info_msg.xzxkxyzg.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    行政许可&nbsp;0
                </a>
            </template>

            <template v-if="Management_info_msg.swxys ">
                <a class="" @click="scollTo(2,'taxCreditList')">
                    税务信用&nbsp;
                    <span>{{Management_info_msg.swxys.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    税务信用&nbsp;0
                </a>
            </template>

            <template v-if="Management_info_msg.products ">
                <a class="" @click="scollTo(2,'productlist')">
                    产品信息&nbsp;
                    <span>{{Management_info_msg.products.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    产品信息&nbsp;0
                </a>
            </template>

            <template v-if="Management_info_msg.rzxxs ">
                <a class="" @click="scollTo(2,'financingList')">
                    融资信息&nbsp;
                    <span>{{Management_info_msg.rzxxs.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    融资信息&nbsp;0
                </a>
            </template>

            <template v-if="Management_info_msg.zdbxx ">
                <a class="" @click="scollTo(2,'tenderlist')">
                    招投标信息&nbsp;
                    <span>{{Management_info_msg.zdbxx.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    招投标信息&nbsp;0
                </a>
            </template>

            <template v-if="Management_info_msg.zp ">
                <a class="" @click="scollTo(2,'joblist')">
                    招聘&nbsp;
                    <span>{{Management_info_msg.zp.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    招聘&nbsp;0
                </a>
            </template>

            <template v-if="Management_info_msg.cwzl ">
                <a class="" @click="scollTo(2,'V3_cwzl')">
                    财务总览&nbsp;
                    <span>{{Management_info_msg.cwzl.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    财务总览&nbsp;0
                </a>
            </template>

            <template v-if="Management_info_msg.jckxy ">
                <a class="" @click="scollTo(2,'V3_cwzl')">
                    进出口信用(无)&nbsp;
                    <span>{{Management_info_msg.jckxy.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    进出口信用(无)&nbsp;0
                </a>
            </template>

            <template v-if="Management_info_msg.wxgzh ">
                <a class="" @click="scollTo(2,'wechatlist')">
                    微信公众号&nbsp;
                    <span>{{Management_info_msg.wxgzh.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    微信公众号&nbsp;0
                </a>
            </template>

            <template v-if="Management_info_msg.jckxy ">
                <a class="" @click="scollTo(2,'V3_cwzl')">
                    新闻舆情(无)&nbsp;
                    <span>{{Management_info_msg.jckxy.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    新闻舆情(无)&nbsp;0
                </a>
            </template>

            <template v-if="Management_info_msg.jckxy ">
                <a class="" @click="scollTo(2,'V3_cwzl')">
                    公告研报(无)&nbsp;
                    <span>{{Management_info_msg.jckxy.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    公告研报(无)&nbsp;0
                </a>
            </template>

            <template v-if="Management_info_msg.jckxy ">
                <a class="" @click="scollTo(2,'V3_cwzl')">
                    地块公示(无)&nbsp;
                    <span>{{Management_info_msg.jckxy.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    地块公示(无)&nbsp;0
                </a>
            </template>

            <template v-if="Management_info_msg.jckxy ">
                <a class="" @click="scollTo(2,'V3_cwzl')">
                    购地信息(无)&nbsp;
                    <span>{{Management_info_msg.jckxy.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    购地信息(无)&nbsp;0
                </a>
            </template>

            <template v-if="Management_info_msg.jckxy ">
                <a class="" @click="scollTo(2,'V3_cwzl')">
                    土地转让(无)&nbsp;
                    <span>{{Management_info_msg.jckxy.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    土地转让(无)&nbsp;0
                </a>
            </template>

            <template v-if="Management_info_msg.jckxy ">
                <a class="" @click="scollTo(2,'V3_cwzl')">
                    债券信息(无)&nbsp;
                    <span>{{Management_info_msg.jckxy.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    债券信息(无)&nbsp;0
                </a>
            </template>
        </li>
        <li :class="{'active': isActive == 3}">
            <a class=" c_disable">
                经营异常(无)&nbsp;0
            </a>
            <template v-if="Management_risk_msg.gqczs">
                <a class="" @click="scollTo(3,'pledgeList')">
                    股权出质&nbsp;
                    <span>{{Management_risk_msg.gqczs.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class="c_disable">
                    股权出质&nbsp;0
                </a>
            </template>

            <template v-if="Management_risk_msg.aas || Management_risk_msg.bbs">
                <a class="" @click="scollTo(3,'xzcf')">
                    行政处罚&nbsp;
                    <span>{{(Management_risk_msg.aas? Management_risk_msg.aas.length :0) + (Management_risk_msg.bbs? Management_risk_msg.bbs.length : 0) }}</span>
                </a>
            </template>
            <template v-else>
                <a class="c_disable">
                    行政处罚&nbsp;0
                </a>
            </template>
            <a class=" c_disable">
                抽查检查(无)&nbsp;0
            </a>
            <a class=" c_disable">
                税收违法(无)&nbsp;0
            </a>
            <a class=" c_disable">
                动产抵押(无)&nbsp;0
            </a>
            <a class=" c_disable">
                环保处罚(无)&nbsp;0
            </a>
            <a class=" c_disable">
                清算信息(无)&nbsp;0
            </a>
            <a class=" c_disable">
                司法拍卖(无)&nbsp;0
            </a>
            <a class=" c_disable">
                土地抵押(无)&nbsp;0
            </a>
            <a class=" c_disable">
                简易注销(无)
            </a>
            <a class=" c_disable">
                公示催告(无)&nbsp;0
            </a>
        </li>
        <li  :class="{'active': isActive == 4}">
            <template v-if="newArr">
                <a v-for="(item,index) in newArr" @click="yearTo(4,index)">
                    {{item.year}}年度报告
                </a>
            </template>
            <template v-else>
               <a class="c_disable">
                   暂无年报
                </a>
            </template>     
          
        </li>
        <li :class="{'active': isActive == 5}">
            <template v-if="Knowledge_info_msg.sbDetails">
                <a class="" @click="scollTo(5,'brankMsg')">
                    商标信息&nbsp;<span>{{Knowledge_info_msg.sbDetails.total || 0}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    商标信息&nbsp;0
                </a>
            </template>

            <template v-if="Knowledge_info_msg.zlxq">
                <a class="" @click="scollTo(5,'zhuanli')">
                    专利信息&nbsp;<span>{{Knowledge_info_msg.zlxq.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    专利信息&nbsp;0
                </a>
            </template>

            <template v-if="Knowledge_info_msg.qyzs">
                <a class="" @click="scollTo(5,'qiyezs')">
                    证书信息&nbsp;<span>{{Knowledge_info_msg.qyzs.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    证书信息&nbsp;0
                </a>
            </template>

            <template v-if="Knowledge_info_msg.zpzzq">
                <a class="" @click="scollTo(5,'qiyezs')">
                    作品著作权&nbsp;<span>{{Knowledge_info_msg.zpzzq.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    作品著作权(无)&nbsp;0
                </a>
            </template>

            <template v-if="Knowledge_info_msg.zzqrz">
                <a class="" @click="scollTo(5,'rjzzq')">
                    软件著作权&nbsp;<span>{{Knowledge_info_msg.zzqrz.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    软件著作权&nbsp;0
                </a>
            </template>

            <template v-if="Knowledge_info_msg.gswz">
                <a class="" @click="scollTo(5,'websiteMsg')">
                    网站信息&nbsp;<span>{{Knowledge_info_msg.gswz.length}}</span>
                </a>
            </template>
            <template v-else>
                <a class=" c_disable">
                    网站信息&nbsp;0
                </a>
            </template>
        </li>
        <li v-if="Base_info_msg" :class="{'active': isActive == 6}">
            <a class="c_disable">历史工商信息&nbsp; </a>
            <a class="c_disable">
                历史对外投资&nbsp;0 </a>
            <a class="c_disable">
                历史股东&nbsp;
            </a>
            <a class="c_disable" >
                历史失信被执行人&nbsp;0
            </a>
            <a class="c_disable" >
                历史被执行人&nbsp;0
            </a>
            <a class="c_disable" >
                历史法院公告&nbsp;0
            </a>
            <a class="c_disable">
                历史裁判文书&nbsp;0
            </a>
            <a class="c_disable" >
                历史行政处罚&nbsp;0
            </a>
            <a class="c_disable" >
                历史动产抵押&nbsp;0
            </a>
            <a class="c_disable">
                历史开庭公告&nbsp;0
            </a>
            <a class="c_disable" >
                历史股权出质&nbsp;0
            </a>
            <a class="c_disable" >
                历史行政许可&nbsp;0
            </a>
        </li>
    </ul>
</template>
<script>
export default {
    props: {
        isActive: 0,
        Base_info_msg: "",
        Law_info_msg: "",
        Management_info_msg: "",
        Management_risk_msg: "",
        Year_report_msg: "",
        Knowledge_info_msg: ""
    },
    data() {
        return {
            newArr: ""
        };
    },
    created() {
        if(this.Year_report_msg.nbArr){
            this.newArr = this.Year_report_msg.nbArr.reverse();
        }
    },
    methods: {
        scollTo(num, ele) {
            this.$emit("update", num);
            setTimeout(() => {
                this.$emit("scoll", ele);
            }, 100);
        },
        yearTo(num1, num2) {
            this.$emit("update", num1);
            setTimeout(() => {
                this.$emit("switchYear", num2);
            }, 100);
        }
    }
};
</script>
<style scoped>
.hover_box {
    position: absolute;
    left: 0;
    top: 47px;
    width: 100%;
    background: #fcfcfd;
    overflow: hidden;
    z-index: 10;
    border-bottom: 1px solid #e7e9ef;
    border-right: 1px solid #e7e9ef;
}
.hover_box > li {
    float: left;
    width: 14.28%;
    border-left: 1px solid #e7e9ef;
    padding-left: 18px;
    height: 422px;
}
.hover_box > li.active {
    background: #ffffff;
}
.hover_box > li > a {
    display: block;
    padding-top: 4px;
    padding-bottom: 4px;
    font-size: 14px;
    letter-spacing: 0;
    color: #3c4144;
}
.hover_box > li > a:not(.c_disable):hover {
    color: #128bed;
}
.hover_box > li > a span {
    color: #128bed;
}

.hover_box > li > .c_disable {
    background: transparent;
    color: #999;
}
</style>