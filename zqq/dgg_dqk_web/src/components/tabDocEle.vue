<template>
    <div class="docEle">
       <ul class="ulBox clear2" :class="[ size == 'small' && 'minsize']">
            <li v-for="(item,idx) in dataArr" :key='idx'>
                <div @click="select(item[val])" :class="{'active':selectedArr.indexOf(item[val]) != -1 }">{{item[name]}}</div>
            </li>
        </ul>
    </div>
</template>
<script>
export default {
    model:{
        prop:"msg",
        event:"func"
    },
    props:{
        msg:"",
        dataArr:{
            default:[]
        },
        model:{
            default:'radio'
        },
        keyName:"",
        size:{
            default:""
        },
        name:{
            default:"name"
        },
        val:{
            default:"code"
        },
        value:{
            default:""
        }
    },
    data(){
        return{ }
    },
    computed:{
        selectedArr(){
            if(this.msg) return this.msg.split(/、/g)
            return []
        }
    },
    methods:{
        select(str){
            if(this.model == 'radio'){
                let newArr=[]
                if(this.selectedArr[0] != str) newArr=[str]

                this.emit(newArr)
            }
            else{
                let idx =this.selectedArr.indexOf(str)
                if( idx > -1 )  this.selectedArr.splice(idx,1);
                else this.selectedArr.push(str)

                this.emit(this.selectedArr)
            }

            
        },
        emit(data){
            let len = data.length,
                str = len == 0 ? "": len == 1 ? data.join('') : data.join('、') 
             
            this.$emit('func',str)
            this.$emit('change',str)
        }
    }
}
</script>

<style lang="stylus" scoped>
    .ulBox{
        &.minsize{
            li{
                width 10%
            }
        }
        li{
            float left;
            width:16.66%;

            >div{
                display inline-block
                padding: 0 10px;
                height: 28px;
                line-height: 28px;
                margin-right: 40px;
                cursor pointer
                &:hover{
                    background-color:#ebf6ff;color:#76beff;
                }
                &.active{
                    background-color:#ebf6ff;color:#08f
                }
            }
        }
    }
</style>
