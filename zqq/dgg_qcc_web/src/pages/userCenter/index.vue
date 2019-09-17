<template>
    <div class="clear">
        <HeadSearch/>
        <div class="col-md-12 clear" style="margin-top:10px">
            <div class="col-md-2 user_l" style="padding-left:0">
                <div>
                    <div class="top_header clearfix">
                        <div class="ft">
                            <div class="img">
                                <img id="faceimg" src="" :onerror="logo">
                                <span>更换头像</span>
                            </div>
                        </div>
                        <div class="bd">
                            <div class="title">
                                <div class="name">
                                   {{this.$store.state.userName}}
                                </div>
                                <div class="desc">
                                    <!-- <a href="javascript:void(0)" class="text-primary">立即开通vip</a> -->
                                </div>
                            </div>
                        </div>
                    </div>
                    <el-menu :default-active="activeIndex" class="el-menu-vertical-demo" @select="selectTab" :default-openeds="showIndexArr">
                        <el-menu-item index="1">
                            <i class="el-icon-bell" style="color:#128bed"></i>
                            <span slot="title" style="font-weight:700">消息中心</span>
                        </el-menu-item>
                        <el-submenu index="2">
                            <template slot="title">
                                <i class="el-icon-menu" style="color:#128bed"></i>
                                <span style="color: #363f44;font-weight:700">个人中心</span>
                            </template>
                            <el-menu-item-group>
                                <el-menu-item index="2-1">我的关注</el-menu-item>
                                <!-- <el-menu-item index="2-2">我的会员</el-menu-item>
                                <el-menu-item index="2-3">我的账户</el-menu-item>
                                <el-menu-item index="2-4">我的积分</el-menu-item>
                                <el-menu-item index="2-5">我的报告</el-menu-item>
                                <el-menu-item index="2-6">我的笔记</el-menu-item>
                                <el-menu-item index="2-7">我的关注</el-menu-item>
                                <el-menu-item index="2-8">我的下载</el-menu-item>
                                <el-menu-item index="2-9">我的公司</el-menu-item>
                                <el-menu-item index="2-10">账号设置</el-menu-item> -->
                            </el-menu-item-group>
                        </el-submenu>
                         <el-submenu index="3">
                            <template slot="title">
                                <i class="jun_icon_api" ></i>
                                <span style="color: #363f44;font-weight:700">数据中心</span>
                            </template>
                            <el-menu-item-group>
                                <el-menu-item index="3-1">我的数据</el-menu-item>
                                <el-menu-item index="3-2">申请数据</el-menu-item>
                            </el-menu-item-group>
                        </el-submenu>
                    </el-menu>
                </div>
            </div>
            <div class="col-md-10">
                <transition name="slide-fade"> 
                    <router-view class="router"/>
                </transition>
            </div>
        </div>
    </div>
</template>
<script>
import HeadSearch from "@/components/HeadSearch";
import userPhoto from "@/assets/images/icon_user2_default.png"
export default {
    props: {
        data: {}
    },
    components: {
        HeadSearch
    },
    data() {
        return {
            activeIndex: "2",
            logo: 'this.src="' + userPhoto + '"',
            showIndexArr: ["3",'2']
        };
    },
    created() {
        switch(this.$route.query.type){
            case "msg": this.activeIndex="1";this.selectTab("1");break;
            case "center": this.activeIndex="2-1";this.selectTab("2-1");break;
        }
    },
    methods: {
        selectTab(key) {
            switch(key){
                case '1':this.$router.push('/Msg');break;
                case '2-1':this.$router.push('/follow');break;
                case '3-1':this.$router.push('/MyData');break;
                case '3-2':this.$router.push('/ApplyData');break;
            }
        }
    }
};
</script>
    
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* //用户的头像 */
.user_l {
    padding-right: 30px;
}
.user_l > div{
    background: #fff;
    min-height: 720px;
}
.top_header {
    padding: 15px 8px 15px 15px;
    border-bottom: solid 1px #eee;
}
.top_header .ft {
    width: 54px;
    float: left;
}
.top_header .img {
    width: 100%;
    height: 54px;
    display: table-cell;
    vertical-align: middle;
    cursor: pointer;
    position: relative;
}
.top_header .img > img {
    width: 100%;
    max-height: 54px;
    border-radius: 6px;
}
.top_header .bd {
    width: 165px;
    float: left;
    margin-left: 15px;
}
.top_header .img > span {
    display: block;
    position: absolute;
    left: 0px;
    top: 0px;
    right: 0px;
    bottom: 0px;
    border-radius: 6px;
    color: #fff;
    background: #000;
    opacity: 0;
    font-size: 14px;
    padding: 8px;
    text-align: center;
    transition: opacity 0.3s;
}
.top_header .name {
    font-size: 16px;
    color: #333;
    margin-bottom: 8px;
    margin-top: 6px;
    width: 115px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
/* //路由动画 */
.router {  
     position: absolute;
     left: -10px;
     top: 0px;  
     width: 100%;  
     transition: all .8s ease;  
}  
.slide-fade-enter-active {
  transition: all 1.2s ease;
}
.slide-fade-leave-active {
  transition: all .01s cubic-bezier(2.0, 0.5, 0.8, 1.0);
}

.slide-fade-enter, .slide-fade-leave-to
{
  left:0;right: 0;
  transform: translateX(50px);
  opacity: 0;
}
/* //小图标  */
.jun_icon_api{
    display: inline-block;
    width: 24px;
    height: 18px;;
    background: url('../../assets/images/api.png') center center no-repeat;
    background-size: auto 16px;;
}
</style>
    