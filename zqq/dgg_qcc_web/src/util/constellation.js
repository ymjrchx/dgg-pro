  export const Constellationel = () => {
    var timer;
    var canvas = document.getElementById("constellationel");

    var ctx = canvas.getContext("2d");
    var father = document.getElementById("Banner_canvas")
    resize();
    window.onresize = resize;

    function resize() {
      canvas.width = father.clientWidth
      canvas.height = father.clientHeight
    }

    //  var RAF = (function() {
    //    return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || function(callback) {
    //          window.setTimeout(callback, 1000 / 10);
    //        };
    //  })();
    // 鼠标活动时，获取鼠标坐标
    var warea = {
      x: null,
      y: null,
      max: 20000
    };
    father.onmousemove = function (e) {
      e = e || window.event;
      warea.x = e.clientX;
      warea.y = e.clientY;
    };
    father.onmouseout = function (e) {
      warea.x = null;
      warea.y = null;
    };
    // 添加粒子
    // x，y为粒子坐标，xa, ya为粒子xy轴加速度，max为连线的最大距离
    var dots = [];

    for (var i = 0; i < 100; i++) {
      var x = Math.random() * canvas.width;
      var y = Math.random() * canvas.height;
      var xa = Math.random() * 2 - 1;
      var ya = Math.random() * 2 - 1;
      dots.push({
        x: x,
        y: y,
        xa: xa,
        ya: ya,
        max: 20000,
        color: 'rgba(255,255,255,' + getRandom(0, 0.8) + ')',
        size: getRandom(0, 2)
      })
    }

    //************************新增的控制速度**************************
    var fps = 14;
    var now;
    var then = Date.now();
    var interval = 1000 / fps;
    var delta;
    window.requestAnimationFrame = window.requestAnimationFrame || window.mozRequestAnimationFrame || window.webkitRequestAnimationFrame || window.msRequestAnimationFrame;

    function tick() {
      if (window.requestAnimationFrame) {
        timer = requestAnimationFrame(tick);
        now = Date.now();
        delta = now - then;
        if (delta > interval) {
          // 这里不能简单then=now，否则还会出现上边简单做法的细微时间差问题。例如fps=10，每帧100ms，而现在每16ms（60fps）执行一次draw。16*7=112>100，需要7次才实际绘制一次。这个情况下，实际10帧需要112*10=1120ms>1000ms才绘制完成。
          then = now - (delta % interval);
          animate(); // ... Code for Drawing the Frame ...
        }
      } else {
        timer = setTimeout(tick, interval);
        animate();
      }
    }
    tick(); //开始执行
    //  ************************新增的控制速度结束**************************  

    function getRandom(a, b) {
      return Math.random() * (b - a) + a;
    }
    // 每一帧循环的逻辑   dots初始创建的粒子数组    ndots 是dotes与鼠标位置放一起的数组   dot是dots的每一项  d2是ndots的每一项
    function animate() {
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      // 将鼠标坐标添加进去，产生一个用于比对距离的点数组
      var ndots = [warea].concat(dots);
      dots.forEach(function (dot) {
        // 粒子位移

        dot.x += dot.xa;
        dot.y += dot.ya;
        // 遇到边界将加速度反向
        dot.xa *= (dot.x > canvas.width || dot.x < 0) ? -1 : 1;
        dot.ya *= (dot.y > canvas.height || dot.y < 0) ? -1 : 1;
        // 绘制圆点
        ctx.beginPath();
        ctx.arc(dot.x - 0.5, dot.y - 0.5, dot.size, 0, 2 * Math.PI);
        ctx.closePath();
        ctx.fillStyle = dot.color
        ctx.fill();

        //当鼠标移入 
        if (warea.x) {
          var xcM = dot.x - warea.x;
          var ycM = dot.y - warea.y;
          // 粒子与鼠标之间的距离
          var disM = xcM * xcM + ycM * ycM;

          // 选中离鼠标距离小于粒子对象的max值的粒子，再找与它相近的粒子  
          if (disM < dot.max) {
            // 循环比对粒子间的距离
            for (var i = 0; i < ndots.length; i++) {
              var d2 = ndots[i];
              if (dot === d2 || d2.x === null || d2.y === null) continue;
              var xc = dot.x - d2.x;
              var yc = dot.y - d2.y;
              // 两个粒子之间的距离
              var dis = xc * xc + yc * yc;
              // 距离比
              var ratio;
              // 如果两个粒子之间的距离小于粒子对象的max值，则在两个粒子间画线
              if (dis < d2.max) {
                // 如果是鼠标，则让粒子向鼠标的位置移动
                // if (d2 === warea && dis > (d2.max / 2)) {

                //   dot.x -= xc * 0.03;
                //   dot.y -= yc * 0.03;
                // }
                // 计算距离比
                ratio = (d2.max - dis) / d2.max * .8;
                // 画线
                ctx.beginPath();
                ctx.lineWidth = ratio / 2;
                ctx.strokeStyle = 'rgba(255,255,255,' + (ratio) + ')';
                ctx.moveTo(dot.x, dot.y);
                ctx.lineTo(d2.x, d2.y);
                ctx.stroke();
              }
            }
          }
        }
        // 将已经计算过的粒子从数组中删除
        ndots.splice(ndots.indexOf(dot), 1);
      });
      //    timer = RAF(animate);
    }

    window.Stop = function () {
      cancelAnimationFrame(timer)
    }

  }
