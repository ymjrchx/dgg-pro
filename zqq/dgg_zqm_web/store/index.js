import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

let store = () => new Vuex.Store({
  state: {
    userInfo: '',
    token:""
  },
  mutations: { //定义方法
    SET_USER(state, obj) {
      state.userInfo = obj
    },
    SET_TOKEN(state, obj) {
      state.token = obj
    }
  },
  actions: {
    nuxtServerInit({commit}, {req}) {
      if (req.session && req.session.authUser) {
        commit('SET_USER', req.session.authUser)
      }
      if (req.session && req.session.serverToken) {
        commit('SET_TOKEN', req.session.serverToken.token)
      }
    },
    async logout({commit}) {
      await axios.post('/server/logout')
      commit('SET_USER', null)
    }
  }
})

export default store
