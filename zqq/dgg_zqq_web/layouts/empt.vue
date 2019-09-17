<template>
    <div>
        <nuxt/>
        <toolBox/>
        <toTop/>
    </div>
</template>
<script>
import toolBox from "~/components/toolBox";
import toTop from "~/components/toTop.vue";

export default {
    mounted() {
        if (this.$store.state.userInfo)
            sessionStorage.setItem("userId", this.$store.state.userInfo.userId);
    },
    components: { 
        toolBox, 
        toTop 
    }
};
</script>
<style>
</style>

