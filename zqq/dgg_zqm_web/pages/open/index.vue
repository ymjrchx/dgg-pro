<template>
    <div class="pageOpen">
        <div class="banner">
          <img src="../../assets/images/open/banner.jpg" alt="开发平台">
          <h1>开放平台</h1>
          <p>国内领先的企业大数据服务平台，已专业能力洞见<br>数据价值数据定制，快速高效</p>
          <a class="zx-btn" href="javascript:void(0);" onclick="DGGkefu.open()">立 即 咨 询</a>
        </div>

        <div class="title-catalog-00">-  数据API  -</div>

        <ul class="dataList">
          <li v-for="item in dataList" :key="item.name">
            <img :src="item.img" alt="icon">
            <div class="hidden">
              <h2 class="name">{{item.name}}</h2>
              <p class="des">{{item.des}}</p>
              <p>申请数：{{item.apply}}</p>
              <p>资费类型：<span>{{item.price}}元/次</span></p>
              <a href="javascript:void(0);" @click="submit" class="sq-btn">申请接口</a>
            </div>
          </li>
        </ul>
    </div>
</template>

<script>
export default {
  data () {
    return {
      dataList: [{
        img: require('../../assets/images/open/icon_hm.png'),
        name: "工商核名接口",
        des: "在该接口中，通过地区、字号、行业、公司类型等关键字",
        apply: 244,
        price: 0.05
      },{
        img: require('../../assets/images/open/icon_sb.png'),
        name: "商标成功率计算",
        des: "通过算法合理计算商标注册成功率",
        apply: 504,
        price: 0.05
      },{
        img: require('../../assets/images/open/icon_jk.png'),
        name: "商标健康检查",
        des: "商标健康检查",
        apply: 34,
        price: 0.05
      },{
        img: require('../../assets/images/open/icon_cx.png'),
        name: "商标查询接口",
        des: "支持全国商标查询，了解最新的商标信息以及申请进度",
        apply: 54,
        price: 0.05
      },{
        img: require('../../assets/images/open/icon_zl.png'),
        name: "专利查询接口",
        des: "商标健康检查",
        apply: 304,
        price: 0.05
      },{
        img: require('../../assets/images/open/icon_cp.png'),
        name: "裁判文书列表查询",
        des: "查询裁判文书相关信息",
        apply: 254,
        price: 0.05
      },{
        img: require('../../assets/images/open/icon_ws.png'),
        name: "裁判文书分类统计",
        des: "经营范围生成工具",
        apply: 454,
        price: 0.05
      },{
        img: require('../../assets/images/open/icon_jy.png'),
        name: "经营范围生成接口",
        des: "企业信息查询",
        apply: 364,
        price: 0.05
      },{
        img: require('../../assets/images/open/icon_qy.png'),
        name: "企业信息查询",
        des: "经营范围生成工具",
        apply: 134,
        price: 0.05
      },{
        img: require('../../assets/images/open/icon_sh.png'),
        name: "税号列表查询",
        des: "企业信息查询",
        apply: 354,
        price: 0.05
      }]
    }
  },
  components: {
  },
  mounted () {

  },
  methods: {
    submit () {
      let that = this
      this.$popup.created({
          title: '请留下您的联系方式，稍后专业顾问为您解答',
          type: 'leaving',
          success (name, phone, backFn) {
            that.$axios.$post('/customerMsg/save', {
            "name": name,
            "phone": phone,
            "type": "customer_msg_type_01"})
            .then((res) => {
              backFn()
            })
          }
      })
    }
  }
}
</script>
 
<style lang="stylus" scoped>
@import '../../assets/css/params.styl'
.pageOpen
  background #fff
  overflow hidden
.banner
  position relative
  width 1920px
  left 50%
  margin-left -960px
  height 420px
  >*
    position absolute
    z-index 2
    left 50%
    margin-left -600px
  img
    position absolute
    width 100%
    height 100%
    z-index 1
    left 0
    margin 0
  h1
    font-size 42px
    color #fff
    top 105px
    font-weight normal
  p
    top 172px
    font-size 24px
    line-height 32px
    color #bbbccc
  .zx-btn
    top 275px
    width 160px
    height 46px
    line-height 46px
    text-indent 20px
    border-radius 50px
    color #333
    font-size 16px
    background #fff url('../../assets/images/open/icon_arr.png') 110px center no-repeat
.dataList
  padding-bottom 35px
  overflow hidden
  width 980px
  margin 0 auto
  li
    float left
    width 460px
    height 210px
    box-sizing border-box
    padding 30px
    margin 15px
    background #fff
    box-shadow 0 0 15px 5px rgba(204, 204, 204, .3)
    img
      // width 70px
      // height 70px
      float left
      margin-right 20px
    .hidden
      overflow hidden
      .name
        font-size 18px
        font-weight normal
      .des
        font-size 12px
        color #666
        line-height 30px
      p
        font-size 14px
        line-height 24px
        span
          color #1c8bee
      .sq-btn
        display inline-block
        width 160px
        height 34px
        line-height 34px
        text-align center
        color #fff
        border-radius 2px
        background #1c8bee
        margin-top 15px
        transition all .5s
    &:hover
      box-shadow 0 0 15px 0 rgba(204, 204, 204, 1)
</style>
