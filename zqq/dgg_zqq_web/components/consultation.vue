<template>
    <transition name="fadeIn">
        <div class="ntlk-main masking" v-show="isShow">
            <div class="ntalk-wrap">
                <div class="layui-layer-title" style="cursor: move;">商标咨询</div>
                <div id="" class="layui-layer-content">
                    <div id="consultationModal" class="consultationModal formModal">

                        <ul style="mrigin-top:24px" :style="ulBox">
                            <li>
                                <label>商标名称：</label>
                                <input type="text" class="input" name="name" readonly="readonly" v-model="name">
                            </li>
                            <li>
                                <label>商标类别：</label>
                                <input type="text" class="input" name="category" readonly="readonly" v-model="type">
                            </li>
                            <li>
                                <label>申请&nbsp;&nbsp;&nbsp;号：</label>
                                <input type="text" class="input" name="category" readonly="readonly" v-model="code">
                            </li>
                            <li>
                                <label>
                                    <i class="star">*</i>您的称呼：</label>
                                <input type="text" class="input" placeholder="如王先生/张女士" v-model="userName" maxlength="20">
                            </li>
                            <li>
                                <label>
                                    <i class="star">*</i>联系方式：</label>
                                <input type="text" class="input" placeholder="方便与您沟通购买事宜" v-model="phone">
                            </li>
                            <li>
                                <label>
                                    <i class="star"></i>您的邮箱：</label>
                                <input type="text" class="input" placeholder="方便与您沟通购买事宜" v-model="email" maxlength="30">
                            </li>
                            <li>
                                <label>
                                    <i class="star">*</i>意向价格：</label>
                                <select class="input" style="width:252px" v-model="intentionalPrice">
                                    <option v-for="(item,idx) in priceArr" :key='idx' :value="item.code">{{item.name}}</option>
                                </select>
                            </li>
                            <li>
                                <label>需求描述：</label>
                                <textarea class="input" v-model="requirementDesc" maxlength="300"></textarea>
                            </li>
                        </ul>
                        <a href="javascript:void(0)" @click="submit" class="button submit">提&nbsp;&nbsp;交</a>
                    </div>
                </div>
                <span class="layui-layer-setwin" @click="close">
                </span>
            </div>
        </div>
    </transition>
</template>

<script>
import { Http } from "~/plugins/axios.js";
export default {
    props: {
        isShow: {
            default: false
        },
        name: {
            default: ""
        },
        type: {
            default: ""
        },
        code: {
            default: ""
        }
    },
    data() {
        return {
            userName: "", //称呼
            phone: "", //手机号
            email: "", //邮箱
            intentionalPrice: 0, //期望价格
            requirementDesc: "", //描述
            c_price: "不限",
            priceArr: [],
            priceIdx: 0,
            ulBox: ""
        };
    },
    mounted() {
        Http("trademarkConsult/selectPrice", {}, "get").then(res => {
            // console.log("价格", res);
            this.priceArr = res.data.data;
            this.intentionalPrice = res.data.data[0].code;
        });

        this.serize();
    },
    methods: {
        close() {
            document.getElementsByTagName("body")[0].style.cssText =
                "overflow:auto";
            this.$emit("close");
        },
        submit() {
            if (this.userName == "") {
                this.$Popup.created({
                    content: "请输入您的称呼",
                    time: 1500
                });
                return;
            }
            if (!/^1(3|4|5|7|8|9)\d{9}$/.test(this.phone)) {
                this.$Popup.created({
                    content: "请填写正确手机号",
                    time: 1500
                });

                return;
            }
            let requireObj = {
                trademarkeName: this.name,
                trademarkerType: this.type,
                applicationNum: this.code,
                userName: this.userName,
                phone: this.phone,
                email: this.email,
                intentionalPrice: this.intentionalPrice,
                requirementDesc: this.requirementDesc
            };
            sessionStorage.setItem("isFrom", 1);
            Http("trademarkConsult/save", requireObj, "post").then(res => {
                sessionStorage.removeItem("isFrom");
                this.$Popup.created({
                    content: "提交成功，稍后会有客服人员与你联系！",
                    time: 2000
                });
                this.close();
            });
        },
        serize() {
            let Height = document.documentElement.clientHeight;
            if (Height <= 570) {
                this.ulBox = {
                    overflow: "auto",
                    height: "300px"
                };
            }
        }
    }
};
</script>
<style scoped>
.masking {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.3);
    z-index: 110;
}
.ntalk-wrap {
    position: fixed;
    left: 50%;
    top: 50%;
    margin-left: -250px;
    transform: translateY(-50%);
    width: 500px;
    padding-bottom: 14px;
    background: #fff;
    box-shadow: 0 0 5px 1px rgba(9, 9, 10, 0.1);
    border-radius: 2px;
    z-index: 120;
}
.layui-layer-title {
    padding-top: 13px;
    height: 52px;
    line-height: 42px;
    border-bottom: none;
    font-size: 16px;
    color: #f08b2f;
    text-align: center;
    overflow: hidden;
    background-color: #fff;
    border-radius: 2px 2px 0 0;
}
.layui-layer-content {
    position: relative;
}
.layui-layer-setwin a {
    position: relative;
    width: 16px;
    height: 16px;
    margin-left: 10px;
    font-size: 12px;
    _overflow: hidden;
}
.layui-layer-setwin {
    position: absolute;
    right: 15px;
    top: 15px;
    font-size: 0;
    line-height: initial;
    width: 23px;
    height: 23px;
    background: url("../assets/images/main/close.png") center no-repeat;
    cursor: pointer;
}
/* //表单 */
.formModal {
    padding: 0 50px;
}
.formModal > ul > li {
    margin-bottom: 15px;
}
.formModal ul li label {
    display: inline-block;
    width: 100px;
    text-align: right;
    vertical-align: middle;
}
.formModal ul li .input {
    display: inline-block;
    padding: 5px;
    border: solid 1px #e3e3e3;
    width: 240px;
    border-radius: 1px;
    color: #333;
    vertical-align: middle;
}
.formModal ul li textarea.input {
    vertical-align: top;
    height: 100px;
    resize: none;
}
.formModal ul li .star {
    margin-right: 5px;
    color: #ff0000;
}
.formModal .submit {
    display: block;
    margin: 0 auto;
    margin-top: 25px;
    width: 190px;
    font-size: 16px;
    line-height: 2.4em;
    color: #fff;
    text-align: center;
    border-radius: 2px;
    background-color: #f08b2f;
}
</style>
