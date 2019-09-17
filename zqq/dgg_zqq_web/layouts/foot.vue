<template>
    <div>
        <nuxt/>
        <toTop/>
        <mainFoot/>
    </div>
</template>
  <script>
import mainFoot from "~/components/foot/mainFoot.vue";
import toTop from "~/components/toTop.vue";

export default {
    created() {},
    components: {
        mainFoot,
        toTop
    },
    mounted() {
        if (this.$store.state.userInfo)
            sessionStorage.setItem("userId", this.$store.state.userInfo.userId);
    }
};
</script>
<style>
</style>
