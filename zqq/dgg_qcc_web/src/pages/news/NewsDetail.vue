<template>
    <section class="m-t-lg" style="min-height:700px">
      
        <div class="container " v-if="json">
            <div class="col-sm-9 no-padding-right">
                <section class="panel b-a">
                      <div class="container backs" @click="back2"> 返回</div>
                    <div class="panel-body news_detail_res" style="padding: 0px 48px 0px;">
                        <h2 class="album-detail-title text-center" style="margin-top:0px;">
                            <span style="font-size: 30px;margin-bottom: 22px;line-height: 1.5;font-weight:bold;" class="text-dark">{{json.title}}</span>
                        </h2>
                        <p >
                           {{json.articleSub}}

                        </p>
                        <!-- <div class="news-content text-dark m-b-lg">
                            <p>
                                忽如一夜寒风来，千店万店不再开……这怕是形容当下邻家便利店最恰当的辞藻了。
                            </p>
                            <p> <br> </p>
                            <p>
                                当各色行人还想在邻家便利店门口买上一杯酸奶，在门外徘徊的时候，他们压根就没有想到昨天购物的便利店，今天就被贴上了“内部调整不营业”的通告。
                            </p>
                            <p> <br> </p>
                            <p>
                                8月1日这一晚，让邻家便利店再也无法在城市的天光下苏醒了。
                            </p>
                            <p> <br> </p>
                            <p>
                                <strong>失联太难受，168家店一同陪葬</strong>
                            </p>
                            <p> <br> </p>
                            <p>
                                邻家死的糊涂。
                            </p>
                            <p> <br> </p>
                            <p>
                                从2015年成立以来，隶属于邻里家（北京）商贸有限公司的邻家便利店怎么也不会想到，有一天法人和股东会同时失联。
                            </p>
                            <p> <br> </p>
                            <p>
                                既无法申请破产清算，又无法处理与供应商之间的债务债权纠纷，邻家进退维谷。
                            </p>
                            <p> <br> </p>
                            <p>
                                想当初，首期注册资金1亿元，作为一家年轻且富有活力的企业，邻家不断引进新的经营模式，打造出了北京市场上较有代表新的新型便利店品牌。直到今年5月之前，就在北京就开出了160多家门店。
                            </p>
                            <p style="text-align:center;"> <img src="http://co-image.qichacha.com/upload/chacha/img/20180806/1533521074637433.png"> </p>
                            <p style="text-align:center;">
                                数据来源：顶企查
                            </p>
                            <p> <br> </p>
                            <p>
                                2017年12月，邻家才开始推出了加盟经营的模式——把门店委托给店长进行经营。
                            </p>
                            <p> <br> </p>
                            <p>
                                邻家在北京快速地布棋，没有人了解到，商品琳琅满目、顾客络绎不绝的便利店，其实早已体力不支。
                            </p>
                            <p> <br> </p>
                            <p>
                                “便利店行业的运营成本正在快速上升，主要原因是租金和人工，其中房租成本上升18%，水电成本上升6.9%，人工成本上升12%。”这是中国连锁经营协会发布的《2018中国便利店报告》中显示的数据。
                            </p>
                            <p> <br> </p>
                            <p>
                                快速地扩大店铺是需要代价的。充足的运营资金对于便利店品牌来说，便是命门。与输血的股东失联，让死亡的弹窗自动推送了出去。
                            </p>
                            <p> <br> </p>
                            <p>
                                <strong> 迟早都会暴的雷， 迟早自己该跳的坑</strong>
                            </p>
                            <p> <br> </p>
                            <p>
                                所以，为什么法人和股东失联？他们在躲什么？藏什么？
                            </p>
                            <p> <br> </p>
                            <p>
                                邻里家（北京）商贸有限公司属于投资方出资设立。法人和股东都是投资方。顶企查发现，其中公司法人兼股东韩磊持股41.25%，而公司股东贾卫平占股41.26%，锦云（深圳）股权投资有限公司则占股14.79%。
                            </p>
                            <p style="text-align:center;"> <img src="http://co-image.qichacha.com/upload/chacha/img/20180806/1533521110524318.png"> </p>
                            <p style="text-align:center;">
                                数据来源：顶企查
                            </p>
                            <p> <br> </p>
                            <p>
                                而工商资料显示中有所发现，邻里家监事是项建安。
                            </p>
                            <p> <br> </p>
                            <p>
                                项建安是谁不重要，重要的是他持股的公司——北京万卓智汇商贸有限公司，这家公司的股份项建安占股40%，而另外6成的掌舵者是周伯云。
                            </p>
                            <p style="text-align:center;"> <img src="http://co-image.qichacha.com/upload/chacha/img/20180806/1533521145854726.png"> </p>
                            <p style="text-align:center;">
                                数据来源：顶企查
                            </p>
                            <p> <br> </p>
                            <p>
                                混在P2P圈子里的人一定都听过周伯云。作为上海善林金融创始人，周伯云为自己画了一个地牢。
                            </p>
                            <p> <br> </p>
                            <p>
                                “鑫月盈”、“鑫季丰”、“鑫年丰”、“政信通”等债权转让理财产品以高利率成功集资了一波公众存款；线上的“善林财富”、“善林宝”、“幸福钱庄”、“广群金融”等理财平台，销售非法理财产品，形成超大资金池。
                            </p>
                            <p> <br> </p>
                            <p>
                                涉案金额600余亿元，终于在今年4月份，因涉嫌非法吸收公众存款，善林金融法定代表人周伯云等8人被批捕。
                            </p>
                            <p> <br> </p>
                            <p>
                                庞氏骗局就是善林金融的爆破点，此后整个导火索都受到了波及。而被周伯云投资的邻里家、被周伯云常常挂在嘴边的“兄弟公司”就成为了那个“同赴死亡”的亲密伙伴。
                            </p>
                            <p> <br> </p>
                            <p>
                                只是，这一次邻里家带着的是恨。
                            </p>
                            <p> <br> </p>
                            <p>
                                <strong> 常在河边站，总有湿鞋人</strong>
                            </p>
                            <p> <br> </p>
                            <p>
                                “邻里家背后唯一的出资方因故受到上海警方调查，导致公司基本账户与一般账户被银行冻结。公司仍处于发展阶段，尚未真正实现盈利，依旧需要投资方注资经营，靠店铺自身销售收入仍然入不敷出；且目前公司账户已被部分供应商诉讼至法院进行了财产保全，账户资金已被冻结，公司账户内已无可支配资金。”
                            </p>
                            <p> <br> </p>
                            <p>
                                邻里家的告知函细的很清楚——未盈利，靠吸血而活，可惜投进来的这钱不干净。
                            </p>
                            <p> <br> </p>
                            <p>
                                邻家是个别案例么？答案是否定的。
                            </p>
                            <p> <br> </p>
                            <p>
                                因为，P2P的资金庞大而不封顶，资金散落在各行各业，各种角落。比如极路由资金链彻底断裂；就比如尚品国旅欠薪裁员；成都亚堂小超董事长因P2P问题自首……只要非法集资的资金还没追回，就能听到各行各业被P2P炸出来的创企们的哀嚎声，资金链条上的每一个环节的颤抖声。
                            </p>
                            <p> <br> </p>
                            <p>
                                拿着P2P平台的钱进行融资的创业者们，当时为了获资着眼于眼前的利益，而事发后又不能为自己开脱，姿态难堪且尴尬。
                            </p>
                            <p> <br> </p>
                            <p>
                                如果邻家当年不受P2P牵连，或许就会有一个不一样的结局。即便是便利店也风起云涌，但也不会一夜之间倒闭。
                            </p>
                            <p> <br> </p>
                            <p>
                                常在河边站，总有湿鞋人。
                            </p>
                        </div> -->
                        <div class="news-content text-dark m-b-lg" v-html="json.content">
                        </div>    
                    </div>
                    <div class="container backs" @click="back2"> 返回</div>
                    <div class="shengming">免责声明：本文来自网络新闻，不代表顶企查的观点和立场。</div>
                </section>
            </div>
            <div class="col-sm-3">
                <section class="panel b-a">
                    <div class="panel-body">
                        <div style="text-align:center;margin-top:20px;">
                            <p><img src="../../assets/images/code_wx.png" style="max-width:60%;"></p>
                            <p>关注微信公众号<br> 查看更多公司头条信息
                            </p>
                        </div>
                    </div>
                </section>
               
                <section class="panel b-a list">
                    <div class="panel-heading b-b">
                        <h1 class="font-bold">您可能感兴趣的新闻</h1>
                    </div>
                    <ul class="list-group no-bg auto">
                        <template v-if="hotNews">
                             <li class="list-group-item clearfix" v-for="(item,idx) in hotNews" :key="idx"  @click="refresh(item.title)">
                                <span>{{item.title}}</span>
                            </li>
                        </template>    
                    </ul>
                </section>
            </div>
        </div>
    </section>
</template>
<script>
import Head from "@/components/Head";
import {GetNews} from '@/util/Http.js';
import Common from '@/util/common.js'
export default {
    components: {
        Head
    },
    data() {
        return {
            json:"",
            hotNews:""
        };
    },
    watch:{
        $route: "initPage",
    },
    created(){
        this.initPage()
        this.initHot();
        Common.remberHistory("news_history",this.$route.query.title,8);
    },
    methods: {
        initPage() {
            this.$axios({type:"get",url:"companyNews/newDetail",data:{title:this.$route.query.title},success:res=>{
                    if(res.data.code ==0){
                        this.json=res.data.data[0]
                    }
                    else{
                        this.$message.error('新闻加载失败，请稍后再试');
                    }
                }   
            },1)
        },
        initHot(){
             GetNews({type:'news_hot',pageIndex:1,pageSize:6},res=>{
                let arr=res.listData;
                if(arr.length>6){
                    arr.splice(6,arr.length)
                }
                this.hotNews=arr
            })
        },
        refresh(msg){
            this.$router.push({"path":'/NewsDetail',query:{title:msg}})
        },
        back2(){
            this.$router.push({path: "/NewsIndex",query: {'type':'recommend'}})
        }
    }
};
</script>
<style scoped>
.backs{
     padding: 10px 40px;
     font-size: 14px;
     text-align: left;
     background: url('../../assets/images/back_icon.png') 14px center no-repeat;
     cursor: pointer;
     background-size: 26px;
     transition: all .1s;
 }
 .backs:hover{
    color: #3685fc;
    background: url('../../assets/images/ic_reply.png') 14px center no-repeat;
    background-size: 26px;
 }
.container {
    width: 1250px;
}
.shengming{
    text-align: center;
    font-size: 12px;
    padding: 0px 48px 20px;
    color:#888;
}
.text-dark {
    color: #666;
}
p {
    margin: 0 0 10px;
}
.panel h1 {
    color: #333;
    font-size: 16px;
    margin-top: 6px;
    font-weight: normal;
}
.b-b {
    border-bottom: 1px solid #eaeef1;
}
.list-group-item.clearfix{
    color: #666666;
}
.panel .list-group-item {
    border-color: #f3f5f7;
}
.panel .list-group li{
    cursor: pointer;
}
.panel .list-group li:hover{background: #f1f9fe;color: #128bed;}
</style>