<template>
    <section class="tool">
        <div class="toolBox">
            <ul class="wrapper">
                <li class="fir">
                    <h3>常用工具<br>推荐</h3>
                </li>
                <li class="line-animate" v-if=' !($route.fullPath == "/createName") '>
                    <router-link to="/createName">
                        <div class="icon">
                        <img src="../assets/images/index/icon_sb.png" alt="公司起名">
                        </div>
                        <h2>公司起名</h2>
                        <p>为您的公司起一个响亮的名号</p>
                    </router-link>
                </li>
               <li class="line-animate" v-if=' !($route.fullPath == "/company") '>
                    <router-link to="/company">
                        <div class="icon">
                        <img src="../assets/images/index/icon_hd.png" alt="公司核名">
                        </div>
                        <h2>公司核名</h2>
                        <p>核名预查询  提高通过率</p>
                    </router-link>
                </li>
                <li class="line-animate" v-if=' !($route.fullPath == "/testName") '>
                    <router-link to="/testName">
                        <div class="icon">
                        <img src="../assets/images/index/icon_jc.png" alt="公司测名">
                        </div>
                        <h2>公司测名</h2>
                        <p>好名字必能逢凶化吉</p>
                    </router-link>
                </li>
                <li class="line-animate" v-if=' !($route.fullPath == "/generator" || $route.fullPath == "/") '>
                    <router-link to="/generator">
                        <div class="icon">
                        <img src="../assets/images/index/icon_dn.png" alt="经营范围生成器">
                        </div>
                        <h2>经营范围生成器</h2>
                        <p>经营范围怎么填  智企名智能生成</p>
                    </router-link>
                </li>

                <!-- <li class="line-animate" v-if=' !($route.fullPath == "/brandSearch") '>
                    <router-link to="/brandSearch">
                        <div class="icon">
                        <img src="../assets/images/index/icon_r.png" alt="商标查询">
                        </div>
                        <h2>商标查询</h2>
                        <p>免费商标查询  官方数据同步更新</p>
                    </router-link>
                </li>

                <li class="line-animate" v-if=' !($route.fullPath == "/patentSearch") '>
                    <router-link to="/patentSearch">
                        <div class="icon">
                        <img src="../assets/images/index/icon_zl.png" alt="专利查询">
                        </div>
                        <h2>专利查询</h2>
                        <p>深度专业全面快捷</p>
                    </router-link>
                </li>

                <li class="line-animate" v-if=' !($route.fullPath == "/taxSearch") '>
                    <router-link to="/taxSearch">
                        <div class="icon">
                        <img src="../assets/images/index/icon_sw.png" alt="税号查询">
                        </div>
                        <h2>税号查询</h2>
                        <p>更快、更全、更精准</p>
                    </router-link>
                </li>
                <li class="line-animate" v-if=' !($route.fullPath == "/domain") '>
                    <router-link to="/domain">
                        <div class="icon">
                        <img src="../assets/images/index/icon_ym.png" alt="域名查询">
                        </div>
                        <h2>域名查询</h2>
                        <p>高精度IP库，精准，稳定</p>
                    </router-link>
                </li> -->
            </ul>
        </div>
        <div class="three">
            <img src="../assets/images/index/three.png" alt="智企名">
        </div>
         <div class="big" v-if="$route.path != '/'">
            <img src="../assets/images/index/foot_banner.png" alt="智企名,让企业更真实">
        </div>
    </section>
</template>

<script>
import Page from '@/components/page'
export default {
  data () {
    return {
        toolBox:[{img:'tool_03.png',name:""}]
    }
  },
  components: {
    Page
  },
  mounted () {
        console.log( this.$route.fullPath)
  },
  methods: {
    
  }
}
</script>
 
<style lang="stylus" scoped>
  @import '../assets/css/params.styl'
   *{
       box-sizing border-box;
   }   
    .tool
        min-width 1100px
        text-align center;
        overflow hidden
        margin auto;
        padding-top 60px;
        background #fff
        .wrapper
            text-align center;
            margin auto;
            width 1032px;
            overflow hidden
            li
                float left
                width 240px
                height 180px
                margin-right 24px
                border 1px solid $solidcolor
                -webkit-box-shadow: 0 2px 7px 0 #f1f1f1
                box-shadow: 0 2px 7px 0 #f1f1f1
                margin-bottom 24px;
                color #333

                a
                    position absolute
                    display block
                    width 100%
                    height 100%
                    z-index 10 

                &.fir
                    background #268ae4 url('../assets/images/index/1213.png') center center no-repeat;
                    border 1px solid #268ae4

                    h3{
                      font-size 36px;
                      color #fff; 
                      font-weight 100;
                      padding-top 42px; 
                    }
                &.none
                    margin-left 0px;
                &:nth-child(4n)
                     margin-right 0 ;
                &:hover
                    // border 1px solid #268ae4
                    -webkit-box-shadow: 0 2px 7px 0 #d5e9fa
                    box-shadow: 0 2px 7px 0 #d5e9fa     
                .icon
                    height 110px;
                    line-height 110px;                 
                h2
                    font-size 16px;
                    font-weight 100;
                    margin-bottom 8px
                p 
                    font-size 14px;
        .three{
            margin 20px auto 50px 
            }
        .big{
            padding-bottom 20px    
        }
</style>
