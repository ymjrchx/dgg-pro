import Vue from 'vue'
import VeeValidate, {
  Validator
} from 'vee-validate'
import {
  DatePicker,
  Upload
} from 'element-ui'
import lang from 'element-ui/lib/locale/lang/zh-CN'
import locale from 'element-ui/lib/locale'
import 'element-ui/lib/theme-chalk/index.css';
// import 'babel-polyfill'
import promise from 'es6-promise';
promise.polyfill();

// ie9兼容
if (process.client) {
  window.history.replaceState = window.history.replaceState || function () {}
}
// 设置语言
locale.use(lang)

Vue.component(DatePicker.name, DatePicker)
Vue.component(Upload.name, Upload)

const config = {
  locale: 'zh_CN',
}
Validator.extend('phone', {
  messages: {
    zh_CN: field => field + '必须是11位手机号码',
  },
  validate: value => {
    return value.length == 11 && /^((13|14|15|16|17|18|19)[0-9]{1}\d{8})$/.test(value)
  }
})
Validator.extend('postcode', {
  messages: {
    zh_CN: field => field + '必须是6位数字',
  },
  validate: value => {
    return value.length == 6 && /^\d{6}$/.test(value)
  }
})
Validator.extend('idcard', {
  messages: {
    zh_CN: field => field + '必须是身份证号码',
  },
  validate: value => {
    return /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/.test(value)
  }
})
Vue.use(VeeValidate, config)
