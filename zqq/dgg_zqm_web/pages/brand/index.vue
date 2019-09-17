<template>
    <div class="pagebrand">
      <div class="banner">
        <img src="../../assets/images/brand/banner.jpg" alt="商标注册">
        <h1>商标注册</h1>
        <p>一个有个性的名字、一个有内涵的品牌<br>从这里开始被记住</p>
      </div>
      <div class="zc">
        <div class="wrapper">
          <div class="title-catalog-00">-  智企名注册速度快、服务好、风险低  -</div>
          <div class="three_box clear marB30">
              <div class="light" :class="{'banner-hover':hoverIdx == 0}" @mouseenter='hoverIdx = 0'>
                  <div class="pad fp">
                      <a class="imgBox" href="javascript:;" >
                          <img src="../../assets/images/brand/icon_r.png" alt="">
                      </a>
                  </div>
                  <div class="pad mofu">
                      <h3>商标普通注册</h3>
                      <p>1-3个工作日内提交申请<br>网上申报，次日拿到申请号</p>
                      <div class="btnBox">
                          <a href="javascript:void(0);" @click="submit">获取报价</a>
                          <a class="colBtn" href="javascript:void(0);" onclick="DGGkefu.open();" >免费咨询</a>
                      </div>
                  </div>
              </div>
              <div class="light" :class="{'banner-hover':hoverIdx == 1}" style="margin-left:0" @mouseenter='hoverIdx = 1'>
                  <div class="pad fp">
                      <a class="imgBox" href="javascript:void(0);" onclick="DGGkefu.open();" >
                          <img src="../../assets/images/brand/icon_ks.png" alt="">
                      </a>
                  </div>
                  <div class="pad mofu">
                      <h3>商标快速注册</h3>
                      <p>59分钟快速申报！</p>
                      <div class="btnBox">
                          <a href="javascript:void(0);" @click="submit">获取报价</a>
                          <a class="colBtn" href="javascript:void(0);" onclick="DGGkefu.open();" >免费咨询</a>
                      </div>
                  </div>
              </div>
              <div class="light" :class="{'banner-hover':hoverIdx == 2}" style="margin-left:0" @mouseenter='hoverIdx = 2'>
                  <div class="pad fp">
                      <a class="imgBox" href="javascript:void(0);" onclick="DGGkefu.open();" >
                          <img src="../../assets/images/brand/icon_xh.png" alt="">
                      </a>
                  </div>
                  <div class="pad mofu">
                      <h3>商标循环注册</h3>
                      <p>为企业保驾护航！</p>
                      
                      <div class="btnBox">
                          <a href="javascript:void(0);" @click="submit">获取报价</a>
                          <a class="colBtn" href="javascript:void(0);" onclick="DGGkefu.open();" >免费咨询</a>
                      </div>
                  </div>
              </div>
          </div>
          
          <div class="title-catalog-00">-  5大优质服务  -</div>
          <p class="sign">服务实力更强！智企名带来更优质的申请体验！</p>
          <div class="tu">
            <img src="../../assets/images/brand/img_brand_00.jpg" alt="01免费专业分析服务 ">
            <ul class="fxfw">
              <li>
                <div class="icon"><img src="../../assets/images/brand/icon_00.png" alt="免费查询商标近似"></div>
                <div class="name">免费</div>
                <p>查询商标近似</p>
              </li>

              <li>
                <div class="icon"><img src="../../assets/images/brand/icon_01.png" alt="30秒快速"></div>
                <div class="name">30秒快速</div>
                <p>反馈查询结果</p>
              </li>

              <li>
                <div class="icon"><img src="../../assets/images/brand/icon_02.png" alt="双重检索"></div>
                <div class="name">双重检索</div>
                <p>商标状态</p>
              </li>

              <li>
                <div class="icon"><img src="../../assets/images/brand/icon_03.png" alt="严格把关"></div>
                <div class="name">严格把关</div>
                <p>降低注册风险</p>
              </li>

              <li>
                <div class="icon"><img src="../../assets/images/brand/icon_04.png" alt="免费查询商标近似"></div>
                <div class="name">多种</div>
                <p>商标解决方案</p>
              </li>

              <li>
                <div class="icon"><img src="../../assets/images/brand/icon_05.png" alt="有效助力"></div>
                <div class="name">有效助力</div>
                <p>商标注册成功</p>
              </li>

              <li>
                <div class="icon"><img src="../../assets/images/brand/icon_06.png" alt="一对一"></div>
                <div class="name">一对一</div>
                <p>专属顾问</p>
              </li>

              <li>
                <div class="icon"><img src="../../assets/images/brand/icon_07.png" alt="7x24小时"></div>
                <div class="name">7x24小时</div>
                <p>提供专业解答</p>
              </li>
            </ul>

            <img class="status" src="../../assets/images/brand/img_brand_01.jpg" alt="及时申报服务 办理状态随时通知 ">
          </div>
        </div>
      </div>


      <div class="fp">
        <img  src="../../assets/images/brand/img_brand_02.jpg" alt="发票、快递正规服务 ">
      </div>
      
      <div class="flow">
        <img src="../../assets/images/brand/img_brand_03.jpg" alt="免费终身享受售后服务">
        <div class="title-catalog-00">-  服务流程  -</div>
        <img class="lc" src="../../assets/images/brand/img_brand_04.jpg" alt="服务流程">
      </div>

    </div>
</template>

<script>
export default {
  data () {
    return {
      hoverIdx: 1
    }
  },
  components: {
  },
  mounted () {

  },
  methods: {
    switchFn (type) {
      this.type = type
    },
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
.pagebrand
  overflow hidden
  min-width 1200px
  width 100%
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
    top 130px
    left 50%
    margin-left -460px
    font-weight normal
  p
    top 210px
    left 50%
    margin-left -460px
    font-size 24px
    line-height 32px
    color #bbbccc
.zc
  background #fff
// 更换
.three_box {
    padding: 12px;
    background: #fff;
    text-align: center;
    font-size: 0;
    padding: 40px 0 0;

    > div {
        display: inline-block;
        width: 380px;
        height: 450px;
        text-align: center;
        // padding: 38px 14px 20px;
        box-sizing: border-box;
        position: relative;
        font-size: 14px;

        .pad {
            padding: 10px 14px 20px;
            h3 {
                font-size 24px
                margin-top 45px
                color #1c8bee
                font-weight normal
              }
            p {
              height 75px
              line-height 24px
              font-size 16px
              color #666
              padding-top 15px
            }
        }

        .mofu {
            box-shadow: 0 0 5px 0 #e1e1e1;
        }

        .pad.fp {
            // transition: all 0.5s;
            height: 130px;
            background: #1c8bee ;
            color: #fff;
            box-sizing border-box
            line-height 130px
        }

        .imgBox {
            display: inline-block;
            border-radius: 50%;
            position: relative;
            z-index: 4;

            img {
                height: 48px;
            }
        }

        .title {
            font-size: 18px;
            font-weight: 100;
            margin-top: 14px;
            // margin-bottom: 20px;
        }

        small {
            color: #565656;
            font-size: 14px;
        }

        .iconP {
            background: #FFFBFA;
            color: #FF4800;
            border-top: 1px solid #FFDDCF;
            border-bottom: 1px solid #FFDDCF;
            padding: 10px 0;
            margin-top: 22px;
            margin-bottom: 28px;
            font-size: 12px;

            span {
                display: inline-block;
                width: 16px;
                height: 100%;
            }
        }
        .btnBox {
            width: 340px;
            margin: auto;
            margin-top: 16px;

            a {
                display: inline-block;
                border-radius: 2px;
                font-size: 14px;
                border: 1px solid #1c8bee;
                color: #1c8bee;
                width: 160px;
                height: 44px;
                line-height: 44px;
                text-align: center;
                position: relative;
                z-index: 4;
                box-sizing: border-box;
            }

            .colBtn {
                margin-left: 12px;
                border: 1px solid #f89d44;
                color: #f89d44;
            }
        }

        .flag {
            position: absolute;
            right: 0;
            top: 0;
        }
    }
}

div.banner-hover {
    position: relative;
    top: -20px;
    width: 420px !important;

    .pad.fp {
        background: #f89d44 !important;
    }

    .pad.mofu {
        padding: 20px 14px 60px !important;
    }

    .btnBox {
        a {
            color: #fff;
            background: #1c8bee;
        }

        .colBtn {
            background: #f89d44;
            color: #fff;
        }
    }
}
.sign
  font-size 18px
  color #999
  margin-top -20px
  text-align center
  font-weight bold
.tu
  text-align center
  padding-top 20px
.fxfw
  width 960px
  margin 0 auto
  text-align center
  overflow hidden
  padding-top 30px
  padding-bottom 30px
  li
    float left
    width 240px
    height 240px
    text-align center
    .icon
      width 140px
      height 140px
      background #e2eeff
      border-radius 50%
      line-height 140px
      text-align center
      margin 0 auto
    .name
      font-size 24px
      line-height 30px
      margin-top 15px
    p
      font-size 14px
      line-height 20px
.status
  padding-bottom 40px
.fp
  padding 60px 0 65px
  text-align center
  overflow hidden
.flow
  text-align center
  padding 60px 0
  background #fff
  overflow hidden
</style>
