<template>
  <div class="pagePatentSearch">
    <div class="banner">
      <img
        src="../../assets/images/gs/banner.jpg"
        alt="公司注册"
      >
      <div class="title">
          <h1>想创业  就上智企名</h1>
          <p>树立企业可信形象，更多信任，更多交易</p>
      </div>
      <Search />
    </div>
    <section class="listBox">
      <div class="head">
        <h2>{{json.name}}</h2>
      </div>
      <div class="wrapper">
        <ul>
          <li
            v-for="(item,idx) in navArr"
            :key='idx' :class="{'active':idx == 0}"
          >
            <a :href="item.url" >{{item.name}}</a>
          </li>
        </ul>
      </div>
    </section>
    <section
      class="wrapper item"
      id='base'
    >
      <p>基本信息</p>
      <table class="cler">
        <tbody>
          <tr>
            <td
              rowspan="3"
              width='200px'
            >
              <img
                :src="json.logo ? json.logo : ''"
                :onerror='errorImg'
                alt=""
                style="max-width:220px"
              >
            </td>
            <td>电话：{{json.tel}}</td>
            <td>官网：{{json.qygsDetail.contactInfoList ? (json.qygsDetail.contactInfoList)[0].webSiteUrl || "暂无" :"暂无"}}</td>
          </tr>
          <tr>
            <td>邮箱：{{json.email || "暂无"}}</td>
            <td>地址：{{json.qygsDetail.address || "暂无"}}</td>
          </tr>
          <tr>
            <td colspan="2">
              公司简介：{{json.briefIntroductionInfo ? json.briefIntroductionInfo.Content : "暂无"}}
            </td>
          </tr>
        </tbody>
      </table>
    </section>
    <section
      class="wrapper item"
      id='ic'
    >
      <p>工商信息</p>
      <table class="ntable">
        <tbody v-if='base'>
         <tr>
              <td width="20%" class="tb">注册资本：</td>
              <td width="30%" class="">{{base.registCapi ? base.registCapi+"万人民币元" : "-" }}</td>
              <td width="20%" class="tb">实缴资本：</td>
              <td width="30%" class=""> {{base.realCapi ? base.realCapi+"万人民币元" : "-" }}</td>
          </tr>
            <tr>
                <td class="tb">经营状态：</td>
                <td class=""> {{base.status || "-"}} </td>
                <td class="tb" width="18%">成立日期：</td>
                <td class="">{{base.startDate || "-"}}</td>
            </tr>
            <tr>
                <td class="tb">统一社会信用代码：</td>
                <td class="">
                    {{base.creditCode}}
                </td>
                <td class="tb">纳税人识别号：</td>
                <td class="">
                      {{base.creditCode}}
                </td>
            </tr>
            <tr>
                <td class="tb">注册号：</td>
                <td class="">
                    {{base.no || "-"}}
                </td>
                <td class="tb" width="15%">组织机构代码：</td>
                <td class="">
                    {{base.orgNo || "-"}}
                </td>
            </tr>
            <tr>
                <td class="tb">公司类型：</td>
                <td class="">
                      {{base.econKind || "-"}}
                </td>
                <td class="tb">所属行业：</td>
                <td class="">
                    {{base.industry || "-"}}
                </td>
            </tr>
            <tr>
                <td class="tb">核准日期：</td>
                <td class="" style="max-width:301px;">
                      {{base.checkDate || "-"}}
                </td>
                <td class="tb">登记机关：</td>
                <td class="">
                    {{base.belongOrg || "-"}}
                </td>
            </tr>
            <tr>
                <td class="tb">所属地区：</td>
                <td class="" style="max-width:301px;">
                      {{base.province || "-"}}
                </td>
                <td class="tb">英文名：</td>
                <td class="">
                      {{base.englishName || "-"}}
                </td>
            </tr>
            <tr>
                <td class="tb">
                    曾用名
                </td>
                <td class="">
                    {{base.originalName || "-"}}
                </td>
                <td class="tb">
                    参保人数
                </td>
                <td class="">
                      {{base.cbPersonNum || "-"}}
                </td>
            </tr>
            <tr>
                <td class="tb">
                    人员规模
                </td>
                <td class="">
                      {{base.personNum || "-"}}
                </td>
                <td class="tb">
                    营业期限
                </td>
                <td class="">
                    {{base.termStart}} - {{base.teamEnd}}
                </td>
            </tr>
            <tr>
                <td class="tb">企业地址：</td>
                <td class="" colspan="3">
                    {{base.address}}
                    <!-- <a onclick="showMapModal('中山市横栏镇新茂工业区康龙三路32号4楼之一','')" class="m-l c_a"> 查看地图</a>
                    <a data-href="/map?keyNo=7e652fc34541b7cdf0b354e5a444b363" class="m-l c_a"> 附近公司</a> -->
                </td>
            </tr>
            <tr>
                <td class="tb">经营范围：</td>
                <td class="" colspan="3">
                    {{base.scope}}
                </td>  
            </tr>
        </tbody>
         <tbody v-else>
          <tr>
            <td colspan="6" class="cen">暂无信息</td>
          </tr>
        </tbody>
      </table>
    </section>
    <section
      class="wrapper item"
      id='shareholder'
    >
      <p>股东信息</p>
      <table class="ntable ntable-odd">
        <thead>
          <tr>
            <th class="tx">序号</th>
            <th width="">股东（发起人）</th>
            <th width="200">持股比例</th>
            <th width="200">认缴出资额(万元)</th>
            <th width="200">认缴出资日期</th>
          </tr>
        </thead>
        <tbody  v-if='base.partnersList && base.partnersList.length > 0'>
          <tr v-for="(item,index) in base.partnersList" :key="index">
              <td class="tx">{{index+1}}</td>
              <td class="cen">
                 {{item.stockName}}
              </td>
              <td class="text-center">{{item.stockPercent || "-"}} </td>
              <td class="text-center">{{item.shouldCapi.replace(/[^\d^.]/g, '') || "-"}}</td>
              <td class="text-center">{{item.shoudDate || "-"}}</td>
          </tr>
        </tbody>
        <tbody v-else>
          <tr>
            <td colspan="6" class="cen">暂无信息</td>
          </tr>
        </tbody>
      </table>
    </section>
    <section
      class="wrapper item"
      id='mainer'
    >
      <p>主要人员</p>
      <table class="ntable ntable-odd">
        <thead>
          <tr>
            <th class="tx">序号</th>
            <th>姓名</th>
            <th>职务</th>
          </tr>
        </thead>
        <tbody v-if='base.employeesList && base.employeesList.length > 0'>
          <tr v-for="(item,index) in base.employeesList" :key='index'> 
              <td class="tx">{{index+1}}</td>
              <td width="50%" class="cen">
                  <a class="c_a cen">{{item.name}}</a>
              </td>
              <td class="text-center cen">
                  {{item.job}}
              </td>
          </tr>
        </tbody>
         <tbody v-else>
          <tr>
            <td colspan="6" class="cen">暂无信息</td>
          </tr>
        </tbody>
      </table>
    </section>
    <section
      class="wrapper item"
      id='invest'
    >
      <p>对外投资分析</p>
      <table class="ntable ntable-odd">
        <thead>
          <tr>
            <th width="300">被投资企业名称</th>
            <th width="320">被投资法定代表人</th>
            <th>注册资本(万人民币元)</th>
            <th>出资比例%</th>
            <th>成立日期</th>
            <th>状态</th>
          </tr>
        </thead>
          <tbody v-if='json.qyzp && json.qyzp.length > 0'>
            <tr v-for="(item,index) in json.qyzp" :key='index'> 
              <td class="tx"><a >{{item.name}}</a></td>
              <td width="160" class="cen">
                  <a class="c_a" >{{item.operName}}</a>
              </td>
              <td class="cen">
                  {{item.registCapi}}
              </td>
              <td class="cen">
                  {{item.fundedRatio}}
              </td>
              <td class="cen">
                  {{item.startDate}}
              </td>
               <td class="cen">
                  {{item.status}}
              </td>
          </tr>
        </tbody>
        <tbody v-else>
          <tr>
            <td colspan="6" class="cen">暂无信息</td>
          </tr>
        </tbody>
      </table>
    </section>
    <section
      class="wrapper item"
      id='update'
    >
      <p>变更记录</p>
      <table class="ntable">
        <thead>
          <tr>
            <th class="tx">序号</th>
            <th>变更日期</th>
            <th>变更项目</th>
            <th>变更前</th>
            <th>变更后</th>
          </tr>
        </thead>
        <tbody v-if='base.changeRecordsList && base.changeRecordsList.length > 0'>
         <tr v-for="(item,index) in base.changeRecordsList" :key="index">
              <td class="tx">{{index+1}}</td>
              <td width="103" class="text-center">{{item.changeDate}}</td>
              <td width="" class="text-center">
                  {{item.projectName}}
              </td>
              <td width="30%" class="cen">
                  <div style="max-width: 300px;" v-html="hasEm(item.beforeContent)"></div>
              </td>
              <td width="30%" class="cen">
                  <div style="max-width: 300px;" v-html="hasEm(item.afterContent)"></div>
              </td>
          </tr>
        </tbody>
        <tbody v-else>
          <tr>
            <td colspan="5" class="cen">暂无信息</td>
          </tr>
        </tbody>
      </table>
    </section>
    <div class="foot wrapper">
      数据来源：国家企业信用信息公示系统 国家版权局 国家工商行政管理总局 国家知识产权局
    </div>
  </div>
</template>

<script>
import Page from "@/components/page";
import Search from "@/components/search";
import img from "@/assets/images/default.jpg";
export default {
  middleware: "auth",
  async asyncData({ $axios, params }) {
    return $axios
      .$get(`company/searchSimpleDetail?companyId=${params.id}`)
      .then(res => {
        let data = res.data[0];
        return {
          json: data,
          base: data.qygsDetail
        };
      })
      .catch(msg => {
        console.log(msg);
      });
  },
  data() {
    return {
      errorImg: `this.src="${img}"`,
      isLoad: false,
      navArr: [
        { name: "基本信息", url: "#base" },
        { name: "工商信息", url: "#ic" },
        { name: "股东信息", url: "#shareholder" },
        { name: "主要成员", url: "#mainer" },
        { name: "对外投资分析", url: "#invest" },
        { name: "变更信息", url: "#update" }
      ]
    };
  },
  components: { Page, Search },
  mounted() {
    // console.log(this.json);
  },
  methods: {
    hasEm(str){
          if(str){
              return str.replace(/退出/g,"<em>退出</em>").replace(/新增/g,"<em>新增</em>")  
          }
          return str    
    },
  }
};
</script>
 
<style lang="stylus" scoped>
@import '../../assets/css/params.styl';

* {
  box-sizing: border-box;
}

.pagePatentSearch {
  overflow: hidden;
  min-width: 1200px;
  width: 100%;
  background: #fff;
  padding-bottom: 80px;
}

.banner {
  width: 1920px;
  position: relative;
  left: 50%;
  margin-left: -960px;
  height: 420px;

  >img {
    height: 100%;
  }

  >.title {
    width: 1035px;
    position: absolute;
    left: 50%;
    margin-left: -517.5px;
    top: 120px;
    text-align: center;
    color: #fff;

    h1 {
      font-size: 48px;
      margin-bottom: 20px;
      font-weight: 100;
    }
    p {
      font-size: 24px;
    }
  }

}

.listBox {
  padding-top: 200px;
  text-align: center;
  width: 1200px;
  margin: auto;
  background: #fff;

  >.head {
    height: 46px;
    line-height: 46px;
    position: relative;
    display: inline-block;
    padding: 0 20px;
    margin: auto;
    margin-bottom: 80px;

    h2 {
      font-size: 36px;
      font-weight: 100;
      color: #777;
    }

    &::before {
      content: ' ';
      position: absolute;
      height: 100%;
      width: 4px;
      left: 0;
      top: 0;
      border: 1px solid #888;
      border-right: none;
    }

    &::after {
      content: ' ';
      position: absolute;
      height: 100%;
      width: 4px;
      right: 0;
      top: 0;
      border: 1px solid #888;
      border-left: none;
    }
  }

  ul {
    overflow: hidden;
    background: #f1f1f1;

    li {
      float: left;
      width: 16.666%;
      text-align: center;
      line-height: 66px;
      font-size: 24px;

      a {
        color: #333;
      }

      &.active, &:hover {
        background: $maincolor;

        a {
          color: #fff;
        }
      }
    }
  }
}


.item {
  margin-top: 20px;

  p {
    background: $maincolor;
    line-height: 40px;
    height: 40px;
    font-size: 16px;
    padding-left: 10px;
    color: #fff;
  }

  table {
    margin-top: 10px;
    width: 100%;
    border-collapse: collapse;
    border-spacing: 0;

    &.cler {
      td {
        border: none;
      }
    }

    th {
      background: #f2f9fc;
      border: 1px solid #e4eef6;
      border-collapse: collapse;
      padding: 10px 12px;
      font-weight: 400;
      color: #444;
      line-height: 19px;

      &.tx {
        width: 59px;
        text-align: center;
      }
    }

    td {
      padding: 12px 10px;
      border: 1px solid #e4eef6;
      word-break: break-all;
      font-size: 14px;
      line-height: 19px;
      color: #222;
      &.cen{
         text-align center 
        }
      &.tx {
        width: 59px;
        text-align: center;
      }

      &.tb {
        background: #f2f9fc;
      }
    }
  }
}

.foot {
  padding-left: 10px;
  height: 76px;
  line-height: 76px;
  color: #888;
}
</style>
