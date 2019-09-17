<template>
    <div class="content">
        <div class="order-page" v-if='json'>
            <h2 class="order-main-title">最新订单
                <div class="fr">
                    <button @click="goBack" class="button back">返回订单中心</button>
                </div>
            </h2>
            <div class="newOrder clear2">
                <ul class="steps">
                    <li v-for='(item,idx) in stepArr' :key='idx' :class="{'active': item.updateTime }">
                        <label>{{item.operation}}</label> {{item.updateTime}}
                    </li>
                </ul>
                <div class="consultation">
                    <div style="height:240px;">
                        <template v-if='json.assContent || ( json.assList && json.assList.length > 0)'>
                            <h4 class="order-main-title f14">顾问审查</h4>
                            <div class="clear2 " style="height:180px;overflow:auto;">
                                <p style="margin-bottom:6px;min-height:50px;word-break:break-word;width:100%">{{json.assContent}}</p>
                                <p class="richText">
                                    <template v-for='(item,idx) in json.assList'>
                                        <a :href="item" target="_black" :key='idx'>
                                             <img  :src="item" :onerror='errorImg' title="点击查看大图">
                                        </a>    
                                    </template>
                                </p>
                            </div>
                        </template>
                    </div>
                    <h4 class="order-main-title f14">订单金额</h4>
                    <div class="btn1">
                        <span>￥{{json.allPrice}}元（服务费+官费）</span>
                        <a v-if=" json.orderStatus == 'order_status_obligation' " :href="'/order/pay/'+json.orderNo" class="fr">立即付款</a>
                        <a v-if=" json.orderStatus == 'order_status_waiting_submit' " @click="submitOrder($route.query.id)" class="fr" style="margin-left:10px">提交订单</a>
                        <a v-if=" json.orderStatus == 'order_status_waiting_submit'  || json.orderStatus == 'order_status_audit_faild'  " :href="'/order/auto?id='+$route.query.id" class="fr">编辑订单</a>
                    </div>
                </div>
            </div>
            <h2 class="order-main-title">申请主体</h2>
            <div class="newOrder tableFrom">
                <div class="fl msgList">
                    <p>
                        <label>申请主体名称:</label>
                        <span>{{json.applicantName || '暂无'}}</span>
                    </p>
                    <p>
                        <label>地址:</label>
                        <span>{{json.businessLicenceAddress || '暂无'}}</span>
                    </p>
                    <p>
                        <label>邮政编码:</label>
                        <span>{{json.postalcode || '暂无'}}</span>
                    </p>
                    <p>
                        <label>联系人:</label>
                        <span>{{json.contactName || '暂无'}}</span>
                    </p>
                    <p>
                        <label>联系电话:</label>
                        <span>{{json.contactPhone || '暂无'}}</span>
                    </p>
                    <p>
                        <label>联系邮箱:</label>
                        <span>{{json.contactEmail || '暂无'}}</span>
                    </p>
                    <p>
                        <label>证件号码:</label>
                        <span>{{json.applicantCardNo || '暂无'}}</span>
                    </p>
                </div>
                <div class="fr copyFile">
                    <div>
                        <a :href="json.businessLicenceFile" target="_blank">
                            <img :src="json.businessLicenceFile" :onerror='errorImg'>
                        </a>
                        <p>
                            <small>(个体户执照复印件签字)</small>
                        </p>
                    </div>
                    <div v-if='json.applicantCardFile'>
                        <a :href="json.applicantCardFile" target="_blank">
                            <img :src="json.applicantCardFile" :onerror='errorImg'>
                        </a>
                        <p>
                            <small>(身份证正反面复印件签字)</small>
                        </p>
                    </div>
                </div>
            </div>
            <h2 class="order-main-title">注册商标信息</h2>
            <div class="newOrder tableFrom">
                <div class="fl msgList">
                    <p>
                        <label>商标名称:</label>
                        <span>{{json.name || '暂无'}}</span>
                    </p>
                    <p>
                        <label>商标说明:</label>
                        <span>{{json.explain || '暂无'}}</span>
                    </p>
                    <p>
                        <label>注册号:</label>
                        <span>{{json.registerNo || '暂无'}}</span>
                    </p>
                    <p>
                        <label>商标分类:</label>
                        <span v-if='json.Trademarkclass && json.Trademarkclass.length > 0'>
                            <em v-for="(item,idx) in json.Trademarkclass" :key='idx' :title="item.classLevel3Code+':'+item.classLevel3Name">
                                {{item.classLevel1Code}}-{{item.classLevel1Name}}:{{item.classLevel3Code}}-{{item.classLevel3Name}};</em>
                        </span>
                        <span v-else>
                            '暂无'
                        </span>
                    </p>
                </div>
                <div class="fr copyFile">
                    <div>
                        <a :href="json.exampleAddress" target="_blank">
                            <img :src="json.exampleAddress" :onerror='errorImg'>
                        </a>
                        <p>
                            <small>(商标图样)</small>
                        </p>
                    </div>
                    <div>
                        <a :href="json.proxyFile" target="_blank">
                            <img :src="json.proxyFile" :onerror='errorImg'>
                        </a>
                        <p>
                            <small>(委托书)</small>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import popup from "~/components/popup"; //消息
import axios from "axios";
import img from "~/assets/images/none.png";

export default {
    head() {
        return {
            title: "订单详情-知千秋官网"
        };
    },
    layout: "center",
    components: {},
    data() {
        return {
            errorImg: `this.src='${img}'`,
            json: "",
            stepArr: [
                { operation: "提交申请", updateTime: "" },
                { operation: "审核评估", updateTime: "" },
                { operation: "确认申报", updateTime: "" },
                { operation: "支付订单", updateTime: "" },
                { operation: "申报商标局", updateTime: "" },
                { operation: "形式审查", updateTime: "" }
            ]
        };
    },
    mounted() {
        this.initOrder();
    },
    methods: {
        initOrder() {
            this.$Http(
                "order/orderInfo",
                {
                    userId: this.$store.state.userInfo.userId,
                    orderId: this.$route.query.id
                },
                "get"
            ).then(res => {
             
                this.json = res.data.data;
                this.initStep(res.data.data.operate);
            });
        },
        initStep(arr) {
            if (arr && arr.length > 0) {
                let newArr = [...this.stepArr];

                for (let item of arr) {
                    for (let item2 of newArr) {
                        if (item2.operation == item.operation) {
                            item2.updateTime = item.createTime;
                        }
                    }
                }
                this.stepArr = newArr;
                // console.log(newArr)
            }
        },
        submitOrder(orederNum) {
            this.$Http(
                "order/changeStatus",
                {
                    userId: this.$store.state.userInfo.userId,
                    orderId: orederNum
                },
                "get"
            ).then(res => {
                // console.log(res);
                this.initOrder();
            });
        },
        goBack() {
            this.$router.go(-1);
        }
    }
};
</script>
<style scoped lang="stylus">
* {
    box-sizing: border-box;
}

.order-page {
    padding: 20px;
    padding-top: 0px;
    min-height: 700px;
    background-color: #fff;
    box-shadow: 0 4px 6px 0 rgba(113, 114, 119, 0.1);
}

.order-main-title {
    margin-bottom: 15px;
    font-size: 16px;
    line-height: 2.8em;
    font-weight: normal;
    border-bottom: 1px solid #e2e3e8;

    button {
        background: #ff7200;
        color: #fff;
        width: 106px;
        cursor: pointer;
        height: 32px;
        line-height: 28px;
    }
}

.newOrder {
    padding: 6px 40px 10px 10px;

    .steps {
        width: 50%;
        padding-right: 20px;
        float: left;

        li {
            height: 50px;
            line-height: 50px;
            padding-left: 20px;
            border-left: 1px solid #dcdcdc;
            position: relative;
            font-size: 12px;

            :before {
                content: '';
                position: absolute;
                top: 50%;
                left: -8px;
                width: 14px;
                height: 14px;
                transform: translateY(-50%);
                background-color: #dcdcdc;
                border: 1px solid #fff;
                border-radius: 50%;
                z-index: 2;
            }

            label {
                display: inline-block;
                width: 70px;
                margin-right: 10px;
            }
        }

        .active {
            border-left: 1px solid #f08b2f;
            color: #f08b2f;

            :before {
                background-color: #f08b2f;
            }
        }
    }

    .consultation {
        width: 50%;
        float: left;

        >.btn1 {
            .scalBox {
                width: 120px;
                height: 120px;
                overflow: hidden;
                float: left;
                margin-right: 40px;
                border: 6px solid #eee;
                margin-top: 8px;
                margin-bottom: 20px;
                cursor: pointer;

                img {
                    width: 100%;
                }
            }

            a {
                width: 80px;
                height: 30px;
                text-align: center;
                line-height: 30px;
                background: #fff;
                color: #ff7200;
                border: 1px solid #ff7200;
                cursor: pointer;
                font-size: 14px;
                border-radius: 4px;
            }
        }
    }
}

.tableFrom {
    padding: 10px 20px;
    overflow: hidden;

    .msgList {
        width: 70%;
        padding-right: 30px;
        float: left;

        p {
            font-size: 16px;
            overflow: hidden;

            label {
                float: left;
                width: 18%;
                height: 40px;
                line-height: 40px;
                padding-right: 10px;
                text-align: right;
                color: #777;
            }

            span {
                float: left;
                width: 82%;
                padding-top: 8px;
                line-height: 1.5em;

                em {
                    float: left;
                    width: 50%;
                    padding-left: 10px;
                    overflow: hidden;
                    white-space: nowrap;
                    font-size: 14px;
                    color: #565656;
                    text-overflow: ellipsis;
                }

                em:nth-child(odd) {
                    padding-left: 0px;
                    padding-right: 10px;
                }
            }
        }
    }

    .copyFile {
        width: 30%;
        text-align: center;

        div {
            width: 150px;
            margin: auto;
            margin-bottom: 10px;
            color: #888;

            a {
                width: 140px;
                min-height: 150px;
                border: 1px solid #eee;
                display: block;
                margin-bottom: 4px;
            }

            img {
                width: 100%;
            }
        }
    }
}
</style>
