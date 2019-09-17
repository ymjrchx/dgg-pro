<template>
    <div class="content">
        <div class="changePW-page order-page">
            <h2 v-if="$route.query.type == 'pwd'" class="order-main-title">
                修改登录密码
            </h2>
            <h2 v-if="$route.query.type == 'email' && !$route.query.bd" class="order-main-title">
                绑定邮箱地址
            </h2>
            <h2 v-if="$route.query.type == 'email' && $route.query.bd" class="order-main-title">
                更换邮箱地址
            </h2>
            <h2 v-if="$route.query.type == 'phone'" class="order-main-title">
                修改绑定手机
            </h2>

            <!-- start section1 -->
            <div class="section1 clearfix">

                <!-- start 进度条 -->
                <div class="task-status-bar status1" :class="{'status2': isVerify}">
                    <ul>
                        <li class="step step1 step-finish">
                            <div class="step-img"></div>
                            <p>身份验证</p>
                        </li>
                        <li class="line line-1">
                            <span class="line-in"></span>
                        </li>
                        <li class="step step2" :class="{'step-finish': isVerify}">
                            <div class="step-img"></div>
                            <p v-if="$route.query.type == 'pwd'">修改密码</p>
                            <p v-if="$route.query.type == 'email'">邮箱地址</p>
                            <p v-if="$route.query.type == 'phone'">新手机号</p>
                        </li>
                        <li class="line line-2">
                            <span class="line-in"></span>
                        </li>
                        <li class="step step3">
                            <div class="step-img"></div>
                            <p>完成</p>
                        </li>
                    </ul>
                </div>
                <!-- end 进度条 -->
            </div>
            <!-- end section1 -->

            <!-- start section2 -->
            <div class="section2 yzm-form" v-if="!isVerify && ($route.query.type == 'phone')">
                <table>
                    <tbody>
                        <tr>
                            <td class="td-title">密码：</td>
                            <td class="userphone">
                                <input type="password" v-model="pwd" class="myinput" name="userCode">
                            </td>
                        </tr>
                        <tr>
                            <td align="center" colspan="3">
                                <div class="btns">
                                    <a @click="pwdVerify" class="mybtn mybtn-inverse mybtn-lg" href="javascript:;">下一步</a>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <!-- end section2 -->

            <!-- start section2 -->
            <div class="section2 yzm-form" v-if="isVerify && $route.query.type == 'phone'">
                <table>
                    <tbody>
                        <tr>
                            <td class="td-title">新手机号：</td>
                            <td><input type="text" v-model="phone" class="myinput" name="userCode" ref="vailPh" @blur="vailPhone"></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="td-title">图形验证码：</td>
                            <td class="userphone">
                                <input type="text" v-model="verifyCode" class="myinput" name="userCode">
                                <img :src="imgSrc" height="46" alt="" @click="updateCode">
                            </td>
                        </tr>
                        <tr>
                            <td class="td-title">手机验证码：</td>
                            <td class="userphone">
                                <input type="text" v-model="verify" class="myinput" name="userCode">
                                <a class="mybtn mybtn-inverse" @click="getCode(1)" style="" id="code" data="1">{{codeStr}}</a>
                            </td>
                        </tr>

                        <tr>
                            <td align="center" colspan="3">
                                <div class="btns">
                                    <a @click="phoneVerify" class="mybtn mybtn-inverse mybtn-lg" href="javascript:;">下一步</a>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <!-- end section2 -->

            <!-- start section2 -->
            <div class="section2 yzm-form" v-if="!isVerify && $route.query.type != 'phone'">
                <table>
                    <tbody>
                        <tr>
                            <td class="td-title">手机号：</td>
                            <td>{{userInfo.phoneNo}}
                                <span class="color">（已通过验证）</span>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="td-title">图形验证码：</td>
                            <td class="userphone">
                                <input type="text" v-model="verifyCode" class="myinput" name="userCode">
                                <img :src="imgSrc" height="46" alt="" @click="updateCode">
                            </td>
                        </tr>
                        <tr>
                            <td class="td-title">手机验证码：</td>
                            <td class="userphone">
                                <input type="text" v-model="verify" class="myinput" name="userCode">
                                <a class="mybtn mybtn-inverse" @click="getCode" style="" id="code" data="1">{{codeStr}}</a>
                            </td>
                            <td style="position: relative">
                                <p class="list-tip" style="display: block; position: absolute;left: 7px;top:18px;font-size: 12px;width: 150px;">
                                    <em class="icon icon-warning"></em>
                                    <span class="text" style="color: #f08b30;"></span>
                                </p>
                            </td>
                        </tr>

                        <tr>
                            <td align="center" colspan="3">
                                <div class="btns">
                                    <a @click="phoneVerify" class="mybtn mybtn-inverse mybtn-lg" href="javascript:;">下一步</a>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <!-- end section2 -->

            <!-- start section2 -->
            <div class="section2 yzm-form" v-if="isVerify && $route.query.type == 'pwd'">
                <table>
                    <tbody>
                        <tr>
                            <td class="td-title">新密码：</td>
                            <td class="userphone">
                                <input onpaste="return false" type="password" v-model="pwd" class="myinput" name="userCode">
                            </td>
                        </tr>
                        <tr>
                            <td class="td-title">确认密码：</td>
                            <td class="userphone">
                                <input onpaste="return false" type="password" v-model="pwd2" class="myinput" name="userCode">
                            </td>
                        </tr>
                        <tr>
                            <td align="center" colspan="3">
                                <div class="btns">
                                    <a @click="update" class="mybtn mybtn-inverse mybtn-lg" href="javascript:;">下一步</a>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <!-- end section2 -->

            <div class="section2 yzm-form" v-if="isVerify && $route.query.type == 'email'">
                <table>
                    <tbody>
                        <tr>
                            <td class="td-title">邮箱地址：</td>
                            <td class="userphone">
                                <input type="email" v-model="email" class="myinput" name="userCode">
                            </td>
                        </tr>
                        <tr>
                            <td align="center" colspan="3">
                                <div class="btns">
                                    <a @click="update" class="mybtn mybtn-inverse mybtn-lg" href="javascript:;">下一步</a>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <!-- end section2 -->
        </div>
    </div>
</template>
<script>
import popup from "~/components/popup"; //消息
import axios from "axios";
export default {
    head() {
        return {
            title: "个人中心-知千秋官网",
            meta: [
                {
                    name: "keywords",
                    hid: "keywords",
                    content:
                        "商标注册，商标免费查询，商标注册查询，商标注册流程及费用，中国商标网，专利申请，专利检索，发明专利"
                },
                {
                    name: "description",
                    hid: "description",
                    content:
                        "知千秋致力于知千秋致力于运营大数据技术、人工智能等技术重新定义知识产权生态链。提供商标查询,商标注册,专利申请,专利查询,版权登记全流程服务。提供免费商标查询Saas工具,智能商标注册0服务费省钱到底,商标查询,商标注册监控,专利申请等服务获得客户的高度认可。"
                }
            ]
        };
    },
    data() {
        return {
            userInfo: "",
            verify: "",
            verifyCode: "",
            txcode: "",
            isHasPhone: 1, //验证此电话是否已被绑定过
            phone: "",
            isVerify: false,
            pwd: "",
            pwd2: "",
            email: "",
            imgSrc: "",
            codeStr: "获取验证码"
        };
    },
    layout: "center",
    created() {
        if (
            this.$route.query.type != "pwd" &&
            this.$route.query.type != "email" &&
            this.$route.query.type != "phone"
        ) {
            this.$router.replace("/404");
            return false;
        }
    },
    mounted() {
        this.loadUserInfo();
        this.updateCode();
    },
    components: {},
    methods: {
        updateCode() {
            this.$Http("imageidentifycode/send", {}, "post").then(res => {
                let img = res.data.data.verifycode;
                this.txcode = res.data.data.valueCode;
                this.imgSrc = "data:image/jpeg;base64," + img;
            });
        },
        loadUserInfo() {
            this.$Http(
                "/persional/Info",
                { userId: this.$store.state.userInfo.userId },
                "get",
                true
            ).then(res => {
                this.userInfo = res.data.data;
            });
        },
        getCode(type) {
            let phoneNo = this.userInfo.phoneNo;
            let url = "/persional/sms/personalCode";
            if (this.$route.query.type == "phone" && !this.phone) {
                popup.created({
                    content: "请输入新手机",
                    time: 2000
                });
                return false;
            }
            if (type == 1) {
                phoneNo = this.phone;
                url = "/persional/sms/newPersonalCode";
            }
            if (this.verifyCode != this.txcode) {
                popup.created({
                    content: "请先输入正确图形验证码",
                    time: 2000
                });
                return false;
            }
            if (this.codeStr.indexOf("(") > -1) {
                return false;
            }

            let min = 60;
            this.$Http(
                url,
                {
                    phoneNo: phoneNo,
                    type: "123",
                    userId: this.$store.state.userInfo.userId
                },
                "post",
                true
            ).then(res => {
                let json = res.data;
                if (json.code == 0) {
                    popup.created({
                        content: "发送成功",
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
        //判断此手机是否已绑过
        vailPhone() {
            if (!/^1(3|4|5|7|8|9)\d{9}$/.test(this.phone)) {
                popup.created({
                    content: "请填写正确电话号码",
                    time: 1000
                });
                return;
            }
            let requireObj = {
                username: this.phone,
                userId: this.$store.state.userInfo.userId
            };
            sessionStorage.setItem("isFrom", 1);
            this.$Http("persional/checkBind", requireObj, "get").then(res => {
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
        phoneVerify() {
            if (this.$route.query.type == "phone" && !this.phone) {
                popup.created({
                    content: "请输入新手机号",
                    time: 2000
                });
                this.$refs.vailPh.focus();
                return false;
            }
            if (this.$route.query.type == "phone" && this.isHasPhone) {
                popup.created({
                    content: "此电话号码已绑定，请更换号码",
                    time: 2000
                });
                this.$refs.vailPh.focus();
                return false;
            }
            if (!this.verify) {
                popup.created({
                    content: "请输入验证码",
                    time: 2000
                });
                return false;
            }
            this.$Http(
                "/persional/phoneVerify",
                {
                    userId: this.$store.state.userInfo.userId,
                    verify: this.verify
                },
                "get"
            ).then(res => {
                this.isVerify = true;
                if (this.$route.query.type == "phone") {
                    this.$Http(
                        "/persional/updateUsername",
                        {
                            userId: this.$store.state.userInfo.userId,
                            username: this.phone
                        },
                        "get"
                    ).then(res => {
                        popup.created({
                            content: "手机号更新成功",
                            time: 2000
                        });
                        let newUserInfo = this.secretPhone(this.phone);
                        axios
                            .post("/server/loginSession", newUserInfo)
                            .then(res => {
                                this.$store.commit("SET_USER", newUserInfo);
                            });
                        setTimeout(() => {
                            this.$router.push("/center/account");
                        }, 2000);
                    });
                }
            });
        },
        //假装加密
        secretPhone(phoneStr) {
            let arr = (phoneStr + "").split("");
            arr[3] = "*";
            arr[4] = "*";
            arr[5] = "*";
            arr[6] = "*";
            let str = arr.join("");
            return Object.assign({}, this.$store.state.userInfo, {
                phoneNo: str
            });
        },
        pwdVerify() {
            if (!this.pwd) {
                popup.created({
                    content: "请输入密码",
                    time: 2000
                });
                return false;
            }

            this.$Http(
                "/persional/pwdVerify",
                {
                    userId: this.$store.state.userInfo.userId,
                    loginPwd: this.pwd
                },
                "get"
            ).then(res => {
                this.isVerify = true;
            });
        },

        update() {
            if (this.$route.query.type == "pwd") {
                if (!this.pwd) {
                    popup.created({
                        content: "请输入密码",
                        time: 2000
                    });
                    return false;
                }

                if (this.pwd.length < 6 || this.pwd.length > 18) {
                    popup.created({
                        content: "密码必须为6-18位",
                        time: 2000
                    });
                    return false;
                }

                let passBool = /^[1-9a-zA-Z]{6,18}$/.test(this.pwd);

                if (
                    !passBool ||
                    /^[1-9]{6,18}$/.test(this.pwd) ||
                    /^[a-zA-Z]{6,18}$/.test(this.pwd)
                ) {
                    let str = this.pwd.replace(/\w/g, "");
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

                if (!this.pwd2) {
                    popup.created({
                        content: "请输入确认密码",
                        time: 2000
                    });
                    return false;
                }

                if (this.pwd !== this.pwd2) {
                    popup.created({
                        content: "两次密码输入不一致",
                        time: 2000
                    });
                    return false;
                }

                if (/\W/.test(this.pwd)) {
                    popup.created({
                        content: "请设置6-16位字母数字组合密码",
                        time: 2000
                    });
                    return false;
                }

                this.$Http(
                    "/persional/updateLoginPwd",
                    {
                        userId: this.$store.state.userInfo.userId,
                        loginPwd: this.pwd
                    },
                    "get"
                ).then(res => {
                    popup.created({
                        content: "密码更新成功",
                        time: 2000
                    });
                    setTimeout(() => {
                        this.$router.push("/center/account");
                    }, 2000);
                });
            }

            if (this.$route.query.type == "email") {
                if (!this.email) {
                    popup.created({
                        content: "请输入邮箱",
                        time: 2000
                    });
                    return false;
                }

                if (
                    !/^[A-Za-z0-9\u4e00-\u9fa5_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(
                        this.email
                    )
                ) {
                    popup.created({
                        content: "请输入正确邮箱",
                        time: 2000
                    });
                    return false;
                }

                this.$Http(
                    "/persional/updateEmail",
                    {
                        userId: this.$store.state.userInfo.userId,
                        email: this.email
                    },
                    "get"
                ).then(res => {
                    if (this.$route.query.bd) {
                        popup.created({
                            content: "更新邮箱成功",
                            time: 2000
                        });
                    } else {
                        popup.created({
                            content: "绑定邮箱成功",
                            time: 2000
                        });
                    }
                    setTimeout(() => {
                        this.$router.push("/center/account");
                    }, 2000);
                });
            }
        }
    }
};
</script>
<style scoped>
.color {
    color: #ff7200;
}
/*进度条*/
.task-status-bar {
    text-align: center;
}
.task-status-bar.status1 {
}
.task-status-bar ul {
    display: inline-block;
    overflow: hidden;
}
.task-status-bar ul li {
    float: left;
    text-align: center;
    vertical-align: middle;
}
.task-status-bar .step.step-finish p {
    color: #f08b2f;
}
.task-status-bar .step .step-img {
    display: inline-block;
    margin-bottom: 9px;
    width: 26px;
    height: 30px;
}
.task-status-bar .step p {
    width: 56px;
    color: #999;
}
.task-status-bar .step.step2 .step-img {
    background: url(../../assets/images/center/step2.png) no-repeat center;
    background-size: cover;
}
.task-status-bar .step.step3 .step-img {
    background: url(../../assets/images/center/step3.png) no-repeat center;
    background-size: cover;
}

.task-status-bar .step.step1.step-finish .step-img {
    background: url(../../assets/images/center/step1-active.png) no-repeat
        center;
    background-size: cover;
}
.task-status-bar .step.step2.step-finish .step-img {
    background: url(../../assets/images/center/step2-active.png) no-repeat
        center;
    background-size: cover;
}
.task-status-bar .step.step3.step-finish .step-img {
    background: url(../../assets/images/center/step3-active.png) no-repeat
        center;
    background-size: cover;
}
.task-status-bar .line {
    margin-top: 13px;
    width: 210px;
    height: 4px;
    border-radius: 2px;
    background-color: #e2e3e8;
}
.task-status-bar .line span {
    display: block;
    height: 100%;
    border-radius: 2px;
    background-color: #f08b2f;
}
.task-status-bar.status1 .line-1 span {
    width: 50%;
}
.task-status-bar.status1 .line-2 span {
    width: 0%;
}
.task-status-bar.status2 .line-1 span {
    width: 100%;
}
.task-status-bar.status2 .line-2 span {
    width: 50%;
}

.order-main-title {
    margin-bottom: 15px;
    font-size: 16px;
    line-height: 2.8em;
    font-weight: normal;
    border-bottom: 1px solid #e2e3e8;
}
.order-page {
    padding: 20px;
    padding-top: 0px;
    min-height: 700px;
    background-color: #fff;
    box-shadow: 0 4px 6px 0 rgba(113, 114, 119, 0.1);
}
.order-page .no-pay-tips {
    margin-top: -10px;
    margin-bottom: 15px;
    font-size: 12px;
    color: #999;
}
.order-page .no-pay-tips .icon {
    margin-right: 2px;
    width: 12px;
    height: 12px;
}
.order-page .no-pay-tips span {
    display: inline-block;
    vertical-align: middle;
}
.order-page .price-box {
    padding-right: 20px;
    text-align: right;
}
.order-page .price-box .button {
    margin-left: 20px;
    width: 120px;
    line-height: 32px;
    text-align: center;
}

/*修改密码*/
.changePW-page .header {
    position: relative;
    padding-left: 8px;
    font-size: 16px;
}
.changePW-page .taskStatusBar {
    padding-left: 200px;
}
.changePW-page .section1 {
    margin-top: 30px;
    padding: 0 10px;
}
.changePW-page .section2 {
    margin-top: 50px;
}
.changePW-page .section2 table {
    margin: auto;
}
.changePW-page .section2 td {
    padding: 8px 5px;
}
.changePW-page .section2 .myinput {
    width: 200px;
}

.myinput {
    height: 28px;
    border: solid 1px #ccc;
    border-radius: 2px;
    font-size: 14px;
    text-indent: 10px;
}
.myinput:focus {
    border-color: #f08b2f;
}
.yzm-form .mybtn:not(.mybtn-lg) {
    display: inline-block;
    width: 100px;
    line-height: 30px;
    text-align: center;
    font-size: 12px;
    color: #fff;
    background-color: #f08c2f;
    border-radius: 2px;
    vertical-align: top;
    margin-left: 15px;
}

.yzm-form img {
    display: inline-block;
    width: 90px;
    height: 30px;
    vertical-align: top;
    margin-left: 15px;
}

/*按钮*/
.btns .mybtn {
    display: inline-block;
    text-align: center;
    line-height: 28px;
    padding: 0 12px;
    min-width: 60px;
    cursor: pointer;
    font-size: 12px;
    margin: 0 5px;
    background-color: #fff;
    border: solid 1px #ff7200;
    color: #ff7200;
    border-radius: 2px;
}
.btns .mybtn-inverse {
    background-color: #ff7200;
    color: #fff;
}
.btns .mybtn-disable {
    cursor: default;
    background-color: #ddd;
    color: #888;
    border: solid 1px #ddd;
}
.btns .mybtn-lg {
    width: 140px;
    line-height: 32px;
    font-size: 14px;
}
.btns .mybtn-sm {
    line-height: 20px;
    padding: 0 5px;
}
.btns .mybtn .glyphicon {
    margin-right: 5px;
}
</style>
