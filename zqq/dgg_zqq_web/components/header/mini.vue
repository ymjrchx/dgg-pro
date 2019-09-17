<template>
  <div class="minheader">
    <div class="fixed">
      <div class="wapper clear">
        <div class="left">
          <div class="logo">
            <a href="/"><img src="../../assets/images/mini_logo.png" alt="知千秋" title='知千秋'></a>
          </div>
          <ul class="clear">

            <li class="menu" @mouseover="isShow=1" @mouseout="isShow=0">
              <h2>
                <a href="javascript:void(0);">服务分类</a>
              </h2>
            </li>
            <li class="navLi minHide">
              <a href="/" class="col">首页</a>
            </li>
            <li class="navLi">

              <a href="/extension" target="_blank">商标注册
                <i class="icon icon-hot"></i>
              </a>
            </li>
            <li class="navLi">
              <a href="javascript:void(0);" @click="open()">商标工具</a>
            </li>
            <!-- <li class="navLi minHide">
                <a href="/question/navigation_trademark.html" target="_blank">商标问答</a>
            </li> -->
            <li class="navLi minHide">
                <a href="/xw/trade_news.html" target="_blank">商标资讯</a>
            </li>
            <li class="navLi ">
              <a href="javascript:void(0);" onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')">商标咨询
                <i class="icon icon-hot"></i>
              </a>
            </li>
          </ul>
        </div>
        <div class="right">
          <a v-if="!$store.state.userInfo" class="register" href="/passport/register" rel="nofollow">注册</a>
          <a v-if="!$store.state.userInfo" class="login" href="/passport/login" rel="nofollow">登录</a>
          <a v-if="$store.state.userInfo" class="center" href="/center" rel="nofollow">
            <i class="iconfont">&#xe636;</i>{{$store.state.userInfo.nickname?$store.state.userInfo.nickname:$store.state.userInfo.phoneNo}}
          </a>
          <div class="scrollBox">
            <transition name="fadeIn" v-if="$route.path!='/extension'">
              <div class="search" v-show="isShowSearch">
                <input type="text" maxlength="50" :placeholder="holderArr[search_type_index]" v-model="keyWord"
                  @keyup.enter='goList'>
                <a href="javascript:void(0);" class="btn-search" @click="goList"></a>
              </div>
            </transition>
            <transition name="fadeIn">
              <div class="nav" v-show='!isShowSearch'>
                <a href="/center" rel="nofollow">我的知千秋</a>
                <a target="_blank" href="/about/4.html" rel="nofollow">帮助中心</a>
                <a href="javascript:;" onclick="window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', 'self')">客户服务</a>
              </div>
            </transition>
          </div>

        </div>

      </div>
      <minNav @mouseFunc='showMinNav' :isShow='isShow' />
    </div>
  </div>
</template>

<script>
  import minNav from "../minNav";
  import {
    uploadImg
  } from "~/assets/js/common.js";
  import Cookies from "js-cookie";

  export default {
    name: "miniHead",
    props: {
      hasFloor: {
        type: Number,
        default: 0
      }
    },
    data() {
      return {
        keyWord: "",
        search_type_index: 0,
        search_type_show: false,
        search_type: ["商标", "专利", "企业"],
        holderArr: [
          "请输入商标名称、申请号、申请人等信息",
          "请输入专利名称",
          "请输入企业名称"
        ],
        isShow: 0,
        Loading: "",
        isShowSearch: 1
      };
    },
    components: {
      minNav
    },
    mounted() {
      if (this.hasFloor) {
        window.addEventListener("scroll", this.handleScroll);
        this.isShowSearch = 0;
      }
    },
    methods: {
      open(){
        this.$store.commit('isShowToolBox',1)
      },
       loadZn(numbers) {
            return this.$Http(`serviceItem/findServiceItemByNumber`, {
                numbers
            }).then(res => {
                this.showInfo = res.data.data[0];
            });
        },
        goOrder(numbers) {
            var newTab=window.open();
            this.loadZn(numbers).then(() => {
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
                newTab.location.href="/order/auto"
            }).catch(() => {
                // window.open(
                //     "/show/navigation_trademark_register_04&S7741102214293331969.html"
                // , "_blank");
            })
        },
      changeSeachType(i) {
        this.search_type_index = i;
        this.search_type_show = false;
      },
      searchMouseout(e) {
        e.stopPropagation();
        this.search_type_show = false;
      },
      searchMouseover(e) {
        e.stopPropagation();
        this.search_type_show = true;
      },
      searchPhoto() {
        this.$refs.fileinput.click();
      },
      showMinNav(num) {
        this.isShow = num;
      },
      goList() {
        this.keyWord = this.keyWord.replace(/ /g, "");
        if (!this.keyWord) {
          this.$Popup.created({
            content: "搜索关键字不能为空",
            time: 2000
          });
          return;
        }
        if (
          /[`~!@#$%^&*_\-+=<>?:"\/'\\[\]·~！@#￥%……&*——\-+=？：.]/im.test(
            this.keyWord
          )
        ) {
          this.$Popup.created({
            content: "关键字不能包含特殊字符",
            time: 2000
          });
          return false;
        }
        this.Loading = this.$Popup.created({
          content: "搜索中...",
          type: "loading",
          icon: "&#xe61c",
          time: 3000
        });
        if (this.search_type_index === 0)
          this.$router.push({
            path: "/shangbiao/" + this.keyWord + ".html"
          });
        else if (this.search_type_index === 1)
          this.$router.push({
            path: "/patentSearch/" + this.keyWord + ".html"
          });
        else if (this.search_type_index === 2) {
          window.open(
            "https://dqc.dgg.net/#/Company?type=all&keyWord=" + this.keyWord,
            "_blank"
          );
          this.Loading.close();
        }
      },
      //upload上传图片
      handleAvatarSuccess(res, file) {
        this.Loading.close();
        uploadImg(this, res);
        if (this.$route.fullPath == "/shangbiao/asImg") {
          window.location.reload();
        }
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === "image/jpeg" || file.type === "image/png";
        const isLt2M = file.size / 1024 / 1024 < 5;

        if (!isJPG) {
          this.$Popup.created({
            content: "上传图片只能是JPG或PNG格式!",
            time: 2000
          });
        }
        if (!isLt2M) {
          this.$Popup.created({
            content: "上传图片大小不能超过 5MB!",
            time: 2000
          });
        }
        if (isJPG && isLt2M) {
          this.Loading = this.$Popup.created({
            content: "上传中...",
            type: "loading",
            icon: "&#xe61c"
          });
        }
        return isJPG && isLt2M;
      },
      handleScroll() {
        var scrollTop =
          window.pageYOffset ||
          document.documentElement.scrollTop ||
          document.body.scrollTop;
        if (scrollTop > 170) {
          this.isShowSearch = 1;
        } else {
          this.isShowSearch = 0;
        }
      }
    },
    destroyed() {
      if (this.Loading) this.Loading.close();
      window.removeEventListener("scroll", this.handleScroll);
    }
  };

</script>

<style scoped lang='stylus'>
  .none {
    display: none;
  }

  .minheader {
    height: 70px;
    margin: 0 auto;

    >.fixed {
      background: #fff;
      line-height: 70px;
      user-select: none;
      // position: fixed;
      // top: 0;
      // z-index: 101;
      width: 100%;
      border-bottom: 1px solid #f5f5f5;
      box-shadow: 0 4px 6px 0 rgba(0, 0, 0, 0.2);

      @media screen and (min-width: 1400px) {
        .wapper {
          padding: 0 42px;
        }
      }

      @media screen and (max-width: 1400px) {
        .wapper {
          padding: 0 14px;
        }
        .minHide{
          display none !important
        }
      }

      .wapper {
        // max-width: 1800px;
        min-width: 1300px;
        width: 100%;
        margin: 0 auto;
        box-sizing: border-box;

        .left {
          float: left;

          .logo {
            float: left;
          }

          ul {
            padding-left: 40px;
            float: left;

            li {
              float: left;

              a {
                display: block;
                padding-right: 28px;
                font-size: 18px;
                color: #333;
              }

              &.menu {
                position: relative;

                a {
                  background: transparent url('../../assets/images/icon_menu.png') no-repeat left center;
                  padding-left: 20px;
                }
              }
            }

            .navLi {
              a:hover {
                color: #fd7d22;
              }

              .icon {
                display: inline-block;
                background-repeat: no-repeat;
              }

              .icon-hot {
                width: 12px;
                height: 16px;
                margin: 10px 0 0 5px;
                background: url('../../assets/images/main/icon-hot.png') no-repeat center;
              }
            }
          }
        }

        .right {
          float: right;
          font-size: 14px;

          >a {
            float: right;
            padding: 0 25px;
            line-height: 40px;
            height: 40px;
            background: #fd7d22;
            color: #fff;
            margin-top: 14px;
            border-radius: 5px;

            &.login {
              margin-right: 8px;
              background: transparent;
              border: 1px solid #ddd;
              color: #333;
            }

            &.center {
              margin-right: 8px;
              background: transparent;
              border: 0;
              color: #333;
              vertical-align: middle;

              i {
                font-size: 20px;
                margin-right: 5px;
                vertical-align: middle;
              }
            }
          }

          .scrollBox {
            float: right;
            height: 70px;
            position: relative;
            min-width: 290px;
            margin-right: 10px;

            .search {
              background: #f5f5f5;
              height: 42px;
              margin-top: 14px;
              border-radius: 5px;
              margin-right: 5px;
              line-height: 36px;

              .type {
                width: 70px;
                text-indent: 15px;
                float: left;
                position: relative;

                b {
                  font-weight: normal;
                  color: #333;
                  line-height: 40px;
                  display: block;
                }

                /*
              &:after {
                content: '';
                position: absolute;
                display: block;
                width: 0;
                height: 0;
                border: 5px solid #333;
                border-color: #333 transparent transparent transparent;
                top: 19px;
                right: 10px;
              }
*/

                a {
                  display: block;
                  color: #333;
                  line-height: 30px;
                  text-align: center;
                  text-indent: 0;
                }

                .list {
                  background: #f5f5f5;
                  padding: 5px 0 10px;
                }
              }

              input {
                border: 0;
                border-left: 1px solid #ddd;
                text-indent: 10px;
                width: 300px;
                background: #f5f5f5;
                height: 22px;
                line-height: 22px;
                vertical-align: middle;
              }

              .cameraBox {
                display: inline-block;
                width: 27px;
                height: 23px;
                margin-right: 25px;

                .photo {
                  display: inline-block;
                  width: 27px;
                  height: 23px;
                  background: transparent url('../../assets/images/icon_photo.png') no-repeat left center;
                  vertical-align: middle;
                }
              }

              .btn-search {
                display: inline-block;
                width: 27px;
                height: 22px;
                background: transparent url('../../assets/images/icon_search.png') no-repeat left center;
                vertical-align: middle;
                margin-right: 10px;
              }
            }

            .nav {
              height: 42px;
              // margin-top: 14px;
              border-radius: 5px;
              margin-right: 5px;
              line-height: 42px;
              position: absolute;
              right: 0;
              top: 14px;

              a {
                font-size: 14px;
                margin:0 20px;
                color: #333;
              }

              a:hover {
                color: #fd7d22;
              }
            }
          }
        }
      }
    }
  }

</style>
