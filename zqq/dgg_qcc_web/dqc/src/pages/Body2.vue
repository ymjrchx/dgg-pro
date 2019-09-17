<template>
    <div>
        <Banner></Banner>
        <Content/>
    </div>
</template>
<script>
import Banner from "@/components/Banner";
import Content from "@/components/Content";

import Store from "@/store";
import Cookie from "@/util/cookie.js";

export default {
    name: "",
    components: {
        Banner,
        Content
    },
    data() {
        return {};
    },
    created() {
        this.initWxLogin();
        var _url =
            window.location.protocol + "//" + window.location.host + "/#";
        window.history.pushState({}, 0, _url);
    },
    methods: {
        initWxLogin() {
            if (this.$store.state.isLogin) return;
            var url = location.search; //获取url中"?"符后的字串
            var theRequest = new Object();
            if (url.indexOf("?") != -1) {
                var str = url.substr(1),
                    strs = str.split("&");
                for (var i = 0; i < strs.length; i++) {
                    theRequest[strs[i].split("=")[0]] = unescape(
                        strs[i].split("=")[1]
                    );
                }
                if (theRequest.code) {
                    let requireObj = {
                        code: theRequest.code,
                        state: theRequest.state
                    };
                    this.$axios(
                        {
                            type: "get",
                            url: "wechat/login.do",
                            data: requireObj,
                            success: res => {
                                // console.log(333, res);
                                if (res.data.code == 0) {
                                    this.setCookie(res.data.data);
                                } else {
                                    this.$confirm(
                                        "微信授权失败，是否重新进行授权",
                                        "提示",
                                        {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "error"
                                        }
                                    )
                                        .then(() => {
                                            location.href =
                                                "https://open.weixin.qq.com/connect/qrconnect?appid=wx2f2243594e584069&redirect_uri=" +
                                                encodeURI(
                                                    "https://dqc.dgg.net"
                                                ) +
                                                "&response_type=code&scope=snsapi_login&state=2014#wechat_redirect";
                                        })
                                        .catch(() => {});
                                }
                            }
                        },
                        1
                    );
                }
            }
        },
        setCookie(data) {
            //console.log("用户信息",data)

            Cookie.setCookie("dqcUserKey", JSON.stringify(data), "h1");

            Store.commit("Logined");
            Store.commit("setUserName", data.phoneNo);
            Store.commit("setUserInfo", data);
            // this.$router.replace("/");
            // this.$router.push("/Qualifications_box");
            var _url =
                window.location.protocol + "//" + window.location.host + "/#";
            window.history.pushState({}, 0, _url);
        }
    }
};
</script>
<style>
</style>