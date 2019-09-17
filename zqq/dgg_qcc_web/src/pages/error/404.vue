<template>

    <div class="error-warp">
        <Head/>
        <div class="w backs" @click="back2"> 返回上一级</div>
        <div class="error-404">
            <div class="error-txt">抱歉~请求页面失败...请检查网络后再试</div>
            <img src="../../assets/images/404.gif" />
            <img src="../../assets/images/piao1.png" class="paper paper1" />
            <img src="../../assets/images/piao2.png" class="paper paper2" />
            <img src="../../assets/images/piao3.png" class="paper paper3" />
        </div>
    </div>

</template>
<script>
import Head from "@/components/Head";
export default {
    data() {
        return {};
    },
    components: {
        Head
    },
    methods: {
        back2(){
             this.$router.go(-2);
        }
    },
    mounted() {
        
    }
};
</script>
<style scoped>
/*
 * Author:xtt
 * 报错页面 css style
 * 2017.08.08
 */
 .backs{
     padding: 24px 50px;
     font-size: 16px;
     color: #3685fc;
     text-align: left;
     background: url('../../assets/images/ic_reply.png') 26px center no-repeat;
     cursor: pointer;
 }
.error-warp {
    display: block;
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    text-align: center;
}
.error-404 {
    position: absolute;
    width: 100%;
    left: 0;
    top: 50%;
    margin-top: -188px;
}
.error-txt {
    font-size: 30px;
    color: #3685fc;
    margin-bottom: 25px;
}
.error-btn-group {
    margin-top: 50px;
}
.error-btn-group a {
    display: inline-block;
    vertical-align: middle;
    width: 120px;
    line-height: 36px;
    border-radius: 18px;
    color: #fff;
    background: #f39628;
    margin: 0 10px;
    text-decoration: none;
}
.error-btn-group a:hover {
    background: #da8622;
}
.paper {
    position: absolute;
}
.paper1 {
    top: 225px;
    left: 50%;
    margin-left: -261px;
    -webkit-animation: pl1 10s linear infinite;
    -moz-animation: pl1 10s linear infinite;
    -ms-animation: pl1 10s linear infinite;
    animation: pl1 10s linear infinite;
}
.paper2 {
    top: 142px;
    left: 50%;
    margin-left: -100px;
    -webkit-animation: pl2 4s linear infinite;
    -moz-animation: pl2 4s linear infinite;
    -ms-animation: pl2 4s linear infinite;
    animation: pl2 4s linear infinite;
}
.paper3 {
    top: 225px;
    left: 50%;
    margin-left: 55px;
    -webkit-animation: pl3 5s linear infinite;
    -moz-animation: pl3 5s linear infinite;
    -ms-animation: pl3 5s linear infinite;
    animation: pl3 5s linear infinite;
}
.error-404 > p {
    padding: 20px 0 0;
    color: #666;
    font-size: 16px;
}

.error-404 > p + .error-btn-group {
    margin-top: 40px;
}
.error-develop {
    position: absolute;
    top: 50%;
    left: 50%;
    -webkit-transform: translateX(-50%) translateY(-50%);
    -moz-transform: translateX(-50%) translateY(-50%);
    -o-transform: translateX(-50%) translateY(-50%);
    -ms-transform: translateX(-50%) translateY(-50%);
    transform: translateX(-50%) translateY(-50%);
}
.error-develop img {
    max-width: 100%;
}
@keyframes pl1 {
    from {
        top: 0;
        opacity: 1;
    }
    15% {
        -webkit-transform: rotate(20deg) translateX(-10px);
        -moz-transform: rotate(20deg) translateX(-10px);
        -ms-transform: rotate(20deg) translateX(-10px);
        transform: rotate(20deg) translateX(-10px);
    }
    50% {
        -webkit-transform: rotate(0deg) translateX(0);
        -moz-transform: rotate(0deg) translateX(0);
        -ms-transform: rotate(0deg) translateX(0);
        transform: rotate(0deg) translateX(0);
    }
    75% {
        -webkit-transform: rotate(-20deg) translateX(10px);
        -moz-transform: rotate(-20deg) translateX(10px);
        -ms-transform: rotate(-20deg) translateX(10px);
        transform: rotate(-20deg) translateX(10px);
    }
    90% {
        top: 225px;
        -webkit-transform: rotate(0deg) translateX(0);
        -moz-transform: rotate(0deg) translateX(0);
        -ms-transform: rotate(0deg) translateX(0);
        transform: rotate(0deg) translateX(0);
    }
    100% {
        top: 336px;
        -webkit-transform: rotate(0deg) translateX(0);
        -moz-transform: rotate(0deg) translateX(0);
        -ms-transform: rotate(0deg) translateX(0);
        transform: rotate(0deg) translateX(0);
        opacity: 0;
    }
}

@-webkit-keyframes pl1 {
    from {
        top: 0;
        opacity: 1;
    }
    15% {
        -webkit-transform: rotate(20deg) translateX(-10px);
        -moz-transform: rotate(20deg) translateX(-10px);
        -ms-transform: rotate(20deg) translateX(-10px);
        transform: rotate(20deg) translateX(-10px);
    }
    50% {
        -webkit-transform: rotate(0deg) translateX(0);
        -moz-transform: rotate(0deg) translateX(0);
        -ms-transform: rotate(0deg) translateX(0);
        transform: rotate(0deg) translateX(0);
    }
    75% {
        -webkit-transform: rotate(-20deg) translateX(10px);
        -moz-transform: rotate(-20deg) translateX(10px);
        -ms-transform: rotate(-20deg) translateX(10px);
        transform: rotate(-20deg) translateX(10px);
    }
    90% {
        top: 225px;
        -webkit-transform: rotate(0deg) translateX(0);
        -moz-transform: rotate(0deg) translateX(0);
        -ms-transform: rotate(0deg) translateX(0);
        transform: rotate(0deg) translateX(0);
    }
    100% {
        top: 336px;
        -webkit-transform: rotate(0deg) translateX(0);
        -moz-transform: rotate(0deg) translateX(0);
        -ms-transform: rotate(0deg) translateX(0);
        transform: rotate(0deg) translateX(0);
        opacity: 0;
    }
}
@keyframes pl2 {
    from {
        top: 80px;
        opacity: 1;
    }
    15% {
        transform: rotate(20deg) translateX(-10px);
    }
    50% {
        transform: rotate(0deg) translateX(0);
    }
    75% {
        transform: rotate(-20deg) translateX(10px);
    }
    90% {
        top: 140px;
        transform: rotate(0deg) translateX(0);
    }
    to {
        top: 142px;
        transform: rotate(0deg) translateX(0);
        opacity: 0;
    }
}

@-webkit-keyframes pl2 {
    from {
        top: 80px;
        opacity: 1;
    }
    15% {
        -webkit-transform: rotate(20deg) translateX(-10px);
        -moz-transform: rotate(20deg) translateX(-10px);
        -ms-transform: rotate(20deg) translateX(-10px);
        transform: rotate(20deg) translateX(-10px);
    }
    50% {
        -webkit-transform: rotate(0deg) translateX(0);
        -moz-transform: rotate(0deg) translateX(0);
        -ms-transform: rotate(0deg) translateX(0);
        transform: rotate(0deg) translateX(0);
    }
    75% {
        -webkit-transform: rotate(-20deg) translateX(10px);
        -moz-transform: rotate(-20deg) translateX(10px);
        -ms-transform: rotate(-20deg) translateX(10px);
        transform: rotate(-20deg) translateX(10px);
    }
    90% {
        top: 140px;
        -webkit-transform: rotate(0deg) translateX(0);
        -moz-transform: rotate(0deg) translateX(0);
        -ms-transform: rotate(0deg) translateX(0);
        transform: rotate(0deg) translateX(0);
    }
    to {
        top: 142px;
        -webkit-transform: rotate(0deg) translateX(0);
        -moz-transform: rotate(0deg) translateX(0);
        -ms-transform: rotate(0deg) translateX(0);
        transform: rotate(0deg) translateX(0);
        opacity: 0;
    }
}
@-webkit-keyframes pl3 {
    from {
        top: 60px;
        opacity: 1;
    }
    15% {
        -webkit-transform: rotate(20deg) translateX(-10px);
        -moz-transform: rotate(20deg) translateX(-10px);
        -ms-transform: rotate(20deg) translateX(-10px);
        transform: rotate(20deg) translateX(-10px);
    }
    50% {
        -webkit-transform: rotate(0deg) translateX(0);
        -moz-transform: rotate(0deg) translateX(0);
        -ms-transform: rotate(0deg) translateX(0);
        transform: rotate(0deg) translateX(0);
    }
    75% {
        -webkit-transform: rotate(-20deg) translateX(10px);
        -moz-transform: rotate(-20deg) translateX(10px);
        -ms-transform: rotate(-20deg) translateX(10px);
        transform: rotate(-20deg) translateX(10px);
    }
    90% {
        top: 220px;
        -webkit-transform: rotate(0deg) translateX(0);
        -moz-transform: rotate(0deg) translateX(0);
        -ms-transform: rotate(0deg) translateX(0);
        transform: rotate(0deg) translateX(0);
    }
    to {
        top: 225px;
        -webkit-transform: rotate(0deg) translateX(0);
        -moz-transform: rotate(0deg) translateX(0);
        -ms-transform: rotate(0deg) translateX(0);
        transform: rotate(0deg) translateX(0);
        opacity: 0;
    }
}
@keyframes pl3 {
    from {
        top: 60px;
        opacity: 1;
    }
    15% {
        transform: rotate(20deg) translateX(-10px);
    }
    50% {
        transform: rotate(0deg) translateX(0);
    }
    75% {
        transform: rotate(-20deg) translateX(10px);
    }
    90% {
        top: 220px;
        transform: rotate(0deg) translateX(0);
    }
    to {
        top: 225px;
        transform: rotate(0deg) translateX(0);
        opacity: 0;
    }
}
</style>


