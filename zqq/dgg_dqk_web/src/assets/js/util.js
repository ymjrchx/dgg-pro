
import Router from '../../router'

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

export const newPage = (path,data)=>{   //新开页面
  const {href} = Router.resolve({
      path: path,
      query: data
  })
  window.open(href, '_blank')
}

export const hisTory={         //记住搜索历史 
  set(key,val,num=6){ 
     let obj={value:val};
     let arrStr=localStorage.getItem(key);
     if(arrStr){
          let arr=JSON.parse(arrStr);
          let i=0;
          for(i;i<arr.length;i++){
              if(val.name==arr[i].value.name) break;
          }
          if(i<arr.length){
              let newObj=arr[i];
                  arr[i]=arr[0];
                  arr[0]=newObj;
          }else{
              arr.unshift(obj);
          }
          if(arr.length >num){
              arr.splice(num)
          }
          localStorage.setItem(key,JSON.stringify(arr))
     }
    else{
        localStorage.setItem(key,JSON.stringify([obj]))
    }
  },
  get(key){  //取出搜索历史 "dqc_history"
     let arrStr = localStorage.getItem(key);
     let arr = arrStr ? JSON.parse(arrStr) : false;
      return arr;
  },
  remove(key){
    localStorage.removeItem(key)
  }
}