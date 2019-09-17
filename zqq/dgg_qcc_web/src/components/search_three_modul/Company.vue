<template>
    <div>
        <div class="car_top">
            <div class="card">
                <div class="active">
                    <!-- <router-link :to="{path:'/Company'}">公司</router-link> -->
                    <a>公司</a>
                </div>
                <div>
                    <!-- <router-link to="/Risk">风险信息</router-link> -->
                    <a>风险信息</a>
                </div>
                <div>
                    <!-- <router-link to="/Knowledge">知识产权</router-link> -->
                    <a>知识产权</a>
                </div>
            </div>
            <div :class="{tab_content:true,hide:isShow}" id="offSet" style="position:relative">
                <Loading_p v-show="showLoading"/>
                <ul>
                    <!-- <li class="tab_content_item">
                        <label>查找范围</label>
                        <div class="tab_content_classify">
                            <span @click="getSearchStyle" data-id="11111">企业名</span>
                            <span>法人或股东</span>
                            <span>高管</span>
                            <span>经营范围</span>
                        </div>
                    </li>
                    <li class="tab_content_item">
                        <label>查找范围</label>
                        <div class="tab_content_classify">
                            <span>企业名</span>
                            <span>法人或股东</span>
                            <span>高管</span>
                            <span>经营范围</span>
                            <span>企业名</span>
                            <span>法人或股东</span>
                            <span>经营范围</span>
                            <span>法人或股东</span>
                            <span>高管</span>
                            <span :class="{hideSpan: isdown ? false:true }">高管</span>
                            <span :class="{hideSpan: isdown ? false:true }">经营范围</span>
                            <span :class="{hideSpan: isdown ? false:true }">企业名</span>
                            <span :class="{hideSpan: isdown ? false:true }">法人或股东</span>
                            <span :class="{hideSpan: isdown ? false:true }">高管</span>
                            <span :class="{hideSpan: isdown ? false:true }">经营范围</span>
                            <span :class="{hideSpan: isdown ? false:true }">高管</span>
                            <span :class="{hideSpan: isdown ? false:true }">经营范围</span>
                            <span :class="{hideSpan: isdown ? false:true }">企业名</span>
                            <span >法人或股东</span>
                            <span :class="{hideSpan: isdown ? false:true }">高管</span>
                            <span :class="{hideSpan: isdown ? false:true }">经营范围</span>
                            <span :class="{hideSpan: isdown ? false:true }">高管</span>
                            <span :class="{hideSpan: isdown ? false:true }">经营范围</span>
                            <span :class="{hideSpan: isdown ? false:true }">企业名</span>
                            <span :class="{hideSpan: isdown ? false:true }">法人或股东</span>
                            <span :class="{hideSpan: isdown ? false:true }">高管</span>
                            <span :class="{hideSpan: isdown ? false:true }">经营范围</span>
                        </div>  
                    </li> -->
                    <li class="tab_content_item SelectedCondition" v-show="SelectedCondition.length>0">
                        <label>已选条件</label>
                        <div class="tab_content_classify">
                            <!-- <span data-id="11111">企业名</span>
                            <span>法人或股东</span>
                            <span>高管</span>
                            <span>经营范围</span> -->
                            <template v-for="(item,index) in SelectedCondition">
                                <span v-for="(val,key,idx) in item" :key="index+idx" @click="cancel(index)" v-if="idx==0">{{mathName(key,val)}}</span>
                            </template>

                        </div>
                        <a class="clearAll fr" @click="cancelAll">全部清除</a>
                    </li>
                    <li class="tab_content_item clear2" v-for="(val,key,index) in dictionaries" v-if="val" :key="key+'1'" >
                        <template v-if="switchKey(key).isShowDown">
                            <span v-if=" val.length>4" :class="{classify_more:true,down:isdown[index]}" @click="downFunc(index)">{{strArr[strIndex[index]]}}</span>
                            <label :key="key+'2'">{{switchKey(key).str}}</label>
                            <div class="tab_content_classify">
                                <template v-for="(item,idx) in val">
                                    <template v-if="switchKey(key).hasNum">
                                        <template v-if="item.ext1 != 0 ">
                                            <span :key="key+3+idx" v-if="idx<4" @click="checked(key,item.name,item.code,item.ext2)" :class="{'active':isCheckedArr[index][idx]}" :data-key="key">{{item.name}}{{'('+item.ext1+')'}}</span>
                                            <span :key="key+3+idx" v-else :class="{'hideSpan': isdown[index] ? false:true ,'active':isCheckedArr[index][idx]}" @click="checked(key,item.name,item.code,item.ext2)" :data-key="key">{{item.name}}{{'('+item.ext1+')'}}</span>
                                         </template>
                                         <template v-else>
                                            <span :key="key+3+idx" v-if="idx<4" :class="{'active':isCheckedArr[index][idx],hasNum0:true}">{{item.name}}(0)</span>
                                            <span :key="key+3+idx" v-else :class="{'hideSpan': isdown[index] ? false:true ,'active':isCheckedArr[index][idx],hasNum0:true}">{{item.name}}(0)</span>
                                         </template>    
                                    </template>
                                    <template v-else>
                                        <span :key="key+3+idx" v-if="idx<4" @click="checked(key,item.name,item.code,item.ext2)" :class="{'active':isCheckedArr[index][idx]}" :data-key="key">{{item.name}}</span>
                                        <span :key="key+3+idx" v-else :class="{'hideSpan': isdown[index] ? false:true ,'active':isCheckedArr[index][idx]}" @click="checked(key,item.name,item.code,item.ext2)" :data-key="key">{{item.name}}</span>
                                    </template>    
                                </template>
                            </div>
                        </template>
                        <template v-else>
                            <span v-if=" val.length>8" :class="{classify_more:true,down:isdown[index]}" @click="downFunc(index)" :data-idx="index">{{strArr[strIndex[index]]}}</span>
                            <label :key="key+'2'">{{switchKey(key).str}}</label>
                            <div class="tab_content_classify">
                                <template v-for="(item,idx) in val">
                                 
                                    <template v-if="switchKey(key).hasNum">
                                        <template v-if="item.ext1 != 0 ">
                                            <span :key="key+3+idx" v-if="idx<8" @click="checked(key,item.name,item.code,item.ext2)" :class="{'active':isCheckedArr[index][idx]}" :data-key="key">{{item.name}}{{'('+item.ext1+')'}}</span>
                                            <span :key="key+3+idx" v-else :class="{'hideSpan': isdown[index] ? false:true ,'active':isCheckedArr[index][idx]}" @click="checked(key,item.name,item.code,item.ext2)" :data-key="key">{{item.name}}{{'('+item.ext1+')'}}</span>
                                         </template>
                                         <template v-else>
                                            <span :key="key+3+idx" v-if="idx<8" :class="{'active':isCheckedArr[index][idx],hasNum0:true}">{{item.name}}(0)</span>
                                            <span :key="key+3+idx" v-else :class="{'hideSpan': isdown[index] ? false:true ,'active':isCheckedArr[index][idx],hasNum0:true}">{{item.name}}(0)</span>
                                         </template>    
                                    </template>
                                    <template v-else>
                                        <span :key="key+3+idx" v-if="idx<8" @click="checked(key,item.name,item.code,item.ext2)" :class="{'active':isCheckedArr[index][idx]}" :data-key="key">{{item.name}}</span>
                                        <span :key="key+3+idx" v-else :class="{'hideSpan': isdown[index] ? false:true ,'active':isCheckedArr[index][idx]}" @click="checked(key,item.name,item.code,item.ext2)" :data-key="key">{{item.name}}</span>
                                    </template>    
                                </template>
                                <transition name="el-fade-in-linear">
                                    <div v-if="key == 'zczb'" class="custom">
                                        <span v-on:click.stop="showCustom('zbShow')">自定义
                                            <span class="caret"></span>
                                        </span>
                                        <section :class="{hide:zbShow}" ref="custom1" @click.stop="stopEvent">
                                            <div class='mon'>
                                                <span>从</span> <input id="registDateStart" class="form-control" type="text" v-model="startM"></div>
                                            <div class='mon'>
                                                <span>至</span> <input id="registDateEnd" class="form-control" type="text" v-model="endM"></div>
                                            <span class="customBtn" @click.stop="goZb">确定</span>
                                        </section>
                                    </div>
                                </transition>
                                <transition name="el-fade-in-linear">
                                    <div v-if="key == 'cl_date'" class="custom">
                                        <span v-on:click.stop="showCustom('timeShow')">自定义
                                            <span class="caret"></span>
                                        </span>
                                        <section :class="{hide:timeShow}" ref="custom2" @click.stop="stopEvent">
                                            <div>
                                                <span>从</span> <input id="registDateStart" class="form-control" type="text" v-model="startYear"></div>
                                            <div>
                                                <span>至</span> <input id="registDateEnd" class="form-control" type="text" v-model="endYear"></div>
                                            <span class="customBtn" @click.stop="goYear">确定</span>
                                        </section>
                                    </div>
                                </transition>
                            </div>
                        </template>
                    </li>
                    <li class="clear"></li>
                </ul>
            </div>
            <div :class="{handel_content:true,isShow:isShow}" @click="showFunc">{{strShow[showIndex]}}</div>
        </div>
        <div class="goods_list">
            <div class="list_title" v-show="searchIng == 1">为您找到<span class="col">{{totalSize>5000? "5000+":totalSize}}</span> 家符合条件的企业</div>
            <div class="list_title" v-show="searchIng == 0">正在为您寻找......</div>
            <table id="list_table" v-if="totalSize>0" class="m_srchList">
                <thead>
                    <tr>
                        <th width="120px">公司</th>
                        <th></th>
                        <th width="140px">状态</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(item,index) in comanyList" :key="index">
                        <td class="logoBox">
                            <img :src="item.logo ? item.logo : ''" :onerror="logo" alt="顶企查" class="logo_img">
                        </td>
                        <td>
                            <p class="company_titel"  :data-id="item.companyId"><span @click="goDeatil(item.companyId,item.name)" v-html="mathColr(item.name) || '暂无'"></span></p>
                            <p>
                                <span class="mar_r16">法定代表人: <span v-html="item.qygsDetail ? mathColr(item.qygsDetail.operName)  || '暂无' : '暂无' "></span></span>
                                <span class="mar_r16">注册资金: {{item.qygsDetail ? mathChina(item.qygsDetail.registCapi)  || "暂无" : "暂无"}}</span>
                                <span class="mar_r16">成立时间: {{mathTime(item.qygsDetail)}}</span>
                            </p>
                            <p>
                                <span class="mar_r16">邮箱: {{item.email || "暂无"}}</span>
                                <span class="mar_r16">电话: {{item.qygsDetail ? getTel(item.tel,item.qygsDetail.contactInfoList[0].phoneNumber) : "暂无"}} </span>
                                <!-- <span v-if="item.qygsDetail ? moreTel(item.qygsDetail.contactInfoList[0].phoneNumber,'bool') :  0 ">
                                    <el-popover placement="right" title="更多号码" width="200" trigger="click" :content="moreTel(item.qygsDetail.contactInfoList[0].phoneNumber,'moreStr')">
                                        <el-button slot="reference">更多号码</el-button>
                                    </el-popover>
                                </span> -->
                                <span v-if="item.qygsDetail ? moreTel(item.qygsDetail.contactInfoList[0].phoneNumber,'bool') :  0 ">
                                    <el-popover placement="right" title="更多号码" width="200" trigger="click" popper-class="getMoreTel">
                                        <div class="dqc_popover_tel">
                                            <div v-if="isLogin" v-html="moreTel(item.qygsDetail.contactInfoList[0].phoneNumber,'html')"></div>
                                            <div v-else class="no_login" @click="goLogin">您还未登录，去登录</div>
                                        </div>
                                        <el-button slot="reference">更多号码</el-button>
                                    </el-popover>
                                </span>
                            </p>
                            <p style="width:650px">
                                地址：<span v-html=" item.qygsDetail ? mathColr(item.qygsDetail.address) || '暂无' : '暂无'"></span>
                            </p>
                        </td>
                        <td>
                            <span class="list_handel green" v-if="item.qygsDetail ? item.qygsDetail.status == '在业' : 0 ">{{item.qygsDetail.status}}</span>
                            <span class="list_handel blue" v-else-if="item.qygsDetail ? item.qygsDetail.status == '存续' : 0">{{item.qygsDetail.status}}</span>
                            <span class="list_handel violet" v-else-if="item.qygsDetail ? item.qygsDetail.status == '开业' : 0 ">{{item.qygsDetail.status}}</span>
                            <span class="list_handel red" v-else>{{mathStatus(item.qygsDetail.status)}}</span>
                        </td>
                    </tr>
                    <!-- <tr>
                  <td class="logoBox">
                      <img src="123.png" :onerror="logo" alt="顶企查" class="logo_img">
                  </td>
                  <td>
                    <p class="company_titel" @click="goDeatil">宇宙银河系五金店</p>
                    <p>
                      <span class="mar_r16">法定代表人:灭霸</span>
                      <span class="mar_r16">注册资金:1000万人民币</span>
                      <span class="mar_r16">成立时间:1001</span>
                    </p>
                    <p>
                      <span class="mar_r16">邮箱:mieba.@163.com</span>
                      <span class="mar_r16">电话:9595995</span>
                    </p>
                    <p>
                      地址：漫威宇宙银河系阿姆斯特丹街4号附8号
                    </p>
                  </td>
                  <td>
                    <span class="list_handel red">倒闭</span>
                  </td>
                </tr> -->
                </tbody>
            </table>
        </div>
        <div class="pageination" v-if="totalSize>0">
            <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageIndex" :page-sizes="[10,20,30]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="totalSize">
            </el-pagination>
        </div>
        <div class="search_no" v-else>
            <img src="../../assets/images/nodata.png" alt="">
            <p>{{returnMsg}}</p>
        </div>
    </div>
</template>
<script>
import userPhoto from "@/assets/images/default.jpg";
import Common,{Jump} from "@/util/common.js";
import Loading_p from "@/components/Loading.vue";

export default {
    components: {Loading_p},
    props: {
        params: ""
    },
    data() {
        return {
            searchIng:0, //显示正在寻找
            headString: ["公司", "风险信息", "知识产权"],
            pageId: 0,
            strShow: ["收起", "展开"],
            isShow: false,
            showIndex: 0,
            logo: 'this.src="' + userPhoto + '"',
            pageSize: 10, //每页多少条
            totalSize: 1, //总条数,
            pageIndex: 1,
            comanyList: [],
            dictionaries: {},
            showLoading:1,  //显示字典遮盖层
            isdown: [0, 0, 0, 0, 0, 0, 0, 0, 0], //控制字典项每一个单项中的小项的显示 单数组 初始都为0
            strArr: ["更多", "收起"], //每一项字典项 操作字段 数组
            strIndex: [0, 0, 0, 0, 0, 0, 0, 0, 0], //每一个字典项 操作字段下标 单数组 初始都为0
            isCheckedArr: [], //控制字典项每一个单项中的小项的选中 二维数组
            requireData: "", //存储搜素关键字
            isShowSelected: 1, //是否显示已选条件
            SelectedCondition: [], //存储要显示的已选条件
            zbShow: 1, //自定义资本
            timeShow: 1, //自定时间
            startM: "",
            endM: "",
            startYear: "",
            endYear: "",
            returnMsg:""
        };
    },
    watch: {
        $route: "reloadPage",

    },
    computed:{
        isLogin:function(){
            return this.$store.state.isLogin
        }
    },
    created() {
        
        //console.log("參數props", this.$route.query);
        this.requireData = this.$route.query;
        if (this.$route.query.type != "all") {
            this.SelectedCondition = [{ type: this.$route.query.type }];
        }
        if (this.$route.query.province) {
            this.SelectedCondition = [{ province:this.$route.query.province, proCode:this.$route.query.proCode}];
        }
        if (this.$route.query.type != "all") {
            this.SelectedCondition = [{ type: this.$route.query.type }];
        }
        this.initDic();
        this.initPageList(666);
    },
    methods: {
        initDic() {
            let objData = Object.assign({}, this.requireData, this.returnObj_list());
            //console.log("字典参数", objData);
            this.showLoading=1;
            this.$axios({
                type:"post",
                url:"company/searchTreeBook",
                data:objData,
                success:res => {
                    //console.log("字典", res.data);
                    let obj = res.data.data;
                    let isdownArr = [],
                        strIndexArr = [],
                        isCheckedArrArr = [],
                        idx = 0;

                    for (let item of this.SelectedCondition) {
                        for (let key in item) {
                            switch (key) {
                                case "type":
                                    delete obj["1212"];
                                    break;
                                //case "province": delete obj['p7453706779315408896'];break;    //省份+code
                                case "org_type":
                                    delete obj["org_type"];
                                    break; //机构类型
                                case "status":
                                    delete obj["qy_status"];
                                    break; //企业状态
                                case "senior":
                                    delete obj["gjsx"];
                                    break; //高级筛选
                                case "content":
                                    delete obj["zczb"];
                                    break; //注册资本
                                case "startDate":
                                    delete obj["cl_date"];
                                    break; //成立日期
                                case "econKind":
                                    delete obj["qy_type"];
                                    break; //企业类型
                                // case "industry":delete obj["industry"];break; //行业门类
                            }
                        }
                    }
                    
                    for (let key in obj) {
                        isdownArr.push(false);
                        strIndexArr.push(0);
                        isCheckedArrArr.push([]);
                        if(obj[key]){
                            for (let val of obj[key]) {
                                isCheckedArrArr[idx].push(0);
                            }
                        }
                        
                        idx++;
                    }

                    this.dictionaries = obj;
                   
                    this.isdown = isdownArr;

                    this.strIndex = strIndexArr;
                    this.isCheckedArr = isCheckedArrArr;
                   
                    this.showLoading=0;

                    
                },
                fail:res => {}
            });
        },
        initPageList(count) {
            this.searchIng=0;
          
            //初始化页面
            let objData = Object.assign(
                {},
                {
                    pageIndex: this.pageIndex,
                    pageSize: this.pageSize
                },
                this.requireData,{type:"all"},
                this.returnObj_list()
            );

            let _this = this;
            this.$axios({type:"post", url:"company/searchList", data:objData,success:res => {
                let obj = res.data.data;
                //console.log("取出数据", res);
                if (obj && Number(obj.totalCount) > 0) {
                     _this.comanyList = obj.listData;
                    _this.totalSize = Number(obj.totalCount);
                } else {
                    _this.totalSize = 0;
                    _this.returnMsg=res.data.msg
                }
                
                this.searchIng=1;

                if(!count) Jump(document.getElementById("offSet2"))
                   
                }
            },1);
        },
        showFunc() {
            this.isShow = this.isShow ? false : true;
            this.showIndex = this.isShow ? 1 : 0;
        },
        downFunc(idx) {
            let isdownArr = [...this.isdown],
                strIndexArr = [...this.strIndex];
            isdownArr[idx] = isdownArr[idx] ? false : true;
            strIndexArr[idx] = strIndexArr[idx] ? 0 : 1;

            this.isdown = isdownArr;
            this.strIndex = strIndexArr;
        },
        checked(key, name, code , isLast) {
            let obj = {};
            let newArr = [...this.SelectedCondition];
            switch (key) {
                case "1212":
                case 0:
                    switch (name) {
                        case "企业名":
                            obj["type"] = "qyn";
                            break;
                        case "高管":
                            obj["type"] = "gg";
                            break;
                        case "法人或股东":
                            obj["type"] = "fr";
                            break;
                        case "品牌产品":
                            obj["type"] = "pp";
                            break;
                        case "联系方式":
                            obj["type"] = "ap";
                            break;
                        case "经营范围":
                            obj["type"] = "bs";
                            break;
                        default:
                            obj["type"] = "all";
                            break;
                    }
                    break;
                case "p7453706779315408896":
                    obj["province"] = name;
                    obj["proCode"] = code;
                    if(isLast === "0")  obj["isLeafPro"] = 0;
                    break; //省份+code
                case "org_type":
                    obj["org_type"] = name;
                    break; //机构类型
                case "qy_status":
                    obj["status"] = name;
                    break; //企业状态
                case "gjsx":
                    obj["senior"] = name;
                    break; //高级筛选
                case "zczb":
                    obj["content"] = name;
                    obj["registCapi"] = code;
                    break; //注册资本
                case "cl_date":
                    obj["startDate"] = name;
                    break; //成立日期
                case "qy_type":
                    obj["econKind"] = name;
                    break; //企业类型
                case "industry":
                    obj["industry"] = name;
                    obj["indCode"] = code;
                    if(isLast === "0")  obj["isLeafInd"] = 0;
                    break; //行业名称
                default:
                    obj["other"] = "其他";
                    break;
            }
            newArr.push(obj);
            this.SelectedCondition = newArr;
            this.pageIndex = 1;
            this.initDic();
            this.initPageList();
            // console.log("123",newArr)
        },
        cancel(idx) {
            let arr = [...this.SelectedCondition];
            arr.splice(idx, 1);
            this.SelectedCondition = arr;
            this.pageIndex = 1;
            this.initDic();
            
            this.initPageList();
        },
        cancelAll() {
            this.SelectedCondition = [];
            this.pageIndex = 1;
            this.initDic();
           
            this.initPageList();
        },
        returnObj() {
            let obj = {};
            for (let key of this.SelectedCondition) {
                Object.assign(obj, key);
            }
            //onsole.log(obj);

            return obj;
        },
        returnObj_list(){
            let obj = {};

            for (let item of this.SelectedCondition) {
                for(let key in item){
                    if(( key == "industry" ||  key == "province" ) && obj[key]){
                        obj[key]=obj[key]+"-"+item[key]
                    }
                    else{
                        obj[key]=item[key]
                    }
                }
            }
            //console.log("列表",obj);

            return obj;
        },
        handleCurrentChange(e) {
            //翻页 跳转到几页
            this.pageIndex = e;
            // console.log('handleCurrentChange',e);
            this.initPageList();
        },
        reloadPage() {
           
            this.pageIndex = 1;
            this.SelectedCondition = [];
            this.requireData = this.$route.query;
            this.initDic();
            this.initPageList();
        },
        handleSizeChange(e) {
            //修改条数

            this.pageSize = e;
            this.pageIndex = 1;
            this.initPageList();
        },
        goDeatil(companyId,name) {
            Common.newPage("Company_detail", { id: companyId,title:name });
        },
        moreTel(data, key) {
            //查看是否有更多的电话号码
            let obj = { bool: false, str: data || "暂无", moreStr: "" };
            if (obj.str.indexOf("、") != -1) {
                let arr = obj.str.split("、"),
                    newArr=[];
                for(let item of arr){   
                    if(item.length > 5){
                        newArr.push(item)
                    }
                }
                if(newArr.length > 0 ){
                    obj.bool = true;
                    let html="";
                    for(let val of newArr){
                        html+="<a href='javascript:void(0)' style='display:block;text-align:left;color:#888'>"+val+"</a>"
                    }
                    obj.html=html
                }
            }
            return obj[key];
        },
        getTel(str1,str2){
            if(str1 && str1 !="更多号码" ) return str1;
            else if(str2){
                let arr = str2.replace(/-/g,"").split("、");
                // if (arr[0] == "更多号码" || arr[0]=="" || arr[0]=="-") return arr[1];
                for(let tel of arr){
                    if(tel.length > 6){
                        let newArr =[...tel]
                        if(!this.isLogin){
                            newArr[3]="*";
                            newArr[4]="*";
                            newArr[5]="*";
                            newArr[6]="*";
                        }
                        return newArr.join("")
                    } 
                   
                }
               
            }
            else return "暂无"
        },
        mathName(key, val) {
            let str = val;
            if (key == "type") {
                switch (val) {
                    case "qyn":
                        str = "企业名";
                        break;
                    case "gg":
                        str = "高管";
                        break;
                    case "fr":
                        str = "法人或股东";
                        break;
                    case "pp":
                        str = "品牌产品";
                        break;
                    case "ap":
                        str = "联系方式";
                        break;
                    case "bs":
                        str = "经营范围";
                        break;
                }
            }
            
            return str;
        },
        mathTime(str){
            if(str && str.startDate){
                let time =Number(str.startDate)
                if(isNaN(time)){
                    return str.startDate
                }
                else{
                    return this.mathTime2(time)
                }
            }
            return " - "
        },
        mathTime2(str){
            let date = new Date(str);
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            var d = date.getDate();

            let timeStr =y+"-"+ (m >9 ? m: ("0"+m))+"-"+ (d >9 ? d: ("0"+d));

            return timeStr
        },
        mathChina(str) {
            //匹配中文
            if(str == "") return "";
            let str2 = (str+"").replace(/\s|人民币/g, "");
            let isMath = Number(str2);
            if (isNaN(isMath)) {
                return str2;
            } else {
                return str2 + "万元";
            }
        },
        mathStatus(str){
            
            if(!str || str == " ") return "未知"
            if(str.length >2) return str.substring(0,2)

            return str;
        },
        mathColr(str){
          
            if(!str) return "";
            let mathStr=this.$route.query.keyWord.replace(/\s*/,"")
            let re = new RegExp(mathStr, 'g');
            let newStr=(str+"").replace(re, "<i style='color:#FD485E'>"+mathStr+"</i>");
            return newStr;
        },
        switchKey(key) {
            let obj = { str: "其他", isShowDown: false };
            switch (key) {
                case "1212":
                    obj.str = "查找范围";
                    break;
                case "org_type":
                    obj.str = "机构类型";
                    break;
                case "qy_type":
                    obj.str = "企业类型";
                    break;
                case "qy_status":
                    obj.str = "企业状态";
                    obj.hasNum=true;
                    break;
                case "zczb":
                    obj.str = "注册资本";
                    break;
                case "cl_date":
                    obj.str = "成立日期";
                    obj.hasNum=true;
                    break;
                case "p7453706779315408896":
                    obj.str = "省份地区";
                    obj.isShowDown = true;
                    obj.hasNum=true;
                    break;
                case "industry":
                    obj.str = "行业门类";
                    obj.isShowDown = true;
                    obj.hasNum=true;
                    break;
                case "gjsx":
                    obj.str = "高级筛选";
                    break;
            }
            return obj;
        },
        showCustom(str) {
            switch (str) {
                case "zbShow":
                    this.timeShow = 1;
                    this.zbShow = this.zbShow ? 0 : 1;
                    break;
                case "timeShow":
                    this.zbShow = 1;
                    this.timeShow = this.timeShow ? 0 : 1;
                    break;
                default:
                    this.zbShow = this.timeShow = 1;
                    document.removeEventListener(
                        "click",
                        this.showCustom,
                        false
                    );
                    break;
            }
            if (this.zbShow == 1 || this.timeShow == 1) {
                document.addEventListener("click", this.showCustom, false);
            }
        },
        stopEvent() {
            event.preventDefault();
            event.stopPropagation();
        },
        goZb() {
            //自定义资本
            let start = Number(this.startM);
            if (isNaN(start) || start < 1)  {
                this.$message({
                    message: "注册资本数值不正确",
                    type: "error",
                    position: "center",
                    duration: 1000
                });
                return;
            } 
          
            let obj={};

            if(this.endM == 0 ){
                obj["content"] = start+"万以上";
                obj["registCapi"] = start+"-0";
            }
            else{

                let end = Number(this.endM);

                if (isNaN(end) || start > end){
                    this.$message({
                        message: "区间注册资本数值不正确",
                        type: "error",
                        position: "center",
                        duration: 1000
                    });
                    return;
                }
            
                if(start == end){
                    obj["content"] = end+"万";
                    obj["registCapi"] = start+"-"+end;
                }
                else{
                    obj["content"] = start+"万-"+end+"万";
                    obj["registCapi"] = start+"-"+end;
                }
            }
            let newArr=[...this.SelectedCondition];
            newArr.push(obj)
            this.SelectedCondition =newArr;
            this.pageIndex = 1;


            this.initDic();
            this.initPageList();

        },
        goYear() {
            //自定义年限
            let start = Number(this.startYear),
                str = "";
            let nowYear = new Date().getFullYear();
            if (isNaN(start) || start < 1948 || start > nowYear ){
                this.$message({
                    message: "成立日期数值不正确",
                    type: "error",
                    position: "center",
                    duration: 1000
                });
                return;
            } 
            let end = Number(this.endYear);
           
            //console.log("isnan",isNaN(end),end)
            if (isNaN(end) || end == 0) {
              
                str = start + "-" + nowYear;
            } 
            else{
                if(start > end || end > nowYear ){
                    this.$message({
                        message: "区间日期数值不正确",
                        type: "error",
                        position: "center",
                        duration: 1000
                    });
                    return;
                } 
                else{
                    str = start + "-" + end;
                }
            }
            let newArr=[...this.SelectedCondition];
                newArr.push({ startDate: str })
            this.SelectedCondition =newArr;
            this.pageIndex = 1;
            
            this.initDic();
            this.initPageList();
        },
        goLogin(){
             this.$router.push({path:"/Login",query:{"back":true}})
        }
    }
};
</script>
<style scoped>
@import "../../assets/companyList.css";
table.m_srchList tr th {
 background:transparent;
}
table.m_srchList tr{
    border: 1px solid transparent;
}
.dqc_popover_tel .no_login{
    color: #128bed;
    cursor: pointer;
}
</style>