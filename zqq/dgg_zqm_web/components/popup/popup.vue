<template>
  <div class="baseZoom" :class="{'zoom': isFixed, 'zoomTrue' :type == 'leaving'|| type == 'confirm'}" v-if="isShow">
    <transition name="fadeIn" v-if="!type || type == 'loading'">
      <div class="content" v-if="type != 'confirm'" :class="{'w100': type == 'loading'}">
        <div v-if="icon" :class="{'loading': type == 'loading'}">
          <span></span>
          <span></span>
          <span></span>
          <span></span>
          <span></span>
        </div>
        <p>{{content}}</p>
      </div>
    </transition>
    <transition name="fadeIn" v-if="type == 'confirm'">
      <div class="content confirm" >
        <em class="icon-close" @click="close"></em>
        <span class="title">{{ title }}</span>
        <p v-html="content"></p>
        <div class="btnBox">
          <button @click="close()" class="cancel" v-if='cancel'>取消</button>
          <button @click="sureFunc()" class="sure">确认</button>
        </div>
      </div>
    </transition>
    <transition name="fadeIn" v-if="type == 'leaving'">
      <div class="content confirm leaving" >
        <em class="icon-close" @click="close"></em>
        <div class="title">{{ title }}</div>
        <dl>
          <dt>
            姓名
          </dt>
          <dd>
            <input type="text" placeholder="请输入您的姓名" maxlength="30" v-model="name">
          </dd>
        </dl>
        <dl>
          <dt>
            电话
          </dt>
          <dd>
            <input type="text" placeholder="请输入您的电话" maxlength="11" v-model="phone">
          </dd>
        </dl>
        <a href="javascript:void(0);" @click="submit" class="btn">确定</a>
      </div>
    </transition>
  </div>
</template>
<script>
import axios from "axios"
export default {
  data() {
    return {
      isFixed: true,
      isShow: true,
      icon: undefined,
      type: "",
      title: "提示",
      name: '',
      phone: '',
      cancel: true
    };
  },
  created() {},
  mounted() {
    if (this.time) {
      t && clearTimeout(t);
      let t = setTimeout(() => {
        this.close();
      }, this.time);
    }
  },
  methods: {
    close() {
      this.isShow = false;
    },
    sureFunc() {
      this.isShow = false;
      this.success();
    },
    submit () {
      if (!this.name) {
        this.$popup.created({
            content: '请输入姓名',
            time: 2000
        })
        return false
      }
      if (!this.phone) {
        this.$popup.created({
            content: '请输入电话',
            time: 2000
        })
        return false
      }
      if (!/^1(3|4|5|6|7|8|9)\d{9}$/.test(this.phone)) {
        this.$popup.created({
            content: '请输入正确电话',
            time: 2000
        })
        return false
      }
      this.success(this.name, this.phone, () => {
        this.isShow = false
        this.$popup.created({
            content: '提交成功',
            time: 2000
        })
      });
    }
  }
};

</script>
<style scoped>
.loading {
  width: 80px;
  height: 20px;
  margin: 15px auto 15px;
}

.loading span {
  display: inline-block;
  width: 8px;
  height: 100%;
  border-radius: 4px;
  background: #84d8ff;
  animation: load 1s ease infinite;
  -webkit-animation: load 1s ease infinite;
}

@keyframes load {
  0%,
  100% {
    height: 20px;
    background: #84d8ff;
  }
  50% {
    height: 50px;
    margin: -15px 0;
    background: #44a0f2;
  }
}
@-webkit-keyframes load {
  0%,
  100% {
    height: 20px;
    background: #84d8ff;
  }
  50% {
    height: 50px;
    margin: -15px 0;
    background: #44a0f2;
  }
}

.loading span:nth-child(2) {
  -webkit-animation-delay: 0.2s;
  animation-delay: 0.2s;
}

.loading span:nth-child(3) {
  animation-delay: 0.4s;
  -webkit-animation-delay: 0.4s;
}

.loading span:nth-child(4) {
  animation-delay: 0.6s;
  -webkit-animation-delay: 0.6s;
}

.loading span:nth-child(5) {
  animation-delay: 0.8s;
  -webkit-animation-delay: 0.8s;
}

.baseZoom {
  position: fixed;
  z-index: 999;
  top: 30%;
  width: 200px;
  left: 50%;
  margin-left: -100px;
}

.zoom {
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  width: 100%;
  margin: 0;
  z-index: 999;
  line-height: 100%;
  text-align: center;
}
.zoomTrue{
  background: rgba(0,0,0,.5);
}

.baseZoom .content.w100 {
  width: 80px;
  margin-left: -40px;
}

.baseZoom .content {
  box-sizing: content-box;
  background: rgba(0, 0, 0, 0.8);
  display: block;
  padding: 20px;
  width: 200px;
  border-radius: 5px;
  vertical-align: middle;
  line-height: 30px;
  z-index: 102;
  left: 50%;
  margin-left: -100px;
  position: fixed;
  top: 30%;
  text-align: center;
  color: #fff;
}

.baseZoom .content i {
  font-size: 40px;
  margin-top: 10px;
  display: inline-block;
}

@keyframes loading {
  0% {
    transform: rotate(0deg);
  }

  50% {
    transform: rotate(180deg);
  }

  100% {
    transform: rotate(360deg);
  }
}

/*.loading {
  animation: loading 2s linear infinite;
}*/

.baseZoom .confirm {
  position: relative;
  background: #fff;
  color: #303133;
  padding: 10px 20px;
  width: 350px;
  margin-left: -195px;
  border-radius: 0;
  box-shadow: 0 0 5px 1px rgba(204,204,204, 0.5)
}

.baseZoom .confirm .icon-close {
  display: block;
  width: 23px;
  height: 23px;
  position: absolute;
  top: 4px;
  right: 4px;
  background: url("./close.png") 50% no-repeat;
  cursor: pointer;
}

.baseZoom .confirm p {
  padding: 40px 0 30px;
  text-align: center;
  min-height: 50px;
  font-size: 16px;
}

.baseZoom .confirm .title {
  display: block;
  text-align: left;
  font-size: 16px;
}


.confirm .btnBox {
  text-align: right;
  overflow: hidden;
  margin: 0 -20px -10px;
}

.confirm .btnBox button {
  width: 50%;
  float: left;
  text-align: center;
  height: 60px;
  line-height: 60px;
  font-size: 16px;
  cursor: pointer;
}

.confirm .btnBox .cancel {
  background: #dddddd;
}

.confirm .btnBox .sure {
  background: #44a0f2;
  color: #fff;
}


.baseZoom .leaving{
  overflow: hidden;
  background: #fff;
  width: 353px;
  padding: 50px 50px 30px;
  color: #333;
  margin-left: -216px!important;
}
.baseZoom .leaving .title{
  padding: 0 0 25px 0;
  text-align: center;
}
.baseZoom .leaving .btn{
  margin-top: 25px;
}
.baseZoom .leaving dl{
  font-size: 14px;
  line-height: 40px;
  margin-bottom: 10px;
  overflow: hidden;
}
.baseZoom .leaving dl dt{
  float: left;
  width: 70px;
  text-align: right;
}
.baseZoom .leaving dl dd{
  padding-left: 10px;
  overflow: hidden;
  box-sizing: border-box;
}
.baseZoom .leaving dl dd input{
  width: 80%;
  height: 40px;
  line-height: 40px;
  border: 1px solid #eee;
  text-indent: 10px;
}
</style>
