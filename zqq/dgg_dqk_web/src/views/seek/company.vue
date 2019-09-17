<template>
    <div class="workBeach">
        <section class="head">
            <div class="Jun_searchBox input-group" >
                <el-autocomplete popper-class="input-autocomplete" v-model="keyWord" :maxlength="50" :fetch-suggestions="querySearchAsync"
                    placeholder="请输入企业名称" @select="handleSelect" :select-when-unmatched='true'>
                    <template slot-scope="{ item }">
                        <div class="list_search">
                            <em v-html="item.heightLight ? item.heightLight : item.value"></em>
                        </div>
                    </template>
                </el-autocomplete>
                <input type="button" value="搜索" class="btn search_button" @click="submit">
                <input type="hidden" ref='move'>
            </div>
        </section>
        <section class="clear2 content" style="margin-bottom:0" ref='jun'>
            <div class="part thred">
                <el-collapse-transition>
                    <div class="docBox" v-show='searchDoc'>
                        <table>
                            <tbody :class="{'showTr': isShowDoc}">
                                <tr>
                                    <td class="head">（多选）查找范围：</td>
                                    <td>
                                        <docEle :model='"multiple"' :dataArr='czfw' v-model="doc.queryScope" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="head">企业状态：</td>
                                    <td>
                                        <docEle :dataArr='qyzt' v-model="doc.status" :val='"name"'/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="head">注册资本：</td>
                                    <td>
                                        <docEle :dataArr='zczb' v-model="doc.regCapital" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="head">注册时长：</td>
                                    <td>
                                        <docEle :dataArr='zcsc' v-model="doc.regTime" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="head">省份地区：</td>
                                    <td class="Jun-option" v-if='provinceArr.length > 0'>
                                        <el-cascader placeholder="请选择省份地区" :options="provinceArr" v-model="doc.province" 
                                           :props="propsSet" :clearable="true"></el-cascader>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="head">行业分类：</td>
                                    <td class="Jun-option" v-if='industryArr.length > 0'>
                                        <el-cascader placeholder="请选择行业分类" :options="industryArr" v-model="doc.industry"
                                            :props="propsSet"  :clearable='true'></el-cascader>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="head">其他筛选：</td>
                                    <td class="Jun-option Jun-option-mult">
                                        <el-select v-model="doc.contentPhone" placeholder="联系电话" >
                                            <el-option v-for="item in contentPhoneArr" :key="item.value"  :label="item.label" :value="item.value">
                                            </el-option>
                                        </el-select>
                                        <el-select v-model="doc.contentEmail" placeholder="联系邮箱">
                                            <el-option v-for="item in contentEmailArr" :key="item.value" :label="item.label" :value="item.value">
                                            </el-option>
                                        </el-select>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <div class="cancel">
                            <a href="javascript:void(0)" class="col" @click="clearAll">清空</a>
                            <span class="line"></span>
                            <a href="javascript:void(0)" class="col" @click=" isShowDoc = !isShowDoc ">{{isShowDoc ?
                                '展开':"收起"}} </a>
                        </div>
                    </div>
                </el-collapse-transition>
                <p>找到 <span class="col-r">{{total >8000 ? '8000+' : total}}</span> 家企业</p>
                <div v-if='total > 0'>
                    <div style="min-height:400px">
                        <table class="thredList">
                            <thead>
                                <th width='41%'>企业信息</th>
                                <th class="text-center">注册资本（元）</th>
                                <th class="text-center">成立时间</th>
                                <th class="text-center">经营状态</th>
                                <th class="text-center">跟进状态</th>
                            </thead>
                            <tbody>
                                <tr v-for='(item,idx) in tableList' :key='idx'>
                                    <td>
                                        <div class="compay_detail">
                                            <h3><a @click="$newPage('/seek/companyInfo',{id:item.companyId})" href="javascript:void(0)">{{item.commercial.companyName}}</a></h3>
                                            <p>法定代表人：{{item.commercial.representMan || '--'}}</p>
                                            <p>地址：{{item.commercial.registerAddress}}</p>
                                        </div>
                                    </td>
                                    <td>{{item.commercial.registerMoney}}万人民币</td>
                                    <td>{{returnTime(item.commercial.registerTime)}}</td>
                                    <td>
                                        <span class="col-g" v-if='stateFunc(item.commercial.businessState)' v-html="item.commercial.businessState"></span>
                                        <span class="col-r" v-else v-html="item.commercial.businessState"></span>
                                    </td>
                                    <td><span class="col-z">无</span></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="pages text-center">
                        <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageIndex"
                            :page-sizes="[10, 20, 30]" :page-size="pageSize" layout="sizes, prev, pager, next, jumper"
                            :total="total">
                        </el-pagination>
                    </div>
                </div>
                <div v-else style="padding:40px 0;text-align:center;position:relative">
                  <nofound :text='noFindText'></nofound>
                </div>
            </div>
            <div class="sellList">
                <!-- <div class="asideList">
                    <h2>猜你喜欢 <span class="col fr cur" @click="exchangeElse"><i class="iconfont" :class="[zhuan && 'loading']">&#xe7b8;</i>
                            换一批</span></h2>
                    <ul>
                        <li><a href="#">神马企业管理成都有限公司</a></li>
                        <li><a href="#">神马企业管理成都有限公司</a></li>
                        <li><a href="#">神马企业管理成都有限公司</a></li>
                        <li><a href="#">神马企业管理成都有限公司</a></li>
                        <li><a href="#">神马企业管理成都有限公司</a></li>
                        <li><a href="#">神马企业管理成都有限公司</a></li>
                    </ul>
                </div> -->
                <div class="asideList">
                    <h2>最近浏览</h2>
                    <ul>
                        <li v-for="(item,idx) in hisToryArr" :key='idx'>
                            <a @click="$newPage('/seek/companyInfo',{id:item.value.id})" href="javascript:void(0)">{{item.value.name}}</a>
                        </li>
                    </ul>
                </div>
            </div>
        </section>
    </div>
</template>
<script>
    import {hisTory} from '@/assets/js/util.js'
    import docEle from '@/components/tabDocEle'
    import nofound from '@/components/noFind'
    export default {
        components: {
            docEle,nofound
        },
        data() {
            return {
                keyWord: "",
                timeout: null,
                zhuan: false,
                propsSet: {
                    value: "name",
                    label: "name"
                },
                searchDoc: false,
                pageIndex: 1, //第几页
                pageSize:10, //每页多少条
                total:0,
                big:false,
                isShowDoc: true,
                isEnterKey:false, // 是否是enter键
                doc:{
                    queryScope: "", //查找范围
                    status: "", //企业状态
                    regCapital: "", //注册资本
                    regTime: "", //注册时长
                    province: [], //省份
                    industry: [], //行业
                    contentPhone: 'all', //有无电话
                    contentEmail: 'all' //有无邮编
                },
                czfw: [], //企业状态数组
                qyzt: [], //企业状态数组
                zczb: [], //注册资本数组
                zcsc: [], //注册时长数组
                provinceArr: [], //省份数组
                industryArr: [], //行业数组
                contentPhoneArr: [{
                        value: 'all',
                        label: '任意联系电话'
                    },
                    {
                        value: '1',
                        label: '有联系电话'
                    },
                    {
                        value: '0',
                        label: '无联系电话'
                    }
                ],
                contentEmailArr: [{
                        value: 'all',
                        label: '任意联系邮箱'
                    },
                    {
                        value: '1',
                        label: '有联系邮箱'
                    }, {
                        value: '0',
                        label: '无联系邮箱'
                    }
                ],
                tableList:[],
                hisToryArr:[],
                noFindText:""
            }
        },
        created() {
            this.getDoc()
            this.getList(true)
            // this.$Loading()
            // console.log('历史',hisTory.get('historyCompany'))
            this.hisToryArr = hisTory.get('historyCompany')
        },
        watch:{
            doc:{
                handler(){
                    this.pageIndex = 1 
                    this.submit()
                },
                deep:true
            },
        },
        methods: {
            getDoc() {
                this.$Api('enterprise/comSearchTreeBook', {}).then(res => {
                    // console.log('数据',res)
                    let { comRegMoney,comRegTime,comScope, comStatus,industrys,provinces } = res.data
                    this.czfw = comScope
                    this.qyzt = comStatus
                    this.zczb = comRegMoney
                    this.zcsc = comRegTime
                   
                    let provinceArr=[]
                        provinces.map((item)=>{
                            let obj = item.father
                                
                            if(item.child && item.child.length > 0){
                                obj.children=item.child
                            }
                            provinceArr.push(obj)  
                        })
                        this.provinceArr = provinceArr
                       
                    let industryArr=[]
                        industrys.map((item)=>{
                            let obj = item.father
                            if(item.child && item.child.length > 0){
                                obj.children=item.child
                            }
                            industryArr.push(obj)  
                        })
     
                        this.industryArr = industryArr
                })
            },
            getList(state){
                let json = {
                    keyWord:this.keyWord,
                    pageIndex:this.pageIndex,
                    pageSize:this.pageSize,
                    queryScope:this.doc.queryScope,//
                    status:this.doc.status, //企业状态
                    regCapital:this.doc.regCapital, //注册资本
                    regTime:this.doc.regTime, //注册时长
                    province:this.hasLast(this.doc.province), //省份
                    industry:this.hasLast(this.doc.industry), //行业
                    phone:this.doc.contentPhone === 'all' ? null : this.doc.contentPhone , //是否有联系电话，  有 1， 没有 0 
                    email:this.doc.contentEmail === 'all' ? null : this.doc.contentEmail//是否有邮箱，  有 1， 没有 0
                }
               
                this.$Api('enterprise/search',json,'post',true).then(res => {
                     let json = res.data
                     if(json == '数据量太大，请更换搜索词'){
                        this.total =0
                        this.big=true
                        this.noFindText = '您的搜索词太宽泛，建议更换一下搜索词'
                     }
                     else if(json == '未找到相关数据'){
                        this.total =0
                        this.big=false
                         this.noFindText = json
                     }
                     else{
                        this.tableList = json.listData
                        this.total =json.totalCount
                        this.big=false
                     }
                    // this.isShowDoc =true;
                    if(!state)
                    this.$jump(this.$refs.jun,-70)  
                })                
            },
            hasLast(str){
                let len = str.length 
                    return  len == 0 ? "":  str[len-1] 
            },
            submit(e) {

                // console.log(123)
                
                if (
                    /[`~!@#$%^&*_\-+=<>?:"\/'\\[\]·~！@#￥%……&*——\-+=？：.]/im.test(this.keyWord)
                ) {
                    this.$message({
                        message: "关键字不能包含特殊字符",
                        type: 'warning',
                        time: 1500
                    });
                    return;
                }
                if (!this.keyWord || this.keyWord.length < 2) {
                    this.$message({
                        message: "关键字长度不能少于两个字符",
                        type: 'warning',
                        time: 1500
                    });
                    return;
                }
               
                this.searchDoc = true
                this.getList ()
            },
            handleSizeChange(val) {
               this.pageSize = val;
               this.pageIndex = 1 ;
               this.getList ()
            },
            handleCurrentChange(val) {
                this.pageIndex = val ;
                this.getList ()
            },
            exchangeElse() { //换一批
                this.zhuan = true
                setTimeout(() => {
                    this.zhuan = false
                }, 3000)
            },
            stateFunc(str){
                return (str == '正常') || (str == '核准设立') || (str == '存续') || (str == '仍注册') || (str == '在营') 
            },
            returnTime(time){
                if(time.indexOf('T') != -1){
                    return time.split('T')[0]
                }
                return time
            },
            querySearchAsync(queryString, cb) {
                let _this = this;
                if (queryString.length > 1) {
                    var results = this.getMsg(queryString, res => {
                        cb(res);
                    });
                } else {
                    cb([]);
                }
            },
            handleSelect(item) {
                this.submit()
                // this.getList();
            },
            getMsg(e, func) {
                let _this = this;
                let obj = {
                    keyWord: e,
                    count: 5,
                };
                this.$Api('enterprise/searchFive', obj,'post').then(res => {
                        // console.log("高亮",res)
                        if (res.code == 0) {
                            let arr = [];
                            for (let item of res.data) {
                                arr.push({
                                    value: item.commercial.companyName,
                                    heightLight: item.lightVal
                                });
                            }

                            func(arr);
                        } else func([]);
                    })
                    .catch(res => {
                        console.log("高亮错误", res);
                        func([]);
                    })
            },
            initHistory() {
                let arr;
                // Common.getHistory("tax_history");
                return arr ? arr : [];
            },
            clearAll(){
                this.doc={
                    queryScope: "", //查找范围
                    status: "", //企业状态
                    regCapital: "", //注册资本
                    regTime: "", //注册时长
                    province: [], //省份
                    industry: [], //行业
                    contentPhone: 'all', //有无电话
                    contentEmail: 'all' //有无邮编
                }
            this.submit()
            }
           
        }
    }
</script>

<style lang="stylus" scoped>
    .workBeach {

        .head {
            padding 40px;
            background #fff;

            .Jun_searchBox {
                width 60%;

                .search_button {
                    width: 10%;
                    color: #fff;
                    text-align: center;
                    height: 40px;
                    font-size: 18px;
                    border: 1px solid #128bed;
                    transition: all .2s ease-in-out;
                    border-radius: 0 4px 4px 0;
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
                width 78%;
                float left;

                .thredList {
                    width 100%;
                    margin-top 20px; 
                    th {
                        background: #fafafa;
                        padding: 16px 8px;
                        border-bottom: 1px solid #e9e9e9;
                        text-align left
                    }

                    td {
                        border-bottom: 1px solid #e9e9e9;
                        padding: 16px 8px;
                        color: #5a5a5a;
                        text-align center
                
                        .compay_detail {
                            text-align left
                            h3 {
                                margin-bottom: .8em;
                                color: rgba(0, 0, 0, .85);
                                font-weight: 500;
                                font-size 18px;
                                font-weight bolder
                            }

                            p {
                                margin-bottom 5px;
                                font-size 12px;
                                color #5a5a5a
                            }
                        }
                    }

                }

                .docBox {
                    margin-bottom 30px;

                    table {
                        width: 100%;
                        border: 1px solid #e9e9e9;
                        border-collapse: collapse;

                        .showTr>tr:not(:first-child) {
                            display none;
                        }

                        tr {
                            td {
                                border-bottom: 1px solid #e9e9e9;
                                padding: 0 4%;
                                color: #5a5a5a;
                                height 50px;

                                &.head {
                                    width: 17%;
                                    min-width: 150px;
                                    padding-right: 30px;
                                    vertical-align: middle;
                                    text-align: right;
                                    font-weight: 400;
                                    color: #919191;
                                    background-color: #f7f7f7;
                                }
                            }
                        }
                    }

                    .cancel {
                        width: 100%;
                        padding: 20px 0 30px;
                        text-align: right;
                        border-bottom: 1px solid #e9e9e9;

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
            }

            .sellList {
                width 22%;
                float left;
                padding-left 14px;

                .asideList {
                    background #fff;
                    margin-bottom 14px;

                    h2 {
                        border-bottom: 1px solid #e8e8e8;
                        background: #f7f7f7;
                        font-size: 13px;
                        height: 46px;
                        line-height 46px;
                        padding 0 20px;
                    }

                    ul {
                        padding 0 20px;

                        li {
                            width: 100%;
                            padding 16px 0;
                            border-bottom: 1px solid #e8e8e8;
                        }
                    }
                }
            }
        }
    }
</style>