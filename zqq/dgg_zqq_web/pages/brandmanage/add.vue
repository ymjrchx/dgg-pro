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
                <li class="step-2">
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
        <div class="brandManageWrap5">
            <ul class="shuoming">
                <h6>3步操作，轻松导入：</h6>
                <li>1. 输入申请人名称，点击【查找】；</li>
                <li>2. 选择您要导入的申请人名称。此次将要导入的申请人，出现在【选中的企业】中；</li>
                <li>3. 确定，然后系统导入商标。可在数据显示中查看</li>
                <li>请注意，删除申请人名称，将同时删除其下所有商标数据</li>
            </ul>
            <div class="content">
                <!-- start 模拟可输入的下拉框 -->
                <div class="simulationSelect" id="s-select-1">
                    <div class="selectInputBox">
                        <input type="text" name="company" @keyup.enter="search" v-model="keywords" class="selectInput" placeholder="请输入您要导入的企业名称，全称或简称">
                        <input type="button" class="downArrow" @click="search" value="查找">
                    </div>
                    <div class="selectOptions" v-if="result.length">
                        <h3 class="options-title" v-if="0">
                            <label style="color: #727171;"><input type="checkbox" class="checkboxAll" @click="seletAll">全选</label>
                        </h3>
                        <ul class="addcompany">
                            <li v-for="(item, index) in result" :key="item.name" @click="seletedItem(index)"><input class="checkboxSingle" type="checkbox" :checked="item.checked">
                                <em class="e1">{{item.name}}</em>
                            </li>
                        </ul>
                    </div>
                </div>
                <!-- end 模拟可输入的下拉框 -->
                <div class="box2" id="box2" style="display: block;">
                    <h3 class="b-title">
                        <span class="s1">选中的企业(
                            <em class="s-number-1">{{seletedTotal}}</em>个)</span>
                        <i @click="showRemove(1)" class="fa fa-edit iconfont">&#xe613;</i>
                    </h3>
                    <ul class="company">
                        <li v-for="(item, index) in result" :key="item.name" v-if="item.checked">
                            <em class="e1">{{item.name}}</em>
                            <i @click="removeItem(index)" class="iconfont">&#xe610;</i>
                        </li>
                    </ul>
                </div>
                <div class="box2 box3" id="box3" style="display: block;overflow:visible;">
                    <h3 class="b-title">
                        <span v-if="importRes" class="s1 added" style="cursor: pointer;color:#ee8b3c;">
                            <em class="e1">已添加企业(
                                <em class="s-number-2">{{importRes.list.length}}</em>个)</em>
                            <i class="fa fa-sort-desc"></i>
                        </span>
                        <span class="s2">
                            <label class="b1">
                                <i @click="showRemove(2)" class="fa fa-edit iconfont">&#xe613;</i>
                            </label>
                            <label class="b2 open" id="del-record-box" v-if="false">
                                <div class="b-header">
                                    <i class="fa fa-clock-o iconfont">&#xe610;</i>删除记录</div>
                                <div class="delete-record" v-if="importRemoveRes.length">
                                    <h3 class="b-title">
                                        <span class="s1">删除记录(
                                            <em class="s-number-3">5</em>个)</span>
                                        <i class="fa fa-edit iconfont">&#xe610;</i>
                                    </h3>
                                    <ul class="delcompany">
                                        <li v-for="item in importRemoveRes" :key="item.applicantCn">
                                            <span class="txt" :title="item.applicantCn">{{item.applicantCn}}</span>
                                            <span class="icon">
                                                <i data_id="14891" class="fa fa-plus-circle"></i>
                                                <i data_id="14891" class="fa fa-trash-o"></i>
                                            </span>
                                        </li>
                                    </ul>
                                </div>
                            </label>
                        </span>
                    </h3>
                    <ul v-if="importRes" class="company">
                        <li v-for="(item, index) in importRes.list" :key="item.id">
                            <span class="txt">{{item.companyName}}</span>
                            <i @click="removeImport(item.id,index)" class="iconfont">&#xe610;</i>
                        </li>
                    </ul>
                </div>
                <!-- start 选中的企业 -->
                <!-- end 选中的企业 -->
                <div class="btns" @click="submit">
                    <a href="javascript:;" class="btn savecompany">确认</a>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
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
            keywords: "",
            isCheckRemove: false,
            isCheckedRemove: false,
            result: [],
            importRes: "",
            importRemoveRes: []
        };
    },
    mounted() {
        this.loadImport();
    },
    components: {},
    methods: {
        search() {
            if (!this.keywords) {
                popup.created({
                    content: "关键字不能为空",
                    time: 2000
                });
                return false;
            }
            if (
                /[`~!@#$%^&*_\-+=<>?:"\/'\\[\]·~！@#￥%……&*——\-+=？：.]/im.test(
                    this.keywords
                )
            ) {
                popup.created({
                    content: "关键字不能包含特殊字符",
                    time: 2000
                });
                return false;
            }
            this.$Http(
                "/userTrademark/findCompanyByName",
                {
                    userId: this.$store.state.userInfo.userId,
                    name: this.keywords,
                    count: 1000
                },
                "get",
                true
            ).then(res => {
                let arr = res.data.data.list;
                if (arr.length == 0) {
                    popup.created({
                        content: "未查询到相关企业",
                        time: 2000
                    });
                    return false;
                }
                let obj = [];
                arr.map(item => {
                    obj.push({
                        name: item
                    });
                });
                this.result = obj;
            });
        },
        loadImport() {
            this.$Http(
                "/userTrademark/findImportCompanyByUserId",
                {
                    userId: this.$store.state.userInfo.userId,
                    pageSize: 1000,
                    flag: 1,
                    pageNum: 1
                },
                "get",
                true
            ).then(res => {
                this.importRes = res.data.data;
            });
        },
        // loadImportRemove() {
        //   this.$Http('/userTrademark/findDeleteByUserId', { userId: this.$store.state.userInfo.userId, count: 1000 }, 'get', true).then((res) => {
        //     this.importRemoveRes = res.data.data
        //   })
        // },
        seletedItem(i) {
            this.$set(this.result[i], "checked", !this.result[i].checked);
        },
        removeItem(i) {
            this.result[i].checked = false;
        },
        removeImport(id, i) {
            popup.created({
                type: "confirm",
                content: "您确定删除该企业吗？",
                success: bool => {
                    if (bool) {
                        this.$Http(
                            "/userTrademark/delCompanyByUserId",
                            {
                                userId: this.$store.state.userInfo.userId,
                                ids: id
                            },
                            "get",
                            true
                        ).then(res => {
                            this.importRes.list.splice(i, 1);
                        });
                    }
                }
            });
        },
        showRemove(type) {
            if (type == 1) {
                this.isCheckRemove = !this.isCheckRemove;
            } else {
                this.isCheckedRemove = !this.isCheckedRemove;
            }
        },
        seletAll() {
            let isAll = true;
            this.result.map(item => {
                if (!item.checked) {
                    isAll = false;
                }
            });
            if (isAll) {
                this.result.map(item => {
                    this.$set(item, "checked", false);
                });
            } else {
                this.result.map(item => {
                    this.$set(item, "checked", true);
                });
            }
        },
        submit() {
            let company = [];
            this.result.map(item => {
                if (item.checked) {
                    company.push(item.name);
                }
            });
            if (!company.length) {
                popup.created({
                    content: "请先选择企业",
                    time: 2000
                });
                return false;
            }
            if (company.length > 3) {
                popup.created({
                    content: "单次导入企业不能超过3",
                    time: 2000
                });
                return false;
            }
            sessionStorage.setItem("isFrom", 1);
            this.$Http(
                "/userTrademark/importCompany",
                {
                    userId: this.$store.state.userInfo.userId,
                    applicantCns: JSON.stringify(company)
                },
                "post",
                true
            ).then(res => {
                sessionStorage.removeItem("isFrom");
                this.$router.push("/brandmanage/list");
            });
        }
    },
    computed: {
        seletedTotal() {
            let i = 0;
            this.result.map(item => {
                if (item.checked) {
                    i++;
                }
            });
            return i;
        }
    }
};
</script>
<style scoped>
/* ------------------ start 监控数据1进度条 ------------------ */
.company li {
    line-height: 35px;
    overflow: hidden;
}

.company li i {
    float: right;
    cursor: pointer;
}

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

/* ------------------ end 监控数据1进度条------------------- */

.brandManageWrap5 {
    width: 100%;
}

.brandManageWrap5 .content {
    margin-top: 70px;
    margin-bottom: 100px;
}

.brandManageWrap5 .content .simulationSelect {
    margin: auto;
    width: 760px;
    height: 56px;
}

.brandManageWrap5 .content .simulationSelect .selectInputBox .downArrow {
    width: 100px;
    border: 0;
    color: #fff;
    font-size: 18px;
    background-color: #ee8b3c;
}

.brandManageWrap5 .content .simulationSelect .selectOptions {
    top: 55px;
    display: block;
    height: auto;
    border-top: solid 1px #ddd;
    overflow: hidden;
    z-index: 99;
}

.brandManageWrap5 .content .simulationSelect .selectOptions .options-title {
    width: 96%;
    box-sizing: border-box;
    color: #008cd6;
}

.brandManageWrap5 .content .simulationSelect .selectOptions .options-title .s1 {
}

.brandManageWrap5
    .content
    .simulationSelect
    .selectOptions
    .options-title
    .fa-edit {
    float: right;
    cursor: pointer;
    font-size: 18px;
    margin: 9px 18px 0 0;
}

.brandManageWrap5 .content .simulationSelect .selectOptions ul {
    max-height: 270px;
    overflow: auto;
}

.brandManageWrap5 .content .simulationSelect .selectOptions ul li {
    font-size: 14px;
}

.brandManageWrap5 .content .simulationSelect .selectOptions ul li:hover {
    background-color: #dfe5e7;
}

.brandManageWrap5 .content .simulationSelect .selectOptions ul li .fa-trash-o {
    display: none;
    float: right;
    margin: 9px 5px 0 0;
    font-size: 18px;
    cursor: pointer;
}

.brandManageWrap5 .content .simulationSelect .selectOptions .btns {
}

.brandManageWrap5 .content .simulationSelect .selectOptions .btns .btn {
    width: 100%;
    padding: 0 1px;
    text-align: center;
    margin-top: 20px;
}

.brandManageWrap5 .content .box2 {
    margin-top: 350px;
}

.brandManageWrap5 .shuoming {
    width: 1200px;
    margin: auto;
    padding: 20px 0 0;
    font-size: 14px;
    color: #999;
}

.brandManageWrap5 .shuoming h6 {
    font-size: 14px;
    margin: 0 0 5px;
    font-weight: normal;
}

.brandManageWrap5 .content .box2 {
    margin-top: 20px;
}

#box2 .txt .e1 {
    display: block;
    float: left;
}

#box2 .txt .icon-new {
    display: block;
    float: left;
    margin: 0px 0 0 5px;
    width: 22px;
    height: 10px;
    background: url(https://www.quandashi.com/home/image/icon-new.png) no-repeat
        0px 0px;
}

.content .box2 .b-title .s2 label {
    display: block;
    float: left;
    cursor: pointer;
    font-size: 14px;
}

.content .box2 .b-title .s2 .b1 {
    /*margin-right:30px;*/
}
.content .box2 .b-title .s2 .b1 i {
}

.content .box2 .b-title .s2 .b2 {
    position: relative;
}

.content .box2 .b-title .s2 .fa {
    color: #ee8b3c;
    float: left;
    font-size: 17px;
    margin: 1px 3px 0 0;
}

.brandManageWrap5 .content .simulationSelect {
    height: auto;
}

.simulationSelect .selectInputBox {
    height: 56px;
}

.simulationSelect .selectOptions {
    position: relative;
}

.brandManageWrap5 .content .simulationSelect .selectOptions ul {
    max-height: 175px;
}

.brandManageWrap5 .content .simulationSelect .selectOptions {
    top: 0px;
}

/*删除记录*/
.delete-record {
    display: none;
    z-index: 99;
    position: absolute;
    top: 30px;
    left: 91px;
    width: 220px;
    border: solid 1px #ddd;
    background-color: #fff;
}

.brandManageWrap5 .content .box3 {
    margin-top: 0;
    border-top: 0;
}

.content .box3 .b-title {
    border-bottom: 0;
    color: #666;
    font-size: 15px;
}

.content .box3 .b-title .s1 .e1 {
    display: block;
    float: left;
}

.content .box3 .b-title .s1 .fa-sort-desc {
    display: block;
    float: left;
    margin: -10px 0 0 4px;
    color: #ee8b3c;
    font-size: 28px;
}

.content .box3 .b-title .s2 {
    display: block;
    float: right;
    margin: 8px 0 0;
}

.content .box3 .delete-record .b-title {
    border-bottom: dotted 1px #ddd;
}

.content .box3 .delete-record .b-title .fa {
    float: right;
    margin: 10px 0 0 0;
}

.content .box3 .delete-record ul {
    max-height: 200px;
    overflow-y: auto;
}

.content .box3 .delete-record ul li {
    padding-right: 5px;
}

.content .box3 .delete-record ul li .txt {
    width: 126px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.content .box3 .delete-record ul li .icon {
    display: none;
    height: 30px;
    float: right;
}

.content .box3 .delete-record ul li .icon .fa {
    float: left;
    font-size: 18px;
    display: block;
    margin: 10px 5px 0;
}

#del-record-box.open .delete-record {
    display: block;
}

#box3 .list {
    display: none;
}

#s-select-1 .selectOptions .addcompany li {
    cursor: pointer;
}

#s-select-1 .selectOptions .addcompany li .checkboxSingle {
    left: 15px;
}

#s-select-1 .selectOptions .addcompany li .e1 {
    padding: 0 0 0 20px;
}

.content .box2 {
    width: 760px;
    margin: auto;
    border: solid 1px #ddd;
    overflow: hidden;
    background-color: #f2f5f7;
}

.content .box2 .b-title {
    width: 100%;
    height: 35px;
    border-bottom: solid 1px #dcdee0;
    box-sizing: border-box;
    padding: 0 15px;
    color: #ee8b3c;
    font-weight: normal;
}

.content .box2 .b-title .s1 {
    display: block;
    float: left;
    margin: 9px 5px 0 0;
    font-size: 14px;
}

.content .box2 .b-title .fa-edit {
    float: right;
    cursor: pointer;
    font-size: 17px;
    margin: 9px 3px 0 0;
}

.content .box2 ul {
    width: 100%;
    max-height: 350px;
    overflow: auto;
}

.content .box2 ul li {
    padding: 0 15px;
    height: 35px;
    font-size: 14px;
}

.content .box2 ul li:hover {
    background-color: #dfe5e7;
}

.content .box2 ul li .txt {
    display: block;
    float: left;
}

.content .box2 ul li .fa-trash-o {
    display: none;
    float: right;
    margin: 9px 5px 0 0;
    font-size: 18px;
    cursor: pointer;
}

.content .btns {
    width: 760px;
    margin: auto;
}

.content .btns .btn {
    width: 100%;
    padding: 0 1px;
    text-align: center;
    margin-top: 20px;
}
</style>
