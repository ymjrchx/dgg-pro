<template>
  <div class='allSelect'>
        <el-select v-model="value" size="small">
          <el-option v-for="item in options" :key="item.value" :label="item.name" :value="item.value">
          </el-option>
        </el-select>    
  </div>
</template>
<script>
  export default {
    props: {

    },
    data() {
      return {
        next:false
      }
    },
    computed:{
        
    },
    watch: {},
    methods: {
       
    }
  };
</script>
<style lang="stylus" scoped >
.allSelect{
    display inline-block
}
</style>