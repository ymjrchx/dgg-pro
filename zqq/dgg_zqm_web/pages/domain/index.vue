<template>
    <div class="pageGenerator">
      <div class="banner">
        <img src="../../assets/images/domain/banner.jpg" alt="域名查询">
        <div class="title">
            <h1>域名查询</h1>
            <p>查询域名相关信息</p>
        </div>
        <div class="search">
          <ul>
            <li class="input">
              <input  type="text" v-model="domainName" placeholder="请输入域名主体，例如，baidu">
            </li>
          </ul>
          <button @click="search">立即搜索</button>
        </div>
        <p class="tips">请输入您的域名, 如："www.baidu.com" 只需输入"baidu"</p>
      </div>
      <section class="listBox" v-if="resultAll">
            <h2>域名查询结果</h2>
            <table cellpadding="0" cellspacing="1" width="100%">
              <tbody>
              <tr>
                <th width="120">序号</th>
                <th>域名</th>
                <th width="120">状态</th>
                <th width="120">操作</th>
              </tr>
              <tr v-for="(item,index) in resultAll.allow" :key="item">
                <td>{{index+1}}</td>
                <td class="name">{{item}}</td>
                <td>未注册</td>
                <td><a href="javascript:void(0)" class="btn">立即咨询</a></td>
              </tr>
              <tr v-for="(item,index) in resultAll.premiums" :key="item.domain">
                <td>{{(resultAll.allow?resultAll.allow.length:0)+index+1}}</td>
                <td class="name">{{item.domain}}</td>
                <td>已注册</td>
                <td><a href="javascript:void(0)" class="btn">立即咨询</a></td>
              </tr>
              <tr v-for="(item,index) in resultAll.registered" :key="item">
                <td>{{(resultAll.premiums?resultAll.premiums.length:0)+(resultAll.allow?resultAll.allow.length:0)+index+1}}</td>
                <td class="name">{{item}}</td>
                <td>已注册</td>
                <td><a href="javascript:void(0);" onclick="DGGkefu.open();" class="btn">立即咨询</a></td>
              </tr>
              
              </tbody>
            </table>

            <a href="javascript:void(0)" @click="switchName" class="btn-00">换一批</a>
      </section> 
      <toolBox/>
    </div>
</template>

<script>
import toolBox from '@/components/toolBanner'
let suffix = ".com,.cn,.xin,.net,.top,.在线,.xyz,.wang,.shop,.site,.club,.cc,.fun,.online,.biz,.red,.link,.ltd,.mobi,.info,.org,.com,.cn,.net,.cn,.org,.cn,.gov,.cn,.name,.vip,.pro,.work,.tv,.co,.kim,.group,.tech,.store,.ren,.ink,.pub,.live,.wiki,.design,.中文网,.我爱你,.中国,.网址,.网店,.公司,.网络,.集团,.beer,.art,.餐厅,.luxe,.商标"

export default {
  data () {
    return {
      show:true,
      domainName: '',
      resultAll: '',

      pageIndex: 0,
      suffixArr: suffix.split(',')
    }
  },
  components: {
    toolBox
  },
  mounted () {
    
  },
  methods: {
    search () {
      if (!this.$store.state.userInfo.userId) {
        this.$loginPop(this)
        return false
      }
      if (!this.domainName) {
        this.$popup.created({
            content: '请输入域名主体',
            time: 2000
        })
        return false
      }
      
      let arr = this.suffixArr.slice(0+(this.pageIndex*5), ((this.pageIndex+1)*5))
      this.$axios.$post(encodeURI('/queryDomain/query?domainName='+this.domainName+'&suffix='+ arr.join(',')))
      .then((res) => {
        this.resultAll = res.data
      })
    },
    switchName () {
      this.pageIndex++
      if (this.pageIndex*5>this.suffixArr.length) {
        this.pageIndex = 1
      }
      this.search()
    }
  }
}
</script>
 
<style lang="stylus" scoped>
@import '../../assets/css/params.styl'
   *{
       box-sizing border-box;
   }   
.pageGenerator
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
  >.title
    width 1035px
    position absolute
    left 50%
    margin-left -517.5px
    top 120px
    text-align center
    color #fff
    h1
      font-size 48px
      margin-bottom 30px
      font-weight 100
    p
    font-size 24px
  .search
    width 850px
    position absolute
    left 50%
    height 70px
    margin-left -425px
    top 340px
    z-index 2
    background #fff
    >ul
      height 70px
      line-height 70px
      padding 20px 0
      float left
      box-sizing border-box
      li
        float left
        line-height 30px
        font-size 16px
        cursor pointer
        border-right 1px solid $solidcolor
        &.address
          width 140px
          text-align center
          position relative
          background transparent url('../../assets/images/icon_address.png') 24px center no-repeat
          &:after
            position absolute
            right 28px
            top 13px
            content ''
            width 0
            height 0
            border 6px solid transparent
            border-top-color #ccc
        &.input
          width 669px
          input
            padding-left 40px
            font-size 16px
            line-height 30px
            width 100%
            color $color
            box-sizing border-box
        &.industry
          padding-left 20px
          position relative
          color #999
          width 204px 
          &.atthis
            color #333
          &:after
            position absolute
            right 28px
            top 13px
            content ''
            width 0
            height 0
            border 6px solid transparent
            border-top-color #ccc
        &.type
          width 200px
          padding-left 20px
          position relative
          border 0
          &:after
            position absolute
            right 28px
            top 13px
            content ''
            width 0
            height 0
            border 6px solid transparent
            border-top-color #ccc
    button
      float left
      width 181px
      line-height 70px
      height 70px
      text-align center
      color #fff
      font-size 18px
      background #2bc1f2
      cursor pointer
    .addressArea
      background #fff
      clear both
      border 1px solid #ccc
      padding 20px
      position relative
      &:after
        content ''
        clear both
        display block
      &:before
        content ''
        position absolute
        width 18px
        height 12px
        background transparent url('../../assets/images/company/icon_arr.jpg') left top no-repeat
        top -12px
        left 66px
      .province
        float left
        width 300px
        padding-left 10px
        padding-right 30px
        border-right 1px solid $solidcolor
        li
          width 50px
          box-sizing border-box
      .city
        float left
        width 320px
        padding-right 30px
        padding-left 20px
        li
          padding 0 10px
      .title
        font-size 14px
        font-weight bold
        color $maincolor
        line-height 30px
      ul
        overflow hidden
        padding-top 10px
        li
          float left
          cursor pointer
          font-size 14px
          line-height 30px
          margin-right 10px
          text-align center
          white-space nowrap
          &.atthis
            color #fff
            background #1c8bee
            border-radius 3px
  .tips
    position absolute
    top 430px
    left 0
    width 100%
    text-align center
    color #fff
    font-size 14px
.listBox
  padding-top 60px;
  text-align center;
  width 1120px;
  margin auto;
  background #fff;
  h2
    font-size 24px
    padding-bottom 50px
  table
    background #fff
    font-size 14px
    line-height 22px
    text-align center
    border-spacing 1px
    border-collapse initial
    th
      background #1c8bee
      color #fff
      padding 22.5px 0
      font-size 18px
      font-weight normal
    td
      background #eee
      padding 22.5px 20px
      &.name
        background #f5f5f5
</style>
