import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    telNumber: '400-871-9995',
    userInfo: "",
  },
  mutations: {
    updateTel(state, val) {
      state.telNumber = val
    },
    decrement(state) {
      state.count--
    },
    SET_USERINFO(state, obj) {
      state.userInfo = obj
    }
  }
})

export default store
