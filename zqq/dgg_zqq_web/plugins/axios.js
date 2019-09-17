import axios from 'axios'
import Vue from 'vue'; //引入Vue
import popup from "~/components/popup"; //消息
import Cookies from "js-cookie"
import qs from 'qs'

var thatObj;

const baseUrl = require('../nuxt.config').env.baseUrl
///定义fetch函数，config为配置
// const baseUrl = 'http://172.16.2.71:8099/' //江胜辉
// const baseUrl = 'http://172.16.0.38:8099/' //李晨
// const baseUrl = 'http://172.16.3.90:8099/' //刘阳
// const baseUrl = 'http://172.16.0.58:8099/' //黄概
// const baseUrl = 'http://172.16.0.57:8099/' //赵
// baseUrl: 'http://10.2.1.178:8099/' //后台地址

const fetch = async function (config, isLoading) {
  let loading = null;
  if (isLoading) {
    loading = popup.created({
      type: "loading",
      icon: "&#xe61c",
      content: "加载中..."
    });
  }

  if (!Cookies.get('token')) {
    let {
      data
    } = await axios.post(baseUrl + 'session/gettoken')
    if (data.code == 0) {
      Cookies.set('token', data.data);
    }
  }

  //返回promise对象
  return new Promise((resolve, reject) => {
    //创建axios实例，把基本的配置放进去
    const instance = axios.create({
      headers: {
        'Content-Type': sessionStorage.getItem("isFrom") ? 'application/x-www-form-urlencoded' : 'application/json',
        'token': sessionStorage.getItem("userId") || Cookies.get('token')
      },
      timeout: 30000,
      baseURL: baseUrl
    });
    //请求成功后执行的函数
    instance(config).then(res => {
      sessionStorage.removeItem("isFrom");
      if (loading) {
        loading.close()
      }
      if (res.data.code == 0) {
        resolve(res);
      } else {

        console.log('请求code不为1', config.url, res.data)
        popup.created({
          content: res.data.msg,
          time: 2000
        });
        if (res.data.msg.indexOf('超期') > -1) {
          setTimeout(() => {
            axios.post("/server/logout").then(res => {
              if (res.data.ok) {
                window.location.href = "/passport/login"
                sessionStorage.removeItem("userId");
              }
            });
          }, 2000)
        }
        if (thatObj) { //传参数，刷新验证码
          thatObj.updateCode()
          thatObj = null
        }
        // window.location.href = "/404"

      }
      //失败后执行的函数
    }).catch(err => {
      sessionStorage.removeItem("isFrom");
      if (loading) {
        loading.close()
      }
      popup.created({
        content: "数据获取失败，请检查网络环境",
        time: 3000
      });
      console.log(err)
    })
  });
}

// 封装调用的接口 
export function Http(url, data, type, loading, that) {
  //如果type为空，默认为get   如果loading存在 就会出现loading
  if (that) thatObj = that
  let requireUrl = url,
    requireData = data;
  if (!type || type == 'get') {
    type = 'get'
    let str = '';
    for (let key in data) {
      if (data[key] !== '') {
        str += key + '=' + data[key] + "&";
      }
    }
    requireUrl = url + "?" + str;
    requireData = {};
  }
  if (sessionStorage.getItem("isFrom")) requireData = qs.stringify(requireData)
  return fetch({
    //这里的url为baseURL下接口地址，如baseURL为'www.baidu.com',接口地址为'www.baidu.com/api/getdata',那么url里就写'api/getdata'
    url: requireUrl,
    method: type,
    data: requireData
  }, loading)
}


//全局使用的一些方法

Vue.prototype.$Http = Http
Vue.prototype.$Popup = popup
Vue.prototype.$baseUrl = baseUrl

Vue.prototype.$Loading = function () {
  popup.created({
    type: "loading",
    icon: "&#xe61c",
    content: "获取中...",
    time: 3000
  });
}
//计算下钱钱
Vue.prototype.$priceCount= (obj)=>{
  return obj.serviceItem.servicePrice + obj.serviceItem.officialPrice
}