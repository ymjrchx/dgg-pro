<template>
    <div class="search_body">
        <!-- <img src="../../assets/images/search-bg-brand.png" alt="顶企查" class="left_img"> -->
        <div class="logoSearch">
            <div class="container w">
                <div class="clogo">裁判文书查询</div>
                <div class="searchStyle">
                    <ul class="style_list">
                        <li v-for="(item,index) in styleArr" :class="{'active': isCheck == index }" :key="index" @click="updateCheck(index)">{{item}}</li>
                    </ul>
                    <div class="searchBox input-group">
                        <!-- <el-autocomplete popper-class="input-autocomplete" v-model="tabval" :placeholder="placeHolderArr[isCheck]" @keyup.enter.native="submit">
                            <template slot-scope="{ item }">
                                <div class="list_search">
                                    <em v-html="item.heightLight ? item.heightLight : item.value"></em>
                                </div>
                            </template>
                        </el-autocomplete> -->
                        <el-input class="ele-jun" v-model="tabval" :placeholder="placeHolderArr[isCheck]" @keyup.enter.native="submit" maxlength="30"></el-input>

                        <input type="button" value="查一下" class="button btn search_button" @click="submit">
                    </div>
                </div>
            </div>
        </div>
        <div class=" marketing">
            <div class="row">
                <div class="col-lg-4">
                    <img src="../../assets/images/icon1.png" />
                    <h2>毫秒查询&nbsp;&nbsp;节省更多时间</h2>
                </div>
                <div class="col-lg-4">
                    <img src="../../assets/images/icon2.png" />
                    <h2>数据全面 &nbsp;&nbsp;提供更多数据</h2>
                </div>
                <div class="col-lg-4">
                    <img src="../../assets/images/icon3.png" />
                    <h2>精确匹配&nbsp;&nbsp;命中目标信息</h2>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: "",
    components: {},
    data() {
        return {
            // styleArr: ["案例","法规","律师","知识"],
            styleArr: ["案例"],
            placeHolderArr: [
                "请输入案例关键字,如案件类型、当事人、被告人.....",
                "输入关键词，支持直接看法规、法条",
                "输入律师地域、姓名、律所、法院、案由、案情描述",
                "输入关键词检索知识 "
            ],
            isCheck: 0,
            restaurants: [],
            tabval: "",
            timeout: null
        };
    },
    created() {},
    methods: {
        updateCheck(index) {
            this.isCheck = index;
        },
        // loadAll() {
        //     return [];
        // },
        // querySearchAsync(queryString, cb) {
        //     var restaurants = this.restaurants;
        //     var results = queryString
        //         ? restaurants.filter(this.createStateFilter(queryString))
        //         : restaurants;
        //     clearTimeout(this.timeout);
        //     this.timeout = setTimeout(() => {
        //         cb(results);
        //     }, 500 * Math.random());
        // },
        // createStateFilter(queryString) {
        //     return state => {
        //         return (
        //             state.value
        //                 .toLowerCase()
        //                 .indexOf(queryString.toLowerCase()) === 0
        //         );
        //     };
        // },
        // handleSelect(item) {
        //     console.log(item);
        // },
        // getMsg(e) {
        //     let str = e + "";
        //     if (str.length == 2) {
        //         console.log("执行");
        //         this.restaurants = [
        //             {
        //                 value: "测试输入数据",
        //                 address: "普陀区金沙江路1699号鑫乐惠美食广场A13"
        //             },
        //             {
        //                 value: "测试数据2",
        //                 address: "普陀区金沙江路1699号鑫乐惠美食广场A13"
        //             },
        //             {
        //                 value: "测试数据3",
        //                 address: "普陀区金沙江路1699号鑫乐惠美食广场A13"
        //             }
        //         ];
        //     } else if (!str || str.length == 0) {
        //         this.restaurants = this.loadAll();
        //     }
        // },
        submit() {
            if (!this.tabval) {
                this.$message({
                    message: "请先填写搜索内容！",
                    type: "error",
                    position: "center",
                    duration: 1000
                });
                return;
            }
            this.$router.push({
                path: "/LawToolContent",
                query: { keyWords: this.tabval }
            });
        }
    },
    mounted() {}
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
    font-size: 48px;
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
.ele-jun {
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