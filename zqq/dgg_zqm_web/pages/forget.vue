<template>
  <div class="passContent">
    <div class="wrapper">
      <div class="right passContentBox">
        <div class="login-panel">
        <div class="login-panel-head clearfix">
            <div class="login-tab col3">
                <a :class="{'active':isTab == 0}">找回密码</a>
            </div>
        </div>
        <!-- //快捷登录 开始 -->
        <div class="login-panel-body login" id="normalLoginPanel" :class="{'hide':isTab == 1}">
            <div class="form-group">
                <input placeholder="请输入注册时的电话号码" v-model="nameVal" />
                <i class="backImg userTel"></i>
            </div>
            
            <div class="form-group eyeFa">
                <input :type="openEye ? 'text' : 'password'" placeholder="密码由6-18位数字与字母组成" v-model="passVal2" @keyup.enter='submit' />
                <i class="backImg userLock"></i>
                <span :class="{'openEye':openEye}" @click="openEye = !openEye"></span>
            </div>
            <div class="form-group codeBox">
                <div class="line-8">
                    <input placeholder="请输入验证码" v-model="codeVal" />
                    <i class="backImg userCode"></i>
                </div>
                <div class="line-4 codeImg" style="border:none">
                    <div class="text-center btn-login btn-primary button  login-btn" :class="{'unUse':isClick}" @click="getCode" :disabled="isClick">{{codeStr}}</div>
                </div>
            </div>
            <div class="form-group ">

                <router-link to='/login' class="col">返回登录</router-link>
            </div>
            <button type="submit" class="btn-login button   btn-primary login-btn" @click="submit">
                <strong>立即找回</strong>
            </button>
        </div>
        <!-- //快捷登录 end -->
    </div>
      </div>
    </div>
  </div>
</template>
<script>
import popup from "~/components/popup"; //消息
import axios from "axios";
export default {
  layout: "login",
  data() {
    return {
      isTab: 0,
      checked: true,
      nameVal: "",
      codeVal: "",
      passVal2: "",
      isClick: false,
      openEye: 0,
      loginNum: 0,
      codeStr: "获取验证码"
    }
  },
  methods: {
    getCode() {
      if (!/^1(3|4|5|7|8|9)\d{9}$/.test(this.nameVal)) {
          popup.created({
              content: "请填写正确的电话号码",
              time: 2000
          });
          return;
      }
      if (this.isClick) return;
      this.loginNum = 0;
      let min = 60;
      this.$axios.$post(
          "sms/sentidentifycode",
          { phoneNo: this.nameVal, type: "fogetpwd" }
      ).then(res => {
          console.log(res);
          if (res.code == 0) {
              popup.created({
                  content: "短信发送成功，请注意查收",
                  time: 2000
              });
              this.isClick = true;
              let timer = setInterval(() => {
                  if (min == 1) {
                      this.isClick = false;
                      clearInterval(timer);
                      this.codeStr = "重新获取";
                  } else {
                      min--;
                      if (min < 10) min = "0" + min;
                      this.codeStr = "重新获取( " + min + " )";
                  }
              }, 1000);
          } else {
              popup.created({
                  content: "发送失败",
                  time: 2000
              });
          }
      }).catch((err) => {
          console.log(err)
      });
  },
  submit() {
      if (this.loginNum >= 10) {
          popup.created({
              content: "登录次数超限，请重新获取验证码",
              time: 2000
          });
          return;
      } else {
          this.loginNum += 1;
      }
      if (!/^1(3|4|5|7|8|9)\d{9}$/.test(this.nameVal)) {
          popup.created({
              content: "请填写正确的电话号码",
              time: 2000
          });
          return;
      }
      if ((this.codeVal == "") | !this.codeVal) {
          popup.created({
              content: "请输入验证码",
              time: 2000
          });
          return;
      }
      let passBool = /^[1-9a-zA-Z]{6,18}$/.test(this.passVal2);
      if (
          this.passVal2 == "" ||
          !passBool ||
          /^[1-9]{6,18}$/.test(this.passVal2) ||
          /^[a-zA-Z]{6,18}$/.test(this.passVal2)
      ) {
          let str = this.passVal2.replace(/\w/g, "");
          if (str == "") {
              popup.created({
                  content: "提示：密码中至少包含一位数字或密码",
                  time: 2000
              });
          } else {
              popup.created({
                  content: "提示：密码中包含特殊字符" + str,
                  time: 2000
              });
          }
          return;
      }

      let obj = {
          phoneNo: this.nameVal,
          smsVerifyCode: this.codeVal,
          newLoginPwd: this.passVal2
      };

      this.$axios.$post("registuser/forgetpwd", obj).then(res => {
          let data = res;
          if (data.code == 0) {
              popup.created({
                  content: "找回成功",
                  time: 2000
              });
              setTimeout(() => {
                  this.$router.push("/login");
              }, 2000);
          } else {
              popup.created({
                  content: data.msg,
                  time: 2000
              });
          }
      }).catch((err) => {
          console.log(err)
      });
  }
  }
}

</script>
<style scoped>
@import url("../assets/css/login");
.login-tab {
    width: 100%;
}

</style>
