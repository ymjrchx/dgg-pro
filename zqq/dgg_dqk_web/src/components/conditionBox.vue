<template>
  <div class='box' :class="{'isLast':isLast}">
    <span class="line1"></span>
    <span class="line2"></span>
    <allContidion @change = 'selectType' :nameStr='nameStr'></allContidion>
    <div class="action" v-if='actionType && actionType != 6'>
        <el-select v-model="actionKey" placeholder="请选择"  size='medium'>
          <el-option
            v-for="item in actionOptions"
            :key="item.value"
            :label="item.label || item.labelName"
            :value="item.value">
          </el-option>
        </el-select>
    </div>
    <!-- 2222  选择地址，行业 -->
    <div class="action mul" v-if="inputType == 2 ">    
        <areaSelect  v-model="inputKey"  :arr='inputOptions'></areaSelect>
    </div>
    <!-- 333  可输入多个词 -->
    <div class="action input jun_in" v-if='inputType == 3'>
        <el-select size='medium'
          v-model="inputKey"
          multiple
          filterable
          allow-create
          clearable
          default-first-option
          @change='getInputVal'
          @clear='clearValue'
          no-data-text='输入后，按回车键进行确认'
          placeholder="请输入">
          <el-option
            v-for="item in inputOptions"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
    </div>
    <!-- 444   选择框 -->
    <div class="action input" v-if='inputType == 4'>
        <el-select v-model="inputKey" size='medium' placeholder="请选择" multiple clearable   @clear='clearValue'>
           <el-option
            v-for="item in inputOptions"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
    </div>
    <!-- 555   数字输入范围框 -->
    <div class="action mul" v-if='inputType == 5'> 
         <count  v-model="inputKey"></count>
    </div>
    <!-- 666 时间输入范围框 -->
    <div class="action mul" v-if='inputType == 6'>
        <timePicker  v-model="inputKey"></timePicker>
    </div>
    <label style="margin-left:10px" v-if='unitStr'>{{unitStr}}</label>
    <i class="el-icon-delete iconDel"  v-if='!isPreview' title="删除" @click="deleFunc"></i>
  </div>
</template>
<script>
  import allContidion from '@/components/allContidion'
  import count from '@/components/count'
  import areaSelect from '@/components/areaSelect'
  import timePicker from '@/components/timePicker'
  export default {
    components: {
      allContidion,count,areaSelect,timePicker
    },
    props: {
      isLast:{
        default:false
      },
      idxT:{
        default:""
      },
      condiType:{
        default: "must",   //二选一  全部还是任意
      },
      condiTypeChild:{
        default: "must",   //二级key
      },
      eleJson:{            //每一项
         default: "",
      },
      filterJson:{  //所有的筛选条件
        default: {},
      },
      isPreview:{
        default:false
      }
    },
    data() {
      return {
        key:"",
        actionType:"", 
        actionOptions:[],
        actionKey:"",

        inputType:"",
        inputOptions:[],
        inputKey:[],
        unitStr:"",
        noWatch:""
        // nameStr:""
      }
    },
    computed:{
        allDocJson(){
          return this.$store.state.conditionConfig
        },
        nameStr(){
          if(this.eleJson){
            for(let key in this.eleJson){
                return key
            }
          }
          return ""
        }
    },
    watch:{
      inputKey(val){
        if(this.isPreview || this.noWatch) return
        this.produceObj()
      },
      actionKey(val){
        if(this.isPreview || this.noWatch) return
        this.produceObj()
      },
      eleJson(arr){
        // console.log("每一项",arr)
        this.noWatch = true
        setTimeout(()=>{
          this.noWatch = false
        },200)
        setTimeout(()=>{
            for(let key in arr){
                for(let key2 in arr[key]){
                    if(key2){
                      this.actionKey = key2
                      this.inputKey = arr[key][key2]
                    }
                }
            }
        },100)
      }
    },
    mounted(){
      if(this.eleJson){

        for(let key in this.eleJson){
        
            for(let key2 in this.eleJson[key]){
                if(key2){
                      this.actionKey = key2
                  // console.log(1245,this.eleJson[key][key2])
                  this.inputKey = this.eleJson[key][key2]
                }
            }
        }
      }
    },
   
    methods: {
      selectType(item){
        this.clearAll();
        if(!item) return;
        // console.log('选中的对象',item)
        
        this.key = item.value
        let arr = this.idxT.split('_'),
            idx = Number(arr[1]);

        let newObj={};
            newObj[item.value] = {}
     
        let actionOptions=[];
       
        if(item.action.type == 5){     
            
              this.actionOptions = item.action.options
              this.actionKey = item.action.options[0].value
            
            this.actionType=item.action.type;
            return
        }

        if(item.action.typeValue){
            let valArr =eval('(' + item.action.typeValue + ')')
            for(let key in valArr){
              actionOptions.push({'label':valArr[key],'value':key})
            }
           
              this.actionOptions = actionOptions
              this.actionKey = actionOptions[0].value
            
            this.actionType=item.action.type
        }
        if(item.input && item.input.type){
          if(item.input.optionsFrom){
            this.inputOptions= this.allDocJson.input_values[item.input.optionsFrom]
          } 
          if(item.unit){
            this.unitStr = item.unit
          }
          this.inputType=item.input.type
        }else{
            this.inputKey = ['1']
        }
      },
      produceObj(){
        let newObj={};
            newObj[this.key]={};
            newObj[this.key][this.actionKey]=this.inputKey;
            // console.log(newObj)

          this.joinStore(newObj)
      },
      deleFunc(){
        let arr = this.idxT.split('_'),
            idx = Number(arr[1]),
            filterJson = JSON.parse(JSON.stringify(this.filterJson))

            console.log(11111,this.idxT)
            if(arr[0] == 'fr'){
                filterJson[this.condiType].splice(idx,1)
            }else{
                let len = filterJson[this.condiType][Number(arr[0])][this.condiTypeChild].length
                if(len == 1){
                  filterJson[this.condiType].splice(Number(arr[0]),1)
                }else{
                  filterJson[this.condiType][Number(arr[0])][this.condiTypeChild].splice(idx,1)
                }
                
            }
            this.noWatch = true
            setTimeout(()=>{
              this.noWatch = false
            },200)
            this.$store.commit('SET_FILTER',filterJson)
      },
      getInputVal(e){
       
      },
      clearAll(){
        this.actionType=""
        this.actionOptions=[]
        this.actionKey=""

        this.inputType=""
        this.inputOptions=[]
        this.inputKey=[]
        this.unitStr =''
      },
      clearValue(e){
       
      },
      joinStore(obj){
        let arr = this.idxT.split('_'),
            idx = Number(arr[1]),
            storeJson =this.filterJson;
        if(arr[0] == 'fr'){
            storeJson[this.condiType][idx] = obj
        }else{
            storeJson[this.condiType][Number(arr[0])][this.condiTypeChild][idx] = obj      
        }
        this.$store.commit('SET_FILTER',storeJson)

        // setTimeout(()=>{
        //   console.log(123,JSON.stringify(this.$store.state.filterJson))
        // },1000)
      }
    }
  };
</script>
<style lang="stylus" scoped >
.box{
  position:relative
  margin: 0;
  margin-left: 20px;
  padding: 10px 0;
  border-left:1px solid  #e2e2e2;
  &.isLast{
    border-left:none;
    .line1{
      position: absolute;
      top: 0;
      left: 0;
      height: 32px;
      width: 1px;
      background-color: #e2e2e2;
    }
     .line2{
       width: 41px;
     }
  }
  .line2{
    display: inline-block;
    width: 40px;
    height: 1px;
    // margin: 18px 0;
    background-color: #e2e2e2;
    position relative;
    top -2px
  }
  .iconDel{
    font-size:20px;
    color:#9e9e9e;
    margin-left:20px;
    cursor:pointer;
    margin-top:8px;
    &:hover{
       color:#006cd9;
    }
  }
  .action{
    display:inline-block;
    width: 170px;
    margin-left:16px;
    vertical-align: top;
    &.input{
      width:auto
    }
    &.mul{
      width:auto;
    }
  }
}
</style>