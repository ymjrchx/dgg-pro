<template>
    <div class="login-panel">
        <div v-show='isShow'>
            <div class="login-panel-head clearfix">
                <div class="login-tab col3">
                    <a @click="switchTab(0)">首次登录认证</a>
                </div>
            </div>
            <!-- //快捷登录 开始 -->
            <div class="login-panel-body login" id="normalLoginPanel">
                <div class="form-group">
                    <input placeholder="请输入手机号" v-model="telVal" @blur="vailPhone" />
                    <i class="backImg userTel"></i>
                </div>
                <div class="form-group codeBox">
                    <div class="line-8">
                        <input placeholder="请输入手机验证码" v-model="codeVal" @keyup.enter="submit" />
                        <i class="backImg userCode"></i>
                    </div>
                    <div class="line-4 codeImg" style="border:none">
                        <div class="text-center btn btn-primary button  login-btn" @click="getCode" disabled="isClick">{{codeStr}}</div>
                    </div>
                </div>
                <button type="submit" class="btn button  loginBtn btn-primary login-btn" @click="submit" :class="{'unUse': isHasPhone}">
                    <strong>确定</strong>
                </button>
            </div>
            <!-- //快捷登录 end -->
        </div>
    </div>
</template>

<script>
import { Http } from "~/plugins/axios.js";
import popup from "~/components/popup"; //消息
import axios from "axios";

export default {
    layout: "login",
    head() {
        return {
            title: "知千秋_首次登录中..."
        };
    },
    data() {
        return {
            telVal: "",
            codeVal: "",
            isClick: false,
            codeStr: "获取验证码",
            params: "",
            userId: "",
            isShow: 0, //是否显示
            loading: "",
            isHasPhone: true
        };
    },
    mounted() {
        this.loading = popup.created({
            type: "loading",
            icon: "&#xe61c",
            content: "授权获取..."
        });
        let obj = this.getRequest();
        this.params = obj;
        this.isHas();
    },
    methods: {
        //判断是否已经绑定过手机号
        isHas() {
            if (this.params.type == "qq") {
                Http(
                    "qq/qqLogin",
                    { accessToken: this.params.access_token },
                    "get"
                ).then(res => {
                    //console.log(res);

                    this.userId = res.data.data.userId;
                    if (res.data.data.phoneBind != 0) {
                        this.serverSession(res.data.data);
                    } else {
                        this.loading.close();
                        this.isShow = 1;
                    }
                });
            } else {
                Http(
                    "wechat/login",
                    { code: this.params.code, state: this.params.code },
                    "get"
                ).then(res => {
                    //console.log(res);

                    this.userId = res.data.data.userId;
                    if (res.data.data.phoneBind != 0) {
                        this.serverSession(res.data.data);
                    } else {
                        this.loading.close();
                        this.isShow = 1;
                    }
                });
            }
        },
        //判断此手机是否已绑过
        vailPhone() {
            if (!/^1(3|4|5|7|8|9)\d{9}$/.test(this.telVal)) {
                popup.created({
                    content: "请填写正确电话号码",
                    time: 1000
                });
                return;
            }
            let requireObj = {
                phone: this.telVal,
                userId: this.userId
            };
            let url =
                this.params.type == "qq" ? "qq/checkBind" : "wechat/checkBind";

            Http(url, requireObj, "get").then(res => {
                if (res.data.data != 0) {
                    popup.created({
                        content: "此电话号码已绑定，请更换号码",
                        time: 2000
                    });
                    this.isHasPhone = 1;
                } else {
                    this.isHasPhone = 0;
                }
            });
        },
        serverSession(obj) {
            let requireObj = {
                nickname: obj.nickname,
                userId: obj.userId,
                phoneNo: obj.phoneNo
            };

            axios.post("/server/loginSession", requireObj).then(res => {
                //console.log("这个是返回的参数", res);

                this.$store.commit("SET_USER", requireObj);
                this.loading.close();
                if (sessionStorage.getItem("isLoginBox")) {
                    this.$router.replace("/order");
                    sessionStorage.removeItem("isLoginBox");
                } else {
                    this.$router.replace("/");
                }
            });
        },
        getRequest() {
            var url = this.$route.fullPath.replace("#", "&"); //获取url中"?"符后的字串
            url = url.substr(url.indexOf("?"));
            var theRequest = new Object();
            if (url.indexOf("?") != -1) {
                let str = url.substr(1),
                    strs = str.split("&");
                for (var i = 0; i < strs.length; i++) {
                    theRequest[strs[i].split("=")[0]] = unescape(
                        strs[i].split("=")[1]
                    );
                }
            }
            return theRequest;
        },
        getCode() {
            if (this.isHasPhone) return;

            if (this.isClick) return;
            if (!/^1(3|4|5|7|8|9)\d{9}$/.test(this.telVal)) {
                popup.created({
                    content: "请填写正确电话号码",
                    time: 1000
                });
                return;
            }
            if (this.isClick) return;
            let min = 60;
            Http(
                "sms/sentidentifycode",
                { phoneNo: this.telVal, type: "123" },
                "post",
                true
            ).then(res => {
                let json = res.data;
                //console.log("短信", json);
                if (json.code == 0) {
                    popup.created({
                        content: "发送成功,请注意查收",
                        time: 1500
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
                        time: 1500
                    });
                }
            });
        },
        submit() {
            if (this.isHasPhone) return;
            if (!/^1(3|4|5|7|8|9)\d{9}$/.test(this.telVal)) {
                popup.created({
                    content: "请填写正确的电话号码",
                    time: 1500
                });
                return;
            }
            if (this.codeVal == "") {
                popup.created({
                    content: "请填写短信验证码",
                    time: 1500
                });
                return;
            }
            let obj = {
                phone: this.telVal,
                smsCode: this.codeVal,
                userId: this.userId
            };
            let requireUrl = "";

            if (this.params.type == "qq") {
                requireUrl = "/qq/bingPhone";
            } else {
                requireUrl = "/wechat/bingPhone";
            }

            Http(requireUrl, obj, "get", true).then(res => {
                let requireObj = {
                    nikename: res.data.data.nikename,
                    userId: res.data.data.userId,
                    phoneNo: res.data.data.phoneNo
                };
                axios.post("/server/loginSession", requireObj).then(res => {
                    this.$store.commit("SET_USER", requireObj);

                    if (sessionStorage.getItem("isLoginBox")) {
                        this.$router.replace("/order");
                        sessionStorage.removeItem("isLoginBox");
                    } else {
                        this.$router.replace("/");
                    }
                });
            });
        }
    },
    destroyed() {
        if (this.loading) this.loading.close();
    }
};
</script>

<style scoped>
@import url("../../assets/css/login");
* {
    box-sizing: border-box;
}
.login-panel {
    height: 460px;
    background: transparent;
}
.login-panel > div {
    padding-top: 1px;
    height: 460px;
    background: #fff;
    border-radius: 4px;
}
.login-panel-head {
    padding: 0;
}
.login-tab > a {
    color: #444;
    font-weight: bold;
    font-size: 20px;
}
.codeImg > .btn {
    height: 100%;
}
.form-group {
    margin-bottom: 34px;
}
.loginBtn {
    margin-top: 60px;
}
</style>

