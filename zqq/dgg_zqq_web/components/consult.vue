<template>
    <transition name="fadeIn">
        <div class="ntlk-main" v-if="isShow">
            <p class="masking"></p>
            <div class="ntalk-wrap">
                <div class="ntalk-box">
                    <p class="close">
                        <a @click="close" href="javascript:;"></a>
                    </p>
                    <b>解决方案咨询</b>
                    <p class="ntalk-tx">提交申请后，专利大师将会和您取得联系，进行问题解答</p>
                    <form class="ntalk-form" action="">
                        <p>
                            <label for="userName">您的姓名</label>
                            <input id="userName" type="text" placeholder="请输入您的姓名">
                        </p>
                        <p>
                            <label for="userTel">联系方式</label>
                            <input id="userTel" type="text" placeholder="请输入您的联系方式">
                        </p>
                        <p>
                            <label for="userQue">咨询问题</label>
                            <textarea id="userQue" maxlength="108" placeholder="请输入您要咨询的问题"></textarea>
                        </p>
                        <p class="show-num">
                            <span>0/108</span>
                        </p>
                        <input id="sub-qus" class="btn" type="button" value="提交">
                    </form>
                </div>
            </div>
        </div>
    </transition>
</template>

<script>
export default {
    props: ['isShow', 'closeConsult'],
    created() {},
    mounted() {
    },
    methods: {
        close () {
            this.$emit('closeConsult')
        }
    }
};
</script>
<style scoped>
    .masking{
        position: fixed;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background: rgba(0,0,0,0.3);
        z-index: 999;
    }
    .ntalk-wrap{
        position: fixed;
        left: 50%;
        top: 50%;
        margin-left: -250px;
        margin-top: -200px;
        width: 500px;
        height: 400px;
        background:#fff;
        box-shadow:0 0 5px 1px rgba(9,9,10,0.10);
        border-radius:2px;
        z-index: 1000;
    }
    .ntalk-box{
        width: 100%;
        height: 100%;
        overflow: hidden;
    }
    .ntalk-box > p{
        overflow: hidden;
    }
    .close a{
        float: right;
        margin: 13px 13px 0 0;
        width: 15px;
        height: 15px;
        background: url(../assets/images/close.png) no-repeat center;
        background-size: 15px 15px
    }
    .ntalk-box b{
        display: block;
        text-align: center;
        font-size: 18px;
        font-weight: bold;
    }
    .ntalk-tx{
        position: relative;
        margin: 12px auto 3px;
        padding-bottom: 20px;
        font-size: 14px;
        line-height: 14px;
        text-align: center;
    }
    .ntalk-tx:after{
        content: '';
        position: absolute;
        left: 0;
        bottom: 0;
        width: 100%;
        height: 1px;
        background: #ddd;
        background-size: 100% 1px;
    }
    .ntalk-form p{
        margin-left: 60px;
        margin-top: 16px;
        overflow: hidden;
    }
    .ntalk-form p label{
        float: left;
        width: 80px;
        font-size: 14px;
        line-height: 36px;
    }
    .ntalk-form p input{
        float: left;
        border:1px solid #ddd;
        border-radius:2px;
        width:294px;
        height:34px;
        font-size: 14px;
        text-indent: 1em
    }
    .ntalk-form textarea{
        background:#fff;
        border:1px solid #ddd;
        border-radius:2px;
        width:298px;
        height:78px;
        font-size: 14px;
        line-height: 24px;
        text-indent: 1em
    }
    .btn{
        display: block;
        margin: 27px auto 0;
        background:#ff7200;
        border-radius:2px;
        width:120px;
        height:32px;
        font-size: 16px;
        text-align: center;
        line-height: 32px;
        color: #fff;
        border: none;
        cursor: pointer;
    }
    .show-num{
        float: right;
        margin-top: 6px!important;
        margin-right: 60px;
        font-size: 0
    }
    .show-num span{
        font-size: 12px;
        color: #999
    }
</style>
