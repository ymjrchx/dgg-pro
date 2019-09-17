<template>
 <div class="loginBox" v-if="0">
    <p>亲：请免费注册智企名登陆账号<br>登陆后可以查看更多数据！</p>
    <div>
        <a href="javascript:void(0);" class="loginBtn" @click="login">立即登录</a>
        <a class="register" href="javascript:void(0);" @click="register">立即注册</a>
    </div>
</div>
</template>
<script>
export default {
  methods: {
    login () {
      this.$loginPop(this,'login')
    },
    register () {
      this.$loginPop(this)
    }
  }
}
</script>

<style lang="stylus" scoped>
.loginBox
    position: absolute;
    width: 340px;
    height: 160px;
    left: 50%;
    top: 20px;
    margin-left: -170px;
    text-align: center;
    font-size: 16px;
    border-radius 3px;
    background #fff;
    padding 15px 20px;
    >p{
        text-align center
        line-height 1.8em;
        margin 10px;
    }
    >div{
        overflow hidden
        a{
            display: inline-block;
            padding: 6px 12px;
            margin-bottom: 0;
            font-size: 14px;
            font-weight: normal;
            line-height: 1.42857143;
            text-align: center;
            white-space: nowrap;
            vertical-align: middle;
            cursor: pointer;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
            background-image: none;
            border: 1px solid transparent;
            border-radius: 4px;
            background: #1c8bee;
            color #fff;
            margin 10px;
            transition all .2s
        }
        a.register{
             background #f89d44
        }
    }
</style>
