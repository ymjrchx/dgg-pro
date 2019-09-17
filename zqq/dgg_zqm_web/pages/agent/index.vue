<template>
    <div class="pageAgent">
        <div class="banner">
          <img src="../../assets/images/agent/banner.jpg" alt="代理记账">
          <h1>代理记账</h1>
          <p>移动互联网+“财务软件”一体化财税服务</p>
        </div>

        <div class="title-catalog-00">-  代理记账快速通道  -</div>
        <ul class="wrapper td">
          <li>
            <h2>小规模代理记账</h2>
            <p>日常财税咨询，保障企业日常经营</p>
            <a href="javascript:void(0);" @click="submit" class="button">预约会计</a>
          </li>
          <li class="bg-01">
            <h2>一般纳税人代理记账</h2>
            <p>一体化企业财税解决方案</p>
            <a href="javascript:void(0);" @click="submit" class="button">预约会计</a>
          </li>
          <li class="bg-02">
            <h2>旧账梳理</h2>
            <p>重新规划您的账本</p>
            <a href="javascript:void(0);" @click="submit" class="button">立即回电</a>
          </li>
          <li class="bg-03">
            <h2>公司注册</h2>
            <p>量身打造，极致体验</p>
            <a href="javascript:void(0);" @click="submit" class="button">预约服务</a>
          </li>
        </ul>

        <div class="title-catalog-00">-  服务内容  -</div>

        <div class="server wrapper">
          <div class="left item">
            <div class="title">
              <h3>基础服务</h3>
              <p>企业代理记账（年付套餐）</p>
            </div>
            <ul>
              <li>整理原始凭据（所有记账报税必须票据）</li>
              <li>财税专家对所有数据资料审核</li>
              <li>财务记账管理咨询建议</li>
              <li>月度或季度税务申报</li>
              <li>税务申报表及时推送、出具财务报表</li>
              <li>月度装订凭证、年末装订账簿</li>
              <li>日常基本财务记账咨询、财税风险预警</li>
            </ul>
            <a href="javascript:void(0);" onclick="DGGkefu.open();" class="btn">详情咨询</a>
          </div>

          <div class="left item">
            <div class="title">
              <h3>免费服务</h3>
              <p>企业代理记账（年付套餐）</p>
            </div>
            <ul>
              <li>企业年度所得税汇算清缴</li>
              <li>新注册公司免费赠送税务报道</li>
              <li>企业工商年检</li>
              <li>终身后续指导和免费财税咨询服务</li>
              <li>核定残保金（年付赠送）</li>
              <li>法人一证通（年付赠送）</li>
            </ul>
            <a href="javascript:void(0);" onclick="DGGkefu.open();" class="btn">详情咨询</a>
          </div>

          <div class="left item">
            <div class="title">
              <h3>资料</h3>
              <p>您需要提供的资料</p>
            </div>
            <ul>
              <li>公司执照副本复印件</li>
              <li>法人、股东身份证复印件</li>
              <li>财务记账管理咨询建议</li>
              <li>银行开户许可证复印件</li>
              <li>一证通</li>
              <li>非新注册的公司交接原会计资料<br>（一证通；记账凭证；会计报表）</li>
            </ul>
            <a href="javascript:void(0);" onclick="DGGkefu.open();" class="btn">详情咨询</a>
          </div>
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
.pageAgent
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
    top 146px
    font-weight normal
  p
    top 238px
    font-size 24px
    line-height 32px
    color #bbbccc
.td
  overflow hidden
  padding-top 10px
  padding-left value
  li
    float left
    text-align center
    width 288px
    height 189px
    margin 0 5px
    color #fff
    background transparent url('../../assets/images/agent/img_00.jpg') left top no-repeat
    &:hover
      .button
        background #fff
        color #34b6e1
    &.bg-01
      background-image url('../../assets/images/agent/img_01.jpg')
      p
        color #c0c9ff
      &:hover
        .button
          background #fff
          color #5f74ef
    &.bg-02
      background-image url('../../assets/images/agent/img_02.jpg')
      p
        color #ddb0ff
      &:hover
        .button
          background #fff
          color #9c3fe3
    &.bg-03
      background-image url('../../assets/images/agent/img_03.jpg')
      p
        color #ffc3a8
      &:hover
        .button
          background #fff
          color #e6723f
    h2
      font-size 18px
      font-weight normal
      line-height 18px
      margin-top 45px
    p
      font-size 14px
      color #c3f0ff
      line-height 47px
    .button
      display inline-block
      width 120px
      height 40px
      line-height 40px
      color #fff
      text-align center
      font-size 14px
      border-radius 2px
      border 1px solid #fff
.server
  overflow hidden
  margin-top 10px
  width 930px
  border-right 1px solid #eee
  border-bottom 1px solid #eee
  margin-bottom 55px
  .item
    width 310px
    height 655px
    text-align center 
    position relative
    border-left 1px solid #eee
    box-sizing border-box
    .btn
      position absolute
      left 50%
      margin-left -110px
      bottom 20px
    &:last-child
      .title
        border 0
    .title
      height 120px
      background #666
      color #fff
      border-right 1px solid #fff
      box-sizing border-box
      padding-top 35px
      h3
        font-size 18px
        font-weight normal
      p
        font-size 14px
        font-weight normal
        padding-top 5px
    ul
      padding 20px
      li
        line-height 60px
        border-bottom 1px solid #eee
</style>
