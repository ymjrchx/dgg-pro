<template>
    <div class="pagePatentSearch">
      <div class="banner">
        <img src="../../assets/images/gs/banner.jpg" alt="公司">
        <div class="title">
            <h1>想创业  就上智企名</h1>
            <p>树立企业可信形象，更多信任，更多交易</p>
        </div>
        <Search :seletedIndex="seletedIndex" :keyWord="keyWord"/>
      </div>
      <section class="listBox" v-show="show">
            <div class="head">
                 <h2>查询结果</h2>
            </div>
            <div v-if='total'>
                <p>为您找到 <span class="col">{{total > 5000 ? '5000+' :  total}}</span>个符合条件的专利</p>
                <table class="tableBox">
                    <thead>
                        <tr>
                            <th style="text-align:left;width:150px">公司</th>
                            <th></th>
                            <th >状态</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for='(item,idx) in listArr' :key='idx'>
                            <td>
                                <img src="" :onerror='errorImg' alt="">
                            </td>
                            <td class="content">
                                <h2><a :href="'/gsxq/'+item.companyId" target="_blank" class="col">{{item.name || '--'}}</a></h2>
                                <p>
                                    <span>法定代表：{{item.qygsDetail ? item.qygsDetail.operName == 'null' ?  '--': item.qygsDetail.operName : '--'}}</span>
                                    <span>注册资金：{{item.qygsDetail ? mathChina(item.qygsDetail.registCapi) || '--' : '--'}}</span>
                                    <span>成立时间：{{mathTime(item.qygsDetail)}}</span>
                                </p>
                                <div>
                                    <span>邮箱：{{item.email || "暂无"}}</span>
                                    <span>电话：{{item.tel || '--'}}</span>
                                    <div class='telBox' v-if='isHasTel(item.qygsDetail)'>更多电话
                                        <ul>
                                            <li v-for='(item,idx) in splitTel(item.qygsDetail.contactInfoList[0].phoneNumber)' :key='idx'>{{item}}</li>
                                        </ul>
                                    </div>
                                </div>
                                <p><span>地址：{{item.qygsDetail ? item.qygsDetail.address : '--'}}</span></p>
                            </td>
                            <td class="flag">
                                <span>{{item.qygsDetail ? item.qygsDetail.status == ' ' ?  '未知' :item.qygsDetail.status: '未知'}}</span>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <Page  
                    :totalPage="total"
                    :pageNum="pageIndex"
                    :pageSize="pageSize"
                    @pagechange="pagechange"/>
            </div>
             <div v-else style="padding:40px 0 100px">
               <img src="../../assets/images/img_noData.png" alt="智企名" width="120px">
            </div>
      </section> 
    </div>
</template>

<script>
import Page from '@/components/page'
import img from '@/assets/images/default.jpg'
import popup from "~/components/popup"
import Search from "~/components/search" 

export default {
  middleware: "auth",
  async asyncData ({$axios,params}) {
  
    let keyArr = params.code.split(/_/)

    let requireObj={
        keyWord:keyArr[1],
        pageIndex:keyArr[2] ? keyArr[2] : 1,
        pageSize:20,
        type:"all"
      }
      switch (Number(keyArr[0])) {
          case 1:
              requireObj["type"] = "qyn";
              break;
          case 2:
              requireObj["type"] = "fr";
              break;
          case 3:
              requireObj["type"] = "bs";
              break;
          default:
              requireObj["type"] = "all";
              break;
      }
      return $axios.$post('company/searchList',requireObj)
            .then((res) => {
                let {data,code} = res;
                let num = Number(data.totalCount)
              return { 
                  show:true,
                  listArr:data.listData,
                  total:isNaN(num) ? 0 : num,
                  keyWord:keyArr[1],
                  seletedIndex:keyArr[0],
                  pageIndex:keyArr[2] ? Number(keyArr[2]) : 1,
                  requireObj
              }
            }).catch(msg=>{
                console.log(msg)
            })
  },
  data () {
    return {
      show:true,
      errorImg:`this.src="${img}"`,
      listArr:[],
      pageIndex: 1, //开始页
      pageSize: 20, 
      imgPrefix:"",//图片头
      total:0,//总数
      seletedIndex:"",
      keyWord:""
    }
  },
  components: {Page, Search},
  mounted () {
    console.log(this.requireObj)
  },
  methods: {
    changTitle (index) {
      this.seletedIndex = index
    },
    switchHot () {
      this.isLoad = true
      setTimeout(() => {
        this.isLoad = false
      }, 3000)
    },
    mathTime(str){
          if(str && str.startDate){
              let time =Number(str.startDate)
              if(isNaN(time)){
                  return str.startDate
              }
              else{
                  return this.mathTime2(time)
              }
          }
          return " - "
    },
    mathTime2(str){
        let date = new Date(str);
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();

        let timeStr =y+"-"+ (m >9 ? m: ("0"+m))+"-"+ (d >9 ? d: ("0"+d));

        return timeStr
    },
    mathChina(str) {
        //匹配中文
        if(str == "") return "";
        let str2 = (str+"").replace(/\s|人民币/g, "");
        let isMath = Number(str2);
        if (isNaN(isMath)) {
            return str2;
        } else {
            return str2 + "万元";
        }
    },
    isHasTel(obj){
      if(obj && obj.contactInfoList && obj.contactInfoList[0].phoneNumber){
          return true
      }
      return false
    },
    splitTel(str){
      if (str.indexOf("、") != -1) {
         return str.split('、')
      }
      return [str]
    },
    pagechange(num){
      if(num == 1){
        this.$router.push(`/gs/${this.seletedIndex}_${this.keyWord}`)
      }
      else
      this.$router.push(`/gs/${this.seletedIndex}_${this.keyWord}_${num}`)
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
  height 420px
  >img
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
      margin-bottom 20px
      font-weight 100
    p
    font-size 24px
    
.listBox{
  padding-top 200px;
  text-align center;
  width 1200px;
  margin auto;
  background #fff;

   >.head{
      height 46px
      line-height 46px
      position relative
      width 280px;
      margin auto;
      margin-bottom 20px;
      h2
      font-size 24px
      &::before{
          content :" "
          position absolute
          height 100%;
          width 4px;
          left 0;
          top 0 
          border 1px solid #888;
          border-right  none
      }
      &::after{
          content :" "
          position absolute
          height 100%;
          width 4px;
          right 0;
          top 0 
          border 1px solid #888;
          border-left none
      }
   } 
   >p{
     margin-bottom 24px
     font-size 16px
     color #808080
     font-weight bold
   }
   table{
      width 1000px;
      margin auto;
      border-collapse:collapse;  
      border-spacing:0; 
      tr{
        padding 20px 14px;
        &:hover{
          background #f9f9f9  
        }
        td,th{
          padding 20px 14px; 
          border-bottom: 1px solid #eee;
          >img{
            max-width 100px
          }
        }
        .content{
          text-align left  
          h2{
            font-size: 18px
          } 
          p{
            margin-top 10px
            color: #777;

            span{
              margin-right 20px
              display inline-block
              min-width 150px
            }
          }
          >div{
            margin-top 10px;
            >span{
              margin-right 20px
              color: #777;
              display inline-block
              min-width 150px
            }
            .telBox{
              display inline-block;
              position relative;
              cursor pointer
              color $maincolor
              ul{
                position absolute;
                background #fff;
                border 1px solid #f1f1f1;
                 -webkit-box-shadow: 0 2px 7px 0 #eaeaea
                box-shadow: 0 2px 7px 0 #eaeaea
                padding 10px 15px
                border-radius 2px
                line-height 1.6em
                display none;
                left 100%
                top 0
                color #777
                // min-height 100px
                min-width 144px
                li{
                    white-space nowrap
                }
                li:hover{
                  color $maincolor
                }
              }
              &:hover ul{
                display block;
                } 
            }
          }           
        }
        .flag{
          span{
            font-weight: 400;
            display: inline-block;
            line-height: 16px;
            font-size: 14px;
            padding: 4px 8px;
            border-radius: 2px;
            border-style: solid;
            border-width: 1px;
            color $maincolor
          }
          .green {
              color: #009944;
          }
          .red {
              color: #ff0000;
          }
          .violet {
              color: #840084;
          }  
        }
      }

   }
}
</style>
