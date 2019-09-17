<template>
  <div class="baseZoom zoom zoomTrue" v-if="isShow">
    <transition name="fadeIn">
      <div class="content w100">
        <div class="icon_loading">
          <span></span> 
          <span></span> 
          <span></span> 
          <span></span> 
          <span></span> 
        </div>
        <p>{{content}}</p>
      </div>
    </transition>
  </div>
</template>
<script>
export default {
  data() {
    return {
      isFixed: true,
      isShow: true,
      content:"请稍后..."
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
    }
  }
};

</script>
<style scoped>
.icon_loading {
  width: 80px;
  height: 30px;
  margin: 15px auto 15px;
}

.icon_loading span {
  display: inline-block;
  width: 8px;
  height: 100%;
  border-radius: 4px;
  background: #84d8ff;
  animation: load 1s ease infinite;
  -webkit-animation: load 1s ease infinite;
  margin: 0 2px;
}

@-webkit-keyframes load {
  0%,
  100% {
    height: 20px;
    background: #84d8ff;
  }
  50% {
    height: 50px;
    margin: -15px 2px;
    background: #44a0f2;
  }
}
@keyframes load {
  0%,
  100% {
    height: 20px;
    background: #84d8ff;
  }
  50% {
    height: 50px;
    margin: -15px 2px;
    background: #44a0f2;
  }
}

.icon_loading span:nth-child(2) {
  -webkit-animation-delay: 0.2s;
  animation-delay: 0.2s;
}

.icon_loading span:nth-child(3) {
  -webkit-animation-delay: 0.4s;
  animation-delay: 0.4s;
}

.icon_loading span:nth-child(4) {
  -webkit-animation-delay: 0.6s;
  animation-delay: 0.6s;
}

.icon_loading span:nth-child(5) {
  -webkit-animation-delay: 0.8s;
  animation-delay: 0.8s;
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
  background: rgba(0,0,0,.2);
}

.baseZoom .content.w100 {
  width: 80px;
  margin-left: -40px;
}

.baseZoom .content {
  box-sizing: content-box;
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

@keyframes icon_loading {
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

/*.icon_loading {
  animation: icon_loading 2s linear infinite;
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
