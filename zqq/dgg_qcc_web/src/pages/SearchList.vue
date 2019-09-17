<template>
  <div class="content" >
    <div class="c-l" id="offSet2">
        <router-view></router-view>
    </div>
    <div class="c-r">
      <a href="#">
        <img src="../assets/images/rightapp2.png" alt="">
      </a>
      <a href="#">
        <img src="../assets/images/righthy2.png" alt="">
      </a>
      <a href="#">
        <img src="../assets/images/rightsearch.png" alt="">
      </a>
      <a href="#">
        <img src="../assets/images/rightsyr.png" alt="">
      </a>
      <a href="#">
        <img src="../assets/images/rightxcx.png" alt="">
      </a>
      
    </div>
     <div class="zoom" ></div>
  </div>
</template>
<script>
import axios from "axios"
export default {
   components: {
   
  },
 data() {
      return {
        headString:["公司","风险信息","知识产权"],
        pageId:0,
        fullscreenLoading: true
      };
    },  
 
    created() {
      window.scrollTo(0, 0);
      // console.log("參數props", this.$route.query);
      this.requireData = this.$route.query;
      // this.initPage(this.$route.query);
    },
    methods: {
      handleClick(tab, event) {
        console.log(tab, event);
      }
    }
};
</script>
<style scoped>
    .content{
      width: 1250px;
      margin: auto;
      margin-top: 20px;
      overflow: hidden;
    }
    .c-l{
      float: left;
      width: 75%;
    }

    .c-r{
      float: right;
      width: 25%;
      text-align: center;
    }
    .c-r img{
      width: 280px;
      margin-bottom: 16px;
    }
    /* //选项卡 */
    .tab_content_item{
      /* overflow: hidden; */
      margin-bottom: 6px;
    }
    .tab_content_item >label{
      float: left;
      font-size: 13px;
      color: #9a9a9a;
      width: 11%;
      line-height: 1.80571;
      font-weight: normal;
    }
    .tab_content_classify{
      width: 89%;
      float: left;

    }
    /* .tab_content_classify > span{
      display: inline-block;
      padding: .1em 0.6em .2em;
      margin: 0 4px 8px;
      border-radius: 1px;
      text-align: center;
      cursor: pointer;
    }
    .tab_content_classify > span:hover{
      background: #128cec;
      color:#fff;
    } */
</style>