<template>
    <section class="panel b-a" style="position:relative">
        
        <div style="height:20px;background:#f6f6f6; width:101%;margin-left:-2px;">
        </div>
        <ul class="list-group list-group-lg no-bg auto">
            <div class="list-group-item clearfix newsList" v-if="jsonArr" v-for="(item,idx) in jsonArr" :key="idx">
                <span class="pull-left thumb-sm  m-r m-t-xs" style="width: 180px">
                    <a data-href="/p_48215430.html"> <img :src="item.titleImg" :alt="item.title"> </a>
                </span>
                <span class="clear">
                    <span>
                        <a class="newsName" style="font-size:20px;" @click="goDetail(item.title)">
                         {{item.title}}
                        </a>
                    </span>
                    <div class="m-t-sm m-b-sm text-delic3"  style="height:60px;color:#888">
                        {{item.part}}
                    </div>
                    <small class="text-muted clear text-ellipsis">
                        <div class="pull-left">来源：{{item.articleSub}}</div>
                    </small>
                </span>
            </div>
            <template v-if="!jsonArr">
                <NoFound msg="抱歉，数据出错，请稍后再试！"/>
            </template>
        </ul>
        <div class="newsPageBtn">
            <el-pagination
            background
             @current-change="handleCurrentChange"
            layout="prev, pager, next"
            :total="pageTotal">
            </el-pagination>
        </div>
        <Loading_p v-show="showLoading"/>
    </section>
</template>
<script>
import NoFound from "@/components/No_found";
import {GetNews} from '@/util/Http.js';
import Common,{Jump} from '@/util/common.js'
import Loading_p from "@/components/Loading.vue";

export default {
    props: {
         pageIndex:'',
         ajaxStr:""
    },
    watch:{
        pageIndex:'initPage',
        ajaxStr:'initPage',
    },
    data() {
        return {
            pageTotal:0,
            jsonArr:"",
             showLoading:0
        };
    },
    created(){
        this.initPage(222)
        // console.log(this.pageIndex,this.ajaxStr)
    },
    components:{
        NoFound,Loading_p
    },
    methods: {
        initPage(count){
             this.showLoading=1;
             GetNews({type:this.ajaxStr,pageIndex:this.pageIndex,pageSize:10},res=>{
                let arr=res.listData;
                // console.log(arr)
                this.jsonArr=res.listData
                this.pageTotal=Number(res.totalCount)
                if(count != 222) Jump(document.getElementById("app"))
                setTimeout(()=>{
                     this.showLoading=0
                },200)
               
            })
        },
        handleCurrentChange(e) {
            //翻页 跳转到几页
            // this.pageIndex = e;
            this.$emit("updateIndex", e);
        },
        goDetail(id){
            Common.newPage("NewsDetail", { title: id });
            
        }
    }
};
</script>
<style scoped>

</style>