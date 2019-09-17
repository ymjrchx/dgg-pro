import axios from "axios"
import {
  Message,
  MessageBox,
  Loading
} from 'element-ui'
import Cookie from '@/util/cookie.js'
import Router from '../router'

const baseUrl = 'http://172.16.2.71:8088/';

//  const baseUrl = 'https://192.168.254.226:8088/';

// 测试地址
// const baseUrl ='http://192.168.254.166:8088/'

// const baseUrl = 'http://172.16.3.90:8088/';

//const baseUrl = 'https://dqcapi.dgg.net/';

//预发布的后台地址
//  const baseUrl = 'https://10.2.1.124:8085/';

// 获取电话图片的路径
export const TelPath = "./staticResource/telImg/"

//后台地址，用于url拼接
export const urlPath = baseUrl




//  type:方式 url：路径 data：参数
//  cancel404是否取消 404页面 默认 fasle ，num参数 1：显示loading窗口 默认不显示

const Ajax = (params, num) => {

  if (num == 1) {
    var loading = Loading.service({
      lock: true,
      text: "Loading",
      spinner: "el-icon-loading",
      background: "rgba(255, 255, 255, 0.7)"
    });
  }

  let cookieObj = JSON.parse(Cookie.getCookie("dqcUserKey"))

  let requestHeaderObj = {
    "Content-Type": "application/json",
    "token": cookieObj ? cookieObj.userId : localStorage.getItem("gettoken"),
    "authorize": "Jun",
    "timestamp": (new Date()).valueOf()
  }

  if (params.type === 'get') {
    axios.get(baseUrl + params.url, {
        params: params.data,
        withCredentials: false,
        headers: requestHeaderObj
      })
      .then(function (response) {
        params.success(response)

        if (num == 1) {
          setTimeout(() => {
            loading.close()
          }, 100)
        }
      })
      .catch(function (error) {
        console.log(error)
        if (!params.cancel404) Router.push('/404')
        if (num == 1) {
          setTimeout(() => {
            loading.close()
          }, 100)
        }
      });
  } else {
    axios.post(baseUrl + params.url, params.data, {
        withCredentials: false,
        responseType: 'json',
        headers: requestHeaderObj
      })
      .then(function (response) {
        params.success(response)
        if (num == 1) {
          setTimeout(() => {
            loading.close()
          }, 100)
        }
      })
      .catch(function (error) {
        console.log(error)
        if (!params.cancel404) Router.push('/404')
        if (num == 1) {
          setTimeout(() => {
            loading.close()
          }, 100)
        }
      });
  }
}

export const GetToken = () => { //获取token

  axios.post(baseUrl + "session/gettoken", {})
    .then(function (response) {
      // console.log("运行了", data)
      var data = JSON.parse(response.data);
      if (data.code == 0) {
        //console.log("tttt",data.data.token);
        localStorage.setItem("gettoken", data.data.token);
      } else if (data.code == -1) {
        console.log(data.msg);
      }
    })
    .catch(function (error) {
      console.log("main网络错误信息", error)
    });
}

export const GetKey = (_this) => { //Api key
  const loading = _this.$loading({
    lock: true,
    text: "Loading",
    spinner: "el-icon-loading",
    background: "rgba(255, 255, 255, 0.7)"
  });
  let userId2 = JSON.parse(Cookie.getCookie("dqcUserKey")).userId;

  axios.get(baseUrl + "api/userkey/create.do", {
      params: {
        userId: userId2
      }
    })
    .then(function (response) {
      loading.close();
      if (response.data.code == 0) {
        Message({
          type: 'success',
          message: '申请成功!'
        });
      } else {
        Message({
          type: 'error',
          message: response.data.msg
        });
      }

    })
    .catch(function (error) {
      Message({
        type: 'error',
        message: "网络请求失败，请检查网络环境！"
      });
    });
}

export const GetNews = (postObj, func, page) => { //获取新闻  page是否出现404

  axios.post(baseUrl + "companyNews/news", postObj)
    .then(function (response) {
      //  console.log("运行了", response)

      var data = response.data;
      if (data.code == 0) {
        func(data.data)
      } else {
        Message({
          type: 'error',
          message: "新闻数据请求失败，请稍后再试！"
        });
      }
    })
    .catch(function (error) {
      console.log("main网络错误信息", error)
      if (!page) Router.push('/404')
    });

}




export default Ajax
