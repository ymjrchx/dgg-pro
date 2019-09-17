<template>
    <div>
        <div class="index-footer">
            <ul class="index-footer-list">
                <li>
                    <a target="_black" href="/about/1.html" rel="nofollow">关于我们</a>
                </li>
                <li>
                    <a target="_black" href="/about/2.html" rel="nofollow">加入我们</a>
                </li>
                <li>
                    <a target="_black" href="/about/3.html" rel="nofollow">联系我们</a>
                </li>
                <li>
                    <a target="_black" href="/about/4.html" rel="nofollow">常见问题</a>
                </li>
                <!-- <li><a href="#" rel="nofollow">支持帮助</a></li> -->
                <li>
                    <a target="_black" href="/about/9.html" rel="nofollow">费用说明</a>
                </li>
                <li>
                    <a target="_black" href="/about/5.html" rel="nofollow">服务说明</a>
                </li>
                <li>
                    <a target="_black" href="/about/6.html" rel="nofollow">模板下载</a>
                </li>
            </ul>
            <p class="index-footer-text">Copyright 2018 知千秋 版权所有 | 京ICP备16023355号-2</p>
        </div>
    </div>
</template>
  <script>
export default {
    created() {},
    components: {},
    mounted () {
        
        var _hmt = _hmt || [];
        (function() {
          var hm = document.createElement("script");
          hm.src = "https://hm.baidu.com/hm.js?f2547b9964cf811a060db6be3d047f51";
          var s = document.getElementsByTagName("script")[0]; 
          s.parentNode.insertBefore(hm, s);
        })();
    },
    data() {
        return {};
    }
};
</script>
<style scoped>
/* //页脚 */
.index-footer {
    margin: 0 auto;
    margin-top: 30px;
    width: 1000px;
    text-align: center;
}
.index-footer-list li {
    display: inline-block;
    margin: 0 12px;
}
.index-footer-list li a {
    color: #999;
    font-size: 12px;
}
.index-footer-list li a:hover {
    color: #f08b30;
}

.index-footer-text {
    margin-top: 10px;
    color: #999;
    font-size: 12px;
}
</style>
