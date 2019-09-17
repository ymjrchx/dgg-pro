<template>
  <div>
    <mini :hasFloor="1" />
    <div class="page-search w2">
      <span class="main-title"><a href='/shangbiao/freesearch' class='col'>商标搜索</a></span>
      <div class="search-input">
        <input name="" placeholder="请输入商标名称、申请号、申请人等信息" class="text fl" type="text" v-model="keyWord" @keyup.enter='goList'>
        <a href="javascript:void(0)" id="search" class="button search" @click="goList">搜索</a>

        <div style="position: absolute;right: 135px; top: 5px;width: 30px;height: 28px;">
          <!-- <el-upload :action="$store.state.upLoadUrl" :show-file-list="false" :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <i id="select-btn" class="icon-camera"></i>
          </el-upload> -->
        </div>
      </div>
      <div class="search-right">
        <a href="/classify" target="_blank" class="button button-white">商品分类</a>
        <a href="/notice" target="_blank" class="button button-white">初审公告</a>
      </div>
    </div>
    <div class="page-nav" id="brandTitle">
      <div class="page-nav-in w2">
        <a href="javascript:void(0)" v-for="(item,idx) in keyArr" :key="idx" :class="{'active':idx == keyIndex}" @click="switchTab(idx,item.val)">{{item.name}}</a>
      </div>
    </div>
    <!-- //搜索正文大头开始 -->
    <section class="w2" style="margin-top:20px">
      <div class="page-checkbox w-center" v-if=" keyIndex == 0">
        <div class=" checkbox-all-box">
          <label class="checkbox-box" v-for='(item,idx) in conditionArr' :key='idx' @click="selectCondition(idx)">
            <label class="checkbox" :class="{'checked':conditionIdxArr[idx]}"></label>
            <span>{{item}}</span>
          </label>
        </div>
      </div>
      <!-- //字典 开始 -->
      <industryBox @change='changeIndustry' />
      <div class="page-form clear2" style="min-height:200px">
        <div class="selected-category clear2">
          <a class="btn cancelAll" @click="cancelAll" v-show="selectArr.length > 0">全部清除</a>
          <label>已选类别</label>
          <div class="selected-category-box">
            <template v-for='(item,idx) in selectArr'>
              <categoryBox :json='item' :index='idx' :key='idx' @remove='removeCategoryBox' @getSelect='getCategoryBoxCode' />
            </template>
          </div>
        </div>
        <ul>
          <li class="search-category" v-for='(val,key,idx) in docData' :class="{'open':openDoc[idx]}" :key='idx'>
            <a class="btn" @click="openLevel(idx)">更多</a>
            <label>{{matchName(key)}}</label>
            <div class="category-show-box">
              <a href="javascript:void(0)" v-for='(item,idx2) in val' :key='idx2' :class="{'active': (key == 'apply_type_p' && docStyleIdxArr[idx2]) || ( key != 'apply_type_p' && val.length == 1) }"
                @click="selectOption(key,idx2,item.key)" v-if=" item.val>0">{{item.key}}({{item.val}})</a>
            </div>
          </li>
        </ul>
      </div>
      <!-- 字典结束 -->
      <!-- 匹配45类没有的商标注册  start -->
      <div class="other" v-if='hasOther'>
        <p>以下类别暂未检索到商标申请，可尝试进行注册：</p>
        <div class='condition'>
          <img src='../../assets/images/zixun.png'>
          <a class='col' href="javascript:void(0);" onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')">
            专家咨询
          </a>
        </div>
        <a src='javascript:void(0)' class="btn" @click="isShowOther = !isShowOther " :class="{open:!isShowOther}">更多</a>
        <ul :class="{isShowOther:isShowOther}">
          <li v-for='(item,idx) in docData.apply_type_p' :key='idx' v-if='item.val == 0' @click='goOrder(item.key)'>{{item.key}}</li>
        </ul>
      </div>
      <!-- 匹配45类没有的商标注册  end -->
      <!-- 列表开始 -->
      <div class="page-content clear2">
        <!-- //左边列表层级 -->
        <div class="page-content-left">
          <template v-if="total > 0">
            <div class="search-top">
              <a href="/" class="col" rel="nofollow"> 首页 </a> >
              <a href="/shangbiao/freesearch">商标查询</a> > 知千秋为您找到“
              <h1 class="col">{{this.oldkeyWord}}商标查询</h1>”相关结果
              <i>{{total}}</i> 个；
              <template v-if='statisticsJson'>
                <span v-if='statisticsJson.register'>商标申请法律状态已注册
                  <i>{{statisticsJson.register}}</i> 个；</span>
                <span v-if='statisticsJson.invalid'>商标注册无效
                  <i>{{statisticsJson.invalid}}</i> 个；</span>
                <span v-if='statisticsJson.applying'>商标注册申请中
                  <i>{{statisticsJson.applying}}</i> 个;</span>
              </template>

            </div>
            <ul class="search-list">
              <li class=" line-animate" v-for='(item,idx) in listData' :key='idx'>
                <div class="result-href">
                  <div class="brand-img">
                    <a :href="initUrl(item.regNo,item.intCls)" target="_blank"><img :src="initImgUrl(item.imageUrl,item.tmiPath)"
                        :title="(item.name?item.name:item.regNo)+''+item.intCls.substr(0, 2)+'商标分类'" :onerror="logo"
                        :alt="(item.name?item.name:item.regNo)+''+item.intCls.substr(0, 2)+'商标分类'" /></a>
                  </div>
                  <div class="brand-info">
                    <h2>
                      <a :href="initUrl(item.regNo,item.intCls)" target="_blank">{{item.name}}</a>

                      <span class="flag_sta">{{item.status}}</span>
                    </h2>
                    <div class="brand-info-in">
                      <ul>
                        <li>
                          <label>商品类别：</label>
                          <span class="td-btype">第{{item.intCls}}</span>
                        </li>
                        <li>
                          <label>申请号：</label>
                          <span>{{item.regNo}}</span>
                        </li>
                        <li>
                          <label>申请日期：</label>
                          <span>{{item.appDate}}</span>
                        </li>
                        <li>
                          <label>申请人：</label>
                          <span class="spea_span">
                            <a :href='"/shangbiao/" + item.applicantCn + "_3.html"' class="col">{{item.applicantCn}}
                              <img src="../../assets/images/main/icon-fd.png" width="16px"></a>
                          </span>
                        </li>
                        <li>
                          <label>初审公告日期：</label>
                          <span>{{item.announcementDate}}</span>
                        </li>
                        <li>
                          <label>注册公告日期：</label>
                          <span>{{item.regDate}}</span>
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>
                <div class="brand-status">
                  <div v-if="hasBuy(item.rules)" style="margin-bottom:10px">
                    <!-- <span class="status1">关注</span> -->
                    <span class="status1" @click="openConsu(item.name,item.intCls,item.regNo)">购买</span>
                  </div>
                  <div>
                    <span v-for='(item2,idx) in initStatuArr(item.rules)' :key='idx' class="btn status2" v-if=" item2 != '购买' && item2 != '关注' && item2.length > 1">
                      <a v-if="item2 == '注册'" href='javascript:void(0)' @click='goOrder(item.intCls)'>注册</a>

                      <a v-else target="_blank" :href="initStatUrl(item2)">{{item2}}</a>
                    </span>

                  </div>
                </div>
                <div class="clear"></div>
                <p style="line-height:1.5;color:#666;padding:0 20px 20px 160px">
                  <label>商品/服务列表：</label>
                  <span>{{returnStrList(item.tmGoodsService)}}</span>
                </p>
                <div class="clear"></div>
              </li>
            </ul>
            <!-- start 分页 -->
            <pagination :totalPage="total" :pageNum="pageIndex" :pageSize="pageSize" @pagechange="pagechange" />
            <!-- end 分页 -->
          </template>
          <noFind :keyWord="keyWord" :type="'商标'" v-else />
        </div>

        <!-- 右边商标出售 -->
        <div class="page-content-right" v-if='hotArr'>
          <div data-type="2" class="hot-brand hot-brand-right">
            <h2>相关商标查询</h2>
            <ul class="">
              <li v-for='(item,idx) in hotArr' :key='idx'>
                <div class="hot-brand-image"><img :src="initImgUrl(item.imageUrl,item.tmiPath)" :onerror="logo" /></div>
                <div class="hot-brand-detail fl">
                  <h6 class="hot-brand-name" :title="item.name">{{item.name}}</h6>
                  <span class="hot-brand-category">第{{item.intCls}}</span>
                  <strong class="hot-brand-price">
                    申请号:{{item.regNo}}
                  </strong>
                  <a @click="openConsu(item.name,item.intCls,item.regNo)" class="button">我有兴趣</a>
                </div>
              </li>
            </ul>
            <a href="javascript:;" class="more hide">查看更多&gt;&gt;</a>
          </div>
        </div>
      </div>
      <!-- 列表结束 -->
    </section>
    <consultation :isShow="showConsu" :name="consuName" :type="consuType" :code="consuRegNO" @close='closeConsu' />
  </div>
</template>
<script>
  import mini from "~/components/header/mini";
  import {
    Http
  } from "~/plugins/axios.js";
  import popup from "~/components/popup";
  import consultation from "~/components/consultation";
  import pagination from "~/components/pagination.vue";
  import img from "~/assets/images/default.png";
  import noFind from "~/components/noFind";
  import categoryBox from "~/components/categoryBox";
  import industryBox from "~/components/industryBox";
  import axios from "axios";
  import {
    Jump,
    uploadImg,
    pushBaidu
  } from "~/assets/js/common.js";
  import Cookies from "js-cookie";

  export default {
    layout: "empt",
    head() {
      return {
        title: this.titleStr,
        meta: this.metaArr,
        link: [{
          rel: "canonical",
          content: this.baseUrl
        }]
      };
    },
    components: {
      mini,
      noFind,
      pagination,
      categoryBox,
      consultation,
      industryBox
    },
    async asyncData({
      params,
      redirect,
      error,
      env,
      route
    }) {
      let keyWordArr = params.result.split(/\./)[0].split(/_/);
      let keyWord = keyWordArr[0];

      // console.log("77777777777777777777777777", keyWordArr);
      let requireObj = {
        pageIndex: 1, //开始页
        pageSize: 20, //每页多少条
        key: "name", //搜索key，取值： 近似商标 nameJs; 精准商标 name; 申请号 regNo; 申请人 applicantCn;商品服务 tmGoodsService; 代理公司 tmSection;
        keyWord: keyWord,
        filterType: "all", //过滤条件，取值： all 全选； jq 精确； bf 部分； jiaz 加字； jianz 减字； bz 变字； hx 换序； py 拼音； tszf 特殊字符; xjz 形近字；
        applyType: "", //申请类别，传值多个值直接用“-”分隔；
        layStatus: "", //法律状态
        applyYear: "" //申请年份
      };
      // if (keyWordArr[1] == "3") {
      //   requireObj.key = "applicantCn";
      // } else if (keyWordArr[1] == "6") {
      //   requireObj.key = "tmSection";
      // }

      switch (keyWordArr[1]) {
        case "0":
          requireObj.key = "nameJs";
          break;
        case "2":
          requireObj.key = "regNo";
          break;
        case "3":
          requireObj.key = "applicantCn";
          break;
        case "4":
          requireObj.key = "tmGoodsService";
          break;
        case "5":
          requireObj.key = "tmSection";
          break;
        default:
          requireObj.key = "name";
          break;
      }

      let [data1, data2, data3, data4, data5] = await Promise.all([
        axios.post(`${env.baseUrl}brandSearch/searchList`, requireObj),
        axios.post(`${env.baseUrl}brandSearch/approximate`, requireObj),
        axios.post(`${env.baseUrl}searchExtension/aggStatus`, requireObj),
        axios.post(`${env.baseUrl}searchExtension/topFive`, requireObj),
        axios.get(`${env.baseUrl}classfirst/query`)
      ]);
      // console.log("data111", data1);
      // console.log("data2222", data2);

      if (data1.data.code == 0 && data2.data.code == 0) {
        let listData = data1.data.data,
          docData = data2.data.data;
        let total = Number(listData.totalCount || 0);
        let statistics = data3.data.data;

        //tdk设置 start
        let titleStr = `${keyWord}商标注册信息查询_${keyWord}商标注册情况/进度查询_${keyWord}注册信息查询系统-知千秋`;
        let metaArr = [{
            name: "keywords",
            hid: "keywords",
            content: `${keyWord}商标注册信息查询,${keyWord}商标注册信息,${keyWord}商标注册情况,${keyWord}商标注册信息查询入口,${keyWord}商标注册信息查询系统`
          },
          {
            name: "description",
            hid: "description",
            content: `知千秋【商标注册信息查询系统】${keyWord}商标信息查询入口页为您提供：${keyWord}的所有商标信息查询，包含；${keyWord}商标注册情况及申请进度查询，提供商标注册、商标管理、极速网申（0元服务费）等服务。`
          }
        ];

        if (keyWordArr[1] == 3) {
          titleStr = `${keyWord}注册商标查询_${keyWord}注册了那些商标？_${keyWord}注册的全部商标-知千秋`;
          metaArr = [{
              name: "keywords",
              hid: "keywords",
              content: `${keyWord}注册商标查询`
            },
            {
              name: "description",
              hid: "description",
              content: `知千秋为您找到“${keyWord}”注册商标查询：共计${total}个；其中商标已注册，注册无效，申请中具体信息【查看详情】！`
            }
          ];
        } else if (keyWordArr[1] == 6) {
          titleStr = `${keyWord}代理商标查询_${keyWord}注册了那些商标？_${keyWord}注册的全部商标-知千秋官网`;
          metaArr = [{
              name: "keywords",
              hid: "keywords",
              content: `${keyWord}代理商标查询,${keyWord}注册的商标`
            },
            {
              name: "description",
              hid: "description",
              content: `【${keyWord}】代理商标查询；共计代理注册总数${total}个商标包含：其代理的商标已注册、商标注册无效、商标申请中等状态的所有商标信息。`
            }
          ];
        }

        //tdk设置 end

        let obj = {
          keyWord: keyWord,
          keyIndex: 1,
          listData: listData.listData,
          hotArr: data4.data.data.topFive,
          total,
          docData: docData,
          oldkeyWord: keyWord,
          baseUrl: env.webSite + route.fullPath,
          classify45: data5.data.data,
          statistics, //统计
          imgPrefix: listData.imgPrefix, //图片打头
          titleStr,
          metaArr
        };
        if (!isNaN(Number(keyWordArr[1]))) {
          obj.keyIndex = Number(keyWordArr[1]);
        }
        return obj;
      } else {
        error({
          statusCode: 500,
          message: "数据获取失败"
        });
      }
      // let { data } = await axios.get(`${env.baseUrl}/serviceItem/queryServiceItemList?code=${params.code.split('.')[0]}`)
      // 不存在数据
    },

    data() {
      return {
        openDoc: [], //展开字典的初始关闭
        listData: [], //列表数组
        total: 0, //总数
        pageIndex: 1,
        pageSize: 20,
        logo: 'this.src="' + img + '"',
        conditionArr: [
          "全选",
          "精确结果",
          "部分相同",
          "加字",
          "减字",
          "变字",
          "换序",
          "拼音",
          "特殊字符",
          "形近字"
        ],
        docData: {}, //字典对象
        docStyleIdxArr: [],
        keyIndex: 1, //搜索key，取值： 近似商标 nameJs; 精准商标 name; 申请号 regNo; 申请人 applicantCn; 代理公司 tmSection;
        keyArr: [{
            name: "近似商标",
            val: "nameJs"
          },
          {
            name: "精准商标",
            val: "name"
          },
          {
            name: "申请号",
            val: "regNo"
          },
          {
            name: "申请人",
            val: "applicantCn"
          },
          {
            name: "商品服务",
            val: "tmGoodsService"
          },
          // {
          //   name: "图形搜索",
          //   val: ""
          // },
          {
            name: "代理公司",
            val: "tmSection"
          }
        ],
        conditionIdxArr: [1, 1, 1, 1, 1, 1, 1, 1, 1, 1], //过滤条件 小分类初始全部选中
        filterType: "all", //过滤条件，取值： all 全选； jq 精确； bf 部分； jiaz 加字； jianz 减字； bz 变字； hx 换序； py 拼音； tszf 特殊字符; xjz 形近字；
        filterTypeArr: [
          "all",
          "jq",
          "bf",
          "jiaz",
          "jianz",
          "bz",
          "hx",
          "py",
          "tszf",
          "xjz"
        ],
        applyType: "", //申请45类别，传值多个值直接用“-”分隔；并获取子级
        layStatus: "", //法律状态
        applyYear: "", //申请年份
        goodsNum: "", //申请小类别，编辑大类下面的小类的数字号，取值：获取前面数字，如“0120-0122-03-22”，多个用“-”分隔；
        selectArr: [], //已选条件
        applyPerson: "", //申请人
        goods: "", //规范商品
        isClearDocData: 0,
        showConsu: 0, //打开咨询窗
        consuName: "", //咨询窗商标名
        consuType: "", //咨询窗商标类型
        consuRegNO: "", //咨询窗商标注册号
        searchLoading: "",
        clickTwice: false, //多次点击
        hotArr: [], //右边热搜
        isShowOther: true //显示其他未注册的
      };
    },
    computed: {
      statisticsJson() {
        try {
          return JSON.parse(this.statistics);
        } catch (msg) {
          return {};
        }
      },
      hasOther() {
        let bool = false;
        for (let item of this.docData.apply_type_p) {
          if (item.val == '0') {
            bool = true
            break;
          }
        }
        return bool
      }
    },
    mounted() {

      pushBaidu()
      Jump(document.getElementsByTagName("body")[0]);
      this.getOrder()
    },
    methods: {
      getList(requireObj) {
        Http("brandSearch/searchList", requireObj, "post", 1).then(res => {
          let listData = res.data.data;

          this.listData = listData.listData;
          this.total = Number(listData.totalCount || 0);
          Jump(document.getElementById("brandTitle"), -72);
        });
      },
      getDoc(requireObj) {
        Http("brandSearch/approximate", requireObj, "post", 1).then(res => {
          if (this.isClearDocData) {
            //是否清除掉已选的字典接口
            this.isClearDocData = 0;
            this.docData = {};
          }

          let docObj = res.data.data;

          if (docObj.apply_type_p_SMALL) {
            this.selectArr = docObj.apply_type_p_SMALL || [];
            delete docObj.apply_type_p_SMALL;
          } else {
            this.selectArr = [];
          }
          let obj = Object.assign({}, this.docData, docObj);

          // console.log("返回的字典", obj);

          this.docData = obj;
        });
      },
      getStatistics(requireObj) {
        Http("searchExtension/aggStatus", requireObj, "post").then(res => {
          this.statistics = res.data.data; //统计
        });
      },
      //浏览器请求在这里
      httpRequire(type) {
        if (!this.keyWord) {
          this.$Popup.created({
            content: "搜索关键字不能为空",
            time: 2000
          });
          return;
        }

        let requireObj = {
          keyWord: this.keyWord,
          pageIndex: this.pageIndex,
          pageSize: this.pageSize,
          key: this.keyArr[this.keyIndex].val,
          filterType: this.keyIndex == 0 ? this.filterType : "all",
          goodsNum: this.goodsNum,
          applyType: this.applyType,
          layStatus: this.layStatus,
          applyYear: this.applyYear,
          applyPerson: this.applyPerson, //申请人的条件
          goods: this.goods //规范商品
        };
        if (this.keyIndex == 0) {
          delete requireObj.applyPerson;
          delete requireObj.goods;
        } else if (this.keyIndex == 3) {
          delete requireObj.goods;
          delete requireObj.filterType;
        } else if (this.keyIndex == 4) {
          delete requireObj.applyPerson;
          delete requireObj.filterType;
        } else {
          delete requireObj.filterType;
        }
        // !type && this.getDoc(requireObj); //有参数时就只会请求列表 如翻页
        if (!type) {
          this.getDoc(requireObj);
          this.getStatistics(requireObj);
        }
        this.getList(requireObj);
      },
      openLevel(num) {
        let newArr = [...this.openDoc];
        newArr[num] = newArr[num] ? 0 : 1;
        this.openDoc = newArr;
      },
      //跳转到详情页面
      initUrl(regNo, intCls) {
        let str1 = (intCls + "").replace(/\D/g, "");
        let str = regNo + "_" + str1 || 0;
        return "/shangbiaoxq/" + str + ".html";
      },
      //黑底导航条分类
      switchTab(num, val) {
        if (val == "") {
          popup.created({
            content: "请点击搜索框上传图片",
            time: 2000
          });
          return;
        }
        if (this.keyIndex != num) {
          this.searchLoading = popup.created({
            type: "loading",
            icon: "&#xe61c",
            content: "搜索中..."
          });
          this.$router.push(`/shangbiao/${this.keyWord}_${num}.html`);
        }
      },
      //匹配字典名
      matchName(name) {
        let str = "其他";
        switch (name) {
          case "apply_type_p":
            str = "申请类别";
            break;
          case "apply_year":
            str = "申请年份";
            break;
          case "lay_status":
            str = "法律状态";
            break;
          case "apply_person":
            str = "申请人";
            break;
          case "goodsService":
            str = "规范商品";
            break;
        }
        return str;
      },
      //近似商标下面的小分类
      selectCondition(num) {
        let allArr = [1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
          oldArr = [...this.conditionIdxArr];
        if (num) {
          oldArr[num] = oldArr[num] ? 0 : 1;
          allArr = oldArr;
        } else {
          allArr = oldArr[0] ? [0, 0, 0, 0, 0, 0, 0, 0, 0, 0] : allArr;
        }
        let minStyle = "";
        allArr[0] = 1;
        for (let i = 1; i < allArr.length; i++) {
          if (allArr[i] === 0) {
            allArr[0] = 0;
          } else {
            minStyle += this.filterTypeArr[i] + "-";
          }
        }
        if (allArr[0]) minStyle = "all";
        else minStyle = minStyle.substr(0, minStyle.length - 1);
        if (!minStyle) minStyle = "all";

        this.conditionIdxArr = allArr;
        if (this.filterType != minStyle) {
          this.filterType = minStyle;
          this.pageIndex = 1;
          this.httpRequire();
        }
      },
      //字典的分类选中
      selectOption(key, num, code) {
        //45大类的选中与取消选中
        if (key == "apply_type_p") {
          let newArr = [...this.docStyleIdxArr];

          newArr[num] = newArr[num] ? 0 : 1;
          this.docStyleIdxArr = newArr;

          this.get45Select(newArr);
        } else if (key == "apply_year") {
          //年的选中
          this.applyYear = this.applyYear ? "" : code;
        } else if (key == "lay_status") {
          //法律状态的选中
          this.layStatus = this.layStatus ? "" : code;
        } else if (key == "apply_person") {
          //申请人的条件
          this.applyPerson = this.applyPerson ? "" : code;
        } else if (key == "goodsService") {
          //规范商品
          this.goods = this.goods ? "" : code;
        }
        this.httpRequire();
      },
      //45类的选中 产生applyType
      get45Select(arr) {
        let str = "",
          _45Arr = this.docData.apply_type_p;

        for (let i = 0; i < _45Arr.length; i++) {
          if (arr[i]) {
            str += _45Arr[i].key + "-";
          }
        }
        if (str) str = str.substr(0, str.length - 1);

        this.applyType = str;

      },
      //翻页
      pagechange(page) {
        this.pageIndex = page;
        if (this.$store.state.userInfo) {
          this.httpRequire(1);
        } else {
          popup.created({
            content: "请先进行登录",
            time: 2000
          });
          setTimeout(() => {
            this.pageIndex = 1;
            this.$router.push({
              path: "/passport/login",
              query: {
                backUrl: `/shangbiao/${this.keyWord}_${this.keyIndex}.html`
              }
            });
          }, 2000);
        }
      },
      //清除所有已选条件
      cancelAll() {
        this.selectArr = [];
        this.docStyleIdxArr = [];
        this.applyType = "";
        this.pageIndex = 1
        this.httpRequire();
      },
      //清除单独的一个已选条件
      removeCategoryBox(idx, name) {
        //已选数组的清除
        let arr = [...this.selectArr];
        arr.splice(idx, 1);
        this.selectArr = arr;
        //45类选中的的取消
        let newArr = [...this.docStyleIdxArr];
        let _45Arr = this.docData.apply_type_p;
        for (let i = 0; i < _45Arr.length; i++) {
          if (_45Arr[i].key == name) {
            newArr[i] = 0;
            break;
          }
        }
        this.docStyleIdxArr = newArr;
        this.get45Select(newArr);
        this.httpRequire();
      },
      //获取每一个CategoryBox 里面小选项的选中的code，全选为空用数组存起来
      getCategoryBoxCode(str, index) {
        console.log("boxID", str, index);
      },
      //upload上传图片
      handleAvatarSuccess(res, file) {
        this.Loading.close();
        uploadImg(this, res);
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === "image/jpeg" || file.type === "image/png";
        const isLt2M = file.size / 1024 / 1024 < 5;

        if (!isJPG) {
          popup.created({
            content: "上传图片只能是JPG或PNG格式!",
            time: 2000
          });
        }
        if (!isLt2M) {
          popup.created({
            content: "上传图片大小不能超过 5MB!",
            time: 2000
          });
        }
        if (isJPG && isLt2M) {
          this.Loading = this.$Popup.created({
            content: "上传中...",
            type: "loading",
            icon: "&#xe61c"
          });
        }
        return isJPG && isLt2M;
      },
      goList() {
        if (this.keyWord == this.oldkeyWord) return;
        this.keyWord = this.keyWord.replace(/ /g, "");
        if (!this.keyWord) {
          this.$Popup.created({
            content: "搜索关键字不能为空",
            time: 2000
          });
          return;
        }
        if (
          /[`~!@#$%^&*_\-+=<>?:"\/'\\[\]·~！@#￥%……&*——\-+=？：.]/im.test(
            this.keyWord
          )
        ) {
          popup.created({
            content: "商标名称不能包含特殊字符",
            time: 2000
          });
          return false;
        }
        if (this.clickTwice) return;
        else {
          this.clickTwice = true;
        }
        this.$router.push({
          path: "/shangbiao/" + this.keyWord + "_" + this.keyIndex + ".html"
        });
        this.searchLoading = popup.created({
          type: "loading",
          icon: "&#xe61c",
          content: "搜索中..."
        });
      },
      //打开购买咨询窗口
      openConsu(name, type, code) {
        this.consuName = name;
        this.consuType = type;
        this.consuRegNO = code;
        document.getElementsByTagName("body")[0].style.cssText =
          "overflow:hidden";
        this.showConsu = 1;
      },
      closeConsu() {
        this.showConsu = 0;
      },
      hasBuy(str) {
        if (str) {
          return str.indexOf("购买") == -1 ? false : true;
        }
        return false;
      },
      initStatuArr(str) {
        if (str) return str.split("、");
      },
      initStatUrl(str) {
        let key = "";
        switch (str) {
          case "无效":
            key = "navigation_trademark_law_06&S7741113092604534784";
            break;
          case "撤三":
            key = "navigation_trademark_law_02&S7741102661842345985";
            break;
          case "转让":
            key = "navigation_trademark_change_05&S7741135255319453697";
            break;
          case "续展":
            key = "navigation_trademark_change_02&S7741134886166175745";
            break;
          case "注册":
            key = "navigation_trademark_register_01&S7741101919677030400";
            break;
          case "变更":
            key = "navigation_trademark_change_01&S7741134790099836929";
            break;
        }
        return "/show/" + key + ".html";
      },
      //行业选择
      changeIndustry(arr) {
        // console.log(333,this.docData.apply_type_p)

        let newArr = [],
          newStrArr = [],
          _45Arr = this.docData.apply_type_p;

        for (let i = 0; i < _45Arr.length; i++) {
          let str = _45Arr[i].key
          let val = _45Arr[i].val
          for (let item of arr) {
            if (str == item && val > 0) {
              newArr[i] = 1;
              newStrArr.push(str)
              break;
            }
          }
        }
        this.docStyleIdxArr = newArr
        this.openDoc = [1]
        this.applyType = newStrArr.join('-')
        this.pageIndex = 1
        this.httpRequire();
      },
      //图片处理
      initImgUrl(img1, img2) {
        return img2 ? this.imgPrefix + img2 : img1 ? img1 : "";
      },
      returnStrList(str) {
        if (str) {
          let newStr = str.replace(/\d|-/g, "").split(" ");
          // if (newStr.length > 5) {
          //     newStr = newStr.splice(0, 5);
          // }
          return newStr.join(";");
        }
        return "";
      },
      //去下单的相关信息
      loadZn(numbers) {
        return this.$Http(`serviceItem/findServiceItemByNumber`, {
          numbers
        }).then(res => {
          this.showInfo = res.data.data[0];
        });
      },
      getOrder(numbers) { // 
        this.loadZn('S7741102214293331969').then(() => {
          Cookies.set("product", {
            name: this.showInfo.serviceItem.name,
            serviceId: this.showInfo.serviceItem.id,
            serviceAttrId: "",
            num: 1,
            officialPrice: this.showInfo.serviceItem.officialPrice,
            servicePrice: this.showInfo.serviceItem.servicePrice,
            total: this.showInfo.serviceItem.officialPrice +
              this.showInfo.serviceItem.servicePrice
          });
          // this.$router.push("/order/auto")
        }).catch(() => {
          // window.open(
          //     "/show/navigation_trademark_register_04&S7741102214293331969.html"
          // , "_blank");
        })
      },
      goOrder(classify) {
        if (Cookies.get('product')) {
          Cookies.set("otherMsg", {
            'name': this.keyWord,
            'type': classify
          })
          window.open("/order/auto?t=1")
        } else {
          window.open(
            "/show/navigation_trademark_register_04&S7741102214293331969.html", "_blank");
        }
      }
    },
    destroyed() {
      if (this.searchLoading) this.searchLoading.close();
    }
  };

</script>
<style scoped lang='stylus'>
  @import url("../../assets/css/search");

  * {
    box-sizing: border-box;
  }
  .spea_span{
    display: inline-block;
    overflow: hidden;
    max-width: 200px;
    white-space: nowrap;
    text-overflow: ellipsis;
    vertical-align: bottom;
  }
  /* //other 45类*/
  .other {
    padding: 20px;
    border: 1px solid #f08b2f;
    border-radius: 2px;
    margin-top: 16px;
    background: #FFFEFD;
    position relative;

    .btn {
      position: absolute;
      top: 48px;
      right: 40px;
      padding-right: 15px;
      color: #666;
      background: url("../../assets/images/searchList/arrow-down.png") no-repeat right center;

      &.open {
        background: url("../../assets/images/searchList/arrow-up.png") no-repeat right center;
      }
    }

    .condition {
      position: absolute;
      right: 20px;
      top: 10px;
      padding 2px 6px;
      border: 1px solid #f08b2f;
    }

    p {
      color: #565656
    }

    ul {
      overflow: hidden;
      margin-top: 10px;

      &.isShowOther {
        height: 20px;
        overflow: hidden;
      }

      li {
          display: inline-block;
          margin-right: 28px;
          margin-bottom: 5px;
          min-width: 134px;
          color: #73777a;
          cursor: pointer;
          margin-bottom: 6px;
        &:hover {
          color: #f08b2f
        }
      }
    }
  }
</style>
