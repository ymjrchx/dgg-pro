<template>
    <div>
        <div class="card">
            <div ><router-link to="/Company">公司</router-link> </div>
            <div class="active"><router-link to="/Risk">风险信息</router-link> </div>
            <div><router-link to="/Knowledge">知识产权</router-link> </div>
        </div>
         <div class="tab_content">
            <ul>
              <li class="tab_content_item">
                <label>查找范围</label>
                <div class="tab_content_classify">
                    <span>企业名</span>
                    <span>法人或股东</span>
                    <span>高管</span>
                    <span>经营范围</span>
                </div>
              </li>
                <li class="tab_content_item">
                <label>查找范围</label>
                <div class="tab_content_classify">
                    <span>企业名</span>
                    <span>法人或股东</span>
                    <span>高管</span>
                    <span>经营范围</span>
                </div>
              </li>
            </ul>
          </div>
        <div class="goods_list"> </div>
   </div>
</template>
<script>

export default {
   components: {
   
  },
 data() {
      return {
        headString:["公司","风险信息","知识产权"],
        pageId:0
      };
    },  
    created(){
     
    },
    methods: {
      handleClick(tab, event) {
        console.log(tab, event);
      }
    }
};
</script>
<style scoped>
    .card{
      width: 100%;
      background-color: #fcfcfd;
      border: 1px solid #e9eeef;
      padding-left: 0;
      margin-bottom: 0;
      list-style: none;
      height: 44px;
    }
    .card > div.active{
        float: left;
        color: #128cec;
        border-left: 1px solid #e9eeef;
        border-right: 1px solid #e9eeef;
        background: #fff;
        margin-bottom: -2px;
        border-bottom: solid 1px #fff;
        font-weight: 700;
        position:relative;
        bottom: -1px;
    }
    .card >div{
      height: 100%;
      line-height: 44px;
      text-align: center;
      float: left;
      display: block;
      font-size: 14px;
      padding-left: 30px;
      padding-right: 30px;
      cursor: pointer;
    }
  .card >div:first-child{
      border-left: none !important;
  }
    /* //选项卡 */
    .tab_content{
      padding: 20px;
    }
    .tab_content_item{
      overflow: hidden;
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
    .tab_content_classify > span{
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
    }
</style>