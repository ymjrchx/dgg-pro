<template>
    <div class="pageCreateName">
      <div class="banner">
        <img src="../../assets/images/brandSearch/banner.jpg" alt="公司注册">
        <div class="title">
            <h1>公司想不出好名字， 智企名为您推荐</h1>
            <p>独特而又响亮的名字，已为500多万家公司推荐名字</p>
        </div>
        <div class="boxDiv"> </div>
        <div class="search"  @mouseleave="close" ref='list'>
          <ul>
            <li class="address" @click="showFix('isArea')">{{provinceIndex > -1 ? area[provinceIndex].child[cityIndex].name.substr(0, 3): '全国'}}</li>
            <li class="industry" :class="{'atthis': industry}" @click="showFix('isIndustry')">{{industry?industry:'请选择行业，如旅游'}}</li>
          </ul>
          <button @click="search">推荐名字</button>

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

        </div>  
      </div>
      <div class="tips" v-show="!resultAll">
        <div class="title">公司起名注意事项</div>
         <ul>
          <li>
            <div class="name">不能重名</div>
            <p>相同或相似行业存在重名公司，不能注册。如，“直招网络科技”与“宜招信息科技”算重名。</p> 
          </li>
          <li class="item01">
            <div class="name">不能触犯驰名商标</div>
            <p>如：“万达”是驰名商标，不能注册为公司名字</p> 
          </li>

          <li  class="item02">
            <div class="name">不能与知名公司名字混淆</div>
            <p>如“新微软”“微软之家”等不能注册为公司名字</p> 
          </li>

          <li  class="item03">
            <div class="name">尽量不用地区名称及简称</div>
            <p>如：上海，兰州，泸等不能注册为公司名字。</p> 
          </li>
          <li  class="item04">
            <div class="name">不能使用繁体、数字、英文</div>
            <p>公司名称只能使用简体中文。</p> 
          </li>

          <li  class="item05">
            <div class="name">不能使用行业通用词汇</div>
            <p>如：“上海电脑科技有限公司”不能注册为公司名字。</p> 
          </li>

          <li  class="item06">
            <div class="name">不能使用名人字号</div>
            <p>如：“马云”，“王健林”等不能注册为公司名字。</p> 
          </li>

          <li  class="item07">
            <div class="name">不能带有宗教色彩</div>
            <p>如：“伊斯兰”等不能注册为公司名字。</p> 
          </li>
        </ul>
      </div>
      <section class="listBox" v-if="results">
            <h2>推荐结果</h2>
            <table cellpadding="0" cellspacing="1" width="100%">
              <thead>
                <tr>
                  <th width="120">推荐</th>
                  <th>公司名称</th>
                </tr>
              </thead>
              <tbody v-if='isLogin'>
                <tr v-for="(item, index) in results" :key="item">
                  <td>推荐{{index+1}}</td>
                  <td class="name">{{cityName}}{{item}}有限公司</td>
                </tr>
              </tbody>
              <tbody v-else>
                <tr v-for="(item) in [1]" :key="item">
                  <td>推荐1</td>
                  <td class="name">{{cityName}}{{results[0]}}</td>
                </tr>
                <tr>
                  <td colspan="2" style="padding:0;position:relative">
                    <img src="../../assets/images/createName/mo.png" width="100%">
                    <loginBox></loginBox>
                  </td>
                </tr>
              </tbody>
            </table>

            <a href="javascript:void(0)"  v-if='isLogin'  @click="switchName" class="btn-00">换一批</a>
      </section> 
      <toolBox />
    </div>
</template>

<script>
import toolBox from '@/components/toolBanner'
const area = require('@/assets/js/area.json')
import loginBox from '@/components/loginBox'

export default {
  data () {
    return {
      show:true,
      results: "",
      resultAll: "",
      resIndex: 1,
      area: area.area_json,
      isIndustry: false,
      industry: '',
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
      provinceIndex: 22,
      cityIndex: 0,
      isArea: false,
    }
  },
  mounted () {
    var _hmt = _hmt || [];
    (function() {
      var hm = document.createElement("script");
      hm.src = "https://hm.baidu.com/hm.js?0217d877202afb61caa00182800ce13a";
      var s = document.getElementsByTagName("script")[0]; 
      s.parentNode.insertBefore(hm, s);
    })();
  },
  components: {
    toolBox,loginBox
  },
  computed:{
    isLogin(){
      return this.$store.state.userInfo ? true : false
    },
    cityName(){
      return  this.provinceIndex > -1 ? this.area[this.provinceIndex].child[this.cityIndex].name.replace('地区', '').replace('省', '').replace('市', ''): ''
    } 
  },
  watch:{
    isLogin(bool){
      if(bool && this.industry) this.search()
    }
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
    seletedIndustry (name) {
      this.industry = name
      this.isIndustry = false
    },
    close () {
      this.isIndustry = false
      this.isArea = false
    },
    showFix (type) {
      // this[type] = !this[type]
       if (type == 'isArea') {
        this.isArea = !this.isArea
        this.isIndustry = false
        this.isType = false
      } else if (type == 'isIndustry') {
        this.isArea = false
        this.isIndustry = !this.isIndustry
        this.isType = false
      }
    },
    switchName () {
      this.results = this.resultAll.slice((0+this.resIndex*10), 10*(this.resIndex+1))
      if (this.results.length<10) {
        this.resIndex = 0
        this.search()
        // this.results = this.resultAll.slice((0+this.resIndex*10), 10*(this.resIndex+1))
      }
      this.resIndex++
    },
    search () {
      // if (!this.$store.state.userInfo.userId) {
      //   this.$loginPop(this)
      //   return false
      // }
      if (!this.industry) {
        this.$popup.created({
            content: '请选择行业',
            time: 2000
        })
        return false
      }
      this.$axios.$post(encodeURI('/getCompanyName/getCompanyName?industry='+this.industryList[this.industry]))
      .then((res) => {
        this.resultAll = res.data.data;
        this.results = res.data.data.slice(0,10)
        this.$Jump(this.$refs.list,80)
        if (!this.$store.state.userInfo.userId) {
          this.$loginPop(this)
        }
      })
    }
  }
}
</script>
 
<style lang="stylus" scoped>
@import '../../assets/css/params.styl'
   *{
       box-sizing border-box;
   }   
.pageCreateName
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
    p
    font-size 24px
  .boxDiv
    width 860px
    position absolute
    left 50%
    height 80px
    margin-left -430px
    top 315px
    z-index 1
    border-radius 4px;
    background-color rgba(0,0,0,.5)    
  .search
    width 850px
    position absolute
    left 50%
    height 70px
    margin-left -425px
    top 320px
    z-index 2
    background #fff
    >ul
      height 70px
      line-height 70px
      padding 20px 0
      float left
      >li{
        width 520px;
        float left
        line-height: 30px;
        padding-left 15px
        font-size: 16px;
        cursor: pointer;
        color #666
        box-sizing border-box
        padding-right 20px
        &.address{
          width 150px
          text-align center
          position relative
          background transparent url('../../assets/images/icon_address.png') 24px center no-repeat;
          &:after{
            position absolute
            right 28px
            top 13px
            content ''
            width 0
            height 0
            border 6px solid transparent
            border-top-color #ccc
            }
          }
        &.atthis{
          color #333
        }
        input{
            padding-left: 20px;
            font-size: 16px;
            width: 100%;
            color: #333;
        }
      }
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
        left 56px
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
.listBox
  padding-top 60px;
  text-align center;
  width 620px;
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
      padding 22.5px 0
      &.name
        background: #f5f5f5;
      &.rate
        font-size 30px
        color #1c8bee
      
.tips
  width 960px
  margin 0 auto
  overflow hidden
  .title
    font-size 30px
    font-weight normal
    line-height 120px
    text-align center
  li
    float left
    width 460px   
    height 140px
    box-sizing border-box
    padding 30px
    background #fff
    border 1px solid #eee
    margin 10px
    position relative
    .name
      width 280px
      height 50px
      border-radius 50px
      position absolute
      left 50%
      margin-left -140px
      top - 25px
      background #00a9ef
      color #fff
      line-height 50px
      text-align center
    p
      margin-top 20px
    &.item01
      .name
        background #f6b720
    &.item02
      .name
        background #f38a51
    &.item03
      .name
        background #31b998
    &.item04
      .name
        background #34b5e5
    &.item05
      .name
        background #f69b26
    &.item06
      .name
        background #35ba99
    &.item07
      .name
        background #8c9af2
    
</style>
