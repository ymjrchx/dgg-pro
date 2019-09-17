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
          <button @click="show = !show">查询</button>
        </div>  
      </div>
     
      <div class="show">
        <table class="table table-bordered" width="100%" cellpadding="0" cellspacing="1">
          <tbody>
            <tr>
              <td width="180" class="title">商标名称： </td> 
              <td>{{json.name || '--'}}</td>
            </tr> 
            <tr>
              <td class="title">申请号： </td> 
              <td>{{json.regNo || '--'}}</td>
            </tr> 
            <tr>
              <td class="title">分类： </td> 
              <td>{{json.intCls || '--'}}</td>
            </tr> 
            <tr>
              <td class="title">商标状态： </td>
              <td>{{json.status || '--'}}</td>
            </tr> 
            <tr>
              <td class="title">申请人： </td> 
              <td>{{json.applicantCn || '--'}}</td>
            </tr> 
            <tr>
              <td class="title">申请日期： </td> 
              <td>{{json.appDate || '--'}}</td>
            </tr> 
            <tr>
              <td class="title">图片： </td> 
              <td>
                <img :src="json.tmiPath ? (imgHead+json.tmiPath) : ''"  :onerror="errorImgFunc(errorImg)" :title="json.name+'商标图片'" :alt="json.name+'商标图片'" height="80px">
              </td>
            </tr> 
            <tr>
              <td class="title">商品/服务列表： </td> 
              <td>
                <ul class="product-list">
                  <li
                    class="list"
                    v-for='(item,idx) in initServerArr(json.tmGoodsService)'
                    :key='idx'
                  >{{item}}</li>
                </ul>
              </td>
            </tr>
            <tr>
              <td class="title">商标流程： </td> 
              <td>
                <ul class="brandstate-list">
                  <li
                    class="list"
                    v-for='(item,idx) in initServerArr2(json.tmApplyFlow)'
                    :key='idx'
                  >{{item}}</li>

                </ul>
              </td>
            </tr> 
            <tr>
              <td class="title">使用期限</td> 
              <td>{{json.validPeriod || '暂无'}} </td>
            </tr> 
            <tr>
              <td class="title">代理机构： </td> 
              <td>{{json.tmSection || '--'}}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <toolBox/>
    </div>
</template>

<script>
import toolBox from '@/components/toolBanner'
import Page from '@/components/page'
import img from '@/assets/images/default.jpg'

export default {
 asyncData ({$axios,params}) {
      let keyWord = params.id.split(/_/);
       let url = encodeURI(
        `brandSearch/brandDetail?type=${keyWord[1]}&appNo=${
          keyWord[0]
        }`
      );
      return $axios.$get(url)
          .then(res =>{
            return {
                json: res.data[0],
                errorImg:`${img}`,
                imgHead: res.data[1].imgPrefix
            }
      })
  },
  data () {
    return {
      json:"",
      keyWord:"", //输入关键字
    }
  },
  components: {
    toolBox,
    Page
  },
  mounted () {
  },
  methods: {
        initServerArr(str) {
          return str ? str.split(" ") : ["--"];
        },
        initServerArr2(str) {
          return str ? str.split(/、| /) : ["--"];
        },
        getList(){
            if (
                  /[`~!@#$%^&*_\-+=<>?:"\/'\\[\]·~！@#￥%……&*——\-+=？：.]/im.test(this.keyWord)
              ) {
                  this.$popup.created({
                      content: "关键字不能包含特殊字符",
                      time: 2000
                  });
                  return;
              }
             if(this.keyWord){
                let requireObj = {
                  pageIndex: this.pageIndex, 
                  pageSize: this.pageSize, 
                  key: "nameJs", //搜索类别
                  keyWord: this.keyWord,
                };
                sessionStorage.setItem("brandObj",JSON.stringify(requireObj))
                this.$router.push('/brandSearch')
             }
           
        },
        errorImgFunc(img){
            return `this.src="${img}"`
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
.show
  padding-top 60px;
  text-align center;
  width 800px;
  margin auto;
  
  table
    text-align center
    background #eee;
    td
      background #fff
      padding 15px
      line-height 22px
      &.title
        background #f5f5f5
      ul{
        overflow hidden
        li{
          float left
          margin-right 30px
        } 
      }  
  
</style>
