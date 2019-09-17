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
                暂无历史信息
        </div>
    </section>
    </div>
</template>
<script>
 
import nofound from '@/assets/images/noFind.png'

export default {
  name: "head1",
  props: {
    data: {}
  },
  data() {
    return {
      msg: "Welcome to Your Vue.js App111111111111",
      name: "张三",
      logo: 'this.src="' + nofound + '"'
    };
  },
  created() {},
  mounted(){
    
  },
  methods: {
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
 @import "../../assets/css/detail.css";
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
