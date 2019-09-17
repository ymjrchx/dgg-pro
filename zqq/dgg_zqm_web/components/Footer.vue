<template>
  <div class="footer">
    <div class="wrapper">
    <ul>
      <li class="link">
        <h6>友情链接</h6>
        <ol>
          <li><a target="_blank" href="https://qds.dggshop.net/">企大顺</a></li>
          <li><a target="_blank" href="http://www.zhiqianqiu.com">知千秋</a></li>
          <li><a target="_blank" href="http://www.dgg.net">顶呱呱集团</a></li>
        </ol>
      </li>

      <li class="info">
        <h6>公司信息</h6>
        <ol>
          <li><router-link target="_blank" to="/about/4">关于我们</router-link><router-link to="/about/1">联系我们</router-link></li>
          <li><router-link target="_blank" to="/about/2">用户协议</router-link><router-link to="/about/5">用户帮助</router-link></li>     
          <li><router-link target="_blank" to="/about/3">用户隐私权</router-link><router-link to="/about/6">名词百科</router-link></li>
        </ol>
      </li>

      <li class="concat">
        <h6>联系我们</h6>
        <ol>
          <!-- <li>智企名官方电话：<span>400-0055-002</span></li> -->
          <li>举报邮箱：jubao@dgg.net</li>
          <li>举报电话：15708452246</li>
          <li>地址：北京市朝阳区东三环北路38号民生大厦21层</li>
        </ol>
      </li>
    </ul>
    </div>
    <p class="cp">数据来源： <a target="_blank" href="http://www.gsxt.gov.cn/">全国企业信用信息公示系统</a> | <a href="http://wenshu.court.gov.cn/" target="_blank">中国裁判文书网</a> | <a href="http://shixin.court.gov.cn/" target="_blank">中国执行信息公开网</a> | <a href="http://www.sipo.gov.cn/" target="_blank">国家知识产权局</a> | <a href="http://sbj.saic.gov.cn/" target="_blank">商标局</a> | <a href="http://www.ncac.gov.cn/" target="_blank">版权局</a><br>Copyright 1996-2018 北京顶呱呱企业管理有限公司 备案号：京ICP备16006021号-1</p>
    <div class="floor">
      <img onclick="DGGkefu.open()" src="../assets/images/kf.png" alt="msg">
      <!-- <a href="javascript:void(0);" onclick="DGGkefu.open()" class="kf"><img src="../assets/images/icon_kf.png" alt="msg"></a> -->
      <a href="#top" v-show="isTop"><img src="../assets/images/icon_top.png" alt="top"></a>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      isTop: false
    }
  }, 
  mounted () {
    window.onscroll = () => {
      if ((document.body.scrollTop || document.documentElement.scrollTop)>500) {
        this.isTop = true
      } else {
        this.isTop = false
      }
    }
  },
  destroyed () {
    window.onscroll = null
  }
}
</script>

<style lang="stylus" scoped>
.footer
  overflow hidden
  background #353535
  padding-top 35px
  ul
    overflow hidden
    padding-bottom 30px
    padding-left 120px
    >li
      float left
      width 33.3%
      line-height 37px
      h6
        color #fff
        font-weight normal
        font-size 18px
      li
        color #666
        font-size 12px
        span
          color #ccc
      a
        color #666
        font-size 12px
        display inline-block
        width 80px
        white-space nowrap
        &:hover
          color #1c8bee
  .cp
    clear both
    font-size 12px
    border-top 1px solid #454545
    color #777
    text-align center
    line-height 20px
    padding 15px 0
    a
      color #777
      &:hover
        color #1c8bee
.floor
  position fixed
  right 0
  height 40px
  bottom 200px
  >img
    position absolute
    right 0
    cursor pointer
    top -116px
  a
    display block
    width 40px
    height 40px
    text-align center
    background #f89d44
    line-height 40px
    &.kf
      border-top 0
    &:hover
      background #2898fc
</style>
