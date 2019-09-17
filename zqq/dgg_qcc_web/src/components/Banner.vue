<template>
  <div class="banner" id="Banner_canvas">
    <canvas id="constellationel" width="100%" height="264"></canvas>
    <div>
      <h1 class="banner_titel">
        查企业，就上顶企查！
      </h1>
      <div class="searchBox">
        <div class="search-nav hidden-xs">
          <ul class="V3_index_search_item clear">
            <template v-for='(item, index) in liArr'>
              <li :class="{'search-type': true, 'active': isChecked == index }" @click="isCheckedFuc(index)" :key="index">{{item}}</li>
              <li :key="index+100"></li>
            </template>
          </ul>
        </div>
        <!-- <form action="javascript:void(0)" id="V3_Index_S" style="padding-right: 60px;"> -->
        <div class="input-group">

          <el-autocomplete popper-class="input-autocomplete" v-model="indexState" :fetch-suggestions="querySearchAsync" :placeholder="holderArr[isChecked]" @select="handleSelect" @keyup.enter.native="goList">
            <template slot-scope="{ item }">
              <div class="list_search">
                <em v-html="item.isName ? item.heightLight : item.value"></em>
                <span class="addr" v-if="item.flag">{{item.flag}}</span>
              </div>
            </template>
          </el-autocomplete>
          <span class="input-group-btn">
            <input @click="goList" id="V3_Search_bt" type="button" class="btn-lg" value="查一下" required="required">
          </span>
        </div>
        <!-- <a class="hidden-md hidden-sm" style="display: block;position: absolute;right: -10px;color: #fff;bottom: 14px;font-size: 16px;" @click="demo">高级搜索</a> -->
        <!-- </form> -->
      </div>
      <div class="searchBox">
        <div class="row">
          <div class="searchBox">
            <p class="index-hot">
              <span id="hot_data_label">热搜：</span>
              <span id="hot_data" v-if="hotArr">
                <a class="index-hot-company" v-for="(item,idx) in hotArr" :key="idx" @click="hotSearch(idx)">{{item.name}}</a>
              </span>
              <a @click="hotArrSplice" class="index-hot-company" style="color:white;" id="refresh_hot">
                <span class="c_icon ca_refresh" style="position:relative;top:4px;"></span>&nbsp;换一批</a>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import $ from 'jquery';

import Common from "@/util/common";
import Store from "@/store";
import { Constellationel} from "@/util/constellation";

export default {
    name: "canvas_live",
    props: {
        data: {}
    },
    data() {
        return {
            msg: "Welcome to Your Vue.js App111111111111",
            liArr: [
                "全部",
                "企业名",
                "法人/股东",
                "高管",
                "品牌/产品",
                "地址/电话",
                "经营范围"
            ],
            holderArr: [
                "请输入企业名称、人名，地址等，如“腾讯马化腾”",
                "请输入企业名称、注册号或统一社会信用代码，如“阿里巴巴”",
                "请输入法人名称或股东名称，如“马云”",
                "请输入高管名称，如“李开复”",
                "请输入品牌或产品的关键字，如“顶企查”",
                "请输入企业注册地址、联系电话、邮箱或网址，如“成都高新区”",
                "请输入企业经营范围，如“电子商务”"
            ],
            isChecked: 0,
            restaurants: [],
            indexState: "",
            splictArr:0,
            hotArr:"",
            companyArr:[{name:"百度",companyName:"百度在线网络技术(北京)有限公司",companyId:"pre91110000802100433B"},
                        {name:"顶呱呱上市",companyName:"成都顶呱呱投资集团有限公司",companyId:"prefirm_d83843dbbaab6702d96d51deaf72f62esu"},
                        {name:"阿里巴巴",companyName:"阿里巴巴杭州文化创意有限公司",companyId:"prefirm_a6ca3d89d1d658b74317b855da139cd9su"},
                        {name:"腾讯",companyName:"深圳市腾讯计算机系统有限公司",companyId:"prefirm_f1c5372005e04ba99175d5fd3db7b8fcsu"},
                        {name:"刘强东",companyName:"北京京东世纪贸易有限公司",companyId:"pre911103026605015136"},
                        {name:"小顶科技",companyName:"北京小顶科技有限公司",companyId:"prefirm_66b98c44413261ded846bfbae92fc4e7su"},
                        {name:"顶峰知产",companyName:"重庆顶峰知识产权代理有限公司",companyId:"prefirm_40235f2912c26fe954c4272bb997b357su"},
                        {name:"顶联互动信息",companyName:"成都顶联互动信息产业有限公司",companyId:"prefirm_a71fa8c8eb4c9f481d28f510600273dasu"},
                        {name:"京东电子商务",companyName:"北京京东叁佰陆拾度电子商务有限公司",companyId:"prefirm_c0fa0f946164132bad214a7ce62ac608su"},
                        {name:"小米科技",companyName:"小米科技有限责任公司",companyId:"pre91110108551385082Q"},
                        {name:"淘宝网络",companyName:"浙江淘宝网络有限公司",companyId:"prefirm_c29fb59a50a8d6f0cab90a2dac54cbf8su"}
                        ]
        };
    },
    created() {
       this.hotArrSplice()
    },
    mounted() {
        this.restaurants = this.initHistory();
        Store.commit("updateHeadActive", 0);
       Constellationel()
    },
    beforeDestroy() {
      
    },
    methods: {
        initHistory() {
            let arr = Common.getHistory("dqc_history");
            return arr ? arr : [];
        },
        isCheckedFuc(index) {
            this.isChecked = index;
        },
        goList() {
            let val = this.indexState;
            if (!val || val == "") {
                this.$message({
                    message: "请先填写搜索内容！",
                    type: "error",
                    position: "center",
                    duration: 1000
                });
            } else {
                Common.remberHistory("dqc_history", val, 5);
                this.$router.push({
                    path: "/SearchResult",
                    query: { type: this.getStyle(), keyWord: val }
                });
            }
        },
        querySearchAsync(queryString, cb) {
            let _this = this;

            if (queryString.length > 1) {
                this.getMsg(queryString, res => {
                    cb(res);
                    }
                )  
            } else {
                if (_this.initHistory().length > 0) {
                    cb(_this.initHistory());
                } else {
                    cb([]);
                }
            }
        },

        handleSelect(item) {
            if (item.hasOwnProperty("companyId")) {
                console.log(item)
                Common.newPage("Company_detail",{ id: item.companyId ,title:item.value});
                Common.remberHistory("dqc_history", item.value, 5);
            }
        },
        hotArrSplice(){
            if(this.splictArr){
                 this.hotArr=this.companyArr.slice(0,6);
                 this.splictArr=0;
            }
            else{
                 this.hotArr=this.companyArr.slice(6);
                 this.splictArr=1;
            }
        },
        hotSearch(idx){
            Common.newPage("Company_detail",{ id: this.hotArr[idx].companyId ,title: this.hotArr[idx].companyName});
        },
        getMsg(e, fn) {
            let str = e + "",
                _this = this;
            // let usrStr='http://172.16.2.71:9091/company/searchFive?keyWord='+e+'&count=5';
            let obj = {
                keyWord: e,
                count: 5,
                type: this.getStyle()
            };

            this.$axios({
                type: "get",
                url: "company/searchFive",
                data: obj,
                success: res => {
                    if (res.data.code == 0) {
                        let arr = [];
                        for (let key of res.data.data) {
                            let str = "",
                                bool = 0;
                            switch (key.highlight) {
                                case "name":
                                    str = "企业名";
                                    bool = 1;
                                    break;
                                case "qygsDetail.operName":
                                    str = "法人名";
                                    break;
                                case "qygsDetail.partnersList.stockName":
                                    str = "股东名";
                                    break;
                                case "qygsDetail.employeesList.name":
                                    str = "高管";
                                    break;
                                case "qygsDetail.address":
                                    str = "地址";
                                    break;
                                case "tel":
                                    str = "电话号码";
                                    break;
                                case "qygsDetail.contactInfoList.webSiteName":
                                    str = "网站名称";
                                    break;
                                case "qygsDetail.scope":
                                    str = "经营范围";
                                    break;
                                case "jyzk.products.name":
                                    str = "产品";
                                    break;
                                default:
                                    str = "其他";
                            }
                            arr.push({
                                value: key.name,
                                heightLight: key.lightVal,
                                companyId: key.companyId,
                                flag: str,
                                isName: bool
                            });
                        }
                        fn(arr);
                    } else fn([]);
                },
                fail: res => {
                    console.log("错误信息", res);
                    fn([]);
                }
            });
        },
        getStyle() {
            let str = "";
            switch (this.isChecked) {
                case 0:
                    str = "all";
                    break;
                case 1:
                    str = "qyn";
                    break;
                case 2:
                    str = "fr";
                    break;
                case 3:
                    str = "gg";
                    break;
                case 4:
                    str = "pp";
                    break;
                case 5:
                    str = "ap";
                    break;
                case 6:
                    str = "bs";
                    break;
            }
            return str;
        },
        submit() {},
        demo() {
            this.$router.push("/Demo");
        },
    }
};
</script>
	
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import "../assets/banner.css";

</style>
