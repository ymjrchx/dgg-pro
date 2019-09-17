<template>
   <div class="header">
        <div class="container">
            <div class="img_logo fl">
                <a @click="goIndex">
                    <img src="@/assets/images/logo4.png">
                </a>
            </div>
           
            <ul class="fr head_nav">
                 <!-- <li class="">
                    <a>APP下载</a>
                </li>
                <li class="line">|</li>
                <li class="">
                    <a>会员服务</a>
                </li>
                <li class="line">|</li>
              -->
                <li class="">
                    <router-link to='/Login'>登录</router-link>
                </li>
                <li class="line">|</li>
                <li class="dropdown">
                     <router-link to='/Register'>免费注册</router-link>
                </li>
            </ul>
        </div>
    </div>
</template>
<script>
export default {
  name: "head1",
  props: {
    data: {}
  },
  data() {
    return {
      msg: "Welcome to Your Vue.js App111111111111",
      name: "张三"
    };
  },
  created() {
    
  },
  methods: {
   goIndex(){
        this.$router.replace("/")
    },
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .header {
    width: 100%;
    height: 56px;
    background-color: #fff;
    box-shadow: 0 2px 2px rgba(0,0,0,0.05), 0 1px 0 rgba(0,0,0,0.05);
    }

    .header > div {
        height: 100%;
    }

    .img_logo {
    height: 100%;
    line-height: 56px;
    margin-right: 50px;
    }

    .img_logo img {
    height: 36px;
    }

    .head_nav > li {
        float: left;
        position: relative;
        display: block;
        cursor: pointer;
    }

    .head_nav a {
    padding: 18px 17px 18px 17px;
    font-size: 15px;
    position: relative;
    display: block;
    line-height: 20px;
    color: #222;
    }

    .head_nav .active > a {
    color: #128bed;
    }
    .head_nav li:hover > a {
    color: #128bed;
    }
    .head_nav .line{
        padding: 18px 0;
        color: #eaeaea;
    }
</style>
