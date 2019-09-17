<template>
    <dl class="fa">
        <dt>
        <span class="i" @click="show = !show">
            <i class="el-icon-caret-right" :class="{show}"></i>
        </span>
        <span class="text text-delic" :class="{'active': selectedArr.indexOf(json.first_level) != -1 }"  
            @click='selectOption(0,json.first_level)' :title="json.first_level">
            {{json.first_level}}
        </span> 
        </dt>
        <el-collapse-transition>
            <dd class="child" v-show='show'> 
                <dl style="margin-left:30px">
                <dd v-for='(item,idx) in json.second_level' :key='idx' 
                    v-if='isHasSearch(item)'
                    :class="{'text-delic':true,'active': selectedArr.indexOf(item) != -1 }" 
                    @click='selectOption(1,item)' :title="item">{{item}}</dd>
                </dl>
            </dd>
        </el-collapse-transition>
    </dl>
</template>
<script>
  export default {
    props: {
        json:{
            default:[],
        },
        boxIdx:{
            default:0,
        },
        searchStr:{
            default:"",
        },
        selectedArr:{
            default:[],
        }
    },
    data() {
      return {
        show:false,
        optionsStr:[],
      }
    },
    watch:{
        optionsStr(arr){
            // console.log("选中",arr)
            this.$emit('change',this.boxIdx,arr)
        }
    },
    mounted(){
        if(this.selectedArr.length > 0){
            let newArr=[];
            if(this.selectedArr.indexOf(this.json.first_level) != -1){
                    this.optionsStr = [this.json.first_level]
            }
            else{
                for(let item of this.json.second_level){
                    if(this.selectedArr.indexOf(item) != -1){
                        newArr.push(item)
                    }
                }
                this.optionsStr = newArr
            }
        }
    },
    methods: {
        isHasSearch(str){
            if(!this.searchStr){
                return true;
            }
            else{
                if(str.indexOf(this.searchStr) >= 0)
                 return true;
            }

            return false;
        },
        selectOption(num,str){
            let newArr = [...this.optionsStr],
                idx = newArr.indexOf(str);
            if(idx != -1){
                newArr.splice(idx,1)
            }    
            else{
                let str0 = this.json.first_level,
                    idx0 = newArr.indexOf(str0);
                if(num){
                    if(idx0 != -1) newArr.splice(idx0,1);
                    newArr.push(str)
                }
                else{
                    newArr = [str]
                }
            }

            this.optionsStr = newArr
            this.$emit('change',this.boxIdx,newArr)
        }
    }
  };
</script>
<style lang="stylus" scoped >
  .fa{
        font-size 14px;
        line-height 2em;
        padding 5px 10px;

        .active{
            background #76bfff
        }
        dt{
            span{
                display inline-block;
                width 20px;
                text-align center;
                cursor pointer;
                
                i{
                    color #888;
                    transition: all .2s;
                    -webkit-transition : all .2s;
                    &.show{
                        transform: rotate(90deg);
                        -webkit-transform : rotate(90deg);
                    }
                }
            }
            .text{
                width calc(100% - 20px)
                padding-left 6px;
                text-align left ;
                border-radius 2px 0 0 2px;
                line-height 24px;
                height 24px;
                margin 2px 0;
                vertical-align: middle;
                &:hover:not(.active){
                    background :#cfe9ff
                }
            }
            
        }
        .child{
            dd{
                border-radius 2px 0 0 2px;
                padding-left 6px;
                line-height 22px;
                height 22px;
                margin 4px 0;
                cursor pointer;
                 &:hover:not(.active){
                    background :#cfe9ff
                }
            }
        }
    }
</style>