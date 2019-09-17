<template>
  <div class="nav">
    <ul>
      <li><router-link  exact to="/">首页</router-link></li>
      <li v-for="item in navs" :key="item.title">
        <router-link  :to="item.url">{{item.title}}</router-link>
        <ol v-if="item.children">
          <li v-for="item in item.children" :key="item.title"><router-link :to="item.url">{{item.title}}</router-link></li>
        </ol>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data () {
    return {
      navs: [{
        title: '公司起名',
        url: '/createName'
      },{
        title: '公司核名',
        url: '/company'
      },{
        title: '公司测名',
        url: '/testName'
      },{
        title: '经营范围生成器',
        url: '/generator'
      }]
    }
  }
}
</script>

<style lang="stylus" scoped>
.nav
  height 100px
  line-height 100px;
  float right
  ul>li
    float left
    position relative
    >a
      float left
      margin-right 40px
      font-size 15px
      color #fff
      line-height 100px
      &.nuxt-link-active
        color #f89d44
        position relative
        &:after
          content '';
          position absolute
          left 0
          bottom 0
          right 0
          display block
          border-bottom 4px solid #f89d44
    &:hover
      ol
        display block
    ol
      display none
      position absolute
      background #fff
      padding 0 0 0 0
      top 50px
      z-index 99
      white-space nowrap
      text-align center
      width 120px
      left -30px
      border 1px solid #eee
      border-width 0 1px
      a
        display block
        line-height 40px
        font-size 14px
        border-bottom 1px solid #eee
        &:hover,&.nuxt-link-active
          //background #1c8bee
          color #1c8bee
          font-weight bold
          
</style>
