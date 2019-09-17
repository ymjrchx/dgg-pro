<template>
    <div class="pagejoin">
      <div class="banner">
        <img src="../../assets/images/join/banner.jpg" alt="公司注册">
        <div class="title">
            <h1>加盟智企名共赢“互联网+工商”未来</h1>
            <p>智企名诚邀全国加盟商<br>统一加盟热线：400-0055-002</p>
        </div>
        <div class="search" @mouseleave="close">
          <ul>
              <li class="address" @click="showFix('isArea')">{{provinceIndex > -1 ? area[provinceIndex].child[cityIndex].name.substr(0, 3): '全国'}}</li>
              <li class="input"><input type="text" v-model="name" maxlength="10" placeholder="请输入您的姓名"></li>
              <li class="input"><input class="tel" v-model="phone" maxlength="11" type="text" placeholder="请输入联系方式"></li>
          </ul>
          <button @click="submit">我要加盟</button>

          <div class="addressArea" v-show="isArea">
            <div class="province">
              <div class="title">省份选择</div>
              <ul>
                <li @click="seletedArea(-1)" :class="{'atthis': -1 == provinceIndex}">全国</li>
                <li v-for="(item, index) in area"  @click="seletedArea(index, 'province')" :class="{'atthis': index == provinceIndex}"  :key="item.id">{{province(item.name)}}</li>
              </ul>
            </div>
            <div class="city" v-if="provinceIndex != -1 ">
              <div class="title">城市选择</div>
              <ul>
                <li v-for="(item, index) in area[provinceIndex].child" @click="seletedArea(index, 'city')" :class="{'atthis': index == cityIndex}"  :key="item.id">{{item.name}}</li>
              </ul>
            </div>
          </div>
          
        </div>

      </div>

      <div class="jm">
        <div class="wrapper">
          <div class="title-catalog-00">-  为什么加盟顶起查  -</div>
          <ul>
            <li><a href="javascript:void(0)"><img src="../../assets/images/join/icon_join_00.png" alt=""><p>服务覆盖全国</p></a></li>
            <li><a href="javascript:void(0)"><img src="../../assets/images/join/icon_join_01.png" alt=""><p>给流量！给客户！</p></a></li>
            <li><a href="javascript:void(0)"><img src="../../assets/images/join/icon_join_02.png" alt=""><p>给管理培训和系统</p></a></li>
            <li><a href="javascript:void(0)"><img src="../../assets/images/join/icon_join_03.png" alt=""><p>互联网一站式服务平台</p></a></li>
          </ul>
        </div>
      </div>

      <div class="ys wrapper">
        <div class="title-catalog-00">-  加盟优势  -</div>
        <ul>
            <li><img src="../../assets/images/join/img_ys_00.jpg" alt=""><p>全国已成立多个办事处</p><a href="javascript:void(0);" onclick="DGGkefu.open();">立即咨询</a></li>
            <li><img src="../../assets/images/join/img_ys_01.jpg" alt=""><p>给流量！给客户！</p><a href="javascript:void(0);" onclick="DGGkefu.open();">立即咨询</a></li>
            <li><img src="../../assets/images/join/img_ys_02.jpg" alt=""><p>给管理培训和系统</p><a href="javascript:void(0);" onclick="DGGkefu.open();">立即咨询</a></li>
            <li><img src="../../assets/images/join/img_ys_03.jpg" alt=""><p>互联网一站式服务平台</p><a href="javascript:void(0);" onclick="DGGkefu.open();">立即咨询</a></li>
          </ul>
      </div>

      <div class="flow">
        <div class="wrapper">
          <div class="title-catalog-00">-  加盟流程  -</div>
          <img src="../../assets/images/join/img_flow.jpg" alt="加盟流程">
          <div class="button">
          <a href="javascript:void(0);" onclick="DGGkefu.open();"><img src="../../assets/images/img_button.png" alt="立即咨询"></a>
          </div>
        </div>
      </div>

    </div>
</template>

<script>
const area = require('@/assets/js/area.json')
export default {
  data () {
    return {
      area: area.area_json,
      provinceIndex: 22,
      cityIndex: 0,
      isArea: false,

      name: '',
      phone: '',
    }
  },
  components: {

  },
  mounted () {

  },
  methods: {
    province (name) {
      switch (name) {
        case '内蒙古自治区': 
          return '内蒙古'
        case '黑龙江省': 
          return '黑龙江'
        default:
          return name.substr(0, 2)
      }
    },
    seletedArea (index, level) {
      if (index === -1) {
        this.provinceIndex = -1
        this.isArea = false
        return false
      }
      if (level == 'province') {
        this.provinceIndex = index
        this.cityIndex = 0
      } else {
        this.cityIndex = index
        this.isArea = false
      }
    },
    close () {
      this.isArea = false
    },
    showFix (type) {
      this.isArea = false
      this[type] = true
    },
    submit () {
      if (!this.name) {
        this.$popup.created({
            content: '请输入姓名',
            time: 2000
        })
        return false
      }
      if (!this.phone) {
        this.$popup.created({
            content: '请输入电话',
            time: 2000
        })
        return false
      }
      if (!/^1(3|4|5|6|7|8|9)\d{9}$/.test(this.phone)) {
        this.$popup.created({
            content: '请输入正确电话',
            time: 2000
        })
        return false
      }
      this.$axios.$post('/customerMsg/save', {
        city: this.provinceIndex > -1 ? this.area[this.provinceIndex].child[this.cityIndex].name: '全国',
        name: this.name,
        phone: this.phone,
        type: "customer_msg_type_02"})
        .then((res) => {
          this.name = ''
          this.phone = ''
          this.$popup.created({
              content: '提交成功',
              time: 2000
          })
      }).catch(() => {
          this.$popup.created({
              content: '提交失败',
              time: 2000
          })
      })
    }
  }
}
</script>
 
<style lang="stylus" scoped>
@import '../../assets/css/params.styl'
.pagejoin
  overflow hidden
  min-width 1200px
  width 100%
  
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
    top 125px
    text-align center
    color #fff
    h1
      font-size 48px
      margin-bottom 30px
      font-weight 100
    p
    font-size 24px
    line-height 42px
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
          width 263px
          input
            padding-left 40px
            font-size 16px
            line-height 30px
            height 30px
            width 100%
            color $color
            box-sizing border-box
            background #fff url('../../assets/images/join/icon_user.png') 10px center no-repeat
            &.tel
              background #fff url('../../assets/images/join/icon_tel.png') 10px center no-repeat
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
.jm
  text-align center
  padding-bottom 60px
  background #fff
  li
    display inline-block
    margin-top 15px
    width 200px
    text-align center
    p
      font-size 16px
      font-weight bold
.ys
  overflow hidden
  padding-bottom 50px
  li
    float left
    width 280px
    height 400px
    box-sizing border-box
    padding 20px
    text-align center
    background #fff
    margin 10px
    img
      width 100%
    p
      font-size 16px
      font-weight bold
      line-height 80px
    a
      display inline-block
      width 150px
      line-height 40px
      height 40px
      box-shadow 0 0 15px 5px rgba(43, 193, 242, .2)
      background #2bc1f2
      border-radius 3px
      color #fff
      &:hover
        box-shadow 0 0 20px 3px rgba(43, 193, 242, .6)
        
.flow
  text-align center
  background #fff
  padding-bottom 70px
  img
    margin-top 15px
  .button
    padding-top 40px
</style>
