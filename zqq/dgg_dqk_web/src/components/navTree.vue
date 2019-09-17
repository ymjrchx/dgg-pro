<template>
    <li class="tree">
        <div class="item title antion" @click="showFunc(itemData.url,itemData.child.length)" :class="{'active':selectedStr == itemData.url}">
            <i class="iconfont mr10" v-html="itemData.icon"></i> <span class="f14">{{itemData.name}}</span>
            <i class="iconfont arrow antion" :class="{'top':isShow}" v-if='itemData.child.length>0'>&#xe7ac;</i>
        </div>
        <el-collapse-transition >
            <ul v-show='isShow' v-if='itemData.child.length>0'>
                <li class="item item_child antion" @click="showFunc(item.url,0)" v-for='(item,idx) in  itemData.child' :key="idx" :class="{'active':selectedStr == item.url}">  
                    <span>{{item.name}}</span> 
                </li>
            </ul>
        </el-collapse-transition>
    </li>
</template>
<script>

export default {
    props:{
        itemData:{},
        selectedStr:""
    },
    data(){
      return{
        isShow: true, //右边展开
      }
    },
    created(){
        if(this.itemData.child){
            for(let item of this.itemData.child){
                
                if(this.selectedStr == item.url){
                    this.isShow = true;
                    break;
                }
            }
        }
    },
    methods:{
        showFunc(str,len){
           if(len>0){
               this.isShow = this.isShow ? false :true;
           }
           else{
            //    this.$emit('checkFunc',str)
                this.$router.push(str)
           }
        }
    }
}
</script>

<style lang="stylus" scoped>
.tree{
    .item{
        line-height: 40px;
        text-overflow: ellipsis;
        font-size 13px;
        color #777;
        cursor pointer;
       
    }
    .title{
        padding-left: 24px;
        font-size: 14px;
    }
    .item_child{
        padding-left: 50px;
    }
    .item:hover{
        color #08f;
    }
    .item.active{
        background-color: #ebf6ff;
        color #08f;
        border-right 2px solid #08f;
    }
    .arrow{
        float right;
        padding-right 10px;
       
        font-size 13px;
        
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



}
 
    
</style>