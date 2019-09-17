import Vue from 'vue'
import App from './App'

import store from '@/store/index'
import asyncRequestMethod from './utils/asyncRequestMethod'
import {
  S_IRWXG
} from 'constants';

// import mptoastRegistry from 'mptoast/registry'
// mptoastRegistry(Vue)

// // 挂载全局的store
Vue.prototype.$store = store
// 挂载一个全局的图片路径,方便后期修改图片地址的时候,做统一修改
Vue.prototype.$imagePath = '../../static/'

// 把 异步请求的方法挂载到this.$post上
Vue.prototype.$Http = asyncRequestMethod.post

// 检查是否登录
Vue.prototype.$checkLogin = asyncRequestMethod.checkLogin

//请求一次 cookie
asyncRequestMethod.getCookie()

Vue.config.productionTip = false
App.mpType = 'app'

const app = new Vue(App)
app.$mount()

//个人信息的请求
