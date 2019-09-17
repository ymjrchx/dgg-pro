<template>
  <div class="j_select" @click="togglePanel($event)">
        <div>{{name}}</div>
        <i class="iconfont arrow antion" :class="{'top':isShow}">&#xe7ac;</i>
        <el-collapse-transition >
            <ul class="box action" v-show='isShow' ref="main">
                <li @click.stop="selectFunc(item)" v-for="item in selectArr" :key='item' :class="{'active':name == item}">{{item}}</li>
            </ul>
        </el-collapse-transition>
  </div>
</template>
<script>
export default {
    props:{
         name:{
             default:''
         },
         selectArr:{
             default:[]
         }
    },
    data(){
        return{
            isShow:false
        }
    },
    methods:{
        selectFunc(item){
            this.$emit("select",item)
            this.isShow = false
        },
        togglePanel (event) { 
　　　　　　　　
　　　　　　　　event || (event = window.event);
　　
　　　　　　　　event.stopPropagation ? event.stopPropagation() : (event.cancelBubble = true); //阻止冒泡

　　　　　　　　this.isShow ? this.hide() : this.show()
        },
        show () {
            this.isShow = true
            document.addEventListener('click', this.hidePanel, false)
        },

        hide () {
            this.isShow = false
            document.removeEventListener('click', this.hidePanel, false)
        },

        hidePanel (e) {
            if (this.$refs.main && !this.$refs.main.contains(e.target)) {//点击除弹出层外的空白区域
                this.hide()
            }
        },
    },
    beforeDestroy () {
        this.hide()
    }
    
}
</script>

<style lang="stylus" scoped>
.j_select{
    min-width:120px;
    height 30px;
    display inline-block;
    border-radius 2px;
    border 1px solid #eaeaea;
    padding-left 8px
    line-height 30px;
    padding-right 6px;
    position relative
    cursor pointer;
    color: #777

    &:hover{
         border: 1px solid #65c3fe !important;
        -webkit-box-shadow: inset 1px 1px 3px 1px #e0f3ff, inset -1px -1px 3px 1px #e0f3ff;
        box-shadow: inset 1px 1px 3px 1px #e0f3ff, inset -1px -1px 3px 1px #e0f3ff
    }

    .arrow{
        position absolute;
        padding-right 10px;
        font-size 13px;
        right -4px;
        top 0;
    
        &.top{
            transform: rotate(180deg);
            -webkit-transform: rotate(180deg);
            -moz-transform: rotate(180deg);
            -o-transform: rotate(180deg);
            -ms-transform: rotate(180deg); 
            transform-origin:34% 50%;
            -o-transform-origin:34% 50%;
            -webkit-transform-origin:34% 50%;
            -moz-transform-origin:34% 50%;
            -ms-transform-origin:34% 50%;
        }        
    }

    .box{

        position absolute;
        z-index 10;
        background #fff;
        min-width:120px;
        left 0;
        padding: 2px 0px;
        border-radius 0 0 2px 2px;
        -webkit-box-shadow: 0 2px 7px 0 rgba(50,99,137,.23);
        box-shadow: 0 2px 7px 0 rgba(50,99,137,.23);

        li{
            padding: 0px 8px;

            &:hover{
                color #08f;
            }
            &.active{
                background-color: #ebf6ff;
                color #08f;
            }
        }

      
    }
}
</style>
