<template>
    <div class="container content" style="min-height:700px">
        <div class="">
            <h1 class="head">{{json.type || "个人信息"}}</h1>
            <h2>
                <!-- 求职情况：浏览
                <i>9</i>次 -->
                <span>
                    <!-- 编号:<label>225314</label>  -->
                    更新：{{json.time || "-"}}
                    <!-- <b>
                        <a style="color: red;">举报此简历</a>
                    </b> -->
                </span>
            </h2>
            <div class="c" >
                
                <div class="touxiang" :class="{'touxiangG': json.sex == '女'}"></div>
                <div class="jiben">
                    <p class="openDegree1"></p>
                    <ul>
                        <!-- 名字+手机验证 -->
                        <li class="name" style="position:relative">
                           {{json.name || "-"}}
                          <img src="@/assets/images/rz.png" alt="手机认证" class="rz_phone">
                          <em>手机已认证</em>
                            <div v-if="this.$store.state.userInfo.isInner == 1" class="coll" title="收藏" @click.stop="isCollFuc" :class="{'active': isColl,'loading':isLoading}"></div>
                        </li>
                        <!-- 基本信息 -->
                        <li>
                            <span class="sex">{{json.sex || "-"}}</span>|
                            <span>{{json.age || "-"}}</span>|
                            <span>{{json.education || "-"}}</span>|
                            <span>{{json.experience || "-"}}</span>
                            |
                            <span>{{json.area || "-"}}</span>
                        </li>
                        <!-- 标签 -->
                        <li>
                            <i v-for="(item,index) in labels" :key="index"> {{item}}</i>

                        </li>
                    </ul>
                    <!-- 证书信息 -->
                    <ul class="zhengs_zhuangt">
                        <li>
                            <span>证书专业：</span>{{json.certificate_type || "-"}}
                        </li>
                        <li>
                            <span>证书状态：</span>
                            {{json.certificate_status || "-"}} </li>
                        <li>
                            <span>期望薪水：</span> {{json.salary || "-"}}
                        </li>
                        <li>
                            <span>社保所在地：</span>{{json.insurance_area || "-"}}
                        </li>

                    </ul>
                </div>
                <div style="clear: both"></div>
            </div>

        </div>
        <div class="cvbtg" v-if="showTel">
            <a @click="lookTel" alt="查看电话" class="resume_see" >查看联系方式</a>
        </div>
        <div class="lxTel" v-else style="height:74px">
            <span>联系电话：</span>
            <!-- <img src="../assets/images/62762.png" alt="123"> -->
            <img :src="fullImg(json.image_url)" alt="联系电话">
        </div>
        <div class="jiben more">
            <dl>
                <dd>其他说明</dd>
                <dt>
                    {{returnStr(json.other_note)}}
                    <!-- <br> 联系我时请说是<a> 顶企查 </a>上看到的，谢谢！ -->
                </dt>
                
            </dl>
            <div class="clear"></div>
        </div>
    </div>
</template>
<script>
import Store from "@/store";
import {TelPath} from "@/util/Http.js";
import {IsLogin} from "@/util/common"

export default {
    data() {
        return {
            json: "",
            labels:[],
            showTel:1,
            isColl:0,
            isLoading:0
        };
    },
    created() {
        let obj = this.$route.query;

        if(obj.resume){
            this.isColl = obj.resume
            delete obj.resume
        }
        this.initPage(obj);
    },
    computed:{
    },
    methods: {
        initPage(obj) {
         
            this.$axios({type:"get",url:"companyAssets/resumesDetail",data:obj, success:res => {
              
                let data=res.data;
                //console.log("參數props",data);
                if(data.code == 0){
                    this.json=data.data[0];
                    this.splitArr(data.data[0].labels)
                }
                else  this.$message.error(data.msg);
                
            }});
        },
        splitArr(str){
            this.labels=str.split(",");
        },
        returnStr(str){
            if(str){
                let str1 = str.replace("其他说明","").split("联系我时");
                return str1[0]
            }
            return ""
        },
        fullImg(path){
            return TelPath+path
        },
        lookTel(){
            let _this=this;
            if(IsLogin(_this)) return ;
            this.showTel=0;
        },
        isCollFuc(){
            let _this = this;
            if(IsLogin(_this)) return ;

            if(this.isLoading) return;
            else this.isLoading = 1;

            let obj=Object.assign({},{cvId:this.$route.query.url,userId:this.$store.state.userInfo.userId});

            if(!this.isColl){
                this.$axios({"type":'get',url:'api/userFocusCv/addUserFocusCv.do',data:obj,success:res=>{
                   
                    if(res.data.code == 0 ){
                        this.isColl=res.data.data
                    } 
                    else{
                        this.$message.error(res.data.msg);
                    }
                    this.isLoading = 0; 
                }})
            }
            else{
                delete obj.cvId;
                obj.userFocusCvId=this.isColl;
                this.$axios({"type":'get',url:'api/userFocusCv/cancelFocus.do',data:obj,success:res=>{
                   if(res.data.code == 0 )  this.isColl=0
                    else this.$message.error(res.data.msg);
                    this.isLoading = 0; 
                }})
            }
        }
    },
    mounted() {}
};
</script>
<style scoped>
.container {
    margin: 20px auto 50px;
    background: #fff;
    padding: 30px 100px 30px;
}
.c{
    width: 800px;
    margin: auto;
}

.head {
    font-weight: bold;
    font-size: 26px;
    text-align: center;
}
h2 {
    position: relative;
    display: block;
    height: 30px;
    margin-top: 14px;
    padding-left: 10pt;
    font-size: 13px;
    font-weight: 400;
    color: #666666;
    border-bottom: 1px dashed #dddddd;
    background: none;
    line-height: 30px;
}
.bor-k,
.jiben,
.jiben li,
.touxiang,
.zs {
    float: left;
}
h2 em {
    color: red;
    font-style: normal;
}
h2 span {
    position: absolute;
    top: 2px;
    right: 0;
    margin-right: 5px;
    font-weight: 400;
    font-size: 13px;
}
h2 label {
    margin-right: 20px;
}
h2 b {
    margin: 0 0 0 20px;
    font-weight: 400;
}
h2 a {
    font-size: 13px;
    color: #ff3c00;
}
.jiben {
    position: relative;
    margin: 0 auto;
    padding: 30px 0px 0px 40px;
    width: 500px;
}
.more{
    width: 800px;
}
.jiben p {
    position: absolute;
    top: 85px;
    left: 450px;
    display: block;
    /* background: url('../../www/images/yz.jpg') left top no-repeat */
}
.jiben .openDegree0 {
    display: block;
    width: 90pt;
    height: 60px;
    background-position: 0 -10px;
}
.jiben .openDegree1 {
    display: block;
    width: 150px;
    height: 5pc;
    background-position: -10px -123px;
}
.jiben .openDegree2 {
    display: block;
    width: 90pt;
    height: 60px;
    background-position: 0 -70px;
}
.jiben .openDegree3 {
    display: block;
    width: 90pt;
    height: 60px;
    background-position: -75pt -8px;
}
.jiben li {
    /*margin-right: 20px;*/
    width: 500px;
    margin-bottom: 30px;
    white-space: nowrap;
    font-size: 14px;
    color: #666666;
    overflow: hidden;
}
.jiben li .tel {
    color: red;
    white-space: nowrap;
    font-weight: 700;
    font-size: 20px;
}
.jiben li b {
    display: inline-block;
    padding-left: 33px;
    height: 26px;
    color: #000;
    font-size: 9pt;
    line-height: 26px;
}

.jiben dl {
    display: block;
    padding: 0 0 0px 70px;
}
.jiben dl dd {
    margin-bottom: 15px;
    font-weight: 700;
    font-size: 16px;
    line-height: 35px;
}
.jiben dl dt {
    font-size: 14px;
    line-height: 25px;
}
.jiben dl dt a {
    font-family: "微软雅黑", "黑体";
    font-size: 16px;
    color: #128bed;
}
.touxiang {
    width: 118px;
    height: 118px;
    margin: 30px 0 10px 60px;
    padding: 0;
    background: url("../assets/images/resume_photo.png") no-repeat;
    border-radius: 100%;
}
.touxiangG {
    background-position: 0 -118px;
}
.cvbtg {
    display: inline-block;
    width: 745px;
    height: 74px;
    padding-left: 255px;
}
.cvbtg a {
    float: right;
    width: 190px;
    height: 50px;
    font-size: 18px;
    margin: 12px 70px 0 0;
    line-height: 50px;
    text-align: center;
    color: #ffffff;
    background-color: #46afe2;
}
.cvbtg a img{
    max-width: 100%;
    max-height: 100%;
}



.cvbtg a:hover {
    text-decoration: none;
}
.cvbtg .resume_see {
    float: left;
    background-color: #128bed;
}

.jiben .name {
    font-size: 28px;
    color: #000000;
}
.jiben li span {
    padding: 0 10px;
}
.jiben li .sex {
    padding-left: 0px;
}
.jiben .rz_phone {
    width: 22px;
    margin:0 -4px 0 30px; 
}
.jiben .name em {
    font-style: normal;
    font-size: 14px;
    color: #666666;
}
.jiben li i {
    display: inline-block;
    margin-right: 10px;
    padding: 5px 15px;
    color: #128bed;
    background: #D7F5FF;
}
.jiben .zhengs_zhuangt {
    float: left;
}
.jiben .zhengs_zhuangt li {
    margin-bottom: 20px;
    color: #000000;
}
.zhengs_zhuangt li span {
    padding: 0px;
    color: #666666;
}
/* //新增 */
.lxTel{
    padding-left: 300px;
    position: relative
}
.lxTel span{
    position: absolute;
    top: 18px;
    left: 304px;
}
.lxTel img{
    height: 34px;
    margin-left: 80px;
}
/* //收藏 */
.coll{
    position: absolute;
    right: 40%;
    top: 8px;
    background: url('../assets/images/collection.png') center center no-repeat;
    background-size: 100% auto;
    width: 28px;
    height: 28px;
    cursor: pointer;
}
.coll:hover,.active.coll{
  background: url('../assets/images/collection_a.png') center center no-repeat;
   background-size: 100% auto;
}
.coll.loading{
    background: url('../assets/images/icon_refresh.gif') center center no-repeat;
    background-size: 70% auto;
}
</style>