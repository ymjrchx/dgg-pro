<template>
    <div class="login-panel">
        <div class="login-panel-head clearfix">
            <div class="login-tab col3">
                <a :class="{'active':isTab == 0}" @click="switchTab(0)">快捷登录</a>
            </div>
            <div class="login-tab col3">
                <a :class="{'active':isTab == 1}" @click="switchTab(1)">密码登录</a>
            </div>
        </div>
        <div class="login-panel-body login" id="normalLoginPanel" :class="{'hide':isTab == 1}">
            <div class="form-group">
                <el-input placeholder="请输入手机号码" v-model="telVal" clearable>
                    <i slot="prefix" class="backImg userTel"></i>
                </el-input>
            </div>
            <div class="form-group codeBox">
                <div class="col-md-8">
                    <el-input placeholder="请输入验证码" v-model="passVal" clearable @keyup.enter.native="speedLogin">
                        <i slot="prefix" class="backImg userCode"></i>
                    </el-input>
                </div>
                <div class="col-md-4 codeImg">
                    <div class="codeStr btn btn-primary btn-block login-btn" @click="getCode" :disabled="isClick">{{codeStr}}</div>
                </div>
            </div>

            <div class="form-group m-t-md">
                <div id="dom_id_one"></div>
            </div>
            <div class="checkbox m-t-md">
                <label class="text-dark-lter">
                    <input type="checkbox" :checked="checked" v-model="checked" value="option1"> 一周内保持登录状态
                </label>
            </div>

            <button type="submit" class="btn btn-primary btn-block m-t-md login-btn" @click="speedLogin">
                <strong>立即登录</strong>
            </button>

        </div>
        <div class="login-panel-body login" :class="{'hide':isTab == 0}" id="verifyLoginPanel">

            <div class="form-group">
                <el-input placeholder="请输入用户名" v-model="nameVal" clearable>
                    <i slot="prefix" class="backImg userHead"></i>
                </el-input>
            </div>
            <div class="form-group codeBox">
                <div class="col-md-8">
                    <el-input placeholder="请输入验证码" v-model="codeVal" clearable>
                        <i slot="prefix" class="backImg userCode"></i>
                    </el-input>
                </div>
                <div class="col-md-4 codeImg">
                    <img :src="imgSrc" alt="" @click="updateCode">
                </div>
            </div>
            <div class="form-group m-t-md">
                <el-input placeholder="请输入密码" v-model="passVal2" clearable type="password" @keyup.enter.native="pwdLogin">
                    <i slot="prefix" class="backImg userLock"></i>
                </el-input>
            </div>
            <div class="form-group m-t-md">
                <div id="dom_id_one"></div>
            </div>
            <div class="checkbox m-t-md">
                <label class="text-dark-lter">
                    <input type="checkbox" v-model="checked" :checked="checked" value="option1"> 一周内保持登录状态
                </label>
            </div>

            <button type="submit" class="btn btn-primary btn-block m-t-md login-btn" @click="pwdLogin">
                <strong>立即登录</strong>
            </button>
        </div>
        <div class="otherLogin" style="padding: 0px 30px 20px 30px;overflow:hidden;line-height:28px;">
            <!-- <span class="wxLogin" alt="微信授权" title="微信授权" @click="wxLogin"></span> -->
            <span class="fl accReg">
                <router-link to='/Register' class="text-primary">账号注册</router-link>
            </span>
            <span class="fl accForget">
                <router-link to='/LosePwd'>忘记密码？</router-link>
            </span>
            <span class="fr">
                <img src="../../assets/images/wx2.png" alt="微信授权" title="微信授权" @click="wxLogin">
                <img src="../../assets/images/qq.png" alt="QQ授权" title="QQ授权">
            </span>
        </div>
    </div>
</template>
<script>
import Cookie from "@/util/cookie.js";
import Store from "@/store";

export default {
    name: "",
    components: {},
    data() {
        return {
            isTab: 0,
            telVal: "",
            passVal: "",
            checked: 1,
            nameVal: "",
            codeVal: "",
            passVal2: "",
            imgSrc: "",
            isClick: false,
            codeStr: "获取验证码"
        };
    },
    created() {
        this.updateCode();
    },
    methods: {
        update(val) {
            this.string = val;
        },
        switchTab(num) {
            this.isTab = num;
        },
        updateCode() {
            this.$axios({
                type: "post",
                url: "imageidentifycode/send",
                data: {},
                success: res => {
                    // console.log("图片",res.data)
                    let img = res.data.data.verifycode;
                    this.imgSrc = "data:image/jpeg;base64," + img;
                }
            });
        },
        getCode() {
            // console.log("执行额")
            if (!/^1(3|4|5|7|8|9)\d{9}$/.test(this.telVal)) {
                this.$message({
                    message: "请填写正确的电话号码",
                    type: "error",
                    duration: 1000
                });
                return;
            }
            let min = 60;
            this.$axios(
                {
                    type: "post",
                    url: "sms/sentidentifycode",
                    data: { phoneNo: this.telVal, type: "123" },
                    success: res => {
                        let json = res.data;
                        //console.log("短信", json);
                        if (json.code == 0) {
                            this.$message({
                                message: "短信已发送，请注意查收",
                                type: "success",
                                duration: 1000
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
                            this.$message({
                                message: json.msg,
                                type: "error",
                                duration: 2000
                            });
                        }
                    }
                },
                1
            );
        },
        speedLogin() {
            //短信验证码登录
            if (this.telVal == "" || this.passVal == "") {
                this.$message({
                    message: "请填写完整的登录信息",
                    type: "error"
                });
                return;
            }

            let obj = {
                phoneNo: this.telVal,
                smsVerifyCode: this.passVal,
                time: this.checked ? 7 : 0
            };
            this.$axios(
                {
                    type: "post",
                    url: "regist/loginByCode",
                    data: obj,
                    success: res => {
                        let data = res.data;
                        //console.log("用户信息",data)
                        if (data.code == 0) {
                            this.setCookie(data.data);
                            this.$message({
                                message: "登录成功",
                                type: "success",
                                duration: 800,
                                onClose: () => {
                                    if (this.$route.query.back) {
                                        this.$router.go(-1);
                                    } else {
                                        this.$router.push("/");
                                    }
                                }
                            });
                        } else {
                            this.$message({
                                message: data.msg,
                                type: "error",
                                duration: 1000
                            });
                        }
                    }
                },
                1
            );
        },
        pwdLogin() {
            //密码登录

            if (
                this.nameVal == "" ||
                this.codeVal == "" ||
                this.passVal2 == ""
            ) {
                this.$message({
                    message: "请填写完整的登录信息",
                    type: "error"
                });
                return;
            }

            let obj = {
                imageVerifyCode: this.codeVal,
                loginPwd: this.passVal2,
                phoneNo: this.nameVal,
                type: "",
                time: this.checked ? 7 : 0
            };
            this.$axios(
                {
                    type: "post",
                    url: "registuser/login",
                    data: obj,
                    success: res => {
                        let data = res.data;
                        //console.log("用户信息",data)
                        if (data.code == 0) {
                            this.setCookie(data.data);
                            this.$message({
                                message: "登录成功",
                                type: "success",
                                duration: 2000,
                                onClose: () => {
                                    // this.$router.push("/");
                                    if (this.$route.query.back) {
                                        this.$router.go(-1);
                                    } else {
                                        this.$router.push("/");
                                    }
                                }
                            });
                        } else {
                            this.$message({
                                message: data.msg,
                                type: "error",
                                duration: 2000
                            });
                        }
                    }
                },
                1
            );
        },
        setCookie(data) {
            //console.log("用户信息",data)
            if (this.checked) {
                Cookie.setCookie("dqcUserKey", JSON.stringify(data), "d7");
            } else {
                Cookie.setCookie("dqcUserKey", JSON.stringify(data), "h1");
            }
            Store.commit("Logined");
            Store.commit("setUserName", data.phoneNo);
            Store.commit("setUserInfo", data);
            if (data.type == 2) {
                Store.commit("closeVipBtn");
            }
        },
        wxLogin() {
            location.href =
                "https://open.weixin.qq.com/connect/qrconnect?appid=wx2f2243594e584069&redirect_uri=" +
                encodeURI("https://dqc.dgg.net") +
                "&response_type=code&scope=snsapi_login&state=2014#wechat_redirect";
        }
    }
};
</script>
<style scoped>
.codeStr {
    width: 100%;
    height: 100%;
    text-align: center;
    font-size: 14px;
    border-radius: 0px 4px 4px 0px;
    -webkit-border-radius: 0px 4px 4px 0px;
    -moz-border-radius: 0px 4px 4px 0px;
    -ms-border-radius: 0px 4px 4px 0px;
    -o-border-radius: 0px 4px 4px 0px;
    cursor: pointer;
}
.codeImg img {
    width: 100%;
    height: 100%;
    cursor: pointer;
}
.otherLogin img {
    width: 26px;
    cursor: pointer;
    margin-left: 4px;
}
.accReg {
    margin-right: 16px;
}
</style>