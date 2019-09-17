<template>
    <div class="pageCreateName">
      <div class="banner">
        <img src="../../assets/images/testName/banner.jpg" alt="公司注册">
        <div class="title">
            <h1>公司名字测试打分  首选智企名</h1>
            <p>免费为您提供精准测名评分，全面、专业、方便、好用</p>
        </div>
      </div>
      <div class="search" ref='list'>
        <div class="name">公司测名</div>
        <dl>
          <dt><label for="">公司名/品牌名</label><input  @keyup="replaceCn" type="text" minlength="2" v-model="name" placeholder="请输入名称" maxlength="10"></dt>
          <dd><label for="">所在领域</label><select name="" id="" v-model="industry">
            <option value="">请选择公司领域</option>
            <option :value="index" v-for="(item, index) in industryList" :key="item">{{index}}</option>
          </select></dd>
        </dl>
        <p class="ts">提示：只需输入公司、产品名字的核心部分。如：成都某某科技有限公司.只需输入‘’某某‘’</p>
        <a href="javascript:void(0);" class="btn-test" @click="search">开始测试</a>
      </div>
      <div class="tips" v-show="!(resultInfo || resultZhouYi)">
        <div class="title">公司起名注意事项</div>
         <ul>
          <li>
            <div class="name">不能重名</div>
            <p>相同或相似行业存在重名公司，不能注册。如，“直招网络科技”与“宜招信息科技”算重名。</p> 
          </li>
          <li class="item01">
            <div class="name">不能触犯驰名商标</div>
            <p>如：“万达”是驰名商标，不能注册为公司名字</p> 
          </li>

          <li  class="item02">
            <div class="name">不能与知名公司名字混淆</div>
            <p>如“新微软”“微软之家”等不能注册为公司名字</p> 
          </li>

          <li  class="item03">
            <div class="name">尽量不用地区名称及简称</div>
            <p>如：上海，兰州，泸等不能注册为公司名字。</p> 
          </li>
          <li  class="item04">
            <div class="name">不能使用繁体、数字、英文</div>
            <p>公司名称只能使用简体中文。</p> 
          </li>

          <li  class="item05">
            <div class="name">不能使用行业通用词汇</div>
            <p>如：“上海电脑科技有限公司”不能注册为公司名字。</p> 
          </li>

          <li  class="item06">
            <div class="name">不能使用名人字号</div>
            <p>如：“马云”，“王健林”等不能注册为公司名字。</p> 
          </li>

          <li  class="item07">
            <div class="name">不能带有宗教色彩</div>
            <p>如：“伊斯兰”等不能注册为公司名字。</p> 
          </li>
        </ul>
      </div>
      <section class="isShowResult" v-if="resultInfo || resultZhouYi">
        
        <section class="listBox" >
            <h2>查询结果</h2>
            <ul v-if=" isLogin">
              <li>
                <div class="title">字义详解</div>
                <ol>
                  <li v-if="resultInfo">
                    <table>
                      <tbody v-html="resultInfo.ziyi">
                      </tbody>
                    </table>
                  </li>
                </ol>
              </li>

              <li>
                <div class="title">名字总评</div>
                <ol v-if="resultInfo">
                  <li>
                  <table>
                    <tbody v-html="resultInfo.zongping">
                    </tbody>
                  </table>
                  </li>
                </ol>
              </li>

              <li v-if="resultZhouYi">
                <div class="title">周易卦象分析</div>
                <p class="text" v-html="resultZhouYi.summ"></p>
              </li>
            </ul>
            <div v-else style="position:relative">
              <img src="../../assets/images/testName/mo.png" alt="测名" width="100%">
              <loginBox></loginBox>
            </div>
        </section>
      </section>
      <toolBox/>
    </div>
</template>

<script>
import toolBox from '@/components/toolBanner'
import loginBox from '@/components/loginBox'
export default {
  data () {
    return {
      show:true,

      name: '',
      resultInfo: '',
      resultZhouYi: '',

      industry: '',
      industryList: {
          '不限行业': '0',
          '电子信息': '1',
          '化工': '2',
          '汽车': '3',
          '机械工业': '4',
          '仪器仪表': '5',
          '发电与输变电设备': '6',
          '生物技术': '7',
          '制药': '8',
          '新材料': '9',
          '光电子企业': '10',
          '纳米技术': '11',
          '新能源': '12',
          '环保': '13',
          '食品和农产品精加工': '14',
          '印刷与包装': '15',
          '钻石珠宝': '16',
          '化妆品及洗涤用品': '17',
          '服装服饰': '18',
          '纺织': '19',
          '轻工': '20',
          '商业零售业': '21',
          '物流运输业': '22',
          '会展业': '23',
          '金融业': '24',
          '餐饮业': '25',
          '旅游业': '26',
          '房地产业': '27',
          '广告业': '28',
          '信息咨询服务业': '29'
      }
    }
  },
  components: {
    toolBox,loginBox
  },
  computed:{
    isLogin(){
      return this.$store.state.userInfo ? true : false
    }
  },
  watch:{
    isLogin(bool){
      if(bool && this.name && this.industry) this.search()
    }
  },
  mounted () {
    var _hmt = _hmt || [];
    (function() {
      var hm = document.createElement("script");
      hm.src = "https://hm.baidu.com/hm.js?8f2b97618282ed37ef6c4bcd1211130a";
      var s = document.getElementsByTagName("script")[0]; 
      s.parentNode.insertBefore(hm, s);
    })();
  },
  methods: {
    replaceCn (e) {
      this.name = e.target.value.replace(/[^\u4e00-\u9fa5]/g, '')
    },
    search () {
      // if (!this.$store.state.userInfo.userId) {
      //   this.$loginPop(this)
      //   return false
      // }
      if (!this.name) {
        this.$popup.created({
            content: '请输入名称',
            time: 2000
        })
        return false
      }

      if (!/^[\u4e00-\u9fa5]+$/.test(this.name)) {
        this.$popup.created({
            content: '公司名称只能使用简体中文',
            time: 2000
        })
        return false
      }
      if (this.name.length<2) {
        this.$popup.created({
            content: '名称至少两个字',
            time: 2000
        })
        return false
      }
      if (!this.industry) {
        this.$popup.created({
            content: '请选择行业',
            time: 2000
        })
        return false
      }
      
      this.$axios.$post(encodeURI('/getCompanyName/queryInfo?name='+this.name+'&industry='+this.industryList[this.industry]))
      .then((res) => {
        this.resultInfo = res.data.data
      })
      this.$axios.$post(encodeURI('/getCompanyName/queryZhouYi?name='+this.name))
      .then((res) => {
        this.resultZhouYi = res.data.data
        this.$Jump(this.$refs.list)
        if (!this.$store.state.userInfo.userId) {
          this.$loginPop(this)
        }
      })
    }
  }
}
</script>
 
<style lang="stylus" scoped>
@import '../../assets/css/params.styl'
   *{
       box-sizing border-box;
   }   
.pageCreateName
  overflow hidden
  min-width 1200px
  width 100%
  background #fff
.banner
  width 1920px
  position relative
  left 50%
  margin-left -960px
  height 500px
  img
    height 100%
  .title
    width 1035px
    position absolute
    left 50%
    margin-left -517.5px
    top 145px
    text-align center
    color #fff
    h1
      font-size 48px
      margin-bottom 30px
    p
    font-size 24px
.search
  width 1200px
  margin 0 auto
  background #fff
  height 320px
  box-sizing border-box
  position relative
  z-index 9
  box-shadow 0 0 5px 1px rgba(238, 238, 238, .75)
  margin-top  -100px
  .name
    font-size 30px
    line-height 30px
    text-align center
    font-weight bold
    padding 30px 0
  dl
    width 710px
    margin 0 auto
    overflow hidden
    line-height 40px
    dt
      float left
      width 350px
      input
        width 200px
        height 40px
        border 1px solid $solidcolor
        text-indent 10px
        margin-left 10px
    dd
      float left
      width 350px
      select
        width 240px
        height 40px
        border 1px solid $solidcolor
        text-indent 10px
        margin-left 10px
  .ts
    font-size 14px
    color #60a4e0
    text-align center
    line-height 50px
    margin-top 10px
  .btn-test
    display block
    width 200px
    height 60px
    margin 30px auto 0
    line-height 60px
    border-radius 3px
    text-align center
    background #f89d44
    font-size 20px
    color #fff
    font-weight bold
    box-shadow 0 0 15px 5px rgba(248, 157, 68, .2)
    
.listBox
  padding-top 60px;
  padding-bottom 60px;
  width 900px;
  margin auto;
  background #fff;
  h2
    font-size 24px
    text-align center
    padding-bottom 50px
  .title
    background #6782e8
    height 50px
    line-height 50px
    padding 0 36px
    font-size 18px
    color #fff
    border-radius 60px
    box-shadow 0 0 15px 5px rgba(103, 130, 232, .2)
    margin 20px 0
  li
    line-height 30px
    font-size 14px
    span
      color #6782e8
      font-weight bold
  ol
    padding-left 20px
  .text
    padding-left 20px  
      
.tips
  width 960px
  margin 0 auto
  overflow hidden
  .title
    font-size 30px
    font-weight normal
    line-height 120px
    text-align center
  li
    float left
    width 460px   
    height 140px
    box-sizing border-box
    padding 30px
    background #fff
    border 1px solid #eee
    margin 10px
    position relative
    .name
      width 280px
      height 50px
      border-radius 50px
      position absolute
      left 50%
      margin-left -140px
      top - 25px
      background #00a9ef
      color #fff
      line-height 50px
      text-align center
    p
      margin-top 20px
    &.item01
      .name
        background #f6b720
    &.item02
      .name
        background #f38a51
    &.item03
      .name
        background #31b998
    &.item04
      .name
        background #34b5e5
    &.item05
      .name
        background #f69b26
    &.item06
      .name
        background #35ba99
    &.item07
      .name
        background #8c9af2
    
</style>
