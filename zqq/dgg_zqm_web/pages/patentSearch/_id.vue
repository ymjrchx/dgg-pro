<template>
    <div class="pagePatentSearch">
      <div class="banner">
        <img src="../../assets/images/pantentSearch/banner.jpg" alt="专利信息查询">
        <div class="title">
            <h1>专利信息查询</h1>
            <p>支持全国商标查询，了解最新的商标信息以及申请进度</p>
        </div>
        <div class="search" >
          <ul>
            <li>
              <input type="text" placeholder="请输入商标，如顶呱呱" v-model="keyWord" @keyup.enter='getList'>
            </li>
          </ul>
           <button @click="getList">查询</button>
        </div>  
      </div>
     
      <div class="show" v-if='jsonObj'>
        <table class="table table-bordered" width="100%" cellpadding="0" cellspacing="1">
          <tbody>
            <tr>
              <td width="180" class="title">名称： </td> 
              <td>{{jsonObj.piInventName}}</td>
            </tr> 
            <tr>
              <td class="title">申请号： </td> 
              <td>{{jsonObj.applicationNumber || '--'}}</td>
            </tr> 
            <tr>
              <td class="title">申请日： </td> 
              <td>{{jsonObj.applicationDate || '--'}} </td>
            </tr> 
            <tr>
              <td class="title">申请公布号： </td> 
              <td>{{jsonObj.publicationNumber || '--'}}</td>
            </tr> 
            <tr>
              <td class="title">申请公布日： </td>
              <td>{{jsonObj.publicationDate || '--'}}</td>
             </tr> 
             <tr>
              <td class="title">发明人： </td> 
              <td>{{jsonObj.inventorString || '--'}} </td>
            </tr> 
            <tr>
              <td class="title">类型： </td>
              <td>{{jsonObj.kindCodeDesc || '--'}}</td>
            </tr> 
            <tr>
              <td class="title">专利代理机构： </td>
               <td>{{jsonObj.agency || '--'}}</td>
             </tr> 
             <tr>
              <td class="title">法律状态： </td> 
              <td>
                 <ul class="list-content songti" v-if='jsonObj.lawInfos'>
                        <li class="item clearfix" v-for='(item,idx) in jsonObj.lawInfos' :key='idx'>
                            <span class="text">{{item.lawDate}}</span>
                            <span class="text">{{item.lawStatus}}</span>
                        </li>
                  </ul>
              </td>
            </tr> 
            <tr>
              <td class="title">摘要： </td> 
              <td>{{jsonObj.abstracts || '暂无'}}</td>
            </tr>
            <tr>
              <td class="title">摘要附图： </td> 
              <td><img :src="initImg(jsonObj.desDrawings)" :onerror="errorImg" alt="摘要附图" title="摘要附图" style="max-height:120px"></td>
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
      let code = params.id
       let url = encodeURI(
        `patentSearch/detail?key=applicationNumber&val=${code}`
      );
      return $axios.$get(url)
          .then(res =>{
            return {
                jsonObj: res.data[0]
            }
      })
  },
  data () {
    return {
      errorImg:`this.src="${img}"`,
      keyWord:"",
      jsonObj:""
    }
  },
  components: {
    toolBox,
    Page
  },
  mounted () {
    console.log(this.json)
  },
  methods: {
      initImg(arr) {
            if (arr == undefined || arr.length == 0) {
                return "";
            } else {
                return arr[0].clUrl;
            }
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
                  keyWord: this.keyWord,
                  dateType:"applicationDate",
                  layStatus:"有权-无权-在审",
                  type:"发明-发明公开-发明授权-实用新型-外观设计"
                };
                  sessionStorage.setItem("patentObj",JSON.stringify(requireObj))
                  this.$router.push('/patentSearch')
            }
      },
  }
}
</script>
 
<style lang="stylus" scoped>
@import '../../assets/css/params.styl'
   *{
       box-sizing border-box;
   }   
.pagePatentSearch
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
      &:last-child{
          text-align left 
        }
      ul
        overflow hidden
        .item
          float left
          margin-right 40px  
</style>
