<template>
  <div class="content">
    <div class="message-page order-page">
      <h2 class="order-main-title">消息</h2>
      <h3 v-if="!msgList.length" class="joinJiGou-wrap">暂无消息</h3>
      <!-- start 分页 -->
      <table width="100%" v-if="msgList.length" :key="item.id" class="table" v-for="item in msgList">
        <caption>
          <h6>
            <span class="column-1">{{item.title}}
              <i v-if="!item.read"></i>
            </span>
            <span class="time">{{item.createTime}}</span>
            <a class="iconfont icon-shanchu" @click="deleMsg(item.id, $event)">&#xe610;</a>
            <a class="iconfont icon-red" v-if="!item.read" @click="readMsg(item.id, $event)">&#xe661;</a>

          </h6>
        </caption>
        <tbody>
          <tr>
            <td>
              <a class="hover" href="javascript:;">{{item.content}}</a>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="myPaging">
        <pagination :totalPage="total" :pageNum="pageNum" :pageSize="pageSize" @pagechange="loadMsg" />
      </div>
    </div>
  </div>
</template>
<script>
import popup from "~/components/popup"; //消息
import pagination from "~/components/pagination.vue";
export default {
    head() {
        return {
            title: "个人中心-知千秋官网",
            meta: [
                {
                    name: "keywords",
                    hid: "keywords",
                    content:
                        "商标注册，商标免费查询，商标注册查询，商标注册流程及费用，中国商标网，专利申请，专利检索，发明专利"
                },
                {
                    name: "description",
                    hid: "description",
                    content:
                        "知千秋致力于知千秋致力于运营大数据技术、人工智能等技术重新定义知识产权生态链。提供商标查询,商标注册,专利申请,专利查询,版权登记全流程服务。提供免费商标查询Saas工具,智能商标注册0服务费省钱到底,商标查询,商标注册监控,专利申请等服务获得客户的高度认可。"
                }
            ]
        };
    },
    layout: "center",
    components: {
        pagination
    },
    data() {
        return {
            msgList: "",
            total: 0,
            pageNum: 1,
            currpage: 1,
            pageSize: 10
        };
    },
    mounted() {
        this.loadMsg(1);
    },
    methods: {
        loadMsg(page) {
            if (page) {
                this.currpage = page;
            }
            this.$Http(
                "/msg/all",
                {
                    userId: this.$store.state.userInfo.userId,
                    pageSize: this.pageSize,
                    pageNum: this.currpage
                },
                "get",
                true
            ).then(res => {
                this.msgList = res.data.data.data;
                this.total = res.data.data.count;
            });
        },
        readMsg(id, e) {
            e.stopPropagation();
            this.$Http(
                "/msg/readed/" + id,
                { userId: this.$store.state.userInfo.userId },
                "get",
                true
            ).then(res => {
                this.loadMsg();
            });
        },
        deleMsg(id, e) {
            e.stopPropagation();
            this.$Http(
                "/msg/delete/" + id,
                { userId: this.$store.state.userInfo.userId },
                "get",
                true
            ).then(res => {
                this.loadMsg();
            });
        },
        evalOrder() {}
    }
};
</script>
<style scoped>
.joinJiGou-wrap {
    margin-top: 50px;
    text-align: center;
    font-size: 14px;
    color: #999;
    font-weight: normal;
}
.color {
    color: #ff7200;
}
.order-main-title {
    margin-bottom: 15px;
    font-size: 16px;
    line-height: 2.8em;
    font-weight: normal;
    border-bottom: 1px solid #e2e3e8;
}
.order-page {
    padding: 20px;
    padding-top: 0px;
    min-height: 700px;
    background-color: #fff;
    box-shadow: 0 4px 6px 0 rgba(113, 114, 119, 0.1);
}
.order-page .no-pay-tips {
    margin-top: -10px;
    margin-bottom: 15px;
    font-size: 12px;
    color: #999;
}
.order-page .no-pay-tips .icon {
    margin-right: 2px;
    width: 12px;
    height: 12px;
}
.order-page .no-pay-tips span {
    display: inline-block;
    vertical-align: middle;
}
.order-page .price-box {
    padding-right: 20px;
    text-align: right;
}
.order-page .price-box .button {
    margin-left: 20px;
    width: 120px;
    line-height: 32px;
    text-align: center;
}

/* ---------- start 消息页 ---------- */
.message-page {
}
.message-page .page-title {
    position: relative;
    padding-left: 8px;
    font-size: 16px;
    color: #000;
}
.message-page .table {
    border: solid 1px #e9e9e9;
    margin-top: 20px;
}
.message-page .table caption {
    padding: 0;
}
.message-page .table caption h6 {
    background-color: #eee;
    padding: 10px 10px;
    border: solid 1px #e9e9e9;
    border-bottom: 0;
    position: relative;
    text-align: left;
}
.message-page .table caption h6 .column-1 {
    color: #333;
    font-size: 14px;
    font-weight: normal;
}
.message-page .table caption h6 .column-1 i {
    background: #ff7200;
    display: inline-block;
    *display: inline;
    *zoom: 1;
    width: 10px;
    height: 10px;
    border-radius: 50%;
    font-size: 0;
}
.message-page .table caption h6 .time {
    font-weight: normal;
    display: block;
    float: right;
    margin-right: 60px;
}
.message-page .table caption h6 .iconfont {
    display: block;
    position: absolute;
    right: 15px;
    top: 12px;
    cursor: pointer;
}
.message-page .table caption h6 .iconfont.icon-red {
    vertical-align: middle;
    right: 40px;
    font-size: 18px;
    top: 8px;
}
.message-page .table td {
    border: 0;
    padding: 10px 10px;
    color: #888;
    font-size: 12px;
}
.message-page .table td a {
    color: #888;
}
.message-page .table td a:hover {
    color: #ff7200;
}
/* ---------- end 消息页 ---------- */
</style>
