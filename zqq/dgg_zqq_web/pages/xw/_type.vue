<template>
  <div class="content">
    <div class="news-lists-wrap">
      <div class="news-lists">
        <!-- start mytab -->
        <div class="mytab">
          <ul class=" mytap-header">
            <!-- <li class="list" :class="{'selected': !seletedType}">
                            <a href="/xw">全部</a>
                        </li> -->
            <li class="list" :class="{'selected': seletedType == item.code}" v-for="(item,index) in type" :key="index">
              <a :href="'/xw/'+ item.code+'.html'" rel="nofollow">{{item.name}}</a>
            </li>
          </ul>
          <div class="mytap-body">
            <!-- content 全部 -->
            <div class="content selected">
              <p class="noData" v-if="!total">暂无相关数据</p>
              <ul class="item" v-if="total">
                <li class="list" v-for="item in list" :key="item.number">
                  <!-- <div class="list-l">
                    <span class="time-day">{{item.createTime.substr(8, 2)}}</span>
                    <span class="time-month">{{item.createTime.substr(0, 7)}}</span>
                  </div> -->
                  <div class="list-m">
                    <a :href="'/xwxq/'+item.number+'.html'" target="_black"><img :src="item.newsPhoto" :onerror="errorImg"
                        :title="item.title" :alt="item.title" /></a>
                  </div>
                  <div class="list-r">
                    <h3>
                      <a :href="'/xwxq/'+''+item.number+'.html'" target="_black" class="col">{{item.title}}</a>
                    </h3>
                    <p>
                      <a class="a1" :href="'/xwxq/'+''+item.number+'.html'" target="_black" v-html="item.content" rel="nofollow"></a>
                      <a :href="'/xwxq/'+''+item.number+'.html'" rel="nofollow" target="_black">［详细］</a>
                    </p>
                    <div class="addFlag">
                      <span>时间:{{item.createTime}}</span>
                      <span>来源：{{item.origin}}</span>
                      <span>标签：<i v-for="(item,idx) in splitFlag(item.label)" :key='idx'>{{item}}</i></span>
                      <span>阅读量：{{item.viewTimes}}</span>
                    </div>
                    <span class="t" v-if="0">{{item.typeLevel1Name}}</span>
                  </div>
                </li>
              </ul>
              <!-- <div class="myloading">
                            <em class="icon icon-loading"></em>
                            <span class="text">内容加载中</span>
                        </div> -->
              <!-- start 分页 -->
              <div class="paging">
                <pagination :totalPage="total" :pageNum="pageNum" :pageSize="pageSize" @pagechange="pagechange" />
              </div>
              <!-- end 分页 -->
            </div>
            <!-- end content 全部 -->
          </div>
          <!-- end mytab -->
        </div>
      </div>
    </div>
    <to-top />
  </div>
</template>
<script>
  import toTop from "@/components/toTop";
  import axios from "axios";
  import pagination from "~/components/pagination.vue";
  import img from "~/assets/images/default_new.jpg";

  export default {
    async asyncData({
      params,
      redirect,
      error,
      env,
      store,
      route
    }) {
      let type = "";
      let pageSize = 10;
      let pageNum = 1;
      let seletedTypeObj = "";
      if (!params.type) {
        type = "";
        seletedTypeObj = {
          name: "",
          ext1: 0
        };
      } else {
        let paramsTemp = params.type.split(".")[0];
        pageNum = paramsTemp.split(",")[1] || 1;
        type = paramsTemp.split(",")[0] || "";
        if (type == "all") {
          type = "";
          seletedTypeObj = {
            name: "",
            ext1: 0
          };
        }
      }

      return Promise.all([
          axios.post(
            `${
          env.baseUrl
        }/news/all?type=${type}&pageSize=${pageSize}&pageNum=${pageNum}`
          ),
          axios.get(`${env.baseUrl}/news/type2`)
        ])
        .then(res => {
          let {
            data
          } = res[0];
          // 不存在数据
          res[1].data.data.map(item => {
            if (item.code == type) {
              seletedTypeObj = item;
            }
          });

          let titleStr =
            "最新【知识产权新闻】最新商标、专利、版权商标资讯-知千秋官网",
            metaArr = [{
                name: "keywords",
                hid: "keywords",
                content: "知识产权新闻,最新知识产权新闻,知识产权新闻网,知识产权商标资讯"
              },
              {
                name: "description",
                hid: "description",
                content: "【知识产权新闻】栏目，深挖知识产权商标资讯热点,收集最新最新商标、专利、版权新闻,为您提供有深度有独特见解的知产新闻信息。"
              }
            ];

          if (type == "first_sound") {
            (titleStr = "知识产权政策法规-知千秋"),
            (metaArr = [{
                name: "keywords",
                hid: "keywords",
                content: "知识产权政策,知识产权政策"
              },
              {
                name: "description",
                hid: "description",
                content: "知千秋【知识产权政策】栏目，收集最新知识产权商标资讯,为您提供有深度有独特见解的知产政策解读。"
              }
            ]);
          } else if (type == "knowledge") {
            (titleStr = "知识产权知识_知识产权相关知识_知识产权技巧-知千秋"),
            (metaArr = [{
                name: "keywords",
                hid: "keywords",
                content: "知识产权知识,知识产权相关知识,知识产权技巧"
              },
              {
                name: "description",
                hid: "description",
                content: "【知识产权知识】栏目，为您整理，实用知识产权相关知识信息提供；有用的知识产权相关技巧与方法。"
              }
            ]);
          } else if (type == "data_report") {
            (titleStr =
              "知识产权报告_中国知识产权报告_中国知识产权排名_知识产权数据统计-知千秋"),
            (metaArr = [{
                name: "keywords",
                hid: "keywords",
                content: "知识产权报告,中国知识产权报告,中国知识产权排名,知识产权数据"
              },
              {
                name: "description",
                hid: "description",
                content: "【知识产权报告】栏目为您提供：各类中国知识产权报告，知识产权行业数据统计类排名信息等。"
              }
            ]);
          }

          return {
            list: data.data.count ? data.data.data : [],
            total: data.data.count ? data.data.count : 0,
            pageNum: Number(pageNum),
            pageSize,
            seletedTypeObj,
            type: res[1].data.data,
            seletedType: type,
            baseUrl: env.webSite + route.fullPath,
            titleStr,
            metaArr
          };
        })
        .catch(() => {
          error({
            statusCode: 500,
            message: "参数错误"
          });
        });
    },
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
    data() {
      return {
        errorImg: 'this.src="' + img + '"'
      };
    },
    components: {
      toTop,
      pagination
    },
    mounted() {
      // console.log(this.list);
    },
    methods: {
      pagechange(page) {
        let type = "all";
        if (this.$route.params.type) {
          type = this.$route.params.type.split(".")[0].split(",")[0];
        }
        if (page === 1) {
          this.$router.push(`/xw/${type}.html`);
        } else {
          this.$router.push(`/xw/${type},${page}.html`);
        }
      },
        splitFlag(str) {
          let newStr = (str || "").replace(/ /g, "");
          return newStr ? newStr.split(/，|-| /) : [];
        }
    }
  };

</script>
<style scoped>
  .noData {
    text-align: center;
    color: #999;
    font-size: 14px;
    border-top: 1px solid #eee;
    padding-top: 30px;
  }

  .news-lists-wrap .news-lists {
    width: 100%;
  }

  .news-lists-wrap .news-lists {
    width: 1240px;
    margin: auto;
  }

  .news-lists-wrap .mytab .mytap-header {
    margin-top: 40px;
  }

  .news-lists-wrap .mytab .mytap-header .list {
    margin-right: 0;
  }

  /* .news-lists-wrap .mytab .mytap-header .list a {
    background-color: #f5f5f5;
  } */

  .mytab .mytap-header .list a {
    -webkit-transition: 0.3s;
    -moz-transition: 0.3s;
    -ms-transition: 0.3s;
    -o-transition: 0.3s;
    transition: 0.3s;
  }

  .news-lists-wrap .mytab .mytap-body .content {
    padding-top: 25px;
  }

  .news-lists-wrap .mytab .mytap-body .content .list {
    *zoom: 1;
    padding: 20px 0;
    border-bottom: dotted 1px #ebebeb;
  }

  .news-lists-wrap .mytab .mytap-body .content .list:after {
    content: "";
    display: block;
    clear: both;
    height: 0;
    visibility: hidden;
  }

  .news-lists-wrap .mytab .mytap-body .content .list .list-l,
  .news-lists-wrap .mytab .mytap-body .content .list .list-m,
  .news-lists-wrap .mytab .mytap-body .content .list .list-r {
    display: block;
    float: left;
  }

  .news-lists-wrap .mytab .mytap-body .content .list .list-l {
    width: 92px;
    height: 135px;
    text-align: center;
    color: #dbdbdb;
    cursor: default;
  }

  .news-lists-wrap .mytab .mytap-body .content .list .list-l .time-day {
    display: block;
    font-size: 60px;
    font-weight: bold;
    line-height: 55px;
    margin: 25px 0 0;
  }

  .news-lists-wrap .mytab .mytap-body .content .list .list-l .time-month {
    display: block;
    font-size: 18px;
    line-height: 28px;
  }

  .news-lists-wrap .mytab .mytap-body .content .list .list-m {
    width: 235px;
    height: 135px;
    margin: 0 35px 0 10px;
    background-color: #f08c2f;
    overflow: hidden;
  }

  .news-lists-wrap .mytab .mytap-body .content .list .list-m img {
    width: 100%;
    height: 100%;
    -webkit-transition: 0.4s;
    -moz-transition: 0.4s;
    -ms-transition: 0.4s;
    -o-transition: 0.4s;
    transition: 0.4s;
  }

  .news-lists-wrap .mytab .mytap-body .content .list .list-r {
    width: 940px;
    height: 150px;
  }

  .news-lists-wrap .mytab .mytap-body .content .list .list-r h3 a {
    font-size: 20px;
    font-weight: bold;
    /* color: #555; */
  }

  .news-lists-wrap .mytab .mytap-body .content .list .list-r p {
    padding: 12px 0 0;
    height: 80px;
    line-height: 27px;
    overflow: hidden;
    color: #999;
    font-size: 14px;
    box-sizing: content-box;
  }

  .news-lists-wrap .mytab .mytap-body .content .list .list-r p .a1 {
    color: #999;
  }

  .news-lists-wrap .mytab .mytap-body .content .list .list-r p a {
    color: #f08c2f;
  }

  .news-lists-wrap .mytab .mytap-body .content .list .list-r .t {
    color: #f08c2f;
    font-size: 12px;
  }

  .news-lists-wrap .mytab .mytap-body .content .list:hover .list-l {
    color: #f08c2f;
  }

  .news-lists-wrap .mytab .mytap-body .content .list .list-m:hover img {
    -webkit-transform: scale(1.1);
    -moz-transform: scale(1.1);
    -ms-transform: scale(1.1);
    -o-transform: scale(1.1);
    transform: scale(1.1);
  }

  .news-lists-wrap .mytab .mytap-body .content .list:hover .list-l {
    color: #f08c2f;
  }

  .news-lists-wrap .mytab .mytap-body .content .list:hover .list-r h3 a {
    color: #f08c2f;
  }

  .mytab .mytap-header {
    overflow: hidden;
    border-bottom: solid 1px #eee;
  }

  .mytab .mytap-header .list {
    float: left;
    margin-left: -1px;
  }

  .mytab .mytap-header .list a {
    display: block;
    /* padding: 0 15px; */
    height: 50px;
    line-height: 50px;
    text-align: center;
    color: #555;
    width: 120px;
    font-size: 16px;
    /* border: solid 1px #eee; */
  }

  .mytab .mytap-header .list a:hover {
    color: #f08c2f;
    /* background-color: #f08c2f;
    border: solid 1px #f08c2f; */
  }

  .mytab .mytap-header .list.selected a {
    color: #f08c2f;
    font-size: 20px;
    border-bottom: 3px solid #f08c2f;
    /* background-color: #f08c2f;
    border: solid 1px #f08c2f; */
  }

  .mytab .mytap-body .content {
    display: none;
  }

  .mytab .mytap-body .content.selected {
    display: block;
  }
  .addFlag{
    margin-top: 10px;
    color: #999;
  }
  .addFlag span{
      display: inline-block;
      margin-right: 40px;
  }
  .addFlag span i{
    margin-left: 15px;
  }
</style>
