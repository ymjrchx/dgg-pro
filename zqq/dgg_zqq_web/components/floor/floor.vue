<template>
    <div class="floor" :class="{'show': show, 'left': widthF < 1500}">
        <!-- <ul>
            <li v-for="(item, index) in floorAttr" :key="item.type" :class="{'atthis': atthis == index}">
                <a href="javascript:void(0);" @click="() => {scrollFn(item.type)}">{{item.name}}</a>
            </li>
        </ul> -->
    </div>
</template>

<script>
export default {
    props: ["floorAttr"],
    data() {
        return {
            atthis: "",
            timer: "",
            show: false,
            widthF: 0
        };
    },
    methods: {
        scrollFn(type) {
            let temp = type.substr(1);
            let height = document.getElementById(temp).offsetTop - 70;
            let documentH = document.body.scrollHeight;
            let clientH = document.documentElement.clientHeight;

            this.timer && clearInterval(this.timer);
            this.timer = setInterval(() => {
                let top = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
                if (height >= top && height <= top + 30) {
                    clearInterval(this.timer);
                }
                if (height > top) {
                    if (document.documentElement.scrollTop != top) {
                        document.body.scrollTop += 30;
                    } else {
                        document.documentElement.scrollTop += 30;
                    }
                } else {
                    if (document.documentElement.scrollTop != top) {
                        document.body.scrollTop -= 30;
                    } else {
                        document.documentElement.scrollTop -= 30;
                    }
                }
                // 上边已经修改了滚动位置，优化bugs
                top = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
                //滚动到底部
                if (top + clientH >= documentH) {
                    console.log(top + clientH, documentH);
                    this.timer && clearInterval(this.timer);
                }
            }, 0);
        }
    },
    mounted() {
        // 滚动监听
        let t;
        this.widthF = document.documentElement.clientWidth;
        window.onscroll = () => {
            let top =
                window.pageYOffset ||
                document.documentElement.scrollTop ||
                document.body.scrollTop;
            let clientH = document.documentElement.clientHeight;
            let documentH = document.body.scrollHeight;

            if (
                top + 100 <
                document.getElementById(this.floorAttr[0].type.substr(1))
                    .offsetTop
            ) {
                this.atthis = 0;
                this.show = false;
            } else {
                this.show = true;
            }
            t && clearTimeout(t);
            t = setTimeout(() => {
                this.floorAttr.map((item, i) => {
                    let temp = item.type.substr(1);
                    item.height = document.getElementById(temp).offsetTop;
                    // 滚动到底部
                    if (top + clientH == documentH) {
                        console.log(item.height + clientH, documentH);
                        this.atthis = i;
                        return false;
                    }
                    if (item.height - 200 >= top) {
                        return false;
                    }
                    this.atthis = i;
                });
            }, 50);
        };
    },
    destroyed() {
        window.onscroll = null;
    }
};
</script>

<style scoped lang='stylus'>
.floor {
    position: fixed;
    left: 100px;
    top: 30%;
    background: #fff;
    border: 1px solid #ddd;
    border-bottom: 0;
    color: #333;
    z-index: 90;
    opacity: 0;
    transition: all 0.5s;

    &.left {
        left: 0;
    }

    &.show {
        opacity: 1;
    }

    ul {
        li {
            width: 44px;
            text-align: center;

            a {
                display: block;
                line-height: 14px;
                font-size: 12px;
                padding: 10px;
                color: #333;
                border-bottom: 1px solid #ddd;
            }

            &.atthis {
                a {
                    background: #fd7d22;
                    color: #fff;
                }
            }
        }
    }
}
</style>
