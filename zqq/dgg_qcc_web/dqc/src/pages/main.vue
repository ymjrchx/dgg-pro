<template>
  <div>
    <transition name="slide-fade">
      <router-view/>
    </transition>
    <Footer/>
    <!-- vip的申请 -->
    <Vip v-if='this.$store.state.showVipBox' />
  </div>
</template>
<script>
import Footer from "@/components/Footer";
import Cookie from "@/util/cookie.js";
import Store from "@/store";
import Vip from "@/components/Vip";

export default {
    name: "",
    components: {
        Footer,Vip
    },
    data() {
        return {};
    },
    created() {
        let cookie = Cookie.getCookie("dqcUserKey");
        // console.log("cookie",cookie)
        if (cookie) {
            Store.commit("Logined");
            Store.commit("setUserName", JSON.parse(cookie).phoneNo);
            Store.commit("setUserInfo", JSON.parse(cookie));
            if(JSON.parse(cookie).type == 2){
                 Store.commit("closeVipBtn");
            }
        }
    },
    methods: {
        func() {
            //router.push({ path: '/F' })
            // this.$router.push({ path: "/F", query: { id: 2 } });
        },
        update(val) {
            this.string = val;
        }
    }
};
</script>
<style scoped>
.slide-fade {
    position: absolute;
    left: 0;
    right: 0;
}
.slide-fade-enter-active {
    transition: all 1.2s ease;
}
.slide-fade-leave-active {
    transition: all 0.1s cubic-bezier(2, 0.5, 0.8, 1);
}
.slide-fade-enter,
.slide-fade-leave-to {
    left: 0;
    right: 0;
    transform: translateX(50px);
    opacity: 0;
}
</style>