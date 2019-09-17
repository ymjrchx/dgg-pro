<template>
    <div class="content w">
        <div class="company">
            <div class="company_l fl">
                <img :src="storeMsg.logo ? storeMsg.logo : ''" :onerror="logo" alt="顶企查" class="logo_img">
            </div>
            <div class="company_m fl" v-if="storeMsg">
                <p>
                    {{storeMsg.name}}
                </p>
                <div class="row_2">
                    <span class="fl">电话：</span>
                    <span class="fl w80">{{storeMsg.qygsDetail ? getTel(storeMsg.tel,storeMsg.qygsDetail.contactInfoList[0].phoneNumber) : "暂无"}} </span>
                </div>
                <div class="row_2">
                    <span class="fl">官网：</span>
                    <span class="fl w80">{{storeMsg.qygsDetail.contactInfoList ? (storeMsg.qygsDetail.contactInfoList)[0].webSiteUrl || "暂无" :"暂无"}}</span>
                </div>
                <div class="row_2">
                    <span class="fl">邮箱：</span>
                    <!-- <span class="fl w80">{{storeMsg.qygsDetail.contactInfoList ? (storeMsg.qygsDetail.contactInfoList)[0].email || "暂无" :"暂无"}}</span> -->
                     <span class="fl w80">{{storeMsg.email || "暂无"}}</span>
                </div>
                <div class="row_2">
                    <span class="fl">地址：</span>
                    <span class="fl w80">{{storeMsg.qygsDetail.address || "暂无"}} <img @click="goMap(storeMsg.qygsDetail.address)" src="../assets/images/company-map.png" style="width:22px;margin-left:10px;cursor:pointer"> </span>
                </div>
                <div class="row_1">
                    <span class="fl">简介：</span>
                    <span class="fl w80">{{storeMsg.briefIntroductionInfo ? storeMsg.briefIntroductionInfo.Content : "暂无"}}</span>
                </div>
            </div>
            <div class="company_r fl">
                <a data-toggle="modal" @click="test"  class="c_iconDt ca_contact">
                    <span></span>笔记
                    <em id="nNodeCount"></em>
                </a>
                <a data-toggle="modal" @click="test"  class="c_iconDt ca_compare">
                    <span></span>对比
                </a>
                <a  @click="flollowFunc" class="c_iconDt ca_focus" :class="{'ca_active':isFollowId}" title="关注公司">
                    <span></span>{{isFollowId ? '已关注' : '关注'}}
                </a>
                <a @click="test" class="c_iconDt ca_jk">
                    <span></span>监控
                </a>
                <p class="m-t" title="上次更新日期：2天前" style="font-size: 15px;color: #444;position: absolute;right: 0px;margin:15px 0 10px;">
                    <a :class="{'m_bt_refresh':true,'m_bt_refresh_loading':isRefresh}" @click="refreshFunc"> </a>
                   {{refreshStr ? '刚刚更新' :'2天前更新'}} 
                </p>
            </div>
        </div>
        <!-- 下面两个部分 -->
        <div class="tab-content main">
            <!-- 左边边部分 -->
            <div class="tab-content_l fl" id="tab_title">
                <div class="card" :class="{isScollto: isHasScoll}" @mouseenter="testEnter" @mouseleave="testLeave">
                    <div :class="{active:isTab == 0}" @click="tab(0)">
                        基本信息 <span>{{Base_info_msg ? numTotal(Base_info_msg) :"" }}</span>            
                    </div>
                    <div :class="{active:isTab == 1}" @click="tab(1)">
                        法律诉讼 <span>{{Law_info_msg ? numTotal(Law_info_msg) :"" }}</span>                            
                    </div>
                    <div :class="{active:isTab == 2}" @click="tab(2)">
                        经营状况 <span>{{Management_info_msg ? numTotal(Management_info_msg) :"" }}</span>                           
                    </div>
                    <div :class="{active:isTab == 3}" @click="tab(3)">
                        经营风险 <span>{{Management_risk_msg ? numTotal(Management_risk_msg) :"" }}</span>                             
                    </div>
                    <div :class="{active:isTab == 4}" @click="tab(4)">
                        企业年报 <span>{{Year_report_msg.nbArr ? Year_report_msg.nbArr.length : 0}}</span>                         
                    </div>
                    <div :class="{active:isTab == 5}" @click="tab(5)">
                        知识产权 <span>{{Knowledge_info_msg ? numTotal(Knowledge_info_msg) :"" }}</span>     
                    </div>
                    <div :class="{active:isTab == 6}" @click="tab(6)">
                        历史信息 <span>0</span> 
                    </div>
                    <MinTab v-if="minTab" v-show="minTabshow" :isActive="isTab" @scoll="allScoll" @update="updateTab" @switchYear="switchYear"
                    :Base_info_msg="Base_info_msg" :Law_info_msg="Law_info_msg" :Management_info_msg="Management_info_msg" :Management_risk_msg="Management_risk_msg"
                    :Year_report_msg="Year_report_msg" :Knowledge_info_msg="Knowledge_info_msg"/>
                </div>
                <div style="height:48px;width:100%" v-show="isHasScoll" ></div>
                <div>
                    <div v-if="Base_info_msg" v-show="isTab == 0" >
                        <Base_info :msg="Base_info_msg" @scoll="allScoll" :keyWord='keyWord'/>
                    </div>
                    <div v-if="Law_info_msg" v-show="isTab == 1" >
                        <Law_info :msg="Law_info_msg" @scoll="allScoll" :keyWord='keyWord'/>
                    </div>
                    <div v-if="Management_info_msg" v-show="isTab == 2">
                        <Management_info :msg="Management_info_msg" @scoll="allScoll"  :keyWord='keyWord'/>
                    </div>
                    <div v-if="Management_risk_msg" v-show="isTab == 3" >
                        <Management_risk :msg="Management_risk_msg" @scoll="allScoll"  :keyWord='keyWord'/>
                    </div>
                    <div v-if="Year_report_msg" v-show="isTab == 4" >
                        <Year_report :msg="Year_report_msg" @scoll="allScoll" :yearTab="yearTab" @onlyYear="onlyYear"  :keyWord='keyWord'/>
                    </div>
                    <div v-if="Knowledge_info_msg" v-show="isTab == 5" >
                        <Knowledge_info :msg="Knowledge_info_msg" @scoll="allScoll"  :keyWord='keyWord'/>
                    </div>
                    <div v-show="isTab == 6">
                        <History @scoll="allScoll"  :keyWord='keyWord'/>
                    </div>

                </div>
                <section class="panel b-a clear last">
                    <div class="m_ptsc" style="padding:20px 0;">数据来源：国家企业信用信息公示系统。</div>
                </section>
            </div>
            <div class="tab-content_r fr">
                <div class=" m_rightPanels">
                    <div class="panel b-a" id="fapiao-title" v-if="storeMsg.fptts">
                        <div class="panel-heading b-b btab">
                            <a data-href="#qiyeQrcode" data-toggle="tab" :class="{'active' :codeIndex==0}" style="display:none">
                                <h2>企业二维码</h2>
                            </a>
                            <a data-href="#fapiaoQrcode"  data-toggle="tab" :class="{'active' :codeIndex==1}" >
                                <h2>企业发票抬头</h2>
                            </a>
                        </div>
                        <div class="panel-body text-center">
                            <div id="qiyeQrcode" class=""  v-show="codeIndex == 0">
                                <div id="qiye_taitou" class="m_qrp" title="">
                                    <img src="data:image/png;base64,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" style="display: block;"></div>
                                <div class="m-t-xs text-dark m-b m_qrp-t">顶企查APP扫一扫查看企业详情</div>
                            </div>
                            <div id="fapiaoQrcode" class="active" v-show="codeIndex == 1">
                                
                                <div class="m-t-md TaxView" style="padding-top:15px">
                                    <p class="text-left">名称&nbsp;:&nbsp;
                                        <span class="Name">{{storeMsg.fptts.name}}</span>
                                    </p>
                                    <p class="text-left">税号&nbsp;:&nbsp;
                                        <span class="CreditCode">{{storeMsg.fptts.no}}</span>
                                    </p>
                                    <p class="text-left">地址&nbsp;:&nbsp;
                                        <span class="Address">{{storeMsg.fptts.address}}</span>
                                    </p>
                                    <p class="text-left">电话&nbsp;:&nbsp;
                                        <span class="PhoneNumber">{{storeMsg.fptts.phoneNo}}</span>
                                    </p>
                                    <p class="text-left">开户银行&nbsp;:&nbsp;
                                        <span class="Bank">{{storeMsg.fptts.bank}}</span>
                                    </p>
                                    <p class="text-left">银行账户&nbsp;:&nbsp;
                                        <span class="Bankaccount">{{storeMsg.fptts.bankAccount}}</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel b-a">
                        <div class="panel-heading b-b">
                            <span class="font-bold font-15 text-dark">
                                <h2>下载报告</h2>
                            </span>
                        </div>
                        <div class="panel-body">
                            <a > <img src="../assets/images/report.png" style="max-width:95%;margin:10px 0 20px 20px;"> </a>
                            <a  class="btn btn-primary basePageBt" @click="test">
                                下载报告
                            </a>
                        </div>
                    </div>
                    <section class="panel b-a">
                        <div class="panel-heading b-b">
                            <span class="font-bold font-15 text-dark">
                                <h2>企业图谱</h2>
                            </span>
                        </div>
                        <div class="panel-body">
                            <a  > <img src="../assets/images/qytu.jpg" style="max-width:95%;margin:10px 0 20px;"> </a>
                            <a  @click="test" class="btn btn-primary basePageBt" >查看企业图谱</a>
                        </div>
                    </section>
                    <section class="panel b-a">
                        <div class="panel-heading b-b">
                            <span class="font-bold font-15 text-dark">
                                <h2>投资族谱</h2>
                            </span>
                        </div>
                        <div class="panel-body">
                            <a > <img src="../assets/images/tupu.png" style="max-width:95%;margin:10px 0 20px;"> </a>
                            <a class="btn btn-primary basePageBt"  @click="test">查看投资族谱</a>
                        </div>
                    </section>
                    <section class="panel b-a">
                        <div class="panel-heading b-b">
                            <span class="font-bold font-15 text-dark">
                                <h2>关联图谱</h2>
                            </span>
                        </div>
                        <div class="panel-body">
                            <a > <img src="../assets/images/muhou.png" style="max-width:95%;margin:10px 0 20px;"> </a>
                            <a  @click="test" class="btn btn-primary basePageBt" >查看关联图谱</a>
                        </div>
                    </section>
                    <!-- <section class="panel b-a">
                        <div class="panel-heading b-b">
                            <span class="font-bold font-15 text-dark">
                                <h2>您可能感兴趣的公司</h2>
                            </span>
                        </div>
                        <ul class="list-group no-bg auto">
                            <a data-href="/firm_8f99299da4d34d3af4e1b2bcda1aa952" target="_blank" class="list-group-item clearfix">
                                <span class="clear">
                                    <span>福建省龙兴房地产开发有限公司</span><br> </span>
                            </a>
                            <a data-href="/firm_3ae53b5d9f32928cb3b5f8055b1a9a9a" target="_blank" class="list-group-item clearfix">
                                <span class="clear">
                                    <span>永康市中安投资有限公司</span><br> </span>
                            </a>
                            <a data-href="/firm_4f3670b679d7c2d9c295e09d4f5503a5" target="_blank" class="list-group-item clearfix">
                                <span class="clear">
                                    <span>太和县供电局倪邱供电所</span><br> </span>
                            </a>
                            <a data-href="/firm_f0ccfd149b252d40b157bb916cbe95b3" target="_blank" class="list-group-item clearfix">
                                <span class="clear">
                                    <span>高唐县王小二手工凉皮店</span><br> </span>
                            </a>
                            <a data-href="/firm_05215fd012fe34f8b66193c3b9a20708" target="_blank" class="list-group-item clearfix">
                                <span class="clear">
                                    <span>济南美林医疗设备有限公司</span><br> </span>
                            </a>
                        </ul>
                    </section> -->
                </div>
            </div>
            <!-- 右边部分 -->
        </div>
    </div>
</template>
<script>

import MinTab from "@/pages/Min_tab"; //小导航
import Base_info from "@/pages/deatil_tab/Base_info"; //详情-基本信息
import History from "@/pages/deatil_tab/History"; //详情-历史信息
import Knowledge_info from "@/pages/deatil_tab/Knowledge"; //详情-知识产权
import Law_info from "@/pages/deatil_tab/Law_info"; //详情-法律诉讼
import Management_info from "@/pages/deatil_tab/Management_info"; //详情-经营状况
import Management_risk from "@/pages/deatil_tab/Management_risk"; //详情-经营风险
import Year_report from "@/pages/deatil_tab/Year_report"; //详情-企业年报

import Common,{Jump} from "@/util/common.js"
import {IsLogin} from "@/util/common"
import userPhoto from "@/assets/images/default.jpg";
export default {
    components: {
        Base_info,MinTab,
        History,
        Knowledge_info,
        Law_info,
        Management_info,
        Management_risk,
        Year_report
    },
    data() {
        return {
            isRefresh: false,
            refreshStr:0,
            logo: 'this.src="' + userPhoto + '"',
            isTab: 0,
            storeMsg: "",
            Base_info_msg: "",
            Law_info_msg: "",
            Management_info_msg:"",
            Management_risk_msg:"",
            Year_report_msg:"",
            Knowledge_info_msg:"",
            codeIndex:1,
            isHasScoll:false,
            minTab:false,
            minTabshow:false,
            hoverTime:"",  //计时器，算时间的
            yearTab:0,
            isFollowId:0, //是否存在关注了公司的Id
            isLoading:0, //防止双击
        };
    },
    created() {
        this.initPage(this.$route.query);
        this.keyWord=this.$route.query
        window.onscroll= ()=>{
            //变量t是滚动条滚动时，距离顶部的距离
            let t = document.documentElement.scrollTop||document.body.scrollTop;
            let scrollup = document.getElementById('tab_title').offsetTop;
            //当滚动到距离顶部200px时，返回顶部的锚点显示
            // console.log("滚动数",t,scrollup)
            if(t<=scrollup){
                this.isHasScoll=0;
            }else{          //恢复正常
                this.isHasScoll=1;
            }
        }
    },
    destroyed(){
        window.onscroll=''
    },
    methods: {
         test(){
            this.$global.alertMessage()
      },
        testEnter(){
            this.hoverTime=setTimeout(()=>{
                 this.minTabshow=1
            },600)
        },
        testLeave(){
           
           if(this.minTabshow == 1){
               this.minTabshow = 0
           }
           else{
               clearTimeout(this.hoverTime)
           }
        },
        initPage(data) {
            //初始化页面
           
            let _this = this;
            let requireUrl = data.type == "quali" ?  "companyAssets/entlibNameDetail" : "company/searchDetail";
            let requireType = data.type == "quali" ?  "get" : "post";
            let requireData = data.type == "quali" ?  { companyId: data.id } : { companyId: data.id ,companyName: data.title };
             this.$axios({
                type:requireType,
                url:requireUrl,
                data:requireData,
                success:res => {
                    //  console.log("详情数据", res);
                   
                    if(res.data.code == 0){
                        let params = this.storeMsg = res.data.data[0];
                        this.Base_info_msg = Object.assign({},params.qygsDetail,{frname:params.qygsDetail.operName,dwtz:params.qyzp}); //基本信息

                        this.Management_info_msg =Object.assign({},params.jyzk ? params.jyzk[0]: '') ;  //经营状况
                        this.Knowledge_info_msg=Object.assign({},{sbDetails:params.brandData,zlxq:params.zlxq,qyzs:params.qyzs,zzqrz:params.zzqrz,gswz:params.gswz,});  //知识产权,
                        this.Law_info_msg =Object.assign({},{"cpws":params.cpws,'fygg':params.fygg,'ktgg':params.ktgg,'sxbzxr':params.sxbzxr,bzxr:params.bzxr}) ; //法律诉讼
                        // this.Management_info_msg=params.qygsDetail;  //历史信息,
                        this.Management_risk_msg=Object.assign({},params.jyfx ? params.jyfx[0] : "");  //经营风险
                     
                        let yearRe =[];
                        if(params.qynbDetail){
                             for(let item of params.qynbDetail){
                                if(item.year) yearRe.push(item);
                            }
                        }
                        this.Year_report_msg={nbArr:yearRe,commonCode:params.qygsDetail.creditCode};  //企业年报


                        this.minTab=1;
                    }
                    else{
                        Common.alertMessage("温馨提示",res.data.msg)
                    }
                   
                }
            },1);
           
        },
        getTel(str1,str2){
            if(str1 && str1 !="更多号码" ) return str1;
            else if(str2){
                let arr = str2.replace(/-/g,"").split("、");
                // if (arr[0] == "更多号码" || arr[0]=="" || arr[0]=="-") return arr[1];
                for(let tel of arr){
                    if(tel.length > 6){
                        let newArr =[...tel]
                        if(!this.$store.state.isLogin){
                            newArr[3]="*";
                            newArr[4]="*";
                            newArr[5]="*";
                            newArr[6]="*";
                        }
                        return newArr.join("")
                    } 
                   
                }
               
            }
            else return "暂无"
        },
        tab(num) {

            clearTimeout(this.hoverTime)
            this.minTabshow = 0

            Jump(document.getElementById('tab_title'))
            let tabNum = this.isTab;
            if (num == tabNum) return false;
            this.isTab = num;
        },
        updateTab(num){
            this.isTab = num;
        },
        switchYear(num){
            this.yearTab=num;
            this.minTabshow=0;
            Jump(document.getElementById('tab_title'))
        },
        onlyYear(num){
            if(this.yearTab == num) return ;
            this.yearTab = num;
        },
        refreshFunc() {
            this.isRefresh = true;
            setTimeout(() => {
                this.isRefresh = 0;
                this.refreshStr=1;
            }, 1500);
        },
       allScoll(ele){
            Jump(document.getElementById(ele),-50)
            this.minTabshow=0
       },
       numTotal(obj){
           let num=0;
            for(let key  in obj ){
                if( obj[key] instanceof Array){
                    if(key == "contactInfoList") continue;
                    num+=obj[key].length
                }
                if( (obj[key]) instanceof Object && (obj[key]).hasOwnProperty('list')){
                    num+=Number(obj[key].total)
                  
                }
            }

            return num
       },
       goMap(str){
           if(str){
               window.open("http://map.baidu.com/?newmap=1&ie=utf-8&s=s%26wd%3D"+str+"%20")
           }
       },
       flollowFunc(){  //关注
          
            let _this = this;
            if(IsLogin(_this)) return ;

            if(this.isLoading) return;
            else this.isLoading = 1;

            let obj;

            if(!this.isFollowId){
                obj=Object.assign({},{enterpriseId:this.$route.query.id,userId:this.$store.state.userInfo.userId});

                this.$axios({"type":'get',url:'api/userFocusEnterprise/addUserFocus.do',data:obj,success:res=>{
                
                    if(res.data.code == 0 ){
                        this.isFollowId=res.data.data;
                       this.$notify({
                            title: '',
                            message: '关注成功',
                            type: 'success',
                            duration:2000
                            });
                    } 
                    else{
                        this.$message.error(res.data.msg);
                    }
                    this.isLoading = 0; 
                }})
            }
            else{
                obj=Object.assign({},{userFocusEnterpriseId:this.isFollowId,userId:this.$store.state.userInfo.userId});

                this.$axios({"type":'get',url:'api/userFocusEnterprise/cancelFocus.do',data:obj,success:res=>{
                   if(res.data.code == 0 ){
                       this.isFollowId=0
                   } 
                    else this.$message.error(res.data.msg);
                    this.isLoading = 0; 
                }})
            }
       }
    }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style >
@import "../assets/detail.css";
@import "../assets/company_detail";
.clear {
    display: block;
}
.isScollto{
    position: fixed;
    top: 0;
    z-index: 20;
    width: 938px !important;
}

</style>
