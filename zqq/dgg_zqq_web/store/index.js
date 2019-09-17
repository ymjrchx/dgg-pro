import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
const baseUrl = require('../nuxt.config').env.baseUrl

Vue.use(Vuex)

let store = () => new Vuex.Store({
  state: {
    testState: "测试",
    toolBox: 0, //打开智能工具弹框
    userInfo: '',
    upLoadUrl: baseUrl+'file/trademarkSearchUpload.do', //过段时间会清理的
    singleUpLoadUrl: baseUrl+'file/singleUpload.do', //一直保存
    docArr: [], // 全局所有字典
  },
  mutations: { //定义方法
    isShowToolBox(state, bool) {
      state.toolBox = bool
    },
    SET_USER(state, obj) {
      state.userInfo = obj
    },
    SET_DOCARR(state, obj) {
      state.docArr = obj
    },
  },
  actions: {
    nuxtServerInit({
      commit
    }, {
      req
    }) {
      if (req.session && req.session.authUser) {
        commit('SET_USER', req.session.authUser)
      }
    },
    async logout({
      commit
    }) {
      await axios.post('/server/logout')
      commit('SET_USER', null)
    }
  }
})

export default store
