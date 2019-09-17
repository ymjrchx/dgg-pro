<template>
    <div class="industrybox">
       <label>行业选择：</label>
       <div class="listBox">
            <div class="strBox" :class="{'hover':show}" @click=" show = true ">
                <span style="color: #9b9ea0;" v-show ="str.indexOf('？') != -1">{{str}}</span>
                <span v-show ="str.indexOf('？') == -1">{{str}}</span>
            </div>
            <div class="l-r" v-show="show" ref="main">
                <ul class="l">
                    <li v-for='(item,idx) in doMain' @click="nextIdx = idx " :key='idx'>{{item.name}}</li>
                </ul>
                <ul class="r" v-show='nextIdx >= 0'>
                    <li v-for='(item,idx) in childArr' @click="getRecom(item)" :key='idx'>{{item.name}}</li>
                </ul>
            </div>
       </div>
    </div>
</template>

<script>
export default {
    props: {
    },
    data(){
        return {
            doMain:[],
            str:"找不到想看的内容，试试按行业筛选？",
            nextIdx:-1,
            childArr:[],
            show:false
        }
    },
    created() {
    
    },
    watch:{
        nextIdx(idx){
           if(idx >= 0) 
           this.childArr = this.doMain[idx].sonArr
       },
       show(bool){
           if(bool){
                document.addEventListener('click', this.hidePanel, false)
           }else{
                document.removeEventListener('click', this.hidePanel, false)
           }
       }
    },
    mounted() {
        this.loadDomain()
    },
    methods: {
        close () {
            this.$emit('closeConsult')
        },
         // 加载推荐
        loadDomain() {
            this.$Http(
                "/industry/queryDomain",
                {  },
                "get",
                true
            ).then(res => {
                this.doMain = res.data.data;
            });
        },
         // 获取推荐
        getRecom(item) {
            this.show = false
            this.$Http(
                "/industry/queryCommodity",
                { id: item.id },
                "get",
                true
            ).then(res => {
                this.str = item.name;
                let Arr =res.data.data
                if(Arr && Arr.length > 0){
                    let newArr = []
                    for(let item of Arr){
                        newArr.push(item.number+"类 "+item.name)
                    }
                    this.$emit('change',newArr)
                }   
               
            });
        },
        hidePanel (e) {
            if (this.$refs.main && !this.$refs.main.contains(e.target)) {//点击除弹出层外的空白区域
               this.show = false
            }
        },
    }
};
</script>
<style scoped lang='stylus'>
*{
    box-sizing border-box
}

    .industrybox{
        font-size: 14px;
        padding: 5px 20px;
        margin: 8px 0;
        border: 1px solid #ddd;
        height: 40px;
        line-height: 20px;
        .listBox{
            position relative
            height: 28px;
            width 500px;
            font-size: 14px;
            display inline-block;
            margin-left 20px;
            .strBox{
                display inline-block;
                height: 28px;
                border: 1px solid #ddd;
                cursor pointer
                line-height: 26px;
                padding-left: 8px;
                padding-right: 33px;
                background: url("../assets/images/searchList/arrow-down.png") no-repeat calc(100%-10px) center;
                transition all .5s;
                &.hover{
                    background: url("../assets/images/searchList/arrow-up.png") no-repeat calc(100%-10px) center;
                    border: 1px solid #fd7d22;
                }
            }
            .l-r{
                position absolute;
                // display none;
                z-index 30;
                background #fff
                border: 1px solid #ddd;
                cursor pointer
                ul{
                    float left;  
                    li{
                        color: #373d41;
                        padding-left: 20px;
                        padding-right: 28px;
                        height: 32px;
                        line-height: 32px;
                        transition all .2s
                        &:hover{
                            background-color #FFF7EE
                        }
                    }
                }
                .l{
                    border-right: 1px solid #ddd;
                    li{
                        background:#fff url("../assets/images/main/right3.png") no-repeat calc(100%-4px) center;
                        background-size 14px auto;
                    }
                }
            }
        }
    }
</style>
