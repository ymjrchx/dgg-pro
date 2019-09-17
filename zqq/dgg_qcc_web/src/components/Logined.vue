<template>
    <li class="dropdown userName">
        <img src="" :onerror="userHead" alt=""  @click="goUser">
        <el-dropdown @command="handleCommand" placement="bottom-start">
            <span class="el-dropdown-link Blue">
                {{this.$store.state.userName}}
                <i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown" class="newPage">
                <el-dropdown-item command="msg">消息中心</el-dropdown-item>
                <el-dropdown-item command="center">个人中心</el-dropdown-item>
                <!-- <el-dropdown-item command="b">我的监控</el-dropdown-item>
                <el-dropdown-item command="c" divided>我的公司</el-dropdown-item>
                <el-dropdown-item command="b">我的账户</el-dropdown-item>
                <el-dropdown-item command="c" divided>用户积分</el-dropdown-item>
                <el-dropdown-item command="d">我的报告</el-dropdown-item>
                <el-dropdown-item command="d">我的关注</el-dropdown-item>
                <el-dropdown-item command="d">我的下载</el-dropdown-item>
                <el-dropdown-item command="d">我的笔记</el-dropdown-item>
                <el-dropdown-item command="d">账号设置</el-dropdown-item> -->
                <el-dropdown-item command="out" divided>
                    <span class="zanwei">退出</span>
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </li>
</template>
<script>
import userPhoto from "@/assets/images/icon_user2_default.png"
import Cookie from '@/util/cookie.js'
import Store from '@/store'

export default {
    props: {
        data: {
           
        }
    },
    data() {
        return {
            userHead: 'this.src="' + userPhoto + '"',
            userInfo:"",
            userName:"123"
        };
    },
    created() {
      
    },
    methods: {
        handleCommand(e) {
           
            switch(e){
                case "out":Cookie.delCookie("dqcUserKey");this.$router.push("/Login");Store.commit("logout");break;
                case "msg":this.$router.push({path:"/userCenter",query:{type:"msg"}});break;
                case "center":this.$router.push({path:"/userCenter",query:{type:"center"}});break;
            }
        },
         goUser() {
            this.$router.push("/userCenter");
        }
    }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import "../assets/head.css";
</style>
