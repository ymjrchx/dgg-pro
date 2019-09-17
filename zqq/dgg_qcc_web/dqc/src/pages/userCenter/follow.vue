<template>
    <div class="col-md-12">
        <div class="wrapper-md">
            <div class="panel">
                <div class="panel-heading b-b clear">
                    <h4>我的关注</h4>
                </div>
                <div class="panel-body">
                    <div class="pills" style="margin-bottom:0">
                        <div class="pills-header">所属分组：</div>
                        <div class="pills-after dqc">
                            <!-- <a  class="pills-item active"> 简历</a>
                            <a  class="pills-item "> 公司</a> -->
                            <a v-for="(item,idx) in headArr" :key="idx" :class="{'pills-item':true,'active': idx == activeIndex}" @click="switchTab(idx)">{{item}}</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel" style="position:relative">
                <Loading_p v-show="showLoading" />
                <div class="panel-body">
                    <table class="ptable" v-if='activeIndex == 0'>
                        <thead>
                            <tr>
                                <th width="20%">姓名</th>
                                <th width="20%">区域</th>
                                <th width="20%">专业</th>
                                <th width="20%">期望薪水</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody v-if="hasMsg">
                            <tr v-for='(item,idx) in listArr' :key="idx">
                                <td>
                                    <span class="text-primary cur" @click="goResume(item.cvId,item.id)">{{item.ownerName}}</span>
                                </td>
                                <td>{{item.area || '-'}}</td>
                                <td>{{item.major || '-'}}</td>
                                <td>{{item.price || '-'}}</td>
                                <td>
                                    <a class="text-primary m-l-xs" @click="removeResume(item.id,idx)">取消</a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <table class="ptable" v-else-if='activeIndex == 1'>
                        <thead>
                            <tr>
                                <th width="50%">企业名称</th>
                                <th width="25%">关注日期</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody v-if="hasMsg">
                            <tr v-for='(item,idx) in listArr' :key="idx">
                                <td>
                                    <span class="text-primary cur" @click="goCompany(item.enterpriseId,item.enterpriseName)">{{item.enterpriseName}}</span>
                                </td>
                                <td>{{item.createTime || '-'}}</td>
                                <td>
                                    <a class="text-primary m-l-xs" @click="removeCompany(item.id)">取消</a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <template v-if="!hasMsg">
                        <NoFound msg="暂无关注记录" />
                    </template>
                </div>
            </div>
            <div class="panel" style="padding:15px;text-align:center">
                <el-pagination @current-change="handleCurrentChange" layout="prev, pager, next" :total="1">
                </el-pagination>
            </div>
        </div>
    </div>
</template>
<script>
import NoFound from "@/components/No_found";
import Common from "@/util/common.js";
import Loading_p from "@/components/Loading.vue";
export default {
    props: {
        data: {}
    },
    components: {
        NoFound,
        Loading_p
    },
    data() {
        return {
            hasMsg: 0,
            pageIndex: 1,
            pageSize: 10,
            listArr: "",
            showLoading: 1, //显示loading层
            activeIndex: 0, //选第几项
            headArr: ["简历", "公司"]
        };
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage() {
            let obj = {
                userId: this.$store.state.userInfo.userId,
                pageIndex: this.pageIndex,
                pageSize: this.pageSize,
                param: ""
            };
            let requireUrl = "";
            switch (this.activeIndex) {
                case 0:
                    requireUrl = "api/userFocusCv/queryMyFocus.do";
                    break;
                case 1:
                    requireUrl = "api/userFocusEnterprise/queryMyFocus.do";
                    break;
            }
            this.$axios({
                type: "get",
                url: requireUrl,
                data: obj,
                success: res => {
                    // console.log("数据",res)
                    this.showLoading = 0;
                    if (res.data.code == 0) {
                        let data = res.data.data;
                        if (data.data.length > 0) {
                            this.hasMsg = 1;
                            this.listArr = data.data;
                        } else {
                            this.hasMsg = 0;
                        }
                    } else {
                        this.$message.error(res.data.msg);
                        this.hasMsg = 0;
                    }
                }
            });
        },
        handleCurrentChange(e) {
            this.pageIndex = e;
            this.initPage();
        },
        removeResume(id) {
            this.$confirm("确认是否取消关注此条信息？")
                .then(_ => {
                    let obj = {
                        userId: this.$store.state.userInfo.userId,
                        userFocusCvId: id
                    };
                    this.$axios({
                        type: "get",
                        url: "api/userFocusCv/cancelFocus.do",
                        data: obj,
                        success: res => {
                            if (res.data.code == 0) {
                                this.initPage();
                                this.successFuc();
                            } else this.$message.error(res.data.msg);
                        }
                    });
                })
                .catch(_ => {});
        },
        removeCompany(id) {
            this.$confirm("确认是否取消关注此公司？")
                .then(_ => {
                    let obj = Object.assign(
                        {},
                        {
                            userFocusEnterpriseId: id,
                            userId: this.$store.state.userInfo.userId
                        }
                    );

                    this.$axios({
                        type: "get",
                        url: "api/userFocusEnterprise/cancelFocus.do",
                        data: obj,
                        success: res => {
                            if (res.data.code == 0) {
                                this.initPage();
                                this.successFuc();
                            } else this.$message.error(res.data.msg);
                        }
                    });
                })
                .catch(_ => {});
        },
        goResume(id, id2) {
            Common.newPage("Qualifications_resume_d", { url: id, resume: id2 });
        },
        goCompany(companyId, name) {
            Common.newPage("Company_detail", { id: companyId, title: name });
        },
        switchTab(num) {
            this.activeIndex = num;
            this.showLoading = 1;
            this.initPage();
        },
        successFuc() {
            this.$notify({
                message: "取消成功",
                type: "success",
                duration: 2000
            });
        }
    }
};
</script>
    
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.ptable {
    width: 100%;
}
.ptable th {
    background: #fafafa;
    border: #eee 1px solid;
    border-collapse: collapse;
    padding: 17px 5px 17px 12px;
    font-weight: normal;
    color: #444;
    line-height: 19px;
    text-align: center;
}
.ptable th:not(:last-child) {
    border-right: #f2f9fc 1px solid;
}
.ptable td {
    padding: 12px 5px 12px 12px;
    border: #eee 1px solid;
    word-break: break-all;
    font-size: 14px;
    line-height: 19px;
    color: #222;
    text-align: center;
}
.ptable td:not(:last-child) {
    border-right: transparent 1px solid;
}
.dqc .pills-item {
    padding: 2px 10px;
    margin: 6px 6px;
    font-size: 16px;
    border-radius: 2px;
}
</style>
    