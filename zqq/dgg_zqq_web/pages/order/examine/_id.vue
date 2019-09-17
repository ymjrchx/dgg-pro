<template>
    <div class="order">
        <ol class="status">
            <li class="atthis">1.填写商标注册信息</li>
            <li class="atthis">2.确认订单提交</li>
        </ol>

        <div class="content" >
            <div class="entrust_suc">
                <h1 v-if="orderInfo && orderInfo.auditType != 'oder_audit_type_04'">提交成功！请等待审核成功后，再确认付款！</h1>
                <h1 v-if="orderInfo &&  orderInfo.auditType == 'oder_audit_type_04'">保存成功！请跳转至个人中心提交审核！</h1>
                <p class="state">我们的专业顾问会在10-30分钟内对您提交的信息进行审核，并出具评估报告，确保更高的注册成功率。</p>
                <div class="btn_box">
                    <a class="btn btn_gray" v-if="orderInfo &&  orderInfo.auditType != 'oder_audit_type_04'" href="/">返回首页</a>
                    <a class="btn btn_gray" v-if="orderInfo &&  orderInfo.auditType == 'oder_audit_type_04'" href="/center/smartOrder">个人中心</a>
                    <a class="btn btn_orange2" href="/order/auto">继续注册</a>
                </div>
                <p class="note">您可以在“
                    <a href="/center/smartOrder">个人中心-订单中心-智能订单</a>”查询您的订单进度和后续商标进度</p>
                <table class="table">
                    <thead>
                        <tr>
                            <th>订单状态</th>
                            <th>商标图样</th>
                            <th>商标名称</th>
                            <th>商标类别</th>
                        </tr>
                    </thead>
                    <tbody v-if="orderInfo.trademark">
                        <tr>
                            <td v-if="orderInfo.status == 'order_status_not_audit'">等待审核</td>
                            <td v-if="orderInfo.status != 'order_status_not_audit' && orderInfo.status !='order_status_waiting_submit' ">审核失败</td>
                            <td v-if="orderInfo.status == 'order_status_waiting_submit'">待提交</td>
                            <td><img class="img_box" :src="orderInfo.trademark.host+orderInfo.trademark.exampleAddress" :alt="orderInfo.trademark.name"></td>
                            <td>{{orderInfo.trademark.name}}</td>
                            <td>
                                <p v-for="item in orderInfo.classList" :key="item.id">第 {{item.classLevel1Code}} 类 {{item.classLevel1Name}}</p>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <to-top />
    </div>
</template>

<script>
import toTop from "@/components/toTop";
import axios from "axios";
import popup from "~/components/popup"; //消息
// import Cookies from "js-cookie";

export default {
    middleware: "auth",
    head() {
        return {
            title: `${this.orderInfo.serviceName ||
                ""}，商标免费注册，商标0元注册-知千秋`,
            meta: [
                {
                    name: "keywords ",
                    hid: "keywords ",
                    content:
                        "注册商标查询，中国商标官网查询，商标搜索，商标检索,知千秋,权大师 "
                },
                {
                    name: "description ",
                    hid: "description ",
                    content:
                        "免费精准的商标查询平台,中国更全的商标信息库,精准智能的商标相似查询结果,为商标申请人降低商标驳回风险,更快,更准的定位商标注册成功概率。知千秋 "
                }
            ]
        };
    },
    data() {
        return {
            orderInfo: ""
        };
    },
    mounted() {
        if (!this.$route.params.id) {
            this.$router.replace("/404");
            return;
        }
        this.loadInfo();
    },
    methods: {
        async loadInfo() {
            let { data } = await this.$Http(
                `/order/queryUserOrderByNo?orderNo=${
                    this.$route.params.id
                }&userId=${this.$store.state.userInfo.userId}`,
                {},
                "GET"
            );

            this.orderInfo = data.data;
            // Cookies.set("product", {
            //     name: this.showInfo.serviceItem.name,
            //     serviceId: this.showInfo.serviceItem.id,
            //     serviceAttrId: "",
            //     num: 1,
            //     officialPrice: this.showInfo.serviceItem.officialPrice,
            //     servicePrice: this.showInfo.serviceItem.servicePrice,
            //     total:
            //         this.showInfo.serviceItem.officialPrice +
            //         this.showInfo.serviceItem.servicePrice
            // });
        }
    },
    components: {
        toTop
    }
};
</script>
<style scoped lang="stylus">
.order {
    width: 1200px;
    margin: 0 auto;

    a {
        color: #fd7d22;
    }

    .content {
        background: #fff;
        border: 1px solid #e7e7e7;
        padding: 30px 40px;
        margin-bottom: 30px;
        position: relative;
    }

    .btn {
        color: #fff !important;
        display: inline-block;
        line-height: 20px;
        padding: 6px 20px;
        margin: 0 5px;
        text-align: center;
        background-color: #999;
        border-radius: 3px !important;
        font-size: 16px;
        border: none;
        cursor: pointer;
        position: relative;
    }

    .status {
        margin: 30px 0;
        overflow: hidden;
        position: relative;

        &:after {
            content: '';
            position: absolute;
            width: 25px;
            height: 40px;
            left: 50%;
            top: 0;
            background: transparent url('../../../assets/images/order/bg_arr.png') left top no-repeat;
        }

        li {
            float: left;
            width: 50%;
            text-align: center;
            font: normal 16px / 40px 'microsoft yahei';
            color: #fff;
            background: #a2b8ce;

            &.atthis {
                background: #fd7d22;
            }
        }
    }
}

.entrust_suc {
    padding: 40px 52px;
    text-align: center;
    background: #fff;
}

.entrust_suc h1 {
    display: inline-block;
    margin: 20px 0;
    padding: 8px 34px;
    text-indent: 23px;
    color: #0bb523;
    font-size: 24px;
    font-weight: 400;
    background: url('../../../assets/images/order/suc.png') no-repeat left center;
}

.entrust_suc .state {
    width: 770px;
    margin: 30px auto;
    font-size: 16px;
    line-height: 34px;
    color: #666;
}

.entrust_suc .btn_box {
    padding: 30px 0;
    border-top: 1px solid #ececec;
}

.entrust_suc .note {
    padding: 20px 0;
}

.entrust_suc .table {
    width: 770px;
    margin: 0 auto;
}

.entrust_suc .table .img_box {
    height: 65px;
    width: 64px;
    border: 1px solid #e9e9e9;
    overflow: hidden;
}

.entrust_suc .table .img_box img {
    width: 100%;
}

.table {
    width: 100%;
    border: 1px solid #e9e9e9;
}

.table tr:hover {
    background-color: #fafafa;
}

.get_recode_table tr:hover {
    background-color: white !important;
}

.table th, .table td {
    padding: 15px 5px;
    text-align: left;
    border-bottom: 1px solid #e9e9e9;
    line-height: 20px;
}

.table_i8td {
    padding-left: 15px !important;
}

.table th {
    color: #696464;
    font-size: 15px;
    background-color: #fafafa;
}

.table td {
    word-break: break-all;
    color: #666;
    font-size: 14px;
}

.table td:nth-child(1), .table th:nth-child(1) {
    padding-left: 15px;
}

.table tr.center th, .table tr.center td {
    text-align: center;
}

.btn_orange2 {
    background-color: #fd7d22 !important;
}
</style>
