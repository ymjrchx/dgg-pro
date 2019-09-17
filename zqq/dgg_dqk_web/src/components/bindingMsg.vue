<template>
    <transition name="fadeIn">
        <div class="ntlk-main masking" v-show="$store.state.showBinding">
            <div class="ntalk-wrap">
                <div class="layer-title">{{btnStr}}
                    <i class="el-icon-close" @click="close"></i>
                </div>
                <div id="" class="layui-layer-content">
                    <div id="consultationModal" class="consultationModal formModal">

                        <ul style="mrigin-top:24px">
                            <li style="margin-bottom:20px" :class="{'jun_in':only}"> 
                                <label>工号：</label>
                                <input type="text" class="input" maxlength="50" v-model="numID" :readonly='only' :disabled ='only'>
                                <small class="tip" v-if='!only'>（请确认工号的准确性，保证转线索成功）</small>
                                <small class="tip" v-else>（如工号错误，请联系管理员修改）</small>
                            </li>
                            <li>
                                <label>名称：</label>
                                <input type="text" class="input" maxlength="50" v-model="person">
                            </li>
                            <li>
                                <label>部门：</label>
                                <input type="text" class="input" maxlength="50" v-model="department" @keydown.enter="submit">
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
            default: "绑定工号"
        },
        userInfo:{
            default: ""
        }
    },
    data() {
        return {
            numID: "", //Iboos编号
            person: "", //负责人
            department: "", //负责人部门
            only:false
        };
    },
    created() {
        
    },
    watch:{
        btnStr(str){
            if(str == '改绑工号'){
                this.numID = this.userInfo.ibossCode //Iboos编号
                this.person = this.userInfo.legenPerson //负责人
                this.department =  this.userInfo.legenDept //负责人部门
                this.only = true
            }else{
                this.numID = '' //Iboos编号
                this.person = '' //负责人
                this.department =  '' //负责人部门
                this.only = false
            }
        }
    },
    methods: {
        close() {
            document.getElementsByTagName("body")[0].style.cssText =
                "overflow:auto";
            this.$store.commit('SET_BINGING',false)
        },
        submit() {
            if (!this.numID || !this.person || !this.department ) {
                this.$message.warning('请先完善信息！');
                setTimeout(()=>{
                    this.$store.commit('SET_BINGING',true)
                },1000)
                return
            }
          
            let requireObj = {
                ibossCode: this.numID,
                legenPerson: this.person,
                legenDept: this.department,
            };
            this.$Api("sysUser/bind", requireObj, "post",1).then(res => {
                
                let obj =JSON.parse(Cookies.get('userInfo'));
                    obj.isBind =  this.person;
                    obj.username =  this.person;
                   
                this.$store.commit("SET_USER",obj)
                Cookies.set('userInfo',obj);

                this.$message.success('绑定成功');
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
    width: 60px;
    text-align: right;
    vertical-align: middle;
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
