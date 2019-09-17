<template>
    <div>
        <div class="loginRegister-header">
            <h1 class="logo">
                <router-link to='/'><img src="../assets/images/main/logo.png" alt="知千秋logo" title="知千秋logo"></router-link>
                <span>专注于商标注册查询智能服务化平台</span>
            </h1>
            <div class="right">
                <router-link to='/' class="f16">首页</router-link>
            </div>
        </div>
        <div class="passContent">
            <div class="w">
                <div class="fr passContentBox">
                    <nuxt/>
                </div>
            </div>
        </div>
        <mainFoot/>
    </div>
</template>
  <script>
import mainFoot from "~/components/foot/searchFoot.vue";

export default {
    created() {},
    components: {
        mainFoot
    }
};
</script>
<style scoped>
.loginRegister-header {
    height: 66px;
    background-color: #fff;
    border-bottom: 1px solid #eeeeee;
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2);
}
.loginRegister-header .logo {
    float: left;
    margin: 10px 20px 0 40px;
}
.loginRegister-header .logo span {
    font-size: 14px;
    vertical-align: middle;
    margin-left: 30px;
}
.loginRegister-header .right {
    float: right;
    line-height: 62px;
    margin-right: 40px;
}
/* //中部大图 */
.passContent {
    width: 100%;
    background: #3f3b80 url("../assets/images/login/bg.png") center center
        no-repeat;
    height: 700px;
}
.passContentBox {
    margin-top: 100px;
}
</style>
