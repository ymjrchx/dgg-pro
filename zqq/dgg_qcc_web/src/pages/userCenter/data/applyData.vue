<template>
    <div class="col-md-12">
        <div class="panel" style="margin-bottom:10px">
            <div class="panel-heading clearfix">
                <h4 class="pull-left">
                    申请数据
                </h4>
            </div>
        </div>
        <div class="panel">
            <div class="panel-body">
                <ul class="dataClassify">
                    <div>数据分类：&nbsp; &nbsp; </div>
                    <li v-for="(item,idx) in keyArr" :key="idx" :class="{'active': idx ==isActive }" @click='switchTab(idx)'>{{item.name}}</li>
                    <!-- <li>法律诉讼</li>
                    <li>知识产权</li>
                    <li class="">关联族谱</li>
                    <li>经营状况</li>
                    <li>经营风险</li>
                    <li>企业库</li>
                    <li>监控报告</li>
                    <li>税务信息</li> -->
                </ul>
                <div class="c">
                    <ul class="Classify-child" v-if="keyArr">
                        <template  v-for='(item,idx) in keyArr[isActive].urlArr'>
                            <li v-if="item.isApply == 1" :key="idx"   class="off">
                                <span>{{item.name}}</span>
                            </li>
                            <li v-else :key="idx"   @click="selectfunc(item.id)" :class="{'active': applyIdArr.indexOf(item.id) != -1 }">
                                <span>{{item.name}}</span>
                            </li>
                        </template>
                    </ul>
                    <div style="margin:20px 10px;" v-show="applyIdArr.length > 0">
                        <el-button size="medium" style="background:#ff7c00;color:#fff;border-color:#ff7c00;outline:none" @click="applyFunc">申请新数据</el-button>
                    </div>
                    <small>备注： 您可以选择多个接口，一起申请！</small>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import NoFound from "@/components/No_found";
import Common from "@/util/common.js";
export default {
    props: {
        data: {}
    },
    components: {
        NoFound
    },
    data() {
        return {
            isActive:0,
            keyArr:"",
            applyIdArr:[]
        };
    },
    created() {
         this.initData()
    },
    methods: {
         initData(){
            this.$axios({
                type: "get",
                url: "apiInfo/getAllApiAndUserApply.do",
                data: {userId:this.$store.state.userInfo.userId},
                success: res => {
                  
                    if (res.data.code === 0) {
                        this.keyArr=res.data.data;
                    } else {
                        this.$alert(res.data.msg);
                    }
                }
            });
        },
       applyKey(id){
              let that = this;
               this.$confirm('确认是否申请此接口？')
                .then(_ => {
                    
                     this.$axios({
                        type: "get",
                        url: "api/userUrlApply/applyApi.do",
                        data: {userId:this.$store.state.userInfo.userId,apiId:id},
                        success: res => {
                           
                            if (res.data.code === 0) {
                                this.$message({
                                    message: '申请成功',
                                    type: 'success'
                                });
                            } else {
                                this.$alert(res.data.msg);
                            }
                        }
                    },1);
                })
                .catch(_ => {
                    
                });
        },
        selectfunc(id){
            let newArr=this.applyIdArr;
            let idx =newArr.indexOf(id);
            if(idx == -1){
                newArr.push(id)
            }
            else{
                newArr.splice(idx,1)
            }
            this.applyIdArr=newArr
        },
        applyFunc(){
            this.$axios({
                type: "get",
                url: "api/userUrlApply/applyApi.do",
                data: {userId:this.$store.state.userInfo.userId,apiIds:this.applyIdArr.join(",")},
                success: res => {
                    if (res.data.code === 0) {
                        this.$message({
                            message: '申请成功',
                            type: 'success',
                            duration:1500,
                            onClose:()=>{
                                    this.$router.push("/MyData")
                            }
                        });
                        
                    } else {
                        this.$alert(res.data.msg);
                    }
                }
            },1);
        },
        switchTab(num){
            if(this.isActive == num ) return; 
                this.isActive = num
                this.applyIdArr = []
        }
    }
};
</script>
    
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .dataClassify{
        overflow: hidden;
        position: relative;
    }
    .dataClassify > div{
        font-weight: bold;
        font-size: 16px;
        float: left;
        height: 42px;
        line-height: 42px;
        border-radius: 2px;
        text-align: center;
    }
     .dataClassify > li{
        float: left;
        width: 90px;
        height: 42px;
        margin: 0 20px;
        line-height: 42px;
        border-radius: 2px;
        text-align: center;
        cursor: pointer;
        font-size: 16px;
     }
    .dataClassify >li.active{
         background: #ECF5FF;
         color: #409EFF;
     }
    div.c{
         margin-left: 95px;
     }
     .Classify-child{
         padding: 30px 34px;
         overflow: hidden;
         border: 1px solid #ECF5FF;
         border-radius: 2px;
         margin-bottom: 20px;
     }
     .Classify-child li{
        float: left;
        padding: 6px 20px;
        border: 1px solid #3b97f5;
        color: #3b97f5;
        cursor: pointer;
        border-radius: 2px;
        margin-right: 24px;
     }
     .Classify-child li a{
         color: #3b97f5;
     }
    .Classify-child >li.active{
        background: #ECF5FF;
        color: #409EFF;
        background: url('../../../assets/images/gou_on.png') no-repeat right bottom;
     }
    .Classify-child >li.off{
        background: url('../../../assets/images/gou_off.png') no-repeat right bottom #f4f4f4;
        border: 1px solid #dfdfdf;
        color: #999;
     }
</style>
    