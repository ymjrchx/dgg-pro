<template>
  <div>
    <Header />
    <Nav />
    <nuxt />
    <div class="footer">
      <ul>
          <li><router-link target="_blank" to="/about/4">关于我们</router-link></li>
          <li><router-link target="_blank" to="/about/1">联系我们</router-link></li>
          <li><router-link target="_blank" to="/about/2">用户协议</router-link></li>
          <li><router-link target="_blank" to="/about/5">用户帮助</router-link></li>     
          <li><router-link target="_blank" to="/about/3">用户隐私权</router-link></li>
          <li><router-link target="_blank" to="/about/6">名词百科</router-link></li>
      </ul>
      <p class="cp">Copyright 2018 智企名 版权所有 | 京ICP备16023355号-2</p>
    </div>
  </div>
</template>
<script>
import Header from '~/components/Header.vue'
import Nav from '~/components/Nav.vue'
export default {
  components: {
    Header,
    Nav
  }
}

</script>
<style lang="stylus" scoped>
.footer
  text-align center
  padding-top 30px
  ul
    li
      display inline-block
      margin 0 10px
      a
        line-height 30px
        font-size 14px
  .cp
    padding-top 15px
    font-size 14px
    color #333
</style>
