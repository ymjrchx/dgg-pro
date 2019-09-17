<template>
  <div class="header" id="top">
    <div class="wrapper">
      <a href="/" class="left"><img  src="../assets/images/logo.png" alt="logo"></a>
      <div class="login right" v-if="!$store.state.userInfo">
        <a href="javascript:void(0);" class="loginBtn" @click="login">登录</a>
        <a class="register" href="javascript:void(0);" @click="register">注册</a>
      </div>

      <div class="login right" v-if="$store.state.userInfo">
        <a href="javascript:void(0);" class="loginBtn name">{{$store.state.userInfo.nickname?$store.state.userInfo.nickname:$store.state.userInfo.phoneNo}}</a>
        <a class="register" href="javascript:void(0);" @click="logout">退出</a>
      </div>

      <Nav />
      
      
    </div>
  </div>
</template>
<script>
import axios from "axios"; 
import Nav from "@/components/Nav"; 
export default {
  methods: {
    logout () {
      this.$popup.created({
        type: "confirm",
        title: "",
        content: "您确认退出吗？",
        success () {
          sessionStorage.removeItem("orderData");
          axios.post("/server/logout").then(res => {
            if (res.data.ok) {
              //window.location.href = "/login"
              window.history.go(0)
              sessionStorage.removeItem("userId")
            }
          }).catch(() => {
              console.log('退出错误')
          });
        }
      });
    },
    login () {
      this.$loginPop(this, 'login')
    },
    register () {
      this.$loginPop(this, 'register')
    }
  },
  components: {
    Nav
  }
}
</script>

<style lang="stylus" scoped>
.header
  overflow hidden
  background #13386f
  height 100px
  line-height 100px
  
  .loginBtn
    padding 5px 15px 6px
    background #fff3e8
    border-radius 3px
    color: #f89d44
    margin-left 35px 
    &.name
      background none
  .register
    padding 5px 15px 6px
    background #f89d44
    border-radius 3px
    color: #fff
    margin-left 8px 
</style>
