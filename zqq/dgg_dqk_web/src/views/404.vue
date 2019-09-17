<template>
  <div class="error">
      <div>
          <router-link to='/main'>
            <el-button type="primary">返回首页</el-button>
          </router-link>
      </div>
      <img src="../assets/images/404.gif" alt="">
  </div>
</template>
<script>
export default {
  data() {
    return {
    };
  },
};
</script>
<style scoped>
.error{
    text-align: center;
    padding-top: 40px;
}
.error>div{
    margin: auto;
    width: 1200px;
    padding: 40px 0;
    text-align: right;
}
</style>
