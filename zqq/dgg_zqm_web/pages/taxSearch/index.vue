<template>
    <div class="pageTaxSearch">
      <div class="banner">
        <img src="../../assets/images/taxSearch/banner.jpg" alt="公司注册">
        <div class="title">
            <h1>税号查询</h1>
            <p>查询纳税人识别号，更快、更全、更精准！</p>
        </div>
        <div class="search" >
          <ul>
            <li>
              <input type="text" placeholder="请输入公司名称、税号等" v-model="keyWord" @keyup.enter='getList'>
            </li>
          </ul>
          <button @click="getList(1)">查询</button>
        </div>  
      </div>
      <p class="hide" ref="list"></p>
      <section class="listBox" v-show="show">
            <h2>搜索结果</h2>
            <div v-if='total > 0'>
                <p>为您找到 <span class="col">{{total}}</span>个符合条件的专利</p>
                <ul>
                    <li v-for="(item,idx) in listArr" :key='idx'>
                      <table  cellspacing="0" cellpadding="0">
                        <tbody>
                             <tr>
                              <td width='140px'>
                                <img :src="item.logo || '' " alt="logo" :onerror='errorImg' style="max-height:100px">
                              </td>
                              <td >
                                <p class="head">
                                  <span>公司名称:</span> 
                                  <a :href="'/gsxq/'+item.companyId" target="_blank" class="col">{{item.name}}</a>
                                  <span class="flag">{{item.qygsDetail ? item.qygsDetail.status : "暂无"}}</span>
                                </p>
                                <p><span>税号:</span>{{item.qygsDetail ? item.qygsDetail.creditCode : "暂无"}}</p>
                                <p><span>地址: </span>{{item.qygsDetail ? item.qygsDetail.address : "暂无"}}</p>
                              </td>
                            </tr>
                        </tbody>
                      </table>
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
import img from '@/assets/images/default.jpg'
import Page from '@/components/page'
export default {
  data () {
    return {
      show:false,
      errorImg:`this.src="${img}"`,
      total:0,
      keyWord:"", //输入关键字
      pageIndex: 1, //开始页
      pageSize: 20,
      listArr:[] 
    }
  },
  components: {toolBox,Page},
  mounted () {},
  methods: {
        getList(page){
          if (
                /[`~!@#$%^&*_\-+=<>?:"\/'\\[\]·~！@#￥%……&*——\-+=？：.]/im.test(this.keyWord)
            ) {
                this.$popup.created({
                    content: "关键字不能包含特殊字符",
                    time: 2000
                });
                return;
            }
            if(!this.$store.state.userInfo){
              this.$loginPop(this)
              return
            }
            if(!this.keyWord) return;
             if(page) this.pageIndex = 1; 
            let requireObj = {
                pageIndex: this.pageIndex, 
                pageSize: this.pageSize, 
                keyWord: this.keyWord,
                type:"tax"
              };
            this.api(requireObj)
        },
        api(requireObj){
          this.$axios.$post('companyItemOld/mix',requireObj)
              .then((res) => {
                let {data,code} = res;
                console.log(data)
                let num = Number(data.totalCount)
                  this.show = true;
                  this.listArr = data.listData
                  this.total = isNaN(num) ? 0 : num
                this.$Jump(this.$refs.list)
            }).catch(res=>{
              console.log(res)
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
.pageTaxSearch
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
   ul{
      width 800px;
      margin auto;
      li:hover{
        background #eef6fe
      }
   }

   table{
     width 800px;
     margin auto;
     border 1px solid $solidcolor
     margin-top 38px;
  
     td{
        // border 1px solid $solidcolor
        height 20px
        text-align left 
        padding 10px 20px
        &.tit{
          text-align right 
          width 200px
          padding 0 10px
        }
        p{
           margin-bottom 10px
           span{
             color #999
             display inline-block;
             text-align right;
             width 74px;
             margin-right 10px
           }
           .flag{
              float right 
              width auto
              font-weight: 400;
              display: inline-block;
              line-height: 16px;
              font-size: 14px;
              padding: 4px 8px;
              border-radius: 2px;
              border-style: solid;
              border-width: 1px;
              box-sizing border-box
              color #1c8bee
              border-color #1c8bee
            }
        }
     }
   }
}

   
</style>
