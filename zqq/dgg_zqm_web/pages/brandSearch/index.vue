<template>
    <div class="pageBrandSearch">
      <div class="banner">
        <img src="../../assets/images/brandSearch/banner.jpg" alt="商标查询">
        <div class="title">
            <h1>商标查询</h1>
            <p>支持全国商标查询，了解最新的商标信息以及申请进度</p>
        </div>
        <div class="search" >
          <ul>
            <li>
              <input type="text" placeholder="请输入商标，如顶呱呱" v-model="keyWord" @keyup.enter='getList'>
            </li>
          </ul>
          <button @click="getList(1)">查询</button>
        </div>  
      </div>
      <p class="hide" ref="list"></p>
      <section class="listBox" v-if="show">
            <h2>查询结果</h2>
            <div v-if='total > 0'> 
                <ul>
                  <li v-for="(item,idx) in listArr" :key='idx'>
                    <a :href="initUrl(item.regNo,item.intCls)">
                      <table width="100%" cellspacing="0" cellpadding="0">
                        <tbody>
                          <tr>
                            <td width="90" align="center" rowspan="2">
                              <img :src="initImg(item.tmiPath,item.imageUrl)" alt="logo" :onerror='errorImg'>
                            </td>
                            <td width="300">商标名：<span>{{item.name}} </span></td>
                            <td width="300">商标类别：<span>{{item.intCls}}</span></td>
                            <td>申请号：<span>{{item.regNo}}</span></td>
                          </tr>
                          <tr>
                            <td>申请日期：<span>{{item.appDate}}</span></td>
                            <td>申请人：<span>{{item.applicantCn}}</span></td>
                            <td>商标状态：<span>{{item.status}}</span></td>
                          </tr>
                        </tbody>
                      </table>
                    </a>
                  </li>
              </ul>
               <Page  
                  :totalPage="total"
                  :pageNum="pageIndex"
                  :pageSize="pageSize"
                  @pagechange="pagechange"/>
            </div>
            <div v-else>
               <img src="../../assets/images/img_noData.png" alt="智企名" width="120px">
            </div>
      </section> 
      <toolBox/>
    </div>
</template>

<script>
import toolBox from '@/components/toolBanner'
import Page from '@/components/page'
import popup from "~/components/popup"

import img from '@/assets/images/default.jpg'

export default {
  data () {
    return {
      errorImg:`this.src="${img}"`,
      show:false,
      listArr:[],
      keyWord:"", //输入关键字
      pageIndex: 1, //开始页
      pageSize: 20, 
      imgPrefix:"",//图片头
      total:0,//总数
    }
  },
  components: {
    toolBox,
    Page
  },
  mounted () {
    let obj = sessionStorage.getItem("brandObj")
    if(obj){
        this.keyWord = JSON.parse(obj).keyWord
        this.api(JSON.parse(obj))
        sessionStorage.removeItem("brandObj")
    }
  },
  methods: {
    getList(page){
      if (
            /[`~!@#$%^&*_\-+=<>?:"\/'\\[\]·~！@#￥%……&*——\-+=？：.]/im.test(this.keyWord)
        ) {
            popup.created({
                content: "关键字不能包含特殊字符",
                time: 2000
            });
            return;
        }
        if(page) this.pageIndex = 1; 
      let requireObj = {
        pageIndex: this.pageIndex, 
        pageSize:  this.pageSize, 
        key: "nameJs", //搜索类别
        keyWord: this.keyWord,
      };
      if(!this.$store.state.userInfo){
        this.$loginPop(this)
        return
      }
      if(this.keyWord)
      this.api(requireObj)
    },
    api(requireObj){
       this.$axios.$post('brandSearch/searchList',requireObj)
          .then((res) => {
            let {data,code} = res;
            let num = Number(data.totalCount)
              this.show = true;
              this.imgPrefix = data.imgPrefix
              this.listArr = data.listData
              this.total = isNaN(num) ? 0 : num
            this.$Jump(this.$refs.list)
        }).catch(res=>{})
    },
    initImg(url1,url2){
      return url1 ? this.imgPrefix + url1 : url2
    },
    //跳转到详情页面
    initUrl(regNo, intCls) {
      let str1 = (intCls + "").replace(/\D/g, "");
      let str = regNo + "_" + str1 || 0;
      return "/brandSearch/" + str;
    },
    pagechange(num){
      this.pageIndex = num
      this.getList()
    }
  }
}
</script>
 
<style lang="stylus" scoped>
@import '../../assets/css/params.styl'
   *{
       box-sizing border-box;
   }   
.pageBrandSearch
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
  .title
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
    top 320px
    z-index 2
    background #fff
    >ul
      height 70px
      line-height 70px
      padding 20px 0
      float left
      >li{
        width 670px;
        float left
        line-height: 30px;
        font-size: 16px;
        cursor: pointer;
        padding-right 20px
        input{
            padding-left: 20px;
            font-size: 16px;
            width: 100%;
            color: #333;
        }
      }
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
.listBox
  padding-top 60px;
  text-align center;
  width 1050px;
  margin auto;
  background #fff;
  h2
    font-size 24px
    padding-bottom 50px
  ul
    overflow hidden
    li
      margin-bottom 10px
      padding 10px
      border 1px solid $solidcolor
      &:hover
        border 1px solid $maincolor
      img
        width 50px
        height 50px
      table
        text-align left
        line-height 24px
        font-size 14px
        color #999
        span
          color #333
  
</style>
