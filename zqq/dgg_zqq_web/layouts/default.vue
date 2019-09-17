<template>
  <div>
    <template>
      <mini-head />
    </template>
    <nuxt/>
    <template>
      <main-foot />
    </template>
    <toolBox/>
  </div>
</template>

<script>
import miniHead from "@/components/header/mini";
import mainFoot from "@/components/foot/mainFoot";
import toolBox from "@/components/toolBox";

export default {
    components: {
        miniHead,
        mainFoot,
        toolBox
    },
    mounted() {
       
        if (this.$store.state.userInfo)
            sessionStorage.setItem("userId", this.$store.state.userInfo.userId);
    }
};
</script>

<style>
</style>

