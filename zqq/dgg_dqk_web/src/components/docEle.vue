<template>
  <div class='docEle'>
    <div>
      <span>满足下列</span>
      <div style="width:74px;display:inline-block;margin:0 8px">
        <el-select v-model="value" size="small">
          <el-option v-for="item in options" :key="item.value" :label="item.name" :value="item.value">
          </el-option>
        </el-select>
      </div>
      <span>条件:</span>  <i v-if='docIdx != "fr" && !isPreview' class="el-icon-delete iconDel" title="删除" @click="deleFunc"></i>
    </div>
    <div class="eleList" >
      <div class="line3" v-if='docArrChild.length > 0'></div>
      <template v-for='(item,idx) in  docArrChild'>
         
         <conditionBox v-if='item' :isLast='(idx == (docArrChild.length-1)) && isFaHas ' :key='idx' :idxT = "docIdx+'_'+idx" :isPreview='isPreview'
         :condiType='condiType' :condiTypeChild='condiTypeChild' :eleJson='item' :filterJson='filterJson'></conditionBox>
      </template>
    </div>
    <div class="addBtn" v-if='!firBox && !isPreview'>
      <el-button icon="el-icon-plus" size='small' @click.stop="addMoreCon(0)">增加条件</el-button>
      <!-- <span class="more" v-if='0' @mouseenter="showMoreCon = true" @mouseleave="showMoreCon = false">...</span> -->
      <ul v-show='showMoreCon' @mouseenter="showMoreCon = true" @mouseleave="showMoreCon = false">
        <li @click.stop="showMoreCon = false">添加单个条件</li>
        <li @click.stop="showMoreCon = false">添加组合条件</li>
      </ul>
    </div>
  </div>
</template>
<script>
  import conditionBox from '@/components/conditionBox'
  export default {
    components: {
      conditionBox
    },
    props: {
      firBox:{
        default: false
      },
      filterJson:{  //所有的筛选条件
        default: {},
      },
      docIdx:{
        default:'fr'   //确定是哪一个二级分类  fr代表全部的首个  其余为idx
      },
      condiType:{
        default: "must",   //二选一  全部还是任意
      },
      condiTypeChild:{
        default: "must",   //二级key
      },
      isPreview:{
        default:false
      }
    },
    data() {
      return {
        selectedArr: [],
        value:'should',
        options: [{
          value: "must",
          name: "所有"
        }, {
          value: "should",
          name: "任意"
        }],
        showMoreCon:false,
        docArrChild:[],
        isFaHas:true,
      }
    },
    watch: {
      value(val){
          if(this.isPreview) return
          let filter = JSON.parse( JSON.stringify( this.filterJson))
          if(this.docIdx == 'fr'){
            let obj={};
                obj[val] = filter[this.condiType]
            this.$store.commit('SET_FILTER',obj)
          }else{
              let obj={};
                  obj[val] = filter[this.condiType][this.docIdx][this.condiTypeChild]
                  filter[this.condiType][this.docIdx]= obj

                this.$store.commit('SET_FILTER',filter)
          }
      },
      filterJson(data){
          
          this.fullChild(data)
         
      }
    },
    computed:{
      // docArrChild(){
      //     // SET_FILTER
      //     let allJson = this.filterJson
  
      //     if(this.docIdx == 'fr'){
      //       let arr=[]
      //       for(let item of allJson[this.condiType]){
      //           if(!item.must && !item.should){
      //               arr.push(item)
      //           }
      //       }
      //       return arr
      //     }
      //     else{
      //       return allJson[this.condiType][Number(this.docIdx)][this.condiTypeChild]
      //     }
      // },
      // isFaHas(){   //是首个，并且不含下一级 must或should
      //    let allJson = this.filterJson,
      //        bool = true ;
      //     if(this.docIdx == 'fr'){
      //       for(let item of allJson[this.condiType]){
      //           if(item.must || item.should){
      //              bool=false
      //           }
      //       }
      //     }
      //     return bool
      // }
    },
    mounted(){
      if(this.isPreview){
         this.fullChild(this.filterJson)
      }
      // setTimeout(()=>{
      //   if(this.isPreview)
      //     console.log(333,JSON.stringify(this.docArrChild))
      // },1000)
    },
    created(){
      this.value = this.condiType
    },
    methods: {
      deleFunc(){
        let arr = JSON.parse(JSON.stringify(this.filterJson))
        arr[this.condiType].splice(this.docIdx,1)

        this.$store.commit('SET_FILTER',arr)
      },
      addMoreCon(){
       let arr = JSON.parse(JSON.stringify(this.filterJson))
        arr[this.condiType][this.docIdx][this.condiTypeChild].push({})
       
        this.$store.commit('SET_FILTER',arr)
      },
      fullChild(data){
         this.isFaHas = true
          if(this.docIdx == 'fr'){
            let arr=[]
            for(let item of data[this.condiType]){
                if(!item.must && !item.should){
                    arr.push(item)
                    
                }else{
                  arr.push(false)
                  this.isFaHas=false
                }
             
            }
            this.docArrChild =  arr
          }
          else{
            this.docArrChild = data[this.condiType][Number(this.docIdx)][this.condiTypeChild]
          }
          // console.log(1123,this.docArrChild)
      }
    }
  };
</script>
<style scoped lang="stylus">
.docEle
  .addBtn
    margin: 30px 62px 10px; 
    position:relative;
    width:126px
    .more
      position:absolute;
      display inline-block
      width 32px;
      height 32px;
      border-radius:0 4px 4px 0;
      border: 1px solid #dcdfe6;
      border-left :none;
      margin-left:-2px;
      text-align:center;
      line-height:24px;
      cursor pointer
      &:hover 
        border-color: #409EFF;
        color: #409EFF;
    ul
      position:absolute;
      width:100%;
      line-height:2em;
      outline: none;
      list-style-type: none;
      padding: 4px 0;
      margin: 0;
      text-align: left;
      background-color: #fff;
      border-radius: 3px;
      box-shadow: 0 2px 8px rgba(0,0,0,.15);
      background-clip: padding-box;
      z-index:4
      li
        padding: 5px 12px;
        margin: 0;
        clear: both;
        font-size: 13px;
        font-weight: 400;
        color: #5a5a5a;
        white-space: nowrap;
        cursor: pointer;
        transition: all .3s;
        line-height: 22px;
        &:hover
          background-color: #ebf6ff;
  .eleList
    .line3
      height: 20px;
      margin-left: 20px;
      border-left: 1px solid #e2e2e2;
.iconDel{
    font-size:20px;
    color:#9e9e9e;
    margin-left:16px;
    cursor:pointer;
    margin-top:8px;
    &:hover{
       color:#006cd9;
    }
  }
</style>