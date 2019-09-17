<template>
  <div class="home">
    <div class="yke">
      <div class="yke_main">
        <router-link to="/main"  class="_2IwVm" >
          <img src="../assets/images/logo1.png" alt="个人中心" width="260px" >
        </router-link>
        <h2>账号登录</h2>
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm"  class="demo-ruleForm">
          <input type="text" style="display:none"/>
          <input type="password" style="display:none"/>
          <el-form-item prop="phone" class="jun">
            <el-input type="text" v-model="ruleForm.phone" placeholder="请输入您的手机号码" prefix-icon="el-icon-mobile-phone" maxlength="11" clearable></el-input>
          </el-form-item>
          <el-form-item  prop="pass">
            <el-input type="password" v-model="ruleForm.pass" placeholder="请输入您的密码"  @keyup.enter.native="submitForm('ruleForm')" prefix-icon="el-icon-edit" clearable></el-input>
          </el-form-item>
          <el-form-item class="item2"> 
            <el-checkbox v-model="checkedNext">下次自动登录</el-checkbox>
            <!-- <a href="javascript:void(0)" @click='forgetPass' class="forgetPass col">忘记密码?</a> -->
             <router-link to="/passport/register"  class="forgetPass col" >注册新账号</router-link>
          </el-form-item>
          <el-form-item class="item2">
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          </el-form-item>
           <el-form-item class="item2" prop="agreement"> 
            <!-- <el-checkbox v-model="agreement">我已阅读并接受<router-link to="/users/agreement" class="col" target="_blank">《顶企客服务协议》</router-link></el-checkbox> -->
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>
// @ is an alias to /src
import Cookies from "js-cookie"

export default {
  name: "home",
  components: {},
  data(){
    var validatePhone= (rule, value, callback)=>{
        if(this.errorMsg){
          return callback(new Error(this.errorMsg));
        }
        if (!value) {
          return callback(new Error('请输入电话号码'));
        }
        setTimeout(() => {
          if (!/^1(3|4|5|7|8|9)\d{9}$/.test(value)) {
               return callback(new Error("请填写正确的电话号码"));
          }else{
             return callback();
          }
        }, 100);
    };
     var validatePass= (rule, value, callback)=>{
        if(this.errorMsg){
          return callback(new Error(this.errorMsg));
        }
        if (!value) {
          return callback(new Error('请输入密码'));
        }
        else{
          return callback();
        }
    };
    var validateRead =(rule, value, callback)=>{
        if (!this.agreement) {
          return callback(new Error('请先阅读并接受《顶企客服务协议》'));
        }
        else{
          return callback();
        }
    }
    return{
        ruleForm: {
          phone: '',
          pass: ''
        },
        checkedNext:false,
        agreement:true,
        rules:{
         phone:[{ validator: validatePhone, trigger: 'blur' }],
         pass:[{ validator: validatePass, trigger: 'blur' }],
         agreement:[{ validator: validateRead, trigger: 'click'}],
         errorMsg:""
       } 
    }
  },
  created(){
        // Cookies.remove("userId")
        // Cookies.remove("userInfo")
  },
 
   methods:{
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (!valid) {
             return false;
          } 
          this.$Api('sysUser/login',
            {"phoneNo":this.ruleForm.phone,"loginPwd":this.ruleForm.pass,"time": this.checkedNext ? 7 : 0},'post',true,false,this.requireFunc)
            .then(res=>{
              let json = res.data
              if(res.code !=0) return

              if(json.isFirstLogin == 1){
                  Cookies.set("isFirstLogin",true,{ expires: 7 })
              }
              let userInfo = {
                  username:json.isBind ? json.isBind : json.username ,
                  userId:json.loginToken,
                  valueId:json.userId,
                  isBind:json.isBind
              } 
              this.$store.commit("SET_USER",userInfo)
              if(this.checkedNext ){
                Cookies.set('userInfo',userInfo, { expires: 7 })
              }
              else{
                Cookies.set("userInfo",userInfo)
              }
             
              this.$notify.success({
                title: '提示',
                message: '登录成功',
                duration:1000,
                onClose:()=>{
                  this.$router.replace('/main')
                }
              });

            }).catch(()=>{})
        });
      },
      requireFunc(data){
          this.errorMsg = data.msg
        if(data.msg.indexOf('注册') >= 0 ){
          this.$refs['ruleForm'].validateField("phone");
        }
        else{
          this.$refs['ruleForm'].validateField("pass");
        }
         this.errorMsg = ''
      },
      forgetPass(){
          this.$notify.info({
              title: '温馨提示',
              message: '暂未开通，请联系管理员'
          });
      }
  }
};
</script>
<style lang="stylus" scoped>
.home {
    position: fixed;
    width: 100%;
    height: 100%;
    left: 0;
    top: 0;
    background: #72BCE1 url('../assets/images/main.jpg') center center no-repeat;
    background-size: cover;
}
.yke{
    padding: 0 140px;
    max-width: 640px;
    position: relative;
    margin: 0 0 0 auto;
    height: 100%;
    background: hsla(0, 0%, 100%, .97);
}
@media screen and (max-width:1200px) {
  .yke {
    padding: 0 60px;
    max-width: 480px;
  }
}
.yke_main{
    height: 100%;
    margin: auto;
    padding-top: 34%;
    text-align center;

    > h2 {
      margin-top: 30px;
      margin-bottom: 62px;
      text-align: center;
      font-size 20px
    }
    .item2{
      margin-bottom: 10px;
      text-align left 

      .forgetPass{
        float right 
      }

      button{
        width 100%
      }
    }
}
.jun{
  margin-bottom 30px
}

</style>

