<template>
    <div>
        <div class="main_head pos_re">
            <div class="fl drop_nav" :class="{'active':isShow}"  @mouseover="isShow=1" @mouseout="isShow=0">
                <i class="icon icon-item"></i>   
            </div>
            <div class="fl">
                <router-link to='/'><img src="../../assets/images/main/logo.png" alt="logo" class="logo" title="知千秋"></router-link>
                <span class='text'>专注于商标注册查询智能服务化平台</span>
            </div>
            <ul class="fr nav">
                <li class="fr" v-if="!$store.state.userInfo">
                    <router-link to='/passport/login' rel="nofollow">登录</router-link>
                </li>
                <li class="fr center" v-if="$store.state.userInfo">
                    <router-link to='/center' rel="nofollow">
                        <i class="iconfont">&#xe636;</i>{{$store.state.userInfo.nickname?$store.state.userInfo.nickname:$store.state.userInfo.phoneNo}}</router-link>
                </li>
                <li class="fr">
                    <a href="javascript:;" onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')" rel="nofollow">客户服务</a>

                </li>
                <li class="fr">
                    <a target="_blank" href="/about/4.html" rel="nofollow">帮助中心</a>
                </li>
                <li class="fr">
                    <router-link to='/center' rel="nofollow">我的知千秋</router-link>
                </li>
                <li class="fr">
                    <a rel="nofollow" class="col">服务热线：400-871-9995</a>
                </li>
            </ul>
            <div class="freeReg" v-if="!$store.state.userInfo">
                <router-link to='/passport/register' rel="nofollow">免费注册</router-link>
            </div>
            <minNav @mouseFunc='showMinNav' :isShow='isShow' :styleObj="{top:'50px'}"/>
        </div>
    </div>
</template>
<script>
import Cookies from "js-cookie";
import minNav from "~/components/minNav";
export default {
    data() {
        return {
            showInfo: "",
            isShow: false,
        };
    },
    components: {
      minNav
    },
    mounted() {
        // console.log(this.priceData);
        this.loadZn();
    },
    methods: {
        loadZn() {
            this.$Http(`serviceItem/findServiceItemByNumber`, {
                numbers: "S7741102214293331969"
            }).then(res => {
                this.showInfo = res.data.data[0];
            });
        },
         showMinNav(num) {
            this.isShow = num;
        },
        goOrder() {
            if (!this.showInfo) {
                window.open(
                    "/show/navigation_trademark_register_04&S7741102214293331969.html"
                );
            }
            Cookies.set("product", {
                name: this.showInfo.serviceItem.name,
                serviceId: this.showInfo.serviceItem.id,
                serviceAttrId: "",
                num: 1,
                officialPrice: this.showInfo.serviceItem.officialPrice,
                servicePrice: this.showInfo.serviceItem.servicePrice,
                total:
                    this.showInfo.serviceItem.officialPrice +
                    this.showInfo.serviceItem.servicePrice
            });
            window.open("/order/auto");
        }
    }
};
</script>
<style scoped>
.main_head {
    height: 50px;
    width: 100%;
    background: #282b3a;
    color: #fff;
    line-height: 50px;
    border-bottom: 1px solid #666;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}
.main_head .text {
    margin-left: 30px;
}
.nav > li {
    margin-right: 40px;
}
.nav > li a {
    color: #fff;
}
.nav > li .col {
    color: #ff6600;
    font-weight: bold;
    font-size: 15px;
}
.nav > li.center a {
    vertical-align: middle;
}
.nav > li.center a i {
    font-size: 16px;
    margin-right: 5px;
    position: relative;
    top: 1px;
}
.freeReg {
    position: absolute;
    right: 0;
    top: 50px;
    width: 120px;
    height: 56px;
    line-height: 56px;
    background: #fd7d22;
    color: #fff;
    border-radius: 0 0 0 4px;
    text-align: center;
    z-index: 2;
}
.freeReg a {
    color: #fff;
    font-size: 16px;
}
.freeReg:hover {
    background: #ff6600;
}
.drop_nav{
    width: 50px;
    height: 50px;
    text-align: center;
    margin-right: 20px;
    cursor: pointer;
    border-right: 1px solid #666;
}
.drop_nav .icon-item {
  display: inline-block;
  width: 16px;
  height: 14px;
  margin-top: 18px; 
  background: url('../../assets/images/main/icon-item.png') no-repeat 0 0;
}
.drop_nav.active{
    background: #ff6600;
     border-right: 1px solid #ff6600;
}

/* .freeReg a:hover,
.nav > li a:hover {
    color: #fd7d22;
} */
</style>
