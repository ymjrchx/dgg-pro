<template>
  <div class="header">
    <div class="w">
      <div class="img_logo fl">
        <a @click="goIndex">
          <img src="../assets/images/logo4.png">
        </a>
      </div>
      <ul class="fl head_nav" style="margin-right:17px;">
        <li>
            <router-link to='/'>首页</router-link>
        </li>
      </ul>      
      <ul class="fl head_nav head_search">
        <el-autocomplete popper-class="input-autocomplete" v-model="state4" :fetch-suggestions="querySearchAsync" 
            placeholder="请输入内容" @select="handleSelect" :trigger-on-focus='false'>
            <template slot-scope="{ item }">
                <div class="list_search"><em v-html="item.isName ? item.heightLight : item.value"></em> <span class="addr" v-if="item.flag">{{item.flag}}</span> </div>
            </template>
        </el-autocomplete>
        <input type="button" value="" class=" button btn search_button" @click="submit">
      </ul>
      <ul class="fr head_nav">
        <!-- <li class="">
          <a>APP下载</a>
        </li>
        <li class="line">|</li>
        <li class="">
          <a>会员服务</a>
        </li>
        <li class="line">|</li>
        <li class="">
          <a>企业版</a>
        </li>
        <li class="line">|</li> -->
        <li class="news_noti" @click="goUser"  v-if="this.$store.state.isLogin">
            <sup class="el-badge__content is-fixed news_num" v-if=" this.$store.state.msgNum > 0" >{{this.$store.state.msgNum}}</sup>
            <img src="../assets/images/news_noti.png" alt="">
            <template v-if="hasNewMsg">
                <MsgBox/>
            </template>
        </li>
        <template v-if="this.$store.state.isLogin">
          <Logined/>
        </template>
        <template v-else>
          <li class="">
            <router-link to='/Login'>登录</router-link>
          </li>
          <li class="line">|</li>
          <li class="dropdown">
            <router-link to='/Register'>免费注册</router-link>
          </li>
        </template>
      </ul>
    </div>
  </div>
</template>
<script>
import Common from "@/util/common";
import Logined from './Logined.vue'
import MsgBox from "./MsgBox"; 
export default {
    name: "",
    components: {
       Logined,MsgBox
    },
    props: {
        val: ""
    },
    data() {
        return {
            hasNewMsg:1,
            state4: "",
            timeout: null,
            isBlur:false
        };
    },
    created() {
        // console.log("数据2",this.val)
        this.state4 = this.val;
    },
    methods: {
        goIndex() {
            this.$router.replace("/");
        },
        loadAll() {
            let arr = Common.getHistory("dqc_history");
            return arr ? arr : [];
        },
        querySearchAsync(queryString, cb,num) {
            let _this = this;
            if (queryString && queryString.length > 1) {
                var results = this.getMsg(queryString, res => {
                    cb(res);
                });
            } else {
                cb(_this.loadAll());
            }
        },
        handleSelect(item) {
           if(item.value == "暂无数据") return;
           if(item.hasOwnProperty('companyId')){
                Common.newPage("Company_detail", { id: item.companyId ,title:item.value});
                Common.remberHistory("dqc_history",item.value,5);
                this.state4="";
            }
        },
        getMsg(e, fn) {
            let str = e + "",
                _this = this;
            let obj = {
                keyWord: e,
                count: 5,
                type: "all"
            };
            this.$axios({
                type:"get",
                url:"company/searchFive",
                data:obj,
                success:res => {
                    //console.log(res)
                    if (res.data.code == 0) {
                        let arr = [];
                        for (let key of res.data.data) {
                           let str="",bool=0;
                                switch(key.highlight){
                                    case "name": str="企业名";bool=1;;break;
                                    case "qygsDetail.operName": str="法人名";break;
                                    case "qygsDetail.partnersList.stockName": str="股东名";break;
                                    case "qygsDetail.employeesList.name": str="高管";break;
                                    case "qygsDetail.address": str="地址";break;
                                    case "tel": str="电话号码";break;
                                    case "qygsDetail.contactInfoList.webSiteName": str="网站名称";break;
                                    case "qygsDetail.scope": str="经营范围";break;
                                    case "jyzk.products.name":str="产品";break;
                                    default:str="其他";
                                }
                           arr.push({ value:key.name , heightLight:key.lightVal , companyId: key.companyId , flag:str,isName:bool});
                        }
                        fn(arr);
                    }
                    else fn([]) 
                },
                fail:res => {
                    console.log("错误信息", res);
                    fn([]);
                }
            });
        },
        submit() {
            if(!this.state4){
                 this.$message({
                    message: "请先填写搜索内容！",
                    type: "error",
                    position: "center",
                    duration: 1000
                });
                return;
            }
            Common.remberHistory("dqc_history",this.state4,5);
            this.$router.push({
                path: "/SearchResult",
                query: { type: "all", keyWord: this.state4 }
            });
        },
        goUser(){
            this.$router.push('/userCenter')
        }
    },
    mounted() {
    
    }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import "../assets/head.css";
/* /搜索框 */
.head_search {
    padding-top: 10px;
    overflow: hidden;
}
.el-autocomplete {
    width: 410px;
    float: left;
}
.head_search .search_button {
    float: left;
    width: 40px;
    height: 36px;
    background-image: url("../assets/images/top-search.png");
    background-repeat: no-repeat;
    background-position: 50% 50%;
    outline: none;
    background-color: #128bed;
    border-color: #128bed;
    margin-left: 0px !important;
    border-radius: 0 4px 4px 0;
}
.head_search .search_button:hover {
    background-color: #1877c3;
}
  .list_search{
    position: relative;
    width: 100%;
    height: 40px !important;
    line-height: 40px !important;
  }
    .list_search span{
      background-color: #1ccacc;
      color: #bef5f6;
      padding: 3px 7px;
      font-size: 12px;
      border-radius: 10px;
      right: 0px;
      top: 8px;
     position: absolute;
      line-height: 1;
    }
/* //消息 */
.news_noti{
    width: 20px;
    height: 100%;
    padding-top: 18px;
    /* line-height: 56px; */
    margin-right: 10px;
}
.news_noti img{
    /* margin-top: 8px; */
    width: 19px;
    vertical-align: middle;
}
.news_noti img.active{
  animation: l_r 2s infinite linear;
}
.news_noti .news_num{
    background: #FD485E;
    line-height: 17px;
    top:20px;
    right:10px;
    width: 18px;
    height: 18px;
    padding: 0;
    z-index: 20;
}
</style>
