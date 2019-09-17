<template>
    <div>
        <mini/>
        <div class="w" style="padding-bottom:50px;">
            <div class="breadcrumb">
                <span>当前位置：</span>
                <span class="breadcrumb-title">
                    <a href="">初审公告</a>
                </span>
            </div>

            <nuxt/>
        </div>
        <toolBox/>
        <toTop/>
        <mainFoot/>
    </div>
</template>
<script>
import mini from "~/components/header/mini";
import toolBox from "~/components/toolBox";
import toTop from "~/components/toTop.vue";
import mainFoot from "~/components/foot/mainFoot.vue";
import { Http } from "~/plugins/axios.js";

export default {
    data() {
        return {};
    },
    components: { toolBox, toTop, mini, mainFoot },
    // mounted() {
    //     Http("classfirst/query", "", "").then(res => {
    //         // console.log("45类", res);
    //         this.classify45 = res.data.data;
    //     });
    // },
    methods: {}
};
</script>
<style scoped>
/* //新增 */

/* //***************************************** */
.breadcrumb {
    margin-top: 20px;
    margin-bottom: 20px;
}
.breadcrumb-title,
.breadcrumb-title a {
    color: #ff7200;
}
</style>

