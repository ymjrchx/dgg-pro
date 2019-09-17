<template>
  <div
    class="site-map"
    v-if="mapData"
  >
    <!--    首页-->
    <div class="site">
      <ul class="clearfix">
        <li class="list">
          <a
            class="text"
            href="/"
          >首页</a>
          <em class="arrow">&gt;</em>
          网站地图
        </li>
      </ul>
    </div>
    <!--    知产服务-->
    <div class="map-knowledge">
      <span>知产服务</span>
    </div>
    <div class="map-Know">
      <div
        v-for="(item, index) in mapData.sonArr"
        :key="item.id"
        class="map-TrademarkBusiness"
        :class="{'border': index == 2}"
      >
        <p><img
            :src="imgs[index]"
            class="xiaologo"
          > {{item.name}}</p>
        <ul class="map">
          <template v-for="(childItem) in item.sonArr">
            <li
              v-for="(threeItem) in childItem.sonArr"
              :key="threeItem.id"
            >
              <a
                href="javascript:;"
                @click="getUrl(threeItem.code, threeItem.ext4)"
              >{{threeItem.name}}</a>
            </li>
          </template>
        </ul>
      </div>
    </div>
    <!--    工具与资讯-->
    <div class="map-ToolsInformation">
      <span>工具与资讯</span>
    </div>
    <div class="map-Tools">
      <div class="map-PatentBusiness">
        <p><img
            src="../assets/images/map/3.png"
            alt=""
            class="xiaologo"
          > 智能工具</p>
        <ul class="map">
          <li>
            <a
              target="_blank"
              href="/shangbiao/freesearch"
            >智能搜索</a>
          </li>
          <li>
            <a
              target="_blank"
              href="/sbzc"
            >注册成功率测算</a>
          </li>
          <li>
            <a
              target="_blank"
              href="/show/navigation_trademark_register_04.html"
            >智能自助注册</a>
          </li>
          <li>
            <a
              target="_blank"
              href="/classify"
            >商标分类查询</a>
          </li>
          <li>
            <a
              target="_blank"
              href="/brandmanage"
            >商标管理</a>
          </li>
          <li>
            <a
              target="_blank"
              href="/notice"
            >初审公告</a>
          </li>
        </ul>
      </div>
      <div class="map-CopyrightBusiness">
        <p><img
            src="../assets/images/map/4.png"
            alt=""
            class="xiaologo"
          > 知产社区</p>
        <ul class="map">
          <li v-for="(item, index) in type" :key="item" >
            <a target="_blank" :href="'/xw/'+index+'.html'" >{{item}}</a>
          </li>
        </ul>
      </div>
    </div>
    <!--    其他-->
    <div class="map-Other">
      <span>其他</span>
    </div>
    <div class="map-OT">
      <div class="map-PatentBusiness">
        <p><img
            src="../assets/images/map/5.png"
            alt=""
            class="xiaologo"
          > 服务支持</p>
        <ul class="map">
          <li>
            <a href="/about/4.html">常见问题</a>
          </li>
          <li>
            <a href="/about/5.html">服务说明</a>
          </li>
          <li>
            <a href="/about/6.html">模板下载</a>
          </li>
          <li>
            <a
              href="javascript:;"
              onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)"
            >客户服务</a>
          </li>
          <li>
            <a
              href="javascript:;"
              onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)"
            >咨询反馈</a>
          </li>
        </ul>
      </div>
      <div class="map-CopyrightBusiness">
        <p><img
            src="../assets/images/map/6.png"
            alt=""
            class="xiaologo"
          >知千秋介绍</p>
        <ul class="map map-Business">
          <li>
            <a href="/about/1.html">关于我们</a>
          </li>
          <li>
            <a href="/about/2.html">加入我们</a>
          </li>
          <li>
            <a href="/about/3.html">联系我们</a>
          </li>
          <li>
            <a href="/about/7.html">售后服务</a>
          </li>
          <li>
            <a href="/about/8.html">用户协议</a>
          </li>
          <li>
            <a href="/about/9.html">官费明细</a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>
<script>
import mainHead from "~/components/head/mainHead";
import { openNewPage } from "~/assets/js/common.js";
import fixNav from "~/components/fixNav";
import minNav from "~/components/minNav";
import { Http } from "~/plugins/axios.js";
import axios from "axios";
import popup from "~/components/popup";
let img0 = require("../assets/images/map/0.png");
let img1 = require("../assets/images/map/1.png");
let img2 = require("../assets/images/map/2.png");
export default {
  data() {
    return {};
  },
  head() {
    return {
      title: "网站地图-知千秋",
      meta: [
        {
          name: "keywords",
          hid: "keywords",
          content:
            "注册商标查询，中国商标官网查询，商标搜索，商标检索,知千秋,权大师"
        },
        {
          name: "description",
          hid: "description",
          content:
            "免费精准的商标查询平台,中国更全的商标信息库,精准智能的商标相似查询结果,为商标申请人降低商标驳回风险,更快,更准的定位商标注册成功概率。知千秋"
        }
      ],
      link: [
        {
          rel: "canonical",
          content: this.baseUrl
        }
      ]
    };
  },
  components: {
    mainHead,
    minNav,
    fixNav
  },
  async asyncData({ params, redirect, error, env, route }) {
    return Promise.all([
      axios.get(`${env.baseUrl}navigation/getTreeData`),
      axios.get(`${env.baseUrl}/news/type`)
    ]).then(res => {
      let { data } = res[0];
      if (data.code == 0) {
        return {
          mapData: data.data,
          type: res[1].data.data,
          imgs: [img0, img1, img2],
          baseUrl: env.webSite + route.fullPath
        };
      } else {
        error({ statusCode: 500, message: data.msg });
      }
    });
  },
  mounted() {},
  methods: {
    getUrl(code, id) {
      let url = "/",
        obj = "";
      switch (code) {
        case "navigation_trademark":
          url = "/extension";
          break; //商标业务
        case "navigation_trademark_query_01":
          url = "/shangbiao/freesearch";
          break; //免费商标查询
        case "navigation_patent":
          url = "/patent";
          break; //专利业务
        case "navigation_copyright":
          url = "/copyright";
          break; //版权业务
        default:
          url = "/show";
          obj = { code: code, id: id };
          break;
      }
      openNewPage(this, url, obj);
    }
  }
};
</script>

<style scoped>
.border {
  border: 0 !important;
}
.site-map {
  width: 1240px;
  margin: 0 auto;
}
.site ul {
  margin-top: 24px;
}
a {
  color: #666666;
}
a:hover {
  color: #ff7200;
}
.list a {
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: #ff7200;
  letter-spacing: 0.19px;
  line-height: 14px;
  text-align: left;
}
.map {
  margin-left: 46px;
}
/*知产服务*/
.map-knowledge {
  margin-top: 34px;
}
.map-knowledge span {
  padding-left: 8px;
  border-left: 5px solid #ff7200;
  font-family: MicrosoftYaHei;
  font-size: 18px;
  color: #222222;
  letter-spacing: 0;
}
.map-TrademarkBusiness {
  overflow: hidden;
  border-bottom: 1px solid #dddddd;
  padding-bottom: 30px;
}
.map-TrademarkBusiness p {
  margin-top: 20px;
  margin-left: 20px;
  font-family: MicrosoftYaHei;
  font-size: 16px;
  color: #ff7200;
  letter-spacing: 0;
  text-align: left;
}
.map-TrademarkBusiness li {
  float: left;
  margin-top: 20px;
  width: 170px;
  align: center;
}
.map-PatentBusiness {
  overflow: hidden;
  border-bottom: 1px solid #dddddd;
  padding-bottom: 30px;
}
.map-PatentBusiness p {
  margin-top: 17px;
  margin-left: 20px;
  font-family: MicrosoftYaHei;
  font-size: 16px;
  color: #ff7200;
  letter-spacing: 0;
  text-align: left;
}
.map-PatentBusiness li {
  float: left;
  margin-top: 20px;
  width: 170px;
  align: center;
}
.map-CopyrightBusiness {
  overflow: hidden;
}
.map-CopyrightBusiness p {
  margin-top: 26px;
  margin-left: 20px;
  font-family: MicrosoftYaHei;
  font-size: 16px;
  color: #ff7200;
  letter-spacing: 0;
  text-align: left;
}
.map-CopyrightBusiness li {
  float: left;
  margin-top: 20px;
  width: 170px;
  align: center;
}
.map-Know {
  margin-top: 14px;
  background: #f9f9f9;
  width: 1200px;
}
/*工具与资讯*/
.map-ToolsInformation {
  margin-top: 30px;
}
.map-ToolsInformation span {
  padding-left: 8px;
  border-left: 5px solid #ff7200;
  font-family: MicrosoftYaHei;
  font-size: 18px;
  color: #222222;
  letter-spacing: 0;
}
.map-Tools {
  margin-top: 14px;
  background: #f9f9f9;
  width: 1200px;
  height: 210px;
}
/*其他*/
.map-Other {
  margin-top: 65px;
}
.map-Other span {
  padding-left: 8px;
  border-left: 5px solid #ff7200;
  font-family: MicrosoftYaHei;
  font-size: 18px;
  color: #222222;
  letter-spacing: 0;
}
.map-OT {
  margin-top: 14px;
  margin-bottom: 51px;
  background: #f9f9f9;
  width: 1200px;
  height: 210px;
}
/*图片大小设置*/
.xiaologo {
  width: 17px;
  height: 17px;
  vertical-align: -3px;
}
</style>
