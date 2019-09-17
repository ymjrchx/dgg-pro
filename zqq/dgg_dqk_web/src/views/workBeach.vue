<template>
    <div class="workBeach">
        <section class="head">
            <table class="title">
                <tbody>
                    <tr>
                        <td class="select_name">统计范围：</td>
                        <td class="contidition">
                            <div style="display:inline-block;width:150px;">
                                <el-select v-model="departent" size='small'>
                                    <el-option v-for="item in departentArr" :key="item.value"  :label="item.label" :value="item.value">
                                    </el-option>
                                </el-select>
                            </div>
                            <i class="mr10"></i>
                            <div style="display:inline-block;width:150px;" v-if='0'>
                                <el-select v-model="comPany" size='small'>
                                    <el-option v-for="item in comPanyArr" :key="item.value"  :label="item.label" :value="item.value">
                                    </el-option>
                                </el-select>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="select_name">时间：</td>
                        <td class="contidition">
                            <div class="timeEle">
                                <div @click="chooseTimeFunc('本周')" :class="{'active':chooseTime == '本周'}" class="antion">本周</div>
                                <div @click="chooseTimeFunc('本月')" :class="{'active':chooseTime == '本月'}" class="antion sec">本月</div>
                            </div>
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
                        </td>
                    </tr>
                </tbody>
            </table>
        </section>
        <section class="content">
            <div class="part report">
                <h2>销售简报</h2>
                <ul>
                    <li>
                        <h3>线索状态</h3>
                        <div class="numBox" v-if='sellReport.length > 0'>
                            <div v-for='(item,idx) in sellReport' :key='idx'> 
                                <p>{{item.key}}</p>
                                <div class="num">
                                    {{item.val}}
                                </div>
                            </div>
                        </div>
                        <div class="numBox" v-else>
                            <div> 
                                <p>已成交</p>
                                <div class="num">
                                    0
                                </div>
                            </div>
                            <div>
                                <p>有意向</p>
                                <div class="num">
                                  0
                                </div>
                            </div>
                            <div>
                                <p>未确认</p>
                                <div class="num">
                                   0
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <h3>新增线索</h3>
                        <div class="numBox">
                            <div>
                                <p>转线索（领取）</p>
                                <div class="num">
                                    {{addReport  ? addReport.val : 0}}
                                </div>
                            </div>
                            <div>
                                <p>转线索（分配）</p>
                                <div class="num">
                                    0
                                </div>
                            </div>
                            <div>
                                <p>转线索</p>
                                <div class="num">
                                  0
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <h3>线索跟进</h3>
                        <div class="numBox">
                            <div>
                                <p>跟进线索</p>
                                <div class="num">
                                    0
                                </div>
                            </div>
                            <div>
                                <p>写跟进次数</p>
                                <div class="num">
                                  0
                                </div>
                            </div>
                            <div>
                                <p>拜访次数</p>
                                <div class="num">
                                   0
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </section>
        <section class="clear2 content" style="margin-bottom:0">
            <div class="part thred">
                <h2>线索动态</h2>
                <!-- <div class="part_radio">
                    <el-radio-group v-model="thred" size="small" class="Jun">
                        <el-radio-button label="已分配"></el-radio-button>
                    </el-radio-group>
                </div> -->
                <el-row :v-loading="tableLoading">
                    <table class="thredList" v-if='total > 0'>
                        <tbody>
                            <tr v-for='(item,idx) in dynamicArr' :key='idx'>
                                <td class="imgTd">
                                    <img src="" :onerror='errorImg' alt="">
                                </td>
                                <td>
                                    <p>{{returnTime(item.statusUpdateTime)}}</p>
                                    <div class="mt5">
                                        <span class="col-b"> {{$store.state.userInfo.username}} </span> 
                                        <span class="col-b"> {{item.toWhere}}</span>
                                         了线索
                                        <span class="col">{{item.comName}}</span>
                                        <!-- <span> 状态：<i class="col">{{item.status}}</i></span> -->
                                        <template v-if='item.reason'>
                                            原因: <span class="col-b">{{item.reason}}</span>
                                        </template>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div v-else>
                        <nofound/>
                    </div>
                </el-row>    
                <div style="text-align:right;padding:40px 20px 0 20px;">
                     <el-pagination v-show='total > 0'
                        background 
                        @current-change="handleCurrentChange"
                        layout="prev, pager, next"
                        :total="total">
                    </el-pagination>
                </div>
            </div>
            <div class="sellList">
                <div class="part">
                    <h2>销售排行榜</h2>
                    <div class="part_radio">
                        <el-radio-group v-model="sellListKey" size="mini" class="Jun">
                            <el-radio-button label="已成交"></el-radio-button>
                            <el-radio-button label="有意向"></el-radio-button>
                        </el-radio-group>
                    </div>
                    <el-row :v-loading="listLoading">
                        <table class="sellList_tab">
                            <thead>
                                <th>排名</th>
                                <th>销售</th>
                                <th>单数</th>
                                <th width='40%'></th>
                            </thead>
                            <tbody id='sellList'>
                                <template v-for='(item,idx) in sellListArr'>
                                    <tr  :key='idx'  v-if='item.val > 0'>
                                        <td>
                                            {{idx+1}}
                                        </td>
                                        <td>
                                            {{item.key}}
                                        </td>
                                        <td>
                                            {{item.val}}
                                        </td>
                                        <td></td>
                                    </tr>
                                </template>
                               
                                <tr v-show='sellListArr.length == 0'>
                                    <td colspan="4" class="text-center">
                                        暂无数据
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </el-row>    
                </div>
            </div>
        </section>
    </div>
</template>
<script>
    // import junSelect from '@/components/jun_select'
    import nofound from '@/components/noFind'
    import img from '@/assets/images/c93fd6c7a8ca3ddcfdfeef24d860ce05.png'

    export default {
        components: {
            nofound
        },
        data() {
            return {
                errorImg: `this.src='${img}'`,
                departent: 'emp', //部门
                departentArr: [
                    {
                        value: 'emp',
                        label: '按人员统计'
                    }
                ],
                comPany: 'emp', 
                comPanyArr: [{
                        value: 'dept',
                        label: '按部门统计'
                    },
                    {
                        value: 'emp',
                        label: '按人员统计'
                    }
                ],
                chooseTime: '本周',
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
                thred: "已分配", //线索 
                sellListKey: "已成交", //销售排行榜
                pageIndex: 1, //第几页
                pageSize: 10, //几条
                sellReport:[], //线索状态数据
                addReport:"",  //新增线索数据
                followReport:[],
                tabLIst:[],
                total:0,
                dynamicArr:[], //线索动态数据
                tableLoading:true, //线索动态的loading
                sellListArr:[],//排行榜数据
                listLoading:true,   //排行榜loading
                isFirst:false,  //监听时间只运行一个
            }
        },
        created() {
            this.weekTime()
        },
        watch:{
            sellListKey(str){
                this.getSellData(str)
            },
            startTime(val){
                this.allRequire()
                setTimeout(()=>{
                    this.isFirst = true
                },100)
            },
            endTime(val){
                if(this.isFirst){
                    this.allRequire()
                }
            }

        },
        methods: {
            allRequire(){
                this.getSellData();
                this.fillData(true);
            },
            fillData(bool){
                
                let obj={
                    key:this.departent,
                    val:this.$store.state.userInfo.valueId,
                    startDate:this.startTime,
                    endDate:this.endTime,
                    pageIndex:this.pageIndex,
                    pageSize:this.pageSize
                }
                if(bool){
                    Promise.all([this.$Api('workbench/searchClues',obj,'post'),this.$Api('workbench/searchStatusCount',obj,'post')]).then(res=>{
                        this.sellReport = res[1].data
                        this.addReport = res[0].data
                        // console.log(res[0])
                    }) 
                }
                this.$Api('workbench/searchCluesRecord',obj,'post','1').then(res =>{
                    this.total = res.data.totalCount
                    this.dynamicArr = res.data.listData
                })
            },
            getSellData(str){
                 let obj={
                    key:this.departent,
                    val:this.$store.state.userInfo.valueId,
                    startDate:this.startTime,
                    endDate:this.endTime,
                    pageIndex:1,
                    pageSize:10,
                    status:str || this.sellListKey
                }
                 const loading = this.$loading({
                        lock: true,
                        spinner: 'el-icon-loading',
                        background: 'rgba(255, 255, 255, 0.7)',
                        target:"#sellList"
                    });
                this.$Api('workbench/searchSaleRank',obj,'post',).then(res =>{
                    this.sellListArr= res.data;
                    loading.close()
                })
            },
            chooseTimeFunc(str) {
                this.chooseTime = str;
                str == "本月" ? this.mounthTime() : this.weekTime()
            },
            mounthTime() {
                let obj = this.getdate();
                let startTime = obj.y + '-' + obj.m + '-01'
                this.isFirst = false
                this.startTime = startTime
                this.endTime = obj.date
                this.pickerOptions2= {
                    disabledDate: time => {
                        let _now = Date.now();
                        return time.getTime() > _now || (this.newDate(startTime).getTime() > time.getTime());
                    }
                }
            },
            weekTime() {
                let obj = this.getdate();

                let times = this.newDate(obj.fullDate).getTime(),
                    firstTime = times - (obj.week - 1) * 24 * 60 * 60 * 1000,
                    startTime = this.getdate(this.newDate(firstTime));
                    this.isFirst = false 
                    this.startTime = startTime.date
                    this.endTime = obj.date

                this.pickerOptions2= {
                    disabledDate: time => {
                        let _now = Date.now();
                        return time.getTime() > _now || (this.newDate(startTime.date).getTime() > time.getTime());
                    }
                }
            },
            getdate(time) {
                let now = time ? time : new Date();
                let y = now.getFullYear(),
                    m = now.getMonth() + 1,
                    d = now.getDate(),
                    week = now.getDay();
                return {
                    fullDate: y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d) + " " + now.toTimeString()
                        .substr(0, 8),
                    date: y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d),
                    week,
                    y,
                    m: (m < 10 ? "0" + m : m),
                    d:(d < 10 ? "0" + d : d),
                    min: now.toTimeString().substr(0, 8)
                }
            },
            changeStart(time){
              
                let _now2 = this.newDate(time).getTime();
                this.chooseTime = '自选'

                this.pickerOptions2= {
                    disabledDate: time => {
                        let _now = Date.now();
                        return time.getTime() > _now || _now2 > time.getTime();
                    }
                }
            },
            changeEnd(time){
                
                let _now2 = this.newDate(time).getTime();

                this.chooseTime = '自选'
                this.pickerOptions= {
                    disabledDate: time => {
                      
                        return time.getTime() > _now2 
                    }
                }
            },
            newDate(str){
                if(typeof(str) == 'number'){
                    return new Date(str)
                }
                var time=str+"";
                    time=time.replace(/-/g,':').replace(' ',':');
                    time=time.split(':');
                return new Date(time[0],(time[1]-1),time[2],time[3] || 0,time[4] || 0,time[5] || 0 );
            },
            handleCurrentChange(val) {
                this.pageIndex = val
                this.fillData(false);
            },
            returnTime(time){
                if(time.indexOf('T') != -1){
                    return time.replace('T',' ')
                }
                return time
            },
        }
    }
</script>

<style lang="stylus" scoped>
    .workBeach {

        .head {
            padding 20px;
            background #fff;

            .title {
                width: 100%;
                border: 1px solid #e9e9e9;
                border-collapse: collapse;

                td {
                    height 50px;
                    border-left: 1px solid #e9e9e9;
                    border-bottom: 1px solid #e9e9e9;
                }

                .select_name {
                    width: 17%;
                    min-width: 150px;
                    padding-right: 30px;
                    vertical-align: middle;
                    text-align: right;
                    font-weight: 400;
                    color: #919191;
                    background-color: #f7f7f7;

                }

                .contidition {
                    width: 83%;
                    padding: 0 50px;
                    vertical-align: middle;

                    .timeEle {
                        display inline-block;
                        overflow hidden ;
                        margin-right 60px;
                        vertical-align: middle;

                        >div {
                            padding 0 7px;
                            height: 28px;
                            line-height: 26px;
                            border-radius 3px 0 0 3px;
                            border: 1px solid #e9e9e9;
                            float left;
                            cursor pointer;
                            &.active {
                                border-color: #08f;
                                color: #08f;
                            }

                            &.sec {
                                border-radius 0px 3px 3px 0;
                            }
                        }
                    }
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

            //销售简报
            .report {
                ul {
                    overflow hidden;

                    li {
                        float left;
                        width 33.33%;
                        border-left: 1px solid #e9e9e9;
                        padding-left 20px;

                        h3 {
                            font-size: 13px;
                            color: #919191;
                            margin-bottom: 10px;
                        }

                        .numBox {
                            width 100%;
                            overflow hidden;

                            >div {
                                float: left;
                                width 33.333%;

                                .num {
                                    line-height 60px;
                                    font-size 28px;
                                    color: #919191;
                                }
                            }
                        }

                        &:first-child {
                            border-left: none;
                            padding-left 0px;
                        }
                    }
                }
            }

            //线索状态
            .thred {
                width 66.66%;
                float left;

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

            .sellList {
                width 33.34%;
                float left;
                padding-left 14px;

                .sellList_tab {
                    width 100%;
                    margin-top 20px;
                    border-top: 1px solid #e9e9e9;

                    th {
                        height 50px;
                        background #f1f1f1;
                        border-bottom: 1px solid #e9e9e9;
                    }

                    td {
                        height 50px;
                        border-bottom: 1px solid #e9e9e9;
                        text-align center
                    }
                }
            }
        }
    }
</style>