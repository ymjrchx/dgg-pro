<template>
    <div class="pageIndex">
      <div class="banner">
          <h1 class="title">想创业  就上智企名</h1>
          <p class="sign">树立企业可信形象，更多信任，更多交易</p>
          <ul class="wrapper">
            <li>
              <router-link to="/createName">
                <div class="icon">
                  <img src="../assets/images/index/icon_sb.png" alt="公司起名">
                </div>
                <h2>公司起名</h2>
                <p>人工智能的免费公司起名系统</p>
              </router-link>
            </li>
            <li>
              <router-link to="/company">
                <div class="icon">
                  <img src="../assets/images/index/icon_hd.png" alt="公司核名">
                </div>
                <h2>公司核名</h2>
                <p>核名预查询  提高通过率</p>
              </router-link>
            </li>

            <li>
              <router-link to="/testName">
                <div class="icon">
                  <img src="../assets/images/index/icon_jc.png" alt="公司测名">
                </div>
                <h2>公司测名</h2>
                <p>人工智能的免费公司起名系统</p>
              </router-link>
            </li>

            <li>
              <router-link to="/generator">
                <div class="icon">
                  <img src="../assets/images/index/icon_dn.png" alt="经营范围生成器">
                </div>
                <h2>经营范围生成器</h2>
                <p>人工智能的免费公司起名系统</p>
              </router-link>
            </li>

            <li>
              <router-link to="/brandSearch">
                <div class="icon">
                  <img src="../assets/images/index/icon_r.png" alt="商标查询">
                </div>
                <h2>商标查询</h2>
                <p>多种套餐任你挑</p>
              </router-link>
            </li>

            <li>
              <router-link to="/patentSearch">
                <div class="icon">
                  <img src="../assets/images/index/icon_zl.png" alt="专利查询">
                </div>
                <h2>专利查询</h2>
                <p>500万+ 专利</p>
              </router-link>
            </li>

            <li>
              <router-link to="/taxSearch">
                <div class="icon">
                  <img src="../assets/images/index/icon_sw.png" alt="税号查询">
                </div>
                <h2>税号查询</h2>
                <p>两步搞定新规</p>
              </router-link>
            </li>
            <li>
              <router-link to="/domain">
                <div class="icon">
                  <img src="../assets/images/index/icon_ym.png" alt="域名查询">
                </div>
                <h2>域名查询</h2>
                <p>高精度IP库，精准，稳定</p>
              </router-link>
            </li>
          </ul>
      </div>
      <div class="searchCont">
      <Search />
      </div>
          <div class="open wrapper">
            <div class="title-catalog-00">
              <h2>开公司，如何少交税？</h2>
              <p>已服务客户10W+，综合税率低至2.76%</p>
            </div>
            <ul style="overflow:hidden">
              <li><a href="javascript:void(0);" onclick="DGGkefu.open();">
                <span class="tu">
                  <img src="../assets/images/index/img_gs_00.jpg" alt="">
                </span>
                <h3>工资收入型</h3>
                <p>（个人所得税）筹划方案</p>
                <button>获取方案</button>
              </a></li>

              <li><a href="javascript:void(0);" onclick="DGGkefu.open();">
                <span class="tu">
                  <img src="../assets/images/index/img_gs_01.jpg" alt="">
                </span>
                <h3>劳务收入型</h3>
                <p>（个人所得税）筹划方案</p>
                <button>获取方案</button>
              </a></li>

              <li><a href="javascript:void(0);" onclick="DGGkefu.open();">
                <span class="tu">
                  <img src="../assets/images/index/img_gs_02.jpg" alt="">
                </span>
                <h3>投资收益型</h3>
                <p>（分红个税）筹划方案</p>
                <button>获取方案</button>
              </a></li>

              <li><a href="javascript:void(0);" onclick="DGGkefu.open();">
                <span class="tu">
                  <img src="../assets/images/index/img_gs_03.jpg" alt="">
                </span>
                <h3>企业利润型</h3>
                <p>（企业所得税）筹划方案</p>
                <button>获取方案</button>
              </a></li>

              <li><a href="javascript:void(0);" onclick="DGGkefu.open();">
                <span class="tu">
                  <img src="../assets/images/index/img_gs_04.jpg" alt="">
                </span>
                <h3>贸易采购型</h3>
                <p>（企业所得税）筹划方案</p>
                <button>获取方案</button>
              </a></li>

              <li><a href="javascript:void(0);" onclick="DGGkefu.open();">
                <span class="tu">
                  <img src="../assets/images/index/img_gs_05.jpg" alt="">
                </span>
                <h3>IT行业型</h3>
                <p>（增值税）筹划方案</p>
                <button>获取方案</button>
              </a></li>
            </ul>
    
      </div>
      <div class="company">
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

      <div class="dz">
        <div class="wrapper">
          <div class="left">
            <h3>定制专属您的财税解决方案</h3>
            <p>该交的税一分不少交，不该交的一分不多交</p>
            <a href="javascript:void(0);" onclick="DGGkefu.open();">立即咨询</a>
          </div>
          <ul class="right">
              <li><a href="javascript:void(0)">
                <img src="../assets/images/index/icon_yl.png" alt="医疗行业">
                <img class="at" src="../assets/images/index/icon_yl_at.png" alt="医疗行业">
                <p>医疗行业</p></a></li>
              <li><a href="javascript:void(0)">
                <img src="../assets/images/index/icon_ht.png" alt="主播与网红">
                <img class="at" src="../assets/images/index/icon_ht_at.png" alt="主播与网红">
                <p>主播与网红</p></a></li>
              <li><a href="javascript:void(0)">
                <img src="../assets/images/index/icon_sp.png" alt="明星与影视">
                <img class="at" src="../assets/images/index/icon_sp_at.png" alt="明星与影视">
                <p>明星与影视</p></a></li>
              <li><a href="javascript:void(0)">
                <img src="../assets/images/index/icon_dp.png" alt="网店">
                <img class="at" src="../assets/images/index/icon_dp_at.png" alt="网店">
                <p>网店</p></a></li>
              <li><a href="javascript:void(0)">
                <img src="../assets/images/index/icon_zx.png" alt="直销行业">
                <img class="at" src="../assets/images/index/icon_zx_at.png" alt="直销行业">
                <p>直销行业</p></a></li>
              <li><a href="javascript:void(0)">
                <img src="../assets/images/index/icon_jr.png" alt="金融行业">
                <img class="at" src="../assets/images/index/icon_jr_at.png" alt="金融行业">
                <p>金融行业</p></a></li>
              <li><a href="javascript:void(0)">
                <img src="../assets/images/index/icon_bx.png" alt="保险行业">
                <img class="at" src="../assets/images/index/icon_bx_at.png" alt="保险行业">
                <p>保险行业</p></a></li>
              <li><a href="javascript:void(0)">
                <img src="../assets/images/index/icon_gs.png" alt="股权投资">
                <img class="at" src="../assets/images/index/icon_gs_at.png" alt="股权投资">
                <p>股权投资</p></a></li>
          </ul>
        </div>
      </div>

      <div class="zx">
        <div class="wrapper">
          <div class="title-catalog-00">
            <h2>智企名创业服务 在线咨询</h2>
            <p>在线服务，实时保障</p>
          </div>

          <ul>
            <li><img src="../assets/images/index/img_sw.jpg" alt="税务筹划"><h5>税务筹划</h5>
  <p>定制方案</p><a href="javascript:void(0);" onclick="DGGkefu.open();">立即咨询</a></li>
            <li><img src="../assets/images/index/img_zx.jpg" alt="公司注销"><h5>公司注销</h5>
  <p>定制方案</p><a href="javascript:void(0);" onclick="DGGkefu.open();">立即咨询</a></li>
            <li><img src="../assets/images/index/img_cy.jpg" alt="创业政策"><h5>创业政策</h5>
  <p>定制方案</p><a href="javascript:void(0);" onclick="DGGkefu.open();">立即咨询</a></li>
            <li><img src="../assets/images/index/img_jz.jpg" alt="记账报税"><h5>记账报税</h5>
  <p>定制方案</p><a href="javascript:void(0);" onclick="DGGkefu.open();">立即咨询</a></li>
          </ul>
        </div>
      </div>

      <div class="news">
              <div class="title-catalog-00">
                <h2>智企名资讯</h2>
                <p>最新行业资讯，不再错过</p>
              </div>
              <div class="wrapper">
                <ul class="left">
                  <li v-for="item in news_tech" :key="item.id"><a :href="'/'+item.title">
                    <img :src="item.titleImg" alt="">
                    <h5>{{item.title}}</h5>
                    <p>{{item.part}}</p>
                  </a></li>
                </ul>

                <ul class="right">
                  <li v-for="item in news_finance" :key="item.id"><a :href="'/'+item.title">
                    <img :src="item.titleImg" alt="">
                    <h5>{{item.title}}</h5>
                    <p>{{item.part}}</p>
                  </a></li>
                </ul>
              </div>
       </div>

       <div class="swch">
         <div class="swch-content" style="width: 296px">
           <!-- <img src="../assets/images/img_ewm.jpg" alt="二维码"> -->
           <h5>税务筹划&nbsp;&nbsp;政策咨询</h5>
           <a class="tel" href="javascript:void(0)">400-0055-002</a>
           <a href="javascript:void(0);" onclick="DGGkefu.open();">在线咨询</a>
         </div>
       </div>
    </div>
</template>

<script>
import popup from "~/components/popup"; 
import Search from "@/components/search";
export default {
  asyncData (context) {
    return Promise.all([context.$axios.$get('/template/findByTemplateType?templateTypes=template_type_15,template_type_16'),
      context.$axios.$post('/companyNews/news',{ type: "news_tech", pageIndex: 1, pageSize: 3 }),
      context.$axios.$post('/companyNews/news',{ type: "news_finance", pageIndex: 1, pageSize: 3 })])
    .then((res) => {
      return { 
        templateTypes: res[0].data,
        news_tech: res[1].data.listData,
        news_finance: res[2].data.listData
      }
    })
  },
  data () {
    return {
    }
  },
  components: {
    Search
  },
  mounted() {
    this.sty='margin:auto'
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
  background #2aaee3 url('../assets/images/index/bg_banner.jpg') no-repeat center top
  height 680px
  .title
    font-size 48px
    color #fff
    font-weight normal
    text-align center
    padding-top 75px
  .sign
    font-size 14px
    color #fff
    line-height 32px
    text-align center
    margin-top 10px
    background transparent url('../assets/images/index/bg_sign.png')  no-repeat center top
  ul
    padding 10px 80px 0
    box-sizing border-box
    overflow hidden
    li
      float left
      width 25%
      text-align center
      margin-top 20px
      height 167px
      a
        display block
        color #fff
        position relative
        padding-bottom 20px
        .icon
          height 100px
          line-height 100px
        &:after
          left 50%
          top 0;
          margin-left -50px
          position absolute
          border-radius 50%
          z-index 1
          content ''
          width 100px
          height 100px
          background #fff
          transition all .5s
          transform-origin 0 0
          box-shadow 0 0 50px 0 rgba(23,150,223,.3)
        &:hover
          color $color
        &:hover:after
            margin-left -120px
            width 240px
            height 100%
            border-radius 4px
        >*
          position relative
          z-index 2
        h2
          font-size 16px
          margin-top 12px
        p
          font-size 14px
.searchCont
  position relative
  height 207px
  >*
    top 0
.open
  clear both
  overflow hidden
  ul
    li
      float left
      width 385px
      height 350px
      background #fff
      margin 0 7px 15px 8px
      text-align center
      box-shadow 0 0 10px 0 $shadowcolor
      a
        h3
          font-size 18px
          color $maincolor
          margin-top 26px
          line-height 18px
        p
          font-size 14px
          color #666
          line-height 40px
        button
          width 140px
          height 38px
          line-height 38px
          background $maincolor
          color #fff
          border-radius 3px
          cursor pointer
        span
          display block
          height 200px
          width 385px
          overflow hidden
          img
            transition all .5s
        &:hover
          img
            transform scale(1.1)
            transform-origin 50% 50%
.company
  margin-top 40px
  background #fff;
  padding: 55px 0 
  overflow hidden
  .left,.right
    width 50%
    padding 0 50px 0 20px
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
.dz
  clear both
  overflow hidden
  height 500px
  background #1682e2 url('../assets/images/index/bg_dz.jpg') center top no-repeat
  .left
    padding-left 57px
    width 400px
    padding-top 123px
    h3
      font-size 48px
      color #fff
      font-weight normal
      line-height 60px
    p
      font-size 15px
      color #fff
      line-height 55px
    a
      display block
      width 220px
      height 50px
      line-height 50px
      color #fff
      text-align center
      background #2bc1f2
      border-radius 3px
      margin-top 15px
      box-shadow 0 0 30px 0 rgba(28, 139, 238, .2)
  .right
    overflow hidden
    padding-top 120px
    width 600px
    li
      float left
      width 148px
      height 125px
      margin-bottom 15px
      text-align center
      a
        display block
        position relative
        line-height 90px
        &:after
          position absolute
          left 50%
          margin-left -45px
          top 0
          content ''
          z-index 1
          width 90px
          height 90px
          border-radius 50%
          border 1px solid #fff
        &:hover
          img
            display none
            &.at
              display inline
        &:hover:after
          background #fff
        img
          position relative
          z-index 2
          &.at
            display none
        p
          line-height 35px
          color #fff
.zx
  overflow hidden
  padding 0 8px
  background #fff
  padding-bottom 60px
  li
    float left
    width 280px
    height 280px
    margin 0 8px  
    position relative
    cursor pointer
    &:after
      content ''
      position absolute
      left 0
      top 0
      bottom 0
      right 0
      background #2b4c69
      opacity .7
      transition all .5s
    &:hover:after
      opacity .9
    h5
      position absolute
      left 0
      top 88px
      right 0 
      text-align center
      font-size 28px
      color #fff
      font-weight normal
      z-index 2
    p
      position absolute
      left 0
      top 135px
      right 0 
      text-align center
      font-size 16px
      color #fff 
      z-index 2
    a
      position absolute
      top 210px
      display block
      width 180px
      height 50px
      line-height 50px
      background #2bc1f2
      color #fff
      text-align center
      left 50%
      margin-left -90px
      border-radius 2px 
      z-index 2
.news
  padding-bottom 60px
  .wrapper
    box-shadow 0 0 20px 0 rgba(244, 244, 244, .75)
    padding 25px 0
    overflow hidden
    background #fff
  ul
    width 50%
    box-sizing border-box
    padding 0 25px 0
    &.left 
      border-right 1px solid $solidcolor
    li
      overflow hidden
      margin-bottom 15px
      &:last-child
        margin-bottom 0
      &:hover
        p
          color #0183f2
      img
        float left
        margin-right 15px
        width 230px
        height 132px
      h5
        font-size 16px
        line-height 16px
        white-space nowrap
        overflow hidden
        text-overflow ellipsis
      p
        font-size 14px
        color #999
        height 100px
        line-height 24px
        margin-top 15px
        overflow hidden
.swch
  height 240px
  background #0183f2 url('../assets/images/index/bg_zc.jpg') center top no-repeat
  .swch-content
    width 450px
    margin 0 auto
    padding-top 55px
    img
      float left
      border-radius 5px
      margin-right 32px
    h5
      font-size 34px
      color #fff
      line-height 45px
      font-weight normal
    a
      margin-top 28px
      display inline-block
      width 136px
      height 34px
      line-height 34px
      border-radius 4px
      border 1px solid #fff
      text-align center
      color #fff
      &.tel
        background #fff
        color #0183f2
        margin-right 8px
    
      
</style>
