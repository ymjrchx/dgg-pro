<template>
    <div class="nofind">
      <img src="../assets/images/big.png" alt="">
        <p >{{text || '暂无数据'}}</p>  
    </div>
</template>
<script>
export default {
    props: {
        text: {
            default: "暂无数据"
        }
    },
    data() {
        return {
        };
    },
};
</script>
<style lang="stylus" scoped>
    .nofind{
        margin: 110px auto 0;
        width: 300px;
        height: 400px;
        text-align: center;
        font-size: 16px;

        p{
            margin-top: 40px;
            color: #5a5a5a;
            &.r{
                color #ff0000;
            } 
        }
    }

</style>
