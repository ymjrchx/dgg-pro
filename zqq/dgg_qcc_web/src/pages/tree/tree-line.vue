<template>
    <ul class="tree-menu">
        <li v-for="(item, index) in data" class="text-delic itemLi" v-if='item.val > 0'>
            <span :title="item[name] || item.name" class="law_item text-delic">
                <i @click="toggle(item, index)" :class="['icon', folderIconList[index] || 'file-text', loading ? loadingIconList[index] : '']"></i>
                <span :class="['item_t', activeStr == item[name] ? 'active' : '' ]" @click="getMsg(item,index)">{{ item[name] }}({{item.val}})</span>
            </span>
            <el-collapse-transition>
                <tree-menu v-if="scope[index]" :keyWord='keyWord' :loading="loading2" :name="name" @getSub="getSubMenu" @getMsg='getMsg' :data="item.children" :activeStr='activeStr' :className='className' :obj='obj'></tree-menu>
            </el-collapse-transition>
        </li>
    </ul>
</template>
 
<script>
export default {
    name: "treeMenu",
    props: {
        data: Array,
        name: String,
        loading: {
            default: false
        },
        activeStr: {
            default: "999999"
        },
        className: String,
        keyWord: String,
        obj: Object
    },
    data() {
        return {
            folderIconList: [], // folder 关闭的文件夹  folder-open 打开的文件夹 file-text文件  loading 加载中
            loadingIconList: [],
            scope: [],
            loading2: ""
        };
    },
    created() {
        this.data.forEach((item, index) => {
            if (
                item.code &&
                (this.className == "actionCause" || this.className == "area")
            ) {
                this.folderIconList[index] = "folder";
            }
        });
    },
    methods: {
        doTask(bool, index) {
            let newArr = [...this.folderIconList];
            if (bool) {
                this.$set(this.scope, index, !this.scope[index]);

                newArr[index] = this.scope[index] ? "folder-open" : "folder";

                this.folderIconList = newArr;
            } else {
                newArr[index] = false;
                this.folderIconList = newArr;
            }

            // this.$set(this.scope, index, !this.scope[index]);
            // this.folderIconList[index] = this.scope[index]
            //     ? "folder-open"
            //     : "folder";
        },
        toggle(item, idx) {
            this.loadingIconList = [];
            if (
                (this.className == "actionCause" || this.className == "area") &&
                item.code
            ) {
                if (item.children && item.children.length) {
                    this.doTask(true, idx);
                } else {
                    this.loadingIconList[idx] = "loading";

                    this.$emit("getSub", item, subMenuList => {
                        this.doTask(subMenuList, idx);
                    });
                }
            }
        },
        getSubMenu(menuItem, callback) {
            this.loading2 = true;
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
                    this.loading2 = false;
                }
            });
        },
        getMsg(item, idx) {
            this.data.forEach((item, index) => {
                if (
                    item.code &&
                    (this.className == "actionCause" ||
                        this.className == "area")
                ) {
                    this.folderIconList[index] = "folder";
                }
            });
            this.scope = [];

            this.$emit("getMsg", item, idx);
        }
    }
};
</script>
 
<style scoped>
.tree-menu {
    list-style: none;
}
.tree-menu li {
    line-height: 2;
}

.icon {
    display: inline-block;
    width: 20px;
    height: 20px;
    background-position: center center;
    background-repeat: no-repeat;
    vertical-align: -4px;
    background-size: 12px;
}
.icon.folder {
    background-image: url("../../assets/images/add.png");
}
.icon.folder-open {
    background-image: url("../../assets/images/reduce.png");
}
.icon.file-text {
    background-image: url("../../assets/images/dian.png");
}
.icon.loading {
    background-image: url("../../assets/images/5-121204193R5-50.gif");
    background-size: 15px;
}
.law_item {
    cursor: pointer;
    display: block;
}
.law_item:hover {
    background: #eaf5fd;
}
.item_t:hover,
.item_t.active {
    color: #409eff;
}
.itemLi > ul {
    padding-left: 16px;
}
</style>