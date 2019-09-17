<template>
    <div class="pageDown">
      <div class="banner">
        <img src="../../assets/images/down/banner.jpg" alt="公司注册">
        <div class="title">
            <h1>开公司必备下载材料</h1>
        </div>
      </div>

      <div class="tab-catalog-01">
        <a :class="{'atthis': tabIndex == 0}" @click="showTab(0)" href="javascript:void(0);">全部</a>
        <a :class="{'atthis': tabIndex == 1}" @click="showTab(1)" href="javascript:void(0);">有限责任公司</a>
        <a :class="{'atthis': tabIndex == 2}" @click="showTab(2)" href="javascript:void(0);">合伙企业</a>
        <a :class="{'atthis': tabIndex == 3}" @click="showTab(3)" href="javascript:void(0);">分公司</a>
        <a :class="{'atthis': tabIndex == 4}" @click="showTab(4)" href="javascript:void(0);">个人独资企业</a>
      </div>
     
      <div class="company">
          <div class="wrapper">
            <div class="left">
              <ul>
              <template  v-if="tabIndex == 1 || tabIndex == 0">
                <li v-for="item in templateTypes[0].list" :key="item.id"><a :href="item.fileUrl" @click="down(item)"><span>{{item.download}}次下载</span>{{item.fileName}}</a></li>
              </template>
              <template   v-if="tabIndex == 2 || tabIndex == 0">
                <li v-for="item in templateTypes[1].list" :key="item.id"><a :href="item.fileUrl" @click="down(item)"><span>{{item.download}}次下载</span>{{item.fileName}}</a></li>
              </template>
              <template   v-if="tabIndex == 3 || tabIndex == 0">
                <li v-for="item in templateTypes[2].list" :key="item.id"><a :href="item.fileUrl" @click="down(item)"><span>{{item.download}}次下载</span>{{item.fileName}}</a></li>
              </template>
              <template v-if="tabIndex == 4 || tabIndex == 0">
                <li v-for="item in templateTypes[3].list" :key="item.id"><a :href="item.fileUrl" @click="down(item)"><span>{{item.download}}次下载</span>{{item.fileName}}</a></li>
              </template>
              <template   v-if="tabIndex == 5 || tabIndex == 0">
                <li v-for="item in templateTypes[4].list" :key="item.id"><a :href="item.fileUrl" @click="down(item)"><span>{{item.download}}次下载</span>{{item.fileName}}</a></li>
              </template>
              <template   v-if="tabIndex == 6 || tabIndex == 0">
                <li v-for="item in templateTypes[5].list" :key="item.id"><a :href="item.fileUrl" @click="down(item)"><span>{{item.download}}次下载</span>{{item.fileName}}</a></li>
              </template>
              </ul>
            </div>
          </div>


      </div>
    </div>
</template>

<script>
import toolBox from '@/components/toolBanner'
export default {
  asyncData (context) {
    return Promise.all([context.$axios.$get('/template/findByTemplateType?templateTypes=template_type_11,template_type_12,template_type_13,template_type_14,template_type_15,template_type_16')])
    .then((res) => {
      return { 
        templateTypes: res[0].data
      }
    })
  },
  data () {
    return {
      show:true,
      tabIndex: 0
    }
  },
  components: {
    toolBox
  },
  mounted () {},
  methods: {
    showTab(index) {
      this.tabIndex = index
    },
    down(item) {
      this.$axios.$get(encodeURI(`/template/addDownload?id=${item.id}`)).then((res) => {
        item.download = res.data
      })
    }
  }
}
</script>
 
<style lang="stylus" scoped>
@import '../../assets/css/params.styl'
.tab-catalog-01
  text-align center
  font-size 0
  margin 50px 0 40px
  a
    display inline-block
    width 200px
    height 50px
    line-height 50px
    font-size 18px
    text-align center
    background #ccc
    margin-left 1px
    &:hover,&.atthis
      background #2bc1f2
      color #fff
  
.pageDown
  overflow hidden
  min-width 1200px
  width 100%
  background #fff
.banner
  width 1920px
  position relative
  left 50%
  margin-left -960px
  height 420px
  img
    height 100%
  .title
    width 1035px
    position absolute
    left 50%
    margin-left -517.5px
    top 165px
    text-align center
    color #fff
    h1
      font-size 48px
      margin-bottom 30px
      font-weight 100
    p
    font-size 24px
  
.company
  margin-top 40px
  background #fff;
  padding: 15px 0 70px
  overflow hidden
  .left,.right
    width 100%
    padding 0 50px 0 20px
    box-sizing border-box
  ul
    overflow hidden
    padding-top 10px
    li
      float left
      width 50%
      box-sizing border-box
      padding 0 20px
      a
        display block
        line-height 68px
        border-bottom 1px solid $solidcolor
        overflow hidden
        white-space nowrap
        text-overflow ellipsis
        background transparent url('../../assets/images/index/icon_word.png') no-repeat left center
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
</style>
