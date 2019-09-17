<template>
    <div class="container m-t-md" id="speedCompany" style="min-height:800px">
        <div class="row">
            <div class="col-md-12">
                <div class="panel b-a padder">
                    <dl class="filter-tag clearfix dqc" v-if="industryArr">
                        <dt>{{linkMsg.type == "pro" ? '所属地区' : '行业门类' }}：</dt>
                        <dd v-for="(item,idx) in industryArr" :key="idx">
                            <a :class="{'current': item.name == linkMsg.industry_o}" @click="searchOne(item.name,item.code)">{{item.name}}</a>
                        </dd>
                    </dl>
                </div>
                <div class="panel b-a padder" v-if="industryArr2">
                    <dl class="filter-tag clearfix dqc">
                        <dt>行业大类：</dt>
                       <dd v-for="(item,idx) in industryArr2" :key="idx">
                            <a :class="{'current': item.name == linkMsg.industry_t}" @click="searchTwo(item.name)">{{item.name}}</a>
                        </dd>
                    </dl>
                </div>
            </div>
            <div class="col-md-12 searchlist" v-show="isShowList">
                <section class="panel panel-default" v-if="totalSize>0" v-for="(item,idx) in companyList" :key="idx">
                    <a data-href="/firm_FJ_573de8018c09180d57bff2670a4395a2.html" target="_blank" class="list-group-item clearfix">
                        <span class="pull-left thumb-md  m-r"> <img :src="item.logo ? item.logo : ''" :onerror="logo" alt=""> </span>
                        <span class="clear">
                            <span class="name" @click="goDeatil(item.companyId,item.name)">{{item.name}}</span>
                            <span class="label label-success m-l-xs">{{item.qygsDetail.status == " " ?  "其他" : item.qygsDetail.status}}</span>
                            <!-- <button class="btn btn-sm btn-default pull-right">关注</button> -->
                            <small class="text-muted clear text-ellipsis m-t-sm">
                                <i class="i i-user3"></i> {{item.qygsDetail.operName}}
                                <i class="i i-clock m-l"></i> {{fullTime( item.qygsDetail.startDate) }}
                                <i class="i i-bulb m-l"></i> {{mathChina(item.qygsDetail.registCapi)  || "暂无"}}
                                <i class="i i-tag2  m-l"></i> {{linkMsg.industry_t ||  linkMsg.industry_o}}
                            </small>
                            <small class="text-muted clear text-ellipsis m-t-sm">
                                <i class="i i-local"></i> {{item.qygsDetail.address || "暂无"}} </small>
                        </span>
                    </a>
                    <!-- <footer class="panel-footer clear">
                        <a data-href="/firm_FJ_573de8018c09180d57bff2670a4395a2.html" class="text-muted"> 评论 · 关注 </a>
                        <span class="pull-right">
                            <button class="btn btn-sm btn-default" data-toggle="tooltip" data-placement="top" title="" data-original-title="曾用名">漳州市荆龙林业科技有限公司</button>
                            <button class="btn btn-sm btn-default" data-toggle="tooltip" data-placement="top" title="" data-original-title="曾用名">福建省荆龙生物科技有限公司</button>
                            <button class="btn btn-sm btn-default" data-toggle="tooltip" data-placement="top" title="" data-original-title="曾用名">漳州市南靖县冠木花卉有限公司</button>
                            <button class="btn btn-sm btn-default" data-toggle="tooltip" data-placement="top" title="" data-original-title="曾用名">福建虎伯寮生物科技有限公司</button>
                        </span>
                        <div class="clear"></div>
                    </footer> -->
                </section>

                <div class="pageination" v-if="totalSize>0">
                    <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageIndex" :page-sizes="[10,20,30]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="totalSize">
                    </el-pagination>
                </div>
                <div class="search_no" v-else>
                    <img src="../assets/images/nodata.png" alt="">
                    <p>{{returnMsg}}</p>
                </div>
            </div>  
        </div>
    </div>
</template>
<script>
import userPhoto from "@/assets/images/default.jpg";
import Common,{Jump} from "@/util/common.js";
export default {
    name: "",
    data() {
        return {
            linkMsg:"",
            industryArr:"",  //一级分类
            industryArr2:"", //二级分类
            logo: 'this.src="' + userPhoto + '"',
            pageSize: 10, //每页多少条
            totalSize: 0, //总条数,
            pageIndex: 1,
            companyList:"",
            isShowList:0,
            returnMsg:""
        };
    },
    created(){
        this.linkMsg=this.$route.query
        
        if(this.$route.query.type == "pro"){
            this.$axios({type:"get",url:"companyAssets/nextNodeName",data:{code:"7453706779315408896"},success:res=>{
                // console.log("省份",res)
                if(res.data.code == 0){
                        this.industryArr=res.data.data
                    }
                }
            })
        }
        else{
             this.$axios({type:"get",url:"companyAssets/nextNodeName",data:{code:"industry"},success:res=>{    //一级企业
                if(res.data.code == 0){
                    let data=this.industryArr=res.data.data
                    for(let key of data){
                            if(key.name == this.$route.query.industry_o){
                                this.indoc(key.code);
                            break;
                            }
                        }
                    }
                }
            })
        }
       
        this.initPageList();
    },
    methods: {
        indoc(code){    //二级类型
    
            this.$axios({type:"get",url:"companyAssets/nextNodeName",data:{code:code},success:res=>{
                
                    if(res.data.code == 0){
                    this.industryArr2=res.data.data
                    }
                }
            })  

        },
        initPageList(){ //列表
          

            let obj={
                type:'',
                province:"",
                register:"",
                pageSize:this.pageSize,
                pageIndex:this.pageIndex
            }
            if(this.linkMsg.type == "pro"){
                obj.province=this.getData();
            }
            else{
                obj.type=this.getData();
            }
             this.$axios({type:"post",url:"companyAssets/QuickSelectCompany",data:obj,success:res=>{
              
                    let date=res.data.data;
                    //console.log("列表",date)
                    if(res.data.code == 0){
                        this.totalSize=Number(date.totalCount);
                        this.companyList=date.listData;
                    }
                    else{
                        this.totalSize=0;
                        this.returnMsg=res.data.msg
                    }

                    Jump(document.getElementById("speedCompany"),-80)

                    this.isShowList=1;
                }
            },1)  
          
        },
        getData(){
            let str= this.linkMsg.industry_t == "" ? this.linkMsg.industry_o : this.linkMsg.industry_o+"-"+this.linkMsg.industry_t;
            return str;
        },
        searchOne(str,code){
            let obj=Object.assign({},this.linkMsg);
                obj.industry_o=str;
                obj.industry_t="";
                this.linkMsg=obj;
                if(obj.type == "classify") this.indoc(code);
                this.pageIndex = 1;
                this.initPageList();

        },
        searchTwo(str){
            let obj=Object.assign({},this.linkMsg);
                obj.industry_t=str;
                this.linkMsg=obj;
                this.pageIndex = 1;
                this.initPageList();

        },
        handleCurrentChange(e) {
            //翻页 跳转到几页
            this.pageIndex = e;
            // console.log('handleCurrentChange',e);
            this.initPageList();
        },
        handleSizeChange(e) {
            //修改条数
            this.pageSize = e;
            this.initPageList();
        },
         goDeatil(companyId,name ){
             Common.newPage("Company_detail", { id: companyId,title:name });
        },
         mathChina(str) {
            //匹配中文
            if(str == "") return "";
            let str2 = (str+"").replace(/\s|人民币/g, "");
            let isMath = Number(str2);
            if (isNaN(isMath)) {
                return str2;
            } else {
                return str2 + "万元";
            }
        },
        fullTime(str){
            if(!str) return "暂无"
            let time =Number(str)
            if(isNaN(time)){
                    return str
            } 
            let date = new Date(time);
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            var d = date.getDate();

            let timeStr =y+"-"+ (m >9 ? m: ("0"+m))+"-"+ (d >9 ? d: ("0"+d));

            return timeStr
        }
    }
};
</script>
<style scoped>
/* @import "../assets/test.css"; */

.dqc.filter-tag{
    padding-left: 90px;
}
.dqc.filter-tag dt{
    position: absolute;
    left: 10px;
}
.searchlist .name {
    font-size: 17px;
    font-weight: 700;
}
.searchlist .name:hover {
    color: #128cec;
}
.searchlist .label {
    font-weight: normal;
    padding: .1em 0.6em .2em;
}
.searchlist small {
    font-size: 14px;
    color: #888;
}
/* //分页 */
.pageination {
    margin-top: 30px;
    padding: 20px 10px;
    background: #fff;
    border: 1px solid #eee;
    margin-bottom: 50px;
    text-align: left;
    padding-right: 50px;
}
.i {
    display: inline-block;
    font-family: "icon";
    font-style: normal;
    font-weight: normal;
    line-height: 1;
    vertical-align: -5%;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}
.i.i-user3 {
    width: 14px;
    height: 14px;
    background: url("../assets/images/icon_m.png") center center no-repeat;
    background-size: 100%;
}
.i.i-clock {
    width: 14px;
    height: 14px;
    background: url("../assets/images/icon_t.png") center center no-repeat;
    background-size: 100%;
}

.i.i-bulb {
    width: 16px;
    height: 16px;
    background: url("../assets/images/icon_d.png") center center no-repeat;
    background-size: 100%;
}

.i.i-tag2 {
    width: 14px;
    height: 14px;
    background: url("../assets/images/icon_cli.png") center center no-repeat;
    background-size: 100%;
}
.i.i-local {
    width: 14px;
    height: 14px;
    background: url("../assets/images/icon_addres.png") center center no-repeat;
    background-size: 100%;
}
/* //未找到页面 */
.search_no{
    text-align: center;
    padding: 50px;
    background: #ffffff;
    box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
    -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
    border: 1px solid #eaeef1;
}
.search_no img{
    width: 80px;
    margin-bottom: 15px;
}
/* //动画 */
section.panel-default{
  -webkit-transition: all 200ms ease-in;
  -moz-transition: all 200ms ease-in;
  -o-transition: all 200ms ease-in;
  transition: all 200ms ease-in;
  display: block;
}

section.panel-default:hover{
  -webkit-transform: translate3d(0, -8px, 0);
  -moz-transform: translate3d(0, -8px, 0);
  transform: translate3d(0, -8px, 0);
  transition: all 0.3s;
  -webkit-transition: all 0.3s;
  -moz-transition: all 0.3s;
  -webkit-box-shadow: 0px 2px 8px 2px rgba(0, 0, 0, 0.1), 1px 1px 30px 5px rgba(0, 0, 0, 0.2);
  -moz-box-shadow: 0px 2px 8px 2px rgba(0, 0, 0, 0.1), 1px 1px 30px 5px rgba(0, 0, 0, 0.2);
  box-shadow: 0px 2px 8px 2px rgba(0, 0, 0, 0.1), 1px 1px 30px 5px rgba(0, 0, 0, 0.2);
}
</style>