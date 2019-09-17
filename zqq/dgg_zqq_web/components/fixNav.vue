<template>
    <div>
        <toolBox/>
        <transition name="fadeIn">
            <div class="fixedNav" v-show='showNav'>
                <miniHead/>
            </div>
        </transition>
    </div>
</template>
  <script>
import miniHead from "~/components/header/mini";
import toolBox from "~/components/toolBox";

export default {
    components: {
        miniHead,
        toolBox
    },
    props: {
        Height: 100
    },
    data() {
        return {
            showNav: 0
        };
    },
    created() {},
    mounted() {
        //监听滚动条
        // window.addEventListener("scroll", this.handleScroll);
    },
    methods: {
        handleScroll() {
            var scrollTop =
                window.pageYOffset ||
                document.documentElement.scrollTop ||
                document.body.scrollTop;
            var offsetTop = this.Height || 100;

            if (scrollTop >= offsetTop) {
                this.showNav = true;
            } else {
                this.showNav = false;
            }
        }
    },
    destroyed() {
        window.removeEventListener("scroll", this.handleScroll);
    }
};
</script>
<style scoped>
.fixedNav {
   
    width: 100%;
    min-width: 1200px;
     /* position: fixed;
    top: 0px;
    left: 0px; */
    height: 66px;
    background-color: #fff;
    box-shadow: 0 4px 6px 0 rgba(0, 0, 0, 0.2);
    z-index: 99;
}
</style>
