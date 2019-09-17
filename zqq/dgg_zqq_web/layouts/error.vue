<template>
    <div class="show">
        <div class="w1200">
            <div class="error-box">
                <div class="error-left">
                    <p v-if="!error.statusCode">小主，您访问的页面走丢了...</p>
                    <p v-if="error.statusCode == 500">小主，服务器错误了，请稍后重试...</p>
                    <a href="/">返回首页</a>
                </div>
                <div class="error-right">
                    <img src="../assets/images/404.jpg">
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    props: ["error"],
    head() {
        return {
            title: "错误页面-知千秋",
            meta: [
                {
                    name: "keywords",
                    hid: "keywords",
                    content:
                        "注册商标查询，中国商标官网查询，商标搜索，商标检索,知千秋,权大师"
                },
                {
                    name: "description",
                    hid: "description",
                    content:
                        "免费精准的商标查询平台,中国更全的商标信息库,精准智能的商标相似查询结果,为商标申请人降低商标驳回风险,更快,更准的定位商标注册成功概率。知千秋"
                }
            ]
        };
    },
    data() {
        return {};
    },
    methods: {}
};
</script>

<style scoped lang="stylus">
.show {
    background: transparent url('../assets/images/show/bg.jpg') repeat-x left top;

    .price-text {
        font-size: 26px;
        color: #ff6000;

        span {
            font-size: 20px;
        }
    }
}

.error-box {
    margin: 0 auto;
    padding-top: 110px;
    padding-bottom: 110px;
    width: 1000px;
    text-align: center;

    .error-left {
        display: inline-block;
        width: 400px;
        font-size: 25px;
        vertical-align: middle;
        text-align: left;

        p {
            margin-bottom: 20px;
        }

        a {
            display: inline-block;
            padding: 0 20px;
            color: #ff7200;
            border: 1px solid #ff7200;
            line-height: 1.8em;
            border-radius: 50px;
        }
    }

    .error-right {
        display: inline-block;
        width: 500px;
        vertical-align: middle;

        img {
            width: 100%;
        }
    }
}
</style>

