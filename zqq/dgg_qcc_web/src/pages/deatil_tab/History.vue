<template>
   <div class="tb_content">
      <section class="panel  clear m_dataTab">
        <div class="panel-body" style="padding-top: 5px">
            <a href="javascript:;"  class="btn btn-sm btn-default  m-r-sm  m-t-sm c_disable"
                style="white-space:nowrap;">
                历史工商信息&nbsp;
            </a>
            <a href="javascript:;"  class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable"
                style="white-space:nowrap;">
                历史对外投资&nbsp;0</a>
            <a href="javascript:;"
                class="btn btn-sm btn-default  m-r-sm  m-t-sm c_disable" style="white-space:nowrap;">
                历史股东&nbsp;
            </a>
            <a href="javascript:;" class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                历史失信被执行人&nbsp;0
            </a>
            <a href="javascript:;" class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                历史被执行人&nbsp;0
            </a>
            <a href="javascript:;" class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                历史法院公告&nbsp;0
            </a>
            <a href="javascript:;"  class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable"
                style="white-space:nowrap;">
                历史裁判文书&nbsp;0
            </a>
            <a href="javascript:;" class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                历史行政处罚&nbsp;0
            </a>
            <a href="javascript:;" class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                历史动产抵押&nbsp;0
            </a>
            <a href="javascript:;"  class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable"
                style="white-space:nowrap;">
                历史开庭公告&nbsp;0
            </a>
            <a href="javascript:;" class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                历史股权出质&nbsp;0
            </a>
            <a href="javascript:;" class="btn btn-sm btn-default  m-r-sm m-t-sm c_disable" style="white-space:nowrap;cursor: default">
                历史行政许可&nbsp;0
            </a>
        </div>
      </section>
      <section class="panel ">
        <div class="panel-body">
            <div class="noresult">
                <p class="noface">
                    <img src="../../assets/images/noradar.png"> </p>
                以下数据只对VIP用户开放哦
                <div>
                    <a  class="btn btn-primary m-t-sm" target="_blank">
                        立即开通VIP
                    </a>
                </div>
            </div>
        </div>
    </section>
    </div>
</template>
<script>
 
 let echarts = require('echarts/lib/echarts')

import userPhoto from "@/assets/images/default.jpg";
import option from '@/util//map-option'  //引入配置项

import 'echarts/map/js/china.js';
import 'echarts/theme/shine.js';

export default {
  name: "head1",
  props: {
    data: {}
  },
  data() {
    return {
      msg: "Welcome to Your Vue.js App111111111111",
      name: "张三",
      logo: 'this.src="' + userPhoto + '"'
    };
  },
  created() {},
  mounted(){
    
  },
  methods: {
    drawLine(){
        // 基于准备好的dom，初始化echarts实例
         let myChart = echarts.init(document.getElementById('myChart'),"shine");
          myChart.setOption(option);
    },
    drawMap(){
        let myMap = this.$echarts.init(document.getElementById('map'));
            myMap.setOption({
                 title: { text: '企业对外投资行业前五分布图' },
                series : [
                        {
                            name: '企业对外投资行业前五分布图',
                            type: 'pie',
                            radius: '55%',
                            roseType: 'angle',
                            data:[
                                {value:235, name:'视频广告'},
                                {value:274, name:'联盟广告'},
                                {value:310, name:'邮件营销'},
                                {value:335, name:'直接访问'},
                                {value:400, name:'搜索引擎'}
                            ]
                        }
                    ]
            })
    }
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
 @import "../../assets/detail.css";
.tb_content {
  border: 1px solid #eee;
  border-top: none;
  background: #fff;
}
.company {
  overflow: hidden;
  padding: 20px;
}
.company_l {
  width: 10%;
  text-align: center;
  padding-right: 20px;
}
.company_l img {
  width: 100px;
}
.company_m {
  width: 80%;
}
.company_m p {
  display: block;
  font-size: 22px;
  color: #000000;
  letter-spacing: 0;
  font-weight: 600;
  line-height: 31px;
}
.row_2 {
  display: inline-block;
  width: 40%;
  margin-bottom: 6px;
  overflow: hidden;
  vertical-align: top;
}
.row_1 {
  overflow: hidden;
}
.row_1 .w80,
.row_2 .w80 {
  width: 80%;
}
section.panel{
    padding: 0 15px;
}
</style>
