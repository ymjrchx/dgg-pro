<template>
    <div class="reg">
        <header >
            <div class="tungee-head-bar">
                <div class="logo inline">
                    <a href="/" class="logo-link">
                        <img src="../../assets/images/logo2.png" alt="个人中心" width="150px" >
                    </a>
                    <router-link to='/' class="col fr f16">返回登录</router-link>
                </div>
               
            </div>
        </header>
        <div class="content">
            <p class="title">注册顶企客，让销售更智能</p>
            <div class="nav">
              <div class="nav-item" :class="{'navItemActive':navStep >= 0 }"><a href="javascript:void(0)">1、填写手机号</a></div>
              <div class="nav-item" :class="{'navItemActive':navStep >= 1 }"><a href="javascript:void(0)">2、填写密码</a></div>
              <div class="nav-item" :class="{'navItemActive':navStep == 2 }"><a href="javascript:void(0)">3、注册成功</a></div>
            </div>
            <div class="step stepOne" v-if='navStep == 0'>
                <div class="stepItem">
                    <input type="text" maxlength="11" v-model="userPhone" placeholder="请输入您的手机号" ref='userPhone'>
                </div>
                <div class="stepItem text-left">
                    <input type="text" maxlength="8" v-model="imgCode" class="imgCode" placeholder="请输入图片验证码" ref='imgCode'>
                    <img :src="baseImg" alt="点击替换" title="点击替换"  class="codeImg" @click="switchImg">
                </div>
                <div class="stepItem text-left">
                    <input type="text" maxlength="8" v-model="msgCode" class="msgCode" placeholder="短信验证码" ref='msgCode' @keydown.native="netxStep(1)">
                    <input type="button" v-model="btnStr" class="msgCode fr btn"  @click="getCode" :class="{'unUse':isClick}">
                </div>
                <!-- <div class="stepItem text-left">
                     <el-checkbox v-model="agreement">我已阅读并接受<router-link to="/users/agreement" class="col" target="_blank">《顶企客服务协议》</router-link></el-checkbox>
                </div> -->
                 <div class="stepItem">
                    <button @click="netxStep(1)" class="btn">下一步</button>
                </div>
            </div>
            <div class="step stepOne"  v-if='navStep == 1'>
                <!-- <div class="stepItem">
                    <input type="text" maxlength="50" v-model="companyName" placeholder="请输入企业全称（必填）" ref='companyName'>
                </div>
                <div class="stepItem">
                    <input type="text" maxlength="50" v-model="contactName" placeholder="请输入联系人名称，请填写身份证上面的姓名（必填）" ref='contactName'>
                </div> -->
                <div class="stepItem">
                    <input type="text" maxlength="50" v-model="emil" placeholder="请输入邮箱地址（选填）" ref='emil'>
                </div>
                <div class="stepItem">
                    <input :type=" passSee ? 'text' : 'password' " maxlength="18" v-model="pass" placeholder="请输入由6-18位数字或字母组成的密码" ref='companyName'>
                    <span :class="{'eyes':true,open:passSee}" @click=" passSee = !passSee"></span>
                </div>
                <div class="stepItem">
                    <input :type=" reSee ? 'text' : 'password' "  maxlength="18" v-model="rePass" placeholder="请重复由6-18位数字或字母组成的密码" ref='contactName'  @keydown.native="netxStep(2)">
                    <span :class="{'eyes':true,open:reSee}"  @click=" reSee = !reSee"></span>
                </div>
                <!-- <div class="stepItem">
                    <input type="text" maxlength="30" v-model="managerCode" placeholder="客户经理代码（如有，如001）" ref='managerCode'>
                </div> -->
                <div class="stepItem">
                    <button @click="netxStep(2)" class="btn">下一步</button>
                </div>
            </div>
             <div class="step lastStep"  v-if='navStep == 2'>
                <div class="cssImg">
                    <div class="gou"></div>
                </div>
                <div class="stepItem">
                    <p> <span class="action">{{timer}}</span> 秒后自动跳转登录界面</p>
                    <button class="btn" @click="goHome">马上登录</button>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
// import img from '@/assets/images/code.jpg'

export default {
    data(){
        return{
            navStep:0,
            // errorImg:`this.src='${img}'`,
            userPhone:"",   //电话
            imgCode:"",     //图片验证
            msgCode:"",     //短信验证
            btnStr:"获取验证码",
            agreement:true,
            companyName:"", 
            contactName:"",
            emil:"",
            managerCode:"",
            timer:3,
            baseImg:"",
            isClick: false, //短信btn是否可以点击
            setTimer:null,
            pass:"",
            rePass:"",
            passSee:false,
            reSee:false
        }
    },
    created(){
       this.switchImg()
       
    },
    mounted(){
        let minTime = 10*60;
            this.setTimer = setInterval(()=>{
            if(minTime == 0){
                 this.$confirm('注册超时，请重新填写信息', '温馨提示', {
                    confirmButtonText: '确定',
                    showClose:false,
                    closeOnClickModal:false,
                    closeOnPressEscape:false,
                    showCancelButton:false,
                    type: 'warning'
                  }).then(() => {
                     window.location.reload()
                  }).catch(() => {
                 
                  });
                clearInterval(this.setTimer)
            }
            else{
                minTime -= 1
            }   
            
        },1000)
    },
    methods:{
        async netxStep(step){
            if(step == 1){
                if (!/^1(3|4|5|7|8|9)\d{9}$/.test(this.userPhone)) {
                    this.$message({
                        showClose: true,
                        message: '手机号码格式错误，请重新输入',
                        type: 'warning'
                    });
                     this.$refs.userPhone.focus();
                     return
                }
                
                if(!this.imgCode || isNaN(Number(this.imgCode)) ){
                    this.$message({
                        showClose: true,
                        message: '请输入正确的图片验证码',
                        type: 'warning'
                    });
                     this.$refs.imgCode.focus();
                     return
                }
                if(!this.msgCode ||  isNaN(Number(this.msgCode))){
                    this.$message({
                        showClose: true,
                        message: '请输入正确的短信验证码',
                        type: 'warning'
                    });
                     this.$refs.msgCode.focus();
                     return
                }
                if(!this.agreement){
                    this.$message({
                        showClose: true,
                        message: '请先阅读并接受《顶企客服务协议》',
                        type: 'warning',
                        time:1500
                    });
                    return
                }

                //验证图片验证码
                try{
                    let vailImgCode = await  this.$Api("imgCode/check",{'code': this.imgCode,})
              
                    if(vailImgCode.code != 0) return;

                    //验证短信验证码
                    let msgCode = await  this.$Api("smsCode/check",{'code': this.msgCode,'phone':this.userPhone})

                    if(msgCode.code != 0) return;
            
                    this.navStep = step;
                }
                catch(e){
                    this.switchImg()
                }
            }
        
            if(step ==2){
            
                if (!this.pass || !this.rePass || !this.emil) {
                    this.$message.warning('请先完善密码信息！');
                    return 
                }
                if( this.pass != this.rePass ){
                    this.$message.error('两次密码不一致');
                    return 
                }

                let passBool = /^[0-9a-zA-Z]{6,18}$/.test(this.pass);
              

                if(this.pass.length < 6 || this.pass.length >18){
                    this.$message.warning('密码长度应为6-18位');
                    return;
                }    
                if (
                    !passBool ||
                    /^[0-9]{6,18}$/.test(this.pass) ||
                    /^[a-zA-Z]{6,18}$/.test(this.pass)
                ) {
                    let str = this.pass.replace(/\w/g, "");
                    if (str == "") {
                        this.$message.warning('不可使用纯数字或字母作为密码');
                    } else {
                        this.$message.warning("提示：密码中包含特殊字符" + str);
                    }
                    return;
                }       
                var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
                if( this.emil &&!reg.test(this.emil)){
                    this.$message({
                        showClose: true,
                        message: '请输入正确的邮箱地址',
                        type: 'warning'
                    });
                     this.$refs.emil.focus();
                     return
                }
               this.goReg()
            }
            
        },
        goReg(){
            let userJson = JSON.stringify(
                {
                    phoneNo:this.userPhone, 
                    smsVerifyCode:this.msgCode,
                    imageVerifyCode:this.imgCode,
                    loginPwd:this.pass,
                    email:this.emil || '123456',
                }
            )
            // let companyJson = JSON.stringify(
            //     {
            //         companyName:this.companyName,
            //         contactName:this.contactName,
            //         contactEmail:this.emil,
            //         customerManagerCode:this.managerCode
            //     }
            // )

            this.$Api('sysUser/regist',{userJson}  ,'post',true,true)
                .then(res=>{
                    this.$message({
                        message: '注册成功',
                        type: 'success'
                    });
                    this.navStep = 2
                    let setTime=setInterval(()=>{
                        this.timer-=1
                        if(this.timer == 0){
                            this.$router.replace('/')
                        }
                    },1000)
            }).catch(res=>{
                console.log(123,res)
            })
        },
        goHome(){
              this.$router.replace('/')
        },
        switchImg(){
            this.$Api('imgCode/send',{})
                .then(res=>{
                this.baseImg ="data:image/jpeg;base64," + res.data.verifycode
            })
        },
        async getCode() {
            if (!/^1(3|4|5|7|8|9)\d{9}$/.test(this.userPhone)) {
               this.$message({
                    message: '手机号码格式错误，请重新输入',
                    type: 'warning'
                });
                this.$refs.userPhone.focus();
                return;
            }
            if(!this.imgCode || isNaN(Number(this.imgCode)) ){
                this.$message({
                    showClose: true,
                    message: '请输入正确的图片验证码',
                    type: 'warning'
                });
                this.$refs.imgCode.focus();
                return
            }
            if (this.isClick) {
                return;
            }

            //验证图片验证码
            let vailImgCode = await  this.$Api("imgCode/check",{'code': this.imgCode,})
    
            if(vailImgCode.code != 0) return;

            let min = 60;
            this.$Api(
                "smsCode/send",
                {'phoneNo': this.userPhone,"type":"regist"},
                "post",
                true
            ).then(res => {
                let json = res.data;
                    this.$message({
                        message: '短信发送成功，请注意查收',
                        type: 'success'
                    });
                    this.isClick = true;
                    let timer = setInterval(() => {
                        if (min == 1) {
                            this.isClick = false;
                            clearInterval(timer);
                            this.btnStr = "重新获取";
                        } else {
                            min--;
                            if (min < 10) min = "0" + min;
                            this.btnStr = "重新获取( " + min + " )";
                        }
                    }, 1000);
                
            });
        },
    },
    destroyed(){
        clearInterval(this.setTimer)
    }
}
</script>

<style lang="stylus" scoped>
    header{
        width 100%;
        height 64px;
        background: #1d2327;
        line-height 64px;
        padding 0 64px;
    }
    .reg{
        position fixed;
        width 100%;
        height 100%;
        background url('../../assets/images/register.jpg');
        background-size cover
    }
    .content{
        width 600px;
        background #fff;
        border-radius: 3px;
        text-align center;
        margin auto;
        margin-top 7%
        .title{
            font-size: 30px;
            color: #333;
           padding 40px 0 30px 0;
        }
        .nav{
            display: inline-block;
            width: 570px;

            .nav-item {
                position: relative;
                width: 32.5%;
                height: 40px;
                line-height: 40px;
                display: inline-block;
                font-size: 16px;
                color: #5a5a5a;
                text-align: center;
                background-color: #eef6fc;
                cursor: pointer;

                &.navItemActive{
                    background-color: #3da4ff;
                    a{
                        color #fff;
                    } 
                }

                a{
                    color #5a5a5a
                    display: inline-block;
                    width: 100%;
                    text-decoration: none;
                }

                &:not(:last-child)::after {
                    content:' ';
                    position: absolute;
                    top: 0;
                    right: -19px;
                    z-index: 1;
                    width: 40px;
                    height: 40px;
                    background: url(../../assets/images/icon-arrow-white.png) no-repeat center center;
                    background-size: contain;
                }
            }
        }
        .step{
            width: 422px;
            margin: auto;
            padding 24px 0;

            input{
                height 44px;
                width: 100%;
                padding: .8225em;
                font-size: 14px;
                border: 1px solid #ddd;
                outline: 0;
            }
            .stepItem{
                padding 16px 0;
                position: relative;

                .eyes{
                    position absolute;
                    background url('../../assets/images/pass_close.png') center center no-repeat;
                    width: 24px;
                    height: 20px;
                    right: 24px;
                    top: 28px;
                    cursor pointer;
                    transition all .2s;
                    &.open{
                        background url('../../assets/images/pass_open.png') center center no-repeat; 
                    }
                }
                .imgCode{
                    width: 65%;
                    display: inline-block;
                }
                .codeImg{
                    width 30%;
                    float right;
                    height 42px;
                    margin-top 1px;
                    cursor pointer
                }
                .msgCode{
                    width:46%;
                    &.unUse{
                        background #777;
                    }
                }
                button{
                    width 100%;
                    height 44px;
                }
            }  
        }
        .lastStep{
            padding 80px 0;

            .cssImg{
                width: 79px;
                height: 79px;
                border-radius: 50%;
                background:#6cce51;
                position: relative;
                margin: 0 auto;
                .gou{
                    width: 50px;
                    height: 25px;
                    border-bottom: 5px solid #fff;
                    border-left: 5px solid #fff;
                    -webkit-transform: rotate(-45deg);
                    transform: rotate(-45deg);
                    position absolute;
                    top:30%;
                    left 20%;
                }
            }
            .stepItem{
                button{
                    width 200px;
                    margin-top 20px;
                }
                p{
                    margin-top 20px;
                    
                    span{
                        color #3da4ff;
                        font-size:22px;
                    }
                }
            }
          
        }
    }
</style>
