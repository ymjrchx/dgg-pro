<template>
    <div class="search_list_body">
        <!-- 商标，专利....搜出的列表的头部  开始 -->
        <div class="sel-bar" style="background:white;">
            <div class="container">
                <div class="top-search-nav">
                    <div class="pull-left">
                        <a class="active">案例</a>
                    </div>
                    <div class="col-md-6 pull-left">
                        <div class="form-group m-b-none">
                            <div class="input-group">
                                <input type="text" v-model="keyWord" class="form-control input-lg" style="font-size: 14px;" name="key" placeholder="请输入你想要查找的商标名称" autocomplete="off" @keyup.enter="submit">
                                <span class="input-group-btn">
                                    <button type="submit" class="input-lg btn-top icon-search" @click="submit"></button>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 商标，专利....搜出的列表的头部 结束-->
        <div class="container m-t-md">
            <div class="row">
                <div class="col-md-12">
                    <ul class="breadcrumb breadcrumb2" style="background:none;border:none;">
                        <li>
                            <a @click="goIndex">
                                <img src="@/assets/images/ic_reply.png">&nbsp;首页</a>
                        </li>
                        <li>
                            <a @click="back">文书查询</a>
                        </li>
                        <li>
                            <h1>{{oldKey}} 查询列表</h1>
                        </li>
                    </ul>
                </div>
                <div class="col-md-3 hidden-xs law_classify" style="min-height:1000px;background:#fff">
                    <h4 class="">所有目类</h4>
                    <div class="law_doc" v-if='docArr["cp_case2"] && docArr["cp_case2"].length > 0'>
                        <h1>案由</h1>
                        <Tree :dataArr='docArr["cp_case2"]' @getMsg='getMenuMsg' :activeStr='menuCon.actionCause' :keyWord='keyWord' :className='"actionCause"' :obj='nodeRequire' />
                    </div>
                    <div class="law_doc" v-if='docArr["cp_courtLevel"] && docArr["cp_courtLevel"].length > 0'>
                        <h1>法院层级</h1>
                        <Tree :dataArr='docArr["cp_courtLevel"]' @getMsg='getMenuMsg' :activeStr='menuCon.courtLevel' :keyWord='keyWord' :className='"courtLevel"' :obj='nodeRequire' />
                    </div>
                    <div class="law_doc" v-if='docArr["cp_year"] && docArr["cp_year"].length > 0'>
                        <h1>裁定年份</h1>
                        <Tree :dataArr='docArr["cp_year"]' @getMsg='getMenuMsg' :activeStr='menuCon.judgeYear' :keyWord='keyWord' :className='"judgeYear"' :obj='nodeRequire' />
                    </div>
                    <div class="law_doc" v-if='docArr["cp_program"] && docArr["cp_program"].length > 0'>
                        <h1>审理程序</h1>
                        <Tree :dataArr='docArr["cp_program"]' @getMsg='getMenuMsg' :activeStr='menuCon.proceedings' :keyWord='keyWord' :className='"proceedings"' :obj='nodeRequire' />
                    </div>
                    <div class="law_doc" v-if='docArr["cp_type"] && docArr["cp_type"].length > 0'>
                        <h1>文书类型</h1>
                        <Tree :dataArr='docArr["cp_type"]' @getMsg='getMenuMsg' :activeStr='menuCon.judgePro' :keyWord='keyWord' :className='"judgePro"' :obj='nodeRequire' />
                    </div>
                    <div class="law_doc" v-if='docArr["cp_court"] && docArr["cp_court"].length > 0'>
                        <h1>地域</h1>
                        <Tree :dataArr='docArr["cp_court"]' @getMsg='getMenuMsg' :activeStr='menuCon.area' :keyWord='keyWord' :className='"area"' :obj='nodeRequire' />
                    </div>
                </div>
                <!-- 右边列表部分 -->
                <div class="col-md-9 sel-list ajaxlist" id="ajaxlist">
                    <div class="condition">
                        <div class="tit">
                            <label>搜索条件</label>
                        </div>

                        <div class="conBox">
                            <span v-if='oldKey' style="padding-right:8px;">{{oldKey}}</span>
                            <span v-for='val,key,idx in menuCon' v-if='val' @click="cancelCon(key)">{{val}}
                                <i>X</i>
                            </span>
                        </div>
                    </div>
                    <!-- 条件筛选 开始 -->
                    <div class="panel panel-default" style="padding:15px 0 15px;">

                        <span class="font-15 text-dark m-l" v-if="totalSize == -1">正在寻找...
                        </span>
                        <span class="font-15 text-dark m-l" v-else>小顶为您找到
                            <span class="text-danger"> {{totalSize}} </span>个符合条件的案例
                        </span>
                        <!-- <div class="btn-group m-r sel-option table-r" style="float: right">公司状态</div> -->
                    </div>
                    <!-- 条件筛选 结束 -->
                    <!-- 列表 开始-->
                    <div class="listContent" v-if='totalSize > 0 '>
                        <section class="panel panel-default" id="searchlist" @click="goDetail(item.id)" style="position:relative" v-for='(item,idx) in listArr' :key='idx'>
                            <div class="caseFlag">
                                <span class="caseFlay_name">{{item.ajsf}}</span>
                                <i class="triangle-top-right"></i>
                            </div>
                            <div class="lawFlagBox">
                                <div class="lawFlg">
                                    <span>{{item.trialRound}}</span>
                                </div>
                                <!-- <div class="lawFlg">
                                    <span>二审</span>
                                </div>
                                <div class="lawFlg">
                                    <span>判决</span>
                                </div> -->
                            </div>
                            <div class="list_num">{{idx+1}}</div>
                            <a class="list-group-item clearfix  case_item" data-key="七夕">
                                <span class="clear">
                                    <span class="name">
                                        {{item.caseName}}
                                    </span>
                                    <small class="text-muted clear text-ellipsis m-t-xs">
                                        <span class="taxNum">{{item.court}}
                                            <span class="gege">|</span>{{item.caseNo}}
                                            <span class="gege">|</span>{{item.judgeDate}}</span>

                                    </small>
                                </span>
                                <div class="judeView">
                                    <h3 class="clear">
                                        <span class="fl">【法院观点】</span>
                                    </h3>
                                    <span>
                                        {{item.pjjg}}
                                    </span>
                                </div>
                            </a>
                            <!-- <footer class="panel-footer clear" style="margin-top:1px;">申请人:
                                <a class="text-primary">999999</a>
                            </footer> -->
                        </section>

                        <!-- 列表结束-->
                        <!-- 分页 开始 -->
                        <div class="pageination">
                            <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageIndex" :page-sizes="[10,15,20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="totalSize">
                            </el-pagination>
                        </div>
                    </div>
                    <!-- 分页结束 -->
                    <div class="search_no" v-if=' totalSize == 0 '>
                        <img src="../../assets/images/nodata.png" alt="">
                        <p>抱歉，未能找到相关数据</p>
                    </div>
                </div>
                <!-- 右边列表部分 -->

            </div>
        </div>
    </div>
</template>
<script>
import treeData from "@/util/testArr.js";
import Tree from "@/pages/tree/tree";
import Common, { Jump } from "@/util/common.js";
export default {
    components: {
        Tree
    },
    data() {
        return {
            showIndexArr: ["1"],
            pageSize: 10, //每页多少条
            totalSize: -1, //总条数,
            pageIndex: 1, //第几页
            treeData: treeData,
            show3: 0,
            show2: 0,
            keyWord: "", //搜索关键字
            oldKey: "",
            menuCon: {
                actionCause: "", //案由
                courtLevel: "", //法院层级
                judgeYear: "", //裁判年份
                proceedings: "", //审理程序
                judgePro: "", //文书类型
                area: "" //地域
            },
            actionCauseItem: [], //选中的案由
            areaItem: [], //选中的地域
            judgeDateSort: 0, //裁判日期排序， 1 升序； 0 降序；
            docArr: {},
            nodeRequire: {}
        };
    },
    created() {
        this.oldKey = this.keyWord = this.$route.query.keyWords;

        this.requireFunc();
    },
    watch: {
        $route: "reloadPage"
    },
    methods: {
        requireFunc(page) {
            let requireData = {
                keyWord: this.keyWord,
                actionCause: this.menuCon.actionCause,
                courtLevel: this.menuCon.courtLevel,
                judgeYear: this.menuCon.judgeYear,
                proceedings: this.menuCon.proceedings,
                judgePro: this.menuCon.judgePro,
                area: this.menuCon.area,
                judgeDateSort: this.judgeDateSort,
                pageSize: this.pageSize, //每页多少条
                pageIndex: this.pageIndex //第几页
            };
            this.nodeRequire = requireData;

            if (!page) this.getTree(requireData);
            this.getList(requireData, page);
        },
        getTree(requireData) {
            this.$axios(
                {
                    type: "post",
                    url: "companyJudge/treeNode",
                    data: requireData,
                    success: res => {
                        // this.docArr = res.data.data;
                        let docArr = res.data.data;
                        if (this.actionCauseItem.length > 0) {
                            docArr.cp_case2 = this.actionCauseItem;
                        }
                        if (this.areaItem.length > 0) {
                            docArr.cp_court = this.areaItem;
                        }
                        this.docArr = docArr;
                    }
                },
                1
            );
        },
        getList(requireData, bool) {
            this.$axios(
                {
                    type: "post",
                    url: "companyJudge/searchList",
                    data: requireData,
                    success: res => {
                        // console.log("列表", res);
                        this.listArr = res.data.data.listData;
                        this.totalSize =
                            res.data.data.totalCount > 0
                                ? Number(res.data.data.totalCount)
                                : 0;
                        Jump(document.getElementsByTagName("body")[0]);
                    }
                },
                bool
            );
        },
        //树结构获取 start ****************************************************************
        getMenuMsg(className, obj, idx) {
            if (this.menuCon[className] == obj.key) {
                this.menuCon[className] = "";
                this[className + "Item"] = [];
            } else {
                this.menuCon[className] = obj.key;
                this[className + "Item"] = [obj];
            }
            this.pageIndex = 1;

            this.requireFunc();
        },

        cancelCon(key) {
            //取消
            this.menuCon[key] = "";
            this[key + "Item"] = [];
            this.pageIndex = 1;
            this.requireFunc();
        },
        //树结构获取 end *******************************************************************
        handleCurrentChange(e) {
            //翻页 跳转到几页
            this.pageIndex = e;
            this.requireFunc(true);
        },

        handleSizeChange(e) {
            //修改条数
            this.pageSize = e;
            this.pageIndex = 1;
            this.requireFunc(true);
        },
        back() {
            this.$router.go(-1);
        },
        goIndex() {
            this.$router.push({ path: "/" });
        },
        goDetail(applicationNumber) {
            // this.$router.push({
            //     path: "/lawToolDetail",
            //     query: { id: applicationNumber }
            // });
            Common.newPage("lawToolDetail", { id: applicationNumber });
        },
        hasChild(item) {
            if (item.hasOwnProperty("children")) return 1;
            else return 0;
        },
        submit() {
            if (!this.keyWord) {
                this.$message({
                    message: "请先填写搜索内容！",
                    type: "error",
                    position: "center",
                    duration: 1000
                });
                return;
            }
            this.$router.push({
                path: "/LawToolContent",
                query: { keyWords: this.keyWord }
            });
        },
        reloadPage() {
            this.menuCon = {
                actionCause: "",
                courtLevel: "",
                judgeYear: "",
                proceedings: "",
                judgePro: "",
                area: ""
            };
            this.actionCauseItem = [];
            this.areaItem = [];
            this.pageSize = 10;
            this.pageIndex = 1;
            this.oldKey = this.keyWord;
            this.requireFunc();
        }
    },
    mounted() {}
};
</script>
<style scoped>
.search_list_body {
    margin-top: 1px;
}
/* //列表 */
a.list-group-item {
    padding: 30px 20px 10px;
}

/* //分页 */
.pageination {
    margin-top: 30px;
    padding: 20px 10px;
    background: #fff;
    border: 1px solid #eee;
    margin-bottom: 50px;
}
.law_classify > h4 {
    font-size: 16px;
    font-weight: bold;
    text-align: center;
    padding-top: 10px;
}
/* //裁判文书 */
.lawFlagBox {
    position: absolute;
    right: 40px;
    top: 0;
    overflow: hidden;
    z-index: 2;
}
.lawFlagBox > div {
    float: left;
    margin-right: 8px;
    width: 0;
    height: 0;
    border-top: 30px solid #409eff;
    border-right: 17.5px solid #409eff;
    border-bottom: 10px solid transparent;
    border-left: 17.5px solid #409eff;
}
.lawFlg > span {
    text-align: center;
    position: relative;
    min-width: 30px;
    display: block;
    font-size: 12px;
    color: white;
    top: -20px;
    left: -15px;
    font-weight: normal;
}
.list_num {
    position: absolute;
    right: 0;
    top: 0;
    z-index: 2;
    width: 35px;
    height: 35px;
    border-left: 1px solid #f1f1f1;
    border-bottom: 1px solid #f1f1f1;
    font-size: 14px;
    color: #69a0bf;
    text-align: center;
    line-height: 35px;
    background: #d7f5ff;
}
.caseFlag {
    position: absolute;
    top: 4px;
    left: -5px;
    z-index: 2;
    padding: 2px 8px;
    background-color: #409eff;
    color: #fff;
}
.triangle-top-right {
    position: absolute;
    left: 0px;
    bottom: -5px;
    width: 0;
    height: 0;
    border-top: 5px solid #0055aa;
    border-left: 5px solid transparent;
    border-right: 0px solid transparent;
}
.list-group-item.case_item {
    padding-top: 50px;
}
.list-group-item.case_item .name {
    display: block;
    margin-bottom: 18px;
}
.list-group-item.case_item .taxNum {
    color: #999;
}
.list-group-item.case_item .taxNum .gege {
    margin: 0 14px;
}
.judeView h3 {
    font-size: 16px;
}
/* //字典设置 */
.law_doc {
    padding-bottom: 14px;
    border-bottom: 1px solid #eaeaea;
}
.law_doc > div {
    max-height: 400px;
    overflow-y: auto;
    overflow-x: auto;
    padding: 0 16px;
    font-size: 14px;
}

.law_doc > h1 {
    color: #222;
    cursor: pointer;
    font-size: 16px;
}
.search_no {
    text-align: center;
    padding: 30px 0;
    background: #fff;
}
.condition {
    padding: 20px 15px;
    background: #fff;
    border: 1px solid#eeeeee;
    overflow: hidden;
}
.condition .conBox > span {
    display: inline-block;
    padding: 5px 8px;
    padding-right: 20px;
    cursor: pointer;
    font-weight: normal;
    color: #128cec;
    background-color: #ffffff;
    border: #128cec 1px solid;
    font-size: 75%;
    line-height: 1;
    vertical-align: baseline;
    cursor: pointer;
    margin-right: 10px;
    margin-bottom: 6px;
    border-radius: 2px;
    min-width: 80px;
    position: relative;
    text-align: center;
    font-size: 14px;
}
.conBox span:hover {
    color: #fff;
    background-color: #128cec;
}
.conBox span i {
    position: absolute;
    right: 4px;
    top: 5px;
}
.condition .tit {
    width: 10%;
    float: left;
    line-height: 22px;
    font-weight: 300;
    font-size: 18px;
    border-right: 2px solid #dadada;
}
.condition .conBox {
    width: 90%;
    float: left;
    padding-left: 10px;
    font-size: 0;
}
</style>