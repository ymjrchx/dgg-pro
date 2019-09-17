<template>

  <div class="header">
    <!-- <div class="v3_header_bg">
          <div class="header_top">
              <div class="top_link">
                  [
                  <a href="/Account/Login" class="login">登录</a>]&nbsp;&nbsp;&nbsp;[
                  <a href="/Account/Register" class="register">注册</a>] &nbsp;&nbsp;&nbsp;
                  <a href="/DataCenter">用户中心</a>|
                  <a href="/Faq">帮助中心</a>
              </div>
          </div>
      </div> -->
    <div class="header_nav">
      <div class="nav">
        <div class="logo">
          <router-link to='/' style="margin-top: 36px;margin-right: 80px;"><img src="../../assets/images/logo4.png"></router-link>
        </div>
        <div class="nav_list">
          <router-link to='/'>首页</router-link>
          <router-link to='/Api' class="selected">数据接口</router-link>
          <a @click="tip">数据超市</a>
          <a @click="tip">测试工具</a>
          <a @click="tip">常见问题</a>
          <a @click="tip">用户中心</a>
        </div>
        <!-- <div class="nav_search">
          <form action="javascript:void(0)">
            <span tabindex="-1" role="presentation" class="k-widget k-autocomplete k-header k-state-default" style="width: 250px;display:block">
              <input type="text" placeholder="请输入您需要的数据接口" class="k-input" autocomplete="off" role="textbox">
            </span>
            <a  class="search_btn"></a>
          </form>
        </div> -->
      </div>
    </div>
  </div>

</template>
<script>
export default {
    name: "",
    components: {},
    data() {
        return {};
    },
    created() {},
    methods: {
        func() {
            //router.push({ path: '/F' })
            // this.$router.push({ path: "/F", query: { id: 2 } });
        },
        update(val) {
            this.string = val;
        },
        tip(){
            this.$message({
              message: '内测中，敬请期待',
              type: 'warning'
            });
        }
    }
};
</script>
<style scoped>
  @import "../../assets/api.css";
</style>