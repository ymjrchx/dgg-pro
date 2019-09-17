<template>
    <div class="search_list_body">
        <!-- 商标，专利....搜出的列表的头部  开始 -->
        <div class="sel-bar" style="background:white;">
            <div class="container">
                <div class="top-search-nav clear col-md-9">
                    <div class="pull-left">
                        <a   v-for="(item,idx) in styleArr" :key="idx" :class="{'active': idx == isCheck}" @click="switchTab(idx)">{{item}}</a>
                        <!-- <router-link to="/Qualifications_box">商标</router-link> -->
                    </div>
                    <div class="col-md-7 pull-right">
                        <div class="form-group m-b-none">
                            <div class="input-group">
                                <input type="text" class="form-control input-lg" style="font-size: 14px;" name="key" placeholder="请输入你想要查找的信息" 
                                autocomplete="off" v-model="keyVal" @keyup.enter.native="submit">
                                <span class="input-group-btn">
                                    <button type="submit" class="input-lg btn-top icon-search" @click="goSearch" @keyup.enter.native="goSearch"></button>
                                </span>
                            </div>
                        </div>
                        <section class="panel" id="search-list" style="width: 490px;top: 40px;"> </section>
                    </div>
                </div>
            </div>
        </div>
        <!-- 商标，专利....搜出的列表的头部 结束-->
        <div class="container m-t-md">
            <div class="row">
                <div class="col-md-12">
                    <ul class="breadcrumb breadcrumb2" style="background:none;border:none;">
                        <li>
                            <a @click="goIndex">
                                <img src="@/assets/images/ic_reply.png">&nbsp;首页</a>
                        </li>
                        <li>
                            <a @click="back">{{styleArr[isCheck]}}查询</a>
                        </li>
                        <li>
                             <a @click="goBack">{{keyWord}} 查询列表</a>
                        </li>
                         <li v-if="hasDetail">
                            <h1>详情</h1>
                        </li>
                    </ul>
                </div>
                <!-- 路由替换中间部分 -->
                <div class="col-md-9 sel-list ajaxlist" id="ajaxlist">
                    <router-view></router-view>
                </div>
                <!-- 路由替换中间部分 -->
                <div class="col-md-3 hidden-xs recommends">
                    <section class="panel b-a">
                        <div class="panel-heading b-b">
                            <h2 class="font-bold">您可能感兴趣的公司</h2>
                        </div>
                        <ul class="list-group no-bg auto" v-if="aboutArr">
                            <a v-for="(item,idx) in aboutArr" :key='idx'  class="list-group-item clearfix" @click="goCompanyDetail(item.companyId,item.name)">
                                <span class="clear">{{item.name}}</span>
                            </a>
                        </ul>
                    </section>
                </div>
            </div>
        </div>
    </div>
</template>
<script>

import Common,{Jump} from "@/util/common";

export default {
    components: {},
    data() {
        return {
            keyVal:"",
            keyWord:"",
            aboutArr:[],
            styleArr: ["税号","商标","专利"],
            hasDetail:0,
            isCheck:0
        };
    },
   watch:{
       $route(to,from){
        //    console.log('to',to)
        //    console.log('from',from)
          if(to.path == from.path){
              this.changgeKey()
          }
          if(to.path == "/Brand_detail" || to.path == "/Patent_detail") this.hasDetail =1
          if(from.path == "/Brand_detail" || from.path == "/Patent_detail") this.hasDetail =0
       }
   },
    created(){
        document.onkeydown = e => {
            let keyNum = window.event ? e.keyCode : e.which;
            if (keyNum == 13) {
                this.goSearch();
            }
        };
        this.keyVal=this.keyWord=this.$route.query.keyWord
        switch(this.$route.query.type){
            case "tax":this.isCheck = 0 ;this.$router.push({path:'/TaxList',query:this.$route.query});break;
            case "brand":this.isCheck = 1;this.$router.push({path:'/BrandList',query:this.$route.query});break;
            case "patent":this.isCheck = 2;this.$router.push({path:'/PatentList',query:this.$route.query});break;
            default:this.isCheck = 3
        }
        this.initAbout()
    },
     destroyed(){
        document.onkeydown = e => { };
    },
    methods: {
        initAbout(){
            let obj = {
                keyWord:sessionStorage.getItem("aboutKey"),
                count: 5,
                type: 'qyn'
            };
            this.$axios({
                type: "get",
                url: "company/searchFive",
                data: obj,
                success: res => {
                    if (res.data.code == 0) {
                        this.aboutArr=res.data.data
                    } 
                },
                fail: res => {
                    console.log("错误信息", res);
                }
            });
        },
        goBack(){
            if(this.hasDetail)  this.$router.go(-1);
        },
        back(){
       
        let str="";
            switch(this.isCheck){
                case 0:str="tax";break;
                case 1:str="brand";break;
                case 2:str="patent";break;
            }
            this.$router.push({path:"/Search",query:{type:str}})
        },
        goIndex(){
         this.$router.push({'path':"/"})
        },
        goCompanyDetail(companyId,name){
            Common.newPage("Company_detail", { id: companyId , title:name});
        },
        switchTab(idx){
            let obj =this.$route.query;
                obj.keyWord=this.keyVal;
            switch(idx){
                case 0:this.isCheck = 0 ; this.$router.push({path:'/TaxList',query:obj});break;
                case 1:this.isCheck = 1 ; this.$router.push({path:'/BrandList',query:obj});break;
                case 2:this.isCheck = 2 ; this.$router.push({path:'/PatentList',query:obj});break;
                default:this.isCheck = 3
            }
        },
        goSearch(){
            let val = this.keyVal;
            if (!val || val == "") {
                this.$message({
                    message: "请先填写搜索内容！",
                    type: "error",
                    position: "center",
                    duration: 1000
                });
                return;
            } 
            if (val == this.keyWord) {
                return;
            } 

            let str="tax",pathStr="/TaxContent";

                switch(this.isCheck){
                    case 0: Common.remberHistory("tax_history", val, 5); pathStr = '/TaxContent' ; str="tax" ;break;
                    case 1: Common.remberHistory("brand_history", val, 5); pathStr = '/BrandList' ;str="brand";break;
                    case 2: Common.remberHistory("patent_history", val, 5); pathStr = '/PatentList' ;str="patent";break;
                }
                if(this.seeionArr){
                     localStorage.setItem("taxArr",JSON.stringify(this.seeionArr))
                }
                this.$router.push({
                    path: pathStr,
                    query: {keyWord: val , type:str }
                });
        },
        changgeKey(){
             this.keyVal=this.keyWord=this.$route.query.keyWord
        }
    }
};
</script>
<style scoped>
.search_list_body {
    margin-top: 1px;
}
/* //分页 */
.pageination {
    margin-top: 30px;
    padding: 20px 10px;
    background: #fff;
    border: 1px solid #eee;
    margin-bottom: 50px;
}
.sel-bar .input-group .input-group-btn{
    right: 40px;
}
.top-search-nav{
    background: #fff;
}
</style>