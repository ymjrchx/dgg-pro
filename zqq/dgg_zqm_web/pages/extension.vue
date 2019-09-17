<template>
    <div>
      <div class="pageCompany" v-if="!result">
        <div class="banner">
            <img src="../assets/images/extension/big.png" alt="公司注册">
            <p class="tips">2018年客户对我们的认可是<span>13亿</span>营业额！</p>
            <div class="search">
                <div class="item">
                    <div>
                        2008年5人创业到<br>2019年公司6000余人
                    </div>
                </div>
                <div class="item">
                    <div>
                        公司自营实体<br>分布全国各地13市
                    </div>
                </div>
                <div class="item last">
                    <div>
                        10年30余万客户<br>信任与选择
                    </div>
                </div>
            </div>
            <p class="tips tips2"><img src="../assets/images/extension/tel.png" alt="" srcset=""> 服务与保障电话  400-0962-540</p>
        </div>

        <div class="zc">
          <div class="wrapper">
            <div class="title-catalog-00">-  为什么要注册公司?  -</div>
            <ul>
             
            </ul>
          </div>
        </div>

        <div class="flow wrapper">
            <div class="title-catalog-00">-  公司注册流程  -</div>
            <ul>
            
            </ul>

            <a href="javascript:void(0);" onclick="DGGkefu.open();" class="btn">免费咨询</a>
        </div>
        
        <div class="cl">
          <div class="wrapper">
            <div class="title-catalog-00">-  注册所需材料  -</div>

            <div class="left item">
             
            </div>
            <div class="right item">
             
            </div>
          </div>
        </div>
      </div>
      <div class="pageCompanyResult" v-if="result">
        <div class="banner">
         
        </div>

        <div class="result wrapper">
          <h3 class="title">相似公司分析</h3>
          
        </div>

        <a href="#top" @click="result=''" class="btn btn-resolve">重新查询</a>
      </div>
    </div>
</template>

<script>
const area = require('@/assets/js/area.json')
export default {
    layout:"empt",
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
@import '../assets/css/params.styl'
*{
    box-sizing border-box
}
.pageCompany
  overflow hidden
  min-width 1200px
  width 100%
  .banner
    width 1920px
    position relative
    left 50%
    margin-left -960px
    height 620px
    img
      height 100% 
    .tips
      width 1000px
      position absolute
      left 50%
      margin-left -500px
      top 270px
      z-index 1
      text-align center
      color #fff
      font-size 32px
      letter-spacing 2px;
      span
        color #ff4311
      &.tips2
        font-size 24px 
        top 550px    
        letter-spacing 0px;
    .search
      width 700px
      position absolute
      left 50%
      margin-left -375px
      top 340px
      z-index 2
      .item
        width 218px
        margin-right 15px
        padding 4px
        position relative  
        height 116px
        float left
        &::before
          content  ' '
          position absolute
          left 0
          top 0
          height 100%
          width 14px
          border 1px solid #ff4311
          border-right none
        &::after
          content  ' '
          position absolute
          right 0
          top 0
          height 100%
          width 14px
          border 1px solid #ff4311
          border-left none
        div
          height 100%
          width 100%
          background rgba(255,67,17,.2)
          color #ff4311
          line-height 30px
          font-size 18px
          text-align center
          padding-top 24px
</style>
