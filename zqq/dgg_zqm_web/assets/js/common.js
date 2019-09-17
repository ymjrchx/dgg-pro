
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

