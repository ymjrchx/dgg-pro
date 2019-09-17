<template>
    <div class="col-md-12">
        <div class="panel" style="margin-bottom:10px">
            <div class="panel-heading clearfix">
                <h4 class="pull-left">
                    我的数据
                </h4>
            </div>
        </div>
        <div class="panel">
            <div class="panel-body">
                <div class="myDataBox">
                    <label>我的Key:</label>
                    <div style="vertical-align:middle">
                        <el-input placeholder="" v-model="secretKey" :disabled="true">
                            <el-button slot="append" icon="el-icon-view" @click="checkval"></el-button>
                        </el-input>
                    </div>
                    <el-button type="primary"  @click="restkey">{{ keyVal ? "重置" : "申请"}}</el-button>
                </div>
                <table class="ptable" style="width:100%">
                    <thead>
                        <tr>
                            <th>数据类型</th>
                            <th width="15%">购买类型</th>
                            <th width="15%">申请时间</th>
                            <th width="15%">剩余次数</th>
                            <th width="15%">过期时间</th>
                            <th width="20%">操作</th>
                        </tr>
                    </thead>
                    <tbody v-if="hasMsg">
                        <tr v-for="(item,idx) in apiList" :key="idx">
                            <td><a class="text-primary" @click="goDataDetail(item.apiId)">{{item.urlName}}</a></td>
                            <td>计次</td>
                            <td>{{item.createTime}}</td>
                            <td>-</td>
                            <td>-</td>
                            <td width="110">
                                <a class="text-primary m-l-xs" @click="delectData(item.id,item.userId)">删除</a>
                            </td>
                        </tr>
                    </tbody>
                    <tbody v-else>
                        <tr>
                            <td colspan="6">您当前没有申请数据接口</td>
                        </tr>
                    </tbody>

                </table>
            </div>
        </div>
    </div>
</template>
<script>
import NoFound from "@/components/No_found";
import {GetKey} from '@/util/Http.js'

export default {
    props: {
        data: {}
    },
    components: {
        NoFound
    },
    data() {
        return {
            hasMsg: 0,
            keyVal: "",
            switch:1,
            secretKey:"",
            pageIndex: 1,
            pageSize: 10,
            apiList:[]
        };
    },
    created() {
        if (this.$store.state.isLogin) {
            this.userInfo = this.$store.state.userInfo;
            this.initKey(); //获取Key
            this.initList(); //获取列表
        }
    },
    methods: {
        initKey() {
            this.$axios({
                type: "get",
                url: "api/userkey/getAppKey.do",
                data: { userId: this.userInfo.userId },
                success: res => {
                    if (res.data.code === 0) {
                        if (res.data.data) {
                            this.keyVal= res.data.data;
                            this.secretKey=this.addSecret(res.data.data)
                        }
                    } else {
                        this.$alert(res.data.msg);
                    }
                }
            });
        },
        initList() {
            let obj={
                param:"",
                pageIndex: this.pageIndex,
                pageSize: this.pageSize,
                userId:this.userInfo.userId
            }
            this.$axios({
                type: "get",
                url: "api/userUrlApply/queryMyApply.do",
                data: obj,
                success: res => {
                    if (res.data.code === 0) {
                        
                        if (res.data.data.data) {
                            this.hasMsg =0;
                           
                            if(res.data.data.data.length >0){
                                this.apiList = res.data.data.data;
                                this.hasMsg =1;
                            }
                        }
                    } else {
                        this.$alert(res.data.msg);
                        this.hasMsg =0;
                    }
                }
            });
        },
        delectData(id,userId){
            let that = this;
               this.$confirm('确认是否删除，删除后此接口将无法访问？')
                .then(_ => {
                    
                     this.$axios({
                        type: "get",
                        url: "api/userUrlApply/deleteMyApply.do",
                        data: { userId:userId, applyId:id },
                        success: res => {
                          
                            if (res.data.code === 0) {
                                that.initList()
                            } else {
                                this.$alert(res.data.msg);
                            }
                        }
                    },1);

                })
                .catch(_ => {
                    
                });
           
        },
        checkval(){
           if(this.switch){
                this.secretKey=this.keyVal;
                this.switch=0;
           }
           else{
                this.secretKey=this.addSecret(this.keyVal)
                 this.switch=1;
           }
        },
        restkey(){
           let _this=this,str=this.keyVal ? '确认是否重置Key' : '确认是否申请Key';
              this.$confirm(str).then(_ => {
                  
                   this.$axios({
                        type: "get",
                        url: "api/userkey/create.do",
                        data: { userId:this.userInfo.userId },
                        success: res => {
                           
                            if (res.data.code === 0) {
                               this.keyVal= res.data.data;
                               this.secretKey=this.addSecret(res.data.data)
                            } else {
                                this.$message.error('申请失败');
                            }
                        }
                    },1);
                })
                .catch(_ => {});
            
        },
        addSecret(str){
            let arr=[...str];
            for(let i=5 ; i<(arr.length - 5);i++){
                arr[i]="*";
            }
            return arr.join("")
        },
        goDataDetail(id){
            sessionStorage.setItem("apiId",id)
            this.$router.push('/Apikey')
        }
    }
};
</script>
    
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.ptable th {
    background: #fafafa;
    border: #eee 1px solid;
    border-collapse: collapse;
    padding: 17px 5px 17px 12px;
    color: #444;
    line-height: 19px;
    font-weight: bold;
    text-align: center;
}
.ptable th:not(:last-child) {
    border-right: #f2f9fc 1px solid;
}
.ptable td {
    padding: 12px 5px 12px 12px;
    border: #eee 1px solid;
    word-break: break-all;
    font-size: 14px;
    line-height: 19px;
    color: #222;
    text-align: center;
}
.ptable td:not(:last-child) {
    border-right: transparent 1px solid;
}
/* //我的Key */
.myDataBox {
    overflow: hidden;
    margin-bottom: 20px;
}
.myDataBox > label {
    font-size: 14px;
    font-weight: bold;
}
.myDataBox > div {
    display: inline-block;
    width: 25%;
    margin: 0 10px 0 4px;
}
</style>
    