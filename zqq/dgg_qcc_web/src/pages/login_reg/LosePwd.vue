<template>
  <div class="login-panel">
    <div class="login-panel-head clearfix">
      <div class="login-tab col3">
        <a class="active">找回密码</a>
      </div>
    </div>
    <div class="login-panel-body login">
      <div class="form-group">
        <el-input placeholder="请输入注册时的电话号码" v-model="nameVal" clearable maxlength="11">
          <i slot="prefix" class="backImg userTel"></i>
        </el-input>
      </div>
      <div class="form-group codeBox">
        <div class="col-md-8">
          <el-input placeholder="请输入验证码" v-model="codeVal" clearable>
            <i slot="prefix" class="backImg userCode"></i>
          </el-input>
        </div>
        <div class="col-md-4 codeImg">
          <div class="codeStr btn btn-primary btn-block login-btn" @click="getCode" :disabled="isClick">{{codeStr}}</div>
        </div>
      </div>
      <div class="form-group m-t-md">
        <el-input placeholder="请输入新密码" v-model="passVal2" clearable type="password">
          <i slot="prefix" class="backImg userLock"></i>
        </el-input>
      </div>
      <div class="form-group m-t-md">
        <div id="dom_id_one"></div>
      </div>
      <div class="checkbox m-t-md">
        <label class="text-dark-lter">
          <input type="checkbox" name="keep" :checked="checked" value="option1">我已经仔细阅读并接受
        </label>
        <router-link to='/Agreement'>《顶企查用户协议》</router-link>
      </div>
      <div class="login-other m-t-md">
        <div class="clearfix form-group m-t-md">
          <div class="pull-left fl">
            <!-- <a href="/Register" class="text-primary"></a> -->
            <router-link to='/Register'>立即注册</router-link>
          </div>
          <div class="text-dark-lt m-l-sm fr">
            <!-- <a href="/FindBack">    忘记密码？</a> -->
            <router-link to='/Login'>返回登录</router-link>
          </div>
        </div>
      </div>
      <button type="submit" class="btn btn-primary btn-block m-t-md login-btn" @click="submit">
        <strong>立即找回</strong>
      </button>
    </div>
  </div>
</template>
<script>
export default {
    name: "",
    components: {},
    data() {
        return {
            isTab: 0,
            checked: true,
            nameVal: "",
            codeVal: "",
            passVal2: "",
            isClick: false,
            codeStr: "获取验证码"
        };
    },
    methods: {
        getCode() {
            if (!/^1(3|4|5|7|8|9)\d{9}$/.test(this.nameVal)) {
                this.$message({
                    message: "请填写正确的电话号码",
                    type: "error",
                    duration: 1000
                });
                return;
            }
            let min = 60;
            this.$axios({
                type:"post",
                url:"sms/sentidentifycode",
                data:{ phoneNo: this.nameVal, type: "fogetpwd" },
                success:res => {
                    let json = res.data
              
                    if (json.code == 0) {
                        this.$message({
                            message: "短信已发送，请注意查收",
                            type: "success",
                            duration: 1000
                        });
                        this.isClick = true;
                        let timer = setInterval(() => {
                            if (min == 1) {
                                this.isClick = false;
                                clearInterval(timer);
                                this.codeStr = "重新获取";
                            } else {
                                min--;
                                if(min<10) min="0"+min
                                this.codeStr = "重新获取( " + min + " )";
                            }
                        }, 1000);
                    } else {
                        this.$message({
                            message: json.msg,
                            type: "error",
                            duration: 2000
                        });
                    }
                }
            });
        },
        submit() {
            if (!/^1(3|4|5|7|8|9)\d{9}$/.test(this.nameVal)) {
                this.$message({
                    message: "请填写正确的电话号码",
                    type: "error",
                    duration: 1000
                });
                return;
            }
            if ((this.codeVal == "") | !this.codeVal) {
                this.$message({
                    message: "请输入验证码",
                    type: "error",
                    duration: 1000
                });
                return;
            }
            if ((this.passVal2 == "") | !this.passVal2) {
                this.$message({
                    message: "请输入密码",
                    type: "error",
                    duration: 1000
                });
                return;
            }
           
            let obj={
                    "phoneNo": this.nameVal,
                    "smsVerifyCode": this.codeVal,
                    "newLoginPwd": this.passVal2
                }
                
            this.$axios({
                type:"post",
                url:"registuser/forgetpwd",
                data:obj,
                success:res => {
                   
                    let data = res.data
                   
                    if (data.code == 0) {
                        this.$message({
                            message: "找回成功",
                            type: "success",
                            duration: 2000,
                            onClose: () => {
                                this.$router.push("/Login");
                            }
                        });
                    } else {
                        this.$message({
                            message: data.msg,
                            type: "error",
                            duration: 2000
                        });
                    }
                }
            },1);
        }
    }
};
</script>
<style scoped>
.login-tab.col3 {
    width: 100%;
}
.login-tab > a.active {
    border: none;
}
.codeStr {
    width: 100%;
    height: 100%;
    text-align: center;
    font-size: 14px;
    border-radius: 0px 4px 4px 0px;
    -webkit-border-radius: 0px 4px 4px 0px;
    -moz-border-radius: 0px 4px 4px 0px;
    -ms-border-radius: 0px 4px 4px 0px;
    -o-border-radius: 0px 4px 4px 0px;
    cursor: pointer;
}
</style>