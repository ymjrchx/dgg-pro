import popup from "~/components/popup";
import Cookies from "js-cookie";

export const Jump = function (ele, num) { //页面滚动

  let total = ele.offsetTop; //滚动到元素的位置
  if (num) {
    total += num;
  }
  let distance = document.documentElement.scrollTop || document.body.scrollTop //当前浏览器滚动的位置

  // 平滑滚动，时长500ms，每10ms一跳，共50跳
  let step = total / 50
  if (total > distance) {
    smoothDown()
  } else {
    let newTotal = distance - total
    step = newTotal / 50
    smoothUp()
  }

  function smoothDown() {
    if (distance < total) {
      distance += step
      document.body.scrollTop = distance
      document.documentElement.scrollTop = distance
      setTimeout(smoothDown, 5)
    } else {
      document.body.scrollTop = total
      document.documentElement.scrollTop = total
    }
  }

  function smoothUp() {
    if (distance > total) {
      distance -= step
      document.body.scrollTop = distance
      document.documentElement.scrollTop = distance
      setTimeout(smoothUp, 10)
    } else {
      document.body.scrollTop = total
      document.documentElement.scrollTop = total
    }
  }
}
//新开一个窗口页面
export const openNewPage = (that, path, data) => {

  let str = path
  if (data) {
    str += '/' + data.code + (data.id ? ('&' + data.id) : '') + '.html'
  }

  const {
    href
  } = that.$router.resolve({
    path: str
  })
  window.open(href, '_blank')
}
// 上传图片
export const uploadImg = (that, params) => {
  if (params.code == 0) {

    let str = params.data.fsHost + params.data.fsPath;
    let str2 = str.replace("https", "http");
    Cookies.set("imgUrl", str2);

    that.$router.push("/shangbiao/asImg");
  } else {
    popup.created({
      content: params.msg,
      time: 2000
    })
  }
}

 //百度的推送
export var pushBaidu = ()=>{
    var _hmt = _hmt || [];
    (function () {
      var hm = document.createElement("script");
      hm.src =
        "https://hm.baidu.com/hm.js?f2547b9964cf811a060db6be3d047f51";
      var s = document.getElementsByTagName("script")[0];
      s.parentNode.insertBefore(hm, s);
    })();
    (function () {
      var bp = document.createElement("script");
      var curProtocol = window.location.protocol.split(":")[0];
      if (curProtocol === "https") {
        bp.src = "https://zz.bdstatic.com/linksubmit/push.js";
      } else {
        bp.src = "http://push.zhanzhang.baidu.com/push.js";
      }
      var s = document.getElementsByTagName("script")[0];
      s.parentNode.insertBefore(bp, s);
    })();
}
 //
export var push_l = () => {
   ! function (e, t, n, g, i) {
     var tag;
     e[i] = e[i] || function () {
       (e[i].q = e[i].q || []).push(arguments)
     }, n = t.createElement("script"), 
    tag = t.getElementsByTagName("script")[0], n.async = 1, n.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + g, tag.parentNode.insertBefore(n, tag)
   }(window, document, "script", "assets.growingio.com/2.1/gio.js", "gio");
   gio('init', '94bea68a50041ef5', {});
   //custom page code begin here
   //custom page code end here
   gio('send');

}