<template>
    <div class="col-md-12">
        <div class="panel" style="margin-bottom:10px">
            <div class="panel-heading clearfix">
                <h4 class="pull-left">消息中心（
                    <span class="text text-danger unreadmsgnum">0 </span>封未读）</h4>
                <div class="pull-right">
                    <a class="btn btn-default"><i class="icon  icon-refresh"></i> 全部已读</a>
                </div>
            </div>
        </div>
        <div class="panel">
            <div class="panel-body">
                <table class="ptable" style="width:100%">
                    <thead>
                        <tr>
                            <th width="220">消息种类</th>
                            <th>内容详情</th>
                            <th width="300">更新时间</th>
                            
                        </tr>
                    </thead>
                    <tbody v-if="hasMsg">
                        <tr>
                            <td>
                                新增工具
                            </td>
                            <td>
                                新增公司范围生成器，智能生成经营范围</td>
                            <td>2018-09-01 17:00</td>
                        </tr> 
                         <tr>   
                            <td>
                                新增版块
                            </td>
                            <td>
                                新增企业税号查询</td>
                            <td>2018-08-20 17:10</td>
                        </tr>
                         <tr>    
                            <td>
                                新增版块
                            </td>
                            <td>
                                新增企业商标查询</td>
                            <td>2018-08-21 17:00</td>
                        </tr>
                        <tr>    
                            <td>
                                新增版块
                            </td>
                            <td>
                               新增专利查询</td>
                            <td>2018-08-21 17:00</td>
                        </tr>
                    </tbody>
                </table>
                <template v-if="!hasMsg">
                    <NoFound msg="暂无相关消息"/>
                </template>
            </div>
        </div>
    </div>
</template>
<script>
import NoFound from "@/components/No_found"
export default {
    props: {
        data: {}
    },
    components:{
        NoFound
    },
    data() {
        return {
            hasMsg:1
        };
    },
    created() {},
    methods: {

    }
};
</script>
    
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.ptable th {
    background: #FAFAFA;
    border: #eee 1px solid;
    border-collapse: collapse;
    padding: 17px 5px 17px 12px;
    color: #444;
    line-height: 19px;
    font-weight: bold;
}
.ptable th:not(:last-child) {
    border-right: #F2F9FC 1px solid;
}
.ptable td {
    padding: 12px 5px 12px 12px;
    border: #eee 1px solid;
    word-break: break-all;
    font-size: 14px;
    line-height: 19px;
    color: #222;
}
.ptable td:not(:last-child) {
    border-right: transparent 1px solid;
}
</style>
    