import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import("babel-polyfill")

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

import '@/assets/css/base.css';
import "@/assets/css/el_cover.css";
import "@/assets/css/animation.css";
import Http from '@/assets/js/Api.js';
import {Jump,newPage} from '@/assets/js/util.js';
import popup from "@/components/popup"; //loading

import NProgress from 'nprogress'; //进度条
import 'nprogress/nprogress.css';
import Cookies from "js-cookie"

router.beforeEach((to, from, next) => {

  let path = to.fullPath
  if( !Cookies.get('userInfo') && path != '/' && path != '/passport/register' && path != '/users/agreement' ){
    next('/')
  }
  else if(Cookies.get('userInfo') && path == '/'){
    next('/workBeach')
  }
  else{
    NProgress.start();
    next()
  }
  // NProgress.start();
  // next()
});
router.afterEach(transition => {
  NProgress.done();
  document.title  = '顶企客'
});


Vue.config.productionTip = false

//全局的一些方法

Vue.prototype.$Api = Http
Vue.prototype.$jump = Jump
Vue.prototype.$Loading = popup.created
Vue.prototype.$newPage = newPage

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
