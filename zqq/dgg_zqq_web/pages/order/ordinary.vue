<template>
  <div class="content myOrder-wrap w1200">
    <div class="section-myorder orderinfo-wrap width1200">
      <h3 class="cont-title">
        <i class="icon-leftBorder"></i>您将要购买的服务如下：</h3>
      <div class="cont-bodyer">
        <table class="table-list">
          <thead>
            <tr>
              <th>服务名称</th>
              <th>购买件数</th>
              <th>官费(元)</th>
              <th>服务费(元)</th>
              <th>服务总价(元)</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{{product?product.name: ''}}</td>
              <td>{{product?product.num:''}}</td>
              <td>{{((product?product.officialPrice:0 )* (product?product.num:'')).toFixed(2)}}</td>
              <td>{{((product?product.servicePrice:0) * (product?product.num:'')).toFixed(2)}}</td>
              <td>{{product?product.total:''}}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <h3 class="cont-title">
        <i class="icon-leftBorder"></i>订单联系人</h3>
      <div class="cont-bodyer clearfix">
        <table class="table-list" width="100%">
          <tbody>
            <tr>
              <td class="td-1" width="80px">
                <span style="color: red;">*</span>联系人</td>
              <td class="td-2">
                <input class="myInput" v-validate="'required'" v-model="contactName" name="name">
              </td>
            </tr>
            <tr>
              <td class="td-1">
                <span style="color: red;">*</span>联系电话</td>
              <td class="td-2">
                <input class="myInput" maxlength="11" v-validate="'required|phone'" v-model="contactPhone" name="phone">
              </td>
            </tr>
            <tr>
              <td class="td-1">
                <span style="color: red;">*</span>联系邮箱</td>
              <td class="td-2">
                <input class="myInput" v-validate="'required|email'" v-model="contactEmail" value="" name="email">
              </td>
            </tr>
            <tr>
              <td class="td-1" style="vertical-align: top;padding-top: 14px;">备注留言</td>
              <td class="td-2">
                <textarea class="myTextarea" maxlength="500" v-model="remark" style="height: 100px;" id="remark"></textarea>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <h3 class="cont-title">
        <i class="icon-leftBorder"></i>服务费用</h3>
      <div class="cont-bodyer clearfix">
        <div class="last-pay personal-last-pay">
          <ul class="list-box">
            <li class="row-officer">官费：￥
              <i>{{product?product.officialPrice:''}}</i>
            </li>
            <li class="row-service">服务费：￥
              <i>{{product?product.servicePrice:''}}</i>
            </li>
            <li class="row-sense">总价：
              <em>￥
                <i>{{product?product.total:''}}</i>
              </em>
            </li>
          </ul>
        </div>
        <div class="btns">
          <a class="btn-prev" @click="goBack" href="javascript:void(0);">上一步</a>
          <a class="btn-next submitOrder" @click="submit" href="javascript:;">提交订单</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import popup from "~/components/popup"; //消息

export default {
    middleware: "auth",
    head() {
        return {
            title: "普通订单-知千秋",
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
            ]
        };
    },
    components: {},
    methods: {
        goBack() {
            this.$router.go(-1);
        },
        submit() {
            console.log();
            if (!this.contactName) {
                popup.created({
                    content: "联系人必须填写",
                    time: 2000
                });
                return false;
            }

            if (!this.contactPhone) {
                popup.created({
                    content: "电话必须填写",
                    time: 2000
                });
                return false;
            }
            if (this.errors.has("phone")) {
                popup.created({
                    content: "请输入正确电话",
                    time: 2000
                });
                return false;
            }
            if (this.errors.has("email")) {
                popup.created({
                    content: "请输入正确邮箱",
                    time: 2000
                });
                return false;
            }
            if (!this.contactEmail) {
                popup.created({
                    content: "邮箱必须填写",
                    time: 2000
                });
                return false;
            }

            let obj = {
                userId: this.$store.state.userInfo.userId,
                serviceId: this.product.serviceId,
                serviceAttrId: this.product.serviceAttrId,
                allPrice: this.product.total,
                contactName: this.contactName,
                contactPhone: this.contactPhone,
                contactEmail: this.contactEmail,
                allNum: this.product.num,
                remark: this.remark
            };

            this.$Http("/order/saveNormalOrder", obj, "POST", true).then(
                res => {
                    if (res.data.code == 0) {
                        Cookies.remove("product");
                        this.$router.replace("/order/pay/" + res.data.data);
                    } else {
                        popup.created({
                            content: res.msg,
                            time: 2000
                        });
                    }
                }
            );
        }
    },
    data() {
        return {
            product: Cookies.getJSON("product"),
            contactName: "",
            contactPhone: "",
            contactEmail: "",
            remark: ""
        };
    }
};
</script>
<style scoped>
/** ---------- start 我的订单 ---------- **/
.myOrder-wrap .section-myorder {
    padding: 55px 140px 30px;
}
.myOrder-wrap .section-myorder .cont-title {
    font-size: 14px;
    font-weight: normal;
    color: #666;
}
.myOrder-wrap .section-myorder .cont-title .icon-leftBorder {
    display: block;
    float: left;
    width: 3px;
    height: 17px;
    background-color: #ff7200;
    border-radius: 1px;
    margin: 3px 8px 0 0;
}
.myOrder-wrap .section-myorder .cont-bodyer {
    margin-bottom: 20px;
    padding-top: 10px;
}
.myOrder-wrap .section-myorder .cont-bodyer .table-list td {
    color: #666;
    padding: 10px 0;
}
.myOrder-wrap .section-myorder .cont-bodyer .bill .list {
    display: inline-block;
    margin-right: 55px;
}
.myOrder-wrap .section-myorder .cont-bodyer .bill .list .color {
    color: #999;
}
/* 总费用 */
.myOrder-wrap .last-pay {
    text-align: left;
    padding: 20px 0px 0px;
}
.myOrder-wrap .last-pay li {
    padding: 4px 0;
    font-size: 16px;
    color: #666;
}
.myOrder-wrap .last-pay .row-sense {
    color: #ff7200;
}
.myOrder-wrap .last-pay .row-sense em {
    font-size: 16px;
    font-weight: bold;
}
.myOrder-wrap .last-pay .row-sense em i {
    font-weight: bold;
}
.myOrder-wrap .last-pay .row-step {
    padding-top: 15px;
}
.myOrder-wrap .last-pay .row-step .btn-next {
    margin-left: 25px;
}
table {
    width: 100%;
}
table thead th {
    font-size: 14px;
    background-color: #f7f8fb;
}
table tr {
    height: 2.4em;
}
table tbody tr:last-of-type {
    border-bottom: none;
}

.table-list td {
    padding: 14px 0;
    color: #666;
    font-size: 12px;
    border-bottom: dashed 1px #e9e9e9;
    text-align: center;
}
.table-list td a:hover {
    color: #f08b2f;
}

.myOrder-wrap .section-myorder .cont-bodyer .table-list td {
    border: 0;
}
.myOrder-wrap .section-myorder .cont-bodyer .table-list .td-1 {
    text-align: left;
}
.myOrder-wrap .section-myorder .cont-bodyer .table-list .td-2 {
    text-align: left;
}

.myInput {
    width: 350px;
    padding: 5px 8px;
    border: solid 1px #e6e6e6;
    border-radius: 3px;
    color: #888;
    box-sizing: border-box;
}
.myInput:focus {
    border-color: #f08b2f;
}
.myTextarea {
    width: 100%;
    padding: 8px;
    border: solid 1px #e6e6e6;
    border-radius: 3px;
    color: #888;
    height: 155px;
    box-sizing: border-box;
}
.personal-last-pay .list-box li {
    display: inline-block;
    margin-right: 80px;
    font-size: 14px;
}

.btn-prev {
    display: inline-block;
    margin-right: 30px;
    line-height: 36px;
    padding: 0 42px;
    border: solid 1px #ff7200;
    color: #ff7200;
    border-radius: 2px;
}
.btn-next {
    display: inline-block;
    line-height: 36px;
    padding: 0 42px;
    background-color: #ff7200;
    color: #fff;
    border-radius: 2px;
}
.btn-next.disabled {
    opacity: 0.2;
    cursor: default;
}
.orderinfo-wrap .btns {
    margin-top: 50px;
    margin-bottom: 80px;
    width: 1100px;
    text-align: center;
}
</style>
