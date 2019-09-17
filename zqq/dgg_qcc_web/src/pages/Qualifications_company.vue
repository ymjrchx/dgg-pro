<template>
    <div style="min-height:820px">
        <!-- 筛选条件 开始 -->
        <div class="panel-heading screen_title" id="gotop">
            筛选条件
        </div>
        <div class="elib-ftop">
            <section class="screen_item select style">
                <label>资质类别：</label>
                <template>
                    <el-select v-model="zzValue" placeholder="请选择">
                        <el-option v-for="item in zzleOptions" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </template>
            </section>
            <section class="screen_item">
                <!-- <label>包含资质名称：</label>
                <el-input placeholder="点击选择资质名称" v-model="company" clearable  @focus="dialogTableVisible=true">
                </el-input> -->
            </section>
            <section class="screen_item">
                <label>企业名称：</label>
                <el-input placeholder="请输入内容" v-model="company" clearable>
                </el-input>

            </section>
            <section class="screen_item">
                <label>统一社会信用代码：</label>
                <el-input placeholder="请输入内容" v-model="code" clearable>
                </el-input>

            </section>
            <section class="screen_item">
                <label>证书编号：</label>
                <el-input placeholder="请输入内容" v-model="cer" clearable>
                </el-input>

            </section>
            <section class="screen_item">
                <label>企业法人代表：</label>
                <el-input placeholder="请输入内容" v-model="lega" clearable>
                </el-input>

            </section>
            <!-- <section class="screen_item select">
                <label>造价企业所属管理机构：</label>
                <template>
                    <el-select v-model="value" placeholder="请选择">
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </template>
            </section> -->
            <section class="screen_item">
                <label>企业注册属地：</label>
                 <el-input placeholder="点击选择注册属地" v-model="partVal" clearable  @focus="dialogTableVisible=true">
                </el-input>
            </section>
            <section class="screen_item handle">
                <el-button type="primary" @click="searchFromBtn">开始查询</el-button>
                <el-button type="primary" plain @click="reseatFrom">重置</el-button>
            </section>
            <!-- //归属地的选择 -->
            <el-dialog title="选择行政区划信息" :visible.sync="dialogTableVisible"  width="30%">
                <div class="partBox">
                    <span v-for="(item,index) in partOptions" :key="index" @click="choosePart(item)" :title="item">{{getTwo(item)}}</span>
                </div>
            </el-dialog>
            <!-- //行政区划的选择 -->
        </div>
        <!-- 筛选条件 结束 -->
        <!-- 表格 开始 -->
        <div class="elib-content m-t">
            <div class="panel-heading" v-show="searchIng == 1">搜索到<span class="text-danger">{{totalSize > 5000 ? '5000+' : totalSize}}</span> 个结果</span></div>
            <div class="panel-heading" v-show="searchIng == 0">正在为您寻找......</div>
            <div class="panel clearfix">
                <table class="ntable qualificationTable" v-if=" totalSize > 0 ">
                    <tr>
                        <th width="260">统一社会信用代码</th>
                        <th width="">企业名称</th>
                        <th width="150">企业法定代表人</th>
                        <th width="220">企业注册属地</th>

                    </tr>
                    <tr v-for="(item,index) in listArr" :key="index">
                        <td class="text-center">{{item.xydm}}</td>
                        <td class="text-center">
                            <a @click="goDetail(item.companyId,item.qymc)"> {{item.qymc}} </a>
                        </td>
                        <td class="text-center">{{item.fr}}</td>
                        <td class="text-center">{{item.zcsd}}</td>
                    </tr>
                    <!-- <tr>
                        <td class="text-center">91360921550886715M</td>
                        <td class="text-center">
                            <a @click="goDetail"> 内蒙古华诚商务有限责任公司 </a>
                        </td>
                        <td class="text-center">（改）赵英</td>
                        <td class="text-center">内蒙古自治区</td>
                    </tr> -->
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
            zzValue:"", //资质类别
            company: "", //公司名称
            code: "", //统一社会信用代码
            cer: "", //证书编号
            lega: "", //企业法人代表
            partVal:"",// 归属地
            zzleOptions:[],  //资质类别
            partOptions:[],     //注册归属地
            listArr:[],
            dialogTableVisible: false,
            count:0
        };
    },
    created(){
            this.zzleInit() //取出资质类别
            this.partInit() //取出注册属地
    },
    methods: {
        zzleInit(){
            this.$axios({type:"get",url:"companyAssets/getZcType",data:{code:"zzlb"},success:res=>{
                //console.log("资质",res.data)
                let arr=[];
                if(res.data.code == 0){
                    for(let key of res.data.data){
                        arr.push({'label':key,'value':key})
                    }
                    arr[0].value="";
                    this.zzValue=arr[0].value;
                    this.zzleOptions=arr;
                }

               this.searchFrom();
                }
            })
        },
        partInit(){
            this.$axios({type:"get",url:"companyAssets/getZcType",data:{code:"7453706779315408896"},success:res=>{
                //console.log("省份",res)
               if(res.data.code == 0){
                    this.partOptions=res.data.data;
               }
                }
            })
        },
        choosePart(str){  //选择归属地
            this.dialogTableVisible=false;
            this.partVal=str;
        },
         searchFromBtn(){
            this.pageIndex = 1;
            this.searchFrom();
        },
        searchFrom(){    //搜索 
             this.searchIng=0;
            

            let obj={
                pageSize: this.pageSize, //每页多少条
                pageIndex: this.pageIndex, //多少页
                zzlb:this.zzValue,   //资质类别
                // zzmc:this.zzValue,   //资质名称
                qymc:this.company,   //企业名称
                xydm:this.code,   //统一社会信用代码
                zsbh:this.cer,   //证书编号
                frdb:this.lega,   //企业法人代表
                // ssgljg:this.zzValue,  //所属管理机构
                zcAddress:this.partVal, //企业注册属地
            }
            //console.log("请求参数",obj)
            this.$axios({type:"post",url:"companyAssets/entlib",data:obj,success:res=>{
                let data=res.data;
                //console.log(data)
                if(data.code == 0){
                    this.listArr = data.data.listData;
                    this.totalSize = Number(data.data.totalCount);
                }
                else{
                     this.totalSize =0;
                }
                this.searchIng=1;
                if(this.count)  Jump(document.getElementById("gotop"))
                else this.count=1;
                
            }},1)

        },
        reseatFrom(){       //重置
            this.pageSize=10;  //每页多少条
            this.pageIndex=1 ; //多少页
            this.zzValue=(this.zzleOptions)[0].value;  //资质类别
            this.company="";  //公司名称
            this.code="";    //统一社会信用代码
            this.cer="";     //证书编号
            this.lega="";    //企业法人代表
            this.partVal="";  // 归属地
        },
        handleCurrentChange(e) {
            //翻页 跳转到几页
            this.pageIndex = e;
            this.searchFrom();
       
        },

        handleSizeChange(e) {
            //修改条数
            this.pageSize = e;
            this.pageIndex = 1;
            this.searchFrom();
        
        },
        getTwo(str){
            return str.substring(0,3)
        },
        back() {
            this.$router.go(-1);
        },

        goDetail(companyId,name) {
             Common.newPage("Qualifications_company_d", { companyId: companyId ,companyName:name});
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
.screen_title {
    height: 58px;
    padding: 0px 15px;
    line-height: 58px;
    font-size: 16px;
    border-bottom: solid 1px #eee;
    background: #fff;
}
.elib-ftop {
    overflow: hidden;
    padding: 20px 15px;
}
.screen_item {
    float: left;
    width: 50%;
    margin-bottom: 10px;
    min-height: 40px;
}
.screen_item label {
    display: inline-block;
    width: 160px;
    text-align: right;
}
.screen_item.handle {
    width: 100%;
    margin-top: 20px;
    text-align: right;
}
.screen_item.handle button {
    margin-right: 30px;
}
.partBox{
    overflow: hidden;
}
.partBox span{
    float: left;
    line-height: 32px;
    padding: 0 10px;
    vertical-align: middle;
    white-space: nowrap;
    color: #348bda;
    cursor: pointer;
    margin: 1px 2px;
    width: 62px;
    overflow: hidden;
     text-align: center
}
.partBox span:hover{
    color: #fff;
    background: #348bda;
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