
import popup from "~/components/popup"; //消息
import Vue from "vue"
import {Jump} from "~/assets/js/common" //跳转
import axios from "axios";


export default async function (contxt) {
  let loading
  // 全局地址
  if (process.env.NODE_ENV != 'production') {
    // contxt.$axios.defaults.baseURL = 'http://192.168.254.166:8065/'
    // contxt.$axios.defaults.baseURL = 'http://172.16.0.116:8065/'
    contxt.$axios.defaults.baseURL = 'http://zhiqiming.dgg.cn/api/'
  }else {
    // 正式
    contxt.$axios.defaults.baseURL = 'http://zhiqiming.dgg.cn/api/'

    //测试
    // contxt.$axios.defaults.baseURL = 'http://192.168.254.166:8065/'
  }


  // 设置 header token****************start*********************
  let token
  if (process.client) {
    axios.defaults.baseURL = '//'
    token = contxt.store.state.token

    if (!contxt.store.state.userInfo && !token) {
      let { data } = await contxt.$axios.post('/session/gettoken')
     if (data.code == 0) {
      await axios.post("/server/token", {token: data.data})
     }
     token = data.data
    } else if(contxt.store.state.userInfo) {
      token = contxt.store.state.userInfo.userId
    }
  } else {
    token = contxt.req.session.serverToken
    if (!contxt.req.session.authUser && !token) {
      let { data } = await contxt.$axios.post('/session/gettoken')
      contxt.req.session.serverToken = {
        token: data.data
      }
    } else if(contxt.req.session.authUser) {
      token = contxt.req.session.authUser.userId
    }
  }
  contxt.$axios.setHeader('token', token )
  // 设置 header token****************end*********************

  contxt.$axios.setHeader('Content-Type', 'application/x-www-form-urlencoded', ['post'])
  contxt.$axios.onRequest(config => {
    if (process.client && !loading) {
        loading = popup.created({
          type: "loading",
          icon: "&#xe61c",
          content: "加载中..."
        });
    }
  })
  // console.log(contxt)
  contxt.$axios.onResponse(response => {
    if (process.client && loading) {
        loading.close()
        loading = null
    }
    if (process.client && response.data.code !== 0) {
      popup.created({
          content: response.data.msg,
          time: 2000
      })
      return Promise.reject(response);
    }
  })
  contxt.$axios.onResponseError(error => {
    if (process.client && loading) {
        loading.close()
        loading = null
    }
  })
  contxt.$axios.onError(error => {
    if (process.client && loading) {
        loading.close()
        loading = null
    }
    const code = parseInt(error.response && error.response.status)
    if (code === 404) {
      contxt.redirect('/404')
    }
  })
}




/*登录弹窗全局*/
function loginPop (that, type) {

    popup.loginPop({
      title: "登录",
      type: type ? type : 'register',
      success (name, phone, backFn) {
        that.$axios.$post('/userNew/register', {
        "nickname": name,
        "username": phone})
        .then((res) => {
           
          that.$store.commit('SET_USER', {
                    nickname: res.data.nickname,
                    userId: res.data.userId,
                    phoneNo: res.data.phoneNo})
          that.$axios.setHeader('token', res.data.userId )
          backFn(res)
         
        })
      },
      loginFn (phone, backFn) {
        that.$axios.$post('/userNew/login', {
        "username": phone})
        .then((res) => {
          backFn(res)
          that.$store.commit('SET_USER', {
                    nickname: res.data.nickname,
                    userId: res.data.userId,
                    phoneNo: res.data.phoneNo})
          that.$axios.setHeader('token', res.data.userId )
        })

      }
  })
}

// 全局popup
Vue.prototype.$popup = popup
Vue.prototype.$Jump = Jump
Vue.prototype.$loginPop = loginPop

