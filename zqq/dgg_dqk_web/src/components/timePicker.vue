<template>
  <div class='count'>
        <el-date-picker :editable='false' :clearable='false' class="jun_time"
            v-model="startTime" size='small' 
            :picker-options="pickerOptions"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择开始日期"
            @change='changeStart'>
        </el-date-picker>
        <div style="display:inline-block;width:20px;" class="text-center">~</div>
        <el-date-picker :editable='false' :clearable='false' class="jun_time"
            v-model="endTime" size='small'
            :picker-options="pickerOptions2"
            value-format="yyyy-MM-dd"
            type="date"
            @change='changeEnd'
            placeholder="选择结束日期">
        </el-date-picker>
  </div>
</template>
<script>
  export default {
    model:{
        prop:"msg",
        event:"func"
    },
    props: {
        msg:""
    },
    data() {
      return {
        pickerOptions: {
            disabledDate: time => {
                let _now = Date.now();
                return time.getTime() > _now 
            }
        },
        pickerOptions2: {
            disabledDate: time => {
                let _now = Date.now();
                return time.getTime() > _now;
            }
        },
        startTime:'',
        endTime:"",
      }
    },
    computed:{
        
    },
    watch: {
        msg(str){
            if(str){
                this.startTime = str[0]
                this.endTime = str[1]
            }
        }
    },
    mounted(){
        if(this.msg){
            this.startTime = this.msg[0]
            this.endTime = this.msg[1]
        }
    },
    methods: {
       changeStart(time){
            let _now2 = new Date(time).getTime();
            this.pickerOptions2= {
                disabledDate: time => {
                    let _now = Date.now();
                    return time.getTime() > _now || _now2 > time.getTime();
                }
            }
            this.emit()
        },
        changeEnd(time){
            let _now2 = new Date(time).getTime();
            this.pickerOptions= {
                disabledDate: time => {
                    return time.getTime() > _now2 
                }
            }
            this.emit()
        },
        emit(){
            if(this.startTime && this.endTime){
                this.$emit('func',[this.startTime,this.endTime])
                this.$emit('change',[this.startTime,this.endTime])
            }
        }
    }
  };
</script>
<style lang="stylus" scoped >
.count{
    
    // &.active{
    //     border: 1px solid #65c3fe ;
    //     -webkit-box-shadow: inset 1px 1px 3px 1px #e0f3ff, inset -1px -1px 3px 1px #e0f3ff;
    //     box-shadow: inset 1px 1px 3px 1px #e0f3ff, inset -1px -1px 3px 1px #e0f3ff
    // }
    // >div{
    //     height 100%;
    //     display flex;
    //     align-items center;
    //     span{
    //         color #333
    //         width 30px;
    //         text-align center
    //     }
    //     input{
    //         width 58px;
    //         height 100%;
    //         text-align center;
    //     }
    // }
}
</style>