<template>
    <div id="loginIndex">
        <Head/>
        <div class="container">
            <div class="row">
                <div class="col-md-12 m-t-lg wrapper-lg" style="margin-bottom:100px;margin-top:40px;">
                    <div class="login-left-img" style="width:760px;">
                        <img src="@/assets/images/loginbanner.png" style="width:100%;margin-left: -100px;margin-top: 70px;" /> </div>
                    <router-view></router-view>

                </div>
            </div>
        </div>
    </div>
</template>
  <script>
import Head from "./Head";
export default {
    name: "",
    components: {
        Head
    },
    data() {
        return {};
    },
    created() {},
    methods: {
        update(val) {
            this.string = val;
        },
        switchTab(num) {
            this.isTab = num;
        }
    }
};
</script>
<style>
@import "../../assets/login.css";
#loginIndex {
    background: #fff !important;
}

.backImg {
    height: 100%;
    width: 25px;
    text-align: center;
    -webkit-transition: all 0.3s;
    transition: all 0.3s;
    line-height: 40px;
    font-family: element-icons !important;
    speak: none;
    font-style: normal;
    font-weight: 400;
    font-variant: normal;
    text-transform: none;
    line-height: 1;
    vertical-align: baseline;
    display: inline-block;
    -webkit-font-smoothing: antialiased;
}
.userTel {
    background: url("../../assets/images/tel2.png") center center no-repeat;
    background-size: 92% auto;
}
.userHead {
    background: url("../../assets/images/userHead.png") center center no-repeat;
    background-size: 92% auto;
}
.userLock {
    background: url("../../assets/images/password.png") center center no-repeat;
    background-size: 92% auto;
}
.userCode {
    background: url("../../assets/images/code.png") center center no-repeat;
    background-size: 92% auto;
}
.form-group {
    margin-bottom: 20px;
}
.login-btn.btn {
    padding-top: 12px;
    padding-bottom: 12px;
}
.login-other {
    margin-top: 40px;
}
.login-panel-body {
    padding-top: 20px;
}
.hide {
    display: none !important;
}
.codeBox {
    overflow: hidden;
}
.codeImg {
    border-radius: 0px 4px 4px 0px;
    -webkit-border-radius: 0px 4px 4px 0px;
    -moz-border-radius: 0px 4px 4px 0px;
    -ms-border-radius: 0px 4px 4px 0px;
    -o-border-radius: 0px 4px 4px 0px;
    border: 1px solid #dcdfe6;
    height: 46px;
    border-left: none;
}
</style>