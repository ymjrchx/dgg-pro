<template>
  <div>
     <Head></Head>
      <router-view></router-view>
  </div>
</template>
<script>
import Head from "@/components/Head"
export default {
  name: "",
  components: {
    Head
  },
  data() {
    return {
      msg: "Welcome to Your Vue.js App111111111111",
      name: "张三",
      string: "8888"
    };
  },
  methods: {
    update(val) {
      this.string = val;
    }
  }
};
</script>
<style>

</style>