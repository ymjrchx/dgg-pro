<template>
    <div class="tab-content">
        <div class="clearfix">
            <table class="ntable qualificationTable">
                <tr>
                    <th width="">姓名</th>
                    <th width="">身份证号码</th>
                    <th width="">注册类别</th>
                    <th width="">注册号（执业印章号）</th>
                    <th width="">注册专业</th>
                </tr>
                <tbody v-if="totalSize">
                    <tr v-for="(item,index) in newArr" :key="index">
                        <td><a @click.stop="openDetail(index)">{{item.name}}</a></td>
                        <td>{{item.sfzh}}</td>
                        <td>{{(item.peopleZsseList)[0].zclb}}</td>
                        <td>{{(item.peopleZsseList)[0].zyyzh}}</td>
                        <td>{{(item.peopleZsseList)[0].zczy || "-"}}</td>
                    </tr>
                </tbody>
                <tr v-else>
                    <td colspan="7">暂未查询到已登记入库的信息</td>
                </tr>
            </table>
        </div>
          <!-- //注册人员详细信息 开始 -->
            <el-dialog title="注册人员详细信息" :visible.sync="regUserShow"  width="40%">
                  <table class="ntable ntable-odd data" v-if="regUserdetail">
                    <tr>
                        <td width="20%" class="tb">姓名</td> 
                        <td width="30%">{{regUserdetail.name}}</td> 
                        <td width="20%" class="tb">性别</td>
                        <td width="30%">{{regUserdetail.xb}}</td>
                    </tr>
                    <tr>
                        <td width="20%" class="tb">证件类型</td> 
                        <td width="30%">{{regUserdetail.zjlx}}</td> 
                        <td width="20%" class="tb">证件号码</td>
                        <td width="30%">{{regUserdetail.sfzh}}</td>
                    </tr>
                    <tr>
                        <td width="20%" class="tb">注册类别</td> 
                        <td width="30%">{{regUserdetail.peopleZsseList[0].zclb}}</td> 
                        <td width="20%" class="tb">注册专业</td>
                        <td width="30%">{{regUserdetail.zczy || "-"}}</td>
                    </tr>
                    <tr>
                        <td width="20%" class="tb">证书编号</td> 
                        <td width="30%">{{regUserdetail.peopleZsseList[0].zsbh}}</td> 
                        <td width="20%" class="tb"> 执业印章号</td>
                        <td width="30%">{{regUserdetail.peopleZsseList[0].zyyzh}}</td>
                    </tr>
                    <tr>
                        <td width="20%" class="tb">有效期</td> 
                        <td width="30%">{{regUserdetail.peopleZsseList[0].yxq}}</td> 
                        <td width="20%" class="tb">注册单位</td>
                        <td width="30%">{{regUserdetail.peopleZsseList[0].zcdw}}</td>
                    </tr>
                </table>
            </el-dialog>
            <!-- //注册人员详细信息 结束 -->
        <div class="pageination">
          <el-pagination background @current-change="handleCurrentChange" layout="prev, pager, next" :pageSize="pageSize" :total="totalSize">
            </el-pagination>
        </div>
    </div>
</template>
<script>
export default {
    props: {
        reg: {}
    },
    data() {
        return {
            regUserdetail:"",
            regUserShow:false,
            newArr:[],
            pageSize: 10, //每页多少条
            totalSize: 0, //总条数,
            pageIndex: 1,
            tabNum: 0,
            isTab: 0
        };
    },
    watch: {
        reg() {
            this.initArr();
        }
    },
    methods: {
        initArr() {
            let allSize;
            if (this.reg && this.reg.length > 0) {
                allSize = this.totalSize = this.reg.length;
                if (allSize <= this.pageSize) {
                    this.newArr = this.reg;
                } else {
                    this.newArr = this.sliceArr(1);
                }
            } else {
                this.totalSize =  0;
            }
        },
        handleCurrentChange(e) {
            //翻页 跳转到几页
            this.pageIndex = e;
            this.newArr = this.sliceArr(e);
        },
        sliceArr(e, num = this.pageSize) {
            let startIdx = (e - 1) * num,
                endIdx = e * num,
                newArr;
            if (endIdx >= this.totalSize) {
                newArr = this.reg.slice(startIdx);
            } else {
                newArr = this.reg.slice(startIdx, endIdx);
            }
            return newArr;
        },
        openDetail(idx){
            this.regUserdetail=(this.newArr)[idx];
            this.regUserShow=true;
        }
    },
    mounted() {}
};
</script>
<style scoped>
/* @import "../../assets/test.css"; */
.search_list_body {
    margin-top: 1px;
}
/* //分页 */
.pageination {
    padding: 10px;
    background: #fff;
    margin-bottom: 10px;
    text-align: right;
}
.table .tb {
    background: #f2f9fc;
    font-weight: bold;
}
tr {
    text-align: center;
}
/* //tab选项内容 */
.tab-content {
    overflow: hidden;
    border: 1px solid #e9eeef;
    border-top: none;
    padding: 40px 20px 15px;
}
</style>