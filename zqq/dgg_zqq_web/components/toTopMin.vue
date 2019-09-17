<template>
    <div class="minBox">
        <div class="ask">
            建<br/>议<br/>咨<br/>询
        </div>
        <div class="topIcon" @click="goTop"></div>
    </div>
</template>
  <script>
import { Jump } from "~/assets/js/common";
export default {
    data() {
        return {
            showNav: 0
        };
    },
    created() {},
    methods: {
        goTop() {
            Jump(document.getElementsByTagName("body")[0]);
        }
    }
};
</script>
<style scoped>
.minBox {
    position: fixed;
    bottom: 40px;
    right: 60px;
    background-color: #fff;
    z-index: 99;
    width: 46px;
    border: 1px solid #eaeaea;
}
.minBox .ask {
    text-align: center;
    padding: 16px;
    color: #fd7d22;
    cursor: pointer;
}
.minBox .ask:hover {
    text-align: center;
    background: #fd7d22;
    color: #fff;
}
.topIcon {
    background: #fff url("../assets/images/top.png") center center no-repeat;
    background-size: 28px;
    width: 100%;
    height: 46px;
    cursor: pointer;
    border-top: 1px solid #eaeaea;
}
.topIcon:hover {
    background: #fff url("../assets/images/top2.png") center center no-repeat;
    background-size: 28px;
}
</style>
