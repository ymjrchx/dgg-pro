<template>
    <div class="pagetax">
      <div class="banner">
        <img src="../../assets/images/tax/banner.jpg" alt="公司注册">
        <div class="title">
            <h1>税务筹划专家</h1>
            <p>为企业和个人量身定制节税方案，提升收入，合法合规享受低税率，<br>节税力度不止80%</p>
        </div>
      </div>
      <div class="jsq">
        <div class="switch" :class="{'selected': type == 1}">
          <a :class="{'atthis': type == 0}" @click="switchFn(0)" href="javascript:void(0);">企业</a>
          <a :class="{'atthis': type == 1}" href="javascript:void(0);" @click="switchFn(1)">个人</a>
        </div>
        <div class="title" v-if="type == 0">请输入企业收入情况：</div>
        <div class="title" v-if="type == 1">请输入个人收入情况：</div>
        <div class="info" v-if="type == 0" :class="{'pl150': taxType==0}">
          <div class="item taxType">
            <label for="">纳税类型</label><select v-model="taxType" name="" id="">
              <option value="0">小规模纳税人</option>
              <option value="1">一般纳税人</option>
          </select>
          </div>
          <div class="item rate">
            <label for="">企业增值税率</label><input v-model="rate" type="number" placeholder="请输入税率"> %
          </div>
          <div class="item income" v-if="taxType==1">
            <label for="">企业进项票金额</label><input v-model="income" type="number" placeholder="请输入金额"> 万元
          </div>
          <div class="item invoice">
            <label for="">企业开票金额</label><input v-model="invoice" type="number" placeholder="请输入金额"> 万元
          </div>
        </div>
        <div class="info pl150" v-if="type == 1">
          <div class="item wages">
            <label for="">工资收入金额</label><input v-model="wages" type="number" placeholder="请输入您的税前收入"> 元
          </div>
          <div class="item risks">
            <label for="">五险一金</label><input v-model="risks" type="number" placeholder="请输入五险一金 (元)"> 元
          </div>
        </div>
        <button @click="reckon">立即计算</button>
      </div>
    
    <div class="result" v-if="result!==''">
      <b>计算结果</b>
      <div class="item" v-if="type==1">
        <span>您个人应缴纳税收</span>
        <p><b>￥</b>{{result}}<i>元</i></p>
      </div>
      <div class="item" v-if="type==0">
        <span>您的企业应缴纳税收</span>
        <p><b>￥</b>{{result.toFixed(2)}}<i>万元</i></p>
      </div>
      <div class="item">
        <span>我们可为您节税</span>
        <p>10-80<i>%</i></p>
      </div>
      <a href="javascript:void(0);" onclick="DGGkefu.open();" class="btn">立即咨询</a>
      <a href="javascript:void(0);" @click="result = ''" class="btn btn-resolve">重新计算</a>
    </div>

    <div class="chhc">
      <div class="title-catalog-00">-  税收筹划的好处  -</div>
      <ul>
        <li>
          <h3>合理省钱</h3>
          <p>所得税率从40%降低至1.5% <br>减少税收负担<br>提升到手收入</p>
        </li>

        <li>
          <h3>降低纳税额</h3>
          <p>合理合法地利用国家财政税收<br>降低应纳税额</p>
        </li>

        <li>
          <h3>降低企业成本</h3>
          <p>增值税降低40%<br>成本下降80%<br>增加可支配资金</p>
        </li>

        <li>
          <h3>享受税收政策</h3>
          <p>量身定制税收方案<br>享受更多税收优惠政策</p>
        </li>
      </ul>
    </div>
    
    <div class="fw">
      <div class="title-catalog-00">-  税收筹划服务  -</div>
      <p class="sign">好方案为您节省千千万万元</p>
      <ul>
        <li class="gr">
          <div class="title">个人税</div>
          <p>股东分红、劳务报酬等个人所得税筹划<br>通过个人税收筹划<br>免去不必要的税收支出.</p>
          <a href="javascript:void(0);" onclick="DGGkefu.open();">立即咨询</a>
        </li>
        <li class="qy">
          <div class="title">企业税</div>
          <p>企业所得税、增值税筹划<br>通过各种企业税收和发展政策<br>为企业节约成本，促进企业发展.</p>
          <a href="javascript:void(0);" onclick="DGGkefu.open();">立即咨询</a>
        </li>
      </ul>
    </div>

    <div class="flow">
      <div class="title-catalog-00">-  服务流程  -</div>
      <p class="sign">四步轻松搞定</p>
      <div class="img"><img src="../../assets/images/tax/flow.png" alt="服务流程"></div>
      <a href="javascript:void(0);" onclick="DGGkefu.open();"><img src="../../assets/images/img_button.png" alt="立即咨询"></a>
    </div>
      

    </div>
</template>

<script>
export default {
  data () {
    return {
      type: 0,
      taxType: 0,

      rate: "",
      income: "",
      invoice: "",

      result: '',

      wages: '',
      risks: ''

    }
  },
  components: {
  },
  mounted () {

  },
  methods: {
    switchFn (type) {
      this.type = type
      this.result = ""
    },
    reckon () {
      if (!this.$store.state.userInfo.userId) {
        this.$loginPop(this)
        return false
      }
      if (this.type == 0 && !this.invoice) {
        this.$popup.created({
            content: '请输入企业开票金额',
            time: 2000
        })
        return false
      }

      if (this.type == 0 && this.invoice < 0) {
        this.$popup.created({
            content: '企业开票金额不能小于0',
            time: 2000
        })
        return false
      }

      if (this.type == 0 && this.type == 1 && !this.income) {
        this.$popup.created({
            content: '请输入企业进项票金额',
            time: 2000
        })
        return false
      }
      if (this.type == 0 && this.type == 1 && this.income<0) {
        this.$popup.created({
            content: '企业进项票金额不能小于0',
            time: 2000
        })
        return false
      }

      if (this.type == 0 && !this.rate) {
        this.$popup.created({
            content: '请输入税率',
            time: 2000
        })
        return false
      }

      if (this.type == 0 && this.rate > 100 && this.rate < 0) {
        this.$popup.created({
            content: '税率不能小于0，不能超过100%',
            time: 2000
        })
        return false
      }

      if (this.type == 1 && !this.wages) {
        this.$popup.created({
            content: '请输入您的收入',
            time: 2000
        })
        return false
      }

      if (this.type == 1 && (this.wages< 0 || this.risks<0)) {
        this.$popup.created({
            content: '工资或五险一金不能小于0',
            time: 2000
        })
        return false
      }

      if (this.type == 1 && !this.risks) {
        this.$popup.created({
            content: '请输入您缴纳的五险一金',
            time: 2000
        })
        return false
      }

      if (this.type == 0) {
        if(this.taxType == 0){
          if(this.invoice>3){
            this.result = this.invoice*(this.rate/100)
          }else{
            this.result = 0
          }
        }else{
          // 销项税额=开票金额×增值税税率
          let outputTax = this.invoice*(this.rate/100);
          //进项税额 =进项票金额* 增值税税率;
          let inputTax =this.income*(this.rate/100);
          if(outputTax < inputTax){
            this.$popup.created({
                content: '开票金额不能小于进项票金额',
                time: 2000
            })
            return;
          }
          this.result = (outputTax - inputTax)
        }
      } else {
        let personTax = 0
        if(this.wages > 5000){
            personTax =this.wages - this.risks - 5000;
           if(personTax<=3000){
              personTax=personTax*0.03-0;
           }else if(personTax>3000 &&personTax<=12000){
               personTax=personTax*0.1-210;
           }else if(personTax>12000 &&personTax<=25000){
              personTax=personTax*0.2-1410;
           }else if(personTax>25000 &&personTax<=35000){
              personTax=personTax*0.25-2660;
           }else if(personTax>35000 &&personTax<=55000){
             personTax=personTax*0.3-4410;
           }else if(personTax>55000 &&personTax<=80000){
             personTax=personTax*0.35-7160;
           }else if(personTax>80000){
             personTax=personTax*0.45-15160;
           }
         }else{
            personTax = 0;
         }
         if(personTax < 0){
          personTax = 0
         }
         this.result = personTax.toFixed(2);
      }
    }
  }
}
</script>
 
<style lang="stylus" scoped>
@import '../../assets/css/params.styl'
.switch
  width 240px
  height 43px
  line-height 43px
  border-radius 43px
  border 3px solid #ccc
  margin 0 auto
  background #1c8bee
  position relative
  &.selected:after
    left 50%
  &:after
    position absolute
    height 43px
    content ''
    width 50%
    background #fff
    border-radius 43px
    left 0
    top 0
    transition all .5s
  a
    position relative
    z-index 3
    float left
    width 50%
    color #fff
    text-align center
    font-size 16px
    &.atthis
      color #333
.pagetax
  overflow hidden
  min-width 1200px
  width 100%
.banner
  width 1920px
  position relative
  left 50%
  margin-left -960px
  height 420px
  img
    height 100%
  >.title
    width 1035px
    position absolute
    left 50%
    margin-left -517.5px
    top 130px
    text-align center
    color #fff
    h1
      font-size 48px
      margin-bottom 30px
      font-weight 100
    p
    font-size 24px
    line-height 36px
.jsq
  position relative
  z-index 9
  width 1200px
  margin 0 auto
  box-sizing border-box
  padding 35px 65px
  margin-top -40px
  box-shadow 0 0 15px 3px rgba(238, 238, 238, .3) 
  background #fff
  .title
    font-size 16px
    font-weight bold
    margin-top 15px
    line-height 30px
    margin-bottom 10px
  .info
    overflow hidden
    height 110px
    &.pl150
      padding-left 150px
    .item
      float left
      width 280px
      line-height 40px
      height 40px
      color #666
      &.wages,&.risks
        width 340px
        input
          width 200px
      &.taxType
        width 230px
        label
          margin-right 10px
        select
          width 130px
          height 40px
          line-height 40px
          border 1px solid #eee 
          color #666
          text-indent 10px
      input
        width 120px
        height 40px
        border 1px solid #eee 
        color #666
        margin-left 10px
        text-indent 10px
  button
    bottom -40px
    width 188px
    height 109px
    text-align center
    line-height 100px
    color #fff
    font-size 20px
    font-weight bold
    cursor pointer
    position absolute
    left 50%
    margin-left -94px
    background transparent url('../../assets/images/tax/bg_button.png') left top no-repeat
.chhc
  padding-top 60px
  padding-bottom 40px
  ul
    overflow hidden
    width 1200px
    margin 0 auto
    li
      float left
      margin 10px
      width 280px
      height 200px
      text-align center
      box-sizing border-box
      background #fff url('../../assets/images/tax/bg_tax_list.png') -4px bottom no-repeat
      h3
        font-size 24px
        font-weight normal
        margin-top 20px
        position relative
        line-height 60px
        &:after
          content ''
          width 45px
          height 3px
          background #333
          position absolute
          left 50%
          margin-left -22.5px
          bottom 0
      p
        font-size 14px
        line-height 24px
        color #666
        margin-top 10px
.sign
  text-align center
  font-size 14px
  color #999
  margin-top -20px
.fw
  background #fff
  ul
    width 1040px
    margin 0 auto
    overflow hidden
    padding 45px 0
    li
      width 500px
      height 240px
      box-sizing border-box
      float left
      margin 0 10px
      background #fff url('../../assets/images/tax/gr.jpg') left top no-repeat
      .title
        font-size 24px
        font-weight normal
        line-height 50px
        padding-top 10px
        padding-left 228px
      p
        padding-left 228px
        line-height 30px
        font-size 14px
        margin-top 5px
      a
        display block
        width 150px
        height 40px
        line-height 40px
        text-align center
        color #fff
        margin-left 228px
        margin-top 15px
        background $maincolor
        border-radius 3px
        box-shadow 0 0 15px 5px rgba(28, 139, 238, .2)
    li.qy
      background #fff url('../../assets/images/tax/qy.jpg') left top no-repeat
.flow
  text-align center
  background #fff
  padding-bottom 70px
  .img
    padding 40px 0 50px
.result
  text-align center
  padding 150px 0 0 0
  overflow hidden
  background transparent url('../../assets/images/tax/icon_arr.png') center 80px no-repeat
  >b
    font-size 24px
    font-weight bold
    margin-right 25px
    vertical-align middle
  .item
    display inline-block
    width 240px
    height 150px
    background #fff
    margin-right 10px
    vertical-align middle
    span
      padding-top: 13px;
      display: inline-block;
      font-size 16px
      margin-top 30px
    p
      font-size 28px
      color #2bc1f2
      font-weight bold
  .btn
    vertical-align middle
    display inline-block
    width 150px
    margin-left 15px
    &.btn-resolve
      background #fff
      border 1px solid #1c8bee
      color #1c8bee
</style>
