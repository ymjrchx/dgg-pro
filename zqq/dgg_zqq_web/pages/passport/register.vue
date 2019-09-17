<template>
    <div class="login-panel">
        <div class="login-panel-head clearfix">
            <div class="login-tab col3">
                <a :class="{'active':isTab == 0}" @click="switchTab(0)">用户注册</a>
            </div>
        </div>
        <!-- //快捷登录 开始 -->
        <div class="login-panel-body login" id="normalLoginPanel" :class="{'hide':isTab == 1}">
            <div class="form-group">
                <input placeholder="请输入手机号码" v-model="nameVal" />
                <i class="backImg userTel"></i>
            </div>
            <div class="form-group codeBox">
                <div class="line-8">
                    <input placeholder="请输入验证码" v-model="codeVal" />
                    <i class="backImg userCode"></i>
                </div>
                <div class="line-4 codeImg" style="border:none">
                    <div class="text-center btn btn-primary button  login-btn" :class="{'unUse':isClick}" @click="getCode" :disabled="isClick">{{codeStr}}</div>
                </div>
            </div>
            <div class="form-group eyeFa">
                <input :type="openEye ? 'text' : 'password'" placeholder="密码由6-18位数字与字母组成" v-model="passVal2" @keyup.enter='submit' />
                <i class="backImg userLock"></i>
                <span :class="{'openEye':openEye}" @click="openEye = !openEye"></span>
            </div>
            <div data-v-3d053abc="" class="checkbox m-t-md">
                <label data-v-3d053abc="" class="text-dark-lter">
                    <input type="checkbox" v-model="isRead" style="position:relative;top:2px;margin-right:2px">我已经仔细阅读并接受
                </label>
                <a href="/about/8.html" target="_blank" class="col">《知千秋用户协议》</a>
            </div>
            <div class="form-group ">
                <router-link to='/passport/login' class="col">返回登录</router-link>
                <span class="fr loginIcon">
                    <span @click="wxLogin" class="wx"></span>
                    <span @click="qqLogin" class="qq"></span>
                </span>
            </div>
            <button type="submit" class="btn button   btn-primary login-btn" @click="submit" :class="{'unUse':!isRead}">
                <strong>立即注册</strong>
            </button>
        </div>
        <!-- //快捷登录 end -->
    </div>
</template>
<script>
import { Http } from "~/plugins/axios.js";
import popup from "~/components/popup"; //消息
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
            isRead: true,
            openEye: 0,
            loginNum: 0, //计数超过10次登录失败提示重新刷新验证码
            codeStr: "获取验证码",
            QC: ""
        };
    },
    mounted() {
        this.QC = require("../../assets/js/qq.js");
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
            let min = 60;
            if (this.isClick) return;
            this.loginNum = 0;
            Http(
                "sms/sentidentifycode",
                { phoneNo: this.nameVal, type: "regist" },
                "post",
                true
            ).then(res => {
                console.log(res);
                if (res.data.code == 0) {
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
            });
        },
        submit() {
            if (!this.isRead) {
                return;
            }
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
            if (this.codeVal == "" || !this.codeVal) {
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
                        content: "不可使用纯数字或字母作为密码",
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

            Http(
                "registuser/regist",
                {
                    phoneNo: this.nameVal,
                    smsVerifyCode: this.codeVal,
                    loginPwd: this.passVal2
                },
                "post",
                1
            ).then(res => {
                let data = res.data;
                if (data.code == 0) {
                    popup.created({
                        content: "注册成功",
                        time: 2000
                    });
                    setTimeout(() => {
                        this.$router.push("/passport/login");
                    }, 2000);
                } else {
                    popup.created({
                        content: data.msg,
                        time: 2000
                    });
                }
            });
        },
        wxLogin() {
            if (this._that) {
                sessionStorage.setItem("isLoginBox", true);
            } else {
                sessionStorage.removeItem("isLoginBox");
            }
            location.href =
                "https://open.weixin.qq.com/connect/qrconnect?appid=wx2158bdc43fc7da5e&redirect_uri=" +
                encodeURI(
                    "http://www.zhiqianqiu.com/login/login.html?type=wchart"
                ) +
                "&response_type=code&scope=snsapi_login&state=2014#wechat_redirect";
        },
        qqLogin() {
            if (this._that) {
                sessionStorage.setItem("isLoginBox", true);
            } else {
                sessionStorage.removeItem("isLoginBox");
            }
            this.QC.QC.Login.showPopup({
                appId: "101508189",
                redirectURI: encodeURI(
                    "http://www.zhiqianqiu.com/login/login.html?type=qq"
                )
            });
        }
    }
};
</script>
<style scoped>
@import url("../../assets/css/login");
* {
    box-sizing: border-box;
}
.login-tab {
    width: 100%;
}
.loginIcon > span {
    display: inline-block;
    width: 24px;
    height: 24px;
    display: inline-block;
    background: url("../../assets/images/login/login_icons.png");
    background-size: 72px 96px;
    margin-left: 6px;
    cursor: pointer;
}
.loginIcon .wx {
    background-position: -0px -24px;
}
.loginIcon .qq {
    background-position: -24px -24px;
}
</style>