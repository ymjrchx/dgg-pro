<template>
    <div>
        <Head/>
        <div class="search_body">
            <!-- <img src="../assets/images/search-bg-brand.png" alt="顶企查" class="left_img"> -->
            <div class="logoSearch company">
                <div class="container w" >
                    <div class="clogo">公司名字预查，提升工商注册通过率</div>
                    <div class="searchStyle" id="searchStyle">
                        <div class="part_i"  >
                            <input type="text" placeholder="城市" @click.stop="showPartBox" v-model="city">
                            <div class="childBox part" v-show="partBoxShow" @click.stop="partBoxShow = 1" >
                                <div class="partHot">热门城市：
                                    <span @click.stop="check('成都','hot')">成都</span>
                                    <span @click.stop="check('上海','hot')">上海</span>
                                    <span @click.stop="check('北京','hot')">北京</span>
                                    <span @click.stop="check('广州','hot')">广州</span>
                                    <span @click.stop="check('深圳','hot')">深圳</span>
                                    <span @click.stop="check('南京','hot')">南京</span>
                                    <span @click.stop="check('杭州','hot')">杭州</span>
                                </div>
                                <ul class="partBox">
                                    <li v-for="(item,idx) in areaArr" @click.stop="check(item.name,idx)" :key="idx" :title="item.name">{{item.name}}</li>
                                </ul>
                            </div>
                        </div>
                        <div class="name_i">
                            <input type="text" placeholder="字号 如：阿里巴巴" v-model="name">
                        </div>
                        <div class="industry_i">
                            <input type="text" placeholder="行业类别 如：信息技术" @click.stop="showIndustryBox" v-model="industry">
                            <div class="childBox industry" v-show="industryBoxShow">
                                <div class="Menu2">
                                    <ul class="zoom clear" v-for="(item,idx) in industryArr" :key="" idx>
                                        <div class="memu2_tit01">{{item.name}}</div>
                                        <li v-for="(item2,index) in item.list" :key="index" @click.top="checkIndustry(item2)">{{item2}}</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="style_i" @click="companyType= !companyType">
                            <ul class="swiper_style" :class="{'active':companyType}">
                                <li>有限公司</li>
                                <li>合伙公司</li>
                            </ul>
                            <b class="caret" :class="{'active':companyType}"></b>
                        </div>
                        <input type="button" value="公司核名" class="button btn search_button" @click="submit">
                    </div>
                    <div style="margin-top:70px;color:#fff">示例：杭州阿里巴巴科技有限公司，地区是“杭州”，字号是“阿里巴巴”，行业是“科技”，类型是“有限公司”</div>
                </div>
            </div>
            <div class=" marketing">
                <div  v-if="searchData == '存在' " style="margin-top:20px">
                    <!-- <div class="col-lg-4">
                        <img src="../assets/images/icon1.png" />
                        <h2>毫秒查询&nbsp;&nbsp;节省更多时间</h2>
                    </div>
                    <div class="col-lg-4">
                        <img src="../assets/images/icon2.png" />
                        <h2>数据全面 &nbsp;&nbsp;提供更多数据</h2>
                    </div>
                    <div class="col-lg-4">
                        <img src="../assets/images/icon3.png" />
                        <h2>精确匹配&nbsp;&nbsp;命中目标信息</h2>
                    </div> -->
                    <div class="hm_bj">
                        <div class="sf cen">
                            <div class="tit">基于企业大数据的核名系统</div>
                            <div class="hm_sf clear">
                                <ul class="clear">
                                    <li>
                                        <a >
                                            <span class="ys1 ">义</span>词义智能识别算法
                                            <p></p>
                                        </a>
                                    </li>
                                    <img src="../assets/images/hm_sf_l.png" alt="" class="l1">
                                    <li>
                                        <a >
                                            <span class="ys2">形</span>词形智能识别算法
                                            <p class="bo1"></p>
                                        </a>
                                    </li>
                                    <img src="../assets/images/hm_sf_l.png" alt="" class="l2">
                                    <li>
                                        <a >
                                            <span class="ys3">音</span>词音智能识别算法
                                            <p class="bo2"></p>
                                        </a>
                                    </li>
                                    <li>
                                        <a >
                                            <span class="ys4">联</span>词联想算法
                                            <p class="bo3"></p>
                                        </a>
                                    </li>
                                    <img src="../assets/images/hm_sf_l.png" alt="" class="l3">
                                    <li>
                                        <a >
                                            <span class="ys5">自</span>机器自动学习算法
                                            <p class="bo4"></p>
                                        </a>
                                    </li>
                                    <img src="../assets/images/hm_sf_l.png" alt="" class="l4">
                                    <li>
                                        <a >
                                            <span class="ys6">似</span>行业相似度智能算法
                                            <p class="bo5"></p>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="zhuyi cen">
                        <div class="tit">公司取名，需要注意哪些事项？</div>
                            <ul>
                                <li><i class="yc1"></i><h3>不能重名</h3><p></p><span>相同或相似行业存在重名公司，不能注册。如：“宜招网络科技”与“宜招信息科技”算重名。</span></li>
                                <li><i class="yc2"></i><h3>不能触犯驰名商标</h3><p></p><span>如：“万达”是驰名商标，不能注册为公司名字；</span></li>
                                <li><i class="yc3"></i><h3>不能与知名公司名字混淆</h3><p></p><span>如“新微软”“微软之家”等不能注册为公司名字。</span></li>
                                <li><i class="yc4"></i><h3>尽量不用地区名称及简称</h3><p></p><span>如：上海，兰州，沪等不能注册为公司名字。</span></li>
                                <li><i class="yc5"></i><h3>不能使用繁体、数字、英文</h3><p></p><span>公司名称只能使用简体中文。</span></li>
                                <li><i class="yc6"></i><h3>不能使用行业通用词汇</h3><p></p><span>如：“上海电脑科技有限公司”不能注册为公司名字。</span></li>
                                <li><i class="yc7"></i><h3>不能使用名人字号</h3><p></p><span>如：“马云”，“王健林”等不能注册为公司名字。</span></li>
                                <li><i class="yc8"></i><h3>不能带有宗教色彩</h3><p></p><span>如：“伊斯兰”等不能注册为公司名字。</span></li>
                            </ul>
                    </div>
                </div>
                <div class="verifyData" v-else>
                    <div class="jg_container">
                        <div id="j_title" class="j_title">"
                            <font color="#f00">{{searchStr}}</font> "的注册通过率
                        </div>
                        <div class="j_cont clear">
                            <ul class="tgl">
                                <li :class="{g: level == 'H' }">
                                    <div class="tgl_bg">高</div>
                                </li>
                                <li :class="{y: level == 'M' }">
                                    <div class="tgl_bg">中</div>
                                </li>
                                <li :class="{r: level == 'L' }">
                                    <div class="tgl_bg">低</div>
                                </li>
                            </ul>
                        </div>
                        <div class="level">
                            <span v-if="level == 'H'">暂未查出问题，请以工商核名为准!</span>
                            <span v-else-if="level == 'M'">通过率一般，请谨慎使用!</span>
                            <span v-else>通过率低，不建议使用!</span>
                        </div>
                        <div class="warp_fx" v-if="companyList.length > 0">
                            <div class="warp_fxtit">
                                <h2>相似公司分析</h2>
                            </div>
                            <div class="warp_fxcon">
                                <ul class="verifyList">
                                    <li v-for="(item,idx) in companyList" :key="">
                                        <p class="clear">
                                            <span class="fl">检索到类似的公司</span>
                                            <span class="fr">相似度 {{percentage(item)}}%</span>
                                        </p>
                                        <p v-html="item"></p>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import Head from "@/components/Head";
import Store from "@/store";
import area from "@/util/area.json";
import Cookie from "@/util/cookie.js";
import { IsLogin,Jump } from "@/util/common";
export default {
    components: { Head },
    data() {
        return {
            city: "",
            areaArr: [],
            partBoxShow: 0, //显示地方选项
            twoChoose: 0, //第几次选择地方
            industry: "", //行业类型
            name: "",
            industryBoxShow: 0, //显示行业类别选项
            industryArr: [
                {
                    name: "科技类",
                    list: [
                        "网络科技",
                        "电子商务",
                        "信息技术",
                        "游戏",
                        "电子",
                        "软件",
                        "新材料",
                        "生物科技",
                        "教育科技"
                    ]
                },
                {
                    name: "许可类",
                    list: [
                        "投资管理",
                        "金融",
                        "资产",
                        "商业保理",
                        "融资租赁",
                        "医疗器械",
                        "人力资源",
                        "食品",
                        "劳务派遣"
                    ]
                },
                {
                    name: "服务类",
                    list: [
                        "广告",
                        "文化传播",
                        "建筑装潢",
                        "设计",
                        "美容美发",
                        "房地产中介",
                        "物业管理",
                        "商务咨询",
                        "企业管理"
                    ]
                },
                {
                    name: "其 他",
                    list: [
                        "贸易",
                        "实业",
                        "制造",
                        "服饰",
                        "化妆品",
                        "工程",
                        "农业",
                        "餐饮管理",
                        "物流"
                    ]
                }
            ],
            companyType: 0,
            searchData: "存在",
            companyList: [],
            level: "",
            searchStr: "",
        };
    },
    created() {
        Store.commit("updateHeadActive", 1);
        document.onkeydown = e => {
            let keyNum = window.event ? e.keyCode : e.which;
            if (keyNum == 13) {
                this.submit();
            }
        };
    },
    destroyed(){
        document.onkeydown = e => {
            
        };
    },
    methods: {
        submit() {
            if (!this.city || !this.industry || !this.name) {
                this.$message({
                    message: "请填写完整的搜索信息",
                    type: "warning"
                });
                return;
            }

             let newStr =this.city+this.name+this.industry+(this.companyType ? "合伙公司" : "有限公司");

            var reg =/^[\u4e00-\u9fa5]+$/; 
                newStr=newStr.replace(/ /g,'')
    
            if( !(reg.test(newStr))){
                this.$message({
                    message: "注意：公司名称只能使用简体中文",
                    type: "warning"
                });
                return;
            }

            let _this = this;
            if (IsLogin(_this)) return;

            let obj = {
                userId: JSON.parse(Cookie.getCookie("dqcUserKey")).userId,
                city: this.city,
                name: this.name,
                industry: this.industry,
                companyType: this.companyType ? "合伙公司" : "有限公司"
            };
           
            if (this.searchStr == newStr) return;
        
            this.$axios({
                type: "get",
                url: "company/checkCompany.do",
                data: obj,
                success: res => {
                    if (res.data.code == 0) {
                        this.searchStr = newStr;
                        this.searchData = 0;
                        this.companyList = res.data.data.names;
                        this.level = res.data.data.possibility;
                        Jump(document.getElementById("searchStyle"))
                    } else {
                        this.$message({
                            message: "网络连接错误:" + res.data.msg,
                            type: "error"
                        });
                    }
                   
                }
            },1);
        },
        showPartBox() {
            this.industryBoxShow = 0;
            this.areaArr = area.area_json;
            this.partBoxShow = 1;
            document.addEventListener("click", this.hidePartBox, false);
        },
        hidePartBox() {
            this.partBoxShow = 0;
            this.twoChoose = 0;
            document.removeEventListener("click", this.hidePartBox, false);
        },
        check(name, idx) {
            this.city = name;
            if (idx == "hot" || this.twoChoose) {
                this.hidePartBox();
                return;
            }
            this.twoChoose = 1;
            this.areaArr = area.area_json[idx].child;
        },
        showIndustryBox() {
            this.partBoxShow = 0;
            this.industryBoxShow = 1;
            document.addEventListener("click", this.hideIndustryBox, false);
        },
        hideIndustryBox() {
            this.industryBoxShow = 0;
            document.removeEventListener("click", this.hideIndustryBox, false);
        },
        checkIndustry(name) {
            this.industry = name;
            this.hideIndustryBox();
        },
        percentage(str) {
       
            let reg=/<i>|<\/i>/g
           let num=str.split("</i>").length-1,
        
               num2= str.replace(reg,"").length;

               let per =Math.round(num/num2*100); 
               
               if (per > 95) per = 95

            return per || 12
        }
    }
};
</script>
<style scoped>
@import url('../assets/Verify_company');
</style>