<template>
    <div class="search_no" v-else>
        <img src="../assets/images/nodata.png" alt="">
        <p>{{msg || "抱歉，未找到相关数据！"}}</p>
    </div>
</template>
<script>
export default {
    props: {
        msg: ""
    }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* //未找到页面 */
.search_no{
    text-align: center;
    padding: 50px;
    background: #fff;
}
.search_no img{
    width: 80px;
    margin-bottom: 15px;
}
</style>
