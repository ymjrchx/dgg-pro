<template>
  <div class="passContent">
    <div class="wrapper">
      <div class="right passContentBox">
        <div class="login-panel">
          <div class="login-panel-head clearfix">
            <div class="login-tab col3">
              <a :class="{'active':isTab == 0}" @click="switchTab(0)">快捷登录</a>
            </div>
            <div class="login-tab col3">
              <a :class="{'active':isTab == 1}" @click="switchTab(1)">密码登录</a>
            </div>
          </div>
          <!-- //快捷登录 开始 -->
          <div class="login-panel-body login" id="normalLoginPanel" :class="{'hide':isTab == 1}">
            <div class="form-group">
              <input placeholder="请输入你的手机号码" autocomplete="off" v-model="telVal" />
              <i class="backImg userTel"></i>
            </div>
            <div class="form-group codeBox">
              <div class="line-8">
                <input placeholder="请输入验证码" maxlength="4" v-model="passVal" @keyup.enter='speedLogin' />
                <i class="backImg userCode"></i>
              </div>
              <div class="line-4 codeImg" style="border:none">
                <div class="text-center btn-login btn-primary button  login-btn" :class="{'unUse':isClick}" @click="getCode" :disabled="isClick">{{codeStr}}</div>
              </div>
            </div>
            <div class="form-group ">
              <div id="dom_id_one"></div>
            </div>
            <div class="checkbox ">
              <label class="text-dark-lter">
                <input type="checkbox" v-model="isChecked" value="option1"> 一周内保持登录状态
              </label>
            </div>
            <button type="submit" class="btn-login button   btn-primary login-btn" @click="speedLogin">
              <strong>立即登录</strong>
            </button>
          </div>
          <!-- //快捷登录 end -->
          <!-- //密码登录 开始 -->
          <div class="login-panel-body login" :class="{'hide':isTab == 0}" id="verifyLoginPanel">
            <div class="form-group">
              <input type="text" name="name" autocomplete="off" placeholder="请输入你的手机号码" v-model="nameVal" />
              <i class="backImg userHead"></i>
            </div>
            <div class="form-group eyeFa">
              <input :type="openEye ? 'text' : 'password'" placeholder="密码由6-18位数字与字母组成" v-model="passVal2" />
              <i class="backImg userLock"></i>
              <span :class="{'openEye':openEye}" @click="openEye = !openEye"></span>
            </div>
            <div class="form-group codeBox">
              <div class="line-8">
                <input type="text" maxlength="4" placeholder="请输入验证码" v-model="codeVal" @keyup.enter='pwdLogin' />
                <i class="backImg userCode"></i>
              </div>
              <div class="line-4 codeImg">
                <img :src="imgSrc" height="46" width="100%" @click="updateCode">
                </div>
              </div>
              <div class="checkbox ">
                <label class="text-dark-lter">
                  <input type="checkbox" v-model="isChecked" value="option1"> 一周内保持登录状态
                </label>
              </div>
              <button type="submit" class="btn-login button   btn-primary login-btn" @click="pwdLogin">
                <strong>立即登录</strong>
              </button>
            </div>
            <!-- //密码登录 end -->
            <div class="otherLogin" style="padding: 0px 30px 20px 30px;overflow:hidden;line-height:28px;">
              <!-- <span class="wxLogin" alt="微信授权" title="微信授权" @click="wxLogin"></span> -->
              <span class="left accReg">
                <router-link to='/register' class="col">账号注册</router-link>
            </span>
              <span class="left accForget" v-show='isTab == 1'>
                <router-link to='/forget'>忘记密码？</router-link>
            </span>
              <span class="right loginIcon" v-if="0">
                <span @click="wxLogin" class="wx"></span>
                <span @click="qqLogin" class="qq"></span>
              </span>
            </div>
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
      telVal: "",
      passVal: "",
      isChecked: 1,
      nameVal: "",
      codeVal: "",
      passVal2: "",
      imgSrc: "",
      isClick: false,
      codeStr: "获取验证码",
      openEye: 0, //显示明文密码
      loginNum: 0, //计数超过10次登录失败提示重新刷新验证码
      QC: ""
    };
  },
  created() {
        if (this.$store.state.userInfo) {
            this.$router.replace("/");
            return false;
        }
    },
    mounted() {
        if (!this.$store.state.userInfo) {
            sessionStorage.removeItem("userId");
        }
        this.updateCode();
        this.QC = require("../assets/js/qq.js");
    },
    methods: {
        switchTab(num) {
            this.isTab = num;
        },
        updateCode() {
            this.$axios.$post("imageidentifycode/send",null).then(res => {
                let img = res.data.verifycode;
                this.imgSrc = "data:image/jpeg;base64," + img;
            }).catch((err) => {
                console.log(err)
            });
        },
        getCode() {
            if (!/^1(3|4|5|7|8|9)\d{9}$/.test(this.telVal)) {
                popup.created({
                    content: "请填写正确电话号码",
                    time: 2000
                });
                return;
            }
            if (this.isClick) {
                return;
            }
            this.loginNum = 0;
            let min = 60;

            this.$axios.$post(
                "sms/sentidentifycode",
                { phoneNo: this.telVal, type: "123" },
            ).then(res => {
                let json = res;
                console.log("短信", json);
                if (json.code == 0) {
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
        speedLogin() {
            //短信验证码登录
            if (this.loginNum >= 10) {
                popup.created({
                    content: "登录次数超限，请重新获取验证码",
                    time: 2000
                });
                return;
            } else {
                this.loginNum += 1;
            }
            if (this.telVal == "" || this.passVal == "") {
                popup.created({
                    content: "请填写完整的登录信息",
                    time: 2000
                });
                return;
            }

            let obj = {
                phoneNo: this.telVal,
                smsVerifyCode: this.passVal,
                time: this.isChecked ? 7 : 0
            };
            this.$axios.$post("/regist/loginByCode", obj).then(res => {
                //console.log("短信登录", res);
                let requireObj = {
                    nickname: res.data.nickname,
                    userId: res.data.userId,
                    phoneNo: res.data.phoneNo,
                    time: this.isChecked ? 7 : 0
                };
                this.saveLoginInfo(requireObj)
            }).catch((err) => {
                console.log(err)
            });
        },
        pwdLogin() {
            //密码登录
            if (
                this.nameVal == "" ||
                this.codeVal == "" ||
                this.passVal2 == ""
            ) {
                popup.created({
                    content: "请填写完整的登录信息",
                    time: 2000
                });
                return;
            }

            let obj = {
                imageVerifyCode: this.codeVal,
                loginPwd: this.passVal2,
                phoneNo: this.nameVal,
                type: "",
                time: this.isChecked ? 7 : 0
            };
            this.$axios.$post("registuser/login", obj).then(res => {
                let requireObj = {
                    nickname: res.data.nickname,
                    userId: res.data.userId,
                    phoneNo: res.data.phoneNo,
                    time: this.isChecked ? 7 : 0
                };
                this.saveLoginInfo(requireObj)
            }).catch((err) => {
                console.log(err)
            });
        },
        saveLoginInfo (requireObj) {
            axios.post("/server/loginSession", requireObj).then(res => {
                this.$store.commit("SET_USER", requireObj);
                if (this.$route.query.backUrl) {
                    this.$router.push(this.$route.query.backUrl);
                } else {
                    // this.$router.push("/");
                    window.location.href = "/"
                }
            }).catch(() => {
                this.updateCode(); //改变验证码
            });
        },
        wxLogin() {
            location.href =
                "https://open.weixin.qq.com/connect/qrconnect?appid=wx2f2243594e584069&redirect_uri=" +
                encodeURI("https://dqc.dgg.net") +
                "&response_type=code&scope=snsapi_login&state=2014#wechat_redirect";
        },
        qqLogin() {
            popup.created({
                content: "敬请期待",
                time: 2000
            });
        }
    }
}

</script>
<style scoped>
@import url("../assets/css/login");
</style>
