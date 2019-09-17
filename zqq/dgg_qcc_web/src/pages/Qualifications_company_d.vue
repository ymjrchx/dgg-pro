<template>
    <div class="container m-t-md" style="min-height:750px">

        <!-- <div class="col-md-12">
            <ul class="breadcrumb breadcrumb2" style="background:none;border:none;">
                <li>
                    <a @click="goIndex">
                        <img src="@/assets/images/ic_reply.png">&nbsp;首页</a>
                </li>
                <li>
                    <a @click="back">商标查询</a>
                </li>
                <li>
                    <h1>七夕商标查询列表</h1>
                </li>
            </ul>
        </div> -->
        <div class="col-md-12 sel-list ajaxlist" id="ajaxlist">
            <div class="panel panel-default">
                <table class="table table-bordered">
                    <tbody>
                        <tr>
                            <td width="20%" class="tb">企业名称： </td>
                            <td>
                                <span style="font-size:20px;color:#128bed;cursor:pointer" @click="goDetail(json.qymc)"> {{json.qymc}} </span>
                            </td>
                        </tr>
                        <tr>
                            <td width="20%" class="tb">统一社会信用代码:</td>
                            <td>{{hasKey(json.xydm)}}</td>
                        </tr>
                        <tr>
                            <td width="20%" class="tb">企业法定代表人： </td>
                            <td>{{hasKey(json.fr)}}</td>
                        </tr>
                        <tr>
                            <td width="20%" class="tb">企业登记注册类型： </td>
                            <td>{{hasKey(json.zclx)}}</td>
                        </tr>
                        <tr>
                            <td width="20%" class="tb">企业注册属地: </td>
                            <td>{{hasKey(json.zcsd)}}</td>
                        </tr>
                        <tr>
                            <td width="20%" class="tb">企业经营地址: </td>
                            <td>{{hasKey(json.jydz)}}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="tab-content_all col-md-12">
            <div class="tab-content_l">
                <div class="card">
                    <div :class="{active:isTab == 0}" @click="tab(0)">企业资质资格

                    </div>
                    <div :class="{active:isTab == 1}" @click="tab(1)">注册人员

                    </div>
                    <div :class="{active:isTab == 2}" @click="tab(2)">工程项目

                    </div>
                    <div :class="{active:isTab == 3}" @click="tab(3)">不良行为

                    </div>
                    <div :class="{active:isTab == 4}" @click="tab(4)">良好行为

                    </div>
                    <div :class="{active:isTab == 5}" @click="tab(5)">黑名单记录

                    </div>
                    <div :class="{active:isTab == 6}" @click="tab(6)">变更记录

                    </div>
                </div>
                <div v-show="isTab == 0">
                    <Company :zzzz="json.zssList"/>
                </div>
                <div v-show="isTab == 1">
                    <Reg :reg="json.registerInfoList"/>
                </div>
                <div v-show="isTab == 2">
                    <Project/>
                </div>
                <div v-show="isTab == 3">
                   <Bad/>
                </div>
                <div v-show="isTab == 4">
                   <Good/>
                </div>
                <div v-show="isTab == 5">
                    <BlackList/>
                </div>
                <div v-show="isTab == 6">
                    <UpdateList/>
                </div>
            </div>
        </div>

    </div>
</template>
<script>
import Company from "@/pages/qualifications/TabCompany"; //企业资质资格
import Reg from "@/pages/qualifications/Reg"; //注册人员
import Project from "@/pages/qualifications/Project"; //工程项目
import Bad from "@/pages/qualifications/Bad"; //不良行为
import Good from "@/pages/qualifications/Good"; //良好行为
import BlackList from "@/pages/qualifications/BlackList"; //黑名单记录
import UpdateList from "@/pages/qualifications/UpdateList"; //变更记录

import Common,{Jump} from "@/util/common.js";

export default {
    components: {
        Company,Reg,Project,Bad,Good,BlackList,UpdateList
    },
    data() {
        return {
            isTab: 0,
            requireData:"",
            json:{},
            isHave:0
        };
    },
     created() {

        //console.log("參數props", this.$route.query);
        this.requireData = this.$route.query;
        this.initPage(this.$route.query);
    },
    methods: {
        initPage(data){
          
            this.$axios({type:"get",url:"companyAssets/entlibDetail",data:data,success:res=>{
                let data=res.data,
                    _this=this;
                // console.log(2222,data)
                if(data.code == 0){
                  this.json=data.data[0]
                  try{
                       _this.isHave =data.data[1].status
                  }
                  catch(error){
                        _this.isHave =0
                  }
                 
                }
                else{
                   this.$message.error(data.msg);
                }
               
                }
            },1)
        },
        hasKey(str){
            if(str && str !="")  return str;
            else return "暂无"
        },
        back() {
            this.$router.go(-1);
        },
        goIndex() {
            this.$router.push({ path: "/" });
        },
        tab(num) {
            if (this.isTab != num) this.isTab = num;
        },
        goDetail(companyId){
            // console.log("id",id)
            if(this.isHave == 1){
                Common.newPage("Company_detail", { id: companyId,type:"quali" });
            }
        }
    },
    mounted() {}
};
</script>
<style scoped>
/* @import "../assets/test.css"; */
section.panel {
    padding: 0;
}
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

.table .tb {
    background: #f2f9fc;
    font-weight: bold;
}
/* //tab选项 */
/* //tab选项内容 */
.tab-content_all{
    margin-top: 20px;
}
.tab-content {
    margin-bottom: 30px;
}
.tab-content_l {
    width: 100%;
    min-height: 200px;
    background: #fff;
}
.tab-content_r {
    width: 24%;
    background: #fff;
}
.card {
    width: 100%;
    background-color: #fcfcfd;
    border: 1px solid #e9eeef;
    padding-left: 0;
    margin-bottom: 0;
    list-style: none;
    height: 50px;
}
.card > div.active {
    float: left;
    color: #128cec;
    border-left: 1px solid #e9eeef;
    border-right: 1px solid #e9eeef;
    background: #fff;
    margin-bottom: -2px;
    border-bottom: solid 1px #fff;
    font-weight: 700;
    position: relative;
    bottom: -1px;
}
.card > div {
    height: 100%;
    line-height: 50px;
    text-align: center;
    float: left;
    display: block;
    font-size: 14px;
    cursor: pointer;
    width: 14.28%;
    font-size: 16px;
}
.card > div span {
    font-size: 12px;
}
.card > div a {
    display: block;
    width: 100%;
    height: 100%;
}
.card > div:first-child {
    border-left: none !important;
}
.card > div:last-child {
    border-right: none !important;
}
</style>