<template>
<div>
    <HeadSearch/>

    <div class="container m-t-md" style="margin-top:20px">
        <div class="row" style="min-height:600px;">
            <div class="col-md-3 cms-left">
                <div class="list-group">
                    <a class="list-group-item " :class="{'active': tabNum == 0 }" @click="switchNum(0)">联系我们</a>
                    <a class="list-group-item " :class="{'active': tabNum == 1 }" @click="switchNum(1)">用户协议</a>
                    <a class="list-group-item " :class="{'active': tabNum == 2 }" @click="switchNum(2)">用户隐私权</a>
                    <a class="list-group-item " :class="{'active': tabNum == 3 }" @click="switchNum(3)">关于我们</a>
                    <a class="list-group-item " :class="{'active': tabNum == 4 }" @click="switchNum(4)">用户帮助</a>
                    <a class="list-group-item " :class="{'active': tabNum == 5 }" @click="switchNum(5)">名词百科</a>
                    <!-- <a class="list-group-item " href="/cm_146499">产品标签</a>
                    <a class="list-group-item " href="/cm_146500">更新记录</a> -->
                </div>
            </div>
            <div class="col-md-9">
                 <template v-if="tabNum==1">
                   <Agreement/>
                </template>    
                 <template v-else-if="tabNum==2">
                   <Privacy/>
                </template>    
                 <template v-else-if="tabNum==3">
                   <AboutUs/>
                </template>    
                 <template v-else-if="tabNum==4">
                   <Help/>
                </template>    
                 <template v-else-if="tabNum==5">
                   <Encyclopedias/>
                </template>
                 <template v-else>
                   <Main/>
                </template>        
            </div>
        </div>
    </div>
</div>   
</template>

<script>

import Main from "@/pages/about_r.vue/Main"; //联系我们
import Agreement from "@/pages/about_r.vue/Agreement"; //用户协议
import Privacy from "@/pages/about_r.vue/Privacy"; //用户隐私权
import AboutUs from "@/pages/about_r.vue/AboutUs"; //关于我们
import Help from "@/pages/about_r.vue/Help"; //用户帮助
import Encyclopedias from "@/pages/about_r.vue/Encyclopedias"; //名词百科

import HeadSearch from "@/components/HeadSearch"
export default {
    components: {
        HeadSearch,Main,Agreement,Privacy,AboutUs,Help,Encyclopedias
    },
    data() {
        return {
            tabNum:0
        };
    },
    created(){
          //console.log("參數props", this.$route.query.type);
        let num= this.$route.query.type ? this.$route.query.type : 0;
          this.switchNum(num)
    },
    methods: {
        switchNum(num){
            this.tabNum=num;
        }
    }
};
</script>
<style scoped>
section.panel.aboutTitel{
    padding: 0;
}
</style>


