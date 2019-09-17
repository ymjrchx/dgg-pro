<template>
  <div class='count' :class="{active}">
      <div class="jun_in">
        <input type="text" placeholder="请输入"  v-model="minVal" @input="VailVal('minVal')" @focus="active = true" @blur="active = false">
          <span>~</span>
        <input type="text" placeholder="请输入" v-model="maxVal" @input="VailVal('maxVal')" @focus="active = true" @blur="active = false">
      </div>
  </div>
</template>
<script>
  export default {
    model:{
        prop:"msg",
        event:"func"
    },
    props: {
        msg:"",
        min:{
            default:"",
        },
        max:{
            default:"",
        }
    },
    data() {
      return {
        minVal:'',
        maxVal:'',
        active:false,
      }
    },
    computed:{
        
    },
    watch: {
        active(bool){
            if(!bool){
                this.minVal = Number(this.minVal || 0); 
                this.maxVal = Number(this.maxVal || 0); 

                if(this.minVal>this.maxVal){
                    let num = this.minVal;
                    this.minVal = this.maxVal;
                    this.maxVal = num
                }

                this.$emit('func',[this.minVal,this.maxVal])
                this.$emit('change',[this.minVal,this.maxVal])
            }
        },
        msg(str){
            if(str){
                this.minVal = str[0]
                this.maxVal = str[1]
            }
        }
    },
    mounted(){
        if(this.msg){
         
            this.minVal = this.msg[0]
            this.maxVal = this.msg[1]
        }
    },
    methods: {
        // emitVal(data){  
        //     this.$emit('func',str)
        //     this.$emit('change',str)
        // },
        VailVal(key){
            let numStr = this[key].replace(/\D/g,"");
            this[key] =  numStr.length >5 ? '99999' : numStr;
        }
    }
  };
</script>
<style lang="stylus" scoped >
.count{
    position relative
    display: inline-block;
    padding: 4px 11px;
    width: 100%;
    height: 36px;
    font-size: 13px;
    line-height: 1.5;
    color: #5a5a5a;
    background-color: #fff;
    background-image: none;
    border: 1px solid #dcdfe6;
    border-radius: 3px;
    -webkit-transition: all 0.3s;
    transition: all 0.3s;
    cursor: pointer;
    &.active{
        border: 1px solid #65c3fe ;
        -webkit-box-shadow: inset 1px 1px 3px 1px #e0f3ff, inset -1px -1px 3px 1px #e0f3ff;
        box-shadow: inset 1px 1px 3px 1px #e0f3ff, inset -1px -1px 3px 1px #e0f3ff
    }
    >div{
        height 100%;
        display flex;
        align-items center;
        span{
            color #333
            width 30px;
            text-align center
        }
        input{
            width 58px;
            height 100%;
            text-align center;
        }
    }
}
</style>