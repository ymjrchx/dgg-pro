<template>
    <div>
      <div class="pageCompany" v-if="!result">
        <div class="banner">
          <img src="../../assets/images/company/banner.jpg" alt="公司注册">
            <div class="boxDiv"> </div>
            <div class="search" @mouseleave="close">
                <ul>
                    <li class="address" @click="showFix('isArea')">{{provinceIndex > -1 ? area[provinceIndex].child[cityIndex].name.substr(0, 3): '全国'}}</li>
                    <li class="input"><input @keyup="replaceCn" v-model="name" minlength="2" type="text" placeholder="请输入公司简称（或关键字）" maxlength="10"></li>
                    <li class="industry" :class="{'atthis': industry}" @click="showFix('isIndustry')">{{industry?industry:'所属行业'}}</li>
                    <li class="type" @click="showFix('isType')">{{type}}</li>
                </ul>
                <button @click="search">立即查询</button>
                <div class="addressArea" v-show="isArea">
                  <div class="province">
                    <div class="title">省份选择</div>
                    <ul>
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
                <div class="industryArea" v-if="isIndustry">
                  <ul>
                    <li>
                      <dl>
                        <dt>所有行业</dt>
                        <dd>
                          <a v-for="(item, v) in industryList" :key="v" @click="seletedIndustry(v)" :class="{'atthis': industry== v}" href="javascript:void(0);">{{v}}</a>
                        </dd>
                      </dl>
                    </li>
                    
                  </ul>
                </div>
                <div class="types" v-if="isType">
                  <ul>
                    <li v-if="0" @click="seletedType('股份有限公司')">股份有限公司</li>
                    <li v-if="0" @click="seletedType('有限责任公司')">有限责任公司</li>
                    <li @click="seletedType('有限公司')">有限公司</li>
                    <li @click="seletedType('合伙公司')">合伙公司</li>
                  </ul>
                </div>
            </div>
            
          <div class="tips">例：成都小布科技有限公司------城市区域：成都; 公司名称：小布科技&nbsp;&nbsp;&nbsp;&nbsp;所属行业：科技&nbsp;&nbsp;&nbsp;&nbsp;企业类型：有限公司</div>
        </div>

        <div class="zc">
          <div class="wrapper">
            <div class="title-catalog-00">-  为什么要注册公司?  -</div>
            <ul>
              <li>
                <img src="../../assets/images/company/img_zc_00.jpg" alt="经营主体合法化">
                <h2 class="name">经营主体合法化</h2>
              </li>
              <li>
                <img src="../../assets/images/company/img_zc_01.jpg" alt="承担有限法律责任">
                <h2 class="name">承担有限法律责任</h2>
              </li>
              <li>
                <img src="../../assets/images/company/img_zc_02.jpg" alt="可建立企业品牌">
                <h2 class="name">可建立企业品牌</h2>
              </li>
              <li>
                <img src="../../assets/images/company/img_zc_03.jpg" alt="便于融资和信贷">
                <h2 class="name">便于融资和信贷</h2>
              </li>
              <li>
                <img src="../../assets/images/company/img_zc_04.jpg" alt="产品推广和服务">
                <h2 class="name">产品推广和服务</h2>
              </li>
              <li>
                <img src="../../assets/images/company/img_zc_05.jpg" alt="购买社保">
                <h2 class="name">购买社保</h2>
              </li>
            </ul>
          </div>
        </div>

        <div class="flow wrapper">
            <div class="title-catalog-00">-  公司注册流程  -</div>
            <ul>
              <li>
                <i><img src="../../assets/images/company/icon_01.png" alt="01"></i>
                <h3 class="name">核准名称</h3>
                <p>由工商局进行重名核实没有重名，则可以使用选用名称。</p>
              </li>
              <li class="arr-right"><img src="../../assets/images/company/icon_arr_right.png" alt="arr"></li>
              <li>
                <i><img src="../../assets/images/company/icon_02.png" alt="02"></i>
                <h3 class="name">提交材料</h3>
                <p>按照预约时间去工商局提交相应材料。</p>
                <p class="tips">温馨提示：请确保所提交材料准确无误，避免造成后续不必要的麻烦！</p>
              </li>
              <li class="arr-right"><img src="../../assets/images/company/icon_arr_right.png" alt="arr"></li>
              <li>
                <i><img src="../../assets/images/company/icon_03.png" alt="03"></i>
                <h3 class="name">领取执照</h3>
                <p>材料审核通过后，携带准予设立登记通知书、办理人身份证原件，到工商局领取营业执照正、副本。</p>
                
              </li>
              <li class="arr-right"><img src="../../assets/images/company/icon_arr_right.png" alt="arr"></li>
              <li>
                <i><img src="../../assets/images/company/icon_04.png" alt="04"></i>
                <h3 class="name">刻章备案</h3>
                <p>凭营业执照，到指定部门办理：公司公章财务章、合同章、法人代表章、发票章。</p>
              </li>
            </ul>

            <a href="javascript:void(0);" onclick="DGGkefu.open();" class="btn">免费咨询</a>
        </div>
        
        <div class="cl">
          <div class="wrapper">
            <div class="title-catalog-00">-  注册所需材料  -</div>

            <div class="left item">
              <h4>新注册公司必备资料</h4>
              <ul>
                <li><i>1</i>草拟公司名字3-5个</li>
                <li><i>2</i>法人身份证原件</li>
                <li><i>3</i>全体自然人股东身份证明原件</li>
                <li><i>4</i>法人股东营业执照复印件加盖印章</li>
                <li><i>5</i>注册地址产权证复印件</li>
                <li><i>6</i>地址所有人身份信息复印件</li>
                <li><i>7</i>注册地租房协议书</li>
              </ul>
              <a href="javascript:void(0);" onclick="DGGkefu.open();" class="btn">免费咨询</a>
            </div>
            <div class="right item">
              <h4>分公司注册必备资料</h4>
              <ul>
                <li><i>1</i>总公司营业执照正本、副本复印件</li>
                <li><i>2</i>总公司税务登证正本、副本复印件</li>
                <li><i>3</i>总公司章程复印件</li>
                <li><i>4</i>总公司开户许可证复印件</li>
                <li><i>5</i>总公司对分公司负责人的任命书</li>
                <li><i>6</i>分公司申请书，需法人代表签字总公司盖章</li>
                <li><i>7</i>分公司负责人身份证原件及复印件</li>
                <li><i>8</i>总公司法定代表人身份证复印件</li>
                <li><i>9</i>分公司注册地址产权证复印件</li>
                <li><i>10</i>分公司注册地址租房协议及情况说明</li>
              </ul>
              <a href="javascript:void(0);" onclick="DGGkefu.open();" class="btn">免费咨询</a>
            </div>
          </div>
        </div>
      </div>
      <div class="pageCompanyResult" v-if="result">
        <div class="banner">
          <img v-if="result.possibility == 'H'" src="../../assets/images/company/banner_height.jpg" alt="高">
          <img v-if="result.possibility == 'M'" src="../../assets/images/company/banner_middle.jpg" alt="中">
          <img v-if="result.possibility == 'L'" src="../../assets/images/company/banner_low.jpg" alt="低">
          <div class="info">
              <h1>“{{this.provinceIndex>-1?this.area[this.provinceIndex].child[this.cityIndex].name:''}}{{name}}{{industry!='不限行业'?industry:''}}{{type}}”</h1>
              <p>注册通过率</p>
              <div class="line">
                <i v-if="result.possibility == 'H'" style="width: 95%"></i>
                <i v-if="result.possibility == 'M'" style="width: 65%"></i>
                <i v-if="result.possibility == 'L'" style="width: 15%"></i>
              </div>
              <ul>
                <li :class="{'atthis': result.possibility == 'L'}">低</li>
                <li :class="{'atthis': result.possibility == 'M'}" class="middle">中</li>
                <li :class="{'atthis': result.possibility == 'H'}">高</li>
              </ul>
          </div>
          <a href="javascript:void(0);" onclick="DGGkefu.open();" class="btn">免 费 咨 询</a>
        </div>

        <div class="result wrapper">
          <h3 class="title">相似公司分析</h3>
          <ul>
            <li v-for="item in result.names" :key="item">
              <div class="right">{{percentage(item)}}%</div>
              <span>检索到类似的公司相似度</span>
              <h4 class="name" v-html="item"></h4>
            </li>
          </ul>
        </div>

        <a href="#top" @click="result=''" class="btn btn-resolve">重新查询</a>
      </div>
    </div>
</template>

<script>
const area = require('@/assets/js/area.json')
export default {
  data () {
    return {
      result: '',
      area: area.area_json,
      provinceIndex: 22,
      cityIndex: 0,
      isArea: false,
      isIndustry: false,
      industry: '请选择行业',
      industryList: {
          '电子信息': '1',
          '化工': '2',
          '汽车': '3',
          '机械工业': '4',
          '仪器仪表': '5',
          '发电与输变电设备': '6',
          '生物技术': '7',
          '制药': '8',
          '新材料': '9',
          '光电子企业': '10',
          '纳米技术': '11',
          '新能源': '12',
          '环保': '13',
          '食品和农产品精加工': '14',
          '印刷与包装': '15',
          '钻石珠宝': '16',
          '化妆品及洗涤用品': '17',
          '服装服饰': '18',
          '纺织': '19',
          '轻工': '20',
          '商业零售业': '21',
          '物流运输业': '22',
          '会展业': '23',
          '金融业': '24',
          '餐饮业': '25',
          '旅游业': '26',
          '房地产业': '27',
          '广告业': '28',
          '信息咨询服务业': '29'
      },

      name: '',

      isType: false,
      type: '有限公司',

    }
  },
  head() {
      return {
          title: "公司核名_注册公司_注册公司流程及费用_工商核名系统_注册公司核名_网上公司核名_智企名",
          meta: [
              {
                  name: "keywords",
                  hid: "keywords",
                  content:
                      "公司注册查询,工商核名,公司核名，公司注册核名，公司核名查询，公司网上核名，智企名"
              },
              {
                  name: "description",
                  hid: "description",
                  content:
                      "公司核名就找智企名，免费核名服务，基于企业数据名录开发的预核名查重系统，专业顾问人工查询，结果准确，注册成功率提升98%，新公司名称网上核准，免费咨询智企名，提供注册公司流程及费用咨询。"
              }
          ]
      };
  },
  async asyncData({ params,$axios }) {

  },
  components: {
  },
  mounted () {
    var _hmt = _hmt || [];
    (function() {
      var hm = document.createElement("script");
      hm.src = "https://hm.baidu.com/hm.js?3d84e9173d73c03bba3b560ad88f532c";
      var s = document.getElementsByTagName("script")[0]; 
      s.parentNode.insertBefore(hm, s);
    })();
    console.log(1)
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
    seletedType (type) {
      this.type = type
      this.isType = false
    },
    seletedIndustry (name) {
      this.industry = name
      this.isIndustry = false
    },
    close () {
      this.isArea = false
      this.isIndustry = false
      this.isType = false
    },
    showFix (type) {
      if (type == 'isArea') {
        this.isArea = !this.isArea
        this.isIndustry = false
        this.isType = false
      } else if (type == 'isIndustry') {
        this.isArea = false
        this.isIndustry = !this.isIndustry
        this.isType = false
      } else if (type == 'isType') {
        this.isArea = false
        this.isIndustry = false
        this.isType = !this.isType
      }
    },
    percentage(str) {
     let reg=/<i>|<\/i>/g
     let num=str.split("</i>").length-1,
  
         num2= str.replace(reg,"").length;

         let per =Math.round(num/num2*100); 
         
         if (per > 95) per = 95

      return per || 12
    },
    replaceCn (e) {
      this.name = e.target.value.replace(/[^\u4e00-\u9fa5]/g, '')
    },
    search () {
      let city = this.provinceIndex > -1 ? this.area[this.provinceIndex].child[this.cityIndex].name: '全国'
      if (!this.$store.state.userInfo.userId) {
        this.$loginPop(this)
        return false
      }

      if (!this.name) {
        this.$popup.created({
            content: '公司名称不能为空',
            time: 2000
        })
        return false
      }
      var reg = /^[\u4e00-\u9fa5]+$/;
      if (!reg.test(this.name)) {
       
          this.$popup.created({
              content: '公司名称只能使用简体中文',
              time: 2000
          })
          return;
      }
      if (this.industry == '请选择行业') {
        this.$popup.created({
            content: '必须选择行业',
            time: 2000
        })
        return false
      }
      this.$axios.$get(encodeURI(`/company/checkCompany?city=${city}&name=${this.name}&industry=${this.industry}&companyType=${this.type}&userId=${this.$store.state.userInfo.userId}`)).then((res) => {
        this.result = res.data
      }).catch(res=>{})

      // this.$axios.$get(`/company/checkCompany`, {params: {city: city, name: this.name, industry: this.industry, companyType: this.type, userId: this.$store.state.userInfo.userId}).then((res) => {
      //   this.result = res.data
      // })
    }
  },
  computed:{
    isLogin(){
      return this.$store.state.userInfo ? true : false
    }
  },
  watch:{
    isLogin(bool){
      if(bool && this.name && this.industry) this.search()
    }
  }
}
</script>
 
<style lang="stylus" scoped>
@import '../../assets/css/params.styl'

.pageCompany
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
    .tips
      width 1035px
      position absolute
      left 50%
      height 70px
      margin-left -517.5px
      top 380px
      z-index 1
      text-align center
      color #fff
      font-size 14px
    .boxDiv
      width 1045px
      position absolute
      left 50%
      height 80px
      margin-left -522.5px
      top 275px
      z-index 1
      border-radius 4px;
      background-color rgba(0,0,0,.5)  
    .search
      width 1035px
      position absolute
      left 50%
      height 70px
      margin-left -517.5px
      top 280px
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
            width 268px
            input
              padding-left 20px
              font-size 16px
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
        width 180px
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
      .industryArea
        background #fff
        padding 20px
        clear both
        border 1px solid #ccc
        position relative
        &:after
          content ''
          position absolute
          width 18px
          height 12px
          background transparent url('../../assets/images/company/icon_arr.jpg') left top no-repeat
          top -12px
          left 486px
        li
          dl
            overflow hidden
            font-size 14px
            line-height 30px
            padding-top 10px
            dt
              float left
              width 70px
              text-align center
              background #d6e5f2
              border-radius 3px
              color $maincolor
            dd
              overflow hidden
              margin-left 85px
              padding 0 0 10px
              border-bottom 1px solid $solidcolor
              a
                display inline-block
                padding 0 6px
                margin-right 30px
                &:hover,&.atthis
                  background $maincolor
                  border-radius 3px
                  color #fff
      .types
        background #fff
        padding 0 0 10px 0
        width 220px
        margin-left 635px
        box-sizing border-box
        font-size 14px
        line-height 30px
        overflow hidden
        li
          padding-left 20px
          &:hover,&.atthis
            background $maincolor
            color #fff
            height 30px
            cursor pointer

  .zc
    background #fff
    padding-bottom 60px
    ul
      overflow hidden
      padding-left 105px
      li
        float left
        width 320px
        height 180px  
        margin 15px 15px 0 0
        position relative
        text-align center
        overflow hidden
        box-shadow 0 0 5px 5px rgba(204, 204, 204, .3)
        img
          transition all .5s
        &:hover
          img
            transform scale(1.1)
        .name
          position absolute
          display block
          left 50%
          margin-left -100px
          top 65px
          width 198px
          height 48px
          line-height 48px
          border 1px solid #fff
          background rgba(255, 255, 255, .2)
          font-size 16px
          color #fff
          font-weight normal
  .flow
    padding-bottom 60px
    ul
      overflow hidden
      padding-top 70px
      padding-bottom 45px
      li
        float left
        width 220px
        height 280px
        background #fff
        text-align center
        padding 0 20px
        box-sizing border-box
        i
          display block
          width 80px
          height 80px
          line-height 80px
          margin -40px auto 0
          background #2bc1f2
          border-radius 50%
        &.arr-right
          width 105px
          background transparent
          line-height 280px
        .name
          font-size 18px
          line-height 50px
          position relative
          margin-bottom 20px
          &:after
            content ''
            width 40px
            height 3px
            background #333
            position absolute
            left 50%
            margin-left -20px
            bottom 0
        p
          font-size 14px
          line-height 24px
          color #999
          text-align left
          &.tips
            color #67abe7
  .cl
    background #fff
    padding-bottom 60px
    overflow hidden
    .item
      width 450px
      height 565px
      margin-top 10px
      box-sizing border-box
      text-align center
      padding 30px 0
      position relative
      box-shadow 0 0 15px 5px rgba(204, 204, 204, .3)
      i
        display inline-block
        width 20px
        height 20px
        border-radius 50%
        background #999
        color #fff
        font-size 14px
        line-height 20px
        text-align center
        margin-right 5px
      .btn
        position absolute
        bottom 30px
        left 50%
        margin-left -110px
      h4
        font-size 18px
        width 300px
        height 50px
        line-height 50px
        border-radius 50px
        background #2bc1f2
        color #fff
        margin 0 auto 15px
      ul
        padding-left 85px
        text-align left
        line-height 36px
        font-size 16px
      &.left
        margin-left 125px
      &.right
        margin-right 125px

.pageCompanyResult
  background #fff
  overflow hidden
  padding-bottom 50px
  .banner
    width 1920px
    position relative
    left 50%
    height 358px
    margin-left -960px
    img
      position absolute
      width 100%
      height 100%
    .info
      position absolute
      width 800px
      left 50%
      margin-left -400px
      z-index 2
      top 80px
      h1
        font-size 30px
        line-height 30px
        color #fff
        text-align center
        font-weight normal
      p
        text-align center
        font-size 16px
        line-height 40px
        color #fff
        margin-top 20px
      .line
        margin-top 10px
        width 100%
        height 10px
        background rgba(255, 255, 255, .23)
        border-radius 10px
        overflow hidden
        i
          float left
          height 10px
          border-radius 10px
          background #fff
      ul
        overflow hidden
        padding-top 30px
        padding-left 50px
        li
          float left
          width 60px
          height 60px
          border-radius 50%
          background rgba(255, 255, 255, .23)
          text-align center
          line-height 60px
          color #fff
          position relative
          &.middle
            margin 0 257px
          &:after
            content ''
            height 30px
            width 0
            border-right 1px dashed #fff
            position absolute
            top -30px
            left 50%
          &.atthis
            color #333
            background #fff
    .btn
      position absolute
      left 50%
      margin-left -110px
      bottom -25px
      z-index 2
  .result
    padding 50px 0
    width 455px
    .title
      font-size 18px
      font-weight bold
    ul
      padding-top 10px
      li
        padding 20px 0 10px
        overflow hidden
        position relative
        border-bottom 1px solid $solidcolor
        &:after
          position absolute
          left 0
          right 0
          top 31px
          display block
          content ''
          width 100%
          height 0
          border-bottom 1px dashed $maincolor
          z-index 1
        span
          position relative
          display block
          color $maincolor
          float left
          background #fff
          padding-right 10px
          z-index 2
        .right
          font-size 30px
          font-weight bold
          color $maincolor
          background #fff
          padding-left 10px
          margin-top -9px
          background #fff
          position relative
          z-index 2
        .name
          clear both
          font-size 16px
          line-height 30px
          font-weight normal

.btn-resolve
  background #fff
  color $maincolor
  border 1px solid $maincolor

</style>
