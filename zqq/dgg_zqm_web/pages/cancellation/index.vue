<template>
    <div class="pageCancell">
        <div class="banner">
          <ul>
            <li>
              <img src="../../assets/images/cancellation/img_zx_00.jpg" alt="公司注销（小规模）">
              <h3>公司注销（小规模）</h3>
              <p class="des">小规模公司注销，绿色通道，多年工商行业经验，注销轻松搞定。</p>
              <p class="price"><b>￥</b>3000<span>起</span></p>
              <a href="javascript:void(0);" onclick="DGGkefu.open();" class="btn">免费咨询详情</a>
            </li>
            <li>
              <img src="../../assets/images/cancellation/img_zx_01.jpg" alt="公司注销（一般纳税人）">
              <h3>公司注销（一般纳税人）</h3>
              <p class="des">丰富的办理经验，顶呱呱绿色通道办理，专业团队一站式服务。</p>
              <p class="price"><b>￥</b>6000<span>起</span></p>
              <a href="javascript:void(0);" onclick="DGGkefu.open();" class="btn">免费咨询详情</a>
            </li>
            <li>
              <img src="../../assets/images/cancellation/img_zx_02.jpg" alt="个体注销">
              <h3>个体注销</h3>
              <p class="des">专业办理渠道，顶呱呱注销全程实时跟进，时间比同行快20%。</p>
              <p class="price"><b>￥</b>1000<span>起</span></p>
              <a href="javascript:void(0);" onclick="DGGkefu.open();" class="btn">免费咨询详情</a>
            </li>
          </ul>
        </div>

        <div class="tab-catalog-00">
          <a class="atthis" href="#jj">服务简介</a>
          <a href="#zx">为什么注销</a>
          <a href="#cl">所需材料</a>
          <a href="#lc">注销流程</a>
        </div>

        <div id="jj" class="wrapper">
          <h1><img src="../../assets/images/cancellation/01.png" alt="01">公司注销是怎么回事？</h1>
          <p>根据《中华人民共和国公司法》规定，注册公司时需要依法向工商行政管理机关申请设立登记，完成整个注册流程。<br>  
公司注册登记项目包括：公司名称、法定代表人姓名、注册资本、地址、公司类型、经营范围、营业期限等。<br>
工商变更指的是一切信息登记完毕，未经变更许可，不得擅自更改原本的信息，经营期间有信息变动的可向公司登记机关申请变更登记</p>
          <h3>公司符合以下条件之一的，可申请注销</h3>

          <ul>
            <li><a href="javascript:void(0);" onclick="DGGkefu.open();">公司被依法宣告破产</a></li>
            <li><a href="javascript:void(0);" onclick="DGGkefu.open();">公司章程规定营业期间限届满<br>或者其他解散事由出现</a></li>
            <li><a href="javascript:void(0);" onclick="DGGkefu.open();">公司因合并、分立解散</a></li>
            <li><a href="javascript:void(0);" onclick="DGGkefu.open();">公司被依法责令关闭</a></li>
          </ul>
        </div>

        <div id="zx">
          <img src="../../assets/images/cancellation/img_why.jpg" alt="为什么注销?找专业的工商代办服务，方便快捷
如果企业成为僵尸企业未及时注销的话，对法人有很大的影响">
        </div>
        <div id="cl" class="tc">
          <img src="../../assets/images/cancellation/img_cl.jpg" alt="所需材料按照法律规定，公司必须严格按照规定时间进行报税，相关规定如下：
企业应当自月份或者季度终了日起十五日内，向税务机关保送预缴企业所得税申报表，预缴税款
企业应当自年终了日起五个月内，向税务机关报送年度企业所得税纳税申报表，并汇算清缴，结清应缴应退税款">
        </div>
        <div id="lc" class="tc">
          <img src="../../assets/images/cancellation/img_flow.png" alt="注销流程">
        </div>

        
    </div>
</template>

<script>
export default {
  data () {
    return {
    }
  },
  components: {
  },
  mounted () {

  },
  methods: {
    
  }
}
</script>
 
<style lang="stylus" scoped>
@import '../../assets/css/params.styl'
.tc
  text-align center
  padding-top 55px
.pageCancell
  background #fff
  overflow hidden
  padding-bottom 50px
  .banner
    height 520px
    background transparent url('../../assets/images/cancellation/bg_banner.jpg') center top no-repeat
    ul
      width 1020px
      margin 0 auto
      padding-top 60px
    li
      float left
      width 320px
      margin 0 10px
      text-align center
      padding 20px
      height 400px
      box-sizing border-box
      background #fff
      box-shadow 0 0 15px 5px rgba(57, 166, 246, .3)
      img
        width 100%
      h3
        font-size 16px
        line-height 40px
        padding-top 10px
      .des
        font-size 14px
        line-height 24px
        color #666
      .price
        font-size 24px
        color #2bc1f2
        line-height 60px
        b
          font-size 12px
        span
          font-size 12px
.tab-catalog-00
  text-align center
  padding-top 40px
  a
    display inline-block
    width 120px
    text-align center
    line-height 45px
    font-size 16px
    height 55px
    &.atthis
      font-weight bold
      background transparent url('../../assets/images/cancellation/bg_arr.png') left top no-repeat   
      color #fff 
#jj
  text-align center
  padding-top 30px
  h1
    font-size 30px
    line-height 30px
    margin-bottom 25px
    img
      margin-right 5px
  p
    font-size 16px
    line-height 30px
  h3
    font-size 24px
    line-height 80px
    margin-top 15px
  ul
    width 760px
    margin 0 auto
    padding-bottom 45px
    overflow hidden
    li
      float left
      width 350px
      height 100px
      line-height 100px
      background #eee
      border-radius 100px
      margin 15px
      &:hover
        background #1c8bee
        a
          color #fff
      a
        display inline-block
        vertical-align middle
        line-height 26px
        text-align center
        font-size 16px
        font-weight bold
</style>
