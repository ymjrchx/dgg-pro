import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

function addClick(param) {
  store.state.showVipBox = 0
}

const store = new Vuex.Store({
  state: {
    testState: "测试",
    isLogin: false, //是否登录
    headActive: 0, //头部选中哪一个 
    msgNum: 0, //消息数量 
    userName: "", //用户名称 
    userInfo: "",
    showVipBtn: 1, //是否显示申请vip按钮
    showVipBox: 0, //是否打开vip申请界面
  },
  mutations: { //定义方法
    Logined(state) { //已登录
      state.isLogin = true
    },
    logout(state) { //注销
      state.isLogin = 0;
    },
    updateHeadActive(state, num) { //改变后台的选中
      state.headActive = num;
    },
    setUserName(state, phone) { //改变后台的选中
      state.userName = phone;
    },
    setUserInfo(state, obj) {
      state.userInfo = obj
    },
    toggleVipBox(state, bool) {
      state.showVipBox = bool;
      if (bool) {
        document.addEventListener("click", addClick, false);
      } else document.removeEventListener("click", addClick, false);
    },
    closeVipBtn(state) {
      state.showVipBtn = 0;
    }
  }
})
export {
  store as
  default
}
