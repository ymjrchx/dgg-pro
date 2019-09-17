// const baseUrl = 'https://zhiqianqiu.com/api/'

const baseUrl = 'http://172.16.3.90:8099/'
// const baseUrl = 'http://192.168.254.166:8099/'

var post = async function (url, data = {}, requireType = 'get', isJson) {
  let contentType = isJson ? 'application/json' : 'application/x-www-form-urlencoded'

  if (!wx.getStorageSync('cookie')) {
    let {
      data
    } = await getCookie()
    if (data.code == 0) {

      wx.setStorageSync('cookie', data.data)
    }
  }
  return new Promise((resolve, reject) => {
    try {
      // 从本地存储里面获取 openid
      // data.openId = wx.getStorageSync('wxInfo').openid || ''
      wx.request({
        url: baseUrl + url,
        data: data,
        method: requireType,
        header: {
          'content-type': contentType,
          token: wx.getStorageSync('cookie')
        },
        success(res) {
          if (res.data.code === 0) {
            resolve(res.data)
          } else {
            wx.showToast({
              title: res.data.msg+'',
              icon: 'none',
              duration: 2000
            })
            reject(res.data)
          }
        },
        fail(e) {
          e.code = 666666
          reject(e) //注意,这里要用resolve,否则前端代码会崩溃
        }
      })
    } catch (e) {
      e.code = 777777
      console.error('网络异步请求出错,', e)
      reject(e) //注意,这里要用resolve,否则前端代码会崩溃
    }
  })
}
const getCookie = function () {
  return new Promise((resolve, reject) => {
    wx.request({
      url: baseUrl + 'session/gettoken',
      data: '',
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      success(res) {
        try {
          wx.setStorageSync('cookie', res.data.data)
        } catch (e) {
          console.log('cookie错误信息', e)
        }
        resolve(res)
      },
      fail(e) {
        resolve(e) //注意,这里要用resolve,否则前端代码会崩溃
      }
    })
  })
}

const checkLogin = function () {
  if (wx.getStorageSync('userInfo')) {
    if (wx.getStorageSync('userInfo').phoneBind === 0) {
      wx.redirectTo({
          url: "/pages/phone/main"
      });
    } else {
      return true
    }
  } else {
    wx.redirectTo({
        url: "/pages/authorized/main"
    });
  }
}
export default {
  post,
  getCookie,
  checkLogin
}
