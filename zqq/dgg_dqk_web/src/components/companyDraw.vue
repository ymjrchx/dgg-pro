<template>
    <div class="infoContent" id='infoContent'>

       <div :class="{show:faId}">
           <userInfo :faId='faId' v-if='faId'></userInfo>
       </div>
       <a href="javascript:void(0)" class="drawBtn" :class="{show:faId}" @click="close"><i class="el-icon-arrow-right"></i></a>
    </div>
</template>
<script>
  import userInfo from '@/views/seek/companyInfo'
export default {
    
    props:{
        faId:{
            default:""
        }
    },
    components: {
     userInfo
    },
    data() {
        return{
            
        }
    },
    created(){
        
    },
    watch: {
       
    },
    mounted(){
      
    },
    methods:{
        close(){
            this.$emit('close')
        }
    }
};
</script>
<style scoped lang="stylus">

.infoContent>div{
    position fixed;
    right -1100px;
    top 60px;
    width 1100px;
    opacity 1;
    height calc(100% - 64px);
    background-color: #EDF0F4;
    -webkit-box-shadow: 0 1px 20px 0 rgba(0,0,0,.2);
    box-shadow: 0 1px 20px 0 rgba(0,0,0,.2);
    z-index 80
    transition all .5s
    overflow-y auto;
    &.show{
        width 1100px;
        opacity 1;
        right 0;
    }
}
.drawBtn{
    position: fixed;
    right:40px;
    top: 60px;
    width 40px;
    height: 40px;
    line-height:40px;
    font-size 16px;
    opacity:0;
    cursor: pointer;
    background-color: #fff;
    border-radius: 2px 0 0 2px;
    -webkit-box-shadow:-6px 1px 10px 0 rgba(0,0,0,0.2);
    box-shadow: -6px 1px 10px 0 rgba(0,0,0,0.2);
    text-align center;
    transition all .5s;
    z-index 82;
    &:hover{
        background-color #ebf6ff;
        color #08f;
    }
    &.show{
        right:1100px;
        opacity 1;
    }
}

</style>