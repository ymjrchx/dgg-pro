<template>
    <transition name="fadeIn">
        <div class="ntlk-main masking">
            <div class="ntalk-wrap">
                <div class="layer-title">
                        首次登录提醒
                </div>
                <div class="layui-layer-content">
                    <h2> <i class="el-icon-warning"></i> 为了保障账号安全，首次登录需要修改密码</h2>
                    <div id="consultationModal" class="consultationModal formModal">
                        <ul style="mrigin-top:24px">
                            <li>
                                <label>请输入新密码：</label>
                                <div class="iptBox" style="width:360px">
                                    <el-input size='medium' maxlength='50' type='password'
                                        placeholder="由6~18位数字字母组成"
                                        v-model="newPass"
                                        clearable>
                                    </el-input>
                                </div>
                            </li>
                            <li>
                                <label>请确认新密码：</label>
                                <div class="iptBox" style="width:360px">
                                    <el-input size='medium' maxlength='50' type='password' @keydown.enter.native="submit"
                                        placeholder="由6~18位数字字母组成"
                                        v-model="newPass2"
                                        clearable>
                                    </el-input>
                                </div>
                            </li>
                        </ul>
                        <div class="text-right">
                            <el-button type="primary" @click="submit" size='medium'>确定</el-button>
                            <!-- <a href="javascript:void(0)" @click="submit" class="button submit">确定</a> -->
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
            if (!this.newPass || !this.newPass2 ) {
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

            this.$Api("sysUser/setPwd",{'pwd':this.newPass2}).then(res=>{
                this.$message({
                    type: 'success',
                    message: '修改成功'
                });
                this.close()
                Cookies.remove('isFirstLogin')
                    
            })
           
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
    margin-left: -260px;
    transform: translateY(-50%);
    width: 520px;
    padding-bottom: 40px;
    background: #fff;
    box-shadow: 0 0 5px 1px rgba(9, 9, 10, 0.1);
    border-radius: 6px;
    z-index: 120;
}
.layer-title {
    height: 60px;
    line-height: 60px;
    border-bottom: 1px solid #eaeaea;
    font-size: 16px;
    padding: 0 26px;
    overflow: hidden;
    background-color: #fff;
    border-radius: 6px 6px 0 0;
}


.layui-layer-content {
    position: relative;
    padding:30px 30px 0;
    
}
.layui-layer-content h2{
    font-size: 14px;
    background: #FFFBE6;
    border: 1px solid #FFE58F;
    border-radius: 2px;
    color: #888;
    padding: 0 20px;
    height: 38px;
    line-height: 38px;
    margin-bottom: 36px;
}
.layui-layer-content h2 i{
    color: #FAAD14;
}

/* //表单 */
.formModal {
    /* padding: 0 20px; */
}
.formModal > ul > li {
    margin-bottom: 30px;
}
.formModal ul li label {
    display: inline-block;
    width: 100px;
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
