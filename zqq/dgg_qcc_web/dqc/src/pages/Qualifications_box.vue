<template>
    <div class="search_list_body">
        <div class="container m-t-md">
            <div class="elib-left">
                <div class="list-group elib-list-group elib-left">
                    <a class="list-group-item "  @click="goIndex">
                        <h2>首页</h2>
                    </a>
                    <a class="list-group-item" :class="{act:tabNum ==0 }" @click="goLink('/Qualifications_company',0)">
                        <h2>企业库</h2>
                    </a>
                    <a class="list-group-item" :class="{act:tabNum ==1 }" @click="goLink('/Qualifications_resume',1)">
                        <h2>简历库</h2>
                    </a>
                </div>
            </div>
            <div class="elib-main" v-show="tabNum == 0">
               <Company/>
            </div>
            <div class="elib-main" v-if="showRsume" v-show="tabNum == 1">
               <Resume/>
            </div>
        </div>
    </div>
</template>
<script>

import Company from "@/pages/Qualifications_company"
import Resume from "@/pages/Qualifications_resume"
import Store from '@/store'
export default {
    components: {
        Company,Resume
    },
    data() {
        return {
            pageSize: 10, //每页多少条
            totalSize: 500, //总条数,
            pageIndex: 1,
            tabNum:0,
            showRsume:0
        };
    },
    created(){
         Store.commit("updateHeadActive",1)
    },
    methods: {
        handleCurrentChange(e) {
            //翻页 跳转到几页
            this.pageIndex = e;
        },

        handleSizeChange(e) {
            //修改条数
            this.pageSize = e;
        },
        back() {
            this.$router.go(-1);
        },
        goIndex() {
            this.$router.push({ path: "/" });
        },
        goLink(path,num){
            // this.$router.push(path);
            if(this.showRsume == 0) this.showRsume=1
            this.tabNum=num;
        }
    },
    mounted() {}
};
</script>
<style scoped>
/* @import "../assets/test.css"; */
@import "../assets/elib.css";
.search_list_body {
    margin-top: 1px;
}
/* //分页 */
.pageination {
    margin-top: 30px;
    padding: 20px 10px;
    background: #fff;
    border: 1px solid #eee;
    margin-bottom: 50px;
}
</style>