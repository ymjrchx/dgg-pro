<template>
    <div>
        <div class="userCenter">
            <div class="imgBox">
                  <img src="" :onerror='errorImg' alt="头像">
            </div>
            <div class="userInfo" v-if='userInfo'> 
                <p class="col-b">{{$store.state.userInfo.username}} <i class="el-icon-edit ml10 col-h2 f14 cur" title="修改名称" @click="editName"></i></p>
                <p><label>手机：{{userInfo.username}}</label> <label>性别：{{userInfo.sex ? userInfo.sex : '未知'}}</label> 
                    <label>注册时间：{{userInfo.createtime}}</label>
                </p>
                <p>
                    <a href="javascript:void(0)" class="col" @click="showUpdate = true ">修改密码</a>
                    <a href="javascript:void(0)" class="col" @click="showBing('改绑工号')" v-if='$store.state.userInfo.isBind'>改绑工号</a>
                    <a href="javascript:void(0)" class="col" @click="showBing('绑定工号')" v-else>绑定工号</a>
                </p>
            </div>
             <el-button v-if='0' icon="el-icon-edit-outline">编辑个人信息</el-button>
        </div>
         <bindingMsg v-if='userInfo' :btnStr='btnStr' :userInfo = 'userInfo'></bindingMsg>
         <updatePassword v-if='showUpdate' @updateShow='updateShow'></updatePassword>
    </div>
</template>
<script>
import img from '@/assets/images/c93fd6c7a8ca3ddcfdfeef24d860ce05.png'
import bindingMsg from '@/components/bindingMsg'
import Cookies from "js-cookie"
import updatePassword from '@/components/updatePassword'

export default {
     components: {
       bindingMsg,updatePassword
    },
    data(){
        return{
            errorImg:`this.src='${img}'`,
            showUpdate:false,
            userInfo:"",
            btnStr:"绑定工号"
        }
    },
    created(){
        this.getUserINfo()
    },
    methods:{
        updateShow(){
            this.showUpdate=false
        },
        showBing(str){
            this.btnStr = str
            this.$store.commit('SET_BINGING',true)
        },
        getUserINfo(){
            this.$Api('sysUser/getUser',{},'get',1).then(res=>{
                // console.log(21222,res)
                this.userInfo = res.data
            })
        },
        editName(){
             this.$prompt('请输入您的新昵称', '修改', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputPattern: /^\S{1,50}$/,
                    inputErrorMessage: '请输入新名称'
                }).then(({ value }) => {
                    this.$Api('sysUser/changeLegenPerson',{name:value},'get').then(res =>{
                        let obj =JSON.parse(Cookies.get('userInfo'));
                            obj.username = value 
                            Cookies.set('userInfo',obj);
                            this.$store.commit("SET_USER",obj)
                    })
                }).catch(() => {
            });
        }
    }
}
</script>

<style lang="stylus" scoped>
    .userCenter{
        position: relative;
        padding-bottom: 52px;
        padding-top: 52px;
        padding-left: 148px;
        background: #fff;
        width 100%;
        .imgBox{
            vertical-align: middle;
            display: inline-block;
            margin-right: 40px;
            position relative;
            width:84px;
            height:84px;
            img{
                max-width 100%;
                border-radius 50%;
            }
        }
        .userInfo{
            vertical-align: middle;
            display: inline-block;
            position: relative;
            margin-right: 40px;
            width: calc(100% - 400px); 
            p{
                line-height 26px;
                >label{
                    margin-right 40px;
                }
                >a{
                     margin-right 40px;
                }
            }
        }
        >button{
            position: absolute;
            top: 30px;
            right: 130px; 
            
        }
    }
</style>