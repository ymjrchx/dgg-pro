<template>
    <div class="categoryBox active">
        <span data-value="1" class="text">{{json.key}}</span>
        <i data-value="1" class="icon icon-close" @click="removeBox(json.key)">×</i>
        <div class="selected-service-box checkbox-all-box">
            <div class="service-list">
                <label class="checkbox-box" @click="allSelectFunc">
                    <label class="checkbox checkbox-all" :class="{checked:allSelect}"></label>
                    <span>全选</span>
                </label>
            </div>
            <div class="selected-service-box-in scroll">
                <div class="service-list" v-for="(item,idx) in json.val" :key='idx'>

                    <label class="checkbox-box noCur" v-if=' item.val == 0'>
                        <label class="checkbox checked"></label>
                        <span>{{item.key}} ({{item.val}})</span>
                    </label>
                    <label class="checkbox-box cur" @click="selectedFunc(idx)" v-else>
                        <label class="checkbox" :class="{'checked':!selectedArr[idx]}"></label>
                        <span>{{item.key}} ({{item.val}})</span>
                    </label>
                </div>
            </div>
            <div class="service-list service-button">
                <!-- <input type="button" data-value="1" value="取消" class="button button-white"> -->
                <input type="button" data-value="1" value="确定" class="button ml6" @click="getSelectClassify"></div>
        </div>
    </div>
</template>
<script>
//  <pagination :totalPage="total" :pageSize='pageSize @pagechange="pagechange" />    totalPage总条数   pageSize每页多少条   pagechange函数名返回一个参数第几页
export default {
    props: {
        json: {
            //显示的关键字
            default: () => {
                return {
                    key: "01类 化学原料",
                    val: [
                        { key: "0101 工业气体，单质", val: 10 },
                        {
                            key:
                                "0102 用于工业、科学、农业、园艺、林业的工业化工原料",
                            val: 16
                        },
                        { key: "0103 放射性元素及其化学品", val: 20 }
                    ]
                };
            }
        },
        index: {
            type: Number,
            default: 1
        }
    },
    mounted() {},
    data() {
        return {
            allSelect: 1,
            selectedArr: []
        };
    },
    methods: {
        selectedFunc(idx) {
            let newArr = [...this.selectedArr];
            newArr[idx] = newArr[idx] ? 0 : 1;
            for (let key of newArr) {
                if (key) {
                    this.allSelect = 0;
                    break;
                }
            }
            this.selectedArr = newArr;
        },
        allSelectFunc() {
            if (this.allSelect) {
                this.allSelect = 0;

                let arr = [];
                for (let key of this.json.val) {
                    arr.push(1);
                }
                this.selectedArr = arr;
            } else {
                this.allSelect = 1;
                this.selectedArr = [];
            }
        },
        removeBox(listName) {
            this.$emit("remove", this.index, listName);
        },
        getSelectClassify() {
            let str = "";
            if (this.allSelect == 0) {
                let childArr = this.json.val;
                let indexArr = this.selectedArr;
                console.log("下标", indexArr, childArr);
                for (let i = 0; i < childArr.length; i++) {
                    if (!indexArr[i] && childArr[i]["val"] != 0) {
                        str += childArr[i].key + "-";
                    }
                }
                str = str.substr(0, str.length - 1);
            }
            console.log("选中的str", str);
            this.$emit("getSelect", str, this.json.index);
        }
    }
};
</script>
<style scoped>
.categoryBox {
    float: left;
    position: relative;
    margin-right: 10px;
    margin-bottom: 10px;
    height: 26px;
    line-height: 24px;
    font-size: 12px;
    text-align: center;
    color: #f08b2f;
    border: 1px solid #f08b2f;
    border-radius: 2px;
    cursor: pointer;
}
.categoryBox .service-list {
    text-align: left;
}
.categoryBox .service-list.service-button {
    padding-right: 15px;
    text-align: right;
}
.categoryBox .service-list.service-button .button {
    width: 40px;
    line-height: 20px;
    background-color: #f08b2f;
}
.categoryBox .service-list.service-button .button.button-white {
    color: #aaa;
    background-color: #fff;
    border: 1px solid #ddd;
}
.categoryBox .service-list .checkbox {
    margin-top: 3px;
    vertical-align: top;
}
.categoryBox .service-list span {
    display: inline-block;
    width: 300px;
    font-size: 12px;
    line-height: 1.5em;
    color: #666;
    vertical-align: top;
    margin-left: 4px;
}
.categoryBox .service-list .disabled span {
    color: #999;
}
.categoryBox .service-list .checkbox-box {
    display: block;
    cursor: pointer;
}
.categoryBox .service-list .checkbox-box.cur:hover span {
    color: #f08b2f;
}
.categoryBox .service-list .checkbox-box.noCur {
    cursor: not-allowed;
}

.categoryBox.active {
    color: #fff;
    background-color: #f08b2f;
    border-color: #f08b2f;
}
.categoryBox:hover {
    border-bottom: none;
}
/*
.categoryBox:hover .selected-service-box {
    display: block;
} */
*/
.categoryBox:not(.active):hover span.text {
    position: relative;
    float: left;
    width: 100%;
    background-color: #fff;
    z-index: 20;
}
.categoryBox span.text {
    display: block;
    padding-left: 10px;
    padding-right: 16px;
    height: 24px;
    text-align: left;
    font-size: 12px;
}
.selected-category-box .icon-close {
    position: absolute;
    right: 3px;
    top: 1px;
    width: 9px;
    height: 9px;
    z-index: 11;
    line-height: 1;
}

.selected-service-box {
    display: none;
    position: absolute;
    left: -1px;
    top: 23px;
    padding: 10px;
    border: 1px solid #f08b2f;
    background-color: #fff;
    z-index: 20;
}
.selected-service-box-in {
    width: 360px;
    height: 124px;
    overflow: auto;
}
.category-show-box {
    float: left;
    width: 1000px;
}
.category-show-box a {
    display: inline-block;
    margin-right: 30px;
    margin-bottom: 5px;
    min-width: 110px;
}
.category-show-box a.active {
    color: #ff5c00;
}
.category-show-box.select-show-category a {
    display: inline-block;
    width: 140px;
}
.category-show-box.select-show-category a:nth-child(6n) {
    margin-right: 0px;
}
.category-show-box.select-show-year a {
    display: inline-block;
    width: 85px;
}
.category-show-box.select-show-year a:nth-child(8n) {
    margin-right: 0px;
}

/* .checkbox */
.selected-category-box a .service-list .checkbox {
    margin-top: 3px;
    vertical-align: top;
    cursor: pointer;
}
.checkbox.checked {
    background-image: url("../assets/images/searchList/icon-checked.png");
    border-color: #ff5c00;
    background-size: 90%;
}

.checkbox {
    display: inline-block;
    width: 13px;
    height: 13px;
    border-width: 1px;
    border-style: solid;
    border-color: #999;
    background-position: center;
    background-repeat: no-repeat;
    vertical-align: middle;
    user-select: none;
}
.checkbox input[type="checkbox"] {
    width: 100%;
    height: 100%;
    opacity: 0;
}
</style>
