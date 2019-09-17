<template>
    <div class="tab-content">
        <div class="clearfix">
            <table class="ntable qualificationTable">
                <tr>
                    <th width="200">变更日期</th>
                    <th>变更内容</th>
                   
                </tr>
                 <tbody v-if="totalSize > 0">
                    <!-- <tr v-for="(item,index) in newArr" :key="index">
                        <td><a>{{item.name}}</a></td>
                        <td>{{item.sfzh}}</td>
                        <td>{{(item.peopleZsseList)[0].zclb}}</td>
                        <td>{{(item.peopleZsseList)[0].zyyzh}}</td>
                        <td>-</td>
                    </tr> -->
                </tbody>
                <tr v-else>
                    <td colspan="2">暂未查询到已登记入库的信息</td>
                </tr>
            </table>
        </div>
        <div class="pageination">
              <el-pagination background @current-change="handleCurrentChange" layout="prev, pager, next" :pageSize="pageSize" :total="totalSize">
            </el-pagination>
        </div>
    </div>
</template>
<script>
export default {
    components: {},
    data() {
        return {
            newArr:[],
            pageSize: 10, //每页多少条
            totalSize: 0, //总条数,
            pageIndex: 1,
            tabNum: 0,
            isTab: 0
        };
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
                this.totalSize = 0;
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
.table .tb{
    background: #f2f9fc;
    font-weight: bold;
}
tr{
    text-align: center;
}
/* //tab选项内容 */
.tab-content {
    overflow: hidden;
    border: 1px solid #e9eeef;
    border-top:none;
    padding:40px  20px 15px;

}

</style>