<template>
    <div>
        <div class="foot_top">
            <div class="w link_flag">
                <ul>
                    <li class="nav_us">
                        <a target="_blank" href="/about/1.html" rel="nofollow">关于我们</a>
                        <a target="_blank" href="/about/2.html" rel="nofollow">加入我们</a>
                        <a target="_blank" href="/about/3.html" rel="nofollow">联系我们</a>
                        <a target="_blank" href="/about/4.html" rel="nofollow">常见问题</a>
                        <a target="_blank" href="/about/5.html" rel="nofollow">服务说明</a>
                        <a target="_blank" href="/about/6.html" rel="nofollow">模板下载</a>
                        <a target="_blank" href="/map" rel="nofollow">网站地图</a>
                    </li>
                    <li>
                        <span>热门产品：</span>
                        <a target="_blank" href='/extension'>商标注册</a>
                        <a target="_blank" href='/patent'>专利申请</a>
                        <a target="_blank" href='/copyright'>版权登记</a>
                        <a target="_blank" href='/show/navigation_patent_law_01.html'>驳回复审</a>
                        <a target="_blank" href='/show/navigation_trademark_law_03.html'>撤三答辩</a>
                        <a target="_blank" href='/show/navigation_trademark_law_08.html'>异议复审</a>
                        <a target="_blank" href='/show/navigation_patent_international_01.html'>国际专利PCT申请</a>
                        <a target="_blank" href='/show/navigation_copyright_register_01.html'>软件著作权登记</a>
                    </li>
                    <li>
                        <span>热门栏目：</span>
                        <a target="_blank" href='/shangbiao/freesearch'>商标查询</a>
                        <a target="_blank" href='/xw/first_sound.html'>政策法规</a>
                        <a target="_blank" href='/xw/knowledge.html'>商标知识</a>
                        <a target="_blank" href='/xw/data_report.html'>商标报告</a>
                        <a target="_blank" href='/question/navigation_trademark.html'>商标问答</a>
                        <!-- <a target="_blank" href='/question/navigation_patent.html'>专利问答</a>
                        <a target="_blank" href='/question/navigation_copyright.html'>版权问答</a> -->
                        <a target="_blank" href='/notice'>商标初审公告</a>
                        <a target="_blank" href='/classify'>商标分类表</a>
                        <a target="_blank" href='/shangbiao/freesearch'>查询商标是否已注册</a>
                        <a target="_blank" href='/history/1.html'>查询结果</a>
                    </li>
                    <li>
                        <span>关于我们：</span>
                        <a target="_blank" href="/about/1.html" rel="nofollow">关于我们</a>
                        <a target="_blank" href="/about/2.html" rel="nofollow">加入我们</a>
                        <a target="_blank" href="/about/3.html" rel="nofollow">联系我们</a>
                    </li>
                </ul>
                <div class="code">
                    <div class="useCode">
                        <p class="white mb8">关注知千秋</p>
                        <img src="../../assets/images/main/code_wx.png" alt="微信"><br>
                        <em class="fr" style="display:block;margin-top:8px;color:#fff;font-size:16px">服务热线：400-871-9995</em>
                    </div>
                </div>
            </div>
        </div>
        <div class="foot_down">
            <div class="w friendLink">
                <nav class="mb5" v-if="$route.fullPath == '/' && footArr.length > 0">
                    <span>友情链接：</span>
                    <template v-for="(item,idx) in footArr">
                        <a :href="item.ext1" target="_blank" :key='idx'>{{item.name}}</a>
                        <span :key='idx+100'> | </span>
                    </template>

                </nav>
                <span>Copyright 2018 四川先锋商标事务所有限公司 版权所有 | 京ICP备16023355号-2 地址：成都市金牛区金府路666号30楼3011、3013室</span>
            </div>
        </div>
    </div>
</template>
  <script>
export default {
    created() {},
    components: {},
    data() {
        return {
            footArr: []
        };
    },
    mounted() {
        
        if (this.$route.fullPath == "/") {
            this.$Http("Count/showLink", {}, "get").then(res => {
                this.footArr = res.data.data;
            });
        }
    },
    methods: {
    }
};
</script>
<style scoped>
* {
    box-sizing: border-box;
}
.foot_top {
    background: #2b2e33;
}
.foot_top a,
.foot_top span {
    color: #999999;
    margin-right: 18px;
    font-size: 12px;
}
.foot_top a:hover {
    color: #fd7d22;
}
.link_flag {
    padding-top: 30px;
    padding-bottom: 1px;
    position: relative;
}
.link_flag li {
    margin-bottom: 20px;
}

.nav_us a {
    color: #fff;
    font-size: 14px;
    margin-right: 32px;
}
.code {
    position: absolute;
    right: 0;
    top: 10px;
}
.useCode {
    text-align: center;
}
.useCode img {
    width: 100px;
    max-width: 100px;
}
/* //下面脚部 */
.foot_down {
    background: #24272b;
}
.friendLink {
    padding: 12px 0;
}
.friendLink span,
.friendLink a {
    color: #999999;
    font-size: 12px;
}
.friendLink a:hover {
    color: #fd7d22;
}
</style>
