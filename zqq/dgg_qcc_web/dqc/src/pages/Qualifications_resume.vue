<template>
    <div style="min-height:820px">
        <!-- 筛选条件 开始 -->
        <div class="elib-ftop" id="gotop" >
            <div class="pills province" v-if="docStyle1Arr.length >0">
                <div class="pills-header">所有分类:</div>
                <div class="pills-after">
                    <a class="pills-item" :class="{'active':checkedArr_1[0]}" @click="chooseAllStyle(':',0)">
                       不限
                    </a>
                    <a class="pills-item " v-for="(item,idx) in docStyle1Arr" :class="{'active':checkedArr_1[idx+1]}" :key="idx" @click="chooseAllStyle(item.code,idx+1)">
                        {{item.name}}
                    </a>
                </div>
            </div>
            <div class="pills province" v-if="docStyle2Arr.length >0">
                <div class="pills-header">二级分类:</div>
                <div class="pills-after">
                    <a class="pills-item" :class="{'active':checkedArr_2[0]}" @click="chooseSecStyle(':',0)">
                         不限
                    </a>
                    <a class="pills-item " v-for="(item,idx) in docStyle2Arr" :class="{'active':checkedArr_2[idx+1]}" :key="idx" @click="chooseSecStyle(item.code,idx+1)">
                        {{item.name}}
                    </a>
                </div>
            </div>
            <div class="pills industry" v-if="docPro1Arr.length >0">
                <div class="pills-header">所有地区：</div>
                <div class="pills-after">
                    <a class="pills-item " :class="{'active':checkedArr_3[0]}" @click="chooseAllpro(':',0)">
                         不限
                    </a>
                   <a class="pills-item " v-for="(item,idx) in docPro1Arr" :class="{'active':checkedArr_3[idx+1]}" :key="idx" @click="chooseAllpro(item.code,idx+1)">
                        {{item.name}}
                    </a>    
                </div>
            </div>
            <div class="pills industry" v-if="docPro2Arr.length >0">
                <div class="pills-header">二级地区：</div>
                <div class="pills-after">
                    <a class="pills-item" :class="{'active':checkedArr_4[0]}" @click="chooseSecpro(':',0)">
                        不限
                    </a>
                    <a class="pills-item " v-for="(item,idx) in docPro2Arr" :class="{'active':checkedArr_4[idx+1]}" :key="idx" @click="chooseSecpro(item.code,idx+1)">
                        {{item.name}}
                    </a>           
                </div>
            </div>
            <div class="pills industry" v-if="isReg">
                <div class="pills-header">注册情况：</div>
                <div class="pills-after">
                    <a class="pills-item active">
                       不限
                    </a>
                    <a class="pills-item " >
                       初始
                    </a>  
                    <a class="pills-item ">
                       转注
                    </a>           
                </div>
            </div>
        </div>
        <!-- 筛选条件 结束 -->
        <!-- 表格 开始 -->
        <div class="elib-content m-t">
            <div class="panel-heading" v-show="searchIng == 1"> 搜索到 <span class="text-danger">{{totalSize > 5000 ? '5000+' : totalSize}}</span> 个结果</span>
            <div class="panel-heading" v-show="searchIng == 0">正在为您寻找......</div>
            </div>
            <div class="panel clearfix">
                <table class="ntable" v-if="totalSize > 0">
                    <tr>
                        <th width="110">姓名</th>
                        <th width="80">性别</th>
                        <th width="120">价格</th>
                        <th width="120">证书状态</th>
                        <th width="">专业</th>
                        <th width="170">地区</th>
                        <th width="170">更新时间</th>
                    </tr>
                    <tr v-for="(item,idx) in listArr" :key="idx" >
                        <td class="text-center"><a @click="goDetail(item.url)"> {{item.name || "-"}} </a></td>
                        <td class="text-center">{{item.sex  || "-"}}</td>
                        <td class="text-center">{{item.salary  || "-"}}</td>
                        <td class="text-center">{{item.certificate_status  || "-"}}</td>
                        <td class="text-center">{{item.type  || "-"}}</td>
                        <td class="text-center">{{item.region  || "-"}}</td>
                        <td class="text-center">{{item.updateTime  || "-"}}</td>
                    </tr>
                </table>

                <div class="search_no" v-else>
                    <img src="../assets/images/nodata.png" alt="">
                    <p>抱歉，未能找到相关数据</p>
                </div>
            </div>
        </div>
        <!-- 表格结束 -->
        <!-- 分页 开始 -->
        <div class="pageination">
            <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageIndex" :page-sizes="[10,20,30]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="totalSize">
            </el-pagination>
        </div>
        <!-- 分页结束 -->
    </div>
</template>
<script>

import Common,{Jump} from "@/util/common.js";

export default {
    components: {},
    data() {
        return {
            searchIng:0, //显示正在寻找
            pageSize: 20, //每页多少条
            totalSize: 0, //总条数,
            pageIndex: 1,
            isReg:false,  //是否注册
            docStyle1Arr:[],  //全部分类
            docStyle2Arr:[],   //二级分类
            docPro1Arr:[],    //全部地区
            docPro2Arr:[],    //二级地区
            type:"zs_type",       //分类1 code
            province:"7453706779315408896",   //省份1 code
            type2:"",       //分类2 code
            province2:"",   //省份2 code
            checkedArr_1:[1],
            checkedArr_2:[1],
            checkedArr_3:[1],
            checkedArr_4:[1],
            listArr:[]
         };
    },
    created(){
            this.initlist(666);
            this.initDoc();
    },
    methods: {
        initDoc(){
           this.initDoc_style(this.type);
           this.initDoc_pro(this.province);
        },
        initDoc_style(str){   // 分类数据
            this.$axios({type:"get",url:"companyAssets/nextNodeName",data:{"code":str},success:res=>{
                //console.log("分类",res)
                let data = res.data;
                if(data.code == 0){
                    if(str == "zs_type"){
                         this.docStyle1Arr=data.data
                    }
                    else{
                        this.docStyle2Arr=data.data
                    }   
                }
            }})
        },
        initDoc_pro(str){  //省份地区 数据
            this.$axios({type:"get",url:"companyAssets/nextNodeName",data:{"code":str},success:res=>{
                //console.log("分类2",res)
                let data = res.data;
                if(data.code == 0){
                    if(str == "7453706779315408896"){
                        this.docPro1Arr=data.data
                    }
                    else{
                        this.docPro2Arr=data.data
                    }   
                }
            }})
        },
        initlist(count){
            this.searchIng=0;
             
            let obj={
                pageIndex:this.pageIndex,
                pageSize:this.pageSize,
                type:this.returnStr(this.docStyle1Arr,this.checkedArr_1,1)+"-"+this.returnStr(this.docStyle2Arr,this.checkedArr_2),
                province:this.returnStr(this.docPro1Arr,this.checkedArr_3,1)+"-"+this.returnStr(this.docPro2Arr,this.checkedArr_4),
                register:""
            }
            if(obj.type == "-") obj.type = "";
            if(obj.province == "-") obj.province = "";
            
            //console.log("请求参数",obj)
            this.$axios({type:"post",url:"companyAssets/certificateResumes",data:obj,success:res=>{
                //console.log("列表",res)
                let data = res.data;
                if(data.code == 0){
                    this.listArr=data.data.listData;
                    this.totalSize=Number(data.data.totalCount)
                }
                else {
        
                    this.totalSize=0;
                }
                this.searchIng=1;
                if(!count) Jump(document.getElementById("gotop"))
               
            }},1)
        },
        handleCurrentChange(e) {
            //翻页 跳转到几页
            this.pageIndex = e;
           
            this.initlist();
          
        },
        handleSizeChange(e) {
            //修改条数
            this.pageSize = e;
            this.pageIndex = 1;
            this.initlist()
        },
        back() {
            this.$router.go(-1);
        },
        goDetail(id) {
          Common.newPage("Qualifications_resume_d", { url : id });
        },
        chooseAllStyle(code,idx){        //选择全部分类
            this.checkedArr_1=this.switchCheck(this.checkedArr_1,idx);
            if(code != ':') this.initDoc_style(code)
            this.docStyle2Arr=[];
            this.checkedArr_2=[1];
            this.initlist();
        },
        chooseSecStyle(code,idx){        //选择二级分类
            this.checkedArr_2=this.switchCheck(this.checkedArr_2,idx);
            this.initlist();
        },
        chooseAllpro(code,idx){        //选择全部地区
            this.checkedArr_3=this.switchCheck(this.checkedArr_3,idx);
            if(code != ':') this.initDoc_pro(code)
            this.docPro2Arr=[];
            this.checkedArr_4=[1];
            this.initlist();
        },
        chooseSecpro(code,idx){        //选择二级地区
            this.checkedArr_4=this.switchCheck(this.checkedArr_4,idx);
            this.initlist();
        },
        switchCheck(arr,idx){
            let newArr=[...arr];
                newArr.length=idx+1;
            for(let i =0 ;i<idx+1;i++){
                newArr[i]=0;
            }    
            newArr[idx] = 1 ;
            return newArr;
        },
        returnStr(strArr,idxArr){
            let i=0,bool=false;
            for(i; i< idxArr.length ; i++){
                if(idxArr[i]){
                    bool=true;
                    break;
                }
            }
            if(bool && i !=0 ){
                
                return strArr[i-1].name
            }
            else  return "" 
        }
    },
    mounted() {}
};
</script>
<style scoped>
/* @import "../assets/test.css"; */
@import "../assets/elib.css";

/* //分页 */
.pageination {
    margin-top: 30px;
    padding: 20px 10px;
    background: #fff;
    border: 1px solid #eee;
    margin-bottom: 50px;
    text-align: right;
    padding-right: 50px;
}
.qualificationTable td {
    padding: 15px 10px;
}
/* //未找到页面 */
.search_no{
    text-align: center;
    padding: 50px;
}
.search_no img{
    width: 80px;
    margin-bottom: 15px;
}
</style>