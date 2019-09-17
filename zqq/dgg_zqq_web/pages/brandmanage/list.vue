<template>
    <div class="manage">
        <div class="spBar3">
            <ul class="speedProgress">
                <li class="step-1 finished">
                    <em class="icon-bar-circle">
                        <a href="/brandmanage"></a>
                    </em>
                    <dl>
                        <dt class="txt">
                            <a href="/brandmanage">导入商标数据</a>
                        </dt>
                    </dl>
                </li>
                <li class="step-2 finished">
                    <em class="icon-bar-circle">
                        <a href="/brandmanage/list"></a>
                    </em>
                    <dl>
                        <dt class="txt">
                            <a href="/brandmanage/list">数据显示</a>
                        </dt>
                    </dl>
                </li>
            </ul>
        </div>
        <div class="brandManageWrap2">
            <div class="content">
                <div class="shuoming">知千秋为您定制专属的商标管理库，简单1步，导入商标数据，您就可以管理您指定申请人下的商标数据</div>
                <div class="c-header">
                    <div class="c-1">
                        <input type="text" v-model="keywords" @keyup.enter="loadImport()" name="key" value="" class="input-text" placeholder="查找已添加商标">
                        <input type="submit" @click="loadImport()" class="btn" value="查找">
                    </div>
                    <div class="c-2">
                    </div>
                </div>
                <div class="c-body" v-if="importRes.length">
                    <div class="c-2">
                        <a class="add songti" href="/brandmanage/add">
                            <i class="fa fa-plus songti">+</i>导入商标数据</a>
                        <a class="add songti" target="_blank" @click="exportExcel">
                            <i class="fa fa-download icon-download">-</i>导出商标数据</a>
                    </div>
                    <table width="100%">
                        <caption>
                            <span class="text">共管理
                                <em>{{importRes.length}}</em>条数据</span>
                            <a href="javascript:;" @click="checkedAll" id="btn-selectAll">
                                <i class="icon icon-check" :class="{'icon-checked': allCheck}"></i>
                                <em class="t">全选</em>
                            </a>
                            <a href="javascript:;" @click="deleteArr" id="btn-delete">
                                <i class="icon icon-delete"></i>
                                <em class="t">删除</em>
                            </a>
                        </caption>
                        <thead>
                            <tr>
                                <td style="width:80px">序号</td>
                                <td>商标图样</td>
                                <td>商标名称</td>
                                <td width="120">注册号</td>
                                <td width="100">类别</td>
                                <td style="width: 130px;">申请日期</td>
                                <td>状态</td>
                                <td>申请人</td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr data="" class="Num1464537600" v-for="(item,index) in importRes" :key="item.id">
                                <td align="center">
                                    <i @click="checkedItem(index)" class="icon icon-check" :class="{'icon-checked': item.checked}" :data="item.id"></i>
                                    <a href="javascript:;">{{index+1}}</a>
                                </td>
                                <td class="td-new">
                                    <em class="icon-new" style="display: none;"></em>
                                    <a target="_blank" :href="'/shangbiaoxq/'+item.registerNo+'_'+parseInt(item.classLevel1Code)+'.html'">

                                        <img v-if="item.trademarkFile" :src="item.trademarkFile" width="135" height="62">

                                    </a>
                                </td>
                                <td align="center">
                                    <a target="_blank" :href="'/shangbiaoxq/'+item.registerNo+'_'+parseInt(item.classLevel1Code)+'.html'">{{item.name}}</a>
                                </td>
                                <td align="center">
                                    <a target="_blank" :href="'/shangbiaoxq/'+item.registerNo+'_'+parseInt(item.classLevel1Code)+'.html'">{{item.registerNo}}</a>
                                </td>
                                <td>
                                    <a target="_blank" :href="'/shangbiaoxq/'+item.registerNo+'_'+parseInt(item.classLevel1Code)+'.html'">{{parseInt(item.classLevel1Code)}}</a>
                                </td>
                                <td>
                                    <a target="_blank" :href="'/shangbiaoxq/'+item.registerNo+'_'+parseInt(item.classLevel1Code)+'.html'">{{item.applyDate?item.applyDate.substr(0, 10):''}}</a>
                                </td>
                                <td>
                                    <a target="_blank" :href="'/shangbiaoxq/'+item.registerNo+'_'+parseInt(item.classLevel1Code)+'.html'">{{item.status}}</a>
                                </td>
                                <td>
                                    <a target="_blank" :href="'/shangbiao/'+item.applicant+'.3.html'">{{item.applicant}}</a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <!-- start 分页 -->
                    <div class="paging">
                        <pagination :totalPage="total" :pageNum="1" :pageSize="pageSize" @pagechange="loadImport" />
                    </div>
                    <!-- end 分页 -->
                </div>
            </div>
        </div>
        <form :action="getUrl()" name="csv_export" method="get" target="_blank">
            <input type="hidden" name="userId" :value="$store.state.userInfo.userId" />
        </form>
    </div>
</template>
<script>
import pagination from "@/components/pagination";
import popup from "~/components/popup"; //消息
export default {
    middleware: "auth",
    head() {
        return {
            title: "商标管理_专注于商标查询、专利查询-知千秋网——",
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
                        "知千秋致力于知千秋致力于运营大数据技术、人工智能等技术重新定义知识产权生态链。提供商标查询,商标注册,专利申请,专利查询,版权登记全流程服务。提供免费商标查询Saas工具,智能商标注册0服务费省钱到底,商标查询,商标注册监控,专利申请等服务获得客户的高度认可"
                }
            ]
        };
    },
    data() {
        return {
            importRes: [],
            total: 0,
            keywords: "",
            pageSize: 20,
            allCheck: false
        };
    },
    components: {
        pagination
    },
    mounted() {
        this.loadImport();
    },
    methods: {
        loadImport(page) {
            this.$Http(
                "/userTrademark/findByUserIdAndName",
                {
                    userId: this.$store.state.userInfo.userId,
                    pageSize: this.pageSize,
                    pageNum: page ? page : 1,
                    name: this.keywords ? this.keywords : ""
                },
                "get",
                true
            ).then(res => {
                this.allCheck = false;
                this.importRes = res.data.data.list;
                this.total = res.data.data.count;
            });
        },
        exportExcel() {
            document.csv_export.submit();
        },
        checkedItem(index) {
            console.log(this.importRes[index].checked);
            this.$set(
                this.importRes[index],
                "checked",
                !this.importRes[index].checked
            );
        },
        checkedAll() {
            let arr = this.importRes;
            let is = false;
            arr.map(item => {
                if (!item.checked) {
                    is = true;
                }
            });
            arr.map((item, index) => {
                if (is) {
                    this.allCheck = true;
                    this.$set(this.importRes[index], "checked", true);
                } else {
                    this.allCheck = !this.allCheck;
                    this.$set(
                        this.importRes[index],
                        "checked",
                        !this.importRes[index].checked
                    );
                }
            });
        },
        // 删除
        deleteArr() {
            let ids = "";
            this.importRes.map(item => {
                if (item.checked) {
                    ids += item.id + ",";
                }
            });
            ids = ids.substr(0, ids.length - 1);
            if (!ids) {
                popup.created({
                    content: "请先选择商标",
                    time: 2000
                });
                return false;
            }
            if (window.confirm("你确定删除选中的商标吗？")) {
                this.$Http(
                    "/userTrademark/deleteArr",
                    { userId: this.$store.state.userInfo.userId, ids: ids },
                    "get",
                    true
                ).then(res => {
                    popup.created({
                        content: "删除成功",
                        time: 2000
                    });
                    this.allCheck = false;
                    this.loadImport();
                });
            }
        },
        getUrl() {
            let url = require("../../nuxt.config").env.baseUrl;
            return url + "userTrademark/exportExcel";
        }
    }
};
</script>
<style scoped>
/* ------------------ start 监控数据1进度条 ------------------ */

.manage {
    min-height: 500px;
}

.zoom {
    position: fixed;
    left: 0;
    top: 0;
    bottom: 0;
    right: 0;
    background: rgba(0, 0, 0, 0.6);
    z-index: 100;
}

.spBar3 {
    width: 1200px;
    margin: auto;
    margin-top: 60px;
    *zoom: 1;
    border-bottom: solid 1px #dbdbdb;
    padding-bottom: 50px;
}

.spBar3:after {
    content: "";
    display: block;
    clear: both;
    height: 0;
    visibility: hidden;
}

.spBar3 .speedProgress li {
    float: left;
    width: 600px;
    position: relative;
    box-sizing: border-box;
}

.spBar3 .speedProgress li .icon-bar-circle {
    display: block;
    width: 100%;
    height: 66px;
    background-image: url(../../assets/images/manage/spBar2-bg-1.png);
    background-repeat: no-repeat;
}

.spBar3 .speedProgress li .icon-bar-circle a {
    display: block;
    margin: auto;
    width: 64px;
    height: 64px;
}

.spBar3 .speedProgress li dl {
    text-align: center;
}

.spBar3 .speedProgress li dl .txt {
    font-size: 16px;
    margin-top: 10px;
}

.spBar3 .speedProgress .finished {
    color: #008cd6;
}

.spBar3 .speedProgress .step-1 .icon-bar-circle {
    background-position: -28px 0px;
}

.spBar3 .speedProgress .step-1.finished .icon-bar-circle {
    background-position: -28px -66px;
}

.spBar3 .speedProgress .step-2 .icon-bar-circle {
    background-position: -580px 0px;
}

.spBar3 .speedProgress .step-2.finished .icon-bar-circle {
    background-position: -580px -66px;
}

.brandManageWrap2 .content {
    margin-top: 40px;
}
.brandManageWrap2 .content .shuoming {
    margin: 20px 0;
    font-size: 14px;
    color: #999;
}

/* --------------- start 商标监控2 --------------- */
.brandManageWrap2 .content {
    width: 1240px;
    margin: auto;
    margin-top: 40px;
}
.brandManageWrap2 .content .c-header {
    margin-bottom: 20px;
    font-weight: normal;
    *zoom: 1;
}
.brandManageWrap2 .content .c-header:after {
    content: "";
    display: block;
    clear: both;
    height: 0;
    overflow: hidden;
    visibility: hidden;
}
.brandManageWrap2 .content .c-header .c-1 .input-text {
    float: left;
    width: 260px;
    height: 34px;
    padding: 0 10px;
    border: solid 1px #d2d2d2;
    box-sizing: border-box;
    font-size: 13px;
}
.brandManageWrap2 .content .c-header .c-1 .btn {
    float: left;
    width: 70px;
    height: 34px;
    border: 0;
    font-size: 14px;
    color: #fff;
    background-color: #ee8b3c;
    box-sizing: border-box;
    cursor: pointer;
}
.brandManageWrap2 .content .c-header .c-2 {
    float: right;
    margin-top: -64px;
}
.brandManageWrap2 .content .c-header .c-2 .add {
    display: block;
    float: left;
    margin: 0 0 0 20px;
    padding: 0 15px;
    height: 32px;
    line-height: 32px;
    color: #ee8b3c;
    border-radius: 3px;
    font-size: 14px;
}
.brandManageWrap2 .content .c-header .c-2 .add .fa {
    display: block;
    float: left;
    font-size: 22px;
    margin: -1px 5px 0 0;
    font-weight: bold;
}
.brandManageWrap2 .content .c-header .c-2 .add .fa-download {
}

.brandManageWrap2 .content .c-header .c-2 .add-manageuser .fa {
    width: 16px;
    height: 16px;
    float: left;
    margin: 7px 6px 0px 0px;
    background: url("../../assets/images/manage/icon-add-manageuser.png")
        no-repeat 0px 0px;
}

.brandManageWrap2 .content .c-body {
    margin-bottom: 100px;
}

.brandManageWrap2 .content .c-body .c-2 {
    float: right;
    margin-bottom: -15px;
    position: relative;
    z-index: 2;
}
.brandManageWrap2 .content .c-body .c-2 .add {
    display: block;
    float: left;
    margin: 0 0 0 20px;
    padding: 0 15px;
    height: 32px;
    line-height: 32px;
    border: solid 1px #ee8b3c;
    color: #ee8b3c;
    border-radius: 3px;
    font-size: 14px;
}
.brandManageWrap2 .content .c-body .c-2 .add .fa {
    display: block;
    float: left;
    font-size: 22px;
    margin: -1px 5px 0 0;
    font-weight: bold;
}
.brandManageWrap2 .content .c-body .c-2 .add .fa-download {
}

.brandManageWrap2 .content .c-body table {
}
.brandManageWrap2 .content .c-body table caption {
    text-align: left;
    height: 30px;
    position: relative;
    font-weight: normal;
    font-size: 14px;
}
.brandManageWrap2 .content .c-body table caption .text {
    display: block;
    float: left;
}
.brandManageWrap2 .content .c-body table caption .text em {
    font-style: normal;
    color: #b70404;
}
.brandManageWrap2 .content .c-body table caption a {
    display: block;
    float: left;
    margin: 1px 0 0 20px;
}
.brandManageWrap2 .content .c-body table .icon {
    display: block;
    width: 16px;
    height: 16px;
    margin: 2px 2px 0 0;
    cursor: pointer;
}
.brandManageWrap2 .content .c-body table .icon-edit {
    float: left;
    background-position: 0px 0px;
    background-image: url(../../assets/images/manage/icon-form.png);
}
.brandManageWrap2 .content .c-body table .icon-check {
    float: left;
    background-position: -33px 1px;
    background-image: url(../../assets/images/manage/icon-form.png);
}
.brandManageWrap2 .content .c-body table .icon-checked {
    float: left;
    background-position: -16px 1px;
    background-image: url(../../assets/images/manage/icon-form.png);
}
.brandManageWrap2 .content .c-body table .icon-delete {
    float: left;
    background-position: -50px 0px;
    background-image: url(../../assets/images/manage/icon-form.png);
}

.brandManageWrap2 .content .c-body table thead td .icon-date {
    display: inline-block;
    width: 9px;
    height: 11px;
    margin-left: 5px;
}
.brandManageWrap2 .content .c-body table thead td .icon-arrow-down {
    display: inline-block;
    width: 11px;
    height: 6px;
    margin-left: 5px;
    background-position: 0px -7px;
}
.brandManageWrap2 .content .c-body table thead td .fa-edit {
    float: left;
    margin: 4px 0 0 -8px;
    font-size: 18px;
}
.brandManageWrap2 .content .c-body table thead td {
    position: relative;
    background-color: #ee8b3c;
    color: #fff;
    text-align: center;
    font-size: 16px;
    border-right: solid 1px #e6a467;
}
.brandManageWrap2 .content .c-body table td {
    padding: 10px 20px;
    border: solid 1px #eee;
    font-size: 14px;
}
.brandManageWrap2 .content .c-body table td a:hover {
    text-decoration: underline;
}
.brandManageWrap2 .content .c-body table tbody tr:hover {
    background-color: #f1f5f8;
}
.brandManageWrap2 .content .c-body table tbody td .icon-check {
    display: block;
    margin: 2px 0 0 -10px;
}
/* --------------- end 商标监控2 --------------- */

.td-new {
    position: relative;
}
.td-new .icon-new {
    display: block;
    position: absolute;
    top: 5px;
    left: 5px;
    width: 22px;
    height: 10px;
    background: #fff;
}
table .dropdown-menu {
    display: none;
    width: 100%;
    max-height: 250px;
    overflow-y: auto;
    background-color: #f1f5f8;
    position: absolute;
    z-index: 2;
    left: -1px;
    top: 45px;
    border: solid 1px #f1f5f8;
    padding: 0 0 15px;
    font-size: 14px;
}
table .dropdown-menu.open {
    display: block;
}
table .dropdown-menu li {
    padding: 6px 0;
    color: #000;
}
table .dropdown-menu li:hover {
    background: #dde5e7;
    cursor: default;
}
.c-body table tbody tr td {
    text-align: center;
}
</style>
