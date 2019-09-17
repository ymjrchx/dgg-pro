import Vue from 'vue'
import Vuex from 'vuex'
import Cookies from "js-cookie"

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
      userInfo:Cookies.get("userInfo") ? JSON.parse(Cookies.get("userInfo")) :"" ,
      filterJson:localStorage.getItem('filterJson') ? JSON.parse(localStorage.getItem('filterJson')) : {must:[]} ,
      conditionConfig:"",
      showBinding:false
  },
  mutations: {
    SET_FILTER(state, obj) {
      state.filterJson = obj
    },
    SET_USER(state, obj) {
      state.userInfo = obj
    },
    SET_CONFIG(state, obj) {
      state.conditionConfig = obj
    },
    SET_BINGING(state,bool){
      state.showBinding = bool
    }
  },
  actions: {

  }
})
