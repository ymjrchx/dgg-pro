<template>
    <div class="pagePatentSearch">
      <div class="banner">
        <img src="../../assets/images/pantentSearch/banner.jpg" alt="公司注册">
        <div class="title">
            <h1>专利信息查询</h1>
            <p>支持全国商标查询，了解最新的商标信息以及申请进度</p>
        </div>
        <div class="search" >
          <ul>
            <li>
              <input type="text" placeholder="请输入关键字" v-model="keyWord" @keyup.enter='getList'>
            </li>
          </ul>
          <button @click="getList(1)">查询</button>
        </div>  
      </div>
      <p class="hide" ref="list"></p>
      <section class="listBox" v-show="show">
            <h2>搜索结果</h2>
            <div  v-if='total > 0'>
              <p>为您找到 <span class="col">{{total}}</span>个符合条件的专利</p>
              <ul class="ulBox">
                  <li v-for='(item,idx) in listArr' :key='idx'>
                      <h2> <a :href="'/patentSearch/'+item.applicationNumber" class="col">{{item.piInventName}}</a></h2>
                      <ul>
                        <li>
                          <label>申请号：</label><span>{{item.applicationNumber || '--'}}</span>
                        </li>
                        <li>
                          <label>公布号：</label><span>{{item.publicationNumber || '--'}}</span>
                        </li>
                        <li>
                          <label>申请公布日：</label><span>{{item.publicationDate || '--'}}</span>
                        </li>
                        <li>
                          <label>类别：</label><span>{{item.kindCodeDesc || '--'}}</span>
                        </li>
                        <li>
                          <label>发明人：</label><span>{{item.inventorString || '--'}}</span>
                        </li>
                      </ul>
                      <p>
                        申请人：{{item.assigneestring || '--'}}
                      </p>
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
export default {
  data () {
    return {
      show:false,
      pageIndex: 1, 
      pageSize: 20, 
      keyWord: "",
      listArr:[],
      total:0,//总数
    }
  },
  components: {toolBox,Page},
  mounted () {
    let obj = sessionStorage.getItem("patentObj")
    if(obj){
        this.keyWord = JSON.parse(obj).keyWord
        this.api(JSON.parse(obj))
        sessionStorage.removeItem("patentObj")
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
          pageSize: this.pageSize, 
          keyWord: this.keyWord,
          dateType:"applicationDate",
          layStatus:"有权-无权-在审",
          type:"发明-发明公开-发明授权-实用新型-外观设计"
        };
        if(!this.$store.state.userInfo){
          this.$loginPop(this)
          return
        }
        if(this.keyWord)
        this.api(requireObj)
      },
      api(requireObj){
        this.$axios.$post('patentSearch/searchList',requireObj)
            .then((res) => {
              let {data,code} = res;
              let num = Number(data.totalCount)

              this.show = true;
              this.imgPrefix = data.imgPrefix
              this.listArr = data.listData
              this.total = isNaN(num) ? 0 : num
              this.$Jump(this.$refs.list)
          })
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
.listBox{
  padding-top 60px;
  text-align center;
  width 1200px;
  margin auto;
  background #fff;

   >h2{
     font-size 24px
     margin-bottom 12px
   }
   >p{
     margin-bottom 24px
     font-size 16px
     color #808080
     font-weight bold
   }
   .ulBox{
      padding 0 30px;
      overflow hidden;
      text-align left
      margin-top 30px 
     >li{
       width 560px;
       margin-right 20px;
        margin-bottom 20px;
       border 1px solid $solidcolor;
       padding  16px 20px;
       float left;
       &:nth-child(2n){
         margin-right 0px;
        }
        &:hover{
            border 1px solid $maincolor;
          }
        h2{
          color #1c8bee
          font-size 16px
        }
        ul{
            border-bottom 1px solid $solidcolor;
            overflow hidden
            padding 8px 0
            margin-bottom 14px
            li{
              width 50%
              float left
              line-height 1.8em
              label{
                 color #999999
              } 
            }
        }
        
     }

   }
}

   
</style>
