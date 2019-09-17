// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'

import NProgress from 'nprogress'; //进度条
import 'nprogress/nprogress.css';
import VueClipboard from 'vue-clipboard2' //一键复制插件
import store from '@/store'

import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/base.css';
import "@/assets/test.css";
import "@/assets/ele_cover.css";
import "@/assets/animation.css";

import echarts from 'echarts' //图表
Vue.prototype.$echarts = echarts

// import VueAwesomeSwiper from 'vue-awesome-swiper'   // 轮播组件
// Vue.use(VueAwesomeSwiper, /* { default global options } */)

Vue.use(ElementUI);

Vue.use(VueClipboard)

Vue.config.productionTip = false


import Ajax, {GetToken} from '@/util/Http.js' 
GetToken();

import Global from '@/util/global'
Vue.prototype.$global = Global

Vue.prototype.$axios = Ajax

router.beforeEach((to, from, next) => {
  NProgress.start();
  next()
});
router.afterEach(transition => {
  NProgress.done();
});

new Vue({
  el: '#app',
  store: store,
  router,
  components: {
    App
  },
  template: '<App/>'
})
