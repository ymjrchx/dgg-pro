<template>
  <div class="header">
      <img src="../assets/images/logo.png" style="margin:-2px 40px 0 0 " width="26px">
     <span class="col">个人中心</span>
     <span style="margin-left:50px" ><router-link to="/main" >线索平台</router-link></span>
     <ul class="hl">
        <li class="user">
            <span>{{$store.state.userInfo.username}}</span> 
            <img src="" :onerror='errorImg' alt="头像">
            <el-collapse-transition>
                <ul class="selet">
                    <li @click="layOut">退出</li>
                </ul>
            </el-collapse-transition>
        </li>
     </ul>
  </div>
</template>
<script>
import img from '@/assets/images/c93fd6c7a8ca3ddcfdfeef24d860ce05.png'
import Cookies from "js-cookie"
export default {
    data(){
        return{
            errorImg:`this.src='${img}'`,
        }
    },
    created(){
        // if(Cookies.get("userInfo")){
        //     let json = JSON.parse(Cookies.get("userInfo"))
        //     this.$store.commit("SET_USER",json)
        // }
    },
    methods:{
        layOut(){
            localStorage.removeItem('filterJson')
            Cookies.remove("userInfo")
            this.$router.replace('/')
             this.$store.commit('SET_FILTER',{must:[]})
        }
    }
}
</script>

<style lang="stylus" scoped>
.header{
    position: fixed;
    left: 0;
    right: 0;
    z-index: 108;
    height: 60px;
    padding: 0 50px 0 20px;
    line-height: 60px;
    background: #fff;
    -webkit-transition: all .3s cubic-bezier(.215,.61,.355,1);
    transition: all .3s cubic-bezier(.215,.61,.355,1);
    -webkit-box-shadow: 0 2px 7px 0 rgba(50,99,137,.23);
    box-shadow: 0 2px 7px 0 rgba(50,99,137,.23);

    >span{
        font-size: 16px;
        display: inline-block;
        margin-left: 14px;
    }
    >.hl{
        float right;
        margin-right 14px
        >li{
            float left
            padding: 0;
            margin-left: 16px;
            margin-right: 16px;
            cursor pointer;

            &:hover{
                color #08f
            }
            .words{
                margin-left 10px
            }
        }
        .user{
            position relative
            img{
                width: 36px;
                height: 36px;
                margin-left: 15px;
                border-radius: 18px;
                vertical-align: middle;
            }

            &:after{
                font-family: anticon!important;
                font-style: normal;
                vertical-align: baseline;
                text-align: center;
                text-transform: none;
                text-rendering: auto;
                position: absolute;
                -webkit-transition: -webkit-transform .3s;
                transition: -webkit-transform .3s;
                transition: transform .3s;
                transition: transform .3s,-webkit-transform .3s;
                content: "\25BC";
                right: -16px;
                top: 0;
                display: inline-block;
                font-size: 13px;
                -webkit-transform: scale(.66666667) rotate(0deg);
                transform: scale(.66666667) rotate(0deg);
                -ms-filter: "progid:DXImageTransform.Microsoft.matrix(sizingMethod='auto expand', M11=1, M12=0, M21=0, M22=1)";
                zoom: 1;
                -webkit-filter: none;
                filter: none;
            }
            &:hover{
                &:after{
                    transform: scale(.75) rotate(180deg);
                    -webkit-transform:scale(.75) rotate(180deg);
                }
                 .selet{
                     display block
                 }
            }
            .selet{
                display none;
                position absolute;
                right 0;
                top:100%;
                box-shadow: 0 2px 8px rgba(0,0,0,.15);
                background #fff;
                width 120px;
                text-align center;
                padding 4px 0;
                color #5a5a5a
                li{
                    height 34px;
                    line-height 34px;
                    cursor pointer
                    &:hover{
                        color #08f
                    }
                }
            }
        }
           
    }
}
</style>