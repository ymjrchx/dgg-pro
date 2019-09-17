<template>
    <transition name="fadeIn">
        <div class="ntlk-main masking">
            <div class="ntalk-wrap">
                <div class="layer-title">{{btnStr}}
                    <i class="el-icon-close" @click="close"></i>
                </div>
                <div id="" class="layui-layer-content">
                    <div id="consultationModal" class="consultationModal formModal">

                        <ul style="mrigin-top:24px">
                            <li style="margin-bottom:20px"> 
                                <label>原密码：</label>
                                <!-- <input type="text" class="input" maxlength="50" v-model="numID"> -->
                                <div class="iptBox" style="width:240px">
                                    <el-input size='medium' maxlength='50' type='password'
                                        placeholder="请输入..."
                                        v-model="pass"
                                        clearable>
                                    </el-input>
                                </div>
                              
                            </li>
                            <li>
                                <label>新密码：</label>
                                <div class="iptBox" style="width:240px">
                                    <el-input size='medium' maxlength='50' type='password'
                                        placeholder="由6~18位数字字母组成"
                                        v-model="newPass"
                                        clearable>
                                    </el-input>
                                </div>
                            </li>
                            <li>
                                <label>重复新密码：</label>
                                <div class="iptBox" style="width:240px">
                                    <el-input size='medium' maxlength='50' type='password' @keydown.enter.native="submit"
                                        placeholder="由6~18位数字字母组成"
                                        v-model="newPass2"
                                        clearable>
                                    </el-input>
                                </div>
                            </li>
                        </ul>
                        <div style='padding-left:60px;'>
                            <a href="javascript:void(0)" @click="submit" class="button submit">提交</a>
                        </div>
                    </div>
                </div>
                <span class="layui-layer-setwin" @click="close">
                </span>
            </div>
        </div>
    </transition>
</template>
<script>
import Cookies from "js-cookie"
export default {
    props: {
        name: {
            default: ""
        },
        type: {
            default: ""
        },
        code: {
            default: ""
        },
        btnStr: {
            default: "修改密码"
        }
    },
    data() {
        return {
            pass: "", //原密码
            newPass: "", //pass
            newPass2: "", //pass
        };
    },
    mounted() {
    },
    methods: {
        close() {
            document.getElementsByTagName("body")[0].style.cssText =
                "overflow:auto";
            this.$emit('updateShow')    
        },
        submit() {
            if (!this.pass || !this.newPass || !this.newPass2 ) {
                this.$message.warning('请先完善修改信息！');
                return 
            }
            if( this.newPass != this.newPass2 ){
                this.$message.error('两次密码不一致');
                return 
            }

            let passBool = /^[1-9a-zA-Z]{6,18}$/.test(this.newPass);
            if(this.newPass.length < 6 ||this.newPass.length >18){
                this.$message.warning('密码长度应为6-18位');
                return;
            }    
            if (
                !passBool ||
                /^[1-9]{6,18}$/.test(this.newPass) ||
                /^[a-zA-Z]{6,18}$/.test(this.newPass)
            ) {
                let str = this.newPass.replace(/\w/g, "");
                if (str == "") {
                    this.$message.warning('不可使用纯数字或字母作为密码');
                } else {
                    this.$message.warning("提示：密码中包含特殊字符" + str);
                }
                return;
            }       

            let requireObj = {
                oldPwd: this.pass,
                newPwd: this.newPass2,
            };
            this.$Api("sysUser/changePwd", requireObj, "get",1).then(res => {
                this.$message.success('修改成功');
              
                Cookies.remove("userInfo")
                setTimeout(()=>{
                     this.$router.replace('/')
                },1000)
                this.close();
               
            });
        }
    }
};
</script>
<style scoped>
.masking {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.3);
    z-index: 110;
    line-height: 1em;
}
.ntalk-wrap {
    position: fixed;
    left: 50%;
    top: 50%;
    margin-left: -190px;
    transform: translateY(-50%);
    width: 380px;
    padding-bottom: 40px;
    background: #fff;
    box-shadow: 0 0 5px 1px rgba(9, 9, 10, 0.1);
    border-radius: 4px;
    z-index: 120;
}
.layer-title {
    padding-top: 13px;
    height: 52px;
    line-height: 42px;
    border-bottom: none;
    font-size: 20px;
    color: #08f;
    text-align: center;
    overflow: hidden;
    background-color: #fff;
    border-radius: 2px 2px 0 0;
    margin-bottom: 30px;
    position: relative;
}
.layer-title i{
    position: absolute;
    right: 6px;
    cursor: pointer;
    top: 6px;
    font-size: 24px;
}

.layui-layer-content {
    position: relative;
}
.layui-layer-setwin a {
    position: relative;
    width: 16px;
    height: 16px;
    margin-left: 10px;
    font-size: 12px;
    overflow: hidden;
}
.layui-layer-setwin {
    position: absolute;
    right: 15px;
    top: 15px;
    font-size: 0;
    line-height: initial;
    width: 23px;
    height: 23px;
    /* background: url("../assets/images/main/close.png") center no-repeat; */
    cursor: pointer;
}
/* //表单 */
.formModal {
    padding: 0 20px;
}
.formModal > ul > li {
    margin-bottom: 30px;
}
.formModal ul li label {
    display: inline-block;
    width: 90px;
    text-align: right;
    vertical-align: middle;
}
.formModal ul li .iptBox{
    width: 240px;
    display: inline-block;
}
.formModal ul li .tip {
    display: block;
    margin-left: 60px;
    text-align: center;
    margin-top: 10px;
    color: #e6a23c;
    width: 240px;
}
.formModal ul li .input {
    display: inline-block;
    padding: 5px;
    border: solid 1px #e3e3e3;
    width: 240px;
    border-radius: 1px;
    color: #333;
    vertical-align: middle;
    height: 36px;;
}
.formModal ul li textarea.input {
    vertical-align: top;
    height: 100px;
    resize: none;
}
.formModal ul li .star {
    margin-right: 5px;
    color: #ff0000;
}
.formModal .submit {
    display: block;
    margin-top: 60px;
    width: 240px;
    font-size: 16px;
    line-height: 2.4em;
    color: #fff;
    text-align: center;
    border-radius: 2px;
    background-color: #08f;
    letter-spacing: 10px;
}
</style>
