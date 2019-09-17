<template>
    <div>
        <myTree :data="dataArr" :name="menuName" :loading="loading" @getSub="getSubMenu" @getMsg='getMsg' :activeStr='activeStr' :className='className' :keyWord='keyWord' :obj='obj'></myTree>
    </div>
</template>
 
<script>
import myTree from "./tree-line.vue";
export default {
    props: {
        dataArr: Array,
        activeStr: {
            default: "999999"
        },
        keyWord: String,
        className: String,
        obj: Object
    },
    components: {
        myTree
    },
    data() {
        return {
            // theData: myData,
            menuName: "key", // 显示菜单名称的属性
            loading: false
        };
    },
    methods: {
        getSubMenu(menuItem, callback) {
            this.loading = true;

            let requireData = Object.assign({}, this.obj, {
                keyWord: this.keyWord,
                code: menuItem.code,
                key:
                    this.className == "actionCause" ? "anyouTree" : "courtsTree"
            });
            this.$axios({
                type: "post",
                url: "companyJudge/treeNode",
                data: requireData,
                success: res => {
                    if (
                        res.data.code == 0 &&
                        res.data.data &&
                        res.data.data[menuItem.code].length > 0
                    ) {
                        menuItem.children = res.data.data[menuItem.code];
                        callback(res.data.data[menuItem.code]);
                    } else {
                        menuItem.code = false;
                        callback(false);
                    }
                    this.loading = false;
                }
            });
        },
        getMsg(item, idx) {
            this.$emit("getMsg", this.className, item, idx);
        }
    }
};
</script>