<template>
    <div>
        <Head/>
        <div class="search_body">
            <!-- <img src="../assets/images/search-bg-brand.png" alt="顶企查" class="left_img"> -->
            <div class="logoSearch company">
                <div class="container w">
                    <div class="clogo">经营范围智能生成器</div>
                    <div class="searchStyle" id="searchStyle">
                        <div class="part_i">
                            <input type="text" placeholder="城市" @click.stop="showPartBox" v-model="city">
                            <div class="childBox part" v-show="partBoxShow" @click.stop="partBoxShow = 1">
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
                        <input type="button" value="一键生成" class="button btn search_button" @click="submit">
                    </div>
                    <div style="margin-top:70px;color:#fff;font-size:16px">经营范围不知道怎么填？智能生成！</div>
                </div>
            </div>
            <div class=" marketing" id="scopedList">

                <div class="verifyData2 clear" v-if="companyList">
                    <div class="jg_container fl" style="min-height:100px; width:860px;">
                        <table class="scopTable" v-if="companyList.length > 0">
                            <tr>
                                <th width='60px'></th>
                                <th width='680px'></th>
                                <th width='60px'></th>
                            </tr>
                            <tr v-for="(item,idx) in companyList" :key="idx">
                                <td valign="top">
                                    <span class="tuijianBtn">推荐{{idx+1}}</span>
                                </td>
                                <td class="middle-t">
                                    <p class="mb10" style="min-height:40px">{{item.qygsDetail.scope}}</p>
                                    <div>参考公司：
                                        <a class="text-primary" @click="goCompany(item.companyId,item.name)"> {{item.name}}</a>
                                    </div>
                                </td>
                                <td class="text-right" valign="top">
                                    <el-button size="mini" @click="copyStr(idx)">复制</el-button>
                                </td>
                            </tr>
                        </table>
                        <div class="nextBtnBox">
                            <button class="nextBtn" @click="newPage"> <img src="../assets/images/5-121204193R5-50.gif" alt="" v-show="searchData"> 换一批</button>
                        </div>
                    </div>
                    <div class="fr">
                        <router-link to='/Verify_company'><img src="../assets/images/hm_img.gif"></router-link>

                    </div>
                </div>
                <div v-else style="margin-top:20px">
                    <div class="jyfw_tj cen" style=" border: 0;">
                        <div class="tit">公司的经营范围有什么讲究？</div>
                        <dl>
                            <dt><img src="../assets/images/jyfw1.png"></dt>
                            <dd>
                                <h2>
                                    <span>行业差异性</span>
                                </h2>
                                <p>不同的行业类型，对应不同的经营范围。原则上不能随意写</p>
                            </dd>
                        </dl>
                        <dl style=" margin: 30px 5% 50px 5%;">
                            <dt><img src="../assets/images/jyfw2.png"></dt>
                            <dd>
                                <h2>
                                    <span>地区差异性</span>
                                </h2>
                                <p>同一个行业，不同地区也有地方差异性，写法不一样</p>
                            </dd>
                        </dl>
                        <dl>
                            <dt><img src="../assets/images/jyfw3.png"></dt>
                            <dd>
                                <h2>
                                    <span>许可经营项</span>
                                </h2>
                                <p>不同时期，许可经营项是不一样的。写法有差异</p>
                            </dd>
                        </dl>
                        <div class="clear2"></div>
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
import Common, { IsLogin, Jump } from "@/util/common";
export default {
    components: { Head },
    data() {
        return {
            city: "",
            areaArr: [],
            partBoxShow: 0, //显示地方选项
            twoChoose: 0, //第几次选择地方
            industry: "", //行业类型
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
            searchData: 0,
            companyList: "",
            level: "",
            searchStr: "",
            pageIndex: 1, //换一批的页数
            isLoading: 1
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
        Jump(document.getElementById("app"));
    },
    destroyed() {
        document.onkeydown = e => {};
    },
    methods: {
        submit() {
            this.pageIndex = 1;
            this.requierAjax();
        },
        requierAjax() {
            if (!this.city || !this.industry) {
                this.$message({
                    message: "请填写完整的搜索信息",
                    type: "warning"
                });
                return;
            }
            let newStr = this.city + this.industry;
            var reg = /^[\u4e00-\u9fa5]+$/;
            newStr = newStr.replace(/ /g, "");

            if (!reg.test(newStr)) {
                this.$message({
                    message: "注意：搜索类容只能使用简体中文",
                    type: "warning"
                });
                return;
            }
            let _this = this;
            if (IsLogin(_this)) return;

            let obj = {
                city: this.city,
                industry: this.industry,
                pageSize: 5,
                pageIndex: this.pageIndex
            };
            this.searchData = 1;
            this.$axios(
                {
                    type: "post",
                    url: "api/resourcePro",
                    data: obj,
                    success: res => {
                        //console.log(333,res)
                        if (res.data.code == 0) {
                            this.searchData = 0;
                            this.companyList = res.data.data.listData;
                            this.totalNum = res.data.data.totalCount;
                            Jump(document.getElementById("scopedList"));
                        } else {
                            this.companyList = "";

                            this.$message({
                                message: res.data.msg,
                                type: "error"
                            });
                        }
                    }
                },
                1
            );
        },
        newPage() {
            if (this.pageIndex < this.totalNum)
                this.pageIndex = this.pageIndex + 1;
            else this.pageIndex = 1;
            this.requierAjax();
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
        goCompany(companyId, name) {
            Common.newPage("Company_detail", { id: companyId, title: name });
        },
        copyStr(idx) {
            let str = this.companyList[idx].qygsDetail.scope;
            let that = this;
            this.$copyText(str).then(
                function(e) {
                    that.$message({
                        message: "复制成功",
                        type: "success",
                        duration: 1500
                    });
                },
                function(e) {
                    that.$message.error("复制失败，请重试");
                }
            );
        }
    }
};
</script>
<style scoped>
@import url("../assets/Verify_company");
.searchStyle .part_i {
    width: 24%;
}
.searchStyle .industry_i {
    width: 56%;
}
.logoSearch.company {
    background: #3fa8c6 url("../assets/images/search-back.jpg") center center
        no-repeat;
    background-size: 100% auto;
}
.searchStyle .childBox.industry {
    left: 0;
}
.search_button {
    width: 17%;
}
.jyfw_tj dl {
    width: 30%;
    float: left;
    margin: 30px 0 50px 0;
}
.jyfw_tj dt {
    width: 25%;
    float: left;
}
.jyfw_tj dt img {
    width: 70px;
    margin-top: 16px;
}
.jyfw_tj dd {
    width: 70%;
    float: left;
}
.jyfw_tj dd h2 {
    font-size: 18px;
    border-bottom: 1px solid #f0f0f0;
    margin-bottom: 15px;
    margin-top: 0;
    color: #333;
    font-weight: 700;
}
.jyfw_tj dd h2 span {
    border-bottom: 2px solid #fbd956;
    line-height: 35px;
    display: inline-block;
    padding-bottom: 6px;
}
.jyfw_tj dd p {
    font-size: 14px;
    color: #999;
    line-height: 26px;
}
.verifyData2 {
    width: 100%;
    margin: auto;
    background: #fff;
    border-radius: 6px;
    padding: 30px 20px 0;
}
.jg_container {
    padding: 0 30px 20px;
}
.scopTable .middle-t {
    padding: 20px 20px 10px;
}
.scopTable td {
    padding: 20px 0;
    border-bottom: 1px solid #eaeaea;
    min-height: 120px;
}
.tuijianBtn {
    width: 45px;
    float: left;
    background: #409eff;
    color: #fff;
    border-radius: 4px;
    text-align: center;
    line-height: 22px;
    font-size: 12px;
    margin-top: 6px;
}
.nextBtnBox {
    text-align: center;
    width: 100%;
    padding: 50px 0 10px 0;
}
.nextBtnBox .nextBtn {
    background: #409eff;
    color: #fff;
    border-radius: 4px;
    width: 260px;
    text-align: center;
    line-height: 50px;
    height: 50px;
    font-size: 15px;
    border: none;
    outline: none;
    font-weight: bold;
}
.nextBtnBox .nextBtn img {
    vertical-align: middle;
    width: 18px;
}
</style>