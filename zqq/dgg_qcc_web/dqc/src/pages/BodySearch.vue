<template>
  <div>
     <HeadSearch :val="getVal"></HeadSearch>
     <router-view></router-view>
  </div>
</template>
<script>
import HeadSearch from "@/components/HeadSearch"
export default {  
  name: "",
  components: {
    HeadSearch
  },
  data() {
    return {
      msg: "Welcome to Your Vue.js App111111111111",
      name: "张三",
      string: "8888",
      getVal:"",
      params:""   //搜索页传过来的数据
    };
  },
  created(){
    //console.log("參數1",this.$route.query)
    this.getVal=this.$route.query.keyWord;
    this.params=this.$route.query;
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