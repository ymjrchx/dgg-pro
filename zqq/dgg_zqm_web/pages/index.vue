<template>
    <div class="pageIndex">
      <div class="banner">
          <!-- <p class="sign">树立企业可信形象，更多信任，更多交易</p> -->
          <a href="/createName" class="btn-banner btn-qm">立即取名</a>
          <a href="/company"  class="btn-banner btn-hm">立即核名</a>
      </div>
         
      <div class="company">
          <div class="title-catalog-01">公司所需材料下载</div>
          <div class="wrapper">
            <div class="left">
              <div class="title">
                <a href="/down">更多下载</a>
                <h3>注册公司</h3>
                <p>工商必备材料</p>
              </div>
              <ul v-if="templateTypes[0]">
                <li v-for="item in templateTypes[0].list" :key="item.id"><a :href="item.fileUrl" @click="down(item)"><span>{{item.download}}次下载</span>{{item.fileName}}</a></li>
              </ul>
            </div>

            <div class="right">
              <div class="title">
                <a href="/down">更多下载</a>
                <h3>经营公司</h3>
                <p>必备合同与协议</p>
              </div>
              <ul v-if="templateTypes[1]">
                <li v-for="item in templateTypes[1].list" :key="item.id" ><a :href="item.fileUrl" @click="down(item)"><span>{{item.download}}次下载</span>{{item.fileName}}</a></li>
              </ul>
            </div>
          </div>
      </div>

      <toolBox />

      <div class="hm">
        <div class="title-catalog-01">智企名 专注公司核名的网站</div>
        <p class="into">已服务客户10W+</p>
        <ul class="wrapper">
          <li>
            <h3>不能重名</h3>
            <p>相同或相似行业存在重名公司，不能注册。如：“顶呱呱网络科技”与“顶呱呱信息科技”算重名</p>
            <a href="/company">立即核名</a>
          </li>

          <li>
            <h3>不能触犯驰名商标</h3>
            <p>如：“万达”是驰名商标，不能注册为公司名字</p>
            <a href="/company">立即核名</a>
          </li>

          <li>
            <h3>不能与知名公司名字混淆</h3>
            <p>如“新微软”“微软之家”等不能注册为公司名字</p>
            <a href="/company">立即核名</a>
          </li>

           <li>
            <h3>尽量不用地区名称及简称</h3>
            <p>如：上海，兰州，泸等不能注册为公司名字。</p>
            <a href="/company">立即核名</a>
          </li>

          <li>
            <h3>不能使用繁体、数字、英文</h3>
            <p>公司名称只能使用简体中文。</p>
            <a href="/company">立即核名</a>
          </li>

          <li>
            <h3>不能使用行业通用词汇</h3>
            <p>如：“上海电脑科技有限公司”不能注册为公司名字。</p>
            <a href="/company">立即核名</a>
          </li>
        </ul>
      </div>

      <div class="qm">
        <div class="title-catalog-01">基于企业大数据的公司起名</div>
        <p class="into">智企名采用企业大数据分析，从企业信息和驰名/著名商标中分析出公司名称的通过率</p>

        <ul class="wrapper">
          <li><a href="javascript:void(0);">
            <span class="icon"><img src="../assets/images/index/icon_ht.png" alt="好听易记"></span>
            <h4>好听易记</h4>
            <p>基于大数据注册企业名字、唐诗宋词词库<br>用字分析起名 让公司名字好听有内涵</p>
          </a></li>

          <li><a href="javascript:void(0);">
            <span class="icon"><img src="../assets/images/index/icon_thl.png" alt="好听易记"></span>
            <h4>注册通过率高</h4>
            <p>注册企业名字分析起名，机器智能算法<br>实时名称核准查重 提升通过率</p>
          </a></li>

          <li><a href="javascript:void(0);">
            <span class="icon"><img src="../assets/images/index/icon_jxky.png" alt="好听易记"></span>
            <h4>吉祥开运</h4>
            <p>易经词库、命理分析智能公司起名，<br>助运事业腾飞、吉祥如意红红火火</p>
          </a></li>

          <li><a href="javascript:void(0);">
            <span class="icon"><img src="../assets/images/index/icon_jsqm.png" alt="好听易记"></span>
            <h4>极速起名</h4>
            <p>基于大数据智能起名系统，输入地域、<br>行业类型一键起名,起出您满意的名字</p>
          </a></li>
        </ul>
      </div>

     
    </div>
</template>

<script>
import toolBox from "@/components/toolBanner"
import popup from "~/components/popup"; 
export default {
  asyncData (context) {
    return Promise.all([context.$axios.$get('/template/findByTemplateType?templateTypes=template_type_15,template_type_16')])
    .then((res) => {
      return { 
        templateTypes: res[0].data,
      }
    })
  },
  data () {
    return {
    }
  },
  components: {
    toolBox
  },
  mounted() {
    var _hmt = _hmt || [];
    (function() {
      var hm = document.createElement("script");
      hm.src = "https://hm.baidu.com/hm.js?054cc93c10c5456fd714cdf8f8e8e4d5";
      var s = document.getElementsByTagName("script")[0]; 
      s.parentNode.insertBefore(hm, s);
    })();
  },
  methods: {
    down(item) {
      this.$axios.$get(`/template/addDownload?id=${item.id}`).then((res) => {
        item.download = res.data
      })
    }
  }
}
</script>
 
<style lang="stylus" scoped>
@import '../assets/css/params.styl'
.banner
  background #2aaee3 url('../assets/images/index/bg_index_banner.jpg') no-repeat center top
  height 680px
  font-size 0
  position relative
  .sign
    position absolute
    left 0
    right 0
    top 200px
    font-size 18px
    color #fff
    line-height 32px
    text-align center
  .btn-banner
    position absolute
    left 50%
    top 482px
    margin-left -336px
    background #f89d44
    width 220px
    height 54px
    line-height 54px
    border-radius 54px
    background #f89d44
    color #fff
    font-size 20px
    font-weight bold
    text-align center
    transition all .5s
    box-shadow 0 0 15px 5px rgba(21, 118, 221, 1)
    &:hover
      background #fe5629
      margin-top -5px
  .btn-hm
    right 50%
    left auto
    marign 0
    margin-right -336px
      
.company
  background #fff;
  padding: 75px 0  0
  overflow hidden
  .left,.right
    width 50%
    padding 45px 50px 0 20px
    box-sizing border-box
  .title
    a
      float right
      color $maincolor
      line-height 40px
    h3
      color $color
      font-size 24px
      line-height 24px
      font-weight normal
    p
      color #999
      font-size 14px
      line-height 14px
      margin-top 5px
  ul
    overflow hidden
    padding-top 10px
    li
      a
        display block
        line-height 68px
        border-bottom 1px solid $solidcolor
        overflow hidden
        white-space nowrap
        text-overflow ellipsis
        background transparent url('../assets/images/index/icon_word.png') no-repeat left center
        padding-left 34px
        span
          float right
          margin-left 10px
          border 1px solid $maincolor
          border-radius 5px
          color $maincolor
          line-height 22px
          padding 0 7px
          margin-top 24px

.into
  font-size 16px
  color #999
  text-align center
  padding-top 22px
.hm
  overflow hidden
  margin-top 50px
  clear both
  padding-top 50px
  padding-bottom 60px
  ul
    overflow hidden
    padding-top 30px
    box-sizing border-box
    padding-left 20px
    li
      float left
      width 380px
      height 240px
      background #fff
      margin 10px 5px
      text-align center
      position relative
      box-shadow 0 0 15px 5px rgba(234, 234, 234, .75)
      h3
        background #1c8bee
        height 60px
        line-height 60px
        font-size 20px
        font-weight normal
        color #fff
      p
        font-size 14px
        line-height 24px
        padding 27px
        text-align left
      a
        display block
        width 140px
        height 38px
        border-radius 3px
        background #2bc1f2
        color #fff
        text-align center
        line-height 38px
        position absolute
        left 50%
        margin-left -70px
        bottom 30px
        transition all .5s
        &:hover
          bottom 35px

.qm
  background #fff
  padding 60px 0
  ul
    overflow hidden
    padding-top 30px
    li
      float left
      width 25%
      text-align center
      .icon
        display block
        width 160px
        height 160px
        line-height 160px
        background #1c8bee
        border-radius 50%
        margin 0 auto
        position relative
        &:after
          content ''
          width 180px
          height 180px
          border 1px dashed #1c8bee
          left -10px
          top -10px
          position absolute
          border-radius 50%
        img
          position relative
          top 5px
          left 5px
      h4
        margin 26px 0 10px
        font-size 18px
        font-weight normal
      p
        font-size 14px
        line-height 24px
        color #666
      a:hover
        .icon
          background #2bc1f2
      
</style>
