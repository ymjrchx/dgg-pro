<template>
    <div class="tab-content">
        <div class="clearfix">
            <table class="ntable qualificationTable">
                <tr>
                    <th width="130">资质类别</th>
                    <th width="140">资质证书号</th>
                    <th width="300">资质名称</th>
                    <th width="110">发证日期</th>
                    <th width="120">证书有效期</th>
                    <th width="190">发证机关</th>
                    <th width=""></th>

                </tr>
                <tbody v-if="totalSize > 0">
                    <tr v-for="(item,index) in newArr" :key="index">
                        <td>{{item.zzlb}}</td>
                        <td>{{item.zzzsh}}</td>
                        <td>{{item.zzmc}}</td>
                        <td>{{item.fzrq}}</td>
                        <td>{{item.zsyxq}}</td>
                        <td>{{item.fzjg}}</td>
                        <td>
                            <a>证书信息</a>
                        </td>
                    </tr>
                </tbody>
                <tr v-else>
                    <td colspan="7">暂未查询到已登记入库的信息</td>
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
    props: {
        zzzz: {}
    },
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
    watch:{
        zzzz(){
            this.initArr()
        }
    },
    methods: {
        initArr(){
           
            let allSize;
            if(this.zzzz && this.zzzz.length > 0){
                allSize= this.totalSize = this.zzzz.length;
                if( allSize <= this.pageSize ){
                    this.newArr = this.zzzz
                }
                else{
                    this.newArr = this.sliceArr(1)
                }
            }
            else{
                this.totalSize= 0
            }
        },
        handleCurrentChange(e) {
            //翻页 跳转到几页
            this.pageIndex = e;
            this.newArr = this.sliceArr(e)
        },
        sliceArr(e,num=this.pageSize){
            let startIdx= (e-1)*num,
                endIdx=e*num,
                newArr;    
            if(endIdx >= this.totalSize){
               newArr=this.zzzz.slice(startIdx)
            }
            else{
               newArr=this.zzzz.slice(startIdx,endIdx)
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
    text-align: right;
    padding: 10px 10px;
    background: #fff;
    margin-bottom: 10px;
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