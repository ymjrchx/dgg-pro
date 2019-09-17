<template>
    <div class="pageGenerator">
      <div class="banner">
        <img src="../../assets/images/generator/banner.jpg" alt="经营范围生成器">
        <div class="title">
            <h1>经营范围生成器</h1>
            <p>生成相关行业的经营范围</p>
        </div>
        <div class="boxDiv"> </div>
        <div class="search" @mouseleave="close" ref='list'>
          <ul>
            <li class="address" @click="showFix('isArea')">{{provinceIndex > -1 ? area[provinceIndex].child[cityIndex].name.substr(0, 3): '全国'}}</li>
            <li class="industry" :class="{'atthis': industry}" @click="showFix('isIndustry')">{{industry?industry:'请选择行业，如旅游'}}</li>
          </ul>
          <button @click="search">立即生成</button>

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
                  <dl  v-for="item in industryList" :key="item.name">
                    <dt>{{item.name}}</dt>
                    <dd>
                      <a v-for="childItem in item.list" :key="childItem" @click="seletedIndustry(childItem)" :class="{'atthis': industry== childItem}" href="javascript:void(0);">{{childItem}}</a>
                    </dd>
                  </dl>
                </li>
                
              </ul>
            </div>

        </div>  
      </div>
      <section class="listBox" v-if="resultInfo">
            <h2>生成器查询结果</h2>
            <table cellpadding="0" cellspacing="1" width="100%">
              <thead>
                 <tr>
                  <th width="120">推荐</th>
                  <th width="300">公司名字</th>
                  <th width="200">行业类别</th>
                  <th>经营范围</th>
                </tr>
              </thead>
              <tbody v-if='isLogin'>  
                <tr  v-for="(item, index) in resultInfo.listData" :key="item.companyId">
                  <td>推荐{{index+1}}</td>
                  <td class="name">{{item.name}}</td>
                  <td>{{item.qygsDetail.industry}}</td>
                  <td class="name" align="left">{{item.qygsDetail.scope}}</td>
                </tr>
              </tbody>
              <tbody v-else>  
                <tr  v-for="item in [1]" :key="item">
                  <td>推荐1</td>
                  <td class="name">{{resultInfo.listData[0].name}}</td>
                  <td>{{resultInfo.listData[0].qygsDetail.industry}}</td>
                  <td class="name" align="left">{{resultInfo.listData[0].qygsDetail.scope}}</td>
                </tr>
                <tr >
                  <td colspan="4" style="padding:0;position:relative;">
                    <img src="../../assets/images/generator/mo.png" alt="经营范围" width="100%">
                    <loginBox></loginBox>
                  </td>
                </tr>
              </tbody>
            </table>
           
            <a href="javascript:void(0)" v-if='isLogin' @click="switchName" class="btn-00">换一批</a>
      </section> 
      <toolBox/>
    </div>
</template>

<script>
const area = require('@/assets/js/area.json')
import toolBox from '@/components/toolBanner'
import loginBox from '@/components/loginBox'
export default {
  data () {
    return {
      show:true,
      area: area.area_json,
      provinceIndex: 22,
      cityIndex: 0,
      isArea: false,

      resultInfo: "",
      isIndustry: false,
      industry: '',
      industryList: [
            {
                name: "科技类",
                list: [
                    "网络科技",
                    "电子商务",
                    "信息技术",
                    "游戏",
                    "电子",
                    "软件",
                    "新材料",
                    "生物科技",
                    "教育科技"
                ]
            },
            {
                name: "许可类",
                list: [
                    "投资管理",
                    "金融",
                    "资产",
                    "商业保理",
                    "融资租赁",
                    "医疗器械",
                    "人力资源",
                    "食品",
                    "劳务派遣"
                ]
            },
            {
                name: "服务类",
                list: [
                    "广告",
                    "文化传播",
                    "建筑装潢",
                    "设计",
                    "美容美发",
                    "房地产中介",
                    "物业管理",
                    "商务咨询",
                    "企业管理"
                ]
            },
            {
                name: "其 他",
                list: [
                    "贸易",
                    "实业",
                    "制造",
                    "服饰",
                    "化妆品",
                    "工程",
                    "农业",
                    "餐饮管理",
                    "物流"
                ]
            }
        ],
      pageIndex: 1,
      // isLogin:false
    }
  },
  components: {
    toolBox,loginBox
  },
  computed:{
    isLogin(){
      return this.$store.state.userInfo ? true : false
    }
  },
  watch:{
    isLogin(bool){
      if(bool && this.industry) this.search()
    }
  },
  mounted () {
    var _hmt = _hmt || [];
    (function() {
      var hm = document.createElement("script");
      hm.src = "https://hm.baidu.com/hm.js?7d609e88c56620ebcc0e27120cd5f600";
      var s = document.getElementsByTagName("script")[0]; 
      s.parentNode.insertBefore(hm, s);
    })();
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
      if (type == 'isArea') {
        this.isArea = !this.isArea
        this.isIndustry = false
      } else {
        this.isArea = false
        this[type] = !this[type]
      }
    },
    seletedIndustry (name) {
      this.industry = name
      this.isIndustry = false
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
      this.$axios.$post('/api/resourcePro',{
        city: this.provinceIndex > -1 ? this.area[this.provinceIndex].child[this.cityIndex].name: null,
        industry: this.industry,
        pageIndex: this.pageIndex,
        pageSize: 5})
      .then((res) => {
        this.resultInfo = res.data;
        this.$Jump(this.$refs.list,80)
        if (!this.$store.state.userInfo.userId) {
          this.$loginPop(this)
        }
      })
    },
    switchName () {
      this.pageIndex++
      if (this.pageIndex > Math.ceil(parseInt(this.resultInfo.totalCount)/5)){
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
    p
    font-size 24px
  .boxDiv
    width 860px
    position absolute
    left 50%
    height 80px
    margin-left -430px
    top 335px
    z-index 1
    border-radius 4px;
    background-color rgba(0,0,0,.5)   
  .search
    width 850px
    position absolute
    left 50%
    height 70px
    margin-left -425px
    top 340px
    z-index 12
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
          width 529px
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
          width 529px 
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
        left 200px
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
              margin-right 10px
              &:hover,&.atthis
                background $maincolor
                border-radius 3px
                color #fff
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
