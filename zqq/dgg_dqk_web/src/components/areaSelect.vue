<template>
  <div class='area' :class="{active}" @click=" focusFunc" ref='main_1'>
      <i class="el-icon-arrow-down" :class="{'show':active}"></i>
      <div class="inputBox jun_in">
        <ul class="tag" @keydown="keyFunc">
            <li v-for="(item,idx) in selectedArr" :key='idx'>{{item}} <i class="el-icon-error" @click="delSelect(item)"></i></li>
            <li class="last"> <input type="text" ref='input' v-model="keyWord"></li>
        </ul>
      </div>
      <div class="pullDown" v-show='active'>
         <div v-for="(item,idx) in arr" :key='idx'  v-show="searchArr == 'all' || searchArr.indexOf(idx) >=0">
            <options :json='item' @change='getOptionMsg' :boxIdx='idx' :searchStr='keyWord' :selectedArr='selectedArr'></options>
         </div>
         <div v-show="searchArr != 'all' && searchArr.length ==  0" class="nofind">
            无匹配结果
         </div>
      </div>
  </div>
</template>
<script>
  import options from './areaSelectOptions'
  export default {
    model:{
        prop:"msg",
        event:"func"
    },
    props: {
        msg:"",
        arr:{
            default:[],
        },
        value:""
    },
    components:{
        options
    },
    data() {
      return {
        active:false,
        allOptionsIdx:[],
        selectedArr:[],
        keyWord:"",
        searchArr:'all'
      }
    },
    computed:{
        
    },
    watch: {
        active(bool){
           if(bool){
                document.addEventListener('click', this.hidePanel, false)
           }else{
                document.removeEventListener('click', this.hidePanel, false)
           }
        },
        allOptionsIdx(arr){
            let newArr = [];
            for(let item of arr){
                newArr.push.apply(newArr,item);
            }
            this.selectedArr = newArr;
            this.$emit('func',newArr)
        },
        keyWord(word){
            if(!word){
                this.searchArr ='all';
                return;
            } 
            let newArr =[];
            let idx = 0;
            for(let item of this.arr){
                if(item.first_level.indexOf(word) >= 0){
                   newArr.push(idx);
                   idx++;
                   continue;
                }
                for(let item2 of item.second_level){
                    if(item2.indexOf(word) >= 0){
                        newArr.push(idx)
                        break;
                    }
                }
                idx++;
            }
          
            this.searchArr = newArr
            // console.log(12345,newArr)
        },
        msg(str){
            if(str) this.selectedArr = this.msg
        }
    },
    mounted(){
        if(this.msg){
            this.selectedArr = this.msg
            // this.$emit('func',this.msg)
        }        
    },
    methods: {
        focusFunc(){
            this.active = true;
            this.$refs.input.focus()
        },
        hidePanel (e) {
            if (this.$refs.main_1 && !this.$refs.main_1.contains(e.target)) {//点击除弹出层外的空白区域
               this.active = false
            }
        },
        keyFunc(e){
            if(e && e.keyCode === 8 && !this.keyWord){
        
                this.delSelect(this.selectedArr[this.selectedArr.length -1])
            }
        },
        getOptionMsg(idx,msg){
            // console.log(idx)
            let newArr = [...this.allOptionsIdx]
                newArr[idx] = msg;
            this.allOptionsIdx = newArr
        },
        delSelect(str){
            // let newArr = [...this.allOptionsIdx]
            // for(let item of newArr){
            //     if(item){
            //         let idx = item.indexOf(str)

            //         if(idx != -1) item.splice(idx,1)
            //     }
            // }
            // this.allOptionsIdx = newArr

            let newArr = [...this.selectedArr]

                newArr.splice(newArr.indexOf(str),1)
                
            this.selectedArr =newArr
            this.$emit('func',newArr)
        }
    }
  };
</script>
<style lang="stylus" scoped >
.area{
    position relative
    display: inline-block;
    padding: 4px 2px;
    width: 420px;
    font-size: 13px;
    line-height: 1.5;
    color: #5a5a5a;
    background-color: #fff;
    background-image: none;
    border: 1px solid #dcdfe6;
    border-radius: 3px;
    -webkit-transition: all 0.3s;
    transition: all 0.3s;
    padding-right: 26px;
    &.active{
        border: 1px solid #65c3fe ;
        -webkit-box-shadow: inset 1px 1px 3px 1px #e0f3ff, inset -1px -1px 3px 1px #e0f3ff;
        box-shadow: inset 1px 1px 3px 1px #e0f3ff, inset -1px -1px 3px 1px #e0f3ff
    }
    >i{
        position:absolute;
        right:10px;
        top:11px;
        transition: all .2s;

        &.show{
            transform: rotate(180deg);
        }
    }
    .inputBox{
       
        .tag{
            display inline-block;
            li{
                display inline-block;
                // height: 24px;
                padding: 0 8px;
                line-height: 22px;
                box-sizing: border-box;
                border-color: transparent;
                margin: 2px 0 2px 6px;
                background-color: #f0f2f5;
                border-radius 2px;
                color:#909399;
                &.last{
                     background-color: #fff;
                    >input{
                        width:40px;
                        border-width: 0;
                        font-size: 100%;
                        height: 100%;
                        background: transparent;
                        outline: 0;
                        border-radius: 3px;
                        line-height: 1;
                    }
                }
                i{
                    color: #c0c4cc;
                    right: -7px;
                    top: 0;
                    position: relative;
                    cursor: pointer;
                    font-size: 12px;
                    height: 16px;
                    width: 16px;
                    line-height: 16px;
                    &:hover{
                        color: #909399;
                    }
                }
            }
        }
    }
    .pullDown{
        position absolute;
        left 0;
        width 100%;
        top:calc(100%+2px);
        z-index 10;
        background #fff;
        border-radius: 3px;
        box-shadow: 0 2px 8px rgba(0,0,0,.15);
        background-clip: padding-box;
        height 260px;
        overflow-y auto;
        .nofind{
            text-align center;
            line-height 40px;
            height 40px;
            color #888;
        }
    }
}
</style>