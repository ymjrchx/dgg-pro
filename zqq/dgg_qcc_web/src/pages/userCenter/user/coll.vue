<template>
    <div class="col-md-12">
         <div class="panel" style="margin-bottom:10px">
            <div class="panel-heading clearfix">
                <h4 class="pull-left">
                    我的收藏
                </h4>
            </div>
        </div>
        <div class="panel">
            <div class="panel-body">
                <div class="myDataBox">
                    <label>我的Key:</label>
                    <div>
                        <el-input
                            placeholder=""
                            v-model="keyVal"
                            :disabled="true">
                        </el-input>
                    </div>
                    <el-button type="primary">重置</el-button>
                </div>
                <table class="ptable" style="width:100%">
                    <thead>
                        <tr>
                            <th >数据类型</th>
                            <th width="15%">购买类型</th>
                            <th width="15%">申请时间</th>
                            <th width="15%">剩余次数</th>
                            <th width="15%">过期时间</th>
                            <th width="20%">操作</th>
                        </tr>
                    </thead>
                    <tbody v-if="hasMsg">
                        <tr>
                            <td>公司核名验证</td>
                            <td>计次</td>
                            <td>2018-8-10</td>
                            <td>-</td>
                            <td>9999-12-31</td>
                            <td width="110">-</td>
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
import NoFound from "@/components/No_found"
import Cookie from '@/util/cookie.js'

export default {
    props: {
        data: {
           
        }
    },
    components:{
        NoFound
    },
    data() {
        return {
            hasMsg:0,
            keyVal:""
        };
    },
    created() {

        // if(this.$store.state.isLogin){
        //     let cookieObj = JSON.parse(Cookie.getCookie("dqcUserKey"))
        //     this.$axios({type:'get',url:'api/userkey/getAppKey.do',data:{userId:cookieObj.userId},success:res=>{
        //         if(res.data.code === 0 ){
        //             if(res.data.data){
        //                 this.keyVal=res.data.data
        //             }
        //         }
        //         else{
        //            this.$alert({
        //                 title: "温馨提示",
        //                 showClose: false,
        //                 message: res.data.msg
        //             });
        //         }
        //     }})
        // }
       
    },
    methods: {

    }
};
</script>
    
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.ptable th {
    background: #FAFAFA;
    border: #eee 1px solid;
    border-collapse: collapse;
    padding: 17px 5px 17px 12px;
    color: #444;
    line-height: 19px;
    font-weight: bold;
     text-align: center;
}
.ptable th:not(:last-child) {
    border-right: #F2F9FC 1px solid;
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
.myDataBox{
    overflow: hidden;
    margin-bottom: 20px;
}
.myDataBox >label{
    font-size: 14px;
    font-weight: bold;
}
.myDataBox >div{
    display: inline-block;
    width: 25%;
    margin:0 10px 0 4px;
}
</style>
    