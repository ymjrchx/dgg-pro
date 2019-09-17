<template>
  <div class="bg">
    <div id="personalCenter2-header">
      <div class="header-box-top">
        <div class="header-box-top-in">
          <span class="header-phone">服务热线：400-871-9995</span>
          <div class="header-box-right">
            <span class="header-user">
              <img src="../assets/images/bg-6.png" onerror="this.src='../assets/images/bg-6.png'" alt="用户头像" title="用户头像">
              <span class="header-user-name">
                {{userInfo.nickname?userInfo.nickname: userInfo.phoneNo}}
                <div class="server-list">
                  <div class="server-list-top">
                    <a href="/center" class="header1">我的知千秋</a>
                  </div>
                  <div class="server-list-bottom">
                    <a href="/center/account" class="header3">账户安全</a>
                    <a href="javascript:;" @click="logout" class="header4">退出登录</a>
                  </div>
                </div>
              </span>
            </span>
            <span class="header-server" onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')">客户服务</span>
          </div>
        </div>
      </div>
      <div class="header-box">
        <div class="logo">
          <a href="/"><img src="../assets/images/mini_logo.png" alt="知千秋logo"></a>
        </div>
        <div class="header-nav">
          <dl class="qds-dropdown">
            <dt class="drop-btn personal-main " :class="{'active': ($route.path.indexOf('message')<0)}">
              <a class="text" href="/center">我的主页</a>
            </dt>
          </dl>
          <dl class="qds-dropdown">
            <dt class="drop-btn personal-info" :class="{'active': ($route.path.indexOf('message')>0)}">
              <a href="/center/message" class="text">
                <span>消息</span>
                <span class="number" v-if="count">
                  <b>({{count}})</b>
                </span>
              </a>
            </dt>
          </dl>
        </div>
      </div>
    </div>
    <div id="personalCenter2-bodyer" class="clearfix">
      <!-- start 左侧导航主导航 -->
      <div id="personalCenter2-leftNav">
        <ul class="nav-box">
          <li class="menu open">
            <div class="menu-header">
              <i class="icon icon-arrowdown"></i>
              <span class="text">订单中心</span>
            </div>
            <ul class="menu-body">
              <li :class="{'selected': $route.path == '/center/smartOrder'}">
                <a class="text" href="/center/smartOrder">智能订单</a>
              </li>
              <li :class="{'selected': $route.query.type == 'navigation_trademark'}">
                <a class="text" href="/center/order?type=navigation_trademark">我的商标</a>
              </li>
              <!-- <li :class="{'selected': $route.query.type == 'navigation_patent'}">
                <a class="text" href="/center/order?type=navigation_patent">我的专利</a>
              </li>
              <li :class="{'selected': $route.query.type == 'navigation_copyright'}">
                <a class="text" href="/center/order?type=navigation_copyright">我的版权</a>
              </li> -->
              <li :class="{'selected': $route.path == '/center/template'}">
                <a class="text" href="/center/template">模板管理</a>
              </li>
            </ul>
          </li>
          <li class="menu ">
            <div class="menu-header">
              <i class="icon icon-arrowdown"></i>
              <span class="text">特色工具</span>
            </div>
            <ul class="menu-body">
              <li>
                <a class="text" target="_blank" href="/show/navigation_trademark_register_01&S7741101919677030400.html">商标注册</a>
              </li>
              <!-- <li>
                <a class="text" target="_blank" href="/brandmanage">商标管理</a>
              </li> -->
              <!-- <li><a class="text" href="/watch">商标监控</a></li> -->
              <li>
                <a class="text" target="_blank" href="/question/navigation_trademark.html">商标问答</a>
              </li>
            </ul>
          </li>
          <li class="menu ">
            <div class="menu-header">
              <i class="icon icon-arrowdown"></i>
              <span class="text">个人设置</span>
            </div>
            <ul class="menu-body">
              <li :class="{'selected': $route.path == '/center/setting'}">
                <a class="text" href="/center/setting">个人信息</a>
              </li>
              <li :class="{'selected': $route.path == '/center/account'}">
                <a class="text" href="/center/account">账户安全</a>
              </li>
              <li>
                <a class="text" onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')"
                  href="javascript:;">联系客服</a>
              </li>
            </ul>
          </li>
        </ul>
      </div>
      <!-- end 左侧导航主导航 -->
      <div id="personalCenter2-rightContainer">
        <nuxt />
      </div>
    </div>
  </div>
</template>
<script>
  import "@/plugins/axios";
  import axios from "axios";
  export default {
    middleware: "auth",
    components: {},
    data() {
      return {
        userInfo: "",
        count: 0
      };
    },
    mounted() {
      this.loadUserInfo();
      this.loadCount();
    },
    methods: {
      loadUserInfo() {
        this.$Http(
          "/persional/Info", {
            userId: this.$store.state.userInfo.userId
          },
          "get",
          true
        ).then(res => {
          this.userInfo = res.data.data;
        });
      },
      loadCount() {
        this.$Http(
          "/msg/counts", {
            userId: this.$store.state.userInfo.userId
          },
          "get",
          true
        ).then(res => {
          this.count = res.data.data;
        });
      },
      logout() {
        sessionStorage.removeItem("orderData");
        axios.post("/server/logout").then(res => {
          if (res.data.ok) {
            window.location.href = "/passport/login";
            sessionStorage.removeItem("userId");
          }
        });
      }
    }
  };

</script>
<style scoped>
  * {
    box-sizing: border-box;
  }

  #uploads {
    width: 340px;
    height: 446px;
  }

  #uploads .cropControls {
    display: none;
  }

  .bg {
    background-color: #f5f5f5;
  }

  .header-phone {
    padding-left: 20px;
    font-size: 14px;
    color: #666666;
    background: url(../assets/images/center/icon-phone.png) no-repeat 0 center;
    background-size: 12px;
  }

  .header-box-top {
    height: 40px;
    line-height: 38px;
    background-color: #f5f5f5;
  }

  .header-box-top-in {
    margin: 0 auto;
    width: 1240px;
    background: #f5f5f5;
  }

  .header-user img {
    width: 20px;
    height: 20px;
    border: 1px solid #e4e4e4;
    border-radius: 50%;
    vertical-align: middle;
  }

  .header-user-name {
    display: inline-block;
    padding-right: 20px;
    color: #666;
    vertical-align: middle;
    background: url(../assets/images/center/icon-hdown.png) no-repeat right center;
  }

  .header-user-name:hover .server-list {
    display: block;
  }

  .person-orderprint .btns {
    margin-top: 20px;
    text-align: center;
  }

  .person-orderprint .cont-crumbs .btns {
    margin-top: 0px;
  }

  .header-box-top .header-box-right {
    float: right;
  }

  .header-ewm img {
    width: 16px;
  }

  .header-box-top .header-box-right>span {
    display: inline-block;
    position: relative;
    margin-right: 15px;
    padding-right: 15px;
    vertical-align: middle;
  }

  .header-box-top .header-box-right>span:after {
    content: "";
    position: absolute;
    top: 12px;
    right: 0px;
    width: 1px;
    height: 16px;
    background-color: #e4e4e4;
  }

  .header-box-top .header-box-right>span:last-of-type:after {
    display: none;
  }

  .header-user-name {
    position: relative;
  }

  .header-user-name .server-list {
    display: none;
    position: absolute;
    left: -20px;
    background: #ffffff;
    box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.2);
    border-radius: 4px;
    width: 120px;
    height: 115px;
    z-index: 99;
  }

  .header-user-name .server-list .server-list-top {
    border-bottom: 1px solid #e4e4e4;
  }

  .header-user-name .server-list div {
    padding: 6px 0;
  }

  .header-user-name .server-list a {
    display: block;
    padding-left: 36px;
    font-size: 12px;
    line-height: 30px;
    color: #666;
  }

  .header-user-name .server-list a.header1 {
    background: url(../assets/images/center/icon-header1.png) no-repeat 15px center;
    background-size: 14px;
  }

  .header-user-name .server-list a.header2 {
    background: url(../assets/images/center/icon-header2.png) no-repeat 15px center;
    background-size: 12px;
  }

  .header-user-name .server-list a.header3 {
    background: url(../assets/images/center/icon-header3.png) no-repeat 15px center;
    background-size: 12px;
  }

  .header-user-name .server-list a.header4 {
    background: url(../assets/images/center/icon-header4.png) no-repeat 15px center;
    background-size: 12px;
  }

  .header-server {
    cursor: pointer;
  }

  .header-ewm {
    position: relative;
  }

  .header-ewm:hover .ewm {
    display: block;
  }

  .header-ewm .ewm {
    display: none;
    position: absolute;
    top: 35px;
    left: -15px;
    padding: 10px;
    text-align: center;
    background-color: #fff;
    box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.2);
    border-radius: 4px;
    z-index: 99;
  }

  .header-ewm .ewm img {
    width: 100px;
  }

  .header-ewm .ewm p {
    margin-top: 6px;
    line-height: 14px;
    color: #f08b2f;
  }

  /* ---------- start 个人中心主框架 ---------- */

  #personalCenter2-wrap {
    color: #333;
  }

  #personalCenter2-wrap .color {
    color: #ff7200;
  }

  #personalCenter2-bodyer {
    width: 1240px;
    padding-top: 35px;
    margin: auto;
    box-sizing: border-box;
  }

  /* 顶部header */

  #personalCenter2-header {
    border-bottom: solid 1px rgba(113, 114, 119, 0.1);
    box-shadow: 0 4px 6px 0 rgba(113, 114, 119, 0.1);
    background-color: #fff;
    min-width: 1240px;
    margin: 0 auto;
    width: 100%;
  }

  #personalCenter2-header .header-box {
    width: 1240px;
    margin: auto;
    height: 80px;
    position: relative;
  }

  #personalCenter2-header .header-box-right {
    display: inline-block;
    margin-left: 15px;
    text-align: left;
    vertical-align: middle;
  }

  #personalCenter2-header .header-box-right h2 {
    font-size: 24px;
    color: #ff7200;
  }

  #personalCenter2-header .header-box-right .btn {
    display: inline-block;
    margin-top: 3px;
    width: 100px;
    line-height: 18px;
    font-size: 12px;
    border: 1px solid #ff7200;
    color: #ff7200;
    text-align: center;
    border-radius: 2px;
    white-space: nowrap;
  }

  #personalCenter2-header .logo {
    padding-top: 0;
    height: 100%;
    color: #ff7200;
    text-align: center;
    position: absolute;
    top: 0px;
    left: 20px;
    z-index: 2;
  }

  #personalCenter2-header .logo a {
    display: block;
    height: 100%;
    line-height: 80px;
  }

  #personalCenter2-header .logo img {
    width: 122px;
    vertical-align: middle;
  }

  #personalCenter2-header .header-nav {
    height: 100%;
    padding-left: 190px;
    position: relative;
  }

  #personalCenter2-header .header-nav .qds-dropdown {
    float: left;
    margin-right: 50px;
  }

  #personalCenter2-header .header-nav .qds-dropdown .drop-btn {
    padding: 0px;
  }

  #personalCenter2-header .header-nav .qds-dropdown .drop-btn.active .text {
    border-bottom: 3px solid #ff7200;
    color: #ff7200;
  }

  #personalCenter2-header .header-nav .qds-dropdown .drop-btn .text {
    display: inline-block;
    float: none;
    font-size: 18px;
    line-height: 79px;
    vertical-align: middle;
  }

  #personalCenter2-header .header-nav .qds-dropdown .drop-btn .icon-arrowdown {
    float: none;
    display: inline-block;
    vertical-align: middle;
  }

  #personalCenter2-header .header-nav .qds-dropdown .drop-btn .number {
    margin-left: 3px;
    font-size: 18px;
  }

  #personalCenter2-header .header-nav .qds-dropdown .drop-btn .number b {
    color: #ff7200;
  }

  #personalCenter2-header .header-nav .qds-dropdown .drop-menu {
    top: 70px;
  }

  #personalCenter2-header .header-nav .qds-dropdown .icon-asmkticon0136 {
    font-size: 19px;
    margin-right: 2px;
    vertical-align: -2px;
  }

  #personalCenter2-header .header-nav .qds-dropdown .icon-tuichu {
    font-size: 14px;
    margin-right: 3px;
  }

  #personalCenter2-header .header-nav .link-putong {
    display: block;
    float: right;
    line-height: 30px;
    padding: 0 15px;
    margin: 25px 0 0 0;
    border: solid 1px #ff7200;
    border-radius: 20px;
    position: absolute;
    right: 0;
  }

  /* 左导航 */

  #personalCenter2-leftNav {
    float: left;
    width: 160px;
    border: solid 1px #f5f5f5;
    padding: 0px 0 10px;
    background-color: #fff;
    box-shadow: 0 4px 6px 0 rgba(113, 114, 119, 0.1);
    box-sizing: border-box;
  }

  #personalCenter2-leftNav .nav-box {
    padding: 10px 0;
  }

  #personalCenter2-leftNav .nav-box .menu .menu-header {
    margin-top: 10px;
    margin-bottom: 4px;
    height: 26px;
    line-height: 26px;
    cursor: pointer;
    font-size: 16px;
  }

  #personalCenter2-leftNav .nav-box .menu .menu-header .icon {
    display: block;
    float: left;
    margin: 10px 8px 0 25px;
    width: 6px;
    height: 6px;
    background-color: #ff7200;
    border-radius: 50%;
  }

  #personalCenter2-leftNav .nav-box .menu .menu-header .text {
    display: block;
    float: left;
  }

  #personalCenter2-leftNav .nav-box .menu .menu-body li a {
    display: block;
    line-height: 32px;
    padding-left: 40px;
    color: #666;
  }

  #personalCenter2-leftNav .nav-box .menu .menu-body li a:hover {
    color: #ff7200;
  }

  #personalCenter2-leftNav .nav-box .menu .menu-body li.selected a {
    color: #ff7200;
  }

  #personalCenter2-leftNav .nav-box .menu.open .menu-header {
    border-left: 6px solid #ff7200;
  }

  #personalCenter2-leftNav .nav-box .menu.open .menu-header .icon {
    margin-left: 20px;
  }

  /* 右侧主显示容器 */

  #personalCenter2-rightContainer {
    float: right;
    width: 1060px;
    min-height: 700px;
    box-sizing: border-box;
    margin-bottom: 70px;
  }

  .home-page-top {
    margin-bottom: 20px;
    padding: 20px;
    background-color: #fff;
    box-shadow: 0 4px 6px 0 rgba(113, 114, 119, 0.1);
  }

  .home-page-bottom {
    padding: 20px;
    background-color: #fff;
    box-shadow: 0 4px 6px 0 rgba(113, 114, 119, 0.1);
  }

  .personalCenter3-wrap {
    padding-bottom: 80px;
    background-color: #f5f5f5;
  }

  .personalCenter3-wrap #personalCenter2-header {
    background-color: #fff;
  }

  .personalCenter3-wrap #personalCenter2-leftNav {
    border: none;
    box-shadow: 0 4px 6px 0 rgba(113, 114, 119, 0.1);
  }

  /* ---------- end 个人中心主框架 ---------- */
  #personalCenter2-wrap {
    color: #333;
  }

  #personalCenter2-wrap .color {
    color: #ff7200;
  }

  #personalCenter2-bodyer {
    width: 1240px;
    padding-top: 35px;
    margin: auto;
  }

</style>
