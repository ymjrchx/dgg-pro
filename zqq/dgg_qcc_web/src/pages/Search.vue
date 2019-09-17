<template>
    <div class="search_body">
        <img src="../assets/images/search-bg-brand.png" alt="顶企查" class="left_img">
        <div class="logoSearch">
            <div class="container w" style="line-height:1.8">
                <div class="clogo">{{headArr[isCheck]}}</div>
                <div class="searchStyle">
                    <ul class="style_list">
                        <li v-for="(item,index) in styleArr" :class="{'active': isCheck == index }" :key="index" @click="updateCheck(index)">{{item}}</li>
                    </ul>
                    <div class="searchBox input-group">
                        <el-autocomplete popper-class="input-autocomplete" v-model="tabval" :fetch-suggestions="querySearchAsync" :placeholder="placeHolderArr[isCheck]" @select="handleSelect" @keyup.enter.native="submit">
                            <template slot-scope="{ item }">
                                <div class="list_search">
                                    <em v-html="item.heightLight ? item.heightLight : item.value"></em>
                                    <!-- <span class="addr" v-if="item.flag">{{item.flag}}</span> -->
                                </div>
                            </template>
                        </el-autocomplete>
                        <input type="button" value="查一下" class="button btn search_button" @click="submit">
                    </div>
                </div>
            </div>
        </div>
        <div class=" marketing">
            <div class="row">
                <div class="col-lg-4">
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
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import Common from "@/util/common";
import Store from "@/store";

export default {
    name: "",
    components: {},
    data() {
        return {
            headArr: ["税号搜索", "商标搜索", "专利搜索", "企业搜索"],
            styleArr: ["税号", "商标", "专利"],
            placeHolderArr: [
                "请输入公司名称或税号",
                "请输入商标名称、注册号或申请人名称",
                "请输入专利名称、申请号或申请人名称"
            ],
            isCheck: 0,
            restaurants: [],
            tabval: "",
            timeout: null,
            isShowVip: false
        };
    },
    created() {
        // this.isCheck = this.$route.query.type;
        switch (this.$route.query.type) {
            case "tax":
                this.isCheck = 0;
                break;
            case "brand":
                this.isCheck = 1;
                break;
            case "patent":
                this.isCheck = 2;
                break;
            default:
                this.isCheck = 3;
        }
        Store.commit("updateHeadActive", 1);
    },
    methods: {
        updateCheck(index) {
            this.isCheck = index;
            this.tabval = "";
        },
        querySearchAsync(queryString, cb) {
            let _this = this;

            if (queryString.length > 1) {
                var results = this.getMsg(queryString, res => {
                    cb(res);
                });
            } else {
                if (_this.initHistory().length > 0) {
                    cb(_this.initHistory());
                } else {
                    cb([]);
                }
            }
        },

        handleSelect(item) {
            // if (item.hasOwnProperty("companyId")) {
            //     Common.newPage("Company_detail", { id: item.companyId });
            //     Common.remberHistory("tax_history", item.value, 5);
            // }
        },
        getMsg(e, func) {
            let _this = this;
            // let usrStr='http://localhost:8088/companyItem/searchFive?keyWord=%E7%99%BE%E5%BA%A6&count=5';
            let obj = {
                keyWord: e,
                count: 5,
                type:
                    this.isCheck === 0
                        ? "tax"
                        : this.isCheck == 1 ? "brand" : "patent"
            };
            // piInventName  专利
            this.$axios({
                type: "get",
                url: "companyItem/searchFive",
                data: obj,
                success: res => {
                    if (res.data.code == 0) {
                        let arr = [];

                        for (let item of res.data.data) {
                            arr.push({
                                value:
                                    obj.type == "patent"
                                        ? item.piInventName
                                        : item.name,
                                heightLight: item.lightVal
                            });
                        }

                        func(arr);
                    } else func([]);
                },
                fail: res => {
                    console.log("错误信息", res);
                    func([]);
                }
            });
        },
        initHistory() {
            let arr;
            Common.getHistory("tax_history");

            switch (this.isCheck) {
                case 0:
                    arr = Common.getHistory("tax_history");
                    break;
                case 1:
                    arr = Common.getHistory("brand_history");
                    break;
                case 2:
                    arr = Common.getHistory("patent_history");
                    break;
            }

            return arr ? arr : [];
        },
        submit() {
            let val = this.tabval;
            if (!val || val == "") {
                this.$message({
                    message: "请先填写搜索内容！",
                    type: "error",
                    position: "center",
                    duration: 1000
                });
            } else {
                let str = "tax",
                    pathStr = "/TaxContent";
                switch (this.isCheck) {
                    case 0:
                        Common.remberHistory("tax_history", val, 5);
                        pathStr = "/TaxContent";
                        str = "tax";
                        break;
                    case 1:
                        Common.remberHistory("brand_history", val, 5);
                        pathStr = "/BrandList";
                        str = "brand";
                        break;
                    case 2:
                        Common.remberHistory("patent_history", val, 5);
                        pathStr = "/PatentList";
                        str = "patent";
                        break;
                }
                sessionStorage.setItem("aboutKey", val);
                this.$router.push({
                    path: pathStr,
                    query: { keyWord: val, type: str }
                });
            }
            // this.$router.push({ path: "/TaxContent", query: "A" });
        }
    }
};
</script>
<style scoped>
.search_body {
    background-color: #fff;
    position: relative;
}
.left_img {
    position: absolute;
    top: 120px;
    right: 20px;
}
.logoSearch {
    margin-top: 1px;

    padding: 140px 0 50px;
    text-align: center;
}
.container {
    width: 1100px;
}
.clogo {
    font-size: 55px;
    margin-bottom: 70px;
}
.searchStyle {
    margin-left: 16.66666667%;
    width: 66.66666667%;
    margin: auto;
}
.style_list {
    text-align: left;
    overflow: hidden;
}
.style_list li {
    float: left;
    font-size: 22px;
    /* display: inline-block; */
    margin-right: 30px;
    margin-bottom: 10px;
    cursor: pointer;
}
.style_list li:hover {
    color: #128bed;
}

.style_list .active {
    color: #128bed;
    border-bottom: 2px solid #128bed;
}
.searchBox {
    position: relative;
    display: table;
    border-collapse: separate;
    width: 100%;
}
.searchBox.input-group .el-autocomplete {
    width: 86%;
    height: 50px;
    float: left;
}
.search_button {
    box-sizing: border-box;
    width: 14%;
    color: white;
    text-align: center;
    height: 50px;
    font-size: 18px;
    background: #128bed;
    border: #128bed 1px solid;
    transition: all 0.2s ease-in-out;
    border-radius: 0 4px 4px 0;
}
.search_button:hover {
    background-color: #1877c3;
}
/* //下面四个图 */
.marketing {
    margin: auto;
    margin-top: 120px;
    padding-bottom: 200px;
    width: 1250px;
}
.row {
    margin-right: -15px;
    margin-left: -15px;
}
.marketing .col-lg-4 {
    margin-bottom: 20px;
    text-align: center;
}
.marketing h2 {
    font-weight: normal;
    font-size: 16px;
    margin-top: 20px;
    color: #6086a4;
}
</style>