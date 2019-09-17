<template>
    <div class="workBeach">
        <section class="head">
            <div>
                <div class="title">
                    <i class="el-icon-warning col"></i>
                    已选择&nbsp;<span class="col">{{docArr[docKey].length}}</span>&nbsp;个筛选条件
                </div>
                <div class="all" v-if='conditionConfig' v-show="!isShowDoc">
                    <docEle :docIdx="'fr'" :condiType="docKey" :firBox="true" :filterJson='docArr'></docEle>
                    <div class="sec">
                        <template v-if='docArr[docKey].length > 0'>
                            <template v-for='(item,idx) in docArr[docKey]'>
                                <div class="secBox"  v-if="item.must || item.should" :key='idx' >
                                    <span class="line1"></span>
                                    <span class="line2"></span> 
                                    <docEle :docIdx="idx" :condiType="docKey" :filterJson='docArr'
                                        :condiTypeChild=" item.must ? 'must': 'should' " ></docEle>
                                </div>
                            </template>
                        </template>
                    </div>
                    <div class="addBtn">
                        <el-button icon="el-icon-plus" size='small' @click="addMoreCon(0)">增加条件</el-button>
                        <span class="more" @mouseenter="showMoreCon = true" @mouseleave="showMoreCon = false">...</span>
                        <ul v-show='showMoreCon' @mouseenter="showMoreCon = true" @mouseleave="showMoreCon = false">
                            <li @click.stop="addMoreCon(0)">添加单个条件</li>
                            <li @click.stop="addMoreCon(1)">添加组合条件</li>
                        </ul>
                    </div>
                </div>
                <div class="handleBtn" style="margin-top:40px" v-show="!isShowDoc">
                    <el-button type="primary" size='medium' @click="goFiltrate">筛选</el-button>&nbsp;&nbsp;&nbsp;&nbsp;
                    <el-button size='medium' @click="saveFilter">保存条件组</el-button>
                </div>
                <div class="cancel" style="width: 100%;text-align: right; padding-right:4px">
                    <a href="javascript:void(0)" class="col" @click="clearFilterJson">清空</a>
                    <span class="line"></span>
                    <a href="javascript:void(0)" class="col" @click=" isShowDoc = !isShowDoc ">
                        {{isShowDoc ?'展开':"收起"}} <i :class="[isShowDoc ? 'el-icon-arrow-down' : 'el-icon-arrow-up','col']"></i> </a>
                </div>
            </div>
        </section>
        <section class="clear2 content" style="margin-bottom:0" ref="list">
            <div class="part thred">
                <h2 >共有 <span class="col">{{(total > 500 ? "5000+": total) || 0 }}</span> 条销售线索</h2>
                <div class="part_radio" style="margin:4px 0 20px">
                   <!-- <el-dropdown split-button type="primary" size='small'>
                            转线索
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item><span @click="getThread">领取线索</span></el-dropdown-item>
                            <el-dropdown-item>分配线索</el-dropdown-item>
                            <el-dropdown-item>加入公海</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown> -->
                    <el-button type="primary" size='small' @click="getThread">领取线索</el-button>
                </div>
                <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%"
                    @selection-change="handleSelectionChange">
                    <el-table-column type="selection" :selectable='isCanSelect'>
                    </el-table-column>
                    <el-table-column label="企业全称" show-overflow-tooltip >
                        <template slot-scope="scope">
                            <a @click=" faId = scope.row.companyId" href="javascript:void(0)" class="col">
                                {{scope.row.commercial.companyName }}
                            </a>
                        </template>
                    </el-table-column>
                    <el-table-column label="公司地址" show-overflow-tooltip>
                           <template slot-scope="scope">
                            <span>{{scope.row.commercial.registerAddress || '--'}}</span>
                        </template>
                    </el-table-column>
                      <el-table-column label="所属行业" width='250' show-overflow-tooltip>
                        <template slot-scope="scope">
                            <span>{{scope.row.commercial.industry || '--'}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="注册资本"  width='150' align='center'>
                        <template slot-scope="scope">{{scope.row.commercial.registerMoney}} <span class="f12">万人民币</span></template>
                    </el-table-column>
                    <el-table-column  label="联系方式(数量)" width='150' align='center'>
                        <template slot-scope="scope">
                            <span class="text-center" style="display:block">{{countTel(scope.row.commercial.companyTel)}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="cluesTimes" label="领取线索次数" width='150' align='center'>
                    </el-table-column>
                    <el-table-column label="注册时间"  width='200'>
                        <template slot-scope="scope">
                            <span>{{returnTime(scope.row.commercial.registerTime)}}</span>
                        </template>
                    </el-table-column>
                    <!-- <el-table-column prop="address" label="转线索次数" show-overflow-tooltip>
                    </el-table-column> -->
                </el-table>
                <div class="pages" v-if='total > 0'>
                    <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageIndex"
                            :page-sizes="[10, 20, 30, 40,50]" :page-size="pageSize" layout="sizes, prev, pager, next, jumper"
                            :total="total">
                        </el-pagination>
                </div>

                <!-- <nofound/> -->
                <draw :isChange='isChange' v-show='!faId'></draw>
                <companyDraw :faId='faId' @close='cloceCompanyDraw'></companyDraw>
            </div>
        </section>
    </div>
</template>
<script>
    import nofound from '@/components/noFind'
    import img from '@/assets/images/c93fd6c7a8ca3ddcfdfeef24d860ce05.png'
    import docEle from '@/components/docEle'
    import draw from '@/components/draw'
    import Cookies from "js-cookie"
    import companyDraw from '@/components/companyDraw'
    export default {
        components: {
            nofound,
            docEle,draw,companyDraw
        },
        data() {
            return {
                errorImg: `this.src='${img}'`,
                isShowDoc: 0,
                tableData: [],
                pagesIndex: 1,
                conditionNum: 0, //条件数量
                showMoreCon:false,
                conditionConfig:"",
                total:0,
                pageIndex: 1, //第几页
                pageSize:20, //每页多少条
                saveObj:"",
                selectTableMsg:[],
                isChange:true, //draw监听它是否改变而执行刷新
                faId:"" //详情id
            }
        },
        created() {
            
            this.getConfigs()
        },
        computed:{
            docArr(){
                // SET_FILTER
                return this.$store.state.filterJson
            },
            docKey(){
                return this.$store.state.filterJson.must ? 'must' :'should'
            },
        },
        // watch:{
        //     docArr(msg){
        //         console.log(123456,msg)
        //     }
        // },
        mounted(){
           
            window.addEventListener("beforeunload", (event)=> {
                if(Cookies.get('userInfo')){
                   
                     localStorage.setItem('filterJson',JSON.stringify( this.$store.state.filterJson))
                }
               
            });
        },
        methods: {
            getConfigs(){
                // GET /condition/configs.do
                if(this.$store.state.conditionConfig){
                    this.conditionConfig = this.$store.state.conditionConfig
                }else{
                    this.$Api('condition/configs.do',{},'get',true).then(res=>{
                        let conditionConfig = res.data
                        this.conditionConfig = conditionConfig
                        this.$store.commit("SET_CONFIG",conditionConfig)
                    })
                }
               
            },
            addMoreCon(num) {
               
                let obj = num ? {must:[{}]} :{}

                let filter = JSON.parse(JSON.stringify( this.docArr))

                filter[this.docKey].push(obj);
                this.$store.commit('SET_FILTER',filter)

                this.showMoreCon = false
            },
            clearFilterJson(){
                this.$store.commit('SET_FILTER',{must:[]})
            },
            //筛选请求列表*******************************************************
            goFiltrate(){   
                let filterJson = this.docArr;
                //  console.log('筛选',JSON.stringify(filterJson))
                let bool= filterJson[this.docKey].length == 0 ? 1 :0
                
                if(!bool){
                    for(let item of filterJson[this.docKey]){
                        if(item.must || item.should){
                            bool = this.vailInput(item.must || item.should)
                            if(bool){
                                this.$message.warning('请先完善筛选信息！');
                                return 
                            }
                        }
                    }
                  
                }

                if(!bool){
                    bool = this.vailInput(filterJson[this.docKey])
                }
               
                if(bool){
                    this.$message.warning('请先完善筛选信息！');
                    return 
                }
                this.assembleJson()  //整合json
            },
            vailInput(arr){
                for(let i of arr){
                    if(i.hasOwnProperty('must') || i.hasOwnProperty('should')) continue;
                    if(i && JSON.stringify(i) != "{}"){
                        
                        for(let k in i){
                              
                            for(let k2 in i[k]){
                                if(i[k][k2]){
    
                                    if(i[k][k2].length == 0){
                                         return 1
                                    }
                                }
                                else{
                                    return 1
                                }
                            }
                        }
                    }else{
                        return 1
                    }
                }
                return 0
            },
            assembleJson(){
                let filterJson = this.docArr;
                let requireObj={
                        "judge": this.docKey,
                        "param": [],
                        "nexts": [],
                    }
                for(let item of filterJson[this.docKey]){
                    let  ownKey = item.hasOwnProperty('must') ? 'must' : item.hasOwnProperty('should') ? "should" :false
                    if(ownKey){
                       let nextObj={"nJudge": ownKey,"nParams": [] }
                        nextObj.nParams = this.assembleJson2(item[ownKey]);
                        requireObj.nexts.push(nextObj)
                    }
                }
                requireObj.param = this.assembleJson2(filterJson[this.docKey]);
                this.pageIndex = 1
                this.requireGet(requireObj)
            },
            assembleJson2(arr){
                let newArr = []
                for(let i of arr){
                    let newObj={}
                    if(i.hasOwnProperty('must') || i.hasOwnProperty('should')) continue;
                    for(let k in i){
                        for(let k2 in i[k]){
                            newObj.one=k;
                            newObj.two=k2;
                            newObj.three=i[k][k2].join('、');
                        }
                    }
                    newArr.push(newObj)
                }
                return newArr
            },
            requireGet(obj){   //获取table表单的数据**************************************
              
                if(obj){
                    this.saveObj = obj
                }
                
                let require =Object.assign({},{pageSize:this.pageSize,pageIndex:this.pageIndex},this.saveObj)

                //  console.log('请求参数',JSON.stringify(require))
                this.$Api('senior/search',require,'post',true).then(res =>{
                    // console.log('数',res)
                    this.total = res.data.totalCount;
                    this.tableData = res.data.listData;

                    this.$jump(this.$refs.list,-74)  
                })
            },
            countTel(str){
               
                if(str){
                    return (str+"").split(',').length
                }
                else{
                    return 0
                }
            },
            handleSizeChange(val) {
               this.pageSize = val;
               this.pageIndex = 1 ;
               this.requireGet ()
            },
            handleCurrentChange(val) {
                this.pageIndex = val ;
                this.requireGet ()
            },
            //表格的函数
            handleSelectionChange(val) {
               this.selectTableMsg = val
            },
            isCanSelect(row,index){
                let tel = row.commercial.companyTel
                return tel ? tel == '暂无' ?  false :  true : false
            },
            getThread() {
                if(!this.$store.state.userInfo.isBind){
                    this.$message.warning('请先绑定工号！');
                    setTimeout(()=>{
                        this.$store.commit('SET_BINGING',true)
                    },1000)
                    return;
                }
               let len =  this.selectTableMsg.length 

               if(len == 0){
                    this.$message.warning('请先勾选你想领取的线索！');
               }else{

                    let allArr = [];
                        for(let item of this.selectTableMsg){
                            allArr.push(item.companyId)
                        }

                   this.$Api('clueApi/turnClue.do',{cluesJson:JSON.stringify(allArr)},'post',1,1).then(res =>{
                       this.$message.success('领取成功！');
                   })
               }
            },
            //保存条件组
            saveFilter(){
                this.$prompt('请输入条件组名称', '保存条件组', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputPattern: /\S/,
                    inputErrorMessage: '名称不能为空'
                }).then(({ value }) => {

                    let obj={
                        name:value,
                        filter:JSON.stringify( this.$store.state.filterJson)
                    }
                   this.$Api('condition/addConditionGroups.do',obj,'post',1,1).then(res =>{
                       this.isChange = this.isChange  ? false : true
                       this.$message.success('保存成功！');
                   })
                }).catch(() => {
                      
                });
            },
            returnTime(time){
                if(time.indexOf('T') != -1){
                    return time.split('T')[0]
                }
                return time
            },
            cloceCompanyDraw(){
                this.faId = ''
            }
        },
        beforeDestroy(){
            // console.log(JSON.stringify( this.$store.state.filterJson))
            if(Cookies.get('userInfo')){   
                localStorage.setItem('filterJson',JSON.stringify( this.$store.state.filterJson))
            }
            document.removeEventListener('beforeunload',function(){}, false)
        }
    }
</script>

<style lang="stylus" scoped>
    .workBeach {
        .head {
            margin 14px;
            padding 20px;
            background #fff;
            border-radius 2px;

            .title {
                border-color: #cfe9ff;
                background-color: #ebf6ff;
                font-family: \\5FAE\8F6F\96C5\9ED1, Microsoft Yahei, Hiragino Sans GB, tahoma, arial, \\5B8B\4F53;
                font-size: 13px;
                font-variant: tabular-nums;
                line-height: 1.5;
                color: #5a5a5a;
                box-sizing: border-box;
                margin: 0;
                padding: 0;
                list-style: none;
                position: relative;
                padding: 8px 15px 8px 37px;
                border-radius: 3px;
                margin-bottom: 30px;
            }

            .all {
                .sec{
                    .secBox:last-child{
                         border-left:none;
                        .line1{
                            width: 1px;
                        }
                    }
                    
                }
                .secBox{
                    padding 20px;
                    margin-left 20px;
                    position relative;
                    padding-left: 40px;
                    border-left:1px solid  #e2e2e2;
                    .line1{
                        position: absolute;
                        top: 0;
                        left: 0;
                        height: 34px;
                        width: 0px;
                        background-color: #e2e2e2;
                    }
                    .line2{
                        display: inline-block;
                        width: 40px;
                        height: 1px;
                        background-color: #e2e2e2;
                        position absolute;
                        top: 34px;
                        left: 0;
                    }
                }
                .addBtn{
                    margin: 30px 62px 10px; 
                    position:relative;
                    width:126px;
                    .more{
                        position:absolute;
                        display inline-block
                        width 32px;
                        height 32px;
                        border-radius:0 4px 4px 0;
                        border: 1px solid #dcdfe6;
                        border-left:none;
                        margin-left:-2px;
                        text-align:center;
                        line-height:24px;
                        cursor pointer   
                    }
                    &:hover{
                        border-color: #409EFF;
                        color: #409EFF;
                    } 
                    ul{
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
                        li{
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
                            &:hover{
                                 background-color: #ebf6ff;
                            }
                        }
                    }
                }
            }
            .cancel {
                .line {
                    width: 1px;
                    height: 12px;
                    margin: 0 10px;
                    background-color: #e9e9e9;
                    display: inline-block;
                    vertical-align: middle;
                }
            }
        }

        .content {
            margin 14px;

            .part {
                background #fff;
                padding 20px;

                >h2 {
                    font-size 15px;
                    margin-bottom 15px;
                    font-weight 700;
                }
            }

            //线索状态
            .thred {
                width 100%;
                min-height 510px;
                .thredList {
                    width 100%;

                    td {
                        border-bottom: 1px solid #e9e9e9;
                        padding: 20px 0;

                        img {
                            width: 40px;
                            height: 40px;
                            margin-top: 3px;
                            border-radius: 50%;
                            vertical-align: middle;
                        }
                    }

                    .imgTd {
                        width 56px;
                    }
                }
            }
        }
    }
</style>