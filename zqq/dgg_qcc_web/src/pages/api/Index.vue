<template>
    <div>
      <Head/>
      <keep-alive include="saveDataContent">
       <router-view></router-view>
       </keep-alive>
    </div>
</template>
<script>
import Head from "./Head";
import Tab_content from "./Tab_content";
export default {
    name: "",
    components: {
        Head,Tab_content
    },
    data() {
        return {};
    },
    created() {
        // console.log(this.$route.query.title)
        if(this.$route.query.title){
            setTimeout(()=>{
                switch(this.$route.query.title){
                case "公司核名验证":this.$router.push('/Apikey');break;
                }
            },100)
        }
    },
    methods: {
        func() {
            //router.push({ path: '/F' })
            // this.$router.push({ path: "/F", query: { id: 2 } });
        },
        update(val) {
            this.string = val;
        }
    }
};
</script>
<style>

</style>