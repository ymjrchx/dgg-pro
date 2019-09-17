<template>
    <div class="foot">
        <footer class="bg-dark" id="footer" style="position: relative;">
            <div class="dk">
                <div class="container" style="width:1250px">
                    <div class="row m-t-lg m-b-lg">
                        <footer class="footer ">
                            <div class="row footer-top">
                                <div class="col-xs-12">
                                    <div class="row about" style="text-align:left">
                                        <div class="col-xs-2" style="width: 132px;">
                                            <h4>关于我们</h4>
                                            <ul class="list-unstyled">
                                                <li>
                                                    <router-link to='/About'>联系我们</router-link>
                                                </li>
                                                <li>
                                                    <a @click="goAbout(1)">用户协议</a>
                                                </li>
                                                <li>
                                                    <a @click="goAbout(2)">用户隐私权</a>
                                                </li>
                                                <li>
                                                    <a @click="goAbout(3)">关于我们</a>
                                                </li>
                                                <li>
                                                    <a @click="goAbout(4)">用户帮助</a>
                                                </li>
                                                <li>
                                                    <a @click="goAbout(5)">名词百科</a>
                                                </li>
                                            </ul>
                                        </div>
                                        <div class="col-xs-3" style="width: 260px;">
                                            <h4>联系方式</h4>
                                            <ul class="list-unstyled">
                                                <li>顶企查官方电话：4000-962540</li>
                                                <li>客服邮箱：xiongfeng@dgg.net</li>
                                                <!-- <li>官方客服QQ：4000-962540</li> -->
                                                <li>微信客服:-</li>
                                                <!-- <li>微信公众号：xxxxxxxxx@xxx</li> -->
                                                <li>地址：四川省成都市武侯区桃源广场27楼</li>
                                            </ul>
                                        </div>
                                        <div class="col-xs-4" style="width: 380px;">
                                            <h4>顶企查服务</h4>
                                            <ul class="list-unstyled m_frilink">
                                                <li>
                                                    <span data-href="http://www.qichacha.com/elib">企业库</span>
                                                    <span @click="goLawTool">裁判文书查询</span>
                                                    <!-- <span data-href="http://sanban.qichacha.com" >新三板企业查询</span> -->
                                                    <!-- <span data-href="http://ipo.qichacha.com/" >上市企业查询</span> -->
                                                    <span data-href="http://www.qichacha.com/gongsi">顶企查企业查询</span>
                                                    <!-- <span data-href="http://m.qichacha.com" >顶企查移动版</span> -->
                                                    <!-- <span data-href="http://www.qichacha.com/dianping" >顶企查社区</span> -->
                                                    <!-- <span data-href="http://www.qichacha.com/more_shixins" >企业风险搜索</span> -->
                                                    <span data-href="http://www.qichacha.com/more_brands">商标专利搜索</span>
                                                    <span data-href="http://www.qichacha.com/daohang">企业网址导航</span>
                                                    <span data-href="http://www.qichacha.com/boss">企业高管查询</span>
                                                    <span data-href="http://www.qichacha.com/tax">企业税号查询</span>
                                                    <span data-href="http://www.qichacha.com/news">企业新闻头条</span>
                                                    <span data-href="http://hao.qichacha.com">企业大数据导航</span>
                                                    <!-- <span data-href="http://www.qichacha.com/weixin" >顶企查下载</span> -->
                                                    <!-- <span data-href="http://www.qichacha.com/more_shixins" >企业失信查询</span> -->
                                                    <!-- <span data-href="http://www.yjapi.com/?from=qichacha"  rel="nofollow">企业版</span> -->
                                                </li>
                                            </ul>
                                        </div>
                                        <div class="col-xs-3" style="width: 470px;text-align: right;margin-top:10px;">
                                            <div class="row m_codeimg">
                                                <div class="col-xs-4">
                                                    <span class="ma_wx">微信公众号</span>
                                                    <img src="../assets/images/code_wx.png" alt="顶企查微信公众号" style="width:117px;"> </div>
                                                <div class="col-xs-4">
                                                    <span class="ma_app">扫码下载APP</span>
                                                    <img src="../assets/images/code_app.png" alt="顶企查APP下载" style="width:117px;"> </div>
                                                <div class="col-xs-4">
                                                    <span class="ma_xcx" style="left: 60px;">小程序</span>
                                                    <img src="../assets/images/code_app.png" alt="顶企查APP下载" style="width:117px;"> </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <hr style="border-top: 1px solid #5f6d8f;">
                            <div class="row footer-bottom text-center">
                                <!-- <div class="text-muted m-t-sm">
                                友情链接：
                                <span>
                                    <a href="https://www.liepin.com/" >猎聘网</a>
                                </span>
                                &nbsp;|
                                <span>
                                    <a href="http://www.tmkoo.com/"  rel="nofollow">标库网</a>
                                </span>
                                &nbsp;|
                                <span>
                                    <a href="https://www.zhipin.com/"  rel="nofollow">BOSS直聘</a>
                                </span>
                                &nbsp;|
                                <span>
                                    <a href="http://www.767stock.com/"  rel="nofollow">乐晴智库</a>
                                </span>
                                &nbsp;|
                                <span>
                                    <a href="https://www.fxiaoke.com/"  rel="nofollow">纷享销客</a>
                                </span>
                                &nbsp;|
                                <span>
                                    <a href="http://www.kuaifawu.com/"  rel="nofollow">快法务</a>
                                </span>
                                &nbsp;|
                                <span>
                                    <a href="http://www.ipzhiku.com/"  rel="nofollow"> i智库</a>
                                </span>
                            </div> -->
                            </div>
                            <div class="row footer-bottom text-center">
                                <div class="text-muted m-t-sm">
                                    数据来源：
                                    <span>全国企业信用信息公示系统</span>&nbsp;|
                                    <span>中国裁判文书网</span>&nbsp;|
                                    <span>中国执行信息公开网</span>&nbsp;|
                                    <span>国家知识产权局</span>&nbsp;|
                                    <span>商标局</span>&nbsp;|
                                    <span>版权局</span>
                                </div>
                            </div>
                            <!-- <div class="row footer-bottom text-center">
                            <div class="text-muted m-t-sm">
                                热点省份：
                                <a data-href="http://ah.qichacha.com" >安徽企业</a>&nbsp;|
                                <a data-href="http://bj.qichacha.com" >北京企业</a>&nbsp;|
                                <a data-href="http://cq.qichacha.com" >重庆企业</a>&nbsp;|
                                <a data-href="http://fj.qichacha.com" >福建企业</a>&nbsp;|
                                <a data-href="http://gs.qichacha.com" >甘肃企业</a>&nbsp;|
                                <a data-href="http://gd.qichacha.com" >广东企业</a>&nbsp;|
                                <a data-href="http://gx.qichacha.com" >广西企业</a>&nbsp;|
                                <a data-href="http://gz.qichacha.com" >贵州企业</a>&nbsp;|
                                <a data-href="http://hain.qichacha.com" >海南企业</a>&nbsp;|
                                <a data-href="http://hb.qichacha.com" >河北企业</a>&nbsp;|
                                <a data-href="http://hlj.qichacha.com" >黑龙江企业</a>&nbsp;|
                                <a data-href="http://hen.qichacha.com" >河南企业</a>&nbsp;|
                                <a data-href="http://hub.qichacha.com" >湖北企业</a>&nbsp;|
                                <a data-href="http://hun.qichacha.com" >湖南企业</a>&nbsp;|
                                <a data-href="http://js.qichacha.com" >江苏企业</a>&nbsp;|
                                <a data-href="http://jx.qichacha.com" >江西企业</a>&nbsp;|
                                <a data-href="http://jl.qichacha.com" >吉林企业</a>&nbsp;|
                                <a data-href="http://ln.qichacha.com" >辽宁企业</a>&nbsp;|
                                <a data-href="http://nmg.qichacha.com" >内蒙古企业</a>&nbsp;|
                                <a data-href="http://nx.qichacha.com" >宁夏企业</a>&nbsp;|
                                <a data-href="http://qh.qichacha.com" >青海企业</a>&nbsp;|
                                <a data-href="http://sd.qichacha.com" >山东企业</a>&nbsp;|
                                <a data-href="http://sh.qichacha.com" >上海企业</a>&nbsp;|
                                <a data-href="http://sx.qichacha.com" >山西企业</a>&nbsp;|
                                <a data-href="http://sax.qichacha.com" >陕西企业</a>&nbsp;|
                                <a data-href="http://sc.qichacha.com" >四川企业</a>&nbsp;|
                                <a data-href="http://tj.qichacha.com" >天津企业</a>&nbsp;|
                                <a data-href="http://xj.qichacha.com" >新疆企业</a>&nbsp;|
                                <a data-href="http://xz.qichacha.com" >西藏企业</a>&nbsp;|
                                <a data-href="http://yn.qichacha.com" >云南企业</a>&nbsp;|
                                <a data-href="http://zj.qichacha.com" >浙江企业</a>&nbsp;|
                            </div>
                        </div> -->
                            <div class="row footer-bottom text-center">
                                <div class="text-muted m-t-sm">
                                    <span>
                                        <a data-href="javascript:void(0)" title="顶企查">&copy;2014-2018</a>
                                        <a data-href="http://www.miibeian.gov.cn/"> 京ICP备16006021号-1</a> 版权所有&nbsp;成都顶联互动信息产业有限公司</span>
                                </div>
                            </div>
                        </footer>
                        <!-- <div class="col-sm-12 text-center">
                        <div class="text-muted m-t-sm">
                            <span>交流QQ群：467569586 369254293 257048933 259189047 371601207 374037798</span>
                            <a href="https://ss.knet.cn/verifyseal.dll?sn=e17091132050068868mhtm000000&comefrom=trust"
                                rel="nofollow" >
                                <img class="m-l-sm" style="width: 80px" src="../assets/images/dependable.png"
                                /> </a>
                            <a href="http://www.jsdsgsxt.gov.cn/mbm/entweb/elec/certView.shtml?siteId=2f2c5b85a5154355a56eb3dee98ad8a3"
                                rel="nofollow" >
                                <img class="m-l-sm" style="width: 40px" src="../assets/images/jsdsgsxt.png"
                                /> </a>
                            <a href="https://v.pinpaibao.com.cn/cert/site/?site=www.qichacha.com&at=official" rel="nofollow"
                                >
                                <img class="m-l-sm" style="height: 32.5px;" src="https://static.anquan.org/static/outer/image/gw_124x47.png"
                                /> </a>
                        </div>
                    </div> -->
                    </div>
                </div>
            </div>
        </footer>
        <!-- 复制粘贴结束 -->
    </div>
</template>
<script>
export default {
    name: "banner",
    props: {
        data: {}
    },
    data() {
        return {
            msg: "Welcome to Your Vue.js App111111111111"
        };
    },
    created() {},
    methods: {
        goAbout(num) {
            this.$router.push({ path: "/About", query: { type: num } });
        },
        goLawTool() {
            this.$router.push("/LawTool");
        }
    }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import "../assets/foot.css";
.foot {
    font-family: "Microsoft YaHei", Arial;
    font-size: 14px;
    -webkit-font-smoothing: antialiased;
    line-height: 1.8;
    background-color: #2d3237;
}
.m_frilink > li > span {
    margin-right: 10px;
    display: inline-block;
}
</style>
