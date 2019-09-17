<template>
    <div class="content">
        <div class="accountSecurity-page order-page">
            <h2 class="order-main-title">模板管理
                <div class="fr">
                    <button v-show='step == 0' @click="goAdd" class="button"><img src="../../assets/images/center/add2.png" alt="新增"> 新增模板</button>
                    <button v-show='step == 1' @click="step = 0" class="button back"><img src="../../assets/images/center/back.png" alt="返回"> 返回管理</button>
                </div>
            </h2>
            <!-- start part1 -->
            <div class="part1 clearfix" v-show='step == 0'>
                <table v-if=' total > 0'>
                    <thead>
                        <tr>
                            <th>模板名称</th>
                            <th>申请主体</th>
                            <th>主体类型</th>
                            <th>模板默认状态</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(item,idx) in formList" :key='idx'>
                            <td>
                                {{item.templateName}}
                            </td>
                            <td>
                                {{item.applicantName}}
                            </td>
                            <td>
                                {{item.applicantType == 'applicant_type2' ? '个人' : "企业" }}
                            </td>
                            <td>
                                <span class="col" v-if='item.defaultSign'>默认</span>
                                <a @click="sureTemplate(item.id)" v-else>设为默认</a>
                            </td>
                            <td class="btn">
                                <a @click='editMsg(idx)' class="col">编辑</a>
                                <a @click="delTemplate(item.id)">删除</a>
                            </td>
                        </tr>

                    </tbody>
                </table>
                <div class="nofind" v-else>
                    <img src="../../assets/images/nofind.png" alt="">
                    <p>
                        您还没有模板，去
                        <a href="javascript:" class="col" @click="step = 1">增加</a>
                    </p>
                </div>
            </div>
            <!-- end part1 -->
            <!-- start part2 -->
            <div class="part2" v-show='step == 1'>
                <div class="tem_name">
                    <span :class="{'active':nameIdx == 0}" @click="nameIdx = 0">企业模板</span>
                    <span :class="{'active':nameIdx == 1}" @click="nameIdx = 1">个人模板</span>

                    <a class="fr col" @click="tipsFunc" style="margin:16px 20px 0 0">文件上传注意事项</a>
                </div>
                <table>
                    <thead>
                        <tr>
                            <th width='15%'></th>
                            <th width='40%'></th>
                            <th width='45%'></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td class="">
                                模板名称：
                            </td>
                            <td colspan="2">
                                <input type="text" placeholder="请输入模板名称" v-model="commonParams.templateName" maxlength="20">
                            </td>
                        </tr>
                        <tr v-show='nameIdx == 1'>
                            <td>
                                身份证：
                            </td>
                            <td class="img">
                                <el-upload :action="$store.state.singleUpLoadUrl" :show-file-list="false" :on-success="handleAvatarSuccess_s" :before-upload="beforeAvatarUpload" :data='uploadObj'>
                                    <div class="picUpload">
                                        <span class="add" v-show='!personFullpath'>+</span>
                                        <img :src="personFullpath" v-if='personFullpath'>
                                    </div>
                                </el-upload>
                                <div class="examples">
                                    <a href="/order/gr-sfz.jpg" target="_blank">
                                        <img src="../../static/order/gr-sfz.jpg" alt="身份证示例">
                                        <span>查看示例</span>
                                    </a>
                                </div>
                            </td>
                            <td>
                                <div class="three_ipt">
                                    <h5>申请人：</h5>
                                    <input type="text" placeholder="请保持与身份证一致" v-model="personParams.applicantUserName" ref='applicantUserName' maxlength="30">
                                    <h5>身份证号：</h5>
                                    <input type="text" placeholder="请保持与身份证一致" v-model="personParams.applicantCardNo" ref='applicantCardNo'>
                                    <h5>申请人地址：</h5>
                                    <input type="text" placeholder="请保持与身份证一致" v-model="personParams.applicantCardAddress" maxlength="50">
                                </div>

                            </td>
                        </tr>
                        <tr>
                            <td>
                                营业执照：
                            </td>
                            <td class="img">
                                <el-upload :action="$store.state.singleUpLoadUrl" :show-file-list="false" :on-success="handleAvatarSuccess_q" :before-upload="beforeAvatarUpload" :data='uploadObj'>
                                    <div class="picUpload">
                                        <span class="add" v-show=' !businessFullpath'>+</span>
                                        <img :src="businessFullpath" v-if='businessFullpath'>
                                    </div>
                                </el-upload>
                                <div class="examples">
                                    <a href="/order/qy-yyzz.jpg" target="_blank">
                                        <img src="../../static/order/qy-yyzz.jpg" alt="营业执照示例">
                                        <span>查看示例</span>
                                    </a>
                                </div>
                            </td>
                            <td>
                                <div class="three_ipt">
                                    <h5>申请主体：</h5>
                                    <input type="text" placeholder="请输入申请主体" v-model="commonParams.applicantName" maxlength="30">
                                    <h5>申请地址：</h5>
                                    <input type="text" placeholder="请输入申请地址" v-model="commonParams.businessLicenceAddress" maxlength="50">
                                    <h5>社会代码：</h5>
                                    <input type="text" placeholder="请输入社会代码" v-model="commonParams.businessLicenceNo" maxlength="30">
                                </div>

                            </td>
                        </tr>
                        <tr>
                            <td>
                                联系人及<br> 联系方式：
                            </td>
                            <td><input type="text" placeholder="请输入联系人名称" v-model="commonParams.contactName" maxlength="30"></td>
                            <td>
                                <input type="text" placeholder="请输入联系电话" v-model="commonParams.contactPhone" ref='contactPhone' maxlength="11">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                邮政编码：
                            </td>
                            <td colspan="2">
                                <input type="text" placeholder="请输入邮政编码" v-model="commonParams.postalcode" ref='postalcode'>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                联系邮箱：
                            </td>
                            <td colspan="2">
                                <input type="text" placeholder="请输入联系邮箱" v-model="commonParams.contactEmail" ref='contactEmail' maxlength="50">
                            </td>
                        </tr>
                        <tr>
                            <td>

                            </td>
                            <td colspan="2">
                                <div class="btnBox">
                                    <button :class="{'unUse': unClick }" @click="saveTemplate">保存</button>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <!-- end part2 -->
        </div>
    </div>
</template>
<script>
import popup from "~/components/popup"; //消息
import { Http } from "~/plugins/axios.js";

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
    components: {},
    data() {
        return {
            step: 0,
            formList: [],
            total: 0,
            nameIdx: 0,
            Loading: "",
            isUpdate: "",
            applicantType: "", //申请人类型， applicant_type1：企业，applicant_type2：个体工商户
            personFullpath: "",
            businessFullpath: "",
            commonParams: {
                templateName: "", //模板名称
                businessLicenceFile: "", //营业执照
                applicantName: "", //申请主体
                businessLicenceAddress: "", //申请地址 - 营业执照所在地区;
                businessLicenceNo: "", //社会代码 -营业执照号
                contactName: "", //联系人名称
                contactPhone: "", //联系人电话
                postalcode: "", //邮政编码
                contactEmail: "" //邮箱
            },
            personParams: {
                applicantCardFile: "", //复印件
                applicantUserName: "", //申请人名称
                applicantCardNo: "", //身份证号
                applicantCardAddress: "" //身份证地址
            },
            uploadObj: {},
            fsHost: ""
        };
    },
    computed: {
        unClick() {
            let bool = false;
            for (let key in this.commonParams) {
                if (this.commonParams[key] == "") {
                    bool = true;
                }
            }
            if (this.nameIdx == 1) {
                for (let key in this.personParams) {
                    if (this.personParams[key] == "") {
                        bool = true;
                    }
                }
            }
            return bool;
        }
    },
    mounted() {
        this.getTemplate();
        this.uploadObj = {
            type: "upload_file_type_02",
            userId: this.$store.state.userInfo.userId
        };
    },
    methods: {
        getTemplate() {
            let requireObj = {
                createrId: this.$store.state.userInfo.userId,
                pageSize: 20,
                pageNum: 1
            };
            Http("applicantTemplate/queryByPage", requireObj, "get").then(
                res => {
                    this.formList = res.data.data.list;
                    this.total = res.data.data.count;
                    this.fsHost = res.data.data.fsHost;
                }
            );
        },
        //删除
        delTemplate(id) {
            popup.created({
                type: "confirm",
                content: "确认是否删除此条模板?",
                success: bool => {
                    if (bool) {
                        Http(
                            "applicantTemplate/del",
                            {
                                createrId: this.$store.state.userInfo.userId,
                                id: id
                            },
                            "get",
                            1
                        ).then(res => {
                            this.getTemplate();
                            popup.created({
                                content: "删除成功",
                                time: 1500
                            });
                        });
                    }
                }
            });
        },
        //默认
        sureTemplate(id) {
            Http(
                "applicantTemplate/makeDefault",
                { createrId: this.$store.state.userInfo.userId, id: id },
                "get",
                1
            ).then(res => {
                this.getTemplate();
                popup.created({
                    content: "修改成功",
                    time: 1500
                });
            });
        },
        //编辑
        editMsg(idx) {
            this.clearAdd();

            let arr = this.formList[idx];
            let commomObj = Object.assign({}, this.commonParams);
            for (let key in commomObj) {
                commomObj[key] = arr[key];
            }
            this.commonParams = commomObj;
            this.businessFullpath = this.fsHost + arr.businessLicenceFile;

            if (arr.applicantType == "applicant_type2") {
                let personObj = Object.assign({}, this.personParams);
                for (let key in personObj) {
                    personObj[key] = arr[key];
                }
                this.personParams = personObj;
                this.nameIdx = 1;
                this.personFullpath = this.fsHost + arr.applicantCardFile;
            }

            this.isUpdate = arr.id;
            this.step = 1;
        },
        //新增按钮
        goAdd() {
            if (this.total >= 10) {
                popup.created({
                    content: "模板最多只能保存十条",
                    time: 2000
                });
            } else {
                this.clearAdd();
                this.step = 1;
            }
        },
        //upload上传 身份证图片
        handleAvatarSuccess_s(res, file) {
            this.Loading.close();

            this.personParams = Object.assign({}, this.personParams, {
                applicantCardFile: res.data.fsPath
            });

            this.personFullpath = res.data.fsHost + res.data.fsPath;

            Http(
                "imgidentify/idCard",
                { filePath: this.personFullpath, idCardSide: "front" },
                "get"
            ).then(imgRes => {
                let imgObj = imgRes.data.data;
                let obj = Object.assign({}, this.personParams);

                obj.applicantUserName = imgObj.name;
                obj.applicantCardNo = imgObj.no;
                obj.applicantCardAddress = imgObj.address;
                this.personParams = obj;
            });
        },
        //upload上传 营业执照
        handleAvatarSuccess_q(res, file) {
            this.Loading.close();

            this.businessFullpath = res.data.fsHost + res.data.fsPath;

            this.commonParams = Object.assign({}, this.commonParams, {
                businessLicenceFile: res.data.fsPath
            });

            Http(
                "imgidentify/license",
                { filePath: this.businessFullpath },
                "get"
            ).then(imgRes => {
                let imgObj = imgRes.data.data;
                let obj = Object.assign({}, this.commonParams);

                obj.applicantName = imgObj.org || imgObj.legalPerson;
                obj.businessLicenceAddress = imgObj.address;
                obj.businessLicenceNo = imgObj.no;
                this.commonParams = obj;

                // console.log(1111111,imgRes.data)
            });
        },
        //温馨提示
        tipsFunc() {
            popup.created({
                type: "confirm",
                title: "注意事项",
                content:
                    "支持智能识别：请上传清晰的身份证明图片，系统识别后请再次核对信息，若识别有误请修改。<br>请按照要求上传扫描件，jpg格式、小于5M",
                cancel: false
            });
        },
        beforeAvatarUpload(file) {
            const isJPG =
                file.type === "image/jpeg" || file.type === "image/png";
            const isLt2M = file.size / 1024 / 1024 < 5;

            if (!isJPG) {
                popup.created({
                    content: "上传图片只能是JPG格式或png格式!",
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
        saveTemplate() {
            if (this.unClick) return;

            if (this.nameIdx == 1) {
                if (
                    !/^[\u4e00-\u9fa5]+$/.test(
                        this.personParams.applicantUserName
                    )
                ) {
                    this.$Popup.created({
                        content: "请填写正确的申请人名称",
                        time: 1500
                    });
                    this.$refs.applicantUserName.focus();
                    return;
                }

                if (
                    !/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(
                        this.personParams.applicantCardNo
                    )
                ) {
                    this.$Popup.created({
                        content: "请填写正确的身份证号",
                        time: 1500
                    });
                    this.$refs.applicantCardNo.focus();
                    return;
                }
            }

            if (!/^1(3|4|5|7|8|9)\d{9}$/.test(this.commonParams.contactPhone)) {
                popup.created({
                    content: "请填写正确电话号码",
                    time: 1000
                });
                this.$refs.contactPhone.focus();
                return;
            }

            if (!/^\d{6}$/.test(this.commonParams.postalcode)) {
                popup.created({
                    content: "请填写正确的邮政编码",
                    time: 1000
                });
                this.$refs.postalcode.focus();
                return;
            }

            if (
                !/^[A-Za-z0-9\u4e00-\u9fa5_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(
                    this.commonParams.contactEmail
                )
            ) {
                popup.created({
                    content: "请填写的邮箱地址",
                    time: 1000
                });
                this.$refs.contactEmail.focus();
                return;
            }

            let requireObj = Object.assign(
                {},
                {
                    createrId: this.$store.state.userInfo.userId,
                    applicantType: "applicant_type1"
                },
                this.commonParams
            );
            if (this.nameIdx == 1) {
                requireObj = Object.assign(
                    {},
                    requireObj,
                    { applicantType: "applicant_type2" },
                    this.personParams
                );
            }
            if (this.isUpdate) {
                requireObj = Object.assign({}, requireObj, {
                    id: this.isUpdate
                });
            }
            Http(
                "applicantTemplate/saveApplicantTemplate",
                requireObj,
                "post",
                1
            ).then(res => {
                this.getTemplate();

                popup.created({
                    content: this.isUpdate ? "修改成功" : "保存成功",
                    time: 1500
                });
                setTimeout(() => {
                    this.step = 0;
                    this.clearAdd();
                }, 1200);
            });
        },
        clearAdd() {
            this.nameIdx = 0;
            this.isUpdate = "";
            this.commonParams = {
                templateName: "", //模板名称
                businessLicenceFile: "", //营业执照
                applicantName: "", //申请主体
                businessLicenceAddress: "", //申请地址 - 营业执照所在地区;
                businessLicenceNo: "", //社会代码 -营业执照号
                contactName: "", //联系人名称
                contactPhone: "", //联系人电话
                postalcode: "", //邮政编码
                contactEmail: "" //邮箱
            };
            this.personParams = {
                applicantCardFile: "", //复印件
                applicantUserName: "", //申请人名称
                applicantCardNo: "", //身份证号
                applicantCardAddress: "" //身份证地址
            };
            this.personFullpath = "";
            this.businessFullpath = "";
        }
    }
};
</script>
<style scoped lang="stylus">
.order-main-title {
    margin-bottom: 15px;
    font-size: 16px;
    line-height: 2.8em;
    font-weight: normal;
    border-bottom: 1px solid #e2e3e8;

    >div {
        margin-right: 20px;

        button {
            background: #ff7200;
            color: #fff;
            width: 106px;
            cursor: pointer;
            height: 32px;
            line-height: 28px;

            img {
                width: 14px;
                margin-right: 4px;
                vertical-align: middle;
            }
        }

        .back {
            background: #fff;
            color: #ff7200;
            border: 1px solid #ff7200;
        }
    }
}

.order-page {
    padding: 20px;
    padding-top: 0px;
    min-height: 700px;
    background-color: #fff;
    box-shadow: 0 4px 6px 0 rgba(113, 114, 119, 0.1);
}

.part1 {
    padding: 20px;

    table {
        width: 100%;

        tr {
            th {
                height: 52px;
                background: #F2F2F2;
                border: 1px solid #E4E4E4;
            }

            td {
                text-align: center;
                height: 78px;
                border: 1px solid #E4E4E4;
            }

            .btn {
                a {
                    margin: 0 6px;
                }
            }
        }
    }

    .nofind {
        padding: 20px;
        text-align: center;

        p {
            display: inline-block;
            color: #565656;
            position: relative;
            top: 50px;
            margin-left: 30px;
        }
    }
}

.part2 {
    padding: 20px 0;

    .tem_name {
        overflow: hidden;
        border-bottom: 1px solid #E4E4E4;
        margin-bottom: 20px;

        span {
            cursor: pointer;
            float: left;
            height: 40px;
            line-height: 40px;
            border-bottom: 2px solid #fff;
            margin-left: 30px;
            width: 80px;
            font-size: 16px;
            text-align: center;
        }

        .active {
            border-bottom: 2px solid #ff7200;
            color: #ff7200;
        }
    }

    table {
        width: 70%;

        .img {
            >div {
                float: left;
            }
        }

        td {
            padding: 10px 8px;
            box-sizing: border-box;

            .picUpload {
                width: 150px;
                height: 180px;
                border: 1px dashed #E4E4E4;
                position: relative;

                .add {
                    position: absolute;
                    left: 50%;
                    top: 50%;
                    transform: translateY(-50%);
                    width: 44px;
                    margin-left: -22px;
                    text-align: center;
                    font-size: 40px;
                    color: #999;
                }

                img {
                    width: 100%;
                    height: 100%;
                }
            }

            .examples {
                display: inline-block;
                width: 90px;
                padding: 0 10px;

                a {
                    display: block;
                    position: relative;
                    text-align: center;
                    border: 1px solid #eaeaea;

                    img {
                        width: 100%;
                    }

                    span {
                        position: absolute;
                        width: 100%;
                        font-size: 12px;
                        height: 20px;
                        line-height: 20px;
                        color: #ff7200;
                        background: rgba(0, 0, 0, 0.1);
                        bottom: 0px;
                        left: 0;
                    }
                }
            }

            .three_ipt {
                padding-left: 30px;

                h5 {
                    font-size: 14px;
                    color: #565656;
                    font-weight: 100;
                    margin-bottom: 6px;
                    margin-top: 10px;
                }

                input {
                    height: 32px;
                }
            }

            .btnBox {
                text-align: center;
                padding-top: 30px;

                button {
                    width: 180px;
                    height: 40px;
                    background: #ff7200;
                    color: #fff;
                    border: 1px solid #ff7200;
                    cursor: pointer;
                    font-size: 16px;
                    border-radius: 4px;
                }

                .unUse {
                    background: #ddd;
                    color: #808080;
                    border: 1px solid #ddd;
                }
            }
        }

        input {
            box-sizing: border-box;
            width: 100%;
            height: 40px;
            font-family: '微软雅黑';
            font-style: normal;
            font-size: 13px;
            text-decoration: none;
            color: #333333;
            text-align: left;
            outline-style: none;
            border: 1px solid #E4E4E4;
            padding: 0 10px;
            border-radius: 2px;
        }

        input:focus {
            border: 1px solid #ff7200;
        }
    }
}
</style>
