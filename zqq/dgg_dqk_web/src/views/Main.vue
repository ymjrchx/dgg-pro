<template>
  <div class="pagesAll">
      <Header/>
      <div class="empt"></div>
      <div class="content">
        <div class="content_l antion" :class="{'showLeft':!show}">
          <div class="l_top">
              <ul class="junFadeIn" v-show='show'>
                  <navTree v-for='item in treeArr' :key='item.name' :itemData='item' @checkFunc='chooseNav' :selectedStr = 'navStr'/>
              </ul>
              <ul class="junFadeIn" v-show='!show'>
                <floatTree v-for='item in treeArr' :key='item.name' :itemData='item' @checkFunc='chooseNav' :selectedStr = 'navStr'/>
              </ul>
          </div>
            <button class="hide_l" v-show="show" @click="show = !show">
              <svg viewBox="64 64 896 896" class="" data-icon="menu-fold" width="1em" height="1em" fill="currentColor" aria-hidden="true"><path d="M408 442h480c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8H408c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8zm-8 204c0 4.4 3.6 8 8 8h480c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8H408c-4.4 0-8 3.6-8 8v56zm504-486H120c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8h784c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8zm0 632H120c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8h784c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8zM115.4 518.9L271.7 642c5.8 4.6 14.4.5 14.4-6.9V388.9c0-7.4-8.5-11.5-14.4-6.9L115.4 505.1a8.74 8.74 0 0 0 0 13.8z"></path></svg>
            </button>
            <button class="hide_l f16" v-show="!show"  @click=" show = !show">
              <svg viewBox="64 64 896 896" class="" data-icon="menu-unfold" width="1em" height="1em" fill="currentColor" aria-hidden="true"><path d="M408 442h480c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8H408c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8zm-8 204c0 4.4 3.6 8 8 8h480c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8H408c-4.4 0-8 3.6-8 8v56zm504-486H120c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8h784c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8zm0 632H120c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8h784c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8zM142.4 642.1L298.7 519a8.84 8.84 0 0 0 0-13.9L142.4 381.9c-5.8-4.6-14.4-.5-14.4 6.9v246.3a8.9 8.9 0 0 0 14.4 7z"></path></svg>
            </button>
        </div>
        <!-- //左边大板块 -->
         
        <div class="content_r antion" :class="{'showRight':!show}">  
          <transition name="slide-fade">
             <router-view/>
          </transition>
        </div>
      </div>
      <!-- <div v-if='isFirstLogin'>
        <firistLogin @updateShow='closeLogin'></firistLogin>
      </div> -->
  </div>
</template>
<script>
import Header from '@/components/header'
import navTree from '@/components/navTree'
import floatTree from '@/components/floatTree'
import Cookies from "js-cookie"
// import firistLogin from '@/components/firistLogin'

export default {
    components: {Header,navTree,floatTree},
    data(){
      return{
        show: true, //右边展开
        navStr:"/workBeach",
        treeArr:[{name:'工作台',icon:"&#xe63e;",child:[],url:"/workBeach"},
                  {name:'发现线索',icon:"&#xe6c6;",child:[
                      {'name':'企业查询',url:"/seek/company"},
                      {'name':'高级筛选',url:"/seek/advanced_filter"},
                      // {'name':'智能推荐',url:"/seek/recommond"},
                    ]
                },
                  // {name:'线索管理',icon:"&#xe674;",child:[
                  //   {'name':'线索公海池',url:""},
                  //   {'name':'线索跟进',url:""}]
                  // }
                ],
        isFirstLogin:false        
      }
    },
    created(){
        // if(Cookies.get('isFirstLogin')){
        //     this.isFirstLogin = true
        // }
      this.navStr = this.$route.fullPath  
    },
    watch:{
      $route(data){
        this.navStr = data.fullPath
      }
    },
    mounted(){
      
      // var ws = new WebSocket(`ws://172.16.0.57:8090//socket/webSocketServer/${this.$store.state.userInfo.valueId}`);

      //   // 建立 web socket 连接成功触发事件
      //   ws.onopen = function () {
      //     // 使用 send() 方法发送数据
      //     ws.send("发送数据");
      //     alert("数据发送中...");
      //   };

      //   // 接收服务端数据时触发事件
      //   ws.onmessage = function (evt) {
      //     var received_msg = evt.data;
      //     console.log('接受数据',evt)
      //     alert("数据已接收..."+received_msg);
      //   };

      //   // 断开 web socket 连接成功触发事件
      //   ws.onclose = function () {
      //     alert("连接已关闭...");
      //   };
    },
    methods:{
      chooseNav(url){
        this.$router.push(url)
      },
      closeLogin(){
          this.isFirstLogin = false;
      }
    }
}
</script>

<style lang="stylus" scoped>
.pagesAll{
  min-width 1200px
}
.empt{
  width 100%;
  height 60px;
}
.content{
    .content_l{
      position fixed;
      top:60px;
      left 0;
      width 200px;
      height 100%;
      background #fff;
      z-index 50;
      &.showLeft{
        width 64px !important;
      }
        .hide_l{
          position: absolute;
          bottom: 60px;
          z-index: 99;
          width: 100%;
          height: 48px;
          line-height: 48px;
          text-align: center;
          cursor: pointer;
          text-decoration: none;
          background-color: #fafafa;
          color: #08f;
        }
        .l_top{
            height: calc(100% - 60px);
            // overflow-y: auto;
            // overflow-x: hidden;
            background-color: #fff;
            -webkit-box-shadow: 0 2px 12px 0 rgba(90,143,203,.32);
            box-shadow: 0 2px 12px 0 rgba(90,143,203,.32);

            >ul{
              // overflow-y: auto;
              // overflow-x: hidden;
              padding-top 10px;
            }
        }

    }
   .content_r{
     padding-left 200px;
     width 100%
     &.showRight{
       padding-left 64px !important;
     }
   } 
}
</style>