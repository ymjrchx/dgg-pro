<template>
    <li class="tree" @mouseenter="isShow = true" @mouseleave="isShow = false">
        <div class="item title antion" @click="showFunc(itemData.url,itemData.child.length)" :class="{'active':faActive}">
            <el-tooltip class="item" effect="dark" :content="itemData.name" placement="top" :enterable='false'>
              <i class="iconfont mr10" v-html="itemData.icon"></i>
            </el-tooltip>
        </div>
        <transition name="el-fade-in">
            <ul v-show='isShow' v-if='itemData.child.length>0' class="antion shadow">
                <li class="item item_child" @click="showFunc(item.url,0)" v-for='(item,idx) in  itemData.child' :key="idx" :class="{'active':selectedStr == item.url}">  
                    <span>{{item.name}}</span> 
                </li>
            </ul>
       </transition>
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
        isShow: false, //右边展开
      }
    },
    computed:{
        faActive(){
            if(this.selectedStr){
                if(this.itemData.url == this.selectedStr) return true

                for(let val of this.itemData.child){
                    if(val.url == this.selectedStr ){
                        return true
                    }
                }
            }
            return false
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
    position relative
    .item{
        line-height: 40px;
        text-overflow: ellipsis;
        font-size 13px;
        color #777;
        cursor pointer;
    }
    .title{
        padding-left: 20px;
       
       i{
            font-size: 20px;
       }
    }
    .item:hover{
        color #08f;
    }
    .item.active{
        background-color: #ebf6ff;
        color #08f;
        i{
            color #08f;
        }
    }
    >ul{
        position absolute;
        left calc(100% + 4px);
        top 0;
        background #fff;
        border-radius 2px;
        text-align left;
        min-width 80px; 
        z-index 10;
        padding 6px 0px;
        overflow hidden;
        li{
            display block;
            white-space:nowrap;
            padding 0 6px;
        }        
    }
}
 
    
</style>