<template>
  <div class='docBox'>
      <input type="text" v-model="val" readonly @click="next = !next" @mouseleave=" next = false" placeholder="请选择筛选条件">
      <i class="el-icon-arrow-down" :class="{'show':next}"></i>
        <el-collapse-transition>
            <div class="box" v-show="next" @mouseenter=" next = true " @mouseleave=" next = false ">
                <ul class="box-l">
                    <li :class="{'active':showItem == 0}" @mouseenter="showItem = 0">热门</li>
                    <li v-for="(item,idx) in allDocJson.categories" :key=" idx" 
                        :class="{'active':showItem == (idx+1)}" @mouseenter="showItem = (idx+1)">
                        {{item.name}}
                    </li>
                </ul>
                <ul class="box-r">
                    <li v-for="(item,idx) in leftBoxArr" :data-value='item' :class="{un:returnChi(item).status != 1}" 
                        :key='idx' @click.stop="selectFunc(item,returnChi(item).status)" >{{returnChi(item).label}}</li>
                </ul>
            </div>
        </el-collapse-transition>
  </div>
</template>
<script>
  export default {
    props: {
      nameStr:{
        default:''
      }
    },
    data() {
      return {
        next:false,
        val:"",
        showItem:0,
        selectKey:"",
        selectObj:""
      }
    },
    computed:{
        leftBoxArr(){
            let arr=[]
            if(this.showItem == 0){
                arr = this.allDocJson.hot_list
            }
            else{
                arr = this.allDocJson.categories[this.showItem-1].dimensions
            }
            return arr
        },
        allDocJson(){
          return this.$store.state.conditionConfig
        }
    },
    mounted(){
        // console.log('新json',this.nameStr)
      if(this.nameStr){
          this.selectFunc(this.nameStr)
      }
    },
    watch: {
      nameStr(str){
        this.selectFunc(this.nameStr)
      }
    },
    methods: {
        returnChi(key){
            let str='暂无'
           
            for(let item of this.allDocJson.filter_options){
                if(item.value == key){
                    // this.selectObj = item
                    str =  item
                    break;
                }
            }
            return str
        },
        selectFunc(key,status = 1){
            if(status != 1) return;
            if(!key){
                this.val = '';
                this.$emit('change',false);
                return;
            } 
             this.allDocJson.filter_options.map((item,idx)=>{
                if(item.value == key){
                    this.val = item.label;
      
                    this.$emit('change',item)
                    this.next= false
                    return;
                }
            })
        }
    }
  };
</script>
<style lang="stylus" scoped >
.docBox{
    position: relative;
    display: inline-block;
    cursor:pointer;
    width:170px;
    vertical-align: top;
    >i{
        position:absolute;
        right:10px;
        top:11px;
        transition: all .2s;
        -webkit-transition : all .2s;
        &.show{
          transform: rotate(180deg);
          -webkit-transform : rotate(180deg);
        }
    }
    >input{
        display: inline-block;
        padding: 4px 11px;
        width: 100%;
        height: 36px;
        font-size: 13px;
        line-height: 1.5;
        color: #5a5a5a;
        background-color: #fff;
        background-image: none;
        border: 1px solid #d9d9d9;
        border-radius: 3px;
        transition: all .3s;
        cursor:pointer;
    }
    .box{
        position:absolute;
        outline: none;
        list-style-type: none;
        padding:0;
        margin: 0;
        text-align: left;
        background-color: #fff;
        border-radius: 3px;
        box-shadow: 0 2px 8px rgba(0,0,0,.15);
        background-clip: padding-box;
        width:700px;
        z-index:20;
        .box-l{
            float:left;
            width:110px;
            min-height:300px;
            background-color: #f7f7f7;
            li{
                overflow: hidden;
                text-overflow: ellipsis;
                padding: 0;
                height: 40px;
                line-height: 40px;
                text-align: center;
                transition:all .2s
                &.active{
                    background-color: #fff;
                    font-weight: 400;
                    color: #08f;
                }
            }
        }
        .box-r{
            float:left;
            width:590px;
            padding:12px;
            li{
                display: inline-block;
                color: #484848;
                width: 120px;
                height: 40px;
                border: 1px solid #e9e9e9;
                margin: 8px;
                border-radius: 3px;
                padding: 0 4px;
                line-height: 38px;
                font-size: 12px;
                overflow: hidden;
                vertical-align: middle;
                transition:all .2s;
                text-align:center
                &:hover{
                    border: 1px solid #08f;
                    color: #08f;
                }
                &.un{
                    background #e9e9e9;
                    cursor:not-allowed;
                    &:hover{
                        border: 1px solid #e9e9e9;
                        color: #484848;
                    }
                }
            }

        }
    }
}
</style>