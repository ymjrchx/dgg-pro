<template>
    <div>
        <div class='draw' :class="{'closeDraw':showDraw}">
            <div>
                <h2>高级筛选条件组</h2>
                <div class="tabContent">
                    <el-tabs style="height: 200px;">
                        <el-tab-pane label="行业模板">
                            <div class="search">
                                <input type="text" placeholder="请输入条件组名称进行查询" v-model="temKey" @keydown.enter="searchTemplate">
                                <input type="submit" class="btn" value="查询" @click="searchTemplate">
                            </div>
                            <div class="fixed2">
                                <div class="temItem" v-for="(item,idx) in templateArr" :key='idx' >
                                    <p class="cur" @click="showTemArrFunc(idx)">
                                        <i class="el-icon-arrow-right" :class="{deg45:showTemArr[idx]}"></i> 
                                        {{item.applicable_industry_name}}({{item.condition_groups.length}})
                                    </p>
                                    <el-collapse-transition>
                                        <ul class="nameItem" v-show='showTemArr[idx]'>
                                            <li v-for='(item2,idx2) in item.condition_groups' :key='idx2'>
                                                <p class="f14">{{item2.name}}
                                                    <span v-if='item2.info'>
                                                        <el-tooltip class="item" effect="light" :content="item2.info" placement="top-start">
                                                        <i class="el-icon-question col f16"></i>
                                                        </el-tooltip>
                                                    </span>
                                                </p>
                                                <div class="handle">
                                                    <a href="javascript:void(0)" class="col" @click="lookTempaly(idx,idx2,item2.name)">预览</a> |
                                                    <a href="javascript:void(0)" class="col" @click="useTempaly(idx,idx2)">使用</a>
                                                </div>
                                            </li>
                                        </ul>
                                    </el-collapse-transition>
                                </div>
                                <p class="text-center col-h2" v-show='templateArr.length == 0'>暂无相关条件组</p>
                            </div>     
                            
                        </el-tab-pane>
                        <el-tab-pane label="历史保存">
                            <div class="search">
                                <input type="text" placeholder="请输入条件组名称进行查询" v-model="historyKey" @keydown.enter="searchhistory">
                                <input type="submit" class="btn" value="查询" @click="searchhistory">
                            </div>
                            <!-- <div style="margin:20px 10px 0 0;cursor:pointer" class="col text-right" @click="refreh">刷新 </div> -->
                            <el-row  v-loading="isLoading">
                                <ul class="nameItem fixed2" v-if='historyArr.length > 0'>
                                    <li v-for='(item,idx) in historyArr' :key='idx'>
                                        <span class="el-icon-close icon" title="删除" @click="delHistory(item.id)"></span>
                                        <p class="f14">{{item.name}}</p>
                                        <p class="col-h2">保存时间：{{item.createTime}}</p>
                                        <div class="handle">
                                            <a href="javascript:void(0)" class="col" @click="lookHistory(idx,item.name)">预览</a> |
                                            <a href="javascript:void(0)" class="col" @click="useHIstory(idx)">使用</a>
                                        </div>
                                    </li>
                                </ul>
                                <p class="text-center col-h2" v-else>暂无历史条件组</p>
                            </el-row>    
                            
                        </el-tab-pane>
                    </el-tabs>
                </div>
            </div>
        </div>
        <a href="javascript:void(0)" class="drawBtn" :class="{'drawBtnOpen':showDraw}" @click="showDraw = !showDraw">
            <div class="_3LySR">
                <i class="anticon anticon-menu-unfold">
                    <svg v-show='!showDraw' viewBox="64 64 896 896" class="" data-icon="menu-unfold" width="1em" height="1em" fill="currentColor" aria-hidden="true"><path d="M408 442h480c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8H408c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8zm-8 204c0 4.4 3.6 8 8 8h480c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8H408c-4.4 0-8 3.6-8 8v56zm504-486H120c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8h784c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8zm0 632H120c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8h784c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8zM142.4 642.1L298.7 519a8.84 8.84 0 0 0 0-13.9L142.4 381.9c-5.8-4.6-14.4-.5-14.4 6.9v246.3a8.9 8.9 0 0 0 14.4 7z"></path></svg>
                    <svg v-show='showDraw' viewBox="64 64 896 896" class="" data-icon="menu-fold" width="1em" height="1em" fill="currentColor" aria-hidden="true"><path d="M408 442h480c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8H408c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8zm-8 204c0 4.4 3.6 8 8 8h480c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8H408c-4.4 0-8 3.6-8 8v56zm504-486H120c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8h784c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8zm0 632H120c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8h784c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8zM115.4 518.9L271.7 642c5.8 4.6 14.4.5 14.4-6.9V388.9c0-7.4-8.5-11.5-14.4-6.9L115.4 505.1a8.74 8.74 0 0 0 0 13.8z"></path></svg>
                </i>
                <div>条</div><div>件</div><div>组</div>
            </div>
        </a>
        <div v-if='showPreview'>
             <previewEle  @changeSHow='changeSHow' :nameStr='showName' :json='showArr'></previewEle>
        </div>
    </div>
</template>
<script>
import previewEle from '@/components/preview'
export default {
    props:{
        isChange:{
            default:""
        }
    },
    components: {
      previewEle
    },
    data() {
      return {
        showDraw:true,
        allHistoryArr:[],
        historyArr:[],
        isLoading:true,
        historyKey:"",
        showPreview:false,
        showArr:[],
        showName:"",
        allTemplateArr:[],
        templateArr:[],
        temKey:"",
        showTemArr:[]
      }
    },
    created(){
        this.getHistory();
        this.getTemplate()
    },
    watch: {
        showPreview(bool){
            if(bool){
                document.getElementsByTagName("body")[0].style.cssText =
                "overflow:hidden";
            }else{
                  document.getElementsByTagName("body")[0].style.cssText =
                "overflow:auto";
            }
        },
        isChange(){
            this.refreh()
        }
    },
    mounted(){
        // setTimeout(()=>{
        //     this.showDraw = true
        // },2000)
    },
    methods: {
        getTemplate(){
            this.$Api('condition/recommend.do',{},'get',0,1).then(res =>{
                this.allTemplateArr = this.templateArr = res.data
                // this.isLoading = false;
            })
        },
        getHistory(){
            this.isLoading = true;
            this.$Api('condition/conditionGroups.do',{},'get',0,1).then(res =>{
                this.allHistoryArr = this.historyArr = res.data
                this.isLoading = false;
            })
        },
        delHistory(id){
            this.$confirm('是否删除此条件组?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$Api('condition/deleteConditionGroups.do',{id:id},'post',0,1).then(res =>{
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.getHistory()
                    })
                }).catch(() => {
                });
        },
        refreh(){
            this.historyKey =''
            this.getHistory()
        },
        searchhistory(){
            if(!this.historyKey){
                this.historyArr = this.allHistoryArr 
                return;
            };
            let newArr = [];
            for(let item of this.allHistoryArr){
                if(item.name.indexOf(this.historyKey) != -1){
                    newArr.push(item)
                }
            }
            this.historyArr = newArr
        },
        searchTemplate(){
            this.showTemArr =[]
            if(!this.temKey){
                this.templateArr = this.allTemplateArr 
                return;
            };
            let newArr = [];
            for(let item of this.allTemplateArr){
                let obj={
                    applicable_industry_name:item.applicable_industry_name,
                    condition_groups:[],
                }
                for(let item2 of item.condition_groups){
                    if(item2.name.indexOf(this.temKey) != -1){
                        obj.condition_groups.push(item2)
                    }
                }
                if(obj.condition_groups.length > 0) newArr.push(obj)
            }
            this.templateArr = newArr
        },
        lookHistory(idx,name){
          
            this.showArr = JSON.parse(this.historyArr[idx].filter)
            this.showName = name
            this.showPreview = true
         
        },
        lookTempaly(idx1,idx2,name){
          
            this.showArr = JSON.parse(this.templateArr[idx1].condition_groups[idx2].filter)
            this.showName = name
            this.showPreview = true
         
        },
        useHIstory(idx){
            let json = JSON.parse(this.historyArr[idx].filter)
            
            //   this.$store.commit('SET_FILTER',{must:[{}]})
            // setTimeout(()=>{
                this.$store.commit('SET_FILTER',json)
            // },2000)
            
        },
        useTempaly(idx1,idx2){
            let json =  JSON.parse(this.templateArr[idx1].condition_groups[idx2].filter)
            this.$store.commit('SET_FILTER',json)
        },
        changeSHow(bool){
            this.showPreview =false
        },
        showTemArrFunc(idx){
     
            let arr = [...this.showTemArr]
            arr[idx] = arr[idx] ? false:true;
            this.showTemArr = arr;
        }
    }
};
</script>
<style scoped lang="stylus">
// body{
//     overflow-x hidden
// }
.draw{
    position fixed;
    right 0;
    top 0;
    width 420px;
    height 100%;
    background #fff;
    -webkit-box-shadow: 0 2px 12px 0 rgba(90,143,203,.48);
    box-shadow: 0 2px 12px 0 rgba(90,143,203,.48);
    z-index 120
    transition all .4s
    &.closeDraw{
         width 0px;
         overflow hidden
    }
    >div{
        width 100%
        background: #fff;
        color: #5a5a5a;
        h2{
            padding: 16px 24px;
            border-radius: 3px 3px 0 0;
            border-bottom: 1px solid #e8e8e8;
            font-size: 15px;
            line-height: 22px;
            font-weight: 500;
            color: rgba(0,0,0,.85);
        }
        .tabContent{
            padding 24px;
            .search{
                overflow hidden;
                border-radius 2px;
                input{
                    border: 1px solid #dcdfe6;
                    height 34px;
                    float left;
                    padding 0 8px;
                    width calc(100% - 60px);
                    &.btn{
                        border:none;
                        width 60px;
                        text-align center;
                        border-radius 0 2px 2px 0;
                    }
                }
            }
            .fixed2{
                position: fixed;
                width: 372px;
                overflow-y: auto;
                height: calc(100% - 210px);
                margin-top: 30px;
            }
            .nameItem{
                margin-top 20px;
                li{
                    font-size 13px;
                    background: #f9f9f9;
                    margin-bottom: 8px;
                    padding: 16px;
                    position: relative;
                    transition all .2s;
                    .icon{
                        position absolute;
                        right:10px;
                        font-size 16px;
                        top:10px;
                        cursor pointer
                    }
                    .handle{
                        position absolute;
                        right:10px;
                        bottom:16px;
                    }
                    p{
                        margin-bottom 5px;
                    }
                    &:hover{
                        background: #eee;
                    }
                }
            }
            .temItem{
                >p{
                    padding: 10px 0 16px 0px;
                    i{
                        transition all .2s;
                        &.deg45{
                            transform: rotate(90deg);
                            -webkit-transform : rotate(90deg);
                        }  
                    }
                }
               
                .nameItem{
                    padding-left 18px;
                    margin-top: 0px
                    p{
                        margin 0
                    }
                }
            }
        }
    }
}
.drawBtn{
    position: fixed;
    right: 420px;
    top: 175px;
    width: 34px;
    height: 110px;
    cursor: pointer;
    background-color: #fff;
    border-radius: 5px 0 0 5px;
    -webkit-box-shadow: -4px 2px 8px 0 rgba(90,143,203,.48);
    box-shadow: -4px 2px 8px 0 rgba(90,143,203,.48);
    text-align center;
    padding-top 14px;
    color  #08f;
    transition all .4s;
    z-index 130;
    &.drawBtnOpen{
        background-color #08f;
        color #fff;
        right: 0px;
    }
}

</style>