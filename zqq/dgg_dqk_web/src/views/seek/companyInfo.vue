<template>
    <div class="workBeach_d">
        <section class="head clear2" v-if='storeMsg' style="height:170px">
            <div class="imgBox fl">
                <img :src="storeMsg.logo ? storeMsg.logo : ''" :onerror='errorImg' alt="公司">
            </div>
            <div class="fl InfoBox" ref='companyName'>
                <h1>{{storeMsg.name}}</h1>
                <p>
                    <span>经营状态：{{isHas(storeMsg.qygsDetail && storeMsg.qygsDetail.status)}}</span>
                    <span>成立日期：{{isHas(storeMsg.qygsDetail && storeMsg.qygsDetail.startDate)}}</span>
                    <span>企业官网：<a :href="storeMsg.qygsDetail && storeMsg.qygsDetail.contactInfoList ? (storeMsg.qygsDetail.contactInfoList)[0].webSiteUrl : ''" class="col" target="_blank">{{ (storeMsg.qygsDetail && storeMsg.qygsDetail.contactInfoList) ? (storeMsg.qygsDetail.contactInfoList)[0].webSiteUrl || "暂无" :"暂无"}}</a></span>
                </p>
                <p>企业地址：{{ isHas(storeMsg.qygsDetail && storeMsg.qygsDetail.address) || "暂无"}}
                    <a :href="'http://map.baidu.com/?newmap=1&ie=utf-8&s=s%26wd%3D'+(storeMsg.qygsDetail && storeMsg.qygsDetail.address)" class="col ml10 f20"
                        target="_blank"><i class="el-icon-location col"></i></a>
                </p>
            </div>
        </section>
        <section class="clear2 content" style="margin-bottom:0" ref='jun'>
            <div class="part thred">
                <el-tabs v-model="fristTab" type="card">
                    <el-tab-pane label="企业信息" name="first">
                        <div class="tab-content main" id="tab_title" ref='tab_title'>
                            <!-- 企业信息部分 start-->
                            <div class="tab-content_l fl">
                                <div class="card" :class="{isScollto: (isHasScoll && !faId)}"  :style="(isHasScoll && !faId)  ? scorllWidth : ''"  @mouseenter="tabEnter" @mouseleave="tabLeave">
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
                                    <MinTab v-if="minTab" v-show="minTabshow" :isActive="isTab" @scoll="allScoll"
                                        @update="updateTab" @switchYear="switchYear" :Base_info_msg="Base_info_msg"
                                        :Law_info_msg="Law_info_msg" :Management_info_msg="Management_info_msg"
                                        :Management_risk_msg="Management_risk_msg" :Year_report_msg="Year_report_msg"
                                        :Knowledge_info_msg="Knowledge_info_msg" />
                                </div>
                                <div style="height:48px;width:100%" v-show="(isHasScoll && !faId)"></div>
                                <div>
                                    <div v-if="Base_info_msg" v-show="isTab == 0">
                                        <Base_info :msg="Base_info_msg" @scoll="allScoll"  />
                                    </div>
                                    <div v-if="Law_info_msg" v-show="isTab == 1">
                                        <Law_info :msg="Law_info_msg" @scoll="allScoll"  />
                                    </div>
                                    <div v-if="Management_info_msg" v-show="isTab == 2">
                                        <Management_info :msg="Management_info_msg" @scoll="allScoll"  />
                                    </div>
                                    <div v-if="Management_risk_msg" v-show="isTab == 3">
                                        <Management_risk :msg="Management_risk_msg" @scoll="allScoll"  />
                                    </div>
                                    <div v-if="Year_report_msg" v-show="isTab == 4">
                                        <Year_report :msg="Year_report_msg" @scoll="allScoll" :yearTab="yearTab"
                                            @onlyYear="onlyYear"  />
                                    </div>
                                    <div v-if="Knowledge_info_msg" v-show="isTab == 5">
                                        <Knowledge_info :msg="Knowledge_info_msg" @scoll="allScoll"  />
                                    </div>
                                    <div v-show="isTab == 6">
                                        <History @scoll="allScoll"  />
                                    </div>
                                </div>
                                <section class="panel b-a clear last">
                                    <div class="m_ptsc" style="padding:20px 0;">数据来源：国家企业信用信息公示系统。</div>
                                </section>
                            </div>    
                        </div>

                    </el-tab-pane>
                    <!-- <el-tab-pane label="跟进记录" name="second">跟进记录(暂无)</el-tab-pane> -->
                    <el-tab-pane label="联系方式" name="third">
                        <div class="contactBox">
                            <div v-if='contactArr.length > 0'>
                                <p class="contact_i" v-for='(item,idx) in  contactArr' :key='idx'>
                                    <span>联系电话：{{item}}</span>
                                </p>
                                <p class="contact_i">
                                    <span>联系邮箱：{{storeMsg.email ? storeMsg.email != "null" ?  storeMsg.email : "暂无" : '暂无'}}</span>
                                </p>
                            </div>
                           
                            <div class="cover" v-if='contactArr.length > 0'>
                                <img src="../../assets/images/lock.png" alt="">
                                <p><a href="javascript:void(0)" @click="getThread" class="col">领取线索</a>以查看联系方式</p>
                            </div>
                            <div v-else>
                                暂无相关数据
                            </div>
                        </div>
                        
                    </el-tab-pane>
                    <!-- <el-tab-pane label="员工人脉" name="fourth">员工人脉(暂无)</el-tab-pane> -->
                </el-tabs>
            </div>
            <div class="sellList">
                <!-- <div class="asideList">
                    <h2>猜你喜欢 <span class="col fr cur" @click="exchangeElse"><i class="iconfont" :class="[zhuan && 'loading']">&#xe7b8;</i>
                            换一批</span></h2>
                    <ul>
                        <li><a href="#">神马企业管理成都有限公司</a></li>
                        <li><a href="#">神马企业管理成都有限公司</a></li>
                        <li><a href="#">神马企业管理成都有限公司</a></li>
                        <li><a href="#">神马企业管理成都有限公司</a></li>
                        <li><a href="#">神马企业管理成都有限公司</a></li>
                        <li><a href="#">神马企业管理成都有限公司</a></li>
                    </ul>
                </div> -->
                <div class="asideList">
                    <h2>最近浏览</h2>
                    <ul>
                        <li v-for="(item,idx) in hisToryArr" :key='idx'>
                            <a @click="$newPage('/seek/companyInfo',{id:item.value.id})" href="javascript:void(0)">{{item.value.name}}</a>
                        </li>
                    </ul>
                </div>
            </div>
        </section>
    </div>
</template>
<script>
    import nofound from '@/assets/images/noFind.png'
    import {hisTory} from '@/assets/js/util.js'

    import MinTab from "@/components/deatil_tab/Min_tab"; //小导航
    import Base_info from "@/components/deatil_tab/Base_info"; //详情-基本信息
    import History from "@/components/deatil_tab/History"; //详情-历史信息
    import Knowledge_info from "@/components/deatil_tab/Knowledge"; //详情-知识产权
    import Law_info from "@/components/deatil_tab/Law_info"; //详情-法律诉讼
    import Management_info from "@/components/deatil_tab/Management_info"; //详情-经营状况
    import Management_risk from "@/components/deatil_tab/Management_risk"; //详情-经营风险
    import Year_report from "@/components/deatil_tab/Year_report"; //详情-企业年报

    export default {
        props:{
            faId:{
                default:""
            }
        },
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
                fristTab:"first",
                zhuan: false,
                errorImg: `this.src="${nofound}"`,
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
                isLoading:0, //防止双击
                scorllWidth:"",
                hisToryArr:[],
                contactArr:[],
                companyId:""
            }
        },
        watch:{
            faId(val){
                this.companyId = val
                this.initPage();
                this.initTel();
            }
        },
        created() {

            this.companyId = this.faId ? this.faId : this.$route.query.id

            this.initPage();
            this.hisToryArr = hisTory.get('historyCompany')
            this.initTel();
        },
        mounted(){
            window.addEventListener("scroll", this.handleScroll);
            window.addEventListener("resize", this.resizeFunc);
          
           this.resizeFunc()
        },
        methods: {
            resizeFunc(){
                  this.scorllWidth={'width':this.getStyle( document.getElementById('tab_title'),'width')}
            },
            getStyle(element, property){
                var proValue = null;
                if (!document.defaultView) {
                    proValue = element.currentStyle[property];  //IE
                } else {
                    proValue = document.defaultView.getComputedStyle(element)[property]; //非IE
                }
                return proValue;
            },
           
            exchangeElse() { //换一批
                this.zhuan = true
                setTimeout(() => {
                    this.zhuan = false
                }, 3000)
            },
            initTel(){
                this.$Api('senior/searchBaseGsDetail',{companyId: this.companyId },'get',true).then(res=>{
                    let str=res.data[0].commercial.companyTel
                    if(str){
                        this.contactArr = (str+"").split(',')
                    }
                    else{
                         this.contactArr = []
                    }
                })
            },
            initPage(data) {
                    //初始化页面
                // this.$route.query.id   0b3393e7a5ab0dafa8218f265cdf1ed0
                this.$Api('/senior/searchSimpleDetail',{companyId: this.companyId},'get',true).then(res=>{
                        let params = this.storeMsg =res.data[0]
                        if(params.qygsDetail){
                            this.Base_info_msg = Object.assign({},params.qygsDetail,{frname:params.qygsDetail.operName,dwtz:params.qyzp}); //基本信息
                            let yearRe =[];
                            if(params.qynbDetail){
                                for(let item of params.qynbDetail){
                                    if(item.year) yearRe.push(item);
                                }
                            }
                            this.Year_report_msg={nbArr:yearRe,commonCode:params.qygsDetail.creditCode};  //企业年报
                        }
                     
                        this.Management_info_msg =Object.assign({},params.jyzk ? params.jyzk[0]: '') ;  //经营状况
                        this.Knowledge_info_msg=Object.assign({},{sbDetails:params.brandData,zlxq:params.zlxq,qyzs:params.qyzs,zzqrz:params.zzqrz,gswz:params.gswz,});  //知识产权,
                        this.Law_info_msg =Object.assign({},{"cpws":params.cpws,'fygg':params.fygg,'ktgg':params.ktgg,'sxbzxr':params.sxbzxr,bzxr:params.bzxr}) ; //法律诉讼
                        // this.Management_info_msg=params.qygsDetail;  //历史信息,
                        this.Management_risk_msg=Object.assign({},params.jyfx ? params.jyfx[0] : "");  //经营风险
                    
                      
                        this.minTab=1;
                        hisTory.set("historyCompany",{'name':res.data[0].name,id: this.companyId})
                })
            },
            tabEnter(){
                this.hoverTime=setTimeout(()=>{
                    this.minTabshow=1
                },600)
            },
            tabLeave(){
                if(this.minTabshow == 1){
                    this.minTabshow = 0
                }
                else{
                    clearTimeout(this.hoverTime)
                }
            },
            tab(num) {

                clearTimeout(this.hoverTime)
                this.minTabshow = 0

                if(!this.faId) this.$jump(this.$refs.tab_title,230)

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
                if(!this.faId) this.$jump(this.$refs.tab_title)
            },
            onlyYear(num){
                if(this.yearTab == num) return ;
                this.yearTab = num;
            },
            allScoll(ele){
                if(!this.faId) this.$jump(document.getElementById(ele),230)
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
            handleScroll(){
                let t = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
                let scrollup = this.$refs.tab_title.offsetTop+260;
                    
                //当滚动到距离顶部200px时，返回顶部的锚点显示
                // console.log("滚动数",t,scrollup)
                if(t<=scrollup){
                    this.isHasScoll=0;
                }else{          //恢复正常
                    this.isHasScoll=1;
                }
            },
            getTel(str1,str2){
                if(str1 && str1 !="更多号码" && str1!= 'null' ) return str1;
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
                            return newArr.join("") == 'null' ? "--" : newArr.join("")
                        } 
                    
                    }
                
                }
                else return "暂无"
            },
            isHas(str){
                return str ? str == 'null' ? "--" : str : "--"
            },
            getThread() {
                if(!this.$store.state.userInfo.isBind){
                    this.$message.warning('请先绑定工号！');
                    setTimeout(()=>{
                        this.$store.commit('SET_BINGING',true)
                    },1000)
                  
                    return;
                }
                this.$Api('clueApi/turnClue.do',{cluesJson:'["'+this.companyId+'"]'},'post',1,1).then(res =>{
                    this.$message.success('领取成功！');
                })
            },
        },
        destroyed() {
            window.removeEventListener("scroll", this.handleScroll);
            if (this.Loading) this.Loading.close();
        }
    }
</script>

<style lang="stylus">

@import "../../assets/css/detail.css";
@import "../../assets/css/company_detail.css";

    .workBeach_d{

        .head {
            position: relative;
            width: 100%;
            padding: 30px 30px 0;
            background-color: #fff;

            .imgBox {
                margin-right 40px 
                width 100px;

                img {
                    max-width 100%;
                }
            }

            .InfoBox {
                width: 850px;
                h1 {
                    color: #404040;
                    font-size: 24px;
                    font-weight: 700;
                    margin-bottom 20px;
                }

                p {
                    margin-bottom 10px;

                    span {
                        margin-right: 30px
                    }
                }
            }
        }

        .content {
            margin 14px;

            .part {
                background #fff;
                padding 20px;
            }

            //线索状态
            .thred {
                width: 78%;
                float: left;
            }

            .sellList {
                width 22%;
                float left;
                padding-left 14px;

                .asideList {
                    background #fff;
                    margin-bottom 14px;

                    h2 {
                        border-bottom: 1px solid #e8e8e8;
                        background: #f7f7f7;
                        font-size: 13px;
                        height: 46px;
                        line-height 46px;
                        padding 0 20px;
                    }

                    ul {
                        padding 0 20px;

                        li {
                            width: 100%;
                            padding 16px 0;
                            border-bottom: 1px solid #e8e8e8;
                        }
                    }
                }
            }
        }
    }
.isScollto {
    position: fixed;
    top: 60px;
    z-index: 20;
    // width: 938px!important;
}
.contactBox{
    min-height 500px;
    position relative;
    margin-top:35px;
    .contact_i{
        float left;
        width:50%;
        font-size:16px;
         line-height 30px;
    }
    .cover{
        position absolute;
        left:0;
        top:0;
        width:100%;
        background :rgba(255,255,255,.8);
        padding-top:80px;
        text-align:center;
        p{
            margin-top:20px;
            font-size:16px;
           
        }
    }
}

</style>