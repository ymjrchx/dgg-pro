<template>
    <div style="min-height:700px">
        <!-- 条件筛选 开始 -->
        <div class="panel panel-default" style="padding:15px 0 45px;">
            <span class="font-15 text-dark pull-left m-l" v-show="searching">小顶正在为您寻找...... </span>
            <span class="font-15 text-dark pull-left m-l" v-show="!searching">小顶为您找到
                <span class="text-danger"> {{totalSize}} </span>个符合条件的专利
            </span>
            <div class="btn-group m-r sel-option table-r" style="float: right">
                商标状态
            </div>
        </div>
        <!-- 条件筛选 结束 -->
        <!-- 列表 开始-->
      <!-- <section class="panel panel-default taxList" style="line-height:1.8">
            <a  class="list-group-item clearfix brandDetailJump brand_detail" data-key="七夕">
                <span class="pull-left thumb-lg m-r">
                    <img src="http://tm-image.qichacha.com/e7d2d22cbce848f413cee0e595e96130.jpg?x-oss-process=style/small" onerror="javascript:this.src='/material/theme/chacha/cms/v2/images/brand-default.png'" alt="七夕-顶企查"> </span>
                <span class="clear">
                    <span class="name">
                        <em>七夕</em> 福建省松溪卓越建设工程有限公司
                    </span>
                    
                    <small class="text-muted clear text-ellipsis m-t-xs">
                       <span class="taxNum">注册号 : 91350724MA31XLHH8D</span>
                       <i class="m-l"></i>
                       <span class="taxNum">申请日期 : 91350724MA31XLHH8D</span>
                       <span class="fr table-r">
                           <span class="nstatus  text-success-lt m-l-xs">在业</span>
                       </span>
                    </small>
                    <small class="text-muted clear text-ellipsis m-t-xs">
                     类别 : 福建省南平市松溪县松源街道枣园巷59号
                    </small>
                </span>
            </a>
             <footer class="panel-footer clear">申请人:<a class="text-primary">as;lfdkksdjf</a> </footer>
        </section> -->
        <div v-show="!searching">
            <template v-if="totalSize">
                <section v-for="(item,idx) in listArr" :key="idx" class="panel panel-default taxList">
                    <a  class="list-group-item clearfix brandDetailJump brand_detail" @click="goDetail(item.regNo)">
                        <span class="pull-left thumb-lg m-r">
                            <img :src="item.imageUrl ? item.imageUrl :''" :onerror="logo" alt="顶企查"> </span>
                        <span class="clear">
                            <span class="name" v-html="mathName(item.name)"></span>
                            <small class="text-muted clear text-ellipsis m-t-xs">
                                <span class="taxNum">注册号 :{{item.regNo || '-'}}</span>
                                <i class="m-l"></i>
                                <span class="taxNum">申请日期 : {{item.appDate}}</span>
                                <span class="fr table-r">
                                    <span class="nstatus  text-success-lt m-l-xs">{{mathStatus(item.status)}}</span>
                                </span>
                            </small>
                            <small class="text-muted clear text-ellipsis m-t-xs">
                                类别: {{item.intCls || '-'}}
                            </small>
                        </span>
                    </a>
                    <footer class="panel-footer clear">申请人:
                        <!-- <a class="text-primary"> {{item.applicantCn || '-'}}</a> -->
                         <a  v-if="item.url" class="text-primary" @click="goCompany(item.url,item.applicantCn)">{{item.applicantCn || '-'}}</a>
                        <span v-else>{{item.applicantCn || '-'}}</span> 
                        </footer>
                </section>
                <!-- 列表结束-->
                <!-- 分页 开始 -->
                <div class="pageination">
                    <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageIndex" :page-sizes="[10,20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="totalSize">
                    </el-pagination>
                </div>
            </template>
            <!-- 分页结束 -->
            <template v-else>
                <NoFound msg="抱歉，未找到相关数据" />
            </template>
        </div>
    </div>
</template>
<script>
import Common, { Jump } from "@/util/common";
import NoFound from "@/components/No_found";
// import userPhoto from "@/assets/images/default.jpg";

export default {
    components: {
        NoFound
    },
    data() {
        return {
            logo:'this.src="' + require('../../assets/images/default.jpg') + '"',
            pageSize: 10, //每页多少条
            totalSize: 0, //总条数,
            pageIndex: 1,
            searching: 1,
            listArr: []
        };
    },
    watch: {
        $route: "reloadPage"
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage() {
          
            let obj = {
                pageIndex: this.pageIndex,
                pageSize: this.pageSize,
                keyWord: this.$route.query.keyWord,
                type:"brand"
            };
            this.$axios({
                type: "post",
                url: "companyItem/mix",
                data: obj,
                success: res => {
                    this.searching = 0;
                    if (res.data.code == 0) {
                        this.listArr = res.data.data.listData;
                        this.totalSize = Number(res.data.data.totalCount);
                    } else {
                        this.totalSize = 0;
                    }
                    Jump(document.getElementById("app"));
                   
                }
            },1);
        },
        handleCurrentChange(e) {
            //翻页 跳转到几页
            this.pageIndex = e;
            this.initPage();
        },
        handleSizeChange(e) {
            //修改条数
            this.pageSize = e;
            this.pageIndex = 1;
            this.initPage();
        },
        mathName(str) {
            if (!str) return " - ";
            let mathStr = this.$route.query.keyWord.replace(/\s*/, "");
            let re = new RegExp(mathStr, "g");
            let newStr = (str + "").replace( re, "<em style='color:#FD485E'>" + mathStr + "</em>");
            return newStr 
        },
        goDetail(regNo) {
            this.$router.push({'path':"/Brand_detail",query:{id: regNo}})
        },
         mathStatus(str){
            if(!str || str == " ") return "未知"
            // if(str.length >2) return str.substring(0,2)

            return str;
        },
        reloadPage(){
            this.pageIndex= 1;
            this.initPage();
        },
         goCompany(companyId,name) {
            Common.newPage("Company_detail", { id: companyId ,title:name });
        },
    }
};
</script>
<style scoped>
/* @import "../assets/test.css"; */

/* //分页 */
.pageination {
    margin-top: 30px;
    padding: 20px 10px;
    background: #fff;
    border: 1px solid #eee;
    margin-bottom: 50px;
}
.table-r {
    width: 340px;
    height: 28px;
    text-align: center;
}
.taxList small > .taxNum {
    margin-top: 8px;
    display: inline-block;
}
section.panel-default {
    -webkit-transition: all 200ms ease-in;
    -moz-transition: all 200ms ease-in;
    -o-transition: all 200ms ease-in;
    transition: all 200ms ease-in;
    display: block;
}

section.panel-default:hover {
    -webkit-transform: translate3d(0, -8px, 0);
    -moz-transform: translate3d(0, -8px, 0);
    transform: translate3d(0, -8px, 0);
    transition: all 0.3s;
    -webkit-transition: all 0.3s;
    -moz-transition: all 0.3s;
    -webkit-box-shadow: 0px 2px 8px 2px rgba(0, 0, 0, 0.1),
        1px 1px 30px 5px rgba(0, 0, 0, 0.2);
    -moz-box-shadow: 0px 2px 8px 2px rgba(0, 0, 0, 0.1),
        1px 1px 30px 5px rgba(0, 0, 0, 0.2);
    box-shadow: 0px 2px 8px 2px rgba(0, 0, 0, 0.1),
        1px 1px 30px 5px rgba(0, 0, 0, 0.2);
}
.sel-list .panel .name,
.ajaxlist .panel .name {
    font-size: 17px;
    font-weight: 700;
}
section.panel-default:hover .name {
    color: #409eff;
}
</style>