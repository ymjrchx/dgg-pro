<template>
    <div class="content">
        <div class="accountSecurity-page order-page">
            <h2 class="order-main-title">账户安全</h2>
            <!-- start section1 -->
            <div class="section1 clearfix">
                <a class="safe-level">
                    <span class="s1">安全级别：</span>
                    <span class="column-2 s2" v-if="safeNum>=3">
                        <i class="bgcolor" :style="'width:'+ (safeNum/5)*100 +'%;background-color:#FF9900'"></i>
                    </span>
                    <span class="column-2 s2" v-if="safeNum<3">
                        <i class="bgcolor" :style="'width:'+ (safeNum/5)*100 +'%'"></i>
                    </span>
                    <span class="s3">
                        <i class="icon icon-di"></i>
                        <span v-if="safeNum<3">较低</span>
                        <span v-if="safeNum==3">中</span>
                        <span v-if="safeNum==4">一般</span>
                        <span v-if="safeNum==5">高</span>
                    </span>

                    <span class="column-4" v-if="safeNum<5">建议您启动全部设置，以保障账户安全。</span>
                </a>
            </div>
            <!-- end section1 -->
            <!-- start section2 -->
            <div class="section2" v-if="safeInfo">
                <table width="100%">
                    <tbody>
                        <tr>
                            <td class="column-1">
                                <i v-if="safeInfo.loginPwd" class="iconfont icon-duigou">&#xe770;</i>
                                <i v-if="!safeInfo.loginPwd" class="iconfont icon-jinggao">&#xe8ed;</i>登录密码</td>
                            <td class="column-2 songti">|</td>
                            <td class="column-3" v-if="safeInfo.loginPwd">安全性高的密码可以使您的账号更安全，建议您定期的更改密码。</td>
                            <td class="column-3" v-if="!safeInfo.loginPwd">
                                <span class="s1">建议您设置密码</span>
                            </td>
                            <td class="column-4">
                                <a class="color" href="/center/flow?type=pwd">设置</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="column-1">
                                <i v-if="safeInfo.email" class="iconfont icon-duigou">&#xe770;</i>
                                <i v-if="!safeInfo.email" class="iconfont icon-jinggao">&#xe8ed;</i>邮箱验证</td>
                            <td class="column-2 songti">|</td>
                            <td class="column-3" v-if="safeInfo.email">您绑定邮箱{{userInfo.email}}，若丢失或停用，请立即更换，避免账户被盗
                            </td>
                            <td class="column-3" v-if="!safeInfo.email">
                                <span class="s1">建议您绑定邮箱，以便于接收知千秋给您发送的重要通知。</span>
                            </td>
                            <td class="column-4" v-if="!safeInfo.email">
                                <a class="color" href="/center/flow?type=email">绑定</a>
                            </td>
                            <td class="column-4" v-if="safeInfo.email">
                                <a class="color" href="/center/flow?type=email&bd=1">更换</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="column-1">
                                <i v-if="userInfo.phoneNo" class="iconfont icon-duigou">&#xe770;</i>
                                <i v-if="!userInfo.phoneNo" class="iconfont icon-jinggao">&#xe8ed;</i>手机验证</td>
                            <td class="column-2 songti">|</td>
                            <td class="column-3">您验证的手机{{userInfo.phoneNo}}若丢失或停用，请立即更换，避免账户被盗。</td>
                            <td class="column-4">
                                <a class="color" href="javacript:void(0)" @click="getPhone">更换</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="column-1">
                                <i v-if="safeInfo.wechat" class="iconfont icon-duigou">&#xe770;</i>
                                <i v-if="!safeInfo.wechat" class="iconfont icon-jinggao">&#xe8ed;</i>微信</td>
                            <td class="column-2 songti">|</td>
                            <td class="column-3" v-if="!safeInfo.wechat">
                                <span class="s1">建议您授权微信登录知千秋提高账号安全。</span>
                            </td>
                            <td class="column-3" v-if="safeInfo.wechat">您绑定的微信号****若丢失或停用，请立即更换，避免账户被盗。</td>
                            <td class="column-4">
                                <a class="color" v-if="safeInfo.wechat" href="javacript:void(0)">已授权</a>
                                <a class="color" v-if="!safeInfo.wechat" @click="wxLogin">未授权</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="column-1">
                                <i v-if="safeInfo.QQ" class="iconfont icon-duigou">&#xe770;</i>
                                <i v-if="!safeInfo.QQ" class="iconfont icon-jinggao">&#xe8ed;</i>QQ</td>
                            <td class="column-2 songti">|</td>
                            <td class="column-3" v-if="safeInfo.QQ">您的QQ********已授权登录知千秋，为了账号内资料安全，请绑定手机号。</td>
                            <td class="column-3" v-if="!safeInfo.QQ">
                                <span class="s1">建议您绑定QQ账号</span>
                            </td>
                            <td class="column-4">
                                <a class="color" v-if="safeInfo.QQ" href="javacript:void(0)">已授权</a>
                                <a class="color" v-if="!safeInfo.QQ" @click="qqLogin">未授权</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <!-- end section2 -->
            <!-- start section3 -->
            <div class="section3 songti">
                <h6>安全服务提升</h6>
                <p>1.确认您登录的是知千秋网址https://www.zhiqianqiu.com，注意防范进入钓鱼网站，不要轻信各种即时通讯工具发送的服务或支付链接，谨防网络诈骗。</p>
                <p>2.建议您安装杀毒软件，并定期更新操作系统等软件补丁，确保账户及交易安全。</p>
            </div>
            <!-- end section3 -->
        </div>
    </div>
</template>
<script>
import popup from "~/components/popup"; //消息
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
    layout: "center",
    components: {},
    data() {
        return {
            userInfo: "",
            safeInfo: "",
            safeNum: 0,
            QC: ""
        };
    },
    mounted() {
        this.loadUserInfo();
        this.loadSafeInfo();
        this.QC = require("../../assets/js/qq.js");
    },
    methods: {
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
        loadSafeInfo() {
            this.$Http(
                "/persional/security",
                { userId: this.$store.state.userInfo.userId },
                "get",
                true
            ).then(res => {
                let obj = res.data.data;
                this.safeInfo = obj;
                for (let k in obj) {
                    if (obj[k]) {
                        this.safeNum++;
                    }
                }
            });
        },
        getPhone() {
            if (!parseInt(this.safeInfo.loginPwd)) {
                popup.created({
                    content: "请先设置密码",
                    time: 2000
                });
                return false;
            }
            this.$router.push("/center/flow?type=phone");
        },
        wxLogin() {
            return;
            location.href =
                "https://open.weixin.qq.com/connect/qrconnect?appid=wx2158bdc43fc7da5e&redirect_uri=" +
                encodeURI(
                    "http://www.zhiqianqiu.com/login/login.html?type=wchart"
                ) +
                "&response_type=code&scope=snsapi_login&state=2014#wechat_redirect";
        },
        qqLogin() {
            return;
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
.color {
    color: #ff7200;
}
.safe-level {
    overflow: hidden;
    display: inline-block;
    vertical-align: middle;
}
.safe-level-complete {
    display: inline-block;
    margin-left: 15px;
    color: #ff7200;
    vertical-align: middle;
}
.safe-level .s1 {
    display: inline-block;
    vertical-align: middle;
}

.safe-level .s2 {
    display: inline-block;
    vertical-align: middle;
}

.safe-level .s3 {
    display: inline-block;
    line-height: 12px;
    color: #ff0000;
    margin-left: 8px;
    vertical-align: middle;
}

.safe-level .s3 .icon {
    margin-right: 3px;
    vertical-align: middle;
}

.safe-level .s3 span {
    vertical-align: middle;
}


.accountSecurity-page .section1 .column-1 {
    font-size: 16px;
}
.accountSecurity-page .section1 .column-2 {
    width: 260px;
    height: 12px;
    background-color: #ddd;
}
.accountSecurity-page .section1 .column-2 .bgcolor {
    display: block;
    height: 100%;
    background-color: #ff0000;
}
.accountSecurity-page .section1 .column-3 {
    margin: 0px 0 0 10px;
    color: #ff0000;
}
.accountSecurity-page .section1 .column-3 .iconfont {
    font-size: 18px;
    margin: -1px 3px 0 0;
}
.accountSecurity-page .section1 .column-4 {
    font-size: 12px;
    margin: 2px 0 0 30px;
    color: #999;
}
.accountSecurity-page .section2 {
    margin-top: 50px;
}
.accountSecurity-page .section2 td {
    padding: 18px 0;
    border-top: solid 1px #eee;
    border-bottom: solid 1px #eee;
}
.accountSecurity-page .section2 .column-1 {
    width: 110px;
    padding-left: 5px;
    font-size: 16px;
}
.accountSecurity-page .section2 .column-1 .iconfont {
    color: #94d16d;
    font-size: 16px;
    margin-right: 15px;
}
.accountSecurity-page .section2 .column-1 .icon-jinggao {
    color: #f19038;
}
.accountSecurity-page .section2 .column-2 {
    width: 80px;
    text-align: center;
    color: #ddd;
}
.accountSecurity-page .section2 .column-3 {
    width: 590px;
    color: #666;
    font-size: 14px;
}
.accountSecurity-page .section2 .column-3 .s1 {
    color: #ff0000;
}
.accountSecurity-page .section2 .column-4 {
    width: 70px;
    text-align: center;
    font-size: 14px;
}
.accountSecurity-page .section3 {
    width: 850px;
    margin-top: 50px;
    color: #aaa;
    font-size: 12px;
    border: solid 1px #eee;
    padding: 15px 22px;
    position: relative;
}
.accountSecurity-page .section3 h6 {
    position: absolute;
    top: -8px;
    left: 50px;
    padding: 0 10px;
    background-color: #fff;
}
.accountSecurity-page .section3 p {
    padding: 8px 0;
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
</style>
