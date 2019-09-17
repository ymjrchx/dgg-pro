<template>
    <div class="header">
        <div class="w">
            <div class="img_logo fl">
                <a @click="goIndex">
                    <img src="../assets/images/logo4.png">
                </a>
            </div>
            <ul class="fl head_nav">
                <li>
                     <router-link to='/'>首页</router-link>
                </li>     
                <li :class="{'active':this.$store.state.headActive == 0 }" @click="goIndex">
                    <a>查企业</a>
                </li>
                <!-- <li class="">
                    <a>搜老板</a>
                </li>
                <li class="">
                    <a>找关系</a>
                </li> -->
                <li class="dropdownBox">
                    <a class="ripple">应用
                        <b class="caret"></b>
                    </a>
                    <!-- <div class="userBox" v-show="applicationShow" @click.stop="applicationShow=1"> -->
                    <div class="userBox">
                        <div class="col-xs-4" @click.stop="go(0)">
                            <!-- <img src="../assets/images/icon_hot.png" alt="" class="icon_hot"> -->
                            <div class="user_icon i9"></div>
                            <span>接口平台</span>
                        </div>
                        <div class="col-xs-4" @click.stop="go(1)">
                            <div class="user_icon i12"></div>
                            <span>建筑资质</span>
                        </div>
                
                        <div class="col-xs-4" @click.stop="go(3)">
                            <div class="user_icon i5"></div>
                            <span>裁判文书</span>
                        </div>
                        <!-- <div class="col-xs-4">
                            <div class="user_icon i2"></div>
                            <span>千寻地图</span>
                        </div>
                        <div class="col-xs-4">
                            <div class="user_icon i3"></div>
                            <span>网址导航</span>
                        </div>
                        <div class="col-xs-4">
                            <div class="user_icon i4"></div>
                            <span>批量查询</span>
                        </div>
                        
                        <div class="col-xs-4">
                            <div class="user_icon i6"></div>
                            <span>企业库</span>
                        </div>
                        <div class="col-xs-4">
                            <div class="user_icon i7"></div>
                            <span>流量分析</span>
                        </div>
                        <div class="col-xs-4">
                            <div class="user_icon i8"></div>
                            <span>企业管家</span>
                        </div>
                        <div class="col-xs-4" @click.stop="goApi">
                            <img src="../assets/images/icon_hot.png" alt="" class="icon_hot">
                            <div class="user_icon i9"></div>
                            <span>接口平台</span>
                        </div>
                        <div class="col-xs-4">
                            <div class="user_icon i10"></div>
                            <span>企风控</span>
                        </div>
                        <div class="col-xs-4">
                            <div class="user_icon i11"></div>
                            <span>媒体认证</span>
                        </div>
                        <div class="col-xs-4">
                            <div class="user_icon i12"></div>
                            <span>招聘信息</span>
                        </div> -->
                    </div>

                </li>
                <li class="dropdownBox">
                    <img src="@/assets/images/icon_new.png" alt="" class="hot_img">
                    <a class="ripple">工具
                        <b class="caret"></b>
                    </a>
                    <div class="userBox2 userBox">
                        <ul>
                            <li @click.stop="go(2)">公司核名工具</li>
                            <li @click.stop="go(4)">经营范围生成器</li>
                        </ul>
                    </div>
                </li>
            </ul>
            <ul class="fr head_nav">
                <!-- <li class="">
                    <a>APP下载</a>
                </li>
                <li class="line">|</li>
                <li class="">
                    <a>会员服务</a>
                </li>
                <li class="line">|</li> -->
                <!-- <li class="">
                    <a>企业版</a>
                </li>
                <li class="line">|</li> -->
                <template v-if="this.$store.state.isLogin">
                    <li class="active" v-if="this.$store.state.showVipBtn">
                        <a @click.stop="doggleVipBox">开通会员</a>
                    </li>
                    <li class="news_noti" @click="goUser">
                        <sup class="el-badge__content is-fixed news_num" v-if=" this.$store.state.msgNum > 0">{{this.$store.state.msgNum}}</sup>
                        <img src="../assets/images/news_noti.png" alt="" :class="{active: this.$store.state.msgNum > 0}">
                        <template v-if="hasNewMsg">
                            <MsgBox/>
                        </template>
                    </li>
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
       
        <!-- <el-dialog :visible.sync="isShowVip"  width="40%" top="5vh">
            <Vip v-if='isShowVip'/>
        </el-dialog> -->
    </div>
</template>
<script>
import Logined from "./Logined.vue";
import Common from "@/util/common.js";
import Store from "@/store";
import MsgBox from "./MsgBox"; 

export default {
    components: {
        Logined,MsgBox
    },
    data() {
        return {
            hasNewMsg:1
        };
    },
    created() {},
    methods: {
        goIndex() {
            this.$router.replace("/");
            Store.commit("updateHeadActive", 0);
        },
        go(num) {
     
            switch (num) {
                case 0:
                    this.$router.push("/Api");
                    break;
                case 1:
                    this.$router.push("/Qualifications_box");
                    break;
                case 2:
                    this.$router.push("/Verify_company");
                    break;
                case 3:
                    this.$router.push("/LawIndex");
                    break;
                case 4:
                    this.$router.push("/CompanyScop");
                    break;
            }
        },
        goUser() {
            this.$router.push("/userCenter");
        },
        doggleVipBox(){

            if(this.$store.state.showVipBox){
                Store.commit("toggleVipBox", 0);
            }
            else{
                Store.commit("toggleVipBox", 1);
            }
        }
    }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import "../assets/head.css";
.dropdownBox {
    position: relative;
      transition: all .2s;
}
.hot_img {
    position: absolute;
    right: 6px;
    top: 8px;
    width: 27px;
    height: 13px;
}

.userBox {
    overflow: hidden;
    background: #fff;
    position: absolute;
    top: 54px;
    left: 50%;
    transform: translateX(-50%);
    z-index: 20;
    font-size: 13px;
    border-radius: 2px;
    -webkit-box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    border: 1px solid #ddd;
    border: 1px solid rgba(0, 0, 0, 0.1);
    padding: 15px;
    width: 300px;
    transition: all .2s;
    display: none;
}

.userBox > div {
    text-align: center;
    margin-top: 8px;
    margin-bottom: 8px;
    position: relative;
}
.userBox > div:hover {
    color: #128bed;
}
.userBox > div:hover div {
    transform: scale(1.2);
}
.user_icon {
    margin: 8px auto;
    background: url("../assets/images/header_app.png") no-repeat;
    background-size: 160px 160px;
    display: block;
    height: 40px;
    width: 40px;
    text-align: center;
    transition: all 0.1s linear;
}
.icon_hot {
    position: absolute;
    right: 20px;
    top: 2px;
    width: 30px;
    height: 15px;
}
.i2 {
    background-position: -40px 0px;
}
.i3 {
    background-position: -79px 0px;
}
.i4 {
    background-position: -120px 0px;
}
.i5 {
    background-position: 0px -40px;
}
.i6 {
    background-position: -40px -40px;
}
.i7 {
    background-position: -79px -40px;
}
.i8 {
    background-position: -120px -40px;
}
.i9 {
    background-position: 0px -80px;
}
.i10 {
    background-position: -40px -80px;
}
.i11 {
    background-position: -80px -80px;
}
.i12 {
    background-position: -120px -80px;
}
/* //消息 */
.news_noti {
    width: 20px;
    height: 100%;
    /* line-height: 56px; */
    padding-top: 18px;
    margin-right: 10px;
    margin-left: 30px;
}
.news_noti img {
    /* margin-top: 8px; */
    width: 19px;
    vertical-align: middle;
}
.news_noti img.active {
    animation: l_r 2s infinite linear;
}

.news_noti .news_num {
    background: #fd485e;
    line-height: 17px;
    top: 20px;
    right: 10px;
    width: 18px;
    height: 18px;
    padding: 0;
    z-index: 20;
}
/* 工具 */
.dropdownBox:hover .userBox{
    display: block;
}
.userBox2{
    width: 180px;
    padding: 15px 0;
    display: none;
}
.userBox2 li{
    line-height: 40px;
    text-align: center;
    font-size: 14px;
    height: 40px;;
}
.userBox2 li:hover{
    background: #409EFF;
    color: #fff; 
}
</style>
