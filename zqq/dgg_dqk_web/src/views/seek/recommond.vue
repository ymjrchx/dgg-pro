<template>
  <div class="workBeach">
      <section class="head">
        <table class="title">
            <tbody>
                 <tr>
                    <td class="select_name">（多选）优客评级：</td>
                    <td class="contidition">
                        <docEle model='multiple' :dataArr='levelArr' :keyName = "'level'" @change='getDocEle' :size='"small"'/>
                    </td>
                </tr>
                 <tr>
                    <td class="select_name">（多选）联系方式：</td>
                    <td class="contidition">
                        <docEle model='multiple' :dataArr='contentTypeArr' :keyName = "'contentType'" @change='getDocEle' :size='"small"'/>
                    </td>
                </tr>
                <tr>
                    <td class="select_name">推荐时间：</td>
                    <td class="contidition">
                        <div class="timeEle">
                            <el-radio-group v-model="chooseTime" size="small">
                                <el-radio-button label="昨日"></el-radio-button>
                                <el-radio-button label="今日"></el-radio-button>
                            </el-radio-group>
                        </div>  
                        <el-date-picker
                            v-model="timeVal"
                            type="daterange"
                            align="right"
                            unlink-panels
                            range-separator="至"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期"
                            value-format="yyyy-MM-dd"
                            @change='changeTime'
                            :picker-options="pickerOptions">
                        </el-date-picker>
                    </td>
                </tr>
                   <tr>
                    <td class="select_name">推荐来源：</td>
                    <td class="contidition">
                       <docEle :dataArr="['自动推荐','导入推荐']" :keyName = "'level'" @change='getDocEle' :size='"small"'/>
                    </td>
                </tr>
            </tbody>
        </table>  
        <div class="cancel" style="width: 100%;margin: 10px 0 0px;text-align: right; padding-right:4px">
            <a href="javascript:void(0)" class="col">清空</a>
        </div> 
      </section>
      <section class="clear2 content" style="margin-bottom:0">
          <div class="part thred">
                <h2>共有 0 条销售线索</h2>
                <div class="part_radio">
                  
                    <el-dropdown>
                        <el-button type="primary">
                            转线索<i class="el-icon-arrow-down el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item><span @click="getThread">领取线索</span></el-dropdown-item>
                            <el-dropdown-item>分配线索</el-dropdown-item>
                            <el-dropdown-item>加入公海</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
                <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
                    <el-table-column type="selection">
                    </el-table-column>
                    <el-table-column label="企业全称" >
                    <template slot-scope="scope">{{ scope.row.date }}</template>
                    </el-table-column>
                    <el-table-column prop="name" label="企业官网" >
                    </el-table-column>
                    <el-table-column prop="address" label="相似线索"  show-overflow-tooltip>
                    </el-table-column>
                     <el-table-column prop="address" label="联系人" show-overflow-tooltip>
                    </el-table-column>
                     <el-table-column prop="address" label="顶企客评级"  show-overflow-tooltip>
                    </el-table-column>
                     <el-table-column prop="address" label="推荐时间"  show-overflow-tooltip>
                    </el-table-column>
                     <el-table-column prop="address" label="转线索次数" show-overflow-tooltip>
                    </el-table-column>
                </el-table>
                <div class="pages">
                    <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="pagesIndex"
                        :page-sizes="[10, 20, 30]"
                        :page-size="10"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="400">
                    </el-pagination>
                </div>

               <!-- <nofound/> -->
          </div>
      </section>
  </div>
</template>
<script>
import nofound from '@/components/noFind'
import img from '@/assets/images/c93fd6c7a8ca3ddcfdfeef24d860ce05.png'
import docEle from '@/components/tabDocEle'

export default {
    components: {nofound,docEle},
    data(){
        return{
            errorImg:`this.src='${img}'`,
            pickerOptions: {
                shortcuts: [{
                    text: '最近一周',
                    onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近一个月',
                    onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                        picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近三个月',
                    onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                        picker.$emit('pick', [start, end]);
                    }
                }],
                disabledDate: time => {
                        let _now = Date.now();
                        return time.getTime() > _now;
                }
            },
               levelArr:['A','B',"C","D"],
               contentTypeArr:['手机','固话','邮箱'],
               chooseTime:"", //推荐时间
               timeVal:"", //时间间隔
               thred:"已分配",//线索 
               sellList:"已成交", //销售排行榜
               pagesIndex:1, //第几页
               //表单数据
                tableData: [
                ],
                multipleSelection: [],
                //表单数据 end
                treadHandel:"",
            }
    },
    created(){
    },
    watch:{
        chooseTime(data){
            if(data){
                data == '今日' ? this.todayTime() : this.yesterdayTime()
            } 
        }
    },
    methods:{
        getDocEle(){

        },
        todayTime(){
            let obj = this.getdate();
            this.timeVal = [obj.date,obj.date]
        },
        yesterdayTime(){
            let obj = this.getdate();
        
            let times = new Date(obj.fullDate).getTime(),
                firstTime = times-24*60*60*1000,
                startTime = this.getdate(new Date(firstTime));

                this.timeVal = [startTime.date,obj.date]
        },
        getdate(time) {
            let now = time ? time: new Date(); 
            let y = now.getFullYear(),
                m = now.getMonth() + 1,
                d = now.getDate(),
                week=now.getDay();
            return{
                fullDate:y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d) + " " + now.toTimeString().substr(0, 8),
                date:y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d),week,y,m,d,min:now.toTimeString().substr(0, 8)
            } 
        },
        changeTime(){
           this.chooseTime = '自选'
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
        },
        //表格的函数
        handleSelectionChange(val) {
            this.multipleSelection = val;
            console.log(111,val)
        },
        getThread(){
            alert('领取')
        }   
    }
}
</script>

<style lang="stylus" scoped>
    .workBeach{
            
        .head{
            padding 20px;
            background #fff;

            .title{
                width: 100%;
                border: 1px solid #e9e9e9;
                border-collapse: collapse;
            td{
                height 50px;
                border-left : 1px solid #e9e9e9;
                border-bottom : 1px solid #e9e9e9;
            }    
            .select_name{
                width: 17%;
                min-width: 150px;
                padding-right: 30px;
                vertical-align: middle;
                text-align: right;
                font-weight: 400;
                color: #919191;
                background-color: #f7f7f7;
          
            }
            .contidition{
                width: 83%;
                padding: 0 50px;
                vertical-align: middle;

                .timeEle{
                    margin-right 40px;
                    display inline-block
                }
            }
            }
        }    
        .content{
            margin  14px;
            .part{
                background #fff;
                padding 20px;

                >h2{
                    font-size 15px;
                    margin-bottom 15px;
                    font-weight 700;
                }
            }
            //线索状态
            .thred{
                width 100%;
                .thredList{
                    width 100%;
                    td{
                        border-bottom :1px solid #e9e9e9;
                        padding: 20px 0;

                        img{
                            width: 40px;
                            height: 40px;
                            margin-top: 3px;
                            border-radius: 50%;
                            vertical-align: middle;
                        }
                    }
                    .imgTd{
                        width 56px;
                    }
                }
            }
        }
    }
</style>
